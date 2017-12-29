/**
 *ip临时库管理
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
    			url:getRootPath()+"/ip/delete",
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

/**
 * 一键清空
 */
function clean() {
    $('#my-confirm').modal({
       relatedTarget: this,
       onConfirm: function(options) {
    	   $.ajax({
    			url:getRootPath()+"/ip/clean",
           		data:{},
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