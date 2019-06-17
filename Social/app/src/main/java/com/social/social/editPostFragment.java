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
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class editPostFragment extends Fragment {
    private EditText postTitle;
    private EditText postDescription;
    private EditText postContent;
    private Button addButton;
    FragmentActivity c ;
    private FirebaseAuth mAuth;



    public editPostFragment() {
        // Required empty public constructor
    }


    public void backToPostList(){
        Notes notes= new Notes();
        FragmentTransaction transaction = c.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_fragment, notes);
       // transaction.addToBackStack(null);
        transaction.commit();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_new_note_form, container, false);
         postTitle = view.findViewById(R.id.PostTitle);
         postDescription = view.findViewById(R.id.PostDescription);
         postContent = view.findViewById(R.id.PostContent);
        c = getActivity();
        mAuth = FirebaseAuth.getInstance();

        final Bundle bundle = this.getArguments();
        postTitle.setText(bundle.get("postTitle").toString());
        postDescription.setText(bundle.get("postDescription").toString());
        postContent.setText(bundle.get("postContent").toString());
        addButton = view.findViewById(R.id.submitButton);
        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
               submitFom(bundle.get("postID").toString());
               backToPostList();
            }
        });

        return view;
    }

    private void submitFom(String postID){
        if(postTitle.getText()==null || postTitle.getText().toString() ==""){
            postTitle.setError("this field can not be empty");
            return;
        }
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String,String> Post = new HashMap<>();
        Post.put("title",postTitle.getText().toString());
        Post.put("description",postDescription.getText().toString());
        Post.put("content",postContent.getText().toString());
        db.collection("users").document(mAuth.getUid()).collection("posts").document(postID).set(Post).addOnSuccessListener(new OnSuccessListener<Void>(){
            @Override
            public void onSuccess(Void aVoid) {
                //Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                Toast.makeText(c,"change successful done",Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(c,"error creating post",Toast.LENGTH_LONG).show();
            }
        });

    }



}
