package elegant.access.apidemo.search

import elegant.access.apidemo.search.model.SearchUserResult
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface SearchUserAPI {

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/search/users")
    fun searchUser(
        @Query("q") keywords: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Observable<SearchUserResult>
}