package com.bean;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Local
@Stateless
public class ResponseSender {

    public void sendResponse(HttpServletResponse resp, String answer){
        PrintWriter out = null;
        try {
            out = resp.getWriter();
            resp.setHeader("Access-Control-Allow-Origin", "*");
            resp.setHeader("Content-Type", "text/plain");
            resp.setStatus(200);
            out.append(answer);
        } catch (IOException e) {
            e.printStackTrace();
            resp.setStatus(400);
            out.append("IOException");
        }catch (NullPointerException e) {
            e.printStackTrace();
            resp.setStatus(400);
            out.append("NullPointerException");
        }
        out.close();
    }
}
