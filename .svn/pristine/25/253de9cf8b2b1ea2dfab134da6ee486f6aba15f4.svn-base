<%@ page import="com.docu.commons.CommonConstants; com.bits.bdfp.util.ApplicationConstants; com.bits.bdfp.inventory.demandorder.PrimaryDemandOrder" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title> Rollback Primary Invoice</title>

<script type="text/javascript" language="Javascript">
    $(document).ready(function() {
        $("#jqgrid-grid").jqGrid({
         /*   url:'${request.contextPath}/${params.controller}/primaryInvoiceListForRollback',*/
            url:'',
            datatype: "json",
            colNames:[
                'Select',
                'Sl',
                'Id',
                'Primary Order Number',
                'Invoice Number',
                'Created By',
                'Customer',
                'Invoice Amount',
                'Invoiced Date',
                'Status'
            ],
            colModel:[
                {name:'edit', index:'edit', width:50, align:'center', hidden:true},
                {name:'sl',index:'sl', width:30},
                {name:'id',index:'id', width:0, hidden:true},
                {name:'order_no',index:'order_no', width:100, align:'center'},
                {name:'invoice_no', index:'invoice_no',width:110,align:'center'},
                {name:'createdBy', index:'createdBy',width:150,align:'left'},
                {name:'customer', index:'customer',width:140,align:'left'},
                {name:'invoiceAmount', index:'invoiceAmount',width:100,align:'right',
                    formatter:'currency',
                    formatoptions:{defaulValue:0,thousandsSeparator:',',decimalPlaces:2}
                },
                {name:'date_created', index:'date_created', width:90,align:'center',
                    formatter : 'date', formatoptions : {newformat : 'd-m-Y'}
                },
                {name:'demand_order_status', index:'demand_order_status', width:100,align:'left'}
            ],
            rowNum:1,
            rowList:[1,-1],
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption:"Received Primary Invoice Information Details",
            autowidth: true,
            height: true,
            scrollOffset: 0,
            loadComplete: function() {
            },
            onSelectRow: function(rowid, status) {
            },
            loadError: function (jqXHR, textStatus, errorThrown) {
            }
        });
    });

    function loadDataByInvoiceNumber(){
        var invoiceNo = $('#searchOrderKey').val();
        if(invoiceNo == ''){
            MessageRenderer.renderErrorText("Please enter invoice number");
            return false;
        }

        $("#jqgrid-grid").setGridParam({url: '${resource(dir:'primaryDemandOrder', file:'primaryInvoiceListForRollback')}?invoiceCode=' + invoiceNo});
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

    function executeAjax() {
        var ids = $("#jqgrid-grid").jqGrid('getDataIDs');

        var invoiceID = filterListOfIds(ids);

        if (invoiceID == '') {
            $("#dialog").dialog("destroy");
            $("#dialog-print-selection").dialog({
                resizable: false,
                height: 150,
                modal: true,
                title: 'Invoice Selection Missing',
                buttons: {
                    Ok: function () {
                        $(this).dialog('close');
                    }
                }
            }); //end of dialog
            return false
        }

        $("#custom_message").html("Received Primary Invoice will be Rollback. Are you sure?");

        $("#dialog-confirm-processed").dialog({
            resizable: false,
            height: 150,
            modal: true,
            title: 'Confirm Dialog',
            buttons: {
                Ok: function () {
                    $(this).dialog('close');
                    SubmissionLoader.showTo();
                    jQuery.ajax({
                        url: "${resource(dir:'primaryDemandOrder', file:'rollbackReceivedPrimaryInvoice')}?invoiceID="+invoiceID,
                        type: "POST",
                        dataType: "json",
                        beforeSend: function () {
                        },
                        success: function (result) {
                            if (result.type == 1) {
                                $("#jqgrid-grid").trigger("reloadGrid");
                            }
                            MessageRenderer.render(result);
                        },
                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                            if(XMLHttpRequest.status = 0){
                                $("#jqgrid-grid").trigger("reloadGrid");
                                MessageRenderer.renderErrorText("Network Problem: Time out");
                            }
                            else{
                                MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                            }
                        },
                        complete: function () {
                            SubmissionLoader.hideFrom();
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
        <h1> Rollback Received Primary Invoice</h1>
        <h3> Rollback Received Primary Invoice</h3>
        <form name='gFormSecondaryDemandOrderSearch' id='gFormSecondaryDemandOrderSearch'>
            <div id="remote-content-territoryConfiguration"></div>
            <br>

            <div class="element_row_content_container lightColorbg pad_bot0">
                <table>

                    <tr>

                        <td>
                            <label class="txtright bold hight1x width150">
                                Invoice Number:
                            </label>

                        </td>

                        <td><input type="text" id="searchOrderKey" name="searchOrderKey" class="width120"/>
                        </td>

                    </tr>

                    <tr>
                        <td>
                            <div class="buttons">
                                <span class="button"><input type="button" name="add-button" id="add-button"
                                                            class="ui-button ui-widget ui-state-default ui-corner-all"
                                                            value="Search"
                                                            onclick="loadDataByInvoiceNumber();"/></span>

                            </div>
                        </td>
                    </tr>
                </table>

                <div class="jqgrid-container">
                    <table id="jqgrid-grid"></table>
                </div>
            </div>
            <div class="clear"></div>
            <div class="buttons">
                <span class="button"><input type="button" name="Print Invoice" id="print-button"
                                            class="ui-button ui-widget ui-state-default ui-corner-all" value="Rollback"
                                            onclick="executeAjax();"/></span>
            </div>
        </form>
        <div id="dialog-confirm-processed" title="Confirm alert" style="display: none">
            <p><span class="ui-icon ui-icon-alert delete-alert-margin content-left">

            </span><span id="custom_message">Received Primary Invoice will be Rollback. Are you sure?</span>
            </p>
        </div>
        <div id="dialog-print-selection" style="display: none">
            <p>Please select at least one invoice</p>
        </div>
        <div id="dialog-print-selection2" style="display: none">
            <p>Please select only one invoice</p>
        </div>
    </div>
</div>