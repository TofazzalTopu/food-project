<%--
  Created by IntelliJ IDEA.
  User: mdalinaser.khan
  Date: 1/12/16
  Time: 3:28 PM
--%>
<%@ page import="com.docu.commons.CommonConstants; com.bits.bdfp.inventory.retailorder.RetailOrder" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title>Search Retail Order</title>
<style>
#gview_jqgrid-grid-retailOrder .ui-state-highlight { background: limegreen !important; }
</style>
<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormRetailOrder").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#orderDateSearch").datepicker( {
            dateFormat: 'dd-mm-yy',
            changeMonth: true,
            changeYear: true,
            maxDate: new Date()
        });

        %{--$('#orderDateSearch').mask('${CommonConstants.DATE_MASK_FORMAT}', {});--}%
        $("#jqgrid-grid-retailOrder").jqGrid({
            url: '${resource(dir:'retailOrder', file:'list')}',
            datatype: "json",
            colNames: [
                'SL',
                'ID',
                'Order No',
                'Order Date',
                'Customer Name',
                'Customer Legacy ID',
                'Customer ERP ID',
                'Enterprise',
                'Delivery Date'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 20, sortable: true, align: 'center'},
                {name: 'id', index: 'id', width: 0, hidden: true},
                {name: 'orderNo', index: 'orderNo', width: 80, align: 'center'},
                {name: 'orderDate', index: 'orderDate', width: 60, align: 'center'},
                {name: 'customerName', index: 'customerName', width: 150, align: 'left'},
                {name: 'legacyId', index: 'legacyId', width: 100, align: 'left'},
                {name: 'customerCode', index: 'customerCode', width: 80, align: 'center'},
                {name: 'enterprise', index: 'enterprise', width: 110, align: 'left'},
                {name: 'deliveryDate', index: 'deliveryDate', width: 60, align: 'center'}
            ],
            rowNum: 10,
            rowList: [10, 20, 30],
            pager: '#jqgrid-pager-retailOrder',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Retail Order List",
            autowidth: true,
            autoheight: true,
            scrollOffset: 0,
            altRows: true,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                executeEditRetailOrder(rowid);
            }
        });
        loadRetailOrderAutoComplete();
    });
    function loadRetailOrderAutoComplete() {
        jQuery('#orderNoSearch').autocomplete({
            minLength:'1',
            source:function (request, response) {
                //EmployeeRegister.employeeCoreInfoId = null;
                var data = {searchKey: request.term};
                var url = '${resource(dir:'retailOrder', file:'retailOrderAutoComplete')}';
                DocuAutoComplete.setSpinnerSelector('searchKey').execute(response, url, data, function (item) {
                    item['label'] =  item['orderNo'];
                    item['value'] = item['label'];
                    return item;
                });
            },
            select:function (event, ui) {
                $("#orderNoSearch").val(ui.item.orderNo);
            }
        }).data("autocomplete")._renderItem = function (ul, item) {
            var orderDetails = "";
            if (item) {
                orderDetails = '<div style="font-size: 9px; color:#326E93;">' + "Customer Legacy ID: " + item.legacyId +", Customer Code: " +item.customerCode + ", Customer Name: " + item.customerName + '</div>';
            }
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + orderDetails).appendTo(ul);
        };
    }
    function executeRetailOrderSearch() {
        // Get Searched data
        $("#jqgrid-grid-retailOrder").jqGrid().setGridParam({url: "${resource(dir:'retailOrder', file:'list')}?"
                + 'orderNo=' + $('#orderNoSearch').val() + '&orderDate=' + $('#orderDateSearch').val(),
            datatype: "json"
        }).trigger("reloadGrid", [
                    {page: 1}
                ]);
    }
    function executePreConditionForEditRetailOrder(retailOrderId) {
        if (retailOrderId == null) {
            alert("Please select a Retail Order to edit");
            return false;
        }
        return true;
    }
    function executeEditRetailOrder(retailOrderId) {
        if (executePreConditionForEditRetailOrder(retailOrderId)) {
            $("#updateRetailOrderDiv").html("");
            SubmissionLoader.showTo();
            $.ajax({
                type: "POST",
                url: "${resource(dir:'retailOrder', file:'edit')}?id=" + retailOrderId,
                success: function (entity) {
                    executePostConditionForEditRetailOrder(entity);
                },
                complete: function(){
                    SubmissionLoader.hideFrom();
                },
                dataType: 'html'
            });
        }
    }
    function executePostConditionForEditRetailOrder(data) {
        if (data == null) {
            alert('Selected Retail Order might have been deleted by someone');  //Message renderer
        } else {
            $("#updateRetailOrderDiv").html(data);
        }
    }
    function executeAjaxSubmitAllRetailOrder(){
        var allRetailOrderIds = $('#jqgrid-grid-retailOrder').jqGrid('getDataIDs');
        if(allRetailOrderIds.length > 0){
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-submitOrder").dialog({
                resizable: false,
                height: 150,
                modal: true,
                title: 'Confirmation alert',
                buttons: {
                    'Submit order(s)': function () {
                        $(this).dialog('close');
                        SubmissionLoader.showTo();
                        $.ajax({
                            type: "POST",
                            dataType: "json",
                            data: {retailOrderIds: allRetailOrderIds.toString()},
                            url: "${resource(dir:'retailOrder', file:'submitOrders')}",
                            success: function (message) {
                                if (message.type == 1) {
                                    $("#jqgrid-grid-retailOrder").trigger("reloadGrid");
                                }
                                MessageRenderer.render(message)
                            },
                            complete: function(){
                                SubmissionLoader.hideFrom();
                            }
                        });
                    },
                    Cancel: function () {
                        $(this).dialog('close');
                    }
                }
            }); //end of dialog
        }
    }
</script>
<div class="main_container" style="width: 950px">
    <div class="content_container width950">
        <h3>Search Retail Order</h3>
        <table>
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label class='txtright bold hight1x width100'>
                        Retail Order No:
                    </label>
                </td>
                <td>
                    <div class='element_row_container inputContainer '>
                        <g:textField name="orderNoSearch" maxlength="20" value=""/>
                    </div>
                </td>
                <td>
                    <label class="txtright bold hight1x width130">
                        Retail Order Date:
                    </label>
                </td>
                <td>
                    <div class='element_row_container inputContainer'>
                        <g:textField name="orderDateSearch" id="orderDateSearch" class="width100"/>
                        <span class="button"><input type="button" name="search-button" id="search-button-retailOrder"
                                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                                    value="Search"
                                                    onclick="executeRetailOrderSearch();"/></span>
                    </div>
                </td>
            </tr>
        </table>
        <div class="jqgrid-container blue_grid">
            <table id="jqgrid-grid-retailOrder"></table>
            <div id="jqgrid-pager-retailOrder"></div>
        </div>
        <div class="buttons" style="margin-left:10px;">
            <span class="button"><input type="button" name="submit-all-button" id="submit-all-button-retailOrder"
                                        class="ui-button ui-widget ui-state-default ui-corner-all"
                                        value="Submit All"
                                        onclick="executeAjaxSubmitAllRetailOrder();"/></span>
        </div>
        <div id="updateRetailOrderDiv"></div>
        <div id="dialog-confirm-submitOrder" title="Confirmation alert" style="display: none;">
            <p><span class="ui-icon ui-icon-alert"
                style="float:left; margin:0 7px 20px 0;"></span>All visible Retail Orders will be submitted. Are you sure?
            </p>
        </div>

    </div>
</div>