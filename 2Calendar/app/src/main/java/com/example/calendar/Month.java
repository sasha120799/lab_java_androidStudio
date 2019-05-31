package com.example.calendar;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import java.time.LocalDate;

public class Month extends AppCompatActivity {
    TextView textView = null;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        textView = findViewById(R.id.textView);
        textView.setText(getIntent().getStringExtra("Месяц"));
        LocalDate date =  LocalDate.of(2019, month_to_int(textView.getText().toString()), 1);
        LocalDate dateNow =  LocalDate.now();
        TableLayout table = findViewById(R.id.table2);
        int val_days=31;
        if (textView.getText().toString().equals("February"))val_days=28;
        else if (textView.getText().toString().equals("April") | textView.getText().toString().equals("June") | textView.getText().toString().equals("September") | textView.getText().toString().equals("November"))val_days=30;
        int dn = -date.getDayOfWeek().getValue()+2;
        int i=0;
        if (val_days-dn>=35)i--;
        for (;i<5;i++){
            TableRow tableRow = new TableRow(this);
            for (int j=0; j <7 ; j++) {
                Button button = new Button(this);
                button.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT,1));
                if (dn>=1&&dn<=val_days){
                    date = LocalDate.of(2019,month_to_int(textView.getText().toString()),dn);
                    if (dateNow.getDayOfYear()>date.getDayOfYear())
                        button.setTextColor(Color.rgb(55,55,222));
                    if (MainActivity.works!=null){
                        int color=0;
                        for(Work work:MainActivity.works) {
                            if (work.getDay()== date.getDayOfMonth()){
                                if (work.getMonth()== date.getMonthValue()){
                                    color++;
                                }
                            }
                        }
                        if (color>5)color=5;
                        if (color>0)
                            button.setBackgroundColor(Color.rgb(color*50,50,50));
                        if (dateNow.getDayOfMonth()==date.getDayOfMonth()&&dateNow.getMonth()==date.getMonth())
                            button.setBackgroundColor(Color.rgb(111,222,111));
                    }
                    button.setText(String.valueOf(dn));
                    button.setMinimumWidth(50);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            click(v);
                        }
                    });
                }
                dn++;
                tableRow.addView(button);
            }
            table.addView(tableRow);
            TextView textView = new TextView(this);
            textView.setText(String.valueOf((date.getDayOfYear()/7+1)));
            textView.setTextSize(20);
            textView.setHeight(127);
            LinearLayout liner = findViewById(R.id.liner);
            liner.addView(textView);
        }

    }

    public void click (View view){
        Button button = (Button) view;
        Intent intent = new Intent(this, Day.class);
        intent.putExtra("День",button.getText());
        intent.putExtra("Месяц",textView.getText());
        startActivity(intent);

    }
    public static int month_to_int (String a){
        if (a.equals("January"))return 1;
        if (a.equals("February"))return 2;
        if (a.equals("March"))return 3;
        if (a.equals("April"))return 4;
        if (a.equals("May"))return 5;
        if (a.equals("June"))return 6;
        if (a.equals("July"))return 7;
        if (a.equals("August"))return 8;
        if (a.equals("September"))return 9;
        if (a.equals("October"))return 10;
        if (a.equals("November"))return 11;
        if (a.equals("December"))return 12;
        return -1;
    }
    public  void back (View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}