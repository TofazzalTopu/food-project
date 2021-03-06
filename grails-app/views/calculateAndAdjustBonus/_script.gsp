<%@ page import="com.docu.commons.CommonConstants" %>
<script type="text/javascript" language="Javascript">
    var deletedIds = [];
    var deleteSize = 0;

    $(document).ready(function () {
        initDatePicker();
        loadGrid();

        $('#ui-widget-header-text').html('PromotionInfo')
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#promotionForm").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#promotionForm").validationEngine('attach');

        reset_form("#promotionForm");

    });

    function loadGrid(){
        $("#jqgrid-grid").jqGrid({
            url: '${request.contextPath}/${params.controller}/search?promotionId='+$('#promotion').val()+'&effectiveFrom='+$('#effectiveFrom').val()+'&effectiveTo='+$('#effectiveTo').val(),
            datatype: "json",
            colNames: [
                'SL',
//                'ID',
                'Promotion ID',
                'Package ID',
                'Promotion Name',
                'Package Name',
                'Discount Amount',
                'Effective From',
                'Effective To'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30, sortable: false, align: 'center'},
//                {name: 'id', index: 'id', width: 0, hidden: true},
                {name: 'promotion_id', index: 'promotion_id', width: 0, hidden: true},
                {name: 'package_id', index: 'package_id', width: 0, hidden: true},
                {name: 'promotion_name', index: 'promotion_name', width: 100, align: 'left'},
                {name: 'package_name', index: 'package_name', width: 100, align: 'left'},
                {name: 'discount_amount', index: 'discount_amount', width: 100, align: 'right'},
                {name: 'effective_from', index: 'effective_from', width: 100, align: 'center'},
                {name: 'effective_to', index: 'effective_to', width: 100, align: 'center'}
            ],
            rowNum: 50,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#jqgrid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "asc",
            caption: "Package List",
            autowidth: true,
            height: true,
            scrollOffset: 30,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
//                executeEditPromotionInfo();
            }
        });
        $("#jqgrid-grid").jqGrid('navGrid', '#jqgrid-pager', {edit: false, add: false, del: false, search: false});
        $(".ui-pg-selbox").children().each(function () {
            if ($(this).val() == -1) {
                $(this).html('All')
            }

        });
    }

    function executePreConditionPromotionInfo() {
        if (!$("#promotionForm").validationEngine('validate')) {
         return false;
         }
        return true;
    }
    function executeAjaxAdjustBonusPromotion() {
        var dataPackageList = $("#jqgrid-grid").jqGrid('getRowData');
        if (dataPackageList.length == 0) {
            MessageRenderer.render({
                messageTitle: 'Calculate and Adjust Bonus',
                type: 0,
                messageBody: 'Package not found. Calculation and adjustment of bonus not performed.'
            });
            return false;
        }
        var actionUrl = "${request.contextPath}/${params.controller}/adjust";
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: {packageList:JSON.stringify(dataPackageList)},
            url: actionUrl,
            success: function (data, textStatus) {
                MessageRenderer.render(data);
                $("#jqgrid-grid").jqGrid("clearGridData");
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if (XMLHttpRequest.status = 0) {
//                    $("#jqgrid-grid").trigger("reloadGrid");
//                    $("#jqgrid-grid").jqGrid("clearGridData");
//                    reset_form('#promotionForm');
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                }
                else {
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

    function initDatePicker() {
        $("#effectiveTo,#effectiveFrom").datepicker(
                {
                    dateFormat: 'dd-mm-yy',
                    changeMonth: true,
                    changeYear: true
                });
        $("#effectiveTo,#effectiveFrom").mask('${CommonConstants.DATE_MASK_FORMAT}', {});
    }

    function executeAjaxSearch(){
        var effectiveFrom = $('#effectiveFrom').val();
        var effectiveTo = $('#effectiveTo').val();
        var promotion = $('#promotion').val();
        var url = '${request.contextPath}/${params.controller}/search?promotionId='+promotion+'&effectiveFrom='+effectiveFrom+'&effectiveTo='+effectiveTo;
        $("#jqgrid-grid").jqGrid('setGridParam',{url:url}).trigger('reloadGrid');
    }

</script>