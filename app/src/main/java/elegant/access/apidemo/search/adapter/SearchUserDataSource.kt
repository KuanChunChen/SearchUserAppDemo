package elegant.access.apidemo.search.adapter

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import elegant.access.apidemo.HideResult
import elegant.access.apidemo.SearchUserUiStats
import elegant.access.apidemo.ShowResult
import elegant.access.apidemo.http.observer.GitHubResultObserver
import elegant.access.apidemo.search.SearchUserRepo
import elegant.access.apidemo.search.model.SearchUserAdapterData
import elegant.access.apidemo.search.model.SearchUserResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class SearchUserDataSource(
    private val searchUserUiStats: MutableLiveData<SearchUserUiStats>,
    private val searchUserRepo: SearchUserRepo
) : PageKeyedDataSource<Int, SearchUserAdapterData>() {


    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, SearchUserAdapterData>
    ) {

        val initPage = 1
        val initPerPage = 30

        if (searchUserRepo.keywords.isEmpty()) {
            searchUserUiStats.postValue(HideResult("Search field is empty."))
            return
        }

        searchUserRepo.searchUser(initPage, initPerPage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : GitHubResultObserver<SearchUserResult>() {
                override fun onSuccess(data: SearchUserResult) {
                    if (data.total_count == 0) {
                        searchUserUiStats.postValue(HideResult("Search result is empty."))
                        return
                    }

                    val nextKey: Int? = if (data.total_count <= initPerPage) null else (initPage + 1)

                    val listSearchUser = arrayListOf<SearchUserAdapterData>()
                    data.items.forEach { userItem ->

                        listSearchUser.add(
                            SearchUserAdapterData(
                                id = userItem.id,
                                userName = userItem.login,
                                avatarUrl = userItem.avatar_url,
                                githubUrl = userItem.url,
                                score = userItem.score,
                            )
                        )
                    }
                    callback.onResult(listSearchUser, initPage, nextKey)
                    searchUserUiStats.postValue(ShowResult)

                }

                override fun onFailure(e: RetrofitResultException) {
                    super.onFailure(e)
                    searchUserUiStats.postValue(HideResult("code:${e.code} , message:${e.msg}."))

                }

            })
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, SearchUserAdapterData>
    ) {

        if (searchUserRepo.keywords.isEmpty()) {
            searchUserUiStats.postValue(HideResult("Search field is empty."))
            return
        }

        searchUserRepo.searchUser(params.key, params.requestedLoadSize)

            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : GitHubResultObserver<SearchUserResult>() {
                override fun onSuccess(data: SearchUserResult) {
                    val nextKey: Int? = if (params.key == data.total_count) null else (params.key + 1)

                    val listSearchUser = arrayListOf<SearchUserAdapterData>()
                    data.items.forEach { userItem ->

                        listSearchUser.add(
                            SearchUserAdapterData(
                                id = userItem.id,
                                userName = userItem.login,
                                avatarUrl = userItem.avatar_url,
                                githubUrl = userItem.url,
                                score = userItem.score,
                            )
                        )
                    }
                    callback.onResult(listSearchUser, nextKey)
                }

                override fun onFailure(e: RetrofitResultException) {
                    super.onFailure(e)
                    searchUserUiStats.postValue(HideResult("code:${e.code} , message:${e.msg}."))

                }

            })

    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, SearchUserAdapterData>
    ) {
    }
}