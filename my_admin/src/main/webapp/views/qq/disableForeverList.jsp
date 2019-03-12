<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="/views/common/meta.jsp"%>

<div class="tpl-portlet-components">
	<form class="am-form am-form-horizontal" id="queryForm" role="form" action="<%=basePath %>/qq/disableForeverList">
		<input type="hidden" id="page" name="page" value="${page.pageNumber}">
		<input type="hidden" id="state" name="state" value="2">
		<div class="am-g tpl-amazeui-form">
			<div class="am-u-lg-3">
				<label for="qq" class="am-u-sm-4 am-form-label">QQ：</label>
				<div class="am-input-group">
					<input type="text" class="am-form-field" name="qq" value="${qq}" placeholder="请输入QQ号码">
				</div>
			</div>

			<div class="am-u-lg-3 am-u-end">
				<label for="qqType" class="am-u-sm-4 am-form-label">分类：</label> 
				<div class="am-input-group am-u-sm-8">
					<select id="qqType" name="qqType" class="inline-block">
						<option value="">-请选择-</option>
						<option value="1" <c:if test="${qqType == 1 }">selected</c:if>>白号</option>
						<option value="2" <c:if test="${qqType == 2 }">selected</c:if>>三问号</option>
						<option value="3" <c:if test="${qqType == 3 }">selected</c:if>>绑机号</option>
						<option value="4" <c:if test="${qqType == 4 }">selected</c:if>>令牌号</option>
					</select>
				 </div>
			</div>
			
			<div class="am-u-lg-3 am-u-end">
                <label for="storageState" class="am-u-sm-4 am-form-label">仓库状态：</label>
                <div class="am-input-group am-u-sm-8"> 
	                <select id="storageState" name="storageState" class="inline-block">
	                    <option value="">-请选择-</option>
	                    <option value="1" <c:if test="${storageState == 1 }">selected</c:if>>未出仓</option>
	                    <option value="2" <c:if test="${storageState == 2 }">selected</c:if>>已出仓</option>
	                </select>
                </div>
            </div>
            
		</div>
		
		<!-- 下一行 -->
		<div class="am-g tpl-amazeui-form">
            <div class="am-u-lg-3">
                <label for="teamName" class="am-u-sm-4 am-form-label">编组：</label>
                <div class="am-input-group">
                    <input type="text" class="am-form-field" name="teamName" value="${teamName}" placeholder="请输入编组名称">
                </div>
            </div>

            <div class="am-u-lg-3 am-u-end">
                <label for="isHaveTag" class="am-u-sm-4 am-form-label">标签：</label> 
                <div class="am-input-group am-u-sm-4"> 
	                <select id="isHaveTag" name="isHaveTag" class="inline-block">
	                    <option value="">-请选择-</option>
	                    <option value="1" <c:if test="${isHaveTag == 1 }">selected</c:if>>包含</option>
	                    <option value="2" <c:if test="${isHaveTag == 2 }">selected</c:if>>不包含</option>
	                </select>
                </div>
                <div class="am-input-group">
                    <input type="text" class="am-form-field" name="tags" value="${tags}" placeholder="多个标签顿号隔开、">
                </div>
            </div>
            
            <div class="am-u-lg-3"> 
                 <label for="qAgeMin" class="am-u-sm-4 am-form-label">Q龄：小于</label>
                  <div class="am-input-group am-u-sm-8">
                    <input type="text" class="am-form-field" name="qAgeMin" value="${qAgeMin}" placeholder="小于多少天">
                  </div>
            </div> 
            <div class="am-u-lg-3"> 
                  <label for="qAgeMax" class="am-u-sm-4 am-form-label">大于</label>
                  <div class="am-input-group am-u-sm-8">
                       <input type="text" class="am-form-field" name="qAgeMax" value="${qAgeMax}" placeholder="大于多少天">
                  </div>
             </div> 
        </div>
        <div class="am-g tpl-amazeui-form">
            <div class="am-u-lg-3"> 
                  <label for="pageSize" class="am-u-sm-4 am-form-label">每页数量：</label>
                  <div class="am-input-group">
                       <input type="text" class="am-form-field" name="pageSize" value="${pageSize}" placeholder="每页数量">
                  </div>
             </div> 
        
            <div class="am-u-lg-3 am-u-end">
                <label for="qqLength" class="am-u-sm-4 am-form-label">QQ长度：</label>
                <div class="am-input-group am-u-sm-8"> 
                    <select id="qqLength" name="qqLength" class="inline-block">
                        <option value="">-请选择-</option>
                        <option value="9" <c:if test="${qqLength == 9 }">selected</c:if>>&nbsp;9位</option>
                        <option value="10" <c:if test="${qqLength == 10 }">selected</c:if>>10位</option>
                    </select>
                </div>
            </div>
        
            <div class="am-u-lg-6 am-u-end">
                <button class="am-btn am-btn-secondary am-radius" type="button" onclick="doQuery();">查询</button>
                <button class="am-btn am-btn-secondary am-radius" type="button" onclick="exportTxt();">导出txt</button>
                <label for="qqamount" class="am-u-sm-4 am-form-label">总计：${page.totalRow}</label>
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
							<th>QQ</th>
							<th>PWD</th>
							<th>所属分类</th>
							<th>编组</th>
							<th>标签</th>
							<th>入库时间</th>
							<th>Q龄</th>
							<th>登录次数</th>
							<th>使用状态</th>
							<th>卖出时间</th>
							<th>剩余天数</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="item">
							<tr>
								<td>${item.id }</td>
								<!-- 根据qq最近登录时间转化为天数  2天没登录红色、1天没登录黄色、当天登录绿色-->
                                <c:set var="loginDays" value="${nowDate.time - item.loginTime.time}"/>
                                <c:if test="${loginDays >= 2*1000*60*60*24  }">
                                    <td style="color:red;">${item.qq }</td>
                                </c:if>
                                <c:if test="${loginDays >= 1*1000*60*60*24 and loginDays<=2*1000*60*60*24 }">
                                    <td style="color:orange;">${item.qq }</td>
                                </c:if>
                                <c:if test="${loginDays < 1*1000*60*60*24  }">
                                    <td style="color:green;">${item.qq }</td>
                                </c:if>
								<td>${item.qqPwd }</td>
								<td>
								    <c:if test="${item.qqType == 1 }">白号</c:if>
								    <c:if test="${item.qqType == 2 }">三问号</c:if>
								    <c:if test="${item.qqType == 3 }">绑机号</c:if>
								    <c:if test="${item.qqType == 4 }">令牌号</c:if>
                                </td>
                                <td>${item.teamName }</td>
                                <td>${item.tags }</td>
								<td><fmt:formatDate value="${item.inStorageTime }" pattern="yyyy-MM-dd HH:mm" /></td>
								<td>${item.qqAge}</td>
								<td>${item.loginCount }</td>
								<td>
                                    <c:if test="${item.state == 1 }">可用</c:if>
                                    <c:if test="${item.state == 0 }">已冻结</c:if>
                                    <c:if test="${item.state == 2 }">永久冻结</c:if>
                                </td>
                                <td>
                                    <c:if test="${not empty item.outStorageTime  }">
                                        <fmt:formatDate value="${item.outStorageTime }" pattern="yyyy-MM-dd HH:mm" />
                                    </c:if>
                                </td>
                                <td>
                                <!-- 根据出库时间转化为天数 -->
                                <c:if test="${not empty item.outStorageTime  }">
                                    <c:set var="outDays" value="${nowDate.time - item.outStorageTime.time}"/>
                                    <fmt:formatNumber value="${item.outStorageDays - outDays/1000/60/60/24}" pattern="#0"/>
                                </c:if>
                                </td>
								<td>
									<div class="am-btn-toolbar">
										<div class="am-btn-group am-btn-group-xs">
											<button type="button" onclick="history( ${item.qq })"
												class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only">查看</button>
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
				
				<!-- 导出TXT -->
				<div class="am-modal am-modal-confirm" tabindex="-1" id="my-popup-exportTxt">
                    <div class="am-modal-dialog">
                        <div class="am-modal-hd">导出TXT</div>
                       
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

<script src="<%=basePath %>/js/qq.js"></script>