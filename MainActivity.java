package com.example.s23498498;

/*  Student: Mogamat Taariq Orrie 23498498
	File Name: MainActivity.java
	Date: 04/08/2026
    About: This application allows a prospective homeowner to calculate
    whether they qualify for a home loan and displays the total
    loan amount and monthly premium. */

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

import android.content.Intent;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    TextView txtIDNumber;
    TextView txtSurname;
    TextView txtGender;
    TextView txtLoanAmount;
    TextView txtYears;
    TextView txtPhysicalAddress;
    TextView txtCellPhone;
    TextView txtMonthlyIncome;
    TextView txtTotal;
    TextView txtMonthlyPremium;

    Button btnCalculate;
    Button btnUpdate;
    Button btnExit;

    ImageView imgHomeLoan;


    double[] minIncome = {5000, 15001, 45001};
    double[] maxLoans = {100000, 1000000, 5000000};
    double[] interestRates = {0.115, 0.125, 0.15};
    String[] outcomes = {
            "Loan Application Successful",
            "Loan Application Unsuccessful"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        txtIDNumber = findViewById(R.id.txtIDNumber);
        txtSurname = findViewById(R.id.txtSurname);
        txtGender = findViewById(R.id.txtGender);
        txtLoanAmount = findViewById(R.id.txtLoanAmount);
        txtYears = findViewById(R.id.txtYears);
        txtPhysicalAddress = findViewById(R.id.txtPhysicalAddress);
        txtCellPhone = findViewById(R.id.txtCellPhone);
        txtMonthlyIncome = findViewById(R.id.txtMonthlyIncome);
        txtTotal = findViewById(R.id.txtTotal);
        txtMonthlyPremium = findViewById(R.id.txtMonthlyPremium);

        btnUpdate = findViewById(R.id.btnUpdate);
        btnExit = findViewById(R.id.btnExit);
        btnCalculate = findViewById(R.id.btnCalculate);

        btnUpdate.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Update.class);
            startActivity(intent);
        });


        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finishAffinity();

            }
        });

        btnCalculate.setOnClickListener(v -> {

            try {
                String incomeText = txtMonthlyIncome.getText().toString().replaceAll("[^\\d.]", "");
                String loanText = txtLoanAmount.getText().toString().replaceAll("[^\\d.]", "");
                String yearsText = txtYears.getText().toString().replaceAll("[^\\d]", "");

                if (incomeText.isEmpty() || loanText.isEmpty() || yearsText.isEmpty()) {
                    Toast.makeText(this, "Please load applicant information first.", Toast.LENGTH_SHORT).show();
                    return;
                }

                double income = Double.parseDouble(incomeText);
                double loanAmount = Double.parseDouble(loanText);
                int years = Integer.parseInt(yearsText);

                double maxLoan = 0;
                double interestRate = 0;


                for (int i = 0; i < minIncome.length; i++) {

                    if (i == minIncome.length - 1) {

                        if (income >= minIncome[i]) {

                            maxLoan = maxLoans[i];
                            interestRate = interestRates[i];
                        }

                    } else {

                        if (income >= minIncome[i] &&
                                income < minIncome[i + 1]) {

                            maxLoan = maxLoans[i];
                            interestRate = interestRates[i];
                        }

                    }
                }


                if (loanAmount <= maxLoan) {

                    double total = loanAmount * interestRate * years;
                    double premium = (total / years) / 12;

                    txtTotal.setText(outcomes[0]
                            + "\n R "
                            + String.format("%.2f", total));

                    txtMonthlyPremium.setText(" R "
                            + String.format("%.2f", premium));

                } else {

                    txtTotal.setText(outcomes[1]);
                    txtMonthlyPremium.setText("Maximum Loan Allowed: R "
                            + String.format("%.2f", maxLoan));

                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid numeric data in file. Please update information.", Toast.LENGTH_LONG).show();
            }

        });

        imgHomeLoan = findViewById(R.id.imgHomeLoan);

        loadData();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }
    private void loadData() {
        try {
            FileInputStream fis = openFileInput("data.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

            txtIDNumber.setText(cleanLabel(reader.readLine()));
            txtSurname.setText(cleanLabel(reader.readLine()));
            txtGender.setText(cleanLabel(reader.readLine()));
            txtLoanAmount.setText(cleanLabel(reader.readLine()));
            txtYears.setText(cleanLabel(reader.readLine()));
            txtPhysicalAddress.setText(cleanLabel(reader.readLine()));
            txtCellPhone.setText(cleanLabel(reader.readLine()));
            txtMonthlyIncome.setText(cleanLabel(reader.readLine()));

            reader.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String cleanLabel(String input) {
        if (input == null) return "";
        if (input.contains(":")) {
            return input.substring(input.indexOf(":") + 1).trim();
        }
        return input.trim();
    }
}