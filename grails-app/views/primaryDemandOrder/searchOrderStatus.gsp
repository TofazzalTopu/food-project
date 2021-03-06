<%@ page import="com.bits.bdfp.inventory.demandorder.DemandOrderStatus; com.docu.commons.CommonConstants; com.bits.bdfp.util.ApplicationConstants; com.bits.bdfp.inventory.demandorder.PrimaryDemandOrder" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title>View Primary Order Status</title>

<script type="text/javascript" language="Javascript">
    $(document).ready(function() {
        loadOrderNo();
        var isNew = $('#isNew').val();
        $("#jqgrid-grid").jqGrid({
            url:'${request.contextPath}/${params.controller}/orderStatusList?isNew=' + isNew,
//            data: {isNew: isNew},
//            type: "POST",
            datatype: "json",
            colNames:[
                'Sl',
                'Id',
                'Primary Order No',
                'Order Date',
                'Proposed Delivery Date',
                'Expected Delivery Date',
                'Status',
                'Customer',
                'Ship To Address',
                'Last Approved By',
                'Is New',
                'Approval History'

            ],
            colModel:[
                {name:'sl',index:'sl', width:20, align:'center'},
                {name:'id',index:'id', width:0, hidden:true},
                {name:'order_no', index:'order_no',width:60,align:'center'},
                {name:'date_order', index:'date_order',width:50,align:'center'},
                {name:'proposed_date', index:'proposed_date',width:60,align:'center'},
                {name:'expected_date', index:'expected_date',width:60,align:'center'},
                {name:'demand_order_status', index:'demand_order_status',width:60,align:'left'},
                {name:'customer', index:'customer',width:130,align:'left'},
                {name:'address', index:'address',width:70,align:'left'},
                {name:'username', index:'username',width:120,align:'left'},
                {name:'is_new', index:'is_new',width:40,align:'center'},
                {name:'edit', index:'edit',width:80,align:'center'}
            ],
            rowNum:50,
            rowList:[10,20,30,40,50,60,70,80,90,100,-1],
            pager: '#jqgrid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption:"Primary Order Status List",
            autowidth: true,
            height: 250,

            scrollOffset: 0,
            loadComplete: function() {
                var ids = $("#jqgrid-grid").jqGrid('getDataIDs');
                allEmail = '';
                allMobile = '';
                for (key in ids) {
                    var id = $("#jqgrid-grid").getCell(ids[key], 'id');
                    $("#jqgrid-grid").jqGrid('setRowData', ids[key], {edit: '<a  href="javascript:loadApprovalInfo(' + id + ')">' + 'Approval History' + '</a>'});
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

        setDateRangeNoLimit('orderDateFrom','orderDateTo');
        setDateRangeNoLimit('deliveryDateFrom','deliveryDateTo');

//        $("#searchOrderKey").blur(function() {
//            alert($("#searchOrderKey").va());
//            if($("#searchOrderKey").va() == ""){
//                $("#name").val("");
//            }
//        });

        $('#searchOrderKey').bind('blur', function () {
            alert($("#searchOrderKey").va());
            if($("#searchOrderKey").va() == ""){
                $("#name").val("");
            }
        })
    });
    function addRemoveCheckBoxForNew(object) {
        if (object.checked) {
            object.value = 'true';
        }
        else {
            object.value = 'false';
        }
    }
    function onBlurOrderNo(){
        if($("#searchOrderKey").val().length == 0){
            $("#name").val('');
        }
    }
    function loadOrderNo(){
        jQuery('#searchOrderKey').autocomplete({
            minLength:'1',
            source:function (request, response) {
                //EmployeeRegister.employeeCoreInfoId = null;
                $('#popEmpDetails').html("");
                var data = {searchKey:request.term, isNew:$("#isNew").val()};
                var url = '${resource(dir:'primaryDemandOrder', file:'listOrderNoAutoComplete')}' ;
                DocuAutoComplete.setSpinnerSelector('searchOrderKey').execute(response, url, data, function (item) {
                    item['label'] = item['order_no'];
                    item['value'] = item['label'];
                    return item;
                });
            },
            select:function (event, ui) {
                $("#searchOrderKey").val(ui.item.order_no);
                $("#name").val(ui.item.name);
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
        $("#jqgrid-grid").setGridParam({url: '${resource(dir:'primaryDemandOrder', file:'orderStatusList')}?orderNo=' + $('#searchOrderKey').val()
            + '&orderDateFrom=' + $('#orderDateFrom').val() + '&orderDateTo=' + $('#orderDateTo').val() + '&isNew=' + $('#isNew').val()  + '&status=' + $('#status').val()
            + '&deliveryDateFrom=' + $('#deliveryDateFrom').val() + '&deliveryDateTo=' + $('#deliveryDateTo').val()});
        $("#jqgrid-grid").trigger("reloadGrid",[{page:1}]);
    }

    function loadApprovalInfo(id){
        ApprovalInfo.popupApprovalListPanel(id)
    }

    var ApprovalInfo = {
        orderInfoId: null,
        popupApprovalListPanel: function(orderInfoId){
            var url = '${resource(dir:'primaryDemandOrder', file:'popupApprovalListPanel')}' ;
            var params = {primaryOrderId:orderInfoId};
            DocuAjax.html(url, params, function(html){
                $.fancybox(html);
            });
        }
    }
</script>
<div class="main_container" style="width: 1000px">
    <div class="content_container">
        <div style="width:100%;">
        <h1>View Primary Demand Order Status</h1>
        <h3>Search Primary Demand order Status</h3>
        <form name='gFormSecondaryDemandOrderSearch' id='gFormSecondaryDemandOrderSearch'>
            <g:hiddenField name="id" value="${secondaryDemandOrder?.id}" />
            <g:hiddenField name="version" value="${secondaryDemandOrder?.version}" />
            <g:hiddenField name="idEnterprise" id="idEnterprise" value=""/>
            <g:hiddenField name="idBusiness" id="idBusiness" value=""/>
            <div id="remote-content-territoryConfiguration"></div>
            <br>

            <div class="element_row_content_container lightColorbg pad_bot0">
                <table>
                    <script type="text/javascript">
                        jQuery(document).ready(function () {
                            $("#orderDateFrom, #orderDateTo,#deliveryDateFrom,#deliveryDateTo").datepicker(
                                    { dateFormat: 'dd-mm-yy',
                                        changeMonth:true,
                                        changeYear:true
                                    });
                            $('#orderDateFrom').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});
                            $('#orderDateTo').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});
                            $('#deliveryDateFrom').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});
                            $('#deliveryDateTo').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});
                        });
                    </script>
                    <tr>
                        <td>
                            <label class="txtright bold hight1x width150">
                                Select Order Date From:
                            </label>
                        </td>

                        <td>
                            <g:textField name="orderDateFrom" id="orderDateFrom" value="" class="width120" />
                        </td>
                        <td>
                            <label class="txtright bold hight1x width100">
                                To:
                            </label>
                        </td>
                        <td>
                            <g:textField name="orderDateTo" id="orderDateTo"  value="" class="width120"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label class="txtright bold hight1x width150">
                                Select Delivery Date From:
                            </label>
                        </td>

                        <td>
                            <g:textField name="deliveryDateFrom" id="deliveryDateFrom" value="" class="width120" />
                        </td>
                        <td>
                            <label class="txtright bold hight1x width100">
                                To:
                            </label>

                        </td>
                        <td>
                            <g:textField name="deliveryDateTo" id="deliveryDateTo"  value="" class="width120"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label class="txtright bold hight1x width150">
                                Primary Order No:
                            </label>

                        </td>

                        <td><input type="text" id="searchOrderKey" name="searchOrderKey" class="width120" onblur="onBlurOrderNo();"/>
                            <input type="hidden" id="productId" name="productId"/>
                            <input type="hidden" id="productCode" />
                            <input type="hidden" id="product" />
                        </td>
                        <td>
                            <label class="txtright bold hight1x width100">
                                Customer:
                            </label>
                        </td>
                        <td>
                            <g:textField name="name" id="name"  value="" class="width250" readonly="readonly"/>

                        </td>
                    </tr>
                    <tr>

                        <td>
                            <label class="txtright bold hight1x width150">
                                Is New:
                            </label>

                        </td>

                        <td><input type="checkbox" id="isNew" value="false" onclick="addRemoveCheckBoxForNew(this)"/>
                        </td>
                        <td>
                            <label class="txtright bold hight1x width100">
                                Status:
                            </label>

                        </td>
                        <td>
                            <g:select name="status" id="status" from="${DemandOrderStatus.values()}" noSelection="['':'']"/>
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
</div>