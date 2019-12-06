package com.example.cs465_menugram;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    // Creating EditText.
    EditText email, password ;

    // Creating string to hold values.
    String EmailHolder, PasswordHolder;

    // Creating buttons.
    ImageView Login;
    TextView SignUP ;

    // Creating Boolean to hold EditText empty true false value.
    Boolean EditTextEmptyCheck;

    // Creating progress dialog.
    ProgressDialog progressDialog;

    // Creating FirebaseAuth object.
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Assign ID's to EditText.
        email = (EditText)findViewById(R.id.editText_email);
        password = (EditText)findViewById(R.id.editText_password);

        // Assign ID's to button.
        Login = (ImageView) findViewById(R.id.button_login);
        SignUP = (TextView) findViewById(R.id.button_SignUP);

        progressDialog =  new ProgressDialog(LoginActivity.this);

        // Assign FirebaseAuth instance to FirebaseAuth object.
        firebaseAuth = FirebaseAuth.getInstance();


        // Checking if user already logged in before and not logged out properly.
        if(firebaseAuth.getCurrentUser() != null){

            // Finishing current Login Activity.
            finish();

            // Opening UserProfileActivity .
            Intent intent = new Intent(LoginActivity.this, UserProfileActivity.class);
            startActivity(intent);
        }


        // Adding click listener to login button.
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Calling method CheckEditTextIsEmptyOrNot().
                CheckEditTextIsEmptyOrNot();

                // If  EditTextEmptyCheck == true
                if(EditTextEmptyCheck)
                {

                    // If  EditTextEmptyCheck == true then login function called.
                    LoginFunction();

                }
                else {

                    // If  EditTextEmptyCheck == false then toast display on screen.
                    Toast.makeText(LoginActivity.this, "Please Fill All the Fields", Toast.LENGTH_LONG).show();
                }


            }
        });

        // Adding click listener to Sign up button.
        SignUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Closing current activity.
                finish();

                // Opening the Main Activity .
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);

            }
        });
    }

    // Creating method to check EditText is empty or not.
    public void CheckEditTextIsEmptyOrNot(){

        // Getting value form Email's EditText and fill into EmailHolder string variable.
        EmailHolder = email.getText().toString().trim();

        // Getting value form Password's EditText and fill into PasswordHolder string variable.
        PasswordHolder = password.getText().toString().trim();

        // Checking Both EditText is empty or not.
        if(TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder))
        {

            // If any of EditText is empty then set value as false.
            EditTextEmptyCheck = false;

        }
        else {

            // If any of EditText is empty then set value as true.
            EditTextEmptyCheck = true ;

        }

    }

    // Creating login function.
    public void LoginFunction(){

        // Setting up message in progressDialog.
        progressDialog.setMessage("Please Wait");

        // Showing progressDialog.
        progressDialog.show();

        // Calling  signInWithEmailAndPassword function with firebase object and passing EmailHolder and PasswordHolder inside it.
        firebaseAuth.signInWithEmailAndPassword(EmailHolder, PasswordHolder)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        // If task done Successful.
                        if(task.isSuccessful()){

                            // Hiding the progress dialog.
                            progressDialog.dismiss();

                            // Closing the current Login Activity.
                            finish();


                            // Opening the UserProfileActivity.
                            Intent intent = new Intent(LoginActivity.this, UserProfileActivity.class);
                            startActivity(intent);
                        }
                        else {

                            // Hiding the progress dialog.
                            progressDialog.dismiss();

                            // Showing toast message when email or password not found in Firebase Online database.
                            Toast.makeText(LoginActivity.this, "Email or Password Not found, Please Try Again", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

}

