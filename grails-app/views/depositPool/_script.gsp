<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        $('#ui-widget-header-text').html('DepositPool')
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormDepositPool").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormDepositPool").validationEngine('attach');
        reset_depositPool_form("#gFormDepositPool");

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
        $("#jqgrid-grid").jqGrid({
            url: '${resource(dir:'depositPool', file:'list')}',
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
                {name: 'accountNo', index: 'accountNo', width: 100, align: 'left'},
                {name: 'distributionPoint', index: 'distributionPoint', width: 100, align: 'left'},
                {name: 'isActive', index: 'isActive', width: 50, align: 'center'}
            ],
            rowNum: 50,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#jqgrid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Deposit Pool List",
            autowidth: true,
            height: true,
            scrollOffset: 0,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                executeEditDepositPool();
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
    function getSelectedDepositPoolId() {
        var depositPoolId = null;
        var rowid = $("#jqgrid-grid").jqGrid('getGridParam', 'selrow');
        if (rowid) {
            depositPoolId = $("#jqgrid-grid").jqGrid('getCell', rowid, 'id');
        }
        if (depositPoolId == null) {
            depositPoolId = $('#gFormDepositPool input[name = id]').val();
        }
        return depositPoolId;
    }
    function executePreConditionDepositPool() {
        // trim field vales before process.
        trim_form();
        if (!$("#gFormDepositPool").validationEngine('validate')) {
            return false;
        }
        return true;
    }
    function executeAjaxDepositPool() {
        if (!executePreConditionDepositPool()) {
            return false;
        }
        var actionUrl = null;
        if ($('#gFormDepositPool input[name = id]').val()) {
            actionUrl = "${request.contextPath}/${params.controller}/update";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/create";
        }
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: jQuery("#gFormDepositPool").serialize(),
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionDepositPool(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
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
    function executePostConditionDepositPool(result) {
        if (result.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_depositPool_form('#gFormDepositPool');
        }
        MessageRenderer.render(result);
    }
    function deleteAjaxDepositPool() {    // Delete record
        var depositPoolId = getSelectedDepositPoolId();
        if (executePreConditionForDeleteDepositPool(depositPoolId)) {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-depositPool").dialog({
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
                            data: jQuery("#gFormDepositPool").serialize(),
                            url: "${resource(dir:'depositPool', file:'delete')}",
                            success: function (message) {
                                executePostConditionForDeleteDepositPool(message);
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

    function executePreConditionForEditDepositPool(depositPoolId) {
        if (depositPoolId == null) {
            alert("Please select a depositPool to edit");
            return false;
        }
        return true;
    }
    function executeEditDepositPool() {
        var depositPoolId = getSelectedDepositPoolId();
        if (executePreConditionForEditDepositPool(depositPoolId)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'depositPool', file:'edit')}?id=" + depositPoolId,
                success: function (entity) {
                    executePostConditionForEditDepositPool(entity);
                },
                dataType: 'json'
            });
        }
    }
    function executePostConditionForEditDepositPool(data) {
        if (data == null) {
            alert('Selected depositPool might have been deleted by someone');  //Message renderer
        } else {
            showDepositPool(data);
        }
    }
    function executePreConditionForDeleteDepositPool(depositPoolId) {
        if (depositPoolId == null) {
            alert("Please select a depositPool to delete");
            return false;
        }
        return true;
    }
    function executePostConditionForDeleteDepositPool(data) {
        if (data.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_depositPool_form('#gFormDepositPool');
        }
        MessageRenderer.render(data)
    }
    function showDepositPool(entity) {
        var depositPool = entity.depositPool
        var enterpriseConfiguration = entity.enterpriseConfiguration
        var distributionPoint = entity.distributionPoint

        $('#gFormDepositPool input[name = id]').val(depositPool.id);
        $('#gFormDepositPool input[name = version]').val(depositPool.version);

        if (enterpriseConfiguration) {
            $('#enterpriseList').setValue(enterpriseConfiguration.name);
            $('#enterpriseConfiguration').val(enterpriseConfiguration.id)
        }

        if (distributionPoint) {
            $('#distributionPointList').setValue(distributionPoint.name);
            $('#distributionPoint').val(distributionPoint.id)
        }

        $('#code').val(depositPool.code);
        $('#code').attr('readonly', 'readonly');
        $('#name').val(depositPool.name);
        $('#accountNo').val(depositPool.accountNo);

        $('#isActive').attr('checked', depositPool.isActive);
        if (depositPool.userCreated) {
            $('#userCreated').val(depositPool.userCreated.id).attr("selected", "selected");
        }
        else {
            $('#userCreated').val(depositPool.userCreated);
        }
        if (depositPool.userUpdated) {
            $('#userUpdated').val(depositPool.userUpdated.id).attr("selected", "selected");
        }
        else {
            $('#userUpdated').val(depositPool.userUpdated);
        }
        $('#dateCreated').val(depositPool.dateCreated);
        $('#lastUpdated').val(depositPool.lastUpdated);
        $('#create-button').attr('value', 'Update');
        $('#cancel-button').attr('value', 'Cancel');
        $('#delete-button').show();
    }
    function executeSearchDepositPool(fieldName, fieldValue) {
        if (executePreConditionForSearchDepositPool(fieldName, fieldValue)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'depositPool', file:'search')}?fieldName=" + fieldName + "&fieldValue=" + fieldValue,
                success: function (entity) {
                    executePostConditionForSearchDepositPool(entity, fieldName, fieldValue);
                },
                dataType: 'json'
            });
        }
    }
    function executePreConditionForSearchDepositPool(fieldName, fieldValue) {
        // trim field vales before process.
        $('#' + fieldName).val($.trim($('#' + fieldName).val()));
        if (fieldValue == '' || fieldValue == null) {
            reset_depositPool_form("#gFormDepositPool");
            return false;
        }
        return true;
    }
    function executePostConditionForSearchDepositPool(data, fieldName, fieldValue) {
        if (data == null) {
            reset_depositPool_form("#gFormDepositPool"); // Clear Form
            $('#' + fieldName).val(fieldValue); // Set search field with fieldValue
        } else {
            showDepositPool(data);
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
                }
                else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete: function () {

            }
        });
    }
    function reset_depositPool_form(formName) {
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