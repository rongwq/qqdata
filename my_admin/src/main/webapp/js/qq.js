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

function inStorage(){
	
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