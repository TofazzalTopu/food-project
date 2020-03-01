<style>
    #gview_jqgrid-grid .ui-state-highlight { background: limegreen !important; }
</style>
<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        $('#ui-widget-header-text').html('Workflow');
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormWorkflow").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormWorkflow").validationEngine('attach');
        reset_Workflow_form("#gFormWorkflow");
        $("#jqgrid-grid").jqGrid({
            url: '${resource(dir:'workflow', file:'list')}?enterpriseId=' + $('#enterpriseConfiguration').val(),
            datatype: "json",
            colNames: [
                'SL',
                'ID',

                'Enterprise',
                'Workflow Name',
                'Menu Name'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30, sortable: false, align: 'left'},
                {name: 'id', index: 'id', width: 0, hidden: true},

                {name: 'enterpriseConfiguration', index: 'enterpriseConfiguration', width: 100, align: 'left'},
                {name: 'name', index: 'name', width: 200, align: 'left'},
                {name: 'menuName', index: 'menuName', width: 250, align: 'left'}
            ],
            rowNum: 50,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#jqgrid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Workflow List",
            autowidth: true,
            height: true,
            scrollOffset: 0,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                executeEditWorkflow();
            }
        });
        $("#jqgrid-grid").jqGrid('navGrid', '#jqgrid-pager', {edit: false, add: false, del: false, search: false});
