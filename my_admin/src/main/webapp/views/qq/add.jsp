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
            	<form id="dataForm" action="<%=basePath%>/qq/add" method="POST" class="am-form am-form-horizontal">
					<div class="am-form-group">
		    			<label for="qqData" class="am-u-sm-3 am-form-label">录入格式&nbsp;&nbsp;<a href="javascript:$('#doc').modal('open');">格式说明</a></label>
		    			<div class="am-u-sm-9">
		    				<textarea  name="qqData" id="qqData" rows="10" ></textarea>
		    			</div>
					</div>
					
					<div class="am-form-group">
                        <label for="teamName" class="am-u-sm-3 am-form-label">编组名称</label>
                        <div class="am-u-sm-9">
                            <input class="am-form-field" type="text" name="teamName" id="teamName" required="required">
                        </div>
                    </div>
                    
                    <div class="am-form-group">
                        <label for="costPrice" class="am-u-sm-3 am-form-label">成本价</label>
                        <div class="am-u-sm-9">
                            <input class="am-form-field" type="text" name="costPrice" id="costPrice" min="1" max="10" required="required"/>
                        </div>
                    </div>
                    
                    <div class="am-form-group">
                        <label for="tags" class="am-u-sm-3 am-form-label">标签</label>
                        <div class="am-u-sm-9">
                            <input class="am-form-field" type="text" name="tags" id="tags" placeholder="名称">
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

<div class="am-modal am-modal-no-btn" tabindex="-1" id="doc">
  <div class="am-modal-dialog">
    <div class="am-modal-hd"><h1>录入格式</h1>
      <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
    </div>
    <div class="am-modal-bd">
<p>注：一行显示一个号码信息，多个号码换行显示</p>
<p>各种类型格式如下：<br>
  白号<br>
  2383088706----xzzqt11480<br>
  三问号<br>
  2383088706----xzzqt11480----您母亲的姓名是？----uesxvm----您高中班主任的名字是？----unojku----您父亲的姓名是？----uyzkox<br>
  绑机号<br>
  2383088706----xzzqt11480----您母亲的姓名是？----uesxvm----您高中班主任的名字是？----unojku----您父亲的姓名是？----uyzkox----15243834134<br>
  令牌号<br>
  2383088706----xzzqt11480----您母亲的姓名是？----uesxvm----您高中班主任的名字是？----unojku----您父亲的姓名是？----uyzkox----15243834134----daac29701bc36f<br>
    </p>
    </div>
  </div>
</div>
    
<script>
  $(function() {
    $("#dataForm").submit(function() {
    	var validSuccess = checkQqData($("#qqData").val());
    	if(!validSuccess){
    		return false;
    	}
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
