<%--
  Created by IntelliJ IDEA.
  User: mdalinaser.khan
  Date: 9/13/15
  Time: 3:45 PM
--%>

<%@ page import="com.bits.bdfp.inventory.demandorder.SecondaryDemandOrder"  contentType="text/html;charset=UTF-8" %>

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
.dataTables_scrollHeadInner{width: 100%!important;}
.display{width: 100% !important;}
</style>

<style>
table {
    table-layout:fixed;
}
td{
    overflow:hidden;
    text-overflow: ellipsis;
}
</style>

<script type="text/javascript">
    var customerDataTable = null;

    $(document).ready(function () {
        InitOverviewDataTable();
    });

    function InitOverviewDataTable() {

        var data = ${aaData}
        customerDataTable = $('#customer_${id}').dataTable({
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
            %{--"sAjaxSource":"${request.contextPath}/${params.controller}/jsonCustomerList/",--}%
            "aaData":data,

            "aoColumns":[
                { "mDataProp":"id", "bVisible":false},
                { "mDataProp":"code", "sType":"html"},
                { "mDataProp":"legacy_id" },
                { "mDataProp":"name" },
                { "mDataProp":"partnerType" },
                { "mDataProp":"present_address" }
            ],
            "fnRowCallback":function (nRow, aData, iDisplayIndex) {
                // ESCAPE CHARACTER: ' -> &#39;
                var cusName = aData.name.toString();
                var cusCode = aData.code.toString();
                var legacyId = aData.legacy_id.toString();
                var cusAddress = aData.present_address.toString();
                var name = "[" + aData.code + "] " + aData.name.toString();
                $('td:eq(0)', nRow).html('<a href="javascript:void(0)" onclick="loadDataInField(' + '\'' + aData.id + '\', \'' + name + '\', \'' + cusName + '\', \'' + cusCode + '\', \'' + cusAddress + '\')">' + aData.code + '</a>');
                return nRow;
            }
        });
    }
    function loadDataInField(customerCodeInfoId, name, cusName, cusCode, cusAddress) {
        $.fancybox.close();
        CustomerInfo.setCustomerInformation(customerCodeInfoId, name);
        $("#customerName").val(cusName);
        $("#customerNumber").val(cusCode);
        $("#customerAddress").val(cusAddress);
    }
</script>


<div class="main_container" style="overflow: hidden;">
    <h3>Special/Negotiated Customer List</h3>
    <table cellpadding="0" cellspacing="0" border="0" class="display" id="customer_${id}">
        <thead>
        <tr>
            <th>Id</th>
            <th>Code</th>
            <th>Legacy ID</th>
            <th>Name</th>
            <th>Partner Type</th>
            <th>Present Address</th>
        </tr>

        </thead>
        <tbody></tbody>
    </table>
</div>