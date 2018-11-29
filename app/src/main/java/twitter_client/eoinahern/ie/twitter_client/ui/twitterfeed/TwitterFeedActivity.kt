package twitter_client.eoinahern.ie.twitter_client.ui.twitterfeed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import dagger.android.AndroidInjection
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_twitter_feed.*
import twitter_client.eoinahern.ie.twitter_client.R
import twitter_client.eoinahern.ie.twitter_client.data.model.Tweet
import twitter_client.eoinahern.ie.twitter_client.tools.task.RepeatingTaskExecutor
import javax.inject.Inject


class TwitterFeedActivity : AppCompatActivity(), TwitterFeedActivityCallback {

    @Inject
    lateinit var adapter: TwitterFeedAdapter

    @Inject
    lateinit var viewModelProvider: TwitterFeedViewModelProvider

    @Inject
    lateinit var repeatingTask: RepeatingTaskExecutor

    private lateinit var viewModel: TwitterFeedViewModel

    private val initTerm = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_twitter_feed)
        setUpToolbar()
        initRepeatingTask()
        initAdapter()
        initViewModel()
        showLoading()
        setUpSearchView()
        viewModel.getTwitterFeed(initTerm)
    }

    private fun setUpToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setIcon(R.drawable.ic_twitter)
    }

    private fun initRepeatingTask() {
        repeatingTask.setActivityCallback(this)
        repeatingTask.startRepeatingTask()
    }

    private fun initAdapter() {
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recycler.adapter = adapter
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelProvider)
            .get(TwitterFeedViewModel::class.java)

        viewModel.getData().observe(this,
            Observer<List<Tweet>> { list ->
                if (!list.isEmpty()) {
                    hideLoading()
                    adapter.updateList(list)
                }
            })

        viewModel.getErrorState().observe(this,
            Observer<String> {
                showError()
                errorTxt.text = it
            })
    }

    private fun setUpSearchView() {
        searchView.isSubmitButtonEnabled = true

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                showLoading()
                searchView.clearFocus()
                viewModel.clearTweetList()
                viewModel.unsubscribe()
                viewModel.getTwitterFeed(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.time_to_live_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.one_minute -> {
                viewModel.setTTLTime(1L)
                return true
            }
            R.id.two_minutes -> {
                viewModel.setTTLTime(2L)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showLoading() {
        hideError()
        recycler.visibility = View.GONE
        loading.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        recycler.visibility = View.VISIBLE
        loading.visibility = View.GONE
        hideError()
    }

    private fun showError() {
        recycler.visibility = View.GONE
        loading.visibility = View.GONE
        errorTxt.visibility = View.VISIBLE
    }

    private fun hideError() {
        errorTxt.visibility = View.GONE
    }

    override fun checkTTLOnTweets() {
        viewModel.delete()
    }

    override fun onDestroy() {
        super.onDestroy()
        repeatingTask.clearTask()
        viewModel.unsubscribe()
    }

}
