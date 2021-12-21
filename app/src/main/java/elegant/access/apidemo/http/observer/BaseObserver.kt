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

    /** 繼承此base類的，需override tagName ，用於顯示本class中的log*/
    protected abstract val tagName: String

    /** 該observer 訂閱成功一開始進 onSubscribe*/
    override fun onSubscribe(d: Disposable) {}
    /** 當observer 整個完成*/
    override fun onComplete() {}

    /** 當observer 中途遇到excption 拋錯時*/
    override fun onError(e: Throwable) {
        e.printStackTrace()

        val resultException: RetrofitResultException

        /**目前已知 non-200 error , exception 等
         * 未來可再擴充*/
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

    /**目前未知的錯誤 處理*/
    private fun returnUnKnow(e: Throwable) {
        Log.d(tagName, e.toString())
        val resultException = RetrofitResultException(RetrofitResultException.UNKNOWN, "UNKNOWN ERROR", e)
        onFailure(resultException)
    }

    /**當observable的過程中 出現錯誤 , exception , 或是資料解析過程不符合自定義要求 ，則會進入 onFailure
     * 在使用到此類的地方可以選擇性 override onFailure
     * 預設未override 印出exception log
     * */

    open fun onFailure(e: RetrofitResultException) {
        e.printStackTrace()
    }

    /**自定義格式的exception **/
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

        /**自定義android 端 error code  **/

        companion object {
            const val UNKNOWN = 1000
            const val PARSE_ERROR = 1001

        }
    }

}