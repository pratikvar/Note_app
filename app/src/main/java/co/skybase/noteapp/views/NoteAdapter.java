package co.skybase.noteapp.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.skybase.noteapp.R;
import co.skybase.noteapp.repository.room.NoteModel;

/**
 * Created by Mach3 on 02-04-2018.
 */

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {

    private LayoutInflater layoutInflater;
    private List<NoteModel> noteModels = new ArrayList<>();

    private final NoteClickCallback noteClickCallback;

    public NoteAdapter(Context context, NoteClickCallback noteClickCallback) {
        layoutInflater = LayoutInflater.from(context);
        this.noteClickCallback = noteClickCallback;
    }

    @Override
    public NoteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.child_note, parent, false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NoteHolder holder, int position) {
        holder.noteView.setText(noteModels.get(position).getNoteData());
    }


    @Override
    public int getItemCount() {
        return noteModels.size();
    }

    public void setNoteData(List<NoteModel> data) {
        noteModels = data;
        notifyDataSetChanged();
    }

    /*
    ViewHolder class
     */
    class NoteHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView noteView;

        public NoteHolder(View itemView) {
            super(itemView);
            noteView = itemView.findViewById(R.id.tv_note);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            noteClickCallback.onNoteClick(noteModels.get(getAdapterPosition()).getNoteId());
        }
    }

    /*
    * Callback interface for RV
    * */
    interface NoteClickCallback {
        void onNoteClick(int noteID);
    }
}
