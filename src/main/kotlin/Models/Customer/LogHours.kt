package Models.Customer

import ObjectIdAsStringSerializer
import kotlinx.serialization.Serializable
import org.litote.kmongo.Id
import org.litote.kmongo.newId
import java.util.*


@Serializable
data class LogHours(
    val startSession: String,
    val endSession :String,
    val instructor: Boolean,
    @Serializable(with = ObjectIdAsStringSerializer::class)
    val _id: Id<User> = newId()
)


