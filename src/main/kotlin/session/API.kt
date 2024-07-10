package session

import cloud.tyty.session.Response
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.*
import kotlin.system.exitProcess

class API {
    private val url = "https://api.revolt.chat"
    private var token: String = ""

    private val client = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    // intention to store the token and other data in a database
    suspend fun login() {
        println("enter your email:")
        val email = readln()
        println("enter your password:")
        val password = readln()

        val loginData = Requests.Login(email = email, password = password)

        val response = client.post(
            "${url}/auth/session/login"
        ) {
            contentType(ContentType.Application.Json)
            setBody(Json.encodeToString(loginData))
        }.body<Response.Login>()

        if (response.result == "Success") {
            println("you have successfully logged in ${response.userId}")
            token = response.token
        } else {
            println("you have failed to login: Response ${response.result}")
            login()
        }

    }
    suspend fun logout() {
        println("would you like to logout? y/n")
        val response = readln()
        val logout = client.post(
            "${url}/auth/session/logout"
        ) {
            header(
                key = "X-Session-Token",
                value = token
            )
        }
        if (response.lowercase(Locale.getDefault()) == "y") {
            if (logout.status.isSuccess()) {
                println("user logged out successfully: Status code ${logout.status}")
                exitProcess(-1)
            } else {
                println("An exception has occurred: Status code ${logout.status}")
            }
        } else {
            logout()
        }
    }
    suspend fun fetchSessions()
    {
        println("would you like a list of currently logged in sessions?")
        val sessions = client.get(
            "${url}/auth/session/all"
        ) {
            header(
                key = "X-Session-Token",
                value = token
            )
        }
        // todo
        // can't do sessions.status.OK. Weird import issue with Ktor :/
            if (sessions.status.value == 200)
            {
                val sessionsList = sessions.body<List<Response.Sessions>>()
                sessionsList.forEach {
                    println(it.name)
                }

            }
        else{
            println()
            }
    }

}