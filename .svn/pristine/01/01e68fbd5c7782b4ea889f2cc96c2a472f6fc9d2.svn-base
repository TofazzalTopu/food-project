<script type="text/javascript" language="Javascript">
    var monthListJScript = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
    var dailySalesTargetFinishProductGrid = "";
    $(document).ready(function () {
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormDailySalesTargetFinishProduct").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormDailySalesTargetFinishProduct").validationEngine('attach');
        jQuery('#productSearchKey').autocomplete({
            minLength: '1',
            source: function (request, response) {
                var yearlySalesTargetByVolumeId = $("#yearlySalesTargetId").val();
                if(!yearlySalesTargetByVolumeId){
                    return false;
                }
                var targetMonth = $("#monthId").val();
                if(!targetMonth){
                    return false
                }

                var employeeId = $("#employeeId").val();
                if(!employeeId){
                    return false
                }
                var data = {query: $('#productSearchKey').val(), yearlySalesTargetByVolumeId: yearlySalesTargetByVolumeId, targetMonth : targetMonth, employeeId: employeeId};
                var url = "${request.contextPath}/${params.controller}/listProduct";
                DocuAutoComplete.setSpinnerSelector('productSearchKey').execute(response, url, data, function (item) {
                    item['label'] = "[" + item['code'] + "] " + item['name'];
                    item['value'] = item['label'];
                    return item;
                });
            },
            select: function (event, ui) {
                loadProductDataInField(ui.item.id, ui.item.code, ui.item.name, ui.item.quantity);
            }
        }).data("autocomplete")._renderItem = function (ul, item) {
            var accountstype = "";
            if (item) {
                accountstype = '<div style="font-size: 9px; color:#326E93;">' + "Code: " + item.code + "; Name: " + item.name + "; " + "Target Quantity: " + item.quantity + '</div>';
            }
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + accountstype).appendTo(ul);
        };
        $('#search-btn-product-id').click(function () {
            var yearlySalesTargetByVolumeId = $("#yearlySalesTargetId").val();
            if(!yearlySalesTargetByVolumeId){
                MessageRenderer.renderErrorText("Please Select Target Year");
                return;
            }
            var targetMonth = $("#monthId").val();
            if(!targetMonth){
                MessageRenderer.renderErrorText("Please Select Target Month");
                return;
            }
            var employeeId = $("#employeeId").val();
            if(!employeeId){
                MessageRenderer.renderErrorText("Please Select Employee/SM");
                return;
            }
            var url = '${resource(dir:'dailySalesTargetFinishProduct', file:'popupProductListPanel')}';
            var params = {yearlySalesTargetByVolumeId: yearlySalesTargetByVolumeId, targetMonth: targetMonth, employeeId: employeeId};
            DocuAjax.html(url, params, function (html) {
                $.fancybox(html);
            });
        });

        dailySalesTargetFinishProductGrid = $("#jqgrid-grid-dailySalesTargetFinishProduct").jqGrid({
            url: '',
            datatype: "local",
            colNames: [
                'SL',
                'ID',
                'Target Day',
                'Quantity'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30, sortable: true, align: 'left'},
                {name: 'id', index: 'id', width: 0, hidden: true},
                {name: 'targetDate', index: 'targetDate', width: 100, align: 'center'},
                {name: 'quantity', index: 'quantity', width: 100, align: 'right', sorttype:'integer', formatter:"integer", editable: true, editrules:{integer:true}, formatoptions:{ thousandsSeparator: ",", defaultValue: '0'}}
            ],
            rowNum: -1,
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Daily Sales Target",
            autowidth: true,
            height: true,
            scrollOffset: 0,
            altRows: true,
            cellEdit: true,
            cellsubmit: 'clientArray',
            cellurl: null,
            footerrow : true,
            loadComplete: function () {
                calculateDailySummaryData();
            },
            afterSaveCell : function(rowid,name,val,iRow,iCol) {
                calculateDailySummaryData();
            },
            onSelectRow: function (rowid, status) {
            }
        });

    });
    function loadProductDataInField(productId, productCode, productName, targetQuantity) {
        $("#productId").val(productId);
        $("#productCode").val(productCode);
        $("#productName").val(productName);
        $("#targetQuantity").val(targetQuantity);
        loadDailyTargetGrid();
    }
    function getSelectedDailySalesTargetFinishProductId() {
        var dailySalesTargetFinishProductId = null;
        var rowid = $("#jqgrid-grid-dailySalesTargetFinishProduct").jqGrid('getGridParam', 'selrow');
        if (rowid) {
            dailySalesTargetFinishProductId = $("#jqgrid-grid-dailySalesTargetFinishProduct").jqGrid('getCell', rowid, 'id');
        }
        if (dailySalesTargetFinishProductId == null) {
            dailySalesTargetFinishProductId = $('#gFormDailySalesTargetFinishProduct input[name = id]').val();
        }
        return dailySalesTargetFinishProductId;
    }
    function executePreConditionDailySalesTargetFinishProduct() {
        // trim field vales before process.
        trim_form();
        if (!$("#gFormDailySalesTargetFinishProduct").validationEngine('validate')) {
            return false;
        }
        return true;
    }
    function executeAjaxDailySalesTargetFinishProduct() {
        if (!executePreConditionDailySalesTargetFinishProduct()) {
            return false;
        }

        $('#jqgrid-grid-dailySalesTargetFinishProduct').jqGrid("editCell", 0, 0, false);
        var gd = $("#jqgrid-grid-dailySalesTargetFinishProduct").jqGrid('getRowData');
        var length = gd.length;
        if(length <= 0){
            MessageRenderer.renderErrorText("No Daily Sales Target Available");
            return false
        }
        var $grid = $('#jqgrid-grid-dailySalesTargetFinishProduct');
        var monthTotal = $grid.jqGrid('getCol', 'quantity', false, 'sum');
        var targetQuantity = Number($("#targetQuantity").val());
        if(monthTotal != targetQuantity){
            MessageRenderer.renderErrorText("Total Daily Sales Target is not equal to Monthly Sales Target");
            return false
        }
        var data = jQuery("#gFormDailySalesTargetFinishProduct").serializeArray();
        for (var i=0; i < length; ++i) {
            data.push({'name':'items.dailySalesTargetFinishProduct['+i+'].id', 'value': gd[i].id});
            data.push({'name':'items.dailySalesTargetFinishProduct['+i+'].quantity', 'value': gd[i].quantity});
        }
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: data,
            url: "${request.contextPath}/${params.controller}/update",
            success: function (data, textStatus) {
                executePostConditionDailySalesTargetFinishProduct(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-grid-dailySalesTargetFinishProduct").trigger("reloadGrid");

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
        return true;
    }
    function executePostConditionDailySalesTargetFinishProduct(result) {
        if (result.type == 1) {
            $("#jqgrid-grid-dailySalesTargetFinishProduct").trigger("reloadGrid");
        }
        MessageRenderer.render(result);
    }

    function reset_form(formName) {
        $(formName).find(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
            if (this.type == 'hidden') {
                this.value = "";
            } else {
                this.value = this.defaultValue;
            }
        });
        $('input[type=checkbox]').attr('checked', false);
        $(formName + ' input[name = create-button]').attr('value', 'Create');
        $(formName + ' input[name = delete-button]').hide();
    }

    function trim_form() {
        $(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
            this.value = $.trim(this.value);
        });
    }

    function loadSubordinate(yearlySalesTargetByVolumeId){
        var subordinateData = "<option value=''>Select Employee/SM</option>";
        if(yearlySalesTargetByVolumeId){
            $.ajax({
                type: "POST",
                url: "${request.contextPath}/${params.controller}/listSubordinate?yearlySalesTargetByVolumeId=" + yearlySalesTargetByVolumeId,
                success: function (data) {
                    $.each(data, function (key, val) {
                        subordinateData += '<option value="' + val.id + '">' + val.customer + '</option>';
                    });
                    $("#employeeId").html(subordinateData);
                },
                dataType: 'json'
            });
        }else{
            $("#employeeId").html(subordinateData);
        }
        clearData();
        $("#monthId").html("<option value=''>Select Month</option>");
    }

    function loadDailyTargetGrid(){
        var productId = $("#productId").val();
        if(!productId){
            return;
        }
        var yearlySalesTargetByVolumeId = $("#yearlySalesTargetId").val();
        if(!yearlySalesTargetByVolumeId){
            return
        }
        var targetMonth = $("#monthId").val();
        if(!targetMonth){
            return
        }
        var employeeId = $("#employeeId").val();
        if(!employeeId){
            return
        }
        $("#jqgrid-grid-dailySalesTargetFinishProduct").jqGrid().setGridParam({url:
                "${request.contextPath}/${params.controller}/list?yearlySalesTargetByVolumeId=" + yearlySalesTargetByVolumeId + "&targetMonth=" + targetMonth + "&employeeId=" + employeeId + "&productId=" + productId,
            datatype: "json",mtype: "POST"}).trigger("reloadGrid");
    }

    function calculateDailySummaryData() {
        var $grid = $('#jqgrid-grid-dailySalesTargetFinishProduct');
        var monthTotal = $grid.jqGrid('getCol', 'quantity', false, 'sum');
        $grid.jqGrid('footerData', 'set', { 'quantity': monthTotal });
        $grid.jqGrid('footerData', 'set', { 'targetDate': 'Month Total' });
    }

    function loadMonthData(yearlySalesTargetByVolumeId){
        var monthSelectionData = "<option value=''>Select Month</option>";
        if(yearlySalesTargetByVolumeId){
            $.ajax({
                type: "POST",
                url: "${request.contextPath}/${params.controller}/getEditableMonthData?yearlySalesTargetByVolumeId=" + yearlySalesTargetByVolumeId,
                success: function (data) {
                    for(var i= data.editableMonthFrom; i < 12; i++){
                        var targetMonth = Number(i) + 1;
                        monthSelectionData += "<option value='" + targetMonth + "'>" + monthListJScript[i] +"</option>";
                    }
                    $("#monthId").html(monthSelectionData);
                },
                dataType: 'json'
            });
        }else{
            $("#monthId").html(monthSelectionData);
        }
    }

    function clearData(){
        $("#jqgrid-grid-dailySalesTargetFinishProduct").jqGrid('clearGridData');
        $("#productId").val("");
        $("#productCode").val("");
        $("#productName").val("");
        $("#targetQuantity").val("");
        $("#productSearchKey").val("");
        calculateDailySummaryData();
    }
</script>