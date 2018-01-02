/**
 *qq数据
 */

/**
 * 初始化
 */
$(function() {
	initPage();//初始化分页
	initQueryForm();//初始化查询
	initDelete();
	initUpdateTeamName();
});


function getCheckQqAndPwd(){
	var allCheckQq = "";
	$.each($('input[name="ckb_id"]'),function(){
		var checkQq = "";
        if($(this).is(':checked')){
        	var qqTr = $(this).parent().parent().parent();
        	var qq = qqTr.find("td:eq(2)").text();
        	var pwd = qqTr.find("td:eq(3)").text();
        	checkQq = (qq +"----"+pwd+"\n");
        	allCheckQq += checkQq;
        }
    });
	return allCheckQq;
}

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
	           		url:getRootPath()+"/qqTeam/delete",
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
 * 修改编组名称
 */
function initUpdateTeamName(){
	$("button[name='updateBtn']").on('click', function() {
	      $('#my-prompt').modal({
	        relatedTarget: this,
	        onConfirm: function(options) {
	        	var $link = $(this.relatedTarget);
	        	if(options.data==""){
	     		   alert("请填写编组名称")
	     		   return ;
	     	   	}
	     	    $.ajax({
	     			url:getRootPath()+"/qqTeam/updateName",
	            		data:{"id":$link.data("id"), "teamName":options.data},
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
	    });
}