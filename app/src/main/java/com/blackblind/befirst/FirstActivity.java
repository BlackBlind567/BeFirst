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
import android.view.MenuItem;
import android.view.View;
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

        firebaseAuth = FirebaseAuth.getInstance();

        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
        View view =getSupportActionBar().getCustomView();

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.open, R.string.close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = findViewById(R.id.nav_menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                if (id == R.id.nav_thought){
                    Toast.makeText(FirstActivity.this, "Thoughts of all publisher", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(FirstActivity.this,MainActivity.class));
                }else if (id == R.id.nav_publish) {
                    Toast.makeText(FirstActivity.this, "Please Register your account to publish your thought", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(FirstActivity.this,RegistrationActivity.class));
                }else if (id == R.id.nav_login) {
                    Toast.makeText(FirstActivity.this, "Make sure you have account", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(FirstActivity.this,LoginActivity.class));

                } else if (id == R.id.nav_logout) {
//
                    if (firebaseAuth.getCurrentUser() != null){
                        firebaseAuth.signOut();
                        Toast.makeText(FirstActivity.this, "Successfully SignOut", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(FirstActivity.this, "You are not logged in", Toast.LENGTH_SHORT).show();
                    }

                }

                return true;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);


    }
}

