package com.example.android.mynote;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.mynote.database.Note;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Derrick on 2018/5/31.
 */

public class MyNoteAdapter extends RecyclerView.Adapter<MyNoteAdapter.NoteViewHolder> {



    private List<Note> mNoteList;
    private static Context mContext;
    private OnLongItemClickListener mOnLongItemClickListener;


    private static final int THEME_GREEN = 0;
    private static final int THEME_BLUE = 1;
    private static final int THEME_RED = 2;
    private static final int THEME_YELLOW = 3;
    private static final int THEME_PINK= 4;
    private static final int THEME_PURPLE= 5;
    public void setOnLongItemClickListener(OnLongItemClickListener onLongItemClickListener) {
        mOnLongItemClickListener = onLongItemClickListener;
    }

    public void setNoteList(List<Note> noteList) {
        mNoteList = noteList;
    }

    public MyNoteAdapter(Context context,List<Note> noteList,OnLongItemClickListener onLongItemClickListener){
        this.mContext = context.getApplicationContext();
        this.mNoteList = noteList;
        this.mOnLongItemClickListener = onLongItemClickListener;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        holder.bind(mNoteList.get(position),mOnLongItemClickListener);
    }

    @Override
    public int getItemCount() {
        return mNoteList.size();
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{

        private TextView mTitleTextView;
        private TextView mTimeTextView;
        private Note mNote;
        private OnLongItemClickListener mOnLongItemClickListener;

        public NoteViewHolder(View itemView) {
            super(itemView);
            mTitleTextView = itemView.findViewById(R.id.title_text_view);
            mTimeTextView = itemView.findViewById(R.id.time_text_view);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void bind(Note note,OnLongItemClickListener onLongItemClickListener){
            mNote = note;
            mOnLongItemClickListener = onLongItemClickListener;
            if(mTitleTextView != null && mTimeTextView != null){
                mTitleTextView.setText(note.getTitle());
                Date date = new Date(note.getModifyTime());
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateString = formatter.format(date);
                mTimeTextView.setText(dateString);
            }

            itemView.setBackgroundColor(mContext.getResources().getColor(mNote.getColor()));
        }

        @Override
        public void onClick(View v) {

            Intent intent = new Intent(mContext,MyNoteActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("id",mNote.getId());
            bundle.putInt("color",mNote.getColor());
            bundle.putString("title",mNote.getTitle());
            bundle.putString("content",mNote.getContent());
            bundle.putLong("time",mNote.getModifyTime());
            intent.putExtra("bundle",bundle);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);

        }

        @Override
        public boolean onLongClick(View v) {
            Log.i("test","onLongClick");
            if(mOnLongItemClickListener != null){
                mOnLongItemClickListener.onLongItemClick(mNote);
            }
            return false;
        }

        private int getBgColor(int colorId){
            int mResId;
            Log.i("test","colorId " + colorId);
            switch (colorId){
                case THEME_GREEN:
                    mResId = R.color.green;
                    break;
                case THEME_BLUE:
                    mResId = R.color.blue;
                    break;
                case THEME_RED:
                    mResId = R.color.red;
                    break;
                case THEME_YELLOW:
                    mResId = R.color.yellow;
                    break;
                case THEME_PINK:
                    mResId = R.color.pink;
                    break;
                case THEME_PURPLE:
                    mResId = R.color.purple;
                    break;
                default:
                    mResId = R.color.white;
                    break;
            }

            return mResId;
        }
    }


}
