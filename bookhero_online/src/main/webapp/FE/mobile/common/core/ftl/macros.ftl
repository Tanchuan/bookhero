<#include "funcs.ftl">
<#import 'JSON.ftl' as JSON>

<#macro jsFile file=[]>
    <#list file as x><script src="${x}"></script></#list><#t>
</#macro>

<#macro cssFile file=[]>
    <#list file as x><link rel="stylesheet" href="${x}"/></#list><#t>
</#macro>

<#macro cutString str n=5 dot="..." ><#if (str!"")?length gt n>${str[0..(n-1)]}${dot}<#else>${str}</#if></#macro>

<#macro formatDateTime dateTime format="yyyy-MM-dd HH:mm:ss">
    <#local dateStr = fn_formatDateTime(dateTime,format)/>
${dateStr}<#t>
</#macro>

<#macro docHeadCore domain="" title="" keywords="" description="" css=[] js=[] vml=false xFrame="" xFrameJS="" bodyClass="" canonical="" agent=[] robots=false touchIcon="" webviewTitle="" meta={} metaList=[] bodyStyle="" favicon="/favicon.ico" viewportContent="" needMultiNested=false>
<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
<#-- 是否启用多插槽（该开关用于兼容旧代码），默认false -->
    <#if needMultiNested><#nested 'top'><#t></#if>

    <script>var _it=[new Date(), "${.now?long?c}", "<!--nginx-server-time-->"];</script>
    <link rel="shortcut icon" href="${favicon}"/>
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="${touchIcon}"/>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="application-name" content="${domain}"/>
    <meta name="viewport" content="<#if viewportContent?length gt 0>${viewportContent}<#else>width=device-width,target-densitydpi=high-dpi,initial-scale=1.0,maximum-scale=1.0,user-scalable=no,minimal-ui</#if>" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta content="telephone=no" name="format-detection" />
    <meta name="renderer" content="webkit"/>
    <meta name="keywords" content="${keywords}"/>
    <meta name="description" content="${description}"/>
    <#if meta?size gt 0>
        <#list meta?keys as list><meta name="${list}" content="${meta[list]}"/></#list>
    </#if>
    <#if metaList?size gt 0>
        <#list metaList as metaMap> <meta <#list metaMap?keys as k> ${k}="${metaMap[k]}"</#list> /> </#list>
    </#if>
    <#if xFrame?length gt 0><meta http-equiv="X-Frame-Options" content="${xFrame}"/></#if>
    <#if robots><meta name="robots" content="noindex"></#if><#t>
    <title><#if webViewName?? && webviewTitle?length gt 0>${webviewTitle}<#else>${title}</#if></title>
    <#list agent as list><#t>
        <meta name="mobile-agent" content="<#if !list?contains('format')>format=html5;url=${list}<#else>${list}</#if>">
    </#list><#t>
    <#if canonical != ""><link rel="canonical" href="${canonical}"/></#if>
    <@cssFile file=css/>
    <@jsFile file=js/>
    <#if xFrame == "DENY" && xFrameJS?length gt 0><@jsFile file=[xFrameJS] /></#if><#t>

    <#if !needMultiNested><#nested><#t></#if>

    <#if vml==true><style>v\:line,v\:rect,v\:oval,v\:group,v\:stroke,v\:fill,v\:polyline{behavior:url(#default#VML)}</style></#if>
    <#if needMultiNested><#nested 'bottom'><#t></#if>
</head>
<body class="<#if webViewName??>webView </#if>${bodyClass}"
      style="${bodyStyle}"
      id="${webViewName!'notInWebView'}"
      <#if osType??>data-os="${osType}"</#if>
      <#if netType??>data-net="${netType}"</#if>>
<#-- 网络类型判断 -->
<script>
        <#if !netType??>
        !function(){try{if(!window.navigator.connection)return;var n=window.navigator.connection,o="highbandwidth";-1!==["cellular","3","4"].indexOf(n.type)&&(o="lowbandwidth"),window.__net__=o,document.body.setAttribute("data-net",o)}catch(t){window.console&&console.error&&console.error(t)}}();
        <#else>
        window.__net__ = '${netType}';
        </#if>
</script>

<#-- No Script -->
<noscript><div id="noScript">
    <div><h2>请开启浏览器的Javascript功能</h2><p>亲，没它我们玩不转啊！求您了，开启Javascript吧！<br/>不知道怎么开启Javascript？那就请<a href="//www.baidu.com/s?wd=%E5%A6%82%E4%BD%95%E6%89%93%E5%BC%80Javascript%E5%8A%9F%E8%83%BD" rel="nofollow" target="_blank">猛击这里</a>！</p></div>
