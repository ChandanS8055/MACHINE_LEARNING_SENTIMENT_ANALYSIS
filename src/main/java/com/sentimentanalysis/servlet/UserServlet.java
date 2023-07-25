package com.sentimentanalysis.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import com.sentimentanalysis.dao.UserDAO;
import com.sentimentanalysis.pojo.User;
import com.sentimentanalysis.daoimpl.UserDAOImpl;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServlet;

public class UserServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        final UserDAO dao = new UserDAOImpl();
        try {
            final String request_type = req.getParameter("request_type");
            if (request_type.equals("adminlogin")) {
                final String adminid = req.getParameter("adminid");
                final String password = req.getParameter("adminpassword");
                if (dao.adminlogin(adminid, password)) {
                    req.getSession().setAttribute("admin", (Object)adminid);
                    resp.sendRedirect("adminwelcome.jsp?msg=Logged in as " + adminid + " (ADMIN)");
                }
                else {
                    resp.sendRedirect("adminlogin.jsp?msg=Invalid Admin Credentials");
                }
            }
            else if (request_type.equals("adminlogout")) {
                req.getSession().invalidate();
                resp.sendRedirect("adminlogin.jsp?msg=Logout successful");
            }
            else if (request_type.equals("register")) {
                final User user = new User();
                final String addr = req.getParameter("addr");
                user.setAddr(addr);
                final String email = req.getParameter("email");
                user.setEmail(email);
                final String fname = req.getParameter("fname");
                user.setFname(fname);
                final String lname = req.getParameter("lname");
                user.setLname(lname);
                final String gender = req.getParameter("gender");
                user.setGender(gender);
                final String mobile = req.getParameter("mobile");
                user.setMobile(mobile);
                final String password2 = req.getParameter("password");
                user.setPassword(password2);
                String role = req.getParameter("role");
                if (role == null || role.trim().length() == 0) {
                    role = "USER";
                }
                user.setRole(role);
                if (addr == null || addr.trim().length() == 0 || email == null || email.trim().length() == 0 || fname == null || fname.trim().length() == 0 || lname == null || lname.trim().length() == 0 || mobile == null || mobile.trim().length() == 0 || role == null || role.trim().length() == 0 || gender == null || gender.trim().length() == 0 || password2 == null || password2.trim().length() == 0) {
                    resp.sendRedirect("register.jsp?msg=Error! All the fields are mandatory. Please provide the details.");
                }
                else {
                    dao.register(user);
                    resp.sendRedirect("register.jsp?msg=Registration Successful");
                }
            }
            else if (request_type.equals("login")) {
                final String email2 = req.getParameter("email");
                final String password = req.getParameter("password");
                final User user2 = dao.getUserDetails(email2, password);
                if (email2 == null || email2.trim().length() == 0 || password == null || password.trim().length() == 0) {
                    resp.sendRedirect("login.jsp?msg=Error! All the fields are mandatory. Please provide the details");
                }
                else if (user2 != null) {
                    req.getSession().setAttribute("user", (Object)user2);
                    resp.sendRedirect("welcome.jsp?msg=Successfully logged in as " + user2.getFname() + " " + user2.getLname() + " (" + user2.getRole() + ") ");
                }
                else {
                    resp.sendRedirect("login.jsp?msg=Invalid Credentials");
                }
            }
            else if (request_type.equals("updateprofile")) {
                final User user = new User();
                final String addr = req.getParameter("addr");
                final String email = req.getParameter("email");
                final String fname = req.getParameter("fname");
                final String lname = req.getParameter("lname");
                final String gender = req.getParameter("gender");
                final String mobile = req.getParameter("mobile");
                String role2 = req.getParameter("role");
                if (role2 == null || role2.trim().length() == 0) {
                    role2 = "USER";
                }
                user.setAddr(addr);
                user.setEmail(email);
                user.setFname(fname);
                user.setLname(lname);
                user.setGender(gender);
                user.setMobile(mobile);
                user.setRole(role2);
                if (addr == null || addr.trim().length() == 0 || email == null || email.trim().length() == 0 || fname == null || fname.trim().length() == 0 || lname == null || lname.trim().length() == 0 || mobile == null || mobile.trim().length() == 0 || role2 == null || role2.trim().length() == 0 || gender == null || gender.trim().length() == 0) {
                    resp.sendRedirect("updateprofile.jsp?msg=Error! All the fields are mandatory. Please provide the details");
                }
                else {
                    dao.updateProfile(user);
                    req.getSession().removeAttribute("user");
                    req.getSession().setAttribute("user", (Object)user);
                    resp.sendRedirect("updateprofile.jsp?msg=Profile Updated Successfully");
                }
            }
            else if (request_type.equals("changepassword")) {
                final String oldpassword = req.getParameter("oldpassword");
                final String newpassword = req.getParameter("newpassword");
                if (oldpassword == null || oldpassword.trim().length() == 0 || newpassword == null || newpassword.trim().length() == 0) {
                    resp.sendRedirect("changepassword.jsp?msg=Error! All the fields are mandatory. Please provide the details");
                }
                else {
                    final boolean result = dao.changePassword(((User)req.getSession().getAttribute("user")).getEmail(), oldpassword, newpassword);
                    if (result) {
                        resp.sendRedirect("changepassword.jsp?msg=Successfully Updated Your Password");
                    }
                    else {
                        resp.sendRedirect("changepassword.jsp?msg=Your Current Password is Wrong");
                    }
                }
            }
            else if (request_type.equals("deleteprofile")) {
                dao.deleteProfile(((User)req.getSession().getAttribute("user")).getEmail());
                req.getSession().invalidate();
                resp.sendRedirect("login.jsp?msg=Profile Deleted Successfully");
            }
            else if (request_type.equals("logout")) {
                req.getSession().removeAttribute("dir");
                req.getSession().removeAttribute("sync");
                req.getSession().invalidate();
                resp.sendRedirect("login.jsp?msg=Successfully Logged Out");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("error.jsp?msg=OOPS! Something went wrong");
        }
    }
    
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
