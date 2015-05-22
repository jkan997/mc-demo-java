package com.adobe.demo.mcdemo;

import com.sun.net.httpserver.HttpServer;
import java.io.Reader;
import java.net.InetSocketAddress;
import java.util.logging.Logger;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class McDemoServer {

    public final Logger logger = Logger.getLogger(McDemoServer.class.getName());
    private final ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
    private final ResourceManager resourceManager = ResourceManager.getInstance();

    private void appUse(String path, String jsFile) throws Exception {
        if (!jsFile.endsWith(".js")) {
            jsFile += ".js";
        }
        Reader reader = null;
        reader = resourceManager.getResourceReader("/routes/" + jsFile);
        if (reader == null) {
            reader = resourceManager.getResourceReader("/routes/aam/" + jsFile);

        }
        if (reader == null) {
            reader = resourceManager.getResourceReader("/routes/analytics/" + jsFile);

        }
        if (reader != null) {
            evalJs(processNodeModule(jsFile, path, reader));
        } else {

        }
    }

    private void evalInternalJs(String relPath) throws Exception {
        Reader reader = resourceManager.getInternalResourceReader(relPath);
        engine.eval(reader);
        reader.close();
    }

    private void evalJs(String jsCode) throws ScriptException {
        engine.eval(jsCode);
    }

    public void init() throws Exception {
        evalInternalJs("/js/log.js");
        evalInternalJs("/jslibs/ejs.js");
        evalInternalJs("/jslibs/moment.js");
        evalInternalJs("/js/router.js");
        evalInternalJs("/js/setup.js");
        evalJs(processNodeModule("mac.js", "/mac", "/routes/mac.js"));
        evalJs(processNodeModule("demo-controller.js", "", "/libs/demo-controller.js"));
        appUse("/reports/anomalydetection", "anomalydetection");
        //appUse("/mac/datasources", "datasources");
        //appUse("/democonsole", "democonsole");
        //appUse("/view", "view");
        //appUse("/rest", "rest");
        //appUse("/portal", "aam");
        //appUse("/portal/api/v1", "aamapi");
        //appUse("/display", "amo");
        //appUse("/CMDashboard", "amoContainer");
        //appUse("/audiences.html", "audiences");
        //appUse("/audiencelibrary", "audiencesapi");
        //appUse("/target", "target");
        //appUse("/onsite", "onsite");
        appUse("/mac", "mac");
        appUse("/analytics", "analytics");
        evalInternalJs("/js/init.js");

    }

    private String processNodeModule(String name, String routerPrefix, String relPath) throws Exception {
        Reader jsReader = resourceManager.getResourceReader(relPath);
        return processNodeModule(name, routerPrefix, jsReader);
    }

    private static String processNodeModule(String name, String routerPrefix, Reader reader) throws Exception {
        String s = IOHelper.readReaderToString(reader);
        s = "nodeModules['" + name + "'] = (function(){var module = {}; module.exports = {}; var exports = {};" + s + "; return module.exports; })()";
        s = s.replace("express.Router()", "new Router('" + routerPrefix + "')");
        return s;
    }

    public void startServer() throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", new HttpRequestHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    public static void main(String[] args) throws Exception {
        McDemoServer s = new McDemoServer();
        s.init();
        s.startServer();
    }
}
