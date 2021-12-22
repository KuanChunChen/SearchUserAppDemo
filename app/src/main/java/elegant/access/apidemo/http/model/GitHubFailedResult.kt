package elegant.access.apidemo.http.model

import java.io.Serializable

data class GitHubFailedResult(
    val documentation_url: String,
    val message: String
) : Serializable