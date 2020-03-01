<%--
  Created by IntelliJ IDEA.
  User: mdalinaser.khan
  Date: 9/8/15
  Time: 12:48 PM
--%>

<%@ page import="com.bits.bdfp.customer.CustomerMaster" contentType="text/html;charset=UTF-8" %>
<link rel="stylesheet" href="${resource(dir: 'js/jquery/DataTables-1.8.2/media/css', file: 'demo_page.css')}"/>
<link rel="stylesheet" href="${resource(dir: 'js/jquery/DataTables-1.8.2/media/css', file: 'demo_table_jui.css')}"/>
<link rel="stylesheet" href="${resource(dir: 'js/jquery/DataTables-1.8.2/media/css', file: 'demo_table.css')}"/>
<link rel="stylesheet" href="${resource(dir: 'js/jquery/DataTables-1.8.2/media/css', file: 'jquery.dataTables.css')}"/>
<style>
.ffb-office-container{
    top: 47px !important;
    left: 10px !important;
    width: 352px !important;
}
</style>

<script type="text/javascript">
    var employeeDataTable = null;
    $(document).ready(function () {
        InitOverviewDataTable();
    });

    function InitOverviewDataTable() {
        employeeDataTable = $('#example_${unqId}').dataTable({
            "sPaginationType":"full_numbers",
            "bPaginate":true,
            "bJQueryUI":true, // ThemeRoller-stöd
            "bLengthChange":true,
            "bFilter":true,
            "bSort":true,
            "bInfo":true,
            "bAutoWidth":false,
            "bProcessing":true,
            sScrollY: "200px",
            "bScrollAutoCss": true,
            "iDisplayLength":10,
            "sAjaxSource":"${request.contextPath}/${params.controller}/jsonEmployeeList",
            "aoColumns":[
                { "mDataProp":"id", "bVisible":false },
                { "mDataProp":"code", "sType":"html" },
                { "mDataProp":"name" },
                { "mDataProp":"designation" },
                { "mDataProp":"department" },
                { "mDataProp":"enterprise" },
                { "mDataProp":"status" }
            ]
            ,
            "fnRowCallback":function (nRow, aData, iDisplayIndex) {
                // ESCAPE CHARACTER: ' -> &#39;
                var name = "[" + aData.code + "] " + aData.name.toString();
                $('td:eq(0)', nRow).html('<a href="javascript:void(0)" onclick="loadDataInField(' + '\'' + aData.id + '\', \'' + name + '\')">' + aData.code + '</a>');
                return nRow;
            }
        });
    }
    function loadDataInField(employeeCodeInfoId, employeeName) {
        $.fancybox.close();
        $("#employee").setValue(employeeName);
        EmployeeRegister.setEmployeeInformation(employeeCodeInfoId, employeeName);
        EmployeeRegister.showEmployeeDetail();
    }
</script>


<div class="main_container" style="overflow: hidden;">
    <h3>Employee List</h3>
    <table cellpadding="0" cellspacing="0" border="0" class="display" id="example_${unqId}">
        <thead>
        <tr>
            <th width="120px">ID</th>
            <th>Employee PIN</th>
            <th>Employee Name</th>
            <th>Designation</th>
            <th>Department</th>
            <th>Enterprise</th>
            <th>Status</th>
        </tr>
        </thead>
        <tbody></tbody>
    </table>
</div>