</div></noscript>
</#macro>

<#macro docFootCore js=[] delayJS=[] mockjaxLib=[] mockjax=[] >
<#--
    开启ajax假数据
    1.false 会由构建工具替换
    2.mockjaxLib默认加载2个常用库，均依赖于jquery
    3.__jqueryAddon 标记，由jquery库一起的脚本使用
    注意： 为了防止入口模块依赖假数据，此处依赖由ftl控制执行顺序（jquery库->mock库->mock假数据->其他）。
-->
    <#if false && mockjax?size gt 0>
        <#local mockJsFileList = mockjaxLib + mockjax />
    <#-- 当前ftl版本不支持join -->
        <#local mockStr = ""/>
        <#list mockJsFileList as mock> <#local mockStr = mockStr + ';' + mock /></#list>
    <script>window.__jqueryAddon='${mockStr}'.replace(';','').split(';');</script>
    </#if>
<div id="touchStrikeLayout" style="position: fixed;left:0;top:0;width:100%;height:100%;z-index:99999;background:#fff;opacity:0;"></div>
    <@jsFile file = js />
<script>
    window.Core&&Core._quick&&Core._quick();
    window.setTimeout(function(){var l=document.getElementById("touchStrikeLayout");l&&l.parentNode.removeChild(l)},500);
</script>
    <#nested>
    <@jsFile file=delayJS />
</body>
</html>
</#macro>

<#macro googleAnalyticsContentExperiment key="">
<#-- 由原始页启动实验室代码，分配变种页面。若存在版本号，则认为是变种，不应该执行实验代码 -->
<#-- 非测试环境，且有实验室键，且不存在版本号 -->
    <#if !false && key?? && key!="" && (!version?? || version == "")>
    <!-- Google Analytics Content Experiment code -->
    <script>function utmx_section(){}function utmx(){}(function(){var
            k='${key}',d=document,l=d.location,c=d.cookie;
        if(l.search.indexOf('utm_expid='+k)>0)return;
        function f(n){if(c){var i=c.indexOf(n+'=');if(i>-1){var j=c.
        indexOf(';',i);return escape(c.substring(i+n.length+1,j<0?c.
                length:j))}}}var x=f('__utmx'),xx=f('__utmxx'),h=l.hash;d.write(
                '<sc'+'ript src="'+'http'+(l.protocol=='https:'?'s://ssl':
                        '://www')+'.google-analytics.com/ga_exp.js?'+'utmxkey='+k+
                '&utmx='+(x?x:'')+'&utmxx='+(xx?xx:'')+'&utmxtime='+new Date().
                valueOf()+(h?'&utmxhash='+escape(h.substr(1)):'')+
                '" type="text/javascript" charset="utf-8"><\/sc'+'ript>')})();
    </script><script>utmx('url','A/B');</script>
    <!-- End of Google Analytics Content Experiment code -->
    </#if>
</#macro>

<#macro docHead title="" keywords="" description="" css=[] namespace="" vml=false xFrame="" canonical="" agent=[] robots=false touchIcon="" clean=false webviewTitle="" meta={} metaList=[] bodyStyle="" bodyClass="" viewportContent="" googleExperimentKey="" useWebCache = true showItunesApp=true>
    <#local cssFileList = RES_CSS_BASE_LIST />
    <#if !clean>
        <#local cssFileList = cssFileList+RES_CSS_CORE_LIST />
    </#if>
    <#if keywords="">
        <#local keywords = "keywords" />
    </#if>
    <#if description="">
        <#local description = "description" />
    </#if>
    <#if meta?size == 0 && showItunesApp>
        <#local meta = {"apple-itunes-app":"app-id=1040726468"} />
    </#if>
    <#if metaList?size == 0>
        <#local metaList = [{"property":"og:type", "content":"money"}, {"property":"og:title", "content":"立马理财"}, {"property":"og:url", "content":"https://www.lmlc.com/wap"}, {"property":"og:image", "content":"https://www.lmlc.com" + "//localhost:8080/cdn/mobile/common/core/img/oglogo.384b31b94e.png"}] />
    </#if>
    <@docHeadCore
    domain = sys_domain
    title = title
    keywords = keywords
    description = description
    css = cssFileList + css
    vml = vml
    xFrame = xFrame
    xFrameJS = ""
    bodyClass = bodyClass
    canonical = canonical
    agent = agent
    robots = robots
    touchIcon = touchIcon
    webviewTitle = webviewTitle
    meta = meta
    metaList = metaList
    favicon = RES_FAVICON
    viewportContent = viewportContent
    needMultiNested = true
    ; nestedPoc
    >
        <#if nestedPoc == 'top'>
            <@googleAnalyticsContentExperiment googleExperimentKey />
        <#elseif nestedPoc == 'bottom'>
        <#-- 私有代码插入head底部 -->
            <#nested><#t>
        </#if>
    </@docHeadCore>
