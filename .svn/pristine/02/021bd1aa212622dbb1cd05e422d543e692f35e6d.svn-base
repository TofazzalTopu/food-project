<%--
  Created by IntelliJ IDEA.
  User: mdalinaser.khan
  Date: 3/28/16
  Time: 2:53 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>

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
    $(document).ready(function () {
        InitProductDataTable();
    });

    function InitProductDataTable() {
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
            "sAjaxSource":"${request.contextPath}/salesHeadByVolume/jsonProductList/",
            "aoColumns":[
                { "mDataProp":"id", "bVisible":false },
                { "mDataProp":"code", "sType":"html" },
                { "mDataProp":"name" },
                { "mDataProp":"packSize" },
                { "mDataProp":"category" },
                { "mDataProp":"type" },
                { "mDataProp":"measurementUnit" },
                { "mDataProp":"enterprise" }
            ],
            "fnRowCallback":function (nRow, aData, iDisplayIndex) {
                // ESCAPE CHARACTER: ' -> &#39;
                var searchName = "[" + aData.code + "] " + aData.name.toString();
                $('td:eq(0)', nRow).html('<a href="javascript:void(0)" onclick="loadDataInField(' + '\'' + aData.id + '\', \'' + aData.code + '\', \'' + aData.name + '\', \'' + searchName + '\')">' + aData.code + '</a>');
                return nRow;
            }
        });
    }
    function loadDataInField(productId, productCode, productName, searchName) {
        $.fancybox.close();
        $("#productSearchKey").val(searchName);
        loadProductDataInField(productId, productCode, productName);
    }
</script>


<div class="main_container" style="overflow: hidden;">
    <h3>Product List</h3>
    <table cellpadding="0" cellspacing="0" border="0" class="display" id="product_${id}">
        <thead>
        <tr>
            <th>Id</th>
            <th>Code</th>
            <th>Name</th>
            <th>Pack Size</th>
            <th>Category</th>
            <th>Type</th>
            <th>Unit</th>
            <th>Enterprise</th>
        </tr>
        </thead>
        <tbody></tbody>
    </table>
</div>
