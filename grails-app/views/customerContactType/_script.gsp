<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        $('#ui-widget-header-text').html('CustomerContactType');
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormCustomerContactType").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormCustomerContactType").validationEngine('attach');

        reset_customerContactType_form("#gFormCustomerContactType");
        $("#enterPriseName").attr("disabled", true);

        $("#jqgrid-grid").jqGrid({
            url: '${resource(dir:'customerContactType', file:'list')}',
            datatype: "json",
            colNames: [
                'SL',
                'ID',

                'Enterprise',
                'Contact Type Name',
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
            caption: "Customer Contact Type List",
            autowidth: true,
            height: true,
            scrollOffset: 0,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                executeEditCustomerContactType();
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
    function getSelectedCustomerContactTypeId() {
        var customerContactTypeId = null;
        var rowid = $("#jqgrid-grid").jqGrid('getGridParam', 'selrow');
        if (rowid) {
            customerContactTypeId = $("#jqgrid-grid").jqGrid('getCell', rowid, 'id');
        }
        if (customerContactTypeId == null) {
            customerContactTypeId = $('#gFormCustomerContactType input[name = id]').val();
        }
        return customerContactTypeId;
    }
    function executePreConditionCustomerContactType() {
        // trim field vales before process.
        trim_form();
        if (!$("#gFormCustomerContactType").validationEngine('validate')) {
            return false;
        }
        return true;
    }
    function executeAjaxCustomerContactType() {
        if (!executePreConditionCustomerContactType()) {
            return false;
        }
        var actionUrl = null;
        if ($('#gFormCustomerContactType input[name = id]').val()) {
            actionUrl = "${request.contextPath}/${params.controller}/update";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/create";
        }
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: jQuery("#gFormCustomerContactType").serialize(),
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionCustomerContactType(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-grid").trigger("reloadGrid");
                    reset_customerContactType_form("#gFormCustomerContactType");
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
    function executePostConditionCustomerContactType(result) {
        if (result.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_customerContactType_form("#gFormCustomerContactType");
        }
        MessageRenderer.render(result);
    }
    function deleteAjaxCustomerContactType() {    // Delete record
        var customerContactTypeId = getSelectedCustomerContactTypeId();
        if (executePreConditionForDeleteCustomerContactType(customerContactTypeId)) {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-customerContactType").dialog({
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
                            data: jQuery("#gFormCustomerContactType").serialize(),
                            url: "${resource(dir:'customerContactType', file:'delete')}",
                            success: function (message) {
                                executePostConditionForDeleteCustomerContactType(message);
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

    function executePreConditionForEditCustomerContactType(customerContactTypeId) {
        if (customerContactTypeId == null) {
            alert("Please select a customerContactType to edit");
            return false;
        }
        return true;
    }
    function executeEditCustomerContactType() {
        var customerContactTypeId = getSelectedCustomerContactTypeId();
        if (executePreConditionForEditCustomerContactType(customerContactTypeId)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'customerContactType', file:'edit')}?id=" + customerContactTypeId,
                success: function (entity) {
                    executePostConditionForEditCustomerContactType(entity);
                },
                dataType: 'json'
            });
        }
    }
    function executePostConditionForEditCustomerContactType(data) {
        if (data == null) {
            alert('Selected customerContactType might have been deleted by someone');  //Message renderer
        } else {
            showCustomerContactType(data);
        }
    }
    function executePreConditionForDeleteCustomerContactType(customerContactTypeId) {
        if (customerContactTypeId == null) {
            alert("Please select a customerContactType to delete");
            return false;
        }
        return true;
    }
    function executePostConditionForDeleteCustomerContactType(data) {
        if (data.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_customerContactType_form("#gFormCustomerContactType");
        }
        MessageRenderer.render(data)
    }
    function showCustomerContactType(entity) {

        var customerContactType = entity.customerContactType
        var enterpriseConfiguration = entity.enterpriseConfiguration

        $('#gFormCustomerContactType input[name = id]').val(customerContactType.id);
        $('#gFormCustomerContactType input[name = version]').val(customerContactType.version);

        if (enterpriseConfiguration) {
            $('#enterpriseList').setValue(enterpriseConfiguration.name);
            $('#enterpriseConfiguration').val(enterpriseConfiguration.id)
        }

        $('#name').val(customerContactType.name);
        $('#note').val(customerContactType.note);
        if (customerContactType.userCreated) {
            $('#userCreated').val(customerContactType.userCreated.id).attr("selected", "selected");
        }
        else {
            $('#userCreated').val(customerContactType.userCreated);
        }
        if (customerContactType.userUpdated) {
            $('#userUpdated').val(customerContactType.userUpdated.id).attr("selected", "selected");
        }
        else {
            $('#userUpdated').val(customerContactType.userUpdated);
        }
        $('#dateCreated').val(customerContactType.dateCreated);
        $('#lastUpdated').val(customerContactType.lastUpdated);
        $('#create-button').attr('value', 'Update');
        $('#cancel-button').attr('value', 'Cancel');
        $('#delete-button').show();
    }
    function executeSearchCustomerContactType(fieldName, fieldValue) {
        if (executePreConditionForSearchCustomerContactType(fieldName, fieldValue)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'customerContactType', file:'search')}?fieldName=" + fieldName + "&fieldValue=" + fieldValue,
                success: function (entity) {
                    executePostConditionForSearchCustomerContactType(entity, fieldName, fieldValue);
                },
                dataType: 'json'
            });
        }
    }
    function executePreConditionForSearchCustomerContactType(fieldName, fieldValue) {
        // trim field vales before process.
        $('#' + fieldName).val($.trim($('#' + fieldName).val()));
        if (fieldValue == '' || fieldValue == null) {
            reset_customerContactType_form("#gFormCustomerContactType");
            return false;
        }
        return true;
    }
    function executePostConditionForSearchCustomerContactType(data, fieldName, fieldValue) {
        if (data == null) {
            reset_customerContactType_form("#gFormCustomerContactType");
            $('#' + fieldName).val(fieldValue); // Set search field with fieldValue
        } else {
            showCustomerContactType(data);
        }
    }
    function reset_customerContactType_form(formName) {
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