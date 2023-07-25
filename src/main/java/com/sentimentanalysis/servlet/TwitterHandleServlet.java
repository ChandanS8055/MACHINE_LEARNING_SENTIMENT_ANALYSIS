package com.sentimentanalysis.servlet;

import com.sentimentanalysis.dao.TwitterHandleDAO;
import javax.servlet.ServletResponse;
import javax.servlet.ServletRequest;
import java.sql.Timestamp;
import com.sentimentanalysis.pojo.TwitterHandle;
import com.sentimentanalysis.pojo.User;
import com.sentimentanalysis.daoimpl.TwitterHandleDAOImpl;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServlet;

public class TwitterHandleServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
    
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        try {
            final TwitterHandleDAO tDao = new TwitterHandleDAOImpl();
            final User user = (User)req.getSession().getAttribute("user");
            final String requestType = req.getParameter("requestType");
            if (requestType.equals("create")) {
                final TwitterHandle th = new TwitterHandle();
                th.setUser(user.getEmail());
                th.setHandle(req.getParameter("handle"));
                th.setEntry_time(new Timestamp(System.currentTimeMillis()));
                tDao.create(th);
                resp.sendRedirect("twitter_handle_add.jsp?msg=Twitter handle added successfully");
            }
            else if (requestType.equals("get")) {
                req.setAttribute("handles", (Object)tDao.getTwitterHandlesByUser(user.getEmail()));
                req.getRequestDispatcher("twitter_handle_get.jsp").forward((ServletRequest)req, (ServletResponse)resp);
            }
            else if (requestType.equals("delete")) {
                tDao.delete(req.getParameter("handle"), user.getEmail());
                resp.sendRedirect("twitterhandle?requestType=get&msg=Twitter handle " + req.getParameter("handle") + " removed successfully");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("error.jsp?msg=Error: " + e.getMessage());
        }
    }
}