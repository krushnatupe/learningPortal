package com.learningportal.dao;
import com.learningportal.model.TestResult;
import com.learningportal.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TestResultDAO {

    public static List<TestResult> getTestResultsByUserId(int userId) {
        List<TestResult> testResults = new ArrayList<>();
        String sql = "SELECT * FROM user_test_results WHERE user_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                TestResult result = new TestResult(
                    rs.getInt("result_id"),
                    userId,
                    rs.getInt("course_id"),
                    rs.getInt("score"),
                    rs.getInt("time_taken"),
                    rs.getTimestamp("test_date"),
                    rs.getString("feedback") // Assuming you're storing feedback in the same table
                );
                testResults.add(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }

        return testResults;
    }

    // Additional DAO methods as needed
}
