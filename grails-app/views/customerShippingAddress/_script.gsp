<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        $('#ui-widget-header-text').html('CustomerShippingAddress')
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormCustomerShippingAddress").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormCustomerShippingAddress").validationEngine('attach');
        reset_form("#gFormCustomerShippingAddress");
        $("#jqgrid-grid").jqGrid({
            url: '${resource(dir:'customerShippingAddress', file:'list')}',
            datatype: "json",
            colNames: [
                'SL',
                'ID',

                'Customer Master',
                'Address',
                'Is Active',
                'User Created',
                'User Updated',
                'Date Created',
                'Last Updated'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30, sortable: false, align: 'left'},
                {name: 'id', index: 'id', width: 0, hidden: true},

                {name: 'customerMaster', index: 'customerMaster', width: 100, align: 'left'},
                {name: 'address', index: 'address', width: 100, align: 'left'},
                {name: 'isActive', index: 'isActive', width: 100, align: 'left'},
                {name: 'userCreated', index: 'userCreated', width: 100, align: 'left'},
                {name: 'userUpdated', index: 'userUpdated', width: 100, align: 'left'},
                {name: 'dateCreated', index: 'dateCreated', width: 100, align: 'left'},
                {name: 'lastUpdated', index: 'lastUpdated', width: 100, align: 'left'}
            ],
            rowNum: 50,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#jqgrid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "All CustomerShippingAddress Information",
            autowidth: true,
            height: 350,
            scrollOffset: 0,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                executeEditCustomerShippingAddress();
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
    function getSelectedCustomerShippingAddressId() {
        var customerShippingAddressId = null;
        var rowid = $("#jqgrid-grid").jqGrid('getGridParam', 'selrow');
        if (rowid) {
            customerShippingAddressId = $("#jqgrid-grid").jqGrid('getCell', rowid, 'id');
        }
        if (customerShippingAddressId == null) {
            customerShippingAddressId = $('#gFormCustomerShippingAddress input[name = id]').val();
        }
        return customerShippingAddressId;
    }
    function executePreConditionCustomerShippingAddress() {
        // trim field vales before process.
        trim_form();
        if ($("#gFormCustomerShippingAddress").validate().form({onfocusout: false}) == false) {
            return false;
        }
        return true;
    }
    function executeAjaxCustomerShippingAddress() {
        if (!executePreConditionCustomerShippingAddress()) {
            return false;
        }
        var actionUrl = null;
        if ($('#gFormCustomerShippingAddress input[name = id]').val()) {
            actionUrl = "${request.contextPath}/${params.controller}/update";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/create";
        }
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: jQuery("#gFormCustomerShippingAddress").serialize(),
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionCustomerShippingAddress(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-grid").trigger("reloadGrid");
                    reset_form('#gFormCustomerShippingAddress');
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
    function executePostConditionCustomerShippingAddress(result) {
        if (result.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_form('#gFormCustomerShippingAddress');
        }
        MessageRenderer.render(result);
    }
    function deleteAjaxCustomerShippingAddress() {    // Delete record
        var customerShippingAddressId = getSelectedCustomerShippingAddressId();
        if (executePreConditionForDeleteCustomerShippingAddress(customerShippingAddressId)) {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-customerShippingAddress").dialog({
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
                            data: jQuery("#gFormCustomerShippingAddress").serialize(),
                            url: "${resource(dir:'customerShippingAddress', file:'delete')}",
                            success: function (message) {
                                executePostConditionForDeleteCustomerShippingAddress(message);
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

    function executePreConditionForEditCustomerShippingAddress(customerShippingAddressId) {
        if (customerShippingAddressId == null) {
            alert("Please select a customerShippingAddress to edit");
            return false;
        }
        return true;
    }
    function executeEditCustomerShippingAddress() {
        var customerShippingAddressId = getSelectedCustomerShippingAddressId();
        if (executePreConditionForEditCustomerShippingAddress(customerShippingAddressId)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'customerShippingAddress', file:'edit')}?id=" + customerShippingAddressId,
                success: function (entity) {
                    executePostConditionForEditCustomerShippingAddress(entity);
                },
                dataType: 'json'
            });
        }
    }
    function executePostConditionForEditCustomerShippingAddress(data) {
        if (data == null) {
            alert('Selected customerShippingAddress might have been deleted by someone');  //Message renderer
        } else {
            showCustomerShippingAddress(data);
        }
    }
    function executePreConditionForDeleteCustomerShippingAddress(customerShippingAddressId) {
        if (customerShippingAddressId == null) {
            alert("Please select a customerShippingAddress to delete");
            return false;
        }
        return true;
    }
    function executePostConditionForDeleteCustomerShippingAddress(data) {
        if (data.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_form('#gFormCustomerShippingAddress');
        }
        MessageRenderer.render(data)
    }
    function showCustomerShippingAddress(entity) {
        $('#gFormCustomerShippingAddress input[name = id]').val(entity.id);
        $('#gFormCustomerShippingAddress input[name = version]').val(entity.version);

        if (entity.customerMaster) {
            $('#customerMaster').val(entity.customerMaster.id).attr("selected", "selected");
        }
        else {
            $('#customerMaster').val(entity.customerMaster);
        }
        $('#address').val(entity.address);
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
    function executeSearchCustomerShippingAddress(fieldName, fieldValue) {
        if (executePreConditionForSearchCustomerShippingAddress(fieldName, fieldValue)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'customerShippingAddress', file:'search')}?fieldName=" + fieldName + "&fieldValue=" + fieldValue,
                success: function (entity) {
                    executePostConditionForSearchCustomerShippingAddress(entity, fieldName, fieldValue);
                },
                dataType: 'json'
            });
        }
    }
    function executePreConditionForSearchCustomerShippingAddress(fieldName, fieldValue) {
        // trim field vales before process.
        $('#' + fieldName).val($.trim($('#' + fieldName).val()));
        if (fieldValue == '' || fieldValue == null) {
            reset_form("#gFormCustomerShippingAddress");
            return false;
        }
        return true;
    }
    function executePostConditionForSearchCustomerShippingAddress(data, fieldName, fieldValue) {
        if (data == null) {
            reset_form("#gFormCustomerShippingAddress"); // Clear Form
            $('#' + fieldName).val(fieldValue); // Set search field with fieldValue
        } else {
            showCustomerShippingAddress(data);
        }
    }
</script>