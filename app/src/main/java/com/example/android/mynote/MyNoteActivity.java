package com.example.android.mynote;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.mynote.database.Note;
import com.example.android.mynote.database.NoteUtil;

import java.util.Random;

public class MyNoteActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private EditText mTitleET;
    private EditText mContentET;
    private Button mOkButton;
    private Button mDeleteButton;
    private Note mNote;


    private int[] colors = new int[]{
            R.color.green,R.color.blue,R.color.red,R.color.yellow,R.color.pink,R.color.purple
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_note);
        initData();
        initView();
    }

    private void initData(){
        mToolbar = (Toolbar) findViewById(R.id.note_toolbar);
        mTitleET = (EditText) findViewById(R.id.title_edit_view);
        mContentET = (EditText) findViewById(R.id.content_edit_view);
        mOkButton = (Button) findViewById(R.id.ok_button);
        mDeleteButton = (Button) findViewById(R.id.delete_button);

        Bundle bundle = getIntent().getBundleExtra("bundle");
        if(bundle != null){

            int id = bundle.getInt("id");
            int color = bundle.getInt("color");
            String title = bundle.getString("title");
            String content = bundle.getString("content");
            long time = bundle.getLong("time");

            mNote = new Note(id,title,content,time);
            mNote.setColor(color);
            mTitleET.setText(mNote.getTitle());
            mContentET.setText(mNote.getContent());

        }else {

            mNote = new Note();
            mTitleET.setText("");
            mContentET.setText("");
            mNote.setColor(colors[new Random().nextInt(6)]);

        }

    }

    private void initView(){
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.back_24);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getIntent().getBundleExtra("bundle") != null && mNote != null){

                    if(mTitleET.getText().length() != 0 && mContentET.getText().length() != 0){
                        mNote.setTitle(mTitleET.getText().toString());
                        mNote.setContent(mContentET.getText().toString());
                        mNote.setModifyTime(System.currentTimeMillis());

                        //保存到数据库
                        NoteUtil.getsNoteUtil(MyNoteActivity.this).updateNote(mNote);
                        finish();
                    }else {
                        Toast.makeText(MyNoteActivity.this,"不能保存空笔记",Toast.LENGTH_SHORT).show();
                    }

                }else {

                    if(mTitleET.getText().length() != 0 && mContentET.getText().length() != 0){

                        mNote.setTitle(mTitleET.getText().toString());
                        mNote.setContent(mContentET.getText().toString());
                        mNote.setModifyTime(System.currentTimeMillis());

                        //保存到数据库
                        NoteUtil.getsNoteUtil(MyNoteActivity.this).addNote(mNote);
                        finish();
                    }else {
                        Toast.makeText(MyNoteActivity.this,"不能保存空笔记",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getIntent().getBundleExtra("bundle") != null && mNote != null){
                    NoteUtil.getsNoteUtil(MyNoteActivity.this).deleteNote(mNote.getId());
                    finish();
                }else {
                    mTitleET.setText("");
                    mContentET.setText("");
                }
            }
        });


    }



}
