<%--
  Created by IntelliJ IDEA.
  User: maimuna.akter
  Date: 6/15/2015
  Time: 11:44 AM
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
</style>

<script type="text/javascript">
    var productDataTable = null;
    var customerId = '${customerId}';
    var territorySubAreaId = '${territorySubAreaId}';
    $(document).ready(function () {
        InitProductDataTable();
    });

    function InitProductDataTable() {
        %{--var data = ${aaData}--}%
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
            "sAjaxSource":"${request.contextPath}/${params.controller}/jsonProductList?customerId=" + customerId + '&territorySubAreaId=' + territorySubAreaId,
//                    "aaData":data,
            "aoColumns":[
                { "mDataProp":"id", "bVisible":false },
                { "mDataProp":"code", "sType":"html" },
                { "mDataProp":"name" },
                { "mDataProp":"p_cat" },
                { "mDataProp":"p_type" },
                { "mDataProp":"price" }
            ],
            "fnRowCallback":function (nRow, aData, iDisplayIndex) {
                // ESCAPE CHARACTER: ' -> &#39;
                var price=aData.price;
                var code=aData.code;
                var productName= aData.name.toString();
                var name = aData.code + " -" + aData.name.toString() ;
                $('td:eq(0)', nRow).html('<a href="javascript:void(0)" onclick="loadDataInField(' + '\'' + aData.id + '\', \'' + name + '\', \'' + price + '\', \'' + code + '\', \'' + productName + '\')">' + aData.code + '</a>');
                return nRow;
            }
        });
    }
    function loadDataInField(customerCodeInfoId, name,price,code,productname) {
        $.fancybox.close();
        $("#searchProductKey").val(name);
        $("#productId").val(customerCodeInfoId);
        $("#finishProduct").val(customerCodeInfoId);
        $('#rate').val(price);
        $('#productCode').val(code);
        $('#productName').val(productname);
        $('#product').val(productname);
//        CustomerInfo.setProductInformation(customerCodeInfoId, name);
    }
</script>


<div class="main_container" style="overflow: hidden;">

    <h3>Product Price List</h3>

    <table cellpadding="0" cellspacing="0" border="0" class="display" id="product_${id}">
        <thead>
            <tr>
                <th>Id</th>
                <th>Code</th>
                <th>Name</th>
                <th>Partner Type</th>
                <th>Product Price Type</th>
                <th>Price</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
        </tbody>
    </table>
</div>

