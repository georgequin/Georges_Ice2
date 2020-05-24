package com.example.georgesice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UpdateEmail extends AppCompatActivity {
    EditText email;
    Button Bcontinue;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_email);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        email = findViewById(R.id.emaidId);
        String Email = email.getText().toString();

        Bcontinue = findViewById(R.id.continueId);
        Bcontinue.setOnClickListener((view -> {


            FirebaseUser User = mAuth.getInstance().getCurrentUser();
            User.updateEmail(Email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {



                            String TAG = "Tag";
                            Log.d(TAG, "User email address updated.");
                            Toast.makeText(UpdateEmail.this, task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            Context context = UpdateEmail.this;
                            Class DestinationActivity = UpdateData.class;
                            Intent intent = new Intent(context, DestinationActivity);
                            intent.putExtra("email", Email);

                            startActivity(intent);
                        }
                    }
            });



        }));


    }
}
