package Centrality;

import Tool.EigenValue;

import java.util.*;

public class Eigenvector {

    public HashMap<String,Double> centrality(String[][] result){
        HashMap<String,Double> map = new HashMap<>();

        int len = result.length;

        String[] names = new String[len - 1];// 顶点数组

        double[] centrality;// 中心度数组

        double[][] values = new double[len - 1][len - 1];// 数据矩阵

        // 准备顶点数组和数据矩阵
        for (int i = 1;i < len;i++){
            names[i - 1] = result[i][0];
            for (int j = 1;j < len;j++){
                values[i - 1][j - 1] = Double.parseDouble(result[i][j]);
            }
        }

        // 计算特征向量
        EigenValue eigenValue = new EigenValue();
        centrality = eigenValue.weight(values);

        // 放入hashmap
        for (int i = 0;i < len - 1;i++){
            map.put(names[i],centrality[i]);
        }

        return map;
    }

}
