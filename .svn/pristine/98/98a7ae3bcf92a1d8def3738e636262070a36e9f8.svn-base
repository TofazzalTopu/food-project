<%--
  Created by IntelliJ IDEA.
  User: maimuna.akter
  Date: 6/15/2015
  Time: 11:44 AM
--%>



<%@ page import="com.bits.bdfp.inventory.demandorder.PrimaryDemandOrder"  contentType="text/html;charset=UTF-8" %>
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
    var employeeDataTable = null;

    $(document).ready(function () {
        InitOverviewDataTable1();
    });

    function InitOverviewDataTable1() {

        var data=${aaData}
                oOverviewTable = $('#product_${id}').dataTable({
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
                        { "mDataProp":"id", "bVisible":false },
                        { "mDataProp":"order_no", "sType":"html" },
                        { "mDataProp":"approve" }
                    ]
//                    "fnRowCallback":function (nRow, aData, iDisplayIndex) {
//                        // ESCAPE CHARACTER: ' -> &#39;
//                        var price=aData.price
//                        var code=aData.code
//                        var productName= aData.name.toString()
//                        var name = aData.code + " -" + aData.name.toString() ;
//                        $('td:eq(0)', nRow).html('<a href="javascript:void(0)" onclick="loadDataInField(' + '\'' + aData.id + '\', \'' + name + '\', \'' + price + '\', \'' + code + '\', \'' + productName + '\')">' + aData.code + '</a>');
//                        return nRow;
//                    }
                });
    }

</script>


<div class="main_container" style="overflow: hidden;">

    <h1>Approval History</h1>

    <table cellpadding="0" cellspacing="0" border="0" class="display" id="product_${id}">
        <thead>
        <tr>
            <th>Id</th>
            <th>Order No</th>
            <th>Status</th>



        </tr>

        </thead>
        <tbody></tbody>
    </table>
</div>

