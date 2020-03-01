<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        $('#ui-widget-header-text').html('Inventory');
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormWarehouse").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormWarehouse").validationEngine('attach');
        reset_Warehouse_form("#gFormWarehouse");
//        $("#businessUnitList").flexbox('Select Business Unit', {
//            watermark: "Select Business Unit",
//            width: 200,
//            onSelect: function () {
//                $("#businessUnitConfiguration").val($('#businessUnitList_hidden').val());
////                        loadBusinessUnit($('#enterpriselist_hidden').val());
//            }
//        });
//        $('#businessUnitList_input').blur(function () {
//            if ($('#businessUnitList_input').val() == '') {
//                $("#businessUnitConfiguration").val("");
//            }
//        });
        $("#jqgrid-grid").jqGrid({
            url: '${resource(dir:'warehouse', file:'list')}',
            datatype: "json",
            colNames: [
                'SL',
                'ID',

                'Inventory Name',
                'Inventory legacy ID',
                'Address',
                'Note',
                'Enterprise',
                'Business Unit'

            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30, sortable: false, align: 'left'},
                {name: 'id', index: 'id', width: 0, hidden: true},
                {name: 'name', index: 'name', width: 100, align: 'left'},
                {name: 'code', index: 'code', width: 100, align: 'left'},
                {name: 'address', index: 'address', width: 100, align: 'left'},
                {name: 'note', index: 'note', width: 100, align: 'left'},
                {name: 'enterpriseConfiguration', index: 'enterpriseConfiguration', width: 100, align: 'left'},
                {name: 'businessUnitConfiguration', index: 'businessUnitConfiguration', width: 100, align: 'left'}
            ],
            rowNum: 50,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#jqgrid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Inventory List",
            autowidth: true,
            height: true,
            scrollOffset: 0,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                executeEditWarehouse();
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
    function getSelectedWarehouseId() {
        var warehouseId = null;
        var rowid = $("#jqgrid-grid").jqGrid('getGridParam', 'selrow');
        if (rowid) {
            warehouseId = $("#jqgrid-grid").jqGrid('getCell', rowid, 'id');
        }
        if (warehouseId == null) {
            warehouseId = $('#gFormWarehouse input[name = id]').val();
        }
        return warehouseId;
    }
    function executePreConditionWarehouse() {
        // trim field vales before process.
        trim_form();
        if (!$("#gFormWarehouse").validationEngine('validate')) {
            return false;
        }
        return true;
    }
    function executeAjaxWarehouse() {
        if (!executePreConditionWarehouse()) {
            return false;
        }
        var actionUrl = null;
        if ($('#gFormWarehouse input[name = id]').val()) {
            actionUrl = "${request.contextPath}/${params.controller}/update";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/create";
        }
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: jQuery("#gFormWarehouse").serialize(),
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionWarehouse(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-grid").trigger("reloadGrid");
                    reset_Warehouse_form('#gFormWarehouse');
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
    function executePostConditionWarehouse(result) {
        if (result.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_Warehouse_form('#gFormWarehouse');
        }
        MessageRenderer.render(result);
    }
    function deleteAjaxWarehouse() {    // Delete record
        var warehouseId = getSelectedWarehouseId();
        if (executePreConditionForDeleteWarehouse(warehouseId)) {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-warehouse").dialog({
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
                            data: jQuery("#gFormWarehouse").serialize(),
                            url: "${resource(dir:'warehouse', file:'delete')}",
                            success: function (message) {
                                executePostConditionForDeleteWarehouse(message);
                            },
                            error: function (XMLHttpRequest, textStatus, errorThrown) {
                                MessageRenderer.renderErrorText(XMLHttpRequest.responseText)
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

    function executePreConditionForEditWarehouse(warehouseId) {
        if (warehouseId == null) {
            alert("Please select a warehouse to edit");
            return false;
        }
        return true;
    }
    function executeEditWarehouse() {
        var warehouseId = getSelectedWarehouseId();
        if (executePreConditionForEditWarehouse(warehouseId)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'warehouse', file:'edit')}?id=" + warehouseId,
                success: function (entity) {
                    executePostConditionForEditWarehouse(entity);
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText)
                },
                dataType: 'json'
            });
        }
    }
    function executePostConditionForEditWarehouse(data) {
        if (data == null) {
            alert('Selected warehouse might have been deleted by someone');  //Message renderer
        } else {
            showWarehouse(data);
        }
    }
    function executePreConditionForDeleteWarehouse(warehouseId) {
        if (warehouseId == null) {
            alert("Please select a warehouse to delete");
            return false;
        }
        return true;
    }
    function executePostConditionForDeleteWarehouse(data) {
        if (data.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_Warehouse_form('#gFormWarehouse');
        }
        MessageRenderer.render(data)
    }
    function showWarehouse(entity) {
        var warehouse = entity.warehouse
        var enterpriseConfiguration = entity.enterpriseConfiguration
        var businessUnitConfiguration = entity.businessUnitConfiguration

        $('#gFormWarehouse input[name = id]').val(warehouse.id);
        $('#gFormWarehouse input[name = version]').val(warehouse.version);

        if (enterpriseConfiguration) {
            $('#enterpriseWarList').setValue(enterpriseConfiguration.name);
            $('#enterpriseConfiguration').val(enterpriseConfiguration.id)
        }
        if (businessUnitConfiguration) {
            $('#businessUnitList').setValue(businessUnitConfiguration.name + '[' + businessUnitConfiguration.code + ']');
            $('#businessUnitConfiguration').val(businessUnitConfiguration.id)
        }

        $('#name').val(warehouse.name);
        $('#code').val(warehouse.code);
        //$('#code').attr('readonly', 'readonly');
        $('#address').val(warehouse.address);
        $('#note').val(warehouse.note);


        if (warehouse.userCreated) {
            $('#userCreated').val(warehouse.userCreated.id).attr("selected", "selected");
        }
        else {
            $('#userCreated').val(warehouse.userCreated);
        }
        if (warehouse.userUpdated) {
            $('#userUpdated').val(warehouse.userUpdated.id).attr("selected", "selected");
        }
        else {
            $('#userUpdated').val(warehouse.userUpdated);
        }
        $('#dateCreated').val(warehouse.dateCreated);
        $('#dateUpdated').val(warehouse.dateUpdated);
        $('#create-button').attr('value', 'Update');
        $('#cancel-button').attr('value', 'Cancel');
        $('#delete-button').show();
    }
    function executeSearchWarehouse(fieldName, fieldValue) {
        if (executePreConditionForSearchWarehouse(fieldName, fieldValue)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'warehouse', file:'search')}?fieldName=" + fieldName + "&fieldValue=" + fieldValue,
                success: function (entity) {
                    executePostConditionForSearchWarehouse(entity, fieldName, fieldValue);
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText)
                },
                dataType: 'json'
            });
        }
    }
    function executePreConditionForSearchWarehouse(fieldName, fieldValue) {
        // trim field vales before process.
        $('#' + fieldName).val($.trim($('#' + fieldName).val()));
        if (fieldValue == '' || fieldValue == null) {
            reset_Warehouse_form("#gFormWarehouse");
            return false;
        }
        return true;
    }
    function executePostConditionForSearchWarehouse(data, fieldName, fieldValue) {
        if (data == null) {
            reset_Warehouse_form("#gFormWarehouse"); // Clear Form
            $('#' + fieldName).val(fieldValue); // Set search field with fieldValue
        } else {
            showWarehouse(data);
        }

    }
    function loadBusinessUnit(id, key) {
        jQuery.ajax({
            type: "POST",
            url: '${resource(dir:'businessUnitConfiguration', file:'listBusinessUnit')}?id=' + id,
            dataType: 'json',
            success: function (data, textStatus) {
//                var options = '<option value="">Select Business Unit</option>';
//                $.each(data, function (key, val) {
//                    options += '<option value="' + val.id + '">' + val.name + '</option>';
////                    console.log(val)
//                });
//                alert(JSON.stringify(data));
//                alert(data);
                if (data.total<2) {
                    $("#businessUnitList").text(data.results[0].name);
                    $("#businessUnitConfiguration").val(data.results[0].id);
                }
            else {
                    $("#businessUnitList").empty();
                    $("#businessUnitList").flexbox(data, {
                        watermark: "Select Business Unit",
                        width: 200,
                        onSelect: function () {
                            $("#businessUnitConfiguration").val($('#businessUnitList_hidden').val());
//                        loadBusinessUnit($('#enterpriselist_hidden').val());
                        }
                    });
                    $('#businessUnitList_input').blur(function () {
                        if ($('#businessUnitList_input').val() == '') {
                            $("#businessUnitConfiguration").val("");
                        }
                    });
                }
//                $("#businessUnitConfiguration").html(options);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                MessageRenderer.renderErrorText(XMLHttpRequest.responseText)
            },
            complete: function () {
                if (key) {
                    $("#businessUnitList").setValue(key.name);
                    $("#idBusiness").val(key.id);
                }
            }
        });
    }
    function reset_Warehouse_form(formName) {
        var enterprise = $("#enterpriseConfiguration").val();
        var businessUnit=$("#businessUnitConfiguration").val();
        $(formName).find(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
            if (this.type == 'hidden') {
                this.value = "";
            } else {
                this.value = this.defaultValue;
            }
        });
        $(formName+' select').val('');
        $(formName+' input').attr('readonly',false);
//    $('input[type=checkbox]').attr('checked', false);
        $(formName + ' input[name = create-button]').attr('value', 'Create');
        $(formName + ' input[name = delete-button]').hide();
        $("#enterpriseConfiguration").val(enterprise);
        $("#businessUnitConfiguration").val(businessUnit);
    }
</script>