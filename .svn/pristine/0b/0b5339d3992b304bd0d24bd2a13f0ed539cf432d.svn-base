<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        $('#ui-widget-header-text').html('CustomerCategory');
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormCustomerCategory").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormCustomerCategory").validationEngine('attach');
        reset_customerCategory_form("#gFormCustomerCategory");
        $("#enterPriseName").attr("disabled", true);

        $("#jqgrid-grid").jqGrid({
            url: '${resource(dir:'customerCategory', file:'list')}',
            datatype: "json",
            colNames: [
                'SL',
                'ID',

                'Enterprise',
                'Category Name',
                'Note'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30, sortable: false, align: 'left'},
                {name: 'id', index: 'id', width: 0, hidden: true},

                {name: 'enterpriseConfiguration', index: 'enterpriseConfiguration', width: 100, align: 'left'},
                {name: 'name', index: 'name', width: 100, align: 'left'},
                {name: 'note', index: 'note', width: 100, align: 'left', hidden: true}
            ],
            rowNum: 50,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#jqgrid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Customer Category List",
            autowidth: true,
            height: true,
            scrollOffset: 0,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                executeEditCustomerCategory();
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
    function getSelectedCustomerCategoryId() {
        var customerCategoryId = null;
        var rowid = $("#jqgrid-grid").jqGrid('getGridParam', 'selrow');
        if (rowid) {
            customerCategoryId = $("#jqgrid-grid").jqGrid('getCell', rowid, 'id');
        }
        if (customerCategoryId == null) {
            customerCategoryId = $('#gFormCustomerCategory input[name = id]').val();
        }
        return customerCategoryId;
    }
    function executePreConditionCustomerCategory() {
        // trim field vales before process.
        trim_form();
        if (!$("#gFormCustomerCategory").validationEngine('validate')) {
            return false;
        }
        return true;
    }
    function executeAjaxCustomerCategory() {
        if (!executePreConditionCustomerCategory()) {
            return false;
        }
        var actionUrl = null;
        if ($('#gFormCustomerCategory input[name = id]').val()) {
            actionUrl = "${request.contextPath}/${params.controller}/update";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/create";
        }
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: jQuery("#gFormCustomerCategory").serialize(),
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionCustomerCategory(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-grid").trigger("reloadGrid");
                    reset_customerCategory_form("#gFormCustomerCategory");
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
    function executePostConditionCustomerCategory(result) {
        if (result.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_customerCategory_form("#gFormCustomerCategory");
        }
        MessageRenderer.render(result);
    }
    function deleteAjaxCustomerCategory() {    // Delete record
        var customerCategoryId = getSelectedCustomerCategoryId();
        if (executePreConditionForDeleteCustomerCategory(customerCategoryId)) {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-customerCategory").dialog({
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
                            data: jQuery("#gFormCustomerCategory").serialize(),
                            url: "${resource(dir:'customerCategory', file:'delete')}",
                            success: function (message) {
                                executePostConditionForDeleteCustomerCategory(message);
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

    function executePreConditionForEditCustomerCategory(customerCategoryId) {
        if (customerCategoryId == null) {
            alert("Please select a customerCategory to edit");
            return false;
        }
        return true;
    }
    function executeEditCustomerCategory() {
        var customerCategoryId = getSelectedCustomerCategoryId();
        if (executePreConditionForEditCustomerCategory(customerCategoryId)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'customerCategory', file:'edit')}?id=" + customerCategoryId,
                success: function (entity) {
                    executePostConditionForEditCustomerCategory(entity);
                },
                dataType: 'json'
            });
        }
    }
    function executePostConditionForEditCustomerCategory(data) {
        if (data == null) {
            alert('Selected customerCategory might have been deleted by someone');  //Message renderer
        } else {
            showCustomerCategory(data);
        }
    }
    function executePreConditionForDeleteCustomerCategory(customerCategoryId) {
        if (customerCategoryId == null) {
            alert("Please select a customerCategory to delete");
            return false;
        }
        return true;
    }
    function executePostConditionForDeleteCustomerCategory(data) {
        if (data.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_customerCategory_form("#gFormCustomerCategory");
        }
        MessageRenderer.render(data)
    }
    function showCustomerCategory(entity) {
        var customerCategory = entity.customerCategory
        var enterpriseConfiguration = entity.enterpriseConfiguration
        $('#gFormCustomerCategory input[name = id]').val(customerCategory.id);
        $('#gFormCustomerCategory input[name = version]').val(customerCategory.version);
        if (enterpriseConfiguration) {
            $('#enterpriseList').setValue(enterpriseConfiguration.name);
            $('#enterpriseConfiguration').val(enterpriseConfiguration.id)
        }


        $('#name').val(customerCategory.name);
        $('#note').val(customerCategory.note);
        if (customerCategory.userCreated) {
            $('#userCreated').val(customerCategory.userCreated.id).attr("selected", "selected");
        }
        else {
            $('#userCreated').val(customerCategory.userCreated);
        }
        if (customerCategory.userUpdated) {
            $('#userUpdated').val(customerCategory.userUpdated.id).attr("selected", "selected");
        }
        else {
            $('#userUpdated').val(customerCategory.userUpdated);
        }
        $('#dateCreated').val(customerCategory.dateCreated);
        $('#lastUpdated').val(customerCategory.lastUpdated);
        $('#create-button').attr('value', 'Update');
        $('#cancel-button').attr('value', 'Cancel');
        $('#delete-button').show();
    }
    function executeSearchCustomerCategory(fieldName, fieldValue) {
        if (executePreConditionForSearchCustomerCategory(fieldName, fieldValue)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'customerCategory', file:'search')}?fieldName=" + fieldName + "&fieldValue=" + fieldValue,
                success: function (entity) {
                    executePostConditionForSearchCustomerCategory(entity, fieldName, fieldValue);
                },
                dataType: 'json'
            });
        }
    }
    function executePreConditionForSearchCustomerCategory(fieldName, fieldValue) {
        // trim field vales before process.
        $('#' + fieldName).val($.trim($('#' + fieldName).val()));
        if (fieldValue == '' || fieldValue == null) {
            reset_customerCategory_form("#gFormCustomerCategory");
            return false;
        }
        return true;
    }
    function executePostConditionForSearchCustomerCategory(data, fieldName, fieldValue) {
        if (data == null) {
            reset_customerCategory_form("#gFormCustomerCategory");
            $('#' + fieldName).val(fieldValue); // Set search field with fieldValue
        } else {
            showCustomerCategory(data);
        }
    }
    function reset_customerCategory_form(formName) {
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