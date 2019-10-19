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

@WebServlet("create-user")
public class CreateNewUserServlet extends HttpServlet {

    @EJB DataEditor dataEditor;
    @EJB RequestReader reader;
    @EJB ResponseSender sender;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        requestToAddNewShooter(req, resp);
    }
    /*
    метод получает userid и передает его для сохранения в базу данных.
    В ответ отправляет success или reject
     */
    public void requestToAddNewShooter(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HashMap<String, String> paramsWithValue = reader.readRequest(req);
        String userId = paramsWithValue.get("userid");
        if(userId != null && userId != ""){
            String shooterId = userId;
            if(dataEditor.isShooterExist(Long.valueOf(shooterId))){
                sender.sendResponse(resp, "exist");
            }else {
                ShooterEntity shooterEntity = dataEditor.createTheNewShooter();
                if(dataEditor.isShooterExist(shooterEntity.getId())){
                    JSONObject object = new JSONObject();
                    object.put("success", shooterEntity.getId());
                    sender.sendResponse(resp, object.toString());
                }else {
                    sender.sendResponse(resp, "something going wrong");
                }
            }
        }else {
            ShooterEntity shooterEntity = dataEditor.createTheNewShooter();
            JSONObject object = new JSONObject();
            object.put("success", shooterEntity.getId());
            sender.sendResponse(resp, object.toString());
        }
    }
}
