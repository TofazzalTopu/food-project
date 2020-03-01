<%--
  Created by IntelliJ IDEA.
  User: mdalinaser.khan
  Date: 2/2/16
  Time: 6:38 PM
--%>

<%@ page import="com.docu.commons.CommonConstants; com.bits.bdfp.util.ApplicationConstants;" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title>Print Retail Invoice</title>

<script type="text/javascript" language="Javascript">
    var print=0;
    $(document).ready(function() {
//        loadOrderNo();
        $("#jqgrid-grid-invoice").jqGrid({
            datatype: "local",
            colNames:[
//                'Sl',
                'Id',
                'Invoice Number',
                'Invoice Amount',
                'Invoice Date',
                'Legacy ID',
                'Customer Name',
                'Customer ID',
                'Retail Order Number',
                'Order Date'
            ],
            colModel:[
//                {name:'sl',index:'sl', width:30, align: 'center'},
                {name:'id',index:'id', width:0, hidden:true},
                {name:'invoiceNo', index:'invoiceNo',width:100,align:'center'},
                {name:'invoiceAmount',index:'invoiceAmount', width:110, hidden:false,align:'right'},
                {name:'invoiceDate', index:'invoiceDate',width:90,align:'center'},
                {name:'legacyId', index:'legacyId',width:90,align:'left'},
                {name:'customerName', index:'customerName',width:150,align:'left'},
                {name:'customerCode', index:'customerCode',width:100,align:'center'},
                {name:'orderNo',index:'orderNo', width:110, hidden:false,align:'center'},
                {name:'orderDate',index:'orderDate', width:80, hidden:false,align:'center'}
            ],
            rowNum:20,
            rowList:[10,20,30,40,50,60,70,80,90,100,-1],
            pager: '#jqgrid-pager-invoice',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption:"Retail Invoice List",
            autowidth: true,
            height: true,
            multiselect: true,
            scrollOffset: 0,
            rownumbers: true,
            loadComplete: function() {
            },
            onSelectRow: function(rowid, status) {
            },
            gridComplete: function() {
//                $("#jqgrid-grid-invoice_cb").css("width","35px");
                $("#jqgrid-grid-invoice_cb").html("&#10004;");
//                $("#jqgrid-grid-invoice tbody tr").children().first("td").css("width","35px");
            }
        });
        $("#jqgrid-grid-invoice").jqGrid('navGrid', '#jqgrid-pager', {edit:false,add:false,del:false,search:false});

        setDateRange('invoiceDateFrom','invoiceDateTo');
        $('#invoiceDateFrom').mask('${CommonConstants.DATE_MASK_FORMAT}', {});
        $('#invoiceDateTo').mask('${CommonConstants.DATE_MASK_FORMAT}', {});
    });

    function searchRetailInvoice(){
        var invoiceNo = $('#invoiceNo').val();
        var invoiceDateFrom = $('#invoiceDateFrom').val();
        var invoiceDateTo = $('#invoiceDateTo').val();
        if(invoiceNo){
            invoiceDateFrom = "";
            invoiceDateTo = ""
        }else{
            if(!invoiceDateFrom || !invoiceDateTo){
                MessageRenderer.renderErrorText("Please input Invoice No or Date Range");
                return
            }
        }
        $("#jqgrid-grid-invoice").jqGrid().setGridParam({url: "${resource(dir:'processRetailOrder', file:'listRetailInvoice')}?"
                + 'invoiceNo=' + invoiceNo + '&invoiceDateFrom=' + invoiceDateFrom + '&invoiceDateTo=' + invoiceDateTo,
            datatype: "json"
        }).trigger("reloadGrid", [
                    {page: 1}
                ]);
    }

    function executeAjaxPrintInvoice() {
        var selectedRetailInvoiceIds = $("#jqgrid-grid-invoice").jqGrid('getGridParam','selarrrow');
        if(selectedRetailInvoiceIds.toString() == ""){
            MessageRenderer.renderErrorText("No Invoice Selected for print");
            return
        }
        window.open("${resource(dir:'processRetailOrder', file:'rptPrintRetailInvoice')}?format=PDF&invoiceIds=" + selectedRetailInvoiceIds.toString() );
    }

</script>
<div class="main_container">
    <div class="content_container">
        <h1>Print Retail Invoice</h1>
        <h3>Retail Invoice Information</h3>
        <form name='gFormSecondaryDemandOrderSearch' id='gFormSecondaryDemandOrderSearch'>
            <div class="element_row_content_container lightColorbg pad_bot0">
                <table>
                    <tr>
                        <td>
                            <label class="txtright bold hight1x width1x" style="width: 130px;">
                                Retail Invoice No:
                            </label>
                        </td>

                        <td>
                            <g:textField type="text" id="invoiceNo" name="invoiceNo" value="" class="width120"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label class="txtright bold hight1x width1x" style="width:130px;">
                                Invoice Date From:
                            </label>
                        </td>

                        <td>
                            <g:textField name="invoiceDateFrom" id="invoiceDateFrom" value="" class="width120" />
                        </td>
                        <td>
                            <label class="txtright bold hight1x width1x" style="width:30px">
                                To:
                            </label>

                        </td>
                        <td>
                            <g:textField name="invoiceDateTo" id="invoiceDateTo"  value="" class="width120"/>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <div class="buttons">
                                <span class="button"><input type="button" name="add-button" id="add-button"
                                                            class="ui-button ui-widget ui-state-default ui-corner-all"
                                                            value="Search"
                                                            onclick="searchRetailInvoice();"/></span>
                            </div>
                        </td>
                    </tr>
                </table>

                <div class="jqgrid-container">
                    <table id="jqgrid-grid-invoice"></table>
                    <div id="jqgrid-pager-invoice"></div>
                </div>
            </div>
            <div class="clear"></div>
            <div class="buttons">
                <span class="button"><input type="button" name="Print" id="print-button"
                                            class="ui-button ui-widget ui-state-default ui-corner-all" value="Print Invoice"
                                            onclick="executeAjaxPrintInvoice();"/></span>
            </div>
        </form>

    </div>
</div>