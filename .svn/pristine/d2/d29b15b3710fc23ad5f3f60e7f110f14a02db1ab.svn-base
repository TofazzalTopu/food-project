<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        $('#ui-widget-header-text').html('SetupIncentive');
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormUpdateIncentive").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormUpdateIncentive").validationEngine('attach');

//        Date Range
        $("#effectiveDateFrom").datepicker(
                {
                    dateFormat: 'dd-mm-yy',
                    changeMonth: true,
                    changeYear: true
                });
        $("#effectiveDateTo").datepicker(
                {
                    dateFormat: 'dd-mm-yy',
                    changeMonth: true,
                    changeYear: true
                });
        $('#effectiveDateFrom').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});
        $('#effectiveDateTo').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});

        //        Incentive Slab
        $("#jqgrid-search-grid").jqGrid({
            url: '',
            datatype: "json",
            colNames: [
                'ID',
                'Program Name',
                'Program Creation Date',
                'Effective From',
                'Effective To',
                'Status',
                'Action'
            ],
            colModel: [
                {name: 'id', index: 'id', width: 30, align: 'left', hidden:true},
                {name: 'programName', index: 'programName', width: 120, align: 'left'},
                {name: 'dateCreated', index: 'dateCreated', width: 100, align: 'left'},
                {name: 'effectiveDateFrom', index: 'effectiveDateFrom', width: 90, align: 'left'},
                {name: 'effectiveDateTo', index: 'effectiveDateTo', width: 90, align: 'left'},
                {name: 'status', index: 'status', width: 70, align: 'left',hidden:true},
                {name: 'action', index: 'action', width: 60, align: 'center'}
            ],
            rowNum: 10,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#jqgrid-search-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "asc",
            caption: "Incentive Programs",
            autowidth: false,
            height: true,
            scrollOffset: 0,
            loadComplete: function () {
                var rowIds = $("#jqgrid-search-grid").jqGrid('getDataIDs')
                for(key in rowIds){
                    $("#jqgrid-search-grid").jqGrid('setRowData', rowIds[key],{
                        action: '<a target="_blank" href="${request.contextPath}/setupIncentive/show?id=' + rowIds[key] + '&type='+$('#programType').val()+'" title="Update Insentive">Update</a>'
                    })
                }
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
    });
    function executeSearchIncentivePrecondition(){
        if(!$("#gFormUpdateIncentive").validationEngine('validate')){
            return false;
        }
        return true;
    }
    function executeAjaxSearchIncentiveProgram(){
        if(!executeSearchIncentivePrecondition()){
            return false;
        }
        jQuery("#jqgrid-search-grid").clearGridData();
        var data = $("#gFormUpdateIncentive").serialize();
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: data,
            url:  "${request.contextPath}/${params.controller}/getAllSearchIncentiveProgramList",
            success: function (data, textStatus) {
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
                    jQuery("#jqgrid-search-grid").clearGridData();
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
    function getIncentivePrograms(id){
        $('#programName').html("");
        $('#programName').append('<option value="">Select program name...</option>');

        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: {id:id},
            url:  "${request.contextPath}/${params.controller}/getIncentiveProgramList",
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
                    $('#programName').html("");
                    $('#programName').append('<option value="">Select program name...</option>');

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

</script>