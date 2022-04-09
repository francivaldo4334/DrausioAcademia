package com.francivaldo.drausioacademia.main.model;

import java.util.ArrayList;
import java.util.List;

public class Mdate {
    public int day;
    public int month;
    public int year;
    public Mdate (int day,int month,int yes){
        this.day = day;
        this.month = month;
        this.year = yes;
    }
    private int toint(List<Character> chars){
        char[] c = new char[chars.size()];
        for (int i = 0; i < c.length; i++) {
                c[i] = chars.get(i);
        }
        int n;
        try{
            n = Integer.valueOf(String.valueOf(c));
        }catch (Exception e){
            n = 0;
        }
        return n;
    }
    public Mdate(String date) {
        CharSequence sequence = date;
        List<Character>[] charList = new List[]{new ArrayList<>(),new ArrayList<>(),new ArrayList<>()};

        int sId = 0;
        for (int i = 0; i < sequence.length(); i++) {
            if(sequence.charAt(i) == '/'||sequence.charAt(i) == ' '||sequence.charAt(i) == '-') {
                sId++;
            }
            else if (sId < 3)
                charList[sId].add(sequence.charAt(i));
        }

        day = toint(charList[0]);
        month = toint(charList[1]);
        year = toint(charList[2]);
    }

    public String toString(){
        return ""+day+"/"+month+"/"+ year;
    }
}
