/**
 * 上下滑动
 */
var detailList = {
		// 下拉刷新容器标识，比如：id、.class等
		divPullRefresh : "#divPullRefresh",
		ulPullRefresh : "#contentData",
		/**
         * 初始化
         */
        init : function() {
            mui.init({
                pullRefresh : {
                    // 下拉刷新容器标识，querySelector能定位的css选择器均可，比如：id、.class等
                    container : detailList.divPullRefresh,
                    down : {
                        // 必选，刷新函数，根据具体业务来编写，比如通过ajax从服务器获取新数据
                        callback : detailList.pulldownRefresh
                    },
                    up : {
                        // 必选，刷新函数，根据具体业务来编写，比如通过ajax从服务器获取新数据
                        callback : detailList.pullupRefresh
                    }
                }
            });

            // 第一次加载数据
            if (mui.os.plus) {
                mui.plusReady(function() {
                    setTimeout(function() {
                        mui(detailList.divPullRefresh).pullRefresh().pullupLoading();
                    }, 1000);
                });
            } else {
                mui.ready(function() {
                    setTimeout(function() {
                        mui(detailList.divPullRefresh).pullRefresh().pullupLoading();
                    }, 1000);
                });
            }

        },
    /**
     * 下拉刷新
     */
	pulldownRefresh : function() {
    	detailList.selectGoods(0,true);
    },
    /**
     * 上拉刷新
     */
    pullupRefresh : function() {
    	var currentPageNo = $(detailList.ulPullRefresh).find(".review").length;
    	detailList.selectGoods(currentPageNo,false);
    },
    /**
     * 加载商品列表
     * 
     * @param result
     * @param isRemoveAll
     */
    selectGoods : function(currentPageNo,isRemoveAll) {
    	var goodsId = $('#goodsId').val();
    	var url = '/fegoods/selectEval';
    	var pageSize = 6;
        $.ajax({
        	data:{'currentPageNo':currentPageNo,'pageSize':pageSize,'goodsId':goodsId},
            type: 'POST',
            url: url,
            dataType: 'json',
            success: function(data){
            	if(data.isError==1){
            		mui.toast(data.msg);
            	}else{
            		if (isRemoveAll) {
            			$(detailList.ulPullRefresh).empty();
            			// 启用上拉加载
            			mui(detailList.divPullRefresh).pullRefresh().enablePullupToRefresh();
            			mui(detailList.divPullRefresh).pullRefresh().scrollTo(0, 0, 100);
            		}
            		if (!data || !data.rows) {
            			// 加载完毕
            			$(detailList.ulPullRefresh).height(35);
            			mui(detailList.divPullRefresh).pullRefresh().endPullupToRefresh(true);
            			return;
            		}
            		var rows = data.rows;
            		var tb = [];
            		if(rows && rows.length>0){
            			for(var i = 0; i < rows.length; i++){
            				var ob = rows[i];
            				tb[tb.length] = '<article class="review clearfix">';
            				tb[tb.length] = '<article class="pic pull-left">';
            				var userpic = '/upload/10.jpg';
            				if(ob.picUrl){
            					userpic = ob.userPicUrl;
            				}
            				tb[tb.length] = '	<figure>';
            				tb[tb.length] = '		<img src="'+userpic+'" alt="">';
            				tb[tb.length] = '		<figcaption>'+nullToStr(ob.userName)+'</figcaption>';
            				tb[tb.length] = '	</figure>';
            				tb[tb.length] = '</article>';
            				tb[tb.length] = '<article class="soldar pull-right">';
            				tb[tb.length] = '	 <section class="item clearfix">';
            				tb[tb.length] = '	 	<ul class="stars pull-left">';
            				var tscore = 0;
            				if(ob.evalTotalScore){
            					tscore = 1*ob.evalTotalScore;
            					for (var j = 0; j < tscore; j++) {
            						tb[tb.length] = '<li class="on"></li>';
            					}
            					var tscorestr = (tscore+'').split('.');
            					var num = 5-1*tscorestr[0];
            					var half = tscorestr[1];
            					
            					if(half && half>0){
            						tb[tb.length] = '<li class="an"></li>';
            						num = num+1;
            					}
            					for (var j = 0; j < num; j++) {
            						tb[tb.length] = '<li></li>';
            					}
            				}else{
            					for (var j = 0; j < 5; j++) {
            						tb[tb.length] = '<li></li>';
            					}
            				}
            				tb[tb.length] = '	 	</ul>';
            				tb[tb.length] = '	 	<span class="tiem pull-right">'+nullToStr(ob.updateDate)+'</span>';
            				tb[tb.length] = '	 </section>';
            				tb[tb.length] = '	<section class="text">';
            				tb[tb.length] = nullToStr(ob.evalComment);
            				tb[tb.length] = '		<div class="imgs">';
            				tb[tb.length] = '			<figure>';
            				var plist = ob.plist;
            				if(plist && plist.length>0){
            					for (var j = 0; j < plist.length; j++) {
            						tb[tb.length] = '<img src="'+nullToStr(plist[j].picUrl)+'" alt="">';
            					}
            				}
            				tb[tb.length] = '	        </figure>';
            				tb[tb.length] = '		</div>';
            				tb[tb.length] = '	</section>';
            				
            				tb[tb.length] = '	<section class="woder">';
            				tb[tb.length] = '		<span>服务：  '+nullToStr(ob.serviceTotalScore)+'</span>';
            				tb[tb.length] = '		<span>环境： '+nullToStr(ob.environmentTotalScore)+'</span>';
            				tb[tb.length] = '		<span>口味：  '+nullToStr(ob.tasteTotalScore)+'</span>';
            				tb[tb.length] = '	</section>';
            				tb[tb.length] = '</article>';
            				tb[tb.length] = '</article>';
            			}
            		}
            		$(detailList.ulPullRefresh).append(tb.join(""));
            		// 当前商品推荐详细信息总数
            		var curtotal =  $(detailList.ulPullRefresh).find(".review").length;
            		// 商品推荐详细信息总数
            		var totalCnt = data.totalCnt;
            		// 若数据已加载完毕
            		if (!curtotal || (curtotal >= totalCnt)) {
            			// 显示加载完毕字样
            			mui(detailList.divPullRefresh).pullRefresh().endPullupToRefresh(true);
            			// 结束上拉加载
            			mui(detailList.divPullRefresh).pullRefresh().disablePullupToRefresh();
            		} else {
            			// 结束加载，但数据没加载完毕
            			mui(detailList.divPullRefresh).pullRefresh().endPullupToRefresh(false);
            			// 启用上拉加载
            			mui(detailList.divPullRefresh).pullRefresh().enablePullupToRefresh();
            		}
            		
            		//$('.mui-pull-top-pocket').css('top', '20px');
            		
            		setTimeout(function() {
            			var height = 0;
            			height += $('.mui-scroll').height();
            			$(detailList.divPullRefresh).parent().height(height);
            			$(detailList.divPullRefresh).height(height);
            		}, 100);
            	}
        	},
            error: function(xhr, type){
            	mui.toast('系统错误，请联系管理员');
            }
        });
    }
};

