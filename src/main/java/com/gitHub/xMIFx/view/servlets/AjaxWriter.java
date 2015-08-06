package com.gitHub.xMIFx.view.servlets;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

 public abstract class AjaxWriter {
    protected  void sendAnswer(String answerStr, HttpServletResponse resp) throws IOException {
        if (answerStr == null) {
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            resp.setContentType("text/xml");
            resp.setHeader("Cache-Control", "no-cache");
            resp.getWriter().write(answerStr);
        }
    }
}
