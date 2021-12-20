package elegant.access.apidemo.base

import elegant.access.apidemo.BuildConfig

object BaseConstants {
//    val SERVICE_LOGGER_TAG = "SERVICE_LOGGER"

    object Http {
        // different api domain for dev / pub
        // is debug key or release key to build apk
        val isDebugMode = BuildConfig.DEBUG
        var HTTP_GITHUB_SERVER = "https://api.github.com"

        const val SUCCESS = 0x0000

    }
}