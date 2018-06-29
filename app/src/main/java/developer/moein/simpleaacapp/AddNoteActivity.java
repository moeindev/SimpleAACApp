package developer.moein.simpleaacapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import developer.moein.simpleaacapp.Database.NoteEntry;
import developer.moein.simpleaacapp.Database.SimpleDatabase;
import developer.moein.simpleaacapp.OtherTools.AddNoteViewModel;
import developer.moein.simpleaacapp.OtherTools.AddNoteViewModelFactory;
import developer.moein.simpleaacapp.OtherTools.SimpleExecutor;

public class AddNoteActivity extends AppCompatActivity {
    //Keys:
    public static final String NOTE_ID = "noteId";
    public static final String INSTANCE_TASK_ID = "instanceTaskId";
    private static final int DEFAULT_TASK_ID = -1;
    //set default task id:
    private int taskId = DEFAULT_TASK_ID;
    //Tag:
    private static final String TAG = AddNoteActivity.class.getSimpleName();
    //Simple database:
    private SimpleDatabase database;
    //initialize views
    @BindView(R.id.note_title) EditText title;
    @BindView(R.id.note_desc) EditText description;
    @BindView(R.id.save_note) Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        ButterKnife.bind(this);
        //get db instance
        database = SimpleDatabase.getInstance(this);
        //get the intent
        Intent intent = getIntent();
        //check the intent contains the note key for updating
        if (intent != null && intent.hasExtra(NOTE_ID)){
            //set button text
            saveButton.setText("Update");
            //get task ID
            taskId = intent.getExtras().getInt(NOTE_ID, DEFAULT_TASK_ID);
            //create the ViewModel factory that we created before:
            AddNoteViewModelFactory viewModelFactory = new AddNoteViewModelFactory(database,taskId);
            //init our ViewModel
            AddNoteViewModel viewModel = ViewModelProviders.of(this,viewModelFactory)
                    .get(AddNoteViewModel.class);
            //observe it
            viewModel.getNote().observe(this, new Observer<NoteEntry>() {
                @Override
                public void onChanged(@Nullable NoteEntry noteEntry) {
                    //update UI when data is changed
                    ShowData(noteEntry);
                }
            });
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!title.getText().toString().isEmpty() &&
                        !description.getText().toString().isEmpty()){
                    OnSaveButtonClicked();
                }
            }
        });
    }

    //handling save button click
    private void OnSaveButtonClicked(){
        String t = title.getText().toString();
        String d = description.getText().toString();
        final Date date = new Date();
        final NoteEntry entry = new NoteEntry(t,d,date);
        SimpleExecutor.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (taskId == DEFAULT_TASK_ID){
                    database.noteDao().NewNote(entry);
                }else {
                    entry.setId(taskId);
                    database.noteDao().UpdateNote(entry);
                }
                finish();
            }
        });
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(INSTANCE_TASK_ID, taskId);
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        if (outState.containsKey(INSTANCE_TASK_ID)){
            taskId = outState.getInt(INSTANCE_TASK_ID, DEFAULT_TASK_ID);
        }
    }

    //populate UI when called
    private void ShowData(NoteEntry note){
        if (note == null){
            return;
        }
        title.setText(note.getTitle());
        description.setText(note.getNote());
    }
}
