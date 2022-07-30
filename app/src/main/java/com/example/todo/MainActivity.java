package com.example.todo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.todo.databinding.ActivityDataInsertBinding;
import com.example.todo.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding activityMainBinding;
    private NoteViewModel noteViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        noteViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory.
                getInstance(this.getApplication())).get(NoteViewModel.class);
        activityMainBinding.mainAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,DataInsert.class);
                startActivityForResult(intent,1);
            }
        });

        activityMainBinding.mainRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        activityMainBinding.mainRecyclerView.setHasFixedSize(true);
        RVAdapter rvAdapter= new RVAdapter();
        activityMainBinding.mainRecyclerView.setAdapter(rvAdapter);

        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                rvAdapter.submitList(notes);
                Log.i("===", "onChanged: ");
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                noteViewModel.delete(rvAdapter.getNote(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(activityMainBinding.mainRecyclerView);


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                if (direction==ItemTouchHelper.RIGHT){
                    Toast.makeText(MainActivity.this,"update",Toast.LENGTH_SHORT).show();
                }
            }
        }).attachToRecyclerView(activityMainBinding.mainRecyclerView);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            String title = data.getStringExtra("title");
            String desc = data.getStringExtra("description");

            Note note = new Note(title,desc);
            noteViewModel.insert(note);
            Toast.makeText(MainActivity.this,"Added Note",Toast.LENGTH_SHORT).show();

        }
    }
}