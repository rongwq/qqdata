$(function() {

        var $fullText = $('.admin-fullText');
        $('#admin-fullscreen').on('click', function() {
            $.AMUI.fullscreen.toggle();
        });

        $(document).on($.AMUI.fullscreen.raw.fullscreenchange, function() {
            $fullText.text($.AMUI.fullscreen.isFullscreen ? '退出全屏' : '开启全屏');
        });

		var dataType = $('body').attr('data-type');
        for (key in pageData) {
            if (key == dataType) {
                pageData[key]();
            }
        }
    })
    // ==========================
    // 侧边导航下拉列表
    // ==========================

$('.tpl-left-nav-link-list').on('click', function() {
			$(this).siblings('.tpl-left-nav-sub-menu').slideToggle(80).end().find('.tpl-left-nav-more-ico').toggleClass('tpl-left-nav-more-ico-rotate');
			$('.am-breadcrumb').children('li').eq(1).children('a').eq(0).text( $(this).children('span').eq(0).text());//设置有则导航栏值
			//点开一个菜单，将其它菜单栏隐藏
			var obj = $(this).siblings('.tpl-left-nav-sub-menu');
			$('.tpl-left-nav-sub-menu').each(function(){
				if(!$(this).is(obj)){
					$(this).hide(500);
				}
			});
    })
    // ==========================
    // 头部导航隐藏菜单
    // ==========================

$('.tpl-header-nav-hover-ico').on('click', function() {
    $('.tpl-left-nav').toggle();
    $('.tpl-content-wrapper').toggleClass('tpl-content-wrapper-hover');
})

/**
 * 给所有a标签添加事件,点击添加选中效果，并将其他选中的效果删除
 */
$("a").click(function(){
	$('a').each(function(){
	   if($(this).attr('href')){
		   $(this).removeClass("active");
	   }
	});
	$(this).addClass("active");
});

var pageData = {
	// ===============================================
    // 后台首页-报表数据
    // ===============================================
		
    'index': function indexData() {
    	var actList,regList,dayList;
    	$.ajax({
			url:getRootPath()+"/qqTypeStatis",
			async : false
		}).done(function(dataInfo){
			var data = dataInfo.resultData;
			//qq数据赋值
			var qqStatisStr = "";
			var qqTypeArr = [];
			var qqTypeArrVal = [];
			for (var i = 0; i < data.length; i++) {
				var qqType = "";
				if(data[i].qqType==1){
					qqType = "白号";
				}else if(data[i].qqType==2){
					qqType = "三问号";
				}else if(data[i].qqType==3){
					qqType = "绑机号";
				}else{
					qqType = "令牌号";
				}
				qqStatisStr += "<tr><td>" + qqType + "</td>" + "<td>"
							+ data[i].allCount + "</td>" + "<td>"
							+ data[i].qqlen9Count + "</td>" + "<td>"
							+ data[i].qqlen10Count + "</td>" + "<td>"
							+ data[i].storageCount + "</td>" + "<td>"
							+ data[i].outStorageCount + "</td>" + "<td>"
							+ data[i].state0 + "</td>" + "<td>"
							+ data[i].state2 + "</td>" + "</tr>";
				qqTypeArr[i] = qqType;
				qqTypeArrVal[i] = data[i].storageCount;
			}
			$("#qqStatis").html(qqStatisStr);
			
			//图表赋值展示
			var echartsA = echarts.init(document.getElementById('tpl-echarts-A'));

			option = {
			    color: ['#3398DB'],
			    tooltip : {
			        trigger: 'axis',
			        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
			            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
			        }
			    },
			    grid: {
			        left: '3%',
			        right: '4%',
			        bottom: '3%',
			        containLabel: true
			    },
			    xAxis : [
			        {
			            type : 'category',
			            data : qqTypeArr,
			            axisTick: {
			                alignWithLabel: true
			            }
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value'
			        }
			    ],
			    series : [
			        {
			            name:'库存数',
			            type:'bar',
			            barWidth: '60%',
			            data:qqTypeArrVal
			        }
			    ]
			};
			
			echartsA.setOption(option);

		});

    }
	
}