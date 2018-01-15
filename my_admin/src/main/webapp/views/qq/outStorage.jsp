<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="/views/common/meta.jsp"%>

<div class="tpl-portlet-components">
    <div class="tpl-block">
    	<div class="am-g">
        	<div class="am-u-sm-12 am-u-md-6">
            	<div class="am-btn-toolbar">
                	<div class="am-btn-group am-btn-group-xs">
                    	
                   	</div>
               	</div>
           	</div>   
      	</div>
        <div class="am-g tpl-amazeui-form">
        	<div class="am-u-sm-12 am-u-md-9">
            	<form id="dataForm" action="<%=basePath%>/qq/outStorage" method="POST" class="am-form am-form-horizontal">
					<div class="am-form-group">
		    			<label id="qqCount" class="am-u-sm-3 am-form-label">已选数量：0</label>
		    			<div class="am-u-sm-9" id="checkBoxDiv">
		    			
		    			</div>
					</div>
					
                    <div class="am-form-group">
                        <label for="tags" class="am-u-sm-3 am-form-label">标签</label>
                        <div class="am-u-sm-9">
                            <input class="am-form-field" type="text" name="tags" id="tags" placeholder="名称">
                        </div>
                    </div>
                    
                     <div class="am-form-group">
                        <label for="outStorageDays" class="am-u-sm-3 am-form-label">使用时长(天)</label>
                        <div class="am-u-sm-9">
                            <input class="am-form-field" type="number" name="outStorageDays" id="outStorageDays" placeholder="按天算">
                        </div>
                    </div>
					
					<div class="am-form-group">
						<div class="am-u-sm-9 am-u-sm-push-3">
		  					<input type="submit" class="am-btn am-btn-primary" value="提交">
		  					<input type="button" onclick="javascript:loadRight('<%=basePath %>/qq/list')" class="am-btn am-btn-default" value="返回">
	  					</div>
  					</div>
  				</form>	
          	</div>
      	</div>
  	</div>
</div>

<script>
  $(function() {
	 var allCheckQq = '${param.val}';  
	 var arr = allCheckQq.split(";");
	 var arrLen = arr.length;
	 var htmlStr = '';
	 htmlStr += '';
	 for(var i=1;i<=arrLen;i++){
		 htmlStr += '<input type="checkbox" name="qq" data-am-ucheck checked = "checked" value="'+arr[i-1]+'">' + arr[i-1] +"&nbsp;&nbsp;&nbsp;&nbsp;";
		 if(i % 5 == 0){
             htmlStr += "<br>";
         }
	 }
	 $("#qqCount").text("已选数量："+arrLen);
	 $("#checkBoxDiv").html(htmlStr);
	  
    $("#dataForm").submit(function() {
		$(this).ajaxSubmit({
			method:"POST",
			data:$('#dataForm').formSerialize(),
			success:function(data) {
				alert(data.resultDes);
       			if(data.resultCode == '1'){
					loadRight(getRootPath()+"/qq/list");
				}
			}
		});
		return false;
	});
  });
</script>  	
