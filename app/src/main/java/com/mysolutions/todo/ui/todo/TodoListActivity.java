package com.mysolutions.todo.ui.todo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.mysolutions.todo.R;
import com.mysolutions.todo.data.model.TodoItem;
import com.mysolutions.todo.ui.adapter.TodoListAdapter;

import java.util.ArrayList;
import java.util.List;

public class TodoListActivity extends AppCompatActivity {

    public static final int NEW_TODOITEM_ACTIVITY_REQUEST_CODE = 1;
    private TodoViewModel mTodoViewModel;
    private RecyclerView recyclerView;
    private EditText etSearch;
    private List<TodoItem> mTodoItems;
    private TodoListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);
        recyclerView = findViewById(R.id.recyclerView);
        etSearch = findViewById(R.id.etTxtSearch);
        adapter = new TodoListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mTodoViewModel = new ViewModelProvider(this).get(TodoViewModel.class);
        recyclerView.setVisibility(View.GONE);
        etSearch.setVisibility(View.GONE);
        mTodoViewModel.getAllTodoItems().observe(this, new Observer<List<TodoItem>>() {
            @Override
            public void onChanged(@Nullable final List<TodoItem> todoItems) {
                if (todoItems != null && todoItems.size() > 0) {
                    mTodoItems = todoItems;
                    recyclerView.setVisibility(View.VISIBLE);
                    etSearch.setVisibility(View.VISIBLE);
                    adapter.setTodoItems(todoItems);
                }
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.todo, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            Intent intent = new Intent(TodoListActivity.this, AddTodoItemActivity.class);
            startActivityForResult(intent, NEW_TODOITEM_ACTIVITY_REQUEST_CODE);
        }
        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_TODOITEM_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            etSearch.setText("");
            TodoItem todoItem = new TodoItem(data.getStringExtra(AddTodoItemActivity.EXTRA_TODOTITLE), data.getStringExtra(AddTodoItemActivity.EXTRA_DESCRIPTION), false);
            mTodoViewModel.insert(todoItem);
        }
    }

    private void filter(String text) {
        if (mTodoItems != null && mTodoItems.size() > 0) {
            List<TodoItem> tempList = new ArrayList();
            for (TodoItem todoItem : mTodoItems) {
                if (todoItem.getTitle().toLowerCase().contains(text.toLowerCase())) {
                    tempList.add(todoItem);
                }
            }
            if (adapter != null)
                adapter.setTodoItems(tempList);
        }
    }

}
