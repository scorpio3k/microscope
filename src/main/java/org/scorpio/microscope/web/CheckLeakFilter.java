package org.scorpio.microscope.web;

import java.io.File;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.scorpio.microscope.enginex.ConnLeaksCheckUtils;
import org.scorpio.microscope.enginex.util.MFileWriter;


public class CheckLeakFilter implements Filter {

    private MFileWriter pfw = new MFileWriter();
    private static boolean isCheckLeak = true;

    public void destroy() {
    }

    public void init(FilterConfig config) throws ServletException {
        try {
            String checkLeak = config.getInitParameter("checkLeak");
            if (checkLeak != null && checkLeak.trim().length() > 0) {
                isCheckLeak = !"false".equalsIgnoreCase(checkLeak);
                System.out.println("isCheckLeak=" + isCheckLeak);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        try {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            /**
             * һ��request����ڶ��ν���ù�������servlet�ض���ĳ�����
             */
            if (request.getAttribute(CheckLeakFilter.class.getName()) != null) {
                filterChain.doFilter(request, response);
                return;
            }
            request.setAttribute(CheckLeakFilter.class.getName(), Boolean.TRUE);
            try {
                filterChain.doFilter(servletRequest, servletResponse);
            } catch (Throwable e) {
                e.printStackTrace();
            }
            checkJdbcResLeaks(request);
        } finally {
            org.scorpio.microscope.enginex.util.ThreadResources.clear();
        }
    }

    public void checkJdbcResLeaks(HttpServletRequest request) {
        int size = 0;
        try {
            if (org.scorpio.microscope.enginex.core.Setting.isCheckConnLeaks()) {
                boolean isConnLeaks = org.scorpio.microscope.enginex.ConnLeaksCheckUtils.isConnLeaks();
                if (isConnLeaks) {
                    size = org.scorpio.microscope.enginex.ConnLeaksCheckUtils.getConnSizeOfCurrentThread();
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        if (size > 0) {
            String url = request.getRequestURL() + "?" + request.getQueryString();
            RuntimeException e = new RuntimeException("Connection leak:" + size + ", URL:" + url);
            logError(request, e);
            e.printStackTrace();
            ConnLeaksCheckUtils.closeConnOnThread();
            throw e;
        }
    }

    public void logError(HttpServletRequest request, RuntimeException e) {
        try {
            File f = new File(request.getSession().getServletContext().getRealPath("/"));
            pfw.setLogFilePath(f.getParent(), "P6Error.log.");
            pfw.writeLine(e.getMessage());
            pfw.writeLine(ConnLeaksCheckUtils.getStackTrace());
        } catch (Throwable e1) {
            e1.printStackTrace();
        }
    }
}
