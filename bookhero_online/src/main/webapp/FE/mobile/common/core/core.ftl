<#compress>
    <#include "ftl/macros.ftl">
    <#setting locale="zh_CN">
    <#setting url_escaping_charset="UTF-8">
    <#assign isLogin = isLogin!(currentLoginUserName!"" != "")/>
    <#assign appid = appid!""/>
    <#assign request = request!requestReferenceForFreemarker!(Request["org.springframework.web.context.request.RequestContextListener.REQUEST_ATTRIBUTES"].request)/>

    <@compress ingle_line = true>
        <#macro ftl2js ftldata='数据为空' keepKeyList=[]>
            <#global keepKeys = (keepKeyList?is_sequence)?then(keepKeyList,keepKeyList?replace(" ","")?split(","))/>
            <#global haskeepKeys = keepKeys?size gt 0/>
            <#if ftldata?is_hash>
            <#-- ftl数据是hash -->
                <@getHash hash=ftldata/><#t>
            <#elseif ftldata?is_sequence>
            <#-- ftl数据是sequence -->
                <@getSequence sequence=ftldata/><#t>
            <#elseif ftldata?is_string>
            <#-- ftl数据是string型 -->
            '${ftldata?replace("\'","\"")}'<#t>
            <#else>
            <#-- ftl数据 -->
            ${(ftldata!'数据为空')?c}<#t>
            </#if>
        </#macro>
    <#-- 获取数组 -->
        <#macro getSequence sequence>
            <#list sequence>
            [<#t>
                <#items as list>
                    <@ftl2js ftldata = list!'数据为空' keepKeyList = .globals.keepKeys/><#sep>,<#t>
                </#items>
            ]<#t>
            <#else>
            []<#t>
            </#list>
        </#macro>
    <#-- 获取哈希 -->
        <#macro getHash hash>
            <#list .globals.keepKeys>
            <#-- 输出对应自定义关键字的值 -->
            {<#t>
                <#items as key>
                '${key}':<@ftl2js ftldata = hash[key]!'数据为空' /><#sep>,<#t>
                </#items>
            }<#t>
            <#else>
            <#-- 输出所有的值 -->
                <#list hash?keys><#t>
                {<#t>
                    <#items as key>
                    '${key}':<@ftl2js ftldata = hash[key]!'数据为空'/><#sep>,<#t>
                    </#items>
                }<#t>
                <#else>
                {}<#t>
                </#list>
            </#list>
        </#macro>

    <#-- 把mcaro封装成方法 -->
        <#function Ftl2JsStr ftldata='数据为空' keepKeyList=[]>
            <#local jsString>
                <@ftl2js ftldata=ftldata keepKeyList=keepKeyList/>
            </#local>
            <#return jsString>
        </#function>

    <#-- 返回js对象 -->
        <#function ftl2js_Obj ftldata='数据为空' keepKeyList=[]>
            <#return Ftl2JsStr(ftldata,keepKeyList)>
        </#function>

    <#-- 返回js字符串 -->
        <#function ftl2js_Str ftldata='数据为空' keepKeyList=[]>
            <#local str = Ftl2JsStr(ftldata,keepKeyList)>
        <#-- 但返回值已经是string型，不再加“"” -->
            <#return str?ends_with("'")?string(str,'"'+str+'"')>
        </#function>

    </@compress>
<#--
判断内嵌客户端类型
-->
    <#assign userAgent = request.getHeader("User-Agent")!'' +" "+ request.getHeader("X-Requested-With")!'' />
    <#assign useragent = userAgent?lower_case />
    <#if useragent?index_of("yixin") gt 0>
        <#assign webViewName = "yixin"/>
    <#elseif useragent?index_of("newsapp") gt 0>
        <#assign webViewName = "newsapp"/>
    <#elseif useragent?index_of("micromessenger") gt 0>
        <#assign webViewName = "weixin"/>
    </#if>
<#--
判断系统类型
-->
    <#assign osType = "other"/>
    <#if useragent?index_of("android") gt 0 || useragent?index_of("adr ") gt 0>
        <#assign osType = "android"/>
    <#elseif useragent?index_of("iphone") gt 0 || useragent?index_of("ipad") gt 0 || useragent?index_of("ipod") gt 0>
        <#assign osType = "ios"/>
    </#if>
<#--
判断网络环境
-->
    <#if getNetType()??>
        <#assign netType = getNetType() />
    </#if>
<#-- 系统相关 -->
<#-- 产品id -->
    <#assign sys_appId = "lmlc"/>
<#-- 顶级域名 -->
    <#assign sys_topDomain = "lmlc.com"/>
<#-- 当前主站域名 -->
    <#assign sys_domain = "www.lmlc.com"/>


<#-- 系统命名空间 *由各项目自己维护* -->
    <#assign sys = {} />
<#-- 业务命名空间 *由各项目自己维护*-->
    <#assign biz = {} />


<#-- 通过参数开启ftl测试模式 -->
    <#assign debugMode = false />
    <#if getPara("debugMode")=="ftl" >
        <#assign debugMode = true />
    </#if>
<#-- 通用的版本号参数(优先取后台的版本分配) -->
    <#if !version?? || version == "">
        <#assign version = getPara('version')!''  />
    </#if>
<#-- 通用的来源参数 -->
    <#assign from = getPara('from')!''  />
    <#if from=="">
        <#assign from = getPara('utm_source')!'' />
    </#if>
    <#assign RES_CSS_BASE_LIST = ['//localhost:8080/cdn/mobile/common/base/base.56894b1288.css']/>
    <#assign RES_CSS_CORE_LIST = ['//localhost:8080/cdn/mobile/common/core/core.d41d8cd98f.css']/>
    <#assign RES_JS_BASE_LIST = ['//localhost:8080/cdn/mobile/common/base/base.656d97494a.js']/>
    <#assign RES_JS_CORE_LIST = ['//localhost:8080/cdn/mobile/common/core/core.7895c5ae59.js']/>
    <#assign RES_MOCK_BASE_LIST = ['//localhost:8080/cdn/mobile/common/mock/base.ajax.d41d8cd98f.js']/>

    <#assign sys = {
    "urlMap": {
        "home" : "/wap"
        }
    } />

</#compress>