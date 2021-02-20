package pl.expense.tracker.expensetracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddExpense extends AppCompatActivity {

    private ConstraintLayout parent;
    private EditText titleInput;
    private EditText priceInput;
    private RadioButton isIncomeButton;
    private RadioButton isExpenseButton;
    private EditText dateInput;
    private EditText placeInput;
    private EditText descriptionInput;
    private Button addButton;

    private final DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    private ExpensesUtilities expensesUtilities = ExpensesUtilities.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        parent = findViewById(R.id.parent);
        titleInput = findViewById(R.id.titleInput);
        priceInput = findViewById(R.id.priceInput);
        isIncomeButton = findViewById(R.id.incomeButton);
        isExpenseButton = findViewById(R.id.expenseButton);
        dateInput = findViewById(R.id.dateInput);
        placeInput = findViewById(R.id.placeInput);
        descriptionInput = findViewById(R.id.descriptionInput);
        addButton = findViewById(R.id.formButton);

        dateInput.setText(formatter.format(new Date()));

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkForm()){
                    expensesUtilities.addElement(makeNewExpenseFromForm());
                    showSnackbar("Added new element");

                    Intent intent = new Intent(AddExpense.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    AddExpense.this.startActivity(intent);
                } else {
                    showSnackbar("Data is incorrect");
                }
            }
        });

    }

    private boolean checkForm(){
        return !titleInput.getText().toString().equals("") && !priceInput.getText().toString().equals("") && (isIncomeButton.isChecked() || isExpenseButton.isChecked()) && !dateInput.getText().toString().equals("");
    }

    private Expense makeNewExpenseFromForm(){
        float newPrice = Float.valueOf(priceInput.getText().toString());
        newPrice = isExpenseButton.isChecked() ? -newPrice : newPrice;

        return new Expense(newPrice, titleInput.getText().toString(), descriptionInput.getText().toString(), new Date(dateInput.getText().toString()), isIncomeButton.isChecked(), placeInput.getText().toString(), expensesUtilities.getNewItemId());
    }

    private void showSnackbar(String message){
        Snackbar.make(parent, message, Snackbar.LENGTH_SHORT).show();
    }
}