<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/base/tag.jsp" %>
<html>
<head>
    <title><fmt:message key="title" bundle="${messagesBundle}"/></title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">

    <link rel="stylesheet" type="text/css" href="${baseurl}styles/style.css">
    <link rel="stylesheet" type="text/css" href="${baseurl}styles/login.css">
    <link rel="stylesheet" type="text/css" href="${baseurl}js/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${baseurl}js/easyui/themes/icon.css">

    <style type="text/css">
        .btnalink {
            display: block;
            width: 80px;
            height: 29px;
            float: left;
            margin: 12px 28px 12px auto;
            line-height: 30px;
            background: url('${baseurl}images/login/btnbg.jpg') no-repeat;
            font-size: 14px;
            color: #fff;
            font-weight: bold;
            text-decoration: none;
        }

        .btnalink:hover {
            cursor: pointer;
        }
    </style>
    <%@ include file="/WEB-INF/jsp/base/common_js.jsp" %>

    <script type="text/javascript">
        $(document).ready(function () {
            //*****************表单校验******************
            $.formValidator.initConfig({
                formID: "loginform",
                mode: 'AlertTip',
                onError: function (msg) {
                    alert(msg);
                },
                onAlert: function (msg) {
                    alert(msg);
                }
            });
            $("#userid").formValidator({
                onShow: "",
                onCorrect: "&nbsp;"
            }).inputValidator({
                min: 1,
                onError: "请输入用户名"
            });
            $("#password").formValidator({
                onShow: "",
                onCorrect: "&nbsp;"
            }).inputValidator({
                min: 1,
                onError: "请输入密码"
            });
            $("#randomcode").formValidator({
                onShow: "",
                onCorrect: "&nbsp;"
            }).inputValidator({
                min: 1,
                onError: "请输入验证码"
            });
            //*****************表单校验******************
        });

        //校验表单输入
        function checkinput() {
            //return $('#loginform').form('validate');
            return $.formValidator.pageIsValid();
        }

        //登录提示方法
        function loginsubmit() {
            if (checkinput()) {//校验表单，如果校验通过则执行jquerySubByFId
                //ajax form提交
                jquerySubByFId('loginform', login_commit_callback, null, 'json');
            }

        }

        //登录提示回调方法
        function login_commit_callback(data) {
            message_alert(data);
            var type = data.resultInfo.type;
            if (1 === type) {//如果登录成功，这里1秒后执行跳转到首页
                setTimeout(tofirst(), 1000);
            } else {
                //登录错误，重新刷新验证码
                randomcode_refresh();
            }

        }

        //刷新验证码
        //实现思路，重新给图片的src赋值，后边加时间，防止缓存
        function randomcode_refresh() {
            $("#randomcode_img").attr('src',
                '${baseurl}validatecode.jsp?time' + new Date().getTime());
        }

        //回首页
        function tofirst() {
            if (parent.parent.parent) {
                window.top.location = '${baseurl}first.action';
            } else if (parent.parent) {
                window.top.location = '${baseurl}first.action';
            } else if (parent) {
                window.top.location = '${baseurl}first.action';
            } else {
                window.location = '${baseurl}first.action';
            }
            // window.location='${baseurl}first.action';
        }
    </script>
</head>
<body style="background: #f6fdff url(${baseurl}images/login/bg1.jpg) repeat-x;">
<form id="loginform" name="loginform" action="${baseurl}loginsubmit.action"
      method="post">
    <div class="logincon">

        <div class="title">
            <img alt="" src="${baseurl}images/login/logo.png">
        </div>

        <div class="cen_con">
            <img alt="" src="${baseurl}images/login/bg2.png">
        </div>

        <div class="tab_con">

            <input type="password" style="display:none;"/>
            <table class="tab" border="0" cellSpacing="6" cellPadding="8">
                <tbody>
                <tr>
                    <td>用户名：</td>
                    <td colSpan="2"><input type="text" id="userid"
                                           name="userid" style="WIDTH: 130px"/></td>
                </tr>
                <tr>
                    <td>密 码：</td>
                    <td><input type="password" id="password" name="pwd" style="WIDTH: 130px"/></td>
                </tr>
                <tr>
                    <td>验证码：</td>
                    <td><input id="randomcode" name="validateCode" size="8"/> <img
                            id="randomcode_img" src="${baseurl}validatecode.jsp" alt=""
                            width="56" height="20" align='absMiddle'/> <a
                            href=javascript:randomcode_refresh()>刷新</a></td>
                </tr>

                <tr>
                    <td colSpan="2" align="center">
                        <input type="button"
                               class="btnalink" onclick="loginsubmit()"
                               value="登&nbsp;&nbsp;录" id="loginBtn"/>
                        <input type="reset" class="btnalink" value="重&nbsp;&nbsp;置" id="restBtn"/>
                    </td>
                </tr>
                </tbody>
            </table>

        </div>
    </div>
</form>
<script type="text/javascript">
    var randomcode = document.getElementById('randomcode');
    randomcode.addEventListener("keyup", e => {
        // alert(e.key)
        if (e.key === 'Enter')
            loginsubmit();
    }, false)
</script>
</body>
</html>
