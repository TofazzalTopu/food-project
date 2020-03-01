<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        $('#ui-widget-header-text').html('CustomerTerritorySubArea')
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormCustomerTerritorySubArea").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormCustomerTerritorySubArea").validationEngine('attach');
        reset_form("#gFormCustomerTerritorySubArea");
        $("#jqgrid-grid").jqGrid({
            url: '${resource(dir:'customerTerritorySubArea', file:'list')}',
            datatype: "json",
            colNames: [
                'SL',
                'ID',

                'Customer Master',
                'Territory Sub Area',
                'User Created',
                'User Updated',
                'Date Created',
                'Last Updated'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30, sortable: false, align: 'left'},
                {name: 'id', index: 'id', width: 0, hidden: true},

                {name: 'customerMaster', index: 'customerMaster', width: 100, align: 'left'},
                {name: 'territorySubArea', index: 'territorySubArea', width: 100, align: 'left'},
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
            caption: "All CustomerTerritorySubArea Information",
            autowidth: true,
            height: 350,
            scrollOffset: 0,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                executeEditCustomerTerritorySubArea();
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
    function getSelectedCustomerTerritorySubAreaId() {
        var customerTerritorySubAreaId = null;
        var rowid = $("#jqgrid-grid").jqGrid('getGridParam', 'selrow');
        if (rowid) {
            customerTerritorySubAreaId = $("#jqgrid-grid").jqGrid('getCell', rowid, 'id');
        }
        if (customerTerritorySubAreaId == null) {
            customerTerritorySubAreaId = $('#gFormCustomerTerritorySubArea input[name = id]').val();
        }
        return customerTerritorySubAreaId;
    }
    function executePreConditionCustomerTerritorySubArea() {
        // trim field vales before process.
        trim_form();
        if ($("#gFormCustomerTerritorySubArea").validate().form({onfocusout: false}) == false) {
            return false;
        }
        return true;
    }
    function executeAjaxCustomerTerritorySubArea() {
        if (!executePreConditionCustomerTerritorySubArea()) {
            return false;
        }
        var actionUrl = null;
        if ($('#gFormCustomerTerritorySubArea input[name = id]').val()) {
            actionUrl = "${request.contextPath}/${params.controller}/update";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/create";
        }
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: jQuery("#gFormCustomerTerritorySubArea").serialize(),
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionCustomerTerritorySubArea(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-grid").trigger("reloadGrid");
                    reset_form('#gFormCustomerTerritorySubArea');
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
    function executePostConditionCustomerTerritorySubArea(result) {
        if (result.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_form('#gFormCustomerTerritorySubArea');
        }
        MessageRenderer.render(result);
    }
    function deleteAjaxCustomerTerritorySubArea() {    // Delete record
        var customerTerritorySubAreaId = getSelectedCustomerTerritorySubAreaId();
        if (executePreConditionForDeleteCustomerTerritorySubArea(customerTerritorySubAreaId)) {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-customerTerritorySubArea").dialog({
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
                            data: jQuery("#gFormCustomerTerritorySubArea").serialize(),
                            url: "${resource(dir:'customerTerritorySubArea', file:'delete')}",
                            success: function (message) {
                                executePostConditionForDeleteCustomerTerritorySubArea(message);
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

    function executePreConditionForEditCustomerTerritorySubArea(customerTerritorySubAreaId) {
        if (customerTerritorySubAreaId == null) {
            alert("Please select a customerTerritorySubArea to edit");
            return false;
        }
        return true;
    }
    function executeEditCustomerTerritorySubArea() {
        var customerTerritorySubAreaId = getSelectedCustomerTerritorySubAreaId();
        if (executePreConditionForEditCustomerTerritorySubArea(customerTerritorySubAreaId)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'customerTerritorySubArea', file:'edit')}?id=" + customerTerritorySubAreaId,
                success: function (entity) {
                    executePostConditionForEditCustomerTerritorySubArea(entity);
                },
                dataType: 'json'
            });
        }
    }
    function executePostConditionForEditCustomerTerritorySubArea(data) {
        if (data == null) {
            alert('Selected customerTerritorySubArea might have been deleted by someone');  //Message renderer
        } else {
            showCustomerTerritorySubArea(data);
        }
    }
    function executePreConditionForDeleteCustomerTerritorySubArea(customerTerritorySubAreaId) {
        if (customerTerritorySubAreaId == null) {
            alert("Please select a customerTerritorySubArea to delete");
            return false;
        }
        return true;
    }
    function executePostConditionForDeleteCustomerTerritorySubArea(data) {
        if (data.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_form('#gFormCustomerTerritorySubArea');
        }
        MessageRenderer.render(data)
    }
    function showCustomerTerritorySubArea(entity) {
        $('#gFormCustomerTerritorySubArea input[name = id]').val(entity.id);
        $('#gFormCustomerTerritorySubArea input[name = version]').val(entity.version);

        if (entity.customerMaster) {
            $('#customerMaster').val(entity.customerMaster.id).attr("selected", "selected");
        }
        else {
            $('#customerMaster').val(entity.customerMaster);
        }
        if (entity.territorySubArea) {
            $('#territorySubArea').val(entity.territorySubArea.id).attr("selected", "selected");
        }
        else {
            $('#territorySubArea').val(entity.territorySubArea);
        }
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
    function executeSearchCustomerTerritorySubArea(fieldName, fieldValue) {
        if (executePreConditionForSearchCustomerTerritorySubArea(fieldName, fieldValue)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'customerTerritorySubArea', file:'search')}?fieldName=" + fieldName + "&fieldValue=" + fieldValue,
                success: function (entity) {
                    executePostConditionForSearchCustomerTerritorySubArea(entity, fieldName, fieldValue);
                },
                dataType: 'json'
            });
        }
    }
    function executePreConditionForSearchCustomerTerritorySubArea(fieldName, fieldValue) {
        // trim field vales before process.
        $('#' + fieldName).val($.trim($('#' + fieldName).val()));
        if (fieldValue == '' || fieldValue == null) {
            reset_form("#gFormCustomerTerritorySubArea");
            return false;
        }
        return true;
    }
    function executePostConditionForSearchCustomerTerritorySubArea(data, fieldName, fieldValue) {
        if (data == null) {
            reset_form("#gFormCustomerTerritorySubArea"); // Clear Form
            $('#' + fieldName).val(fieldValue); // Set search field with fieldValue
        } else {
            showCustomerTerritorySubArea(data);
        }
    }
</script>