package com.project.stock2.controller;

import com.project.stock2.model.Mouvement;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Date;

@WebServlet(name = "ModifConfirmationServlet", value = "/ModifConfirmationServlet")
public class ModifConfirmationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id_mouvement = request.getParameter("id");
        Mouvement mouvement = new Mouvement();
        Mouvement mvt = new Mouvement();
        mouvement.setId_demande(id_mouvement);
        try {
            mvt = mouvement.getInfoDemande(null);
        }catch (Exception e){
            e.printStackTrace();
        }
        request.setAttribute("mouvement", mvt);
        request.setAttribute("message", "Veuillez modifier les informations");
        request.getRequestDispatcher("/WEB-INF/ModifConfirmation.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String id_article = request.getParameter("id_article");
        String id_magasin = request.getParameter("id_magasin");
        double quantite = Double.parseDouble(request.getParameter("quantite"));
        System.out.println(quantite);
        Date date = Date.valueOf(request.getParameter("date"));
        Mouvement mouvement = new Mouvement();
        mouvement.setId(id);
        mouvement.setId_article(id_article);
        mouvement.setId_magasin(id_magasin);
        mouvement.setQuantite(quantite);
        mouvement.setDate_mouvement(date);
        try{
            mouvement.updateDemande(null);
        }catch (Exception e){
            e.printStackTrace();
        }
        response.sendRedirect("ConfirmationServlet");
    }
}