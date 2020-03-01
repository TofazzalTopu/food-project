<script type="text/javascript" language="Javascript">
    var jqGridProductList = "";
    $(document).ready(function () {
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormSalesHeadByVolume").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormSalesHeadByVolume").validationEngine('attach');
        reset_form("#gFormSalesHeadByVolume");
        jQuery('#employeeSearchKey').autocomplete({
            minLength: '1',
            source: function (request, response) {
                var data = {searchKey: request.term};
                var url = '${resource(dir:'salesHeadByVolume', file:'listEmployee')}?query=' + $('#employeeSearchKey').val();
                DocuAutoComplete.setSpinnerSelector('employeeSearchKey').execute(response, url, data, function (item) {
                    item['label'] = "[" + item['code'] + "] " + item['name'];
                    item['value'] = item['label'];
                    return item;
                });
            },
            select: function (event, ui) {
                loadEmployeeDataInField(ui.item.id, ui.item.name, ui.item.code, ui.item.designation);
            }
        }).data("autocomplete")._renderItem = function (ul, item) {
            var accountstype = "";
            if (item) {
                accountstype = '<div style="font-size: 9px; color:#326E93;">' + "PIN: " + item.code + "; Name: " + item.name + "; " + "Enterprise: " + item.enterprise + "; " + "Designation: " + item.designation + '</div>';
            }
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + accountstype).appendTo(ul);
        };
        $('#search-btn-employee-id').click(function () {
            var url = '${resource(dir:'salesHeadByVolume', file:'popupEmployeeListPanel')}';
            var params = {};
            DocuAjax.html(url, params, function (html) {
                $.fancybox(html);
            });
        });

        jQuery('#productSearchKey').autocomplete({
            minLength: '1',
            source: function (request, response) {
                var data = {searchKey: request.term};
                var url = '${resource(dir:'salesHeadByVolume', file:'listProduct')}?query=' + $('#productSearchKey').val();
                DocuAutoComplete.setSpinnerSelector('productSearchKey').execute(response, url, data, function (item) {
                    item['label'] = "[" + item['code'] + "] " + item['name'];
                    item['value'] = item['label'];
                    return item;
                });
            },
            select: function (event, ui) {
                loadProductDataInField(ui.item.id, ui.item.code, ui.item.name);
            }
        }).data("autocomplete")._renderItem = function (ul, item) {
            var accountstype = "";
            if (item) {
                accountstype = '<div style="font-size: 9px; color:#326E93;">' + "Code: " + item.code + "; Name: " + item.name + "; " + "Enterprise: " + item.enterprise + '</div>';
            }
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + accountstype).appendTo(ul);
        };
        $('#search-btn-product-id').click(function () {
            var url = '${resource(dir:'salesHeadByVolume', file:'popupProductListPanel')}';
            var params = {};
            DocuAjax.html(url, params, function (html) {
                $.fancybox(html);
            });
        });
        $("#quantity").format({precision: 0, allow_negative: false, autofix: true});
        $("#targetYear").mask("2099");
        jqGridProductList = $("#jqgrid-grid-finishProduct").jqGrid({
            url:'',
            datatype: "local",
            colNames:[
                'ID',
                'Product Code',
                'Product Name',
                'Quantity',
                ''
            ],
            colModel:[
                {name:'id', index:'id', width:0, hidden:true},
                {name:'productCode', index:'productCode', width:120, align:'center'},
                {name:'productName', index:'productName', width:200, align:'left'},
                {name:'quantity', index:'quantity', width:80, align:'center', sorttype:'number', formatter:"number", editable: true, editrules:{number:true}, formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 0}},
                {name:'delete', index:'delete', width:30, align:'center', sortable:false}
            ],
            rowNum: -1,
            viewrecords:true,
            caption:"Product List for Volume Based Target Setup",
            autowidth:true,
            height:true,
            scrollOffset:0,
            gridview:true,
//            multiselect: true,
            rownumbers: true,
            cellEdit: true,
            cellsubmit: 'clientArray',
            cellurl: null,
            footerrow : true,
            gridComplete: function() {
                calculateSummaryData();
            },
            loadComplete: function (data) {
            },
            onSelectRow: function(rowid, status) {
            },
            afterSaveCell : function(rowid,name,val,iRow,iCol) {
                calculateSummaryData();
            },
            loadError: function (jqXHR, textStatus, errorThrown) {
                MessageRenderer.renderErrorText(jqXHR.responseText,'');
            }
        });
    });

    function calculateSummaryData() {
        var $grid = $('#jqgrid-grid-finishProduct');
        var sumQty = $grid.jqGrid('getCol', 'quantity', false, 'sum');
        $grid.jqGrid('footerData', 'set', { 'quantity': sumQty });
        $grid.jqGrid('footerData', 'set', { 'productCode': 'Total'});
    }
    function loadEmployeeDataInField(employeeId, cusName, code, designation) {
        $("#employeeId").val(employeeId);
        $("#employeePin").val(code);
        $("#employeeName").val(cusName);
        $("#employeeDesignation").val(designation);
    }

    function loadProductDataInField(productId, productCode, productName) {
        $("#productId").val(productId);
        $("#productCode").val(productCode);
        $("#productName").val(productName);
        $("#quantity").focus();
    }

    function executePreConditionSalesHeadByVolume() {
        // trim field vales before process.
        trim_form();
        if (!$("#gFormSalesHeadByVolume").validationEngine('validate')) {
            return false;
        }
        return true;
    }
    function executeAjaxSalesHeadByVolume() {
        if (!executePreConditionSalesHeadByVolume()) {
            return false;
        }
        var data = jQuery("#gFormSalesHeadByVolume").serializeArray();
        var gd = $("#jqgrid-grid-finishProduct").jqGrid('getRowData');
        var itemCount = gd.length;
        if(itemCount <= 0){
            MessageRenderer.renderErrorText("No Product Item Selected");
            return;
        }
        for (var i=0; i < itemCount; ++i) {
            data.push({'name':'salesHeadFinishProduct['+i+'].finishProductId', 'value': gd[i].id});
            data.push({'name':'salesHeadFinishProduct['+i+'].quantity', 'value': gd[i].quantity});
        }
        data.push({'name':'itemCount', 'value': itemCount});
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: data,
            url: "${request.contextPath}/${params.controller}/create",
            success: function (data, textStatus) {
                executePostConditionSalesHeadByVolume(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    reset_form('#gFormSalesHeadByVolume');
                    $("#jqgrid-grid-finishProduct").jqGrid('clearGridData');

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
        return false;
    }
    function executePostConditionSalesHeadByVolume(result) {
        if (result.type == 1) {
            reset_form('#gFormSalesHeadByVolume');
            $("#jqgrid-grid-finishProduct").jqGrid('clearGridData');
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

    function addFinishProductToGrid(){
        var productId = $("#productId").val();
        if(!productId){
            MessageRenderer.renderErrorText("ProductItem is not selected");
            return
        }

        var totalQuantity = Number($("#quantity").val());
        if(totalQuantity <= 0){
            MessageRenderer.renderErrorText("Quantity is empty");
            return
        }
        var productName = $("#productName").val();
        var productCode = $("#productCode").val();

        var rowTo = jqGridProductList.getRowData(productId.toString());
        if (!rowTo.id) {
            var newRowData = [
                { 'id':productId.toString(), 'productCode':productCode.toString(), 'productName':productName.toString(),
                    'quantity':totalQuantity.toString(), 'delete': '<a  href="javascript:deleteProduct(' + productId + ')" class="ui-icon ui-icon-trash" title="Delete"></a>'}
            ];
            jqGridProductList.addRowData(productId.toString(), newRowData[0]);
        }else{
            MessageRenderer.renderErrorText("Product item is already on list");
            return;
        }
        clearProductItem();
        $('#productSearchKey').focus();
    }
    function clearProductItem(){
        $("#productId").val('');
        $("#productName").val('');
        $("#productCode").val('');
        $("#quantity").val('');
        $('#productSearchKey').val('');
    }
    function deleteProduct(productId){

        $('#jqgrid-grid-finishProduct').jqGrid('delRowData', productId);
    }
</script>