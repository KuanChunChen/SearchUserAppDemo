package elegant.access.apidemo.search.adapter

import androidx.paging.PageKeyedDataSource
import elegant.access.apidemo.http.observer.GitHubResultObserver
import elegant.access.apidemo.search.SearchUserRepo
import elegant.access.apidemo.search.model.SearchUserAdapterData
import elegant.access.apidemo.search.model.SearchUserResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class SearchUserDataSource(
    private val searchUserRepo: SearchUserRepo
) : PageKeyedDataSource<Int, SearchUserAdapterData>() {


    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, SearchUserAdapterData>
    ) {

        val initPage = 1
        val initPerPage = 30


        searchUserRepo.searchUser(initPage, initPerPage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : GitHubResultObserver<SearchUserResult>() {
                override fun onSuccess(data: SearchUserResult) {
                    val nextKey: Int? = if (data.total_count == 30) null else (initPage + 1)

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

                }

            })
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, SearchUserAdapterData>
    ) {

        searchUserRepo.searchUser(params.key, params.requestedLoadSize)

            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : GitHubResultObserver<SearchUserResult>() {
                override fun onSuccess(data: SearchUserResult) {
                    val nextKey: Int? = if (params.key == 43563) null else (params.key + 1)

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

            })

    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, SearchUserAdapterData>
    ) {
    }
}