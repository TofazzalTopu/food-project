<%--
  Created by IntelliJ IDEA.
  User: NZ
  Date: 1/21/2016
  Time: 3:39 PM
--%>

<%@ page import="com.docu.commons.CommonConstants; com.bits.bdfp.util.ApplicationConstants" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title>Search Market Return</title>

<g:javascript src="jquery/watermark/jquery.watermark.js"/>
<script type="text/javascript" language="Javascript">

    var idBeingEdited = -1;
    var rowIndex = -1;
    var selectedProduct = -1;
    var deletedIds = [];
    var deletedIdsSize = 0;
    var baseUrl = '${request.contextPath}/${params.controller}/orderListFrGrid';

    $(document).ready(function () {
        loadOrderNo();
        $("#primary-order-grid").jqGrid({
            url: '${resource(dir:'marketReturn', file:'listMr')}',
            datatype: "json",
            colNames: [
                'Sl',
                'Id',
                'MR No.',
                'Primary Customer',
                'Destination DP',
                'Source Dp',
                'Sub-Warehouse',
                'MR Status'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 20, align: 'center'},
                {name: 'id', index: 'id', width: 0, hidden: true},
                {name: 'mrNo', index: 'mrNo', width: 80, align: 'center'},
                {name: 'customer', index: 'customer', width: 100, align: 'left'},
                {name: 'destinationDp', index: 'destinationDp', width: 70, align: 'left'},
                {name: 'sourceDp', index: 'sourceDp', width: 80, align: 'left'},
                {name: 'subWarehouse', index: 'subWarehouse', width: 80, align: 'left'},
                {name: 'meStatus', index: 'meStatus', width: 85, align: 'left'}
            ],
            rowNum: 50,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#primary-order-grid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Primary Demand Order List",
            autowidth: true,
            height: 150,

            scrollOffset: 0,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                executeEditProduct(rowid);
            }
        });
        $("#primary-order-grid").jqGrid('navGrid', '#primary-order-grid-pager', {
            edit: false,
            add: false,
            del: false,
            search: false
        });
//     $("#jqgrid-grid").gridResize({minWidth:350,maxWidth:800,minHeight:200});
        $(".ui-pg-selbox").children().each(function () {
            if ($(this).val() == -1) {
                $(this).html('All')
            }

        });
        resetAll();
        setDateRangeNoLimit('dateFrom', 'dateTo');

    });

//    $(document).bind('beforeunload',exit());
    $(window).unload(function () {
        jQuery.ajax({
            type: 'post',
            url: '${resource(dir:'marketReturn', file:'statusChange')}',
            success: function (data, textStatus) {
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
            },
            complete: function () {
            },
            dataType: 'json'
        });
        return null;
    });

    function initDatePicker() {
        $("#dateProposedDelivery, #orderDate, #dateExpectedDeliver").datepicker(
                {
                    dateFormat: 'dd-mm-yy',
                    changeMonth: true,
                    changeYear: true
                });
        $('#dateProposedDelivery').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});
        $('#orderDate').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});
        $('#dateExpectedDeliver').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});
    }

    function resetAll() {
        $('#popEmpDetails').html('');
        $('#searchOrderKey').val('');
        $('#dateFrom').val('');
        $('#dateTo').val('');
        $("#primary-order-grid").setGridParam({url: baseUrl}).trigger("reloadGrid");
        idBeingEdited = -1;
        rowIndex = -1;
        selectedProduct = -1;
        deletedIds = [];
        deletedIdsSize = 0;
    }

    function executeEditProduct(id) {
//        var data = jQuery('#primary-order-grid').jqGrid('getRowData', id);
//        $('#id').val(id);
//        $('#popEmpDetails').html('');
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            url: '${resource(dir:'marketReturn', file:'edit')}?id=' + id,
            success: function (data, textStatus) {
                if (data) {
                    $('#popEmpDetails').html(data);
                } else {
                    MessageRenderer.render({
                        messageTitle: 'Error',
                        type: 3,
                        messageBody: 'Can not edit this instance at this moment.'
                    });
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                }  else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete: function (data) {
//                initGrid();
//                initDatePicker();
//                $(".amount").format({precision: 2, allow_negative: false, autofix: true});
                %{--$('#dateProposedDelivery').datepicker('setDate', ${dateProposedDelivery});--}%
                SubmissionLoader.hideFrom();
            },
            dataType: 'html'
        });
    }

    function executePreConditionMarketReturn() {
        // trim field vales before process.
        trim_form();
        if ($("#gFormMarketReturnEdit").validate().form({onfocusout: false}) == false) {
            return false;
        }
        return true;
    }

    function executeAjaxMarketReturn() {
        if (!executePreConditionMarketReturn()) {
            return false;
        }
        var actionUrl = "${request.contextPath}/${params.controller}/update";

        var data = $("#gFormMarketReturnEdit").serializeArray();
        var gd = $("#jqgrid-grid-marketReturn").jqGrid('getRowData');
        var length = gd.length;
        for (var i = 0; i < length; i++) {
            data.push({'name': 'items.marketReturnDetails[' + i + '].id', 'value': gd[i].id > 0 ? gd[i].id : ''});
            data.push({'name': 'items.marketReturnDetails[' + i + '].finishProduct.id', 'value': gd[i].productId});
            data.push({'name': 'items.marketReturnDetails[' + i + '].mrType', 'value': gd[i].mrType});
            data.push({'name': 'items.marketReturnDetails[' + i + '].quantity', 'value': gd[i].quantity});
            data.push({'name': 'items.marketReturnDetails[' + i + '].invoice.id', 'value': gd[i].invoiceId});
            data.push({'name': 'items.marketReturnDetails[' + i + '].batch', 'value': gd[i].batch});
            data.push({'name': 'items.marketReturnDetails[' + i + '].reference', 'value': gd[i].reference});
            data.push({'name': 'items.marketReturnDetails[' + i + '].remarks', 'value': gd[i].remarks});
            data.push({
                'name': 'items.marketReturnDetails[' + i + '].distributionPointStock.id',
                'value': gd[i].stockId
            });
            if (gd[i].price != '') {
//                data.push({'name': 'priceOf' + gd[i].productId, 'value': gd[i].price});
                data.push({'name': 'items.marketReturnDetails[' + i + '].price', 'value': gd[i].price});
            }
        }

        jQuery.ajax({
            type: 'post',
            data: data,
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionMarketReturn(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                }  else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete: function () {
            },
            dataType: 'json'
        });
        return false;
    }

    function executePostConditionMarketReturn(result) {
        alert("executePostConditionMarketReturn");
        if (result.type == 1) {
            $('#popEmpDetails').html('');
        }
        MessageRenderer.render(result);
        $("#primary-order-grid").trigger("reloadGrid");
    }

    function loadOrderNo() {
        jQuery('#searchOrderKey').autocomplete({
            minLength: '1',
            source: function (request, response) {
                //EmployeeRegister.employeeCoreInfoId = null;
                $('#popEmpDetails').html("");
                var data = {searchKey: request.term};
                var url = '${resource(dir:'marketReturn', file:'listMrForAutoComplete')}';
                DocuAutoComplete.setSpinnerSelector('searchOrderKey').execute(response, url, data, function (item) {
                    item['label'] = item['mr_no'];
                    item['value'] = item['label'];
                    return item;
                });
            },
            select: function (event, ui) {

                $("#searchOrderKey").val(ui.item.mr_no);
            }
        }).data("autocomplete")._renderItem = function (ul, item) {
            var accountstype = "";
            if (item) {
                accountstype = '<div style="font-size: 9px; color:#326E93;">' + "Customer Name: " + item.customer_name +
                ", Source DP: " + item.source_dp + ", Destination DP: " + item.destination_dp + '</div>';
            }
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + accountstype).appendTo(ul);

        }
    }

    function loadDataByOrder() {
        $("#primary-order-grid").setGridParam({
            url: '${resource(dir:'marketReturn', file:'listMr')}?orderNo=' + $('#searchOrderKey').val()
            + '&dateFrom=' + $('#dateFrom').val() + '&dateTo=' + $('#dateTo').val()
        });
        $("#primary-order-grid").trigger("reloadGrid");

    }

