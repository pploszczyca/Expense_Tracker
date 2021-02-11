package pl.expense.tracker.expensetracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class ExpenseRecyclerViewAdapter extends RecyclerView.Adapter<ExpenseRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Expense> expenses = new ArrayList<>();
    private Context motherContext;

    public ExpenseRecyclerViewAdapter(Context motherContext) {
        this.motherContext = motherContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.expenseName.setText(expenses.get(position).getTitle());
        holder.expensePrice.setText(String.valueOf(expenses.get(position).getPrice()));
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }


    public void setExpenses(ArrayList<Expense> expenses) {
        this.expenses = expenses;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private MaterialCardView parent;
        private TextView expenseName;
        private TextView expensePrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parent = itemView.findViewById(R.id.parent);
            expenseName = itemView.findViewById(R.id.expenseName);
            expensePrice = itemView.findViewById(R.id.expensePrice);
        }
    }
}
