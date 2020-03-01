<script type="text/javascript" language="Javascript">

    var branch = 1;

    $(document).ready(function () {
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormWriteOff").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormWriteOff").validationEngine('attach');
//        reset_form("#gFormWriteOff");
        $("#jqgrid-grid-writeOff").jqGrid({
            %{--url: '${resource(dir:'writeOff', file:'list')}',--}%
            datatype: "json",
            colNames: [
                'ID',

                'Select',
                'Invoice No',
                'Invoice Date',
                'Invoice Amount',
                'Due Amount',
                'Write Off Amount',
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
                    name: 'writeOffAmount', index: 'writeOffAmount', width: 100, align: 'right'
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
//            pager: '#jqgrid-pager-writeOff',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Invoice List",
            autowidth: true,
            height: 300,
//            autoheight: true,
            scrollOffset: 0,
            altRows: true,
            cellEdit: true,
            cellsubmit: 'clientArray',
            cellurl: null,
            loadComplete: function () {
//                $("#writeOffAmount").val($("#jqgrid-grid-writeOff").jqGrid('getCol', 'writeOffAmount', false, 'sum'));
                $("#writeOffAmount").val(0);
                $("#appliedAmount").val(0);
            },
            onSelectRow: function (rowid, status) {
            },
            afterSaveCell:function (rowid) {
                var due = parseFloat($("#jqgrid-grid-writeOff").jqGrid('getCell', rowid, 'dueAmount'));
                var applied = parseFloat($("#jqgrid-grid-writeOff").jqGrid('getCell', rowid, 'writeOffAmount'));
                if(applied > due){
                    MessageRenderer.render({
                        messageTitle: 'Data Error',
                        type: 2,
                        messageBody: 'Applied amount can not be greater than due amount.'
                    });
                    $("#jqgrid-grid-writeOff").jqGrid('setCell', rowid, 'writeOffAmount', 0);
                }
                loadTotalAmount();
            }
        });

        loadTerritory();
        populateCustomer();
    });

    function cboxFormatter(cellvalue, options, rowObject) {
        return '<input id="chkbox'+ options.rowId +'" type="checkbox" ' + (cellvalue == 'true' ? ' checked="checked"' : '') +
                'value="false" onchange="if(this.checked){selected(' + options.rowId + ', true)}else{selected(' + options.rowId + ', false)};"/>';
    }

    function selected(id, val) {
        $("#jqgrid-grid-writeOff").jqGrid('setCell', id, 'selected', val);
        loadTotalAmount();
//        var totalAmount = parseFloat($("#writeOffAmount").val());
//        var amount = parseFloat($("#jqgrid-grid-writeOff").jqGrid('getCell', id, 'writeOffAmount'));
//        if (val == false) {
//            $("#writeOffAmount").val(totalAmount - amount);
//        } else {
//            $("#writeOffAmount").val(totalAmount + amount);
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
                MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
            },
            complete: function () {
            },
            dataType: 'json'
        });
    }

    function loadGeo() {
        $("#territorySubArea").html('');
        $("#distributionPoint").html('');
        $("#defaultCustomer").val('');
        $("#jqgrid-grid-writeOff").jqGrid('clearGridData');
        $("#searchKey").val('');
        $("#customerMaster").val('');
        $("#customerMasterCode").val('');
        $("#writeOffAmount").val('');
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
                MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
            },
            complete: function () {
            },
            dataType: 'json'
        });
    }

    function loadDp() {
        $("#distributionPoint").html('');
        $("#defaultCustomer").val('');
        $("#jqgrid-grid-writeOff").jqGrid('clearGridData');
        $("#searchKey").val('');
        $("#customerMaster").val('');
        $("#customerMasterCode").val('');
        $("#writeOffAmount").val('');
        if ($("#territorySubArea").val() == '') {
            return false;
        }
        jQuery.ajax({
            type: 'post',
            url: "${resource(dir:'writeOff', file:'listDp')}?geoId=" + $("#territorySubArea").val(),
            success: function (data, textStatus) {
                var option = '<option value="">Please Select</option>';
                for (var i = 0; i < data.length; i++) {
                    option += '<option value="' + data[i].id + '" name="' + data[i].customer + '" id="' + data[i].customer_code + '">' + data[i].name + '</option>';
                }
                $("#distributionPoint").html(option);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
            },
            complete: function () {
            },
            dataType: 'json'
        });
    }

    function setDefaultCustomer() {
        $("#defaultCustomer").val($("#distributionPoint").find(':selected').attr('name'));
        $("#defaultCustomerCode").val($("#distributionPoint").find(':selected').attr('id'));
    }

    function showDp(key) {
        $("#jqgrid-grid-writeOff").jqGrid('clearGridData');
        $("#searchKey").val('');
        $("#customerMaster").val('');
        $("#customerMasterCode").val('');
        $("#writeOffAmount").val('');
        if (key == 1) {
            $("#forBranch").removeAttrs('hidden');
            branch = 1;
        } else {
            $("#forBranch").attr('hidden', true);
            $("#distributionPoint").val('');
            $("#defaultCustomer").val('');
            branch = 0;
        }
    }

    function populateCustomer() {
        jQuery('#searchKey').autocomplete({
            minLength: '1',
            source: function (request, response) {
                //EmployeeRegister.employeeCoreInfoId = null;
                if ($("#territorySubArea").val() == null || $("#territorySubArea").val() == '') {
                    return false;
                }
                var data;
                if (branch == 1) {
                    data = {searchKey: request.term, cat: 3, dpId: $("#distributionPoint").val()};
                } else {
                    data = {searchKey: request.term};
                }
                var url = '${resource(dir:'writeOff', file:'fetchCustomer')}?geoId=' + $("#territorySubArea").val();
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
            var url = '${resource(dir:'writeOff', file:'popupCustomerListPanel')}?geoId=' + $("#territorySubArea").val();
            if (branch == 1) {
                url = url + "&cat=3" + "&dpId=" + $("#distributionPoint").val();
            }
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
            $("#jqgrid-grid-writeOff").setGridParam({
                url: '${resource(dir:'writeOff', file:'listInvoice')}?customerId=' + $("#customerMaster").val()
            });
            $("#jqgrid-grid-writeOff").trigger("reloadGrid");
        }
    };

    function executePreConditionWriteOff() {
        // trim field vales before process.
        trim_form();
        if (!$("#gFormWriteOff").validationEngine('validate')) {
            return false;
        }
        if($("#appliedAmount").val() != $("#writeOffAmount").val()){
            MessageRenderer.render({
                messageTitle: 'Data Error',
                type: 2,
                messageBody: 'Total Applied Amount must be equal to Total Write Off Amount.'
            });
            return false;
        }
        return true;
    }

    function executeAjaxWriteOff() {
        if (!executePreConditionWriteOff()) {
            return false;
        }
        var data = $("#gFormWriteOff").serializeArray();
        var gd = $("#jqgrid-grid-writeOff").jqGrid('getRowData');
        var length = gd.length;
        for (var i = 0; i < length; i++) {
            if(gd[i].selected == 'true') {
                data.push({'name': 'items.writeOff[' + i + '].invoice.id', 'value': gd[i].id});
                data.push({'name': 'items.writeOff[' + i + '].writeOffAmount', 'value': gd[i].writeOffAmount});
            }
        }
        data.push({'name': 'isBranch', 'value': branch});

        var actionUrl = "${request.contextPath}/${params.controller}/create";
        jQuery.ajax({
            type: 'post',
            data: data,
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionWriteOff(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#territoryConfiguration").val('');
                    $("#territorySubArea").html('');
                    $("#distributionPoint").html('');
                    $("#jqgrid-grid-writeOff").jqGrid('clearGridData');
                    var ent = $("#enterpriseConfiguration").val();
                    reset_form('#gFormWriteOff');
                    $("#enterpriseConfiguration").val(ent);
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                } else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }

            },
            complete: function () {
            },
            dataType: 'json'
        });
        return false;
    }

    function executePostConditionWriteOff(result) {
        if (result.type == 1) {
            $("#territoryConfiguration").val('');
            $("#territorySubArea").html('');
            $("#distributionPoint").html('');
            $("#jqgrid-grid-writeOff").jqGrid('clearGridData');
            var ent = $("#enterpriseConfiguration").val();
            reset_form('#gFormWriteOff');
            $("#enterpriseConfiguration").val(ent);
        }
        MessageRenderer.render(result);
    }

    function applyDue(amount) {
        if(!amount){
            amount = 0;
        }
//        $("#appliedAmount").val(amount);
        var gridCollection = jQuery("#jqgrid-grid-writeOff").jqGrid('getRowData');
        var remainder = parseFloat(amount);
        for (var i = 0; i < gridCollection.length; i++) {
            var due = parseFloat(gridCollection[i].dueAmount);
            if(remainder <= due){
                jQuery("#jqgrid-grid-writeOff").jqGrid('setCell', gridCollection[i].id, 'writeOffAmount', remainder.toFixed(2));
                if(remainder == 0){
                    jQuery("#jqgrid-grid-writeOff").jqGrid('setCell', gridCollection[i].id, 'select', 'false');
                    jQuery("#jqgrid-grid-writeOff").jqGrid('setCell', gridCollection[i].id, 'selected', 'false');
                }else{
                    jQuery("#jqgrid-grid-writeOff").jqGrid('setCell', gridCollection[i].id, 'select', 'true');
                    jQuery("#jqgrid-grid-writeOff").jqGrid('setCell', gridCollection[i].id, 'selected', 'true');
                }
            }else{
                jQuery("#jqgrid-grid-writeOff").jqGrid('setCell', gridCollection[i].id, 'writeOffAmount', due.toFixed(2));
                jQuery("#jqgrid-grid-writeOff").jqGrid('setCell', gridCollection[i].id, 'select', 'true');
                jQuery("#jqgrid-grid-writeOff").jqGrid('setCell', gridCollection[i].id, 'selected', 'true');
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
        var avail = parseFloat($('#writeOffAmount').val());
        var deselect = -1;
        var gridData = $("#jqgrid-grid-writeOff").jqGrid('getRowData');
        for(var i = 0; i < gridData.length; i++){
            if(gridData[i].selected == 'true'){
                sum = sum + parseFloat(gridData[i].writeOffAmount);
            }
            if(sum > avail){
                MessageRenderer.render({
                        messageTitle: 'Can not add data',
                        type: 2,
                        messageBody: 'Total Applied amount becomes greater than Total received amount.'
                    });
                deselect = i;
                break;
            }
        }
        if(deselect != -1){
            sum = sum - parseFloat(jQuery("#jqgrid-grid-writeOff").jqGrid('getCell', gridData[deselect].id, 'writeOffAmount'));
            $('#appliedAmount').val(sum);
            while(deselect < gridData.length){
//                document.getElementById("chkbox" + gridData[deselect].id).checked = false;
                jQuery("#jqgrid-grid-writeOff").jqGrid('setCell', gridData[deselect].id, 'select', 'false');
                jQuery("#jqgrid-grid-writeOff").jqGrid('setCell', gridData[deselect].id, 'selected', 'false');
                deselect ++;
            }
        }else{
            $('#appliedAmount').val(sum);
        }
    }
</script>