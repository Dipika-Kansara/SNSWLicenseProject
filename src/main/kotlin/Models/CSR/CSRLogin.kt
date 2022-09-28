package Models.CSR

import Models.Customer.User
import ObjectIdAsStringSerializer
import kotlinx.serialization.Serializable
import org.litote.kmongo.Id
import org.litote.kmongo.newId

@Serializable
data class CSRLogin (

        val email : String,
        val password : String,
        @Serializable(with = ObjectIdAsStringSerializer::class)
        val _id: Id<User> = newId()
    )
