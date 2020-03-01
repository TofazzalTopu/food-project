<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        $('#ui-widget-header-text').html('BankAccount');
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormBankAccount").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormBankAccount").validationEngine('attach');
        reset_form("#gFormBankAccount");
        $("#jqgrid-grid").jqGrid({
            url: '${resource(dir:'bankAccount', file:'list')}',
            datatype: "json",
            colNames: [
                'SL',
                'ID',

                'Bank',
                'Bank Branch',
                'Account No',
                'Ledger Account Code',
                'Is Active'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30, sortable: false, align: 'left'},
                {name: 'id', index: 'id', width: 0, hidden: true},

                {name: 'bank', index: 'bank', width: 100, align: 'left'},
                {name: 'bankBranch', index: 'bankBranch', width: 100, align: 'left'},
                {name: 'accountNo', index: 'accountNo', width: 100, align: 'left'},
                {name: 'ledgerAccountCode', index: 'ledgerAccountCode', width: 100, align: 'left'},
                {name: 'isActive', index: 'isActive', width: 50, align: 'center'}
            ],
            rowNum: 50,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#jqgrid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "asc",
            caption: "Bank Account List",
            autowidth: true,
            height: true,
            scrollOffset: 0,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                executeEditBankAccount();
            }
        });
        $("#jqgrid-grid").jqGrid('navGrid', '#jqgrid-pager', {edit: false, add: false, del: false, search: false});
