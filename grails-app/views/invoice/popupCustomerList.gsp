<%--
  Created by IntelliJ IDEA.
  User: alinaser
  Date: 9/18/15
  Time: 9:17 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="com.bits.bdfp.inventory.demandorder.PrimaryDemandOrder"  contentType="text/html;charset=UTF-8" %>

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
                    "iDisplayLength":20,
                    %{--"sAjaxSource":"${request.contextPath}/${params.controller}/jsonCustomerList/",--}%
                    "aaData":data,
                    "aoColumns":[
                        { "mDataProp":"id", "bVisible":false },
                        { "mDataProp":"code", "sType":"html" },
                        { "mDataProp":"name" },
                        { "mDataProp":"category" },
                        { "mDataProp":"present_address" }
                    ],
                    "fnRowCallback":function (nRow, aData, iDisplayIndex) {
                        // ESCAPE CHARACTER: ' -> &#39;
                        var cusName = aData.name.toString();
                        var cusCode = aData.code.toString();
                        var cusAddress = aData.present_address.toString();
                        var name = "[" + cusCode + "] " + cusName;
                        $('td:eq(0)', nRow).html('<a href="javascript:void(0)" onclick="loadDataInField(' + '\'' + aData.id + '\', \'' + name + '\', \'' + cusName + '\', \'' + cusCode + '\', \'' + cusAddress + '\')">' + aData.code + '</a>');
                        return nRow;
                    }
                });
    }
    function loadDataInField(customerCodeInfoId, name, cusName, cusCode, cusAddress) {
        $.fancybox.close();
        CustomerInfo.setCustomerInformation(customerCodeInfoId, name);
        loadProduct(customerCodeInfoId);
        $("#customerName").val(cusName);
        $("#customerNumber").val(cusCode);
        $("#customerAddress").val(cusAddress);
        $('#searchProductKey').focus();
//        CustomerInfo.setCustomerDeliveryInformation(customerCodeInfoId, name);
    }
</script>


<div class="main_container" style="overflow: hidden;">
    <table cellpadding="0" cellspacing="0" border="0" class="display" id="customer_${id}">
        <thead>
        <tr>
            <th width="120px">Id</th>
            <th>Code</th>
            <th>Name</th>
            <th>Category</th>
            <th>Present Address</th>
        </tr>

        </thead>
        <tbody></tbody>
    </table>
</div>
