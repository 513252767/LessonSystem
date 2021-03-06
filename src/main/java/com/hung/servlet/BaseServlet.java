package com.hung.servlet;

import com.hung.util.spring.annotation.Autowired;
import com.hung.util.spring.annotation.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @author Hung
 */

@Controller("baseServlet")
public class BaseServlet extends HttpServlet {
    @Autowired
    ActionServlet actionServlet;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        try {
            // 获取请求标识
            String methodName = request.getParameter("method");
            // 获取指定类的字节码对象
            //这里的this指的是继承BaseServlet对象
            Class<? extends BaseServlet> clazz = actionServlet.getClass();
            // 通过类的字节码对象获取方法的字节码对象
            Method method = clazz.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            // 让方法执行
            method.invoke(actionServlet, request, response);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
