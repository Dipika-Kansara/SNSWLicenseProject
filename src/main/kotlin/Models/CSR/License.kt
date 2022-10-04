package Models.CSR

import ObjectIdAsStringSerializer
import kotlinx.serialization.Serializable
import org.litote.kmongo.Id
import org.litote.kmongo.newId
import java.time.LocalDate

@Serializable
data class License(
    var licenseClass: String = "l",
    var issueDate: String = LocalDate.now().toString(),
    var expiryDate: String = LocalDate.now().plusYears(5).toString(),
    var issuingCSR: String = "",
    var number: Int = 0,

    @Serializable(with =ObjectIdAsStringSerializer::class)
    val val_id: Id<License> = newId(),
)
