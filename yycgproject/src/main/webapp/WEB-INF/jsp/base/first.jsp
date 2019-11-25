<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/base/tag.jsp" %>
<html>
<head>
    <title>药品采购平台</title>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Cache-Control" content="no-cache">
    <meta http-equiv="Expires" content="0">
    <meta name="GENERATOR" content="MSHTML 9.00.8112.16540">
    <link rel="stylesheet" type="text/css" href="${baseurl}js/easyui/styles/default.css">
    <%@ include file="/WEB-INF/jsp/base/common_css.jsp" %>
    <link rel="stylesheet" href="${baseurl}styles/first.css">
    <%@ include file="/WEB-INF/jsp/base/common_js.jsp" %>
    <style type="text/css">
        .log {
            color: #fff;
        }

        .log:hover {
            color: #fff;
        }

        .footer_first .footer_l {
            text-align: left;
            display: inline-block;
            float: left;
            margin-left: 5px;
        }

        .footer_first .footer_r {
            text-align: right;
            display: inline-block;
            float: right;
            margin-right: 20px;
        }
    </style>
    <script>
        window.onload = function () {
            startTime();

            function startTime() {
                var today = new Date();
                var year = today.getFullYear();
                var month = today.getMonth() + 1;
                var date = today.getDate();
                var day = today.getDay();
                var h = today.getHours();
                var m = today.getMinutes();
                var s = today.getSeconds();// 在小于10的数字前加一个‘0’
                m = checkTime(m);
                s = checkTime(s);
                day = getLocalDay(day);
                document.getElementById('time').innerHTML = year + "/" + month + "/" + date + "&nbsp;&nbsp;" + day + "&nbsp;" + h + ":" + m + ":" + s;
                setTimeout(function () {
                    startTime()
                }, 500);
            }

            function getLocalDay(d) {
                switch (d) {
                    case 0:
                        d = "星期日";
                        break;
                    case 1:
                        d = "星期一";
                        break;
                    case 2:
                        d = "星期二";
                        break;
                    case 3:
                        d = "星期三";
                        break;
                    case 4:
                        d = "星期四";
                        break;
                    case 5:
                        d = "星期五";
                        break;
                    default:
                        d = "星期六";
                }
                return d;
            }

            function checkTime(i) {
                if (i < 10) {
                    i = "0" + i;
                }
                return i;
            }
        }
    </script>
    <script type="text/javascript">
        var tabOnSelect = function (title) {
            //根据标题获取tab对象
            var currTab = $('#tabs').tabs('getTab', title);
            var iframe = $(currTab.panel('options').content);//获取标签的内容
            var src = iframe.attr('src');//获取iframe的src
            //当重新选中tab时将ifram的内容重新加载一遍，目的是获取当前页面的最新内容
            if (src)
                $('#tabs').tabs('update', { // 更新tab内容
                    tab: currTab,
                    options: {
                        content: createFrame(src)//实现刷新ifram内容
                    }
                });
        };
        var _menus;
        $(function () {//预加载方法
            //通过ajax请求菜单
            $.ajax({
                url: '${baseurl}menu.json',
                type: 'POST',
                dataType: 'json',
                success: function (data) {
                    _menus = data;
                    initMenu(_menus);//解析json数据，将菜单生成
                },
                error: function () {
                    alert('菜单加载异常!');
                }
            });

            //tabClose();
            //tabCloseEven();
            //加载欢迎页面
            $('#tabs').tabs('add', {
                title: '欢迎使用',
                content: createFrame('${baseurl}welcome.action')
            }).tabs({
                //当重新选中tab时将ifram的内容重新加载一遍
                onSelect: tabOnSelect
            });

            //修改密码
            $('#modifypwd').click(menuclick);

        });

        //退出系统方法
        function logout() {
            _confirm('您确定要退出本系统吗?', null,
                function () {
                    location.href = '${baseurl}logout.action';
                }
            )
        }

        //帮助
        function showhelp() {
            window.open('${baseurl}help/help.html', '帮助文档');
        }
    </script>
</head>

<body style="overflow-y: hidden;" class="easyui-layout" scroll="no">
<div class="header_first" border="false" split="false" region="north" style="width: 610px; height: 30px">
		<span style="padding-right: 20px; float: right;" class="head">
			欢迎当前用户：${activeUser.username}&nbsp;&nbsp;
			<a href=javascript:showhelp()>使用帮助</a>
			&nbsp;&nbsp;
			<a title='修改密码' ref='modifypwd' href="#" rel='${baseurl}user/updatepwd.action' icon='icon-null'
               id="modifypwd">修改密码</a>
			&nbsp;&nbsp;
			<a id="loginOut" href=javascript:logout()>退出系统</a>
		</span>

    <span style="padding-left: 10px; font-size: 16px;">
            <a href="${baseurl}" class="log">
    <img align="absmiddle" src="images/blocks.gif" width="20" height="20">
			医药集中采购系统
            </a>
    </span>
    <span style="padding-left: 15px;" id="News"></span>
</div>

<div style="background: rgb(210, 224, 242); height: 30px;" split="false"
     region="south">
    <div class="footer footer_first">
        <span class="footer_l">系统版本号：${version_number}&nbsp;&nbsp;&nbsp;发布日期：${version_date}</span>
        <span class="footer_r">系统时间：
            <span id="time"></span>
        </span>
    </div>
</div>

<div style="width: 180px;" id="west" title="导航菜单" split="false"
     region="west" hide="true">

    <div id="nav" class="easyui-accordion" border="false" fit="true">
    </div>
</div>

<div style="background: rgb(238, 238, 238); overflow-y: hidden;" id="mainPanle" region="center">
    <div id="tabs" class="easyui-tabs" border="false" fit="true"></div>
</div>
</body>
</html>
