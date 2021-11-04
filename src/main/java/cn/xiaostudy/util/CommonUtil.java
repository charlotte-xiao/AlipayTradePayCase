package cn.xiaostudy.util;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author charlotte xiao
 * @date 2021/10/28
 * @description
 */
public class CommonUtil {

    /**
     * 获取GET请求参数
     * @param httpServletRequest 请求servlet
     * @return map格式的参数
     */
    public static Map<String,String> getRequestParams(HttpServletRequest httpServletRequest){
        Map<String, String> params = new HashMap<>(16);
        Map<String, String[]> requestParams = httpServletRequest.getParameterMap();
        for (String name : requestParams.keySet()) {
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        return params;
    }
}
