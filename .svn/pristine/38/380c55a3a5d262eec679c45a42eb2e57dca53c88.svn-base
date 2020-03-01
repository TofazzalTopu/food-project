<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        $('#ui-widget-header-text').html('Designation')
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormDesignation").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormDesignation").validationEngine('attach');
        reset_form("#gFormDesignation");
        $("#jqgrid-grid").jqGrid({
            url: '${resource(dir:'designation', file:'list')}',
            datatype: "json",
            colNames: [
                'SL',
                'ID',

                'Code',
                'Name',
                'Note'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30, sortable: false, align: 'left'},
                {name: 'id', index: 'id', width: 0, hidden: true},

                {name: 'code', index: 'code', width: 100, align: 'left'},
                {name: 'name', index: 'name', width: 100, align: 'left'},
                {name: 'note', index: 'note', width: 100, align: 'left'}
            ],
            rowNum: 50,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#jqgrid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "All Designation Information",
            autowidth: true,
            height: 350,
            scrollOffset: 0,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                executeEditDesignation();
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
    function getSelectedDesignationId() {
        var designationId = null;
        var rowid = $("#jqgrid-grid").jqGrid('getGridParam', 'selrow');
        if (rowid) {
            designationId = $("#jqgrid-grid").jqGrid('getCell', rowid, 'id');
        }
        if (designationId == null) {
            designationId = $('#gFormDesignation input[name = id]').val();
        }
        return designationId;
    }
    function executePreConditionDesignation() {
        // trim field vales before process.
        trim_form();
        if ($("#gFormDesignation").validate().form({onfocusout: false}) == false) {
            return false;
        }
        return true;
    }
    function executeAjaxDesignation() {
        if (!executePreConditionDesignation()) {
            return false;
        }
        var actionUrl = null;
        if ($('#gFormDesignation input[name = id]').val()) {
            actionUrl = "${request.contextPath}/${params.controller}/update";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/create";
        }
        jQuery.ajax({
            type: 'post',
            data: jQuery("#gFormDesignation").serialize(),
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionDesignation(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-grid").trigger("reloadGrid");
                    reset_form('#gFormDesignation');
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                }
                else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete: function () {
            },
            dataType: 'json'
        });
        return false;
    }
    function executePostConditionDesignation(result) {
        if (result.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_form('#gFormDesignation');
        }
        MessageRenderer.render(result);
    }
    function deleteAjaxDesignation() {    // Delete record
        var designationId = getSelectedDesignationId();
        if (executePreConditionForDeleteDesignation(designationId)) {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-designation").dialog({
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
                            data: jQuery("#gFormDesignation").serialize(),
                            url: "${resource(dir:'designation', file:'delete')}",
                            success: function (message) {
                                executePostConditionForDeleteDesignation(message);
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

    function executePreConditionForEditDesignation(designationId) {
        if (designationId == null) {
            alert("Please select a designation to edit");
            return false;
        }
        return true;
    }
    function executeEditDesignation() {
        var designationId = getSelectedDesignationId();
        if (executePreConditionForEditDesignation(designationId)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'designation', file:'edit')}?id=" + designationId,
                success: function (entity) {
                    executePostConditionForEditDesignation(entity);
                },
                dataType: 'json'
            });
        }
    }
    function executePostConditionForEditDesignation(data) {
        if (data == null) {
            alert('Selected designation might have been deleted by someone');  //Message renderer
        } else {
            showDesignation(data);
        }
    }
    function executePreConditionForDeleteDesignation(designationId) {
        if (designationId == null) {
            alert("Please select a designation to delete");
            return false;
        }
        return true;
    }
    function executePostConditionForDeleteDesignation(data) {
        if (data.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_form('#gFormDesignation');
        }
        MessageRenderer.render(data)
    }
    function showDesignation(entity) {
        $('#gFormDesignation input[name = id]').val(entity.id);
        $('#gFormDesignation input[name = version]').val(entity.version);

        $('#code').val(entity.code);
        $('#name').val(entity.name);
        $('#note').val(entity.note);
        if (entity.userCreated) {
            $('#userCreated').val(entity.userCreated.id).attr("selected", "selected");
        }
        else {
            $('#userCreated').val(entity.userCreated);
        }
        if (entity.userUpdated) {
            $('#userUpdated').val(entity.userUpdated.id).attr("selected", "selected");
        }
        else {
            $('#userUpdated').val(entity.userUpdated);
        }
        $('#dateCreated').val(entity.dateCreated);
        $('#dateUpdated').val(entity.dateUpdated);
        $('#create-button').attr('value', 'Update');
        $('#cancel-button').attr('value', 'Cancel');
        $('#delete-button').show();
    }
    function executeSearchDesignation(fieldName, fieldValue) {
        if (executePreConditionForSearchDesignation(fieldName, fieldValue)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'designation', file:'search')}?fieldName=" + fieldName + "&fieldValue=" + fieldValue,
                success: function (entity) {
                    executePostConditionForSearchDesignation(entity, fieldName, fieldValue);
                },
                dataType: 'json'
            });
        }
    }
    function executePreConditionForSearchDesignation(fieldName, fieldValue) {
        // trim field vales before process.
        $('#' + fieldName).val($.trim($('#' + fieldName).val()));
        if (fieldValue == '' || fieldValue == null) {
            reset_form("#gFormDesignation");
            return false;
        }
        return true;
    }
    function executePostConditionForSearchDesignation(data, fieldName, fieldValue) {
        if (data == null) {
            reset_form("#gFormDesignation"); // Clear Form
            $('#' + fieldName).val(fieldValue); // Set search field with fieldValue
        } else {
            showDesignation(data);
        }
    }
</script>