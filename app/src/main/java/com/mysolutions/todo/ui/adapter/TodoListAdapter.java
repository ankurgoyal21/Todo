package com.mysolutions.todo.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mysolutions.todo.R;
import com.mysolutions.todo.data.model.TodoItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.TodoListViewHolder> {

    private final LayoutInflater mInflater;
    private List<TodoItem> mTodoItems;
    private Context mContext;

    public TodoListAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public TodoListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.todo_list_item, parent, false);
        return new TodoListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final @NonNull TodoListViewHolder holder, final int position) {
        final TodoItem todoItem = mTodoItems.get(position);
        holder.tvTitle.setText(todoItem.getTitle());
        holder.tvDescription.setText(todoItem.getDescription());
        holder.checkBox.setChecked(todoItem.isChecked());
        if (todoItem.isChecked()) {
            holder.cardView.setCardBackgroundColor(Color.GRAY);
            holder.cardView.setEnabled(false);
        } else {
            holder.cardView.setCardBackgroundColor(Color.WHITE);
            holder.cardView.setEnabled(true);
        }
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!todoItem.isChecked())
                {
                    todoItem.setChecked(true);
                    mTodoItems.remove(todoItem);
                    mTodoItems.add(todoItem);
                }
                notifyDataSetChanged();
            }
        });
    }

    public void setTodoItems(List<TodoItem> todoItems) {
        mTodoItems = todoItems;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mTodoItems != null && mTodoItems.size() > 0 ? mTodoItems.size() : 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class TodoListViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTitle;
        private final TextView tvDescription;
        private final CheckBox checkBox;
        private final CardView cardView;

        private TodoListViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            checkBox = itemView.findViewById(R.id.checkbox);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }

}