//     $("#jqgrid-grid").gridResize({minWidth:350,maxWidth:800,minHeight:200});
//      $(".ui-pg-selbox").children().each(function() {
//          if ($(this).val() == -1) {
//              $(this).html('All')
//          }
//
//      });
    });
    function getSelectedBankAccountId() {
        var bankAccountId = null;
        var rowid = $("#jqgrid-grid").jqGrid('getGridParam', 'selrow');
        if (rowid) {
            bankAccountId = $("#jqgrid-grid").jqGrid('getCell', rowid, 'id');
        }
        if (bankAccountId == null) {
            bankAccountId = $('#gFormBankAccount input[name = id]').val();
        }
        return bankAccountId;
    }
    function executePreConditionBankAccount() {
        // trim field vales before process.
        trim_form();
        if (!$("#gFormBankAccount").validationEngine('validate')) {
            return false;
        }
        return true;
    }
    function executeAjaxBankAccount() {
        if (!executePreConditionBankAccount()) {
            return false;
        }
        var actionUrl = null;
        if ($('#gFormBankAccount input[name = id]').val()) {
            actionUrl = "${request.contextPath}/${params.controller}/update";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/create";
        }
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: jQuery("#gFormBankAccount").serialize(),
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionBankAccount(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-grid").trigger("reloadGrid");
                    reset_form('#gFormBankAccount');
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
    function executePostConditionBankAccount(result) {
        if (result.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_form('#gFormBankAccount');
        }
        MessageRenderer.render(result);
    }
    function deleteAjaxBankAccount() {    // Delete record
        var bankAccountId = getSelectedBankAccountId();
        if (executePreConditionForDeleteBankAccount(bankAccountId)) {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-bankAccount").dialog({
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
                            data: jQuery("#gFormBankAccount").serialize(),
                            url: "${resource(dir:'bankAccount', file:'delete')}",
                            success: function (message) {
                                executePostConditionForDeleteBankAccount(message);
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

    function executePreConditionForEditBankAccount(bankAccountId) {
        if (bankAccountId == null) {
            alert("Please select a bankAccount to edit");
            return false;
        }
        return true;
    }
    function executeEditBankAccount() {
        var bankAccountId = getSelectedBankAccountId();
        if (executePreConditionForEditBankAccount(bankAccountId)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'bankAccount', file:'edit')}?id=" + bankAccountId,
                success: function (entity) {
                    executePostConditionForEditBankAccount(entity);
                },
                dataType: 'json'
            });
        }
    }
    function executePostConditionForEditBankAccount(data) {
        if (data == null) {
            alert('Selected bankAccount might have been deleted by someone');  //Message renderer
        } else {
            showBankAccount(data);
        }
    }
    function executePreConditionForDeleteBankAccount(bankAccountId) {
        if (bankAccountId == null) {
            alert("Please select a bankAccount to delete");
            return false;
        }
        return true;
    }
    function executePostConditionForDeleteBankAccount(data) {
        if (data.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_form('#gFormBankAccount');
        }
        MessageRenderer.render(data)
    }
    function showBankAccount(entity) {

        var bankAccount = entity.bankAccount
        var bank = entity.bank
        var bankBranch = entity.bankBranch
        var enterpriseConfiguration = entity.enterpriseConfiguration


        $('#gFormBankAccount input[name = id]').val(bankAccount.id);
        $('#gFormBankAccount input[name = version]').val(bankAccount.version);

        if (enterpriseConfiguration) {
            $('#enterpriseList').setValue(enterpriseConfiguration.name);
            $('#enterpriseConfiguration').val(enterpriseConfiguration.id)
        }
        if (bank) {
            var options = '<option value="' + bank.id + '">' + bank.name + '</option>';

            $("#bank").html(options);
        }
        if (bankBranch) {
            var options = '<option value="' + bankBranch.id + '">' + bankBranch.name + '</option>';
            $("#bankBranch").html(options);
        }
        $('#accountNo').val(bankAccount.accountNo);
        $('#ledgerAccountCode').val(bankAccount.ledgerAccountCode);
//        $('#ledgerAccountCode').attr('readonly','readonly');
        $('#isActive').attr('checked', bankAccount.isActive);

        $('#create-button').attr('value', 'Update');
        $('#cancel-button').attr('value', 'Cancel');
        $('#delete-button').show();
    }
    function executeSearchBankAccount(fieldName, fieldValue) {
        if (executePreConditionForSearchBankAccount(fieldName, fieldValue)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'bankAccount', file:'search')}?fieldName=" + fieldName + "&fieldValue=" + fieldValue,
                success: function (entity) {
                    executePostConditionForSearchBankAccount(entity, fieldName, fieldValue);
                },
                dataType: 'json'
            });
        }
    }
    function executePreConditionForSearchBankAccount(fieldName, fieldValue) {
        // trim field vales before process.
        $('#' + fieldName).val($.trim($('#' + fieldName).val()));
        if (fieldValue == '' || fieldValue == null) {
            reset_form('#gFormBankAccount');
            return false;
        }
        return true;
    }
    function executePostConditionForSearchBankAccount(data, fieldName, fieldValue) {
        if (data == null) {
            reset_form('#gFormBankAccount');
            $('#' + fieldName).val(fieldValue); // Set search field with fieldValue
        } else {
            showBankAccount(data);
        }
    }
    function loadBank(id) {
        jQuery.ajax({
            type: "POST",
            url: '${resource(dir:'bank', file:'loadBank')}?id=' + id,
            dataType: 'json',
            success: function (data, textStatus) {

                var options = '<option value="">Select Bank</option>';
                $.each(data, function (key, val) {
                    options += '<option value="' + val.id + '">' + val.name + '</option>';
                });
                $("#bank").html(options);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                }else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete: function () {

            }
        });
    }
    function loadBankBranch() {
        if ($("#bank").val()) {
            jQuery.ajax({
                type: "POST",
                url: '${resource(dir:'bankBranch', file:'loadBankBranch')}?id=' + $("#bank").val(),
                dataType: 'json',
                success: function (data, textStatus) {

                    var options = '<option value="">Select Bank Branch</option>';
                    $.each(data, function (key, val) {
                        options += '<option value="' + val.id + '">' + val.name + '</option>';
                    });
                    $("#bankBranch").html(options);
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    if(XMLHttpRequest.status = 0){
                        MessageRenderer.renderErrorText("Network Problem: Time out");
                    }else{
                        MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                    }
                },
                complete: function () {

                }
            });
        }

    }
    function reset_form(formName) {
        var enterpriseId = $("#enterpriseConfiguration").val();
        $(formName).find(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
            if (this.type == 'hidden') {
                this.value = "";
            } else {
                this.value = this.defaultValue;
            }
        });
        $(formName + ' input[name = create-button]').attr('value', 'Create');
        $(formName + ' input[name = delete-button]').hide();
        $("#bank").html('Select Bank');
        $("#bankBranch").html('Select Bank Branch');
        $("#enterpriseConfiguration").val(enterpriseId);
    }
</script>