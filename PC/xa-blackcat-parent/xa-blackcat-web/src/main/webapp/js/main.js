
//index.html-iframe高度计算
function adjustIfrHt(){
    var ht = $(window).height();
	var topHeader = $(".header").height();
    $("#ti_panel_frame").height(ht);
    $("#ti_panel_frame").height(ht - topHeader);
}
$(window).resize(adjustIfrHt);


function initLeftMenu(){
	//header 点击三角弹出信息
	$('.triangle_top').click(function(){
		$('.msg').toggle(500);
	});
	//侧面菜单
	$('.sidebar_dl dt').each(function(index,domEle){
		$(domEle).click(function(){
			if ($(this).siblings('dd').is(':visible')) {
				$(this).siblings('dd').slideUp();
				$(this).children('i').addClass('triangle_top2').removeClass('triangle_bottom2');
			} else{
				$('.sidebar_dl dd').slideUp();
				$('.sidebar_dl dt > i').removeClass('triangle_bottom2').addClass('triangle_top2');
				$(this).siblings('dd').slideDown();
				$(this).children('i').addClass('triangle_bottom2').removeClass('triangle_top2');
			}
		});
	});
	//侧边栏收缩
	$('.sidebar .l-hide').click(function(){
		if ($(this).siblings('.sidebar_item').is(':visible')) {
			$(this).siblings('.sidebar_item').hide();
			$('.main').animate({'margin-left':'20px'}, 300, 'linear');
		} else{
			$(this).siblings('.sidebar_item').show();
			$('.main').animate({'margin-left':'+220px'}, 100);
		}
	});
	
	//main页面表格变色
	$('.tab1 tr').filter(':even').css('background','#f8f8f8');
	
};





