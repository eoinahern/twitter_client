package twitter_client.eoinahern.ie.twitter_client.testUtils

import io.reactivex.Observer
import org.mockito.stubbing.Answer

class RxAnswer {

    companion object {

        fun <T> onNextCompleted(vararg items: T): Answer<T> {

            return Answer<T> {
                val obs = it.getArgument(0) as? Observer<T>

                for (item in items) {
                    obs?.onNext(item)
                }

                obs?.onComplete()
                null
            }
        }

        fun <T> onError(throwable: Throwable): Answer<T> {
            return Answer<T> {
                val obs = it.getArgument(0) as? Observer<T>
                obs?.onError(throwable)
                null
            }
        }

    }
}