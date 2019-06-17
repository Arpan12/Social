package com.social.social;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class new_note_form extends Fragment {
    private TextView title;
    private TextView description;
    private TextView content;
    private Button submitButton;
    FragmentActivity c ;
    private FirebaseAuth mAuth;

    public new_note_form() {

    }

    public void backToPostList(){
        Notes notes= new Notes();
        FragmentTransaction transaction = c.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_fragment, notes);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_new_note_form, container, false);
        c = getActivity();
        title = view.findViewById(R.id.PostTitle);
        description = view.findViewById(R.id.PostDescription);
        content = view.findViewById(R.id.PostContent);
        submitButton = (Button)view.findViewById(R.id.submitButton);
        mAuth = FirebaseAuth.getInstance();
        submitButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        submitForm();
                        backToPostList();
                    }
                }
        );
        return view;
    }

    void submitForm(){
        if(title.getText()==null || title.getText() ==""){
            title.setError("this field can not be empty");
            return;
        }
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String,String> Post = new HashMap<>();
        Post.put("title",title.getText().toString());
        Post.put("description",description.getText().toString());
        Post.put("content",content.getText().toString());
        db.collection("users").document(mAuth.getUid()).collection("posts").add(Post).addOnSuccessListener(new OnSuccessListener<DocumentReference>(){
            @Override
            public void onSuccess(DocumentReference documentReference) {
                //Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                Toast.makeText(c,"DocumentSnapshot added with ID:"+documentReference.getId(),Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(c,"error creating post",Toast.LENGTH_LONG).show();
            }
        });


    }


}
