package com.example.android.mynote.database;

import android.database.Cursor;
import android.database.CursorWrapper;



/**
 *
 */

public class NoteCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public NoteCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Note getNote(){
        int id = getInt(getColumnIndex(NoteDbSchema.NoteTable.Cols.ID));
        int color = getInt(getColumnIndex(NoteDbSchema.NoteTable.Cols.COLOR));
        String title = getString(getColumnIndex(NoteDbSchema.NoteTable.Cols.TITLE));
        String content = getString(getColumnIndex(NoteDbSchema.NoteTable.Cols.CONTENT));
        long modifytime = getLong(getColumnIndex(NoteDbSchema.NoteTable.Cols.MODIFYTIME));


        Note book = new Note(id,title,content,modifytime);
        book.setColor(color);
        return book;
    }


}
