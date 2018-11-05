package twitter_client.eoinahern.ie.twitter_client.ui.twitterfeed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import dagger.android.AndroidInjection
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_twitter_feed.*
import twitter_client.eoinahern.ie.twitter_client.R
import twitter_client.eoinahern.ie.twitter_client.data.model.Tweet
import javax.inject.Inject


class TwitterFeedActivity : AppCompatActivity() {

    @Inject
    lateinit var adapter: TwitterFeedAdapter

    @Inject
    lateinit var viewModelProvider: TwitterFeedViewModelProvider

    private lateinit var viewModel: TwitterFeedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_twitter_feed)
        setUpToolbar()
        initAdapter()
        initViewModel()
        showLoading()
        setUpSearchView()
        viewModel.getTwitterFeed()
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
                adapter.updateList(list)
            })
    }

    private fun setUpSearchView() {
        searchView.isSubmitButtonEnabled = true

    }


    private fun showLoading() {
        recycler.visibility = View.GONE
        loading.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        recycler.visibility = View.VISIBLE
        loading.visibility = View.GONE
    }

}
