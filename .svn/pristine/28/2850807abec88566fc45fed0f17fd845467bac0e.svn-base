<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        $('#ui-widget-header-text').html('CustomerType');
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormCustomerType").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormCustomerType").validationEngine('attach');
        reset_form("#gFormCustomerType");
        $("#jqgrid-grid").jqGrid({
            url: '${resource(dir:'customerType', file:'list')}',
            datatype: "json",
            colNames: [
                'SL',
                'ID',
                'Type Name',
                'Receivable A/C Code',
                'Advance A/C Code',
                'Note'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30, sortable: false, align: 'left'},
                {name: 'id', index: 'id', width: 0, hidden: true},

                {name: 'name', index: 'name', width: 100, align: 'left'},
                {name: 'receivableCode', index: 'receivableCode', width: 100, align: 'left'},
                {name: 'advanceCode', index: 'advanceCode', width: 100, align: 'left'},
                {name: 'note', index: 'note', width: 100, align: 'left'}
            ],
            rowNum: 50,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#jqgrid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Customer Type List",
            autowidth: true,
            height: false,
            scrollOffset: 0,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                executeEditCustomerType();
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
    function getSelectedCustomerTypeId() {
        var customerTypeId = null;
        var rowid = $("#jqgrid-grid").jqGrid('getGridParam', 'selrow');
        if (rowid) {
            customerTypeId = $("#jqgrid-grid").jqGrid('getCell', rowid, 'id');
        }
        if (customerTypeId == null) {
            customerTypeId = $('#gFormCustomerType input[name = id]').val();
        }
        return customerTypeId;
    }
    function executeAjaxCustomerType() {
//        debugger;
        if (!executePreConditionCustomerType()) {
            return false;
        }
        var actionUrl = null;
        if ($('#gFormCustomerType input[name = id]').val()) {
            actionUrl = "${request.contextPath}/${params.controller}/update";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/create";
        }
        jQuery.ajax({
            type: 'post',
            data: jQuery("#gFormCustomerType").serialize(),
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionCustomerType(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                } else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete: function () {
            },
            dataType: 'json'
        });
    }
    function executePreConditionCustomerType() {
        // trim field vales before process.
        trim_form();
        if (!$("#gFormCustomerType").validationEngine('validate')) {
            return false;
        }
        return true;
    }
    function executePostConditionCustomerType(result) {
        if (result.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_form('#gFormCustomerType');
        }
        MessageRenderer.render(result);
    }
    function deleteAjaxCustomerType() {    // Delete record
        var customerTypeId = getSelectedCustomerTypeId();
        if (executePreConditionForDeleteCustomerType(customerTypeId)) {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-customerType").dialog({
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
                            data: jQuery("#gFormCustomerType").serialize(),
                            url: "${resource(dir:'customerType', file:'delete')}",
                            success: function (message) {
                                executePostConditionForDeleteCustomerType(message);
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

    function executePreConditionForEditCustomerType(customerTypeId) {
        if (customerTypeId == null) {
            alert("Please select a customerType to edit");
            return false;
        }
        return true;
    }
    function executeEditCustomerType() {
        var customerTypeId = getSelectedCustomerTypeId();
        if (executePreConditionForEditCustomerType(customerTypeId)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'customerType', file:'edit')}?id=" + customerTypeId,
                success: function (entity) {
                    executePostConditionForEditCustomerType(entity);
                },
                dataType: 'json'
            });
        }
    }
    function executePostConditionForEditCustomerType(data) {
        if (data == null) {
            alert('Selected customerType might have been deleted by someone');  //Message renderer
        } else {
            showCustomerType(data);
        }
    }
    function executePreConditionForDeleteCustomerType(customerTypeId) {
        if (customerTypeId == null) {
            alert("Please select a customerType to delete");
            return false;
        }
        return true;
    }
    function executePostConditionForDeleteCustomerType(data) {
        if (data.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_form('#gFormCustomerType');
        }
        MessageRenderer.render(data)
    }
    function showCustomerType(entity) {
        $('#gFormCustomerType input[name = id]').val(entity.id);
        $('#gFormCustomerType input[name = version]').val(entity.version);

        $('#name').val(entity.name);
        $('#receivableCode').val(entity.receivableCode);
        $('#advanceCode').val(entity.advanceCode);
        $('#note').val(entity.note);
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
    function executeSearchCustomerType(fieldName, fieldValue) {
        if (executePreConditionForSearchCustomerType(fieldName, fieldValue)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'customerType', file:'search')}?fieldName=" + fieldName + "&fieldValue=" + fieldValue,
                success: function (entity) {
                    executePostConditionForSearchCustomerType(entity, fieldName, fieldValue);
                },
                dataType: 'json'
            });
        }
    }
    function executePreConditionForSearchCustomerType(fieldName, fieldValue) {
        // trim field vales before process.
        $('#' + fieldName).val($.trim($('#' + fieldName).val()));
        if (fieldValue == '' || fieldValue == null) {
            reset_form("#gFormCustomerType");
            return false;
        }
        return true;
    }
    function executePostConditionForSearchCustomerType(data, fieldName, fieldValue) {
        if (data == null) {
            reset_form("#gFormCustomerType"); // Clear Form
            $('#' + fieldName).val(fieldValue); // Set search field with fieldValue
        } else {
            showCustomerType(data);
        }
    }
</script>