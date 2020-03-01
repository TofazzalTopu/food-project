<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        $('#ui-widget-header-text').html('DistributionPointWarehouse')
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormDistributionPointWarehouse").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormDistributionPointWarehouse").validationEngine('attach');
        reset_form("#gFormDistributionPointWarehouse");
        $("#jqgrid-grid").jqGrid({
            url: '${resource(dir:'distributionPointWarehouse', file:'list')}',
            datatype: "json",
            colNames: [
                'SL',
                'ID',

                'Distribution Point',
                'Warehouse'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30, sortable: false, align: 'left'},
                {name: 'id', index: 'id', width: 0, hidden: true},

                {name: 'distributionPoint', index: 'distributionPoint', width: 100, align: 'left'},
                {name: 'warehouse', index: 'warehouse', width: 100, align: 'left'}
            ],
            rowNum: 50,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#jqgrid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "All DistributionPointWarehouse Information",
            autowidth: true,
            height: 350,
            scrollOffset: 0,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                executeEditDistributionPointWarehouse();
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
    function getSelectedDistributionPointWarehouseId() {
        var distributionPointWarehouseId = null;
        var rowid = $("#jqgrid-grid").jqGrid('getGridParam', 'selrow');
        if (rowid) {
            distributionPointWarehouseId = $("#jqgrid-grid").jqGrid('getCell', rowid, 'id');
        }
        if (distributionPointWarehouseId == null) {
            distributionPointWarehouseId = $('#gFormDistributionPointWarehouse input[name = id]').val();
        }
        return distributionPointWarehouseId;
    }
    function executePreConditionDistributionPointWarehouse() {
        // trim field vales before process.
        trim_form();
        if ($("#gFormDistributionPointWarehouse").validate().form({onfocusout: false}) == false) {
            return false;
        }
        return true;
    }
    function executeAjaxDistributionPointWarehouse() {
        if (!executePreConditionDistributionPointWarehouse()) {
            return false;
        }
        var actionUrl = null;
        if ($('#gFormDistributionPointWarehouse input[name = id]').val()) {
            actionUrl = "${request.contextPath}/${params.controller}/update";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/create";
        }
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: jQuery("#gFormDistributionPointWarehouse").serialize(),
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionDistributionPointWarehouse(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-grid").trigger("reloadGrid");
                    reset_form('#gFormDistributionPointWarehouse');
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
    function executePostConditionDistributionPointWarehouse(result) {
        if (result.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_form('#gFormDistributionPointWarehouse');
        }
        MessageRenderer.render(result);
    }
    function deleteAjaxDistributionPointWarehouse() {    // Delete record
        var distributionPointWarehouseId = getSelectedDistributionPointWarehouseId();
        if (executePreConditionForDeleteDistributionPointWarehouse(distributionPointWarehouseId)) {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-distributionPointWarehouse").dialog({
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
                            data: jQuery("#gFormDistributionPointWarehouse").serialize(),
                            url: "${resource(dir:'distributionPointWarehouse', file:'delete')}",
                            success: function (message) {
                                executePostConditionForDeleteDistributionPointWarehouse(message);
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

    function executePreConditionForEditDistributionPointWarehouse(distributionPointWarehouseId) {
        if (distributionPointWarehouseId == null) {
            alert("Please select a distributionPointWarehouse to edit");
            return false;
        }
        return true;
    }
    function executeEditDistributionPointWarehouse() {
        var distributionPointWarehouseId = getSelectedDistributionPointWarehouseId();
        if (executePreConditionForEditDistributionPointWarehouse(distributionPointWarehouseId)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'distributionPointWarehouse', file:'edit')}?id=" + distributionPointWarehouseId,
                success: function (entity) {
                    executePostConditionForEditDistributionPointWarehouse(entity);
                },
                dataType: 'json'
            });
        }
    }
    function executePostConditionForEditDistributionPointWarehouse(data) {
        if (data == null) {
            alert('Selected distributionPointWarehouse might have been deleted by someone');  //Message renderer
        } else {
            showDistributionPointWarehouse(data);
        }
    }
    function executePreConditionForDeleteDistributionPointWarehouse(distributionPointWarehouseId) {
        if (distributionPointWarehouseId == null) {
            alert("Please select a distributionPointWarehouse to delete");
            return false;
        }
        return true;
    }
    function executePostConditionForDeleteDistributionPointWarehouse(data) {
        if (data.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_form('#gFormDistributionPointWarehouse');
        }
        MessageRenderer.render(data)
    }
    function showDistributionPointWarehouse(entity) {
        $('#gFormDistributionPointWarehouse input[name = id]').val(entity.id);
        $('#gFormDistributionPointWarehouse input[name = version]').val(entity.version);

        if (entity.distributionPoint) {
            $('#distributionPoint').val(entity.distributionPoint.id).attr("selected", "selected");
        }
        else {
            $('#distributionPoint').val(entity.distributionPoint);
        }
        if (entity.warehouse) {
            $('#warehouse').val(entity.warehouse.id).attr("selected", "selected");
        }
        else {
            $('#warehouse').val(entity.warehouse);
        }
        $('#create-button').attr('value', 'Update');
        $('#cancel-button').attr('value', 'Cancel');
        $('#delete-button').show();
    }
    function executeSearchDistributionPointWarehouse(fieldName, fieldValue) {
        if (executePreConditionForSearchDistributionPointWarehouse(fieldName, fieldValue)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'distributionPointWarehouse', file:'search')}?fieldName=" + fieldName + "&fieldValue=" + fieldValue,
                success: function (entity) {
                    executePostConditionForSearchDistributionPointWarehouse(entity, fieldName, fieldValue);
                },
                dataType: 'json'
            });
        }
    }
    function executePreConditionForSearchDistributionPointWarehouse(fieldName, fieldValue) {
        // trim field vales before process.
        $('#' + fieldName).val($.trim($('#' + fieldName).val()));
        if (fieldValue == '' || fieldValue == null) {
            reset_form("#gFormDistributionPointWarehouse");
            return false;
        }
        return true;
    }
    function executePostConditionForSearchDistributionPointWarehouse(data, fieldName, fieldValue) {
        if (data == null) {
            reset_form("#gFormDistributionPointWarehouse"); // Clear Form
            $('#' + fieldName).val(fieldValue); // Set search field with fieldValue
        } else {
            showDistributionPointWarehouse(data);
        }
    }
</script>