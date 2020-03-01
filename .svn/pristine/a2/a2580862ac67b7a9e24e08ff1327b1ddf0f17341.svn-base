<script type="text/javascript">
    var jqGridProductList = "";
    $(document).ready(function(){
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

        jqGridProductList = $("#jqgrid-grid-finishProduct").jqGrid({
            url:"${resource(dir:'salesHeadByVolume', file:'listFinishProductBySalesHead')}?salesHeadByVolumeId=${salesHeadByVolume.id}",
            datatype: "json",
            mtype: "POST",
            %{--postData : {salesHeadByVolumeId: ${salesHeadByVolume.id}},--}%
            colNames:[
                'ID',
                'salesHeadFinishProductId',
                'Product Code',
                'Product Name',
                'Quantity',
                ''
            ],
            colModel:[
                {name:'id', index:'id', width:0, hidden:true},
                {name:'salesHeadFinishProductId', index:'salesHeadFinishProductId', width:0, hidden:true},
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
//                $.each(data.rows, function (i, item) {
//                    var rowData = $("#jqgrid-grid-finishProduct").jqGrid('getRowData', data.rows[i].id);
//                    rowData.delete = '<a  href="javascript:deleteProduct(' + data.rows[i].id + ')" class="ui-icon ui-icon-trash" title="Delete"></a>';
//                    $('#jqgrid-grid-finishProduct').jqGrid('setRowData', data.rows[i].id, rowData);
//                });
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
                { 'id':productId.toString(), 'salesHeadFinishProductId': '', 'productCode':productCode.toString(), 'productName':productName.toString(),
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
<g:hiddenField name="id" id="salesHeadByVolumeId" value="${salesHeadByVolume?.id}"/>
<g:hiddenField name="version" value="${salesHeadByVolume?.version}"/>
<g:hiddenField name="targetYear" value="${salesHeadByVolume?.targetYear}"/>
<g:hiddenField name="employee.id" id="employeeId" value="${salesHeadByVolume?.employee?.id}"/>
<h3>Current Sales Head Information</h3>
<div class="element_container_big">
    <div class="block-title">
        <div class="element-title width190">Employee PIN</div>
        <div class="element-title input_width320">Employee Name</div>
        <div class="element-title input_width200">Designation</div>
        <div class="clear"></div>
    </div>
    <div class="block-input">
        <div class="element-input inputContainer value width190">
            <g:textField class="width100 validate[required]" name="pin" id="pin" value="${salesHeadByVolume?.employee?.code}" readonly="readonly" />
        </div>
        <div class="element-input inputContainer value input_width320">
            <g:textField name="name" class="width300" readonly="readonly" value="${salesHeadByVolume?.employee?.name}" />
        </div>
        <div class="element-input inputContainer value input_width200">
            <g:textField name="designation" class="width200" readonly="readonly" value="${salesHeadByVolume?.employee?.designation?.name}" />
        </div>
        <div class="clear"></div>
    </div>
</div>
<h3>Replace Sales Head Information</h3>
<div class="element_container_big">
    <div class="block-title">
        <div class="element-title input_width320">New Sales Head</div>

        <div class="clear"></div>
    </div>

    <div class="block-input">
        <div class='element-input-td inputContainer width320'>
            <input type="text" id="employeeSearchKey" name="customerSearchKey" class="width290"/>
            <span id="search-btn-employee-id" title="" role="button"
                  class="fld-btn ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only floatR">
                <span class="ui-button-icon-primary ui-icon ui-icon-search"></span>
                <span class="ui-button-text"></span>
            </span>
        </div>
        <div class="clear"></div>
    </div>
</div>
<div class="element_container_big">
    <div class="block-title">
        <div class="element-title width190">Employee PIN</div>
        <div class="element-title input_width320">Employee Name</div>
        <div class="element-title input_width200">Designation</div>
        <div class="clear"></div>
    </div>

    <div class="block-input">
        <div class="element-input inputContainer value width190">
            <g:textField class="width100" name="employeePin" id="employeePin" value="" readonly="readonly" />
        </div>
        <div class="element-input inputContainer value input_width320">
            <g:textField name="employeeName" class="width300" readonly="readonly" value="" />
        </div>
        <div class="element-input inputContainer value input_width200">
            <g:textField name="employeeDesignation" class="width200" readonly="readonly" value="" />
        </div>
        <div class="clear"></div>
    </div>
</div>
<div class="element_container_big">
    <div class="block-title">
        <div class="element-title input_width420">Select New Product<span class="mendatory_field"> * </span></div>
        <div class="clear"></div>
    </div>

    <div class="block-input">
        <div class='element-input-td inputContainer width420'>
            <input type="text" id="productSearchKey" name="productSearchKey" class="width390"/>
            <span id="search-btn-product-id" title="" role="button"
                  class="fld-btn ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only floatR">
                <span class="ui-button-icon-primary ui-icon ui-icon-search"></span>
                <span class="ui-button-text"></span>
            </span>
        </div>
        <div class="clear"></div>
    </div>
</div>

<div class="element_container_big">
    <div class="block-title">
        <div class="element-title width190">Product Code<span class="mendatory_field"> * </span></div>
        <div class="element-title input_width320">Product Name<span class="mendatory_field"> * </span></div>
        <div class="element-title input_width200">Quantity <span class="mendatory_field"> * </span></div>
        <div class="clear"></div>
    </div>

    <div class="block-input">
        <g:hiddenField name="productId" id="productId" value=""/>
        <div class="element-input inputContainer value width190">
            <g:textField class="width180" name="productCode" id="productCode" value="" readonly="readonly" />
        </div>
        <div class="element-input inputContainer value input_width320">
            <g:textField name="productName" class="width300" readonly="readonly" value="" />
        </div>
        <div class="element-input inputContainer value input_width200">
            <g:textField name="quantity" class="width90" value="" />
            <span class="button"><input type="button" name="add-button" id="add-button-salesHeadByVolume"
                                        class="ui-button ui-widget ui-state-default ui-corner-all"
                                        value="Add"
                                        onclick="addFinishProductToGrid();"/></span>
        </div>
        <div class="clear"></div>
    </div>
</div>
<div class="clear"></div>
<div class="jqgrid-container">
    <table id="jqgrid-grid-finishProduct"></table>
</div>
<div class="clear"></div>
<div class="buttons" style="margin-left:10px;">
    <span class="button"><input type="button" name="create-button" id="create-button-salesHeadByVolume"
        class="ui-button ui-widget ui-state-default ui-corner-all"
        value="Update"
        onclick="executeAjaxSalesHeadByVolume();"/>
    </span>
</div>