<%@ page import="com.bits.bdfp.util.ApplicationConstants; com.docu.commons.CommonConstants" %>
<script type="text/javascript" language="Javascript">
    var jqGridRetailInvoiceList = null;
    $(document).ready(function () {
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormCashCollect").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormCashCollect").validationEngine('attach');
        loadCustomerForCancelInvoice();
        jqGridRetailInvoiceList = $("#jqgrid-grid-retailInvoice").jqGrid({
            url:'',
            datatype: "local",
            colNames:[
                'ID',
                'Invoice No',
                'Invoice Date',
                'Invoice Amount'
            ],
            colModel:[
                {name:'id', index:'id', width:0, hidden:true},
                {name:'invoiceNo', index:'invoiceNo', width:120, align:'center'},
                {name:'invoiceDate', index:'invoiceDate', width:100, align:'center'},
                {name:'invoiceAmount', index:'invoiceAmount', width:150, align:'right', formatter:'currency', formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2}}
            ],
            rowNum: -1,
            viewrecords:true,
            caption:"Available Invoices",
            autowidth:false,
            shrinkToFit: true,
            height:true,
            scrollOffset:0,
            gridview:true,
            multiselect: true,
//            rownumbers: true,

            gridComplete: function() {
                $("#jqgrid-grid-retailInvoice_cb").html("&#10004;");
//                $("#jqgrid-grid-retailInvoice tbody tr").children().first("td").css("padding-right","10px");
//                $("#jqgrid-grid-retailInvoice tbody tr").children().first("td").css("padding-top","10px");
            },
            loadComplete: function (data) {
            },
            onSelectRow: function(rowid, status) {
            },
            loadError: function (jqXHR, textStatus, errorThrown) {
                MessageRenderer.renderErrorText(jqXHR.responseText,'');
            }
        });
        setDateRange('dateFrom', 'dateTo');
        $("#dateFrom").mask('${CommonConstants.DATE_MASK_FORMAT}', {});
        $("#dateTo").mask('${CommonConstants.DATE_MASK_FORMAT}', {})
    });


    function executeAjaxCancelInvoice() {
        var selectedRetailInvoiceIds = jQuery("#jqgrid-grid-retailInvoice").jqGrid('getGridParam','selarrrow');
        if(selectedRetailInvoiceIds.toString() == ""){
            MessageRenderer.renderErrorText('Invoice(s) are not selected','Message');
            return false;
        }
        $("#dialog").dialog("destroy");
        $("#dialog-confirm-retailInvoice").dialog({
            resizable: false,
            height: 150,
            modal: true,
            title: 'Cancel Alert',
            buttons: {
                'Cancel Invoice(s)': function () {
                    $(this).dialog('close');
                    SubmissionLoader.showTo();
                    jQuery.ajax({
                        type: 'post',
                        data: {selectedInvoiceIds: selectedRetailInvoiceIds.toString()},
                        url: "${request.contextPath}/${params.controller}/cancelRetailInvoice",
                        success: function (data, textStatus) {
                            executePostConditionCancelInvoice(data);
                        },
                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                            if(XMLHttpRequest.status = 0){
                                $("#jqgrid-grid-retailInvoice").trigger("reloadGrid");
                                MessageRenderer.renderErrorText("Network Problem: Time out");
                            }
                            else{
                                MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                            }
                        },
                        complete: function () {
                            SubmissionLoader.hideFrom();
                        },
                        dataType: 'json'
                    });
                },
                Cancel: function () {
                    $(this).dialog('close');
                }
            }
        }); //end of dialog

        return false;
    }
    function executePostConditionCancelInvoice(result) {
        $("#jqgrid-grid-retailInvoice").trigger("reloadGrid");
        MessageRenderer.render(result);
    }

    var CustomerInfo = {
        customerCoreInfoId: null,
        popupCustomerListPanel: function(){
            var url = '${resource(dir:'processRetailOrder', file:'popupCustomerListPanel')}' ;
            var params = {};
            DocuAjax.html(url, params, function(html){
                $.fancybox(html);
            });
        },
        setCustomerInformation: function(customerCoreInfoId, customerCoreInfo){
            $("#searchCustomerKey").val(customerCoreInfo);
            $("#customerId").val(customerCoreInfoId);
            CustomerInfo.customerCoreInfoId = customerCoreInfoId;
        }
    };

    function loadCustomerForCancelInvoice() {
        jQuery('#searchCustomerKey').autocomplete({
            minLength:'1',
            source:function (request, response) {
                //EmployeeRegister.employeeCoreInfoId = null;
                var data = {query: request.term};
                var url = '${resource(dir:'retailCashCollect', file:'customerAutoComplete')}';
                DocuAutoComplete.setSpinnerSelector('searchKey').execute(response, url, data, function (item) {
                    item['label'] = item['legacy_id'] + " [" + item['code'] + "] " + item['name'];
                    item['value'] = item['label'];
                    return item;
                });
            },
            select:function (event, ui) {
                loadCustomerNonPaidInvoice(ui.item.id);
                CustomerInfo.setCustomerInformation(ui.item.id, ui.item.value);
                $("#customerId").val(ui.item.id);
                $("#legacyId").val(ui.item.legacy_id);
                $("#customerNumber").val(ui.item.code);
                $("#customerName").val(ui.item.name);
                $("#customerAddress").val(ui.item.present_address);
            }
        }).data("autocomplete")._renderItem = function (ul, item) {
            var custometDetailts = "";
            if (item) {
                custometDetailts = '<div style="font-size: 9px; color:#326E93;">' + "Legacy ID: " + item.legacy_id +", Code: " +item.code + ", Name: " + item.name + ", Status: " + item.status + ", Address: " + item.present_address + '</div>';
            }
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + custometDetailts).appendTo(ul);
        };
    }

    $('#search-btn-customer-register-id').click(function(){
        CustomerInfo.popupCustomerListPanel();
    });

    function loadCustomerNonPaidInvoice(customerId){
        var fromDate = $("#dateFrom").val();
        var toDate = $("#dateTo").val();
        if(!fromDate || !toDate){
            fromDate = "";
            toDate = ""
        }
        $("#jqgrid-grid-retailInvoice").jqGrid().setGridParam({url: "${resource(dir:'retailCashCollect', file:'listNonPaidRetailInvoice')}?"
                + 'customerId=' + customerId + "&fromDate=" + fromDate + "&toDate=" + toDate,
            datatype: "json"
        }).trigger("reloadGrid", [
                    {page: 1}
                ]);
//        var gridWidth = $("#jqgrid-grid-retailInvoice").jqGrid('getGridParam', 'width');
//        $("#jqgrid-grid-retailInvoice").jqGrid('setGridWidth', gridWidth + 160);
    }

</script>