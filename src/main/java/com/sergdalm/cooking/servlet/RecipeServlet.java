package com.sergdalm.cooking.servlet;

import com.sergdalm.cooking.service.RecipeService;
import com.sergdalm.cooking.util.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/recipes")
public class RecipeServlet extends HttpServlet {
    private final RecipeService recipeService = RecipeService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("recipes", recipeService.findAll());
        req.getRequestDispatcher(JspHelper.getPath("recipes"))
                .forward(req, resp);
    }
}
