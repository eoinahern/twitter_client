package twitter_client.eoinahern.ie.twitter_client.ui.twitterfeed

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.reactivex.Observer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import twitter_client.eoinahern.ie.twitter_client.data.database.TweetDao
import twitter_client.eoinahern.ie.twitter_client.data.sharedprefs.SharedPrefsHelper
import twitter_client.eoinahern.ie.twitter_client.domain.DeleteAllTweetsInteractor
import twitter_client.eoinahern.ie.twitter_client.domain.DeleteExpiredTweetsInteractor
import twitter_client.eoinahern.ie.twitter_client.domain.GetTwitterDataInteractor
import twitter_client.eoinahern.ie.twitter_client.tools.resources.ResourceProvider
import java.net.SocketTimeoutException

class TwitterFeedViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

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


    @InjectMocks
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

    //create helper class for this
    private fun <T> any(): T {
        Mockito.any<T>()
        return uninitialized()
    }

    private fun <T> uninitialized(): T = null as T

    @Test
    fun getTwitterFeed() {
        val term = "hi"
        Mockito.`when`(mockGetTwitterDataInteractor.setSearchTerm(term)).thenReturn(mockGetTwitterDataInteractor)

        Mockito.doAnswer { innvocation ->
            val obs = innvocation.arguments[0] as Observer<Unit>
            obs.onError(SocketTimeoutException())
            obs.onComplete()
        }.`when`(mockGetTwitterDataInteractor).execute(any())


        twitterFeedViewModel.getTwitterFeed(term)
        Mockito.verify(mockGetTwitterDataInteractor).setSearchTerm(term)
        Mockito.verify(mockResourceProvider).getString(Mockito.anyInt())
    }
}