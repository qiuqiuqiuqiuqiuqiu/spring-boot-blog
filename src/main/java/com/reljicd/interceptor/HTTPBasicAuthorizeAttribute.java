package com.reljicd.interceptor;

/**
 * Created by Administrator on 2018-06-26.
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reljicd.model.ResultMsg;
import com.reljicd.model.ResultStatusCode;
import com.reljicd.service.BaseAuthSettingService;
import org.springframework.web.context.WebApplicationContext;
import sun.misc.BASE64Decoder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SuppressWarnings("restriction")
public class HTTPBasicAuthorizeAttribute implements Filter {
    private WebApplicationContext wac;
    private BaseAuthSettingService baseAuthSettingService;

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        ResultStatusCode resultStatusCode = checkHTTPBasicAuthorize(request);
        if (resultStatusCode != ResultStatusCode.OK) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setCharacterEncoding("UTF-8");
            httpResponse.setContentType("application/json; charset=utf-8");
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            ObjectMapper mapper = new ObjectMapper();
            ResultMsg resultMsg = new ResultMsg(ResultStatusCode.PERMISSION_DENIED.getErrcode(), ResultStatusCode.PERMISSION_DENIED.getErrmsg(), null);
            httpResponse.getWriter().write(mapper.writeValueAsString(resultMsg));
            httpResponse.setHeader("Cache-Control", "no-store");
            httpResponse.setDateHeader("Expires", 0);
            httpResponse.setHeader("WWW-authenticate", "Basic Realm=\"test\"");
            return;
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        wac = (WebApplicationContext) arg0.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        baseAuthSettingService=(BaseAuthSettingService)wac.getBean("baseAuthSettingService");
    }

    private ResultStatusCode checkHTTPBasicAuthorize(ServletRequest request) {
        try {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String auth = httpRequest.getHeader("Authorization");
            if ((auth != null) && (auth.length() > 6)) {
                String HeadStr = auth.substring(0, 5).toLowerCase();
                if (HeadStr.compareTo("basic") == 0) {
                    auth = auth.substring(6, auth.length());
                    String decodedAuth = getFromBASE64(auth);
                    if (decodedAuth != null) {
                        String[] UserArray = decodedAuth.split(":");

                        if (UserArray != null && UserArray.length == 2) {
                            if (baseAuthSettingService.authIsPass(UserArray[0],UserArray[1])) {
                                return ResultStatusCode.OK;
                            }
                        }
                    }
                }
            }
            return ResultStatusCode.PERMISSION_DENIED;
        } catch (Exception ex) {
            return ResultStatusCode.PERMISSION_DENIED;
        }

    }

    private String getFromBASE64(String s) {
        if (s == null)
            return null;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] b = decoder.decodeBuffer(s);
            return new String(b);
        } catch (Exception e) {
            return null;
        }
    }

}

