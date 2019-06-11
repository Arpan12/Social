package com.social.social;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

public class notes_adapter extends RecyclerView.Adapter<notes_adapter.ViewHolder> {
    private TextView titleText;
    private TextView describtiontext;


    public class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View viewItem){
            super(viewItem);
            titleText = (TextView)viewItem.findViewById(R.id.rowTitle);
            describtiontext = (TextView)viewItem.findViewById(R.id.rowDescription);
        }
    }

    private List<note> mnotesLists;
    public notes_adapter(List<note> noteslist){
        mnotesLists = noteslist;
    }

    @NonNull
    @Override
    public notes_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View noteView = inflater.inflate(R.layout.notes_row,parent,false);
        ViewHolder viewHolder= new ViewHolder(noteView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        note note = mnotesLists.get(position);
        

    }
}
