package com.sentimentanalysis.servlet;

import com.sentimentanalysis.vader.TwitterKeywordVaderThread;
import com.sentimentanalysis.vader.TwitterHandleVaderThread;
import com.sentimentanalysis.pojo.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServlet;

public class RunTwitterSentimentServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
    
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        try {
            final User user = (User)req.getSession().getAttribute("user");
            final String check = (String)req.getSession().getAttribute("inprogress");
            if (check != null && check.equals("yes")) {
                resp.sendRedirect("Twitter Sentiment Analysis run has already been initiated and haven't completed yet. Please wait for some more time");
            }
            else {
                req.getSession().removeAttribute("twitterhandleresults");
                req.getSession().removeAttribute("twitterkeywordresults");
                new TwitterHandleVaderThread(user.getEmail(), req.getSession());
                new TwitterKeywordVaderThread(user.getEmail(), req.getSession());
                resp.sendRedirect("run.jsp");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("error.jsp?msg=Error: " + e.getMessage());
        }
    }
}