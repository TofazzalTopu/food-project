<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        $('#ui-widget-header-text').html('CustomerSalesChannel');
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormCustomerSalesChannel").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormCustomerSalesChannel").validationEngine('attach');

        reset_customerSalesChannel_form("#gFormCustomerSalesChannel");
        $("#enterPriseName").attr("disabled", true);

        $("#jqgrid-grid").jqGrid({
            url: '${resource(dir:'customerSalesChannel', file:'list')}',
            datatype: "json",
            colNames: [
                'SL',
                'ID',

                'Enterprise',
                'Channel Name',
                'Note',
                'Is Active'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30, sortable: false, align: 'left'},
                {name: 'id', index: 'id', width: 0, hidden: true},

                {name: 'enterpriseConfiguration', index: 'enterpriseConfiguration', width: 100, align: 'left'},
                {name: 'name', index: 'name', width: 100, align: 'left'},
                {name: 'note', index: 'note', width: 100, align: 'left'},
                {name: 'isActive', index: 'isActive', width: 100, align: 'left'}
            ],
            rowNum: 50,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#jqgrid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Customer Sales Channel List",
            autowidth: true,
            height: true,
            scrollOffset: 0,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                executeEditCustomerSalesChannel();
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
    function getSelectedCustomerSalesChannelId() {
        var customerSalesChannelId = null;
        var rowid = $("#jqgrid-grid").jqGrid('getGridParam', 'selrow');
        if (rowid) {
            customerSalesChannelId = $("#jqgrid-grid").jqGrid('getCell', rowid, 'id');
        }
        if (customerSalesChannelId == null) {
            customerSalesChannelId = $('#gFormCustomerSalesChannel input[name = id]').val();
        }
        return customerSalesChannelId;
    }
    function executePreConditionCustomerSalesChannel() {
        // trim field vales before process.
        trim_form();
        if (!$("#gFormCustomerSalesChannel").validationEngine('validate')) {
            return false;
        }
        return true;
    }
    function executeAjaxCustomerSalesChannel() {
        if (!executePreConditionCustomerSalesChannel()) {
            return false;
        }
        var actionUrl = null;
        if ($('#gFormCustomerSalesChannel input[name = id]').val()) {
            actionUrl = "${request.contextPath}/${params.controller}/update";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/create";
        }
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: jQuery("#gFormCustomerSalesChannel").serialize(),
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionCustomerSalesChannel(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-grid").trigger("reloadGrid");
                    reset_customerSalesChannel_form("#gFormCustomerSalesChannel");
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
    function executePostConditionCustomerSalesChannel(result) {
        if (result.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_customerSalesChannel_form("#gFormCustomerSalesChannel");
        }
        MessageRenderer.render(result);
    }
    function deleteAjaxCustomerSalesChannel() {    // Delete record
        var customerSalesChannelId = getSelectedCustomerSalesChannelId();
        if (executePreConditionForDeleteCustomerSalesChannel(customerSalesChannelId)) {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-customerSalesChannel").dialog({
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
                            data: jQuery("#gFormCustomerSalesChannel").serialize(),
                            url: "${resource(dir:'customerSalesChannel', file:'delete')}",
                            success: function (message) {
                                executePostConditionForDeleteCustomerSalesChannel(message);
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

    function executePreConditionForEditCustomerSalesChannel(customerSalesChannelId) {
        if (customerSalesChannelId == null) {
            alert("Please select a customerSalesChannel to edit");
            return false;
        }
        return true;
    }
    function executeEditCustomerSalesChannel() {
        var customerSalesChannelId = getSelectedCustomerSalesChannelId();
        if (executePreConditionForEditCustomerSalesChannel(customerSalesChannelId)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'customerSalesChannel', file:'edit')}?id=" + customerSalesChannelId,
                success: function (entity) {
                    executePostConditionForEditCustomerSalesChannel(entity);
                },
                dataType: 'json'
            });
        }
    }
    function executePostConditionForEditCustomerSalesChannel(data) {
        if (data == null) {
            alert('Selected customerSalesChannel might have been deleted by someone');  //Message renderer
        } else {
            showCustomerSalesChannel(data);
        }
    }
    function executePreConditionForDeleteCustomerSalesChannel(customerSalesChannelId) {
        if (customerSalesChannelId == null) {
            alert("Please select a customerSalesChannel to delete");
            return false;
        }
        return true;
    }
    function executePostConditionForDeleteCustomerSalesChannel(data) {
        if (data.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_customerSalesChannel_form("#gFormCustomerSalesChannel");
        }
        MessageRenderer.render(data)
    }
    function showCustomerSalesChannel(entity) {
        var customerSalesChannel = entity.customerSalesChannel
        var enterpriseConfiguration = entity.enterpriseConfiguration

        $('#gFormCustomerSalesChannel input[name = id]').val(customerSalesChannel.id);
        $('#gFormCustomerSalesChannel input[name = version]').val(customerSalesChannel.version);

        if (enterpriseConfiguration) {
            $('#enterpriseList').setValue(enterpriseConfiguration.name);
            $('#enterpriseConfiguration').val(enterpriseConfiguration.id)
        }
        $('#name').val(customerSalesChannel.name);
        $('#note').val(customerSalesChannel.note);
        $('#isActive').attr('checked', customerSalesChannel.isActive);
        if (customerSalesChannel.userCreated) {
            $('#userCreated').val(customerSalesChannel.userCreated.id).attr("selected", "selected");
        }
        else {
            $('#userCreated').val(customerSalesChannel.userCreated);
        }
        if (customerSalesChannel.userUpdated) {
            $('#userUpdated').val(customerSalesChannel.userUpdated.id).attr("selected", "selected");
        }
        else {
            $('#userUpdated').val(customerSalesChannel.userUpdated);
        }
        $('#dateCreated').val(customerSalesChannel.dateCreated);
        $('#lastUpdated').val(customerSalesChannel.lastUpdated);
        $('#create-button').attr('value', 'Update');
        $('#cancel-button').attr('value', 'Cancel');
        $('#delete-button').show();
    }
    function executeSearchCustomerSalesChannel(fieldName, fieldValue) {
        if (executePreConditionForSearchCustomerSalesChannel(fieldName, fieldValue)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'customerSalesChannel', file:'search')}?fieldName=" + fieldName + "&fieldValue=" + fieldValue,
                success: function (entity) {
                    executePostConditionForSearchCustomerSalesChannel(entity, fieldName, fieldValue);
                },
                dataType: 'json'
            });
        }
    }
    function executePreConditionForSearchCustomerSalesChannel(fieldName, fieldValue) {
        // trim field vales before process.
        $('#' + fieldName).val($.trim($('#' + fieldName).val()));
        if (fieldValue == '' || fieldValue == null) {
            reset_customerSalesChannel_form("#gFormCustomerSalesChannel");
            return false;
        }
        return true;
    }
    function executePostConditionForSearchCustomerSalesChannel(data, fieldName, fieldValue) {
        if (data == null) {
            reset_customerSalesChannel_form("#gFormCustomerSalesChannel");
            $('#' + fieldName).val(fieldValue); // Set search field with fieldValue
        } else {
            showCustomerSalesChannel(data);
        }
    }
    function reset_customerSalesChannel_form(formName) {
        var enterpriseId = $('#enterpriseConfiguration').val();
        $(formName).find(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
            if (this.type == 'hidden') {
                this.value = "";
            } else {
                this.value = this.defaultValue;
            }
        });

        $(formName+' :checkbox').attr('checked', false);
        $(formName+' select').val('');
        $(formName+' input').attr('readonly',false);
        $(formName + ' input[name = create-button]').attr('value', 'Create');
        $(formName + ' input[name = delete-button]').hide();
        $('#enterpriseConfiguration').val(enterpriseId);
    }
</script>