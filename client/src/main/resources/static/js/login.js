//弹出自定义提示窗口
var showAlert= function(msg,url,element){
    //遮罩
    if ( $("#cover_alert").length > 0) {
        //遮罩存在，直接调起
        $("#cover_alert").show();
    }else{
        var coverHtml ='<div class="cover"  id="cover_alert"  onclick="closeAlert()" style="z-index: 101;"></div>';
        $("body").append(coverHtml);
        $("#cover_alert").show();
    }
    //弹框
    if ( $("#pop_box_alert").length > 0 &&  $("#pop_box_msg").length > 0 ) {
        //存在，则只修改文字
        $('#pop_box_msg').html(msg);
        $("#pop_box_msg").show();
    } else{
        var messageHtml = '<div class="pop_box" id="pop_box_alert" onclick="closeAlert()" style="z-index: 102;">'+
            '<div class="pop_img">'+
            '<img src="/assets/images/front/images/pop_top.png">'+
            '</div>'+
            '<div class="pop_center">'+
            '<p id="pop_box_msg">'+msg+'</p>'+
            '</div>'+
            '<div class="pop_img">'+
            '<img src="/assets/images/front/images/pop_bottom.png">'+
            '</div>'+
            '</div>';
        $("body").append(messageHtml);
    }
    $("#pop_box_alert").show();
    var sVal = arguments[2] ? arguments[2] : 0;
    if(url){
        if(url=='share'){
            setTimeout("showTip()", 2000 )
        }
        if(url=='list'){
            var rit ='/meeting/list/'+sVal;
            setTimeout(function(){redirectList(rit)} , 2000 );
        }
    }else{
        setTimeout("$('#pop_box_alert').hide();$('#cover_alert').hide();" , 2000);
    }
}
//重定义alert
//那么重点来了！！！！！！！！！！！！！！！
window.alert=showAlert;
//那么重点来了！！！！！！！！！！！！！！！
//点击遮罩关闭
function closeAlert(){
    $("#pop_box_alert").hide();
    $("#cover_alert").hide();
}

//跳转列表页
function redirectList(url){
    $('#pop_box_alert').hide();
    window.location.href=url;
}

//显示遮罩碳层
function showTip(element){
    $("#pop_box_alert").hide();
    $("#cover_alert").hide();
    $('#shareZindex').show();
}