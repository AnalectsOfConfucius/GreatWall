//内容省略
function substr(w,cls){
	var num = w/8;
	var len = cls.length;
	for (var i = 0; i < len; i++) {
		var text = cls.eq(i);
		var str = text.html();
		if(str.length > num ){
			text.html(str.substring(0,num )+"...");
		}
	}
}
/**
 * 展开，收起
 * @param ob
 * @param panel- div的class
 * @param n- div的位置
 */
function updown(ob,panel,n){
	var txt = $(ob).find("span").text();
	if(txt.indexOf("展开")>=0){
		$(panel).eq(n).css("height","auto");
		$(ob).find("span").text("收起");
		$(ob).find("i").attr("class","ico i-jts");
	}else{
		$(panel).eq(n).css("height","32px");
		$(ob).find("span").text("展开");
		$(ob).find("i").attr("class","ico i-jtx");
	}
}
/**
 * 企业信息，展开收起
 * @param ob
 * @param panel- div的class
 * @param n- div的位置
 */
function updown_n(ob,panel,n){
	var ty = $(ob).attr("data-type");
	if(ty==1){
		$(panel).eq(n).css("height","auto");
		$(ob).find("i").attr("class","ico i-jts floatright");
		$(ob).attr("data-type","2");
	}else{
		$(panel).eq(n).css("height","0px");
		$(ob).find("i").attr("class","ico i-jtx floatright");
		$(ob).attr("data-type","1");
	}
}
/**
 * 
 * @param ob-this
 * @param n-位置
 */
function navtab(ob,n){
	$(ob).parent().find("li").attr("class","");
	$(ob).parent().parent().find("a").attr("class","");
	$(ob).attr("class","current");
	$(".content").hide();
	$(".content").eq(n).show();
}
/**
 * 信息提示框
 * @param msg-信息
 * @param w-长度
 * @param h-宽度
 */
function showwin(msg,w,h){
	var w = w ? w : 200;
	var h = h ? h : 150;
	var t = -h/2;
	var l = -w/2;
	var sb = [];
	
	sb[sb.length] = '<div class="tcbg" onclick="hidewin()"></div>';
	sb[sb.length] = '<div class="tcxx ft14" style="width:'+w+'px;height:'+h+'px;margin:'+t+'px '+l+'px;z-index:999;">';
	sb[sb.length] = '<div class="tt" align="center">温馨提示</div>';
	sb[sb.length] = '<div class="cont">';
	sb[sb.length] = '<div class="floatleft mt10 mr10"><img alt="" src="images/suc.png" width="25"></div>';
	sb[sb.length] = '<div class="floatleft" style="width:75%;">'+msg+'</div>';
	sb[sb.length] = '<div class="clearboth"></div>';
	sb[sb.length] = '</div>';
	sb[sb.length] = '<div class="bt" align="center"><input type="button" value="知道了" onclick="javascript:hidewin();"></div>';
	sb[sb.length] = '</div>';
	var content = sb.join("");
	var ob = document.getElementById("mesWindow");
	if(ob){
		$(ob).html(content);
		$(ob).show();
	}else{
		createMessageWindow(content);
	}
	
}
/**
 * 信息提示框
 * @param msg-信息
 * @param w-长度
 * @param h-宽度
 */
