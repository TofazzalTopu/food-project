<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormCustomerEligibilityMaster").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormCustomerEligibilityMaster").validationEngine('attach');
        reset_form("#gFormCustomerEligibilityMaster");
        $("#jqgrid-grid-customerEligibilityMaster").jqGrid({
            url: '${resource(dir:'customerEligibilityMaster', file:'list')}',
            datatype: "json",
            colNames: [
                'SL',
                'ID',

                'Eligibility Criteria',
                'Amount'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30, sortable: true, align: 'left'},
                {name: 'id', index: 'id', width: 0, hidden: true},


                {name: 'eligibilityTemplate', index: 'eligibilityTemplate', width: 100, align: 'left'}

                ,

                {name: 'amount', index: 'amount', width: 100, align: 'right', formatter: 'number', formatoptions: {thousandsSeparator: ","}}


            ],
            rowNum: 10,
            rowList: [10, 20, 30],
            pager: '#jqgrid-pager-customerEligibilityMaster',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Customer Eligibility Master List",
            autowidth: true,
            autoheight: true,
            scrollOffset: 0,
            altRows: true,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                executeEditCustomerEligibilityMaster();
            }
        });

    });
    function getSelectedCustomerEligibilityMasterId() {
        var customerEligibilityMasterId = null;
        var rowid = $("#jqgrid-grid-customerEligibilityMaster").jqGrid('getGridParam', 'selrow');
        if (rowid) {
            customerEligibilityMasterId = $("#jqgrid-grid-customerEligibilityMaster").jqGrid('getCell', rowid, 'id');
        }
        if (customerEligibilityMasterId == null) {
            customerEligibilityMasterId = $('#gFormCustomerEligibilityMaster input[name = id]').val();
        }
        return customerEligibilityMasterId;
    }
    function executePreConditionCustomerEligibilityMaster() {
        // trim field vales before process.
        trim_form();
        if (!$("#gFormCustomerEligibilityMaster").validationEngine('validate')) {
            return false;
        }
        return true;
    }
    function executeAjaxCustomerEligibilityMaster() {
        if (!executePreConditionCustomerEligibilityMaster()) {
            return false;
        }
        var actionUrl = null;
        if ($('#gFormCustomerEligibilityMaster input[name = id]').val()) {
            actionUrl = "${request.contextPath}/${params.controller}/update";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/create";
        }
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: jQuery("#gFormCustomerEligibilityMaster").serialize(),
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionCustomerEligibilityMaster(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-grid-customerEligibilityMaster").trigger("reloadGrid");
                    reset_form('#gFormCustomerEligibilityMaster');
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
    function executePostConditionCustomerEligibilityMaster(result) {
        if (result.type == 1) {
            $("#jqgrid-grid-customerEligibilityMaster").trigger("reloadGrid");
            reset_form('#gFormCustomerEligibilityMaster');
        }
        MessageRenderer.render(result);
    }
    function deleteAjaxCustomerEligibilityMaster() {    // Delete record
        var customerEligibilityMasterId = getSelectedCustomerEligibilityMasterId();
        if (executePreConditionForDeleteCustomerEligibilityMaster(customerEligibilityMasterId)) {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-customerEligibilityMaster").dialog({
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
                            data: jQuery("#gFormCustomerEligibilityMaster").serialize(),
                            url: "${resource(dir:'customerEligibilityMaster', file:'delete')}",
                            success: function (message) {
                                executePostConditionForDeleteCustomerEligibilityMaster(message);
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

    function executePreConditionForEditCustomerEligibilityMaster(customerEligibilityMasterId) {
        if (customerEligibilityMasterId == null) {
            alert("Please select a customerEligibilityMaster to edit");
            return false;
        }
        return true;
    }
    function executeEditCustomerEligibilityMaster() {
        var customerEligibilityMasterId = getSelectedCustomerEligibilityMasterId();
        if (executePreConditionForEditCustomerEligibilityMaster(customerEligibilityMasterId)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'customerEligibilityMaster', file:'edit')}?id=" + customerEligibilityMasterId,
                success: function (entity) {
                    executePostConditionForEditCustomerEligibilityMaster(entity);
                },
                dataType: 'json'
            });
        }
    }
    function executePostConditionForEditCustomerEligibilityMaster(data) {
        if (data == null) {
            alert('Selected customerEligibilityMaster might have been deleted by someone');  //Message renderer
        } else {
            showCustomerEligibilityMaster(data);
        }
    }
    function executePreConditionForDeleteCustomerEligibilityMaster(customerEligibilityMasterId) {
        if (customerEligibilityMasterId == null) {
            alert("Please select a customerEligibilityMaster to delete");
            return false;
        }
        return true;
    }
    function executePostConditionForDeleteCustomerEligibilityMaster(data) {
        if (data.type == 1) {
            $("#jqgrid-grid-customerEligibilityMaster").trigger("reloadGrid");
            reset_form('#gFormCustomerEligibilityMaster');
        }
        MessageRenderer.render(data)
    }
    function showCustomerEligibilityMaster(entity) {
        $('#gFormCustomerEligibilityMaster input[name = id]').val(entity.id);
        $('#gFormCustomerEligibilityMaster input[name = version]').val(entity.version);

        if (entity.eligibilityTemplate) {
            $('#eligibilityTemplate').val(entity.eligibilityTemplate.id).attr("selected", "selected");
        }
        else {
            $('#eligibilityTemplate').val(entity.eligibilityTemplate);
        }
        $('#amount').val(entity.amount);
        $('#create-button-customerEligibilityMaster').attr('value', 'Update');
        $('#delete-button-customerEligibilityMaster').show();
    }
    function executeSearchCustomerEligibilityMaster(fieldName, fieldValue) {
        if (executePreConditionForSearchCustomerEligibilityMaster(fieldName, fieldValue)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'customerEligibilityMaster', file:'search')}?fieldName=" + fieldName + "&fieldValue=" + fieldValue,
                success: function (entity) {
                    executePostConditionForSearchCustomerEligibilityMaster(entity, fieldName, fieldValue);
                },
                dataType: 'json'
            });
        }
    }
    function executePreConditionForSearchCustomerEligibilityMaster(fieldName, fieldValue) {
        // trim field vales before process.
        $('#' + fieldName).val($.trim($('#' + fieldName).val()));
        if (fieldValue == '' || fieldValue == null) {
            reset_form("#gFormCustomerEligibilityMaster");
            return false;
        }
        return true;
    }
    function executePostConditionForSearchCustomerEligibilityMaster(data, fieldName, fieldValue) {
        if (data == null) {
            reset_form("#gFormCustomerEligibilityMaster"); // Clear Form
            $('#' + fieldName).val(fieldValue); // Set search field with fieldValue
        } else {
            showCustomerEligibilityMaster(data);
        }
    }

    function reset_form(formName) {
        $(formName).find(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
            if (this.type == 'hidden') {
                this.value = "";
            } else {
                this.value = this.defaultValue;
            }
        });
        $('input[type=checkbox]').attr('checked', false);
        $(formName + ' input[name = create-button]').attr('value', 'Create');
        $(formName + ' input[name = delete-button]').hide();
    }

    function trim_form() {
        $(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
            this.value = $.trim(this.value);
        });
    }
    function getValuesForCustomer(id) {
        jQuery("#jqgrid-grid-finishProduct").jqGrid().setGridParam({url: '${resource(dir:'customerEligibilityMaster', file:'listRetailOrderDetails')}?customerMasterId=' + id,
            datatype: "json", mtype: "POST"}).trigger("reloadGrid");
    }
</script>