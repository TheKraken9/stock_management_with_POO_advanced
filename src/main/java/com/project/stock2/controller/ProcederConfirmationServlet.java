package com.project.stock2.controller;

import com.project.stock2.model.Article;
import com.project.stock2.model.Mouvement;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Date;

@WebServlet(name = "ProcederConfirmationServlet", value = "/ProcederConfirmationServlet")
public class ProcederConfirmationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id_mouvement = request.getParameter("id");
        String action = request.getParameter("action");
        String date = request.getParameter("date");
        Mouvement mouvement = new Mouvement();
        mouvement.setId_demande(id_mouvement);
        try{
            Mouvement mvt = mouvement.getInfoDemande(null);
            if(action.equalsIgnoreCase("ok")){
                Article article = new Article();
                article.setId(mvt.getId_article());
                article.articleExists(null);
                boolean ok = mouvement.dateAnterieure(null,mvt.getId_article(), mvt.getId_magasin(), Date.valueOf(date));
                if(ok){
                    request.setAttribute("mouvement", mvt);
                    request.setAttribute("message", "La date de mouvement est antérieure à la date de dernière mouvement");
                    request.getRequestDispatcher("/WEB-INF/ModifConfirmation.jsp").forward(request, response);
                    return;
                }
                boolean exists = article.articleExistsInMagasinOnDate(null, mvt.getId_magasin(), Date.valueOf(date));
                if(!exists){
                    request.setAttribute("mouvement", mvt);
                    request.setAttribute("message", "L'article n'existe pas dans le magasin à cette date");
                    request.getRequestDispatcher("/WEB-INF/ModifConfirmation.jsp").forward(request, response);
                    return;
                }
                boolean sufficient = article.quantiteForArticleIsSufficient(null, mvt.getId_magasin(), mvt.getQuantite(), Date.valueOf(date));
                if(sufficient) {
                    mvt.insertMouvement(null, Date.valueOf(date));
                }
                else{
                    request.setAttribute("mouvement", mvt);
                    request.setAttribute("message", "La quantité en stock n'est pas suffisante");
                    request.getRequestDispatcher("/WEB-INF/ModifConfirmation.jsp").forward(request, response);
                    return;
                }
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}