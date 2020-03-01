<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        $('#ui-widget-header-text').html('SubWarehouse')
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }
        $("#warehouseList").flexbox('Select Warehouse', {
            watermark: "Select Inventory",
            width: 260,
            onSelect: function () {
                $("#warehouse").val($('#warehouseList_hidden').val());
//                        loadBusinessUnit($('#enterpriselist_hidden').val());
            }
        });
        $('#warehouseList_input').blur(function () {
            if ($('#warehouseList_input').val() == '') {
                $("#warehouseList").val("");
            }
        });

        $("#gFormSubWarehouse").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormSubWarehouse").validationEngine('attach');
        reset_SubWarehouse_form("#gFormSubWarehouse");
        $("#jqgrid-grid").jqGrid({
            url: '${resource(dir:'subWarehouse', file:'list')}',
            datatype: "json",
            colNames: [
                'SL',
                'ID',

                'Inventory',
                'Sub Inventory Type',
                'Sub Inventory Name'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30, sortable: false, align: 'left'},
                {name: 'id', index: 'id', width: 0, hidden: true},

                {name: 'warehouse', index: 'warehouse', width: 100, align: 'left'},
                {name: 'subWarehouseType', index: 'subWarehouseType', width: 100, align: 'left'},
                {name: 'name', index: 'name', width: 100, align: 'left'}
            ],
            rowNum: 50,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#jqgrid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Sub Inventory List",
            autowidth: true,
            height: true,
            scrollOffset: 0,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                executeEditSubWarehouse();
            }
        });
        $("#jqgrid-grid").jqGrid('navGrid', '#jqgrid-pager', {edit: false, add: false, del: false, search: false});
