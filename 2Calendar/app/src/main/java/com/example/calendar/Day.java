package com.example.calendar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
@RequiresApi(api = Build.VERSION_CODES.O)
public class Day extends AppCompatActivity {
    Spinner spinner = null;
    EditText editText = null;
    TableLayout table = null;
    int value = 1;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        TextView textView = findViewById(R.id.textView2);
        table = findViewById(R.id.table3);
        textView.setText(getIntent().getStringExtra("День")+" "+getIntent().getStringExtra("Месяц"));
        spinner = findViewById(R.id.spinner);
        Integer time[] = new Integer[24];
        for (int i=0;i<24;i++){time[i]=i;}
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, time);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setPrompt("Время");
        editText = findViewById(R.id.editText2);
        update();

    }


    public void add (View view){
        Work work = new Work(Month.month_to_int(getIntent().getStringExtra("Месяц")), Integer.parseInt(getIntent().getStringExtra("День")), (int)spinner.getSelectedItem(), editText.getText().toString());
        MainActivity.works.add(work);
        table.removeAllViews();
        update();
    }
    public void remove (View view){
        int day = Integer.parseInt(getIntent().getStringExtra("День"));
        String month = getIntent().getStringExtra("Месяц");
        Button button = (Button)view;
        int index=0;
        int i=0;
        if (MainActivity.works!=null){
            while(i<MainActivity.works.size()) {
                Work work = MainActivity.works.get(i);
                if (work.getDay()== day){
                    if (work.getMonth()== Month.month_to_int(month)){
                        index++;
                        CheckBox checkBox = findViewById(index);
                        if (!button.getText().toString().equals("Remove")) {
                            MainActivity.works.remove(work);
                            i--;
                        }
                        else if (checkBox.isChecked()){
                            MainActivity.works.remove(work);
                            i--;
                        }

                    }
                }
                i++;
            }
        }
        table.removeAllViews();
        update();
    }
    public  void back (View view){
        Intent intent = new Intent(this, Month.class);
        intent.putExtra("Месяц",getIntent().getStringExtra("Месяц"));
        startActivity(intent);
    }
    @SuppressLint("SetTextI18n")
    public void update (){
        int day = Integer.parseInt(getIntent().getStringExtra("День"));
        String month = getIntent().getStringExtra("Месяц");
        value =1;
        if (MainActivity.works!=null){
            for (Work work:
                    MainActivity.works) {
                if (work.getDay()== day){
                    if (work.getMonth()== Month.month_to_int(month)){
                        TableRow tableRow = new TableRow(this);
                        TextView textView1 = new TextView(this);
                        textView1.setText(String.valueOf(work.getTime())+" - "+work.getText());
                        textView1.setTextSize(30);
                        CheckBox checkBox = new CheckBox(this);
                        checkBox.setId(value);
                        value++;
                        tableRow.addView(checkBox);
                        tableRow.addView(textView1);
                        table.addView(tableRow);
                    }
                }
            }
        }
    }
}
