package Routes

import Models.CSR.License
import Models.Customer.LogHours
import com.mongodb.client.MongoDatabase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.litote.kmongo.getCollection

fun Route.licenseRoute (db: MongoDatabase) {

    val licenseCollection = db.getCollection<License>("license")

    route("/") {

        post("/issuelicense") {

            val data = call.receive<License>()
            val license = License(data.licenseClass, data.issueDate, data.expiryDate, data.issuingCSR, data.number)
            licenseCollection.insertOne(license)
            call.respond(HttpStatusCode.Created, license)

        }
    }
}