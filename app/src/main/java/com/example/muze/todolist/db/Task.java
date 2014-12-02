package com.example.muze.todolist.db;
import android.provider.BaseColumns;

/**
 * Created by muze on 28/11/14.
 */
public class Task {
    public static final String DB_NAME = "com.example.muze.todolist.db.tasks";
    public static final int DB_VERSION = 1;
    public static final String Table = "tasks";

    public class Columns{
        public static final String TASK = "task";
        public static final String _ID = BaseColumns._ID;
    }

}
