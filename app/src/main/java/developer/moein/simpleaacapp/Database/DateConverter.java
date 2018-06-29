package developer.moein.simpleaacapp.Database;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * TODO (3): Create data converter class!
 * Why? because we used java.util.Date in our Entry Class which is not supported in SQLite!
 * Check here:
 * https://www.sqlite.org/datatype3.html
 * What does this class do?
 * Well it's simple! It will get the java.util.Date and convert it to timeStamp!
 * How do I convert all of recorded data?? Well! Room will take the responsibility!
 * Just initialize the damn converter and give it to room! :/ :|
 */

public class DateConverter {
    //This method will convert timeStamp to Date!
    //For notifying room use this annotation!
    @TypeConverter
    public static Date toDate(Long timeStamp){
        return timeStamp == null ? null : new Date(timeStamp);
    }
    //This method will convert Date to timeStamp!
    @TypeConverter
    public static Long ToLong(Date date){
        return date == null ? null : date.getTime();
    }
}
