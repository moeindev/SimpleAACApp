package developer.moein.simpleaacapp.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.util.Log;

/**
 * TODO (5): Create Database Class
 * Right now, create an abstract class for your database!
 * It must extends RoomDatabase.
 * Define your Entry Classes and Converters here!
 */
//defining entities:
@Database(entities = {NoteEntry.class},version = 1,exportSchema = false)
//defining converters:
@TypeConverters(DateConverter.class)
public abstract class SimpleDatabase extends RoomDatabase{
    //log tag
    private static final String TAG = SimpleDatabase.class.getSimpleName();
    //synchronized object
    private static final Object Lock = new Object();
    //DB Name
    private static final String DATABASE_NAME = "notesDB";
    //instance:
    private static SimpleDatabase Instance;

    //creating getInstance by using singleton pattern
    public static SimpleDatabase getInstance(Context context){
        if (Instance == null){
            synchronized (Lock){
                Log.d(TAG, "Creating database instance");
                Instance = Room.databaseBuilder(context,
                        SimpleDatabase.class, DATABASE_NAME)
                        .build();
            }
        }
        Log.d(TAG, "Getting database instance!");
        return Instance;
    }
    //Get DAO:
    public abstract NoteDao noteDao();

    /*
    Additional notes:
    Singleton pattern : https://en.wikipedia.org/wiki/Singleton_pattern
     */
}
