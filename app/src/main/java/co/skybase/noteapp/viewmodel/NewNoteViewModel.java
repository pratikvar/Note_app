package co.skybase.noteapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import co.skybase.noteapp.repository.NoteRepository;
import co.skybase.noteapp.repository.room.NoteModel;

/**
 * Created by Mach3 on 02-04-2018.
 */

public class NewNoteViewModel extends AndroidViewModel {
    private NoteRepository noteRepository;


    public NewNoteViewModel(Application application) {
        super(application);
        noteRepository = new NoteRepository(application);
    }

    public void insertNote(NoteModel model) {
        noteRepository.insertNote(model);
    }

    public LiveData<NoteModel> getNote(int id) {
        return noteRepository.getNote(id);
    }
}
