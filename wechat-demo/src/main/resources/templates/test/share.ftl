<html>
<head>
    <script src="http://res.wx.qq.com/open/js/jweixin-1.4.0.js"></script>
    <script src="http://code.jquery.com/jquery-latest.js"></script>
</head>
<body>

1213131231312312321
</body>
<script type="text/javascript">
    $(function(){
        HideOption.init({appId:'${signature.appId!}',
            timeStr:'${signature.timestamp!}',
            nonceStr:'${signature.nonceStr!}',
            signStr:'${signature.signature!}',
        });
    });

    var Browser = {
        android : navigator.userAgent.indexOf('Android') > -1 || navigator.userAgent.indexOf('Linux') > -1,
        iPhone : navigator.userAgent.indexOf('iPhone') > -1 || navigator.userAgent.indexOf('Mac') > -1,
        weixin : function(){
            var ua = window.navigator.userAgent.toLowerCase();
            if(ua.match(/MicroMessenger/i) == 'micromessenger'){
                return true;
            }else{
                return false;
            }
        }(),
        qq : navigator.userAgent.toLowerCase().indexOf('qqbrowser')> -1,
        safari:navigator.userAgent.toLowerCase().indexOf('safari')> -1//苹果原生浏览器
    };

    (function($,window,document,undefined){
        var pub = {};
        window.HideOption = pub;
        var appId;
        var timeStr;
        var nonceStr;
        var signStr;
        pub.init = function(config){
            appId = config['appId'];
            timeStr = config['timeStr'];
            nonceStr = config['nonceStr'];
            signStr = config['signStr'];
            if(Browser.weixin){
                var linkUrl = window.location.href.split('#')[0];
                function getQueryString(name) {//根据字段看网址是否拼接&字符串
                    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
                    var r = window.location.search.substr(1).match(reg);
                    if (r != null)
                        return unescape(r[2]);
                    return null;
                }
                var from = getQueryString('from');
                var appinstall = getQueryString('appinstall');
                var sec = getQueryString('sec');
                var timekey = getQueryString('timekey');

                if(from || appinstall || sec || timekey){//假如拼接上了
                    window.location.href = "/share"
                }

                wx.config({
                    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
                    appId: appId, // 必填，企业号的唯一标识，此处填写企业号corpid
                    timestamp: timeStr, // 必填，生成签名的时间戳
                    nonceStr: nonceStr, // 必填，生成签名的随机串
                    signature: signStr,// 必填，签名，见附录1
                    jsApiList: ['onMenuShareTimeline','onMenuShareAppMessage'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
                });
                wx.ready(function(){
                    // wx.hideMenuItems({
                    //     menuList: ['menuItem:favorite','menuItem:copyUrl','menuItem:share:QZone','menuItem:share:qq','menuItem:openWithSafari','menuItem:share:email','menuItem:share:facebook','menuItem:openWithQQBrowser'] // 要隐藏的菜单项，所有menu项见附录3
                    // });

                    wx.onMenuShareTimeline({
                        title: '课后网优惠码', // 分享标题
                        link: linkUrl, // 分享链接
                        imgUrl: 'http://wybcs.wezoz.com/kehou.png', // 分享图标
                        success: function () {
                        },
                        cancel: function () {
                            // 用户取消分享后执行的回调函数
                        }
                    });

                    wx.onMenuShareAppMessage({
                        title: '课后网优惠码', // 分享标题
                        desc: '把名师请回家    把未来握在手    网上课程更精彩', // 分享描述
                        link: linkUrl, // 分享链接
                        imgUrl: 'http://wybcs.wezoz.com/kehou.png', // 分享图标
                        type: '', // 分享类型,music、video或link，不填默认为link
                        dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
                        success: function () {
                            // 用户确认分享后执行的回调函数
                        },
                        cancel: function () {
                            // 用户取消分享后执行的回调函数
                        }
                    });
                });
            }
        }
    })(jQuery,window,document);
</script>
</html>
