package com.example.buddyapplication.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.buddyapplication.R;
import com.example.buddyapplication.database.DBHelper;
import com.example.buddyapplication.model.Buddy;

import java.util.Calendar;

public class AddEditBuddyActivity extends AppCompatActivity {

    // UI Components
    private EditText etName, etPhone, etEmail, etDob;
    private RadioGroup rgGender;
    private RadioButton rbMale, rbFemale;
    private Button btnSave;

    private DBHelper dbHelper;
    private Buddy buddyToEdit;
    private boolean isEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_buddy);

        dbHelper = new DBHelper(this);

        etName = findViewById(R.id.et_name);
        etPhone = findViewById(R.id.et_phone);
        etEmail = findViewById(R.id.et_email);
        etDob = findViewById(R.id.et_dob);
        rgGender = findViewById(R.id.rg_gender);
        rbMale = findViewById(R.id.rb_male);
        rbFemale = findViewById(R.id.rb_female);
        btnSave = findViewById(R.id.btn_save);

        etDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        if (getIntent().hasExtra("EXTRA_BUDDY")) {
            isEditMode = true;
            buddyToEdit = (Buddy) getIntent().getSerializableExtra("EXTRA_BUDDY");
            populateFields(buddyToEdit);
            btnSave.setText("Update Friend");
            setTitle("Edit Details");
        } else {
            setTitle("Add New Friend");
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBuddy();
            }
        });
    }

    private void showDatePicker() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        etDob.setText(selectedDate);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    private void populateFields(Buddy buddy) {
        etName.setText(buddy.getName());
        etPhone.setText(buddy.getPhone());
        etEmail.setText(buddy.getEmail());
        etDob.setText(buddy.getDob());

        if (buddy.getGender() != null && buddy.getGender().equalsIgnoreCase("Male")) {
            rbMale.setChecked(true);
        } else if (buddy.getGender() != null && buddy.getGender().equalsIgnoreCase("Female")) {
            rbFemale.setChecked(true);
        }
    }

    private void saveBuddy() {
        // A. Validate Inputs
        String name = etName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String dob = etDob.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Name and Phone are required", Toast.LENGTH_SHORT).show();
            return;
        }

        String gender = "Other";
        if (rbMale.isChecked()) gender = "Male";
        else if (rbFemale.isChecked()) gender = "Female";

        if (phone.startsWith("0")) {
            phone = "60" + phone.substring(1);
        }

        if (isEditMode) {
            // UPDATE EXISTING
            buddyToEdit.setName(name);
            buddyToEdit.setGender(gender);
            buddyToEdit.setDob(dob);
            buddyToEdit.setPhone(phone);
            buddyToEdit.setEmail(email);

            dbHelper.updateBuddy(buddyToEdit);
            Toast.makeText(this, "Friend Updated!", Toast.LENGTH_SHORT).show();
        } else {
            Buddy newBuddy = new Buddy(name, gender, dob, phone, email);
            dbHelper.addBuddy(newBuddy);
            Toast.makeText(this, "Friend Added!", Toast.LENGTH_SHORT).show();
        }

        finish();
    }
}