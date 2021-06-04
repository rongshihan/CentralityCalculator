package Centrality;

import Tool.EigenValue;
import Tool.Matrix;

import java.util.HashMap;
import java.util.Random;

public class PageRank {

    // 两个参数值
    private double Alpha = 0.0;
    private double Beta = 0.0;

    private double lamta = 0.0;

    public HashMap<String,Double> centrality(String[][] result, double alpha, double beta){
        Matrix matrix = new Matrix();

        HashMap<String,Double> map = new HashMap<>();

        int len = result.length;

        String[] names = new String[len - 1];// 顶点数组

        double[][] centrality;// 中心度数组

        double[][] values = new double[len - 1][len - 1];// 数据矩阵

        // 准备顶点数组和数据矩阵
        for (int i = 1;i < len;i++){
            names[i - 1] = result[i][0];
            for (int j = 1;j < len;j++){
                values[i - 1][j - 1] = Double.parseDouble(result[i][j]);
            }
        }

        // 对数据矩阵做转置处理
        double[][] tMatrix = matrix.transpose(values);

        // 由数据矩阵求出度
        double[] outdegree = matrix.outDegree(values);

        // 构造关于出度的对角矩阵
        double[][] ODMatrix = new double[len - 1][len - 1];
        for (int i = 0;i < len - 1;i++){
            for (int j = 0;j < len - 1;j++){
                if(i == j){
                    ODMatrix[i][j] = outdegree[i];
                }
                else {
                    ODMatrix[i][j] = 0.0;
                }
            }
        }

        // 对角矩阵求逆
        double[][] inverse = matrix.inverse(ODMatrix);

        // 处理后的数据矩阵
        double[][] source = matrix.vectorMult(tMatrix,inverse);

        // 得到矩阵的最大特征值
        EigenValue eigenValue = new EigenValue();
        eigenValue.weight(source);
        lamta = eigenValue.getLamta();

        if(alpha == 0.0){
            // 随机一个0到1/lamta大小的小数
            alpha = new Random().nextDouble()*(1/lamta);
        }

        if(beta == 0.0){
            beta = 0.1;
        }

        Alpha = alpha;
        Beta = beta;

        // 构造一个与原矩阵等大小的单位矩阵
        double[][] iMatrix = new double[len - 1][len - 1];
        for (int i = 0;i < len - 1;i++){
            for (int j = 0;j < len - 1;j++){
                if(i == j){
                    iMatrix[i][j] = 1.0;
                }
                else {
                    iMatrix[i][j] = 0.0;
                }
            }
        }

        // 构造一个长度为数据矩阵行数的元素全1的向量
        double[][] iVector = new double[len - 1][1];
        for (int i = 0;i < len - 1;i++){
            iVector[i][0] = 1.0;
        }

        double[][] a = matrix.scalarMult(source,alpha);//a = α * source
        double[][] b = matrix.subtract(iMatrix,a);//b = iMatrix - a
        double[][] c = matrix.inverse(b);//c = b的逆
        double[][] d = matrix.scalarMult(c,beta);//d = β * c
        double[][] e = matrix.vectorMult(d,iVector);//e = d * iVector

        centrality = e;
        // 放入hashmap
        for (int i = 0;i < len - 1;i++){
            map.put(names[i],centrality[i][0]);
        }

        return map;
    }

    public double getAlpha(){
        return Alpha;
    }

    public double getBeta(){
        return Beta;
    }

    public double getLamta(){
        return lamta;
    }
}
