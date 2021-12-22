package elegant.access.apidemo.search.adapter

import android.util.Log
import androidx.paging.PageKeyedDataSource
import elegant.access.apidemo.http.observer.GitHubResultObserver
import elegant.access.apidemo.search.SearchUserRepo
import elegant.access.apidemo.search.model.SearchUserAdapterData
import elegant.access.apidemo.search.model.SearchUserResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class SearchUserDataSource(private val searchUserRepo: SearchUserRepo) : PageKeyedDataSource<Int, SearchUserAdapterData>() {


    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, SearchUserAdapterData>
    ) {
        Log.i("test:loadInitial", "Loading Rang " + params.placeholdersEnabled + " Count " + params.requestedLoadSize);

        searchUserRepo.searchUser("gg",1,8)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :GitHubResultObserver<SearchUserResult>(){
                override fun onSuccess(data: SearchUserResult) {
                    Log.d("test","onSuccess")
                    val listSearchUser = arrayListOf<SearchUserAdapterData>()
                    listSearchUser.add(SearchUserAdapterData((0 until 120).random()))
                    listSearchUser.add(SearchUserAdapterData((0 until 120).random()))
                    listSearchUser.add(SearchUserAdapterData((0 until 120).random()))
                    listSearchUser.add(SearchUserAdapterData((0 until 120).random()))
                    listSearchUser.add(SearchUserAdapterData((0 until 120).random()))
                    listSearchUser.add(SearchUserAdapterData((0 until 120).random()))
                    listSearchUser.add(SearchUserAdapterData((0 until 120).random()))
                    callback.onResult(listSearchUser, 1, 2)
                    Log.d("test","onResult")

                }

            })
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, SearchUserAdapterData>
    ) {

        Log.i("test:loadAfter", "Loading Rang " + params.key + " Count " + params.requestedLoadSize);

        searchUserRepo.searchUser("gg",params.key,params.requestedLoadSize)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :GitHubResultObserver<SearchUserResult>(){
                override fun onSuccess(data: SearchUserResult) {
                    Log.d("test","onSuccess")
                    val nextKey: Int? = if (params.key == 43563) null else (params.key + 1)

                    val listSearchUser = arrayListOf<SearchUserAdapterData>()
                    listSearchUser.add(SearchUserAdapterData((0 until 120).random()))
                    listSearchUser.add(SearchUserAdapterData((0 until 120).random()))
                    listSearchUser.add(SearchUserAdapterData((0 until 120).random()))
                    listSearchUser.add(SearchUserAdapterData((0 until 120).random()))
                    listSearchUser.add(SearchUserAdapterData((0 until 120).random()))
                    listSearchUser.add(SearchUserAdapterData((0 until 120).random()))
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