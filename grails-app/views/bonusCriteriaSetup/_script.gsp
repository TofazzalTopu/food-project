<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        $('#ui-widget-header-text').html('BonusCriteriaSetup')
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormBonusCriteriaSetup").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormBonusCriteriaSetup").validationEngine('attach');
        reset_form("#gFormBonusCriteriaSetup");
        $("#jqgrid-grid").jqGrid({
            url: '${resource(dir:'bonusCriteriaSetup', file:'list')}',
            datatype: "json",
            colNames: [
                'SL',
                'ID',

                'Finish Product',
                'Required Quantity',
                'Bonus Quantity',
                'Is Multiplexing',
                'Is Active'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30, sortable: false, align: 'left'},
                {name: 'id', index: 'id', width: 0, hidden: true},

                {name: 'finishProduct', index: 'finishProduct', width: 100, align: 'left'},
                {name: 'requiredQuantity', index: 'requiredQuantity', width: 100, align: 'left'},
                {name: 'bonusQuantity', index: 'bonusQuantity', width: 100, align: 'left'},
                {name: 'isMultiplexing', index: 'isMultiplexing', width: 100, align: 'left'},
                {name: 'isActive', index: 'isActive', width: 50, align: 'center'}
            ],
            rowNum: 50,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#jqgrid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "All BonusCriteriaSetup Information",
            autowidth: true,
            height: true,
            scrollOffset: 0,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                executeEditBonusCriteriaSetup();
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
    function getSelectedBonusCriteriaSetupId() {
        var bonusCriteriaSetupId = null;
        var rowid = $("#jqgrid-grid").jqGrid('getGridParam', 'selrow');
        if (rowid) {
            bonusCriteriaSetupId = $("#jqgrid-grid").jqGrid('getCell', rowid, 'id');
        }
        if (bonusCriteriaSetupId == null) {
            bonusCriteriaSetupId = $('#gFormBonusCriteriaSetup input[name = id]').val();
        }
        return bonusCriteriaSetupId;
    }
    function executePreConditionBonusCriteriaSetup() {
        // trim field vales before process.
        trim_form();
        if (!$("#gFormBonusCriteriaSetup").validationEngine('validate')) {
            return false;
        }
        return true;
    }

    function executeAjaxBonusCriteriaSetup() {
        if (!executePreConditionBonusCriteriaSetup()) {
            return false;
        }
        var actionUrl = null;
        if ($('#gFormBonusCriteriaSetup input[name = id]').val()) {
            actionUrl = "${request.contextPath}/${params.controller}/update";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/create";
        }
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: jQuery("#gFormBonusCriteriaSetup").serialize(),
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionBonusCriteriaSetup(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-grid").trigger("reloadGrid");
                    reset_form('#gFormBonusCriteriaSetup');
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
    function executePostConditionBonusCriteriaSetup(result) {
        if (result.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_form('#gFormBonusCriteriaSetup');
        }
        MessageRenderer.render(result);
    }
    function deleteAjaxBonusCriteriaSetup() {    // Delete record
        var bonusCriteriaSetupId = getSelectedBonusCriteriaSetupId();
        if (executePreConditionForDeleteBonusCriteriaSetup(bonusCriteriaSetupId)) {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-bonusCriteriaSetup").dialog({
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
                            data: jQuery("#gFormBonusCriteriaSetup").serialize(),
                            url: "${resource(dir:'bonusCriteriaSetup', file:'delete')}",
                            success: function (message) {
                                executePostConditionForDeleteBonusCriteriaSetup(message);
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

    function executePreConditionForEditBonusCriteriaSetup(bonusCriteriaSetupId) {
        if (bonusCriteriaSetupId == null) {
            alert("Please select a bonusCriteriaSetup to edit");
            return false;
        }
        return true;
    }
    function executeEditBonusCriteriaSetup() {
        var bonusCriteriaSetupId = getSelectedBonusCriteriaSetupId();
        if (executePreConditionForEditBonusCriteriaSetup(bonusCriteriaSetupId)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'bonusCriteriaSetup', file:'edit')}?id=" + bonusCriteriaSetupId,
                success: function (entity) {
                    executePostConditionForEditBonusCriteriaSetup(entity);
                },
                dataType: 'json'
            });
        }
    }
    function executePostConditionForEditBonusCriteriaSetup(data) {
        if (data == null) {
            alert('Selected bonusCriteriaSetup might have been deleted by someone');  //Message renderer
        } else {
            showBonusCriteriaSetup(data);
        }
    }
    function executePreConditionForDeleteBonusCriteriaSetup(bonusCriteriaSetupId) {
        if (bonusCriteriaSetupId == null) {
            alert("Please select a bonusCriteriaSetup to delete");
            return false;
        }
        return true;
    }
    function executePostConditionForDeleteBonusCriteriaSetup(data) {
        if (data.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_form('#gFormBonusCriteriaSetup');
        }
        MessageRenderer.render(data)
    }
    function showBonusCriteriaSetup(entity) {
        $('#gFormBonusCriteriaSetup input[name = id]').val(entity.id);
        $('#gFormBonusCriteriaSetup input[name = version]').val(entity.version);

        if (entity.finishProduct) {
            $('#finishProduct').val(entity.finishProduct.id).attr("selected", "selected");
        }
        else {
            $('#finishProduct').val(entity.finishProduct);
        }
        $('#requiredQuantity').val(entity.requiredQuantity);
        $('#bonusQuantity').val(entity.bonusQuantity);
        $('#isMultiplexing').attr('checked', entity.isMultiplexing);
        $('#isActive').attr('checked', entity.isActive);
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
        $('#create-button').attr('value', 'Update');
        $('#cancel-button').attr('value', 'Cancel');
        $('#delete-button').show();
    }
    function executeSearchBonusCriteriaSetup(fieldName, fieldValue) {
        if (executePreConditionForSearchBonusCriteriaSetup(fieldName, fieldValue)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'bonusCriteriaSetup', file:'search')}?fieldName=" + fieldName + "&fieldValue=" + fieldValue,
                success: function (entity) {
                    executePostConditionForSearchBonusCriteriaSetup(entity, fieldName, fieldValue);
                },
                dataType: 'json'
            });
        }
    }
    function executePreConditionForSearchBonusCriteriaSetup(fieldName, fieldValue) {
        // trim field vales before process.
        $('#' + fieldName).val($.trim($('#' + fieldName).val()));
        if (fieldValue == '' || fieldValue == null) {
            reset_form("#gFormBonusCriteriaSetup");
            return false;
        }
        return true;
    }
    function executePostConditionForSearchBonusCriteriaSetup(data, fieldName, fieldValue) {
        if (data == null) {
            reset_form("#gFormBonusCriteriaSetup"); // Clear Form
            $('#' + fieldName).val(fieldValue); // Set search field with fieldValue
        } else {
            showBonusCriteriaSetup(data);
        }
    }
</script>