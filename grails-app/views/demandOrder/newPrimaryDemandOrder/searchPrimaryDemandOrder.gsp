<%--
  Created by IntelliJ IDEA.
  User: mdalinaser.khan
  Date: 9/20/15
  Time: 3:07 PM
--%>

<%@ page import="com.bits.bdfp.inventory.demandorder.DemandOrderStatus; com.docu.commons.CommonConstants; com.bits.bdfp.util.ApplicationConstants; com.bits.bdfp.inventory.demandorder.SecondaryDemandOrder" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title>Search New Primary Demand Order</title>
 <style>
 .ui-jqgrid .ui-jqgrid-btable
 {
     table-layout: auto;
 }
 </style>
<g:javascript src="jquery/watermark/jquery.watermark.js"/>
<script type="text/javascript" language="Javascript">
    $(document).ready(function() {
        loadOrderNoAutoComplete();
        setDateRangeNoLimit('orderDateFrom','orderDateTo');
        $('#orderDateFrom').mask('${CommonConstants.DATE_MASK_FORMAT}', {});
        $('#orderDateTo').mask('${CommonConstants.DATE_MASK_FORMAT}', {});
        setDateRangeNoLimit('deliveryDateFrom','deliveryDateTo');
        $('#deliveryDateFrom').mask('${CommonConstants.DATE_MASK_FORMAT}', {});
        $('#deliveryDateTo').mask('${CommonConstants.DATE_MASK_FORMAT}', {});
        $("#status").val('').attr('selected', 'selected');
        $("#jqgrid-grid").jqGrid({
            url:'${request.contextPath}/${params.controller}/searchNewPrimaryDemandOrder',
            datatype: "json",
            colNames:[
                'Sl',
                'Id',

                'Order No.',
                'Order Date',
                'Delivery Date',
                'Status',
                'Storage Location',
                'Customer',
                'Shipping Address',
                'Edit'

            ],
            colModel:[
                {name:'sl',index:'sl', width:10, sortable: false, hidden: true},
                {name:'id',index:'id', width:0, hidden:true},
                {name:'order_no', index:'order_no', width:60, align:'center', sortable:false},
                {name:'order_date', index:'order_date', width:40, align:'center', sortable:false},
                {name:'delivery_date', index:'delivery_date', width:40, align:'center', sortable:false},
                {name:'demand_order_status', index:'demand_order_status', width:80, align:'left', sortable:false},
                {name:'distribution_point', index:'distribution_point', width:60, align:'left', sortable:false},
                {name:'customer_master', index:'customer_master', width:110, align:'left', sortable:false},
                {name:'address', index:'address', width:50, align:'left', sortable:false},
                {name:'edit', index:'edit', width:20, align:'left'}
            ],
            rowNum:50,
            rowList:[10,20,30,40,50,60,70,80,90,100,-1],
            pager: '#jqgrid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption:"New Primary demand Order List",
            autowidth: true,
            height: true,
            multiselect: true,
            scrollOffset: 0,
            loadComplete: function() {
                var ids = $("#jqgrid-grid").jqGrid('getDataIDs');
                for (var key in ids) {
                    var id = $("#jqgrid-grid").getCell(ids[key], 'id');
                    $("#jqgrid-grid").jqGrid('setRowData', ids[key], {edit: '<a  href="javascript:editDemandOrder(' + id + ')" class="ui-icon ui-icon-pencil" title="Edit"></a>'});
                }
            },
            onSelectRow: function(rowid, status) {
//                executeEditProduct();
            },
            loadError: function (jqXHR, textStatus, errorThrown) {
                MessageRenderer.renderErrorText(jqXHR.responseText);
            }

        });
        $("#jqgrid-grid").jqGrid('navGrid', '#jqgrid-pager', {edit:false,add:false,del:false,search:false});
//     $("#jqgrid-grid").gridResize({minWidth:350,maxWidth:800,minHeight:200});
//        $(".ui-pg-selbox").children().each(function() {
//            if ($(this).val() == -1) {
//                $(this).html('All')
//            }
//        });
        $("#cb_jqgrid-grid").hide();
    });
    function loadOrderNoAutoComplete(){
        jQuery('#searchOrderKey').autocomplete({
            minLength:'1',
            source:function (request, response) {
                //EmployeeRegister.employeeCoreInfoId = null;
                var data = {searchKey:request.term, isNew: true};
                var url = '${resource(dir:'primaryDemandOrder', file:'listOrderNoAutoComplete')}' ;
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
                accountstype = '<div style="font-size: 9px; color:#326E93;">' +" Order No: " +item.order_no + '</div>';
            }
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + accountstype).appendTo(ul);
        }
    }

    function searchPrimaryDemandOrder(){
        $("#jqgrid-grid").setGridParam({url: '${resource(dir:'primaryDemandOrder', file:'searchNewPrimaryDemandOrder')}?orderNo=' + $('#searchOrderKey').val() + '&status=' + $('#status').val() + '&legacyId=' + $('#legacyId').val() +
                '&orderDateFrom=' + $('#orderDateFrom').val() + '&orderDateTo=' + $('#orderDateTo').val() + "&deliveryDateFrom=" + $("#deliveryDateFrom").val() + "&deliveryDateTo=" + $("#deliveryDateTo").val()});
        $("#jqgrid-grid").trigger("reloadGrid", [{page: 1}]);
    }

    function editDemandOrder(id){
        window.open('${createLink(uri: '/')}primaryDemandOrder/readNewPrimaryDemandOrder?demandOrderId=' + id);
    }

    function cancelPrimaryDemandOrders(){
        var selRowIds = $('#jqgrid-grid').jqGrid('getGridParam', 'selarrrow');
        if(selRowIds.length <=  0){
            MessageRenderer.renderErrorText("Please Select Primary Demand Order");
            return false
        }
        SubmissionLoader.showTo();
        jQuery.ajax({
            type:'post',
            data: {primaryOrderIds: selRowIds.toString()},
            url: "${request.contextPath}/${params.controller}/cancelNewDemandOrder",
            success:function(data, textStatus) {
                if(data.type == 1){
                    $("#jqgrid-grid").trigger("reloadGrid");
                }
                MessageRenderer.render(data);
            },
            error:function(XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                } else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete:function(){
                SubmissionLoader.hideFrom();
            },
            dataType:'json'
        });
        return true
    }
