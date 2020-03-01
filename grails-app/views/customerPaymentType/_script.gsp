<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        $('#ui-widget-header-text').html('CustomerPaymentType')
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormCustomerPaymentType").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormCustomerPaymentType").validationEngine('attach');

        reset_customerPaymentType_form("#gFormCustomerPaymentType");
        $("#enterPriseName").attr("disabled", true);

        $("#jqgrid-grid").jqGrid({
            url: '${resource(dir:'customerPaymentType', file:'list')}',
            datatype: "json",
            colNames: [
                'SL',
                'ID',
                'Enterprise',
                'Payment Type',
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
            caption: "Customer Payment Type List",
            autowidth: true,
            height: true,
            scrollOffset: 0,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                executeEditCustomerPaymentType();
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
    function getSelectedCustomerPaymentTypeId() {
        var customerPaymentTypeId = null;
        var rowid = $("#jqgrid-grid").jqGrid('getGridParam', 'selrow');
        if (rowid) {
            customerPaymentTypeId = $("#jqgrid-grid").jqGrid('getCell', rowid, 'id');
        }
        if (customerPaymentTypeId == null) {
            customerPaymentTypeId = $('#gFormCustomerPaymentType input[name = id]').val();
        }
        return customerPaymentTypeId;
    }
    function executePreConditionCustomerPaymentType() {
        // trim field vales before process.
        trim_form();
        if (!$("#gFormCustomerPaymentType").validationEngine('validate')) {
            return false;
        }
        return true;
    }
    function executeAjaxCustomerPaymentType() {
        if (!executePreConditionCustomerPaymentType()) {
            return false;
        }
        var actionUrl = null;
        if ($('#gFormCustomerPaymentType input[name = id]').val()) {
            actionUrl = "${request.contextPath}/${params.controller}/update";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/create";
        }
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: jQuery("#gFormCustomerPaymentType").serialize(),
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionCustomerPaymentType(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-grid").trigger("reloadGrid");
                    reset_customerPaymentType_form("#gFormCustomerPaymentType");
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
        return false;
    }
    function executePostConditionCustomerPaymentType(result) {
        if (result.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_customerPaymentType_form("#gFormCustomerPaymentType");
        }
        MessageRenderer.render(result);
    }
    function deleteAjaxCustomerPaymentType() {    // Delete record
        var customerPaymentTypeId = getSelectedCustomerPaymentTypeId();
        if (executePreConditionForDeleteCustomerPaymentType(customerPaymentTypeId)) {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-customerPaymentType").dialog({
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
                            data: jQuery("#gFormCustomerPaymentType").serialize(),
                            url: "${resource(dir:'customerPaymentType', file:'delete')}",
                            success: function (message) {
                                executePostConditionForDeleteCustomerPaymentType(message);
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

    function executePreConditionForEditCustomerPaymentType(customerPaymentTypeId) {
        if (customerPaymentTypeId == null) {
            alert("Please select a customerPaymentType to edit");
            return false;
        }
        return true;
    }
    function executeEditCustomerPaymentType() {
        var customerPaymentTypeId = getSelectedCustomerPaymentTypeId();
        if (executePreConditionForEditCustomerPaymentType(customerPaymentTypeId)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'customerPaymentType', file:'edit')}?id=" + customerPaymentTypeId,
                success: function (entity) {
                    executePostConditionForEditCustomerPaymentType(entity);
                },
                dataType: 'json'
            });
        }
    }
    function executePostConditionForEditCustomerPaymentType(data) {
        if (data == null) {
            alert('Selected customerPaymentType might have been deleted by someone');  //Message renderer
        } else {
            showCustomerPaymentType(data);
        }
    }
    function executePreConditionForDeleteCustomerPaymentType(customerPaymentTypeId) {
        if (customerPaymentTypeId == null) {
            alert("Please select a customerPaymentType to delete");
            return false;
        }
        return true;
    }
    function executePostConditionForDeleteCustomerPaymentType(data) {
        if (data.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_customerPaymentType_form("#gFormCustomerPaymentType");
        }
        MessageRenderer.render(data)
    }
    function showCustomerPaymentType(entity) {
        var customerPaymentType = entity.customerPaymentType
        var enterpriseConfiguration = entity.enterpriseConfiguration
        $('#gFormCustomerPaymentType input[name = id]').val(customerPaymentType.id);
        $('#gFormCustomerPaymentType input[name = version]').val(customerPaymentType.version);

        if (enterpriseConfiguration) {
            $('#enterpriseList').setValue(enterpriseConfiguration.name);
            $('#enterpriseConfiguration').val(enterpriseConfiguration.id);
        }

        $('#name').val(customerPaymentType.name);
        $('#note').val(customerPaymentType.note);

        if (customerPaymentType.userCreated) {
            $('#userCreated').val(customerPaymentType.userCreated.id).attr("selected", "selected");
        }
        else {
            $('#userCreated').val(customerPaymentType.userCreated);
        }
        if (customerPaymentType.userUpdated) {
            $('#userUpdated').val(customerPaymentType.userUpdated.id).attr("selected", "selected");
        }
        else {
            $('#userUpdated').val(customerPaymentType.userUpdated);
        }
        $('#dateCreated').val(customerPaymentType.dateCreated);
        $('#lastUpdated').val(customerPaymentType.lastUpdated);
        $('#create-button').attr('value', 'Update');
        $('#cancel-button').attr('value', 'Cancel');
        $('#delete-button').show();
    }
    function executeSearchCustomerPaymentType(fieldName, fieldValue) {
        if (executePreConditionForSearchCustomerPaymentType(fieldName, fieldValue)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'customerPaymentType', file:'search')}?fieldName=" + fieldName + "&fieldValue=" + fieldValue,
                success: function (entity) {
                    executePostConditionForSearchCustomerPaymentType(entity, fieldName, fieldValue);
                },
                dataType: 'json'
            });
        }
    }
    function executePreConditionForSearchCustomerPaymentType(fieldName, fieldValue) {
        // trim field vales before process.
        $('#' + fieldName).val($.trim($('#' + fieldName).val()));
        if (fieldValue == '' || fieldValue == null) {
            reset_customerPaymentType_form("#gFormCustomerPaymentType");
            return false;
        }
        return true;
    }
    function executePostConditionForSearchCustomerPaymentType(data, fieldName, fieldValue) {
        if (data == null) {
            reset_customerPaymentType_form("#gFormCustomerPaymentType");
            $('#' + fieldName).val(fieldValue); // Set search field with fieldValue
        } else {
            showCustomerPaymentType(data);
        }
    }
    function reset_customerPaymentType_form(formName) {
        var enterpriseId = $('#enterpriseConfiguration').val();
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
        $('#enterpriseConfiguration').val(enterpriseId);
    }
</script>