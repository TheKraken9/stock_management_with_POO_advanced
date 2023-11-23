package com.project.stock2.controller;

import com.project.stock2.model.Article;
import com.project.stock2.model.Magasin;
import com.project.stock2.model.Mouvement;
import com.project.stock2.model.UniteEquivalence;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

@WebServlet(name = "NewMouvementEntreServlet", value = "/NewMouvementEntreServlet")
public class NewMouvementEntreServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<Article> articles = new ArrayList<>();
        ArrayList<Magasin> magasins = new ArrayList<>();
        ArrayList<UniteEquivalence> uniteEquivalences = new ArrayList<>();
        Article article = new Article();
        Magasin magasin = new Magasin();
        UniteEquivalence uniteEquivalence = new UniteEquivalence();
        try{
            articles = article.getArticles(null);
            magasins = magasin.getMagasins(null);
            uniteEquivalences = uniteEquivalence.getUnitesEquivalence();
        }catch (Exception e){
            e.printStackTrace();
        }
        request.setAttribute("articles", articles);
        request.setAttribute("magasins", magasins);
        request.setAttribute("uniteEquivalences", uniteEquivalences);
        request.getRequestDispatcher("/WEB-INF/NewMouvementEntre.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Mouvement mouvement = new Mouvement();
        String id_article = request.getParameter("id_article");
        String id_magasin = request.getParameter("id_magasin");
        String id_unite_equivalence = request.getParameter("id_unite_equivalence");
        int quantite = Integer.parseInt(request.getParameter("quantite"));
        Date date_mouvement = Date.valueOf(request.getParameter("date_mouvement"));
        double prix = Double.parseDouble(request.getParameter("prix"));
        mouvement.setId_article(id_article);
        mouvement.setId_magasin(id_magasin);
        mouvement.setQuantite(quantite);
        mouvement.setDate_mouvement(date_mouvement);
        mouvement.setPrix_unitaire(prix);
        mouvement.setUnite_equivalence(id_unite_equivalence);
        try{
            Article article = new Article();
            article.setId(id_article);
            boolean exist = article.uniteEquivalenceExistsPourCeProduit(null, id_unite_equivalence);
            if(exist){
                mouvement.insertEntre(null);
            }else{
                request.setAttribute("message", "L'unité d'équivalence n'existe pas pour ce produit");
                request.getRequestDispatcher("/WEB-INF/Erreur.jsp").forward(request, response);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}