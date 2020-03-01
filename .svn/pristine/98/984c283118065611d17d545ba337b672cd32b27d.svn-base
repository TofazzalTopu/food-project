<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        $('#ui-widget-header-text').html('ChargeType')
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormChargeType").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormChargeType").validationEngine('attach');
        reset_chargeType_form("#gFormChargeType");
        $("#jqgrid-grid").jqGrid({
            url: '${resource(dir:'chargeType', file:'list')}',
            datatype: "json",
            colNames: [
                'SL',
                'ID',

                'Enterprise Configuration',
                'Name',
                'Account Code',
                'Note'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30, sortable: false, align: 'left'},
                {name: 'id', index: 'id', width: 0, hidden: true},

                {name: 'enterpriseConfiguration', index: 'enterpriseConfiguration', width: 100, align: 'left'},
                {name: 'name', index: 'name', width: 100, align: 'left'},
                {name: 'accountCode', index: 'accountCode', width: 100, align: 'left'},
                {name: 'note', index: 'note', width: 100, align: 'left'}
            ],
            rowNum: 50,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#jqgrid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "All ChargeType Information",
            autowidth: true,
            height: 350,
            scrollOffset: 0,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                executeEditChargeType();
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
    function getSelectedChargeTypeId() {
        var chargeTypeId = null;
        var rowid = $("#jqgrid-grid").jqGrid('getGridParam', 'selrow');
        if (rowid) {
            chargeTypeId = $("#jqgrid-grid").jqGrid('getCell', rowid, 'id');
        }
        if (chargeTypeId == null) {
            chargeTypeId = $('#gFormChargeType input[name = id]').val();
        }
        return chargeTypeId;
    }
    function executePreConditionChargeType() {
        // trim field vales before process.
        trim_form();
        if (!$("#gFormChargeType").validationEngine('validate')) {
            return false;
        }
        return true;
    }
    function executeAjaxChargeType() {
        if (!executePreConditionChargeType()) {
            return false;
        }
        var actionUrl = null;
        if ($('#gFormChargeType input[name = id]').val()) {
            actionUrl = "${request.contextPath}/${params.controller}/update";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/create";
        }
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: jQuery("#gFormChargeType").serialize(),
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionChargeType(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-grid").trigger("reloadGrid");
                    reset_chargeType_form('#gFormChargeType');
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
    function executePostConditionChargeType(result) {
        if (result.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_chargeType_form('#gFormChargeType');
        }
        MessageRenderer.render(result);
    }
    function deleteAjaxChargeType() {    // Delete record
        var chargeTypeId = getSelectedChargeTypeId();
        if (executePreConditionForDeleteChargeType(chargeTypeId)) {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-chargeType").dialog({
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
                            data: jQuery("#gFormChargeType").serialize(),
                            url: "${resource(dir:'chargeType', file:'delete')}",
                            success: function (message) {
                                executePostConditionForDeleteChargeType(message);
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

    function executePreConditionForEditChargeType(chargeTypeId) {
        if (chargeTypeId == null) {
            alert("Please select a chargeType to edit");
            return false;
        }
        return true;
    }
    function executeEditChargeType() {
        var chargeTypeId = getSelectedChargeTypeId();
        if (executePreConditionForEditChargeType(chargeTypeId)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'chargeType', file:'edit')}?id=" + chargeTypeId,
                success: function (entity) {
                    executePostConditionForEditChargeType(entity);
                },
                dataType: 'json'
            });
        }
    }
    function executePostConditionForEditChargeType(data) {
        if (data == null) {
            alert('Selected chargeType might have been deleted by someone');  //Message renderer
        } else {
            showChargeType(data);
        }
    }
    function executePreConditionForDeleteChargeType(chargeTypeId) {
        if (chargeTypeId == null) {
            alert("Please select a chargeType to delete");
            return false;
        }
        return true;
    }
    function executePostConditionForDeleteChargeType(data) {
        if (data.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_chargeType_form('#gFormChargeType');
        }
        MessageRenderer.render(data)
    }
    function showChargeType(entity) {
        var chargeType = entity.chargeType
        var enterpriseConfiguration = entity.enterpriseConfiguration

        $('#gFormChargeType input[name = id]').val(chargeType.id);
        $('#gFormChargeType input[name = version]').val(chargeType.version);

        if (enterpriseConfiguration) {
            $('#enterpriseList').setValue(enterpriseConfiguration.name);
            $('#enterpriseConfiguration').val(enterpriseConfiguration.id)
        }

        $('#name').val(chargeType.name);
        $('#accountCode').val(chargeType.accountCode);
        $('#note').val(chargeType.note);
        if (chargeType.userCreated) {
            $('#userCreated').val(chargeType.userCreated.id).attr("selected", "selected");
        }
        else {
            $('#userCreated').val(chargeType.userCreated);
        }
        if (chargeType.userUpdated) {
            $('#userUpdated').val(chargeType.userUpdated.id).attr("selected", "selected");
        }
        else {
            $('#userUpdated').val(chargeType.userUpdated);
        }
        $('#dateCreated').val(chargeType.dateCreated);
        $('#dateUpdated').val(chargeType.dateUpdated);
        $('#create-button').attr('value', 'Update');
        $('#cancel-button').attr('value', 'Cancel');
        $('#delete-button').show();
    }
    function executeSearchChargeType(fieldName, fieldValue) {
        if (executePreConditionForSearchChargeType(fieldName, fieldValue)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'chargeType', file:'search')}?fieldName=" + fieldName + "&fieldValue=" + fieldValue,
                success: function (entity) {
                    executePostConditionForSearchChargeType(entity, fieldName, fieldValue);
                },
                dataType: 'json'
            });
        }
    }
    function executePreConditionForSearchChargeType(fieldName, fieldValue) {
        // trim field vales before process.
        $('#' + fieldName).val($.trim($('#' + fieldName).val()));
        if (fieldValue == '' || fieldValue == null) {
            reset_chargeType_form("#gFormChargeType");
            return false;
        }
        return true;
    }
    function executePostConditionForSearchChargeType(data, fieldName, fieldValue) {
        if (data == null) {
            reset_chargeType_form("#gFormChargeType"); // Clear Form
            $('#' + fieldName).val(fieldValue); // Set search field with fieldValue
        } else {
            showChargeType(data);
        }
    }
    function reset_chargeType_form(formName) {
        var enterpriseId = $('#enterpriseConfiguration').val();
        $(formName).find(":input").not(":button, :submit, :reset, :checkbox, :radio, :selected").each(function () {
            if (this.type == 'hidden') {
                this.value = "";
            } else {
                this.value = this.defaultValue;
            }
        });
//    $('input[type=checkbox]').attr('checked', false);
        $(formName + ' input[name = create-button]').attr('value', 'Create');
        $(formName + ' input[name = delete-button]').hide();
        $(formName + ' select').val('');
//        $("#packageType").html('');
//        $("#measureUnitConfiguration").html('');
        $('#enterpriseConfiguration').val(enterpriseId);
    }
</script>