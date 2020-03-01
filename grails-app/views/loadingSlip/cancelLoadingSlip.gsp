<%--
  Created by IntelliJ IDEA.
  User: mdtofazzal.hossain
  Date: 6/06/2017
  Time: 1:08 PM
--%>

<%@ page import="com.docu.commons.CommonConstants; com.bits.bdfp.util.ApplicationConstants; com.bits.bdfp.inventory.demandorder.PrimaryDemandOrder" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title> Rollback Invoice from Loading Slip</title>

<script type="text/javascript" language="Javascript">
    $(document).ready(function() {

        $("#jqgrid-grid").jqGrid({
            url:'',
            datatype: "local",
            colNames:[
                'Sl',
                'Id',
                'Invoice No',
                'Amount',
                'Customer',
                'Primary Order No',
                'Order Status',
                'Invoice Date',
                'Loading Slip No',
                'Loading Slip Date'
            ],
            colModel:[
                {name:'sl',index:'sl', width:30,align:'center'},
                {name:'id',index:'id', width:0, hidden:true},
                {name:'invoiceNo', index:'invoiceNo',width:80,align:'center'},
                {name:'invoiceAmount', index:'invoiceAmount',width:80,align:'right'},
                {name:'customer', index:'customer',width:180,align:'left'},
                {name:'primaryOrderNo', index:'primaryOrderNo',width:90,align:'center'},
                {name:'orderStatus', index:'orderStatus',width:110,align:'left'},
                {name:'invoiceDate', index:'invoiceDate',width:100,align:'center'},
                {name:'loadingSlipNo', index:'loadingSlipNo',width:100,align:'center'},
                {name:'loadingSlipDate', index:'loadingSlipDate',width:100,align:'center'}
            ],
            rowNum: 10,
            rowList: [10, 20, -1],
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Loading Slip Information",
            autowidth: false,
            scrollOffset: 0,
            loadComplete: function() {
            },
            onSelectRow: function(rowid, status) {
            }
        });

    });

    function getLoadingSlip(){
        var invoiceNo = $('#searchOrderKey').val();
        if(invoiceNo==""){
            MessageRenderer.renderErrorText("Please enter invoice number.");
            return false;
        }
        $("#jqgrid-grid").setGridParam({url: '${resource(dir:'loadingSlip', file:'getLoadingSlipByInvoiceNumber')}?invoiceNo=' + $('#searchOrderKey').val(), datatype: "json"});
        $("#jqgrid-grid").trigger("reloadGrid");
    }

    function filterListOfIds(idsArray){
        var ids = [];
        $.each(idsArray, function(i, e) {
            if ($.inArray(e, ids) == -1) ids.push(e);
        });
        ids = ids.filter(function(n){ return n != undefined });
        return ids;
    }

    function executeAjaxForRollbackLoadingSlip() {
        var invoiceCode = $('#searchOrderKey').val();
        var ids = $("#jqgrid-grid").jqGrid('getDataIDs');
        var loadingSlipId = filterListOfIds(ids);
        if (loadingSlipId == '') {
            $("#dialog").dialog("destroy");
            $("#dialog-print-selection").dialog({
                resizable: false,
                height: 150,
                modal: true,
                title: 'Loading Slip Selection Missing',
                buttons: {
                    Ok: function () {
                        $(this).dialog('close');
                    }
                }
            }); //end of dialog
            return false
        }

        $("#custom_message").html("Are you sure.. You want to rollback loading slip?");
        $("#dialog-confirm-processed").dialog({
            resizable: false,
            height: 150,
            modal: true,
            title: 'Confirm Dialog',
            buttons: {
                Ok: function () {
                    $(this).dialog('close');
                    jQuery.ajax({
                        url: "${resource(dir:'loadingSlip', file:'deleteLoadingSlipByInvoiceNumber')}?loadingSlipId="+loadingSlipId+ "&invoiceCode=" + invoiceCode,
                        type: "POST",
                        dataType: "json",
                        beforeSend: function () {
                        },
                        success: function (result) {
                            $("#jqgrid-grid").trigger("reloadGrid");
                            MessageRenderer.render(result);
                        },
                        complete: function () {
                        }
                    });
                },
                Cancel: function () {
                    $(this).dialog('close');
                }
            }
        }); //end of dialog
        return false;
    }

</script>
<div class="main_container">
    <div class="content_container">
        <h1> Rollback Invoice From Loading Slip </h1>
        <h3> Loading Slip Information </h3>
        <form name='gFormSecondaryDemandOrderSearch' id='gFormSecondaryDemandOrderSearch'>
            <div id="remote-content-territoryConfiguration"></div>
            <br>

            <div class="element_row_content_container lightColorbg pad_bot0">
                <table>
                    <tr>
                        <td>
                            <label class="txtright bold hight1x width1x" style="width: 150px;">
                               Invoice Number:
                                <span class="mendatory_field">*</span>
                            </label>

                        </td>

                        <td><input type="text" id="searchOrderKey" class="validate[required]" name="searchOrderKey" class="width120"/>
                            %{--<input type="hidden" id="invoiceNumber" class="validate[required]" name="invoiceNumber"/>--}%

                        </td>
                    </tr>

                    <tr>
                        <td>
                            <div class="buttons">
                                <span class="button">
                                    <input type="button" name="add-button" id="add-button"
                                                            class="ui-button ui-widget ui-state-default ui-corner-all"
                                                            value="Search"
                                                            onclick="getLoadingSlip();"/>
                                </span>
                            </div>
                        </td>
                    </tr>
                </table>

                <div class="jqgrid-container">
                    <table id="jqgrid-grid"></table>

                    %{--<div id="jqgrid-pager"></div>--}%
                </div>
            </div>
            <div class="clear"></div>
            <div class="buttons">
                <span class="button"><input type="button" name="print" id="print-button"
                                            class="ui-button ui-widget ui-state-default ui-corner-all" value="Rollback"
                                            onclick="executeAjaxForRollbackLoadingSlip();"/></span>
            </div>
        </form>
        <div id="dialog-confirm-processed" title="Confirm alert" style="display: none">
            <p><span class="ui-icon ui-icon-alert delete-alert-margin content-left">

            </span><span id="custom_message"> Are you sure.. You want to rollback loading slip? </span>
            </p>
        </div>
        <div id="dialog-print-selection" style="display: none">
            <p>Please select at least one Loading Slip.</p>
        </div>
    </div>
</div>