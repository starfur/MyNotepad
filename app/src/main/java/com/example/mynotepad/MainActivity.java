package com.example.mynotepad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import java.util.ArrayList;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<NoteModel> arrayList;
    RecyclerView myRecyclerView;
    FloatingActionButton addButton;
    DatabaseHelper database_helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        addButton= (FloatingActionButton) findViewById(R.id.addB);
        database_helper = new DatabaseHelper (this);
        displayNotes();
        addButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showEnterNote();
            }
        });
    }

    public void displayNotes() {
        arrayList = new ArrayList<>(database_helper.getNotes());
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        myRecyclerView.setItemAnimator(new DefaultItemAnimator());
        NotesAdapter adapter = new NotesAdapter(getApplicationContext(), this, arrayList);
        myRecyclerView.setAdapter(adapter);
    }


    public void showEnterNote(){
        final EditText titleNote, descNote;
        Button submit;
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        dialog.setContentView(R.layout.enternote);
        params.copyFrom(dialog.getWindow().getAttributes());
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        titleNote = (EditText) dialog.findViewById(R.id.baslik);
        descNote = (EditText) dialog.findViewById(R.id.icerik);
        submit = (Button)dialog.findViewById(R.id.kaydet);
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (titleNote.getText().toString().isEmpty()) {
                    titleNote.setError("Please Enter Title");
                }else if(descNote.getText().toString().isEmpty()) {
                    descNote.setError("Please Enter Description");
                }else {
                    database_helper.addNotes(titleNote.getText().toString(), descNote.getText().toString());
                    dialog.cancel();
                    displayNotes();
                }
            }
        });

    }


}