package License


import Models.Customer.LogEntryDTO
import Models.Customer.LogHours
import Models.Customer.TimeUnitDTO
import ObjectIdAsStringSerializer
import kotlinx.serialization.Serializable
import org.litote.kmongo.Id
import org.litote.kmongo.newId
import java.time.Duration
import java.time.LocalDate

@Serializable
data class LearnerLicence(

    val issued : String = LocalDate.now().toString(),
    val expiry : String = LocalDate.now().plusYears(5).toString(),
    val issuedBy: String = "",
    val licenseNumber : String,
    val logEntries: MutableList<LogHours> = mutableListOf(),
    @Serializable(with = ObjectIdAsStringSerializer::class)
    val _id: Id<LearnerLicence> = newId(),

   )
{
    fun dto() : LearnerLicenceDTO = LearnerLicenceDTO(this)
}


@Serializable
class LearnerLicenceDTO{

    @Serializable(with = ObjectIdAsStringSerializer::class)

    val issued:String
    val expiry : String
    val issuedBy: String
    val licenseNumber : String
    val logEntries: MutableList<LogEntryDTO>
    val total: TimeUnitDTO
    val _id: Id<LearnerLicence>

    constructor(licence: LearnerLicence){

        issued = licence.issued
        expiry = licence.expiry
        issuedBy = licence.issuedBy
        licenseNumber = licence.licenseNumber
        var licenceTotal : Long = 0
        logEntries = licence.logEntries.map {
            val duration = TimeUnitDTO(it.duration)
            val bonus = TimeUnitDTO(it.bonus)
            val total = TimeUnitDTO(it.total)
            licenceTotal += it.total.toSeconds()
            LogEntryDTO(it.startSession,it.endSession,it.instructor,duration,bonus,total)
        }.toMutableList()
        _id = licence._id
        val ltd = Duration.ofSeconds(licenceTotal)
        total = TimeUnitDTO(ltd)
    }
}