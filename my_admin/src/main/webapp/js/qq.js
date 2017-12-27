/**
 *qq数据
 */

/**
 * 初始化
 */
$(function() {
	initPage();//初始化分页
	initQueryForm();//初始化查询
});

/**
 * 选中所有，和取消选中
 */
function checkAll(){ 
	if ($("#checkAllBox").is(':checked')) {
		$('input[name="ckb_id"]').uCheck('check');
	}else{
		$('input[name="ckb_id"]').uCheck('uncheck');
	}
}

function updatePwd(){
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
	loadRight(getRootPath()+'/views/qq/editPwd.jsp?val='+encodeURI(allCheckQq),'密码修改');
}

/**
 * 查看
 */
function history(qq){
	loadRight(getRootPath()+"/qq/history?qq="+qq, "QQ历史信息");
}


/**
 * 删除
 * @param id
 */
function updateState(id) {
    $('#my-confirm').modal({
       relatedTarget: this,
       onConfirm: function(options) {
    	   $.ajax({
    			url:getRootPath()+"/qq/delete",
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