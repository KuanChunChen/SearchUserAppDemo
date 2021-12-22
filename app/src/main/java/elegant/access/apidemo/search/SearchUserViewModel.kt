package elegant.access.apidemo.search

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import elegant.access.apidemo.http.observer.GitHubResultObserver
import elegant.access.apidemo.search.adapter.SearchUserDataSourceFactory
import elegant.access.apidemo.search.model.SearchUserAdapterData
import elegant.access.apidemo.search.model.SearchUserResult
import java.util.concurrent.Executors
import javax.inject.Inject


class SearchUserViewModel @Inject constructor(private val searchUserRepo: SearchUserRepo) {


    private val sourceFactory by lazy {
        SearchUserDataSourceFactory(searchUserRepo)
    }


    var pagingDataItems: LiveData<PagedList<SearchUserAdapterData>>

    init {
        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(20)
            .build()
        val executor = Executors.newFixedThreadPool(5);

        pagingDataItems = LivePagedListBuilder(sourceFactory, pagedListConfig)
                .setFetchExecutor(executor)
                .build()

    }

    fun queryUser(
        keywords: String,
        page: String,
        perPage: String,
        result: GitHubResultObserver<SearchUserResult>
    ) {

//        searchUserRepo.searchUser(keywords,page, perPage)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(result)
    }

}