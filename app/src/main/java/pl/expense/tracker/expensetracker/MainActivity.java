package pl.expense.tracker.expensetracker;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    private ExpenseRecyclerViewAdapter adapter;
    private RecyclerView expensesRecycleView;
    private TextView allSumInWallet;
    private FloatingActionButton addButton;
    private ExpensesUtilities expensesUtilities;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        setContentView(R.layout.activity_main);
        expensesUtilities = ExpensesUtilities.getInstance(this);

        expensesRecycleView = findViewById(R.id.expensesRecyclerView);
        allSumInWallet = findViewById(R.id.allSumInWallet);
        addButton = findViewById(R.id.addButton);
        adapter = new ExpenseRecyclerViewAdapter(this, expensesUtilities.getExpenses(), ExpenseRecyclerViewAdapter.ALL_OPTIONS_VIEW);

        expensesRecycleView.setAdapter(adapter);
        expensesRecycleView.setLayoutManager(new LinearLayoutManager(this));

        allSumInWallet.setText(decimalFormat.format(expensesUtilities.sumAllElements()));

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddExpense.class);
                MainActivity.this.startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.favouriteListButton) {
            Intent intent = new Intent(MainActivity.this, FavouriteList.class);
            MainActivity.this.startActivity(intent);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}