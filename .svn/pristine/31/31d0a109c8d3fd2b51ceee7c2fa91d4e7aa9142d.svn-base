<%--
  Created by IntelliJ IDEA.
  User: liton.miah
  Date: 11/30/2015
  Time: 2:30 PM
--%>

<%@ page import="com.docu.commons.CommonConstants; com.bits.bdfp.util.ApplicationConstants; com.bits.bdfp.inventory.demandorder.PrimaryDemandOrder" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title>Print Loading Slip</title>

<script type="text/javascript" language="Javascript">
    var print=0;
    $(document).ready(function() {
        loadOrderNo();
        $("#jqgrid-grid").jqGrid({
            url:'${request.contextPath}/${params.controller}/loadingSlipList',
            datatype: "json",
            colNames:[
                'Select',
                'Sl',
                'Id',
                'Loading Slip Number',
                'Vehicle Name',
                'Vehicle Number',
                'User Created',
                'Loading Slip Date',
                'Print Count'
            ],
            colModel:[
                {name:'edit', index:'edit',width:40,align:'center'},
                {name:'sl',index:'sl', width:30},
                {name:'id',index:'id', width:0, hidden:true},
                {name:'loadingSlipNumber', index:'loadingSlipNumber',width:120,align:'center'},
                {name:'vehicleName', index:'vehicleName',width:100,align:'left'},
                {name:'vehicleNumber', index:'vehicleNumber',width:100,align:'center'},
                {name:'userCreated', index:'userCreated',width:100,align:'left'},
                {name:'loadingSlipDate', index:'loadingSlipDate',width:90,align:'center'},
                {name:'printCount', index:'printCount',width:60,align:'center'}

            ],
            rowNum:10,
            rowList:[10,20,30,40,50,60,70,80,90,100,-1],
            pager: '#jqgrid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "asc",
            caption:"All Loading Slip Information",
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
                var url = '${resource(dir:'loadingSlip', file:'loadingSlipNoAutoComplete')}' ;
                DocuAutoComplete.setSpinnerSelector('searchOrderKey').execute(response, url, data, function (item) {
                    item['label'] = item['slipNo'];
                    item['value'] = item['label'];
                    return item;
                });
            },
            select:function (event, ui) {
                $("#searchOrderKey").val(ui.item.slipNo);
                $("#name").val(ui.item.name);
            }
        }).data("autocomplete")._renderItem = function (ul, item) {
            var accountstype = "";
            if (item) {
                accountstype = '<div style="font-size: 9px; color:#326E93;">' +" Slip No: " +item.slipNo+ '</div>';
            }
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + accountstype).appendTo(ul);

        }
    }
    function loadDataByOrder(){
        $("#jqgrid-grid").setGridParam({url: '${resource(dir:'loadingSlip', file:'loadingSlipList')}?slipNo=' + $('#searchOrderKey').val()
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
        var slipNo = 0;
        var allIds = {};
        var loadingSlipIds = "";
        var i = 0;
        $('.print-checkbox').each(function () {
            if (this.checked) {
                var approvalInfo = $(this).val();
//                slipNo = $("#jqgrid-grid").getCell(approvalInfo, 'loadingSlipNumber');

                if(slipNo){
                    slipNo = slipNo + ',' + $("#jqgrid-grid").getCell(approvalInfo, 'loadingSlipNumber');
                }else{
                    slipNo = $("#jqgrid-grid").getCell(approvalInfo, 'loadingSlipNumber');
                }

                if(loadingSlipIds != ""){
                    loadingSlipIds = loadingSlipIds + ',' + $("#jqgrid-grid").getCell(approvalInfo, 'id');
                }else{
                    loadingSlipIds = $("#jqgrid-grid").getCell(approvalInfo, 'id');
                }

                allIds['items.print[' + i + '].id'] = $("#jqgrid-grid").getCell(approvalInfo, 'id');
                allIds['items.print[' + i + '].loadingSlipNumber'] = $("#jqgrid-grid").getCell(approvalInfo, 'loadingSlipNumber');

                i++
            }
        });
        if (i < 1) {
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

//        if (i > 1) {
//            $("#dialog").dialog("destroy");
//            $("#dialog-print-selection2").dialog({
//                resizable: false,
//                height: 150,
//                modal: true,
//                title: 'Loading Slip Selection Missing',
//                buttons: {
//                    Ok: function () {
//                        $(this).dialog('close');
//                    }
//                }
//            }); //end of dialog
//            return false
//        }

        $("#custom_message").html("Loading Slip will be printed. Are you sure?");

        $("#dialog-confirm-processed").dialog({
            resizable: false,
            height: 150,
            modal: true,
            title: 'Confirm Dialog',
            buttons: {
                Ok: function () {
                    $(this).dialog('close');
                    jQuery.ajax({
                        url: "${request.contextPath}/${params.controller}/printLoadingSlipCount",
                        type: "POST",
                        data: allIds,
                        dataType: "json",
                        beforeSend: function () {
                            $("#print-button").attr('disabled', 'disabled')
                        },
                        success: function (result) {
                            if (result.type == 1) {
                                window.open("${resource(dir:'loadingSlip', file:'printLoadingSlipAsPdf')}?format=PDF&slipNo="+slipNo + "&loadingSlipIds=" + loadingSlipIds);
                                $("#jqgrid-grid").trigger("reloadGrid");
                            }
                            MessageRenderer.render(result);
                        },
                        complete: function () {
                            $("#print-button").removeAttr('disabled');

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
        <h1><g:message code="enterpriseConfiguration.create.label" default=" Print Loading Slip"/></h1>
        <h3><g:message code="enterpriseConfiguration.info.label" default="Loading Slip Information"/></h3>
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
                            <label class="txtright bold hight1x width1x" style="width:150px;">
                                <g:message code="secondaryDemandOrder.orderDate.label"
                                           default="Loading Slip Date From"/>

                            </label>
                        </td>

                        <td>
                            <g:textField name="orderDateFrom" id="orderDateFrom" value="" class="width120" />
                        </td>
                        <td>
                            <label class="txtright bold hight1x width1x" style="width:60px">
                                <g:message code="secondaryDemandOrder.deliveryDate.label" default="To"/>

                            </label>

                        </td>
                        <td>
                            <g:textField name="orderDateTo" id="orderDateTo"  value="" class="width120"/>
                        </td>
                    </tr>

                    <tr>

                        <td>
                            <label class="txtright bold hight1x width1x" style="width: 150px;">
                                <g:message code="secondaryDemandOrder.product.label"
                                           default="Loading Slip Number"/>
                            </label>

                        </td>

                        <td><input type="text" id="searchOrderKey" name="searchOrderKey" class="width120"/>
                            <input type="hidden" id="invoiceNumber" name="invoiceNumber"/>

                        </td>
                    </tr>

                    <tr>
                        <td>
                            <div class="buttons">
                                <span class="button">
                                    <input type="button" name="add-button" id="add-button"
                                                            class="ui-button ui-widget ui-state-default ui-corner-all"
                                                            value="Search"
                                                            onclick="loadDataByOrder();"/>
                                </span>
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
                                            class="ui-button ui-widget ui-state-default ui-corner-all" value="Print"
                                            onclick="executeAjax();"/></span>
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
            <p>Please select at least one Loading Slip</p>
        </div>
        <div id="dialog-print-selection2" style="display: none">
            <p>Please select only one Loading Slip</p>
        </div>
    </div>
</div>