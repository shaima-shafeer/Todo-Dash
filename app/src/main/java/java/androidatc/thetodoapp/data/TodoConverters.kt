package java.androidatc.thetodoapp.data

import androidx.room.TypeConverter
import java.time.LocalDate
import java.util.Date

class Converters {
    @TypeConverter
    fun fromDate(date: LocalDate?): Long? {
        return date?.toEpochDay()
    }

    @TypeConverter
    fun toDate(epochDay: Long?): LocalDate? {
        return epochDay?.let { LocalDate.ofEpochDay(it) }
    }
}
