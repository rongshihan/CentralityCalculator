package Graph;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 图的邻接矩阵实现
 * @param <E>
 */
public class GraphAdjMatrix<E> implements IGraph<E> {
    public E[] vexs;// 存储图的顶点的一维数组
    public double[][] edges;// 存储图的边的二维数组
    public int numOfVexs;// 顶点的实际数量
    public int maxNumOfVexs;// 顶点的最大数量
    private boolean[] visited;// 判断顶点是否被访问过

    @SuppressWarnings("unchecked")
    public GraphAdjMatrix(int maxNumOfVexs, Class<E> type) {
        this.maxNumOfVexs = maxNumOfVexs;
        edges = new double[maxNumOfVexs][maxNumOfVexs];
        vexs = (E[]) Array.newInstance(type, maxNumOfVexs);
    }

    // 得到顶点的数目
    public int getNumOfVertex() {
        return numOfVexs;
    }

    // 插入顶点
    public boolean insertVex(E v) {
        if (numOfVexs >= maxNumOfVexs)
            return false;
        vexs[numOfVexs++] = v;
        return true;
    }

    // 删除顶点
    public boolean deleteVex(E v) {
        for (int i = 0; i < numOfVexs; i++) {
            if (vexs[i].equals(v)) {
                if (numOfVexs - 1 - i >= 0)
                    System.arraycopy(vexs, i + 1, vexs, i, numOfVexs - 1 - i);
                vexs[numOfVexs - 1] = null;
                for (int col = i; col < numOfVexs - 1; col++) {
                    if (numOfVexs >= 0)
                        System.arraycopy(edges[col + 1], 0, edges[col], 0, numOfVexs);
                }
                for (int row = i; row < numOfVexs - 1; row++) {
                    for (int col = 0; col < numOfVexs; col++) {
                        edges[col][row] = edges[col][row + 1];
                    }
                }
                numOfVexs--;
                return true;
            }
        }
        return false;
    }

    // 定位顶点的位置
    public int indexOfVex(E v) {
        for (int i = 0; i < numOfVexs; i++) {
            if (vexs[i].equals(v)) {
                return i;
            }
        }
        return -1;
    }

    // 定位指定位置的顶点
    public E valueOfVex(int v) {
        if (v < 0 ||v >= numOfVexs )
            return null;
        return vexs[v];
    }

    // 插入边
    public boolean insertEdge(int v1, int v2, Double weight) {
        if (v1 < 0 || v2 < 0 || v1 >= numOfVexs || v2 >= numOfVexs)
            throw new ArrayIndexOutOfBoundsException();
        edges[v1][v2] = weight;
        //edges[v2][v1] = weight;
        return true;
    }

    // 删除边
    public boolean deleteEdge(int v1, int v2) {
        if (v1 < 0 || v2 < 0 || v1 >= numOfVexs || v2 >= numOfVexs)
            throw new ArrayIndexOutOfBoundsException();
        edges[v1][v2] = 0;
        //edges[v2][v1] = 0;
        return true;
    }

    // 查找边
    public double getEdge(int v1, int v2){
        if (v1 < 0 || v2 < 0 || v1 >= numOfVexs || v2 >= numOfVexs)
            throw new ArrayIndexOutOfBoundsException();
        return edges[v1][v2];
    }

    // 深度优先搜索遍历
    public String depthFirstSearch(int v) {
        if (v < 0 || v >= numOfVexs)
            throw new ArrayIndexOutOfBoundsException();
        visited = new boolean[numOfVexs];
        StringBuilder sb = new StringBuilder();
        Stack<Integer> stack = new Stack<>();
        stack.push(v);
        visited[v] = true;
        while (!stack.isEmpty()) {
            v = stack.pop();
            sb.append(vexs[v]).append(",");
            for (int i = numOfVexs - 1; i >= 0; i--) {
                if ((edges[v][i] != 0 && edges[v][i] != Double.MAX_VALUE)
                        && !visited[i]) {
                    stack.push(i);
                    visited[i] = true;
                }
            }
        }
        return sb.length() > 0 ? sb.substring(0, sb.length() - 1) : null;
    }

    // 广度优先搜索遍历
    public String breadFirstSearch(int v) {
        if (v < 0 || v >= numOfVexs)
            throw new ArrayIndexOutOfBoundsException();
        visited = new boolean[numOfVexs];
        StringBuilder sb = new StringBuilder();
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(v);
        visited[v] = true;
        while (!queue.isEmpty()) {
            v = queue.poll();
            sb.append(vexs[v]).append(",");
            for (int i = 0; i < numOfVexs; i++) {
                if ((edges[v][i] != 0 && edges[v][i] != Double.MAX_VALUE)
                        && !visited[i]) {
                    queue.offer(i);
                    visited[i] = true;
                }
            }
        }
        return sb.length() > 0 ? sb.substring(0, sb.length() - 1) : null;
    }

    //求顶点的入度
    public int inDegree(int v) {
        if (v < 0 || v >= numOfVexs)
            throw new ArrayIndexOutOfBoundsException();
        visited = new boolean[numOfVexs];
        int degree = 0;
        for (int i = 0; i < numOfVexs; i++) {
            if ((edges[i][v] != 0.0 && edges[i][v] != Double.MAX_VALUE)
                    && !visited[i]) {
                degree++;
                visited[i] = true;
            }
        }
        return degree;
    }

