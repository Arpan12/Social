package com.social.social;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class Notes extends Fragment {
    Button addButton;
    private FirebaseAuth mAuth;
       public Notes(){

       }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_notes, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.notes_list);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        final ArrayList<note> noteslist = new ArrayList<note>();

        final FragmentActivity c = getActivity();
        final notes_adapter mnotes_adapter = new notes_adapter(noteslist,c);
        recyclerView.setAdapter(mnotes_adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(c);
        recyclerView.setLayoutManager(layoutManager);
        db.collection("users").document(mAuth.getUid()).collection("posts").
                get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Log.d(TAG, document.getId() + " => " + document.getData());
                                System.out.println(document.get("content"));
                                noteslist.add(new note(document.getId().toString(),document.get("title").toString(),document.get("description").toString(),document.get("content").toString()));
                                mnotes_adapter.notifyDataSetChanged();
                            }
                        } else {
                            //Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


        addButton = (Button) view.findViewById(R.id.AddNote);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new_note_form new_note= new new_note_form();
                FragmentTransaction transaction = c.getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_fragment, new_note);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return view;
    }

}