//detailList.init();
$(function(){
	selectGoods();
	$('#morediv').click(function(){
		selectGoods();
	});
	function selectGoods(){
		var currentPageNo =  $('#contentData').find(".review").length;
		var goodsId = $('#goodsId').val();
    	var url = '/fegoods/selectEval';
    	var pageSize = 6;
        $.ajax({
        	data:{'currentPageNo':currentPageNo,'pageSize':pageSize,'goodsId':goodsId},
            type: 'POST',
            url: url,
            dataType: 'json',
            success: function(data){
            	if(data.isError==1){
            		mui.toast(data.msg);
            	}else{
            		
            		var rows = data.rows;
            		var tb = [];
            		if(rows && rows.length>0){
            			for(var i = 0; i < rows.length; i++){
            				var ob = rows[i];
            				tb[tb.length] = '<article class="review clearfix">';
            				tb[tb.length] = '<article class="pic pull-left">';
            				var userpic = '/upload/10.jpg';
            				if(ob.picUrl){
            					userpic = ob.userPicUrl;
            				}
            				tb[tb.length] = '	<figure>';
            				tb[tb.length] = '		<img src="'+userpic+'" alt="">';
            				tb[tb.length] = '		<figcaption>'+nullToStr(ob.userName)+'</figcaption>';
            				tb[tb.length] = '	</figure>';
            				tb[tb.length] = '</article>';
            				tb[tb.length] = '<article class="soldar pull-right">';
            				tb[tb.length] = '	 <section class="item clearfix">';
            				tb[tb.length] = '	 	<ul class="stars pull-left">';
            				var tscore = 0;
            				if(ob.evalTotalScore){
            					tscore = 1*ob.evalTotalScore;
            					var tscorestr = (tscore+'').split('.');
            					var onnum = 1*tscorestr[0];
            					var num = 5-onnum;
            					for (var j = 0; j < onnum; j++) {
            						tb[tb.length] = '<li class="on"></li>';
            					}
            					var half = tscorestr[1];
            					
            					if(half && half>0){
            						tb[tb.length] = '<li class="an"></li>';
            						num = num-1;
            					}
            					for (var j = 0; j < num; j++) {
            						tb[tb.length] = '<li></li>';
            					}
            				}else{
            					for (var j = 0; j < 5; j++) {
            						tb[tb.length] = '<li></li>';
            					}
            				}
            				tb[tb.length] = '	 	</ul>';
            				tb[tb.length] = '	 	<span class="tiem pull-right">'+nullToStr(ob.updateDate)+'</span>';
            				tb[tb.length] = '	 </section>';
            				tb[tb.length] = '	<section class="text">';
            				tb[tb.length] = nullToStr(ob.evalComment);
            				tb[tb.length] = '		<div class="imgs">';
            				tb[tb.length] = '			<figure>';
            				var plist = ob.plist;
            				if(plist && plist.length>0){
            					for (var j = 0; j < plist.length; j++) {
            						tb[tb.length] = '<img src="'+nullToStr(plist[j].picUrl)+'" alt="">';
            					}
            				}
            				tb[tb.length] = '	        </figure>';
            				tb[tb.length] = '		</div>';
            				tb[tb.length] = '	</section>';
            				
            				tb[tb.length] = '	<section class="woder">';
            				tb[tb.length] = '		<span>服务：  '+nullToStr(ob.serviceTotalScore)+'</span>';
            				tb[tb.length] = '		<span>环境： '+nullToStr(ob.environmentTotalScore)+'</span>';
            				tb[tb.length] = '		<span>口味：  '+nullToStr(ob.tasteTotalScore)+'</span>';
            				tb[tb.length] = '	</section>';
            				tb[tb.length] = '</article>';
            				tb[tb.length] = '</article>';
            			}
            		}
            		$('#contentData').append(tb.join(""));
            		var curtotal =  $('#contentData').find(".review").length;
            		// 商品推荐详细信息总数
            		var totalCnt = data.totalCnt;
            		// 若数据已加载完毕
            		if (!curtotal || (curtotal >= totalCnt)) {
            			$('#morediv').text('没有更多了');
            		}
            	}
        	},
            error: function(xhr, type){
            	mui.toast('系统错误，请联系管理员');
            }
        });
	}
});
