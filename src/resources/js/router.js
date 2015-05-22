var RouterManager = Java.type('com.adobe.demo.mcdemo.RouterManager');

var Router = function (path) {
    RouterManager.registerRouter(path, this);
    this.prefixPath = path;
};


Router.prototype.handlers = "";

Router.prototype.matchRoute = function (testKey, routeKey) {
    if (testKey === routeKey) {
        return true;
    }
    var res = false;
    var match;
    if (routeKey.indexOf("*") >= 0) {
        var regExp = routeKey.replace("*", "(.)*?");
        match = (testKey.match(regExp));
        res = ((match != undefined) && (match != null))
    }
//    log(testKey + " " + routeKey + "(" + regExp + ") =  " + match)
    return res;
}

Router.prototype.processCall = function (method, path, request, response) {
    log(this.prefixPath);
    var relPath = path.substr(this.prefixPath.length, path.length);
    relPath = relPath.trim();
    if (relPath == '')
        relPath = '/';
    var key = method.toLowerCase() + ":" + relPath;

    log("Process Call " + key + " rel path '" + relPath + "'");
    var handlerFunc = null;
    for (var handlerKey in this.handlers) {

        if (this.matchRoute(key, handlerKey)) {
            handlerFunc = this.handlers[handlerKey];
            break;
        }
    }
    if (!handlerFunc) {
        var key = "all:" + relPath;
        for (var handlerKey in this.handlers) {
            if (this.matchRoute(key, handlerKey)) {
                handlerFunc = this.handlers[handlerKey];
                break;
            }
        }
    }
    if (handlerFunc) {

        log("FOUND HANDLER!");
        var responseWrapper = new ResponseWrapper(response);
        var requestWrapper = new RequestWrapper(request);
        handlerFunc(requestWrapper, responseWrapper, null);
        return true;

    } else {
        log("NO HANDLER :(");
    }
    return false;
};


Router.prototype.handlers = {};

Router.prototype.registerHandler = function (key, handler) {
    log("Registering handler " + key);
    this.handlers[key] = handler;
};

Router.prototype.all = function (path, handler) {
    var key = "all:" + path;
    this.registerHandler(key, handler);
};

Router.prototype.get = function (path, handler) {
    var key = "get:" + path;
    this.registerHandler(key, handler);
};

Router.prototype.post = function (path, handler) {
    var key = "post:" + path;
    this.registerHandler(key, handler);
};

Router.prototype.put = function (path, handler) {
    var key = "put:" + path;
    this.registerHandler(key, handler);
};

Router.prototype.patch = function (path, handler) {
    var key = "patch:" + path;
    this.registerHandler(key, handler);
};


Router.prototype.delete = function (path, handler) {
    var key = "delete:" + path;
    this.registerHandler(key, handler);
};


ResponseWrapper = function (response) {
    this.response = response;
    this.props = {};
};

ResponseWrapper.prototype.set = function (key, val) {
    this.props[key] = val;
}

ResponseWrapper.prototype.send = function (content) {
    var contentType = this.props['Content-Type'];
    if (typeof content != 'string') {
        content = JSON.stringify(content);
    }
    content = content.replace('\\"')
    this.response.sendResponse(contentType, content);

}

ResponseWrapper.prototype.templateCache = {};

ResponseWrapper.prototype.render = function (templatePath, data) {
    var template = this.templateCache[templatePath];
    if (!template) {
        var templateStr = this.response.getTemplateStr(templatePath);
        var template = ejs.compile(templateStr, {});
        this.templateCache[templatePath] = template;
    }
    var html = template(data);
    this.response.sendResponse(null, html);
};


var RequestWrapper = function (request) {
    this.session = {};
    this.query = {};
    if (request.query) {
        var vars = request.query.split('&');
        for (var i = 0; i < vars.length; i++) {
            var pair = vars[i].split('=');
            var key = decodeURIComponent(pair[0]);
            var val = decodeURIComponent(pair[1]);
            this.query[key] = val;
        }
    }
    if (!this.query.locale) {
        this.query.locale = "en_US";
    }


}