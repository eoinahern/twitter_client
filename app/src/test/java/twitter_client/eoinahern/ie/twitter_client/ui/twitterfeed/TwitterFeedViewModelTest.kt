package twitter_client.eoinahern.ie.twitter_client.ui.twitterfeed

import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import twitter_client.eoinahern.ie.twitter_client.data.database.TweetDao
import twitter_client.eoinahern.ie.twitter_client.data.sharedprefs.SharedPrefsHelper
import twitter_client.eoinahern.ie.twitter_client.domain.DeleteAllTweetsInteractor
import twitter_client.eoinahern.ie.twitter_client.domain.DeleteExpiredTweetsInteractor
import twitter_client.eoinahern.ie.twitter_client.domain.GetTwitterDataInteractor
import twitter_client.eoinahern.ie.twitter_client.tools.resources.ResourceProvider

class TwitterFeedViewModelTest {

    @Mock
    lateinit var mockDeleteAllTweetsInteractor: DeleteAllTweetsInteractor

    @Mock
    lateinit var mockDeleteExpiredTweetsInteractor: DeleteExpiredTweetsInteractor

    @Mock
    lateinit var mockGetTwitterDataInteractor: GetTwitterDataInteractor

    @Mock
    lateinit var mockTweetDao: TweetDao

    @Mock
    lateinit var mockSharedPrefsHelper: SharedPrefsHelper

    @Mock
    lateinit var mockResourceProvider: ResourceProvider

    lateinit var twitterFeedViewModel: TwitterFeedViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        twitterFeedViewModel = TwitterFeedViewModel(
            mockGetTwitterDataInteractor, mockDeleteAllTweetsInteractor,
            mockDeleteExpiredTweetsInteractor, mockTweetDao, mockSharedPrefsHelper, mockResourceProvider
        )
    }

    @Test
    fun setTTLTime() {
        twitterFeedViewModel.setTTLTime(1L)
        Mockito.verify(mockSharedPrefsHelper).saveLong(Mockito.anyString(), Mockito.anyLong())
    }

    @Test
    fun delete() {
        twitterFeedViewModel.delete()
        Mockito.verify(mockDeleteExpiredTweetsInteractor).execute()
    }

    @Test
    fun unsubscribe() {
        twitterFeedViewModel.unsubscribe()
        Mockito.verify(mockDeleteAllTweetsInteractor).execute()
        Mockito.verify(mockGetTwitterDataInteractor).unsubscribe()
    }
}