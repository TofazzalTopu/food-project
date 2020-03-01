
<%@ page import="com.bits.bdfp.inventory.demandorder.SecondaryDemandOrder"  contentType="text/html;charset=UTF-8" %>
<g:javascript src="jquery/watermark/jquery.watermark.js"/>
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
    var employeeTable = null;
    $(document).ready(function () {
        InitEmployeeDataTable();
    });

    function InitEmployeeDataTable() {
        employeeTable = $('#employee_${id}').dataTable({
            "sPaginationType":"full_numbers",
            "bPaginate":true,
            "bJQueryUI":true, // ThemeRoller-stÃ¶d
            "bLengthChange":true,
            "bFilter":true,
            "bSort":true,
            "bInfo":true,
            "bAutoWidth":false,
            "bProcessing":true,
            sScrollY: "200px",
            "bScrollAutoCss": true,
            "iDisplayLength":10,
            "sAjaxSource":"${request.contextPath}/visitPlan/jsonEmployeeList/",
            "aoColumns":[
                { "mDataProp":"id", "bVisible":false },
                { "mDataProp":"code", "sType":"html" },
                { "mDataProp":"employee" },
                { "mDataProp":"designation" },
                { "mDataProp":"department" },
                { "mDataProp":"enterprise" }
            ],
            "fnRowCallback":function (nRow, aData, iDisplayIndex) {
                // ESCAPE CHARACTER: ' -> &#39;
                var cusName = aData.employee.toString();
                var name = "[" + aData.code + "] " + aData.employee.toString();
                $('td:eq(0)', nRow).html('<a href="javascript:void(0)" onclick="setDataField(' + '\'' + aData.id + '\', \'' + name + '\', \'' + cusName + '\', \'' + aData.code + '\', \'' + aData.enterprise + '\')">' + aData.code + '</a>');
                return nRow;
            }
        });
    }
    function setDataField(employeeId, name, empName, code, enterprise) {
        $.fancybox.close();
        $("#employeeName").val(name);
        $("#ename").val(empName);
        $("#employeeId").val(code);
        $("#employeeNamehidden").val(employeeId);
        $("#enterprise").val(enterprise);
//        loadEmployeeDataInField(employeeId, empName, code, enterprise);
    }
</script>


<div class="main_container" style="overflow: hidden;">
    <h3>Employee List</h3>
    <table cellpadding="0" cellspacing="0" border="0" class="display" id="employee_${id}">
        <thead>
        <tr>
            <th>Id</th>
            <th>PIN</th>
            <th>Employee Name</th>
            <th>Designation</th>
            <th>Department</th>
            <th>Enterprise</th>
        </tr>
        </thead>
        <tbody></tbody>
    </table>
</div>

