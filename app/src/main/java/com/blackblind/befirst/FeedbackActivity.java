package com.blackblind.befirst;

import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.PrivateKey;

public class FeedbackActivity extends AppCompatActivity {

    private EditText editTextSubject;
    private EditText editTextEmail;
    private EditText editTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        TextInputLayout subjectWrapper = findViewById(R.id.subjectWrapper);
        TextInputLayout emailWrapper = findViewById(R.id.fedEmailWrapper);
        TextInputLayout messageWrraper = findViewById(R.id.messageWrapper);
        editTextSubject = findViewById(R.id.fed_subject);
        editTextEmail = findViewById(R.id.fed_email);
        editTextMessage = findViewById(R.id.fed_message);
        Button buttonMail = findViewById(R.id.fed_sendMail);


        buttonMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SendMail();
                
            }
        });


    }

    private void SendMail() {

        String toEmailAddress = editTextEmail.getText().toString().trim();
//        we can store data of user in feedback
        String MailMessage = editTextMessage.getText().toString().trim();
        String MailSubject = editTextSubject.getText().toString().trim();
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        Log.i("Send email", "");
        String[] TO = {"shantanujaiswal20@gmail.com"};
        String[] CC = {"developerdev90@gmail.com"};

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, MailSubject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, MailMessage);
        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending email", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(FeedbackActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
//    // This is just a sample script. Paste your real code (javascript or HTML) here.
//if ('this_is' == /an_example/) {
//        of_beautifier();
//    } else {
//        var a = b ? (c % d) : e[f];
//
//    }
}
