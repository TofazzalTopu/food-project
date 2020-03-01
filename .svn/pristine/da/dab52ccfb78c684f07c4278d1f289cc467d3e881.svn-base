<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormCurrencyDemonstration").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormCurrencyDemonstration").validationEngine('attach');
        reset_form("#gFormCurrencyDemonstration");
        $("#jqgrid-grid-currencyDemonstration").jqGrid({
            url: '${resource(dir:'currencyDemonstration', file:'list')}',
            datatype: "json",
            colNames: [
                'SL',
                'ID',

                'User Created',
                'User Updated',
                'Date Created',
                'Date Updated',
                'Local Currency',
                'Note Name',
                'Value',
                'Is Active'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30, sortable: true, align: 'left'},
                {name: 'id', index: 'id', width: 0, hidden: true},


                {name: 'userCreated', index: 'userCreated', width: 100, align: 'left', hidden: true}

                ,

                {name: 'userUpdated', index: 'userUpdated', width: 100, align: 'left', hidden: true}

                ,

                {name: 'dateCreated', index: 'dateCreated', width: 100, align: 'left', formatter: 'date', formatoptions: {newformat: 'd-m-Y'}, hidden: true}

                ,

                {name: 'dateUpdated', index: 'dateUpdated', width: 100, align: 'left', formatter: 'date', formatoptions: {newformat: 'd-m-Y'}, hidden: true}

                ,

                {name: 'localCurrency', index: 'localCurrency', width: 100, align: 'left'}

                ,

                {name: 'noteName', index: 'noteName', width: 100, align: 'left'}

                ,

                {name: 'value', index: 'value', width: 100, align: 'right', formatter: 'number', formatoptions: {thousandsSeparator: ","}}

                ,

                {name: 'isActive', index: 'isActive', width: 100, align: 'left'}


            ],
            rowNum: 10,
            rowList: [10, 20, 30],
            pager: '#jqgrid-pager-currencyDemonstration',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "All Currency Demonstration Information",
            autowidth: true,
            autoheight: true,
            scrollOffset: 0,
            altRows: true,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                executeEditCurrencyDemonstration();
            }
        });
        $("#jqgrid-grid-currencyDemonstration").jqGrid('navGrid', '#jqgrid-pager-currencyDemonstration', {edit: false, add: false, del: false, search: false})
                .navButtonAdd('#jqgrid-pager-currencyDemonstration', {
                    caption: "Edit",
                    buttonicon: "ui-icon-edit",
                    onClickButton: function () {
                        executeEditCurrencyDemonstration();
                    },
                    position: "last"
                })
                .navButtonAdd('#jqgrid-pager-currencyDemonstration', {
                    caption: "Delete",
                    buttonicon: "ui-icon-del",
                    onClickButton: function () {
                        deleteAjaxCurrencyDemonstration();
                    },
                    position: "last"
                });
    });
    function getSelectedCurrencyDemonstrationId() {
        var currencyDemonstrationId = null;
        var rowid = $("#jqgrid-grid-currencyDemonstration").jqGrid('getGridParam', 'selrow');
        if (rowid) {
            currencyDemonstrationId = $("#jqgrid-grid-currencyDemonstration").jqGrid('getCell', rowid, 'id');
        }
        if (currencyDemonstrationId == null) {
            currencyDemonstrationId = $('#gFormCurrencyDemonstration input[name = id]').val();
        }
        return currencyDemonstrationId;
    }
    function executePreConditionCurrencyDemonstration() {
        // trim field vales before process.
        trim_form();
        if ($("#gFormCurrencyDemonstration").validate().form({onfocusout: false}) == false) {
            return false;
        }
        return true;
    }
    function executeAjaxCurrencyDemonstration() {
        if (!executePreConditionCurrencyDemonstration()) {
            return false;
        }
        var actionUrl = null;
        if ($('#gFormCurrencyDemonstration input[name = id]').val()) {
            actionUrl = "${request.contextPath}/${params.controller}/update";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/save";
        }
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: jQuery("#gFormCurrencyDemonstration").serialize(),
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionCurrencyDemonstration(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-grid-currencyDemonstration").trigger("reloadGrid");
                    reset_form('#gFormCurrencyDemonstration');
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
    function executePostConditionCurrencyDemonstration(result) {
        if (result.type == 1) {
            $("#jqgrid-grid-currencyDemonstration").trigger("reloadGrid");
            reset_form('#gFormCurrencyDemonstration');
        }
        MessageRenderer.render(result);
    }
    function deleteAjaxCurrencyDemonstration() {    // Delete record
        var currencyDemonstrationId = getSelectedCurrencyDemonstrationId();
        if (executePreConditionForDeleteCurrencyDemonstration(currencyDemonstrationId)) {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-currencyDemonstration").dialog({
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
                            data: jQuery("#gFormCurrencyDemonstration").serialize(),
                            url: "${resource(dir:'currencyDemonstration', file:'delete')}",
                            success: function (message) {
                                executePostConditionForDeleteCurrencyDemonstration(message);
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

    function executePreConditionForEditCurrencyDemonstration(currencyDemonstrationId) {
        if (currencyDemonstrationId == null) {
            alert("Please select a currencyDemonstration to edit");
            return false;
        }
        return true;
    }
    function executeEditCurrencyDemonstration() {
        var currencyDemonstrationId = getSelectedCurrencyDemonstrationId();
        if (executePreConditionForEditCurrencyDemonstration(currencyDemonstrationId)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'currencyDemonstration', file:'edit')}?id=" + currencyDemonstrationId,
                success: function (entity) {
                    executePostConditionForEditCurrencyDemonstration(entity);
                },
                dataType: 'json'
            });
        }
    }
    function executePostConditionForEditCurrencyDemonstration(data) {
        if (data == null) {
            alert('Selected currencyDemonstration might have been deleted by someone');  //Message renderer
        } else {
            showCurrencyDemonstration(data);
        }
    }
    function executePreConditionForDeleteCurrencyDemonstration(currencyDemonstrationId) {
        if (currencyDemonstrationId == null) {
            alert("Please select a currencyDemonstration to delete");
            return false;
        }
        return true;
    }
    function executePostConditionForDeleteCurrencyDemonstration(data) {
        if (data.type == 1) {
            $("#jqgrid-grid-currencyDemonstration").trigger("reloadGrid");
            reset_form('#gFormCurrencyDemonstration');
        }
        MessageRenderer.render(data)
    }
    function showCurrencyDemonstration(entity) {
        $('#gFormCurrencyDemonstration input[name = id]').val(entity.id);
        $('#gFormCurrencyDemonstration input[name = version]').val(entity.version);

        $('#dateUpdated').val(entity.dateUpdated);
        $('#isActive').attr('checked', entity.isActive);
        if (entity.localCurrency) {
            $('#localCurrency').val(entity.localCurrency.id).attr("selected", "selected");
        }
        else {
            $('#localCurrency').val(entity.localCurrency);
        }
        $('#noteName').val(entity.noteName);
        $('#value').val(entity.value);
        $('#create-button-currencyDemonstration').attr('value', 'Update');
        $('#delete-button-currencyDemonstration').show();
    }
    function executeSearchCurrencyDemonstration(fieldName, fieldValue) {
        if (executePreConditionForSearchCurrencyDemonstration(fieldName, fieldValue)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'currencyDemonstration', file:'search')}?fieldName=" + fieldName + "&fieldValue=" + fieldValue,
                success: function (entity) {
                    executePostConditionForSearchCurrencyDemonstration(entity, fieldName, fieldValue);
                },
                dataType: 'json'
            });
        }
    }
    function executePreConditionForSearchCurrencyDemonstration(fieldName, fieldValue) {
        // trim field vales before process.
        $('#' + fieldName).val($.trim($('#' + fieldName).val()));
        if (fieldValue == '' || fieldValue == null) {
            reset_form("#gFormCurrencyDemonstration");
            return false;
        }
        return true;
    }
    function executePostConditionForSearchCurrencyDemonstration(data, fieldName, fieldValue) {
        if (data == null) {
            reset_form("#gFormCurrencyDemonstration"); // Clear Form
            $('#' + fieldName).val(fieldValue); // Set search field with fieldValue
        } else {
            showCurrencyDemonstration(data);
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
</script>