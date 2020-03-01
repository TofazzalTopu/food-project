<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        $('#ui-widget-header-text').html('DemandOrderApprovalWorkflow')
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormDemandOrderApprovalWorkflow").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormDemandOrderApprovalWorkflow").validationEngine('attach');
        reset_form("#gFormDemandOrderApprovalWorkflow");
        $("#jqgrid-grid").jqGrid({
            url: '${resource(dir:'demandOrderApprovalWorkflow', file:'list')}',
            datatype: "json",
            colNames: [
                'SL',
                'ID',

                'Workflow',
                'Is Approve',
                'Is Reject',
                'User Approved Reject',
                'Date Approved Reject',
                'User Created',
                'User Updated',
                'Date Created',
                'Last Updated',
                'Enterprise Configuration'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30, sortable: false, align: 'left'},
                {name: 'id', index: 'id', width: 0, hidden: true},

                {name: 'workflow', index: 'workflow', width: 100, align: 'left'},
                {name: 'isApprove', index: 'isApprove', width: 100, align: 'left'},
                {name: 'isReject', index: 'isReject', width: 100, align: 'left'},
                {name: 'userApprovedReject', index: 'userApprovedReject', width: 100, align: 'left'},
                {name: 'dateApprovedReject', index: 'dateApprovedReject', width: 100, align: 'left'},
                {name: 'userCreated', index: 'userCreated', width: 100, align: 'left'},
                {name: 'userUpdated', index: 'userUpdated', width: 100, align: 'left'},
                {name: 'dateCreated', index: 'dateCreated', width: 100, align: 'left'},
                {name: 'lastUpdated', index: 'lastUpdated', width: 100, align: 'left'},
                {name: 'enterpriseConfiguration', index: 'enterpriseConfiguration', width: 100, align: 'left'}
            ],
            rowNum: 50,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#jqgrid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "All DemandOrderApprovalWorkflow Information",
            autowidth: true,
            height: 350,
            scrollOffset: 0,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                executeEditDemandOrderApprovalWorkflow();
            }
        });
        $("#jqgrid-grid").jqGrid('navGrid', '#jqgrid-pager', {edit: false, add: false, del: false, search: false});
        $("#jqgrid-grid").gridResize({minWidth: 350, maxWidth: 800, minHeight: 200});
        $(".ui-pg-selbox").children().each(function () {
            if ($(this).val() == -1) {
                $(this).html('All')
            }

        });
    });
    function getSelectedDemandOrderApprovalWorkflowId() {
        var demandOrderApprovalWorkflowId = null;
        var rowid = $("#jqgrid-grid").jqGrid('getGridParam', 'selrow');
        if (rowid) {
            demandOrderApprovalWorkflowId = $("#jqgrid-grid").jqGrid('getCell', rowid, 'id');
        }
        if (demandOrderApprovalWorkflowId == null) {
            demandOrderApprovalWorkflowId = $('#gFormDemandOrderApprovalWorkflow input[name = id]').val();
        }
        return demandOrderApprovalWorkflowId;
    }
    function executePreConditionDemandOrderApprovalWorkflow() {
        // trim field vales before process.
        trim_form();
        if ($("#gFormDemandOrderApprovalWorkflow").validate().form({onfocusout: false}) == false) {
            return false;
        }
        return true;
    }
    function executeAjaxDemandOrderApprovalWorkflow() {
        if (!executePreConditionDemandOrderApprovalWorkflow()) {
            return false;
        }
        var actionUrl = null;
        if ($('#gFormDemandOrderApprovalWorkflow input[name = id]').val()) {
            actionUrl = "${request.contextPath}/${params.controller}/update";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/create";
        }
        SubmissionLoader.showTo()
        jQuery.ajax({
            type: 'post',
            data: jQuery("#gFormDemandOrderApprovalWorkflow").serialize(),
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionDemandOrderApprovalWorkflow(data);
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
        return false;
    }
    function executePostConditionDemandOrderApprovalWorkflow(result) {
        if (result.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_form('#gFormDemandOrderApprovalWorkflow');
        }
        MessageRenderer.render(result);
    }
    function deleteAjaxDemandOrderApprovalWorkflow() {    // Delete record
        var demandOrderApprovalWorkflowId = getSelectedDemandOrderApprovalWorkflowId();
        if (executePreConditionForDeleteDemandOrderApprovalWorkflow(demandOrderApprovalWorkflowId)) {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-demandOrderApprovalWorkflow").dialog({
                resizable: false,
                height: 150,
                modal: true,
                title: 'Delete alert',
                buttons: {
                    'Delete item(s)': function () {
                        $(this).dialog('close');
                        $.ajax({
                            type: "POST",
                            dataType: "json",
                            data: jQuery("#gFormDemandOrderApprovalWorkflow").serialize(),
                            url: "${resource(dir:'demandOrderApprovalWorkflow', file:'delete')}",
                            success: function (message) {
                                executePostConditionForDeleteDemandOrderApprovalWorkflow(message);
                            }
                        });
                    },
                    Cancel: function () {
                        $(this).dialog('close');
                    }
                }
            }); //end of dialog
        }
    }

    function executePreConditionForEditDemandOrderApprovalWorkflow(demandOrderApprovalWorkflowId) {
        if (demandOrderApprovalWorkflowId == null) {
            alert("Please select a demandOrderApprovalWorkflow to edit");
            return false;
        }
        return true;
    }
    function executeEditDemandOrderApprovalWorkflow() {
        var demandOrderApprovalWorkflowId = getSelectedDemandOrderApprovalWorkflowId();
        if (executePreConditionForEditDemandOrderApprovalWorkflow(demandOrderApprovalWorkflowId)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'demandOrderApprovalWorkflow', file:'edit')}?id=" + demandOrderApprovalWorkflowId,
                success: function (entity) {
                    executePostConditionForEditDemandOrderApprovalWorkflow(entity);
                },
                dataType: 'json'
            });
        }
    }
    function executePostConditionForEditDemandOrderApprovalWorkflow(data) {
        if (data == null) {
            alert('Selected demandOrderApprovalWorkflow might have been deleted by someone');  //Message renderer
        } else {
            showDemandOrderApprovalWorkflow(data);
        }
    }
    function executePreConditionForDeleteDemandOrderApprovalWorkflow(demandOrderApprovalWorkflowId) {
        if (demandOrderApprovalWorkflowId == null) {
            alert("Please select a demandOrderApprovalWorkflow to delete");
            return false;
        }
        return true;
    }
    function executePostConditionForDeleteDemandOrderApprovalWorkflow(data) {
        if (data.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_form('#gFormDemandOrderApprovalWorkflow');
        }
        MessageRenderer.render(data)
    }
    function showDemandOrderApprovalWorkflow(entity) {
        $('#gFormDemandOrderApprovalWorkflow input[name = id]').val(entity.id);
        $('#gFormDemandOrderApprovalWorkflow input[name = version]').val(entity.version);

        if (entity.workflow) {
            $('#workflow').val(entity.workflow.id).attr("selected", "selected");
        }
        else {
            $('#workflow').val(entity.workflow);
        }
        $('#isApprove').attr('checked', entity.isApprove);
        $('#isReject').attr('checked', entity.isReject);
        if (entity.userApprovedReject) {
            $('#userApprovedReject').val(entity.userApprovedReject.id).attr("selected", "selected");
        }
        else {
            $('#userApprovedReject').val(entity.userApprovedReject);
        }
        $('#dateApprovedReject').val(entity.dateApprovedReject);
        if (entity.userCreated) {
            $('#userCreated').val(entity.userCreated.id).attr("selected", "selected");
        }
        else {
            $('#userCreated').val(entity.userCreated);
        }
        if (entity.userUpdated) {
            $('#userUpdated').val(entity.userUpdated.id).attr("selected", "selected");
        }
        else {
            $('#userUpdated').val(entity.userUpdated);
        }
        $('#dateCreated').val(entity.dateCreated);
        $('#lastUpdated').val(entity.lastUpdated);
        if (entity.enterpriseConfiguration) {
            $('#enterpriseConfiguration').val(entity.enterpriseConfiguration.id).attr("selected", "selected");
        }
        else {
            $('#enterpriseConfiguration').val(entity.enterpriseConfiguration);
        }
        $('#create-button').attr('value', 'Update');
        $('#cancel-button').attr('value', 'Cancel');
        $('#delete-button').show();
    }
    function executeSearchDemandOrderApprovalWorkflow(fieldName, fieldValue) {
        if (executePreConditionForSearchDemandOrderApprovalWorkflow(fieldName, fieldValue)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'demandOrderApprovalWorkflow', file:'search')}?fieldName=" + fieldName + "&fieldValue=" + fieldValue,
                success: function (entity) {
                    executePostConditionForSearchDemandOrderApprovalWorkflow(entity, fieldName, fieldValue);
                },
                dataType: 'json'
            });
        }
    }
    function executePreConditionForSearchDemandOrderApprovalWorkflow(fieldName, fieldValue) {
        // trim field vales before process.
        $('#' + fieldName).val($.trim($('#' + fieldName).val()));
        if (fieldValue == '' || fieldValue == null) {
            reset_form("#gFormDemandOrderApprovalWorkflow");
            return false;
        }
        return true;
    }
    function executePostConditionForSearchDemandOrderApprovalWorkflow(data, fieldName, fieldValue) {
        if (data == null) {
            reset_form("#gFormDemandOrderApprovalWorkflow"); // Clear Form
            $('#' + fieldName).val(fieldValue); // Set search field with fieldValue
        } else {
            showDemandOrderApprovalWorkflow(data);
        }
    }
</script>