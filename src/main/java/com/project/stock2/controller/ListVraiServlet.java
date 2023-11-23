package com.project.stock2.controller;

import com.project.stock2.model.Mouvement;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "ListVraiServlet", value = "/ListVraiServlet")
public class ListVraiServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<Mouvement> mouvements = new ArrayList<>();
        Mouvement mouvement = new Mouvement();
        try {
            mouvements = mouvement.getListVrai(null);
        }catch (Exception e){
            e.printStackTrace();
        }
        request.setAttribute("vrai", mouvements);
        request.getRequestDispatcher("/WEB-INF/Resultat.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}