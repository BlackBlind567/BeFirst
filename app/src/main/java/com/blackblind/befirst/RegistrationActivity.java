package com.blackblind.befirst;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
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

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {

    private TextView textView;
    private Button button;
    private TextInputLayout nameWrapper;
    private TextInputLayout EmailWrapper;
    private TextInputLayout passWrapper;
    private TextInputLayout mobileWrapper;
    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextNumber;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

//        This is for custom Action Bar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
        View view =getSupportActionBar().getCustomView();

        progressDialog = new ProgressDialog(this);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        nameWrapper = findViewById(R.id.nameWrapper);
        EmailWrapper = findViewById(R.id.EmailWrapper);
        passWrapper = findViewById(R.id.passWrapper);
        mobileWrapper = findViewById(R.id.mobileWrapper);
        editTextName = findViewById(R.id.reg_name);
        editTextEmail = findViewById(R.id.reg_email);
        editTextPassword = findViewById(R.id.reg_password);
        editTextNumber = findViewById(R.id.reg_mobile);
        textView = (TextView) findViewById(R.id.tv_signin);
        button = (Button) findViewById(R.id.btn_signup);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideKeyboard();

                attemptRegistration();



            }
        });

    }

    private void fireBaseAuth() {

        String userEmail = editTextEmail.getText().toString();
        String userPassword = editTextPassword.getText().toString();

        progressDialog.setMessage("User Registering....");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    finish();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
                if (!task.isSuccessful()){
                    Toast.makeText(RegistrationActivity.this, "May be email already has been registered.Please make sure", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });

        fireStoreReg();
    }

    private void fireStoreReg() {

        String userName = editTextName.getText().toString();
        String userEmail = editTextEmail.getText().toString();
        String userPassword = editTextPassword.getText().toString();
        String userNumber = editTextNumber.getText().toString();

        Map<String , String> userMap = new HashMap<>();

        userMap.put("name" , userName);
        userMap.put("email" , userEmail);
        userMap.put("password" , userPassword);
        userMap.put("mobile" , userNumber);

        firebaseFirestore.collection("users").add(userMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                Toast.makeText(RegistrationActivity.this, "registration is successful", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                String error = e.getMessage();
                Toast.makeText(RegistrationActivity.this, "Registration is unsuccessful" + error, Toast.LENGTH_SHORT).show();

            }
        });




    }

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private void attemptRegistration() {

        // Reset errors.
        nameWrapper.setError(null);
        EmailWrapper.setError(null);
        passWrapper.setError(null);
        mobileWrapper.setError(null);

        String name = nameWrapper.getEditText().getText().toString();
        String email = EmailWrapper.getEditText().getText().toString();
        String password = passWrapper.getEditText().getText().toString();
        String Number = mobileWrapper.getEditText().getText().toString();

        boolean cancel = false;
        View focusView = null;

        // check for a valid name
        if (TextUtils.isEmpty(name)) {
            nameWrapper.setError(getString(R.string.error_required_field));
            focusView = nameWrapper;
            cancel = true;
        } else if (!isNameValid(name)) {
            nameWrapper.setError(getString(R.string.error_name));
            focusView = nameWrapper;
            cancel = true;
        }

        // check for a valid email
        if (TextUtils.isEmpty(email)) {
            EmailWrapper.setError(getString(R.string.error_required_field));
            focusView = EmailWrapper;
            cancel = true;
        } else if (!isEmailValid(email)) {
            EmailWrapper.setError(getString(R.string.error_email_address));
            focusView = EmailWrapper;
            cancel = true;
        }


        // check for a valid password
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            passWrapper.setError(getString(R.string.error_invalid_password));
            focusView = passWrapper;
            cancel = true;
        }

        // check for a valid mobile number
        if (TextUtils.isEmpty(Number)) {
            mobileWrapper.setError(getString(R.string.error_required_field));
            focusView = mobileWrapper;
            cancel = true;
        } else if (!isNumberValid(Number)) {
            mobileWrapper.setError(getString(R.string.error_mobile_number));
            focusView = mobileWrapper;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();

        }else{
            fireBaseAuth();
        }
    }

    private boolean isNumberValid(String Number) {
        return Number.length() > 9;
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 8;
    }

    private boolean isEmailValid(String email) {
        String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isNameValid(String name) {
        String NAME_PATTERN = "^[a-zA-Z ]+$";
        Pattern pattern = Pattern.compile(NAME_PATTERN);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

}
