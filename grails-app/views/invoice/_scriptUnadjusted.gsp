<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormUnadjustedInvoice").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormUnadjustedInvoice").validationEngine('attach');
//        reset_form("#gFormUnadjustedInvoice");
        $("#jqgrid-grid-unadjusted-invoice").jqGrid({
            %{--url: '${resource(dir:'mushak', file:'list')}',--}%
            datatype: "json",
            colNames: [
                'ID',

                'Customer Id',
                'Invoice No',
                'Invoice Date',
                'Customer Name',
                'Customer Code',
                'Invoice Amount',
                'Due Amount'
            ],
            colModel: [
                {name: 'id', index: 'id', width: 0, hidden: true},
                {name: 'customerId', index: 'customerId', width: 0, align: 'left', hidden: true},
                {name: 'invoiceNo', index: 'invoiceNo', width: 100, align: 'left'},
                {name: 'invoiceDate', index: 'invoiceDate', width: 100, align: 'center'},
                {name: 'customerName', index: 'customerName', width: 100, align: 'left'},
                {name: 'customerCode', index: 'customerCode', width: 100, align: 'left'},
                {name: 'invoiceAmount', index: 'invoiceAmount', width: 100, align: 'right'},
                {name: 'dueAmount', index: 'dueAmount', width: 100, align: 'right'}
            ],
            rowNum: 10,
            rowList: [10, 20, 30],
            pager: '#jqgrid-pager-unadjusted-invoice',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Unadjusted Invoice List",
            autowidth: true,
            autoheight: true,
            scrollOffset: 0,
            altRows: true,
            afterSaveCell: function (id, name, val, iRow, iCol) {
            },
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
            }
        });

        $('#dateFrom,#dateTo').datepicker({
            dateFormat: 'dd-mm-yy',
            changeMonth: true,
            changeYear: true
        });
        $('#dateFrom').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});
        $('#dateTo').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});

        populateCustomer();
    });

    function populateCustomer() {
        jQuery('#searchKey').autocomplete({
            minLength: '1',
            source: function (request, response) {
                //EmployeeRegister.employeeCoreInfoId = null;
                var data = {searchKey: request.term};
                var url = '${resource(dir:'invoice', file:'listCustomerByGeoAndCategory')}?entId=' + $("#enterpriseConfiguration").val();
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
            var url = '${resource(dir:'invoice', file:'popUpCustomerList')}?entId=' + $("#enterpriseConfiguration").val();
            var params = {};
            DocuAjax.html(url, params, function (html) {
                $.fancybox(html);
            });
        },
        setCustomerInformation: function (customerCoreInfoId, customerCoreInfo, code, category) {
            $("#searchKey").val(customerCoreInfo);
            $("#code").val(code);
            $("#customer").val(customerCoreInfoId);
            CustomerInfo.customerCoreInfoId = customerCoreInfoId;
        }
    };

    function searchInvoice(){
        $("#jqgrid-grid-unadjusted-invoice").setGridParam({
            url: '${resource(dir:'invoice', file:'listInvoice')}?entId=' + $("#enterpriseConfiguration").val() +
            '&customerId=' + $("#customer").val() + '&dateFrom=' + $("#dateFrom").val() + '&dateTo=' + $("#dateTo").val()
        });
        $("#jqgrid-grid-unadjusted-invoice").trigger("reloadGrid");
    }

</script>