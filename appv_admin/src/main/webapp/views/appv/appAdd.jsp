<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="/views/common/meta.jsp"%>  
<div class="tpl-portlet-components">
    <div class="tpl-block">
        <div class="am-g tpl-amazeui-form">
        	<div class="am-u-sm-12 am-u-md-9">
            	<form id="dataForm" action="<%=basePath%>/appv/appSave" method="POST" class="am-form am-form-horizontal">
  					<div class="am-form-group">
  						<label for="userName" class="am-u-sm-3 am-form-label">APP名称 </label>
		    			<div class="am-u-sm-9">
		      				<input class="am-form-field" type="text" name="appName" id="appName" placeholder="输入APP名称" required="required" maxlength="20">
		    			</div>
					</div>
					<div class="am-form-group">
		    			<label for="mobile" class="am-u-sm-3 am-form-label">CODE</label>
		    			<div class="am-u-sm-9">
		      				<input class="am-form-field" type="text" name="appCode" id="appCode" placeholder="输入APP的CODE" required="required"  maxlength="16">
		    			</div>
					</div>
					<div class="am-form-group">
		   				<label for="configremark" class="am-u-sm-3 am-form-label">备注</label>
		    			<div class="am-u-sm-9">
		    				<textarea  name="remark" id="remark" rows="5" ></textarea>
		    			</div>
					</div>
					<div class="am-form-group">
						<div class="am-u-sm-9 am-u-sm-push-3">
		  					<input type="submit" class="am-btn am-btn-primary" value="提交">
		  					<input type="button" onclick="javascript:loadRight('<%=basePath %>/appv/appList?page=${page }')" class="am-btn am-btn-default" value="返回">
	  					</div>
  					</div>
  				</form>
          	</div>
      	</div>
  	</div>
</div>
  
<script>
  $(function() {
    $("#dataForm").submit(function() {
		$(this).ajaxSubmit({
			method:"POST",
			data:$('#dataForm').formSerialize(),
			success:function(data) {
				alert(data.resultDes);
       			if(data.resultCode == '1'){
					loadRight(getRootPath()+"/appv/appList");
				}
			}
		});
		return false;
	});
  });
</script>  	
