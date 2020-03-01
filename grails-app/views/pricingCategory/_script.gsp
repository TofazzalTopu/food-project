<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        $('#ui-widget-header-text').html('PricingCategory')
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormPricingCategory").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormPricingCategory").validationEngine('attach');

        var id = $('#enterpriseConfiguration').val();
        reset_form("#gFormPricingCategory");
        $('#enterpriseConfiguration').val(id);
        $("#enterPriseName").attr("disabled", true);

        $("#jqgrid-grid").jqGrid({
            url: '${resource(dir:'pricingCategory', file:'list')}',
            datatype: "json",
            colNames: [
                'SL',
                'ID',
                'Enterprise',
                'Pricing Category Code',
                'Pricing Category Name',
                'Short Name',
                'Note'

            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30, sortable: false, align: 'left'},
                {name: 'id', index: 'id', width: 0, hidden: true},

                {name: 'enterpriseConfiguration', index: 'enterpriseConfiguration', width: 100, align: 'left'},
                {name: 'code', index: 'code', width: 100, align: 'left'},
                {name: 'name', index: 'name', width: 100, align: 'left'},
                {name: 'shortName', index: 'shortName', width: 100, align: 'left'},
                {name: 'note', index: 'note', width: 100, align: 'left'}

            ],
            rowNum: 50,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#jqgrid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Pricing Category List",
            autowidth: true,
            height: true,
            scrollOffset: 0,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                executeEditPricingCategory();
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
    function getSelectedPricingCategoryId() {
        var pricingCategoryId = null;
        var rowid = $("#jqgrid-grid").jqGrid('getGridParam', 'selrow');
        if (rowid) {
            pricingCategoryId = $("#jqgrid-grid").jqGrid('getCell', rowid, 'id');
        }
        if (pricingCategoryId == null) {
            pricingCategoryId = $('#gFormPricingCategory input[name = id]').val();
        }
        return pricingCategoryId;
    }
    function executePreConditionPricingCategory() {
        // trim field vales before process.
        trim_form();
        if (!$("#gFormPricingCategory").validationEngine('validate')) {
            return false;
        }
        return true;
    }
    function executeAjaxPricingCategory() {
        if (!executePreConditionPricingCategory()) {
            return false;
        }
        var actionUrl = null;
        if ($('#gFormPricingCategory input[name = id]').val()) {
            actionUrl = "${request.contextPath}/${params.controller}/update";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/create";
        }
        jQuery.ajax({
            type: 'post',
            data: jQuery("#gFormPricingCategory").serialize(),
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionPricingCategory(data);
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
    function executePostConditionPricingCategory(result) {
        if (result.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            var id = $('#enterpriseConfiguration').val();
            reset_form("#gFormPricingCategory");
            $('#enterpriseConfiguration').val(id);
        }
        MessageRenderer.render(result);
    }
    function deleteAjaxPricingCategory() {    // Delete record
        var pricingCategoryId = getSelectedPricingCategoryId();
        if (executePreConditionForDeletePricingCategory(pricingCategoryId)) {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-pricingCategory").dialog({
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
                            data: jQuery("#gFormPricingCategory").serialize(),
                            url: "${resource(dir:'pricingCategory', file:'delete')}",
                            success: function (message) {
                                executePostConditionForDeletePricingCategory(message);
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

    function executePreConditionForEditPricingCategory(pricingCategoryId) {
        if (pricingCategoryId == null) {
            alert("Please select a pricingCategory to edit");
            return false;
        }
        return true;
    }
    function executeEditPricingCategory() {
        var pricingCategoryId = getSelectedPricingCategoryId();
        if (executePreConditionForEditPricingCategory(pricingCategoryId)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'pricingCategory', file:'edit')}?id=" + pricingCategoryId,
                success: function (entity) {
                    executePostConditionForEditPricingCategory(entity);
                },
                dataType: 'json'
            });
        }
    }
    function executePostConditionForEditPricingCategory(data) {
        if (data == null) {
            alert('Selected pricingCategory might have been deleted by someone');  //Message renderer
        } else {
            showPricingCategory(data);
        }
    }
    function executePreConditionForDeletePricingCategory(pricingCategoryId) {
        if (pricingCategoryId == null) {
            alert("Please select a pricingCategory to delete");
            return false;
        }
        return true;
    }
    function executePostConditionForDeletePricingCategory(data) {
        if (data.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            var id = $('#enterpriseConfiguration').val();
            reset_form("#gFormPricingCategory");
            $('#enterpriseConfiguration').val(id);
        }
        MessageRenderer.render(data)
    }
    function showPricingCategory(entity) {
        var pricingCategory = entity.pricingCategory
        var enterpriseConfiguration = entity.enterpriseConfiguration
        $('#gFormPricingCategory input[name = id]').val(pricingCategory.id);
        $('#gFormPricingCategory input[name = version]').val(pricingCategory.version);


        if (enterpriseConfiguration) {
            $('#enterpriseList').setValue(enterpriseConfiguration.name);
            $('#enterpriseConfiguration').val(enterpriseConfiguration.id)
        }
        $('#code').val(pricingCategory.code);
        $('#name').val(pricingCategory.name);
        $('#note').val(pricingCategory.note);
        $('#shortName').val(pricingCategory.shortName);
        $('#create-button').attr('value', 'Update');
        $('#cancel-button').attr('value', 'Cancel');
        $('#delete-button').show();
    }
    function executeSearchPricingCategory(fieldName, fieldValue) {
        if (executePreConditionForSearchPricingCategory(fieldName, fieldValue)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'pricingCategory', file:'search')}?fieldName=" + fieldName + "&fieldValue=" + fieldValue,
                success: function (entity) {
                    executePostConditionForSearchPricingCategory(entity, fieldName, fieldValue);
                },
                dataType: 'json'
            });
        }
    }
    function executePreConditionForSearchPricingCategory(fieldName, fieldValue) {
        // trim field vales before process.
        $('#' + fieldName).val($.trim($('#' + fieldName).val()));
        if (fieldValue == '' || fieldValue == null) {
            var id = $('#enterpriseConfiguration').val();
            reset_form("#gFormPricingCategory");
            $('#enterpriseConfiguration').val(id);
            return false;
        }
        return true;
    }
    function executePostConditionForSearchPricingCategory(data, fieldName, fieldValue) {
        if (data == null) {
            var id = $('#enterpriseConfiguration').val();
            reset_form("#gFormPricingCategory");
            $('#enterpriseConfiguration').val(id);
            $('#' + fieldName).val(fieldValue); // Set search field with fieldValue
        } else {
            showPricingCategory(data);
        }
    }
</script>