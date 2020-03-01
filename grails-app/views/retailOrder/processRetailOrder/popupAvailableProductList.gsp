<%--
  Created by IntelliJ IDEA.
  User: mdalinaser.khan
  Date: 9/13/15
  Time: 3:45 PM
--%>

<%@ page import="com.bits.bdfp.customer.CustomerStock"  contentType="text/html;charset=UTF-8" %>

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
    var productDataTable = null;

    $(document).ready(function () {
        InitOverviewDataTable();
    });

    function InitOverviewDataTable() {

        var data = ${aaData}
        productDataTable = $("#productDataTable").dataTable({
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
                { "mDataProp":"productId", "bVisible":false},
                { "mDataProp":"productCode", "sType":"html"},
                { "mDataProp":"productName" },
                { "mDataProp":"batchNo" },
                { "mDataProp":"availableQuantity" }
            ],
            "fnRowCallback":function (nRow, aData, iDisplayIndex) {
                // ESCAPE CHARACTER: ' -> &#39;
                var productName = aData.productName.toString();
                var productCode = aData.productCode.toString();
                var batchNo = aData.batchNo.toString();
                var availableQty = aData.availableQuantity.toString();
                var name = "[" + aData.productCode + "] " + aData.productName.toString();
                $('td:eq(0)', nRow).html('<a href="javascript:void(0)" onclick="loadDataInField(' + '\'' + aData.id + '\', \'' + name + '\', \'' + aData.productId + '\', \'' + productName + '\', \'' + productCode + '\', \'' + batchNo + '\', \'' + availableQty + '\')">' + aData.productCode + '</a>');
                return nRow;
            }
        });
    }
    function loadDataInField(customerStockId, name, productId, productName, productCode, batchNo, availableQuantity) {
        $.fancybox.close();
        setProductData(customerStockId, name, productId, productName, productCode, batchNo, availableQuantity);
    }
</script>


<div class="main_container" style="overflow: hidden;">
    <h3>Available Product List</h3>
    <table cellpadding="0" cellspacing="0" border="0" class="display" id="productDataTable">
        <thead>
        <tr>
            <th>Id</th>
            <th>ProductId</th>
            <th>Product Code</th>
            <th>Product Name</th>
            <th>Batch No</th>
            <th>Available Qty</th>
        </tr>

        </thead>
        <tbody></tbody>
    </table>
</div>
