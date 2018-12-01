package twitter_client.eoinahern.ie.twitter_client.domain

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import twitter_client.eoinahern.ie.twitter_client.data.database.TweetDao
import twitter_client.eoinahern.ie.twitter_client.data.sharedprefs.SharedPrefsHelper
import twitter_client.eoinahern.ie.twitter_client.tools.DateUtil

class DeleteExpiredTweetsInteractorTest {

    @Mock
    lateinit var mockTweetDao: TweetDao

    @Mock
    lateinit var mockSharedPrefsHelper: SharedPrefsHelper

    @Mock
    lateinit var mockDateUtil: DateUtil

    private lateinit var deleteExpiredTweetsInteractor: DeleteExpiredTweetsInteractor

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        deleteExpiredTweetsInteractor = DeleteExpiredTweetsInteractor(
            mockTweetDao,
            mockSharedPrefsHelper, mockDateUtil
        )
    }

    @Test
    fun testBuildObservable() {
        deleteExpiredTweetsInteractor.buildObservable().blockingFirst()
        Mockito.verify(mockTweetDao).deleteSubsetTweets(Mockito.anyLong(), Mockito.anyLong())
        Mockito.verify(mockDateUtil).getLinuxTimeNow()
        Mockito.verify(mockSharedPrefsHelper).getLong(Mockito.anyString())
    }
}