package net.zypro.istrone.provider;

import net.zypro.istrone.provider.VideoContract.VideoDrafts;
import android.R.integer;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class VideoProvider extends ContentProvider{
	private VideoDatabase mOpenHelper;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    
    private static final int VIDEO_DRAFTS = 100;
    private static final int VIDEO_DRAFTS_ID = 101;
    
    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = VideoContract.CONTENT_AUTHORITY;
        
        matcher.addURI(authority, "drafts", VIDEO_DRAFTS);
        matcher.addURI(authority, "drafts/*", VIDEO_DRAFTS_ID);
        
        return matcher;
    }
    
    @Override
    public boolean onCreate() {
        mOpenHelper = new VideoDatabase(getContext());
        return true;
    }

    private void deleteDatabase() {
        // TODO: wait for content provider operations to finish, then tear down
        mOpenHelper.close();
        Context context = getContext();
        VideoDatabase.deleteDatabase(context);
        mOpenHelper = new VideoDatabase(getContext());
    }
    
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case VIDEO_DRAFTS: 
            	return VideoDrafts.CONTENT_TYPE;
            case VIDEO_DRAFTS_ID:
            	return VideoDrafts.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		final SQLiteDatabase db = mOpenHelper.getReadableDatabase();
		final int match = sUriMatcher.match(uri);
		
		switch (match) {
			case VIDEO_DRAFTS:
				long rowId=db.insert(VideoDatabase.Tables.VIDEO_DRAFTS,VideoContract.VideoDrafts.DRAFT_ID, values);
				
				if(rowId>0)
				{
					Uri dUri=ContentUris.withAppendedId(uri, rowId);
					getContext().getContentResolver().notifyChange(dUri, null);  
					return dUri;
				}
				
				break;
		}
		
		return null;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		final SQLiteDatabase db = mOpenHelper.getReadableDatabase();
		final int match = sUriMatcher.match(uri);
		
		switch (match) {
			case VIDEO_DRAFTS:
				return db.query(VideoDatabase.Tables.VIDEO_DRAFTS, projection, selection, selectionArgs, null, null, sortOrder);
		}
		
		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		final SQLiteDatabase db = mOpenHelper.getReadableDatabase();
		final int match = sUriMatcher.match(uri);
		int num=0;
		
		switch (match) {
			case VIDEO_DRAFTS_ID:
				
		}
		
		getContext().getContentResolver().notifyChange(uri, null);
		
		return num;
	}
}
