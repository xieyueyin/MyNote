package com.example.android.mynote;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.android.mynote.database.Note;
import com.example.android.mynote.database.NoteUtil;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnLongItemClickListener,OnColorChangeListener {

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private MaterialSearchView mSearchView;
    private FloatingActionButton mFloatingActionButton;
    private List<Note> mNoteList;
    private Note mNote;
    private MyNoteAdapter mAdapter;

    private Button mTimeOrderButton;
    private Button mColorOrderButton;
    private Button mTitleOrderButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();



    }


    private void initData(){
        mNoteList = NoteUtil.getsNoteUtil(this).getNotes();
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        Log.i("test","current size "+ mNoteList.size());
        mAdapter = new MyNoteAdapter(this,mNoteList,this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        registerForContextMenu(mRecyclerView);


    }

    private void initView(){

        setSupportActionBar(mToolbar);
       // mToolbar.setLogo(getResources().getDrawable(R.drawable.app_notes));

        initSearchView();

    }

    /**
     * 初始化搜索框
     */
    private void initSearchView(){

        mTimeOrderButton = (Button) findViewById(R.id.time_button);
        mColorOrderButton = (Button) findViewById(R.id.color_button);
        mTitleOrderButton = (Button) findViewById(R.id.title_button);

        mSearchView = (MaterialSearchView) findViewById(R.id.search_view);
        mSearchView.setVoiceSearch(false);
        mSearchView.setCursorDrawable(R.drawable.custom_cursor);
        mSearchView.setEllipsize(true);

        mSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<Note> queryList = NoteUtil.getsNoteUtil(MainActivity.this).search(newText);
                mAdapter.setNoteList(queryList);
                mAdapter.notifyDataSetChanged();
                return true;
            }
        });

        mSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {
                updateUI();
            }
        });

        mColorOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUI("modifytime");
            }
        });
        mTimeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUI("color");
            }
        });
        mTitleOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUI("title");
            }
        });
    }




    @Override
    protected void onResume() {
        super.onResume();
        Log.i("test","onResume");
        updateUI();
    }

    private void updateUI(){
        List<Note> noteList = NoteUtil.getsNoteUtil(this).getNotes();
        mNoteList = noteList;
        if(mAdapter == null){
            mAdapter = new MyNoteAdapter(this,noteList,this);
            mRecyclerView.setAdapter(mAdapter);
        }else {
            if(mAdapter.getItemCount() != noteList.size()){
                mAdapter = new MyNoteAdapter(this,noteList,this);
                mRecyclerView.setAdapter(mAdapter);
            }else {
                mAdapter.setNoteList(noteList);
                mAdapter.notifyDataSetChanged();
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    private void updateUI(String order){
        List<Note> noteList = NoteUtil.getsNoteUtil(this).orderBy(order);
        mNoteList = noteList;
        if(mAdapter == null){
            mAdapter = new MyNoteAdapter(this,noteList,this);
            mRecyclerView.setAdapter(mAdapter);
        }else {
            if(mAdapter.getItemCount() != noteList.size()){
                mAdapter = new MyNoteAdapter(this,noteList,this);
                mRecyclerView.setAdapter(mAdapter);
            }else {
                mAdapter.setNoteList(noteList);
                mAdapter.notifyDataSetChanged();
            }
        }
        mAdapter.notifyDataSetChanged();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_action, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        mSearchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent intent = new Intent(MainActivity.this,MyNoteActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.long_click_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete:
                NoteUtil.getsNoteUtil(this).deleteNote(mNote.getId());
                Log.i("test","delete");
                updateUI();
                break;
            case R.id.change_bg:
                ColorDialogFragment fragment = ColorDialogFragment.newInstance(mNote);
                fragment.setOnColorChangeListener(this);
                fragment.show(getSupportFragmentManager(),"color");
                break;
            default:
                break;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public void onLongItemClick(Note note) {
        mNote = note;
    }

    @Override
    public void onColorChange() {
        updateUI();
    }
}
