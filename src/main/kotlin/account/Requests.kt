package cloud.tyty.account

import kotlinx.serialization.Serializable

sealed class Requests {
    @Serializable
    data class CreateAccount(
        val email: String,
        val password: String,
        val invite: String? = null,
        val captcha: String? = null
    )
    @Serializable
    data class ResendVerification(
        val email: String,
        val captcha: String? = null
    )
}