var img = "<img class='am-img-thumbnail' height='120px' width='120px' src='";
var closeHead = "<a class='am-close am-close-alt am-icon-times' onclick=\"deleteFile('";
var closeFoot = "', this)\"/>";
var foot = "'/>";
var inputHead = "<input name='";
var inputName = "img";
var inputMiddle = "' type='hidden' value='";
var inputFoot = "'/>";
$(function() {
	// 图片上传
	$("input[type=file]").fileupload(
			{
				// 图片上传路径
				url : getRootPath() + "/file/uploadImg",
				dataType : 'json',
				change : function(e, data) {
					if (data.files.length > 1) {
						alert("一次只能选择一张图片上传");
						return false;
					}
				},
				done : function(e, data) {
					if (data.result.error) {
						alert("不能上传非图片文件");
						return;
					}
					var imgPath = data.result.imgPath;
					$(e.target).before(img + imgPath + foot);
					$(e.target).before(closeHead + imgPath + closeFoot);
					if ($(e.target).attr("create")) {
						var createName = $(e.target).attr("create");
						$(e.target).before(
								inputHead + createName + inputMiddle + imgPath
										+ inputFoot);
					} else {
						$(e.target).before(
								inputHead + inputName + inputMiddle + imgPath
										+ inputFoot);
					}
					var imgs = $(e.target).siblings("img");
					if ($(e.target).data("number")) {
						if (imgs.length >= $(e.target).data("number")) {
							$(e.target).attr("disabled", true);
						}
					}
				}
			});
	//显示数据
	var showImage = $("input[name=showImage]");
	for(var i=0; i<showImage.length; i++){
		var imgValue = $(showImage[i]).val();
		var fileName = $(showImage[i]).siblings("input[type=file]").attr("create");
		if(imgValue && (imgValue.indexOf("null") == -1)){
			var prefix = imgValue.indexOf("[");
			var suffix = imgValue.indexOf("]");
			if(prefix >= 0) {
				if(imgValue.length<=2){
					break;
				}
				imgValue = imgValue.substring(prefix + 1, suffix);
			}
			var imgs = imgValue.split(",");
			for(var j=0; j<imgs.length; j++){
				imgs[j] = imgs[j].trim();
				if (fileName){
					$(showImage[i]).after(inputHead + fileName + inputMiddle + imgs[j] + inputFoot);
				}else{
					$(showImage[i]).after(inputHead + inputName + inputMiddle + imgs[j] + inputFoot);
				}
				$(showImage[i]).after(closeHead + imgs[j] + closeFoot);
				$(showImage[i]).after(img + imgs[j] + foot);
			}
			var fileNumber = $(showImage[i]).siblings("input[type=file]").data("number");
			if(fileNumber){				
				if(j >= fileNumber){
					$(showImage[i]).siblings("input[type=file]").attr("disabled", true);
				}
			}
		}
	}
});
function deleteFile(fileName, element) {
	$.ajax({
		url : getRootPath() + "/file/delete",
		data : {
			"fileName" : fileName
		},
		dataType : "text",
		success : function(data) {
			$(element).prev().remove();
			$(element).next().remove();
			if ($(element).siblings("input[type=file]").attr("disabled")) {
				$(element).siblings("input[type=file]").removeAttr("disabled");
			}
			$(element).remove();
		}
	});
}
