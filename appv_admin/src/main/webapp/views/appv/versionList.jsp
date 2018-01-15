<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false" %>
  <%@ include file="/views/common/meta.jsp"%>
<div class="tpl-portlet-components">
	<div class="portlet-title">
		<div class="caption font-green bold">
			<div class="am-btn-toolbar">
				<div class="am-btn-group am-btn-group-xs">
					<button type="button" id="addBtn"
							onclick="javascript:loadBack('<%=basePath%>/appv/appList')"
							class="am-btn am-btn-default">
							<span class="am-icon-backward"></span> 返回
					</button>
					<shiro:hasPermission name="config-create">
						<button type="button" id="addBtn"
							onclick="javascript:loadBack('<%=basePath%>/views/appv/versionAdd.jsp?appId=${app.id}&appName=${app.appName}&appCode=${app.appCode}')"
							class="am-btn am-text-secondary">
							<span class="am-icon-plus"></span> 新增版本
						</button>
					</shiro:hasPermission>
				</div>
			</div>
		</div>
	</div>
	
    <div class="tpl-block">
        <div class="am-g">
        	<div class="am-u-sm-12 am-scrollable-horizontal">
            	<form class="am-form am-form-inline" id="queryForm" role="form" action="<%=basePath %>/appv/versionList">
            		<input type="hidden" id="page" name="page" value="${page.pageNumber}">
            		<input type="hidden" name="appId" value="${app.id}">
            	</form>
                	<table class="am-table am-table-striped am-table-hover table-main am-text-nowrap">
		    			<thead>
		        			<tr>
		        				<th>APP名称</th>
		        				<th>版本名称</th>
		        				<th>版本号</th>
		        				<th>文件大小</th>
		        				<th>系统类型</th>
		        				<th>是否公布</th>
		        				<th>自动下载</th>
		        				<th>创建时间</th>
		        				<th>备注</th>
		        				<th>操作</th>
		        			</tr>
		    			</thead>
		    			<tbody>
		    				<c:forEach items="${page.list}" var="item">
			        			<tr>
			            			<td>${item.appName }</td>
			            			<td>${item.versionName }</td>
			            			<td>${item.appVersion }</td>
			            			<td>
			            			<c:if test="${item.fileSize < 1024 }">${item.fileSize }B</c:if>
			            			<c:if test="${item.fileSize >= 1024 and item.fileSize< 1024*1024 }">
			            				<fmt:formatNumber type="number" maxFractionDigits="2" value="${item.fileSize/1024 }" /> KB
			            			</c:if>
			            			<c:if test="${item.fileSize >= 1024*1024 and item.fileSize< 1024*1024*1024 }">
			            				<fmt:formatNumber type="number" maxFractionDigits="2" value="${item.fileSize/1024/1024 }" /> MB
			            			</c:if>
			            			</td>
			            			<td>
			            				<c:if test="${item.systemType==1 }">Android</c:if>
			            				<c:if test="${item.systemType==2 }">iOS</c:if>
			            			</td>
			            			<td>
			            				<c:if test="${item.isPublish}">公布</c:if>
			            				<c:if test="${!item.isPublish }">未公布</c:if>
			            			</td>
			            			<td>
			            				<c:if test="${item.autoDownload}">自动下载</c:if>
			            				<c:if test="${!item.autoDownload }">否</c:if>
			            			</td>
			            			<td><fmt:formatDate value="${item.createTime }" pattern="yyyy-MM-dd HH:mm"/></td>
			            			<td>${item.remark }</td>
			            			<td>
			              				<div class="am-btn-toolbar">
                    						<div class="am-btn-group am-btn-group-xs">
			            						<shiro:hasPermission name="user-update">  
													<button type="button" onclick="javascript:loadRight('<%=basePath %>/appv/toVersionUpdate?id=${item.id}')" class="am-btn am-btn-default am-btn-xs am-text-secondary"><span class="am-icon-pencil-square-o"></span>修改</button>
												</shiro:hasPermission>
												<shiro:hasPermission name="user-update"> 
													<c:if test="${item.isPublish }">
														<button type="button" data-id="${item.id}" name="disableBtn" class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only"><span class="am-icon-trash-o"></span>禁用</button> 
													</c:if>
													<c:if test="${!item.isPublish }">
														<button type="button" data-id="${item.id}" name="enableBtn" class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only"><span class="am-icon-trash-o"></span>发布</button> 
													</c:if>
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
          	</div>
      	</div>
  	</div>
</div>

<script src="<%=basePath %>/js/version.js"></script>