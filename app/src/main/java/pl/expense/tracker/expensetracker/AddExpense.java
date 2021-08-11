package pl.expense.tracker.expensetracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AddExpense extends AppCompatActivity {

    private ConstraintLayout parent;
    private TextInputLayout titleInput;
    private AutoCompleteTextView autoCompleteTitleInput;
    private TextInputLayout priceInput;
    private RadioButton isIncomeButton;
    private RadioButton isExpenseButton;
    private TextInputLayout dateInput;
    private TextInputLayout placeInput;
    private AutoCompleteTextView autoCompletePlaceInput;
    private TextInputLayout descriptionInput;
    private Button addButton;

    private final DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    private final ExpensesUtilities expensesUtilities = ExpensesUtilities.getInstance(this);

    private void setAdapterToAutoCompleteTextField(AutoCompleteTextView autoCompleteTextView, List<String> stringList) {
        autoCompleteTextView.setAdapter(new ArrayAdapter<String>(AddExpense.this, R.layout.dropdown_input_item, stringList));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        parent = findViewById(R.id.parent);
        titleInput = findViewById(R.id.titleInput);
        autoCompleteTitleInput = findViewById(R.id.autoCompleteTitleInput);
        priceInput = findViewById(R.id.priceInput);
        isIncomeButton = findViewById(R.id.incomeButton);
        isExpenseButton = findViewById(R.id.expenseButton);
        dateInput = findViewById(R.id.dateInput);
        placeInput = findViewById(R.id.placeInput);
        autoCompletePlaceInput = findViewById(R.id.autoCompletePlaceInput);
        descriptionInput = findViewById(R.id.descriptionInput);
        addButton = findViewById(R.id.formButton);

        dateInput.getEditText().setText(formatter.format(new Date()));

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkForm()) {
                    try {
                        expensesUtilities.addElement(makeNewExpenseFromForm());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    showSnackbar("Added new element");

                    Intent intent = new Intent(AddExpense.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    AddExpense.this.startActivity(intent);
                } else {
                    showSnackbar("Data is incorrect");
                }
            }
        });

        setAdapterToAutoCompleteTextField(autoCompleteTitleInput, expensesUtilities.getExpensesTitles());
        setAdapterToAutoCompleteTextField(autoCompletePlaceInput, expensesUtilities.getExpensesPlaces());
    }

    private boolean checkForm() {
        return !autoCompleteTitleInput.getText().toString().equals("") && !priceInput.getEditText().getText().toString().equals("") && (isIncomeButton.isChecked() || isExpenseButton.isChecked()) && !dateInput.getEditText().getText().toString().equals("");
    }

    private Expense makeNewExpenseFromForm() throws ParseException {
        float newPrice = Float.valueOf(priceInput.getEditText().getText().toString());
        newPrice = isExpenseButton.isChecked() ? -newPrice : newPrice;

        return new Expense(newPrice, autoCompleteTitleInput.getText().toString(), descriptionInput.getEditText().getText().toString(), formatter.parse(dateInput.getEditText().getText().toString()), isIncomeButton.isChecked(), autoCompletePlaceInput.getText().toString(), expensesUtilities.getNewItemId());
    }

    private void showSnackbar(String message) {
        Snackbar.make(parent, message, Snackbar.LENGTH_SHORT).show();
    }
}