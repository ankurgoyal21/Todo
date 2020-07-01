package com.mysolutions.todo.ui.todo;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mysolutions.todo.data.TodoRepository;
import com.mysolutions.todo.data.model.TodoItem;

import java.util.List;

public class TodoViewModel extends AndroidViewModel {

    private TodoRepository mRepository;
    private LiveData<List<TodoItem>> mAllTodoItems;

    public TodoViewModel(Application application) {
        super(application);
        mRepository = new TodoRepository(application);
        mAllTodoItems = mRepository.getAllTodoItems();
    }

    LiveData<List<TodoItem>> getAllTodoItems() {
        return mAllTodoItems;
    }

    public void insert(TodoItem todoItem) {
        mRepository.insert(todoItem);
    }
}
