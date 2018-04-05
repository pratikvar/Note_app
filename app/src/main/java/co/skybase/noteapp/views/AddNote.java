package co.skybase.noteapp.views;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.MenuItem;
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

        registerEditTextLinks();
        setupToolbar();
    }

    private void setupToolbar() {
        ActionBar toolbar = getSupportActionBar();
        if (toolbar != null) {
            toolbar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            super.onBackPressed();
        return true;
    }

    private void register(int id) {
        viewModel.getNote(id).observe(this, new Observer<NoteModel>() {
            @Override
            public void onChanged(@Nullable NoteModel model) {
                if (model != null) {
                    note.setText(model.getNoteData());
                    note.setSelection(model.getNoteData().length());
                    noteID = model.getNoteId();
                }
            }
        });
    }

    private void registerEditTextLinks() {
        note.setLinksClickable(true);
        note.setAutoLinkMask(Linkify.WEB_URLS);
        note.setMovementMethod(LinkMovementMethod.getInstance());
        //If the edit text contains previous text with potential links
        Linkify.addLinks(note, Linkify.WEB_URLS);

        note.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                Linkify.addLinks(s, Linkify.WEB_URLS);
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
