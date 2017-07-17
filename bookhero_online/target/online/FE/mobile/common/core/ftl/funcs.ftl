<#function replaceXSS str="">
    <#return str?replace("<","")?replace(">","") />
</#function>

<#function getPara para defaultPara="">
    <#local val = ((Parameters!RequestParameters!{})[para])!(defaultPara!"") />
    <#if val == "">
        <#local val = defaultPara!"" />
    </#if>
    <#return val />
</#function>

<#function getSafePara para defaultPara="">
    <#local val = replaceXSS(getPara(para, defaultPara))?replace("'","")?replace('"',"")/>
    <#return val />
</#function>

<#function getCookie name=''>
    <#local cookies = []/>
    <#if request??>
        <#local cookies = request.getCookies()![] />
    </#if>
    <#local value = '' />
    <#list cookies as cookie>
        <#if cookie.name == name>
            <#local value = cookie.value />
            <#break/>
        </#if>
    </#list>
    <#return value />
</#function>

<#function isSafeUrl url="">
    <#if url?length lte 0>
        <#return false/>
    </#if>
    <#local str = url?lower_case />
<#-- 仅仅支持返回http类型的url -->
    <#if str?matches("^\\w+://.+") && !str?matches("^https*://.+")>
        <#return false/>
    </#if>
<#-- 仅仅支持返回网易163.com域名的url -->
<#--支持不带结尾斜线 原: "^http://[\\w\\.]+?\\.163\\.com\\/.*"-->
    <#if str?matches("^https*://.+") && !str?matches("^https*:\\/\\/[\\w\\.]+?\\.163\\.com(?:$|\\/.*)")>
        <#return false/>
    </#if>
<#-- 检查路径中是否包含XSS风险的代码 -->
    <#local checkUrl = replaceXSS(url)/>
    <#if checkUrl != url>
        <#return false/>
    </#if>
<#-- 相对路径和163.com的域名一律通过 -->
    <#return true/>
</#function>

<#function fn_formatDateTime dateTime="" format="yyyy-MM-dd HH:mm:ss">
    <#if dateTime?is_date>
        <#return dateTime?string(format)>
    </#if>
    <#if dateTime?is_number>
        <#return dateTime?number_to_datetime?string(format)>
    </#if>
    <#return ''>
</#function>

<#function getNetType>
<#-- 匹配腾讯系或支持NetType的终端 -->
    <#local netTypeRes = useragent?matches(r".*(?:nettype|network)/([^/ ]+).*")>
    <#if !netTypeRes>
    <#-- 匹配电商系 -->
        <#local netTypeRes = useragent?matches(r".*[^/ ]+/[^/ ]+/[\d\.]+/\d+/([012]).*")>
    </#if>

    <#if netTypeRes>
        <#local netType = "highbandwidth" />
        <#if netTypeRes?groups[1] = "1" || netTypeRes?groups[1] = "mobile" || netTypeRes?groups[1] = "2g" || netTypeRes?groups[1] = "3g+">
            <#local netType = "lowbandwidth" />
        </#if>
    </#if>
    <#return netType>
</#function>