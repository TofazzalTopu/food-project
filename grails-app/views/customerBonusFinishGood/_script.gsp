<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        $('#ui-widget-header-text').html('CustomerBonusFinishGood')
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormCustomerBonusFinishGood").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormCustomerBonusFinishGood").validationEngine('attach');
        reset_form("#gFormCustomerBonusFinishGood");
        $("#jqgrid-grid").jqGrid({
            url: '${resource(dir:'customerBonusFinishGood', file:'list')}',
            datatype: "json",
            colNames: [
                'SL',
                'ID',

                'Bonus Criteria Setup',
                'Bonus Quantity',
                'Date Created',
                'Finished Product Booked Details',
                'Last Updated',
                'User Created',
                'User Updated'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30, sortable: false, align: 'left'},
                {name: 'id', index: 'id', width: 0, hidden: true},

                {name: 'bonusCriteriaSetup', index: 'bonusCriteriaSetup', width: 100, align: 'left'},
                {name: 'bonusQuantity', index: 'bonusQuantity', width: 100, align: 'left'},
                {name: 'dateCreated', index: 'dateCreated', width: 100, align: 'left'},
                {name: 'finishedProductBookedDetails', index: 'finishedProductBookedDetails', width: 100, align: 'left'},
                {name: 'lastUpdated', index: 'lastUpdated', width: 100, align: 'left'},
                {name: 'userCreated', index: 'userCreated', width: 100, align: 'left'},
                {name: 'userUpdated', index: 'userUpdated', width: 100, align: 'left'}
            ],
            rowNum: 50,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#jqgrid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "All CustomerBonusFinishGood Information",
            autowidth: true,
            height: 350,
            scrollOffset: 0,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                executeEditCustomerBonusFinishGood();
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
    function getSelectedCustomerBonusFinishGoodId() {
        var customerBonusFinishGoodId = null;
        var rowid = $("#jqgrid-grid").jqGrid('getGridParam', 'selrow');
        if (rowid) {
            customerBonusFinishGoodId = $("#jqgrid-grid").jqGrid('getCell', rowid, 'id');
        }
        if (customerBonusFinishGoodId == null) {
            customerBonusFinishGoodId = $('#gFormCustomerBonusFinishGood input[name = id]').val();
        }
        return customerBonusFinishGoodId;
    }
    function executePreConditionCustomerBonusFinishGood() {
        // trim field vales before process.
        trim_form();
        if ($("#gFormCustomerBonusFinishGood").validate().form({onfocusout: false}) == false) {
            return false;
        }
        return true;
    }
    function executeAjaxCustomerBonusFinishGood() {
        if (!executePreConditionCustomerBonusFinishGood()) {
            return false;
        }
        var actionUrl = null;
        if ($('#gFormCustomerBonusFinishGood input[name = id]').val()) {
            actionUrl = "${request.contextPath}/${params.controller}/update";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/create";
        }
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: jQuery("#gFormCustomerBonusFinishGood").serialize(),
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionCustomerBonusFinishGood(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-grid").trigger("reloadGrid");
                    reset_form('#gFormCustomerBonusFinishGood');
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                }else{
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
    function executePostConditionCustomerBonusFinishGood(result) {
        if (result.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_form('#gFormCustomerBonusFinishGood');
        }
        MessageRenderer.render(result);
    }
    function deleteAjaxCustomerBonusFinishGood() {    // Delete record
        var customerBonusFinishGoodId = getSelectedCustomerBonusFinishGoodId();
        if (executePreConditionForDeleteCustomerBonusFinishGood(customerBonusFinishGoodId)) {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-customerBonusFinishGood").dialog({
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
                            data: jQuery("#gFormCustomerBonusFinishGood").serialize(),
                            url: "${resource(dir:'customerBonusFinishGood', file:'delete')}",
                            success: function (message) {
                                executePostConditionForDeleteCustomerBonusFinishGood(message);
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

    function executePreConditionForEditCustomerBonusFinishGood(customerBonusFinishGoodId) {
        if (customerBonusFinishGoodId == null) {
            alert("Please select a customerBonusFinishGood to edit");
            return false;
        }
        return true;
    }
    function executeEditCustomerBonusFinishGood() {
        var customerBonusFinishGoodId = getSelectedCustomerBonusFinishGoodId();
        if (executePreConditionForEditCustomerBonusFinishGood(customerBonusFinishGoodId)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'customerBonusFinishGood', file:'edit')}?id=" + customerBonusFinishGoodId,
                success: function (entity) {
                    executePostConditionForEditCustomerBonusFinishGood(entity);
                },
                dataType: 'json'
            });
        }
    }
    function executePostConditionForEditCustomerBonusFinishGood(data) {
        if (data == null) {
            alert('Selected customerBonusFinishGood might have been deleted by someone');  //Message renderer
        } else {
            showCustomerBonusFinishGood(data);
        }
    }
    function executePreConditionForDeleteCustomerBonusFinishGood(customerBonusFinishGoodId) {
        if (customerBonusFinishGoodId == null) {
            alert("Please select a customerBonusFinishGood to delete");
            return false;
        }
        return true;
    }
    function executePostConditionForDeleteCustomerBonusFinishGood(data) {
        if (data.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_form('#gFormCustomerBonusFinishGood');
        }
        MessageRenderer.render(data)
    }
    function showCustomerBonusFinishGood(entity) {
        $('#gFormCustomerBonusFinishGood input[name = id]').val(entity.id);
        $('#gFormCustomerBonusFinishGood input[name = version]').val(entity.version);

        if (entity.bonusCriteriaSetup) {
            $('#bonusCriteriaSetup').val(entity.bonusCriteriaSetup.id).attr("selected", "selected");
        }
        else {
            $('#bonusCriteriaSetup').val(entity.bonusCriteriaSetup);
        }
        $('#bonusQuantity').val(entity.bonusQuantity);
        $('#dateCreated').val(entity.dateCreated);
        if (entity.finishedProductBookedDetails) {
            $('#finishedProductBookedDetails').val(entity.finishedProductBookedDetails.id).attr("selected", "selected");
        }
        else {
            $('#finishedProductBookedDetails').val(entity.finishedProductBookedDetails);
        }
        $('#lastUpdated').val(entity.lastUpdated);
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
        $('#create-button').attr('value', 'Update');
        $('#cancel-button').attr('value', 'Cancel');
        $('#delete-button').show();
    }
    function executeSearchCustomerBonusFinishGood(fieldName, fieldValue) {
        if (executePreConditionForSearchCustomerBonusFinishGood(fieldName, fieldValue)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'customerBonusFinishGood', file:'search')}?fieldName=" + fieldName + "&fieldValue=" + fieldValue,
                success: function (entity) {
                    executePostConditionForSearchCustomerBonusFinishGood(entity, fieldName, fieldValue);
                },
                dataType: 'json'
            });
        }
    }
    function executePreConditionForSearchCustomerBonusFinishGood(fieldName, fieldValue) {
        // trim field vales before process.
        $('#' + fieldName).val($.trim($('#' + fieldName).val()));
        if (fieldValue == '' || fieldValue == null) {
            reset_form("#gFormCustomerBonusFinishGood");
            return false;
        }
        return true;
    }
    function executePostConditionForSearchCustomerBonusFinishGood(data, fieldName, fieldValue) {
        if (data == null) {
            reset_form("#gFormCustomerBonusFinishGood"); // Clear Form
            $('#' + fieldName).val(fieldValue); // Set search field with fieldValue
        } else {
            showCustomerBonusFinishGood(data);
        }
    }
</script>