<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        $('#ui-widget-header-text').html('BankBranch');
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormBankBranch").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormBankBranch").validationEngine('attach');
        reset_bankBranch_form("#gFormBankBranch");
        $("#jqgrid-grid").jqGrid({
            url: '${resource(dir:'bankBranch', file:'list')}',
            datatype: "json",
            colNames: [
                'SL',
                'ID',

                'Bank',
                'Name',
                'Note',
                'Is Active'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30, sortable: false, align: 'left'},
                {name: 'id', index: 'id', width: 0, hidden: true},

                {name: 'bank', index: 'bank', width: 100, align: 'left'},
                {name: 'name', index: 'name', width: 100, align: 'left'},
                {name: 'note', index: 'note', width: 100, align: 'left', hidden: true},
                {name: 'isActive', index: 'isActive', width: 50, align: 'center'}
            ],
            rowNum: 50,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#jqgrid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Bank Branch List",
            autowidth: true,
            height: true,
            scrollOffset: 0,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                executeEditBankBranch();
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
    function getSelectedBankBranchId() {
        var bankBranchId = null;
        var rowid = $("#jqgrid-grid").jqGrid('getGridParam', 'selrow');
        if (rowid) {
            bankBranchId = $("#jqgrid-grid").jqGrid('getCell', rowid, 'id');
        }
        if (bankBranchId == null) {
            bankBranchId = $('#gFormBankBranch input[name = id]').val();
        }
        return bankBranchId;
    }
    function executePreConditionBankBranch() {
        // trim field vales before process.
        trim_form();
        if (!$("#gFormBankBranch").validationEngine('validate')) {
            return false;
        }
        return true;
    }
    function executeAjaxBankBranch() {
        if (!executePreConditionBankBranch()) {
            return false;
        }
        var actionUrl = null;
        if ($('#gFormBankBranch input[name = id]').val()) {
            actionUrl = "${request.contextPath}/${params.controller}/update";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/create";
        }
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: jQuery("#gFormBankBranch").serialize(),
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionBankBranch(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-grid").trigger("reloadGrid");
                    reset_bankBranch_form('#gFormBankBranch');
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
    function executePostConditionBankBranch(result) {
        if (result.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_bankBranch_form('#gFormBankBranch');
        }
        MessageRenderer.render(result);
    }
    function deleteAjaxBankBranch() {    // Delete record
        var bankBranchId = getSelectedBankBranchId();
        if (executePreConditionForDeleteBankBranch(bankBranchId)) {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-bankBranch").dialog({
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
                            data: jQuery("#gFormBankBranch").serialize(),
                            url: "${resource(dir:'bankBranch', file:'delete')}",
                            success: function (message) {
                                executePostConditionForDeleteBankBranch(message);
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

    function executePreConditionForEditBankBranch(bankBranchId) {
        if (bankBranchId == null) {
            alert("Please select a bankBranch to edit");
            return false;
        }
        return true;
    }
    function executeEditBankBranch() {
        var bankBranchId = getSelectedBankBranchId();
        if (executePreConditionForEditBankBranch(bankBranchId)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'bankBranch', file:'edit')}?id=" + bankBranchId,
                success: function (entity) {
                    executePostConditionForEditBankBranch(entity);
                },
                dataType: 'json'
            });
        }
    }
    function executePostConditionForEditBankBranch(data) {
        if (data == null) {
            alert('Selected bankBranch might have been deleted by someone');  //Message renderer
        } else {
            showBankBranch(data);
        }
    }
    function executePreConditionForDeleteBankBranch(bankBranchId) {
        if (bankBranchId == null) {
            alert("Please select a bankBranch to delete");
            return false;
        }
        return true;
    }
    function executePostConditionForDeleteBankBranch(data) {
        if (data.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_bankBranch_form('#gFormBankBranch');
        }
        MessageRenderer.render(data)
    }
    function showBankBranch(entity) {
        var bankBranch = entity.bankBranch
        var bank = entity.bank
        var enterpriseConfiguration = entity.enterpriseConfiguration
        $('#gFormBankBranch input[name = id]').val(bankBranch.id);
        $('#gFormBankBranch input[name = version]').val(bankBranch.version);

        if (enterpriseConfiguration) {
            $('#enterpriseList').setValue(enterpriseConfiguration.name);
            $('#enterpriseConfiguration').val(enterpriseConfiguration.id)
        }
        if (bank) {
            var options = '<option value="' + bank.id + '">' + bank.name + '</option>';

            $("#bank").html(options);
        }
        $('#name').val(bankBranch.name);
        $('#note').val(bankBranch.note);
        $('#isActive').attr('checked', bankBranch.isActive);

        $('#create-button').attr('value', 'Update');
        $('#cancel-button').attr('value', 'Cancel');
        $('#delete-button').show();
    }
    function executeSearchBankBranch(fieldName, fieldValue) {
        if (executePreConditionForSearchBankBranch(fieldName, fieldValue)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'bankBranch', file:'search')}?fieldName=" + fieldName + "&fieldValue=" + fieldValue,
                success: function (entity) {
                    executePostConditionForSearchBankBranch(entity, fieldName, fieldValue);
                },
                dataType: 'json'
            });
        }
    }
    function executePreConditionForSearchBankBranch(fieldName, fieldValue) {
        // trim field vales before process.
        $('#' + fieldName).val($.trim($('#' + fieldName).val()));
        if (fieldValue == '' || fieldValue == null) {
            reset_bankBranch_form("#gFormBankBranch");
            return false;
        }
        return true;
    }
    function executePostConditionForSearchBankBranch(data, fieldName, fieldValue) {
        if (data == null) {
            reset_bankBranch_form("#gFormBankBranch"); // Clear Form
            $('#' + fieldName).val(fieldValue); // Set search field with fieldValue
        } else {
            showBankBranch(data);
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
                } else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete: function () {

            }
        });
    }
    function reset_bankBranch_form(formName) {
        var enterpriseId = $("#enterpriseConfiguration").val();
        $(formName).find(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
            if (this.type == 'hidden') {
                this.value = "";
            } else {
                this.value = this.defaultValue;
            }
        });
        $(formName+' select').val('');
//        $(formName+' input').attr('readonly',false);
//    $('input[type=checkbox]').attr('checked', false);
        $(formName + ' input[name = create-button]').attr('value', 'Create');
        $(formName + ' input[name = delete-button]').hide();
        $("#enterpriseConfiguration").val(enterpriseId);
    }
</script>