package de.seel_dev.myapp;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class commentData {
	
	// Database fields
	private SQLiteDatabase db;
	private DBHelper dbHelper;
	private String[] allColumns = {DBHelper.COLUMN_ID, DBHelper.COLUMN_COMMENT};
	
	public commentData(Context context) {
		dbHelper = new DBHelper(context);
	}
	
	public void open() throws SQLException {
		db = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		dbHelper.close();
	}
	
	public comment createComment(String comment) {
		ContentValues values = new ContentValues();
		values.put(DBHelper.COLUMN_COMMENT, comment);
		long insertId = db.insert(DBHelper.TABLE_COMMENTS, null, values);
		Cursor cursor = db.query(DBHelper.TABLE_COMMENTS,
				allColumns, DBHelper.COLUMN_ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		comment newComment = cursorToComment(cursor);
		cursor.close();
		return newComment;
	}
	
	public void deleteComment(comment comment) {
		long id = comment.getId();
		System.out.println("Comment deleted with id: " + id);
		db.delete(DBHelper.TABLE_COMMENTS, DBHelper.COLUMN_ID + " = " + id, null);
	}
	
	public List<comment> getAllComments() {
		List<comment> comments = new ArrayList<comment>();
		
		Cursor cursor = db.query(DBHelper.TABLE_COMMENTS, allColumns, null, null, null, null, null);
		cursor.moveToFirst();
		
		while (!cursor.isAfterLast()) {
			comment comment = cursorToComment(cursor);
			comments.add(comment);
			cursor.moveToNext();
		}
		
		// make sure to close the cursor
		cursor.close();
		return comments;
	}
	
	private comment cursorToComment(Cursor cursor) {
		comment comment = new comment();
		comment.setId(cursor.getLong(0));
		comment.setComment(cursor.getString(1));
		return comment;
	}
}
