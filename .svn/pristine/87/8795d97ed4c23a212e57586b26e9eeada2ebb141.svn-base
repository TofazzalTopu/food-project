<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormCustomerSettlement").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormCustomerSettlement").validationEngine('attach');
//        reset_form("#gFormCustomerSettlement");
        $("#adjust-button-customerSettlement").hide();
        $("#withdraw-button-customerSettlement").hide();
        $("#withdrawAmount").format({precision: 2, allow_negative: false, autofix: true});
    });
    function getSelectedCustomerSettlementId() {
        var customerSettlementId = $('#gFormCustomerSettlement input[name = id]').val();
        return customerSettlementId;
    }
    function executePreConditionCustomerSettlement() {
        // trim field vales before process.
        trim_form();
        if (!$("#gFormCustomerSettlement").validationEngine('validate')) {
            return false;
        }
        return true;
    }
    function executeAjaxCustomerSettlement() {
        if (!executePreConditionCustomerSettlement()) {
            return false;
        }
        var actionUrl = null;
        if ($('#gFormCustomerSettlement input[name = id]').val()) {
            actionUrl = "${request.contextPath}/${params.controller}/update";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/create";
        }
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: jQuery("#gFormCustomerSettlement").serialize(),
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionCustomerSettlement(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    reset_form('#gFormCustomerSettlement');
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                }
                else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete: function () {
                SubmissionLoader.hideFrom()
            },
            dataType: 'json'
        });
        return false;
    }
    function executePostConditionCustomerSettlement(result) {
        if (result.type == 1) {
            reset_form('#gFormCustomerSettlement');
        }
        MessageRenderer.render(result);
    }
    function deleteAjaxCustomerSettlement() {    // Delete record
        var customerSettlementId = getSelectedCustomerSettlementId();
        if (executePreConditionForDeleteCustomerSettlement(customerSettlementId)) {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-customerSettlement").dialog({
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
                            data: jQuery("#gFormCustomerSettlement").serialize(),
                            url: "${resource(dir:'customerSettlement', file:'delete')}",
                            success: function (message) {
                                executePostConditionForDeleteCustomerSettlement(message);
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

    function executePreConditionForEditCustomerSettlement(customerSettlementId) {
        if (customerSettlementId == null) {
            alert("Please select a customerSettlement to edit");
            return false;
        }
        return true;
    }
    function executeEditCustomerSettlement() {
        var customerSettlementId = getSelectedCustomerSettlementId();
        if (executePreConditionForEditCustomerSettlement(customerSettlementId)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'customerSettlement', file:'edit')}?id=" + customerSettlementId,
                success: function (entity) {
                    executePostConditionForEditCustomerSettlement(entity);
                },
                dataType: 'json'
            });
        }
    }
    function executePostConditionForEditCustomerSettlement(data) {
        if (data == null) {
            alert('Selected customerSettlement might have been deleted by someone');  //Message renderer
        } else {
            showCustomerSettlement(data);
        }
    }
    function executePreConditionForDeleteCustomerSettlement(customerSettlementId) {
        if (customerSettlementId == null) {
            alert("Please select a customerSettlement to delete");
            return false;
        }
        return true;
    }
    function executePostConditionForDeleteCustomerSettlement(data) {
        if (data.type == 1) {
            $("#jqgrid-grid-customerSettlement").trigger("reloadGrid");
            reset_form('#gFormCustomerSettlement');
        }
        MessageRenderer.render(data)
    }
    function showCustomerSettlement(entity) {
        $('#gFormCustomerSettlement input[name = id]').val(entity.id);
        $('#gFormCustomerSettlement input[name = version]').val(entity.version);

        if (entity.customerMaster) {
            $('#customerMaster').val(entity.customerMaster.id).attr("selected", "selected");
        }
        else {
            $('#customerMaster').val(entity.customerMaster);
        }
        $('#settlementDate').val(entity.settlementDate);
        $('#receivableAmount').val(entity.receivableAmount);
        $('#securityDeposit').val(entity.securityDeposit);
        $('#dueToCustomer').val(entity.dueToCustomer);
        $('#dueToCompany').val(entity.dueToCompany);
        $('#adjustAmount').val(entity.adjustAmount);
        $('#withdrawAmount').val(entity.withdrawAmount);
        $('#paidThrough').val(entity.paidThrough);
        $('#reference').val(entity.reference);
        $('#create-button-customerSettlement').attr('value', 'Update');
        $('#delete-button-customerSettlement').show();
    }
    function executeSearchCustomerSettlement(fieldName, fieldValue) {
        if (executePreConditionForSearchCustomerSettlement(fieldName, fieldValue)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'customerSettlement', file:'search')}?fieldName=" + fieldName + "&fieldValue=" + fieldValue,
                success: function (entity) {
                    executePostConditionForSearchCustomerSettlement(entity, fieldName, fieldValue);
                },
                dataType: 'json'
            });
        }
    }
    function executePreConditionForSearchCustomerSettlement(fieldName, fieldValue) {
        // trim field vales before process.
        $('#' + fieldName).val($.trim($('#' + fieldName).val()));
        if (fieldValue == '' || fieldValue == null) {
            reset_form("#gFormCustomerSettlement");
            return false;
        }
        return true;
    }
    function executePostConditionForSearchCustomerSettlement(data, fieldName, fieldValue) {
        if (data == null) {
            reset_form("#gFormCustomerSettlement"); // Clear Form
            $('#' + fieldName).val(fieldValue); // Set search field with fieldValue
        } else {
            showCustomerSettlement(data);
        }
    }

    function reset_form(formName) {
        $(formName).find(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
            if (this.type == 'hidden') {
                this.value = "";
            } else {
                this.value = this.defaultValue;
            }
        });
        $('input[type=checkbox]').attr('checked', false);
        $(formName + ' input[name = create-button]').attr('value', 'Create');
        $(formName + ' input[name = delete-button]').hide();
    }

    function trim_form() {
        $(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
            this.value = $.trim(this.value);
        });
    }

    function getTerritoryByEnterprise(enterpriseId){
        var options = "<option value=''>Select Territory</option>";
        if(enterpriseId){
            $.ajax({
                type:'POST',
                data:'enterpriseId=' + enterpriseId,
                url:'${request.contextPath}/territoryConfiguration/searchTerritoryByEnterprise',
                success:function (data) {
                    var territoryCount = data.length;
                    for(var i=0; i < territoryCount; i++){
                        options += "<option value='" + data[i].id + "'>" + data[i].name + "</option>"
                    }
                    $("#territory").html(options);
                },
                error:function (XMLHttpRequest, textStatus, errorThrown) {
                    if(XMLHttpRequest.status = 0){
                        $("#jqgrid-grid").trigger("reloadGrid");
                        reset_form('#gFormCustomerTerritorySubArea');
                        MessageRenderer.renderErrorText("Network Problem: Time out");
                    } else{
                        MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                    }
                },
                complete:function (data) {
                },
                dataType:'json'
            });
        }else{
            $("#territory").html(options);
        }
    }

    function getFlexBoxTerritorySubAreaData(territoryId) {
        $("#geoLocation").html("");
        $.ajax({
            type: "POST",
            data:'territoryId=' + territoryId,
            url: "${request.contextPath}/territorySubArea/getFlexBoxTerritorySubAeaData",
            success: function(result) {
                $("#geoLocation").flexbox(result, {
                    watermark: "",
                    width: 485,
                    onSelect: function() {
                        $("#territorySubAreaId").val($('#geoLocation_hidden').val());
                        getDPByTerritorySubArea($('#geoLocation_hidden').val());
                        if(document.getElementById('others').checked) {
                            loadExternalCustomerForSettlement($('#geoLocation_hidden').val());
                        }else{
                            $("#searchCustomerKey").html("");
                            $("#searchCustomerKey").val("");
                        }
                    }
                });
                $('#geoLocation_input').focus();
                $('#geoLocation_input').blur(function() {
                    if($('#geoLocation_input').val() == ''){
                        $("#territorySubAreaId").val("");
                    }
                });
            },
            dataType:'json'
        });
    }

    function getDPByTerritorySubArea(geoLocationId){
        var options = "<option value=''>Select DP</option>";
        if(geoLocationId){
            $.ajax({
                type:'POST',
                data:'territorySubAreaId=' + geoLocationId,
                url:'${request.contextPath}/distributionPoint/listDistributionPointBySubArea',
                success:function (data) {
                    var dpCount = data.length;
                    for(var i=0; i < dpCount; i++){
                        options += "<option value='" + data[i].id + "'>" + data[i].name + "</option>"
                    }
                    $("#distributionPoint").html(options);
                },
                error:function (XMLHttpRequest, textStatus, errorThrown) {
                    if(XMLHttpRequest.status = 0){
                        MessageRenderer.renderErrorText("Network Problem: Time out");
                    } else{
                        MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                    }
                },
                complete:function (data) {
                },
                dataType:'json'
            });
        }else{
            $("#distributionPoint").html(options);
        }
    }

    function loadExternalCustomerForSettlement(territorySubAreaId) {
        if(!territorySubAreaId){
            return
        }
        jQuery('#searchCustomerKey').autocomplete({
            minLength:'1',
            source:function (request, response) {
                //EmployeeRegister.employeeCoreInfoId = null;
                var data = {searchKey: request.term, territorySubAreaId: territorySubAreaId};
                var url = '${resource(dir:'customerSettlement', file:'othersCustomerAutoComplete')}';
                DocuAutoComplete.setSpinnerSelector('searchKey').execute(response, url, data, function (item) {
                    item['label'] = "[" + item['code'] + "] " + item['name'];
                    item['value'] = item['label'];
                    return item;
                });
            },
            select:function (event, ui) {
                $("#customerId").val(ui.item.id);
                $("#legacyId").val(ui.item.legacy_id);
                $("#customerNumber").val(ui.item.code);
                $("#customerName").val(ui.item.name);
                $("#customerAddress").val(ui.item.address);
                $('#searchProductKey').focus();
                getCustomerSettlementData(ui.item.id);
            }
        }).data("autocomplete")._renderItem = function (ul, item) {
            var custometDetailts = "";
            if (item) {
                custometDetailts = '<div style="font-size: 9px; color:#326E93;">' + "Legacy ID: " + item.legacy_id +", Code: " +item.code + ", Name: " + item.name + ", Address: " + item.address + '</div>';
            }
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + custometDetailts).appendTo(ul);
        };
    }

    function loadBranchCustomerForSettlement(distributionPointId) {
        if(!distributionPointId){
            return
        }
        jQuery('#searchCustomerKey').autocomplete({
            minLength:'1',
            source:function (request, response) {
                //EmployeeRegister.employeeCoreInfoId = null;
                var data = {searchKey: request.term, distributionPointId: distributionPointId};
                var url = '${resource(dir:'customerSettlement', file:'branchCustomerAutoComplete')}';
                DocuAutoComplete.setSpinnerSelector('searchKey').execute(response, url, data, function (item) {
                    item['label'] = "[" + item['code'] + "] " + item['name'];
                    item['value'] = item['label'];
                    return item;
                });
            },
            select:function (event, ui) {
                $("#customerId").val(ui.item.id);
                $("#legacyId").val(ui.item.legacy_id);
                $("#customerNumber").val(ui.item.code);
                $("#customerName").val(ui.item.name);
                $("#customerAddress").val(ui.item.address);
                $('#searchProductKey').focus();
                getCustomerSettlementData(ui.item.id);
            }
        }).data("autocomplete")._renderItem = function (ul, item) {
            var custometDetailts = "";
            if (item) {
                custometDetailts = '<div style="font-size: 9px; color:#326E93;">' + "Legacy ID: " + item.legacy_id +", Code: " +item.code + ", Name: " + item.name + ", Address: " + item.address + '</div>';
            }
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + custometDetailts).appendTo(ul);
        };
    }

    $('#search-btn-customer-register-id').click(function(){
        var territorySubAreaId = $("#territorySubAreaId").val();
        if(!territorySubAreaId){
            MessageRenderer.render({
                "messageBody": "Geo Location is not selected",
                "messageTitle": "Customer Settlement",
                "type": "0"
            });
            return
        }
        var params = {territorySubAreaId: territorySubAreaId};
        var url = "";
        if(document.getElementById('branch').checked) {
            var distributionPointId = $("#distributionPoint").val();
            if(!distributionPointId){
                MessageRenderer.render({
                    "messageBody": "Distribution Point is not selected",
                    "messageTitle": "Customer Settlement",
                    "type": "0"
                });
                return
            }
            params = {distributionPointId: distributionPointId};
            url = '${resource(dir:'customerSettlement', file:'popupBranchCustomerListPanel')}' ;
        }else if(document.getElementById('others').checked) {
            url = '${resource(dir:'customerSettlement', file:'popupOthersCustomerListPanel')}' ;
        }

        DocuAjax.html(url, params, function(html){
            $.fancybox(html);
        });
    });

    function getCustomerSettlementData(customerId){
        if(customerId){
            $.ajax({
                type:'POST',
                data:'customerMasterId=' + customerId,
                url:'${request.contextPath}/${params.controller}/getCustomerSettlementData',
                success:function (data) {
                    $("#receivableAmount").val(data.receivableAmount);
                    $("#securityDeposit").val(data.securityDeposit);
                    $("#dueToCustomer").val(data.receivableAmount);
                    $("#dueToCompany").val(data.securityDeposit);
                    var remainingAmount =  data.securityDeposit - data.receivableAmount;
                    $("#remainingAfterAdjustAmount").val(remainingAmount.toFixed(2));
                    if(remainingAmount > 0.00){
                        $("#withdraw-button-customerSettlement").show();
                    }else{
                        $("#withdraw-button-customerSettlement").hide();
                    }
                    if(data.receivableAmount > 0.00){
                        $("#adjust-button-customerSettlement").show();
//                        $("#withdraw-button-customerSettlement").hide();
                    }else if(data.securityDeposit > 0.00){
                        $("#adjust-button-customerSettlement").show();
//                        $("#withdraw-button-customerSettlement").show();
                    }
                    $("#withdrawAmount").val("");
//                    $("#remainingAfterAdjustAmount").val("");
                },
                error:function (XMLHttpRequest, textStatus, errorThrown) {
                    if(XMLHttpRequest.status = 0){
                        $("#receivableAmount").val("");
                        $("#securityDeposit").val("");
                        $("#dueToCustomer").val("");
                        $("#dueToCompany").val("");
                        $("#withdrawAmount").val("");
                        $("#remainingAfterAdjustAmount").val("");
                        MessageRenderer.renderErrorText("Network Problem: Time out");
                    } else{
                        MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                    }
                },
                complete:function (data) {
                },
                dataType:'json'
            });
        }else{
            $("#receivableAmount").val("");
            $("#securityDeposit").val("");
            $("#dueToCustomer").val("");
            $("#dueToCompany").val("");
            $("#remainingAfterAdjustAmount").val("");
        }
    }

    function handleCustomerTypeClick(customerTypeRadio) {
        if(customerTypeRadio.value == "branch"){
            // Branch
            var distributionPointId = $("#distributionPoint").val();
            if(distributionPointId){
                loadBranchCustomerForSettlement(distributionPointId)
            }else{
                $('#searchCustomerKey').html("");
                $('#searchCustomerKey').val("");
            }
            $("#dpSelection").show();
        }else{
            // Others
            var territorySubAreaId = $("#territorySubAreaId").val();
            if(territorySubAreaId){
                loadExternalCustomerForSettlement(territorySubAreaId)
            }else{
                $('#searchCustomerKey').html("");
                $('#searchCustomerKey').val("");
            }
            $("#dpSelection").hide();
        }
    }

    function executeAjaxAdjustWithReceivable() {
        var customerMasterId = $("#customerMasterId").val();
        if(!customerMasterId){
            MessageRenderer.render({
                "messageBody": "Customer is not selected",
                "messageTitle": "Customer Settlement",
                "type": "0"
            });
            return false
        }
        var dueToCompany = Number($("#dueToCompany").val());
        var dueToCustomer = Number($("#dueToCustomer").val());
        var data = $("#gFormCustomerSettlement").serializeArray();
        data.push({'name':'selectedCustomerType', value: $('input[name=customerType]:checked').val()});

        if(dueToCustomer == 0.00){
            $("#remainingAfterAdjustAmount").val(dueToCompany);
            $("#withdraw-button-customerSettlement").show();
        } else if(dueToCompany > 0.00){
            if(dueToCompany <= dueToCustomer){
                data.push({name:'adjustAmount', value: dueToCompany});
            }else{
                data.push({name:'adjustAmount', value: dueToCustomer});
            }
            SubmissionLoader.showTo();
            jQuery.ajax({
                type: 'post',
                data: data,
                url: "${request.contextPath}/${params.controller}/adjustWithReceivable",
                success: function (data, textStatus) {
                    MessageRenderer.render(data);
                    if(data.type == 1){
                        getCustomerSettlementData($("#customerMasterId").val());
//                        var dataItems = data.messageBody[0].split(":");
//                        if(dataItems.length > 0){
//                            var adjustedAmount  = Number(dataItems[1]);
//                            var dueToCustomer = Number($("#dueToCustomer").val());
//                            var dueToCompany = Number($("#dueToCompany").val());
//                            $("#remainingAfterAdjustAmount").val(dueToCompany - dueToCustomer - adjustedAmount);
//                            if((dueToCompany - dueToCustomer - adjustedAmount) > 0.00){
//                                $("#adjust-button-customerSettlement").hide();
//                                $("#withdraw-button-customerSettlement").show();
//                            }
//                        }
                    }
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
                },
                dataType: 'json'
            });
        }else if(dueToCompany == 0.00){
            MessageRenderer.render({
                "messageBody": "No Due to Company",
                "messageTitle": "Customer Settlement",
                "type": "0"
            });
        }
        return true
    }

    function getDefaultCustomerByDp(distributionPointId){
        if(distributionPointId){
            $.ajax({
                type:'POST',
                data:'distributionPointId=' + distributionPointId,
                url:'${request.contextPath}/${params.controller}/getDefaultCustomerByDP',
                success:function (data) {
                    $("#defaultCustomerId").val(data.customerId);
                    $("#defaultCustomer").val("[" + data.customerCode + "] " + data.customerName);
                },
                error:function (XMLHttpRequest, textStatus, errorThrown) {
                    if(XMLHttpRequest.status = 0){
                        $("#defaultCustomerId").val("");
                        $("#defaultCustomer").val("");
                        MessageRenderer.renderErrorText("Network Problem: Time out");
                    } else{
                        MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                    }
                },
                complete:function (data) {
                },
                dataType:'json'
            });
        }else{
            $("#defaultCustomerId").val("");
            $("#defaultCustomer").val("");
        }
    }

    function executeAjaxWithdraw() {
        var customerMasterId = $("#customerMasterId").val();
        if(!customerMasterId){
            MessageRenderer.render({
                "messageBody": "Customer is not selected",
                "messageTitle": "Customer Settlement",
                "type": "0"
            });
            return false
        }
        var remainingAfterAdjustAmount = Number($("#remainingAfterAdjustAmount").val());
        var withdrawAmount = Number($("#withdrawAmount").val());
        var data = $("#gFormCustomerSettlement").serializeArray();
        data.push({'name':'selectedCustomerType', value: $('input[name=customerType]:checked').val()});

        if(!withdrawAmount){
            MessageRenderer.render({
                "messageBody": "Please Input Withdraw Amount",
                "messageTitle": "Customer Settlement",
                "type": "0"
            });
            return false
        }

        if(withdrawAmount > remainingAfterAdjustAmount){
            MessageRenderer.render({
                "messageBody": "Withdraw Amount can not greater than Remaining Balance After Adjustment",
                "messageTitle": "Customer Settlement",
                "type": "0"
            });
            return false
        }
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: data,
            url: "${request.contextPath}/${params.controller}/withdraw",
            success: function (data, textStatus) {
                MessageRenderer.render(data);
                var enterpriseId = $("#enterpriseId").val();
                reset_form("#gFormCustomerSettlement"); // Clear Form
                $("#enterpriseId").val(enterpriseId);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    var enterpriseId = $("#enterpriseId").val();
                    reset_form("#gFormCustomerSettlement"); // Clear Form
                    $("#enterpriseId").val(enterpriseId);
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
        return true
    }
</script>