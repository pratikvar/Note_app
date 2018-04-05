package co.skybase.noteapp.viewmodel;

import android.app.AlertDialog;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import co.skybase.noteapp.repository.NoteRepository;
import co.skybase.noteapp.repository.room.NoteModel;

/**
 * Created by Mach3 on 02-04-2018.
 */

public class NoteViewModel extends AndroidViewModel {
    private LiveData<List<NoteModel>> noteList;

    private NoteRepository noteRepository;

    public NoteViewModel(Application application) {
        super(application);
        noteRepository = new NoteRepository(application);
        noteList = noteRepository.getAllNotes();
    }

    public LiveData<List<NoteModel>> getNoteList() {
        return noteList;
    }

    public void deleteNote(Integer id) {
        noteRepository.deleteNote(id);
    }
}
