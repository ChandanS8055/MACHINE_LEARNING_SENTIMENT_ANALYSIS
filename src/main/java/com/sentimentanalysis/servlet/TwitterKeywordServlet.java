package com.sentimentanalysis.servlet;

import com.sentimentanalysis.dao.TwitterKeywordDAO;
import javax.servlet.ServletResponse;
import javax.servlet.ServletRequest;
import java.sql.Timestamp;
import com.sentimentanalysis.pojo.TwitterKeyword;
import com.sentimentanalysis.pojo.User;
import com.sentimentanalysis.daoimpl.TwitterKeywordDAOImpl;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServlet;

public class TwitterKeywordServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
    
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        try {
            final TwitterKeywordDAO tDao = new TwitterKeywordDAOImpl();
            final User user = (User)req.getSession().getAttribute("user");
            final String requestType = req.getParameter("requestType");
            if (requestType.equals("create")) {
                final TwitterKeyword th = new TwitterKeyword();
                th.setUser(user.getEmail());
                th.setKeyword(req.getParameter("keyword"));
                th.setEntry_time(new Timestamp(System.currentTimeMillis()));
                tDao.create(th);
                resp.sendRedirect("twitter_keyword_add.jsp?msg=Twitter keyword added successfully");
            }
            else if (requestType.equals("get")) {
                req.setAttribute("keywords", (Object)tDao.getTwitterKeywordsByUser(user.getEmail()));
                req.getRequestDispatcher("twitter_keyword_get.jsp").forward((ServletRequest)req, (ServletResponse)resp);
            }
            else if (requestType.equals("delete")) {
                tDao.delete(req.getParameter("keyword"), user.getEmail());
                resp.sendRedirect("twitterkeyword?requestType=get&msg=Twitter Keyword " + req.getParameter("keyword") + " removed successfully");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("error.jsp?msg=Error: " + e.getMessage());
        }
    }
}
