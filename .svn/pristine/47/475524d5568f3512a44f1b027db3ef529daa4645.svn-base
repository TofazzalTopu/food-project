<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        $('#ui-widget-header-text').html('SetupIncentive');
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormSearchIncentive").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormSearchIncentive").validationEngine('attach');

//        Date Range
        $("#effectiveDateFrom, #effectiveDateTo").datepicker(
                {
                    dateFormat: 'dd-mm-yy',
                    changeMonth: true,
                    changeYear: true
                });

        $('#effectiveDateFrom, #effectiveDateTo').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});

        //        Incentive Slab
        $("#jqgrid-search-grid").jqGrid({
            url: '',
            datatype: "json",
            colNames: [
                'Customer Name',
                '',
                'Customer ID',
                'Net Sales Amount',
                'Incentive Amount',
                'cIds'
            ],
            colModel: [
                {name: 'name', index: 'name', width: 200, align: 'left'},
                {name: 'customerId', index: 'customerId', width: 100, align: 'left',hidden:true},
                {name: 'code', index: 'code', width: 100, align: 'left'},
                {name: 'netSalesAmount', index: 'netSalesAmount', width: 120, align: 'left'},
                {name: 'incentiveAmount', index: 'incentiveAmount', width: 120, align: 'left'},
                {name: 'cIds', index: 'cIds', width: 120, align: 'left',hidden:true}

            ],
            rowNum: 10,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#jqgrid-search-pager',
            sortname: 'customerName',
            viewrecords: true,
            sortorder: "asc",
            caption: "Eligible Customers",
            autowidth: false,
            height: true,
            scrollOffset: 0,
            loadComplete: function () {
                %{--var rowIds = $("#jqgrid-search-grid").jqGrid('getDataIDs')--}%
                %{--for(key in rowIds){--}%
                    %{--$("#jqgrid-search-grid").jqGrid('setRowData', rowIds[key],{--}%
                        %{--action: '<a target="_blank" href="${request.contextPath}/setupIncentive/show?id=' + rowIds[key] + '&type='+$('#programType').val()+'" title="Update Insentive">Update</a>'--}%
                    %{--})--}%
                %{--}--}%
            },
            onSelectRow: function (rowid, status) {

            }
        });
        $("#tb-search-grid").jqGrid('navGrid', '#jqgrid-search-pager', {edit: false, add: false, del: false, search: false});
        $(".ui-pg-selbox").children().each(function () {
            if ($(this).val() == -1) {
                $(this).html('All')
            }
        });

        $("#effectiveDateFrom, #effectiveDateTo").change(function(){
            getIncentivePrograms($('#programType').val());
            executeAjaxSearchIncentiveCustomers($('#programName').val());
        });
    });

    function executeSearchIncentivePrecondition(){
        if(!$("#gFormSearchIncentive").validationEngine('validate')){
            return false;
        }
        return true;
    }

    function executeAjaxSearchIncentiveCustomers(id){
        if(!id){
            $("#jqgrid-search-grid").clearGridData();
            return false;
        }
        if(!executeSearchIncentivePrecondition()){
            return false;
        }
        jQuery("#jqgrid-search-grid").clearGridData();
        var data = $("#gFormSearchIncentive").serialize();

        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: data,
            url:  "${request.contextPath}/${params.controller}/getAllSearchIncentiveCustomerList",
            success: function (data, textStatus) {
//                console.log(data);
                if(data[0]){
                    jQuery("#jqgrid-search-grid")
                            .jqGrid('setGridParam',
                            {
                                datatype: 'local',
                                data: data
                            })
                            .trigger("reloadGrid");
                }else{
                    jQuery("#jqgrid-search-grid").clearGridData();
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    jQuery("#jqgrid-search-grid")
                            .jqGrid('setGridParam',
                            {
                                datatype: 'local',
                                data: data
                            })
                            .trigger("reloadGrid");
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
    }

    function getIncentivePrograms(programType){
        $('#programName').html("");
        $('#programName').append('<option value="">Select program name...</option>');
        $("#jqgrid-search-grid").clearGridData();
        if(!programType){
            return false;
        }

        var effectiveDateFrom = $('#effectiveDateFrom').val();
        var effectiveDateTo = $('#effectiveDateTo').val();

        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: {programType:programType, effectiveDateFrom:effectiveDateFrom, effectiveDateTo:effectiveDateTo},
            url:  "${request.contextPath}/${params.controller}/getAllSearchIncentiveProgramList",
            success: function (data, textStatus) {
                if(data[0]){
                    $.each(data, function(key, val){
                        $('#programName').append('<option value="'+val.id+'">'+ val.programName +'</option>');
                    });
                }else{
                    $('#programName').html("");
                    $('#programName').append('<option value="">Select program name...</option>');
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    jQuery("#jqgrid-search-grid")
                            .jqGrid('setGridParam',
                            {
                                datatype: 'local',
                                data: data
                            })
                            .trigger("reloadGrid");
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
    }

    function calculateIncentive(){
        var ids = $("#jqgrid-search-grid").jqGrid('getDataIDs');
        SubmissionLoader.showTo();
        for(key in ids){
            var customerId = $("#jqgrid-search-grid").jqGrid('getCell',ids[key],'customerId');

            getNetSalesAmount(ids[key],customerId);
//            getIncentiveAmount(ids[key],customerId);
        }
        SubmissionLoader.hideFrom();
    }

    function getNetSalesAmount(rowId,customerId){
        jQuery.ajax({
            type: 'post',
            data: {programType:$('#programType').val(), programId:$('#programName').val(), customerId:customerId},
            url:  "${request.contextPath}/${params.controller}/getNetSalesAmountByCustomer",
            success: function (data, textStatus) {
                $("#jqgrid-search-grid").jqGrid('setRowData', rowId,{
                    netSalesAmount: data.netSales,
                    incentiveAmount: data.incentiveAmount,
                    cIds: data.cIds
                });
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-search-grid").jqGrid('setRowData', rowId,{
                        netSalesAmount: data.netSales,
                        incentiveAmount: data.incentiveAmount,
                        cIds: data.cIds
                    });
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                }
                else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete: function () {
            },
            dataType: 'json'
        });
    }

    /*
    function getIncentiveAmount(rowId,customerId){
        var ret = '';
        jQuery.ajax({
            type: 'post',
            data: {programType:$('#programType').val(), customerId:customerId},
            url:  "${request.contextPath}/${params.controller}/getIncentiveAmountByCustomer",
            success: function (data, textStatus) {
                $("#jqgrid-search-grid").jqGrid('setRowData', rowId,{
                    incentiveAmount: 'Incentive-'+data
                });
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                MessageRenderer.renderErrorText(XMLHttpRequest.statusText);
            },
            complete: function () {
            },
            dataType: 'json'
        });
    } */

    function adjustIncentive(){
        var allData = {};
        var customerIds = '';
        var ids = $("#jqgrid-search-grid").jqGrid('getDataIDs');
        for(key in ids){
            var incentiveAmount = $("#jqgrid-search-grid").jqGrid('getCell',ids[key],'incentiveAmount');
            var customerId = $("#jqgrid-search-grid").jqGrid('getCell',ids[key],'customerId');
            var cIds = $("#jqgrid-search-grid").jqGrid('getCell',ids[key],'cIds');
            if(incentiveAmount>0){
                if(customerIds){
                    customerIds += ','+customerId;
                }else{
                    customerIds = customerId;
                }

                allData['allIncentive.incentive['+ids[key]+'].incentiveType'] = $('#programType').val();
                allData['allIncentive.incentive['+ids[key]+'].incentiveProgram'] = $('#programName').val();
                allData['allIncentive.incentive['+ids[key]+'].customer.id'] = customerId;
                allData['allIncentive.incentive['+ids[key]+'].incentiveAmount'] = incentiveAmount;
                allData['allIncentive.incentive['+ids[key]+'].cIds'] = cIds;
            }
        }

        if(customerIds == ''){
            MessageRenderer.render({
                "messageBody": "No incentive amount is found.",
                "messageTitle": "Calculate and Adjust Incentive",
                "type": "0"
            });
            return false;
        }else{
            SubmissionLoader.showTo();
            jQuery.ajax({
                type: 'post',
                url: "${resource(dir:'calculateAndAdjustIncentive', file:'adjustIncentive')}",
                data:allData,
                success: function (data) {
                    if(data.type == 1){
                        executeAjaxSearchIncentiveCustomers();
                    }
                    MessageRenderer.render(data);
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    if(XMLHttpRequest.status = 0){
                        MessageRenderer.renderErrorText("Network Problem: Time out");
                    } else{
                        MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                    }
                },
                complete: function () {
                    SubmissionLoader.hideFrom();
                },
                dataType: 'json'
            });
        }
    }
</script>