<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        $('#ui-widget-header-text').html('Nationality')
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormNationality").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormNationality").validationEngine('attach');
        reset_form("#gFormNationality");
        $("#jqgrid-grid").jqGrid({
            url: '${resource(dir:'nationality', file:'list')}',
            datatype: "json",
            colNames: [
                'SL',
                'ID',

                'Name',
                'Note'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30, sortable: false, align: 'left'},
                {name: 'id', index: 'id', width: 0, hidden: true},

                {name: 'name', index: 'name', width: 100, align: 'left'},
                {name: 'note', index: 'note', width: 100, align: 'left'}
            ],
            rowNum: 50,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#jqgrid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "All Nationality Information",
            autowidth: true,
            height: 350,
            scrollOffset: 0,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                executeEditNationality();
            }
        });
        $("#jqgrid-grid").jqGrid('navGrid', '#jqgrid-pager', {edit: false, add: false, del: false, search: false});
//        $("#jqgrid-grid").gridResize({minWidth: 350, maxWidth: 800, minHeight: 200});
        $(".ui-pg-selbox").children().each(function () {
            if ($(this).val() == -1) {
                $(this).html('All')
            }

        });
    });
    function getSelectedNationalityId() {
        var nationalityId = null;
        var rowid = $("#jqgrid-grid").jqGrid('getGridParam', 'selrow');
        if (rowid) {
            nationalityId = $("#jqgrid-grid").jqGrid('getCell', rowid, 'id');
        }
        if (nationalityId == null) {
            nationalityId = $('#gFormNationality input[name = id]').val();
        }
        return nationalityId;
    }
    function executePreConditionNationality() {
        // trim field vales before process.
        trim_form();
        if ($("#gFormNationality").validate().form({onfocusout: false}) == false) {
            return false;
        }
        return true;
    }
    function executeAjaxNationality() {
        if (!executePreConditionNationality()) {
            return false;
        }
        var actionUrl = null;
        if ($('#gFormNationality input[name = id]').val()) {
            actionUrl = "${request.contextPath}/${params.controller}/update";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/create";
        }
        jQuery.ajax({
            type: 'post',
            data: jQuery("#gFormNationality").serialize(),
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionNationality(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                }else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete: function () {
            },
            dataType: 'json'
        });
        return false;
    }
    function executePostConditionNationality(result) {
        if (result.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_form('#gFormNationality');
        }
        MessageRenderer.render(result);
    }
    function deleteAjaxNationality() {    // Delete record
        var nationalityId = getSelectedNationalityId();
        if (executePreConditionForDeleteNationality(nationalityId)) {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-nationality").dialog({
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
                            data: jQuery("#gFormNationality").serialize(),
                            url: "${resource(dir:'nationality', file:'delete')}",
                            success: function (message) {
                                executePostConditionForDeleteNationality(message);
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

    function executePreConditionForEditNationality(nationalityId) {
        if (nationalityId == null) {
            alert("Please select a nationality to edit");
            return false;
        }
        return true;
    }
    function executeEditNationality() {
        var nationalityId = getSelectedNationalityId();
        if (executePreConditionForEditNationality(nationalityId)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'nationality', file:'edit')}?id=" + nationalityId,
                success: function (entity) {
                    executePostConditionForEditNationality(entity);
                },
                dataType: 'json'
            });
        }
    }
    function executePostConditionForEditNationality(data) {
        if (data == null) {
            alert('Selected nationality might have been deleted by someone');  //Message renderer
        } else {
            showNationality(data);
        }
    }
    function executePreConditionForDeleteNationality(nationalityId) {
        if (nationalityId == null) {
            alert("Please select a nationality to delete");
            return false;
        }
        return true;
    }
    function executePostConditionForDeleteNationality(data) {
        if (data.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_form('#gFormNationality');
        }
        MessageRenderer.render(data)
    }
    function showNationality(entity) {
        $('#gFormNationality input[name = id]').val(entity.id);
        $('#gFormNationality input[name = version]').val(entity.version);

        $('#name').val(entity.name);
        $('#note').val(entity.note);
        $('#create-button').attr('value', 'Update');
        $('#cancel-button').attr('value', 'Cancel');
        $('#delete-button').show();
    }
    function executeSearchNationality(fieldName, fieldValue) {
        if (executePreConditionForSearchNationality(fieldName, fieldValue)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'nationality', file:'search')}?fieldName=" + fieldName + "&fieldValue=" + fieldValue,
                success: function (entity) {
                    executePostConditionForSearchNationality(entity, fieldName, fieldValue);
                },
                dataType: 'json'
            });
        }
    }
    function executePreConditionForSearchNationality(fieldName, fieldValue) {
        // trim field vales before process.
        $('#' + fieldName).val($.trim($('#' + fieldName).val()));
        if (fieldValue == '' || fieldValue == null) {
            reset_form("#gFormNationality");
            return false;
        }
        return true;
    }
    function executePostConditionForSearchNationality(data, fieldName, fieldValue) {
        if (data == null) {
            reset_form("#gFormNationality"); // Clear Form
            $('#' + fieldName).val(fieldValue); // Set search field with fieldValue
        } else {
            showNationality(data);
        }
    }
</script>