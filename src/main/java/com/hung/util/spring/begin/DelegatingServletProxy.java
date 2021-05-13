package com.hung.util.spring.begin;

import com.hung.util.spring.ioc.ApplicationContext;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author Hung
 */
public class DelegatingServletProxy extends GenericServlet {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String targetBean;
    private Servlet proxy;

    @Override
    public void service(ServletRequest req, ServletResponse res)
            throws ServletException, IOException {
        proxy.service(req, res);
    }

    /**
     * 初始化
     */
    @Override
    public void init() throws ServletException {
        this.targetBean = getServletName();
        getServletBean();
        proxy.init(getServletConfig());
    }

    /**
     * 获取Bean
     */
    private void getServletBean() {
        ApplicationContext applicationContext = new ApplicationContext();
        this.proxy = (Servlet) applicationContext.getBean(targetBean);
    }

}
