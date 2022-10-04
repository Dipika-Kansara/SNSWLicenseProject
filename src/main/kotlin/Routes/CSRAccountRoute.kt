package Routes

import Models.Customer.User
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.mongodb.client.MongoDatabase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.litote.kmongo.findOne
import org.litote.kmongo.getCollection
import org.mindrot.jbcrypt.BCrypt
import java.util.*

fun Route.CSRAccountRoute (db: MongoDatabase) {
    val usersCollection = db.getCollection<User>("Users")

    route("/account") {

        get("/search/{email}") {

            val email = call.parameters["email"].toString()
            val emailFilter = "{email:/^${email}$/i}"
            val user = usersCollection.findOne(emailFilter)
            if (user == null) {
                return@get call.respond(HttpStatusCode.NotFound)
            }
            call.respond(user)


        }
//            post("/register/csr") {
//                val data = call.receive<User>()
//                val hashed = BCrypt.hashpw(data.password, BCrypt.gensalt())
//                val CSRuser = User(
//                    data.firstName,
//                    data.lastName,
//                    data.dob,
//                    data.address,
//                    data.email,
//                    password = hashed,
//                    data.mobile,
//                    roles = listOf("CSRS")
//                )
//                usersCollection.insertOne(CSRuser)
//                call.respond(HttpStatusCode.Created)
//
//            }
//
//            post("/login/csr") {
//                val data = call.receive<Login>()
//
//                val filter = "{email:/^${data.email}$/i}"
//                val user = usersCollection.findOne(filter)
//
//                if (user == null) {
//                    return@post call.respond(HttpStatusCode.BadRequest)
//                }
//                val valid = BCrypt.checkpw(data.password, user.password)
//                if (!valid) {
//                    return@post call.respond(HttpStatusCode.BadRequest)
//                }
//                val expiry = Date(System.currentTimeMillis() + 86400000)
//                val token = JWT.create()
//                    .withAudience("http://localhost:8080")
//                    .withIssuer("http://localhost:8080")
//                    .withClaim("email", user?.email)
//                    .withClaim("roles", user?.roles)
//                    .withExpiresAt(expiry)
//                    .sign(Algorithm.HMAC256("secret"))
//
//                return@post call.respond(token)
//
//
//            }

        }
    }
