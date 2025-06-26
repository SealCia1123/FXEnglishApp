package com.sealcia.services;

import com.sealcia.pojo.Choice;
import com.sealcia.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChoiceServices {
    public static List<Choice> getChoicesByQuestion(String questionId) throws SQLException {
        Connection connection = JdbcUtils.getInstance().connect();
        PreparedStatement stm =
                connection.prepareStatement("SELECT * FROM choice WHERE question_id = ?");
        stm.setString(1, questionId);
        ResultSet rs = stm.executeQuery();

        List<Choice> choices = new ArrayList<>();
        while (rs.next()) {
            choices.add(
                    new Choice.Builder()
                            .id(rs.getString("id"))
                            .content(rs.getString("content"))
                            .correct(rs.getBoolean("is_correct"))
                            .questionId(rs.getString("question_id"))
                            .build());
        }
        return choices;
    }
}
