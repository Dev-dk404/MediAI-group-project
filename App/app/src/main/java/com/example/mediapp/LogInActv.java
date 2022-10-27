package com.example.mediapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

import data.MyApp;

public class LogInActv extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //MyApp is a static class that is always running in the background to provide access to resources without the need of sending bundles
        MyApp.createData(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_actv);
        Button button = (Button) findViewById(R.id.btnLogIn);

    }

    public void LogIn(View view) throws InvalidArgumentException {
        /*
    Log In method, when provided with credentials, returns true the user is a valid user, false otherwise
     */
        String TAG = "LogIn";
        String email = ((TextView) findViewById(R.id.txtUserId)).getText().toString();
        String pass = ((TextView) findViewById(R.id.txtPassword)).getText().toString();
        if (email.isEmpty() || pass.isEmpty()) {
            ((TextView) findViewById(R.id.txtIncorrectLogIn)).setText(getString(R.string.txt_login_empty));
            return;
        } else {


            Task auth = MyApp.getData().getMAuth().signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this,
                            new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        newActivity();
                                        Log.d(TAG, "signInWithEmail:success");
                                        FirebaseUser fbUser = MyApp.getData().getMAuth().getCurrentUser();
                                       // MyApp.getData().setUser(fbUser);
                                        //don't wait for this, it will run after execution

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        ((TextView) findViewById(R.id.txtIncorrectLogIn)).setText(getString(R.string.login_failed));
                                    }
                                }
                            });

        }
        MyApp.getData().setUser();

    }

    private void newActivity() {
        Intent send = null;
        //TODO: uncomment when full authentication is done
        //   if (MyApp.getData().getUser() instanceof Client)
        send = new Intent(LogInActv.this, MenuActv.class);
       /* if (MyApp.getData().getUser() instanceof DoctorUser)
            send = new Intent(LogInActv.this, MenuActv.class);
        if (send != null)*/
        startActivity(send);
        /*else
            throw new InvalidArgumentException("User is not doctor nor Client");*/
    }


    private class InvalidArgumentException extends Exception {
        public InvalidArgumentException(String s) {
        }
    }
}