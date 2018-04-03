package co.skybase.noteapp.repository.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Mach3 on 02-04-2018.
 */

@Dao
public interface NoteDao {
    @Query("select * from " + NoteContract.NOTE_TABLE_NAME + " order by " + NoteContract.NOTE_COLUMN_ID + " desc")
    LiveData<List<NoteModel>> selectAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNote(NoteModel model);

    @Query("select * from " + NoteContract.NOTE_TABLE_NAME + " where " + NoteContract.NOTE_COLUMN_ID + "=:id")
    LiveData<NoteModel> getNote(int id);

}
