<%--
  Created by IntelliJ IDEA.
  User: maimuna.akter
  Date: 6/15/2015
  Time: 11:44 AM
--%>



<%@ page import="com.bits.bdfp.inventory.demandorder.SecondaryDemandOrder" contentType="text/html;charset=UTF-8" %>

<link rel="stylesheet" href="${resource(dir: 'js/jquery/DataTables-1.8.2/media/css', file: 'demo_page.css')}"/>
<link rel="stylesheet" href="${resource(dir: 'js/jquery/DataTables-1.8.2/media/css', file: 'demo_table_jui.css')}"/>
<link rel="stylesheet" href="${resource(dir: 'js/jquery/DataTables-1.8.2/media/css', file: 'demo_table.css')}"/>
<link rel="stylesheet" href="${resource(dir: 'js/jquery/DataTables-1.8.2/media/css', file: 'jquery.dataTables.css')}"/>
<style>
.ffb-office-container {
    top: 47px !important;
    left: 10px !important;
    width: 352px !important;
}
.dataTables_scrollHeadInner{
    width: 800px !important;
}
table.display, table.dataTable{
    width: 800px !important;
}
</style>

<script type="text/javascript">
    var employeeDataTable = null;

    $(document).ready(function () {
        InitOverviewDataTable1();
    });

    function InitOverviewDataTable1() {
        var data = ${aaData}
                oOverviewTable = $('#product_${id}').dataTable({
                    "sPaginationType": "full_numbers",
                    "bPaginate": true,
                    "bJQueryUI": true, // ThemeRoller-stöd
                    "bLengthChange": true,
                    "bFilter": true,
                    "bSort": true,
                    "bInfo": true,
                    "bAutoWidth": false,
                    "bProcessing": true,
                    sScrollY: "300px",
                    "bScrollAutoCss": true,
                    "iDisplayLength": 20,
                    %{--"sAjaxSource":"${request.contextPath}/${params.controller}/jsonCustomerList/",--}%
                    "aaData": data,
                    "aoColumns": [
                        {"mDataProp": "id", "bVisible": false},
                        {"mDataProp": "code", "sType": "html"},
                        {"mDataProp": "name"},
                        {"mDataProp": "quantity"}
                    ],
                    "fnRowCallback": function (nRow, aData, iDisplayIndex) {
                        // ESCAPE CHARACTER: ' -> &#39;
                        var cusName = aData.name.toString()
                        var quantity = ''
                        if (aData.quantity) {
                            quantity = aData.quantity
                           // alert("quantity "+aData.quantity)
                        }

                        var name = aData.name + "[" + aData.code + "]";
                        $('td:eq(0)', nRow).html('<a href="javascript:void(0)" onclick="loadDataInField(' + '\'' + aData.id + '\', \'' + name + '\', \'' + cusName + '\', \'' + quantity + '\')">' + aData.code  +  '</a>');
                        return nRow;
                    }
                });
    }
    function loadDataInField(productCodeInfoId, name, cusName,quantity) {
        $.fancybox.close();
        if(tabIndex == 'replacement'){
            ReplacementProductInfo.setProductInformation(productCodeInfoId, name, quantity);
        }else if(tabIndex == 'entertainment'){
            EntertainmentProductInfo.setProductInformation(productCodeInfoId, name);
        }else if(tabIndex == 'sample'){
            SampleProductInfo.setProductInformation(productCodeInfoId, name);
        }else if(tabIndex == 'damage'){
            DamageProductInfo.setProductInformation(productCodeInfoId, name);
        }else if(tabIndex == 'rtp'){
            RtpProductInfo.setProductInformation(productCodeInfoId, name);
        }
    }
</script>


<div class="main_container" style="overflow: hidden;">
    <table cellpadding="0" cellspacing="0" border="0" class="display" id="product_${id}">
        <thead>
        <tr>
            <th>Id</th>
            <th>Code</th>
            <th>Name</th>
            <th>Quantity</th>
        </tr>
        </thead>
        <tbody></tbody>
    </table>
</div>

