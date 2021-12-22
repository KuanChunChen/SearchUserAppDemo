package elegant.access.apidemo.search.adapter

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import elegant.access.apidemo.search.SearchUserRepo
import elegant.access.apidemo.search.model.SearchUserAdapterData

class SearchUserDataSourceFactory(private val searchUserRepo: SearchUserRepo) : DataSource.Factory<Int, SearchUserAdapterData>() {

    private val sourceLiveData = MutableLiveData<SearchUserDataSource>()

    override fun create(): DataSource<Int, SearchUserAdapterData> {

        val source = SearchUserDataSource(searchUserRepo)
        sourceLiveData.postValue(source)
        return source
    }
}