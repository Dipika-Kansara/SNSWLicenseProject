package Routes

import Models.Customer.LogHours
import com.mongodb.client.MongoDatabase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.litote.kmongo.*

fun Route.logHoursRoute (db: MongoDatabase) {

    val logHoursCollection = db.getCollection<LogHours>("loghours")

    route("/loghours") {
        get {
            val entity = logHoursCollection.find().toList()
            call.respond(entity)
        }

        post {

                val data = call.receive<LogHours>()
                val logHours= LogHours(data.startSession,data.endSession,data.instructor)
                logHoursCollection.insertOne(logHours)
                call.respond(HttpStatusCode.Created,logHours)
        }

        delete ("/{id}") {
            val id = call.parameters["id"].toString()
            val filter = "{_id:ObjectId('$id')}"
            val entity = logHoursCollection.deleteOne(filter)
            if (entity.deletedCount.toInt() == 1) {
                call.respond(HttpStatusCode.NoContent)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }
    }

}