//     $("#jqgrid-grid").gridResize({minWidth:350,maxWidth:800,minHeight:200});
//      $(".ui-pg-selbox").children().each(function() {
//          if ($(this).val() == -1) {
//              $(this).html('All')
//          }
//
//      });
        $("#prioritySequence").format({precision: 2, allow_negative: false, autofix: true});
    });
    function getSelectedWorkflowId() {
        var workflowId = null;
        var rowid = $("#jqgrid-grid").jqGrid('getGridParam', 'selrow');
        if (rowid) {
            workflowId = $("#jqgrid-grid").jqGrid('getCell', rowid, 'id');
        }
        if (workflowId == null) {
            workflowId = $('#gFormWorkflow input[name = id]').val();
        }
        return workflowId;
    }
    function executePreConditionWorkflow() {
        // trim field vales before process.
        trim_form();
        if (!$("#gFormWorkflow").validationEngine('validate')) {
            return false;
        }
        return true;
    }
    function executeAjaxWorkflow() {
        if (!executePreConditionWorkflow()) {
            return false;
        }

        $('#jqgrid-grid-applicationUser').jqGrid("editCell", 0, 0, false);

        var actionUrl = null;
        if ($('#gFormWorkflow input[name = id]').val()) {
            actionUrl = "${request.contextPath}/${params.controller}/update";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/create";
        }
        var data = jQuery("#gFormWorkflow").serializeArray();
        var userData = $("#jqgrid-grid-applicationUser").jqGrid('getRowData');
        var length = userData.length;
        for (var i=0; i < length; ++i) {
            data.push({'name':'users.workflowUserMapping['+i+'].applicationUser.id', 'value': userData[i].id});
            data.push({'name':'users.workflowUserMapping['+i+'].prioritySequence', 'value': userData[i].prioritySequence});
            data.push({'name':'users.workflowUserMapping['+i+'].id', 'value': userData[i].workflowUserMappingId});
        }
        var customerData = $("#jqgrid-grid-customerMaster").jqGrid('getRowData');
        var customerDataLength = customerData.length;
        for (var j=0; j < customerDataLength; ++j) {
            data.push({'name':'customers.workflowCustomerMapping['+j+'].customerMaster.id', 'value': customerData[j].id});
            data.push({'name':'customers.workflowCustomerMapping['+j+'].id', 'value': customerData[j].workflowCustomerMappingId});
        }
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: data,
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionWorkflow(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-grid").trigger("reloadGrid");
                    $("#jqgrid-grid-applicationUser").clearGridData();
                    $("#jqgrid-grid-customerMaster").clearGridData();
                    reset_Workflow_form('#gFormWorkflow');

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
    function executePostConditionWorkflow(result) {
        if (result.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            $("#jqgrid-grid-applicationUser").clearGridData();
            $("#jqgrid-grid-customerMaster").clearGridData();
            reset_Workflow_form('#gFormWorkflow');
        }
        MessageRenderer.render(result);
    }
    function deleteAjaxWorkflow() {    // Delete record
        var workflowId = getSelectedWorkflowId();
        if (executePreConditionForDeleteWorkflow(workflowId)) {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-workflow").dialog({
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
                            data: jQuery("#gFormWorkflow").serialize(),
                            url: "${resource(dir:'workflow', file:'delete')}",
                            success: function (message) {
                                executePostConditionForDeleteWorkflow(message);
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

    function executePreConditionForEditWorkflow(workflowId) {
        if (workflowId == null) {
            alert("Please select a workflow to edit");
            return false;
        }
        return true;
    }
    function executeEditWorkflow() {
        var workflowId = getSelectedWorkflowId();
        if (executePreConditionForEditWorkflow(workflowId)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'workflow', file:'edit')}?id=" + workflowId,
                success: function (entity) {
                    executePostConditionForEditWorkflow(entity);
                },
                dataType: 'json'
            });
        }
    }
    function executePostConditionForEditWorkflow(data) {
        if (data == null) {
            alert('Selected workflow might have been deleted by someone');  //Message renderer
        } else {
            showWorkflow(data);
        }
    }
    function executePreConditionForDeleteWorkflow(workflowId) {
        if (workflowId == null) {
            alert("Please select a workflow to delete");
            return false;
        }
        return true;
    }
    function executePostConditionForDeleteWorkflow(data) {
        if (data.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_Workflow_form('#gFormWorkflow');
        }
        MessageRenderer.render(data)
    }
    function showWorkflow(entity) {
        var workflow = entity.workflow
        var enterpriseConfiguration = entity.enterpriseConfiguration

        $('#gFormWorkflow input[name = id]').val(workflow.id);
        $('#gFormWorkflow input[name = version]').val(workflow.version);

        if (enterpriseConfiguration) {
            $('#enterpriseList').setValue(enterpriseConfiguration.name);
            $('#enterpriseConfiguration').val(enterpriseConfiguration.id);
            $("#enterpriseList_input").attr('readonly',true);
        }

        $('#name').val(workflow.name);
        $('#menuName').val(workflow.menuName);
        $('#description').val(workflow.description);

        jQuery("#jqgrid-grid-applicationUser").jqGrid().setGridParam({url:
        '${resource(dir:'workflow', file:'listUserMapping')}?id=' + $('#id').val(),
            datatype: "json",mtype: "POST"}).trigger("reloadGrid");

        jQuery("#jqgrid-grid-customerMaster").jqGrid().setGridParam({url:
                '${resource(dir:'workflow', file:'listCustomerMapping')}?id=' + $('#id').val(),
            datatype: "json",mtype: "POST"}).trigger("reloadGrid");

        $('#create-button').attr('value', 'Update');
        $('#cancel-button').attr('value', 'Cancel');
        $('#delete-button').show();

    }
    function executeSearchWorkflow(fieldName, fieldValue) {
        if (executePreConditionForSearchWorkflow(fieldName, fieldValue)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'workflow', file:'search')}?fieldName=" + fieldName + "&fieldValue=" + fieldValue,
                success: function (entity) {
                    executePostConditionForSearchWorkflow(entity, fieldName, fieldValue);
                },
                dataType: 'json'
            });
        }
    }
    function executePreConditionForSearchWorkflow(fieldName, fieldValue) {
        // trim field vales before process.
        $('#' + fieldName).val($.trim($('#' + fieldName).val()));
        if (fieldValue == '' || fieldValue == null) {
            reset_Workflow_form("#gFormWorkflow");
            return false;
        }
        return true;
    }
    function executePostConditionForSearchWorkflow(data, fieldName, fieldValue) {
        if (data == null) {
            reset_Workflow_form("#gFormWorkflow"); // Clear Form
            $('#' + fieldName).val(fieldValue); // Set search field with fieldValue
        } else {
            showWorkflow(data);
        }
    }
    function reset_Workflow_form(formName) {
        var enterprise = $("#enterpriseConfiguration").val();
        $(formName).find(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
            if (this.type == 'hidden') {
                this.value = "";
            } else {
                this.value = this.defaultValue;
            }
        });
        $(formName+' select').val('');
        $(formName+' input').attr('readonly',false);
//    $('input[type=checkbox]').attr('checked', false);
        $(formName + ' input[name = create-button]').attr('value', 'Create');
        $(formName + ' input[name = delete-button]').hide();
        $("#enterpriseConfiguration").val(enterprise);
        $("#menuName").attr('readonly', true);
    }
</script>