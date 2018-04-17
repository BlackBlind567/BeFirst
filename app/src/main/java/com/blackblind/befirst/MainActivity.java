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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;



public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private static final String TAG = "FireLog";
    private List<quote> quoteList;
    private quoteListAdapter quoteListAdapter;



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();

        quoteList = new ArrayList<>();
        quoteListAdapter = new quoteListAdapter(quoteList);

        RecyclerView recyclerView = findViewById(R.id.rw_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(quoteListAdapter);

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.collection("UserThoughts").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                if (e != null){

                    Log.d(TAG , "Error : " + e.getMessage());
                }

                for (DocumentChange documentChange: documentSnapshots.getDocumentChanges()){

                    switch (documentChange.getType()) {
                        case ADDED:
                            quote quotes = documentChange.getDocument().toObject(quote.class);
                            quoteList.add(quotes);
                            quoteListAdapter.notifyDataSetChanged();
                            break;
                        case REMOVED:
                            quote quotes1 = documentChange.getDocument().toObject(quote.class);
                            quoteList.remove(quotes1);
                            quoteListAdapter.notifyDataSetChanged();
                            break;
                        case MODIFIED:
                            quote quotes2 = documentChange.getDocument().toObject(quote.class);
                            quoteList.getClass();
                            quoteListAdapter.notifyDataSetChanged();
                            break;
                    }

                }

            }
        });


    }
}

