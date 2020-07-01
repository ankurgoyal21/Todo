package com.mysolutions.todo.ui.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mysolutions.todo.R;

public class AddTodoItemActivity extends AppCompatActivity {

    public static final String EXTRA_TODOTITLE = "ccom.mysolutions.todo.ui.todo.TITLE";
    public static final String EXTRA_DESCRIPTION = "ccom.mysolutions.todo.ui.todo.DESCRIPTION";
    private EditText etTitle;
    private EditText etDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo_item);
        getSupportActionBar().setTitle(R.string.new_todo_item);
        etTitle = findViewById(R.id.etTxtTitle);
        etDescription = findViewById(R.id.etTxtDescription);
        final Button btnCancel = findViewById(R.id.btnCancel);
        final Button btnDone = findViewById(R.id.btnDone);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent todoIntent = new Intent();
                if (TextUtils.isEmpty(etTitle.getText()) || TextUtils.isEmpty(etDescription.getText())) {
                    setResult(RESULT_CANCELED, todoIntent);
                } else {
                    todoIntent.putExtra(EXTRA_TODOTITLE, etTitle.getText().toString());
                    todoIntent.putExtra(EXTRA_DESCRIPTION, etDescription.getText().toString());
                    setResult(RESULT_OK, todoIntent);
                }
                finish();
            }
        });
    }
}
