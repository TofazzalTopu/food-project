<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        $('#ui-widget-header-text').html('BankPaymentMethod')
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormBankPaymentMethod").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormBankPaymentMethod").validationEngine('attach');
        reset_form("#gFormBankPaymentMethod");
        $("#jqgrid-grid").jqGrid({
            url: '${resource(dir:'bankPaymentMethod', file:'list')}',
            datatype: "json",
            colNames: [
                'SL',
                'ID',

//        'Enterprise Configuration',
                'Name',
                'Short Name',
                'Is Active'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30, sortable: false, align: 'left'},
                {name: 'id', index: 'id', width: 0, hidden: true},

//        {name:'enterpriseConfiguration', index:'enterpriseConfiguration',width:100,align:'left'},
                {name: 'name', index: 'name', width: 100, align: 'left'},
                {name: 'shortName', index: 'shortName', width: 100, align: 'left'},
                {name: 'isActive', index: 'isActive', width: 50, align: 'center'}
            ],
            rowNum: 50,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#jqgrid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "All BankPaymentMethod Information",
            autowidth: true,
            height: true,
            scrollOffset: 0,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                executeEditBankPaymentMethod();
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
    function getSelectedBankPaymentMethodId() {
        var bankPaymentMethodId = null;
        var rowid = $("#jqgrid-grid").jqGrid('getGridParam', 'selrow');
        if (rowid) {
            bankPaymentMethodId = $("#jqgrid-grid").jqGrid('getCell', rowid, 'id');
        }
        if (bankPaymentMethodId == null) {
            bankPaymentMethodId = $('#gFormBankPaymentMethod input[name = id]').val();
        }
        return bankPaymentMethodId;
    }
    function executePreConditionBankPaymentMethod() {
        // trim field vales before process.
        trim_form();
        if ($("#gFormBankPaymentMethod").validate().form({onfocusout: false}) == false) {
            return false;
        }
        return true;
    }
    function executeAjaxBankPaymentMethod() {
        if (!executePreConditionBankPaymentMethod()) {
            return false;
        }
        var actionUrl = null;
        if ($('#gFormBankPaymentMethod input[name = id]').val()) {
            actionUrl = "${request.contextPath}/${params.controller}/update";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/create";
        }
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: jQuery("#gFormBankPaymentMethod").serialize(),
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionBankPaymentMethod(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-grid").trigger("reloadGrid");
                    reset_form('#gFormBankPaymentMethod');
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
    function executePostConditionBankPaymentMethod(result) {
        if (result.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_form('#gFormBankPaymentMethod');
        }
        MessageRenderer.render(result);
    }
    function deleteAjaxBankPaymentMethod() {    // Delete record
        var bankPaymentMethodId = getSelectedBankPaymentMethodId();
        if (executePreConditionForDeleteBankPaymentMethod(bankPaymentMethodId)) {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-bankPaymentMethod").dialog({
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
                            data: jQuery("#gFormBankPaymentMethod").serialize(),
                            url: "${resource(dir:'bankPaymentMethod', file:'delete')}",
                            success: function (message) {
                                executePostConditionForDeleteBankPaymentMethod(message);
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

    function executePreConditionForEditBankPaymentMethod(bankPaymentMethodId) {
        if (bankPaymentMethodId == null) {
            alert("Please select a bankPaymentMethod to edit");
            return false;
        }
        return true;
    }
    function executeEditBankPaymentMethod() {
        var bankPaymentMethodId = getSelectedBankPaymentMethodId();
        if (executePreConditionForEditBankPaymentMethod(bankPaymentMethodId)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'bankPaymentMethod', file:'edit')}?id=" + bankPaymentMethodId,
                success: function (entity) {
                    executePostConditionForEditBankPaymentMethod(entity);
                },
                dataType: 'json'
            });
        }
    }
    function executePostConditionForEditBankPaymentMethod(data) {
        if (data == null) {
            alert('Selected bankPaymentMethod might have been deleted by someone');  //Message renderer
        } else {
            showBankPaymentMethod(data);
        }
    }
    function executePreConditionForDeleteBankPaymentMethod(bankPaymentMethodId) {
        if (bankPaymentMethodId == null) {
            alert("Please select a bankPaymentMethod to delete");
            return false;
        }
        return true;
    }
    function executePostConditionForDeleteBankPaymentMethod(data) {
        if (data.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_form('#gFormBankPaymentMethod');
        }
        MessageRenderer.render(data)
    }
    function showBankPaymentMethod(entity) {
        var bankPaymentMethod = entity.bankPaymentMethod
        var enterpriseConfiguration = entity.enterpriseConfiguration

        $('#gFormBankPaymentMethod input[name = id]').val(bankPaymentMethod.id);
        $('#gFormBankPaymentMethod input[name = version]').val(bankPaymentMethod.version);


        if (enterpriseConfiguration) {
            $('#enterpriseList').setValue(enterpriseConfiguration.name);
            $('#enterpriseConfiguration').val(enterpriseConfiguration.id)
        }
        $('#name').val(bankPaymentMethod.name);
        $('#shortName').val(bankPaymentMethod.shortName);
        $('#isActive').attr('checked', bankPaymentMethod.isActive);
        if (bankPaymentMethod.userCreated) {
            $('#userCreated').val(bankPaymentMethod.userCreated.id).attr("selected", "selected");
        }
        else {
            $('#userCreated').val(bankPaymentMethod.userCreated);
        }
        if (bankPaymentMethod.userUpdated) {
            $('#userUpdated').val(bankPaymentMethod.userUpdated.id).attr("selected", "selected");
        }
        else {
            $('#userUpdated').val(bankPaymentMethod.userUpdated);
        }
        $('#dateCreated').val(bankPaymentMethod.dateCreated);
        $('#lastUpdated').val(bankPaymentMethod.lastUpdated);
        $('#create-button').attr('value', 'Update');
        $('#cancel-button').attr('value', 'Cancel');
        $('#delete-button').show();
    }
    function executeSearchBankPaymentMethod(fieldName, fieldValue) {
        if (executePreConditionForSearchBankPaymentMethod(fieldName, fieldValue)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'bankPaymentMethod', file:'search')}?fieldName=" + fieldName + "&fieldValue=" + fieldValue,
                success: function (entity) {
                    executePostConditionForSearchBankPaymentMethod(entity, fieldName, fieldValue);
                },
                dataType: 'json'
            });
        }
    }
    function executePreConditionForSearchBankPaymentMethod(fieldName, fieldValue) {
        // trim field vales before process.
        $('#' + fieldName).val($.trim($('#' + fieldName).val()));
        if (fieldValue == '' || fieldValue == null) {
            reset_form("#gFormBankPaymentMethod");
            return false;
        }
        return true;
    }
    function executePostConditionForSearchBankPaymentMethod(data, fieldName, fieldValue) {
        if (data == null) {
            reset_form("#gFormBankPaymentMethod"); // Clear Form
            $('#' + fieldName).val(fieldValue); // Set search field with fieldValue
        } else {
            showBankPaymentMethod(data);
        }
    }
</script>