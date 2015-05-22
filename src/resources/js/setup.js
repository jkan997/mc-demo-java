log("INIT MC DEMO JAVA!");
var ResourceManager = Java.type('com.adobe.demo.mcdemo.ResourceManager');


var demo = {};

var fs = ResourceManager.getInstance();
var nodeModules = {};
nodeModules["fs"] = fs;
var module = {};

var express = {};
express.Router = function () {
    return new Router();
};


function require(arg) {

    for (var key in nodeModules) {
        log("TRY REQUIRE " + key + " " + arg);
        if (arg.indexOf(key) >= 0) {
            log("REQUIRE " + key + " " + arg);
            return nodeModules[key];
        }
    }
    if (arg.indexOf("democonfig.json") >= 0) {
        var demoConfigStr = fs.readFileSync("config/democonfig.json", "UTF-8");
        log("demoConfigStr " + demoConfigStr);
        var demoConfig = JSON.parse(demoConfigStr);
        for (var dcKey in demoConfig) {
            log(dcKey + " " + demoConfig[dcKey]);
        }
        return demoConfig;
    }
    if (arg == "express") {
        return express;
    }

    if (arg == "moment") {
        return moment;
    }



}


exports = {};

module = {
    exports: exports
};
