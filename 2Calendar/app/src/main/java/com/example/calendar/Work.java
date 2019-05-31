package com.example.calendar;


public class Work {
    private int Month;
    private int Day;
    private int Time;
    private String text;

    public  Work (int a,int b,int c,String d){
        Month=a;
        Day=b;
        Time=c;
        text=d;
    }
    public int getMonth(){
        return Month;
    }
    public int getDay(){
        return Day;
    }
    public int getTime(){
        return Time;
    }
    public String getText(){
        return text;
    }

}
