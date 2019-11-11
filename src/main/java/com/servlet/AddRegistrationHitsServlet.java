package com.servlet;

import com.bean.DataEditor;
import com.bean.RequestReader;
import com.bean.ResponseSender;
import com.entity.ShooterEntity;
import org.json.JSONObject;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@WebServlet("reghits")
public class AddRegistrationHitsServlet extends HttpServlet {

    @EJB
    DataEditor dataEditor;
    @EJB
    RequestReader reader;
    @EJB
    ResponseSender sender;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleRequest(req, resp);
    }

    private void handleRequest(HttpServletRequest req, HttpServletResponse resp) {
        HashMap<String, String> paramsWithValue = reader.readRequest(req);
        String userId = paramsWithValue.get("userid");
        if(userId != null){
            long shooterId = Long.valueOf(userId);
            double xHits = Double.valueOf(paramsWithValue.get("xHits"));
            double yHits = Double.valueOf(paramsWithValue.get("yHits"));
            int countOfRegHits = Integer.valueOf(paramsWithValue.get("countOfRegHits"));
            ShooterEntity shooterEntity = dataEditor.getTheShooter(shooterId);
            if(shooterEntity != null){
                dataEditor.addRegHitToShooter(shooterEntity, xHits, yHits, countOfRegHits);
                sender.sendResponse(resp, "hits saved");
            }else {
                shooterEntity = dataEditor.createTheNewShooter();
                if(shooterEntity != null){
                    dataEditor.addRegHitToShooter(shooterEntity, xHits, yHits, countOfRegHits);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("shooterId", shooterEntity.getId()); //hits saved for the new id;
                    sender.sendResponse(resp, jsonObject.toString());
                }else {
                    sender.sendResponse(resp, "something going wrong");
                }
            }
        }
    }
}
