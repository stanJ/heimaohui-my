package com.xa3ti.blackcat.base.interceptor;

import com.alibaba.fastjson.JSON;
import com.xa3ti.blackcat.base.util.Settings;
import com.xa3ti.blackcat.base.util.Token;
import com.xa3ti.blackcat.business.entity.Browse;
import com.xa3ti.blackcat.business.service.BrowseService;
import com.xa3ti.blackcat.business.util.TokenCenter;
import com.xa3ti.blackcat.remote.vo.ValidataVo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by jackson.liu on 2017/2/24.
 * URL 拦截器
 */
public class URLRoleInterceptor implements HandlerInterceptor {
    private static Logger log = Logger.getLogger(URLRoleInterceptor.class);
    private static String config_count_url = "";
    @Autowired
    private BrowseService browseService;
    private  Map<String,Object> resMp = null;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        resMp = null;
        try {

            response.setHeader("usesParams","");
            String url = request.getRequestURI();
            String token = URLDecoder.decode(request.getParameter("token"), "utf-8");
            Token mytoken = null;
            String userId = null;
            try{
                 mytoken = TokenCenter.getToken(token);
                 userId = mytoken.getUserId();
            }catch (Exception e){
             /*   ValidataVo vo = new ValidataVo("-1", "令牌失效，请重新登录", null);
                String jstr = JSON.toJSONString(vo);
                response.getWriter().println(jstr);
                return  false;*/
            }
            log.info("#################################################################");
            log.info("url:" + url);
            log.info("token:" + token);
            System.out.println("tokenType:" + mytoken.getType());
            boolean isValidataUrl = browseService.isValidataUrl(url, mytoken.getType());
            System.out.println("isValidataUrl:" + isValidataUrl);
            log.info("#################################################################");

            if (null != mytoken && isValidataUrl) {
                Browse browse = getBrowse(request);
                if (null != browse) {
                    boolean flag = browseService.checkCanBrowsed(browse, mytoken.getType());
                    if (flag) {
                        ValidataVo vo = new ValidataVo("-1", "已超过访问次数", null);
                        String jstr = JSON.toJSONString(vo);
                        response.getWriter().println(jstr);
                        return false;
                    }
                    if (!flag) {
                        log.info("<<<<<<<<<<<<checkCanBrowsed : is false>>>>>>>>>>>>>>>>>>");
                        browseService.checkSaveBrowsed(browse);
                    }
                    resMp = browseService.findUserBrowseInfo(browse, mytoken.getType());
                    String jstr = JSON.toJSONString(resMp);
                    response.setHeader("usesParams",jstr);


                   // response.getWriter().append(jstr);
                }
            }
            if(isConfigParentUrl(url) && null != userId && userId.trim().length()>0){
                resMp = browseService.findUserBrowseParentInfo(url,userId , mytoken.getType());
                String jstr = JSON.toJSONString(resMp);
                response.setHeader("usesParams",jstr);
            }
            response.setHeader("Access-Control-Expose-Headers", "usesParams");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {


    }

    private boolean  isConfigParentUrl(String url){
         boolean flag = false;
        config_count_url = Settings.getInstance().getString("config_count_url");
        if(null != url && url.trim().length()>0) {
            String[] urls = config_count_url.split(";");
            for (String str : urls) {
                flag = (url.indexOf(str) != -1);
                if (flag)
                    break;

            }
        }
        return flag;
    }

    private Browse getBrowse(HttpServletRequest request) {
        Browse browse = null;
        try {
            String paramsjstr = getParams(request.getParameterMap());
            log.info("paramsjstr:" + paramsjstr);
            log.info("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
            if (null != paramsjstr && paramsjstr.trim().length() > 0) {
                String token = URLDecoder.decode(request.getParameter("token"), "utf-8");
                Token mytoken = TokenCenter.getToken(token);
                Integer tokenType = mytoken.getType();
                Date date = new Date();
                String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
                String userUrl = request.getRequestURI();
                //  HashMap<String, String[]> params = (HashMap<String, String[]>) request.getParameterMap();
                if (null != token && token.trim().length() > 0) {
                    String[] strs = paramsjstr.split("token");
                    paramsjstr = strs[0];
                    if (strs[1].indexOf("&") != -1) {
                        paramsjstr += strs[1].substring(strs[1].indexOf("&"));
                    }
                }

                if (paramsjstr.endsWith("&")) {
                    paramsjstr = paramsjstr.substring(0, paramsjstr.length() - 1);
                }
                String userId = mytoken.getUserId();
                String modelId = request.getParameter("modelId");
                ////////////////////////////
                browse = new Browse();

                browse.setUserId(userId);
                browse.setBtime(date);
                browse.setCreateTime(time);
                browse.setUserUrl(userUrl);
                browse.setUserParams(paramsjstr);
                browse.setBtype(tokenType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return browse;
    }

    private String getParams(Map<String, String[]> params) {
        String str = "";
        try {
            if (null != params && params.size() > 0) {
                int i = 0;
                Iterator<String> it = params.keySet().iterator();
                while (it.hasNext()) {
                    String val = "";
                    String key = it.next();
                    String[] vals = params.get(key);
                    if (null != vals && vals.length > 0) {
                        if (vals.length == 1) {
                            val = vals[0];
                        } else {
                            val = JSON.toJSONString(vals);
                        }
                    }
                    if (i == 0) {
                        str += key + "=" + val;
                    } else {
                        str += "&" + key + "=" + val;
                    }
                    i++;
                }
            }
            str = str.replace("'", "");
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return str;
    }

}
