package com.gestionqcm.controller;

import com.gestionqcm.model.Question;
import com.gestionqcm.service.QuestionService;
import com.gestionqcm.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "QCMServlet", urlPatterns = {"/qcm"})
public class QCMServlet extends HttpServlet {
    private final QuestionService questionService = new QuestionService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int page = WebUtils.parseIntOrDefault(req.getParameter("page"), 1);
            int pageSize = 10;
            int total = questionService.countTotal();
            req.setAttribute("qcms", questionService.listPage(page, pageSize));
            req.setAttribute("pageCount", (int) Math.ceil(total / (double) pageSize));
            req.setAttribute("currentPage", page);
            req.setAttribute("pageSize", pageSize);
        } catch (Exception e) {
            throw new ServletException(e);
        }
        req.getRequestDispatcher("/WEB-INF/views/qcm.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        try {
            if ("delete".equals(action)) {
                int id = Integer.parseInt(req.getParameter("num_quest"));
                questionService.delete(id);
            } else {
                int num = Integer.parseInt(req.getParameter("num_quest"));
                Question q = new Question(num, req.getParameter("question"), req.getParameter("categorie"), req.getParameter("reponse1"), req.getParameter("reponse2"), req.getParameter("reponse3"), req.getParameter("reponse4"), req.getParameter("bonne"));
                questionService.save(q);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
        resp.sendRedirect(req.getContextPath() + "/qcm");
    }
}
