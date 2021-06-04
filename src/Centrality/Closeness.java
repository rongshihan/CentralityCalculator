package Centrality;

import Graph.GraphAdjMatrix;

import java.util.*;

public class Closeness {

    public HashMap<String, Double> centrality(GraphAdjMatrix graph) {
        HashMap<String, Double> map = new HashMap<>();

        int len = graph.numOfVexs;// 顶点数组长度，代表顶点个数
        for (int i = 0; i < len; i++) {// i代表当前顶点索引
            double total = 0.0;// 最短路径长度和
            double centrality = 0.0;// 紧密中心度

            HashMap<String, Double> hashMap = graph.dijkstra(i);
            for (String p : hashMap.keySet()) {
                //System.out.print(p + ":");
                Double distance = hashMap.get(p);
                if (distance != Double.MAX_VALUE) {
                    //System.out.print(distance + " ");
                    // 最短路径长度不为无穷时计算长度和
                    total += distance;
                }
            }
            System.out.println();

            double[] a = graph.dijkstra1(i);
            centrality = 1 / total;

            map.put(graph.vexs[i].toString(), centrality);
        }

        return map;
    }

    public HashMap<String, Double> normalization(HashMap<String, Double> source) {
        HashMap<String, Double> result = new HashMap<>();

        double max = source.size() - 1;// 最大接近中心度
        for (String key : source.keySet()) {
            result.put(key, max * source.get(key));
        }
        return result;
    }

}
