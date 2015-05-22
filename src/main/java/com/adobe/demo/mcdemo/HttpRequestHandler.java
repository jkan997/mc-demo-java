/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adobe.demo.mcdemo;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;

public class HttpRequestHandler implements HttpHandler {

    public void handle(final HttpExchange t) throws IOException {
        LogHelper.log("Processing URL %s", t.getRequestURI().toString());
        String path = t.getRequestURI().getPath();
        String query = t.getRequestURI().getQuery();
            //t.sendResponseHeaders(200, response.length());
        //OutputStream os = t.getResponseBody();

        //  t.sendResponseHeaders(200, response.length());
        HttpRequest request = new HttpRequest();
        request.query = query;
        HttpResponse response = new HttpResponse() {
            @Override
            public void sendResponse(String contentType, int code, byte[] content) {
                try {
                    OutputStream responseOs = t.getResponseBody();
                    if (contentType != null) {
                        t.getResponseHeaders().add("Content-Type", contentType);
                    }
                    t.sendResponseHeaders(code, content.length);

                    responseOs.write(content);
                    responseOs.close();
                } catch (Exception ex) {
                    LogHelper.logError(ex);
                }
            }

        };

        RouterManager.processRequest("GET", path, request, response);

    }
}
