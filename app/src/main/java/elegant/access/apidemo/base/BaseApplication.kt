package elegant.access.apidemo.base

import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication

open class BaseApplication  : MultiDexApplication() {


    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)

    }

}
