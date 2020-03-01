<%@ page import="com.docu.commons.CommonConstants; com.bits.bdfp.util.ApplicationConstants;" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title>DP Inventory Inquiry</title>

<script type="text/javascript" language="Javascript">
    $(document).ready(function () {

        $("#warehouseList").flexbox('Select Inventory', {
            watermark: "Select Inventory",
            width: 260,
            onSelect: function () {
                $("#warehouse").val($('#warehouseList_hidden').val());
//                        loadBusinessUnit($('#enterpriselist_hidden').val());
            }
        });
        $('#warehouseList_input').blur(function () {
            if ($('#warehouseList_input').val() == '') {
                $("#warehouseList").val("");
            }
        });


        $("#subInventoryList").flexbox('Select Sub Inventory', {
            watermark: "Select Sub Inventory",
            width: 260,
            onSelect: function () {

                $("#subWarehouse").val($('#subInventoryList_hidden').val());
            }
        });
        $('#subInventoryList_input').blur(function () {
            if ($('#subInventoryList_input').val() == '') {
                $("#subInventoryList").val("");
            }
        });


        $("#jqgrid-grid").jqGrid('navGrid', '#jqgrid-pager', {edit: false, add: false, del: false, search: false});
//     $("#jqgrid-grid").gridResize({minWidth:350,maxWidth:800,minHeight:200});
        $(".ui-pg-selbox").children().each(function () {
            if ($(this).val() == -1) {
                $(this).html('All')
            }

        });

        $("#jqgrid-grid-product").jqGrid({
            datatype: "local",
            colNames: [

                'Id',
                'Product Code',
                'Product Name',
                'Received Qty',
                'Delivered Qty',
                'Remaining Qty',
                'Unit Price',
                'Amount'
            ],
            colModel: [

                {name: 'id', index: 'id', width: 0, hidden: true},
                {name: 'code', index: 'code', width: 150, align: 'left'},
                {name: 'name', index: 'name', width: 200, align: 'left'},
                {name: 'received_quantity', index: 'received_quantity', width: 80, align: 'right'},
                {name: 'delivered_quantity', index: 'delivered_quantity', width: 80, align: 'right'},
                {name: 'remaining_quantity', index: 'remaining_quantity', width: 80, align: 'right'},
                {
                    name: 'unit_price',
                    index: 'unit_price',
                    width: 80,
                    align: 'right',
                    formatter: 'currency',
                    formatoptions: {decimalSeparator: ".", thousandsSeparator: ",", decimalPlaces: 2}
                },
                { name: 'amount', index: 'amount', width: 60,
                    align: 'right',
                    formatter: 'currency',
                    formatoptions: {decimalSeparator: ".", thousandsSeparator: ",", decimalPlaces: 2}
                }
            ],
            rowNum: 50,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#jqgrid-pager-product',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Product Status",
            width: 1100,
            height: true,
            footerrow : true,
            scrollOffset: 0,
            loadComplete: function (data) {
                $.each(data.rows, function (i, item) {
                    var rowData = $("#jqgrid-grid-product").jqGrid('getRowData', data.rows[i].id);
                    rowData.amount = rowData.unit_price * rowData.remaining_quantity;
                    $('#jqgrid-grid-product').jqGrid('setRowData', data.rows[i].id, rowData);
                });
                var $grid = $('#jqgrid-grid-product');
                var colSum = $grid.jqGrid('getCol', 'amount', false, 'sum');
                $grid.jqGrid('footerData', 'set', { 'amount': colSum.toFixed(2) , unit_price:'Total Amount'},false);
            },
            onSelectRow: function (rowid, status) {
//                executeEditProduct();
            }
        });
        $("#jqgrid-grid-product").jqGrid('navGrid', '#jqgrid-pager-product', {
            edit: false,
            add: false,
            del: false,
            search: false
        });

        $(".ui-pg-selbox").children().each(function () {
            if ($(this).val() == -1) {
                $(this).html('All')
            }
        });

        $("#jqgrid-grid-batch").jqGrid('navGrid', '#jqgrid-pager-batch', {
            edit: false,
            add: false,
            del: false,
            search: false
        });
        $(".ui-pg-selbox").children().each(function () {
            if ($(this).val() == -1) {
                $(this).html('All')
            }
        });
        $("#jqgrid-grid-total").jqGrid('navGrid', '#jqgrid-pager-total', {
            edit: false,
            add: false,
            del: false,
            search: false
        });

        $(".ui-pg-selbox").children().each(function () {
            if ($(this).val() == -1) {
                $(this).html('All')
            }
        });
        setDateRangeNoLimit('deliveryDateFrom', 'deliveryDateTo');
    });

    function loadDataByOrder(warehouseId) {
        loadSubInventoryList(warehouseId);
        if (warehouseId) {
            $("#jqgrid-grid-product").setGridParam({
                url: '${resource(dir:'finishGoodWarehouse', file:'issuedQuantityItemListByDp')}?warehouseId=' + warehouseId,
                datatype: 'json'
            });
            $("#jqgrid-grid-product").trigger("reloadGrid");
        } else {
            $("#jqgrid-grid-product").jqGrid('clearGridData');
        }
    }

    function loadSubInventoryList(inventoryId) {
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: "POST",
            url: '${resource(dir:'subWarehouse', file:'listSubWarehouse')}?id=' + inventoryId,
            dataType: 'json',
            success: function (data, textStatus) {
                $('#subWarehouse').val('');
                var options = '<option value="">All</option>';
                for (var i = 0; i < data.results.length; i++) {
                    options += '<option value="' + data.results[i].id + '">' + data.results[i].name + '</option>';
                }
                $("#subWarehouse").html(options);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                }  else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete: function () {
                SubmissionLoader.hideFrom();
            }
        });
    }

    function loadDataBySubInventory(subInventoryId) {
        var warehouseId = $('#warehouseId').val();
        $("#jqgrid-grid-product").setGridParam({
            url: '${resource(dir:'finishGoodWarehouse', file:'issuedQuantityItemListByDp')}?warehouseId=' + warehouseId + '&subWarehouseId=' + subInventoryId,
            datatype: 'json'
        });
        $("#jqgrid-grid-product").trigger("reloadGrid");
    }

    function loadWarehouse(id) {
        jQuery.ajax({
            type: "POST",
            url: '${resource(dir:'warehouse', file:'listWarehouse')}?id=' + id,
            dataType: 'json',
            success: function (data, textStatus) {
                $("#warehouseList").empty();
                $("#warehouseList").flexbox(data, {
                    watermark: "Select Inventory",
                    width: 260,
                    onSelect: function () {
                        $("#warehouse").val($('#warehouseList_hidden').val());
                        loadSubInventory($('#warehouseList_hidden').val());
                    }
                });
                $('#warehouseList_input').blur(function () {
                    if ($('#warehouseList_input').val() == '') {
                        $("#warehouseList").val("");
                    }
                });

            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                }  else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete: function () {

            }
        });

    }
    function loadSubInventory(id) {

        jQuery.ajax({
            type: "POST",
            url: '${resource(dir:'subWarehouse', file:'listSubWarehouse')}?id=' + id,
            dataType: 'json',
            success: function (data, textStatus) {

                $("#subInventoryList").empty();
                $("#subInventoryList").flexbox(data, {
                    watermark: "Select Sub Inventory",
                    width: 260,
                    onSelect: function () {

                        $("#subWarehouse").val($('#subInventoryList_hidden').val());
                    }
                });
                $('#subInventoryList_input').blur(function () {
                    if ($('#subInventoryList_input').val() == '') {
                        $("#subInventoryList").val("");
                    }
                });

            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                }  else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete: function () {

            }
        });

    }
