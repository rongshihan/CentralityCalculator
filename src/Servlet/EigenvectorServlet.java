package Servlet;

import Centrality.Eigenvector;
import Entity.EigenvectorE;
import Tool.ReadExcel;
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

@WebServlet(name = "eigenvectorServlet",urlPatterns = "/eigenvectorServlet")
public class EigenvectorServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String filePath = session.getAttribute("filePath").toString();

        ReadExcel readExcel = new ReadExcel();
        String[][] result = readExcel.Read(filePath);

        Eigenvector eigenvector = new Eigenvector();
        HashMap<String,Double> EC = eigenvector.centrality(result);

        List<EigenvectorE> total = new ArrayList<>();

        SortMap sortMap = new SortMap();
        if(result.length != 0){
            String enlarge = req.getParameter("enlarge");

            if(enlarge == null || enlarge.length() == 0){
                enlarge = "100";
            }

            req.setAttribute("enlarge",enlarge);
            req.setAttribute("data", result);
            req.setAttribute("EC",sortMap.sortMap(EC));

            String sort = req.getParameter("sort");
            if(sort == null || sort.length() == 0){
                for (String key : EC.keySet()) {
                    EigenvectorE eigenvectorE = new EigenvectorE();
                    eigenvectorE.setName(key);
                    eigenvectorE.setEc(EC.get(key));
                    total.add(eigenvectorE);
                }
            }
            else {
                if (sort.equals("1")) {
                    LinkedHashMap<String, Double> sort_EC = sortMap.sortMap(EC);
                    for (String key : sort_EC.keySet()) {
                        EigenvectorE eigenvectorE = new EigenvectorE();
                        eigenvectorE.setName(key);
                        eigenvectorE.setEc(sort_EC.get(key));
                        total.add(eigenvectorE);
                    }
                } else {
                    for (String key : EC.keySet()) {
                        EigenvectorE eigenvectorE = new EigenvectorE();
                        eigenvectorE.setName(key);
                        eigenvectorE.setEc(EC.get(key));
                        total.add(eigenvectorE);
                    }
                }
            }
            req.setAttribute("total",total);
        }
        else
            req.setAttribute("msg","读取excel错误");
        req.getRequestDispatcher("/eigenvector.jsp").forward(req,resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }
}
