package com.treasurehunt.treasurehunt.ui;

/*
*   Created by Fady on 08/11/2016.
  */

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.treasurehunt.treasurehunt.R;
import com.treasurehunt.treasurehunt.model.PlayerInfo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {
    EditText AvatarName, Email, Password, CPassword, Phone;
    Button SignUp;
    TextView Birthdate;
    Toolbar toolbar;
    PlayerInfo player;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    ProgressDialog dialog;
    View decorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Log.i("This is the start", "of register activity class");
        ;
        decorView = getWindow().getDecorView();

//        toolbar = (Toolbar) findViewById(R.id.RegisterToolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Registration");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Email = (EditText) findViewById(R.id.Email);
        AvatarName = (EditText) findViewById(R.id.Avatar);
        Password = (EditText) findViewById(R.id.Password);
        CPassword = (EditText) findViewById(R.id.CPassword);
        Phone = (EditText) findViewById(R.id.Phone);
        Birthdate = (TextView) findViewById(R.id.Birthdate);
        SignUp = (Button) findViewById(R.id.Register);
        dialog = new ProgressDialog(RegisterActivity.this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("Loading ... ");
        Birthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDate();
            }
        });
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int Error = 0;
                if (AvatarName.getText().toString().equals("")) {
                    AvatarName.setError("Required Field");
                    Error = 1;
                }
                if (Email.getText().toString().equals("")) {
                    Email.setError("Required Field");
                    Error = 1;
                }
                if (Password.getText().toString().equals("")) {
                    Password.setError("Required Field");
                    Error = 1;
                }
                if (!Password.getText().toString().equals("") && Password.getText().toString().length() < 8) {
                    Password.setError("Password can not be less than 8 characters");
                    Error = 1;
                }
                if (!CPassword.getText().toString().equals(Password.getText().toString())) {
                    CPassword.setError("Not Match");
                    Error = 1;
                }
                if (Phone.getText().toString().equals("")) {
                    Phone.setError("Required");
                    Error = 1;
                }
                if (Birthdate.getText().toString().equals("")) {
                    Birthdate.setError("Select Your BirthDate");
                    Error = 1;
                } else {
                    String[] PDate = Birthdate.getText().toString().split("/");
                    int Pyear = Integer.parseInt(PDate[2]);
                    Log.e("year", String.valueOf(Pyear));
                    if (Pyear > 2004) {
                        Birthdate.setError("you must be older than 12 to play this game");
                        Error = 1;
                    }
                }
                if (Error == 0) {
                    dialog.show();
                    InsertNewUser(AvatarName.getText().toString(),
                            Email.getText().toString(),
                            Password.getText().toString(),
                            Phone.getText().toString(),
                            Birthdate.getText().toString());
                }


            }
        });
    }

    public void InsertNewUser(String AvatarName, String Email,
                              String Password, String Phone, String BirthDate) {
        player = new PlayerInfo(AvatarName, Email, Password, Phone, BirthDate);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    InsertPlayerData(player);
                } else {
                    dialog.dismiss();
                    Toast.makeText(RegisterActivity.this, "An Error Occured ,Try again later ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void InsertPlayerData(final PlayerInfo player) {
        DatabaseReference ref = database.getReference("Player/PlayerInfo");
        ref.child(player.getAvatarName()).setValue(player)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            dialog.setMessage("Successfully Sign up");
                            FirebaseAuth.getInstance().signInWithEmailAndPassword(player.geteMail().toString(), player.getPassword().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    dialog.dismiss();
                                    Toast.makeText(RegisterActivity.this, "your account Successfully created", Toast.LENGTH_SHORT).show();
                                    //account created and logged in!
                                    startActivity(new Intent(RegisterActivity.this, MenuActivity.class));
                                    finish();
                                }
                            });

                        } else {
                            dialog.dismiss();
                            Toast.makeText(RegisterActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    public void selectDate() {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(Calendar.YEAR, i);
                calendar.set(Calendar.MONTH, i1);
                calendar.set(Calendar.DAY_OF_MONTH, i2);
                String myformate = " dd/MM/yyyy";
                SimpleDateFormat dateformat = new SimpleDateFormat(myformate, Locale.US);
                Birthdate.setText(dateformat.format(calendar.getTime()));
                Log.e("Birth", Birthdate.getText().toString());
            }
        };
        new DatePickerDialog(this, onDateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }
}