package com.example.myapplication;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pole = findViewById(R.id.pole);
        pole.setText("");

    }

    TextView pole=null;
    int op=-1;
    boolean prov = false;
    double chislo1 = 0;
    double chislo2 = 0;

    @SuppressLint("SetTextI18n")
    public void value (View view){

        if (pole==null) return;
        if (pole.getText().toString().equals("Infinity"))Clear();
        Button button = (Button) view;
            if (prov)pole.setText("");
            prov=false;
            if (!(button.getText().toString().equals(".") && pole.getText().toString().contains(".")))
            pole.setText(pole.getText().toString()+ button.getText().toString());

    }

    public void operation (View view) {
        if (pole.getText().toString().equals("NaN"))Clear();
        Button button = (Button) view;
            if (op!=-1) {
                if (!prov) if (!pole.getText().toString().equals("")) chislo2 = Double.parseDouble(pole.getText().toString());
                chislo1 = equally(chislo1, chislo2, op);
                char[] a = String.valueOf(chislo1).toCharArray();
                pole.setText(a, 0, a.length);
            } else {
                    if (!pole.getText().toString().equals(""))
                        chislo1 = Double.parseDouble(pole.getText().toString());
                    pole.setText("");
            }
            op =oper(button.getText().toString());
           if (op!=0)prov = true;
    }
    public double equally (double ch1, double ch2, int op){
        if (op==1){
            ch1+=ch2;
        }
        if (op==2){
            ch1-=ch2;
        }
        if (op==3){
            ch1*=ch2;
        }
        if (op==4){
            ch1/=ch2;
        }
        return ch1;
    }

    public void Clear (){
        pole.setText("");
        op=-1;
        prov = false;
        chislo1 = 0;
        chislo2 = 0;
    }
    public void clear (View view){
        Clear();
    }

    public int oper (String znac){
        if (znac.equals("+")) return 1;
        if (znac.equals("-")) return 2;
        if (znac.equals("*")) return 3;
        if (znac.equals("/")) return 4;
        return 0;
    }

    public void ce (View view){
        if (pole.getText().toString().equals("Infinity"))Clear(); else {
            CharSequence str = pole.getText();
            if (str.length() > 0) pole.setText(str.subSequence(0, str.length() - 1));
            op = -1;
        }
    }
}