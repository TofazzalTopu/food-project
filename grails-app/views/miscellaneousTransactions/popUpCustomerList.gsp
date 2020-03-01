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
                oOverviewTable = $('#customer_${id}').dataTable({
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
                        {"mDataProp": "status"},
                        {"mDataProp": "geo_location"}
                    ],
                    "fnRowCallback": function (nRow, aData, iDisplayIndex) {
                        // ESCAPE CHARACTER: ' -> &#39;
                        var cusName = aData.name.toString()
                        var geoLocaion = ''
                        if (aData.geo_location) {
                            geoLocaion = aData.geo_location
                        }
                        var name = aData.name + "[" + aData.code + "]";
                        $('td:eq(0)', nRow).html('<a href="javascript:void(0)" onclick="loadDataInField(' + '\'' + aData.id + '\', \'' + name + '\', \'' + cusName + '\')">' + aData.code + '</a>');
                        return nRow;
                    }
                });
    }
    function loadDataInField(customerCodeInfoId, name, cusName) {
        $.fancybox.close();
        if(tabIndex == 'replacement'){
            ReplacementCustomerInfo.setCustomerInformation(customerCodeInfoId, name);
        }else if(tabIndex == 'entertainment'){
            EntertainmentCustomerInfo.setCustomerInformation(customerCodeInfoId, name);
        }else if(tabIndex == 'sample'){
            SampleCustomerInfo.setCustomerInformation(customerCodeInfoId, name);
        }else if(tabIndex == 'damage'){
            DamageCustomerInfo.setCustomerInformation(customerCodeInfoId, name);
        }else if(tabIndex == 'rtp'){
            RtpCustomerInfo.setCustomerInformation(customerCodeInfoId, name);
        }
    }
</script>


<div class="main_container" style="overflow: hidden;">
    <table cellpadding="0" cellspacing="0" border="0" class="display" id="customer_${id}">
        <thead>
        <tr>
            <th>Id</th>
            <th>Code</th>
            <th>Name</th>
            <th>Status</th>
            <th>Geo Location</th>
        </tr>
        </thead>
        <tbody></tbody>
    </table>
</div>

