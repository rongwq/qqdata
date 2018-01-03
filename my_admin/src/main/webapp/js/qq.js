/**
 *qq数据管理
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
 * 选中所有，和取消选中
 */
function checkAll(){ 
	if ($("#checkAllBox").is(':checked')) {
		$('input[name="ckb_id"]').uCheck('check');
	}else{
		$('input[name="ckb_id"]').uCheck('uncheck');
	}
}

/**
 * 更新密码
 */
function updatePwd(){
	var allCheckQq = getCheckQqAndPwd();
	loadRight(getRootPath()+'/views/qq/editPwd.jsp?val='+encodeURI(allCheckQq),'密码修改');
}

/**
 * 更新标签
 */
function updateTag(){
	var allCheckQq = getCheckQq();
	loadRight(getRootPath()+'/views/qq/editTag.jsp?val='+encodeURI(allCheckQq),'标签修改');
}

/**
 * 出库
 * 自动去除已出库的复选框
 */
function outStorage(){
	var len = $('input[name="ckb_id"]:checked').length;
	if(len==0){
		alert("请选择qq");
		return false;
	}
	var allCheckQq = "";
	$.each($('input[name="ckb_id"]'),function(){
		var checkQq = "";
        if($(this).is(':checked')){
        	var qqTr = $(this).parent().parent().parent();
        	var qqStorageState = $.trim(qqTr.find("td:eq(10)").text());
        	var qqState = $.trim(qqTr.find("td:eq(11)").text());
        	if(qqStorageState=="未出仓" && qqState=='可用'){//去掉不可用和已出库
	        	var qq = qqTr.find("td:eq(2)").text();
	        	checkQq = (qq +";");
	        	allCheckQq += checkQq;
        	}
        }
    });
	allCheckQq = allCheckQq.substr(0,allCheckQq.length-1);
	loadRight(getRootPath()+'/views/qq/outStorage.jsp?val='+encodeURI(allCheckQq),'出库');
}

/**
 * 获取表格qq和密码
 * @returns qq----密码
 */
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
 * 获取所有qq
 * @returns qq
 */
function getCheckQq(){
	return getCheckQqByTag("\n");
}

/**
 * 获取所有qq
 * @returns qq
 */
function getCheckQqByTag(tag){
	var allCheckQq = "";
	$.each($('input[name="ckb_id"]'),function(){
		var checkQq = "";
        if($(this).is(':checked')){
        	var qqTr = $(this).parent().parent().parent();
        	var qq = qqTr.find("td:eq(2)").text();
        	checkQq = (qq +tag);
        	allCheckQq += checkQq;
        }
    });
	return allCheckQq;
}

/**
 * 查看
 */
function history(qq){
	loadRight(getRootPath()+"/qq/history?qq="+qq, "QQ历史信息");
}

/**
 * 导出txt
 */
function exportTxt() {
	$.ajax({
		url : getRootPath() + "/qq/exportTxt",
		data : $('#queryForm').formSerialize()
	}).done(function(data) {
		window.open(getRootPath()+"/qq导出数据.zip");
	});
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
	           		url:getRootPath()+"/qq/delete",
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