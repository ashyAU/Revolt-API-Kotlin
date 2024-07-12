package cloud.tyty.account

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class API {
    private val url = "https://api.revolt.chat"

    private val client = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    suspend fun createAccount(
        email: String, password: String, invite: String? = null, captcha: String? = null
    ) {
        val request = client.post("${url}/auth/account/create") {
            contentType(ContentType.Application.Json)
            setBody(
                Json.encodeToString(
                    Requests.CreateAccount(
                        email = email, password = password, invite = invite, captcha = captcha
                    )
                )
            )
        }
        if (request.status.value == 204) {
            return
        } else {
            println("${request.status.description} ${request.status.value}")
        }
    }

    // need to get captcha working
    suspend fun resendVerification(
        email: String, captcha: String? = null
    ) {

        val request = client.post("${url}/auth/account/reverify") {
            contentType(ContentType.Application.Json)
            setBody(
                Json.encodeToString(
                    Requests.ResendVerification(
                        email = email, captcha = captcha
                    )
                )
            )
        }
        if (request.status.value == 204) {
            return
        } else {
            println("${request.status.description} ${request.status.value}")
        }
    }

    // todo awaiting clarification on how the token is obtained
    suspend fun confirmAccountDeletion(token: String) {
    }

    // Requests to have the account deleted
    suspend fun deleteAccount(
        token: String
    ) {
        val request = client.post("${url}/auth/account/delete") {
            header(
                key = "X-Session-Token", value = token
            )
        }
        if (request.status.value == 204) {
            return
        } else {
            println("${request.status.description}, ${request.status.value}")
        }
    }

    suspend fun fetchAccount(
        token: String
    ): Response.FetchAccount? {
        val request = client.get("${url}/auth/account") {
            header(
                key = "X-Session-Token", value = token
            )
        }
        if (request.status.value == 200) {
            return request.body()
        } else {
            println("${request.status.description}, ${request.status.value}")
        }
        return null
    }

    suspend fun disableAccount(token: String) {
        val request = client.post("${url}/auth/account/disable") {
            header(
                key = "X-Session-Token", value = token
            )
        }
        if (request.status.value == 204) {
            return
        } else {
            println("${request.status.description}, ${request.status.value}")
        }
    }

    suspend fun changePassword(
        password: String, currentPassword: String, token: String
    ) {
        val request = client.patch("${url}/auth/account/change/password") {
            contentType(ContentType.Application.Json)
            header(
                key = "X-Session-Token", value = token
            )
            setBody(
                Json.encodeToString(
                    Requests.ChangePassword(
                        currentPassword = currentPassword, password = password
                    )
                )
            )
        }
        if (request.status.value == 204) {
            return
        } else {
            println("${request.status.description}, ${request.status.value}")
        }
    }

    suspend fun changeEmail(
        email: String, currentPassword: String, token: String
    ) {
        val request = client.patch("${url}/auth/account/change/email") {
            contentType(ContentType.Application.Json)
            header(
                key = "X-Session-Token", value = token
            )
            setBody(
                Json.encodeToString(
                    Requests.ChangeEmail(
                        email = email, currentPassword = currentPassword
                    )
                )
            )
        }
        if (request.status.value == 204) {
            return
        } else {
            println("${request.status.description}, ${request.status.value}")
        }
    }

    suspend fun verifyEmail(
        code: String
    ): Response.VerifyEmail? {
        val request = client.post("${url}/auth/account/verify/$code")

        if (request.status.value == 200) {
            return request.body()
        } else {
            println("${request.status.description}, ${request.status.value}")
        }
        return null
    }

    suspend fun sendPasswordReset(
        email: String, captcha: String?
    ) {
        val request = client.post("${url}/auth/account/reset_password") {
            contentType(ContentType.Application.Json)
            setBody(
                Json.encodeToString(
                    Requests.SendPasswordReset(
                        email = email, captcha = captcha
                    )
                )
            )
        }
        if (request.status.value == 204) {
            return
        } else {
            println("${request.status.description}, ${request.status.value}")
        }
    }

    suspend fun passwordReset(
        token: String, password: String, removeSessions: Boolean = false
    ) {
        val request = client.patch("${url}/auth/account/reset_password") {
            contentType(ContentType.Application.Json)
            setBody(
                Json.encodeToString(
                    passwordReset(
                        token = token, password = password, removeSessions = removeSessions
                    )
                )
            )
        }
        if (request.status.value == 204) {
            return
        } else {
            println("${request.status.description}, ${request.status.value}")
        }
    }
}