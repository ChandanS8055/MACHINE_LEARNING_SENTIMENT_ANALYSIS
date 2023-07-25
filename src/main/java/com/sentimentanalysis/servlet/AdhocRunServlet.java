package com.sentimentanalysis.servlet;

import javax.servlet.ServletResponse;
import javax.servlet.ServletRequest;
import com.sentimentanalysis.vader.VaderService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServlet;

public class AdhocRunServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
    
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        try {
            final String sentence = req.getParameter("sentence");
            if (sentence.trim().length() < 50) {
                resp.sendRedirect("adhoc_run.jsp?msg=The input sentence must be a minimum 50 characters.");
            }
            else {
                final VaderService vs = new VaderService();
                req.setAttribute("result", (Object)vs.analyzeText(sentence));
                req.getRequestDispatcher("adhoc_run.jsp").forward((ServletRequest)req, (ServletResponse)resp);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("error.jsp?msg=Error: " + e.getMessage());
        }
    }
}
