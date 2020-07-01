package com.mysolutions.todo.data.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "todo_table")
public class TodoItem {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id")
    private int id;

    @NonNull
    @ColumnInfo(name = "title")
    private String title;

    @NonNull
    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "checked")
    private boolean checked;

    public TodoItem(@NonNull String title, @NonNull String description, boolean checked) {
        this.title = title;
        this.description = description;
        this.checked = checked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
