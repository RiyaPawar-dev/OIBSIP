package com.example.to_dolist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {

    ArrayList<String> tasks;
    OnDeleteClick listener;

    public interface OnDeleteClick {
        void onDelete(int position);
    }

    public TodoAdapter(ArrayList<String> tasks, OnDeleteClick listener) {
        this.tasks = tasks;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_todo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvTask.setText(tasks.get(position));

        holder.btnDelete.setOnClickListener(v ->
                listener.onDelete(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTask;
        ImageButton btnDelete;

        ViewHolder(View itemView) {
            super(itemView);
            tvTask = itemView.findViewById(R.id.tvTask);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