//     $("#jqgrid-grid").gridResize({minWidth:350,maxWidth:800,minHeight:200});
        $(".ui-pg-selbox").children().each(function () {
            if ($(this).val() == -1) {
                $(this).html('All')
            }

        });
    });
    function getSelectedSubWarehouseId() {
        var subWarehouseId = null;
        var rowid = $("#jqgrid-grid").jqGrid('getGridParam', 'selrow');
        if (rowid) {
            subWarehouseId = $("#jqgrid-grid").jqGrid('getCell', rowid, 'id');
        }
        if (subWarehouseId == null) {
            subWarehouseId = $('#gFormSubWarehouse input[name = id]').val();
        }
        return subWarehouseId;
    }
    function executePreConditionSubWarehouse() {
        // trim field vales before process.
        trim_form();
        if (!$("#gFormSubWarehouse").validationEngine('validate')) {
            return false;
        }
        return true;
    }
    function executeAjaxSubWarehouse() {

        if (!executePreConditionSubWarehouse()) {
            return false;
        }
        var actionUrl = null;
        if ($('#gFormSubWarehouse input[name = id]').val()) {
            actionUrl = "${request.contextPath}/${params.controller}/update";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/create";
        }

        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: jQuery("#gFormSubWarehouse").serialize(),
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionSubWarehouse(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-grid").trigger("reloadGrid");
                    reset_SubWarehouse_form('#gFormSubWarehouse');
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
    function executePostConditionSubWarehouse(result) {
        if (result.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_SubWarehouse_form('#gFormSubWarehouse');
        }
        MessageRenderer.render(result);
    }
    function deleteAjaxSubWarehouse() {    // Delete record
        var subWarehouseId = getSelectedSubWarehouseId();
        if (executePreConditionForDeleteSubWarehouse(subWarehouseId)) {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-subWarehouse").dialog({
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
                            data: jQuery("#gFormSubWarehouse").serialize(),
                            url: "${resource(dir:'subWarehouse', file:'delete')}",
                            success: function (message) {
                                executePostConditionForDeleteSubWarehouse(message);
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

    function executePreConditionForEditSubWarehouse(subWarehouseId) {
        if (subWarehouseId == null) {
            alert("Please select a Sub Inventory to edit");
            return false;
        }
        return true;
    }
    function executeEditSubWarehouse() {
        var subWarehouseId = getSelectedSubWarehouseId();
        if (executePreConditionForEditSubWarehouse(subWarehouseId)) {
            SubmissionLoader.showTo();
            $.ajax({
                type: "POST",
                url: "${resource(dir:'subWarehouse', file:'edit')}?id=" + subWarehouseId,
                success: function (entity) {
                    executePostConditionForEditSubWarehouse(entity);
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    if(XMLHttpRequest.status = 0){
                        MessageRenderer.renderErrorText("Network Problem: Time out");
                    } else{
                        MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                    }
                },
                complete: function(){
                    SubmissionLoader.hideFrom();
                },
                dataType: 'json'
            });
        }
    }
    function executePostConditionForEditSubWarehouse(data) {
        if (data == null) {
            alert('Selected Sub Inventory might have been deleted by someone');  //Message renderer
        } else {
            showSubWarehouse(data);
        }
    }
    function executePreConditionForDeleteSubWarehouse(subWarehouseId) {
        if (subWarehouseId == null) {
            alert("Please select a Sub Inventory to delete");
            return false;
        }
        return true;
    }
    function executePostConditionForDeleteSubWarehouse(data) {
        if (data.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_SubWarehouse_form('#gFormSubWarehouse');
        }
        MessageRenderer.render(data)
    }
    function showSubWarehouse(entity) {
        var subWarehouse = entity.subWarehouse
        var enterpriseConfiguration = entity.enterpriseConfiguration
        var warehouse = entity.warehouse
        $('#gFormSubWarehouse input[name = id]').val(subWarehouse.id);
        $('#gFormSubWarehouse input[name = version]').val(subWarehouse.version);

        if (enterpriseConfiguration) {
            $('#enterpriseSubWareList').setValue(enterpriseConfiguration.name);
            $('#enterpriseConfiguration').val(enterpriseConfiguration.id)
        }

        if (warehouse) {
            $('#warehouseList').setValue(warehouse.name + '[' + warehouse.code + ']');
            $('#warehouse').val(warehouse.id)
        }
        if (subWarehouse.subWarehouseType) {
            $('#subWarehouseType').val(subWarehouse.subWarehouseType.id).attr("selected", "selected");
        }
        else {
            $('#subWarehouseType').val(subWarehouse.subWarehouseType);
        }
        $('#name').val(subWarehouse.name);
        $("#warehouseList_input").attr('disabled', 'disabled');
        $("#subWarehouseType").attr('disabled', 'disabled');
        //$("#name").attr('disabled', 'disabled');
        $('#create-button').attr('value', 'Update');
        $('#cancel-button').attr('value', 'Cancel');
        $('#delete-button').show();
    }
    function executeSearchSubWarehouse(fieldName, fieldValue) {
        if (executePreConditionForSearchSubWarehouse(fieldName, fieldValue)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'subWarehouse', file:'search')}?fieldName=" + fieldName + "&fieldValue=" + fieldValue,
                success: function (entity) {
                    executePostConditionForSearchSubWarehouse(entity, fieldName, fieldValue);
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    if(XMLHttpRequest.status = 0){
                        MessageRenderer.renderErrorText("Network Problem: Time out");
                    } else{
                        MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                    }
                },
                dataType: 'json'
            });
        }
    }
    function executePreConditionForSearchSubWarehouse(fieldName, fieldValue) {
        // trim field vales before process.
        $('#' + fieldName).val($.trim($('#' + fieldName).val()));
        if (fieldValue == '' || fieldValue == null) {
            reset_SubWarehouse_form("#gFormSubWarehouse");
            return false;
        }
        return true;
    }
    function executePostConditionForSearchSubWarehouse(data, fieldName, fieldValue) {
        if (data == null) {
            reset_SubWarehouse_form("#gFormSubWarehouse"); // Clear Form
            $('#' + fieldName).val(fieldValue); // Set search field with fieldValue
        } else {
            showSubWarehouse(data);
        }
    }
    function loadWarehouse(id) {
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: "POST",
            url: '${resource(dir:'warehouse', file:'listWarehouse')}?id=' + id,
            dataType: 'json',
            success: function (data, textStatus) {

                $("#warehouseList").empty();
                $("#warehouseList").flexbox(data, {
                    watermark: "Select Warehouse",
                    width: 260,
                    onSelect: function () {
                        $("#warehouse").val($('#warehouseList_hidden').val());
                    }
                });
                $('#warehouseList_input').blur(function () {
                    if ($('#warehouseList_input').val() == '') {
                        $("#warehouseList").val("");
                    }
                });

            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                } else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete: function () {
                SubmissionLoader.hideFrom();
            }
        });

    }
    function reset_SubWarehouse_form(formName) {
        var enterprise = $("#enterpriseConfiguration").val();
        $(formName).find(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
            if (this.type == 'hidden') {
                this.value = "";
            } else {
                this.value = this.defaultValue;
            }
        });
        $("#warehouseList_input").attr('disabled', '');
        $("#subWarehouseType").attr('disabled', '');
        $("#name").attr('disabled', '');
        $(formName+' select').val('');
        $(formName+' input').attr('readonly',false);
//    $('input[type=checkbox]').attr('checked', false);
        $(formName + ' input[name = create-button]').attr('value', 'Create');
        $(formName + ' input[name = delete-button]').hide();
        $("#enterpriseConfiguration").val(enterprise);
    }
</script>