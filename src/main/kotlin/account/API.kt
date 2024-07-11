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
    suspend fun createAccount() {
        println("enter your new account email")
        val email = readln()
        println("enter your new account password")
        val password = readln()
        val createData = Requests.CreateAccount(email = email, password = password)

        val request = client.post("${url}/auth/account/create") {
            contentType(ContentType.Application.Json)
            setBody(Json.encodeToString(createData))
        }
        if (request.status.value == 204) {
            println("Your account has been successfully created, please check your emails to finish the creation process")
        } else {
            println("${ request.status.description } ${request.status.value }")
        }
    }
}