<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        $("#dateStart, #dateEnd").datepicker(
                {
                    dateFormat: 'dd-mm-yy',
                    changeMonth: true,
                    changeYear: true
                });
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormFinancialYear").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormFinancialYear").validationEngine('attach');
        reset_form("#gFormFinancialYear");
        $("#jqgrid-grid-financialYear").jqGrid({
            url: '${resource(dir:'financialYear', file:'list')}',
            datatype: "json",
            colNames: [
                'SL',
                'ID',
                'Financial Year',
                'Date Start',
                'Date End',
                'Is Opened',
                'User Created',
                'Date Created',
                'User Last Updated',
                'Date Last Updated'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30, sortable: true, align: 'left'},
                {name: 'id', index: 'id', width: 0, hidden: true},

                {name: 'name', index: 'name', width: 100, align: 'left'}

                ,
                {
                    name: 'dateStart',
                    index: 'dateStart',
                    width: 100,
                    align: 'left',
                    formatter: 'date',
                    formatoptions: {newformat: 'd-m-Y'}
                }

                ,

                {
                    name: 'dateEnd',
                    index: 'dateEnd',
                    width: 100,
                    align: 'left',
                    formatter: 'date',
                    formatoptions: {newformat: 'd-m-Y'}
                }

                ,
                {name: 'isOpened', index: 'isOpened', width: 100, align: 'left'}

                ,

                {name: 'userCreated', index: 'userCreated', width: 100, align: 'left'}

                ,
                {
                    name: 'dateCreated',
                    index: 'dateCreated',
                    width: 100,
                    align: 'left',
                    formatter: 'date',
                    formatoptions: {newformat: 'd-m-Y'}
                }

                ,


                {name: 'userLastUpdated', index: 'userLastUpdated', width: 100, align: 'left'}

                ,


                {
                    name: 'dateLastUpdated',
                    index: 'dateLastUpdated',
                    width: 100,
                    align: 'left',
                    formatter: 'date',
                    formatoptions: {newformat: 'd-m-Y'}
                }


            ],
            rowNum: 10,
            rowList: [10, 20, 30],
            pager: '#jqgrid-pager-financialYear',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "All Financial Year Information",
            autowidth: true,
            autoheight: true,
            scrollOffset: 0,
            altRows: true,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
//                executeEditFinancialYear();
            }
        });
        $("#jqgrid-grid-financialYear").jqGrid('navGrid', '#jqgrid-pager-financialYear', {
            edit: false,
            add: false,
            del: false,
            search: false
        })
                .navButtonAdd('#jqgrid-pager-financialYear', {
                    caption: "Edit",
                    buttonicon: "ui-icon-edit",
                    onClickButton: function () {
                        executeEditFinancialYear();
                    },
                    position: "last"
                })
                .navButtonAdd('#jqgrid-pager-financialYear', {
                    caption: "Delete",
                    buttonicon: "ui-icon-del",
                    onClickButton: function () {
                        deleteAjaxFinancialYear();
                    },
                    position: "last"
                });
    });
    function getSelectedFinancialYearId() {
        var financialYearId = null;
        var rowid = $("#jqgrid-grid-financialYear").jqGrid('getGridParam', 'selrow');
        if (rowid) {
            financialYearId = $("#jqgrid-grid-financialYear").jqGrid('getCell', rowid, 'id');
        }
        if (financialYearId == null) {
            financialYearId = $('#gFormFinancialYear input[name = id]').val();
        }
        return financialYearId;
    }
    function executePreConditionFinancialYear() {
        // trim field vales before process.
        trim_form();
        if ($("#gFormFinancialYear").validate().form({onfocusout: false}) == false) {
            return false;
        }
        return true;
    }
    function executeAjaxFinancialYear() {
        if (!executePreConditionFinancialYear()) {
            return false;
        }
        var actionUrl = null;
        if ($('#gFormFinancialYear input[name = id]').val()) {
            actionUrl = "${request.contextPath}/${params.controller}/update";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/save";
        }
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: jQuery("#gFormFinancialYear").serialize(),
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionFinancialYear(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-grid-financialYear").trigger("reloadGrid");
                    reset_form('#gFormFinancialYear');
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                }  else{
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
    function executePostConditionFinancialYear(result) {
        if (result.type == 1) {
            $("#jqgrid-grid-financialYear").trigger("reloadGrid");
            reset_form('#gFormFinancialYear');
        }
        MessageRenderer.render(result);
    }
    function deleteAjaxFinancialYear() {    // Delete record
        var financialYearId = getSelectedFinancialYearId();
        if (executePreConditionForDeleteFinancialYear(financialYearId)) {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-financialYear").dialog({
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
                            data: jQuery("#gFormFinancialYear").serialize(),
                            url: "${resource(dir:'financialYear', file:'delete')}",
                            success: function (message) {
                                executePostConditionForDeleteFinancialYear(message);
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

    function executePreConditionForEditFinancialYear(financialYearId) {
        if (financialYearId == null) {
            alert("Please select a financialYear to edit");
            return false;
        }
        return true;
    }
    function executeEditFinancialYear() {
        var financialYearId = getSelectedFinancialYearId();
        if (executePreConditionForEditFinancialYear(financialYearId)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'financialYear', file:'edit')}?id=" + financialYearId,
                success: function (entity) {
                    executePostConditionForEditFinancialYear(entity);
                },
                dataType: 'json'
            });
        }
    }
    function executePostConditionForEditFinancialYear(data) {
        if (data == null) {
            alert('Selected financialYear might have been deleted by someone');  //Message renderer
        } else {
            showFinancialYear(data);
        }
    }
    function executePreConditionForDeleteFinancialYear(financialYearId) {
        if (financialYearId == null) {
            alert("Please select a financialYear to delete");
            return false;
        }
        return true;
    }
    function executePostConditionForDeleteFinancialYear(data) {
        if (data.type == 1) {
            $("#jqgrid-grid-financialYear").trigger("reloadGrid");
            reset_form('#gFormFinancialYear');
        }
        MessageRenderer.render(data)
    }
    function showFinancialYear(entity) {
        $('#gFormFinancialYear input[name = id]').val(entity.id);
        $('#gFormFinancialYear input[name = version]').val(entity.version);
        $('#name').val(entity.name);
        $('#dateEnd').val(entity.dateEnd);
        $('#dateLastUpdated').val(entity.dateLastUpdated);
        $('#dateStart').val(entity.dateStart);
        $('#isOpened').attr('checked', entity.isOpened);
        if (entity.userLastUpdated) {
            $('#userLastUpdated').val(entity.userLastUpdated.id).attr("selected", "selected");
        }
        else {
            $('#userLastUpdated').val(entity.userLastUpdated);
        }
        $('#create-button-financialYear').attr('value', 'Update');
        $('#delete-button-financialYear').show();
    }
    function executeSearchFinancialYear(fieldName, fieldValue) {
        if (executePreConditionForSearchFinancialYear(fieldName, fieldValue)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'financialYear', file:'search')}?fieldName=" + fieldName + "&fieldValue=" + fieldValue,
                success: function (entity) {
                    executePostConditionForSearchFinancialYear(entity, fieldName, fieldValue);
                },
                dataType: 'json'
            });
        }
    }
    function executePreConditionForSearchFinancialYear(fieldName, fieldValue) {
        // trim field vales before process.
        $('#' + fieldName).val($.trim($('#' + fieldName).val()));
        if (fieldValue == '' || fieldValue == null) {
            reset_form("#gFormFinancialYear");
            return false;
        }
        return true;
    }
    function executePostConditionForSearchFinancialYear(data, fieldName, fieldValue) {
        if (data == null) {
            reset_form("#gFormFinancialYear"); // Clear Form
            $('#' + fieldName).val(fieldValue); // Set search field with fieldValue
        } else {
            showFinancialYear(data);
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
        $(formName + ' input[name = create-button]').attr('value', 'Open');
        $(formName + ' input[name = delete-button]').hide();
    }

    function trim_form() {
        $(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
            this.value = $.trim(this.value);
        });
    }
</script>