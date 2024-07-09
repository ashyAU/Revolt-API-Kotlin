package cloud.tyty

import Session
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

suspend fun main() {
    println("enter your email:")
    var email = readln()
    println("enter your password:")
    var password = readln()

    val client = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(Json
            {
                ignoreUnknownKeys = true
            })
        }
    }
    val loginData = Session.Requests.Login(email = email, password = password)
    val response = client.post(
        "https://api.revolt.chat/auth/session/login"
    ) {
        contentType(ContentType.Application.Json)
        setBody(Json.encodeToString(loginData))
    }.body<Session.Response.Login>()
    println(response)
}