</script>
<div class="main_container">
    <div class="content_container">
        <h1>Search New Primary Demand Order</h1>
        <h3>New Primary Demand order Information</h3>
        <form name='gFormSecondaryDemandOrderSearch' id='gFormSecondaryDemandOrderSearch'>
            <div id="remote-content-territoryConfiguration"></div>
            <br>

            <div class="element_row_content_container lightColorbg pad_bot0">
                <table>
                    <tr >
                        <td>
                            <label class="txtright bold hight1x width1x width100">
                                Primary Order No:
                            </label>

                        </td>
                        <td>
                            <input type="text" id="searchOrderKey" name="searchOrderKey" class="width150" maxlength="20"/>
                        </td>
                        <td>
                            <label class="txtright bold hight1x width1x width160">
                                Select Order Date From:
                            </label>
                        </td>
                        <td>
                            <g:textField name="orderDateFrom" id="orderDateFrom" value="" class="width120" onload="loadDataByOrder();"/>
                        </td>
                        <td>
                            <label class="txtright bold hight1x width60">
                                To:
                            </label>

                        </td>
                        <td>
                            <g:textField name="orderDateTo" id="orderDateTo"  value="" class="width120"/>
                        </td>

                    </tr>
                    <tr >
                        <td>
                            <label class="txtright bold hight1x width1x width100">
                                Status:
                            </label>

                        </td>
                        <td>
                            <g:select class="minWidth125" name="status" id="status" from="${DemandOrderStatus.values()}" noSelection="['':'']"/>
                        </td>
                        <td>
                            <label class="txtright bold hight1x width1x width160">
                                Select Delivery Date From:
                            </label>
                        </td>
                        <td>
                            <g:textField name="deliveryDateFrom" id="deliveryDateFrom" value="" class="width120" onload="loadDataByOrder();"/>
                        </td>
                        <td>
                            <label class="txtright bold hight1x width60">
                                To:
                            </label>

                        </td>
                        <td>
                            <g:textField name="deliveryDateTo" id="deliveryDateTo"  value="" class="width120"/>
                        </td>
                    </tr>
                    <tr >
                        <td>
                            <label class="txtright bold hight1x width1x width100">
                                Legacy ID:
                            </label>

                        </td>
                        <td>
                            <input type="text" id="legacyId" name="legacyId" class="width150" maxlength="20"/>
                        </td>
                        <td colspan="4">
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="buttons">
                                <span class="button"><input type="button" name="add-button" id="add-button"
                                                            class="ui-button ui-widget ui-state-default ui-corner-all"
                                                            value="Search"
                                                            onclick="searchPrimaryDemandOrder();"/></span>
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
<div class="buttons">
    <span class="button"><input type="button" name="cancel-button" id="cancel-button" class="ui-button ui-widget ui-state-default ui-corner-all" value="Cancel Order" onclick="cancelPrimaryDemandOrders();"/></span>
</div>