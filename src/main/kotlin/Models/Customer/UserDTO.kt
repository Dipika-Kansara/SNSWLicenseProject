package Models.Customer

import ObjectIdAsStringSerializer
import kotlinx.serialization.Serializable
import org.litote.kmongo.Id
import org.litote.kmongo.newId

@Serializable
data class UserDTO(
    val firstName : String ,
    val lastName : String,
    val dob : String,
    val licenseNumber : String,
    val email : String,
    val password: String,
    val mobile : Long,
    val roles: List<String> = listOf(),
    @Serializable(with = ObjectIdAsStringSerializer::class)
    val _id: Id<User> = newId()
)

fun User.toDTO(): UserDTO {
    return UserDTO(
        firstName = this.firstName,
        lastName = this.lastName,
        dob =this.dob,
        licenseNumber= this.licenseNumber,
        email= this.email,
        password= this.password,
        mobile = this.mobile,
        _id = this._id
    )
}
