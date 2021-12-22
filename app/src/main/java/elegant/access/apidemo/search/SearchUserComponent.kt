package elegant.access.apidemo.search

import dagger.BindsInstance
import dagger.Component
import elegant.access.apidemo.SearchUserFragment
import elegant.access.apidemo.di.ActivityScope
import elegant.access.apidemo.di.AppComponent

//@ActivityScope
@Component(modules = [SearchUserModule::class],
    dependencies = [AppComponent::class])
interface SearchUserComponent {

    fun inject(target: SearchUserFragment)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun fragment(fragment: SearchUserFragment): Builder

        fun appComponent(component : AppComponent) : Builder

        fun build(): SearchUserComponent

    }


}