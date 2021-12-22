package elegant.access.apidemo.http.observer

import android.util.Log
import com.google.gson.JsonIOException
import com.google.gson.JsonParseException
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import retrofit2.HttpException
import java.io.IOException
import java.util.*

abstract class BaseObserver <T> : Observer<T> {

    protected abstract val tagName: String

    override fun onSubscribe(d: Disposable) {}
    override fun onComplete() {}

    override fun onError(e: Throwable) {
        e.printStackTrace()

        val resultException: RetrofitResultException

        if (e is HttpException) {
            resultException = RetrofitResultException(e.code(), "non-200 HTTP ERROR", e)
            Log.e(tagName, "Retrofit Http Error")
            onFailure(resultException)
        } else if (e is IOException) {
            resultException = RetrofitResultException(0, "NETWORK ERROR", e)
            Log.e(tagName, "Retrofit Network Error")
            onFailure(resultException)
        } else if (e is UnknownFormatConversionException || e is JsonIOException || e is JsonParseException) {
            resultException = RetrofitResultException(RetrofitResultException.PARSE_ERROR, "PARSE ERROR", e)
            Log.e(tagName, "Retrofit Parse Error")
            onFailure(resultException)
        } else {
            returnUnKnow(e)
        }
    }

    private fun returnUnKnow(e: Throwable) {
        Log.d(tagName, e.toString())
        val resultException = RetrofitResultException(RetrofitResultException.UNKNOWN, "UNKNOWN ERROR", e)
        onFailure(resultException)
    }


    open fun onFailure(e: RetrofitResultException) {
        e.printStackTrace()
    }

    class RetrofitResultException : RuntimeException {
        var code: Int = 0
        var msg: String
        var throwable: Throwable? = null

        constructor(code: Int, msg: String) {
            this.code = code
            this.msg = msg
        }

        constructor(code: Int, msg: String, e: Throwable) {
            this.code = code
            this.msg = msg
            this.throwable = e
        }

        override fun toString(): String {
            return "http code = " + code + "\n cause = " + msg + "\n" + super.toString()
        }


        companion object {
            const val UNKNOWN = 1000
            const val PARSE_ERROR = 1001

        }
    }

}