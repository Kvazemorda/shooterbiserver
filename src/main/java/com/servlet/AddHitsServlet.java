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

@WebServlet("hits")
public class AddHitsServlet extends HttpServlet {

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
            ShooterEntity shooter = createShooterFromRequest(paramsWithValue);
            if(shooter != null && dataEditor.isShooterExist(shooter.getId())){
                dataEditor.addHitToShooter(shooter);
                sender.sendResponse(resp, "hits saved");
            }else {
                //если не найден ID из запроса, то создаем новый
                ShooterEntity shooterNew = dataEditor.createTheNewShooter();
                //обновляем ID у стрелка полученного из запроса, на новый созданный в базе
                shooter.setId(shooterNew.getId());
                if(shooter != null){
                    dataEditor.addHitToShooter(shooter);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("shooterId", shooter.getId()); //hits saved for the new id;
                    sender.sendResponse(resp, jsonObject.toString());
                }else {
                    sender.sendResponse(resp, "something going wrong");
                }
            }
        }
    }

    private ShooterEntity createShooterFromRequest(HashMap<String, String> paramsWithValue) {
        String hitsLyingParamName = "hitsLying";
        int hitsLying = Integer.valueOf(paramsWithValue.get(hitsLyingParamName));

        String shootCountLyingParamName = "hitsCountLying";
        int shootCountLying = Integer.valueOf(paramsWithValue.get(shootCountLyingParamName));

        String hitsStandParamName = "hitsStand";
        int hitsStand = Integer.valueOf(paramsWithValue.get(hitsStandParamName));

        String shootCountStandParamName = "hitsCountStand";
        int shootCountStand = Integer.valueOf(paramsWithValue.get(shootCountStandParamName));

        String  lyingStatParamName = "lyingStat";
        double lyingStat = Double.valueOf(paramsWithValue.get(lyingStatParamName));

        String  standStatParamName = "standStat";
        double standStat = Double.valueOf(paramsWithValue.get(standStatParamName));

        String hitsXParamName = "hitsX";
        double hitsX = Double.valueOf(paramsWithValue.get(hitsXParamName));

        String hitsYParamName = "hitsY";
        double hitsY = Double.valueOf(paramsWithValue.get(hitsYParamName));

        String shootCountRegisterParamName = "hitsCountRegister";
        int shootCountRegister = Integer.valueOf(paramsWithValue.get(shootCountRegisterParamName));

        String registerStatParamName = "registerStat";
        double registerStat = Double.valueOf(paramsWithValue.get(registerStatParamName));

        String shooterIdParamName = "userid";
        long userId = Long.valueOf(paramsWithValue.get(shooterIdParamName));

        ShooterEntity shooter = new ShooterEntity(
                userId,
                shootCountStand,
                hitsStand,
                shootCountLying,
                hitsLying,
                hitsX,
                hitsY,
                shootCountRegister,
                standStat,
                lyingStat,
                registerStat
        );

        return shooter;
    }
}
