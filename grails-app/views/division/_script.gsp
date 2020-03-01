<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        $('#ui-widget-header-text').html('Division')
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormDivision").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormDivision").validationEngine('attach');
        reset_form("#gFormDivision");
        $("#jqgrid-grid").jqGrid({
            url: '${resource(dir:'division', file:'list')}',
            datatype: "json",
            colNames: [
                'SL',
                'ID',

                'Geo Code',
                'Division Name'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30, sortable: false, align: 'left'},
                {name: 'id', index: 'id', width: 0, hidden: true},

                {name: 'geoCode', index: 'geoCode', width: 100, align: 'left'},
                {name: 'name', index: 'name', width: 100, align: 'left'}
            ],
            rowNum: 50,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#jqgrid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "asc",
            caption: "Division List",
            autowidth: true,
            height: true,
            scrollOffset: 0,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                executeEditDivision();
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
    function getSelectedDivisionId() {
        var divisionId = null;
        var rowid = $("#jqgrid-grid").jqGrid('getGridParam', 'selrow');
        if (rowid) {
            divisionId = $("#jqgrid-grid").jqGrid('getCell', rowid, 'id');
        }
        if (divisionId == null) {
            divisionId = $('#gFormDivision input[name = id]').val();
        }
        return divisionId;
    }
    function executePreConditionDivision() {
        // trim field vales before process.
        trim_form();
        if (!$("#gFormDivision").validationEngine('validate')) {
//        if ($("#gFormDivision").validate().form({onfocusout: false}) == false) {
            return false;
        }
        return true;
    }
    function executeAjaxDivision() {
        if (!executePreConditionDivision()) {
            return false;
        }
        var actionUrl = null;
        if ($('#gFormDivision input[name = id]').val()) {
            actionUrl = "${request.contextPath}/${params.controller}/update";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/create";
        }
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: jQuery("#gFormDivision").serialize(),
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionDivision(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-grid").trigger("reloadGrid");
                    $("#geoCode").val("");
                    $("#name").val("");
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
    function executePostConditionDivision(result) {
        if (result.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            $("#geoCode").val("");
            $("#name").val("");
//            reset_form('#gFormDivision');
        }
        MessageRenderer.render(result);
    }
    function deleteAjaxDivision() {    // Delete record
        var divisionId = getSelectedDivisionId();
        if (executePreConditionForDeleteDivision(divisionId)) {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-division").dialog({
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
                            data: jQuery("#gFormDivision").serialize(),
                            url: "${resource(dir:'division', file:'delete')}",
                            success: function (message) {
                                executePostConditionForDeleteDivision(message);
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

    function executePreConditionForEditDivision(divisionId) {
        if (divisionId == null) {
            alert("Please select a division to edit");
            return false;
        }
        return true;
    }
    function executeEditDivision() {
        var divisionId = getSelectedDivisionId();
        if (executePreConditionForEditDivision(divisionId)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'division', file:'edit')}?id=" + divisionId,
                success: function (entity) {
                    executePostConditionForEditDivision(entity);
                },
                dataType: 'json'
            });
        }
    }
    function executePostConditionForEditDivision(data) {
        if (data == null) {
            alert('Selected division might have been deleted by someone');  //Message renderer
        } else {
            showDivision(data);
        }
    }
    function executePreConditionForDeleteDivision(divisionId) {
        if (divisionId == null) {
            alert("Please select a division to delete");
            return false;
        }
        return true;
    }
    function executePostConditionForDeleteDivision(data) {
        if (data.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_form('#gFormDivision');
        }
        MessageRenderer.render(data)
    }
    function showDivision(entity) {
        $('#gFormDivision input[name = id]').val(entity.id);
        $('#gFormDivision input[name = version]').val(entity.version);

        $('#geoCode').val(entity.geoCode);
        $('#name').val(entity.name);
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
        if (entity.countryInfo) {
            $('#countryInfo').val(entity.countryInfo.id).attr("selected", "selected");
        }
        else {
            $('#countryInfo').val(entity.countryInfo);
        }
        $('#dateCreated').val(entity.dateCreated);
        $('#lastUpdated').val(entity.lastUpdated);
        $('#create-button').attr('value', 'Update');
        $('#cancel-button').attr('value', 'Cancel');
        $('#delete-button').show();
    }
    function executeSearchDivision(fieldName, fieldValue) {
        if (executePreConditionForSearchDivision(fieldName, fieldValue)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'division', file:'search')}?fieldName=" + fieldName + "&fieldValue=" + fieldValue,
                success: function (entity) {
                    executePostConditionForSearchDivision(entity, fieldName, fieldValue);
                },
                dataType: 'json'
            });
        }
    }
    function executePreConditionForSearchDivision(fieldName, fieldValue) {
        // trim field vales before process.
        $('#' + fieldName).val($.trim($('#' + fieldName).val()));
        if (fieldValue == '' || fieldValue == null) {
            reset_form("#gFormDivision");
            return false;
        }
        return true;
    }
    function executePostConditionForSearchDivision(data, fieldName, fieldValue) {
        if (data == null) {
            reset_form("#gFormDivision"); // Clear Form
            $('#' + fieldName).val(fieldValue); // Set search field with fieldValue
        } else {
            showDivision(data);
        }
    }
</script>