package elegant.access.apidemo.http.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class NoKeyResult(
    @SerializedName("State") var state: Int = -1,
    @SerializedName("Msg") var msg: String? = null
) : Serializable