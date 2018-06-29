package developer.moein.simpleaacapp.OtherTools;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import developer.moein.simpleaacapp.Database.NoteEntry;
import developer.moein.simpleaacapp.R;
import developer.moein.simpleaacapp.R2;

public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.SimpleViewHolder>{
    private static final String TIME_FORMAT = "dd/MM/yyy";
    private Context context;
    private final ItemClickListener itemClickListener;
    private List<NoteEntry> noteEntries;
    private SimpleDateFormat simpleDateFormat =
            new SimpleDateFormat(TIME_FORMAT, Locale.getDefault());
    public List<NoteEntry> getNoteEntries() {
        return noteEntries;
    }

    public void setNoteEntries(List<NoteEntry> noteEntries) {
        this.noteEntries = noteEntries;
        notifyDataSetChanged();
    }

    public SimpleAdapter(Context c, ItemClickListener itemClickListener){
        this.context = c;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public SimpleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SimpleViewHolder(LayoutInflater.from(context)
        .inflate(R.layout.item_notes, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SimpleViewHolder holder, int position) {
        NoteEntry entry = noteEntries.get(position);
        holder.title.setText(entry.getTitle());
        holder.note.setText(entry.getNote());
        holder.date.setText(simpleDateFormat.format(entry.getUpdatedAt()));
    }

    @Override
    public int getItemCount() {
        if (noteEntries.size() == 0){
            return 0;
        }
        return noteEntries.size();
    }

    class SimpleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        //init item views
        @BindView(R2.id.item_title)
        TextView title;
        @BindView(R2.id.item_note)
        TextView note;
        @BindView(R2.id.item_date)
        TextView date;
        public SimpleViewHolder(View itemView) {
            super(itemView);
            //binding views:
            ButterKnife.bind(this,itemView);
            //setting on click listener
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //get element id:
            int elementId = noteEntries.get(getAdapterPosition()).getId();
            itemClickListener.OnItemClickListener(elementId);
        }
    }

    //creating public interface for on item click listener
    public interface ItemClickListener{
        void OnItemClickListener(int itemId);
    }
}
