package com.adobe.demo.mcdemo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 *
 * @author jakaniew
 */
public class ResourceManager {

    public final static String BASE_PATH = "";//"/Volumes/MacData/jakaniew/git/Adobe/Other/MarketingCloudDemo/mc-demo-java/src/resources";
    public final static String NODE_BASE_PATH = "";//"/Volumes/MacData/jakaniew/git/Adobe/Other/MarketingCloudDemo/mc-demo";
    public final static boolean USE_EMBEDED_JS = true;

    private static ResourceManager _instance;

    public static ResourceManager getInstance() {
        if (_instance == null) {
            _instance = new ResourceManager();
        }
        return _instance;
    }

    public String readFileSync(String relPath, String encoding) {
        return readResource(relPath);
    }

    public String readFileSync(String o) {
        return readFileSync(o, "UTF-8");
    }

    public String readResource(String relPath) {
        String res = "";
        try {
            Reader reader = getResourceReader(relPath);
            res = IOHelper.readReaderToString(reader);
            return res;
        } catch (Exception ex) {
            LogHelper.logError(ex);
        }
        return res;
    }

    public Reader getResourceReader(String relPath) throws Exception {
        InputStream is = getResourceInputStream(relPath);
        if (is != null) {
            InputStreamReader reader = new InputStreamReader(is);
            return reader;
        }
        return null;
    }

    public InputStream getResourceInputStream(String relPath) throws Exception {
        if (!relPath.startsWith("/")) {
            relPath = "/" + relPath;
        }
        if (USE_EMBEDED_JS) {
            try{
            InputStream is = this.getClass().getResourceAsStream(relPath);
            return is;
            }  catch (Exception ex){
                LogHelper.logError(ex);
            }
        } else {
            String filePath = NODE_BASE_PATH + relPath;
            File f = new File(filePath);
            if (f.exists()) {
                return new FileInputStream(f);
            }
        }
        return null;
    }

    public Reader getInternalResourceReader(String relPath) throws Exception {
        InputStream is = this.getClass().getResourceAsStream(relPath);
        InputStreamReader reader = new InputStreamReader(is);
        return reader;

    }

}
