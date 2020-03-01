<%@ page import="com.docu.commons.CommonConstants" %>
<script type="text/javascript" language="Javascript">
    var format = 'pdf';
    var productIds  = 0;
    var packSizeIds = 0;
    var channelIds  = 0;
    var categoryIds = 0;

    function generateReport() {
        var isAll = '';
        if ($('#isAll').is(':checked')) {
            $("#isAll").val('all')
            isAll = $("#isAll").val();
        } else {
            $("#isAll").val('1')
            isAll = $("#isAll").val();
        }
        var fromDate = $("#fromDate").val();
        if (!fromDate) {
            MessageRenderer.renderErrorText("Please Select From Date");
            return
        }
        var toDate = $("#toDate").val();
        if (!toDate) {
            MessageRenderer.renderErrorText("Please Select To Date");
            return
        }
//        alert("all "+ isAll)

        if (isAll == 1) {
            if (!productIds) {
                MessageRenderer.renderErrorText("Please Select Product");
                return false;
            }
            if (!packSizeIds) {
                MessageRenderer.renderErrorText("Please Select Pack Size");
                return false;
            }
            if (!channelIds) {
                MessageRenderer.renderErrorText("Please Select Channel");
                return false;
            }

            if (!categoryIds) {
                MessageRenderer.renderErrorText("Please Select Category");
                return false;
            }
        } else {
            if (!channelIds) {
                MessageRenderer.renderErrorText("Please Select Channel");
                return false;
            }

            if (!categoryIds) {
                MessageRenderer.renderErrorText("Please Select Category");
                return false;
            }
        }
        if (!productIds) {
            productIds = '0'
            packSizeIds = '0'
        }

        SubmissionLoader.showTo();
        window.open("${resource(dir:'reportList', file:'regionAndUserWiseDemandReport')}?format=" + format + "&productIds=" + productIds + "&packSizeIds=" + packSizeIds + "&channelIds=" + channelIds + "&categoryIds=" + categoryIds + "&fromDate=" + fromDate + "&toDate=" + toDate + "&isAll=" + isAll);
        SubmissionLoader.hideFrom();
    }
    function initDatePicker() {
        $("#toDate,#fromDate").datepicker(
                {
                    dateFormat: 'dd-mm-yy',
                    changeMonth: true,
                    changeYear: true
                });
        $('#fromDate').mask('${CommonConstants.DATE_MASK_FORMAT}', {});
        $('#toDate').mask('${CommonConstants.DATE_MASK_FORMAT}', {});
    }

    function changeFormat(val) {
        if (val == 2) {
            format = 'xlsx';
        } else {
            format = 'pdf';
        }
    }


    function getRowIdsAll(selectedRows) {
        var result = [];
        $.each(selectedRows, function (i, e) {
            if ($.inArray(e, result) == -1) result.push(e);
        });
        var selectedRowsFinal = result.filter(function (n) {
            return n != undefined
        });
        var ids = ''
        for (key in selectedRowsFinal) {
            if (ids) {
                ids += ',' + selectedRowsFinal[key]
            } else {
                ids += selectedRowsFinal[key]
            }
        }
//        ids += ') '
        productIds = ids
//        alert("productIds " + productIds)
        console.log(productIds);
        return productIds;
    }
    function getPackSizeRowIds(selectedRows) {
        var result = [];
        $.each(selectedRows, function (i, e) {
            if ($.inArray(e, result) == -1) result.push(e);
        });
        var selectedRowsFinal = result.filter(function (n) {
            return n != undefined
        });
        var ids = ""
        for (key in selectedRowsFinal) {
            if (ids) {
                ids += ',' + selectedRowsFinal[key]
            } else {
                ids = selectedRowsFinal[key]
            }
        }
        packSizeIds = ids
//        alert("packSizeIds " + packSizeIds)
        return packSizeIds;
    }
    function getCategoryRowIds(selectedRows) {
        var result = [];
        $.each(selectedRows, function (i, e) {
            if ($.inArray(e, result) == -1) result.push(e);
        });
        var selectedRowsFinal = result.filter(function (n) {
            return n != undefined
        });
        var ids = ""
        for (key in selectedRowsFinal) {
            if (ids) {
                ids += ',' + selectedRowsFinal[key]
            } else {
                ids = selectedRowsFinal[key]
            }
        }
        categoryIds = ids
//        alert("categoryIds " + categoryIds)
        return categoryIds;
    }
    function getChannelRowIdsAll(selectedRows) {
        var result = [];
        $.each(selectedRows, function (i, e) {
            if ($.inArray(e, result) == -1) result.push(e);
        });
        var selectedRowsFinal = result.filter(function (n) {
            return n != undefined
        });
        var ids = ""
        for (key in selectedRowsFinal) {
            if (ids) {
                ids += ',' + selectedRowsFinal[key]
            } else {
                ids = selectedRowsFinal[key]
            }
        }
        channelIds = ids
//        alert("channelIds " + channelIds)
        return channelIds;
    }

    function executePsPrecondition() {
        if (!$("#gFormRegionWiseDemand").validationEngine('validate')) {
            return false;
        }
        return true;
    }
</script>