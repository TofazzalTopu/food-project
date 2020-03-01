<%--
  Created by IntelliJ IDEA.
  User: mdtofazzal.hossain
  Date: 3/6/2017
  Time: 11:23 AM
--%>


<%@ page import="com.docu.commons.CommonConstants; com.bits.bdfp.util.ApplicationConstants; com.bits.bdfp.inventory.demandorder.PrimaryDemandOrder" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title>Cancel Secondary Invoice</title>

<script type="text/javascript" language="Javascript">
    var print=0;
    $(document).ready(function() {
            loadOrderNo();
            $("#jqgrid-grid").jqGrid({
            url:'${request.contextPath}/${params.controller}/viewCancelSecondaryInvoiceList',
            datatype: "json",
            colNames:[
                'Select',
                'Sl',
                'Id',
                'Secondary Order Number',
                'Invoice Number',
                'Created By',
                'Customer',
                'Invoiced Amount',
                'Invoiced Date',
                'Status'
//                'Select'
            ],
            colModel:[
                {name:'edit', index:'edit', width:50, align:'center'},
                {name:'sl',index:'sl', width:30},
                {name:'id',index:'id', width:0, hidden:true},
                {name:'order_no',index:'order_no', width:100, hidden:false,align:'center'},
                {name:'invoice_no', index:'invoice_no',width:100,align:'center'},
                {name:'createdBy', index:'createdBy',width:100,align:'left'},
                {name:'customer', index:'customer',width:130,align:'left'},
                {name:'invoiceAmount', index:'invoiceAmount',width:80,align:'right', formatter:'currency', formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2}},
                {name:'date_created', index:'date_created', width:90,align:'center',
                    formatter : 'date', formatoptions : {newformat : 'd-m-Y'}
                },
                {name:'demand_order_status', index:'demand_order_status', width:100,align:'left'}
//                {name:'edit', index:'edit', width:50, align:'center'}
            ],
            rowNum:10,
            rowList:[10,20,30,40,50,60,70,80,90,100,-1],
            pager: '#jqgrid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption:"All Secondary Invoice Information",
            autowidth: true,
            height: true,
            scrollOffset: 0,
            loadComplete: function() {
                var ids = $("#jqgrid-grid").jqGrid('getDataIDs');
                allEmail = '';
                allMobile = '';
                for (key in ids) {
                    var id = $("#jqgrid-grid").getCell(ids[key], 'id');
                    $("#jqgrid-grid").jqGrid('setRowData', ids[key], {edit: '<input type="checkbox" id="print-checkbox' + id + '" class="print-checkbox" value="' + id + '" />'});

                    $('.print-checkbox').each(function () {
                        var printId= $(this).val();
                        var print= $("#jqgrid-grid").getCell(printId, 'print_status');
                        if (print>0) {
                            //this.checked = true; Check box off
                        }
                    })
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

    });
    function loadOrderNo(){
        jQuery('#searchOrderKey').autocomplete({
            minLength:'1',
            source:function (request, response) {
                //EmployeeRegister.employeeCoreInfoId = null;
                $('#popEmpDetails').html("");
                var data = {searchKey:request.term};
                var url = '${resource(dir:'secondaryDemandOrder', file:'listPrintInvoiceNoAutoComplete')}' ;
                DocuAutoComplete.setSpinnerSelector('searchOrderKey').execute(response, url, data, function (item) {
                    item['label'] = item['invoice_no'];
                    item['value'] = item['label'];
                    return item;
                });
            },
            select:function (event, ui) {
                $("#searchOrderKey").val(ui.item.invoice_no);
                $("#name").val(ui.item.name);
            }
        }).data("autocomplete")._renderItem = function (ul, item) {
            var accountstype = "";
            if (item) {
                accountstype = '<div style="font-size: 9px; color:#326E93;">' + " Order No: " + item.invoice_no + " Customer: " +  item.name + '</div>';
            }
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + accountstype).appendTo(ul);

        }
    }
    function loadDataByOrder(){
        $("#jqgrid-grid").setGridParam({url: '${resource(dir:'primaryDemandOrder', file:'viewCancelSecondaryInvoiceList')}?orderNo=' + $('#searchOrderKey').val()
        +'&orderDateFrom='+$('#orderDateFrom').val()+'&orderDateTo='+$('#orderDateTo').val()
        });
        $("#jqgrid-grid").trigger("reloadGrid");

    }

    function loadApprovalInfo(id){
        ApprovalInfo.popupApprovalListPanel(id)
    }
    var ApprovalInfo = {
        orderInfoId: null,
        popupApprovalListPanel: function(orderInfoId){
            var url = '${resource(dir:'primaryDemandOrder', file:'popupApprovalListPanel')}' ;
            var params = {id:orderInfoId};
            DocuAjax.html(url, params, function(html){

                $.fancybox(html);
            });
        }
    };

    function selectAllApplicant() {
        if ($('#select-button').val() == 'Select All') {
            $('.print-checkbox').each(function () {
                this.checked = true;
                $('#select-button').attr('value', 'Deselect All')
            })
        }
        else {
            $('.print-checkbox').each(function () {
                this.checked = false;
                $('#select-button').attr('value', 'Select All')
            })
        }
    }

    function executeAjax() {
//        debugger
        var invoiceNo = '';
        var allIds = {};
        var i = 0;
        $('.print-checkbox').each(function () {
            if (this.checked) {
                var approvalInfo = $(this).val();
                if(invoiceNo){
                    invoiceNo = invoiceNo + ',' + $("#jqgrid-grid").getCell(approvalInfo, 'invoice_no');
                }else{
                    invoiceNo = $("#jqgrid-grid").getCell(approvalInfo, 'invoice_no');
                }

                allIds['items.print[' + i + '].id'] = $("#jqgrid-grid").getCell(approvalInfo, 'id');
                allIds['items.print[' + i + '].invoice_number'] = $("#jqgrid-grid").getCell(approvalInfo, 'invoice_no');

                i++
            }
        });
        if (i < 1) {
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

//        if (i > 1) {
//            $("#dialog").dialog("destroy");
//            $("#dialog-print-selection2").dialog({
//                resizable: false,
//                height: 150,
//                modal: true,
//                title: 'Invoice Selection Missing',
//                buttons: {
//                    Ok: function () {
//                        $(this).dialog('close');
//                    }
//                }
//            }); //end of dialog
//            return false
//        }

        $("#custom_message").html("Invoice will be canceled. Are you sure?");

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
                        url: "${request.contextPath}/${params.controller}/cancelSecondaryInvoice",
                        type: "POST",
                        data: allIds,
                        dataType: "json",
                        beforeSend: function () {
                            $("#print-button").attr('disabled', 'disabled')

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
                            $("#print-button").removeAttr('disabled');
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

    function executeeAjax() {
        var invoiceNo = '';
        var allIds = {};
        var i = 0;
        $('.print-checkbox').each(function () {
            if (this.checked) {
                var approvalInfo = $(this).val();
                if(invoiceNo){
                    invoiceNo = invoiceNo + ',' + $("#jqgrid-grid").getCell(approvalInfo, 'invoice_no');
                }else{
                    invoiceNo = $("#jqgrid-grid").getCell(approvalInfo, 'invoice_no');
                }

                allIds['items.print[' + i + '].id'] = $("#jqgrid-grid").getCell(approvalInfo, 'id');
                allIds['items.print[' + i + '].invoice_number'] = $("#jqgrid-grid").getCell(approvalInfo, 'invoice_no');

                i++
            }
        });
        if (i < 1) {
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

//        if (i > 1) {
//            $("#dialog").dialog("destroy");
//            $("#dialog-print-selection2").dialog({
//                resizable: false,
//                height: 150,
//                modal: true,
//                title: 'Invoice Selection Missing',
//                buttons: {
//                    Ok: function () {
//                        $(this).dialog('close');
//                    }
//                }
//            }); //end of dialog
//            return false
//        }

        $("#custom_message").html("Invoice will be printed. Are you sure?");

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
                        url: "${request.contextPath}/${params.controller}/printSecondaryInvoice",
                        type: "POST",
                        data: allIds,
                        dataType: "json",
                        beforeSend: function () {
                            $("#print-button").attr('disabled', 'disabled')

                        },
                        success: function (result) {
                            if (result.type == 1) {
                                window.open("${resource(dir:'primaryDemandOrder', file:'rptPrintSecondaryInvoice')}?format=PDF&invoiceNo="+invoiceNo );
                                $("#jqgrid-grid").trigger("reloadGrid");

                            }
                            MessageRenderer.render(result);
                        },
                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                            if(XMLHttpRequest.status = 0){
                                window.open("${resource(dir:'primaryDemandOrder', file:'rptPrintSecondaryInvoice')}?format=PDF&invoiceNo="+invoiceNo );
                                $("#jqgrid-grid").trigger("reloadGrid");

                                MessageRenderer.renderErrorText("Network Problem: Time out");
                            }
                            else{
                                MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                            }
                        },
                        complete: function () {
                            $("#print-button").removeAttr('disabled');
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

    function executeAjaxDeliveryChallan() {
        var invoiceNo = '';
        var allIds = {};
        var i = 0;
        $('.print-checkbox').each(function () {
            if (this.checked) {
                var approvalInfo = $(this).val();
                if(invoiceNo){
                    invoiceNo = invoiceNo + ',' + $("#jqgrid-grid").getCell(approvalInfo, 'invoice_no');
                }else{
                    invoiceNo = $("#jqgrid-grid").getCell(approvalInfo, 'invoice_no');
                }

                allIds['items.print[' + i + '].id'] = $("#jqgrid-grid").getCell(approvalInfo, 'id');
                allIds['items.print[' + i + '].invoice_number'] = $("#jqgrid-grid").getCell(approvalInfo, 'invoice_no');

                i++
            }
        });
        if (i < 1) {
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
        $("#custom_message").html("Invoice will be printed. Are you sure?");
        $("#dialog-confirm-processed").dialog({
            resizable: false,
            height: 150,
            modal: true,
            title: 'Confirm Dialog',
            buttons: {
                Ok: function () {
                    $(this).dialog('close');
                    SubmissionLoader.showTo();
                    window.open("${resource(dir:'primaryDemandOrder', file:'rptPrintSecondaryDeliveryChallan')}?format=PDF&invoiceNo="+invoiceNo );
                    $("#jqgrid-grid").trigger("reloadGrid");
                    SubmissionLoader.hideFrom();
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
        <h1>Cancel Secondary Invoice</h1>
        <h3>Secondary Invoice Information</h3>
        <form name='gFormSecondaryDemandOrderSearch' id='gFormSecondaryDemandOrderSearch'>
            <g:hiddenField name="id" value="" />
            <g:hiddenField name="version" value="" />
            <g:hiddenField name="idEnterprise" id="idEnterprise" value=""/>
            <g:hiddenField name="idBusiness" id="idBusiness" value=""/>
            <div id="remote-content-territoryConfiguration"></div>
            <br>

            <div class="element_row_content_container lightColorbg pad_bot0">
                <table>
                    <script type="text/javascript">
                        jQuery(document).ready(function () {
                            $("#orderDateFrom, #orderDateTo").datepicker(
                                    { dateFormat: 'dd-mm-yy',
                                        changeMonth:true,
                                        changeYear:true
                                    });
                            $('#orderDateFrom').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});
                            $('#orderDateTo').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});
                        });
                    </script>

                    <tr>
                        <td>
                            <label class="txtright bold hight1x width150">
                                Invoice Date From
                            </label>
                        </td>

                        <td>
                            <g:textField name="orderDateFrom" id="orderDateFrom" value="" class="width120" />
                        </td>
                        <td>
                            <label class="txtright bold hight1x width60">
                                To
                            </label>

                        </td>
                        <td>
                            <g:textField name="orderDateTo" id="orderDateTo"  value="" class="width120"/>
                        </td>
                    </tr>

                    <tr>

                        <td>
                            <label class="txtright bold hight1x width150">
                                Invoice Number
                            </label>

                        </td>

                        <td><input type="text" id="searchOrderKey" name="searchOrderKey" class="width120"/>
                            <input type="hidden" id="invoiceNumber" name="invoiceNumber"/>

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
            <div class="clear"></div>
            <div class="buttons">
                <span class="button"><input type="button" name="Print" id="print-button"
                                            class="ui-button ui-widget ui-state-default ui-corner-all" value="Cancel Secondary Invoice"
                                            onclick="executeAjax();"/></span>

             %{--   <span class="button"><input type="button" name="printDelviveryChillan" id="chillan-button"
                                            class="ui-button ui-widget ui-state-default ui-corner-all" value="Print Delivery Challan"
                                            onclick="executeAjaxDeliveryChallan();"/></span>
                --}%
                <span class="button"><input type="button" name="select-button" id="select-button"
                                            class="ui-button ui-widget ui-state-default ui-corner-all" value="Select All"
                                            onclick="selectAllApplicant();"/></span>

            </div>
        </form>
        <div id="dialog-confirm-processed" title="Confirm alert" style="display: none">
            <p><span class="ui-icon ui-icon-alert delete-alert-margin content-left">

            </span><span id="custom_message">These item(s) will be permanently deleted and cannot be recovered. Are you sure?</span>
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