import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class Session {
    sealed class Requests {
        @Serializable
        data class Login(val email: String, val password: String)
    }
    sealed class Response {
        @Serializable
        data class Login(
            @SerialName("result") val result: String,
            @SerialName("_id") val id: String,
            @SerialName("user_id") val userId: String,
            @SerialName("token") val token: String,
            @SerialName("name") val name: String
        )
    }
}