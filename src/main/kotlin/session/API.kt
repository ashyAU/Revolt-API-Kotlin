package session

import cloud.tyty.session.Response
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

    suspend fun login(
        email: String, password: String, friendlyName: String?
    ): Requests.Login? {
        val response = client.post(
            "${url}/auth/session/login"
        ) {
            contentType(ContentType.Application.Json)
            setBody(
                Json.encodeToString(
                    Requests.Login(
                        email = email, password = password, friendlyName = friendlyName
                    )
                )
            )
        }

        if (response.status.value == 200) {
            return response.body()
        } else {
            println("${response.status.description}, ${response.status.value}")
        }
        return null
    }

    suspend fun logout(
        token: String
    ) {
        val response = client.post(
            "${url}/auth/session/logout"
        ) {
            header(
                key = "X-Session-Token", value = token
            )
        }
        if (response.status.value == 204) {
            return
        } else {
            println("${response.status.description}, ${response.status.value}")
        }
    }

    suspend fun fetchSessions(token: String): List<Response.Sessions>? {
        val response = client.get(
            "${url}/auth/session/all"
        ) {
            header(
                key = "X-Session-Token", value = token
            )
        }
        // can't do sessions.status.OK. Weird import issue with Ktor :/
        if (response.status.value == 200) {
            return response.body<List<Response.Sessions>>()
        } else {
            println("${response.status.description}, ${response.status.value}")
        }
        return null
    }

    suspend fun deleteSessions(token: String) {
        println("Log out of all other sessions?")

        val response = client.delete(
            "${url}/auth/session/"
        ) {
            header(
                key = "X-Session-Token", value = token
            )
        }
        if (response.status.value == 204) {
            return
        } else {
            println("${response.status.description}, ${response.status.value}")
        }
    }

    suspend fun revokeSession(friendlyName: String?, token: String, id: String) {
        val response = client.delete(
            "${url}/auth/session/$id"
        ) {
            header(
                key = "X-Session-Token", value = token
            )
        }
        if (response.status.value == 204) {
            return
        } else {
            println("${response.status.description}, ${response.status.value}")
        }
    }

    suspend fun editSession(id: String, token: String, newFriendlyName: String?) {
        val response = client.patch("${url}/auth/session/$id") {
            contentType(ContentType.Application.Json)
            header(
                key = "X-Session-Token", value = token
            )
            setBody(
                Json.encodeToString(Requests.FriendlyName(newFriendlyName))
            )
        }
        if (response.status.value == 200) {
            return
        } else {
            println("${response.status.description}, ${response.status.value}")
        }
    }
}