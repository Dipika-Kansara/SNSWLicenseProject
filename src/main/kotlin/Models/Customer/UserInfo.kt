package Models.Customer

import ObjectIdAsStringSerializer
import kotlinx.serialization.Serializable
import org.litote.kmongo.Id
import org.litote.kmongo.newId

@Serializable
data class UserInfo(
    val firstName : String ,
    val lastName : String,
    val dob : String,
    val licenseNumber : String,
    val email : String,
    val mobile : Long,
    val roles: List<String> = listOf(),
    @Serializable(with = ObjectIdAsStringSerializer::class)
    val _id: Id<User> = newId()
)
