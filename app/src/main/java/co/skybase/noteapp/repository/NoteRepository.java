package co.skybase.noteapp.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import co.skybase.noteapp.repository.room.NoteDao;
import co.skybase.noteapp.repository.room.NoteDatabase;
import co.skybase.noteapp.repository.room.NoteModel;

/**
 * Created by Mach3 on 02-04-2018.
 */

public class NoteRepository {
    private LiveData<List<NoteModel>> wordData;
    private NoteDao noteDao;

    public NoteRepository(Application application) {
        NoteDatabase noteDatabase = NoteDatabase.getDatabase(application);
        noteDao = noteDatabase.noteDao();
        wordData = noteDao.selectAll();
    }

    //Does not use AsyncTask
    public LiveData<List<NoteModel>> getAllNotes() {
        return wordData;
    }

    //Does not use AsyncTask
    public LiveData<NoteModel> getNote(int id) {
        return noteDao.getNote(id);
    }

    //Then why this using AsyncTask
    public void insertNote(NoteModel model) {
        new InsertNote(noteDao).execute(model);
    }

    public void deleteNote(int id) {
        new DeleteNote(noteDao).execute(id);
    }

    private static class InsertNote extends AsyncTask<NoteModel, Void, Void> {
        NoteDao noteDao;

        InsertNote(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(NoteModel... noteModels) {
            noteDao.insertNote(noteModels[0]);
            return null;
        }
    }


    private static class DeleteNote extends AsyncTask<Integer, Void, Void> {
        NoteDao noteDao;

        DeleteNote(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Integer... ids) {
            noteDao.deleteNote(ids[0]);
            return null;
        }
    }

}
