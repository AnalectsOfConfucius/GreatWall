var wxm=wxm||{};wxm.namespace=function(name){var domains=name.split(".");var cur_domain=window;for(var i=0;i<domains.length;i++){var domain=domains[i];if(typeof(cur_domain[domain])=="undefined"){cur_domain[domain]={}}cur_domain=cur_domain[domain]}return cur_domain};wxm.namespace("wxm.util");wxm.util.FormatString=function(format){var args=Array.prototype.slice.call(arguments,1);return format.replace(/\{(\d+)\}/g,function(m,i){return args[i]})};wxm.util.hasText=function(string){if(string!=null&&string!=""){return true}return false};wxm.dialog=function(option){var $body=$(window.document.body);var $background=$('<div class="wxm-maskBackground"></div>');var $box=$('<div class="wxm-dialog-box"></div>');var $header=$('<div class="wxm-dialog-header"></div>');var $headerIcon=$(wxm.util.FormatString('<i class="{0}"></i>',option&&(option.type?wxm.dialog.ICON[option.type]:wxm.dialog.ICON.success)||wxm.dialog.ICON.success));var $alert=$(wxm.util.FormatString('<div class="alert">{0}</div>',option&&(option.message||"操作成功")||"操作成功"));var $footer=$('<div class="wxm-dialog-fotter"></div>');var $correctBtn=$('<span class="btn btn-default">确定</span>');var $cancelBtn=$('<span class="btn btn-default">取消</span>');var windowHeight=$(document).height();var windowWidth=$(window).width();window.scroll(0,(document.body.scrollHeight-document.body.offsetHeight)/2);$body.append($background);$body.append($box);$box.append($header);$box.append($alert);$box.append($footer);$header.append($headerIcon);$header.append("提示信息");$footer.append($correctBtn);if(option&&option.type==wxm.dialog.TYPE.confirm){$footer.append($cancelBtn)}$background.css("background-color","#000");$background.css("margin-top","2px");$background.height(windowHeight-4);$box.css("top",windowHeight/2-$box.height()+"px");$box.css("left",windowWidth/2-$box.width()/2+"px");var alertHeight=$box.height()-$header.outerHeight()-$footer.outerHeight()-20;$alert.height(alertHeight);$alert.css("line-height",alertHeight+"px");if(option&&wxm.util.hasText(option.style)&&wxm.util.hasText(option.style.alertFontSize)){$alert.css("font-size",option.style.alertFontSize+"px")}$correctBtn.on("click",function(){wxm.dialog.close($background,$box);if(option&&wxm.util.hasText(option.callback)&&wxm.util.hasText(option.callback.correct)){option.callback.correct()}});$cancelBtn.on("click",function(){wxm.dialog.close($background,$box);if(option&&wxm.util.hasText(option.callback)&&wxm.util.hasText(option.callback.cancel)){option.callback.cancel()}})};wxm.dialog.TYPE={success:"success",error:"error",info:"info",danger:"danger",confirm:"confirm"};wxm.dialog.ICON={success:"fa fa-check-circle",error:"fa fa-times-circle",info:"fa fa-info-circle",danger:"fa fa-exclamation-triangle",confirm:"fa fa-question-circle"};wxm.dialog.ALERT={success:"success",error:"danger",info:"info",danger:"warning",confirm:"warning"};wxm.dialog.close=function($background,$box){$background.remove();$box.remove()};wxm.maskLayer=function(){var $body=$(window.document.body);var $background=$('<div class="wxm-maskBackground wxm-mask-layer-ctn"></div>');var $box=$('<div class="wxm-mask-layer"><i class="fa fa-spinner fa-pulse"></i> 请稍后...</div>');var windowHeight=$(document).height();var windowWidth=$(window).width();$body.append($background);$body.append($box);$background.css("background-color","#000");$background.css("margin-top","2px");$background.height(windowHeight-4);$box.css("top",$(window).height()/2-$box.height()+"px");$box.css("left",windowWidth/2-$box.width()/2+"px")};wxm.maskLayer.close=function(){$(".wxm-maskBackground.wxm-mask-layer-ctn").remove();$(".wxm-mask-layer").remove()};