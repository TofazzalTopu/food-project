<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        $('#ui-widget-header-text').html('CashPool')
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }
        $("#distributionPointList").flexbox('Select Distribution Point', {
            watermark: "Select Distribution Point",
            width: 260,
            onSelect: function () {
                $("#distributionPoint").val($('#distributionPointList_hidden').val());
//                        loadBusinessUnit($('#enterpriselist_hidden').val());
            }
        });
        $('#distributionPointList_input').blur(function () {
            if ($('#distributionPointList_input').val() == '') {
                $("#distributionPointList").val("");
            }
        });
        $("#gFormCashPool").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormCashPool").validationEngine('attach');
        reset_cashPool_form("#gFormCashPool");
        $("#jqgrid-grid").jqGrid({
            url: '${resource(dir:'cashPool', file:'list')}',
            datatype: "json",
            colNames: [
                'SL',
                'ID',

                'Enterprise',
                'Code',
                'Name',
                'Account No',
                'Distribution Point',
                'Is Active'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30, sortable: false, align: 'left'},
                {name: 'id', index: 'id', width: 0, hidden: true},

                {name: 'enterpriseConfiguration', index: 'enterpriseConfiguration', width: 100, align: 'left'},
                {name: 'code', index: 'code', width: 100, align: 'left'},
                {name: 'name', index: 'name', width: 100, align: 'left'},
                {name: 'accountNo', index: 'accountNo', hidden: true},
                {name: 'distributionPoint', index: 'distributionPoint', width: 100, align: 'left'},
                {name: 'isActive', index: 'isActive', width: 50, align: 'center'}
            ],
            rowNum: 50,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#jqgrid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Cash Pool List",
            autowidth: true,
            height: true,
            scrollOffset: 0,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                executeEditCashPool();
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
    function getSelectedCashPoolId() {
        var cashPoolId = null;
        var rowid = $("#jqgrid-grid").jqGrid('getGridParam', 'selrow');
        if (rowid) {
            cashPoolId = $("#jqgrid-grid").jqGrid('getCell', rowid, 'id');
        }
        if (cashPoolId == null) {
            cashPoolId = $('#gFormCashPool input[name = id]').val();
        }
        return cashPoolId;
    }
    function executePreConditionCashPool() {
        // trim field vales before process.
        trim_form();
        if (!$("#gFormCashPool").validationEngine('validate')) {
            return false;
        }
        return true;
    }
    function executeAjaxCashPool() {
        if (!executePreConditionCashPool()) {
            return false;
        }
        var actionUrl = null;
        if ($('#gFormCashPool input[name = id]').val()) {
            actionUrl = "${request.contextPath}/${params.controller}/update";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/create";
        }
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: jQuery("#gFormCashPool").serialize(),
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionCashPool(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-grid").trigger("reloadGrid");
                    reset_cashPool_form('#gFormCashPool');
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
    function executePostConditionCashPool(result) {
        if (result.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_cashPool_form('#gFormCashPool');
        }
        MessageRenderer.render(result);
    }
    function deleteAjaxCashPool() {    // Delete record
        var cashPoolId = getSelectedCashPoolId();
        if (executePreConditionForDeleteCashPool(cashPoolId)) {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-cashPool").dialog({
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
                            data: jQuery("#gFormCashPool").serialize(),
                            url: "${resource(dir:'cashPool', file:'delete')}",
                            success: function (message) {
                                executePostConditionForDeleteCashPool(message);
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

    function executePreConditionForEditCashPool(cashPoolId) {
        if (cashPoolId == null) {
            alert("Please select a cashPool to edit");
            return false;
        }
        return true;
    }
    function executeEditCashPool() {
        var cashPoolId = getSelectedCashPoolId();
        if (executePreConditionForEditCashPool(cashPoolId)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'cashPool', file:'edit')}?id=" + cashPoolId,
                success: function (entity) {
                    executePostConditionForEditCashPool(entity);
                },
                dataType: 'json'
            });
        }
    }
    function executePostConditionForEditCashPool(data) {
        if (data == null) {
            alert('Selected cashPool might have been deleted by someone');  //Message renderer
        } else {
            showCashPool(data);
        }
    }
    function executePreConditionForDeleteCashPool(cashPoolId) {
        if (cashPoolId == null) {
            alert("Please select a cashPool to delete");
            return false;
        }
        return true;
    }
    function executePostConditionForDeleteCashPool(data) {
        if (data.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_cashPool_form('#gFormCashPool');
        }
        MessageRenderer.render(data)
    }
    function showCashPool(entity) {
        var cashPool = entity.cashPool
        var enterpriseConfiguration = entity.enterpriseConfiguration
        var distributionPoint = entity.distributionPoint
        $('#gFormCashPool input[name = id]').val(cashPool.id);
        $('#gFormCashPool input[name = version]').val(cashPool.version);

        if (enterpriseConfiguration) {
            $('#enterpriseList').setValue(enterpriseConfiguration.name);
            $('#enterpriseConfiguration').val(enterpriseConfiguration.id)
        }

        if (distributionPoint) {
            $('#distributionPointList').setValue(distributionPoint.name);
            $('#distributionPoint').val(distributionPoint.id)
        }

        $('#code').val(cashPool.code);
        $('#code').attr("readonly",true);
        $('#name').val(cashPool.name);
//        $('#accountNo').val(cashPool.accountNo);

        $('#isActive').attr('checked', cashPool.isActive);
        if (cashPool.userCreated) {
            $('#userCreated').val(cashPool.userCreated.id).attr("selected", "selected");
        }
        else {
            $('#userCreated').val(cashPool.userCreated);
        }
        if (cashPool.userUpdated) {
            $('#userUpdated').val(cashPool.userUpdated.id).attr("selected", "selected");
        }
        else {
            $('#userUpdated').val(cashPool.userUpdated);
        }
        $('#dateCreated').val(cashPool.dateCreated);
        $('#lastUpdated').val(cashPool.lastUpdated);
        $('#create-button').attr('value', 'Update');
        $('#cancel-button').attr('value', 'Cancel');
        $('#delete-button').show();
    }
    function executeSearchCashPool(fieldName, fieldValue) {
        if (executePreConditionForSearchCashPool(fieldName, fieldValue)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'cashPool', file:'search')}?fieldName=" + fieldName + "&fieldValue=" + fieldValue,
                success: function (entity) {
                    executePostConditionForSearchCashPool(entity, fieldName, fieldValue);
                },
                dataType: 'json'
            });
        }
    }
    function executePreConditionForSearchCashPool(fieldName, fieldValue) {
        // trim field vales before process.
        $('#' + fieldName).val($.trim($('#' + fieldName).val()));
        if (fieldValue == '' || fieldValue == null) {
            reset_cashPool_form("#gFormCashPool");
            return false;
        }
        return true;
    }
    function executePostConditionForSearchCashPool(data, fieldName, fieldValue) {
        if (data == null) {
            reset_cashPool_form("#gFormCashPool"); // Clear Form
            $('#' + fieldName).val(fieldValue); // Set search field with fieldValue
        } else {
            showCashPool(data);
        }
    }
    function loadDistributionPoint(enterpriseId) {
        jQuery.ajax({
            type: "POST",
            url: '${resource(dir:'distributionPoint', file:'loadDistributionPoint')}?enterpriseId=' + enterpriseId,
            dataType: 'json',
            success: function (data, textStatus) {

                $("#distributionPointList").empty();
                $("#distributionPointList").flexbox(data, {
                    watermark: "Select Distribution Point",
                    width: 260,
                    onSelect: function () {
                        $("#distributionPoint").val($('#distributionPointList_hidden').val());
                    }
                });
                $('#distributionPointList_input').blur(function () {
                    if ($('#distributionPointList_input').val() == '') {
                        $("#distributionPointList").val("");
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

            }
        });
    }
    function reset_cashPool_form(formName) {
        var enterpriseId = $("#enterpriseConfiguration").val();
        $(formName).find(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
            if (this.type == 'hidden') {
                this.value = "";
            } else {
                this.value = this.defaultValue;
            }
        });
        $(formName+' select').val('');
        $('#code').attr('readonly',false);
        $('input[type=checkbox]').attr('checked', false);
        $(formName + ' input[name = create-button]').attr('value', 'Create');
        $(formName + ' input[name = delete-button]').hide();
        $("#enterpriseConfiguration").val(enterpriseId);
    }
</script>