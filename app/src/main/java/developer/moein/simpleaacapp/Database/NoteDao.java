package developer.moein.simpleaacapp.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * TODO (4): Create your Data Access Object Class!
 * Also known as DAO!
 * By setting @Dao annotation, room will notice that is the DAO class
 * What does this creepy DAO do?! well,Simply implements your query to the database!
 * How? by just adding some abstract methods!
 * Lets dive into the code now!
 */
@Dao
public interface NoteDao {
    /**
     * What is liveData?? take a look at here :
     * https://developer.android.com/topic/libraries/architecture/livedata
     * https://android.jlelse.eu/android-architecture-components-livedata-1ce4ab3c0466
     * Summery : It's a self-aware lifecycle object!
     * Let's use it for getting List of Notes!
     */
    //Get the data by using @Query annotation:
    @Query("SELECT * FROM notes")
    LiveData<List<NoteEntry>> Notes();

    //insert data by using @Insert annotation:
    @Insert
    void NewNote(NoteEntry note);

    //Update data by using @Update annotation:
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void UpdateNote(NoteEntry note);

    //Delete data by using @Delete annotation:
    @Delete
    void DeleteNote(NoteEntry note);

    //Get Note as LiveData by id
    @Query("SELECT * FROM notes WHERE id = :id")
    LiveData<NoteEntry> LoadNoteById(int id);

    /**
     * Done!
     * Lets move forward!
     * Additional notes:
     * Can you write SQL Queries??
     * here is some SQL courses:
     * https://www.sololearn.com/Course/SQL/
     * https://www.linkedin.com/premium/plan/learning?categoryId=446&categoryCollection=developer&trk=sem_src.go-pa_c.google-lil-sem-prs-b2c-uk-eng-nb-beta-b2-dev-sql_pkw.%2Bsql+%2Bcourse_pmt.b_pcrid.269555865824_pdv.c_plc._trg._net.g_learning&hero=10&veh=sem_src.go-pa_c.google-lil-sem-prs-b2c-uk-eng-nb-beta-b2-dev-sql_pkw.%2Bsql+%2Bcourse_pmt.b_pcrid.269555865824_pdv.c_plc._trg._net.g_learning&src=go-pa&gclid=EAIaIQobChMI2ZK2jsT22wIVCc0bCh2kzgvlEAAYAiAAEgL8S_D_BwE
     * https://www.udemy.com/the-complete-sql-bootcamp/?utm_term=_._pl__._pd__._ti_aud-452969460454:kwd-399849333_._kw_sql%20course_._&k_clickid=5ee07220-870c-494c-9fed-58cfd4c1fe2e_408_GOOGLE_NEW-AW-PROS-PROF-Business-Sql-EN-ENG_._ci_762616_._sl_ENG_._vi_PROF_._sd_._la_EN_.__course_sql%20course_e_192996774858_c&utm_campaign=NEW-AW-PROS-PROF-Business-Sql-EN-ENG_._ci_762616_._sl_ENG_._vi_PROF_._sd_._la_EN_._&matchtype=e&utm_source=adwords&utm_medium=udemyads&utm_content=_._ag_course_._ad_192996774858_._de_c_._dm__._lo_2826_._&gclid=EAIaIQobChMI2ZK2jsT22wIVCc0bCh2kzgvlEAAYBCAAEgJiOvD_BwE
     * https://www.coursera.org/courses?languages=en&query=sql
     * Original Website:
     * https://www.sqlite.org/index.html
     */
}
