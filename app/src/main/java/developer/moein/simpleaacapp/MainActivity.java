package developer.moein.simpleaacapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import developer.moein.simpleaacapp.Database.NoteEntry;
import developer.moein.simpleaacapp.Database.SimpleDatabase;
import developer.moein.simpleaacapp.OtherTools.SimpleAdapter;
import developer.moein.simpleaacapp.OtherTools.SimpleExecutor;
import developer.moein.simpleaacapp.OtherTools.SimpleViewModel;

/**
 * Now it's time to use our database!
 */
public class MainActivity extends AppCompatActivity implements
        SimpleAdapter.ItemClickListener{
    //Tag
    private static final String TAG = MainActivity.class.getSimpleName();
    //Init views
    @BindView(R.id.notes_list)
    RecyclerView notesRecycler;
    private SimpleAdapter adapter;
    private SimpleDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ButterKnife binding:
        ButterKnife.bind(this);
        //init adapter
        adapter = new SimpleAdapter(this,this);
        notesRecycler.setLayoutManager(new LinearLayoutManager(this));
        notesRecycler.setAdapter(adapter);
        DividerItemDecoration decoration = new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL);
        notesRecycler.addItemDecoration(decoration);
        database = SimpleDatabase.getInstance(this);
        //Item Touch helper for handling swipe :
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                //Delete Note after swipe
                SimpleExecutor.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        int position = viewHolder.getAdapterPosition();
                        List<NoteEntry> entries = adapter.getNoteEntries();
                        database.noteDao().DeleteNote(entries.get(position));
                    }
                });
            }
        }).attachToRecyclerView(notesRecycler);
        FetchData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate add note menu
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.add_note){
            Intent addNote = new Intent(this,AddNoteActivity.class);
            startActivity(addNote);
        }
        return true;
    }

    @Override
    public void OnItemClickListener(int itemId) {
        Intent noteIntent = new Intent(this,AddNoteActivity.class);
        noteIntent.putExtra(AddNoteActivity.NOTE_ID, itemId);
        startActivity(noteIntent);
    }


    /*
    Credit : MoeinDeveloper
    Google I/O 2018
    Android Architecture Components
     */

    //Retrieve data from Database when called
    private void FetchData(){
        SimpleViewModel viewModel = ViewModelProviders.of(this).get(SimpleViewModel.class);
        viewModel.getNotes().observe(this, new Observer<List<NoteEntry>>() {
            @Override
            public void onChanged(@Nullable List<NoteEntry> noteEntries) {
                adapter.setNoteEntries(noteEntries);
            }
        });
    }

}
