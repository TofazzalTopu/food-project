<%--
  Created by IntelliJ IDEA.
  User: alinaser
  Date: 9/19/15
  Time: 11:31 PM
  To change this template use File | Settings | File Templates.
--%>


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
.dataTables_scrollHeadInner{width: 100%!important;}
.display{width: 100% !important;}
</style>

<script type="text/javascript">
    var productDataTable = null;
    var customerId = '${customerId}';
    var territorySubAreaId = '${territorySubAreaId}';
    $(document).ready(function () {
        InitProductDataTable();
    });

    function InitProductDataTable() {
        var data = ${aaData}
                productDataTable = $('#product_${id}').dataTable({
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
                    %{--"sAjaxSource":"${request.contextPath}/${params.controller}/jsonProductList?customerId=" + customerId + "&territorySubAreaId=" + territorySubAreaId,--}%
                    "aaData":data,
                    "aoColumns":[
                        { "mDataProp":"id", "bVisible":false },
                        { "mDataProp":"code", "sType":"html" },
                        { "mDataProp":"date_created" },
                        { "mDataProp":"name" },
                        { "mDataProp":"present_address" }
                    ],
                    "fnRowCallback":function (nRow, aData, iDisplayIndex) {
                        // ESCAPE CHARACTER: ' -> &#39;
                        var code = aData.code;
                        $('td:eq(0)', nRow).html('<a href="javascript:void(0)" onclick="loadDataInField(' +
                        '\'' + aData.id + '\', \'' + aData.code + '\', \'' + aData.name +
                        '\', \'' + aData.present_address + '\', \'' + aData.date_created + '\')">' + aData.code + '</a>');
                        return nRow;
                    }
                });
    }
    function loadDataInField(id, code, name, address, date) {
        $.fancybox.close();
        CustomerInfo.setInvoiceInformation(id, code, name, address, date);
    }
</script>


<div class="main_container" style="overflow: hidden;">
    <h3>Invoice List</h3>
    <table cellpadding="0" cellspacing="0" border="0" class="display" id="product_${id}">
        <thead>
        <tr>
            <th width="120px">Id</th>
            <th width="150px">Code</th>
            <th width="150px">Invoice</th>
            <th width="250px">Customer</th>
            <th width="250px">Address</th>
        </tr>
        </thead>
        <tbody></tbody>
    </table>
</div>
