package co.skybase.noteapp.views;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import co.skybase.noteapp.R;
import co.skybase.noteapp.repository.room.NoteModel;
import co.skybase.noteapp.viewmodel.NewNoteViewModel;

public class AddNote extends AppCompatActivity implements View.OnClickListener {
    NewNoteViewModel viewModel;
    EditText note;
    Button save;

    int noteID = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        viewModel = ViewModelProviders.of(this).get(NewNoteViewModel.class);
        if (getIntent().hasExtra("id")) {
            register(getIntent().getIntExtra("id", -1));
        }
        note = findViewById(R.id.et_note);
        save = findViewById(R.id.save);
        save.setOnClickListener(this);

    }

    private void register(int id) {
        viewModel.getNote(id).observe(this, new Observer<NoteModel>() {
            @Override
            public void onChanged(@Nullable NoteModel model) {
                if (model != null) {
                    note.setText(model.getNoteData());
                    noteID = model.getNoteId();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.save) {
            if (note.getText().length() > 0) {
                NoteModel model = new NoteModel();
                model.setNoteData(note.getText().toString());
                if (noteID != -1)
                    model.setNoteId(noteID);
                viewModel.insertNote(model);
                AddNote.super.onBackPressed();
            }

        }
    }
}
