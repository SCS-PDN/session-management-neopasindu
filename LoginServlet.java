import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    private static final Map<String, String> USERS = new HashMap<>();
    static {
        USERS.put("testStuden1", "pass1");
        USERS.put("testStudent2", "pass2");
        USERS.put("admin", "admin12345");
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
    
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username != null && USERS.containsKey(username) && USERS.get(username).equals(password)) {
            // Session creation in login
            HttpSession session = request.getSession(true);
            session.setAttribute("username", username);
            session.setMaxInactiveInterval(30 * 60); 

            Cookie userCookie = new Cookie("username", username);
            userCookie.setMaxAge(30 * 60); 
            userCookie.setPath("/");
            response.addCookie(userCookie);

            response.sendRedirect("DashboardServlet");
        } else {
            response.sendRedirect("login.html?error=1");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("login.html");
    }
}