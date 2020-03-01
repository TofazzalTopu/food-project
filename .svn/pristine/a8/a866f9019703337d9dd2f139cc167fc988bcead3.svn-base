<script type="text/javascript" language="Javascript">
    var subAreaId = 1;
    var cross_string = '<font style="font-weight: bold;color: red;">X</font>';
    var tick_string = '<font style="font-weight: bold;color: green;">&radic;</font>';
    $(document).ready(function () {
        $('#delete-button').hide();
        $("#batchDate").datepicker(
                {
                    dateFormat: 'dd-mm-yy',
                    changeMonth: true,
                    changeYear: true
                });
        $('#timeTransaction').timepicker({showPeriod: true, showLeadingZero: true});
        $('#ui-widget-header-text').html('Finish Goods');
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }
        $("#gFormReverseFinishGood").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
//        $("#gFormDistributionPoint").validationEngine('attach');
//        reset_form("#gFormDistributionPoint");

        $("#jqgrid-grid").jqGrid({
            url: '',
            datatype: "local",
            colNames: [
                'SL',
                'id',
                '&#10004;',
                'Name',
                'Quantity',
                'UOM',
                'Batch No',
                'Cost Price',
                'Inventory',
                'Sub Inventory',
                'Product Ref',
                'good_id'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30, sortable: false, align: 'left'},
                {name: 'id', index: 'id', width: 0, hidden: true},
                {name: 'checkGood', index: 'checkGood', width: 20},
                {name: 'name', index: 'name', width: 150},
                {name: 'quantity', index: 'quantity', width: 60, align:'center', formatter:'number', formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 0}},
                {name: 'uom', index: 'uom', width: 50, align: 'left'},
                {name: 'batch_no', index: 'batch_no', width: 60, align: 'left'},
                {name: 'cost', index: 'cost', width: 50, align: 'right', formatter:'currency', formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2}},
                {name: 'ware_house_name', index: 'ware_house_name', width: 100, align: 'left'},
                {name: 'sub_ware_house_name', index: 'sub_ware_house_name', width: 150, align: 'left'},
                {name: 'product_ref_no', index: 'product_ref_no', width: 220, align: 'left'},
                {name: 'good_id', index: 'good_id', width: 50, align: 'left', 'hidden': true}
            ],
            rowNum: -1,
//            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Finish Good Information",
            autowidth: true,
            height: true,
            scrollOffset: 0,
            loadComplete: function () {
                var ids = $("#jqgrid-grid").jqGrid('getDataIDs');
                for (key in ids) {
                    var rowData = $("#jqgrid-grid").getRowData(ids[key]);
                    $("#jqgrid-grid").jqGrid('setRowData', ids[key], {checkGood: '<input type="checkbox"  onchange="buttonStatusChange();"  class="disChck" value="' + ids[key] + '" />'});
                }
            },
            onSelectRow: function (rowid, status) {
                //executeEditDistributionPoint();
            }
        });

        $('#transactionRef').blur(function () {
            if ($('#transactionRef').val() == '') {
                $("#referenceId").val('');
            }
        });

        jQuery('#transactionRef').autocomplete({
            minLength: '2',
            source: function (request, response) {
                $('#popEmpDetails').html("");
                var data = {searchKey: request.term};
                var url = "${request.contextPath}/${params.controller}/transProductReferenceAutoComplete?query=" + $('#transactionRef').val();
                DocuAutoComplete.setSpinnerSelector('transactionRef').execute(response, url, data, function (item) {
                    item['label'] = item['ref_no'] + " [" + item['type'] + "]";
                    item['value'] = item['label'];
                    return item;
                });
            },
            select: function (event, ui) {
                CustomerInfo.setCustomerInformation(ui.item.ref_no, ui.item.value);
            }
        }).data("autocomplete")._renderItem = function (ul, item) {
            var customerDetails = "";
//                customerDetails = '<div style="font-size: 9px; color:#326E93;">' + " Ref: " + item.ref_no + "[" + item.type + "]" + '</div>';
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + customerDetails).appendTo(ul);
        };
    });
    var CustomerInfo = {
        customerCoreInfoId: null,
        popupCustomerListPanel: function (customerCoreInfoId) {
            var url = '${resource(dir:'finishGoodWarehouse', file:'transProductReferenceForPopup')}';
            var params = {id: customerCoreInfoId};
            DocuAjax.html(url, params, function (html) {

                $.fancybox(html);
            });
        },

        setCustomerInformation: function (customerCoreInfoId, customerCoreInfo) {
            $("#transactionRef").val(customerCoreInfo);
            $("#referenceId").val(customerCoreInfoId);
            CustomerInfo.customerCoreInfoId = customerCoreInfoId;
        }
    };

    $('#search-btn-customer-register-id').click(function () {
        CustomerInfo.popupCustomerListPanel($('#referenceId').val());
    });

    function loadGridInfo() {
        var referenceId = $('#referenceId').val();
        if (referenceId) {
            $("#jqgrid-grid").jqGrid().setGridParam({url: "${resource(dir:'finishGoodWarehouse', file:'productListForReverseFinishGood')}?"
                    + 'query=' + referenceId,
                datatype: "json"
            }).trigger("reloadGrid", [
                        {page: 1}
                    ]);

        } else {
            MessageRenderer.renderErrorText("Please enter a reference number");
        }
    }

    function buttonStatusChange() {
        var isSelected = true;
        $('.disChck').each(function () {
            if (this.checked) {
                $('#select-button').attr('value', 'Deselect All');
                isSelected = false
            }
        });

        if (isSelected) {
            $('#select-button').attr('value', 'Select All')
        }
    }

    function checkUnCheck() {
        if ($('#select-button').val() == 'Select All') {
            $('.disChck').each(function () {
                this.checked = true;
                $('#select-button').attr('value', 'Deselect All')
            })
        }
        else {
            $('.disChck').each(function () {
                this.checked = false;
                $('#select-button').attr('value', 'Select All')
            })
        }
    }
    function reverseGood() {

        var allIds = {};
        var i = 0;

        $('.disChck').each(function () {
            if (this.checked) {
                allIds['items.finishGood[' + i + '].detailsId'] = $(this).val();
                i++
            }
        });
        if (i < 1) {
            $("#dialog").dialog("destroy");
            $("#dialog-geoLocation-selection").dialog({
                resizable: false,
                height: 150,
                modal: true,
                title: 'Product Selection Missing',
                buttons: {
                    Ok: function () {
                        $(this).dialog('close');
                    }
                }
            }); //end of dialog

            return false
        }
        SubmissionLoader.showTo();
        var actionUrl = null;
        actionUrl = "${request.contextPath}/${params.controller}/reverseFinishGood";
        jQuery.ajax({
            type: 'post',
            data: allIds,
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionReverseFinishGood(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-grid").setGridParam({
                        url: '${resource(dir:'finishGoodWarehouse', file:'productListForReverseFinishGood')}?query=0'
                    });
                    $("#jqgrid-grid").trigger("reloadGrid");
                    reset_form('#gFormReverseFinishGood');
                    
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

    function executePostConditionReverseFinishGood(result) {
        if (result.type == 1) {
            $("#jqgrid-grid").setGridParam({
                url: '${resource(dir:'finishGoodWarehouse', file:'productListForReverseFinishGood')}?query=0'
            });
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_form('#gFormReverseFinishGood');
        }
        MessageRenderer.render(result);
    }

</script>