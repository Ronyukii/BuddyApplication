package com.example.buddyapplication.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.buddyapplication.R;
import com.example.buddyapplication.database.DBHelper;
import com.example.buddyapplication.model.User;

public class RegisterActivity extends AppCompatActivity {

    private EditText etUsername, etPassword, etConfirmPass;
    private Button btnRegister;
    private TextView tvLogin;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbHelper = new DBHelper(this);

        etUsername = findViewById(R.id.et_reg_username);
        etPassword = findViewById(R.id.et_reg_password);
        etConfirmPass = findViewById(R.id.et_reg_confirm_password);
        btnRegister = findViewById(R.id.btn_register_submit);
        tvLogin = findViewById(R.id.tv_back_to_login);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = etUsername.getText().toString().trim();
                String pass = etPassword.getText().toString().trim();
                String confirm = etConfirmPass.getText().toString().trim();

                if (validateInput(user, pass, confirm)) {
                    // Create User Object
                    User newUser = new User(user, pass);

                    // Save to DB
                    boolean success = dbHelper.registerUser(newUser);

                    if (success) {
                        Toast.makeText(RegisterActivity.this, "Account Created! Please Login.", Toast.LENGTH_SHORT).show();
                        finish(); // Close registration and go back to login
                    } else {
                        Toast.makeText(RegisterActivity.this, "Registration Failed. Username might exist.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private boolean validateInput(String user, String pass, String confirm) {
        if (TextUtils.isEmpty(user)) {
            etUsername.setError("Username required");
            return false;
        }
        if (TextUtils.isEmpty(pass)) {
            etPassword.setError("Password required");
            return false;
        }
        if (!pass.equals(confirm)) {
            etConfirmPass.setError("Passwords do not match");
            return false;
        }
        return true;
    }
}