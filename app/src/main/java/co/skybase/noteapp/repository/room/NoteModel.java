package co.skybase.noteapp.repository.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Mach3 on 02-04-2018.
 */

@Entity(tableName = NoteContract.NOTE_TABLE_NAME)
public class NoteModel {
    @PrimaryKey(autoGenerate = true)
    private
    int noteId;

    @ColumnInfo(name = NoteContract.NOTE_COLUMN_NOTE)
    private
    String noteData;

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public String getNoteData() {
        return noteData;
    }

    public void setNoteData(String noteData) {
        this.noteData = noteData;
    }
}