</script>

<div class="main_container">
    <div class="content_container">
        <h1><g:message code="enterpriseConfiguration.create.label" default="Search Market Return"/></h1>

        <h3><g:message code="enterpriseConfiguration.info.label" default="Market Return Information"/></h3>

        <form name='gFormSecondaryDemandOrderSearch' id='gFormSecondaryDemandOrderSearch'>
            <g:hiddenField name="id" value="${primaryDemandOrder?.id}"/>
            <g:hiddenField name="version" value="${primaryDemandOrder?.version}"/>
            <g:hiddenField name="idEnterprise" id="idEnterprise" value=""/>
            <g:hiddenField name="idBusiness" id="idBusiness" value=""/>
            <div id="remote-content-territoryConfiguration"></div>
            <br>

            <div class="element_row_content_container lightColorbg pad_bot0">
                <table>

                    <tr>
                        <script type="text/javascript">
                            jQuery(document).ready(function () {
                                $("#dateFrom, #dateTo").datepicker(
                                        {
                                            dateFormat: 'dd-mm-yy',
                                            changeMonth: true,
                                            changeYear: true
                                        });
                                $('#dateFrom').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});
                                $('#dateTo').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});
                            });
                        </script>
                        <td>
                            <label class="txtright bold hight1x width120">
                                <g:message code="secondaryDemandOrder.product.label"
                                           default="MR No"/>
                            </label>

                        </td>
                        <td><input type="text" id="searchOrderKey" name="searchOrderKey" class="width120"/>
                            <input type="hidden" id="productId" name="productId" value=""/>
                            <input type="hidden" id="productCode" value=""/>
                            <input type="hidden" id="product" value=""/>
                        </td>
                        <td>
                            <label class="txtright bold hight1x width60">
                                <g:message code="secondaryDemandOrder.orderDate.label"
                                           default="Date From"/>

                            </label>
                        </td>
                        <td>
                            <g:textField name="dateFrom" id="dateFrom" value="" class="width120"
                                         onload="loadDataByOrder();"/>
                        </td>
                        <td>
                            <label class="txtright bold hight1x width60">
                                <g:message code="secondaryDemandOrder.deliveryDate.label" default="Date To"/>

                            </label>

                        </td>
                        <td>
                            <g:textField name="dateTo" id="dateTo" value="" class="width120"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="buttons">
                                <span class="button"><input type="button" name="show-button" id="show-button"
                                                            class="ui-button ui-widget ui-state-default ui-corner-all"
                                                            value="Search"
                                                            onclick="loadDataByOrder();"/></span>

                            </div>
                        </td>
                    </tr>
                </table>

                <div class="jqgrid-container">
                    <table id="primary-order-grid"></table>

                    <div id="primary-order-grid-pager"></div>
                </div>
            </div>

            <div id="popEmpDetails">
            </div>
        </form>
    </div>
</div>

