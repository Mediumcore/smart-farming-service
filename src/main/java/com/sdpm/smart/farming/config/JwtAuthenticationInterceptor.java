package com.sdpm.smart.farming.config;

import com.alibaba.fastjson.JSON;
import com.sdpm.smart.farming.common.rest.RestMessage;
import com.sdpm.smart.farming.common.util.JwtTokenUtils;
import io.jsonwebtoken.Claims;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Jwt验证拦截器
 *
 * @author shirukai
 */
public class JwtAuthenticationInterceptor extends HandlerInterceptorAdapter {
    private final String tokenId;
    private final String jwtRegular;

    public JwtAuthenticationInterceptor(String tokenId,String jwtRegular) {
        this.tokenId = tokenId;
        this.jwtRegular = jwtRegular;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getServletPath();
        if (path.matches(jwtRegular)) {
            // 不需要拦截
            return true;
        } else {
            String token = request.getHeader("x-token");
            boolean success = false;
            if (token != null) {
                Claims claims = JwtTokenUtils.validateToken(token);
                if (claims != null) {
                    Object role = claims.get("role");
                    if ("admin".equals(role) || tokenId.equals(claims.get("id").toString())) {
                        success = true;
                    }
                }
            }
            if (!success) {
                RestMessage message = new RestMessage();
                message.setCode(403);
                message.setSuccess(false);
                message.setMsg("权限未认证，请重新登录。");
                response.setContentType("application/json;charset=utf-8");
                try (PrintWriter out = response.getWriter()) {
                    out.write(JSON.toJSONString(message));
                    out.flush();
                } catch (IOException e) {
                    //
                }
                return false;
            } else {
                return true;
            }
        }
    }
}
