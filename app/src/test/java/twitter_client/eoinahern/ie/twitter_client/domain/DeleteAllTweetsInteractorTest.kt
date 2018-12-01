package twitter_client.eoinahern.ie.twitter_client.domain

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import twitter_client.eoinahern.ie.twitter_client.data.database.TweetDao

class DeleteAllTweetsInteractorTest {

    @Mock
    lateinit var tweetsDao: TweetDao

    private lateinit var deleteAllTweetsInteractor: DeleteAllTweetsInteractor

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        deleteAllTweetsInteractor = DeleteAllTweetsInteractor(tweetsDao)

    }

    @Test
    fun testbuildObservable() {
        deleteAllTweetsInteractor.buildObservable().blockingFirst()
        Mockito.verify(tweetsDao).deleteAll()
    }
}