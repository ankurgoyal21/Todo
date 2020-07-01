package com.mysolutions.todo.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.mysolutions.todo.data.model.TodoItem;

import java.util.List;

@Dao
public interface TodoItemDao {

    @Query("SELECT * from todo_table")
    LiveData<List<TodoItem>> getTodoList();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(TodoItem todoItem);
}
