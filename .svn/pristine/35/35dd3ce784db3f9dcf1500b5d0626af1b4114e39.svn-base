<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        $('#ui-widget-header-text').html('MeasureUnitConfiguration')
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormMeasureUnitConfiguration").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormMeasureUnitConfiguration").validationEngine('attach');
        reset_measureUnit_form("#gFormMeasureUnitConfiguration");
        $("#jqgrid-grid").jqGrid({
            url: '${resource(dir:'measureUnitConfiguration', file:'list')}',
            datatype: "json",
            colNames: [
                'SL',
                'ID',

                'Enterprise',
                'UoM Name',
                'Note',
                'Is Master Uom'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30, sortable: false, align: 'left'},
                {name: 'id', index: 'id', width: 0, hidden: true},

                {name: 'enterpriseConfiguration', index: 'enterpriseConfiguration', width: 100, align: 'left'},
                {name: 'name', index: 'name', width: 100, align: 'left'},
                {name: 'note', index: 'note', width: 100, align: 'left'},
                {name: 'isMasterUom', index: 'isMasterUom', width: 50, align: 'center'}

            ],
            rowNum: 50,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#jqgrid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Measurement Unit List",
            autowidth: true,
            height: true,
            scrollOffset: 0,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                executeEditMeasureUnitConfiguration();
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
    });
    function getSelectedMeasureUnitConfigurationId() {
        var measureUnitConfigurationId = null;
        var rowid = $("#jqgrid-grid").jqGrid('getGridParam', 'selrow');
        if (rowid) {
            measureUnitConfigurationId = $("#jqgrid-grid").jqGrid('getCell', rowid, 'id');
        }
        if (measureUnitConfigurationId == null) {
            measureUnitConfigurationId = $('#gFormMeasureUnitConfiguration input[name = id]').val();
        }
        return measureUnitConfigurationId;
    }
    function executePreConditionMeasureUnitConfiguration() {
        // trim field vales before process.
        trim_form();
        if (!$("#gFormMeasureUnitConfiguration").validationEngine('validate')) {
            return false;
        }
        return true;
    }
    function executeAjaxMeasureUnitConfiguration() {
        if (!executePreConditionMeasureUnitConfiguration()) {
            return false;
        }
        var actionUrl = null;
        if ($('#gFormMeasureUnitConfiguration input[name = id]').val()) {
            actionUrl = "${request.contextPath}/${params.controller}/update";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/create";
        }
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: jQuery("#gFormMeasureUnitConfiguration").serialize(),
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionMeasureUnitConfiguration(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-grid").trigger("reloadGrid");
                    reset_measureUnit_form('#gFormMeasureUnitConfiguration');
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                }  else{
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
    function executePostConditionMeasureUnitConfiguration(result) {
        if (result.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_measureUnit_form('#gFormMeasureUnitConfiguration');
        }
        MessageRenderer.render(result);
    }
    function deleteAjaxMeasureUnitConfiguration() {    // Delete record
        var measureUnitConfigurationId = getSelectedMeasureUnitConfigurationId();
        if (executePreConditionForDeleteMeasureUnitConfiguration(measureUnitConfigurationId)) {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-measureUnitConfiguration").dialog({
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
                            data: jQuery("#gFormMeasureUnitConfiguration").serialize(),
                            url: "${resource(dir:'measureUnitConfiguration', file:'delete')}",
                            success: function (message) {
                                executePostConditionForDeleteMeasureUnitConfiguration(message);
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

    function executePreConditionForEditMeasureUnitConfiguration(measureUnitConfigurationId) {
        if (measureUnitConfigurationId == null) {
            alert("Please select a measureUnitConfiguration to edit");
            return false;
        }
        return true;
    }
    function executeEditMeasureUnitConfiguration() {
        var measureUnitConfigurationId = getSelectedMeasureUnitConfigurationId();
        if (executePreConditionForEditMeasureUnitConfiguration(measureUnitConfigurationId)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'measureUnitConfiguration', file:'edit')}?id=" + measureUnitConfigurationId,
                success: function (entity) {
                    executePostConditionForEditMeasureUnitConfiguration(entity);
                },
                dataType: 'json'
            });
        }
    }
    function executePostConditionForEditMeasureUnitConfiguration(data) {
        if (data == null) {
            alert('Selected measureUnitConfiguration might have been deleted by someone');  //Message renderer
        } else {
            showMeasureUnitConfiguration(data);
        }
    }
    function executePreConditionForDeleteMeasureUnitConfiguration(measureUnitConfigurationId) {
        if (measureUnitConfigurationId == null) {
            alert("Please select a measureUnitConfiguration to delete");
            return false;
        }
        return true;
    }
    function executePostConditionForDeleteMeasureUnitConfiguration(data) {
        if (data.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_measureUnit_form('#gFormMeasureUnitConfiguration');
        }
        MessageRenderer.render(data)
    }
    function showMeasureUnitConfiguration(entity) {
        var measureUnitConfiguration = entity.measureUnitConfiguration
        var enterpriseConfiguration = entity.enterpriseConfiguration

        $('#gFormMeasureUnitConfiguration input[name = id]').val(measureUnitConfiguration.id);
        $('#gFormMeasureUnitConfiguration input[name = version]').val(measureUnitConfiguration.version);


        if (enterpriseConfiguration) {
            $('#enterpriseList').setValue(enterpriseConfiguration.name);
            $('#enterpriseConfiguration').val(enterpriseConfiguration.id)
        }
        $('#name').val(measureUnitConfiguration.name);
        $('#note').val(measureUnitConfiguration.note);

        if(measureUnitConfiguration.isMasterUom){
            $('#isMasterUom').attr('checked',true);
        }else{
            $('#isMasterUom').attr('checked',false);
        }

        if (measureUnitConfiguration.userCreated) {
            $('#userCreated').val(measureUnitConfiguration.userCreated.id).attr("selected", "selected");
        }
        else {
            $('#userCreated').val(measureUnitConfiguration.userCreated);
        }
        if (measureUnitConfiguration.userUpdated) {
            $('#userUpdated').val(measureUnitConfiguration.userUpdated.id).attr("selected", "selected");
        }
        else {
            $('#userUpdated').val(measureUnitConfiguration.userUpdated);
        }
        $('#dateCreated').val(measureUnitConfiguration.dateCreated);
        $('#dateUpdated').val(measureUnitConfiguration.dateUpdated);
        $('#create-button').attr('value', 'Update');
        $('#cancel-button').attr('value', 'Cancel');
        $('#delete-button').show();
    }
    function executeSearchMeasureUnitConfiguration(fieldName, fieldValue) {
        if (executePreConditionForSearchMeasureUnitConfiguration(fieldName, fieldValue)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'measureUnitConfiguration', file:'search')}?fieldName=" + fieldName + "&fieldValue=" + fieldValue,
                success: function (entity) {
                    executePostConditionForSearchMeasureUnitConfiguration(entity, fieldName, fieldValue);
                },
                dataType: 'json'
            });
        }
    }
    function executePreConditionForSearchMeasureUnitConfiguration(fieldName, fieldValue) {
        // trim field vales before process.
        $('#' + fieldName).val($.trim($('#' + fieldName).val()));
        if (fieldValue == '' || fieldValue == null) {
            reset_measureUnit_form("#gFormMeasureUnitConfiguration");
            return false;
        }
        return true;
    }
    function executePostConditionForSearchMeasureUnitConfiguration(data, fieldName, fieldValue) {
        if (data == null) {
            reset_measureUnit_form("#gFormMeasureUnitConfiguration"); // Clear Form
            $('#' + fieldName).val(fieldValue); // Set search field with fieldValue
        } else {
            showMeasureUnitConfiguration(data);
        }
    }
    function reset_measureUnit_form(formName) {
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
        $('input[type=checkbox]').attr('checked', false);
        $(formName + ' input[name = create-button]').attr('value', 'Create');
        $(formName + ' input[name = delete-button]').hide();
        $("#enterpriseConfiguration").val(enterprise);
    }
</script>