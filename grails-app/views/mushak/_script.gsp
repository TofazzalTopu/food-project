<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormMushak").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormMushak").validationEngine('attach');
//        reset_form("#gFormMushak");
        $("#jqgrid-grid-mushak").jqGrid({
            %{--url: '${resource(dir:'mushak', file:'list')}',--}%
            datatype: "json",
            colNames: [
                'ID',

                'Product Name',
                'Quantity',
                'Unit Price',
                'SD Imposable Price',
                'SD Percentage',
                'SD Amount',
                'VAT Imposable Price',
                'VAT Percentage',
                'VAT Amount',
                'Total Price',
                ''
            ],
            colModel: [
                {name: 'id', index: 'id', width: 0, hidden: true},
                {name: 'finishProduct', index: 'finishProduct', width: 150, align: 'left'},
                {
                    name: 'quantity', index: 'quantity', width: 70, align: 'left', sorttype: 'number',
                    formatter: "number",
                    formatoptions: {decimalSeparator: ".", thousandsSeparator: ",", decimalPlaces: 0},
                    editable: true,
                    editrules: {number: true}
                },
                {name: 'unitPrice', index: 'unitPrice', width: 0, align: 'left', hidden: true},
                {name: 'sdImposable', index: 'sdImposable', width: 100, align: 'right'},
                {name: 'sdPercent', index: 'sdPercent', width: 0, hidden: true},
                {name: 'sdAmount', index: 'sdAmount', width: 100, align: 'right'},
                {name: 'vatImposable', index: 'vatImposable', width: 100, align: 'right'},
                {name: 'vatPercent', index: 'vatPercent', width: 0, hidden: true},
                {name: 'vatAmount', index: 'vatAmount', width: 100, align: 'right'},
                {name: 'totalPrice', index: 'totalPrice', width: 100, align: 'right'},
                {name: 'delete', index: 'delete', width: 40, align: 'center'}
            ],
            rowNum: 10,
            rowList: [10, 20, 30],
            pager: '#jqgrid-pager-mushak',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Product Price List <span class='mendatory_field'>*</span>",
            autowidth: true,
            autoheight: true,
            scrollOffset: 0,
            altRows: true,
            cellEdit: true,
            cellsubmit: 'clientArray',
            cellurl: null,
            afterSaveCell: function (id, name, val, iRow, iCol) {
                calculatePrice(id);
            },
            loadComplete: function () {
                chkGrid();
                calculateTotal();
            },
            onSelectRow: function (rowid, status) {
                executeEditMushak();
            }
        });

        $('#date,#challanHandoverDate,#deliveryDate').datepicker({
            dateFormat: 'dd-mm-yy',
            changeMonth: true,
            changeYear: true
        });
        $('#challanHandoverDate').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});
        $('#deliveryDate').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});
        $('#date').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});
        $('#challanHandoverTime').timepicker({
            timeFormat: 'hh:mm tt',
            ampm: true
        });
        loadDp();
        loadInvoice();
    });

    function loadDp(){
        if('${dpSize}' == '0'){
            $("#dpName").val('No Distribution Point Found.');
            $("#distributionPointId").val(0);
        }else{
            var size = ${dpSize};
            if(size == 1){
                $("#dpName").val(${dpList}[0].name);
                $("#distributionPointId").val(${dpList}[0].id);
            }else{
                $("#single").attr('hidden', true);
                $("#multi").removeAttrs('hidden');
                var id = '';
                var options = '<option value="">Please Select</option>';
                for (var i = 0; i < size; i++) {
                    if('' + ${dpList}[i].is_factory == 'true'){
                        id = ${dpList}[i].id;
                    }
                    options += '<option value="' + ${dpList}[i].id + '">' + ${dpList}[i].name + '</option>';
                }
                $("#distributionPoint").html(options);
                $("#distributionPoint").val(id);
                $("#distributionPoint").attr('disabled', true);
            }
        }
    }

    function loadInvoice() {
        jQuery('#searchInvoiceKey').autocomplete({
            minLength: '1',
            source: function (request, response) {
                var data = {searchKey: request.term};
                var dpId = null;
                if($("#distributionPointId").val()) {
                    dpId = $("#distributionPointId").val();
                }else{
                    dpId = $("#distributionPoint").val();
                }
                var url = '${resource(dir:'mushak', file:'listInvoice')}?id=' + dpId + '&date=' + $("#date").val();
                DocuAutoComplete.setSpinnerSelector('searchInvoiceKey').execute(response, url, data, function (item) {
                    item['label'] = item['code'];
                    item['value'] = item['label'];
                    return item;
                });
            },
            select: function (event, ui) {
                CustomerInfo.setInvoiceInformation(ui.item.id, ui.item.code, ui.item.name, ui.item.present_address, ui.item.date_created);
            }
        }).data("autocomplete")._renderItem = function (ul, item) {
            var accountstype = "";
            if (item) {
                accountstype = '<div style="font-size: 9px; color:#326E93;">' + " Code: " + item.code + ", Customer: " +
                item.name + ", Invoice Date: " + item.date_created + '</div>';
            }
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + accountstype).appendTo(ul);
        };

        $('#search-btn-customer-invoice-id').click(function () {
            CustomerInfo.popupInvoiceListPanel();
        });
    }

    var CustomerInfo = {
        customerCoreInfoId: null,
        popupInvoiceListPanel: function () {
            var url = '';
            var dpId = null;
            if($("#distributionPointId").val()) {
                dpId = $("#distributionPointId").val();
            }else{
                dpId = $("#distributionPoint").val();
            }
            url = '${resource(dir:'mushak', file:'popupInvoiceListPanel')}?id=' + dpId + '&date=' + $("#date").val();
            var params = {};
            DocuAjax.html(url, params, function (html) {
                $.fancybox(html);
            });
        },
        setInvoiceInformation: function (id, code, name, address, date) {
            $("#searchInvoiceKey").val(code);
            $("#invoice").val(id);
            $("#name").val(name);
            $("#address").val(address);
            $("#jqgrid-grid-mushak").setGridParam({
                url: '${resource(dir:'mushak', file:'listInvoiceDetails')}?id=' + id
            });
            $("#jqgrid-grid-mushak").trigger("reloadGrid");
        }
    };

    function chkGrid(){
        var gd = $("#jqgrid-grid-mushak").jqGrid('getRowData');
        var length = gd.length;
        if(length <= 0){
            MessageRenderer.render({messageTitle: 'Grid Empty', type: 2, messageBody: 'Selected Invoice does not contain any product with an active Vat Rate.'});
        }
    }

    function calculateTotal(){
        var col = $("#jqgrid-grid-mushak").jqGrid('getCol', 'vatAmount');
        var total = 0;
        for(var i = 0; i < col.length; i++){
            if(col[i]) {
                total = total + parseFloat(col[i]);
            }
        }
        $("#totalMushakAmount").val(total);
    }

    function deleteItemFromGrid(id) {
        var myGrid = $("#jqgrid-grid-mushak");
        myGrid.jqGrid('delRowData', id);
        calculateTotal();
    }

    function calculatePrice(id) {
        var rowData = $("#jqgrid-grid-mushak").jqGrid('getRowData', id);
        rowData.sdImposable = parseFloat(rowData.unitPrice) * parseFloat(rowData.quantity);
        rowData.sdAmount = rowData.sdImposable * parseFloat(rowData.sdPercent) / 100;
        rowData.vatImposable = rowData.sdAmount + rowData.sdImposable;
        rowData.vatAmount = rowData.vatImposable * parseFloat(rowData.vatPercent) / 100;
        rowData.totalPrice = rowData.vatImposable + rowData.vatAmount;
        $('#jqgrid-grid-mushak').jqGrid('setRowData', id, rowData);
        calculateTotal();
    }

    function executePreConditionMushak() {
        // trim field vales before process.
        $("#enterpriseConfiguration").removeAttrs('disabled');
        $("#enterpriseTin").removeAttrs('disabled');
        $("#enterpriseAddress").removeAttrs('disabled');
        $("#distributionPoint").removeAttrs('disabled');
        trim_form();
        if (!$("#gFormMushak").validationEngine('validate')) {
            return false;
        }
        return true;
    }
    function executeAjaxMushak() {
        if (!executePreConditionMushak()) {
            return false;
        }
        var data = $("#gFormMushak").serializeArray();
        var gd = $("#jqgrid-grid-mushak").jqGrid('getRowData');
        var length = gd.length;
        if(length <= 0){
            var x = {messageTitle: 'Grid Empty', type: 2, messageBody: 'Given Invoice does not contain any product with an active Vat Rate.'};
            executePostConditionMushak(x);
            return false;
        }
        for (var i = 0; i < length; i++) {
            data.push({'name': 'items.mushakDetails[' + i + '].finishProduct.id', 'value': gd[i].id});
            data.push({'name': 'items.mushakDetails[' + i + '].quantity', 'value': gd[i].quantity});
            data.push({'name': 'items.mushakDetails[' + i + '].rate', 'value': gd[i].unitPrice});
            data.push({'name': 'items.mushakDetails[' + i + '].sdAmount', 'value': gd[i].sdAmount});
            data.push({'name': 'items.mushakDetails[' + i + '].vatAmount', 'value': gd[i].vatAmount});
            data.push({'name': 'items.mushakDetails[' + i + '].totalAmount', 'value': gd[i].totalPrice});
            data.push({'name': 'items.mushakDetails[' + i + '].sdPercent', 'value': gd[i].sdPercent});
            data.push({'name': 'items.mushakDetails[' + i + '].vatPercent', 'value': gd[i].vatPercent});
        }

        var actionUrl = "${request.contextPath}/${params.controller}/create";
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: data,
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionMushak(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#enterpriseConfiguration").attr('disabled', 'true');
                    $("#enterpriseTin").attr('disabled', 'true');
                    $("#enterpriseAddress").attr('disabled', 'true');
                    $("#distributionPoint").attr('disabled', 'true');
                    if (result.type == 1) {
                        $("#jqgrid-grid-mushak").jqGrid('clearGridData');
                        var name = $("#dpName").val();
                        var id = $("#distributionPointId").val();
                        var entId = $("#enterpriseConfiguration").val();
                        clear_form('#gFormMushak');
                        $("#dpName").val(name);
                        $("#distributionPointId").val(id);
                        $("#enterpriseConfiguration").val(entId);
                    }

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
    function executePostConditionMushak(result) {
        $("#enterpriseConfiguration").attr('disabled', 'true');
        $("#enterpriseTin").attr('disabled', 'true');
        $("#enterpriseAddress").attr('disabled', 'true');
        $("#distributionPoint").attr('disabled', 'true');
        if (result.type == 1) {
            $("#jqgrid-grid-mushak").jqGrid('clearGridData');
            var name = $("#dpName").val();
            var id = $("#distributionPointId").val();
            var entId = $("#enterpriseConfiguration").val();
            clear_form('#gFormMushak');
            $("#dpName").val(name);
            $("#distributionPointId").val(id);
            $("#enterpriseConfiguration").val(entId);
        }
        MessageRenderer.render(result);
    }
</script>