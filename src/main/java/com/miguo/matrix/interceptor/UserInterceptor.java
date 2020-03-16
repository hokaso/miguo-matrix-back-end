package com.miguo.matrix.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miguo.matrix.dto.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 功能描述：
 *
 * @author Hocassian
 * @date 2020-03-08 23:33
 */
@Component
public class UserInterceptor implements HandlerInterceptor {

    @Autowired
    private HttpSession session;

    private static final String STAFF = "user";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handle) throws Exception {

        if(session.getAttribute("user") !=null){
            return true;
        }
        else{
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            OutputStream outputStream = response.getOutputStream();
            outputStream.write((new ObjectMapper().writeValueAsString(new Result<String>().setCode(HttpStatus.UNAUTHORIZED).setMessage("noLogin").setData("noLogin"))).getBytes());
            outputStream.flush();
            outputStream.close();
            return false;
        }
    }
}
