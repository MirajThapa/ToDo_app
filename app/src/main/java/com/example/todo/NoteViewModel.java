package com.example.todo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private Note_Repository note_repository;
    private LiveData<List<Note>> noteList;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        note_repository = new Note_Repository(application);
        noteList = note_repository.readData();
    }

    public void insert(Note note){
        note_repository.insertData(note);
    }
    public void update(Note note){
        note_repository.updateData(note);
    }
    public void delete(Note note){
        note_repository.deleteData(note);
    }

    public LiveData<List<Note>> getAllNotes(){
        return noteList;
    }

}
