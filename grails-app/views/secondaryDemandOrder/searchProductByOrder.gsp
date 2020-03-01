<%@ page import="com.bits.bdfp.inventory.demandorder.DemandOrderStatus; com.docu.commons.CommonConstants; com.bits.bdfp.util.ApplicationConstants; com.bits.bdfp.inventory.demandorder.SecondaryDemandOrder" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title>Search Demand Order</title>

<g:javascript src="jquery/watermark/jquery.watermark.js"/>
<script type="text/javascript" language="Javascript">
    $(document).ready(function() {
        loadOrderNo();
        $("#jqgrid-grid").jqGrid({
            url:'${request.contextPath}/${params.controller}/list',
            datatype: "json",
            colNames:[
                'Sl',
                'Id',
                'Customer',
                'Order No.',
                'Order Date',
                'Total Demand Amount',
                'Order Status',
                'Edit'

            ],
            colModel:[
                {name:'sl',index:'sl', width:20, align:'center'},
                {name:'id',index:'id', width:0, hidden:true},
                {name:'name', index:'name',width:200,align:'left'},
                {name:'order_no', index:'order_no',width:80,align:'center'},
                {name:'date_order', index:'date_order',width:60,align:'center'},
                {name:'amount', index:'amount',width:80,align:'right'},
                {name:'demand_order_status', index:'demand_order_status',width:100,align:'left'},
                {name:'edit', index:'edit',width:40,align:'center'}
            ],
            rowNum:20,
            rowList:[10,20,30,40,50,60,70,80,90,100,-1],
            pager: '#jqgrid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption:"Secondary Demand Order List",
            autowidth: true,
            height: true,

            scrollOffset: 0,
            loadComplete: function() {

                var ids = $("#jqgrid-grid").jqGrid('getDataIDs');
                allEmail = '';
                allMobile = '';
                for (key in ids) {
                    var id = $("#jqgrid-grid").getCell(ids[key], 'id');
                    var status=$("#jqgrid-grid").getCell(ids[key], 'demand_order_status');

                    %{--if (status='${com.bits.bdfp.inventory.demandorder.DemandOrderStatus.PROCESSING}'){--}%
                        $("#jqgrid-grid").jqGrid('setRowData', ids[key], {edit: '<a  href="javascript:editDemandOrder(' + id + ')" class="ui-icon ui-icon-pencil" title="Edit"></a>'});
                    %{--}--}%


                }

            },
            onSelectRow: function(rowid, status) {
//                executeEditProduct();
            }
        });
        $("#jqgrid-grid").jqGrid('navGrid', '#jqgrid-pager', {edit:false,add:false,del:false,search:false});
//     $("#jqgrid-grid").gridResize({minWidth:350,maxWidth:800,minHeight:200});
        $(".ui-pg-selbox").children().each(function() {
            if ($(this).val() == -1) {
                $(this).html('All')
            }

        });



    });
function loadOrderNo(){
    jQuery('#searchOrderKey').autocomplete({
        minLength:'1',
        source:function (request, response) {
            //EmployeeRegister.employeeCoreInfoId = null;
            $('#popEmpDetails').html("");
            var data = {searchKey:request.term};
            var url = '${resource(dir:'secondaryDemandOrder', file:'listOrderNoAutoComplete')}' ;
            DocuAutoComplete.setSpinnerSelector('searchOrderKey').execute(response, url, data, function (item) {
                item['label'] = item['order_no'];
                item['value'] = item['label'];
                return item;
            });
        },
        select:function (event, ui) {

            $("#searchOrderKey").val(ui.item.order_no);
        }
    }).data("autocomplete")._renderItem = function (ul, item) {
        var accountstype = "";
        if (item) {
            accountstype = '<div style="font-size: 9px; color:#326E93;">' +" Order No: " +item.order_no+ '</div>';
        }
        return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + accountstype).appendTo(ul);

    }
}
    function loadDataByOrder(){
        $("#jqgrid-grid").setGridParam({url: '${resource(dir:'secondaryDemandOrder', file:'list')}?orderNo=' + $('#searchOrderKey').val()
        +'&status='+$('#status').val()+'&dateFrom='+$('#dateFrom').val()+'&dateTo='+$('#dateTo').val()});
        $("#jqgrid-grid").trigger("reloadGrid");

    }

    function editDemandOrder(id){
        window.open('${createLink(uri: '/')}secondaryDemandOrder/editDemandOrder?id=' + id);
    }
