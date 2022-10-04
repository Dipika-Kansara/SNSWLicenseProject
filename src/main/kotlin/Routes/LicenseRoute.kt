package Routes

import License.LearnerLicence
import com.mongodb.client.MongoDatabase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.litote.kmongo.*

fun Route.licenseRoute (db: MongoDatabase) {

    val licenseCollection = db.getCollection<LearnerLicence>("license")

    route("/") {

        post("/issuelicense") {

            val data = call.receive<LearnerLicence>()
            val license = LearnerLicence(data.issued, data.expiry, data.issuedBy, data.licenseNumber, data.logEntries)
            licenseCollection.insertOne(license)
            call.respond(HttpStatusCode.Created, license)

        }
    }
}