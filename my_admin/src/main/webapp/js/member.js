/**
 *会员用户
 */

/**
 * 初始化
 */
$(function() {
	initPage();//初始化分页
	initQueryForm();//初始化查询
});

/**
 * 重置密码
 * @param id
 */
function resetPassword(id) {
    $('#my-prompt').modal({
       relatedTarget: this,
       onConfirm: function(options) {
    	   $.ajax({
    			url:getRootPath()+"/user/resetPassword",
           		data:{"id":id, "password":options.data},
           		dataType:"text",
           		success:function(data){
           			var obj = jQuery.parseJSON(data);
           			alert(obj.resultDes);
           			if(obj.resultCode == '1'){
           				doQuery();
           			}
           		}
    		});
       }
    });   
}

/**
 * 更新用户状态
 * @param id
 * @param isEnable
 */
function updateState(id,isEnable) {
    $('#my-confirm').modal({
       relatedTarget: this,
       onConfirm: function(options) {
    	   $.ajax({
    			url:getRootPath()+"/user/setEnable",
           		data:{"id":id, "isEnable":isEnable},
           		dataType:"text",
           		success:function(data){
           			var obj = jQuery.parseJSON(data);
           			alert(obj.resultDes);
           			if(obj.resultCode == '1'){
           				doQuery();
           			}
           		}
    		});
       }
    });   
}

/**
 * 启用
 * @param id
 */
function enable(id){
	updateState(id, true);
}

/**
 * 禁用
 * @param id
 */
function disEnable(id){
	updateState(id, false);
}