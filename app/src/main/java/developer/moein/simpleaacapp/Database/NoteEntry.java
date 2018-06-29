package developer.moein.simpleaacapp.Database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import java.util.Date;

//TODO (2) Create Entry Class!
/**
 * This is the room Entry class.
 * Room will use this class to create a table with the name of the class;
 * For example this class is named NoteEntry, so room will create a table and name it to NoteEntry!
 * What if I want to change the table name??
 * So the answer is simple, insert the table name inside the @Entry annotation!
 */
  //defining Entry class:
@Entity(tableName = "notes")
public class NoteEntry {
    //set the id as primary key and auto generate!
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String note;
    //changing updatedAt to updated_at
    @ColumnInfo(name = "updated_at")
    private Date updatedAt;

    //Creating Constructors for Entry class:
    //Ignoring this first constructor for room library!
    @Ignore
    public NoteEntry(String title,String note,Date date){
        this.title = title;
        this.note = note;
        this.updatedAt = date;
    }
    //second constructor is for room library to retrieve data
    public NoteEntry(int id,String title,String note,Date date){
        this.id = id;
        this.title = title;
        this.note = note;
        this.updatedAt = date;
    }

    public NoteEntry(){

    }


    //Initializing Getters and Setters for Entry class:
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
