package Graph;

import java.util.HashMap;

public interface IGraph<E> {
    int getNumOfVertex();//获取顶点的个数
    boolean insertVex(E v);//插入顶点
    boolean deleteVex(E v);//删除顶点
    int indexOfVex(E v);//定位顶点的位置
    E valueOfVex(int v);// 定位指定位置的顶点
    boolean insertEdge(int v1, int v2, Double weight);//插入边
    boolean deleteEdge(int v1, int v2);//删除边
    double getEdge(int v1, int v2);//查找边
    String depthFirstSearch(int v );//深度优先搜索遍历
    String breadFirstSearch(int v );//广度优先搜索遍历
    int inDegree(int v);//求顶点的入度
    int outDegree(int v);//求顶点的出度
    HashMap<String,Double> dijkstra(int v);//查找源点到其它顶点的路径
}
