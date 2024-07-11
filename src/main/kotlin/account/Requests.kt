package cloud.tyty.account

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

sealed class Requests {
    @Serializable
    data class CreateAccount(
        @SerialName("email") val email: String,
        @SerialName("password") val password: String,
        @SerialName("invite") val invite: String? = null,
        @SerialName("captcha") val captcha: String? = null
    )

    @Serializable
    data class ResendVerification(
        @SerialName("email") val email: String,
        @SerialName("captcha") val captcha: String? = null
    )

    @Serializable
    data class ChangePassword(
        @SerialName("password") val password: String,
        @SerialName("current_password") val currentPassword: String
    )

    @Serializable
    data class ChangeEmail(
        @SerialName("email") val email: String,
        @SerialName("current_password") val currentPassword: String
    )

}