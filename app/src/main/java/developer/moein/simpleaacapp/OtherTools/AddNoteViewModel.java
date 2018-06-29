package developer.moein.simpleaacapp.OtherTools;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import developer.moein.simpleaacapp.Database.NoteEntry;
import developer.moein.simpleaacapp.Database.SimpleDatabase;

//TODO (8): Create Add note viewModel
public class AddNoteViewModel extends ViewModel{
    //set the variables:
    private LiveData<NoteEntry> Note;
    public AddNoteViewModel(SimpleDatabase database,int id){
        Note = database.noteDao().LoadNoteById(id);
    }

    //Getter for getting data :
    public LiveData<NoteEntry> getNote() {
        return Note;
    }
}