</script>
<div class="main_container">
    <div class="content_container">
        <h1><g:message code="enterpriseConfiguration.create.label" default="Search Secondary Demand order"/></h1>
        <h3><g:message code="enterpriseConfiguration.info.label" default="Secondary Demand order Information"/></h3>
<form name='gFormSecondaryDemandOrderSearch' id='gFormSecondaryDemandOrderSearch'>
    <g:hiddenField name="id" value="${secondaryDemandOrder?.id}" />
    <g:hiddenField name="version" value="${secondaryDemandOrder?.version}" />
    <g:hiddenField name="idEnterprise" id="idEnterprise" value=""/>
    <g:hiddenField name="idBusiness" id="idBusiness" value=""/>
    <div id="remote-content-territoryConfiguration"></div>
    <br>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <table>

            <tr >
                <script type="text/javascript">
                    jQuery(document).ready(function () {
//                        $("#dateFrom, #dateTo").datepicker(
//                                { dateFormat: 'dd-mm-yy',
//                                    changeMonth:true,
//                                    changeYear:true
//                                });
                        setDateRangeNoLimit('dateFrom','dateTo');
                        $('#dateFrom').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});
                        $('#dateTo').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});
                    });
                </script>
                <td>
                    <label class="txtright bold hight1x width1x" style="width: 60px;">
                        <g:message code="secondaryDemandOrder.product.label"
                                   default="Order No"/>
                    </label>

                </td>
                <td><input type="text" id="searchOrderKey" name="searchOrderKey" class="width120"/>
                    <input type="hidden" id="productId" name="productId"/>
                    <input type="hidden" id="productCode" />
                    <input type="hidden" id="product" />
                 </td>
                <td>
                    <label class="txtright bold hight1x width1x" style="width: 60px;">
                        <g:message code="secondaryDemandOrder.orderDate.label"
                                   default="Date From"/>

                    </label>
                </td>
                <td>
                    <g:textField name="dateFrom" id="dateFrom" value="" class="width120" onload="loadDataByOrder();"/>
                </td>
                <td>
                    <label class="txtright bold hight1x width1x" style="width: 60px">
                        <g:message code="secondaryDemandOrder.deliveryDate.label" default="Date To"/>

                    </label>

                </td>
                <td>
                    <g:textField name="dateTo" id="dateTo"  value="" class="width120"/>
                </td>
                <td>
                    <label class="txtright bold hight1x width1x" style="width: 40px">
                        <g:message code="secondaryDemandOrder.deliveryDate.label" default="Status"/>

                    </label>

                </td>
                <td>
                    <select name="status" id="status">
                        <option value=""></option>
                        <option value="${DemandOrderStatus.UNDER_PROCESS}">${DemandOrderStatus.UNDER_PROCESS}</option>
                        <option value="${DemandOrderStatus.REJECTED}">${DemandOrderStatus.REJECTED}</option>
                    </select>

                </td>
            </tr>
<tr>
    <td>
        <div class="buttons">
            <span class="button"><input type="button" name="add-button" id="add-button"
                                        class="ui-button ui-widget ui-state-default ui-corner-all"
                                        value="Search"
                                        onclick="loadDataByOrder();"/></span>

        </div>
    </td>
</tr>
        </table>

        <div class="jqgrid-container">
            <table id="jqgrid-grid"></table>

            <div id="jqgrid-pager"></div>
        </div>
    </div>

</form>
        </div>
</div>