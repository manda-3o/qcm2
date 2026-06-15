package com.gestionqcm.controller;

import com.gestionqcm.model.Examen;
import com.gestionqcm.model.Question;
import com.gestionqcm.model.Etudiant;
import com.gestionqcm.service.ExamService;
import com.gestionqcm.service.EtudiantService;
import com.gestionqcm.service.QuestionService;
import com.gestionqcm.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "ExamServlet", urlPatterns = {"/examens"})
public class ExamServlet extends HttpServlet {
    private static final String SESSION_QUESTION_IDS = "examQuestionIds";
    private static final String SESSION_SELECTED_STUDENT = "examStudentId";
    private static final String SESSION_SELECTED_YEAR = "examYear";
    private static final String SESSION_START_TIME = "examStartTime";
    private static final int EXAM_DURATION_SECONDS = 600;

    private final ExamService examService = new ExamService();
    private final QuestionService questionService = new QuestionService();
    private final EtudiantService etudiantService = new EtudiantService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int page = WebUtils.parseIntOrDefault(req.getParameter("page"), 1);
            int pageSize = 10;
            int total = examService.countTotal();
            req.setAttribute("examens", examService.listPage(page, pageSize));
            req.setAttribute("pageCount", (int) Math.ceil(total / (double) pageSize));
            req.setAttribute("currentPage", page);
            req.setAttribute("pageSize", pageSize);
            req.setAttribute("etudiants", etudiantService.listAll());
            String action = req.getParameter("action");
            HttpSession session = req.getSession();

            if ("quiz".equals(action)) {
                String numEtu = req.getParameter("num_etudiant");
                String annee = req.getParameter("annee");
                List<Question> questions = questionService.randomQuestions(10);
                List<Integer> ids = questions.stream().map(Question::getNumQuest).collect(Collectors.toList());
                session.setAttribute(SESSION_QUESTION_IDS, ids);
                session.setAttribute(SESSION_SELECTED_STUDENT, numEtu);
                session.setAttribute(SESSION_SELECTED_YEAR, annee);
                session.setAttribute(SESSION_START_TIME, System.currentTimeMillis());
                req.setAttribute("questions", questions);
                req.setAttribute("selectedStudent", etudiantService.findById(numEtu));
                req.setAttribute("selectedYear", annee);
                req.setAttribute("durationSeconds", EXAM_DURATION_SECONDS);
            } else if ("cancel".equals(action)) {
                session.removeAttribute(SESSION_QUESTION_IDS);
                session.removeAttribute(SESSION_SELECTED_STUDENT);
                session.removeAttribute(SESSION_SELECTED_YEAR);
                session.removeAttribute(SESSION_START_TIME);
                resp.sendRedirect(req.getContextPath() + "/examens");
                return;
            } else {
                @SuppressWarnings("unchecked")
                List<Integer> ids = (List<Integer>) session.getAttribute(SESSION_QUESTION_IDS);
                if (ids != null && !ids.isEmpty()) {
                    List<Question> questions = new ArrayList<>();
                    for (Integer id : ids) {
                        Question question = questionService.findById(id);
                        if (question != null) {
                            questions.add(question);
                        }
                    }
                    req.setAttribute("questions", questions);
                    String numEtu = (String) session.getAttribute(SESSION_SELECTED_STUDENT);
                    String annee = (String) session.getAttribute(SESSION_SELECTED_YEAR);
                    req.setAttribute("selectedStudent", etudiantService.findById(numEtu));
                    req.setAttribute("selectedYear", annee);
                    req.setAttribute("durationSeconds", EXAM_DURATION_SECONDS);
                }
            }

            String submitted = req.getParameter("submitted");
            if ("true".equals(submitted)) {
                req.setAttribute("examResult", req.getParameter("score"));
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
        req.getRequestDispatcher("/WEB-INF/views/examens.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        try {
            if ("start".equals(action)) {
                HttpSession session = req.getSession();
                @SuppressWarnings("unchecked")
                List<Integer> questionIds = (List<Integer>) session.getAttribute(SESSION_QUESTION_IDS);
                String numEtu = (String) session.getAttribute(SESSION_SELECTED_STUDENT);
                String annee = (String) session.getAttribute(SESSION_SELECTED_YEAR);
                long startTime = session.getAttribute(SESSION_START_TIME) instanceof Long ? (Long) session.getAttribute(SESSION_START_TIME) : System.currentTimeMillis();
                int score = 0;

                if (questionIds != null) {
                    for (Integer numQuest : questionIds) {
                        Question question = questionService.findById(numQuest);
                        if (question == null) {
                            continue;
                        }
                        String selectedAnswer = req.getParameter("answer_" + numQuest);
                        if (selectedAnswer != null && selectedAnswer.equals(question.getBonne())) {
                            score++;
                        }
                    }
                }

                int durationSeconds = (int) Math.min(EXAM_DURATION_SECONDS, (System.currentTimeMillis() - startTime) / 1000);
                Examen examen = new Examen("X" + System.currentTimeMillis(), numEtu, annee, score, durationSeconds, "terminé");
                examService.save(examen);
                session.removeAttribute(SESSION_QUESTION_IDS);
                session.removeAttribute(SESSION_SELECTED_STUDENT);
                session.removeAttribute(SESSION_SELECTED_YEAR);
                session.removeAttribute(SESSION_START_TIME);
                resp.sendRedirect(req.getContextPath() + "/examens?submitted=true&score=" + score);
                return;
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
        resp.sendRedirect(req.getContextPath() + "/examens");
    }
}
