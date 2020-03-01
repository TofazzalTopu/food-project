<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        $('#ui-widget-header-text').html('SubWarehouseType')
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormSubWarehouseType").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormSubWarehouseType").validationEngine('attach');
        reset_SubWarehouseType_form("#gFormSubWarehouseType");
        $("#jqgrid-grid").jqGrid({
            url: '${resource(dir:'subWarehouseType', file:'list')}',
            datatype: "json",
            colNames: [
                'SL',
                'ID',
                'Enterprise',
                'Sub Inventory Type Name',
                'Note'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30, sortable: false, align: 'left'},
                {name: 'id', index: 'id', width: 0, hidden: true},
                {name: 'enterpriseConfiguration', index: 'enterpriseConfiguration', width: 100, align: 'left'},
                {name: 'name', index: 'name', width: 100, align: 'left'},
                {name: 'note', index: 'note', width: 100, align: 'left'}

            ],
            rowNum: 50,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#jqgrid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Sub Inventory Type List",
            autowidth: true,
            height: true,
            scrollOffset: 0,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                executeEditSubWarehouseType();
            }
        });
        $("#jqgrid-grid").jqGrid('navGrid', '#jqgrid-pager', {edit: false, add: false, del: false, search: false});
//     $("#jqgrid-grid").gridResize({minWidth:350,maxWidth:800,minHeight:200});
        $(".ui-pg-selbox").children().each(function () {
            if ($(this).val() == -1) {
                $(this).html('All')
            }

        });
    });
    function getSelectedSubWarehouseTypeId() {
        var subWarehouseTypeId = null;
        var rowid = $("#jqgrid-grid").jqGrid('getGridParam', 'selrow');
        if (rowid) {
            subWarehouseTypeId = $("#jqgrid-grid").jqGrid('getCell', rowid, 'id');
        }
        if (subWarehouseTypeId == null) {
            subWarehouseTypeId = $('#gFormSubWarehouseType input[name = id]').val();
        }
        return subWarehouseTypeId;
    }
    function executePreConditionSubWarehouseType() {
        // trim field vales before process.
        trim_form();
        if (!$("#gFormSubWarehouseType").validationEngine('validate')) {
            return false;
        }
        return true;
    }
    function executeAjaxSubWarehouseType() {
        if (!executePreConditionSubWarehouseType()) {
            return false;
        }
        var actionUrl = null;
        if ($('#gFormSubWarehouseType input[name = id]').val()) {
            actionUrl = "${request.contextPath}/${params.controller}/update";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/create";
        }
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: jQuery("#gFormSubWarehouseType").serialize(),
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionSubWarehouseType(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-grid").trigger("reloadGrid");
                    reset_SubWarehouseType_form('#gFormSubWarehouseType');

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
    function executePostConditionSubWarehouseType(result) {
        if (result.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_SubWarehouseType_form('#gFormSubWarehouseType');
        }
        MessageRenderer.render(result);
    }
    function deleteAjaxSubWarehouseType() {    // Delete record
        var subWarehouseTypeId = getSelectedSubWarehouseTypeId();
        if (executePreConditionForDeleteSubWarehouseType(subWarehouseTypeId)) {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-subWarehouseType").dialog({
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
                            data: jQuery("#gFormSubWarehouseType").serialize(),
                            url: "${resource(dir:'subWarehouseType', file:'delete')}",
                            success: function (message) {
                                executePostConditionForDeleteSubWarehouseType(message);
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

    function executePreConditionForEditSubWarehouseType(subWarehouseTypeId) {
        if (subWarehouseTypeId == null) {
            alert("Please select a subWarehouseType to edit");
            return false;
        }
        return true;
    }
    function executeEditSubWarehouseType() {
        var subWarehouseTypeId = getSelectedSubWarehouseTypeId();
        if (executePreConditionForEditSubWarehouseType(subWarehouseTypeId)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'subWarehouseType', file:'edit')}?id=" + subWarehouseTypeId,
                success: function (entity) {
                    executePostConditionForEditSubWarehouseType(entity);
                },
                dataType: 'json'
            });
        }
    }
    function executePostConditionForEditSubWarehouseType(data) {
        if (data == null) {
            alert('Selected subWarehouseType might have been deleted by someone');  //Message renderer
        } else {
            showSubWarehouseType(data);
        }
    }
    function executePreConditionForDeleteSubWarehouseType(subWarehouseTypeId) {
        if (subWarehouseTypeId == null) {
            alert("Please select a subWarehouseType to delete");
            return false;
        }
        return true;
    }
    function executePostConditionForDeleteSubWarehouseType(data) {
        if (data.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_SubWarehouseType_form('#gFormSubWarehouseType');
        }
        MessageRenderer.render(data)
    }
    function showSubWarehouseType(entity) {
        var subWarehouseType = entity.subWarehouseType
        var enterpriseConfiguration = entity.enterpriseConfiguration

        $('#gFormSubWarehouseType input[name = id]').val(subWarehouseType.id);
        $('#gFormSubWarehouseType input[name = version]').val(subWarehouseType.version);

        $('#name').val(subWarehouseType.name);
        $('#note').val(subWarehouseType.note);

        if (enterpriseConfiguration) {
            $('#enterpriseList').setValue(enterpriseConfiguration.name);
            $('#enterpriseConfiguration').val(enterpriseConfiguration.id)
        }
//        console.log(enterpriseConfiguration);

        $('#create-button').attr('value', 'Update');
        $('#cancel-button').attr('value', 'Cancel');
        $('#delete-button').show();
    }
    function executeSearchSubWarehouseType(fieldName, fieldValue) {
        if (executePreConditionForSearchSubWarehouseType(fieldName, fieldValue)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'subWarehouseType', file:'search')}?fieldName=" + fieldName + "&fieldValue=" + fieldValue,
                success: function (entity) {
                    executePostConditionForSearchSubWarehouseType(entity, fieldName, fieldValue);
                },
                dataType: 'json'
            });
        }
    }
    function executePreConditionForSearchSubWarehouseType(fieldName, fieldValue) {
        // trim field vales before process.
        $('#' + fieldName).val($.trim($('#' + fieldName).val()));
        if (fieldValue == '' || fieldValue == null) {
            reset_SubWarehouseType_form("#gFormSubWarehouseType");
            return false;
        }
        return true;
    }
    function executePostConditionForSearchSubWarehouseType(data, fieldName, fieldValue) {
        if (data == null) {
            reset_SubWarehouseType_form("#gFormSubWarehouseType"); // Clear Form
            $('#' + fieldName).val(fieldValue); // Set search field with fieldValue
        } else {
            showSubWarehouseType(data);
        }
    }

    function reset_SubWarehouseType_form(formName) {
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
    }
</script>