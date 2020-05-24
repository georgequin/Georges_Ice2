package com.example.georgesice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.io.Console;
import java.util.concurrent.TimeUnit;

public class HomeActivity extends AppCompatActivity {
    Button complete ;
    EditText PhoneNum ;
    String FullPhone;
    FirebaseAuth mAuth;
    CountryCodePicker Cpp;

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    String mVerificationId;
    PhoneAuthProvider.ForceResendingToken mReturnToken;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        Cpp = findViewById(R.id.ccp);
        Cpp.setNumberAutoFormattingEnabled(true);
        PhoneNum = findViewById(R.id.phone_num);
        String mPhoneNum = PhoneNum.getText().toString();
        mAuth = FirebaseAuth.getInstance();

        complete = findViewById(R.id.complete);

        complete.setOnClickListener((view) -> {
            Cpp.registerCarrierNumberEditText(PhoneNum);
            String phone = Cpp.getFullNumberWithPlus();
            FullPhone = phone + mPhoneNum;

            Context context = HomeActivity.this;
            Class DestinationActivity = VerifyActivity.class;
            Intent intent = new Intent(context, DestinationActivity);
            intent.putExtra("phoneNum",FullPhone);
            startActivity(intent);

            Intent dataAct = new Intent(HomeActivity.this,UpdateData.class);
            dataAct.putExtra("phoneNum", FullPhone);
                                       // updateUser();



        });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null){
            Intent intent = new Intent(HomeActivity.this, NavssActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }
        else {

        }
        //updateUI(currentUser);
    }









}
