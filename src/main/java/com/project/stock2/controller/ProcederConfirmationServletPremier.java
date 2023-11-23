package com.project.stock2.controller;

import com.project.stock2.model.Mouvement;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "ProcederConfirmationServletPremier", value = "/ProcederConfirmationServletPremier")
public class ProcederConfirmationServletPremier extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id_mouvement = request.getParameter("id");
        String action = request.getParameter("action");
        Mouvement mouvement = new Mouvement();
        mouvement.setId_demande(id_mouvement);
        try{
            Mouvement mvt = mouvement.getInfoDemande(null);
            request.setAttribute("mvt", mvt);
            request.getRequestDispatcher("/WEB-INF/ProcederConfirmationPremier.jsp").forward(request, response);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}