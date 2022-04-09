package com.francivaldo.drausioacademia.main.model;

public class PersonalInformation {
    public String[] infor = new String[10];
    public PersonalInformation(){
        for (int i = 0; i < infor.length; i++) {
            infor[i] = "";
        }
    }
}
