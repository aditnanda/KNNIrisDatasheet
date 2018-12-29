package com.nand_project.www.k_nnirisdatashet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nand_project.www.k_nnirisdatashet.KNN.Iris;
import com.nand_project.www.k_nnirisdatashet.KNN.IrisFile;
import com.nand_project.www.k_nnirisdatashet.KNN.IrisType;
import com.nand_project.www.k_nnirisdatashet.KNN.Knn;
import com.nand_project.www.k_nnirisdatashet.Model.DataSet;
import com.nand_project.www.k_nnirisdatashet.Model.Jarak;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {
    private TextView tv_hasil, tv_jarak, tv_knn;
    private EditText et_sl, et_sw, et_pl, et_pw, et_k;
    private Button btn_proses, btn_detail;
    private CardView cv_hasil, cv_jarak, cv_knn;

    public ArrayList<Jarak> jarakArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_hasil = findViewById(R.id.hasil);
        et_pl = findViewById(R.id.pl);
        et_pw = findViewById(R.id.pw);
        et_sl = findViewById(R.id.sl);
        et_sw = findViewById(R.id.sw);
        et_k = findViewById(R.id.k);
        cv_hasil = findViewById(R.id.cv_hasil);
        cv_jarak = findViewById(R.id.cv_jarak);
        cv_knn = findViewById(R.id.cv_knn);
        tv_jarak = findViewById(R.id.jarakData);
        tv_knn = findViewById(R.id.knn);
        btn_proses = findViewById(R.id.proses);



        btn_proses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_jarak.setText("");
                tv_knn.setText("");
                if (et_pl.getText().toString().equals("") || et_pw.getText().toString().equals("") || et_sl.getText().toString().equals("") || et_sw.getText().toString().equals("") || et_k.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this, "Isi semua data terlebih dahulu", Toast.LENGTH_SHORT).show();
                }else{
                    IrisFile irisFile = new IrisFile("iris.txt",getApplicationContext());

                    List<Iris> irisDataset =  irisFile.getIrisDataList();


                    Iris newIris = new Iris(Double.parseDouble(et_sl.getText().toString()),Double.parseDouble(et_sw.getText().toString()),Double.parseDouble(et_pl.getText().toString()),Double.parseDouble(et_pw.getText().toString()),null);

                    newIris.setIrisType(getIrisType(Integer.parseInt(et_k.getText().toString()), irisDataset, newIris));

                    //System.out.println(newIris.toString());
                    cv_hasil.setVisibility(View.VISIBLE);
                    cv_jarak.setVisibility(View.VISIBLE);
                    cv_knn.setVisibility(View.VISIBLE);
                    tv_hasil.setText(newIris.toString());


                }
            }
        });



    }

    private double getDistance(double[] p, double[] k) {
        int dimension = k.length;
        double distance = 0;

        for (int i = 0; i < dimension; i++) {
            distance += Math.pow(Math.abs(k[i] - p[i]), 2);

            tv_jarak.append(Math.pow(Math.abs(k[i] - p[i]), 2)+" ("+k[i]+"-"+p[i]+")\n");

        }

        tv_jarak.append("Hasil jarak (akar) "+Math.sqrt(distance) +"\n");



        return Math.sqrt(distance);
    }


    public IrisType getIrisType(int k, List<Iris> dataset, Iris newIris) {
        Map<Double, Iris> neighbourDistance = new TreeMap<>();

        for (Iris iris : dataset) {
            neighbourDistance.put(getDistance(newIris.getAllSize(), iris.getAllSize()), iris);
            tv_jarak.append(iris+"\n\n");
        }


        int counter = 0;
        Map<IrisType, Integer> irisTypes = new TreeMap<>();

        for (Map.Entry<Double, Iris> neighbour : neighbourDistance.entrySet()) {
            if (counter == k) break;

            irisTypes.put(neighbour.getValue().getIrisType(),
                    (irisTypes.get(neighbour.getValue().getIrisType()) == null ?
                            0 : irisTypes.get(neighbour.getValue().getIrisType())) + 1);
            tv_knn.append(neighbour.toString()+"\n\n");

            counter++;
        }

        return irisTypes.entrySet().iterator().next().getKey();
    }
}
