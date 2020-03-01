<script>
    $(document).ready(function () {
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#chartOfAccountUploadForm").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#chartOfAccountUploadForm").validationEngine('attach');

        $("#transactionDate").datepicker(
                {
                    dateFormat: 'dd-mm-yy',
                    changeMonth: true,
                    changeYear: true
                });
        $('#transactionDate').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});

        $("#jqgrid-grid-uploadDataInfo").jqGrid({
            %{--url: '${resource(dir:'chargeType', file:'list')}',--}%
            data: ${personList},
            datatype: "local",
            colNames: [
                'SL',
                'Customer/Party',
                'Prefix Code',
                'Account Head',
                'Account Code',
                'Dr. Balance',
                'Cr. Balance',
                'Ledger Book',
                'Post-Fix Code'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30, sortable: false, align: 'left'},
                {name: 'customerPartyPrefix', index: 'customerPartyPrefix', width: 110, align: 'left'},
                {name: 'prefixCode', index: 'prefixCode', width: 100, align: 'center'},
                {name: 'accountHead', index: 'accountHead', width: 200, align: 'left'},
                {name: 'accountCode', index: 'accountCode', width: 100, align: 'center'},
                {name: 'drBalance', index: 'drBalance', width: 100, align: 'right',
                    formatter:'currency',
                    formatoptions:{defaulValue:0,thousandsSeparator:',',decimalPlaces:2}
                },
                {name: 'crBalance', index: 'crBalance', width: 100, align: 'right',
                    formatter:'currency',
                    formatoptions:{defaulValue:0,thousandsSeparator:',',decimalPlaces:2}
                },
                {name: 'ledgerBookPostFix', index: 'ledgerBookPostFix', width: 120, align: 'left'},
                {name: 'postFixCode', index: 'postFixCode', width: 100, align: 'center'}
            ],
            rowNum: ${rowNum},
//            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
//            pager: '#jqgrid-pager-uploadDataInfo',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "All Data Info",
            autowidth: false,
            height: true,
            scrollOffset: 0,
            loadComplete: function () {
                var grid =  $("#jqgrid-grid-uploadDataInfo");
                var rowIds = grid.jqGrid('getDataIDs');
                for(key in rowIds){
                    grid.jqGrid('setRowData', rowIds[key],{
                        sl:rowIds[key]
                    })
                }
            },
            onSelectRow: function (rowid, status) {
//                executeEditChargeType();
            }
        });
