package com.example.buddyapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.buddyapplication.R;
import com.example.buddyapplication.database.DBHelper;
import com.example.buddyapplication.model.User;
import com.example.buddyapplication.utils.SessionManager;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private Button btnLogin;
    private TextView tvRegister;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Initialize DB and Helpers
        dbHelper = new DBHelper(this);
        SessionManager sessionManager = new SessionManager(this);


        if (sessionManager.isLoggedIn()) {
            startActivity(new Intent(this, DashboardActivity.class));
            finish();
        }

        // 3. Bind UI Components
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        tvRegister = findViewById(R.id.tv_register);

        // 4. Login Button Logic
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = etUsername.getText().toString().trim();
                String pass = etPassword.getText().toString().trim();

                if (validateInput(user, pass)) {
                    // Check Database
                    boolean isAllowed = dbHelper.checkLogin(user, pass);

                    if (isAllowed) {
                        Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();

                        sessionManager.createLoginSession(user);

                        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                        startActivity(intent);

                        finish();
                    } else {
                        // Login Failed
                        Toast.makeText(LoginActivity.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Quick method to add a user for testing purposes
                User newUser = new User("student", "1234");
                boolean success = dbHelper.registerUser(newUser); // Ensure you updated registerUser in DBHelper!

                if (success) {
                    Toast.makeText(LoginActivity.this, "User 'student' / '1234' registered!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Registration failed (User might exist)", Toast.LENGTH_SHORT).show();
                }

                // I cant access the app ngl
                // ALTERNATIVE: Go to Register Activity
                // Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                // startActivity(intent);
            }
        });
    }

    // Helper method to validate input
    private boolean validateInput(String user, String pass) {
        if (TextUtils.isEmpty(user)) {
            etUsername.setError("Username is required");
            return false;
        }
        if (TextUtils.isEmpty(pass)) {
            etPassword.setError("Password is required");
            return false;
        }
        return true;
    }

}