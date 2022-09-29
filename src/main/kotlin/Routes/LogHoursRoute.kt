package Routes

import Models.Customer.LogHours
import com.mongodb.client.MongoDatabase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.litote.kmongo.getCollection

fun Route.logHoursRoute (db: MongoDatabase) {

    val logHoursCollection = db.getCollection<LogHours>("loghours")

    route("/") {

        post("/loghours") {

                val data = call.receive<LogHours>()
                val logHours= LogHours(data.startSession,data.endSession,data.instructor)
                logHoursCollection.insertOne(logHours)
                call.respond(HttpStatusCode.Created,logHours)
        }
    }

}

