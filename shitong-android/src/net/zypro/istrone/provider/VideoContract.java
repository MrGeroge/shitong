package net.zypro.istrone.provider;

import android.net.Uri;
import android.provider.BaseColumns;

public class VideoContract {
	
    interface VideoDraftsColumns{
		public static final String DRAFT_ID="draft_id";
		public static final String DRAFT_COVER_PATH="draft_cover_path";
		public static final String DARFT_VIDEO_PATH="darft_video_path";
		public static final String DRAFT_DESCRIPTION="draft_desc";
		public static final String DRAFT_TIMESTAMP="draft_time";
		public static final String DRAFT_TAGS="draft_tags";
	}
	
    public static final String CONTENT_AUTHORITY = "net.zypro.istrone";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
	
    private static final String PATH_VIDEO_DRAFTS="drafts";
    
    public static class VideoDrafts implements VideoDraftsColumns,BaseColumns{
    	public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_VIDEO_DRAFTS).build();
    	
    	public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/net.zypro.istrone.draft";
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/net.zypro.istrone.draft";
        
        public static Uri buildDraftsUri() {
            return CONTENT_URI;
        }
        
        public static Uri buildDraftUri(String draftId) {
            return CONTENT_URI.buildUpon().appendPath(draftId).build();
        }
        
        public static String getDraftId(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }
}
