package elegant.access.apidemo.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {


    @Provides
//    @Singleton
    fun providesApplication(): Application = application

//    @Singleton
    @Provides
    fun provideAppContext(): Context = application


}