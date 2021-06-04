package Servlet;

import Centrality.PageRank;
import Entity.PageRankE;
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

@WebServlet(name = "pageRankServlet",urlPatterns = "/pageRankServlet")
public class PageRankServlet extends HttpServlet {
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

        PageRank pageRank = new PageRank();
        HashMap<String, Double> PRC = pageRank.centrality(result,Double.parseDouble(alpha),Double.parseDouble(beta));

        List<PageRankE> total = new ArrayList<>();

        SortMap sortMap = new SortMap();
        if (result.length != 0) {
            String enlarge = req.getParameter("enlarge");

            if(enlarge == null || enlarge.length() == 0){
                enlarge = "100";
            }

            req.setAttribute("enlarge",enlarge);
            req.setAttribute("data", result);
            req.setAttribute("PRC", sortMap.sortMap(PRC));
            req.setAttribute("lamta",pageRank.getLamta());
            req.setAttribute("range",1/pageRank.getLamta());
            req.setAttribute("alpha",pageRank.getAlpha());
            req.setAttribute("beta",pageRank.getBeta());

            String sort = req.getParameter("sort");
            if(sort == null || sort.length() == 0){
                for (String key : PRC.keySet()){
                    PageRankE pageRankE = new PageRankE();
                    pageRankE.setName(key);
                    pageRankE.setPrc(PRC.get(key));
                    total.add(pageRankE);
                }
            }
            else {
                if(sort.equals("1")){
                    LinkedHashMap<String,Double> sort_PRC = sortMap.sortMap(PRC);
                    for (String key : sort_PRC.keySet()){
                        PageRankE pageRankE = new PageRankE();
                        pageRankE.setName(key);
                        pageRankE.setPrc(sort_PRC.get(key));
                        total.add(pageRankE);
                    }
                }
                else {
                    for (String key : PRC.keySet()){
                        PageRankE pageRankE = new PageRankE();
                        pageRankE.setName(key);
                        pageRankE.setPrc(PRC.get(key));
                        total.add(pageRankE);
                    }
                }
            }
            req.setAttribute("total",total);
        }
        else
            req.setAttribute("msg","读取excel错误");
        req.getRequestDispatcher("/pagerank.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }
}
