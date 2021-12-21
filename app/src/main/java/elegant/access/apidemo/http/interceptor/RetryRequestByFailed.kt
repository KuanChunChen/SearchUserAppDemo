package elegant.access.apidemo.http.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.io.InterruptedIOException

class RetryRequestByFailed internal constructor(builder: Builder) : Interceptor {
    private var executionCount: Int = 0
    private val retryInterval: Long

    init {
        this.executionCount = builder.retryCount
        this.retryInterval = builder.retryInterval
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response? {
        val request = chain.request()
        var response = doRequest(chain, request)
        var retryNum = 0
        while ((response.first == null || !response.first!!.isSuccessful) && retryNum <= executionCount) {
            //            LogTool.d("intercept Request is not successful - %d",retryNum);
            val nextInterval = retryInterval
            try {
                //                LogTool.d("Wait for %s", nextInterval);
                Thread.sleep(nextInterval)
            } catch (e: InterruptedException) {
                Thread.currentThread().interrupt()
                throw InterruptedIOException()
            }

            retryNum++
            // retry the request
            response = doRequest(chain, request)
        }

        if (retryNum > executionCount && response.second != null) {
            throw response.second!!
        }
        return response.first
    }

    private fun doRequest(chain: Interceptor.Chain, request: Request): Pair<Response?, Exception?> {
        var response: Response? = null
        try {
            response = chain.proceed(request)
        } catch (e: Exception) {
            e.printStackTrace()
            return (response to e)
        }

        return (response to null)
    }

    class Builder {
        internal var retryCount: Int = 0
        internal var retryInterval: Long = 0

        init {
            retryCount = 0
            retryInterval = 1000
        }

        fun setRetryCount(retryCount: Int): Builder {
            this.retryCount = retryCount
            return this
        }

        fun setRetryInterval(retryInterval: Long): Builder {
            this.retryInterval = retryInterval
            return this
        }

        fun build(): RetryRequestByFailed {
            return RetryRequestByFailed(this)
        }
    }

}