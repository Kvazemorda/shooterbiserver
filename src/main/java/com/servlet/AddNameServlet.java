package com.servlet;

import com.bean.DataEditor;
import com.bean.RequestReader;
import com.bean.ResponseSender;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@WebServlet("add-name")
public class AddNameServlet extends HttpServlet {

    @EJB DataEditor dataEditor;
    @EJB RequestReader reader;
    @EJB ResponseSender sender;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleRequest(req, resp);
    }

    private void handleRequest(HttpServletRequest req, HttpServletResponse resp) {
        HashMap<String, String> paramsWithValue = reader.readRequest(req);
        String userId = paramsWithValue.get("userid");
        if(userId != null){
            long shooterId = Long.valueOf(userId);
            String name = paramsWithValue.get("name");

            if(dataEditor.isShooterExist(shooterId)){
                dataEditor.addNameOfShooter(shooterId, name);
                sender.sendResponse(resp, "name saved");
            }else {
                if(dataEditor.createTheNewShooter() != null){
                    dataEditor.addNameOfShooter(shooterId, name);
                    sender.sendResponse(resp, "name saved");
                }else {
                    sender.sendResponse(resp, "something going wrong");
                }
            }
        }
    }
}
