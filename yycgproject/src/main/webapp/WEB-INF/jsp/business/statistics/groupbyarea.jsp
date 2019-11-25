<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/base/tag.jsp" %>
<html>
<head>
    <title>按区域统计</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">

    <%@ include file="/WEB-INF/jsp/base/common_css.jsp" %>
    <%@ include file="/WEB-INF/jsp/base/common_js.jsp" %>
    <style type="text/css">
        #main {
            width: 100%;
            height: 400px;
            margin: 25px auto;
        }
    </style>
    <script type="text/javascript">
        $(function () {
            initGrid();
        });

        function initGrid(data) {
            var main = document.getElementById('main');
            var myChart = echarts.init(main, 'light');
            myChart.setOption({
                title: {
                    text: '药品采购金额汇总',
                },
                tooltip: {},
                legend: {
                    data:['采购金额']
                },
                xAxis: {
                    data: []
                },
                yAxis: {},
                series: [{
                    name: '采购金额',
                    type: 'bar',
                    data: []
                }
                ]


            });
            var areaNames = [];
            var cgjes = [];
            $.ajax({
                type: 'post',
                url: '${baseurl}/statistics/groupbyareaResult.action',
                dataType: 'json',
                data: data,
                success: function (result) {
                    if (result) {
                        for (var i = 0; i < result.length; i++) {
                            areaNames.push(result[i].areaname);
                            cgjes.push(result[i].cgje);
                        }
                        myChart.setOption({
                            xAxis: {
                                data: areaNames
                            },
                            series: [{
                                name: '采购金额',
                                data: cgjes
                            }]
                        })
                    }
                }
            })
        }

        function businesslistquery() {
            var formdata = $("#businesslistForm").serializeJson();
            console.log(formdata);
            initGrid(formdata);
        }
    </script>
</HEAD>
<BODY>
<form id="businesslistForm" name="businesslistForm" method="post">
    <TABLE class="table_search">
        <TBODY>
        <TR>
            <TD class="left">年份(如2014)：</TD>
            <td>
                <select id="year" name="year">
                    <option value="2019">2019</option>
                </select>
            </td>
            <TD class="left">医院名称：</TD>
            <td><INPUT type="text" name="useryyCustom.mc"/></td>
            <TD class="left">供货商：</TD>
            <td><INPUT type="text" name="useryyCustom.mc"/></td>
            <TD class="left">采购单号：</td>
            <td><INPUT type="text" name="yycgdCustom.bm"/></TD>


        </TR>
        <TR>
            <TD class="left">流水号：</TD>
            <td><INPUT type="text" name="ypxxCustom.bm"/></td>
            <TD class="left">通用名：</td>
            <td><INPUT type="text" name="ypxxCustom.mc"/>
            </TD>
            <TD class="left">采购时间：</TD>
            <td>
                <INPUT id="yycgdCustom.cjtime_start"
                       name="yycgdCustom.cjtime_start"
                       onfocus="WdatePicker({isShowWeek:false,skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
                       style="width:80px"/>--
                <INPUT id="yycgdCustom.cjtime_end"
                       name="yycgdCustom.cjtime_end"
                       onfocus="WdatePicker({isShowWeek:false,skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
                       style="width:80px"/>

            </td>
            <TD class="left">采购状态：</TD>
            <td>
                <select id="yycgdmxCustom_cgzt" name="yycgdmxCustom.cgzt" style="width:150px">
                </select>
                <a id="btn" href="#" onclick="businesslistquery()" class="easyui-linkbutton"
                   iconCls='icon-search'>统计</a>

            </td>


        </tr>


        </TBODY>
    </TABLE>


</form>
<div id="main"></div>
</BODY>
</HTML>

