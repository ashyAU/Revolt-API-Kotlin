package cloud.tyty.account

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

sealed class Response {
    @Serializable
    data class FetchAccount(
        @SerialName("_id") val id: String,
        @SerialName("email") val email: String
    )
}