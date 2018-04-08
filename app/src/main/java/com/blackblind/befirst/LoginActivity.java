package com.blackblind.befirst;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {


    private TextInputLayout emailWrapper;
    private android.support.design.widget.TextInputLayout passwordWrapper;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private EditText editTextEmail;
    private EditText editTextPassword;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        getSupportActionBar().setDisplayShowCustomEnabled(true);
//        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
//        View view =getSupportActionBar().getCustomView();


        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
//        if (firebaseAuth.getCurrentUser() != null){
//            startActivity(new Intent(LoginActivity.this, MainActivity.class));
//            finish();
//        }

        progressDialog = new  ProgressDialog(this);

        emailWrapper = findViewById(R.id.emailWrapper);
        passwordWrapper = findViewById(R.id.passwordWrapper);
        editTextEmail = findViewById(R.id.et_email);
        editTextPassword = findViewById(R.id.et_password);

        TextView textView = findViewById(R.id.tv_signUp);

        Button btnSignin = findViewById(R.id.btn_signIn);

        emailWrapper.setHint("Email");
        passwordWrapper.setHint("Password");

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegistrationActivity.class));
                finish();
            }
        });


        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                hideKeyboard();

                attemptLogin();


            }


        });
    }

    private void fireBaseLogin() {

        String email = editTextEmail.getText().toString().trim();
        String pass = editTextPassword.getText().toString().trim();

        progressDialog.setMessage("User Logining....");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                    finish();

                    startActivity(new Intent(getApplicationContext(), MainActivity.class));

//                    if (firebaseAuth.getCurrentUser() != null){
//                        startActivity(new Intent(LoginActivity.this, ChoiceActivity.class));
//                        finish();
//                    }
                }
                if (!task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Login failed !! Please try again later", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void attemptLogin() {

        emailWrapper.setError(null);
        passwordWrapper.setError(null);

        String email = Objects.requireNonNull(emailWrapper.getEditText()).getText().toString();
        String password = Objects.requireNonNull(passwordWrapper.getEditText()).getText().toString();

        boolean cancel = false;
        View focusView = null;

        // check for a valid email
        if (TextUtils.isEmpty(email)) {
            emailWrapper.setError(getString(R.string.error_required_field));
            focusView = emailWrapper;
            cancel = true;
        } else if (!isEmailValid(email)) {
            emailWrapper.setError(getString(R.string.error_email_address));
            focusView = emailWrapper;
            cancel = true;
        }

        // check for a valid password
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            passwordWrapper.setError(getString(R.string.error_invalid_password));
            focusView = passwordWrapper;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        }else {
            fireBaseLogin();
        }

    }

    private boolean isPasswordValid(String password) {
        String PASSWORD_PATTERN = "^(?=.*\\d)(?=.*[a-zA-Z])(?!.*[\\W_\\x7B-\\xFF]).{8,15}$";
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    private boolean isEmailValid(String email) {
        String EMAIL_PATTERN = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) Objects.requireNonNull(getSystemService(Context.INPUT_METHOD_SERVICE))).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }

    }
}

