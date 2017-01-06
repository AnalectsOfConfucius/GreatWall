//banner
var swiper = new Swiper('.swiper-cat-index-ad', {
    pagination : '.swiper-pagination',
    paginationClickable : true,
    // Disable preloading of all images
    preloadImages : false,
    // Enable lazy loading
    lazyLoading : true,
    loop : true,
    autoplay : 2500,
    autoplayDisableOnInteraction : false
});
// swiper-container-item
var Swiper2 = new Swiper('.swiper-container-item', {
    pagination : '.swiper-pagination-02',
    paginationClickable : true,
    loop : true,

});
// Tablist
var tabsSwiper1 = new Swiper('#tabs-container-1', {
    speed : 500,
    onSlideChangeStart : function() {
        $("#tabs-1 .active").removeClass('active');
        $("#tabs-1 a").eq(tabsSwiper1.activeIndex).addClass('active')
    }
});
$("#tabs-1 a").on('touchstart mousedown', function(e) {
    e.preventDefault();
    $("#tabs-1 .active").removeClass('active');
    $(this).addClass('active');
    tabsSwiper1.slideTo($(this).index())
});
var tabsSwiper2 = new Swiper('#tabs-container-2', {
    speed : 500,
    onSlideChangeStart : function() {
        $("#tabs-2 .active").removeClass('active');
        $("#tabs-2 a").eq(tabsSwiper2.activeIndex).addClass('active')
    }
});
$("#tabs-2 a").on('touchstart mousedown', function(e) {
    e.preventDefault();
    $("#tabs-2 .active").removeClass('active');
    $(this).addClass('active');
    tabsSwiper2.slideTo($(this).index())
});
$(".tabs a").click(function(e) {
    e.preventDefault();
});

// 星星评论
$(function() {
    $(".evstar li").click(function() {
        var pn = $(".evstar").index($(this).parent());
        var n = $(".evstar").eq(pn).find("li").index(this);
        var panel = $(this).parent().find("li");
        panel.attr("class", "");
        for (var i = n; i >= 0; i--) {
            panel.eq(i).attr("class", "on");
        }
        $(this).parents('.evstar').siblings('.evstar-count').text((n + 1) + '星');
        $(this).parent().parent().find("input").val(n + 1);
        countStar();
    });
    function countStar() {
        var panel = $(".countStar li");
        panel.attr("class", "");
        var evnum = $(".evstar").length;
        var len = $(".evstar li.on").length;
        var cont = parseInt(len / evnum);
        var hcont = parseInt(len % evnum);
        for (var i = 0; i < cont; i++) {
            panel.eq(i).attr("class", "on");
        }
        var onnum = $(".countStar li.on").length;
        if (hcont > 1) {
            panel.eq(onnum).attr("class", "an");
        }
        $('.countStar').siblings('.evstar-count').text(cont + '星');
        var total;
        if (hcont > 1) {
            total = cont + 0.5;
        } else {
            total = cont;
        }
        $("#evalTotalScore").val(total);
    }
});

// 字数限制
var len = 80;
function checkWord(_this) {
    var str = $(_this).val();
    var myLen = getStrleng(str);

    if (myLen > len * 2) {
        str = str.substring(0, i + 1);
    } else {
        $(_this).siblings('.tex').find('.word-check').text(Math.floor((len * 2 - myLen) / 2));
    }
}
function getStrleng(str) {
    myLen = 0;
    i = 0;
    for (; (i < str.length) && (myLen <= len * 2); i++) {
        if (str.charCodeAt(i) > 0 && str.charCodeAt(i) < 128)
            myLen++;
        else
            myLen += 2;
    }
    return myLen;
}

