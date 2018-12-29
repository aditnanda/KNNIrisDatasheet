package com.nand_project.www.k_nnirisdatashet.KNN;

import android.content.Context;

import com.nand_project.www.k_nnirisdatashet.Model.DataSet;
import com.nand_project.www.k_nnirisdatashet.Model.Jarak;

import java.util.*;

public class Knn {

    public ArrayList<Jarak> hasilJarak =new ArrayList<>();
    public ArrayList<DataSet> datasetIris= new ArrayList<>();

    Context context;

    public Knn(Context mContext){
        context = mContext;
    }


    private double getDistance(double[] p, double[] k) {
        int dimension = p.length;
        double distance = 0;

        for (int i = 0; i < dimension; i++) {
            distance += Math.pow(Math.abs(p[i] - k[i]), 2);
            Jarak jarak = new Jarak();
            jarak.setHasil(Math.pow(Math.abs(p[i] - k[i]), 2)+"");
            hasilJarak.add(jarak);


        }

        for (int i =0;i<hasilJarak.size();i++){
            //Log.d("jarak",hasilJarak.get(i).getHasil()+"");
        }

        return Math.sqrt(distance);
    }


    public IrisType getIrisType(int k, List<Iris> dataset, Iris newIris) {
        Map<Double, Iris> neighbourDistance = new TreeMap<>();

        for (Iris iris : dataset) {
            neighbourDistance.put(getDistance(newIris.getAllSize(), iris.getAllSize()), iris);
            DataSet dataSet = new DataSet();
            dataSet.setNama(iris+"");
            datasetIris.add(dataSet);
        }


        int counter = 0;
        Map<IrisType, Integer> irisTypes = new TreeMap<>();

        for (Map.Entry<Double, Iris> neighbour : neighbourDistance.entrySet()) {
            if (counter == k) break;

            irisTypes.put(neighbour.getValue().getIrisType(),
                    (irisTypes.get(neighbour.getValue().getIrisType()) == null ?
                            0 : irisTypes.get(neighbour.getValue().getIrisType())) + 1);

            counter++;
        }

        return irisTypes.entrySet().iterator().next().getKey();
    }

    public ArrayList<Jarak> getHasilJarak() {
        return hasilJarak;
    }

    public ArrayList<DataSet> getDatasetIris() {
        return datasetIris;
    }

}
