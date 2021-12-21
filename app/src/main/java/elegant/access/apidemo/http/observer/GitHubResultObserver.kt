package elegant.access.apidemo.http.observer

import com.google.gson.Gson
import elegant.access.apidemo.http.model.GitHubFailedResult

abstract class GitHubResultObserver<T> : BaseObserver<T>() {


    /** 該observer 的 tag name*/
    override val tagName: String get() = this.javaClass.name

    /** 當 操作符 observable 有值返回後會進入onNext 週期
     * 如 ：retrofit 連線返回後
     * 或是使用其他操作符如flapmap等 去跑自定義邏輯後 返回的observable*/

    override fun onNext(t: T) {

        /** 1. 當observable 返回的資料整個為null 進 onFailure */
        if (t == null) {

            onFailure(
                RetrofitResultException(
                    RetrofitResultException.PARSE_ERROR,
                    "Return null result from server"
                )
            )
            return
        }

        /** 2. 解析 隨資料返回的Result Key 若內容object為null 進 onFailure */
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

        /** 3. 解析 result內的 state code 如果是 定義中的SUCCESS的值 進 onSuccess
         * 錯誤則進 onFailure */


        try {
            onSuccess(t)

        } catch (e: Exception) {
            e.printStackTrace()
        }


    }


    /** 抽象類 onSuccess 用於 ： 當 onNext 結果成功後，onSuccess返回訂閱observer的地方*/
    abstract fun onSuccess(data: T)


}