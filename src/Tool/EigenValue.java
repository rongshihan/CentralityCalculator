package Tool;

import Centrality.Eigenvector;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * 求矩阵的最大特征值和对应的特征向量
 */
public class EigenValue {

    // 平均随机一致性指标
    private double[] RI = {
            0.00, 0.00, 0.52, 0.89, 1.12,
            1.26, 1.36, 1.41, 1.46, 1.49,
            1.52, 1.24, 1.56, 1.58, 1.59,
            1.5943,1.6064,1.6133,1.6207,1.6292,
            1.6385,1.6403,1.6462,1.6497,1.6556,
            1.6587,1.6631,1.6670,1.6693,1.6724
    };

    // 随机一致性比率
    private double CR = 0.0;

    private double lamta = 0.0;

    public double[] weight(double[][] matrix) {
        int len = matrix.length;

        double[] w0 = new double[len];// 初始向量Wk

        for (int i = 0; i < len; i++) {
            w0[i] = 1.0 / len;
        }

        double[] w1 = new double[len];// 一般向量W（k+1）

        double[] w2 = new double[len];// W（k+1）的归一化向量

        double sum = 1.0;

        double d = 1.0;

        double delt = 0.00001;// 误差

        while (d > delt) {
            d = 0.0;
            sum = 0;

            // 获取向量
            for (int i = 0; i < len; i++) {
                double temp = 0.0;
                for (int j = 0; j < len; j++) {
                    temp += matrix[i][j] * w0[j];
                }
                w1[i] = temp;
                sum += w1[i];
            }

            // 向量归一化
            for (int i = 0; i < len; i++) {
                w2[i] = w1[i] / sum;

                d = Math.max(Math.abs(w2[i] - w0[i]), d);// 最大差值

                w0[i] = w2[i];// 用于下次迭代使用
            }
        }

        // 计算矩阵最大特征值lamta，一致性指标CI，随机一致性指标RI
        lamta = 0.0;

        for (int i = 0; i < len; i++) {
            if(w1[i] != 0 && w0[i] != 0) {
                lamta += w1[i] / (len * w0[i]);
            }
        }

        double CI = (lamta - len) / (len - 1);

        if (RI[len - 1] != 0) {
            CR = CI / RI[len - 1];
        }


        // 四舍五入处理
        lamta = round(lamta, 3);
        CI = Math.abs(round(CI, 3));
        CR =  Math.abs(round(CR, 3));

        for (int i = 0; i < len; i++) {
            w0[i] = round(w0[i], 18);
            w1[i] = round(w1[i], 18);
            w2[i] = round(w2[i], 18);
        }

        return w2;
    }

    public double getLamta(){
        return lamta;
    }

    private double round(double value, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(value));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static void main(String[] args) {
        EigenValue eigenValue = new EigenValue();
        double[][] a = {
                {0,1,0,1,1,0,0},
                {1,0,1,0,0,0,0},
                {0,1,0,0,0,1,1},
                {1,0,0,0,0,0,0},
                {1,0,0,0,0,0,0},
                {0,0,1,0,0,0,0},
                {0,0,1,0,0,0,0}
        };
        System.out.println(Arrays.toString(eigenValue.weight(a)));
    }
}
