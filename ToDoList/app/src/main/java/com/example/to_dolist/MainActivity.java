package com.example.to_dolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText etTask;
    MaterialButton btnAdd, btnLogout;
    RecyclerView recyclerTasks;
    TextView tvActiveCount;
    View emptyLayout;

    DBHelper db;
    TodoAdapter adapter;
    ArrayList<String> tasks;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        session = new SessionManager(this);

        // 🔒 Protect dashboard
        if (!session.isLoggedIn()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_main);

        // Init views
        etTask = findViewById(R.id.etTask);
        btnAdd = findViewById(R.id.btnAdd);
        btnLogout = findViewById(R.id.btnLogout);
        recyclerTasks = findViewById(R.id.recyclerTasks);
        tvActiveCount = findViewById(R.id.tvActiveCount);
        emptyLayout = findViewById(R.id.emptyLayout);

        db = new DBHelper(this);
        tasks = db.getTasks();

        // RecyclerView setup
        adapter = new TodoAdapter(tasks, this::deleteTask);
        recyclerTasks.setLayoutManager(new LinearLayoutManager(this));
        recyclerTasks.setItemAnimator(new DefaultItemAnimator());
        recyclerTasks.setAdapter(adapter);

        updateUI();

        // ➕ Add task
        btnAdd.setOnClickListener(v -> {
            String task = etTask.getText().toString().trim();

            if (task.isEmpty()) {
                Toast.makeText(this, "Enter a task", Toast.LENGTH_SHORT).show();
                return;
            }

            db.insertTask(task);
            tasks.add(0, task); // newest first (like React app)
            adapter.notifyItemInserted(0);
            recyclerTasks.scrollToPosition(0);
            etTask.setText("");

            updateUI();
        });

        // 🚪 Logout
        btnLogout.setOnClickListener(v -> {
            session.logout();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    // ❌ Delete task
    private void deleteTask(int position) {
        String task = tasks.get(position);
        db.deleteTask(task);
        tasks.remove(position);
        adapter.notifyItemRemoved(position);
        updateUI();
    }

    // 🔄 Update empty state & counter
    private void updateUI() {
        int count = tasks.size();
        tvActiveCount.setText(count + " active " + (count == 1 ? "task" : "tasks"));

        if (count == 0) {
            emptyLayout.setVisibility(View.VISIBLE);
            recyclerTasks.setVisibility(View.GONE);
        } else {
            emptyLayout.setVisibility(View.GONE);
            recyclerTasks.setVisibility(View.VISIBLE);
        }
    }
}
