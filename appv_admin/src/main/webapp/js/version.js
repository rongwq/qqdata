/**
 * app版本管理
 */

$(function() {
	initPage();//初始化分页
	initQueryForm();
	initDelete();
	initDisable();
	initEnable();
	initUploadFile();
});

/**
 * 上传文件
 */
function initUploadFile(){
	$("#file").fileupload({
		url : getRootPath() + "/file/upload?type=file",
		dataType: 'json',
		change: function(e, data) {
            if(data.files.length > 1){
                alert("一次只能选择一个文件上传");
                return false;
            }
        },
        done: function (e, data) {
        	var file = data.result.file;
        	var fileSrc = file.url;
        	$("#downloadUrl").val(fileSrc);
        	var size = file.size;
        	var fileName = fileSrc.substring(fileSrc.lastIndexOf("/") + 1);
        	$(e.target).before("<span class='am-badge' name='file'>" + fileName + "</span>");
        	$("#fileSize").val(size);
        	$("#isFile").val("1");
        	$(e.target).before("<a class='am-close am-close-alt am-icon-times'/>");
        	var spans = $(e.target).siblings("span");
        	if($(e.target).attr("number")) {
        		if(spans.length >= $(e.target).attr("number")){
        			$(e.target).attr("disabled", true);
        		}
        	}
        }
	});
	
	var showFile = $("#showFile");
	var fileNameVal = $("#showFile").val();
	var isFile = $("#isFile").val();
	if(isFile) {		
		$(showFile).after("<a class='am-close am-close-alt am-icon-times' onclick='delFile(\"" + fileNameVal + "\",this)'/>");
		$(showFile).after("<span class='am-badge' name='file'>" + fileNameVal + "</span>");
		$("#file").attr("disabled", true);
	}
}


/**
 * 初始化删除事件
 */
function initDelete(){
	$("button[name='delBtn']").on('click', function() {
		$('#confirm_msg').text("确定删除该app版本？");
	      $('#my_confirm').modal({
	        relatedTarget: this,
	        onConfirm: function(options) {
	        	var $link = $(this.relatedTarget);
	        	$.ajax({
	           		url:getRootPath()+"/appv/versionDelete",
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
 * 初始启用事件
 */
function initEnable(){
	$("button[name='enableBtn']").on('click', function() {
		$('#confirm_msg').text("确定启用此版本？");
	      $('#my_confirm').modal({
	        relatedTarget: this,
	        onConfirm: function(options) {
	        	var $link = $(this.relatedTarget);
	        	$.ajax({
	           		url:getRootPath()+"/appv/versionEnable",
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
 * 初始禁用事件
 */
function initDisable(){
	$("button[name='disableBtn']").on('click', function() {
		$('#confirm_msg').text("确定禁用此版本？");
	      $('#my_confirm').modal({
	        relatedTarget: this,
	        onConfirm: function(options) {
	        	var $link = $(this.relatedTarget);
	        	$.ajax({
	           		url:getRootPath()+"/appv/versionDisable",
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


