package pl.expense.tracker.expensetracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class FavouriteList extends AppCompatActivity {

    private RecyclerView favouriteRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_list);

        favouriteRecyclerView = findViewById(R.id.favouriteRecyclerView);
        ExpenseRecyclerViewAdapter adapter = new ExpenseRecyclerViewAdapter(this, ExpensesUtilities.getFavouriteExpenses());

        favouriteRecyclerView.setAdapter(adapter);
        favouriteRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}