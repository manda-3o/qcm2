package com.gestionqcm.service;

import com.gestionqcm.dao.QuestionDAO;
import com.gestionqcm.model.Question;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuestionService {
    private final QuestionDAO dao = new QuestionDAO();

    public List<Question> listAll() throws SQLException {
        return dao.listAll();
    }

    public List<Question> listPage(int page, int size) throws SQLException {
        return dao.listPage(page, size);
    }

    public int countTotal() throws SQLException {
        return dao.countTotal();
    }

    public Question findById(int id) throws SQLException {
        return dao.findById(id);
    }

    public void save(Question question) throws SQLException {
        if (question == null) {
            return;
        }
        dao.create(question);
    }

    public void delete(int id) throws SQLException {
        dao.delete(id);
    }

    public List<Question> randomQuestions(int count) throws SQLException {
        List<Question> questions = new ArrayList<>(dao.listAll());
        Collections.shuffle(questions);
        return questions.subList(0, Math.min(count, questions.size()));
    }
}
