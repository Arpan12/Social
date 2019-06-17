package com.social.social;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class notes_adapter extends RecyclerView.Adapter<notes_adapter.ViewHolder> {
    Context context;
    FragmentActivity activity;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView titleText;
        private TextView describtiontext;
        public View view;



        public ViewHolder(View viewItem){
            super(viewItem);
            titleText = (TextView)viewItem.findViewById(R.id.rowTitle);
            describtiontext = (TextView)viewItem.findViewById(R.id.rowDescription);
            this.view =  viewItem;

        }
    }

    private ArrayList<note> mnotesLists;
    public notes_adapter(ArrayList<note> noteslist,FragmentActivity c){
        mnotesLists = noteslist;
        activity = c;

    }

    @NonNull
    @Override
    public notes_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View noteView = inflater.inflate(R.layout.notes_row,parent,false);
        ViewHolder viewHolder= new ViewHolder(noteView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        System.out.println("binding position"+position);
        final note mnote = mnotesLists.get(position);
        viewHolder.titleText.setText(mnotesLists.get(position).title);
        viewHolder.describtiontext.setText(mnotesLists.get(position).description);
        viewHolder.view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                editPostFragment meditPostFragment = new editPostFragment();
                Bundle bundle = new Bundle();
                bundle.putString("postTitle",mnote.title);
                bundle.putString("postDescription",mnote.description);
                bundle.putString("postContent",mnote.content);
                bundle.putString("postID",mnote.noteId);
                meditPostFragment.setArguments(bundle);

               FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
              transaction.replace(R.id.main_fragment, meditPostFragment);
               transaction.addToBackStack(null);
               transaction.commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mnotesLists.size();
    }

}
