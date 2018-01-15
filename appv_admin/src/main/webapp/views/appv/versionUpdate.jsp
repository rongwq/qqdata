<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="/views/common/meta.jsp"%>  
<div class="tpl-portlet-components">
    <div class="tpl-block">
        <div class="am-g tpl-amazeui-form">
        	<div class="am-u-sm-12 am-u-md-9">
            	<form id="dataForm" action="<%=basePath%>/appv/versionUpdate" method="POST" class="am-form am-form-horizontal">
            		<input type="hidden" name="id" value="${model.id }">
            		<input type="hidden" id="fileSize" name="fileSize" value="${model.fileSize }">
					<input type="hidden" id="isFile" name="isFile" value="${model.isFile }">
            		<div class="am-form-group">
					    <label for="userName" class="am-u-sm-2 am-form-label">项目</label>
					    <div class="am-u-sm-10">
					      	<label for="userName" class="am-form-label">${model.appName}</label>
					    </div>
					</div>
			  		<div class="am-form-group">
					    <label for="versionName" class="am-u-sm-2 am-form-label">版本名称</label>
					    <div class="am-u-sm-10">
					      	<input class="am-form-field" type="text" name="versionName" placeholder="输入版本名称" value="${model.versionName }" required="required">
					    </div>
					</div>
					<div class="am-form-group">
					    <label for="appVersion" class="am-u-sm-2 am-form-label">版本号</label>
					    <div class="am-u-sm-10">
					      	<input class="am-form-field" type="text" name="appVersion" placeholder="输入版本号" value="${model.appVersion }" required="required">
					    </div>
					</div>
					<div class="am-form-group">
					    <label for="downloadUrl" class="am-u-sm-2 am-form-label">下载链接</label>
					    <div class="am-u-sm-10">
					      	<input class="am-form-field" type="text" id="downloadUrl" name="downloadUrl" placeholder="输入下载链接" value="${model.downloadUrl }" required="required">
					    </div>
					</div>
					<div class="am-form-group">
						<label for="systemType" class="am-u-sm-2 am-form-label">系统类型</label>
						<div class="am-u-sm-10">
							<input type="radio" name="systemType"  <c:if test="${model.systemType == 1}">checked</c:if> value="1"/>Android
							<input type="radio" name="systemType" <c:if test="${model.systemType == 2 }">checked</c:if> value="2"/>iOS
						</div>
					</div>
					<div class="am-form-group">
						<label for="sequence" class="am-u-sm-2 am-form-label">是否自动下载</label>
						<div class="am-u-sm-10">
							<input type="radio" name="autoDownload" <c:if test="${!model.autoDownload}">checked</c:if> value="0"/>否
							<input type="radio" name="autoDownload" <c:if test="${model.autoDownload}">checked</c:if> value="1"/>是
						</div>
					</div>
					<div class="am-form-group">
						<label for="downloadUrl" class="am-u-sm-2 am-form-label">文件</label>
						<div class="am-u-sm-10">
							<!-- 这里是用来接收后台传来的数据 -->
							<input type="hidden" id="showFile" value="${model.downloadUrl }"/>
							<!-- number是用来限制上传图片的数量，达到数量则上传按钮会失效，如果不想做限制，则可以去除掉number属性或者是设置为大数量如1000-->
							<input id="file" type="file" name="files[]" multiple class="am-form-field" data-number="1"/>
						</div>
					</div>
					<div class="am-form-group">
						<label for="img" class="am-u-sm-2 am-form-label">图标</label>
						<div class="am-u-sm-10">
							<input type="hidden" name="showImage" value="${model.iconUrl }">
							<!-- number是用来限制上传图片的数量，达到数量则上传按钮会失效，如果不想做限制，则可以去除掉number属性或者是设置为大数量如1000-->
							<input required="required" type="file" name="files[]" multiple class="am-form-field" data-number="1"/>
						</div>
					</div>
					
					<div class="am-form-group">
						<label for="sequence" class="am-u-sm-2 am-form-label">备注</label>
						<div class="am-u-sm-10">
			          		   <textarea class="am-form-field" name="remark" placeholder="备注" rows="10">${model.remark}</textarea>
						</div>
					</div>
					
					<div class="am-form-group">
						<div class="am-u-sm-10 am-u-sm-offset-2">
					  		<input type="submit" class="am-btn am-btn-primary" value="提交">
					  		<input type="button" onclick="javascript:loadRight('<%=basePath %>/appv/versionList?appId=${model.appId}')" class="am-btn am-btn-default" value="返回">
				  		</div>
			  		</div>
  				</form>
          	</div>
      	</div>
  	</div>
</div>
  
<script src="<%=basePath %>/assets/js/fileupload.js"></script>
<script src="<%=basePath %>/js/version.js"></script>
  
<script>
  $(function() {
    $("#dataForm").submit(function() {
		$(this).ajaxSubmit({
			method:"POST",
			data:$('#dataForm').formSerialize(),
			success:function(data) {
				alert(data.resultDes);
       			if(data.resultCode == '1'){
					loadRight(getRootPath()+"/appv/versionList?appId=${model.appId}");
				}
			}
		});
		return false;
	});
  });
</script>  	
