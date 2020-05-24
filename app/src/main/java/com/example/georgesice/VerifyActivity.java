package com.example.georgesice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.session.MediaSession;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class VerifyActivity extends AppCompatActivity {

    TextView Phone;
    EditText Vcode;
    Button Verify;
    FirebaseAuth mAuth;
    String mVcode;

    String mVerificationId;
    PhoneAuthProvider.ForceResendingToken mResendToken;

    PhoneAuthProvider.ForceResendingToken mReturnToken;

    //   private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        Phone = findViewById(R.id.phone);
        Vcode = findViewById(R.id.Vcode);
        Verify = findViewById(R.id.VerifyButton);

        mVcode = Vcode.getText().toString();
        String phone = getIntent().getStringExtra("phoneNum");
        Phone.setText(phone);

        mAuth = FirebaseAuth.getInstance();
        SendVerificationCodeToUser(phone);

        Verify.setOnClickListener((view) -> {

        Verifycode(mVcode);


        });
    }

    public void SendVerificationCodeToUser(String phoneNum){

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNum,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                TaskExecutors.MAIN_THREAD,               // Activity (for callback binding)
                mCallbacks              // OnVerificationStateChangedCallbacks
        );

    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.

            String code = credential.getSmsCode();
            if (code != null){
                Verifycode(code);
                Toast.makeText(VerifyActivity.this,"Verified",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(VerifyActivity.this,"code ia null",Toast.LENGTH_SHORT).show();
            }

            
            //Log.d(TAG, "onVerificationCompleted:" + credential);

            //signInWithPhoneAuthCredential(credential);
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            //Log.w(TAG, "onVerificationFailed", e);

            if (e instanceof FirebaseAuthInvalidCredentialsException) {
                // Invalid request
                Toast.makeText(VerifyActivity.this,"Incorrect code",Toast.LENGTH_SHORT).show();
                // ...
            } else if (e instanceof FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                Toast.makeText(VerifyActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                // ...
            }

            // Show a message and update the UI
            // ...
        }

        @Override
        public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken token) {
            super.onCodeSent(verificationId, token);
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            //Log.d(TAG, "onCodeSent:" + verificationId);

            // Save verification ID and resending token so we can use them later
            mVerificationId = verificationId;
            mResendToken = token;
            Toast.makeText(VerifyActivity.this, "code sent" + mVerificationId,Toast.LENGTH_SHORT).show();


            // ...
        }
    };

    public void Verifycode(String VerificationCode){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, VerificationCode);
        SIgnInTheUserByCredential(credential);

    }

    public void SIgnInTheUserByCredential(PhoneAuthCredential credential){
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithCredential(credential).addOnCompleteListener(VerifyActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Context context = VerifyActivity.this;
                    Class DestinationActivity = UpdateEmail.class;
                    Intent intent = new Intent(context, DestinationActivity);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                }else {
                    Toast.makeText(VerifyActivity.this, task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

//
//
//    ///Add this method below auth initialization in the onCreate method.
//
//    void updateUser(){
//
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
//                .setDisplayName("Jane Q. User")
//                .setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
//                .build();
//
//        user.updateProfile(profileUpdates)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            String TAG = "Tag";
//                            Log.d(TAG, "User profile updated.");
//                        }
//                    }
//                });
//
//    }
}