<#-- 传递变量 -->
<script id="tempDBInfo">
    window.__sysInfo = {
        'appId': '${sys_appId}',
        'topDomain': '${sys_topDomain}',
        'domain': '${sys_domain}'
    }
    <#-- 同步sys,biz配置数据 -->
    window.__syncInfo = {
        sys: <@ftl2js sys />,
        biz: <@ftl2js biz />
    }

        <#if !useWebCache>
        /**
         * 不使用页面缓存
         * 国产浏览器大部分无用！
         * 安卓原生浏览器 QQ UC等无用（历史页面相当于被隐藏，回退后再显示！无解）
         * webview内得APP自己设置禁用缓存
         * 下面只是尝试解决页面缓存问题
         */
        // Safari有效
        window.addEventListener('pagehide', function(e) {
            document.body.style.display = 'none';
            // wait for this callback to finish executing and then...
            setTimeout(function() {
                window.location.reload(true);
                // $body.append("<script type='text/javascript'>window.location.reload();<\/script>");
            });
        });
        // 部分安卓浏览器有效
        window.addEventListener('pageshow', function(e) {
            if (e.persisted) {
                window.location.reload(true);
            }
        });
        // 最后的方案：缓存时间标记
        try{
            var _useWebCacheKey = sessionStorage.getItem('_useWebCacheKey');
            sessionStorage.setItem('_useWebCacheKey', '${.now?long}');
            if(_useWebCacheKey === sessionStorage.getItem('_useWebCacheKey')){
                window.location.reload(true);
            }
        }catch(e){}
        </#if>
    document.getElementById('tempDBInfo').parentNode.removeChild(document.getElementById('tempDBInfo'));
</script>
</#macro>

<#macro docFoot js=[] mockjax=[] delayJS=[] useFtlData=false clean=false >
    <#local jsFileList = RES_JS_BASE_LIST />
    <#if !clean>
        <#local jsFileList = jsFileList + RES_JS_CORE_LIST />
    </#if>
    <#if RES_MOCK_BASE_LIST?size gt 0 >
        <#local mockjax = RES_MOCK_BASE_LIST + mockjax />
    </#if>
<#-- 测试后台数据代码 -->
    <#if debugMode >
    <script>
            window.__ftlDataStr =  "${JSON.stringify(data)?js_string}";
            window.__ftlData =  ${JSON.stringify(data)};
            window.__ftl = {
                data: window.__ftlData,
                dataStr : window.__ftlDataStr,
                <#if isLogin>
                    isLogin : true,
                <#else>
                    warn: '特别注意：isLogin为true代表登录成功，如果为空当前账号也存在登录的可能（后端没传该字段），可用Core.user.getInfo做判断',
                </#if>
                retcode : ${retcode!"-9999"},
                retdesc : "${retdesc!"后端同学没传描述"}"
            }
            console.log(window.__ftl);
        </script>
    </#if>
    <#if useFtlData>
    <script>
        try{
            window.__data =  ${JSON.stringify(data)};
            <#if isLogin> window.__isLogin = true; </#if>
            <#if retcode??> window.__retcode = "${retcode}"; </#if>
        }catch(e){
            console && console.error('FTL数据data解析失败！');
        }
    </script>
    </#if>
    <@docFootCore
    js = jsFileList + js
    delayJS = delayJS
    mockjaxLib = ["//localhost:8080/cdn/.remote/mockjax/jquery-mockjax/jquery.mockjax.fa3007ae2b.js", "//localhost:8080/cdn/.remote/mockjax/mock/mock.79e6cf8f0b.js"]
    mockjax = mockjax
    >
        <#nested><#t>
    </@docFootCore>
</#macro>

<#macro pageHead showLoading=false loadingType=0>
<div id="main" class="f-cb" >
</#macro>

<#macro pageFoot>
</div>
</#macro>