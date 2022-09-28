package Routes

import Models.CSR.CSRUser
import Models.Customer.Login
import Models.Customer.User
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.mongodb.client.MongoDatabase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.litote.kmongo.findOne
import org.litote.kmongo.getCollection
import org.mindrot.jbcrypt.BCrypt
import java.util.*

fun Route.CSRAccountRoute (db: MongoDatabase) {

    val CSRSCollection = db.getCollection<CSRUser>("CSRS")

    route("/account") {

        post("/register/csr") {
            val data = call.receive<CSRUser>()
            val hashed = BCrypt.hashpw(data.password, BCrypt.gensalt())
            val CSRuser = CSRUser(data.firstName, data.lastName, data.email, data.staffId, password = hashed,roles = listOf("CSRS"))
            CSRSCollection.insertOne(CSRuser)
            call.respond(HttpStatusCode.Created)

        }

        post("/login/csr") {
            val data = call.receive<Login>()

            val filter = "{email:/^${data.email}$/i}"
            val user = CSRSCollection.findOne(filter)

            if (user == null) {
                return@post call.respond(HttpStatusCode.BadRequest)
            }
            val valid = BCrypt.checkpw(data.password, user.password)
            if (!valid) {
                return@post call.respond(HttpStatusCode.BadRequest)
            }
            val expiry = Date(System.currentTimeMillis() + 86400000)
            val token = JWT.create()
                .withAudience("http://localhost:8080")
                .withIssuer("http://localhost:8080")
                .withClaim("email", user?.email)
                .withClaim("roles", user?.roles)
                .withExpiresAt(expiry)
                .sign(Algorithm.HMAC256("secret"))

            return@post call.respond(token)


        }

    }
}