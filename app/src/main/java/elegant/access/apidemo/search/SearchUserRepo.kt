package elegant.access.apidemo.search

import elegant.access.apidemo.search.model.SearchUserResult
import io.reactivex.Observable
import javax.inject.Inject

class SearchUserRepo @Inject constructor(private val api : SearchUserAPI) {


    fun searchUser(keywords: String, page: String, perPage: String): Observable<SearchUserResult> =
        api.searchUser(keywords, page, perPage)



}