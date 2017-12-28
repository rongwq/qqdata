<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="/views/common/meta.jsp"%>

<div class="tpl-portlet-components">
	<form class="am-form am-form-horizontal" id="queryForm" role="form" action="<%=basePath %>/qqTeam/list">
		<input type="hidden" id="page" name="pageNumber" value="${page.pageNumber}">
	</form>
	<div class="tpl-block">
		<div class="am-g">
			<div class="am-u-sm-12 am-scrollable-horizontal">
				<table
					class="am-table am-table-striped am-table-hover table-main am-text-nowrap">
					<thead>
						<tr>
							<th>编号</th>
							<th>编组名称</th>
							<th>QQ数量</th>
							<th>存活量</th>
							<th>活号成本</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="item">
							<tr>
								<td>${item.id }</td>
								<td>${item.teamName }</td>
                                <td>${item.qqCount }</td>
                                <td>${item.qqCountLived }</td>
								<td><fmt:formatNumber value="${item.costPrice*item.qqCount/item.qqCountLived}" pattern="#0.00"/></td>
								<td>
									<div class="am-btn-toolbar">
										<div class="am-btn-group am-btn-group-xs">
											<button type="button" onclick="toUpdate( ${item.id })"
												class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only">修改编组名称</button>
											<button type="button" onclick="deleteTeam(${item.id })"
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

<script src="<%=basePath %>/js/qqTeam.js"></script>