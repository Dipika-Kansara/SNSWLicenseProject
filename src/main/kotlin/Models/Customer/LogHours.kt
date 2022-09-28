package Models.Customer

import ObjectIdAsStringSerializer
import kotlinx.serialization.Serializable
import org.litote.kmongo.Id
import org.litote.kmongo.newId


@Serializable
data class LogHours(
    val date: Int,
    val time: String,
    val hours: Int,
    val minutes: Int,
    val instructor: String,
    val tripDetails: String,
    @Serializable(with = ObjectIdAsStringSerializer::class)
    val _id: Id<User> = newId()
)


