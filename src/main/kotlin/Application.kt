import io.ktor.http.*

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.litote.kmongo.KMongo

val client = KMongo.createClient()
val db = client.getDatabase("SNSWDriverLicenses")

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.init() {
    install(ContentNegotiation) {
        json()
    }

    install(CORS) {
        allowHost("*")
        allowMethod(HttpMethod.Post)
        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.Authorization)
    }

    install(Authentication){
      jwt{
          realm = "license.com.au"
          verifier(
              JWT
              .require(Algorithm.HMAC256("secret"))
              .withAudience("http://localhost:8080")
              .withIssuer("http://localhost:8080")
              .build()
          )
          validate{
                  token -> JWTPrincipal(token.payload)
          }
          challenge{
                  defaultScheme, realm ->  call.respond(HttpStatusCode.Unauthorized,"Invalid Token")
          }
      }
  }

    routing {
        accountRoute(db)
        }


    }

