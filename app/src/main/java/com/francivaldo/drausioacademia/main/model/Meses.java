package com.francivaldo.drausioacademia.main.model;

import com.francivaldo.drausioacademia.R;

public class Meses {
    public Mes[] mes;
    public int id = 0;
    public Meses(){
        this.mes = new Mes[12];
        mes[0]  = new Mes("janeiro",false);
        mes[1]  = new Mes("fevereiro",false);
        mes[2]  = new Mes("março",false);
        mes[3]  = new Mes("abril",false);
        mes[4]  = new Mes("maio",false);
        mes[5]  = new Mes("junho",false);
        mes[6]  = new Mes("julho",false);
        mes[7]  = new Mes("agosto",false);
        mes[8]  = new Mes("setembro",false);
        mes[9]  = new Mes("outubro",false);
        mes[10] = new Mes("novembro",false);
        mes[11] = new Mes("dezembro",false);
    }
    public static class Mes{
        public String text;
        public String dateCheck;
        public boolean isCheck;
        public int bmpId;

        public Mes(String text,boolean isCheck){
            this.text = text;
            this.isCheck = isCheck;
            dateCheck = "null";
            if(isCheck)
                bmpId = R.drawable.ic_check;
            else
                bmpId = R.drawable.ic_uncheck;
        }
    }
}
