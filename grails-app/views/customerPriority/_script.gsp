<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        $('#ui-widget-header-text').html('CustomerPriority')
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormCustomerPriority").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormCustomerPriority").validationEngine('attach');

        reset_customerPriority_form("#gFormCustomerPriority");
        $("#enterPriseName").attr("disabled", true);

        $("#jqgrid-grid").jqGrid({
            url: '${resource(dir:'customerPriority', file:'list')}',
            datatype: "json",
            colNames: [
                'SL',
                'ID',

                'Enterprise',
                'Priority Name',
                'Priority',
                'Note'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30, sortable: false, align: 'left'},
                {name: 'id', index: 'id', width: 0, hidden: true},

                {name: 'enterpriseConfiguration', index: 'enterpriseConfiguration', width: 100, align: 'left'},
                {name: 'name', index: 'name', width: 100, align: 'left'},
                {name: 'priority', index: 'priority', width: 100, align: 'left'},
                {name: 'note', index: 'note', width: 100, align: 'left', hidden: true}
            ],
            rowNum: 50,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#jqgrid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "All Customer Priority Information",
            autowidth: true,
            height: true,
            scrollOffset: 0,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                executeEditCustomerPriority();
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
    function getSelectedCustomerPriorityId() {
        var customerPriorityId = null;
        var rowid = $("#jqgrid-grid").jqGrid('getGridParam', 'selrow');
        if (rowid) {
            customerPriorityId = $("#jqgrid-grid").jqGrid('getCell', rowid, 'id');
        }
        if (customerPriorityId == null) {
            customerPriorityId = $('#gFormCustomerPriority input[name = id]').val();
        }
        return customerPriorityId;
    }
    function executePreConditionCustomerPriority() {
        // trim field vales before process.
        trim_form();
        if (!$("#gFormCustomerPriority").validationEngine('validate')) {
            return false;
        }
        return true;
    }
    function executeAjaxCustomerPriority() {
        if (!executePreConditionCustomerPriority()) {
            return false;
        }
        var actionUrl = null;
        if ($('#gFormCustomerPriority input[name = id]').val()) {
            actionUrl = "${request.contextPath}/${params.controller}/update";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/create";
        }
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: jQuery("#gFormCustomerPriority").serialize(),
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionCustomerPriority(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-grid").trigger("reloadGrid");
                    reset_customerPriority_form("#gFormCustomerPriority");

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
    function executePostConditionCustomerPriority(result) {
        if (result.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_customerPriority_form("#gFormCustomerPriority");
        }
        MessageRenderer.render(result);
    }
    function deleteAjaxCustomerPriority() {    // Delete record
        var customerPriorityId = getSelectedCustomerPriorityId();
        if (executePreConditionForDeleteCustomerPriority(customerPriorityId)) {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-customerPriority").dialog({
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
                            data: jQuery("#gFormCustomerPriority").serialize(),
                            url: "${resource(dir:'customerPriority', file:'delete')}",
                            success: function (message) {
                                executePostConditionForDeleteCustomerPriority(message);
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

    function executePreConditionForEditCustomerPriority(customerPriorityId) {
        if (customerPriorityId == null) {
            alert("Please select a customerPriority to edit");
            return false;
        }
        return true;
    }
    function executeEditCustomerPriority() {
        var customerPriorityId = getSelectedCustomerPriorityId();
        if (executePreConditionForEditCustomerPriority(customerPriorityId)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'customerPriority', file:'edit')}?id=" + customerPriorityId,
                success: function (entity) {
                    executePostConditionForEditCustomerPriority(entity);
                },
                dataType: 'json'
            });
        }
    }
    function executePostConditionForEditCustomerPriority(data) {
        if (data == null) {
            alert('Selected customerPriority might have been deleted by someone');  //Message renderer
        } else {
            showCustomerPriority(data);
        }
    }
    function executePreConditionForDeleteCustomerPriority(customerPriorityId) {
        if (customerPriorityId == null) {
            alert("Please select a customerPriority to delete");
            return false;
        }
        return true;
    }
    function executePostConditionForDeleteCustomerPriority(data) {
        if (data.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_customerPriority_form("#gFormCustomerPriority");
        }
        MessageRenderer.render(data)
    }
    function showCustomerPriority(entity) {
        var customerPriority = entity.customerPriority
        var enterpriseConfiguration = entity.enterpriseConfiguration
        $('#gFormCustomerPriority input[name = id]').val(customerPriority.id);
        $('#gFormCustomerPriority input[name = version]').val(customerPriority.version);

        if (enterpriseConfiguration) {
            $('#enterpriseList').setValue(enterpriseConfiguration.name);
            $('#enterpriseConfiguration').val(enterpriseConfiguration.id)
        }

        $('#name').val(customerPriority.name);
        $('#priority').val(customerPriority.priority);
        $('#note').val(customerPriority.note);
        if (customerPriority.userCreated) {
            $('#userCreated').val(customerPriority.userCreated.id).attr("selected", "selected");
        }
        else {
            $('#userCreated').val(customerPriority.userCreated);
        }
        if (customerPriority.userUpdated) {
            $('#userUpdated').val(customerPriority.userUpdated.id).attr("selected", "selected");
        }
        else {
            $('#userUpdated').val(customerPriority.userUpdated);
        }
        $('#dateCreated').val(customerPriority.dateCreated);
        $('#lastUpdated').val(customerPriority.lastUpdated);
        $('#create-button').attr('value', 'Update');
        $('#cancel-button').attr('value', 'Cancel');
        $('#delete-button').show();
    }
    function executeSearchCustomerPriority(fieldName, fieldValue) {
        if (executePreConditionForSearchCustomerPriority(fieldName, fieldValue)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'customerPriority', file:'search')}?fieldName=" + fieldName + "&fieldValue=" + fieldValue,
                success: function (entity) {
                    executePostConditionForSearchCustomerPriority(entity, fieldName, fieldValue);
                },
                dataType: 'json'
            });
        }
    }
    function executePreConditionForSearchCustomerPriority(fieldName, fieldValue) {
        // trim field vales before process.
        $('#' + fieldName).val($.trim($('#' + fieldName).val()));
        if (fieldValue == '' || fieldValue == null) {
            reset_customerPriority_form("#gFormCustomerPriority");
            return false;
        }
        return true;
    }
    function executePostConditionForSearchCustomerPriority(data, fieldName, fieldValue) {
        if (data == null) {
            reset_customerPriority_form("#gFormCustomerPriority");
            $('#' + fieldName).val(fieldValue); // Set search field with fieldValue
        } else {
            showCustomerPriority(data);
        }
    }
    function reset_customerPriority_form(formName) {
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