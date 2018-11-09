package twitter_client.eoinahern.ie.twitter_client.ui.twitterfeed

import androidx.recyclerview.widget.DiffUtil
import twitter_client.eoinahern.ie.twitter_client.data.model.Tweet
import twitter_client.eoinahern.ie.twitter_client.di.PerScreen
import javax.inject.Inject

@PerScreen
class TwitterFeedDiffUtil @Inject constructor() : DiffUtil.Callback() {

    private var oldList: MutableList<Tweet> = mutableListOf()
    private var newList: MutableList<Tweet> = mutableListOf()

    fun setLists(oldListIn: List<Tweet>, newListIn: List<Tweet>) {
        oldList.clear()
        newList.clear()

        oldList.addAll(oldListIn)
        newList.addAll(newListIn)
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].id_str == newList[newItemPosition].id_str

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {

        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return ((oldItem.id_str == newItem.id_str) && (oldItem.datetime == newItem.datetime) &&
                (oldItem.text == newItem.text) && (oldItem.user.name == newItem.user.name))
    }
}