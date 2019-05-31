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
import java.time.LocalDate;
import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity {
    LocalDate data =  LocalDate.now();
    String Month[]={"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    public static ArrayList<Work> works = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        TableLayout table = findViewById(R.id.Month);
        for (int i=0;i<4;i++){
            TableRow tableRow = new TableRow(this);
            for (int j = 0; j <3 ; j++) {
                final Button button = new Button(this);
                button.setText(Month[i*3+j]);
                if (data.getMonth().getValue()-1>i*3+j)
                    button.setTextColor(Color.rgb(50,50,200));
                button.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT,1));
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        click(v);
                    }
                });
                if (MainActivity.works!=null) {
                    int color = 0;
                    for (Work work : MainActivity.works) {
                            if (work.getMonth()-1 == i*3+j) {
                                color++;
                            }

                    }color=(color+4)/5;
                    if (color>5)color=5;
                    if (color>0)
                        button.setBackgroundColor(Color.rgb(255-color*50,50,50));
                }
                tableRow.addView(button);
            }
            tableRow.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT,1));
            table.addView(tableRow);
        }

    }

    public void click (View view){
        Button button = (Button) view;
        Intent intent = new Intent(this, Month.class);
        intent.putExtra("Месяц",button.getText());
        startActivity(intent);

    }
}
