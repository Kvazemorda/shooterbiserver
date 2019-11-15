package com.servlet;

import com.bean.DataEditor;
import com.bean.RequestReader;
import com.bean.ResponseSender;
import com.entity.ShooterEntity;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


@WebServlet("better-ten-register")
public class GetBetterShooterServlet extends HttpServlet{

    @EJB DataEditor dataEditor;
    @EJB RequestReader reader;
    @EJB ResponseSender sender;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleRequest(resp, req);
    }

    private void handleRequest(HttpServletResponse resp, HttpServletRequest req) {
        HashMap<String, String> paramsWithValue = reader.readRequest(req);
        String shooterId = paramsWithValue.get("userid");
        if(shooterId != null){
            long id = Long.valueOf(shooterId);
            if(dataEditor.isShooterExist(id)){
                prepareJsonShooterData(dataEditor.tenOfBetterRegisterThenTheShooter(id), resp);
            }else {
                sender.sendResponse(resp, "no data");
            }
        }else {
            sender.sendResponse(resp, "shooterId is null");
        }
    }

    /**
     * Метод преобразует список стрелков в JSON объекты и отправляет обратно пользователю
     * @param listOfShooters
     * @param resp
     */
    private void prepareJsonShooterData(ArrayList<ShooterEntity> listOfShooters, HttpServletResponse resp){
        JSONArray jsonArray = new JSONArray();
        for(ShooterEntity shooter: listOfShooters){
            JSONObject jsonObject = new JSONObject();
            if(shooter.getName() != null){
                jsonObject.put("name", shooter.getName());
            }else {
                jsonObject.put("name", "shooter-" + shooter.getId());
            }
            jsonObject.put("stat", shooter.getRegisterStat());
            jsonArray.put(jsonObject);
        }
        JSONObject fullObject = new JSONObject();
        fullObject.put("shooters", jsonArray);
        sender.sendResponse(resp, fullObject.toString());
    }

}
