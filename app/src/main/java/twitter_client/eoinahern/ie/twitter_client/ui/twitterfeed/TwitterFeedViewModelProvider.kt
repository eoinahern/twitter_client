package twitter_client.eoinahern.ie.twitter_client.ui.twitterfeed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalStateException
import javax.inject.Inject

class TwitterFeedViewModelProvider @Inject constructor(private val viewModel: TwitterFeedViewModel) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {


        throw IllegalStateException("unknown viewmodel class")
    }


}