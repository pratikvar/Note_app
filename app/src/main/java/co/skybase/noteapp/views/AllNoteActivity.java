package co.skybase.noteapp.views;

import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.List;

import co.skybase.noteapp.R;
import co.skybase.noteapp.repository.room.NoteModel;
import co.skybase.noteapp.viewmodel.NoteViewModel;

public class AllNoteActivity extends AppCompatActivity implements NoteAdapter.NoteClickCallback {

    RecyclerView rvNoteData;
    NoteAdapter noteAdapter;

    Button newNote;

    NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_note);
        newNote = findViewById(R.id.new_note);
        rvNoteData = findViewById(R.id.rv_note_list);
        //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        rvNoteData.setLayoutManager(layoutManager);
        //rvNoteData.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        noteAdapter = new NoteAdapter(this, this);
        rvNoteData.setAdapter(noteAdapter);

        register();
        registerEvent();
    }

    private void register() {
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);

        noteViewModel.getNoteList().observe(this, new Observer<List<NoteModel>>() {
            @Override
            public void onChanged(@Nullable List<NoteModel> noteModels) {
                noteAdapter.setNoteData(noteModels);
            }
        });
    }

    private void registerEvent() {
        newNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AllNoteActivity.this, AddNote.class));
            }
        });
    }

    @Override
    public void onNoteClick(int noteID) {
        Intent intent = new Intent(this, AddNote.class);
        intent.putExtra("id", noteID);
        startActivity(intent);
    }

    @Override
    public void onNoteLongClick(final int noteID) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setTitle(R.string.confirm_delete);
        builder.setMessage(R.string.delete_note);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                noteViewModel.deleteNote(noteID);
            }
        });

        builder.setNegativeButton(R.string.cancel, null);
        builder.show();
    }
}
