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
 * 删除
 * @param id
 */
function deleteTeam(id) {
    $('#my-confirm').modal({
       relatedTarget: this,
       onConfirm: function(options) {
    	   $.ajax({
    			url:getRootPath()+"/qqTeam/delete",
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