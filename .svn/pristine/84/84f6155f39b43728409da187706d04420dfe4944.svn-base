<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        $('#ui-widget-header-text').html('PackageType')
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormPackageType").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormPackageType").validationEngine('attach');
        reset_packageType_form("#gFormPackageType");
        $("#jqgrid-grid").jqGrid({
            url: '${resource(dir:'packageType', file:'list')}',
            datatype: "json",
            colNames: [
                'SL',
                'ID',

                'Enterprise',
                'Package Type Name',
                'Note'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30, sortable: false, align: 'left'},
                {name: 'id', index: 'id', width: 0, hidden: true},

                {name: 'enterpriseConfiguration', index: 'enterpriseConfiguration', width: 100, align: 'left'},
                {name: 'name', index: 'name', width: 100, align: 'left'},
                {name: 'note', index: 'note', width: 100, align: 'left'}
            ],
            rowNum: 50,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#jqgrid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Package Type List",
            autowidth: true,
            height: true,
            scrollOffset: 0,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                executeEditPackageType();
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
    function getSelectedPackageTypeId() {
        var packageTypeId = null;
        var rowid = $("#jqgrid-grid").jqGrid('getGridParam', 'selrow');
        if (rowid) {
            packageTypeId = $("#jqgrid-grid").jqGrid('getCell', rowid, 'id');
        }
        if (packageTypeId == null) {
            packageTypeId = $('#gFormPackageType input[name = id]').val();
        }
        return packageTypeId;
    }
    function executePreConditionPackageType() {
        // trim field vales before process.
        trim_form();
        if (!$("#gFormPackageType").validationEngine('validate')) {
            return false;
        }
        return true;
    }
    function executeAjaxPackageType() {
        if (!executePreConditionPackageType()) {
            return false;
        }
        var actionUrl = null;
        if ($('#gFormPackageType input[name = id]').val()) {
            actionUrl = "${request.contextPath}/${params.controller}/update";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/create";
        }
        jQuery.ajax({
            type: 'post',
            data: jQuery("#gFormPackageType").serialize(),
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionPackageType(data);
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
    function executePostConditionPackageType(result) {
        if (result.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_packageType_form('#gFormPackageType');
        }
        MessageRenderer.render(result);
    }
    function deleteAjaxPackageType() {    // Delete record
        var packageTypeId = getSelectedPackageTypeId();
        if (executePreConditionForDeletePackageType(packageTypeId)) {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-packageType").dialog({
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
                            data: jQuery("#gFormPackageType").serialize(),
                            url: "${resource(dir:'packageType', file:'delete')}",
                            success: function (message) {
                                executePostConditionForDeletePackageType(message);
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

    function executePreConditionForEditPackageType(packageTypeId) {
        if (packageTypeId == null) {
            alert("Please select a packageType to edit");
            return false;
        }
        return true;
    }
    function executeEditPackageType() {
        var packageTypeId = getSelectedPackageTypeId();
        if (executePreConditionForEditPackageType(packageTypeId)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'packageType', file:'edit')}?id=" + packageTypeId,
                success: function (entity) {
                    executePostConditionForEditPackageType(entity);
                },
                dataType: 'json'
            });
        }
    }
    function executePostConditionForEditPackageType(data) {
        if (data == null) {
            alert('Selected packageType might have been deleted by someone');  //Message renderer
        } else {
            showPackageType(data);
        }
    }
    function executePreConditionForDeletePackageType(packageTypeId) {
        if (packageTypeId == null) {
            alert("Please select a packageType to delete");
            return false;
        }
        return true;
    }
    function executePostConditionForDeletePackageType(data) {
        if (data.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_packageType_form('#gFormPackageType');
        }
        MessageRenderer.render(data)
    }
    function showPackageType(entity) {
        var packageType = entity.packageType
        var enterpriseConfiguration = entity.enterpriseConfiguration

        $('#gFormPackageType input[name = id]').val(packageType.id);
        $('#gFormPackageType input[name = version]').val(packageType.version);

        if (enterpriseConfiguration) {
            $('#enterpriseList').setValue(enterpriseConfiguration.name);
            $('#enterpriseConfiguration').val(enterpriseConfiguration.id)
        }

        $('#name').val(packageType.name);
        $('#note').val(packageType.note);

        $('#create-button').attr('value', 'Update');
        $('#cancel-button').attr('value', 'Cancel');
        $('#delete-button').show();
    }
    function executeSearchPackageType(fieldName, fieldValue) {
        if (executePreConditionForSearchPackageType(fieldName, fieldValue)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'packageType', file:'search')}?fieldName=" + fieldName + "&fieldValue=" + fieldValue,
                success: function (entity) {
                    executePostConditionForSearchPackageType(entity, fieldName, fieldValue);
                },
                dataType: 'json'
            });
        }
    }
    function executePreConditionForSearchPackageType(fieldName, fieldValue) {
        // trim field vales before process.
        $('#' + fieldName).val($.trim($('#' + fieldName).val()));
        if (fieldValue == '' || fieldValue == null) {
            reset_packageType_form("#gFormPackageType");
            return false;
        }
        return true;
    }
    function executePostConditionForSearchPackageType(data, fieldName, fieldValue) {
        if (data == null) {
            reset_packageType_form("#gFormPackageType"); // Clear Form
            $('#' + fieldName).val(fieldValue); // Set search field with fieldValue
        } else {
            showPackageType(data);
        }
    }
    function reset_packageType_form(formName) {
        var enterprise = $("#enterpriseConfiguration").val();
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
    }
</script>