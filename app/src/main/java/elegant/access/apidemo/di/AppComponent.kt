package elegant.access.apidemo.di

import dagger.Component
import elegant.access.apidemo.SearchUserApplication
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])

interface AppComponent {

    fun inject(target: SearchUserApplication)


}