</script>

<div class="main_container">
    <div class="content_container">
        <h1>DP Inventory Inquiry</h1>

        <h3>Distribution Point Inventory Information</h3>

        <form name='gFormSecondaryDemandOrderSearch' id='gFormSecondaryDemandOrderSearch'>
            <g:hiddenField name="id" value="${secondaryDemandOrder?.id}"/>
            <g:hiddenField name="version" value="${secondaryDemandOrder?.version}"/>
            <g:hiddenField name="idEnterprise" id="idEnterprise" value=""/>
            <g:hiddenField name="idBusiness" id="idBusiness" value=""/>
            <div id="remote-content-territoryConfiguration"></div>
            <br>

            <div class="element_row_content_container lightColorbg pad_bot0">
                <table>
                    <script type="text/javascript">
                        jQuery(document).ready(function () {
                            $("#deliveryDateFrom,#deliveryDateTo").datepicker(
                                    {
                                        dateFormat: 'dd-mm-yy',
                                        changeMonth: true,
                                        changeYear: true
                                    });

                            $('#deliveryDateFrom').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});
                            $('#deliveryDateTo').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});
                        });
                    </script>

                    <tr>
                        <td>
                            <label class="txtright bold hight1x width1x">
                                Enterprise
                            </label>
                        </td>

                        <td>
                            <g:if test="${list}">
                                <g:if test="${list.size() == 1}">
                                    <div class='element-input inputContainer'>
                                        <g:textField name="enterPriseName" readonly="readonly" value="${list[0].name}"/>
                                        <g:hiddenField name="enterpriseConfiguration.id" id="enterpriseConfiguration"
                                                       value="${list[0].id}"/>
                                    </div>
                                %{--<script type="text/javascript">--}%
                                %{--jQuery(document).ready(function () {--}%
                                %{--loadWarehouse(${list[0].id});--}%
                                %{--});--}%
                                %{--</script>--}%
                                </g:if>
                                <g:else>
                                    <div class='element-input inputContainer'>
                                        <div id="enterpriseWarList" style="width: 350px;"></div>
                                        <script type="text/javascript">
                                            jQuery(document).ready(function () {
                                                var data = ${result}
                                                        $("#enterpriseWarList").empty();
                                                $("#enterpriseWarList").flexbox(data, {
                                                    watermark: "Select Enterprise",
                                                    width: 260,
                                                    onSelect: function () {
                                                        $("#enterpriseConfiguration").val($('#enterpriseWarList_hidden').val());
                                                        //loadWarehouse($('#enterpriseWarList_hidden').val());
                                                    }
                                                });
                                                $('#enterpriseWarList_input').blur(function () {
                                                    if ($('#enterpriseWarList_input').val() == '') {
                                                        $("#enterpriseWarList").val("");
                                                    }
                                                });
                                            });
                                        </script>
                                        <g:hiddenField name="enterpriseConfiguration.id" id="enterpriseConfiguration"
                                                       value=""/>
                                    </div>
                                </g:else>

                            </g:if>
                            <g:else>
                                <div class='element-input inputContainer'>
                                    <g:textField name="enterPriseName" readonly="readonly" value=""/>
                                    <script type="text/javascript">
                                        jQuery(document).ready(function () {
                                            MessageRenderer.showHeaderMessage("You have no assigned enterprise, please assign enterprise first.", 0)
                                        });
                                    </script>
                                </div>
                            </g:else>
                        </td>
                    </tr>
                %{--<tr hidden="hidden">--}%
                %{--<td>--}%
                %{--<label class="txtright bold hight1x width1x">--}%
                %{--<g:message code='businessUnitConfiguration.code.label' default='Inventory' />--}%

                %{--</label>--}%

                %{--</td>--}%

                %{--<td>--}%
                %{--<div  class='element-input inputContainer'>--}%
                %{--<div id="warehouseList" style="width: 350px;"></div>--}%

                %{--<g:hiddenField name="warehouse.id"  id="warehouse" value="" />--}%


                %{--</div>--}%


                %{--</td>--}%

                %{--</tr>--}%
                    <g:if test="${warehouseList}">
                        <tr>
                            <td>
                                <label for="warehouse" class="txtright bold hight1x width130"
                                       style="width: 160px;">Inventory:
                                    <span class="mendatory_field">*</span>
                                </label>
                            </td>
                            <td>
                                <g:select name="warehouse"
                                          class="validate[required]"
                                          style="width: 300px; height: 20px;" id="warehouseId"
                                          optionKey="id" onchange="loadDataByOrder(this.value)"/>
                            </td>

                            <td>
                                <label for="warehouse" class="txtright bold hight1x width130"
                                       style="width: 160px;">Sub-Inventory:
                                %{--<span class="mendatory_field">*</span>--}%
                                </label>
                            </td>
                            <td>
                                <g:select name="subWarehouse"
                                          class="validate[required]"
                                          style="width: 250px; height: 20px;"
                                          optionKey="id" onchange="loadDataBySubInventory(this.value)"/>
                            </td>
                        </tr>
                        <script type="text/javascript">
                            $(document).ready(function () {
                                $('#warehouseId').val('');
                                var options = '<option value="">Please Select</option>';
                                for (var i = 0; i < ${warehouseCount}; i++) {
                                    options += '<option value="' + ${warehouseList}[i].id + '">' + ${warehouseList}[i].name + '</option>';
                                }
                                $("#warehouseId").html(options);
                            });
                        </script>
                    </g:if>
                %{--<tr>--}%
                %{--<td>--}%
                %{--<label class="txtright bold hight1x width1x">--}%
                %{--<g:message code='businessUnitConfiguration.code.label' default='Distribution Point' />--}%

                %{--</label>--}%

                %{--</td>--}%

                %{--<td>--}%
                %{--<g:select name="distributionPoint.id"--}%
                %{--from="${distributionPointList}"--}%
                %{--id="distributionPoint"--}%
                %{--style="width: 165px; height: 20px;"--}%
                %{--optionKey="id" optionValue="name" value="" noSelection="['': 'Please Select']"--}%
                %{--onchange="loadDataByOrder(this.value);"/>--}%
                %{--</td>--}%

                %{--</tr>--}%
                    <tr hidden="hidden">
                        <td>
                            <label class="txtright bold hight1x width1x" style="width: 150px;">
                                Date Range From
                            </label>
                        </td>

                        <td>
                            <g:textField name="deliveryDateFrom" id="deliveryDateFrom" value="" class="width120"/>
                        </td>
                        <td>
                            <label class="txtright bold hight1x width1x" style="width: 60px">
                                To
                            </label>
                        </td>
                        <td>
                            <g:textField name="deliveryDateTo" id="deliveryDateTo" value="" class="width120"/>
                        </td>
                    </tr>

                    <tr hidden="hidden">
                        <td>
                            <div class="buttons">
                                <span class="button"><input type="button" name="add-button" id="add-button"
                                                            class="ui-button ui-widget ui-state-default ui-corner-all"
                                                            value="Show"
                                                            onclick="loadDataByOrder();"/></span>
                            </div>
                        </td>
                    </tr>
                </table>
                <table>
                    <tr>
                        <td>
                            <div class="jqgrid-container">
                                <table id="jqgrid-grid"></table>

                                <div id="jqgrid-pager"></div>
                            </div>
                        </td>
                        <td>
                            <div class="jqgrid-container">
                                <table id="jqgrid-grid-product"></table>

                                <div id="jqgrid-pager-product"></div>
                            </div>
                        </td>
                    </tr>
                </table>
            </div>
        </form>

        <div class="clear"></div>
        <div>
            <table>
                <tr>
                    <td>
                        <div class="jqgrid-container">
                            <table id="jqgrid-grid-batch"></table>

                            <div id="jqgrid-pager-batch"></div>
                        </div>
                    </td>
                    <td>
                        <div class="jqgrid-container">
                            <table id="jqgrid-grid-total"></table>

                            <div id="jqgrid-pager-total"></div>
                        </div>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</div>