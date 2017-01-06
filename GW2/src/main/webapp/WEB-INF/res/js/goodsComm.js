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
    	var currentPageNo = $(detailList.ulPullRefresh).find(".item").length;
    	detailList.selectGoods(currentPageNo,false);
    },
    /**
     * 加载商品列表
     * 
     * @param result
     * @param isRemoveAll
     */
    selectGoods : function(currentPageNo,isRemoveAll) {
    	var typeCode = $('#typeCode').val();
    	var url = '/fegoods/query';
    	if(typeCode==5){//酒店客房
    		url = '/selectGuestRoom';
    	}
    	var pageSize = 6;
        $.ajax({
        	data:{'currentPageNo':currentPageNo,'pageSize':pageSize,'typeCode':typeCode},
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
                    var tb = '';
            		var rows = data.rows;
            		
            		if(rows && rows.length>0){
        				if(typeCode==5){//客房
        					tb = roomHtml(rows);
        				}else{//年卡，特色商品
        					tb = initHtml(rows);
        				}
            		}
            		$(detailList.ulPullRefresh).append(tb);
            		// 当前商品推荐详细信息总数
            		var curtotal =  $(detailList.ulPullRefresh).find(".item").length;
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
            		
            		$('.mui-pull-top-pocket').css('top', '20px');
            		// 绑定详细信息单击事件
                    mui(detailList.ulPullRefresh).off('tap').on('tap', 'a', function() {
                    	window.location.href = $(this).attr('data-url');
                    });
                    
                    setTimeout(function() {
                        var height = 0;
                        //height += $(detailList.divPullRefresh).find('.mui-scroll').height();
//                        $(detailList.ulPullRefresh).find('.item').each(function() {
//                            height += $(this).outerHeight();
//                        });
                        //$(detailList.ulPullRefresh).height(height);
                    }, 100);
            	}
            },
            error: function(xhr, type){
            	mui.toast('系统错误，请联系管理员');
            }
        });
    }
};

detailList.init();

//年卡，特色商品
function initHtml(rows){
	var tb = [];
	for(var i = 0; i < rows.length; i++){
		var ob = rows[i];
		tb[tb.length] = '<div class="item">';
		tb[tb.length] = '<figure>';
		var pic = '/upload/10.jpg';
		if(ob.picUrl){
			pic = ob.picUrl;
		}
		tb[tb.length] = '  <a data-url="/fegoods/toDetail?goodsId='+ob.id+'" href="javascript:void(0);"><img src="'+pic+'" alt="" style="width:100%;max-height:100px;"></a>';
		tb[tb.length] = '</figure>';
		tb[tb.length] = '<aside>';
		tb[tb.length] = '  <h3><a href="javascript:void(0);" data-url="/fegoods/toDetail?goodsId='+ob.id+'">'+nullToStr(ob.goodsName)+'</a></h3>';
		tb[tb.length] = '  <p>'+nullToStr(ob.goodsName)+'</p>';
		tb[tb.length] = '  <span class="price">￥'+nullToStr(ob.sellPrice)+'</span>';
		tb[tb.length] = '</aside>';
		tb[tb.length] = '</div>';
	}
	return tb.join("");
}
//客房
function roomHtml(rows){
	var tb = [];
	for(var i = 0; i < rows.length; i++){
		var ob = rows[i];
		var pic = '/upload/6.jpg';
		if(ob.picUrl){
			pic = ob.picUrl;
		}
		tb[tb.length] = '<div class="item">';
		tb[tb.length] = '<figure>';
		tb[tb.length] = '  <a href="javascript:void(0);" data-url="/toRoomDetail?roomId='+ob.id+'"><img src="'+pic+'" alt="" style="width:100%;max-height:100px;"></a>';
		tb[tb.length] = '</figure>';
		tb[tb.length] = '<aside>';
		tb[tb.length] = '  <h3><a href="javascript:void(0);" data-url="/toRoomDetail?roomId='+ob.id+'">'+nullToStr(ob.guestRoomName)+'</a></h3>';
		tb[tb.length] = '  <p>'+nullToStr(ob.guestRoomBrief)+'</p>';
		tb[tb.length] = '  <span class="price">￥'+nullToStr(ob.sellPrice)+'/晚</span>';
		tb[tb.length] = '</aside>';
		tb[tb.length] = '</div>';
	}
	return tb.join("");
}