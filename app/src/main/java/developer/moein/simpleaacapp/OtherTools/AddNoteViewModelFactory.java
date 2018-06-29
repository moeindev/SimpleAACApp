package developer.moein.simpleaacapp.OtherTools;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import developer.moein.simpleaacapp.Database.SimpleDatabase;

//TODO (9): Create a viewModel for adding new note!
public class AddNoteViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    //Create variables:
    private final SimpleDatabase database;
    private final int id;
    //Constructor:
    public AddNoteViewModelFactory(SimpleDatabase database,int id){
        this.database = database;
        this.id = id;
    }

    //Create method:
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AddNoteViewModel(database,id);
    }
}
