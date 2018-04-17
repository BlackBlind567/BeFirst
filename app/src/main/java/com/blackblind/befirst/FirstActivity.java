package com.blackblind.befirst;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class FirstActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

//        firebase instance
        firebaseAuth = FirebaseAuth.getInstance();

        LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.custom_toast,(ViewGroup) findViewById(R.id.custom_toast_container));

        final TextView text = layout.findViewById(R.id.text);


//        custom action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
        View view =getSupportActionBar().getCustomView();

//        navigation drawer
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

//        navgation item selection
        NavigationView navigationView = findViewById(R.id.nav_menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                if (id == R.id.nav_your_thought){
//                    Toast.makeText(FirstActivity.this, "This feature will be added soon", Toast.LENGTH_SHORT).show();
                    text.setText(R.string.your_thought_toast);
                    Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.BOTTOM, 10, 80
                    );
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();
//                    toast.setMargin(10,20);
                }else if (id == R.id.nav_publish) {
//                    Toast.makeText(FirstActivity.this, "You can see the all publisher thoughts", Toast.LENGTH_SHORT).show();
                    text.setText(R.string.users_thought);
                    Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.BOTTOM, 10, 80
                    );
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();
                    startActivity(new Intent(FirstActivity.this,MainActivity.class));
                }else if (id == R.id.nav_think_say) {
                    if (firebaseAuth.getCurrentUser() != null){
                        startActivity(new Intent(FirstActivity.this, ThinkActivity.class));
                    }else {
//                        Toast.makeText(FirstActivity.this, "Go in profile section and login", Toast.LENGTH_SHORT).show();
                        text.setText(R.string.login_first);
                        Toast toast = new Toast(getApplicationContext());
                        toast.setGravity(Gravity.BOTTOM, 10, 80
                        );
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.setView(layout);
                        toast.show();
                    }
                } else if (id == R.id.nav_feedback) {
                    startActivity(new Intent(FirstActivity.this,FeedbackActivity.class));
//                    Toast.makeText(FirstActivity.this, "We need your feedback", Toast.LENGTH_SHORT).show();
                    text.setText(R.string.feedback_toast);
                    Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.BOTTOM, 10, 80
                    );
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();

                }else if (id == R.id.nav_logout) {
                    if (firebaseAuth.getCurrentUser() != null){
                        firebaseAuth.signOut();
//                        Toast.makeText(FirstActivity.this, "Successfully SignOut", Toast.LENGTH_SHORT).show();
                        text.setText(R.string.Successfully_SignOut);
                        Toast toast = new Toast(getApplicationContext());
                        toast.setGravity(Gravity.BOTTOM, 10, 80
                        );
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.setView(layout);
                        toast.show();
                        startActivity(new Intent(FirstActivity.this,LoginActivity.class));
                    }else{
//                        Toast.makeText(FirstActivity.this, "You are not logged in", Toast.LENGTH_SHORT).show();
                        text.setText(R.string.not_logged_in);
                        Toast toast = new Toast(getApplicationContext());
                        toast.setGravity(Gravity.BOTTOM, 10, 80
                        );
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.setView(layout);
                        toast.show();
                    }

                }

                return true;
            }
        });



    }

//    context menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_profile) {
           if (firebaseAuth.getCurrentUser() != null){
               startActivity(new Intent(FirstActivity.this,ThinkActivity.class));
           }else {
               startActivity(new Intent(FirstActivity.this,ChoiceActivity.class));
           }
        }

        return actionBarDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);


    }
}

