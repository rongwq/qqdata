<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="/views/common/meta.jsp"%>

<div class="tpl-portlet-components">
	<form class="am-form am-form-horizontal" id="queryForm" role="form" action="<%=basePath %>/ip/list">
		<input type="hidden" id="page" name="page" value="${page.pageNumber}">
		<div class="am-g tpl-amazeui-form">
            <div class="am-u-lg-6 am-u-end">
                <button class="am-btn am-btn-secondary am-radius" type="button" onclick="clean()">一键清空</button>
            </div>
        </div>
	</form>
	<div class="tpl-block">
		<div class="am-g">
			<div class="am-u-sm-12 am-scrollable-horizontal">
				<table
					class="am-table am-table-striped am-table-hover table-main am-text-nowrap">
					<thead>
						<tr>
							<th>编号</th>
							<th>日期</th>
							<th>IP</th>
							<th>IP归属地</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="item">
							<tr>
								<td>${item.id }</td>
								<td><fmt:formatDate value="${item.createTime }" pattern="yyyy-MM-dd HH:mm" /></td>
                                <td>${item.ip }</td>
                                <td>${item.address }</td>
								<td>
									<div class="am-btn-toolbar">
										<div class="am-btn-group am-btn-group-xs">
											<button type="button" name="delBtn" data-id="${item.id }"
												class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only">删除</button>
										</div>
									</div>
								</td>
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
				<hr>


				<div class="am-modal am-modal-confirm" tabindex="-1" id="my-confirm">
					<div class="am-modal-dialog">
						<div class="am-modal-hd">温馨提示：</div>
						<div class="am-modal-bd">你，确定要执行此次操作吗？</div>
						<div class="am-modal-footer">
							<span class="am-modal-btn" data-am-modal-cancel>取消</span> <span
								class="am-modal-btn" data-am-modal-confirm>确定</span>
						</div>
					</div>
				</div>
				
			</div>
		</div>
	</div>
</div>

<script src="<%=basePath %>/js/ip.js"></script>