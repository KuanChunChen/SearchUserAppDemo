package elegant.access.apidemo.search

import dagger.Module
import dagger.Provides
import elegant.access.apidemo.http.HttpModule
import retrofit2.Retrofit


@Module(includes = [HttpModule::class])

class SearchUserModule {

    @Provides
    fun api(retrofit : Retrofit): SearchUserAPI = retrofit.create(SearchUserAPI::class.java)

    @Provides
    fun repository(api: SearchUserAPI) = SearchUserRepo(api)

    @Provides
    fun viewModel(repo: SearchUserRepo) = SearchUserViewModel(repo)

}