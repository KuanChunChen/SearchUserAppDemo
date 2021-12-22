package elegant.access.apidemo.search

import elegant.access.apidemo.search.model.SearchUserResult
import io.reactivex.Observable
import javax.inject.Inject

class SearchUserRepo @Inject constructor(private val api : SearchUserAPI) {

    var keywords: String = ""

    fun searchUser(page: Int, perPage: Int): Observable<SearchUserResult> =
        api.searchUser(keywords, page, perPage).apply {

        }



}