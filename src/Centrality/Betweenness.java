package Centrality;

import Graph.GraphAdjMatrix;

import java.util.*;

public class Betweenness{

    public HashMap<String,Double> centrality(GraphAdjMatrix graph) {
        HashMap<String,Double> map = new HashMap<>();

        int len = graph.numOfVexs;// 顶点数组长度，代表顶点个数
        for (int i = 0;i < len;i++){// i代表当前顶点索引
            double a = 0.0;// 某一顶点到其它顶点的所有最短路径数目
            double b = 0.0;// 某一顶点到其它顶点的所有最短路径中包括当前顶点的数目
            double centrality = 0.0;// 中间中心度
            for(int j = 0;j < len;j++){
                if(j!=i) {// 不遍历当前顶点到其它顶点的最短路径
                    HashMap<String,Double> hashMap = graph.dijkstra(j);
                    a = hashMap.size();
                    for (String p : hashMap.keySet()) {
                        System.out.println(p);
                        if (p.contains(graph.vexs[i].toString())) {
                            // 当前顶点在当前最短路径时中心度+1
                            b++;
                        }
                    }
                }
            }
            centrality = b / a;

            map.put(graph.vexs[i].toString(),centrality);
        }

        return map;
    }

    public HashMap<String,Double> normalization(HashMap<String,Double> source){
        HashMap<String,Double> result = new HashMap<>();

        int len = source.size();// 待归一化的哈希表长度，代表图的顶点个数
        double max = len * (len - 1);// 最大中间中心度

        for (String key : source.keySet()){
            result.put(key,2 * source.get(key)/max);
        }
        return result;
    }
}
