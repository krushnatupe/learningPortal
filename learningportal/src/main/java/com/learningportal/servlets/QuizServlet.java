package com.learningportal.servlets;

import com.learningportal.utils.DBConnection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient.Version;
import java.time.Duration;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;


@WebServlet("/QuizServlet")
public class QuizServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        //Integer userId = (Integer) session.getAttribute("userid");
       // System.out.print(userId);
        
        
        String course = request.getParameter("course").replace("_", " ");
        int courseid = Integer.parseInt(request.getParameter("courseid"));
        session.setAttribute("courseid", courseid);
        session.setAttribute("coursename", course);

        session.removeAttribute("questions");
        session.removeAttribute("currentQuestionIndex");

        if (course != null && !course.isEmpty()) {
            if (loadQuestionsForCourse(courseid, session)) {
                response.sendRedirect("question.jsp");
            } else {
                session.setAttribute("error", "No questions found for the selected course.");
                response.sendRedirect("home.jsp");
            }
        } else {
        	
            response.sendRedirect("home.jsp");
        }
    }
    
    private boolean loadQuestionsForCourse(int courseid, HttpSession session) {
        List<Question> questions = new ArrayList<>();
      
        //int courseId = 1;

        if (courseid == -1) return false;

        String query = "SELECT * FROM questions WHERE course_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            
            ps.setInt(1, courseid);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                questions.add(new Question(
                    rs.getInt("question_id"),
                    rs.getString("question_text"),
                    rs.getString("option_a"),
                    rs.getString("option_b"),
                    rs.getString("option_c"),
                    rs.getString("option_d"),
                    rs.getString("correct_option")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        if (questions.isEmpty()) return false;

        session.setAttribute("questions", questions);
        session.setAttribute("currentQuestionIndex", 0);
        return true;
    }

    private int getCourseId(String courseName) {
        int courseId = -1;
        String sql = "SELECT id FROM courses WHERE lower(title) = lower(?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, courseName);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                courseId = rs.getInt("course_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courseId;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer currentQuestionIndex = (Integer) session.getAttribute("currentQuestionIndex");
        List<String> userResponses = (List<String>) session.getAttribute("userResponses");
        List<Integer> timeSpentOnQuestions = (List<Integer>) session.getAttribute("timeSpentOnQuestions");


        // Initialize lists 
        if (userResponses == null) {
            userResponses = new ArrayList<>();
            session.setAttribute("userResponses", userResponses);
        }
        if (timeSpentOnQuestions == null) {
            timeSpentOnQuestions = new ArrayList<>();
            session.setAttribute("timeSpentOnQuestions", timeSpentOnQuestions);
        }

        // Handling the response and timing for the current question
        String selectedOption = request.getParameter("option");
        String timeSpent = request.getParameter("timeSpent");

        System.out.println("Selected Option: " + selectedOption);
        System.out.println("Time Spent: " + timeSpent);
        
        // Avoid adding null or invalid responses
        if (selectedOption != null && !selectedOption.trim().isEmpty()) {
            userResponses.add(selectedOption);
        }
        if (timeSpent != null) {
            try {
                timeSpentOnQuestions.add(Integer.parseInt(timeSpent));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        if (currentQuestionIndex == null) {
            currentQuestionIndex = 0;
        } else {
            currentQuestionIndex++;
        }
        session.setAttribute("currentQuestionIndex", currentQuestionIndex);

        List<Question> questions = (List<Question>) session.getAttribute("questions");
        if (questions != null && currentQuestionIndex < questions.size()) {
            Question currentQuestion = questions.get(currentQuestionIndex);
            request.setAttribute("currentQuestion", currentQuestion);
            request.getRequestDispatcher("/question.jsp").forward(request, response);
        } else {
            
            
            
            //openai_logic
        	String courseName = (String) session.getAttribute("coursename");
            StringBuilder promptBuilder = new StringBuilder();
            promptBuilder.append(String.format("You are a feedback assistant of Learning Portal and your work is to congratulate user for completing test(MCQ) of topic %s on LearningPortal and analyse the user's response and give appropriate detailed analysis and feedback directly to the user.\n\n",courseName));

            int totalQuestions = questions.size();
            int correctAnswersCount = calculateCorrectAnswersCount(questions, userResponses);
            List<Integer> correctQuestions = new ArrayList<>();
            List<Integer> wrongQuestions = new ArrayList<>();
            String usrName = (String) session.getAttribute("username");
            System.out.println(usrName+"hi");
            System.out.println("Course id is "+session.getAttribute("courseid"));
            int courseId = (Integer) session.getAttribute("courseid");

         
            System.out.println("Course id is "+courseId);
//           int courseId = Integer.parseInt((String) session.getAttribute("courseid"));
            for (int i = 0; i < questions.size(); i++) {
                if (userResponses.get(i).equals(questions.get(i).getCorrectOption())) {
                    correctQuestions.add(i + 1);
                } else {
                    wrongQuestions.add(i + 1);
                }
            }
            
            Integer userId = (Integer) session.getAttribute("userid");
        	int score = calculateScore(questions, userResponses);
            int totalTimeSpent = 0;
            if (timeSpentOnQuestions != null) {
            	System.out.println("Time Spent on Questions: " + timeSpentOnQuestions);
                totalTimeSpent = timeSpentOnQuestions.stream().mapToInt(Integer::intValue).sum();
            }

            promptBuilder.append(String.format("So, the user has attempted a total of %d questions and out of %d, he gets %d correct. The correct answers he gave are of questions %s.\n", totalQuestions, totalQuestions, correctAnswersCount, correctQuestions.toString()));

            promptBuilder.append("Here are the questions with the time consumed in seconds for each:\n");
            for (int i = 0; i < questions.size(); i++) {
                promptBuilder.append(String.format("%d. %s %d,\n", i + 1, questions.get(i).getQuestionText(), timeSpentOnQuestions.get(i)));
            }

            promptBuilder.append("According to this data, analyze the user's performance and tell him in which area he is good and which needs improvement. Also, for each wrong question, explain that concept in short and tell him how can he remember that concept forever with some real-life example.\n");
            promptBuilder.append(String.format("Provide feedback directly to the user, using 'You' or %s instead of 'the user'.\n",usrName.toString()));

            
            String finalPrompt = promptBuilder.toString().replace("\"", "\\\"");

            // Call the OpenAI API with this finalPrompt and handle the response
            String aiFeedback = "";
            try { 
            	System.out.print("Hello");  
            	aiFeedback = callOpenAI(finalPrompt);
            	
            	//saveResultsToDatabase(userId, score, totalTimeSpent, aiFeedback);
            	
            	session.setAttribute("aiFeedback", aiFeedback);
            }catch(Exception e) {
            	System.out.print("Hello2"); 
            	System.out.print(e);            
            }
            
        	
            if (userId != null) {
                saveResultsToDatabase(userId, courseId, score, totalTimeSpent,aiFeedback);
                System.out.println("After saving records");
            } else {
                response.sendRedirect("home.jsp");
                return;
            }
          

            // Cleanup session and forward to results
            //session.removeAttribute("currentQuestionIndex");
           // session.removeAttribute("questions");
           // session.removeAttribute("userResponses");
           // session.removeAttribute("timeSpentOnQuestions");
            session.setAttribute("totalTimeSpent", totalTimeSpent);
            session.setAttribute("score", score);
            System.out.println("Hello33");
//            request.getRequestDispatcher("./quiz-results.jsp").forward(request, response);
            response.sendRedirect("quiz-results.jsp");
        }
    }


    private void saveResultsToDatabase(int userId, int courseId, int score, int totalTimeSpent, String feedback) {
    	System.out.println("Courseid");
    	System.out.println(courseId);
    	System.out.println(userId);
    	try (Connection conn = DBConnection.getConnection();
        	
             PreparedStatement ps = conn.prepareStatement("INSERT INTO user_test_results (user_id, course_id, score, time_taken, test_date,feedback) VALUES (?, ?, ?, ?, NOW(),?)")) {
            ps.setInt(1, userId);
            ps.setInt(2, courseId);
            ps.setInt(3, score);
            ps.setInt(4, totalTimeSpent);
            ps.setString(5, feedback);
            
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Implement more robust error handling as needed
        }
    }

    private int calculateScore(List<Question> questions, List<String> userResponses) {
        int score = 0;
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            String correctOption = question.getCorrectOption();
            String userResponse = userResponses.size() > i ? userResponses.get(i) : "";
            
            if (correctOption.equalsIgnoreCase(userResponse)) {
                score++;
            }
        }
        System.out.println(score);
        return score;
    }
    
    
    
    private int calculateCorrectAnswersCount(List<Question> questions, List<String> userResponses) {
        int correctCount = 0;
        for (int i = 0; i < questions.size(); i++) {
            if (userResponses.get(i).equalsIgnoreCase(questions.get(i).getCorrectOption())) {
                correctCount++;
            }
        }
        return correctCount;
    }

    
    

    
    private String callOpenAI(String prompt) throws JSONException {
        String apiKey = System.getenv("OPENAI_API_KEY"); // Make sure this environment variable is correctly set
        System.out.println(apiKey); // Debugging purpose only, ensure it's not null and looks correct

        HttpClient client = HttpClient.newHttpClient();

        JSONObject json = new JSONObject();
        json.put("model", "gpt-3.5-turbo-instruct");
        json.put("prompt", prompt);
        json.put("max_tokens", 1024);
        json.put("temperature", 0.7);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/completions"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject jsonResponse = new JSONObject(response.body());
            System.out.println(response.body());
            JSONArray choices = jsonResponse.getJSONArray("choices");
            String aiTextResponse = choices.getJSONObject(0).getString("text").trim(); // Extracts the text part of the response
            System.out.println(aiTextResponse);
            return aiTextResponse;
            //return response.body(); // Extract and process the AI response as needed
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Failed to get feedback from AI."; // Consider more nuanced error handling based on your application's needs
        }
    }

}
