package com.example.ivysaur;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ivysaur.utils.DateUtils;

import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class TaskEditActivity extends AppCompatActivity {

    EditText mDeadlineEdit;

    EditText mTitleEdit;

    EditText mDetailEdit;

    Button mDelete;

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_edit);

        mDeadlineEdit = findViewById(R.id.deadlineEditView);
        mTitleEdit = findViewById(R.id.titleEditView);
        mDetailEdit = findViewById(R.id.taskDetailEditView);
        mDelete = findViewById(R.id.delete);

        Realm.init(getApplicationContext());
        RealmConfiguration realmConfig = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(realmConfig);

        long taskId = getIntent().getLongExtra("task_id", -1);
        if (taskId != -1) {
            RealmResults<Task> results = realm.where(Task.class).equalTo("id", taskId).findAll();
            Task task = results.first();
            mDeadlineEdit.setText(DateUtils.toStringDate(task.getDeadLine()));
            mTitleEdit.setText(task.getTitle());
            mDetailEdit.setText(task.getDetail());
            mDelete.setVisibility(View.VISIBLE);
        } else {
            mDelete.setVisibility(View.INVISIBLE);
        }

        mDeadlineEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(TaskEditActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("DefaultLocale")
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mDeadlineEdit.setText(String.format("%d/%02d/%02d", year, month + 1, dayOfMonth));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
                datePickerDialog.show();
            }
        });
    }

    public void onSaveTapped(View view) {
        long taskId = getIntent().getLongExtra("task_id", -1);
        Task task;
        realm.beginTransaction();
        if (taskId != -1) {
            RealmResults<Task> results = realm.where(Task.class).equalTo("id", taskId).findAll();
            task = results.first();
        } else {
            Number maxId = realm.where(Task.class).max("id");
            long nextId = maxId != null ? maxId.longValue() + 1 : 1;
            task = realm.createObject(Task.class, nextId);
        }
        task.setDeadLine(DateUtils.toDate(mDeadlineEdit));
        task.setTitle(mTitleEdit.getText().toString());
        task.setDetail(mDetailEdit.getText().toString());
        realm.commitTransaction();

        Toast.makeText(this, "保存しました", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void onDeleteTapped(View view) {
        long taskId = getIntent().getLongExtra("task_id", -1);
        if (taskId != -1) {
            RealmResults<Task> results = realm.where(Task.class).equalTo("id", taskId).findAll();
            realm.beginTransaction();
            results.deleteFromRealm(0);
            realm.commitTransaction();
            Toast.makeText(this, "削除しました", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}
