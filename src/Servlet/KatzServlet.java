package Servlet;

import Centrality.Katz;
import Entity.KatzE;
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

@WebServlet(name = "katzServlet",urlPatterns = "/katzServlet")
public class KatzServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String filePath = session.getAttribute("filePath").toString();

        ReadExcel readExcel = new ReadExcel();
        String[][] result = readExcel.Read(filePath);

        String alpha = req.getParameter("alpha");
        String beta = req.getParameter("beta");

        if(alpha == null || alpha.length() == 0){
            alpha = "0.0";
        }

        if(beta == null || beta.length() == 0){
            beta = "0.0";
        }

        Katz katz = new Katz();
        HashMap<String, Double> KC = katz.centrality(result,Double.parseDouble(alpha),Double.parseDouble(beta));

        List<KatzE> total = new ArrayList<>();

        SortMap sortMap = new SortMap();
        if (result.length != 0) {
            String enlarge = req.getParameter("enlarge");

            if(enlarge == null || enlarge.length() == 0){
                enlarge = "100";
            }

            req.setAttribute("enlarge",enlarge);
            req.setAttribute("data", result);
            req.setAttribute("KC", sortMap.sortMap(KC));
            req.setAttribute("lamta",katz.getLamta());
            req.setAttribute("range",1/katz.getLamta());
            req.setAttribute("alpha",katz.getAlpha());
            req.setAttribute("beta",katz.getBeta());

            String sort = req.getParameter("sort");
            if(sort == null || sort.length() == 0){
                for (String key : KC.keySet()){
                    KatzE katzE = new KatzE();
                    katzE.setName(key);
                    katzE.setKc(KC.get(key));
                    total.add(katzE);
                }
            }
            else {
                if(sort.equals("1")){
                    LinkedHashMap<String,Double> sort_KC = sortMap.sortMap(KC);
                    for (String key : sort_KC.keySet()){
                        KatzE katzE = new KatzE();
                        katzE.setName(key);
                        katzE.setKc(sort_KC.get(key));
                        total.add(katzE);
                    }
                }
                else {
                    for (String key : KC.keySet()){
                        KatzE katzE = new KatzE();
                        katzE.setName(key);
                        katzE.setKc(KC.get(key));
                        total.add(katzE);
                    }
                }
            }
            req.setAttribute("total",total);
        }
        else
            req.setAttribute("msg","读取excel错误");
        req.getRequestDispatcher("/katz.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }
}
