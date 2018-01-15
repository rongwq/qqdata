<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false" %>
  <%@ include file="/views/common/meta.jsp"%>
<div class="tpl-portlet-components">
	<div class="portlet-title">
		<div class="caption font-green bold">
			<div class="am-btn-toolbar">
				<div class="am-btn-group am-btn-group-xs">
					<shiro:hasPermission name="config-create">
						<button type="button" id="addBtn"
							onclick="javascript:loadBack('<%=basePath%>/views/appv/appAdd.jsp')"
							class="am-btn am-btn-default">
							<span class="am-icon-plus"></span> 新增
						</button>
					</shiro:hasPermission>
				</div>
			</div>
		</div>
	</div>

	<div class="tpl-block">
        <div class="am-g">
        	<div class="am-u-sm-12 am-scrollable-horizontal">
            	<form class="am-form am-form-inline" id="queryForm" role="form" action="<%=basePath %>/appv/appList">
            		<input type="hidden" id="page" name="page" value="${page.pageNumber}">
                	<table class="am-table am-table-striped am-table-hover table-main am-text-nowrap">
		    			<thead>
		        			<tr>
		        				<th>APP名称</th>
		        				<th>CODE</th>
		        				<th>创建时间</th>
		        				<th>更新时间</th>
		        				<th>备注</th>
		        				<th>操作</th>
		        			</tr>
		    			</thead>
		    			<tbody>
		    				<c:forEach items="${page.list}" var="item">
			        			<tr>
			            			<td>${item.appName }</td>
			            			<td>${item.appCode }</td>
			            			<td><fmt:formatDate value="${item.createTime }" pattern="yyyy-MM-dd HH:mm"/></td>
			            			<td><fmt:formatDate value="${item.updateTime }" pattern="yyyy-MM-dd HH:mm"/></td>
			            			<td>${item.remark }</td>
			            			<td>
			              				<div class="am-btn-toolbar">
                    						<div class="am-btn-group am-btn-group-xs">
                    							<shiro:hasPermission name="user-update">  
													<button type="button" onclick="javascript:loadRight('<%=basePath %>/appv/versionList?appId=${item.id}')" class="am-btn am-btn-default am-btn-xs am-text-secondary"><span class="am-icon-pencil-square-o"></span>版本列表</button>
												</shiro:hasPermission>
			            						<shiro:hasPermission name="user-update">  
													<button type="button" onclick="javascript:loadRight('<%=basePath %>/appv/toAppUpdate?id=${item.id}')" class="am-btn am-btn-default am-btn-xs am-text-secondary"><span class="am-icon-pencil-square-o"></span>修改</button>
												</shiro:hasPermission>
			            						<shiro:hasPermission name="user-update">  
													<button type="button" data-id="${item.id}" name="delBtn" class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only"><span class="am-icon-trash-o"></span>删除</button>
												</shiro:hasPermission>
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
							<input type="hidden" id="pages" name="pages" value="${page.totalPage}">        
                        </div>
                    </div>
                    <hr>
          		</form>
          	</div>
      	</div>
  	</div>
</div>

<script src="<%=basePath %>/js/app.js"></script>