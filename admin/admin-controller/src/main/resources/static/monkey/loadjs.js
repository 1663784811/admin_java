// ==UserScript==
// @name         小红书数据采集
// @namespace    http://tampermonkey.net/
// @version      0.1.2
// @description  小红书数据采集
// @author       You
// @match        https://www.xiaohongshu.com/explore
// @icon         data:image/gif;base64,R0lGODlhAQABAAAAACH5BAEKAAEALAAAAAABAAEAAAICTAEAOw==
// @grant        none
// ==/UserScript==


(function () {
    loadJS('http://localhost:8080/monkey/common.js', function () {
        console.log("common.js 文件加载完成");
    });
})();


function loadJS(url, callback) {
    var script = document.createElement('script'), fn = callback || function () {
    };
    script.type = 'text/javascript';
    if (script.readyState) {
        script.onreadystatechange = function () {
            if (script.readyState == 'loaded'
                || script.readyState == 'complete') {
                script.onreadystatechange = null;
                fn();
            }
        };
    } else {
        // 其他浏览器
        script.onload = function () {
            fn();
        };
    }
    script.src = url;
    document.getElementsByTagName('head')[0].appendChild(script);
}