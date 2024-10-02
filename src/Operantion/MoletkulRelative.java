package Operantion;

import Database.OperationDatabase.AmbilData;
import Util.Senyawa;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoletkulRelative {
    public HashMap<String, Double> VitungMR(String dataSenyawa){
        HashMap<String, Double> senyawa = new HashMap<>();
        char[] arraySenyawa = dataSenyawa.toCharArray();
        int i = 0;
        String atom = "";
        while (i < arraySenyawa.length){
            if(Character.isUpperCase(arraySenyawa[i])){
                atom = String.valueOf(arraySenyawa[i]);
                i++;
                while (i < arraySenyawa.length && Character.isLowerCase(arraySenyawa[i])){
                    atom += String.valueOf(arraySenyawa[i]);
                    i++;
                }
                int count = 1;
                while (i < arraySenyawa.length && Character.isDigit(arraySenyawa[i])){
                    count *= Double.parseDouble(String.valueOf(arraySenyawa[i]));
                    i++;
                }
                senyawa.put(atom, senyawa.getOrDefault(atom, 0.0) + count);
            } else if(arraySenyawa[i] == '('){ //i == 2
                i++;
                String subSenyawa = "";
                while (i < arraySenyawa.length && arraySenyawa[i] != ')'){
                    subSenyawa += String.valueOf(arraySenyawa[i]);
                    i++;
                }
                i++;
                int count = 1;
                while (i < arraySenyawa.length && Character.isDigit(arraySenyawa[i])){
                    count *= Double.parseDouble(String.valueOf(arraySenyawa[i]));
                    i++;
                }
                HashMap<String, Double> map2 = subSenyawa(subSenyawa);
                for(Map.Entry<String, Double> maping : map2.entrySet()){
                    maping.setValue(maping.getValue() * count);
                }
                for(Map.Entry<String, Double> entry : map2.entrySet()){
                    senyawa.put(entry.getKey(),  entry.getValue());
                }
            }
        }
        return senyawa;
    }

    public static HashMap<String, Double> subSenyawa(String senyawa2){
        HashMap<String, Double> senyawa = new HashMap<>();
        char[] arraySenyawa = senyawa2.toCharArray();
        int i = 0;
        String atom = "";
        while (i < arraySenyawa.length){
            if(Character.isUpperCase(arraySenyawa[i])){
                atom = String.valueOf(arraySenyawa[i]);
                i++;
                while (i < arraySenyawa.length && Character.isLowerCase(arraySenyawa[i])){
                    atom += String.valueOf(arraySenyawa[i]);
                    i++;
                }
                int count = 1;
                while (i < arraySenyawa.length && Character.isDigit(arraySenyawa[i])){
                    count *= Double.parseDouble(String.valueOf(arraySenyawa[i]));
                    i++;
                }
                senyawa.put(atom, senyawa.getOrDefault(atom, 0.0) + count);
            } else if(arraySenyawa[i] == '('){
                i++;
                String subSenyawa = "";
                while (i < arraySenyawa.length && arraySenyawa[i] != ')'){
                    subSenyawa += String.valueOf(arraySenyawa[i]);
                    i++;
                }
                i++;
                int temp = 1;
                while (i < arraySenyawa.length && Character.isDigit(arraySenyawa[i])){
                    temp *= Double.parseDouble(String.valueOf(arraySenyawa[i]));
                    i++;
                }
                HashMap<String, Double> map2 = new HashMap<>();
                for(Map.Entry<String, Double> maping : map2.entrySet()){
                    maping.setValue(maping.getValue() * temp);
                }
                for(Map.Entry<String, Double> entry : map2.entrySet()){
                    map2.put(entry.getKey(), map2.getOrDefault(entry.getKey(), 0.0) + entry.getValue());

                }
            }
        }
        return senyawa;
    }

    public  double hitungMassaMolekul(HashMap<String, Double> senyawa) throws SQLException, ClassNotFoundException {
        double angka = 0;
        String angkaMolekul;

        for(Map.Entry<String, Double> entryMap : senyawa.entrySet()){
            List<Senyawa> lableSenyawa = new ArrayList<>();
            AmbilData ambilData = new AmbilData();
            List<Senyawa> data = ambilData.fetchAll(entryMap.getKey()); // Return LIst
            angkaMolekul = data.toString().replaceAll("[\\[\\]]", "");
            angka += (Double.parseDouble(angkaMolekul)* entryMap.getValue());
        }
        return angka;
    }


}
