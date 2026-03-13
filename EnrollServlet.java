import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.util.*;

@WebServlet("/EnrollServlet")
public class EnrollServlet extends HttpServlet {
    private static final Map<Integer, Course> COURSE_MAP = new HashMap<>();
    static {
        COURSE_MAP.put(101, new Course(1001, "Web Technologies",       "Dr. Silva"));
        COURSE_MAP.put(102, new Course(1002, "Database Management",    "Prof. Perera"));
        COURSE_MAP.put(103, new Course(1023, "Software Engineering",   "Dr. Fernando"));
        COURSE_MAP.put(104, new Course(1034, "Computer Networks",      "Prof. Jayawardena"));
        COURSE_MAP.put(105, new Course(1055, "Data Structures",        "Dr. Rajapaksa"));
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        //changed code
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login.html");
            return;
        }

        String courseIdParam = request.getParameter("courseId");
        if (courseIdParam == null) {
            response.sendRedirect("DashboardServlet");
            return;
        }

        int courseId = Integer.parseInt(courseIdParam);
        Course selectedCourse = COURSE_MAP.get(courseId);

        if (selectedCourse != null) {
            @SuppressWarnings("unchecked")
            List<Course> enrolledCourses = (List<Course>) session.getAttribute("enrolledCourses");
            if (enrolledCourses == null) {
                enrolledCourses = new ArrayList<>();
            }
            boolean alreadyEnrolled = enrolledCourses.stream()
                    .anyMatch(c -> c.getCourseId() == courseId);
            if (!alreadyEnrolled) {
                enrolledCourses.add(selectedCourse);
            }

            session.setAttribute("enrolledCourses", enrolledCourses);
        }

        response.sendRedirect("DashboardServlet?success=1");
    }
}