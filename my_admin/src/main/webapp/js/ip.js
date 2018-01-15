/**
 *ip临时库管理
 */

/**
 * 初始化
 */
$(function() {
	initPage();//初始化分页
	initQueryForm();//初始化查询
	initDelete();
});

/**
 * 初始化删除事件
 */
function initDelete(){
	$("button[name='delBtn']").on('click', function() {
		$('#confirm_msg').text("确定删除？");
	      $('#my_confirm').modal({
	        relatedTarget: this,
	        onConfirm: function(options) {
	        	var $link = $(this.relatedTarget);
	        	$.ajax({
	           		url:getRootPath()+"/ip/delete",
	           		data:{"id":$link.data("id")},
	           		dataType:"text",
	           		success:function(data){
	           			$('#my_confirm').modal("close");
	           			var obj = jQuery.parseJSON(data);
	           			alert(obj.resultDes);
	           			if(obj.resultCode == '1'){
	           				doQuery();
	           			}
	           		}
	           	})
	        }
	      });
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