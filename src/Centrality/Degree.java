package Centrality;

import Graph.GraphAdjMatrix;

import java.util.*;

public class Degree {

    public HashMap<String,Double> inDegree(GraphAdjMatrix graph){
        HashMap<String,Double> map = new HashMap<>();// 存放（节点名，度中心度）的键值对

        int len = graph.numOfVexs;
        for (int i = 0;i < len;i++){
            String name = graph.vexs[i].toString();// 顶点名称
            Double indegree = (double) graph.inDegree(i);// 入度

            map.put(name,indegree);
        }
        return map;
    }

    public HashMap<String,Double> inWeight(GraphAdjMatrix graph){
        HashMap<String,Double> map = new HashMap<>();

        int len = graph.numOfVexs;
        for (int i = 0;i < len;i++){
            String name = graph.vexs[i].toString();
            Double indegree = graph.inWeight(i);

            map.put(name,indegree);
        }
        return map;
    }

    public HashMap<String,Double> outDegree(GraphAdjMatrix graph){
        HashMap<String,Double> map = new HashMap<>();

        int len = graph.numOfVexs;
        for (int i = 0;i < len;i++){
            String name = graph.vexs[i].toString();
            Double outdegree = (double) graph.outDegree(i);

            map.put(name,outdegree);
        }
        return map;
    }

    public HashMap<String,Double> outWeight(GraphAdjMatrix graph){
        HashMap<String,Double> map = new HashMap<>();

        int len = graph.numOfVexs;
        for (int i = 0;i < len;i++){
            String name = graph.vexs[i].toString();
            Double outdegree = graph.outWeight(i);

            map.put(name,outdegree);
        }
        return map;
    }

    public HashMap<String,Double> normalization(HashMap<String,Double> source){
        HashMap<String,Double> result = new HashMap<>();

        int len = source.size();// 待归一化的哈希表长度，代表图的顶点个数
        double max = len - 1;// 最大入（出）度中心度

        for (String key : source.keySet()){
            result.put(key,source.get(key)/max);
        }
        return result;
    }

}
