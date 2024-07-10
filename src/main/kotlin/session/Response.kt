package cloud.tyty.session

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

sealed class Response {
    @Serializable
    data class Login(
        @SerialName("result") val result: String,
        @SerialName("_id") val id: String,
        @SerialName("user_id") val userId: String,
        @SerialName("token") val token: String,
        @SerialName("name") val name: String,
/*
        @SerialName("subscription") val subscription: Subscription
*/
    )
/*    @Serializable
    data class Subscription(
        @SerialName("endpoint") val endpoint: String,
        @SerialName("p256dh") val p256dh: String,
        @SerialName("auth") val auth: String
    )*/

    @Serializable
    data class Sessions(
        @SerialName("_id") val id: String,
        @SerialName("name") val name: String
    )
}