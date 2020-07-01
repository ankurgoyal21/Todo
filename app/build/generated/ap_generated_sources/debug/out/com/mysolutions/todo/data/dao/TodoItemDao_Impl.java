package com.mysolutions.todo.data.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.lifecycle.ComputableLiveData;
import androidx.lifecycle.LiveData;
import androidx.room.EntityInsertionAdapter;
import androidx.room.InvalidationTracker.Observer;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.mysolutions.todo.data.model.TodoItem;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unchecked")
public final class TodoItemDao_Impl implements TodoItemDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfTodoItem;

  public TodoItemDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTodoItem = new EntityInsertionAdapter<TodoItem>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR IGNORE INTO `todo_table`(`Id`,`title`,`description`,`checked`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, TodoItem value) {
        stmt.bindLong(1, value.getId());
        if (value.getTitle() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTitle());
        }
        if (value.getDescription() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getDescription());
        }
        final int _tmp;
        _tmp = value.isChecked() ? 1 : 0;
        stmt.bindLong(4, _tmp);
      }
    };
  }

  @Override
  public void insert(TodoItem todoItem) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfTodoItem.insert(todoItem);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<TodoItem>> getTodoList() {
    final String _sql = "SELECT * from todo_table";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<TodoItem>>(__db.getQueryExecutor()) {
      private Observer _observer;

      @Override
      protected List<TodoItem> compute() {
        if (_observer == null) {
          _observer = new Observer("todo_table") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("Id");
          final int _cursorIndexOfTitle = _cursor.getColumnIndexOrThrow("title");
          final int _cursorIndexOfDescription = _cursor.getColumnIndexOrThrow("description");
          final int _cursorIndexOfChecked = _cursor.getColumnIndexOrThrow("checked");
          final List<TodoItem> _result = new ArrayList<TodoItem>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final TodoItem _item;
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final boolean _tmpChecked;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfChecked);
            _tmpChecked = _tmp != 0;
            _item = new TodoItem(_tmpTitle,_tmpDescription,_tmpChecked);
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    }.getLiveData();
  }
}
