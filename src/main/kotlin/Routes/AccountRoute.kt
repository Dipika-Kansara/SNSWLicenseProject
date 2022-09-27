import Models.User
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.mongodb.client.MongoDatabase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.litote.kmongo.*
import org.mindrot.jbcrypt.BCrypt
import java.util.*


fun Route.accountRoute (db:MongoDatabase) {

    val usersCollection = db.getCollection<User>("Users")

    route("/account") {

        post("/register") {
            val data = call.receive<User>()
            val hashed = BCrypt.hashpw(data.password, BCrypt.gensalt())
            val user = User(data.firstName, data.lastName, data.dob,  data.email, password = hashed, data.mobile,roles = listOf("customers"))
            usersCollection.insertOne(user)
            call.respond(HttpStatusCode.Created)

        }

        post("/login") {
            val data = call.receive<User>()

            val filter = "{username:/^${data.email}$/i}"
            val user = usersCollection.findOne(filter)

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
                .withClaim("username", user?.email)
                .withClaim("roles", user?.roles)
                .withExpiresAt(expiry)
                .sign(Algorithm.HMAC256("secret"))

            return@post call.respond(token)


        }

    }
}