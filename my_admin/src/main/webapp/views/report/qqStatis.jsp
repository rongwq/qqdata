<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false" %>
  <%@ include file="/views/common/meta.jsp"%>
  
<div class="tpl-portlet-components">
    <form class="am-form am-form-horizontal form-border" id="queryForm" role="form" action="<%=basePath %>/report/qqStatis">
        <input type="hidden" id="page" name="page" value="${page.pageNumber}">
        <div class="am-g tpl-amazeui-form">
            <div class="am-u-lg-2  am-g-collapse">
                 <input type="text" class="am-form-field" placeholder="开始时间" name="time" value="${time}" id="datetimeStart">
            </div>
            <div class="am-u-lg-2 am-u-end">
                <button class="am-btn am-btn-secondary am-radius" type="button" onclick="doMyQuery();"> 搜索</button>
            </div>
        </div>  
    </form> 
    <div class="tpl-block">
        <div class="am-g">
            <div class="am-u-sm-12 am-scrollable-horizontal">
                <table class="am-table am-table-striped am-table-hover table-main am-text-nowrap">
                    <thead>
                        <tr>
                            <th>日期</th>
                            <th>编组</th>
                            <th>QQ数量</th>
                            <th>存活率</th>
                            <th>活号成本</th>
                            <th>白号</th>
                            <th>三问号</th>
                            <th>绑机号</th>
                            <th>令牌号</th>
                            <th>冻结号</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${page.list}" var="item">
                            <tr>
                                <td><fmt:formatDate value="${item.createTime }" pattern="yyyy-MM-dd"/></td>
                                <td>${item.teamName}</td>
                                <td>${item.qqCount }</td>
                                <td>
                                    <c:if test="${item.qqCount == 0 or empty item.qqCount}">0</c:if>
                                    <c:if test="${item.qqCount != 0 and not empty item.qqCount}">
                                        <fmt:formatNumber value="${item.qq_count_lived*100/item.qqCount}" pattern="#0.00"/>
                                    </c:if>
                                    %
                                <td>
                                    <c:if test="${item.qqCountLived == 0 or empty item.qqCountLived}">0</c:if>
                                    <c:if test="${item.qqCountLived != 0 and not empty item.qqCountLived}">
                                        <fmt:formatNumber value="${item.costPrice*item.qqCount/item.qqCountLived}" pattern="#0.00"/>
                                    </c:if>
                                </td>
                                <td>${item.qqCountType1 eq null?0:item.qqCountType1 }</td>
                                <td>${item.qqCountType2 eq null?0:item.qqCountType2 }</td>
                                <td>${item.qqCountType3 eq null?0:item.qqCountType3 }</td>
                                <td>${item.qqCountType4 eq null?0:item.qqCountType4 }</td>
                                <td>${item.qqState0 eq null?0:item.qqState0 }</td>
                            </tr>           
                        </c:forEach>
                    </tbody>
                </table>
                <div class="am-cf">
                    <div class="am-fr">
                        <!-- 分页使用 -->
                        <div id="pageDiv"></div>
                        <input type="hidden" id="pages" name="pages" value="${page.totalPage}">        
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>



<script>
$(function() {
    initQueryForm();
    initPage();
});

function doMyQuery() {
    var startD= $("#datetimeStart").val();
    if(!startD){
        alert("温馨提示：请选择时间进行查询！")  
        return false;
    }
    doQuery();
}

//日期时间选择器（开始、结束）
$("#datetimeStart").datepicker({
    language: 'zh-CN', 
    format: "yyyy-mm-dd",
    autoclose: true,
    maxView: "decade",
    todayBtn: true,
    pickerPosition: "bottom-left"
})
</script>