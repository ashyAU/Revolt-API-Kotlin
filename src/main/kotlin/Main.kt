package cloud.tyty
import session.API

suspend fun main() {
    val sessionAPI = API()
    sessionAPI.login()
    sessionAPI.fetchSessions()
}