//        $("#jqgrid-grid-uploadDataInfo").jqGrid('navGrid', '#jqgrid-pager-uploadDataInfo', {edit: false, add: false, del: false, search: false});
//        $(".ui-pg-selbox").children().each(function () {
//            if ($(this).val() == -1) {
//                $(this).html('All')
//            }
//        });
    });

    function executePreCondition() {
        if(!$('#chartOfAccountUploadForm').validationEngine('validate')){
            return false;
        }

        var rowIds = $("#jqgrid-grid-uploadDataInfo").jqGrid('getDataIDs');
        if (rowIds == '') {
            MessageRenderer.render({
                "messageBody": "No data found from excel sheet.",
                "messageTitle": "Chart Of Account Upload",
                "type": "0"
            });
            return false;
        }

        if($('#enterpriseConfiguration').val() == ''){
            MessageRenderer.render({
                "messageBody": "Please select Enterprise first.",
                "messageTitle": "Chart Of Account Upload",
                "type": "0"
            });
            return false;
        }

        return true;
    }

    function executeAjaxUpload() {
        if (!executePreCondition()) {
            return false;
        }

        var dataSet = {}

        var grid =  $("#jqgrid-grid-uploadDataInfo");
        var gridData = grid.jqGrid('getRowData');

        dataSet['confirm'] = false;
        dataSet['enterprise'] = $('#enterpriseConfiguration').val();
        dataSet['transactionDate'] = $('#transactionDate').val();

        for(var i=0; i<gridData.length; i++){
            dataSet["dataList.data["+i+"].rowNum"] = gridData[i].sl;
            dataSet["dataList.data["+i+"].prefixCode"] = gridData[i].prefixCode;
            dataSet["dataList.data["+i+"].accountCode"] = gridData[i].accountCode;
            dataSet["dataList.data["+i+"].drBalance"] = gridData[i].drBalance;
            dataSet["dataList.data["+i+"].crBalance"] = gridData[i].crBalance;
            dataSet["dataList.data["+i+"].postFixCode"] = gridData[i].postFixCode;
        }

        var actionUrl = "${request.contextPath}/${params.controller}/ajaxUpload";

        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: dataSet,
            url: actionUrl,
//            contentType: false,
//            processData: false,
//            dataType: 'scriptData',
//            cache: false,
            dataType: 'json',
            success: function (data, textStatus) {
                if(data.message.type == 1){
                    executePostConditionChargeType(dataSet);
                    MessageRenderer.render(data.message);
                }else{
//                    MessageRenderer.render(data.message);
                    var msg = '';

                    if(data.accCodeLine){
                        if(msg) {
                            msg += "<br> System does not found 'Account Code' in line (" + data.accCodeLine.split(',').sort() + ")";
                        }else{
                            msg = "System does not found 'Account Code' in line (" + data.accCodeLine.split(',').sort() + ")";
                        }
                    }

                    if(data.preFixLine){
                        if(msg) {
                            msg += "<br> System does not found 'Prefix-Code' in line (" + data.preFixLine.split(',').sort() + ")";
                        }else{
                            msg = "System does not found 'Prefix-Code' in line (" + data.preFixLine.split(',').sort() + ")";
                        }
                    }

                    if(data.postFixLine){
                        if(msg) {
                            msg += "<br> System does not found 'Post-Fix-Code' in line (" + data.postFixLine.split(',').sort() + ")";
                        }else{
                            msg = "System does not found 'Post-Fix-Code' in line (" + data.postFixLine.split(',').sort() + ")";
                        }
                    }

                    MessageRenderer.render({
                        "messageBody": msg,
                        "messageTitle": "Chart Of Account Upload",
                        "type": "0"
                    });

                    for(var i=0; i<data.lines.split(',').length; i++){
                        grid.jqGrid('setRowData',data.lines.split(',')[i],false, {'font-weight':'bold', color:'white', background:'#FF6060'});
                    }

                    for(var i=0; i<data.accCodeLine.split(',').length; i++){
                        grid.jqGrid('setCell',data.accCodeLine.split(',')[i],'accountCode', '', 'my-highlight');
                    }

                    for(var i=0; i<data.preFixLine.split(',').length; i++){
                        grid.jqGrid('setCell',data.preFixLine.split(',')[i],'prefixCode', '', 'my-highlight');
                    }

                    for(var i=0; i<data.postFixLine.split(',').length; i++){
                        grid.jqGrid('setCell',data.postFixLine.split(',')[i],'postFixCode', '', 'my-highlight');
                    }
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
//                    reset_chargeType_form('#chartOfAccountUploadForm');
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                } else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete: function () {
                SubmissionLoader.hideFrom();
            }
        });
        return false;
    }

    function executePostConditionChargeType(dataSet) {
        dataSet['confirm'] = true;
        var actionUrl = "${request.contextPath}/${params.controller}/ajaxUpload";

        $("#custom_message").html("System verified that all data is ok. Do you really want to proceed to upload all data?");

        $("#dialog-confirm-chargeType").dialog({
            resizable: false,
            height: 150,
            modal: true,
            title: 'Confirm Dialog',
            buttons: {
                Yes: function () {
                    $(this).dialog('close');
                    SubmissionLoader.showTo();
                    jQuery.ajax({
                        url: actionUrl,
                        type: "POST",
                        data: dataSet,
                        dataType: "json",
                        beforeSend: function () {
                            $("#upload-button").attr('disabled', 'disabled')
                        },
                        success: function (result) {
                            if (result.type == 1) {
                                $("#jqgrid-grid-uploadDataInfo").jqGrid('clearGridData');
                            }
                            MessageRenderer.render(result);
                        },
                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                            if(XMLHttpRequest.status = 0){
                                MessageRenderer.renderErrorText("Network Problem: Time out");
                            } else{
                                MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                            }
                        },
                        complete: function () {
                            $("#upload-button").removeAttr('disabled');
                            SubmissionLoader.hideFrom();
                        }
                    });
                },
                No: function () {
                    $(this).dialog('close');
                }
            }
        }); //end of dialog
    }

    function reset_chargeType_form(formName) {
        $(formName).find(":input").not(":button, :submit, :reset, :checkbox, :radio, :selected").each(function () {
            if (this.type == 'hidden') {
                this.value = "";
            } else {
                this.value = this.defaultValue;
            }
        });

        $(formName + ' input[name = create-button]').attr('value', 'Create');
        $(formName + ' input[name = delete-button]').hide();
        $(formName + ' select').val('');
    }
</script>

<style type="text/css">
.my-highlight {background-color: yellow; color: red; font-weight: bold !important; }
</style>