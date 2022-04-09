package com.francivaldo.drausioacademia.aluno;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.francivaldo.drausioacademia.R;
import com.francivaldo.drausioacademia.database.LogClass;
import com.francivaldo.drausioacademia.ficha.ActivityTreinos;
import com.francivaldo.drausioacademia.ficha.ActivityUserInfor;
import com.francivaldo.drausioacademia.main.Main;
import com.francivaldo.drausioacademia.main.model.FichaTreino;
import com.francivaldo.drausioacademia.main.model.Mdate;
import com.francivaldo.drausioacademia.main.model.User;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class AlunoInfor extends AppCompatActivity {
    TextView textName,textDate,textNameInfor;
    User user;
    Dialog dialog;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aluno_infor);

        textName = findViewById(R.id.txt_nome_infor);
        textDate = findViewById(R.id.date_aluno_infor);
        textNameInfor = findViewById(R.id.nome_aluno_infor);

        pd = new ProgressDialog(this);

        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_pix_qr_code);

        Bundle extras = getIntent().getExtras();

        user = new User(extras.getString(Main.db.INFOR),extras.getString(Main.db.ID));
        user.setTreino(extras.getString(Main.db.TREINO));
        user.setMeses(extras.getString(Main.db.MESES));

        textName.setText(user.getName());
        textNameInfor.setText(user.getName());
        Mdate today = user.getDate();
        String month = String.valueOf(today.month) ;
        month = today.month+1 < 10?"0"+month:month;
        String day = String.valueOf(today.day);
        day = today.day < 10?"0"+day:day;
        String date;
        date = day +"/"+ month +"/"+ today.year;
        textDate.setText(date);
    }
    @Override
    public void onBackPressed() {
        Main.db.logClass = null;
        startActivity(new Intent(this,Alunos.class));
        finish();
    }
    public void onIcBack(View view) {
        onBackPressed();
    }
    public void onConfirmPix(View view) {
        pd.setTitle("gravando...");
        pd.show();

        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        user.getMeses().mes[today.month].isCheck = true;
        user.getMeses().mes[today.month].dateCheck =today.monthDay + "/" + (today.month+1) + "/" + today.year;
        Main.db.logClass = new LogClass() {
            @Override
            public void onSucess() {
                pd.dismiss();
            }

            @Override
            public void onFailure() {
                pd.dismiss();
            }
        };
        Main.db.update(user);
        onCancelPix(view);
    }
    public void onCancelPix(View view) {
        dialog.dismiss();
    }
    public void onQRCODE(View view) {
        dialog.show();
    }
    public void onDeleteUser(View view) {
        pd.setTitle("deletando...");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialog_delete_confirm);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                pd.show();

                Main.db.logClass = new LogClass() {
                    @Override
                    public void onSucess() {
                        pd.dismiss();
                        onBackPressed();
                    }

                    @Override
                    public void onFailure() {
                        Toast.makeText(AlunoInfor.this, "Não foi possivel excluir o usuario!", Toast.LENGTH_SHORT).show();
                    }
                };
                Main.db.delet(user);
                
            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                builder.setCancelable(true);
            }
        });
        Dialog dialog = builder.create();
        dialog.show();
    }
    public void onStartActiviyFicha(View view) {
        Intent intent = new Intent(this, ActivityUserInfor.class);
        intent.putExtra(Main.db.ID,user.getId());
        intent.putExtra(Main.db.INFOR,user.getPIString());
        intent.putExtra(Main.db.TREINO,user.getTreinoString());
        intent.putExtra(Main.db.MESES,user.getMesesString());
        startActivity(intent);
        finish();
    }
    public void onStartActiviyTreino(View view) {
        Intent intent = new Intent(this, ActivityTreinos.class);
        intent.putExtra(Main.db.ID,user.getId());
        intent.putExtra(Main.db.INFOR,user.getPIString());
        intent.putExtra(Main.db.TREINO,user.getTreinoString());
        intent.putExtra(Main.db.MESES,user.getMesesString());
        startActivity(intent);
        finish();
    }

    //
    public void onShare(View view) {
        createPdf();
    }
    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }
    private void createPdf(){
        int textSize = 10;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.ficha_model_2);
        bitmap = getResizedBitmap(bitmap,1200,579);

        PdfDocument pdfDocument = new PdfDocument();
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(textSize);
        paint.setFakeBoldText(true);

        PdfDocument.PageInfo mypageInfo = new PdfDocument.PageInfo.Builder(1200,579, 1).create();
        PdfDocument.Page myPage = pdfDocument.startPage(mypageInfo);
        Canvas canvas = myPage.getCanvas();
        int x,y,H = 20;
        x = 10;
        y = H-textSize/2;
        canvas.drawBitmap(bitmap,0,0,null);
        canvas.drawText(" NOME DO ALUNO :"+user.getPi().infor[0],x,y,paint);
        y += H;
        String s = user.getPi().infor[1];
        switch (s){
            case "1":
                s =  "( x )Emagrecimento (   )Hipertrofia (   )Res.Musc.Loc";
                break;
            case "2":
                s =  "(   )Emagrecimento ( x )Hipertrofia (   )Res.Musc.Loc";
                break;
            case "3":
                s =  "(   )Emagrecimento (   )Hipertrofia ( x )Res.Musc.Loc";
                break;
            default:
                s =  "(   )Emagrecimento (   )Hipertrofia (   )Res.Musc.Loc";
                break;
        }
        canvas.drawText(s,x,y,paint);
        y += H;
        canvas.drawText(" Professor :"+user.getPi().infor[2],x,y,paint);
        y += H;
        canvas.drawText(" Data de início : "
                +user.getDate()+
                "                                                                                                       " +
                "Validade da Ficha : "
                +user.getPi().infor[7]+
                "                                                                                                       " +
                "Contato: "+user.getPi().infor[8]
                ,x,y,paint);
        x += bitmap.getWidth()/2;
        y = H-textSize/2;
        canvas.drawText(" Venc. da Mensalidade: Dia( "+user.getPi().infor[3]+" )",x,y,paint);
        y += H;
        canvas.drawText(" Nº de vezes na semana :"+user.getPi().infor[4],x,y,paint);
        y += H;
        canvas.drawText(" IMC :"+user.getPi().infor[5],x,y,paint);
        y += H;
        y += H;
        y += H;
        int cm1 = 140,cm2 = 200,cm3 = 64;
        x -= bitmap.getWidth()/2;
        for (FichaTreino.Treino treino: user.getTreino().treinoPeito) {
            canvas.drawText(treino.nameTreino,x + cm1,y,paint);
            canvas.drawText(String.valueOf(treino.cmp1),x + cm2 + cm1,y,paint);
            canvas.drawText(String.valueOf(treino.cmp2),x + cm2 + cm1 + (cm3*1),y,paint);
            canvas.drawText(String.valueOf(treino.cmp3),x + cm2 + cm1 + (cm3*2),y,paint);
            canvas.drawText(String.valueOf(treino.cmp4),x + cm2 + cm1 + (cm3*3),y,paint);
            y += H;
        }
        for (FichaTreino.Treino treino: user.getTreino().treinoCostas) {
            canvas.drawText(treino.nameTreino,x + cm1,y,paint);
            canvas.drawText(String.valueOf(treino.cmp1),x + cm2 + cm1,y,paint);
            canvas.drawText(String.valueOf(treino.cmp2),x + cm2 + cm1 + (cm3*1),y,paint);
            canvas.drawText(String.valueOf(treino.cmp3),x + cm2 + cm1 + (cm3*2),y,paint);
            canvas.drawText(String.valueOf(treino.cmp4),x + cm2 + cm1 + (cm3*3),y,paint);
            y += H;
        }
        for (FichaTreino.Treino treino: user.getTreino().treinoBiceps) {
            canvas.drawText(treino.nameTreino,x + cm1,y,paint);
            canvas.drawText(String.valueOf(treino.cmp1),x + cm2 + cm1,y,paint);
            canvas.drawText(String.valueOf(treino.cmp2),x + cm2 + cm1 + (cm3*1),y,paint);
            canvas.drawText(String.valueOf(treino.cmp3),x + cm2 + cm1 + (cm3*2),y,paint);
            canvas.drawText(String.valueOf(treino.cmp4),x + cm2 + cm1 + (cm3*3),y,paint);
            y += H;
        }
        for (FichaTreino.Treino treino: user.getTreino().treinoAnBraco) {
            canvas.drawText(treino.nameTreino,x + cm1,y,paint);
            canvas.drawText(String.valueOf(treino.cmp1),x + cm2 + cm1,y,paint);
            canvas.drawText(String.valueOf(treino.cmp2),x + cm2 + cm1 + (cm3*1),y,paint);
            canvas.drawText(String.valueOf(treino.cmp3),x + cm2 + cm1 + (cm3*2),y,paint);
            canvas.drawText(String.valueOf(treino.cmp4),x + cm2 + cm1 + (cm3*3),y,paint);
            y += H;
        }
        for (FichaTreino.Treino treino: user.getTreino().treinoPernas) {
            canvas.drawText(treino.nameTreino,x + cm1,y,paint);
            canvas.drawText(String.valueOf(treino.cmp1),x + cm2 + cm1,y,paint);
            canvas.drawText(String.valueOf(treino.cmp2),x + cm2 + cm1 + (cm3*1),y,paint);
            canvas.drawText(String.valueOf(treino.cmp3),x + cm2 + cm1 + (cm3*2),y,paint);
            canvas.drawText(String.valueOf(treino.cmp4),x + cm2 + cm1 + (cm3*3),y,paint);
            y += H;
        }
        y = 5*H + (H-textSize/2);
        x += bitmap.getWidth()/2;
        for (FichaTreino.Treino treino: user.getTreino().treinoOmbro) {
            canvas.drawText(treino.nameTreino,x + cm1,y,paint);
            canvas.drawText(String.valueOf(treino.cmp1),x + cm2 + cm1,y,paint);
            canvas.drawText(String.valueOf(treino.cmp2),x + cm2 + cm1 + (cm3*1),y,paint);
            canvas.drawText(String.valueOf(treino.cmp3),x + cm2 + cm1 + (cm3*2),y,paint);
            canvas.drawText(String.valueOf(treino.cmp4),x + cm2 + cm1 + (cm3*3),y,paint);
            y += H;
        }
        for (FichaTreino.Treino treino: user.getTreino().treinoTriceps) {
            canvas.drawText(treino.nameTreino,x + cm1,y,paint);
            canvas.drawText(String.valueOf(treino.cmp1),x + cm2 + cm1,y,paint);
            canvas.drawText(String.valueOf(treino.cmp2),x + cm2 + cm1 + (cm3*1),y,paint);
            canvas.drawText(String.valueOf(treino.cmp3),x + cm2 + cm1 + (cm3*2),y,paint);
            canvas.drawText(String.valueOf(treino.cmp4),x + cm2 + cm1 + (cm3*3),y,paint);
            y += H;
        }
        for (FichaTreino.Treino treino: user.getTreino().treinoAbVert) {
            canvas.drawText(treino.nameTreino,x + cm1,y,paint);
            canvas.drawText(String.valueOf(treino.cmp1),x + cm2 + cm1,y,paint);
            canvas.drawText(String.valueOf(treino.cmp2),x + cm2 + cm1 + (cm3*1),y,paint);
            canvas.drawText(String.valueOf(treino.cmp3),x + cm2 + cm1 + (cm3*2),y,paint);
            canvas.drawText(String.valueOf(treino.cmp4),x + cm2 + cm1 + (cm3*3),y,paint);
            y += H;
        }
        for (FichaTreino.Treino treino: user.getTreino().treinoCoxGlu) {
            canvas.drawText(treino.nameTreino,x + cm1,y,paint);
            canvas.drawText(String.valueOf(treino.cmp1),x + cm2 + cm1,y,paint);
            canvas.drawText(String.valueOf(treino.cmp2),x + cm2 + cm1 + (cm3*1),y,paint);
            canvas.drawText(String.valueOf(treino.cmp3),x + cm2 + cm1 + (cm3*2),y,paint);
            canvas.drawText(String.valueOf(treino.cmp4),x + cm2 + cm1 + (cm3*3),y,paint);
            y += H;
        }

        pdfDocument.finishPage(myPage);
        File file = new File(getExternalCacheDir().getPath().toString()+ "/ficha_de_"+user.getName()+".pdf");
        try {
            pdfDocument.writeTo(new FileOutputStream(file));
            sharePDF(file);
            Log.d("FILE :",file.getPath());
        } catch (IOException e) {
            Toast.makeText(this, e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        pdfDocument.close();
    }
    public void sharePDF(File file) {
        if (file.exists()) {
            Uri uri = Build.VERSION.SDK_INT < 24? Uri.fromFile(file) : Uri.parse(file.getPath());
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setType("application/pdf");
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Purchase Bill...");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Sharing Bill purchase items...");
            startActivity(Intent.createChooser(shareIntent, "Share Via"));
            Toast.makeText(this, "salvo em :"+file.getPath(), Toast.LENGTH_SHORT).show();
        }
    }
}