function showloginwin(w,h){
	var w = w ? w : 280;
	var h = h ? h : 260;
	var t = -h/2;
	var l = -w/2;
	var sb = [];
	
	sb[sb.length] = '<div class="tcbg" onclick="hidewin()"></div>';
	sb[sb.length] = '<div class="tcxx ft14" style="width:'+w+'px;height:'+h+'px;margin:'+t+'px '+l+'px;border-radius:10px;">';
	sb[sb.length] = '<div class="tt" align="center" style="border-radius:10px 10px 0px 0px;">温馨提示</div>';
	sb[sb.length] = '<div class="mt10" align="center">您还未登录，请输入您手机号和用户名</div>';
	sb[sb.length] = '<div class="cont" style="width: 90%;margin: 10px 14px;">';
	sb[sb.length] = '<div class="mt10"><input style="width:100%;height: 33px;border:1px solid #e0e0e0;" placeholder="请输入手机号"/></div>';
	sb[sb.length] = '<div class="mt10"><input style="width:156px;;height: 33px;border:1px solid #e0e0e0;border-right:none;" placeholder="请输入验证码"/><input type="button" value="获得验证码" style="background:#fafafa;color:#38cf88;border-radius:0px;border-color:#e0e0e0;"/></div>';
	sb[sb.length] = '<div class="mt10"><input style="width:100%;height: 33px;border:1px solid #e0e0e0;" placeholder="请输入用户名"/></div>';
	sb[sb.length] = '<div class="mt10 botbtn" align="center"><input type="button" value="点击确定" style="width:100%;" onclick="javascript:hidewin();"></div>';
	sb[sb.length] = '</div>';
	sb[sb.length] = '</div>';
	var content = sb.join("");
	var ob = document.getElementById("mesWindow");
	if(ob){
		$(ob).html(content);
		$(ob).show();
	}else{
		createMessageWindow(content);
	}
	
}
function createMessageWindow(content, divId) {
	divId = divId ? divId : "mesWindow";
	var mesW = document.createElement("div");
	mesW.id = divId;
	mesW.className = divId;

	var sb = [];
	sb[sb.length] = '<div id="mesWindowContent">';
	sb[sb.length] = content;
	sb[sb.length] = '</div>';
	mesW.innerHTML = sb.join("");

//	sb = [];
//	sb[sb.length] = 'left:' + left + 'px;';
//	sb[sb.length] = 'top:' + top + 'px;';
//	sb[sb.length] = 'z-index:126;position: fixed;width:' + wWidth + 'px;';
//	mesW.style.cssText = sb.join("");
	document.body.appendChild(mesW);
	//$("#mesWindowContent").draggable();
}
function hidewin(){
	$("#mesWindow").hide();
}
function bodyScroll(){
	//debugger
	var of_y = $('body').css('overflow-y');
	if(of_y=='hidden'){
		$('body').css('overflow-y','auto');
	}else{
		$('body').css('overflow-y','hidden');
	}
}
function disPlay(id){
	bodyScroll();
	$('.bg').hide();
	$('#'+id).hide(100);
};
function opendiv(divname){
	bodyScroll();
	$('#'+divname).toggle(100);
	$('.bg').toggle();
	$('.bg').css('bottom','60px');
	return false;
}
/**
 * 跳转
 * @param url
 */
function goToUrl(url){
	window.location.href = url;
}
/**
 * 上传图片
 */
function loadimg(){
	var n = $("#preview").find(".loadimgdiv img").length;
	$(".upload").eq(n).trigger("click");
}
/**
 * 上传图片
 */
function previewImage(file,num) {
	var MAXWIDTH = 55;
	var MAXHEIGHT = 50;
	var div = document.getElementById('preview');
	var n = $("#preview").find(".loadimgdiv img").length;
	if (n >= num) {
		return;
	}
	if (file.files && file.files[0]) {
		
		$("#preview").find(".addimg").before('<div class="loadimgdiv"><img ><br><a onclick="delimg(this)">删除</a></div>');
		var img = $(".loadimgdiv").eq(n).find("img");
		
		//var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);
		// img.width = rect.width;
		// img.height = rect.height;
		// img.style.marginLeft = rect.left+'px';
		// img.style.marginTop = rect.top+'px';
		img.width(55);
		img.height(50);
		var reader = new FileReader();
		reader.onload = function(evt) {
			img.attr("src",evt.target.result);
		}
		reader.readAsDataURL(file.files[0]);
		$(file).after('<input type="file" onchange="previewImage(this,5)" class="upload hiden"/>');
	} else {
		var sFilter = 'filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src="';
		file.select();
		var src = document.selection.createRange().text;
		div.innerHTML = '<img id="imghead">';
		var img = document.getElementById('imghead');
		img.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src = src;
		var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth,
				img.offsetHeight);
		status = ('rect:' + rect.top + ',' + rect.left + ',' + rect.width + ',' + rect.height);
		div.innerHTML = "<div id='divhead' style='width:" + rect.width
				+ "px;height:" + rect.height + "px;margin-top:" + rect.top
				+ "px;margin-left:" + rect.left + "px;" + sFilter + src
				+ "\"'></div>";
	}
}
/**
 * 图片大小
 */
