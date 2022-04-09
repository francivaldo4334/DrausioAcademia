package com.francivaldo.drausioacademia.main.model;

import java.util.ArrayList;
import java.util.List;

public class FichaTreino {
    public List<Treino> treinoPeito = new ArrayList<>();
    public List<Treino> treinoCostas = new ArrayList<>();
    public List<Treino> treinoBiceps = new ArrayList<>();
    public List<Treino> treinoAnBraco = new ArrayList<>();
    public List<Treino> treinoPernas = new ArrayList<>();
    public List<Treino> treinoOmbro = new ArrayList<>();
    public List<Treino> treinoTriceps = new ArrayList<>();
    public List<Treino> treinoAbVert = new ArrayList<>();
    public List<Treino> treinoCoxGlu = new ArrayList<>();
    public FichaTreino(){

//        //0 a 4
        treinoPeito.add(new Treino("supino reto"));
        treinoPeito.add(new Treino("supino inclinado"));
        treinoPeito.add(new Treino("supino declinado"));
        treinoPeito.add(new Treino("pullver"));
        treinoPeito.add(new Treino("crucifixo"));
        treinoPeito.add(new Treino("voador"));
//
//        //5 a 10
        treinoCostas.add(new Treino("barra fixa"));
        treinoCostas.add(new Treino("remada unilateral"));
        treinoCostas.add(new Treino("remada curvada"));
        treinoCostas.add(new Treino("puxador alto"));
        treinoCostas.add(new Treino("puxador baixo"));
        treinoCostas.add(new Treino("remo cavalinho"));
//
//        //11 a 14
        treinoBiceps.add(new Treino("rosca direta"));
        treinoBiceps.add(new Treino("rosca no scott"));
        treinoBiceps.add(new Treino("rosca 21"));
        treinoBiceps.add(new Treino("rosca concentrada"));
        treinoBiceps.add(new Treino("rosca martelo"));

//        //15 a 16
        treinoAnBraco.add(new Treino("rosca punho"));
        treinoAnBraco.add(new Treino("rosca inversa"));

//        //17 a 18
        treinoPernas.add(new Treino("panturrilha sentada"));
        treinoPernas.add(new Treino("panturrilha em pé"));
//
//        //19 a 25
        treinoOmbro.add(new Treino("desenv. do ombro"));
        treinoOmbro.add(new Treino("elevação lateral"));
        treinoOmbro.add(new Treino("elevação frontal"));
        treinoOmbro.add(new Treino("crucifixo inverso"));
        treinoOmbro.add(new Treino("remada alta"));
        treinoOmbro.add(new Treino("encolh. de ombro"));
//
//        //26 a 29
        treinoTriceps.add(new Treino("testa"));
        treinoTriceps.add(new Treino("francês"));
        treinoTriceps.add(new Treino("pulley"));
        treinoTriceps.add(new Treino("corda"));
//
//        //30 a 33
        treinoAbVert.add(new Treino("abdominal"));
        treinoAbVert.add(new Treino("oblíquo"));
        treinoAbVert.add(new Treino("infra"));
        treinoAbVert.add(new Treino("prancha"));
//        //34 a 43
        treinoCoxGlu.add(new Treino("agachamento"));
        treinoCoxGlu.add(new Treino("hack machine"));
        treinoCoxGlu.add(new Treino("leg pross 180º"));
        treinoCoxGlu.add(new Treino("leg pross 45º"));
        treinoCoxGlu.add(new Treino("extensora"));
        treinoCoxGlu.add(new Treino("stiff"));
        treinoCoxGlu.add(new Treino("agacha. unilateral"));
        treinoCoxGlu.add(new Treino("elevação pélvica"));
        treinoCoxGlu.add(new Treino("clúteo cabo"));
        treinoCoxGlu.add(new Treino("flexora deitada"));

    }
    public class Treino{
        public String nameTreino;
        public boolean add;
        public int cmp1,cmp2,cmp3,cmp4;
        public Treino(String nameTreino){
            add = false;
            this.nameTreino = nameTreino;
            cmp1 = 0;
            cmp2 = 0;
            cmp3 = 0;
            cmp4 = 0;
        }
    }
}
