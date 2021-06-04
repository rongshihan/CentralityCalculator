package Servlet;

import Centrality.Closeness;
import Entity.ClosenessE;
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

@WebServlet(name = "closenessServlet",urlPatterns = "/closenessServlet")
public class ClosenessServlet extends HttpServlet {
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

        Closeness closeness = new Closeness();
        HashMap<String, Double> ICC = closeness.centrality(graph);
        //HashMap<String, Double> ODC = degree.Outdegree(graph);
        HashMap<String, Double> NICC = closeness.normalization(ICC);
        //HashMap<String, Double> NODC = degree.normalization(ODC);

        List<ClosenessE> total = new ArrayList<>();

        SortMap sortMap = new SortMap();
        if (result.length != 0) {
            String enlarge = req.getParameter("enlarge");

            if(enlarge == null || enlarge.length() == 0){
                enlarge = "100";
            }

            req.setAttribute("enlarge",enlarge);
            req.setAttribute("data", result);
            req.setAttribute("NICC", sortMap.sortMap(NICC));
            //req.setAttribute("NODC", degree.sortMap(NODC));

            String sort = req.getParameter("sort");
            if (sort == null || sort.length() == 0) {
                for (String key : ICC.keySet()) {
                    ClosenessE closenessE = new ClosenessE();
                    closenessE.setName(key);
                    closenessE.setIcc(ICC.get(key));
                    //degreeE.setOdc(ODC.get(key));
                    closenessE.setNicc(NICC.get(key));
                    //degreeE.setNodc(NODC.get(key));
                    total.add(closenessE);
                }
            }
            else {
                if(sort.equals("1")){
                    LinkedHashMap<String,Double> sort_ICC = sortMap.sortMap(ICC);
                    for (String key : sort_ICC.keySet()){
                        ClosenessE closenessE = new ClosenessE();
                        closenessE.setName(key);
                        closenessE.setIcc(ICC.get(key));
                        //closenessE.setOcc(IDC.get(key));
                        closenessE.setNicc(NICC.get(key));
                        //degreeE.setNodc(NODC.get(key));
                        total.add(closenessE);
                    }
                }
                else if (sort.equals("2")){
                    LinkedHashMap<String,Double> sort_NICC = sortMap.sortMap(NICC);
                    for (String key : sort_NICC.keySet()){
                        ClosenessE closenessE = new ClosenessE();
                        closenessE.setName(key);
                        closenessE.setIcc(ICC.get(key));
                        //degreeE.setOdc(ODC.get(key));
                        closenessE.setNicc(NICC.get(key));
                        //degreeE.setNodc(NODC.get(key));
                        total.add(closenessE);
                    }
                }
                else if (sort.equals("3")){
                    /*
                    LinkedHashMap<String,Double> sort_OCC = closeness.sortMap(OCC);
                    for (String key : sort_OCC.keySet()){
                        ClosenessE closenessE = new ClosenessE();
                        closenessE.setName(key);
                        closenessE.setIcc(ICC.get(key));
                        //closenessE.setOdc(ODC.get(key));
                        closenessE.setNicc(NICC.get(key));
                        //closenessE.setNodc(NODC.get(key));
                        total.add(closenessE);
                    }

                     */
                }
                else if (sort.equals("4")){
                    /*
                    LinkedHashMap<String,Double> sort_NOCC = closeness.sortMap(NOCC);
                    for (String key : sort_NOCC.keySet()){
                        ClosenessE closenessE = new ClosenessE();
                        closenessE.setName(key);
                        closenessE.setIcc(ICC.get(key));
                        //degreeE.setOdc(ODC.get(key));
                        closenessE.setNicc(NICC.get(key));
                        //degreeE.setNodc(NODC.get(key));
                        total.add(closenessE);
                    }

                     */
                }
                else {
                    for (String key : ICC.keySet()){
                        ClosenessE closenessE = new ClosenessE();
                        closenessE.setName(key);
                        closenessE.setIcc(ICC.get(key));
                        //degreeE.setOdc(ODC.get(key));
                        closenessE.setNicc(NICC.get(key));
                        //degreeE.setNodc(NODC.get(key));
                        total.add(closenessE);
                    }
                }
            }
            req.setAttribute("total",total);
        }
        else
            req.setAttribute("msg","读取excel错误");
        req.getRequestDispatcher("/closeness.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }
}
