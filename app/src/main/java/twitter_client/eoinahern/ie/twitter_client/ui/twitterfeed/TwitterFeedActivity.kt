package twitter_client.eoinahern.ie.twitter_client.ui.twitterfeed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import dagger.android.AndroidInjection
import androidx.lifecycle.ViewModelProviders
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
        initAdapter()
        initViewModel()
        viewModel.getTwitterFeed()
    }

    private fun initAdapter() {
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelProvider)
            .get(TwitterFeedViewModel::class.java)

        viewModel.getData().observe(this,
            Observer<List<Tweet>> { list ->
                adapter.updateList(list)
            })
    }


    private fun showLoading() {

    }

    private fun hideLoading() {

    }
}
