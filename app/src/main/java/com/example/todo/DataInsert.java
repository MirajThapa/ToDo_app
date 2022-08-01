package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.todo.databinding.ActivityDataInsertBinding;

public class DataInsert extends AppCompatActivity {
    ActivityDataInsertBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDataInsertBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String type = getIntent().getStringExtra("type");
        if (type.equals("update")) {
            setTitle("update");
            binding.dataInsertTitle.setText(getIntent().getStringExtra("title"));
            binding.dataInsertDesc.setText(getIntent().getStringExtra("description"));
            int id = getIntent().getIntExtra("id",0);
            binding.addNote.setText("update note");

            binding.addNote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.putExtra("title", binding.dataInsertTitle.getText().toString());
                    intent.putExtra("description", binding.dataInsertDesc.getText().toString());
                    intent.putExtra("id",id);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });

        } else {

            setTitle("Add Mode");

            binding.addNote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.putExtra("title", binding.dataInsertTitle.getText().toString());
                    intent.putExtra("description", binding.dataInsertDesc.getText().toString());
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
        }
    }
}