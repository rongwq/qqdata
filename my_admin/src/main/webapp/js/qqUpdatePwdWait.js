/**
 *qq等待修改密码
 */

/**
 * 初始化
 */
$(function() {
	initPage();//初始化分页
	initQueryForm();//初始化查询
});

/**
 * 删除
 * @param id
 */
function deleteTeam(id) {
    $('#my-confirm').modal({
       relatedTarget: this,
       onConfirm: function(options) {
    	   $.ajax({
    			url:getRootPath()+"/qq/deleteUpdatePwdWait",
           		data:{"id":id},
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