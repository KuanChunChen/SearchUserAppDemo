package elegant.access.apidemo.base

import elegant.access.apidemo.BuildConfig

object BaseConstants {

    object Http {
        val isDebugMode = BuildConfig.DEBUG
        var HTTP_GITHUB_SERVER = "https://api.github.com"

    }
}