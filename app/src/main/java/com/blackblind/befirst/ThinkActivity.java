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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ThinkActivity extends AppCompatActivity {

    private EditText editTextquote;
    private EditText editTextauthor;
    private FirebaseFirestore firebaseFirestore;
    private ProgressDialog progressDialog;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_think);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
//        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        getSupportActionBar().setDisplayShowCustomEnabled(true);
//        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
//        View view = getSupportActionBar().getCustomView();

        progressDialog = new ProgressDialog(this);

        firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        TextInputLayout quoteWrapper = findViewById(R.id.quoteWrapper);
        TextInputLayout authorWrapper = findViewById(R.id.authorWrapper);
        editTextquote = findViewById(R.id.save_quote);
        editTextauthor = findViewById(R.id.save_author);
        Button button = findViewById(R.id.btn_continue);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideKeyboard();

                fireStoreData();
                progressDialog.setMessage("Submitting your thought");
                progressDialog.show();



            }
        });

    }

    private void fireStoreData() {

        String userQuote = editTextquote.getText().toString();
        String userAuthor = editTextauthor.getText().toString();



        Map<String, String> userMap = new HashMap<>();

        userMap.put("quote", userQuote);
        userMap.put("author", userAuthor);




        firebaseFirestore.collection("thoughts").add(userMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                Toast.makeText(ThinkActivity.this, "Successfully submit", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(ThinkActivity.this,MainActivity.class));
                progressDialog.dismiss();
                finish();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                String error = e.getMessage();
                Toast.makeText(ThinkActivity.this, "Something went wrong" + error, Toast.LENGTH_SHORT).show();

            }
        });
        progressDialog.dismiss();

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
