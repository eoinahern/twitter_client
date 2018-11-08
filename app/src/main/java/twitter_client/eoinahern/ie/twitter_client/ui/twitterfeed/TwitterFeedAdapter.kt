package twitter_client.eoinahern.ie.twitter_client.ui.twitterfeed

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
class TwitterFeedAdapter @Inject constructor(
) :
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

    fun updateList(listIn: List<Tweet>) {

        /*if (list.isEmpty()) {
            list.addAll(listIn)
            notifyItemRangeInserted(0, listIn.size)
            return
        } else {

            val currentInt = list.size
            list.addAll(listIn)
            notifyItemRangeInserted(currentInt, listIn.size)
        }*/
        if (list.size > 0)
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