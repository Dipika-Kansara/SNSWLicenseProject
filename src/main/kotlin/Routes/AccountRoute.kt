import Models.User
import com.mongodb.client.MongoDatabase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.litote.kmongo.*
import org.mindrot.jbcrypt.BCrypt


fun Route.accountRoute (db:MongoDatabase) {

    val usersCollection = db.getCollection<User>("Users")

    route("/account") {

        post("/register") {
            val data = call.receive<User>()
            val hashed = BCrypt.hashpw(data.password,BCrypt.gensalt())
            val user = User(data.username, password = hashed, roles = listOf("customers"))
            usersCollection.insertOne(user)
            call.respond(HttpStatusCode.Created)

        }




    }

}