<%@ page import="java.util.List" %>
<%@ page import="Course" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Course Dashboard</title>
    <style>
            body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            margin: 0;
            padding: 0;
        }

        .topbar {
            background-color: #007bff;
            color: white;
            padding: 15px 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .topbar a {
            color: white;
            text-decoration: none;
            font-weight: bold;
        }

        .content {
            max-width: 900px;
            margin: 20px auto;
            padding: 20px;
            background-color: white;
            box-shadow: 0px 0px 10px rgba(0,0,0,0.1);
            border-radius: 8px;
        }

        h2 {
            color: #333;
            margin-top: 0;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
        }

        table, th, td {
            border: 1px solid #ddd;
        }

        th, td {
            padding: 10px;
            text-align: left;
        }

        th {
            background-color: #f2f2f2;
        }

        tr:hover {
            background-color: #f9f9f9;
        }

        .enroll-btn {
            display: inline-block;
            padding: 6px 12px;
            background-color: #28a745;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            transition: background-color 0.3s ease;
        }

        .enroll-btn:hover {
            background-color: #218838;
        }

        .success {
            background-color: #d4edda;
            color: #155724;
            padding: 10px;
            border-radius: 5px;
            margin-bottom: 15px;
            border: 1px solid #c3e6cb;
        }

        .enrolled-section {
            margin-top: 30px;
        }
    </style>
</head>
<body>
    <div class="topbar">
        <span>Welcome, <%= session.getAttribute("username") %>!</span>
        <a href="LogoutServlet">Logout</a>
    </div>
    <div class="content">

        <%-- Success message --%>
        <% if (request.getAttribute("successMessage") != null) { %>
            <div class="success"><%= request.getAttribute("successMessage") %></div>
        <% } %>

        <%-- Available Courses Table (Task 2) --%>
        <h2>Available Courses</h2>
        <table>
            <tr>
                <th>Course ID</th>
                <th>Course Name</th>
                <th>Instructor</th>
                <th>Action</th>
            </tr>
            <%
                List<Course> courses = (List<Course>) request.getAttribute("courses");
                if (courses != null) {
                    for (Course c : courses) {
            %>
            <tr>
                <td><%= c.getCourseId() %></td>
                <td><%= c.getCourseName() %></td>
                <td><%= c.getInstructor() %></td>
                <%-- URL Rewriting (Task 3) --%>
                <td><a class="enroll-btn" href="EnrollServlet?courseId=<%= c.getCourseId() %>">Enroll</a></td>
            </tr>
            <%
                    }
                }
            %>
        </table>

        <%-- Enrolled Courses Section (Task 3) --%>
        <%
            List<Course> enrolled = (List<Course>) session.getAttribute("enrolledCourses");
            if (enrolled != null && !enrolled.isEmpty()) {
        %>
        <div class="enrolled-section">
            <h2>My Enrolled Courses</h2>
            <table>
                <tr>
                    <th>Course ID</th>
                    <th>Course Name</th>
                    <th>Instructor</th>
                </tr>
                <% for (Course ec : enrolled) { %>
                <tr>
                    <td><%= ec.getCourseId() %></td>
                    <td><%= ec.getCourseName() %></td>
                    <td><%= ec.getInstructor() %></td>
                </tr>
                <% } %>
            </table>
        </div>
        <% } %>
    </div>
</body>
</html>