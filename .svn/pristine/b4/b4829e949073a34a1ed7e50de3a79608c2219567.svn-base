<script type="text/javascript" language="Javascript">
    $(document).ready(function(){
        $("#asOfDate").datepicker(
                {
                    dateFormat: 'dd-mm-yy',
                    changeMonth: true,
                    changeYear: true,
                    maxDate:0
                });
        $('#asOfDate').val($.datepicker.formatDate('dd-mm-yy',new Date()));
        $('#asOfDate').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});

        $('#territory').focus();
        $('#gridTP').hide();
        $('#defaultArea').hide();

        jQuery('#defaultCustomer').autocomplete({
            minLength: '1',
            source: function (request, response) {
                if($('#distributionPoint').val()){
                    MessageRenderer.render({
                        "messageBody": "Distribution point is selected.",
                        "messageTitle": "View Security Deposit",
                        "type": "0"
                    });
                    return false;
                }
                //EmployeeRegister.employeeCoreInfoId = null;
                if ($('#territory').val()) {
//                    $('#popEmpDetails').html("");
                    var data = {searchKey: request.term};
                    var url = "${request.contextPath}/${params.controller}/popUpCustomerListByTerritory?territoryId=" + $('#territory').val() + "&key=" + $('#defaultCustomer').val();
                    DocuAutoComplete.setSpinnerSelector('defaultCustomer').execute(response, url, data, function (item) {
                        item['label'] = item['name'] + " [" + item['code'] + "]";
                        item['value'] = item['label'];
                        return item;
                    });
                } else {
                    MessageRenderer.render({
                        "messageBody": "Please select a territory.",
                        "messageTitle": "View Security Deposit",
                        "type": "0"
                    });
                    return false;
                }

            },
            select: function (event, ui) {
                CustomerInfo.setCustomerInformation(ui.item.id, ui.item.code);
            }
        }).data("autocomplete")._renderItem = function (ul, item) {
            var accountstype = "";
            if (item.id) {
                accountstype = '<div style="font-size: 9px; color:#326E93;">' +" Legacy ID: " + item.legacy_id + ", Code: " +item.code+", Name: "+item.name+", Geo: "+item.geo + '</div>';
            }
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + accountstype).appendTo(ul);
        };
    });

    function clearData(){
        $('#defaultCustomer').val('');
        $('#defaultCustomerId').val('');
        $('#defaultCustomerCode').val('');
        $('#availableDPSecurityDeposit').val('');
        $('#gridTP').hide();
        $('#defaultArea').hide();
    }

    function clearAll(){
        $('#territory').val('');
        $('#distributionPoint').val('');
        clearData();
    }

    function clearTp(){
        $("#jqgrid-tp-grid").trigger("reloadGrid");
        $('#tpId').val('');
    }

    function loadTerritory(id){
        var actionUrl = "${request.contextPath}/${params.controller}/getTerritoryListByEnterprise";
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: {enterpriseId:id},
            url: actionUrl,
            success: function (data, textStatus) {
                clearData();
                $('#territory option').remove();
                $('#territory').append('<option value="">'+ " - Select Territory - " +'</option>');
                for(key in data){
                    $('#territory').append('<option value="'+ data[key].id +'">'+ data[key].name +'</option>');
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $('#territory option').remove();
                    $('#territory').append('<option value="">'+ " - Select Territory - " +'</option>');
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
    }

    function loadDP(id){
        var actionUrl = "${request.contextPath}/${params.controller}/getDpListByTerritory";
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: {territoryId:id},
            url: actionUrl,
            success: function (data, textStatus) {
                clearData();
                $('#distributionPoint option').remove();
                $('#distributionPoint').append('<option value="">'+ " - Select DP - " +'</option>');
                for(key in data){
                    $('#distributionPoint').append('<option value="'+ data[key].id +'">'+ data[key].name +'</option>');
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                MessageRenderer.renderErrorText(XMLHttpRequest.responseText)
            },
            complete: function () {
                SubmissionLoader.hideFrom();
            },
            dataType: 'json'
        });
    }

    function loadDpDefaultCustomer(id){
        if(id == ''){
            clearData();
            clearTp();
            $('#gridTP').hide();
            return false
        }

        var actionUrl = "${request.contextPath}/${params.controller}/getDpDefaultCustomer";
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: {dpId:id, date:$('#asOfDate').val()},
            url: actionUrl,
            success: function (data, textStatus) {
                clearData();
                if(data == 0){
                    MessageRenderer.render({
                        "messageBody": "No default customer is found!",
                        "messageTitle": "Security Deposit",
                        "type": "0"
                    });
                    return false;
                }else{
                    $('#defaultCustomer').val(data.defaultCustomerName);
                    $('#defaultCustomerId').val(data.defaultCustomerId);
                    $('#defaultCustomerCode').val(data.defaultCustomerCode);

                    //getOtherCustomersSd(data.default_customer_id);
                    $('#defaultArea').show();
                    if ($('#defaultCustomerId').val()) {
                        loadDpDefaultCustomersTp(data.defaultCustomerId);
                    }
                    $('#availableDPSecurityDeposit').val(data.defaultCustomerSD_Balance);
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
            },
            complete: function () {
                SubmissionLoader.hideFrom();
            },
            dataType: 'json'
        });
    }

    function loadDpDefaultCustomersTp(id){
        var actionUrl = "${request.contextPath}/${params.controller}/getDpDefaultCustomersTp";
        var dpId = $("#distributionPoint").val();
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: {dpId:dpId, asOfDate:$('#asOfDate').val()},
            url: actionUrl,
            success: function (data, textStatus) {
                if(data == 0){
                    $('#gridTP').hide();
                    MessageRenderer.render({
                        "messageBody": "No Available Trade Partners!",
                        "messageTitle": "Security Deposit",
                        "type": "0"
                    });
                    return false;
                }else{
                    clearTp();
                    loadTpsGrid(data);
                    $('#gridTP').show();
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
            },
            complete: function () {
                SubmissionLoader.hideFrom();
            },
            dataType: 'json'
        });
    }

    function loadTpsGrid(data){
        var totalAmount = 0;
        $("#jqgrid-tp-grid").jqGrid({
            datatype: "local",
            data: data,
            colNames: [
                'SI',
                '',
                'Customer Name',
                '',
                'Customer ID',
                'Available Trade Partner Security Deposit'
            ],
            colModel: [
                {name: 'si', index: 'si', width: 30},
                {name: 'select', index: 'select', width: 30, hidden:true},
                {name: 'customer_name', index: 'customer_name', width: 130},
                {name: 'customer_master_id', index: 'customer_master_id', width: 100,hidden:true},
                {name: 'code', index: 'code', width: 110},
                {
                    name: 'amount',
                    index: 'amount',
                    width: 250,
                    align: 'center',
                    editable: true,
                    edittype: 'text',
                    resizable: true,
                    editoptions: {},
                    editrules: {}
                }
            ],
            rowNum: 20,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#jqgrid-tp-pager',
            sortname: 'customer_name',
            viewrecords: true,
            sortorder: "asc",
            caption: "Available Trade Partners",
            autowidth: false,
            height: true,
//            footerrow:true,
            scrollOffset: 0,
            loadComplete: function () {
                var totalAmount = 0;
                var ids = jQuery("#jqgrid-tp-grid").jqGrid('getDataIDs');
                for (key in ids) {
                    var id = $("#jqgrid-tp-grid").getCell(ids[key], 'customer_master_id');
                    var amount = $("#jqgrid-tp-grid").getCell(ids[key], 'amount');

                    totalAmount += parseFloat(amount);

                    $("#jqgrid-tp-grid").jqGrid('setRowData', ids[key], {
                        si:ids[key],
                        select: '<input name="tp-select" type="radio" id="tp-pool-radio-' + id + '" class="tp-pool-radio" val="' +
                        amount + '" onclick="getTotalDepositAmount('+id+','+amount+');" />'});
                }
            },
            onSelectRow: function (rowid, status) {
                var id = $("#jqgrid-tp-grid").getCell(rowid, 'customer_master_id');
                var amount = $("#jqgrid-tp-grid").getCell(rowid, 'amount');
                $('#tp-pool-radio-'+id).attr('checked',true);
                getTotalDepositAmount(id,amount);
            }
        });
        $("#jqgrid-tp-grid").jqGrid('navGrid', '#jqgrid-tp-pager', {
            edit: false,
            add: false,
            del: false,
            search: false
        });
        $(".ui-pg-selbox").children().each(function() {
            if ($(this).val() == -1) {
                $(this).html('All')
            }
        });

        jQuery("#jqgrid-tp-grid")
                .jqGrid('setGridParam',
                {
                    datatype: 'local',
                    data:data
                })
                .trigger("reloadGrid");

//        console.log(data);
//        alert(data.length);
        if(data.length > 0){
            for(var i=0; i<data.length; i++){
                totalAmount += data[i].amount;
            }
        }
//        alert(totalAmount);
        $('#totalAvailableSD').val(totalAmount);
    }

    function getTotalDepositAmount(id,amount){
        if($('#tp-pool-radio-' + id).is(':checked')){
//            $('#totalAvailableSD').val(amount);
            $('#tpId').val(id);
        }
    }

    function updateDefaultCustomer(){
        if(!$('#defaultCustomerId').val()){
            MessageRenderer.render({
                messageTitle: 'Security Deposit',
                type: 0,
                messageBody: 'No default customer is available.'
            });
            return false;
        }
        var url = '${resource(dir:'viewAndEditSecurityDeposit', file:'popupDefaultCustomerListPanel')}';
        var params = {customerId:$('#defaultCustomerId').val(), date:$('#asOfDate').val()};
        DocuAjax.html(url, params, function (html) {
            $.fancybox(html);
        });
    }
    function updateTpCustomer(){
        if(!$('#tpId').val()){
            MessageRenderer.render({
                messageTitle: 'Security Deposit',
                type: 0,
                messageBody: 'Please select a trade partner first.'
            });
            return false;
        }
        var url = '${resource(dir:'viewAndEditSecurityDeposit', file:'popupDefaultCustomerListPanel')}';
        var params = {customerId:$('#tpId').val(), date:$('#asOfDate').val()};
        DocuAjax.html(url, params, function (html) {
            $.fancybox(html);
        });
    }


    $('#search-btn-customer-id').click(function () {
        if($('#distributionPoint').val()){
            MessageRenderer.render({
                "messageBody": "Distribution point is selected.",
                "messageTitle": "View Security Deposit",
                "type": "0"
            });
            return false;
        }

        if ($('#territory').val()) {
            CustomerInfo.popupCustomerListPanel($('#territory').val());
        }
        else {
            MessageRenderer.render({
                "messageBody": "Please select a territory.",
                "messageTitle": "View Security Deposit",
                "type": "0"
            });
            return false;
        }
    });

    var CustomerInfo = {
        customerCoreInfoId: null,
        popupCustomerListPanel: function (territoryId) {
            var url = '${resource(dir:'viewAndEditSecurityDeposit', file:'popUpCustomerListByTerritory')}';
            var params = {territoryId: territoryId};
            DocuAjax.html(url, params, function (html) {
                $.fancybox(html);
            });
        },

        setCustomerInformation: function (customerCoreInfoId, code, name, securityDeposit) {
            SubmissionLoader.showTo();
            $("#defaultCustomer").val(name);
            $("#defaultCustomerCode").val(code);

            CustomerInfo.customerCoreInfoId = customerCoreInfoId;
            getOtherCustomersSd(customerCoreInfoId);
            SubmissionLoader.hideFrom();
        }
    }

    function getOtherCustomersSd(cId){
        var url = '${resource(dir:'viewAndEditSecurityDeposit', file:'getOtherCustomersSd')}';
        var params = {cId: cId, date:$('#asOfDate').val()};
        DocuAjax.json(url, params, function (data) {
            $("#availableDPSecurityDeposit").val(data.securityDeposit?data.securityDeposit:0);
        });
    }
</script>