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


@WebServlet("my-statistic")
public class GetShooterStatServlet extends HttpServlet{

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
                prepareJsonShooterData(id, resp);
            }else {
                sender.sendResponse(resp, "no data");
            }
        }else {
            sender.sendResponse(resp, "shooterId is null");
        }
    }

    private void prepareJsonShooterData(long id, HttpServletResponse resp){
        ShooterEntity shooter = dataEditor.getTheShooter(id);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("lyingHit", shooter.getLyingHit());
        jsonObject.put("lyingShootCount", shooter.getLyingShootCount());
        jsonObject.put("standHit", shooter.getStandHit());
        jsonObject.put("standShootCount", shooter.getStandShootCount());
        jsonObject.put("lyingStat", shooter.getLyingStat());
        jsonObject.put("standStat", shooter.getStandStat());
        jsonObject.put("generalStat", shooter.getGeneralStat());
        String jsonStatistic = jsonObject.toString();
        sender.sendResponse(resp, jsonStatistic);
    }
}
