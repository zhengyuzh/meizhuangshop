$(function(){
    $(".sideMenu .nLi h3").click(function(){
        // 切换箭头方向
        const className = $(this).find(".arrow").attr("class");
        if(className) {
            if(className.indexOf("fa-angle-up") > 0) {
                $(this).find(".arrow").removeClass("fa-angle-up").addClass("fa-angle-down");
            } else {
                $(this).find(".arrow").removeClass("fa-angle-down").addClass("fa-angle-up");
            }
        }

        if($(this).parent(".nLi").hasClass("on")){
            $(this).next(".sub").slideUp(300,function(){
                $(this).parent(".nLi").removeClass("on")
            });
        }else{
            $(this).next(".sub").slideDown(300,function(){
                $(this).parent(".nLi").addClass("on")
            });
        }
    });

    // 侧边菜单高度设置
    const menuHeight = $(".sideMenu li").length * 50 + $(".sideMenu h3").length * 50 + 200;
    const pageHeight = $("#main").height();
    const bodyHeight = document.getElementsByTagName("body")[0].clientHeight;
    const winHeight = $(window).height();

    let height = menuHeight > pageHeight ? menuHeight : pageHeight;
    height = height > bodyHeight ? height : bodyHeight;
    height = height > winHeight ? height : winHeight;

    $(".sideMenu").css("height", height + 'px');
});
