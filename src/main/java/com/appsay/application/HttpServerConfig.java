package com.appsay.application;

/**
 * Created by appsay02 on 11-05-2017.
 */
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Scanner;
import java.util.logging.Level;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import org.apache.commons.io.output.TeeOutputStream;
import org.apache.log4j.Logger;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mock.web.DelegatingServletOutputStream;

@Configuration
public class HttpServerConfig {

    private final static Logger LOG = Logger.getLogger(HttpServerConfig.class);

    @Bean
    public FilterRegistrationBean logFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new Filter() {
            @Override
            public void init(FilterConfig fc) throws ServletException {
                LOG.info("Init LOG Request Filter");
            }

            private void logRequest(HttpServletRequest httpReq) throws IOException {
                // log request headers

                LOG.info("Method : "+httpReq.getMethod());
                LOG.info("Path : "+httpReq.getRequestURI());

                LOG.info("### Request Headers:");
                for (String header : Collections.list(httpReq.getHeaderNames())) {
                  LOG.info(header +" : "+httpReq.getHeader(header));
                    //LOG.log(Level.INFO, "\t* {0}: {1}", new Object[]{header, httpReq.getHeader(header)});
                }

                // log request body
                Scanner qs = new Scanner(httpReq.getInputStream()).useDelimiter("\\A");
                String qb = qs.hasNext() ? qs.next() : "[empty body]";
                LOG.info("### Request body: `{0}` ### \n"+ qb.toString());
            }

            private void logResponse(HttpServletResponse httpResp, ByteArrayOutputStream baos) {
                // log response headers
                LOG.info("status :"+httpResp.getStatus());
              //  LOG.log(Level.INFO, "### Response [{0}] Headers:", httpResp.getStatus());
                for (String header : httpResp.getHeaderNames()) {
                    LOG.info(header +" : "+httpResp.getHeader(header));
                //    LOG.log(Level.INFO, "\t* {0}: {1}", new Object[]{header, httpResp.getHeader(header)});
                }
                // log response body
                LOG.info("### Response body: "+baos.toString());
                //LOG.log(Level.INFO, "### Response body: `{0}` ###", baos.toString());
            }

            @Override
            public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
                logRequest((HttpServletRequest) req);

                HttpServletResponse httpResp = (HttpServletResponse) resp;
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                final PrintStream ps = new PrintStream(baos);
                chain.doFilter(req, new HttpServletResponseWrapper(httpResp) {
                    @Override
                    public ServletOutputStream getOutputStream() throws IOException {
                        return new DelegatingServletOutputStream(new TeeOutputStream(super.getOutputStream(), ps));
                    }

                    @Override
                    public PrintWriter getWriter() throws IOException {
                        return new PrintWriter(new DelegatingServletOutputStream(new TeeOutputStream(super.getOutputStream(), ps)));
                    }
                });
                logResponse(httpResp, baos);

            }

            @Override
            public void destroy() {
                LOG.info("Destroy LOG Request Filter");
            }
        });
        return registration;
    }
}