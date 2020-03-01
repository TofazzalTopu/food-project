<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        $('#ui-widget-header-text').html('PosCustomer');
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormPosCustomer").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
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
        $("#gFormPosCustomer").validationEngine('attach');
        reset_PosCustomer_form("#gFormPosCustomer");
        $("#jqgrid-grid").jqGrid({
            url: '${resource(dir:'posCustomer', file:'list')}',
            datatype: "json",
            colNames: [
                'SL',
                'ID',

                'Enterprise',
                'Distribution Point',
                'Customer Master'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30, sortable: false, align: 'left'},
                {name: 'id', index: 'id', width: 0, hidden: true},

                {name: 'enterpriseConfiguration', index: 'enterpriseConfiguration', width: 100, align: 'left'},
                {name: 'distributionPoint', index: 'distributionPoint', width: 100, align: 'left'},
                {name: 'customerMaster', index: 'customerMaster', width: 100, align: 'left'}
            ],
            rowNum: 50,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#jqgrid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "POS Customer List",
            autowidth: true,
            height: true,
            scrollOffset: 0,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                executeEditPosCustomer();
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
    function getSelectedPosCustomerId() {
        var posCustomerId = null;
        var rowid = $("#jqgrid-grid").jqGrid('getGridParam', 'selrow');
        if (rowid) {
            posCustomerId = $("#jqgrid-grid").jqGrid('getCell', rowid, 'id');
        }
        if (posCustomerId == null) {
            posCustomerId = $('#gFormPosCustomer input[name = id]').val();
        }
        return posCustomerId;
    }
    function executePreConditionPosCustomer() {
        // trim field vales before process.
        trim_form();
        if (!$("#gFormPosCustomer").validationEngine('validate')) {
            return false;
        }
        return true;
    }
    function executeAjaxPosCustomer() {
        if (!executePreConditionPosCustomer()) {
            return false;
        }
        var actionUrl = null;
        if ($('#gFormPosCustomer input[name = id]').val()) {
            actionUrl = "${request.contextPath}/${params.controller}/update";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/create";
        }
        jQuery.ajax({
            type: 'post',
            data: jQuery("#gFormPosCustomer").serialize(),
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionPosCustomer(data);
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
    function executePostConditionPosCustomer(result) {
        if (result.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_PosCustomer_form('#gFormPosCustomer');
        }
        MessageRenderer.render(result);
    }
    function deleteAjaxPosCustomer() {    // Delete record
        var posCustomerId = getSelectedPosCustomerId();
        if (executePreConditionForDeletePosCustomer(posCustomerId)) {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-posCustomer").dialog({
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
                            data: jQuery("#gFormPosCustomer").serialize(),
                            url: "${resource(dir:'posCustomer', file:'delete')}",
                            success: function (message) {
                                executePostConditionForDeletePosCustomer(message);
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

    function executePreConditionForEditPosCustomer(posCustomerId) {
        if (posCustomerId == null) {
            alert("Please select a posCustomer to edit");
            return false;
        }
        return true;
    }
    function executeEditPosCustomer() {
        var posCustomerId = getSelectedPosCustomerId();
        if (executePreConditionForEditPosCustomer(posCustomerId)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'posCustomer', file:'edit')}?id=" + posCustomerId,
                success: function (entity) {
                    executePostConditionForEditPosCustomer(entity);
                },
                dataType: 'json'
            });
        }
    }
    function executePostConditionForEditPosCustomer(data) {
        if (data == null) {
            alert('Selected posCustomer might have been deleted by someone');  //Message renderer
        } else {
            showPosCustomer(data);
        }
    }
    function executePreConditionForDeletePosCustomer(posCustomerId) {
        if (posCustomerId == null) {
            alert("Please select a posCustomer to delete");
            return false;
        }
        return true;
    }
    function executePostConditionForDeletePosCustomer(data) {
        if (data.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_PosCustomer_form('#gFormPosCustomer');
        }
        MessageRenderer.render(data)
    }
    function showPosCustomer(entity) {
        var posCustomer = entity.posCustomer
        var enterpriseConfiguration = entity.enterpriseConfiguration
        var distributionPoint = entity.distributionPoint
        var customerMaster = entity.customerMaster

        $('#gFormPosCustomer input[name = id]').val(posCustomer.id);
        $('#gFormPosCustomer input[name = version]').val(posCustomer.version);

        if (enterpriseConfiguration) {
            $('#enterpriseList').setValue(enterpriseConfiguration.name);
            $('#enterpriseConfiguration').val(enterpriseConfiguration.id)
        }

        if (distributionPoint) {
            $('#distributionPointList').setValue(distributionPoint.name);
            $('#distributionPoint').val(distributionPoint.id)
        }

        if (entity.customerMaster) {
            $('#customerMaster').val(entity.customerMaster.id).attr("selected", "selected");
        }
        else {
            $('#customerMaster').val(entity.customerMaster);
        }

        if (customerMaster) {
            $('#searchKey').val(customerMaster.code + '-' + customerMaster.name);
            $("#customerMaster").val(customerMaster.id);
        }

        $('#create-button').attr('value', 'Update');
        $('#cancel-button').attr('value', 'Cancel');
        $('#delete-button').show();
    }
    function executeSearchPosCustomer(fieldName, fieldValue) {
        if (executePreConditionForSearchPosCustomer(fieldName, fieldValue)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'posCustomer', file:'search')}?fieldName=" + fieldName + "&fieldValue=" + fieldValue,
                success: function (entity) {
                    executePostConditionForSearchPosCustomer(entity, fieldName, fieldValue);
                },
                dataType: 'json'
            });
        }
    }
    function executePreConditionForSearchPosCustomer(fieldName, fieldValue) {
        // trim field vales before process.
        $('#' + fieldName).val($.trim($('#' + fieldName).val()));
        if (fieldValue == '' || fieldValue == null) {
            reset_PosCustomer_form("#gFormPosCustomer");
            return false;
        }
        return true;
    }
    function executePostConditionForSearchPosCustomer(data, fieldName, fieldValue) {
        if (data == null) {
            reset_PosCustomer_form("#gFormPosCustomer"); // Clear Form
            $('#' + fieldName).val(fieldValue); // Set search field with fieldValue
        } else {
            showPosCustomer(data);
        }
    }
    function loadCustomer() {

        $('#searchKey').blur(function () {
            if ($('#searchKey').val() == '') {
                $("#customerId").val('');
                $('#name').val('')
            }
        });


        jQuery('#searchKey').autocomplete({
            minLength: '1',
            source: function (request, response) {
                //EmployeeRegister.employeeCoreInfoId = null;
                if ($("#enterpriseConfiguration").val()) {
                    var data = {searchKey: request.term};
                    var url = '${resource(dir:'secondaryDemandOrder', file:'listCustomer')}?id=' + $("#enterpriseConfiguration").val() + "&query=" + $('#searchKey').val();
                    DocuAutoComplete.setSpinnerSelector('searchKey').execute(response, url, data, function (item) {
                        item['label'] = item['code'] + "-" + item['name'] + "-" + "-" + item['status'] + "-" + item['geo_location'];
                        item['value'] = item['label'];
                        return item;
                    });
                }

            },
            select: function (event, ui) {

                CustomerInfo.setCustomerInformation(ui.item.id, ui.item.value);

            }
        }).data("autocomplete")._renderItem = function (ul, item) {
            var accountstype = "";
            if (item) {
                accountstype = '<div style="font-size: 9px; color:#326E93;">' + " Code: " + item.code + ", Name: " + item.name + "-" + ", Status: " + item.status + "-" + ", Location: " + item.geo_location + '</div>';
            }
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + accountstype).appendTo(ul);

        }

    }
    $('#search-btn-customer-register-id').click(function () {

        CustomerInfo.popupCustomerListPanel();

    });
    var CustomerInfo = {
        customerCoreInfoId: null,
        popupCustomerListPanel: function () {
            var url = '${resource(dir:'secondaryDemandOrder', file:'popupCustomerListPanel')}';
            var params = {id: $("#enterpriseConfiguration").val()};
            DocuAjax.html(url, params, function (html) {

                $.fancybox(html);
            });
        },
        setCustomerInformation: function (customerCoreInfoId, customerCoreInfo) {
            $("#searchKey").val(customerCoreInfo);
            $("#customerMaster").val(customerCoreInfoId);

            CustomerInfo.customerCoreInfoId = customerCoreInfoId;

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
                var result = data.results;
                if(result.length == 1){
                    $("#distributionPointList").setValue(result[0].name);
                    $("#distributionPoint").val(result[0].id);
                    loadCustomer();
                }

            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                }else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete: function () {

            }
        });

    }
    function reset_PosCustomer_form(formName) {
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