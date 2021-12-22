package elegant.access.apidemo.http

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class HttpModule {


    @Provides
    fun retrofit(): Retrofit = RetrofitClient.http()


}