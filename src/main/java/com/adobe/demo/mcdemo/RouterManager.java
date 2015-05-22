/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adobe.demo.mcdemo;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import jdk.nashorn.api.scripting.JSObject;

/**
 *
 * @author jakaniew
 */
public class RouterManager {

    private static Map<String, JSObject> routers = new HashMap<String, JSObject>();

    public static void registerRouter(String path, JSObject router) {
        LogHelper.log("REGISTERED ROUTER " + path);
        routers.put(path, router);
    }

    public static void processRequest(String method, String path, HttpRequest request, HttpResponse response) {
        try {
            for (Map.Entry<String, JSObject> me : routers.entrySet()) {
                JSObject router = me.getValue();
                String prefixPath = (String) router.getMember("prefixPath");
                if (path.startsWith(prefixPath)) {
                    LogHelper.log("Prefix fine for router " + prefixPath);
                    JSObject processCall = (JSObject) router.getMember("processCall");
                    boolean res = (boolean) processCall.call(router, method, path, request, response);
                    if (res == true) {
                        return;
                    }
                }
            }

            String assetPath = "/public" + path;
            InputStream is = ResourceManager.getInstance().getResourceInputStream(assetPath);
            if (is != null) {
                try {
                    byte[] bytes = IOHelper.readInputStreamToBytes(is);
                    String contentType = null;
                    if (path.endsWith(".svg")) {
                        contentType = "image/svg+xml";
                    }
                    if (path.endsWith(".css")) {
                        //   contentType = "text/css";
                    }
                    if (path.endsWith(".css")) {
                        //   contentType = "application/javascript";
                    }
                    if (path.endsWith(".png")) {
                        contentType = "image/png";
                    }
                    if (path.endsWith(".jpg")) {
                        contentType = "image/jpeg";
                    }
                    if (path.endsWith(".json")) {
                        //  contentType = "application/json";
                    }
                    if (path.endsWith(".html")) {
                        //  contentType = "text/html";
                    }
                    response.sendResponse(contentType, bytes);
                    return;
                } catch (Exception ex) {
                    LogHelper.logError(ex);
                }
            } else {
                LogHelper.log("Asset not found in public "+assetPath);
            }
        } catch (Exception ex) {
            LogHelper.logError(ex);
        }
        response.sendNotFound(path);
    }

}
