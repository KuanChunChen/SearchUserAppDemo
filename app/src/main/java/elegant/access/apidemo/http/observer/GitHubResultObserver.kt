package elegant.access.apidemo.http.observer

import com.google.gson.Gson
import elegant.access.apidemo.http.model.GitHubFailedResult

abstract class GitHubResultObserver<T> : BaseObserver<T>() {


    override val tagName: String get() = this.javaClass.name


    override fun onNext(t: T) {

        if (t == null) {

            onFailure(
                RetrofitResultException(
                    RetrofitResultException.PARSE_ERROR,
                    "Return null result from server"
                )
            )
            return
        }

        val onSearchUserFailed = Gson().fromJson(Gson().toJson(t), GitHubFailedResult::class.java)
        if (onSearchUserFailed?.message != null) {
            onFailure(
                RetrofitResultException(
                    RetrofitResultException.PARSE_ERROR,
                    onSearchUserFailed.message
                )
            )
            return
        }



        try {
            onSuccess(t)

        } catch (e: Exception) {
            e.printStackTrace()
        }


    }


    abstract fun onSuccess(data: T)


}