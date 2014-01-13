package de.seel_dev.myapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

	public static final String TABLE_COMMENTS = "comments";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_COMMENT = "comment";
	
	private static final String DB_NAME = "comments.db";
	private static final int DB_VERSION = 1;

	public DBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db)	{
		String sqlStatement = "CREATE TABLE comments("
				+ "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
				+ "comment TEXT NOT NULL); ";
		Log.d("onCreate DB", sqlStatement);
		db.execSQL(sqlStatement);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d("onUpgrade DB", "Drop Table and create new, dry run");
		onCreate(db);
	}

}
