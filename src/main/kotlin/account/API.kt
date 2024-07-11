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
import javax.swing.text.AbstractDocument.Content

/*
POST/auth/account/create
POST/auth/account/reverify
PUT/auth/account/delete
POST/auth/account/delete
GET/auth/account/
POST/auth/account/disable
PATCH/auth/account/change/password
PATCH/auth/account/change/email
POST/auth/account/verify/{code}
POST/auth/account/reset_password
PATCH/auth/account/reset_password
*/

class API {
    private val url = "https://api.revolt.chat"

    private val client = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    // Needs to get recaptcha enabled, will need to be done on the client
    suspend fun createAccount(
        email: String, password: String, invite: String? = null, captcha: String? = null
    ) {
        val createData = Requests.CreateAccount(
            email = email, password = password, invite = invite, captcha = captcha
        )

        val request = client.post("${url}/auth/account/create") {
            contentType(ContentType.Application.Json)
            setBody(Json.encodeToString(createData))
        }
        if (request.status.value == 204) {
            println("Your account has been successfully created, please check your emails to finish the creation process")
        } else {
            println("${request.status.description} ${request.status.value}")
        }
    }

    // need to get captcha working
    suspend fun resendVerification(
        email: String, captcha: String? = null
    ) {
        val resendData = Requests.ResendVerification(
            email = email, captcha = captcha
        )

        val request = client.post("${url}/auth/account/reverify") {
            contentType(ContentType.Application.Json)
            setBody(Json.encodeToString(resendData))
        }
        if (request.status.value == 204) {
            println("A new verification email has been sent to your account, please allow up to 5 minutes for it to arrive")
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
            println("please check your email for a confirmation to delete your account")
        } else {
            println("${request.status.description}, ${request.status.value}")
        }
    }

    suspend fun fetchAccount(
        token: String
    ) {
        val request = client.get("${url}/auth/account") {
            header(
                key = "X-Session-Token", value = token
            )
        }
        if (request.status.value == 200) {
            val body = request.body<Response.FetchAccount>()
            println("${body.id}, ${body.email}")
        } else {
            println("${request.status.description}, ${request.status.value}")
        }
    }

    suspend fun disableAccount(token: String) {
        val request = client.post("${url}/auth/account/disable") {
            header(
                key = "X-Session-Token", value = token
            )
        }
        if (request.status.value == 204) {
            println("Your account is disabled")
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
            println("Password changed successfully.")
        } else {
            println("${request.status.description}, ${request.status.value}")
        }
    }
}