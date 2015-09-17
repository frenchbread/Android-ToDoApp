package damir.android_todoapp.com.androidtodoapp.db;

import android.provider.BaseColumns;

/**
 * Created by damir on 17/09/15.
 */

public class Task {
    public static final String DB_NAME = "damir.android_todoapp.com.androidtodoapp.db.tasks";
    public static final int DB_VERSION = 1;
    public static final String Table = "tasks";

    public class Columns{
        public static final String TASK = "task";
        public static final String _ID = BaseColumns._ID;
    }
}