    //求顶点的入度（带边权值）
    public double inWeight(int v) {
        if (v < 0 || v >= numOfVexs)
            throw new ArrayIndexOutOfBoundsException();
        visited = new boolean[numOfVexs];
        double degree = 0;
        for (int i = 0; i < numOfVexs; i++) {
            if ((edges[i][v] != 0.0 && edges[i][v] != Double.MAX_VALUE)
                    && !visited[i]) {
                degree += edges[i][v];
                visited[i] = true;
            }
        }
        return degree;
    }

    //求顶点的出度
    public int outDegree(int v) {
        if (v < 0 || v >= numOfVexs)
            throw new ArrayIndexOutOfBoundsException();
        visited = new boolean[numOfVexs];
        int degree = 0;
        for (int i = 0; i < numOfVexs; i++) {
            if ((edges[v][i] != 0.0 && edges[v][i] != Double.MAX_VALUE)
                    && !visited[i]) {
                degree++;
                visited[i] = true;
            }
        }
        return degree;
    }

    //求顶点的出度（带边权值）
    public double outWeight(int v) {
        if (v < 0 || v >= numOfVexs)
            throw new ArrayIndexOutOfBoundsException();
        visited = new boolean[numOfVexs];
        double degree = 0;
        for (int i = 0; i < numOfVexs; i++) {
            if ((edges[v][i] != 0.0 && edges[v][i] != Double.MAX_VALUE)
                    && !visited[i]) {
                degree+=edges[v][i];
                visited[i] = true;
            }
        }
        return degree;
    }

    // 实现Dijkstra算法
    public HashMap<String,Double> dijkstra(int v) {
        if (v < 0 || v >= numOfVexs)
            throw new ArrayIndexOutOfBoundsException();
        boolean[] st = new boolean[numOfVexs];// 默认初始为false

        HashMap<String,Double> map = new HashMap<>();// 存放源点到其它点的最短路径和距离键值对

        StringBuilder[] path = new StringBuilder[numOfVexs];// 存放源点到其它点的最短路径组合
        double[] distance = new double[numOfVexs];// 存放源点到其它点的矩离

        for (int i = 0; i < numOfVexs; i++)
            for (int j = 0; j < edges[i].length; j++) {
                if (edges[i][j] == 0.0) {
                    edges[i][j] = Double.MAX_VALUE;// 边权值为0的边全置为无穷大
                }
            }

        for (int i = 0; i < numOfVexs; i++) {
            distance[i] = edges[v][i];// 初始化源点到其它顶点所有路径长度为无穷大
            path[i] = new StringBuilder().append(vexs[v]);// 初始化最短路径的源点
        }
        st[v] = true;
        // 处理从源点到其余顶点的最短路径
        for (int i = 0; i < numOfVexs; ++i) {
            double min = Double.MAX_VALUE;
            int index=-1;

            // 比较从源点到其余顶点的路径长度
            for (int j = 0; j < numOfVexs; ++j) {
                // 从源点到j顶点的最短路径还没有找到
                if (!st[j]) {
                    // 从源点到j顶点的路径长度最小
                    if (distance[j] < min) {
                        index = j;
                        min = distance[j];
                        path[j].append(vexs[index]);
                    }
                }
            }

            //找到源点到索引为index顶点的最短路径长度
            if(index!=-1)
                st[index] = true;
            // 更新当前最短路径及距离
            for (int w = 0; w < numOfVexs; w++) {
                if (!st[w] && index != -1) {
                    if (edges[index][w] != Double.MAX_VALUE
                            && (min + edges[index][w] < distance[w])) {
                        distance[w] = min + edges[index][w];
                        path[w].append(vexs[index]);
                    }
                }
            }

            map.put(path[i].toString(),distance[i]);
        }
        return map;
    }

    public double[] dijkstra1(int v) {
        if (v < 0 || v >= numOfVexs)
            throw new ArrayIndexOutOfBoundsException();
        boolean[] st = new boolean[numOfVexs];// 默认初始为false
        double[] distance = new double[numOfVexs];// 存放源点到其他点的矩离

        for (int i = 0; i < numOfVexs; i++)
            for (int j = i + 1; j < numOfVexs; j++) {
                if (edges[i][j] == 0) {
                    edges[i][j] = Double.MAX_VALUE;
                    edges[j][i] = Double.MAX_VALUE;
                }
            }
        for (int i = 0; i < numOfVexs; i++) {
            distance[i] = edges[v][i];
        }
        st[v] = true;
        // 处理从源点到其余顶点的最短路径
        for (int i = 0; i < numOfVexs; ++i) {
            double min = Double.MAX_VALUE;
            int index=-1;
            // 比较从源点到其余顶点的路径长度
            for (int j = 0; j < numOfVexs; ++j) {
                // 从源点到j顶点的最短路径还没有找到
                if (st[j]==false) {
                    // 从源点到j顶点的路径长度最小
                    if (distance[j] < min) {
                        index = j;
                        min = distance[j];
                    }
                }
            }
            //找到源点到索引为index顶点的最短路径长度
            if(index!=-1)
                st[index] = true;
            // 更新当前最短路径及距离
            for (int w = 0; w < numOfVexs; w++)
                if (st[w] == false) {
                    if (edges[index][w] != Integer.MAX_VALUE
                            && (min + edges[index][w] < distance[w]))
                        distance[w] = min + edges[index][w];
                }
            System.out.println(distance[i]);
        }
        return distance;
    }

}
