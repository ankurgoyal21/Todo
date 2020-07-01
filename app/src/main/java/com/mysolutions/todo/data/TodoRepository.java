package com.mysolutions.todo.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.mysolutions.todo.data.dao.TodoItemDao;
import com.mysolutions.todo.data.model.TodoItem;

import java.util.List;

public class TodoRepository {
    private TodoItemDao mTodoItemDao;
    private LiveData<List<TodoItem>> mAllTodoItems;

    public TodoRepository(Application application) {
        TodoRoomDatabase db = TodoRoomDatabase.getDatabase(application);
        mTodoItemDao = db.todoItemDao();
        mAllTodoItems = mTodoItemDao.getTodoList();
    }

    public LiveData<List<TodoItem>> getAllTodoItems() {
        return mAllTodoItems;
    }

    public void insert(TodoItem todoItem) {
        new insertAsyncTask(mTodoItemDao).execute(todoItem);
    }

    private static class insertAsyncTask extends AsyncTask<TodoItem, Void, Void> {

        private TodoItemDao mAsyncTaskDao;

        insertAsyncTask(TodoItemDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final TodoItem... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
