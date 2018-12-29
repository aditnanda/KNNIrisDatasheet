package com.nand_project.www.k_nnirisdatashet.KNN;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class IrisFile {

    String DataSet = null;

    public IrisFile(String filename, Context context) {

        try {
            InputStream is = context.getAssets().open("iris.txt");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            DataSet = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    public List<Iris> getIrisDataList() {
        List<Iris> irisDataset = new ArrayList<>();

        try {
            Scanner scan = new Scanner(DataSet);
            String[] irisInfo;

            while (scan.hasNext()) {
                irisInfo = scan.nextLine().split(",");
                IrisType irisType;

                switch (irisInfo[4]) {
                    case "Iris-setosa":
                        irisType = IrisType.Iris_setosa; break;
                    case "Iris-versicolor":
                        irisType = IrisType.Iris_versicolor; break;
                    case "Iris-virginica":
                        default: irisType = IrisType.Iris_virginica; break;
                }

                irisDataset.add(new Iris(
                        Double.parseDouble(irisInfo[0]),
                        Double.parseDouble(irisInfo[1]),
                        Double.parseDouble(irisInfo[2]),
                        Double.parseDouble(irisInfo[3]),
                        irisType
                ));
            }

        } catch (Exception e) {
            e.getStackTrace();
        }

        return irisDataset;
    }

}
