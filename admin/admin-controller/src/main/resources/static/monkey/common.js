(function () {
    // 第一步判断是否加载jquery
    if (typeof jQuery === "undefined") {
        console.log("当前没有加载jQuery")
        loadJS("https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js", runFn);
    } else {
        console.log("当前已加载jQuery")
        runFn()
    }


})()

/**
 * 执行
 */
function runFn() {
    setTimeout(function () {
        var url = window.location.href;
        console.log("当前url", url)
        // 判断url执行要加载哪个js文件
        if (url === "https://www.xiaohongshu.com/explore") {
            loadJS("http://localhost:8080/monkey/xiaohongshu/index.js");
        }
    }, 1000)
}


/**
 * 加载js
 */
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
        script.onload = function () {
            fn();
        };
    }
    script.src = url;
    document.getElementsByTagName('head')[0].appendChild(script);
}







