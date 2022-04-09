package com.francivaldo.drausioacademia.main.model;

import com.google.gson.Gson;

public class User {
    private Meses meses;
    private FichaTreino treino;
    private Gson gson;
    private String id;
    private PersonalInformation pi;

    public User(String name, String date, String id) {
        gson = new Gson();
        this.id = id;
        treino = new FichaTreino();
        meses = new Meses();
        pi = new PersonalInformation();
        pi.infor[0] = name;
        pi.infor[6] = date;
    }

    public User(String infor, String id) {
        gson = new Gson();
        this.id = id;
        this.pi = gson.fromJson(infor,PersonalInformation.class);
        treino = new FichaTreino();
        meses = new Meses();
    }

    public User() {
        gson = new Gson();
        treino = new FichaTreino();
        meses = new Meses();
        pi = new PersonalInformation();
    }

    public String getName() {
        return pi.infor[0];
    }

    public void setName(String name) {
        this.pi.infor[0] = name;
    }

    public String getMesesString() {

        return gson.toJson(meses);
    }
    public Meses getMeses() {
        return meses;
    }
    public void setMeses(String meses) {
        this.meses = gson.fromJson(meses, Meses.class);
    }

    public FichaTreino getTreino(){
        return treino;
    }
    public void setTreino(String s){
        treino = gson.fromJson(s,FichaTreino.class);
    }
    public Mdate getDate() {
        return gson.fromJson(pi.infor[6],Mdate.class);
    }
    public String getDateString() {
        return pi.infor[6];
    }
    public String getId() {
        return id;
    }
    public PersonalInformation getPi(){
        return pi;
    }
    public String getPIString() {
        return gson.toJson(pi);
    }

    public void setPi(String pi) {
        this.pi = gson.fromJson(pi,PersonalInformation.class);
    }

    public String getTreinoString() {
        return gson.toJson(treino);
    }

    public void setID(String string) {
        id = string;
    }
}
