<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/base/tag.jsp" %>
<html>
<head>
    <title>药品信息导入</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">

    <%@ include file="/WEB-INF/jsp/base/common_css.jsp" %>
    <%@ include file="/WEB-INF/jsp/base/common_js.jsp" %>
    <style type="text/css">
        .errorList {
            color: red;
        }
    </style>
    <script type="text/javascript">
        $(function () {
            //***********按钮**************
            $('#submitbtn').linkbutton({
                iconCls: 'icon-ok'
            });
            $('#closebtn').linkbutton({
                iconCls: 'icon-cancel'
            });
        });

        //文件导入提交
        function ypxximportsubmit() {
            jquerySubByFId('ypxximportForm', ypxximportsubmit_callback, null);
        }

        function ypxximportsubmit_callback(data) {
            message_alert(data);
        }
    </script>

</head>
<body>
<form id="ypxximportForm" name="ypxximportForm"
      action="${baseurl}ypml/importypxxsubmit.action" method="post"
      enctype="multipart/form-data">
    <table border=0 cellSpacing=0 cellPadding=0 width="100%" bgColor=#c4d8ed>
        <tbody>
        <tr>
            <td background=images/r_0.gif width="100%">
                <table cellSpacing=0 cellPadding=0 width="100%">
                    <tbody>
                    <tr>
                        <td>&nbsp;药品信息导入</td>
                        <td align=right>&nbsp;</td>
                    </tr>
                    </tbody>
                </table>
            </td>
        </tr>
        <tr>
            <td>
                <table class="toptable grid" border=1 cellSpacing=1 cellPadding=4
                       align=center>
                    <tbody>

                    <tr>
                        <td height=30 align=right>导入说明：</td>
                        <td>
                            1、导入文件为Excel 97-2003版本，文件扩展名为.xls，如果使用高版本的Excel请另存为Excel 97-2003版本。
                            <br>2、点击 <a class="blue" href="http://localhost/upload/ypxx_template.xls"><u>药品信息模板</u></a>
                            下载，并按照说明录入药品信息。
                            <br>3、导入文件内容填写完毕请在下方选择导入文件，点击 导入按钮。
                        </td>
                    </tr>
                    <tr>
                        <td height=30 align=right>选择导入文件</td>
                        <td class=category>
                            <input type="file" name="ypxximportfile"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan=2 align=center class=category>
                            <a id="submitbtn" href="#" onclick="ypxximportsubmit()">导入</a>
                            <a id="closebtn" href="#" onclick="parent.closemodalwindow()">关闭</a>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </td>
        </tr>
        </tbody>
    </table>
</form>
</body>
</html>

