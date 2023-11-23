package com.project.stock2.controller;

import com.project.stock2.model.Mouvement;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "ConfirmationServlet", value = "/ConfirmationServlet")
public class ConfirmationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<Mouvement> mouvements = new ArrayList<>();
        Mouvement mouvement = new Mouvement();
        try{
            mouvements = mouvement.getDemandes(null);
        }catch (Exception e){
            e.printStackTrace();
        }
        request.setAttribute("demandes", mouvements);
        request.getRequestDispatcher("/WEB-INF/Confirmation.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}