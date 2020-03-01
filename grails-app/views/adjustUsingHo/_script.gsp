<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormAdjustUsingHo").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormAdjustUsingHo").validationEngine('attach');
//        reset_form("#gFormAdjustUsingHo");
        $("#jqgrid-grid-adjustUsingHo").jqGrid({
            %{--url: '${resource(dir:'adjustUsingHo', file:'list')}',--}%
            datatype: "json",
            colNames: [
                'ID',

                'Select',
                'Invoice No',
                'Invoice Date',
                'Invoice Amount',
                'Due Amount',
                'Amount To Be Adjusted',
                'Selected'
            ],
            colModel: [
                {name: 'id', index: 'id', width: 0, hidden: true},
                {
                    name: 'select', width: 50, align: 'center',
                    formatter: cboxFormatter, formatoptions: {disabled: false},
                    edittype: "checkbox", editoptions: {value: "true:false", defaultValue: "false"},
                    stype: "select", searchoptions: {
                    sopt: ["eq", "ne"],
                    value: ":Any;true:true;false:false"
                }
                },
                {name: 'invoiceNo', index: 'invoiceNo', width: 100, align: 'left'},
                {name: 'invoiceDate', index: 'invoiceDate', width: 100, align: 'center'},
                {name: 'invoiceAmount', index: 'invoiceAmount', width: 100, align: 'right'},
                {name: 'dueAmount', index: 'dueAmount', width: 100, align: 'right'},
                {
                    name: 'adjustedAmount', index: 'adjustedAmount', width: 100, align: 'right'
                    , sorttype: 'number',
                    formatter: "number",
                    formatoptions: {decimalSeparator: ".", thousandsSeparator: "", decimalPlaces: 2},
                    editable: true,
                    editrules: {number: true}
                },
                {name: 'selected', index: 'selected', width: 0, align: 'left', hidden: true}
            ],
            rowNum: 10,
            rowList: [10, 20, 30],
//            pager: '#jqgrid-pager-adjustUsingHo',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Unadjusted Invoice List",
            autowidth: true,
            height: 300,
//            autoheight: true,
            scrollOffset: 0,
            altRows: true,
            cellEdit: true,
            cellsubmit: 'clientArray',
            cellurl: null,
            loadComplete: function () {
//                $("#totalAmount").val($("#jqgrid-grid-adjustUsingHo").jqGrid('getCol', 'adjustedAmount', false, 'sum'));
                $("#totalAmount").val(0);
                $('#totalAdjustedAmount').val(0);
            },
            onSelectRow: function (rowid, status) {
                executeEditAdjustUsingHo();
            },
            afterSaveCell:function (rowid) {
                var due = parseFloat($("#jqgrid-grid-adjustUsingHo").jqGrid('getCell', rowid, 'dueAmount'));
                var applied = parseFloat($("#jqgrid-grid-adjustUsingHo").jqGrid('getCell', rowid, 'adjustedAmount'));
                if(applied > due){
                    MessageRenderer.render({
                        messageTitle: 'Data Error',
                        type: 2,
                        messageBody: 'Adjusted amount can cot be greater than due amount.'
                    });
                    $("#jqgrid-grid-adjustUsingHo").jqGrid('setCell', rowid, 'adjustedAmount', 0);
                }
                loadTotalAmount();
            }
        });
        loadTerritory();
        populateCustomer();
    });

    function cboxFormatter(cellvalue, options, rowObject) {
        return '<input type="checkbox" ' + (cellvalue == 'true' ? ' checked="checked"' : '') +
                'value="false" onchange="if(this.checked){selected(' + options.rowId + ', true)}else{selected(' + options.rowId + ', false)};"/>';
    }

    function selected(id, val) {
        $("#jqgrid-grid-adjustUsingHo").jqGrid('setCell', id, 'selected', val);
        loadTotalAmount();
//        var totalAmount = parseFloat($("#totalAmount").val());
//        var amount = parseFloat($("#jqgrid-grid-adjustUsingHo").jqGrid('getCell', id, 'adjustedAmount'));
//        if (val == false) {
//            $("#totalAmount").val(totalAmount - amount);
//        } else {
//            $("#totalAmount").val(totalAmount + amount);
//        }
    }

    function loadTerritory() {
        jQuery.ajax({
            type: 'post',
            url: "${resource(dir:'writeOff', file:'listTerritory')}?entId=" + $("#enterpriseConfiguration").val(),
            success: function (data, textStatus) {
                var option = '<option value="">Please Select</option>';
                for (var i = 0; i < data.length; i++) {
                    option += '<option value="' + data[i].id + '">' + data[i].name + '</option>';
                }
                $("#territoryConfiguration").html(option);
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
    }

    function loadGeo() {
        $("#territorySubArea").html('');
        if ($("#territoryConfiguration").val() == '') {
            return false;
        }
        jQuery.ajax({
            type: 'post',
            url: "${resource(dir:'writeOff', file:'listGeo')}?territoryId=" + $("#territoryConfiguration").val(),
            success: function (data, textStatus) {
                var option = '<option value="">Please Select</option>';
                for (var i = 0; i < data.length; i++) {
                    option += '<option value="' + data[i].id + '">' + data[i].name + '</option>';
                }
                $("#territorySubArea").html(option);
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
    }

    function populateCustomer() {
        jQuery('#searchKey').autocomplete({
            minLength: '1',
            source: function (request, response) {
                //EmployeeRegister.employeeCoreInfoId = null;
                if ($("#territorySubArea").val() == null || $("#territorySubArea").val() == '') {
                    return false;
                }
                var data = {searchKey: request.term};
                var url = '${resource(dir:'adjustUsingHo', file:'fetchCustomer')}?geoId=' + $("#territorySubArea").val();
                DocuAutoComplete.setSpinnerSelector('searchKey').execute(response, url, data, function (item) {
                    item['label'] = item['name'];
                    item['value'] = item['label'];
                    return item;
                });
            },
            select: function (event, ui) {
                CustomerInfo.setCustomerInformation(ui.item.id, ui.item.value, ui.item.code, ui.item.category);
            }
        }).data("autocomplete")._renderItem = function (ul, item) {
            var custometDetailts = "";
            if (item) {
                custometDetailts = '<div style="font-size: 9px; color:#326E93;">' + " Code: " + item.code + ", Name: " + item.name + ", Category: " + item.category + '</div>';
            }
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + custometDetailts).appendTo(ul);
        };

        $('#search-btn-customer-id').click(function () {
            CustomerInfo.popupCustomerListPanel();
        });

    }

    var CustomerInfo = {
        customerCoreInfoId: null,
        popupCustomerListPanel: function () {
            if ($("#territorySubArea").val() == null || $("#territorySubArea").val() == '') {
                return false;
            }
            var url = '${resource(dir:'adjustUsingHo', file:'popupCustomerListPanel')}?geoId=' + $("#territorySubArea").val();
            var params = {};
            DocuAjax.html(url, params, function (html) {
                $.fancybox(html);
            });
        },
        setCustomerInformation: function (customerCoreInfoId, customerCoreInfo, code, category) {
            $("#searchKey").val(customerCoreInfo);
            $("#customerMaster").val(customerCoreInfoId);
            $("#customerMasterCode").val(code);
            CustomerInfo.customerCoreInfoId = customerCoreInfoId;
            $("#jqgrid-grid-adjustUsingHo").setGridParam({
                url: '${resource(dir:'writeOff', file:'listInvoice')}?customerId=' + $("#customerMaster").val()
            });
            $("#jqgrid-grid-adjustUsingHo").trigger("reloadGrid");
        }
    };

    function executePreConditionAdjustUsingHo() {
        // trim field vales before process.
        trim_form();
        if (!$("#gFormAdjustUsingHo").validationEngine('validate')) {
            return false;
        }
        if($("#totalAmount").val() != $("#totalAdjustedAmount").val()){
            MessageRenderer.render({
                messageTitle: 'Data Error',
                type: 2,
                messageBody: 'Total Adjusted Amount must be equal to Total Amount.'
            });
            return false;
        }
        return true;
    }

    function executeAjaxAdjustUsingHo() {
        if (!executePreConditionAdjustUsingHo()) {
            return false;
        }
        var actionUrl = "${request.contextPath}/${params.controller}/create";

        var data = $("#gFormAdjustUsingHo").serializeArray();
        var gd = $("#jqgrid-grid-adjustUsingHo").jqGrid('getRowData');
        var length = gd.length;
        for (var i = 0; i < length; i++) {
            if(gd[i].selected == 'true') {
                data.push({'name': 'items.adjustUsingHoDetails[' + i + '].invoice.id', 'value': gd[i].id});
                data.push({'name': 'items.adjustUsingHoDetails[' + i + '].adjustedAmount', 'value': gd[i].adjustedAmount});
            }
        }
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: data,
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionAdjustUsingHo(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#territoryConfiguration").val('');
                    $("#territorySubArea").html('');
                    $("#jqgrid-grid-adjustUsingHo").jqGrid('clearGridData');
                    var ent = $("#enterpriseConfiguration").val();
                    reset_form('#gFormAdjustUsingHo');
                    $("#enterpriseConfiguration").val(ent);
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                }else{
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

    function executePostConditionAdjustUsingHo(result) {
        if (result.type == 1) {
            $("#territoryConfiguration").val('');
            $("#territorySubArea").html('');
            $("#jqgrid-grid-adjustUsingHo").jqGrid('clearGridData');
            var ent = $("#enterpriseConfiguration").val();
            reset_form('#gFormAdjustUsingHo');
            $("#enterpriseConfiguration").val(ent);
        }
        MessageRenderer.render(result);
    }

    function applyDue(amount) {
        if(!amount){
            amount = 0;
        }
//        $("#appliedAmount").val(amount);
        var gridCollection = jQuery("#jqgrid-grid-adjustUsingHo").jqGrid('getRowData');
        var remainder = parseFloat(amount);
        for (var i = 0; i < gridCollection.length; i++) {
            var due = parseFloat(gridCollection[i].dueAmount);
            if(remainder <= due){
                jQuery("#jqgrid-grid-adjustUsingHo").jqGrid('setCell', gridCollection[i].id, 'adjustedAmount', remainder.toFixed(2));
                if(remainder == 0){
                    jQuery("#jqgrid-grid-adjustUsingHo").jqGrid('setCell', gridCollection[i].id, 'select', 'false');
                    jQuery("#jqgrid-grid-adjustUsingHo").jqGrid('setCell', gridCollection[i].id, 'selected', 'false');
                }else{
                    jQuery("#jqgrid-grid-adjustUsingHo").jqGrid('setCell', gridCollection[i].id, 'select', 'true');
                    jQuery("#jqgrid-grid-adjustUsingHo").jqGrid('setCell', gridCollection[i].id, 'selected', 'true');
                }
            }else{
                jQuery("#jqgrid-grid-adjustUsingHo").jqGrid('setCell', gridCollection[i].id, 'adjustedAmount', due.toFixed(2));
                jQuery("#jqgrid-grid-adjustUsingHo").jqGrid('setCell', gridCollection[i].id, 'select', 'true');
                jQuery("#jqgrid-grid-adjustUsingHo").jqGrid('setCell', gridCollection[i].id, 'selected', 'true');
            }
            remainder = remainder - due;
            if(remainder <= 0){
                remainder = 0;
            }
        }
        loadTotalAmount();
    }

    function loadTotalAmount() {
        var sum = 0;
        var avail = parseFloat($('#totalAmount').val());
        var deselect = -1;
        var gridData = $("#jqgrid-grid-adjustUsingHo").jqGrid('getRowData');
        for(var i = 0; i < gridData.length; i++){
            if(gridData[i].selected == 'true'){
                sum = sum + parseFloat(gridData[i].adjustedAmount);
            }
            if(sum > avail){
                MessageRenderer.render({
                    messageTitle: 'Can not add data',
                    type: 2,
                    messageBody: 'Total Adjusted amount becomes greater than Total Received amount.'
                });
                deselect = i;
                break;
            }
        }
        if(deselect != -1){
            sum = sum - parseFloat(jQuery("#jqgrid-grid-adjustUsingHo").jqGrid('getCell', gridData[deselect].id, 'adjustedAmount'));
            $('#totalAdjustedAmount').val(sum);
            while(deselect < gridData.length){
//                document.getElementById("chkbox" + gridData[deselect].id).checked = false;
                jQuery("#jqgrid-grid-adjustUsingHo").jqGrid('setCell', gridData[deselect].id, 'select', 'false');
                jQuery("#jqgrid-grid-adjustUsingHo").jqGrid('setCell', gridData[deselect].id, 'selected', 'false');
                deselect ++;
            }
        }else{
            $('#totalAdjustedAmount').val(sum);
        }
    }

    function clearData(){
        $("#searchKey").val('');
        $("#customerMaster").val('');
        $("#customerMasterCode").val('');
        $("#totalAmount").val('');
        $("#jqgrid-grid-adjustUsingHo").jqGrid('clearGridData');
    }

</script>