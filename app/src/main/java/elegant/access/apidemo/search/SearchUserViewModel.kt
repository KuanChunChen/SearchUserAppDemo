package elegant.access.apidemo.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import elegant.access.apidemo.http.observer.GitHubResultObserver
import elegant.access.apidemo.search.model.SearchUserResult
import elegant.access.apidemo.search.model.UserListData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SearchUserViewModel @Inject constructor(private val searchUserRepo: SearchUserRepo) {

    private val _searchUserData = MutableLiveData<List<UserListData>>()
    val searchUserData: LiveData<List<UserListData>>
        get() = _searchUserData


    fun queryUser(
        keywords: String,
        page: String,
        perPage: String,
        result: GitHubResultObserver<SearchUserResult>
    ) {

        searchUserRepo.searchUser(keywords,page, perPage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(result)
    }

}