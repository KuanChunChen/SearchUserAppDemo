package elegant.access.apidemo.search

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import elegant.access.apidemo.search.adapter.SearchUserDataSourceFactory
import elegant.access.apidemo.search.model.SearchUserAdapterData
import java.util.concurrent.Executors
import javax.inject.Inject


class SearchUserViewModel @Inject constructor(private val searchUserRepo: SearchUserRepo) {


    private val sourceFactory by lazy {
        SearchUserDataSourceFactory(searchUserRepo)
    }

    var pagingDataItems: LiveData<PagedList<SearchUserAdapterData>>

    init {
        val executor = Executors.newFixedThreadPool(5)
        val pagedListConfig :PagedList.Config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(30)
            .build()
        pagingDataItems = LivePagedListBuilder(sourceFactory, pagedListConfig)
                .setFetchExecutor(executor)
                .build()

    }

    fun queryUser(keywords: String) {

        searchUserRepo.keywords = keywords
        pagingDataItems.value?.dataSource?.invalidate()

    }

}