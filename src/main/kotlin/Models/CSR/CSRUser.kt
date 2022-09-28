package Models.CSR

import Models.Customer.User
import ObjectIdAsStringSerializer
import kotlinx.serialization.Serializable
import org.litote.kmongo.Id
import org.litote.kmongo.newId


@Serializable
data class CSRUser(
    val firstName : String ,
    val lastName : String,
    val email : String,
    val staffId:Int,
    val password: String,
    val roles: List<String> = listOf(),
    @Serializable(with = ObjectIdAsStringSerializer::class)
    val _id: Id<User> = newId()
)
