<%@ page import="com.docu.commons.CommonConstants; com.bits.bdfp.util.ApplicationConstants; com.bits.bdfp.inventory.demandorder.PrimaryDemandOrder" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title>Update Delivery Date</title>
<script type="text/javascript" language="Javascript">
    $(document).ready(function() {
        var actionUrl;
//        debugger;
        if('${ids}' != ''){
            actionUrl = '${resource(dir:'primaryDemandOrder', file:'listForUpdateDeliveryDate')}?ids=${ids}'
        }else
        {
            actionUrl = '${request.contextPath}/${params.controller}/listForUpdateDeliveryDate'
        }
        $("#jqgrid-grid").jqGrid({
            url:actionUrl,
            datatype: "json",
            colNames:[
                'Sl',
                'Id',
                'Cus Id',
                'Order No',
                'Customer Name',
                'Order Date.',
                'Delivery Date',
                'Updated Delivery Date',
                'Amount',
                'Ship to Customer',
                'Ship to Address',
                'Select'

            ],
            colModel:[
                {name:'sl',index:'sl', width:30},
                {name:'id',index:'id', width:0, hidden:true},
                {name:'code', index:'code',width:50,align:'left'},
                {name:'order_no', index:'order_no',width:80,align:'left'},
                {name:'cus_name', index:'cus_name',width:120,align:'left'},
                {name:'date_order', index:'date_order',width:70,align:'left'},
                {name:'deliver_date', index:'deliver_date',width:70,align:'left'},
                {name:'updated_date', index:'updated_date',width:70,align:'left'},
                {name:'amount', index:'amount',width:100,align:'left'},
                {name:'name', index:'name',width:120,align:'left'},
                {name:'address', index:'address',width:120,align:'left'},
                {name:'edit', index:'edit',width:50,align:'left'}
            ],
            rowNum:50,
            rowList:[10,20,30,40,50,60,70,80,90,100,-1],
            pager: '#jqgrid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption:"All Order Information",
            autowidth: true,
            height: 250,

            scrollOffset: 0,
            loadComplete: function() {

                var ids = $("#jqgrid-grid").jqGrid('getDataIDs');
                allEmail = '';
                allMobile = '';
                for (key in ids) {
                    var id = $("#jqgrid-grid").getCell(ids[key], 'id');

                    $("#jqgrid-grid").jqGrid('setRowData', ids[key], {edit: '<input type="checkbox" id="print-checkbox' + id + '" class="print-checkbox" value="' + id + '" />'});


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



    });

    function loadDataByOrder(){
        $("#jqgrid-grid").setGridParam({url: '${resource(dir:'primaryDemandOrder', file:'listForUpdateDeliveryDate')}?orderDateFrom=' + $('#orderDateFrom').val()
       });
        $("#jqgrid-grid").trigger("reloadGrid");

    }

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
        if (!$("#gFormSecondaryDemandOrderSearch").validationEngine('validate')) {
            return false;
        }
        var allIds = {}
        var i = 0

        $('.print-checkbox').each(function () {
            if (this.checked) {
                var approvalInfo =$(this).val();
                allIds['items.print[' + i + '].id'] = $("#jqgrid-grid").getCell(approvalInfo, 'id');
                i++

                }

        })



        $("#custom_message").html("Delivery Date Will be Updated. Are you sure?")

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
                        url: "${request.contextPath}/${params.controller}/updateDeliveryDate?orderDateFrom=" + $('#orderDateFrom').val(),
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
</script>
<div class="main_container">
    <div class="content_container">
        <h1>Update Delivery date</h1>
        <h3>Demand order Information</h3>
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
                            $("#orderDateFrom").datepicker(
                                    { dateFormat: 'dd-mm-yy',
                                        changeMonth:true,
                                        changeYear:true
//                                        minDate:0
                                    });

                            $('#orderDateFrom').mask('${CommonConstants.DATE_MASK_FORMAT}', {});
                        });
                    </script>

                    <tr>
                        <td>
                            <label class="txtright bold hight1x width150">
                                Select Delivery Date
                                <span class="mendatory_field">*</span>
                            </label>
                        </td>

                        <td>
                            <g:textField name="orderDateFrom" id="orderDateFrom" value="" class="validate[required] width180" />
                        </td>

                    </tr>



                    <tr>
                        <td>
                            <div class="buttons" hidden="hidden">
                                <span class="button"><input type="button" name="add-button" id="add-button"
                                                            class="ui-button ui-widget ui-state-default ui-corner-all"
                                                            value="Validate" hidden="hidden"
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
                                            class="ui-button ui-widget ui-state-default ui-corner-all" value="Update"
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
            <p>Please select at least one demand order</p>
        </div>
    </div>
</div>