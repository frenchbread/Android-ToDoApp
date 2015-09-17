package damir.android_todoapp.com.androidtodoapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import damir.android_todoapp.com.androidtodoapp.db.Task;
import damir.android_todoapp.com.androidtodoapp.db.TaskDBHelper;

public class MainActivity extends ListActivity {

    private TaskDBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.refresh();
    }

    private void refresh(){
        helper = new TaskDBHelper(MainActivity.this);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.query(Task.Table,
                new String[]{Task.Columns._ID, Task.Columns.TASK},
                null,null,null,null,null);

        ListAdapter listAdapter = new SimpleCursorAdapter(
                this,
                R.layout.task_view,
                c,
                new String[] { Task.Columns.TASK},
                new int[] { R.id.taskTextView},
                0
        );
        this.setListAdapter(listAdapter);
    }

    public void onDoneButtonClick(View view) {
        View v = (View) view.getParent();
        TextView taskTextView = (TextView) v.findViewById(R.id.taskTextView);
        String task = taskTextView.getText().toString();

        String sql = String.format("DELETE FROM %s WHERE %s = '%s'",
                Task.Table,
                Task.Columns.TASK,
                task);


        helper = new TaskDBHelper(MainActivity.this);
        SQLiteDatabase sqlDB = helper.getWritableDatabase();
        sqlDB.execSQL(sql);
        refresh();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_task  ) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Add a task.");
            builder.setMessage("What would you like to do?");

            final EditText inpField = new EditText(this);
            builder.setView(inpField);
            builder.setPositiveButton("Add", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface di, int i){
                    String task = inpField.getText().toString();

                    if(task.isEmpty()){
                        Toast.makeText(MainActivity.this, "Error, field is empty.", Toast.LENGTH_LONG).show();
                    }else{
                        TaskDBHelper helper = new TaskDBHelper(MainActivity.this);
                        SQLiteDatabase db = helper.getWritableDatabase();
                        ContentValues values = new ContentValues();

                        values.clear();
                        values.put(Task.Columns.TASK, task);

                        db.insertWithOnConflict(Task.Table, null, values, SQLiteDatabase.CONFLICT_IGNORE);

                        MainActivity.this.refresh();
                    }
                }
            });

            builder.setNegativeButton("Cancel", null);

            builder.create().show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
