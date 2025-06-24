package com.sealcia.services;

import com.sealcia.pojo.*;
import com.sealcia.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuestionServices {
    public static void addQuestion(Question question, ArrayList<Choice> choices)
        throws SQLException {
        String sql1 = "INSERT INTO question(id, content, category_id) VALUES(?, ?, ?)";
        String sql2 = "INSERT INTO choice(id, content, is_correct, question_id) VALUES(?, ?, ?, ?)";

        Connection connection = JdbcUtils.getInstance().connect();
        connection.setAutoCommit(false);

        PreparedStatement stm = connection.prepareStatement(sql1);
        stm.setString(1, question.getId());
        stm.setString(2, question.getContent());
        stm.setInt(3, question.getCategoryId());

        stm.executeUpdate();

        for (Choice c : choices) {
            PreparedStatement st = connection.prepareCall(sql2);
            st.setString(1, c.getId());
            st.setString(2, c.getContent());
            st.setBoolean(3, c.isCorrect());
            st.setString(4, question.getId());
            st.executeUpdate();
        }

        connection.commit();
    }

    public static List<Question> getQuestions(String keywords) throws SQLException {
        String sql = "SELECT * FROM question";
        if (!keywords.isBlank()) {
            sql += " WHERE content like ?";
        }

        Connection connection = JdbcUtils.getInstance().connect();
        PreparedStatement stm = connection.prepareStatement(sql);

        if (!keywords.isBlank()) {
            stm.setString(1, String.format("%%%s%%", keywords));
        }

        ResultSet rs = stm.executeQuery();
        List<Question> questions = new ArrayList<>();
        while (rs.next()) {
            Question q =
                new Question(rs.getString("id"), rs.getString("content"), rs.getInt("category_id"));
            questions.add(q);
            List<Choice> choices = ChoiceServices.getChoicesByQuestion(q.getId());
            q.setChoices(choices);
        }
        return questions;
    }

    public static void deleteQuestion(String questionId) throws SQLException {
        String sql1 = "DELETE FROM choice WHERE question_id = ?";
        String sql2 = "DELETE FROM question WHERE id = ?";
        Connection connection = JdbcUtils.getInstance().connect();

        PreparedStatement stmDeleteChoice = connection.prepareStatement(sql1);
        PreparedStatement stmDeleteQuestion = connection.prepareStatement(sql2);
        stmDeleteChoice.setString(1, questionId);
        stmDeleteQuestion.setString(1, questionId);

        connection.setAutoCommit(false);

        stmDeleteChoice.executeUpdate();
        stmDeleteQuestion.executeUpdate();

        connection.commit();
    }
}
