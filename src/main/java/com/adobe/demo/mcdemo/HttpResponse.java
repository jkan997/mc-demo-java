/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adobe.demo.mcdemo;

import java.io.Reader;

/**
 *
 * @author jakaniew
 */
public abstract class HttpResponse {

    public abstract void sendResponse(String contentType, int code, byte[] content);

    public void sendResponse(String contentType, byte[] content) {
        sendResponse(contentType, 200, content);
    }

    public void sendResponse(String contentType, String str) {
        sendResponse(contentType, str.getBytes());
    }

    public void sendNotFound(String msg) {
        sendResponse("text/plain", 404,("Not found " + msg).getBytes());
    }

    public String getTemplateStr(String template) throws Exception {
        String res;
        Reader reader = ResourceManager.getInstance().getResourceReader("/views/" + template + ".ejs");
        res = IOHelper.readReaderToString(reader);
        return res;
    }

}
