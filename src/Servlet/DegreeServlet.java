package Servlet;


import Centrality.Degree;
import Entity.DegreeE;
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

@WebServlet(name = "degreeServlet", urlPatterns = "/degreeServlet")
public class DegreeServlet extends HttpServlet {
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

        Degree degree = new Degree();
        HashMap<String, Double> IDC = degree.inDegree(graph);
        HashMap<String, Double> ODC = degree.outDegree(graph);
        HashMap<String, Double> NIDC = degree.normalization(IDC);
        HashMap<String, Double> NODC = degree.normalization(ODC);

        List<DegreeE> total = new ArrayList<>();

        SortMap sortMap = new SortMap();
        if (result.length != 0) {
            String enlarge = req.getParameter("enlarge");

            if (enlarge == null || enlarge.length() == 0) {
                enlarge = "100";
            }

            req.setAttribute("enlarge", enlarge);
            req.setAttribute("data", result);
            req.setAttribute("NIDC", sortMap.sortMap(NIDC));
            req.setAttribute("NODC", sortMap.sortMap(NODC));

            String sort = req.getParameter("sort");
            if (sort == null || sort.length() == 0) {
                for (String key : IDC.keySet()) {
                    DegreeE degreeE = new DegreeE();
                    degreeE.setName(key);
                    degreeE.setIdc(IDC.get(key));
                    degreeE.setOdc(ODC.get(key));
                    degreeE.setNidc(NIDC.get(key));
                    degreeE.setNodc(NODC.get(key));
                    total.add(degreeE);
                }
            } else {
                if (sort.equals("1")) {
                    LinkedHashMap<String, Double> sort_IDC = sortMap.sortMap(IDC);
                    for (String key : sort_IDC.keySet()) {
                        DegreeE degreeE = new DegreeE();
                        degreeE.setName(key);
                        degreeE.setIdc(IDC.get(key));
                        degreeE.setOdc(ODC.get(key));
                        degreeE.setNidc(NIDC.get(key));
                        degreeE.setNodc(NODC.get(key));
                        total.add(degreeE);
                    }
                } else if (sort.equals("2")) {
                    LinkedHashMap<String, Double> sort_NIDC = sortMap.sortMap(NIDC);
                    for (String key : sort_NIDC.keySet()) {
                        DegreeE degreeE = new DegreeE();
                        degreeE.setName(key);
                        degreeE.setIdc(IDC.get(key));
                        degreeE.setOdc(ODC.get(key));
                        degreeE.setNidc(NIDC.get(key));
                        degreeE.setNodc(NODC.get(key));
                        total.add(degreeE);
                    }
                } else if (sort.equals("3")) {
                    LinkedHashMap<String, Double> sort_ODC = sortMap.sortMap(ODC);
                    for (String key : sort_ODC.keySet()) {
                        DegreeE degreeE = new DegreeE();
                        degreeE.setName(key);
                        degreeE.setIdc(IDC.get(key));
                        degreeE.setOdc(ODC.get(key));
                        degreeE.setNidc(NIDC.get(key));
                        degreeE.setNodc(NODC.get(key));
                        total.add(degreeE);
                    }
                } else if (sort.equals("4")) {
                    LinkedHashMap<String, Double> sort_NODC = sortMap.sortMap(NODC);
                    for (String key : sort_NODC.keySet()) {
                        DegreeE degreeE = new DegreeE();
                        degreeE.setName(key);
                        degreeE.setIdc(IDC.get(key));
                        degreeE.setOdc(ODC.get(key));
                        degreeE.setNidc(NIDC.get(key));
                        degreeE.setNodc(NODC.get(key));
                        total.add(degreeE);
                    }
                } else {
                    for (String key : ODC.keySet()) {
                        DegreeE degreeE = new DegreeE();
                        degreeE.setName(key);
                        degreeE.setIdc(IDC.get(key));
                        degreeE.setOdc(ODC.get(key));
                        degreeE.setNidc(NIDC.get(key));
                        degreeE.setNodc(NODC.get(key));
                        total.add(degreeE);
                    }
                }
            }
            req.setAttribute("total", total);
        } else
            req.setAttribute("msg", "读取excel错误");
        req.getRequestDispatcher("/degree.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
