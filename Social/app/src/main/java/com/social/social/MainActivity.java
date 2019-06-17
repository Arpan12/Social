package com.social.social;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth mAuth;
   // private EditText UserName;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            if(resultCode == RESULT_OK){
                updateUI(mAuth.getCurrentUser());
            }
            else {
                Toast.makeText(this,"SignIn failed", LENGTH_LONG);
                updateUI(null);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            mAuth = FirebaseAuth.getInstance();
        }catch(Exception e){
            System.out.println("this is wrong");
        }

        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        menu.add(0,1,0,"history");
        SubMenu msubmenu = menu.addSubMenu(0,2,0,"developer tool");
        msubmenu.add(0,3,0,"debug");

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.add:
                Toast.makeText(this,"notes",LENGTH_LONG).show();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                Notes notes = new Notes();
                transaction.replace(R.id.main_fragment, notes);
                transaction.addToBackStack(null);
                transaction.commit();
                return true;
            case 1:
                Toast.makeText(this,"History",LENGTH_LONG).show();
                return true;
            case 3:
                Toast.makeText(this,"debug",LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateUI(mAuth.getCurrentUser());
    }

    void startSignIn(){
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);

    }

    void updateUI(FirebaseUser user){
        if(user!=null){
          //  UserName = (EditText) findViewById(R.id.UserName);
          //  UserName.setText(user.getDisplayName());

        }
        else{
            startSignIn();
        }
    }
}
