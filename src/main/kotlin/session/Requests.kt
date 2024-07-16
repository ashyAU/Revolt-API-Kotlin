package session

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

sealed class Requests {
    @Serializable
    data class Login(
        @SerialName("email") val email: String,
        @SerialName("password") val password: String,
        @SerialName("friendly_name") val friendlyName: String?
    )
    @Serializable
    data class FriendlyName(
        @SerialName("friendly_name") val friendlyName: String?
    )


}