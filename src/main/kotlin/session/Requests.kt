package session

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

sealed class Requests {
    @Serializable
    data class Login(
        val email: String,
        val password: String,
        @SerialName("friendly_name") val friendlyName: String? = "default"
    )
    data class FriendlyName(
        @SerialName("friendly_name") val friendlyName: String?
    )
}