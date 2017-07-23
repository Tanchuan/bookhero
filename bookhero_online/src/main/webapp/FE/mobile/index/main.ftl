<#-- <#mock "mock.ftl.js"> -->
<#include "../common/core/core.ftl">
<@docHead
title = "月内活动日历"
css=["//localhost:8080/cdn/mobile/index/main.09bbd61013.css"]
/>
<#-- livereload -->
<@pageHead/>
<link href="https://cdn.bootcss.com/material-design-icons/3.0.1/iconfont/material-icons.min.css" rel="stylesheet">
<#-- 私有代码 -->
<#list data.weeks as week>
<div class="m-week">
    <div class="neo-category" data-path="3">
        <div>
            <div class="neo-category-content">
                <#if  week.order == 0>
                ${"本周"}
                <#elseif week.order == 1>
                ${"下周"}
                <#elseif week.order == 2>
                ${"下下周"}
                <#else>
                ${"＋" + week.order + "周"}
                </#if>
            </div>
        </div>
    </div>

    <#list week.days as day>
        <#if fn_formatDateTime(day.startTime, "yyyy-MM-dd") == fn_formatDateTime(.now, "yyyy-MM-dd")>
        <div class="m-day today">
        <#else>
        <div class="m-day">
        </#if>
        <div class="date">
        ${fn_formatDateTime(day.startTime, "E，dd号")}
        </div>
        <#list day.events as event>
            <a href="${event.eventUrl}">
                <div class="event">
                    <div class="startTime">
                    ${fn_formatDateTime(event.startTime, "ah:mm")}
                    </div>
                    <div class="endTime">
                    ${(event.endTime?long - event.startTime?long)/3600000 + '小时'}
                    </div>
                    <div class="divider"></div>
                    <div class="topic">
                    ${event.title}
                    </div>
                    <div class="place">
                        <i class="material-icons">place</i>${event.address}
                    </div>
                </div>
            </a>
        </#list>
    </div>
    </#list>
</div>

</#list>






<@pageFoot />
<@docFoot js=["//localhost:8080/cdn/mobile/index/main.b9dc976e9d.js"] mockjax=["//localhost:8080/cdn/mobile/index/mock.ajax.fa407b9b84.js"] />
