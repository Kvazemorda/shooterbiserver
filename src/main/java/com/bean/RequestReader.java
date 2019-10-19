package com.bean;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;

@Local
@Stateless
public class RequestReader {

    public RequestReader() {
    }

    public HashMap<String, String> readRequest(HttpServletRequest req){
        HashMap<String, String> paramsWithValue = new HashMap<>();
        Enumeration<String> parameterNames = req.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            String[] paramValues = req.getParameterValues(paramName);

            for (int i = 0; i < paramValues.length; i++) {
                String paramValue = paramValues[i];
                paramsWithValue.put(paramName, paramValue);
            }
        }
        return paramsWithValue;
    }
}
