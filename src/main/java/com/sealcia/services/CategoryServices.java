package com.sealcia.services;

import com.sealcia.pojo.Category;
import com.sealcia.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategoryServices {
    public static List<Category> getCategories() throws SQLException {
        Connection connection = JdbcUtils.getInstance().connect();
        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery("SELECT * FROM category");

        List<Category> categories = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            categories.add(new Category(id, name));
        }
        return categories;
    }
}
