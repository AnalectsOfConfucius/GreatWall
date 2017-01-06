//调用微信JS api 支付
function jsAPICall(JSAPIParameters) {
	// alert('请求服务端，生成支付订单');
	// 请求服务端，生成支付订单
	WeixinJSBridge.invoke('getBrandWCPayRequest', {
		"appId" : JSAPIParameters.appId, //公众号名称，由商户传入
		"timeStamp" : JSAPIParameters.timeStamp, //时间戳，自 1970 年以来的秒数
		"nonceStr" : JSAPIParameters.nonceStr, //随机串
		"package" : JSAPIParameters.packages,
		"signType" : JSAPIParameters.signType, //微信签名方式:
		"paySign" : JSAPIParameters.paySign
	//微信签名
	}, function(res) {
		WeixinJSBridge.log(res.err_msg);
		// 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg 将在用户支付成功后返回 ok，但并不保证它绝对可靠。
		if (res.err_msg == "get_brand_wcpay_request:ok") {
			var orderId = $('#hideOrderId').val();
			if (orderId == "") {
				mui.toast("操作异常，请稍后在我的订单查看支付是否成功！");
				window.location.href = "/order";
			} else {
				$.ajax({
					cache : true,
					type : "POST",
					url : "/order/query",
					data : {
						orderId : orderId
					},
					async : true,
					error : function(request) {
						mui.toast("网络连接超时，请稍后在我的订单查看支付是否成功！");
						window.location.href = "/order";
					},
					success : function(data) {
						if (data) {
							mui.toast(data.msg);
							//window.location.href = "/order";
							$('.success-msg .msgcontent').text(data.msg);
							success();
						} else {
							mui.toast("操作异常，请稍后在我的订单查看支付是否成功！");
							window.location.href = "/order";
						}
					}
				});
			}
		} else {
			mui.toast("操作异常，请稍后在我的订单查看支付是否成功！");
			window.location.href = "/order";
		}
	});
}
//支付
function payOrder(){
	var orderId = $('#hideOrderId').val();
	if (orderId == "") {
		mui.toast("操作异常，请刷新页面后重试！");
	} else {
		$.ajax({
			cache : true,
			type : "POST",
			url : "/order/pay",
			data : {
				orderId : orderId
			},
			async : true,
			error : function(request) {
				mui.toast("网络连接超时，请重试！");
			},
			success : function(data) {
				if (data) {
					if ('0' == data.isError) {
						var JSAPIParameters = data.JSAPIParameters;
						/* 3、调用微信支付JSAPI */
						if (typeof WeixinJSBridge == "undefined") {
							if (document.addEventListener) {
								document.addEventListener('WeixinJSBridgeReady',jsAPICall, false);
							} else if (document.attachEvent) {
								document.attachEvent('WeixinJSBridgeReady',jsAPICall);
								document.attachEvent('onWeixinJSBridgeReady',jsAPICall);
							}
						} else {
							jsAPICall(JSAPIParameters);
						}
					} else {
						mui.toast(data.msg);
						location.href = "/order";
					}

				} else {
					mui.toast("操作异常，请重试！");
				}
			}
		});
	}
}
//订单详情
function orderDetail(){
	var orderId = $('#hideOrderId').val();
	window.location.href = '/order/detail?id='+orderId;
}