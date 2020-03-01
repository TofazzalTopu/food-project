<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        $('#ui-widget-header-text').html('MainProduct');
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $('#isActive').attr("checked", true);
        $('#divActive').hide();

        $('#cancel-button').click(function () {
            if ($('#cancel-button').val() == 'Cancel') {
                $('#divActive').hide();
            }
        });

        $("#gFormMainProduct").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormMainProduct").validationEngine('attach');
        reset_mainProduct_form("#gFormMainProduct");
        $("#jqgrid-grid").jqGrid({
            url: '${resource(dir:'mainProduct', file:'list')}',
            datatype: "json",
            colNames: [
                'SL',
                'ID',

                'Enterprise',
                'Main Product Code',
                'Main Product Name',
                'Note',
                'Is Active',
                'Sequence Number'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30, sortable: false, align: 'left'},
                {name: 'id', index: 'id', width: 0, hidden: true},

                {name: 'enterpriseConfiguration', index: 'enterpriseConfiguration', width: 100, align: 'left'},
                {name: 'code', index: 'code', width: 100, align: 'left'},
                {name: 'name', index: 'name', width: 100, align: 'left'},
                {name: 'note', index: 'note', width: 100, align: 'left', hidden: true},
                {name: 'isActive', index: 'isActive', width: 50, align: 'center'},
                {name:'sequenceNumber', index:'sequenceNumber',width:65,align:'center'}
            ],
            rowNum: 50,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#jqgrid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Main Product List",
            autowidth: true,
            height: true,
            scrollOffset: 0,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                executeEditMainProduct();
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
    function getSelectedMainProductId() {
        var mainProductId = null;
        var rowid = $("#jqgrid-grid").jqGrid('getGridParam', 'selrow');
        if (rowid) {
            mainProductId = $("#jqgrid-grid").jqGrid('getCell', rowid, 'id');
        }
        if (mainProductId == null) {
            mainProductId = $('#gFormMainProduct input[name = id]').val();
        }
        return mainProductId;
    }
    function executePreConditionMainProduct() {
        // trim field vales before process.
        trim_form();
        if (!$("#gFormMainProduct").validationEngine('validate')) {
//      if ($("#gFormMainProduct").validate().form({onfocusout: false}) == false) {
            return false;
        }
        return true;
    }
    function executeAjaxMainProduct() {
        if (!executePreConditionMainProduct()) {
            return false;
        }

        var actionUrl = null;
        if ($('#gFormMainProduct input[name = id]').val()) {
            actionUrl = "${request.contextPath}/${params.controller}/update";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/create";
        }
        jQuery.ajax({
            type: 'post',
            data: jQuery("#gFormMainProduct").serialize(),
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionMainProduct(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                }  else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete: function () {
            },
            dataType: 'json'
        });
        return false;
    }
    function executePostConditionMainProduct(result) {
        if (result.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_mainProduct_form('#gFormMainProduct');
            $("#divActive").hide();
        }
        MessageRenderer.render(result);
    }
    function deleteAjaxMainProduct() {    // Delete record
        var mainProductId = getSelectedMainProductId();
        if (executePreConditionForDeleteMainProduct(mainProductId)) {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-mainProduct").dialog({
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
                            data: jQuery("#gFormMainProduct").serialize(),
                            url: "${resource(dir:'mainProduct', file:'delete')}",
                            success: function (message) {
                                executePostConditionForDeleteMainProduct(message);
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

    function executePreConditionForEditMainProduct(mainProductId) {
        if (mainProductId == null) {
            alert("Please select a mainProduct to edit");
            return false;
        }
        return true;
    }
    function executeEditMainProduct() {
        var mainProductId = getSelectedMainProductId();
        if (executePreConditionForEditMainProduct(mainProductId)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'mainProduct', file:'edit')}?id=" + mainProductId,
                success: function (entity) {
                    executePostConditionForEditMainProduct(entity);
                },
                dataType: 'json'
            });
        }
    }
    function executePostConditionForEditMainProduct(data) {
        if (data == null) {
            alert('Selected mainProduct might have been deleted by someone');  //Message renderer
        } else {
            showMainProduct(data);
        }
    }
    function executePreConditionForDeleteMainProduct(mainProductId) {
        if (mainProductId == null) {
            alert("Please select a mainProduct to delete");
            return false;
        }
        return true;
    }
    function executePostConditionForDeleteMainProduct(data) {
        if (data.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_mainProduct_form('#gFormMainProduct');
            $("#divActive").hide();
        }
        MessageRenderer.render(data)
    }
    function showMainProduct(entity) {
        $('#gFormMainProduct input[name = id]').val(entity.id);
        $('#gFormMainProduct input[name = version]').val(entity.version);

        if (entity.enterpriseConfiguration) {
            $('#enterpriseConfiguration').val(entity.enterpriseConfiguration.id).attr("selected", "selected");
        }
        else {
            $('#enterpriseConfiguration').val(entity.enterpriseConfiguration);
        }
        $('#code').val(entity.code);
        $('#code').attr('readonly','readonly');
        $('#name').val(entity.name);
        $('#note').val(entity.note);
        $('#sequenceNumber').val(entity.sequenceNumber);

        if (entity.isActive) {
            $('#isActive').attr('checked', true);
        } else {
            $('#isActive').attr('checked', false);
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

        $('#divActive').show();
    }
    function executeSearchMainProduct(fieldName, fieldValue) {
        if (executePreConditionForSearchMainProduct(fieldName, fieldValue)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'mainProduct', file:'search')}?fieldName=" + fieldName + "&fieldValue=" + fieldValue,
                success: function (entity) {
                    executePostConditionForSearchMainProduct(entity, fieldName, fieldValue);
                },
                dataType: 'json'
            });
        }
    }
    function executePreConditionForSearchMainProduct(fieldName, fieldValue) {
        // trim field vales before process.
        $('#' + fieldName).val($.trim($('#' + fieldName).val()));
        if (fieldValue == '' || fieldValue == null) {
            reset_mainProduct_form("#gFormMainProduct");
            return false;
        }
        return true;
    }
    function executePostConditionForSearchMainProduct(data, fieldName, fieldValue) {
        if (data == null) {
            reset_mainProduct_form("#gFormMainProduct"); // Clear Form
            $('#' + fieldName).val(fieldValue); // Set search field with fieldValue
        } else {
            showMainProduct(data);
        }
    }
    function reset_mainProduct_form(formName) {
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