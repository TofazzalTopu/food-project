<%--
  Created by IntelliJ IDEA.
  User: mdalinaser.khan
  Date: 1/12/16
  Time: 3:28 PM
--%>
<%@ page import="com.docu.commons.CommonConstants; com.bits.bdfp.inventory.retailorder.RetailOrder" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title>Search Secondary Order</title>
<style>
#gview_jqgrid-grid-retailOrder .ui-state-highlight { background: yellow !important; }
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
        $("#jqgrid-grid-secondaryDemandOrder").jqGrid({
            url: '${resource(dir:'consolidateRetailOrder', file:'list')}',
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
                {name: 'sl', index: 'sl', width: 30, sortable: true, align: 'center'},
                {name: 'id', index: 'id', width: 0, hidden: true},
                {name: 'orderNo', index: 'orderNo', width: 70, align: 'center'},
                {name: 'orderDate', index: 'orderDate', width: 60, align: 'center'},
                {name: 'customerName', index: 'customerName', width: 150, align: 'left'},
                {name: 'legacyId', index: 'legacyId', width: 100, align: 'left'},
                {name: 'customerCode', index: 'customerCode', width: 80, align: 'center'},
                {name: 'enterprise', index: 'enterprise', width: 110, align: 'left'},
                {name: 'deliveryDate', index: 'deliveryDate', width: 60, align: 'center'}
            ],
            rowNum: 10,
            rowList: [10, 20, 30],
            pager: '#jqgrid-pager-secondaryDemandOrder',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Secondary Order List",
            autowidth: true,
            autoheight: true,
            scrollOffset: 0,
            altRows: true,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                executeEditSecondaryOrder(rowid);
            }
        });
        loadSecondaryOrderAutoComplete();
    });
    function loadSecondaryOrderAutoComplete() {
        jQuery('#orderNoSearch').autocomplete({
            minLength:'1',
            source:function (request, response) {
                //EmployeeRegister.employeeCoreInfoId = null;
                var data = {searchKey: request.term};
                var url = '${resource(dir:'consolidateRetailOrder', file:'secondaryOrderAutoComplete')}';
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
    function executeSecondaryOrderSearch() {
        // Get Searched data
        $("#jqgrid-grid-secondaryDemandOrder").jqGrid().setGridParam({url: "${resource(dir:'consolidateRetailOrder', file:'list')}?"
                + 'orderNo=' + $('#orderNoSearch').val() + '&orderDate=' + $('#orderDateSearch').val(),
            datatype: "json"
        }).trigger("reloadGrid", [
                    {page: 1}
                ]);
    }
    function executePreConditionForEditSecondaryOrder(secondaryOrderId) {
        if (secondaryOrderId == null) {
            alert("Please select a Secondary Order to edit");
            return false;
        }
        return true;
    }
    function executeEditSecondaryOrder(secondaryOrderId) {
        if (executePreConditionForEditSecondaryOrder(secondaryOrderId)) {
            $("#updateSecondaryOrderDiv").html("");
            SubmissionLoader.showTo();
            $.ajax({
                type: "POST",
                url: "${resource(dir:'consolidateRetailOrder', file:'edit')}?id=" + secondaryOrderId,
                success: function (entity) {
                    executePostConditionForEditSecondaryOrder(entity);
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    if(XMLHttpRequest.status = 0){

                        MessageRenderer.renderErrorText("Network Problem: Time out");
                    }
                    else{
                        MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                    }
                },
                complete: function(){
                    SubmissionLoader.hideFrom();
                },
                dataType: 'html'
            });
        }
    }
    function executePostConditionForEditSecondaryOrder(data) {
        if (data == null) {
            alert('Selected Secondary Order might have been deleted by someone');  //Message renderer
        } else {
            $("#updateSecondaryOrderDiv").html(data);
        }
    }
    function executeAjaxSubmitAllSecondaryOrder(){
        var secondaryOrderIds = $('#jqgrid-grid-secondaryDemandOrder').jqGrid('getDataIDs');
        if(secondaryOrderIds.length > 0){
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
                            data: {secondaryOrderIds: secondaryOrderIds.toString()},
                            url: "${resource(dir:'consolidateRetailOrder', file:'submitSecondaryOrders')}",
                            success: function (message) {
                                if (message.type == 1) {
                                    $("#jqgrid-grid-secondaryDemandOrder").trigger("reloadGrid");
                                }
                                MessageRenderer.render(message)
                            },
                            error: function (XMLHttpRequest, textStatus, errorThrown) {
                                if(XMLHttpRequest.status = 0){
                                    $("#jqgrid-grid-secondaryDemandOrder").trigger("reloadGrid");
                                    MessageRenderer.renderErrorText("Network Problem: Time out");
                                }
                                else{
                                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                                }
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
        <h3>Search Secondary Order</h3>
        <table>
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label class='txtright bold hight1x width130'>
                        Secondary Order No:
                    </label>
                </td>
                <td>
                    <div class='element_row_container inputContainer '>
                        <g:textField name="orderNoSearch" maxlength="20" value=""/>
                    </div>
                </td>
                <td>
                    <label class="txtright bold hight1x width130">
                        Secondary Order Date:
                    </label>
                </td>
                <td>
                    <div class='element_row_container inputContainer'>
                        <g:textField name="orderDateSearch" id="orderDateSearch" class="width100"/>
                        <span class="button"><input type="button" name="search-button" id="search-button-retailOrder"
                                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                                    value="Search"
                                                    onclick="executeSecondaryOrderSearch();"/></span>
                    </div>
                </td>
            </tr>
        </table>
        <div class="jqgrid-container blue_grid">
            <table id="jqgrid-grid-secondaryDemandOrder"></table>
            <div id="jqgrid-pager-secondaryDemandOrder"></div>
        </div>
        <div class="buttons" style="margin-left:10px;">
            <span class="button"><input type="button" name="submit-all-button" id="submit-all-button-retailOrder"
                                        class="ui-button ui-widget ui-state-default ui-corner-all"
                                        value="Submit All"
                                        onclick="executeAjaxSubmitAllSecondaryOrder();"/></span>
        </div>
        <div id="updateSecondaryOrderDiv"></div>
        <div id="dialog-confirm-submitOrder" title="Confirmation alert" style="display: none;">
            <p><span class="ui-icon ui-icon-alert"
                     style="float:left; margin:0 7px 20px 0;"></span>All visible Secondary Orders will be submitted. Are you sure?
            </p>
        </div>

    </div>
</div>