package Models.Customer

import License.LearnerLicence
import ObjectIdAsStringSerializer
import com.fasterxml.jackson.annotation.JsonIgnore
import kotlinx.serialization.Serializable
import org.litote.kmongo.Id
import org.litote.kmongo.newId
import java.time.Duration
import java.time.LocalDate



@Serializable
data class LogHours(
    val startSession: Long,
    val endSession: Long,
    val instructor: Boolean,
    @Serializable(with = ObjectIdAsStringSerializer::class)
    val _id: Id<User> = newId()
)
{
    @get:JsonIgnore
    val duration : Duration
        get(){
            return Duration.ofMillis(endSession - startSession)
        }
    @get:JsonIgnore
    val bonus : Duration
        get(){
            return Duration.ofMillis(if(instructor) duration.toMillis() * 2 else 0)
        }
    @get:JsonIgnore
    val total : Duration
        get() {
            return Duration.ofMillis(duration.toMillis() + bonus.toMillis())
        }
}


@Serializable
data class LogEntryDTO(
    val startSession: Long,
    val endSession: Long,
    val instructor:Boolean,
    val duration : TimeUnitDTO,
    val bonus : TimeUnitDTO,
    val total : TimeUnitDTO
)

@Serializable
class TimeUnitDTO {
    val hours: Long
    val minutes: Long
    val seconds: Long
    constructor(d:Duration){
        hours = d.seconds / 3600;
        minutes = (d.seconds  % 3600) / 60;
        seconds = d.seconds  % 60;
    }
}


