package com.example.georgesice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateData extends AppCompatActivity {
    EditText firstName, lastName;
    Button doneButton;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
// ...



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        firstName = findViewById(R.id.first_nameId);
        lastName = findViewById(R.id.last_nameId);
        doneButton = findViewById(R.id.done);


        String FirstName = firstName.getText().toString();
        String LastName = lastName.getText().toString();
        String Fullname = FirstName + " " + lastName;
        String phone = getIntent().getStringExtra("phoneNum");
        String Email = getIntent().getStringExtra("email");


        doneButton.setOnClickListener(view -> {

            mDatabase = FirebaseDatabase.getInstance().getReference().child("User");

            mDatabase.setValue(new User(phone,Fullname, Email))
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getApplicationContext(), "Added new User", Toast.LENGTH_SHORT).show();
                            Context context = UpdateData.this;
                            Class DestinationActivity = NavssActivity.class;
                            Intent intent = new Intent(context, DestinationActivity);

                        }
                    });

//            User user = new User(phone, Fullname, Email );
//            mDatabase.child("users").child(Fullname).setValue(user);



        });


    }
}
