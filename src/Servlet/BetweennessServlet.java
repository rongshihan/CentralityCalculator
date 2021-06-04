package Servlet;

import Centrality.Betweenness;
import Entity.BetweennessE;
import Tool.ReadExcel;
import Graph.GraphAdjMatrix;
import Tool.SortMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@WebServlet(name = "betweennessServlet", urlPatterns = "/betweennessServlet")
public class BetweennessServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String filePath = session.getAttribute("filePath").toString();

        ReadExcel readExcel = new ReadExcel();
        String[][] result = readExcel.Read(filePath);

        GraphAdjMatrix graph = null;
        try {
            graph = new GraphAdjMatrix(result.length,
                    Class.forName("java.lang.String"));

            for (int i = 0; i < result.length; i++) {
                for (int j = 1; j < result[i].length; j++) {
                    if (i == 0) {
                        String name = result[i][j];
                        graph.insertVex(name);//插入顶点
                    } else {
                        double edge = Double.parseDouble(result[i][j]);
                        if (edge != 0.0) {
                            graph.insertEdge(i - 1, j - 1, edge);//插入边
                        }
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Betweenness betweenness = new Betweenness();
        HashMap<String, Double> BC = betweenness.centrality(graph);
        HashMap<String, Double> NBC = betweenness.normalization(BC);

        List<BetweennessE> total = new ArrayList<>();

        SortMap sortMap = new SortMap();
        if (result.length != 0) {
            String enlarge = req.getParameter("enlarge");

            if(enlarge == null || enlarge.length() == 0){
                enlarge = "100";
            }

            req.setAttribute("enlarge",enlarge);
            req.setAttribute("data", result);
            req.setAttribute("BC", sortMap.sortMap(BC));
            req.setAttribute("NBC", sortMap.sortMap(NBC));

            String sort = req.getParameter("sort");
            if(sort == null || sort.length() == 0){
                for (String key : BC.keySet()){
                    BetweennessE betweennessE = new BetweennessE();
                    betweennessE.setName(key);
                    betweennessE.setBc(BC.get(key));
                    betweennessE.setNbc(NBC.get(key));
                    total.add(betweennessE);
                }
            }
            else {
                if(sort.equals("1")){
                    LinkedHashMap<String,Double> sort_BC = sortMap.sortMap(BC);
                    for (String key : sort_BC.keySet()){
                        BetweennessE betweennessE = new BetweennessE();
                        betweennessE.setName(key);
                        betweennessE.setBc(sort_BC.get(key));
                        betweennessE.setNbc(NBC.get(key));
                        total.add(betweennessE);
                    }
                }
                else if (sort.equals("2")){
                    LinkedHashMap<String,Double> sort_NBC = sortMap.sortMap(NBC);
                    for (String key : sort_NBC.keySet()){
                        BetweennessE betweennessE = new BetweennessE();
                        betweennessE.setName(key);
                        betweennessE.setBc(BC.get(key));
                        betweennessE.setNbc(sort_NBC.get(key));
                        total.add(betweennessE);
                    }
                }
                else {
                    for (String key : NBC.keySet()){
                        BetweennessE betweennessE = new BetweennessE();
                        betweennessE.setName(key);
                        betweennessE.setBc(BC.get(key));
                        betweennessE.setNbc(NBC.get(key));
                        total.add(betweennessE);
                    }
                }
            }
            req.setAttribute("total",total);
        }
        else
            req.setAttribute("msg","读取excel错误");
        req.getRequestDispatcher("/betweenness.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
