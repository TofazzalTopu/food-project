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
.dataTables_scrollHeadInner{width: 100%!important;}
.display{width: 100% !important;}

</style>

<script type="text/javascript">
    var productTableData = null;

    $(document).ready(function () {
        InitOverviewDataTable();
    });

    function InitOverviewDataTable() {

        var data = ${aaData}
                productTableData = $('#product_${id}').dataTable({
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
                        { "mDataProp":"pack_size" },
                        { "mDataProp":"p_cat" },
                        { "mDataProp":"p_type" },
                        { "mDataProp":"mu" }
                    ],
                    "fnRowCallback":function (nRow, aData, iDisplayIndex) {
                        // ESCAPE CHARACTER: ' -> &#39;
                        var price = aData.price;
                        var code = aData.code;
                        var name = aData.name;
                        var pack_size = aData.pack_size;
                        var mu = aData.mu;
                        var muId = aData.muId;
                        //alert(aData.mu)
                      //  alert(aData.muId)
                        var productName = aData.name.toString();
                        //var name = aData.code + "-" + aData.name.toString() ;
                        $('td:eq(0)', nRow).html('<a href="javascript:void(0)" onclick="loadDataInField(' + '\'' + aData.id + '\', \'' + name + '\', \'' + code + '\', \'' + productName + '\', \'' + pack_size +'\', \'' + mu +'\', \'' + muId + '\')">' + aData.code + '</a>');
                       // $('td:eq(0)', nRow).html('<a href="javascript:void(0)" onclick="loadDataInField(' + '\'' + aData.id + '\', \'' + name + '\', \'' + code + '\', \'' + productName + '\')">' + aData.code + '</a>');
                        return nRow;
                    }
                });
    }
    function loadDataInField(customerCodeInfoId, name,code,productname,packSize,mu,muId) {
       // alert("packSize "+packSize+"-"+muId)
        $.fancybox.close();
        ProductInfo.setProductInformation(customerCodeInfoId, name, code, packSize,mu,muId);

    }
</script>


<div class="main_container" style="overflow: hidden;">

    <h1>Product List</h1>

    <table cellpadding="0" cellspacing="0" border="0" class="display" id="product_${id}">
        <thead>
        <tr>
            <th width="120px">Id</th>
            <th width="200px">Code</th>
            <th width="200px">Name</th>
            <th width="100px">Pack Size</th>
            <th width="100px">Product Category</th>
            <th width="100px">Product Type</th>
            <th width="50px">M. Unit</th>
        </tr>

        </thead>
        <tbody></tbody>
    </table>
</div>

