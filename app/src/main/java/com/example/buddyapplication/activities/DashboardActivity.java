package com.example.buddyapplication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.buddyapplication.R;
import com.example.buddyapplication.adapter.BuddyAdapter;
import com.example.buddyapplication.database.DBHelper;
import com.example.buddyapplication.model.Buddy;
import com.example.buddyapplication.utils.SessionManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton fabAdd;
    EditText etSearch;
    ImageView btnProfileMenu;
    TextView tvGreeting;
    BuddyAdapter adapter;
    List<Buddy> buddyList;
    DBHelper dbHelper;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin(); // Redirects if not logged in

        dbHelper = new DBHelper(this);
        recyclerView = findViewById(R.id.rv_buddy_list);
        fabAdd = findViewById(R.id.fab_add);
        etSearch = findViewById(R.id.et_search);
        btnProfileMenu = findViewById(R.id.btn_profile_menu);
        tvGreeting = findViewById(R.id.tv_greeting);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        buddyList = new ArrayList<>();

        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, AddEditBuddyActivity.class);
            startActivity(intent);
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        btnProfileMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProfileMenu(v);
            }
        });
    }

    private void showProfileMenu(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.getMenuInflater().inflate(R.menu.dashboard_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.action_logout) {
                    sessionManager.logoutUser();
                    finish();
                    return true;
                }
                else if (id == R.id.action_report) {
                    Intent intent = new Intent(DashboardActivity.this, ReportsActivity.class);
                    startActivity(intent);
                    return true;
                }
                return true;
            }
        });

        popup.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        String currentUser = sessionManager.getUsername(); // Get current user
        buddyList = dbHelper.getAllBuddies(sessionManager.getUsername());   // Pass user to DB
        adapter = new BuddyAdapter(this, buddyList, dbHelper);
        recyclerView.setAdapter(adapter);
    }

    private void filter(String text) {
        String currentUser = sessionManager.getUsername();
        List<Buddy> filteredList = dbHelper.searchBuddy(text, sessionManager.getUsername()); // Pass user to DB
        adapter.updateList(filteredList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboard_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            sessionManager.logoutUser();
            finish();
            return true;
        }
        else if (id == R.id.action_report) {
            Intent intent = new Intent(DashboardActivity.this, ReportsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}