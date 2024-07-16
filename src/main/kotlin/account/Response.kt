package cloud.tyty.account

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

sealed class Response {
    @Serializable
    data class FetchAccount(
        @SerialName("_id") val id: String,
        @SerialName("email") val email: String
    )

    data class VerifyEmail(
        @SerialName("ticket") val ticket: Ticket
    )
    data class Ticket(
        @SerialName("_id") val id: String,
        @SerialName("account_id") val accountId: String,
        @SerialName("token") val token: String,
        @SerialName("validated") val validated: Boolean,
        @SerialName("authorised") val authorised: Boolean,
        @SerialName("last_totp_code") val lastTotpCode: String
    )
}