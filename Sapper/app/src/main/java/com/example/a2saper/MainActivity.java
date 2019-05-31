package com.example.a2saper;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    EditText editText = null;
    Spinner spinner =null;
    TextView textView =null;
    int numWin = 0;
    int size=5;
    int min = 5;
    List<Point> coord = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        spinner = findViewById(R.id.spinner);
        textView = findViewById(R.id.textView4);
    }
    public void start (View view){
        textView.setText("Работаем");
        numWin = 0;
        size = spinner.getSelectedItemPosition()+5;
        min = 5;
        if (!editText.getText().toString().equals(""))min=Integer.parseInt(editText.getText().toString());
        if (min<5)min=5;
        if (min >size*size-10)min=size*size-10;
        coord = new ArrayList<>();
        int m=0;
        while (m<min){
            Random ran = new Random();
            Point a = new Point(ran.nextInt(size),ran.nextInt(size));
            if (!coord.contains(a)){coord.add(a);m++;}
        }
        TableLayout tableLayout = findViewById(R.id.tableLayout);
        tableLayout.removeAllViews();
        for (int i=0;i<size;i++){
            TableRow tableRow = new TableRow(this);
            for (int j=0;j<size;j++){
                final Button button = new Button(this);
                button.setId(i*10+j);
                button.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT,1));
                button.setMinimumWidth(40);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        press (v);
                    }

                });
                tableRow.addView(button);
            }
            tableLayout.addView(tableRow);
        }
    }

    public void press (View view){
        numWin++;
        Button button =(Button)view;
        button.setClickable(false);
        button.setPressed(true);
        int w =button.getId();
        int h =button.getId();
        String ot;
        int col=0;
        w/=10;h%=10;
        Point a = new Point( w,h);
        if (coord.contains(a)){
            if (!textView.getText().equals("Обезвреженно")){
                textView.setText("БУМ!!!");
                numWin=-100;
                for (int i=0;i<size;i++) {
                    for (int j = 0; j < size; j++) {
                        if (coord.contains(new Point(i,j))){
                            Button button2 =findViewById(i*10+j);
                            button2.setText("X");
                        }
                    }
                }
            }
        }else{
            for (int i=w-1;i<=w+1;i++){
                for (int j=h-1;j<=h+1;j++){
                    if (coord.contains(new Point(i,j)))col++;
                }
            }
            ot = String.valueOf(col);
            /*if (col==0){
                button.setBackgroundColor(Color.LTGRAY);
                for (int i=w-1;i<=w+1;i++){
                    for (int j=h-1;j<=h+1;j++){
                        if ((i>=0&&j>=0&&i<size&&j<size)&&(j!=h||w!=i)){
                        Button zeroBut=findViewById(i*10+j);
                        if (!zeroBut.isPressed())
                        press(zeroBut);
                        }
                    }
                }
            }else */button.setText(ot);
            if (numWin==size*size-min)textView.setText("Обезвреженно");
        }

    }
}

