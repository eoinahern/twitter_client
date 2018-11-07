package twitter_client.eoinahern.ie.twitter_client.ui.twitterfeed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import twitter_client.eoinahern.ie.twitter_client.data.sharedprefs.SharedPrefsHelper
import twitter_client.eoinahern.ie.twitter_client.tools.TWEET_TTL_KEY
import javax.inject.Inject


class TwitterFeedActivity : AppCompatActivity() {

    @Inject
    lateinit var adapter: TwitterFeedAdapter

    @Inject
    lateinit var viewModelProvider: TwitterFeedViewModelProvider

    @Inject
    lateinit var sharedPrefs: SharedPrefsHelper

    private lateinit var viewModel: TwitterFeedViewModel

    private val INNITTERM = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_twitter_feed)
        setUpToolbar()
        initAdapter()
        initViewModel()
        showLoading()
        setUpSearchView()
        viewModel.getTwitterFeed(INNITTERM)
    }

    private fun setUpToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setIcon(R.drawable.ic_twitter)
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
                hideLoading()
                println(list.size)
                adapter.updateList(list)
            })

        viewModel.getErrorState().observe(this,
            Observer<Boolean> {
                if (it)
                    showError()
            })
    }

    private fun setUpSearchView() {
        searchView.isSubmitButtonEnabled = true

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchView.clearFocus()
                viewModel.unsubscribe()
                adapter.deleteList()
                showLoading()
                viewModel.getTwitterFeed(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
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
        hideLoading()
        errorTxt.visibility = View.VISIBLE
    }

    private fun hideError() {
        errorTxt.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.unsubscribe()
    }

}
