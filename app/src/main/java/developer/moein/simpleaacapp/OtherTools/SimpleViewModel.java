package developer.moein.simpleaacapp.OtherTools;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import developer.moein.simpleaacapp.Database.NoteEntry;
import developer.moein.simpleaacapp.Database.SimpleDatabase;

/**
 * TODO (7): Create a viewModel!
 * What is a ViewModel?
 * https://developer.android.com/topic/libraries/architecture/viewmodel
 * The ViewModel class is designed to store and manage UI-related data in a lifecycle conscious way.
 * The ViewModel class allows data to survive configuration changes such as screen rotations.
 */
public class SimpleViewModel extends AndroidViewModel{
    //For tagging
    private static final String TAG = SimpleViewModel.class.getSimpleName();
    private LiveData<List<NoteEntry>> Notes;

    //Default constructor:
    public SimpleViewModel(@NonNull Application application) {
        super(application);
        //Get App Database
        SimpleDatabase database = SimpleDatabase.getInstance(this.getApplication());
        Log.d(TAG, "retrieving the tasks from the DataBase");
        Notes = database.noteDao().Notes();
    }
    //Getter for Notes:
    public LiveData<List<NoteEntry>> getNotes() {
        return Notes;
    }
}
