<script type="text/javascript" language="Javascript">

    var multi = 0;

    $(document).ready(function () {
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gViewProcessedMarketReturn").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gViewProcessedMarketReturn").validationEngine('attach');
//        reset_form("#gViewProcessedMarketReturn");
        $("#jqgrid-grid-processed").jqGrid({
            %{--url: '${resource(dir:'mushak', file:'list')}',--}%
            datatype: "json",
            colNames: [
                'ID',
                'Customer Name',
                'Customer Code',
                'MR No',
                'Process Date',
                'QC Performing Time',
                'QC Person Name',
                'QC Person Pin',
                'MR Status'
            ],
            colModel: [
                {name: 'id', index: 'id', width: 0, hidden: true},
                {name: 'customerName', index: 'customerName', width: 100, align: 'left'},
                {name: 'customerCode', index: 'customerCode', width: 100, align: 'left'},
                {name: 'mrNo', index: 'mrNo', width: 100, align: 'left'},
                {name: 'processDate', index: 'processDate', width: 100, align: 'left'},
                {name: 'qcTime', index: 'qcTime', width: 100, align: 'left'},
                {name: 'qcName', index: 'qcName', width: 100, align: 'left'},
                {name: 'qcPin', index: 'qcPin', width: 100, align: 'left'},
                {name: 'status', index: 'status', width: 100, align: 'left'}
            ],
            rowNum: 10,
            rowList: [10, 20, 30],
            pager: '#jqgrid-pager-processed',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Processed Market Return",
            autowidth: true,
            autoheight: true,
            scrollOffset: 0,
            altRows: true,
            afterSaveCell: function (id, name, val, iRow, iCol) {
            },
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                loadDetails(rowid);
            }
        });

        $("#jqgrid-grid-details").jqGrid({
            %{--url: '${resource(dir:'mushak', file:'list')}',--}%
            datatype: "json",
            colNames: [
                'Product ID',

                'Product Name',
                'Product Code',
                'Leak Claimed',
                'Leak Accepted',
                'Short Claimed',
                'Short Accepted',
                'MR Claimed',
                'MR Accepted',
                'Challan Short Claimed',
                'Challan Short Accepted',
                'Damage Claimed',
                'Damage Accepted',
                'Total Accepted',
                'Total Value'
            ],
            colModel: [
                {name: 'id', index: 'id', width: 0, hidden: true},
                {name: 'productName', index: 'productName', width: 80, align: 'left'},
                {name: 'productCode', index: 'productCode', width: 80, align: 'left'},
                {name: 'leak', index: 'leak', width: 54, align: 'left'},
                {name: 'leakAccepted', index: 'leakAccepted', width: 58, align: 'left'},
                {name: 'short', index: 'short', width: 53, align: 'left'},
                {name: 'shortAccepted', index: 'shortAccepted', width: 57, align: 'left'},
                {name: 'mr', index: 'mr', width: 53, align: 'left'},
                {name: 'mrAccepted', index: 'mrAccepted', width: 56, align: 'left'},
                {name: 'challan', index: 'challan', width: 52, align: 'left'},
                {name: 'challanAccepted', index: 'challanAccepted', width: 55, align: 'left'},
                {name: 'damage', index: 'damage', width: 51, align: 'left'},
                {name: 'damageAccepted', index: 'damageAccepted', width: 54, align: 'left'},
                {name: 'totalAccepted', index: 'totalAccepted', width: 55, align: 'left'},
                {name: 'totalValue', index: 'totalValue', width: 50, align: 'right'}
            ],
            rowNum: 10,
            rowList: [10, 20, 30],
            pager: '#jqgrid-pager-details',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Processed Market Return Deails",
            width: 1000,
            autoheight: true,
            scrollOffset: 0,
            altRows: true,
            footerrow:true,
            afterSaveCell: function (id, name, val, iRow, iCol) {
            },
            loadComplete: function () {
                var $grid = $('#jqgrid-grid-details');
                var colSum = $grid.jqGrid('getCol', 'totalValue', false, 'sum');
                $grid.jqGrid('footerData', 'set', { totalValue: colSum, totalAccepted:'Total  ' });
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
        $("#isNonDpCustomer").attr('checked', false);
        loadDp();
    });

    function loadDp() {
        if ('${dpSize}' == '0') {
            %{--multi = 1;--}%
            %{--$("#single").attr('hidden', true);--}%
            %{--$("#multi").removeAttrs('hidden');--}%
            %{--jQuery.ajax({--}%
                %{--type: 'post',--}%
                %{--url: "${resource(dir:'customerPayment', file:'loadDp')}?entId=" + $("#enterpriseConfiguration").val(),--}%
                %{--success: function (data, textStatus) {--}%
                    %{--var option = '<option value="">Please Select</option>';--}%
                    %{--for (var i = 0; i < data.length; i++) {--}%
                        %{--option += '<option value="' + data[i].id + '" name="' + data[i].customer + '">' + data[i].name + '</option>';--}%
                    %{--}--}%
                    %{--$("#distributionPoint").html(option);--}%
                %{--},--}%
                %{--error: function (XMLHttpRequest, textStatus, errorThrown) {--}%
                %{--},--}%
                %{--complete: function () {--}%
                %{--},--}%
                %{--dataType: 'json'--}%
            %{--});--}%
            $("#dp_dev").attr('hidden', true);
            populateCustomer(0);
        } else {
            var size = ${dpSize};
            if (size == 1) {
                multi = 0;
                $("#dpName").val(${dpList}[0].name);
                $("#distributionPointId").val(${dpList}[0].id);
            } else {
                multi = 1;
                $("#single").attr('hidden', true);
                $("#multi").removeAttrs('hidden');
                var options = '<option value="">Please Select</option>';
                for (var i = 0; i < size; i++) {
                    options += '<option value="' + ${dpList}[i].id + '">' + ${dpList}[i].name + '</option>';
                }
                $("#distributionPoint").html(options);
                $("#customer_type_dev").removeAttrs('hidden');
                populateCustomer(1);
            }
            $("#customer_dev").attr('hidden', true);
        }
    }

    function populateCustomer(factory) {
        jQuery('#searchKey').autocomplete({
            minLength: '1',
            source: function (request, response) {
                //EmployeeRegister.employeeCoreInfoId = null;
                var data = {searchKey: request.term};
                var url = '${resource(dir:'processMarketReturn', file:'fetchCustomerForView')}?factory=' + factory;
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
            CustomerInfo.popupCustomerListPanel(factory);
        });

    }

    function customerTypeChange() {
        if (document.getElementById('isNonDpCustomer').checked) {
            $("#dp_dev").attr('hidden', true);
            $("#customer_dev").removeAttrs('hidden');
            $("#distributionPoint").val('');
        } else {
            $("#dp_dev").removeAttrs('hidden');
            $("#customer_dev").attr('hidden', true);
            $("#searchKey").val('');
            $("#customerMaster").val('');
            $("#code").val('');
        }
        $("#jqgrid-grid-processed").jqGrid("clearGridData");
        $("#jqgrid-grid-details").jqGrid("clearGridData");
    }

    var CustomerInfo = {
        customerCoreInfoId: null,
        popupCustomerListPanel: function (factory) {
            var url = '${resource(dir:'processMarketReturn', file:'popupCustomerListPanelForView')}?factory=' + factory;
            var params = {};
            DocuAjax.html(url, params, function (html) {
                $.fancybox(html);
            });
        },
        setCustomerInformation: function (customerCoreInfoId, customerCoreInfo, code, category) {
            $("#searchKey").val(customerCoreInfo);
            $("#customerMaster").val(customerCoreInfoId);
            $("#code").val(code);
            CustomerInfo.customerCoreInfoId = customerCoreInfoId;
        }
    };

    function searchMarketReturn() {
        var url = '';
        if ($("#distributionPoint").val() == '' && $("#customerMaster").val() == '') {
            return false;
        }
        if ($("#customerMaster").val() == '') {
            url = "${resource(dir:'processMarketReturn', file:'searchMarketReturn')}?dpId=" +
            (multi == 1 ? $("#distributionPoint").val() : $("#distributionPointId").val()) + "&dateFrom=" +
            $("#dateFrom").val() + "&dateTo=" + $("#dateTo").val();
        } else {
            url = "${resource(dir:'processMarketReturn', file:'searchMarketReturn')}?customerId=" +
            $("#customerMaster").val() + "&dateFrom=" + $("#dateFrom").val() + "&dateTo=" + $("#dateTo").val();
        }
        $("#jqgrid-grid-processed").setGridParam({
            url: url
        });
        $("#jqgrid-grid-processed").trigger("reloadGrid");
        $("#jqgrid-grid-details").jqGrid("clearGridData");
    }

    function loadDetails(id){
//        alert(id);
        var stat = $("#jqgrid-grid-processed").jqGrid('getCell', id, 'status');
        if(stat == "PROCESSED") {
            $("#jqgrid-grid-details").setGridParam({
                url: "${resource(dir:'processMarketReturn', file:'fetchDetails')}?id=" + id
            });
        }else{
            $("#jqgrid-grid-details").setGridParam({
                url: "${resource(dir:'processMarketReturn', file:'fetchDetails')}?rejectId=" + id
            });
        }
        $("#jqgrid-grid-details").trigger("reloadGrid");
    }

</script>