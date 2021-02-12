package pl.expense.tracker.expensetracker;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ExpenseRecyclerViewAdapter extends RecyclerView.Adapter<ExpenseRecyclerViewAdapter.ViewHolder> {

    private Context motherContext;
    private final DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    private ExpensesUtilities utilities;

    public ExpenseRecyclerViewAdapter(Context motherContext, ExpensesUtilities utilities) {
        this.motherContext = motherContext;
        this.utilities = utilities;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Expense expense = utilities.getElement(position);

        holder.expenseName.setText(expense.getTitle());
        holder.expensePrice.setText(String.valueOf(expense.getPrice()));
        holder.expensePrice.setTextColor(holder.colorOfExpensePrice(expense.isIncome()));
        holder.expenseDate.setText(formatter.format(expense.getDate()));
        holder.expensePlace.setText(expense.getPlace());
        holder.expenseDescription.setText(expense.getDescription());
        holder.expandedCardInfo.setVisibility(expense.isExpended() ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return utilities.getSize();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        private MaterialCardView parent;
        private TextView expenseName;
        private TextView expensePrice;
        private TextView expenseDate;
        private TextView expensePlace;
        private TextView expenseDescription;
        private ImageView arrowButton;
        private RelativeLayout expandedCardInfo;
        private Button removeButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parent = itemView.findViewById(R.id.parent);
            expenseName = itemView.findViewById(R.id.expenseName);
            expensePrice = itemView.findViewById(R.id.expensePrice);
            expenseDate = itemView.findViewById(R.id.expenseDate);
            expensePlace = itemView.findViewById(R.id.expensePlace);
            expenseDescription = itemView.findViewById(R.id.expenseDescription);
            arrowButton = itemView.findViewById(R.id.expendViewArrow);
            expandedCardInfo = itemView.findViewById(R.id.expandedCard);
            removeButton = itemView.findViewById(R.id.removeButton);

            arrowButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Expense expense = utilities.getElement(getAdapterPosition());
                    expense.setExpended(!expense.isExpended());
                    notifyItemChanged(getAdapterPosition());
                }
            });

            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(parent.getContext());
                    materialAlertDialogBuilder.setMessage("Do want to remove this element?");
                    materialAlertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            utilities.removeElement(getAdapterPosition());
                            notifyDataSetChanged();
                        }
                    });
                    materialAlertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    materialAlertDialogBuilder.show();
                }
            });
        }


        private int colorOfExpensePrice(boolean isIncome){
            return parent.getResources().getColor(isIncome? R.color.incomeColor : R.color.expenseColor);
        }

    }
}