function clacImgZoomParam( maxWidth, maxHeight, width, height ){
    var param = {top:0, left:0, width:width, height:height};
    if( width>maxWidth || height>maxHeight )
    {
        rateWidth = width / maxWidth;
        rateHeight = height / maxHeight;
        if( rateWidth > rateHeight )
        {
            param.width =  maxWidth;
            param.height = Math.round(height / rateWidth);
        }else
        {
            param.width = Math.round(width / rateHeight);
            param.height = maxHeight;
        }
    }
// 	param.width = 55;
//  param.height = 50;
    param.left = Math.round((maxWidth - param.width) / 2);
    param.top = Math.round((maxHeight - param.height) / 2);
    return param;
}
/**
 * 删除图片
 */
function delimg(ob){
	var n = $("#preview .loadimgdiv").index($(ob).parent());
	$(ob).parent().remove();
	$(".upload").eq(n).remove();
	var len = $(".upload").length;
	if(len<=0){
		$("#preview").parent().append('<input type="file" onchange="previewImage(this,5)" class="upload hiden"/>');
	}
}
/**
 * 图片预览
 * @param imgob
 */
function viewimg(src){
	var ob = document.getElementById("imgWin");
	if(ob){
		$(ob).find("img").attr("src",src);
		$(ob).show();
	}else{
		var divId = "imgWin";
		var tb = []
		tb[tb.length] = '<div class="tcbg" onclick="javascript:$(\'#imgWin\').hide();"></div>';
		tb[tb.length] = '<div class="tcxx" style="">';
		tb[tb.length] = '<img alt="" src="'+src+'" width="100%">';
		tb[tb.length] = '</div>';
		var content = tb.join("");
		var mesW = document.createElement("div");
		mesW.id = divId;
		mesW.className = divId;
		mesW.innerHTML = content;
		document.body.appendChild(mesW);
	}
	var maxw = $(window).width()*0.9;
	var maxh = $(window).height()*0.9;
	
	var img = new Image();
	img.src = $("#imgWin").find("img").attr("src");
	var w = img.width;
	var h = img.height;
	var rect = clacImgZoomParam(maxw,maxh,w,h);
	var timg = $("#imgWin").find("img");
	timg.width(rect.width);
	timg.height(rect.height);
	var t = -(rect.height/2);
	var l = -(rect.width/2);
	$("#imgWin").find(".tcxx").css({'width':rect.width,'height':rect.height,'margin-left':l,'margin-top':t});
}
/**
 * JSON对象转String
 * 
 * @param o
 * @returns {String}
 */
function JsonToStr(obj) {
	if (obj == null) {
		return '""';
	}
	switch (typeof (obj)) {
		default:
		case 'number':
		case 'string':
			return '"' + obj + '"';
		case 'object': {
			if (obj instanceof Array) {
				var strArr = [];
				var len = obj.length;
				for ( var i = 0; i < len; i++) {
					strArr.push(JsonToStr(obj[i]));
				}
				return '[' + strArr.join(',') + ']';
			} else {
				var arr = [];
				for ( var i in obj) {
					arr.push('"' + i + '":' + JsonToStr(obj[i]));
				}
				return "{" + arr.join(',') + "}";
			}
		}
	}
	return '""';
}
/**
 * null,undefind转""
 * @param v
 * @returns {String}
 */
function nullToStr(v){
	if(!v && v!=0){
		v = "";
	}
	return v;
}
//整数的正则表达式
function isInt(intValue){
  	var intPattern=/^0$|^[1-9]\d*$/; //整数的正则表达式 
 	result=intPattern.test(intValue); 
  	return result; 
}
/** 
 * 验证电话号码 
 * @param phoneValue 要验证的电话号码 
 * @returns 匹配返回true 不匹配返回false 
 */
function validatePhone(phoneValue) {
	 var reg = /^1[3|4|5|8][0-9]\d{4,8}$/;
	 return reg.test(phoneValue);
}
/**
 * 根据给的日期获得周几
 * @param indate
 */
function getWeek(date){
	var week = new Date(date).getDay();
  	if(week==1){
  		week = "一";
  	}else if(week==2){
  		week = "二";
  	}else if(week==3){
  		week = "三";
  	}else if(week==4){
  		week = "四";
  	}else if(week==5){
  		week = "五";
  	}else if(week==6){
  		week = "六";
  	}else if(week==7){
  		week = "日";
  	}
  	return week;
}