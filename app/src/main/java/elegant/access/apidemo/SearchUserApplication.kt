package elegant.access.apidemo

import android.content.Context
import elegant.access.apidemo.base.BaseApplication
import elegant.access.apidemo.di.AppComponent
import elegant.access.apidemo.di.AppModule
import elegant.access.apidemo.di.DaggerAppComponent
import javax.inject.Inject

class SearchUserApplication: BaseApplication() {


    val appComponent: AppComponent by lazy {
        initDagger()
    }

    @Inject
    lateinit var mContext: Context

    override fun onCreate() {

        super.onCreate()
        appComponent.inject(this)

    }

    private fun initDagger(): AppComponent =
        DaggerAppComponent.builder().appModule(AppModule(this)).build()

}