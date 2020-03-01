<%@ page import="com.bits.bdfp.bonus.CustomerBonusFinishGood" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title>Check Eligibility</title>

<script type="text/javascript" language="Javascript">
    $(document).ready(function() {
        $('#view-button').click(function(){

            EligibilityInfo.popupViewEligibleListPanel();

        });

        $("#jqgrid-grid").jqGrid({
            url:'${request.contextPath}/${params.controller}/listForCheckEligibility',
            datatype: "json",
            colNames:[
                'Sl',
                'Id',
                'Cus Id',
                'Order No',
                'Customer Name',
                'Order Date.',
                'Delivery Date',
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
        $("#jqgrid-grid").setGridParam({url: '${resource(dir:'customerBonusFinishGood', file:'listForCheckEligibility')}'
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
        var allIds = {}
        var i = 0

        $('.print-checkbox').each(function () {
            if (this.checked) {
                var approvalInfo =$(this).val();
                allIds['items.print[' + i + '].ids'] = $("#jqgrid-grid").getCell(approvalInfo, 'id');
                i++

            }

        })



        $("#custom_message").html("Bonus Order will be Confirmed. Are you sure?")

        $("#dialog-confirm-processed").dialog({
            resizable: false,
            height: 150,
            modal: true,
            title: 'Confirm Dialog',
            buttons: {
                Ok: function () {
                    $(this).dialog('close');
                    jQuery.ajax({
                        url: "${request.contextPath}/${params.controller}/confirmBonus" ,
                        type: "POST",
                        data: allIds,
                        dataType: "json",
                        beforeSend: function () {


                        },
                        success: function (result) {

                            $("#jqgrid-grid").trigger("reloadGrid");
                            MessageRenderer.render(result);
                        },
                        complete: function () {
                            $("#jqgrid-grid").trigger("reloadGrid");

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
    function checkEligibility() {
        var allIds = ""
        var i = 0

        $('.print-checkbox').each(function () {
            if (this.checked) {
                    allIds += $(this).val() + ",";
                    i++
            }

        })

        if (i < 1) {
            $("#dialog").dialog("destroy");
            $("#dialog-print-selection").dialog({
                resizable: false,
                height: 150,
                modal: true,
                title: 'Order Selection Missing',
                buttons: {
                    Ok: function () {
                        $(this).dialog('close');
                    }
                }
            }); //end of dialog

            return false
        }
        else {
            allIds = allIds.substring(0, allIds.length - 1)
            jQuery.ajax({
                url: "${request.contextPath}/${params.controller}/checkEligibility?ids=" + allIds,
//                type: "POST",
//                data: allIds,
                dataType: "json",
                beforeSend: function () {
                    $("#print-button").attr('disabled', 'disabled')

                },
                success: function (result) {
//                    console.log(result)
                    if (result.isEligible) {

                        $('#view-button').show();
                        $('#confirm-button').show();
                        $('#cancel-button').show();

                    }
                    else{
                        MessageRenderer.renderErrorText("Order's are not eligible for bonus");
                    }

                },
                complete: function () {
                    $("#print-button").removeAttr('disabled');

                }
            });


        }

    }


    var EligibilityInfo = {
        customerCoreInfoId: null,
        popupViewEligibleListPanel: function(){

            var allIds = ""
            var i = 0

            $('.print-checkbox').each(function () {
                if (this.checked) {
                    allIds += $(this).val() + ",";
                    i++
                }

            })

            if (i < 1) {
                $("#dialog").dialog("destroy");
                $("#dialog-print-selection").dialog({
                    resizable: false,
                    height: 150,
                    modal: true,
                    title: 'Order Selection Missing',
                    buttons: {
                        Ok: function () {
                            $(this).dialog('close');
                        }
                    }
                }); //end of dialog

                return false
            }
            else {
                allIds = allIds.substring(0, allIds.length - 1)

                var url = '${resource(dir:'customerBonusFinishGood', file:'popupViewEligibleListPanel')}';
                var params = {ids: allIds};
                DocuAjax.html(url, params, function (html) {
                    $.fancybox(html);
                });
            }
        }


    }
    function cancelOrder(){
        $('.print-checkbox').each(function () {
            this.checked = false;
            $('#select-button').attr('value', 'Select All')
        })
        $('#view-button').hide();
        $('#confirm-button').hide();
        $('#cancel-button').hide();
    }
</script>
<div class="main_container">
    <div class="content_container">
        <h1><g:message code="enterpriseConfiguration.create.label" default="Check Eligibility"/></h1>
        <h3><g:message code="enterpriseConfiguration.info.label" default="Check Eligibility"/></h3>
        <form name='gFormSecondaryDemandOrderSearch' id='gFormSecondaryDemandOrderSearch'>
            <g:hiddenField name="id" value="${secondaryDemandOrder?.id}" />
            <g:hiddenField name="version" value="${secondaryDemandOrder?.version}" />
            <g:hiddenField name="idEnterprise" id="idEnterprise" value=""/>
            <g:hiddenField name="idBusiness" id="idBusiness" value=""/>
            <div id="remote-content-territoryConfiguration"></div>
            <br>

            <div class="element_row_content_container lightColorbg pad_bot0">


                <div class="jqgrid-container">
                    <table id="jqgrid-grid"></table>

                    <div id="jqgrid-pager"></div>
                </div>
            </div>
            <div class="clear"></div>
            <div class="buttons">
                <span class="button"><input type="button" name="check" id="check-button"
                                            class="ui-button ui-widget ui-state-default ui-corner-all" value="Check Eligibility"
                                            onclick="checkEligibility();"/></span>
                <span class="button"><input type="button" name="view-button" id="view-button" style="display: none"
                                            class="ui-button ui-widget ui-state-default ui-corner-all" value="View"
                                            /></span>
                <span class="button"><input type="button" name="confirm" id="confirm-button" style="display: none"
                                            class="ui-button ui-widget ui-state-default ui-corner-all" value="Confirm"
                                            onclick="executeAjax();"/></span>
                <span class="button"><input type="button" name="cancel" id="cancel-button" style="display: none"
                                            class="ui-button ui-widget ui-state-default ui-corner-all" value="Cancel"
                                            onclick="cancelOrder();"/></span>
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