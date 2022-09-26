package Models

import ObjectIdAsStringSerializer
import kotlinx.serialization.Serializable
import org.litote.kmongo.Id
import org.litote.kmongo.newId


@Serializable
data class User(val username: String,
                val password: String,
                val roles: List<String> = listOf(),
                @Serializable(with = ObjectIdAsStringSerializer::class)
                val _id: Id<User> = newId()
) {

}