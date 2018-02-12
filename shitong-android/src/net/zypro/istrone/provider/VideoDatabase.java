package net.zypro.istrone.provider;

import net.zypro.istrone.provider.VideoContract.VideoDraftsColumns;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class VideoDatabase extends SQLiteOpenHelper{
	private static final String DATABASE_NAME = "video.db";
	
	private static final int CUR_DATABASE_VERSION = 1;

    private final Context mContext;
    
    interface Tables {
    	String VIDEO_DRAFTS="drafts";
    }
    
    public VideoDatabase(Context context) {
        super(context, DATABASE_NAME, null, CUR_DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    	db.execSQL("create table " +Tables.VIDEO_DRAFTS+" ("
				+BaseColumns._ID+" integer primary key autoincrement,"
				+VideoDraftsColumns.DRAFT_ID+" TEXT NOT NULL,"
				+VideoDraftsColumns.DRAFT_COVER_PATH+" TEXT NOT NULL,"
				+VideoDraftsColumns.DARFT_VIDEO_PATH+" TEXT NOT NULL,"
				+VideoDraftsColumns.DRAFT_TIMESTAMP+"  TEXT NOT NULL,"
				+VideoDraftsColumns.DRAFT_DESCRIPTION+" TEXT,"
				+VideoDraftsColumns.DRAFT_TAGS+" TEXT,"
				+"UNIQUE (" + VideoDraftsColumns.DRAFT_ID + ") ON CONFLICT REPLACE)");
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	db.execSQL("DROP TABLE IF EXISTS " + Tables.VIDEO_DRAFTS);
    	onCreate(db);
    }
    
    public static void deleteDatabase(Context context) {
        context.deleteDatabase(DATABASE_NAME);
    }
}
