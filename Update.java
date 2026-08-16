package com.example.s23498498;

/*  Student: Mogamat Taariq Orrie 23498498
	File Name: Update.java
	Date: 04/08/2026
    About: This application allows a prospective homeowner to calculate
    whether they qualify for a home loan and displays the total
    loan amount and monthly premium. */

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Update extends AppCompatActivity {

    EditText edtIDNumber;
    EditText edtSurname;
    EditText edtLoanAmount;
    EditText edtYears;
    EditText edtPhysicalAddress;
    EditText edtCellPhone;
    EditText edtMonthlyIncome;

    RadioGroup rgGender;
    RadioButton rbMale;
    RadioButton rbFemale;

    Button btnSave;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update);

        edtIDNumber = findViewById(R.id.edtIDNumber);
        edtSurname = findViewById(R.id.edtSurname);
        edtLoanAmount = findViewById(R.id.edtLoanAmount);
        edtYears = findViewById(R.id.edtYears);
        edtPhysicalAddress = findViewById(R.id.edtPhysicalAddress);
        edtCellPhone = findViewById(R.id.edtCellPhone);
        edtMonthlyIncome = findViewById(R.id.edtMonthlyIncome);

        rgGender = findViewById(R.id.rgGender);
        rbMale = findViewById(R.id.rbMale);
        rbFemale = findViewById(R.id.rbFemale);

        btnSave = findViewById(R.id.btnSave);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            finish();
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String surname = edtSurname.getText().toString();

                String gender = "";
                if (rbMale.isChecked()) {
                    gender = "Male";
                } else if (rbFemale.isChecked()) {
                    gender = "Female";
                }

                String idNumber = edtIDNumber.getText().toString();
                String loanAmount = edtLoanAmount.getText().toString();
                String years = edtYears.getText().toString();
                String physicalAddress = edtPhysicalAddress.getText().toString();
                String cellPhone = edtCellPhone.getText().toString();
                String monthlyIncome = edtMonthlyIncome.getText().toString();

                String data = idNumber + "\n"
                        + surname + "\n"
                        + gender + "\n"
                        + loanAmount + "\n"
                        + years + "\n"
                        + physicalAddress + "\n"
                        + cellPhone + "\n"
                        + monthlyIncome + "\n";

                try {
                    FileOutputStream fos = openFileOutput("data.txt", MODE_PRIVATE);
                    fos.write(data.getBytes());
                    fos.close();

                    Toast.makeText(Update.this, "Data saved successfully", Toast.LENGTH_SHORT).show();

                    finish();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}