import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.util.*;

@WebServlet("/DashboardServlet")
public class DashboardServlet extends HttpServlet {
    private static final List<Course> ALL_COURSES = new ArrayList<>();
    static {
        ALL_COURSES.add(new Course(1001, "Web Technologies",       "Dr. Silva"));
        ALL_COURSES.add(new Course(1002, "Database Management",    "Prof. Perera"));
        ALL_COURSES.add(new Course(1023, "Software Engineering",   "Dr. Fernando"));
        ALL_COURSES.add(new Course(1034, "Computer Networks",      "Prof. Jayawardena"));
        ALL_COURSES.add(new Course(1055, "Data Structures",        "Dr. Rajapaksa"));
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Guarded- must be logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login.html");
            return;
        }

        request.setAttribute("courses", ALL_COURSES);

        String successMsg = request.getParameter("success");
        if (successMsg != null) {
            request.setAttribute("successMessage", "Successfully enrolled in the course!");
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("dashboard.html");
        dispatcher.forward(request, response);
    }
}