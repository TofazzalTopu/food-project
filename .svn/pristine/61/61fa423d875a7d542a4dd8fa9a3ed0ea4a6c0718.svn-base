<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        $('#ui-widget-header-text').html('Bank')
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormBank").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormBank").validationEngine('attach');
        reset_bank_form("#gFormBank");
        $("#jqgrid-grid").jqGrid({
            url: '${resource(dir:'bank', file:'list')}',
            datatype: "json",
            colNames: [
                'SL',
                'ID',
                'Enterprise',
                'Code',
                'Name',
                'Note',
                'Is Active'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30, sortable: false, align: 'left'},
                {name: 'id', index: 'id', width: 0, hidden: true},
                {name: 'enterpriseConfiguration', index: 'enterpriseConfiguration', width: 100, align: 'left'},
                {name: 'code', index: 'code', width: 100, align: 'left'},
                {name: 'name', index: 'name', width: 100, align: 'left'},
                {name: 'note', index: 'note', width: 100, align: 'left'},
                {name: 'isActive', index: 'isActive', width: 50, align: 'center'}
            ],
            rowNum: 50,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100],
            pager: '#jqgrid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "asc",
            caption: "Bank List",
            autowidth: true,
            height: true,
            scrollOffset: 0,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                executeEditBank();
            }
        });
        $("#jqgrid-grid").jqGrid('navGrid', '#jqgrid-pager', {edit: false, add: false, del: false, search: false});
//        $("#jqgrid-grid").gridResize({minWidth: 350, maxWidth: 800, minHeight: 200});
//        $(".ui-pg-selbox").children().each(function () {
//            if ($(this).val() == -1) {
//                $(this).html('All')
//            }
//
//        });
    });
    function getSelectedBankId() {
        var bankId = null;
        var rowid = $("#jqgrid-grid").jqGrid('getGridParam', 'selrow');
        if (rowid) {
            bankId = $("#jqgrid-grid").jqGrid('getCell', rowid, 'id');
        }
        if (bankId == null) {
            bankId = $('#gFormBank input[name = id]').val();
        }
        return bankId;
    }
    function executePreConditionBank() {
        // trim field vales before process.
        trim_form();
        if (!$("#gFormBank").validationEngine('validate')) {
            return false;
        }
        return true;
    }
    function executeAjaxBank() {
        if (!executePreConditionBank()) {
            return false;
        }
        var actionUrl = null;
        if ($('#gFormBank input[name = id]').val()) {
            actionUrl = "${request.contextPath}/${params.controller}/update";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/create";
        }
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: jQuery("#gFormBank").serialize(),
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionBank(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-grid").trigger("reloadGrid");
                    reset_bank_form('#gFormBank');
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
    function executePostConditionBank(result) {
        if (result.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_bank_form('#gFormBank');
        }
        MessageRenderer.render(result);
    }
    function deleteAjaxBank() {    // Delete record
        var bankId = getSelectedBankId();
        if (executePreConditionForDeleteBank(bankId)) {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-bank").dialog({
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
                            data: jQuery("#gFormBank").serialize(),
                            url: "${resource(dir:'bank', file:'delete')}",
                            success: function (message) {
                                executePostConditionForDeleteBank(message);
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

    function executePreConditionForEditBank(bankId) {
        if (bankId == null) {
            alert("Please select a bank to edit");
            return false;
        }
        return true;
    }
    function executeEditBank() {
        var bankId = getSelectedBankId();
        if (executePreConditionForEditBank(bankId)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'bank', file:'edit')}?id=" + bankId,
                success: function (entity) {
                    executePostConditionForEditBank(entity);
                },
                dataType: 'json'
            });
        }
    }
    function executePostConditionForEditBank(data) {
        if (data == null) {
            alert('Selected bank might have been deleted by someone');  //Message renderer
        } else {
            showBank(data);
        }
    }
    function executePreConditionForDeleteBank(bankId) {
        if (bankId == null) {
            alert("Please select a bank to delete");
            return false;
        }
        return true;
    }
    function executePostConditionForDeleteBank(data) {
        if (data.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_bank_form('#gFormBank');
        }
        MessageRenderer.render(data)
    }
    function showBank(entity) {
        var bank=entity.bank
        var enterpriseConfiguration=entity.enterpriseConfiguration
        $('#gFormBank input[name = id]').val(bank.id);
        $('#gFormBank input[name = version]').val(bank.version);

        if (enterpriseConfiguration){
            $('#enterpriseList').setValue(enterpriseConfiguration.name);
            $('#enterpriseConfiguration').val(enterpriseConfiguration.id)
        }

        $('#code').val(bank.code);
//        $('#code').attr('readonly','readonly');
        $('#name').val(bank.name);
        $('#note').val(bank.note);
        $('#isActive').attr('checked', bank.isActive);
        if (bank.userCreated) {
            $('#userCreated').val(bank.userCreated.id).attr("selected", "selected");
        }
        else {
            $('#userCreated').val(bank.userCreated);
        }
        if (bank.userUpdated) {
            $('#userUpdated').val(bank.userUpdated.id).attr("selected", "selected");
        }
        else {
            $('#userUpdated').val(bank.userUpdated);
        }
        $('#dateCreated').val(bank.dateCreated);
        $('#lastUpdated').val(bank.lastUpdated);
        $('#create-button').attr('value', 'Update');
        $('#cancel-button').attr('value', 'Cancel');
        $('#delete-button').show();
    }
    function executeSearchBank(fieldName, fieldValue) {
        if (executePreConditionForSearchBank(fieldName, fieldValue)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'bank', file:'search')}?fieldName=" + fieldName + "&fieldValue=" + fieldValue,
                success: function (entity) {
                    executePostConditionForSearchBank(entity, fieldName, fieldValue);
                },
                dataType: 'json'
            });
        }
    }
    function executePreConditionForSearchBank(fieldName, fieldValue) {
        // trim field vales before process.
        $('#' + fieldName).val($.trim($('#' + fieldName).val()));
        if (fieldValue == '' || fieldValue == null) {
            reset_bank_form("#gFormBank");
            return false;
        }
        return true;
    }
    function executePostConditionForSearchBank(data, fieldName, fieldValue) {
        if (data == null) {
            reset_bank_form("#gFormBank"); // Clear Form
            $('#' + fieldName).val(fieldValue); // Set search field with fieldValue
        } else {
            showBank(data);
        }
    }

    function reset_bank_form(formName) {
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