package com.sentimentanalysis.servlet;

import com.sentimentanalysis.vader.VScore;
import java.util.Map;
import java.io.PrintWriter;
import com.google.gson.Gson;
import com.sentimentanalysis.vader.VaderService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServlet;

public class AsAServiceServlet extends HttpServlet
{
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
    
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        final PrintWriter pw = resp.getWriter();
        try {
            final String text = req.getParameter("text");
            if (text == null || text.trim().length() == 0) {
                pw.println("Invalid Text");
            }
            else {
                final VaderService vs = new VaderService();
                final Map<String, VScore> result = (Map<String, VScore>)vs.analyzeText(text);
                final Gson gson = new Gson();
                final String json = gson.toJson((Object)result);
                pw.println(json);
            }
        }
        catch (Exception e) {
            pw.print("Error: " + e.getMessage());
        }
        pw.close();
    }
}
