<%@ page import="com.docu.commons.CommonConstants" %>
<script type="text/javascript" language="Javascript">
    var deletedIds = [];
    var deleteSize = 0;

    $(document).ready(function () {
        initDatePicker();

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
        $("#jqgrid-grid").jqGrid({
            url: '${resource(dir:'promotion', file:'list')}',
            datatype: "json",
            colNames: [
                'SL',
                'ID',
                'Name',

                'Effective From Date',
                'Effective To Date'

            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30, sortable: false, align: 'center'},
                {name: 'id', index: 'id', width: 0, hidden: true},

                {name: 'name', index: 'name', width: 100, align: 'left'},
                {name: 'effectiveFrom', index: 'effectiveFrom', width: 100, align: 'center'},
                {name: 'effectiveTo', index: 'effectiveTo', width: 100, align: 'center'}

            ],
            rowNum: 50,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#jqgrid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "asc",
            caption: "Promotion List",
            autowidth: true,
            height: true,
            scrollOffset: 20,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                executeEditPromotionInfo();
            }
        });
        $("#jqgrid-grid").jqGrid('navGrid', '#jqgrid-pager', {edit: false, add: false, del: false, search: false});
        $(".ui-pg-selbox").children().each(function () {
            if ($(this).val() == -1) {
                $(this).html('All')
            }

        });
    });

    function executeEditPromotionInfo() {
        var promotionInfoId = getSelectedPromotionInfoId();
        if (executePreConditionForEditPromotionInfo(promotionInfoId)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'promotion', file:'edit')}?id=" + promotionInfoId,
                success: function (entity) {
                    executePostConditionForEditPromotionInfo(entity);
                },
                dataType: 'json'
            });
        }
    }
    function getSelectedPromotionInfoId() {
        var promotionInfoId = null;
        var rowid = $("#jqgrid-grid").jqGrid('getGridParam', 'selrow');
        if (rowid) {
            promotionInfoId = $("#jqgrid-grid").jqGrid('getCell', rowid, 'id');
        }
        if (promotionInfoId == null) {
            promotionInfoId = $('#promotionForm input[name = id]').val();
        }
        return promotionInfoId;
    }
    function executePreConditionForEditPromotionInfo(promotionInfoId) {
        if (promotionInfoId == null) {
            alert("Please select a promotion to edit");
            return false;
        }
        return true;
    }
    function executePostConditionForEditPromotionInfo(data) {
        if (data == null) {
            alert('Selected promotion might have been deleted by someone');  //Message renderer
        } else {
            showPromotionInfo(data);
        }
    }
    function showPromotionInfo(entity) {
        $('#promotionForm input[name = id]').val(entity.id);
        $('#promotionForm input[name = version]').val(entity.version);

        var effectiveFromDate = $.datepicker.formatDate('dd-mm-yy', new Date(entity.effectiveFrom));
        var effectiveToDate = $.datepicker.formatDate('dd-mm-yy', new Date(entity.effectiveTo));
        var asOfDate = $.datepicker.formatDate('dd-mm-yy', new Date(entity.dateCreated));

        $('#promotionName').val(entity.name);
        $('#effectiveFrom').val(effectiveFromDate);
        $('#effectiveTo').val(effectiveToDate);
        $('#asOfDate').val(asOfDate);
        $('#isActive').attr('checked',entity.isActive);

        if(entity.calculationStatus == 'current'){
            $('#calculationStatusCurrent').attr('checked',true);
        }else if(entity.calculationStatus == 'post'){
            $('#calculationStatusPost').attr('checked',true);
        }else{
            $('#calculationStatusCurrent').attr('checked',false);
            $('#calculationStatusPost').attr('checked',false);
        }

        $('#create-button').attr('value', 'Update');
        $('#cancel-button').attr('value', 'Cancel');
        $('#delete-button').show();
    }

    function executePostConditionPromotionPackage(result) {
        if (result.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_form('#promotionForm');
        }
        MessageRenderer.render(result);
    }
    function executePreConditionPromotionInfo() {
        // trim field vales before process.
        trim_form();
        /*if (!$("#promotionName").val()) {
            MessageRenderer.renderErrorText("Please enter promotion name");
            return false;
        }

        if (!$("#effectiveFrom").val()) {
            MessageRenderer.renderErrorText("Please enter effective from date");
            return false;
        }
        if (!$("#effectiveTo").val()) {
            MessageRenderer.renderErrorText("Please enter effective to date");
            return false;
        }*/

        if (!$("#promotionForm").validationEngine('validate')) {
         return false;
         }
        return true;
    }
    function executeAjaxPromotion() {
        if (!executePreConditionPromotionInfo()) {
            return false;
        }
        var fromDate = DocuDateUtil.createDateFromString($('#effectiveFrom').val());
        var toDate = DocuDateUtil.createDateFromString($('#effectiveTo').val());

        if ($('#effectiveFrom').val() == '') {
            MessageRenderer.render({
                messageTitle: 'Can not create promotion',
                type: 0,
                messageBody: 'Please enter Effective From date.'
            });
            return false;
        }
        if ($('#effectiveTo').val() == '') {
            MessageRenderer.render({
                messageTitle: 'Can not create promotion',
                type: 0,
                messageBody: 'Please enter Effective To date.'
            });
            return false;
        }

        if (fromDate > toDate) {
            MessageRenderer.render({
                messageTitle: 'Can not create promotion',
                type: 0,
                messageBody: 'Effective from  date cannot greater than effective to date.'
            });
            return false
        }

        var actionUrl = null;
        if ($('#promotionForm input[name = id]').val()) {
            actionUrl = "${request.contextPath}/${params.controller}/update";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/create";
        }
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: jQuery("#promotionForm").serialize(),
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionPromotionPackage(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if (XMLHttpRequest.status = 0) {
                    $("#jqgrid-grid").trigger("reloadGrid");
                    reset_form('#promotionForm');
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
    function deleteAjaxPromotionInfo() {    // Delete record
        var promotionInfoId = getSelectedPromotionInfoId();
        if (executePreConditionForDeletePromotionInfo(promotionInfoId)) {

            $("#dialog").dialog("destroy");
            $("#dialog-confirm-promotion").dialog({
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
                            data: jQuery("#promotionForm").serialize(),
                            url: "${resource(dir:'promotion', file:'inactive')}",
                            success: function (message) {
                                executePostConditionForDeletePromotionInfo(message);
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
    function executePreConditionForDeletePromotionInfo(promotionInfoId) {
        if (promotionInfoId == null) {
            alert("Please select a promotion to delete");
            return false;
        }
        return true;
    }
    function executePostConditionForDeletePromotionInfo(data) {
        if (data.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_form('#promotionForm');
        }
        MessageRenderer.render(data)
    }
    function initDatePicker() {
        $("#asOfDate,#effectiveTo,#effectiveFrom").datepicker(
                {
                    dateFormat: 'dd-mm-yy',
                    changeMonth: true,
                    changeYear: true
                });
        $('#asOfDate').mask('${CommonConstants.DATE_MASK_FORMAT}', {});
        $('#effectiveFrom').mask('${CommonConstants.DATE_MASK_FORMAT}', {});
        $('#effectiveTo').mask('${CommonConstants.DATE_MASK_FORMAT}', {});
    }

    function reset_form(formName) {
        $(formName).find(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
            if (this.type == 'hidden') {
                this.value = "";
            } else {
                this.value = this.defaultValue;
            }
        });
        $(formName + ' select').val('');
        $('input[type=checkbox]').attr('checked', false);
        $('input[type=radio]').attr('checked', false);
        $(formName + ' input[name = create-button]').attr('value', 'Create');
        $(formName + ' input[name = delete-button]').hide();
    }
    function clear_form(formName) {
        $(formName).find(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
            if (this.type == 'hidden') {
                this.value = "";
            } else {
                this.value = this.defaultValue;
            }
        });
        $(formName).validationEngine('hideAll');
        $('input[type=checkbox]').attr('checked', false);
        $(formName + ' input[name = create-button]').attr('value', 'Create');
        $(formName + ' input[name = delete-button]').hide();
    }

    function trim_form() {
        $(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
            this.value = $.trim(this.value);
        });
    }

</script>