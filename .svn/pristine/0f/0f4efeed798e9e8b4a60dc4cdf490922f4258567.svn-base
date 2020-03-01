
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
    var customerTable = null;
    $(document).ready(function () {
        InitCustomerDataTable();
    });

    function InitCustomerDataTable() {
        customerTable = $('#customer_${id}').dataTable({
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
            "sAjaxSource":"${request.contextPath}/workflow/jsonCustomerListByEnterprise?enterpriseId=" + $("#enterpriseConfiguration").val(),
            "aoColumns":[
                { "mDataProp":"id", "bVisible":false },
                { "mDataProp":"code", "sType":"html" },
                { "mDataProp":"name" },
                { "mDataProp":"category" },
                { "mDataProp":"enterprise" },
                { "mDataProp":"present_address" }
            ],
            "fnRowCallback":function (nRow, aData, iDisplayIndex) {
                // ESCAPE CHARACTER: ' -> &#39;
                var cusName = aData.name.toString();
                var name = "[" + aData.code + "] " + aData.name.toString();
                $('td:eq(0)', nRow).html('<a href="javascript:void(0)" onclick="setDataField(' + '\'' + aData.id + '\', \'' + name + '\', \'' + cusName + '\', \'' + aData.code + '\', \'' + aData.enterprise + '\', \'' + aData.present_address + '\')">' + aData.code + '</a>');
                return nRow;
            }
        });
    }
    function setDataField(customerId, name, cusName, code, enterprise, address) {
        $.fancybox.close();
        $("#customerSearchKey").val(name);
        loadCustomerData(customerId, cusName, code, enterprise, address);
    }
</script>


<div class="main_container" style="overflow: hidden;">
    <h3>Customer List</h3>
    <table cellpadding="0" cellspacing="0" border="0" class="display" id="customer_${id}">
        <thead>
        <tr>
            <th>Id</th>
            <th>Customer ID</th>
            <th>Customer Name</th>
            <th>Category</th>
            <th>Enterprise</th>
            <th>Address</th>
        </tr>
        </thead>
        <tbody></tbody>
    </table>
</div>

