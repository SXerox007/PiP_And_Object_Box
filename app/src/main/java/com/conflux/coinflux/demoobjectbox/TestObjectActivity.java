package com.conflux.coinflux.demoobjectbox;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Date;
import java.util.List;

import io.objectbox.Box;

/**
 * Developer: SUMIT_THAKUR
 * Dated: 29/06/17.
 */
public class TestObjectActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Box<Note> boxStore = ((MyApplication) getApplication()).getBoxStore().boxFor(Note.class);
        Note note = new Note(0, "alolo", "sumit is Great.", new Date());
        //1. to add
        boxStore.put(note);
        Log.e("Error", "Inserted new note, Info: \n " + note.getId()
                + "\n" + note.getText()
                + "\n" + note.getComment()
                + "\n" + note.getDate());

        //2. to remove
        // boxStore.remove(note);
        //3. to remove all
        //boxStore.removeAll();

        //4. to get all the save
        List<Note> test = boxStore.getAll();
        Log.e("Error", "Size" + String.valueOf(test.size()));

        for (int i = 0; i < test.size(); i++) {
            Log.e("Error", "find something:- " + test.get(i).getText());
        }

        //5. for find  Query
        // something in the list of Notes
        //simple Query
        List<Note> notes = boxStore.query().equal(Note_.text, "abcdef").build().find();
        for (int i = 0; i < notes.size(); i++) {
            Log.e("Error", "find Sumit:- " + notes.get(i).getText());
        }
    }
}