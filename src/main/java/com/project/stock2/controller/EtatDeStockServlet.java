package com.project.stock2.controller;

import com.project.stock2.model.EtatDeStock;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

@WebServlet(name = "EtatDeStockServlet", value = "/EtatDeStockServlet")
public class EtatDeStockServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String date_debut = request.getParameter("date1");
        String date_fin = request.getParameter("date2");
        String id_article = request.getParameter("id_article");
        String id_magasin = request.getParameter("id_magasin");
        ArrayList<EtatDeStock> etatDeStocks1 = new ArrayList<>();
        ArrayList<EtatDeStock> etatDeStocks2 = new ArrayList<>();
        EtatDeStock etatDeStock = new EtatDeStock();
        try{
            etatDeStocks1 = etatDeStock.getEtatDeStocks(id_article, id_magasin, date_debut);
            etatDeStocks2 = etatDeStock.getEtatDeStocks(id_article, id_magasin, date_fin);
        }catch (Exception e){
            e.printStackTrace();
        }
        request.setAttribute("etatDeStocks1", etatDeStocks1);
        request.setAttribute("etatDeStocks2", etatDeStocks2);
        request.setAttribute("date1", date_debut);
        request.setAttribute("date2", date_fin);
        request.getRequestDispatcher("/WEB-INF/EtatDeStock.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}