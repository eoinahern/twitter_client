package twitter_client.eoinahern.ie.twitter_client.ui.twitterfeed

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.tweet_item_layout.*
import twitter_client.eoinahern.ie.twitter_client.R
import twitter_client.eoinahern.ie.twitter_client.data.model.Tweet
import twitter_client.eoinahern.ie.twitter_client.data.model.getDateTime
import twitter_client.eoinahern.ie.twitter_client.data.sharedprefs.SharedPrefsHelper
import twitter_client.eoinahern.ie.twitter_client.di.PerScreen
import twitter_client.eoinahern.ie.twitter_client.tools.DateUtil
import twitter_client.eoinahern.ie.twitter_client.tools.TWEET_TTL_KEY
import javax.inject.Inject

@PerScreen
class TwitterFeedAdapter @Inject constructor(private val diffutilCallback: TwitterFeedDiffUtil) :
    RecyclerView.Adapter<TwitterFeedAdapter.ViewHolder>() {

    private val list: MutableList<Tweet> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tweet_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tweet = list[position]
        holder.nameTxt.text = tweet.user.name
        holder.tweetTxt.text = tweet.text
    }

    /*
    * at the moment just updating the list seems like the best option
    * DiffUtil causes scrolling to be jerky and duplicate tweets?
    *
     */

    fun updateList(listIn: List<Tweet>) {
        list.clear()
        list.addAll(listIn)
        notifyDataSetChanged()
    }

    fun deleteList() {
        val currentSize = itemCount
        list.clear()
        notifyItemRangeRemoved(0, currentSize)
    }

    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer
}