page.index = {
	bindEvent:function(){
		$(".sy-show-top").click(function(){
			var href = $(this).data('href');
			utilObj.navigate(href);
		})
		$(".intro-ul").on('click','.btn-download',function(e){
			e.stopPropagation();
			var picPath = $(this).data('href');
			var name = $(this).siblings('.sy-filename').children(".announce-content").text();
			picPath = utilObj.fillPicPath(picPath);
			utilObj.downloadAll(picPath,name);
			
		})
	},
	loadTopNewsList:function(){
		var _this = this;
		var jsonFilter = {
			search_EQ_isTop:'1',
		}
		var param = {
			jsonFilter:JSON.stringify(jsonFilter),
			pageSize:pageSetting.Pagination_MaxPage,
			callback:function(d){							
				var h = '';
				$.each(d.content,function(i,val){
					h += 	'<li class="sy-info-li"><div class="sy-info-li-num">'+(i+1)+'</div>\
								<div class="sy-info-li-text" title="'+val.content+'">'+val.content+'</div>\
							</li>';
				})
				$(".top-news").html(h);
			}
		}
		page.service.getNewsData(param);
	},
	loadTopIntroList:function(){
		var _this = this;
		var jsonFilter = {
			search_EQ_isTop:'1',
		}
		var param = {
			jsonFilter:JSON.stringify(jsonFilter),
			pageSize:pageSetting.Pagination_MaxPage,
			callback:function(d){							
				var h = '';
				$.each(d.content,function(i,val){
					h += 	'<li class="intro-li">\
								<span class="serial-number">'+(i*1+1)+'.</span>\
								<div class="sy-filename" title="'+val.fileName+'">\
									'+val.fileName+'\
								</div>\
								<button class="bszk-label file-btn btn-download" data-href="'+val.filePath+'">点击下载</button>\
							</li>';
				})
				$(".intro-ul").html(h);
//				_this.bindDownloadEvent();
			}
		}
		page.service.getIntroData(param);
	},
//	bindDownloadEvent:function(){
//		$(".btn-download").click(function(e){
//			var href = $(this).data('href');
//			var filepath = utilObj.fillPicPath(href);
//			utilObj.downloadFile(filepath);
//			e.stopPropagation();
//		})
//	},
	loadAnnounce:function(){
		var _this = this;
		var param = {
			callback:function(d){							
				var data = d.content[0];
				var obj = $(".sy-notice-wrapper");
				obj.find(".ayear").text(data.ayear);
				obj.find(".yearOrders").text(data.yearOrders);
				obj.find(".byear").text(data.byear);
				obj.find(".bmonth").text(data.bmonth);
				obj.find(".monthOrders").text(data.monthOrders);
			}
		}
		page.service.getAnnounceData(param);
	},
	init:function(){
		utilObj.reSizeIndex();
		this.bindEvent();
		this.loadTopNewsList();
		this.loadTopIntroList();
		this.loadAnnounce();
	},
}
$(function(){
	page.index.init();
})
