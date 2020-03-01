<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        $('#ui-widget-header-text').html('DistributionPointTerritorySubArea')
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormDistributionPointTerritorySubArea").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormDistributionPointTerritorySubArea").validationEngine('attach');
        reset_form("#gFormDistributionPointTerritorySubArea");
        $("#jqgrid-grid").jqGrid({
            url: '${resource(dir:'distributionPointTerritorySubArea', file:'list')}',
            datatype: "json",
            colNames: [
                'SL',
                'ID',

                'Distribution Point',
                'Territory Sub Area',
                'User Created',
                'User Updated',
                'Date Created',
                'Date Updated'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30, sortable: false, align: 'left'},
                {name: 'id', index: 'id', width: 0, hidden: true},

                {name: 'distributionPoint', index: 'distributionPoint', width: 100, align: 'left'},
                {name: 'territorySubArea', index: 'territorySubArea', width: 100, align: 'left'},
                {name: 'userCreated', index: 'userCreated', width: 100, align: 'left'},
                {name: 'userUpdated', index: 'userUpdated', width: 100, align: 'left'},
                {name: 'dateCreated', index: 'dateCreated', width: 100, align: 'left'},
                {name: 'dateUpdated', index: 'dateUpdated', width: 100, align: 'left'}
            ],
            rowNum: 50,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#jqgrid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "All DistributionPointTerritorySubArea Information",
            autowidth: true,
            height: 350,
            scrollOffset: 0,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                executeEditDistributionPointTerritorySubArea();
            }
        });
        $("#jqgrid-grid").jqGrid('navGrid', '#jqgrid-pager', {edit: false, add: false, del: false, search: false});
        $("#jqgrid-grid").gridResize({minWidth: 350, maxWidth: 800, minHeight: 200});
        $(".ui-pg-selbox").children().each(function () {
            if ($(this).val() == -1) {
                $(this).html('All')
            }

        });
    });
    function getSelectedDistributionPointTerritorySubAreaId() {
        var distributionPointTerritorySubAreaId = null;
        var rowid = $("#jqgrid-grid").jqGrid('getGridParam', 'selrow');
        if (rowid) {
            distributionPointTerritorySubAreaId = $("#jqgrid-grid").jqGrid('getCell', rowid, 'id');
        }
        if (distributionPointTerritorySubAreaId == null) {
            distributionPointTerritorySubAreaId = $('#gFormDistributionPointTerritorySubArea input[name = id]').val();
        }
        return distributionPointTerritorySubAreaId;
    }
    function executePreConditionDistributionPointTerritorySubArea() {
        // trim field vales before process.
        trim_form();
        if ($("#gFormDistributionPointTerritorySubArea").validate().form({onfocusout: false}) == false) {
            return false;
        }
        return true;
    }
    function executeAjaxDistributionPointTerritorySubArea() {
        if (!executePreConditionDistributionPointTerritorySubArea()) {
            return false;
        }
        var actionUrl = null;
        if ($('#gFormDistributionPointTerritorySubArea input[name = id]').val()) {
            actionUrl = "${request.contextPath}/${params.controller}/update";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/create";
        }
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: jQuery("#gFormDistributionPointTerritorySubArea").serialize(),
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionDistributionPointTerritorySubArea(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-grid").trigger("reloadGrid");
                    reset_form('#gFormDistributionPointTerritorySubArea');
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
    function executePostConditionDistributionPointTerritorySubArea(result) {
        if (result.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_form('#gFormDistributionPointTerritorySubArea');
        }
        MessageRenderer.render(result);
    }
    function deleteAjaxDistributionPointTerritorySubArea() {    // Delete record
        var distributionPointTerritorySubAreaId = getSelectedDistributionPointTerritorySubAreaId();
        if (executePreConditionForDeleteDistributionPointTerritorySubArea(distributionPointTerritorySubAreaId)) {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-distributionPointTerritorySubArea").dialog({
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
                            data: jQuery("#gFormDistributionPointTerritorySubArea").serialize(),
                            url: "${resource(dir:'distributionPointTerritorySubArea', file:'delete')}",
                            success: function (message) {
                                executePostConditionForDeleteDistributionPointTerritorySubArea(message);
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

    function executePreConditionForEditDistributionPointTerritorySubArea(distributionPointTerritorySubAreaId) {
        if (distributionPointTerritorySubAreaId == null) {
            alert("Please select a distributionPointTerritorySubArea to edit");
            return false;
        }
        return true;
    }
    function executeEditDistributionPointTerritorySubArea() {
        var distributionPointTerritorySubAreaId = getSelectedDistributionPointTerritorySubAreaId();
        if (executePreConditionForEditDistributionPointTerritorySubArea(distributionPointTerritorySubAreaId)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'distributionPointTerritorySubArea', file:'edit')}?id=" + distributionPointTerritorySubAreaId,
                success: function (entity) {
                    executePostConditionForEditDistributionPointTerritorySubArea(entity);
                },
                dataType: 'json'
            });
        }
    }
    function executePostConditionForEditDistributionPointTerritorySubArea(data) {
        if (data == null) {
            alert('Selected distributionPointTerritorySubArea might have been deleted by someone');  //Message renderer
        } else {
            showDistributionPointTerritorySubArea(data);
        }
    }
    function executePreConditionForDeleteDistributionPointTerritorySubArea(distributionPointTerritorySubAreaId) {
        if (distributionPointTerritorySubAreaId == null) {
            alert("Please select a distributionPointTerritorySubArea to delete");
            return false;
        }
        return true;
    }
    function executePostConditionForDeleteDistributionPointTerritorySubArea(data) {
        if (data.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_form('#gFormDistributionPointTerritorySubArea');
        }
        MessageRenderer.render(data)
    }
    function showDistributionPointTerritorySubArea(entity) {
        $('#gFormDistributionPointTerritorySubArea input[name = id]').val(entity.id);
        $('#gFormDistributionPointTerritorySubArea input[name = version]').val(entity.version);

        if (entity.distributionPoint) {
            $('#distributionPoint').val(entity.distributionPoint.id).attr("selected", "selected");
        }
        else {
            $('#distributionPoint').val(entity.distributionPoint);
        }
        if (entity.territorySubArea) {
            $('#territorySubArea').val(entity.territorySubArea.id).attr("selected", "selected");
        }
        else {
            $('#territorySubArea').val(entity.territorySubArea);
        }
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
    function executeSearchDistributionPointTerritorySubArea(fieldName, fieldValue) {
        if (executePreConditionForSearchDistributionPointTerritorySubArea(fieldName, fieldValue)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'distributionPointTerritorySubArea', file:'search')}?fieldName=" + fieldName + "&fieldValue=" + fieldValue,
                success: function (entity) {
                    executePostConditionForSearchDistributionPointTerritorySubArea(entity, fieldName, fieldValue);
                },
                dataType: 'json'
            });
        }
    }
    function executePreConditionForSearchDistributionPointTerritorySubArea(fieldName, fieldValue) {
        // trim field vales before process.
        $('#' + fieldName).val($.trim($('#' + fieldName).val()));
        if (fieldValue == '' || fieldValue == null) {
            reset_form("#gFormDistributionPointTerritorySubArea");
            return false;
        }
        return true;
    }
    function executePostConditionForSearchDistributionPointTerritorySubArea(data, fieldName, fieldValue) {
        if (data == null) {
            reset_form("#gFormDistributionPointTerritorySubArea"); // Clear Form
            $('#' + fieldName).val(fieldValue); // Set search field with fieldValue
        } else {
            showDistributionPointTerritorySubArea(data);
        }
    }
</script>