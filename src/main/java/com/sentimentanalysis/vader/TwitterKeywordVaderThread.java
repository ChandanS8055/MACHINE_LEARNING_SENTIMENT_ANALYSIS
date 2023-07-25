package com.sentimentanalysis.vader;

import javax.servlet.http.HttpSession;

public class TwitterKeywordVaderThread implements Runnable
{
    HttpSession session;
    String email;
    Thread t;
    
    public TwitterKeywordVaderThread(final String email, final HttpSession session) {
        this.email = email;
        this.session = session;
        (this.t = new Thread(this)).start();
    }
    
    @Override
    public void run() {
        try {
            this.session.setAttribute("inprogress", (Object)"yes");
            final VaderService service = new VaderService();
            this.session.setAttribute("twitterkeywordresults", (Object)service.analyzeTwitterKeywords(this.email));
            this.session.removeAttribute("inprogress");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
