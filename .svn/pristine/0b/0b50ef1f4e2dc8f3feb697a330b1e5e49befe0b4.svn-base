<%@ page import="com.docu.commons.CommonConstants; com.bits.bdfp.util.ApplicationConstants" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title>Finish Good Inventory Inquiry</title>

<script type="text/javascript" language="Javascript">
    $(document).ready(function() {

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


        $("#subInventoryList").flexbox('Select Sub Inventory' ,{
            watermark: "Select Sub Inventory",
            width: 260,
            onSelect: function() {

                $("#subWarehouse").val($('#subInventoryList_hidden').val());
            }
        });
        $('#subInventoryList_input').blur(function() {
            if($('#subInventoryList_input').val() == ''){
                $("#subInventoryList").val("");
            }
        });

        $("#jqgrid-grid").jqGrid({
            url:'${request.contextPath}/${params.controller}/finishGoodInquiryList',
            datatype: "json",
            colNames:[
                'Id',
                'Product Code',
                'Product Name',
                'Qty',
                'UOM',
                'Batch Number',
                'Cost Price/Unit',
                'Product Ref. No.',
                'Transaction Ref. No.'
            ],
            colModel:[

                {name:'id',index:'id', width:0, hidden:true},
                {name:'p_code', index:'p_code',width:100,align:'left'},
                {name:'p_name', index:'p_name',width:150,align:'left'},
                {name:'quantity', index:'quantity',width:50,align:'center'},
                {name:'uom', index:'uom',width:50,align:'left'},
                {name:'batch_no', index:'batch_no',width:50,align:'left'},
                {name:'cost', index:'cost',width:50,align:'right'},
                {name:'product_ref_no', index:'product_ref_no',width:200,align:'left'},
                {name:'transaction_no', index:'transaction_no',width:100,align:'center'}
            ],
            rowNum:50,
            rowList:[10,20,30,40,50,60,70,80,90,100,-1],
            pager: '#jqgrid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption:"Product Received",
            autowidth: true,
            height: 230,

            scrollOffset: 0,
            loadComplete: function() {
                var ids = $("#jqgrid-grid").jqGrid('getDataIDs');
                allEmail = '';
                allMobile = '';
                for (key in ids) {
                    var id = $("#jqgrid-grid").getCell(ids[key], 'id');
                    $("#jqgrid-grid").jqGrid('setRowData', ids[key], {edit: '<a  href="javascript:loadApprovalInfo(' + id + ')">' + 'Approval History' + '</a>'});
                }
            },
            onSelectRow: function(rowid, status) {
//                executeEditProduct();
            }
        });
        $("#jqgrid-grid").jqGrid('navGrid', '#jqgrid-pager', {edit:false,add:false,del:false,search:false});
//     $("#jqgrid-grid").gridResize({minWidth:350,maxWidth:800,minHeight:200});
        $(".ui-pg-selbox").children().each(function() {
            if ($(this).val() == -1) {
                $(this).html('All')
            }
        });

        $("#jqgrid-grid-product").jqGrid({
            url:'${request.contextPath}/${params.controller}/issuedQuantityItemList',
            datatype: "json",
            colNames:[
                'Id',
                'Product Code',
                'Product Name',
                'Qty',
                'UOM',
                'Batch No',
                'Cost Price/Unit(AVG)',
                'Reference No',
                'Sub Inventory',
                'Inventory'
            ],
            colModel:[
                {name:'id',index:'id', width:0, hidden:true},
                {name:'product_code', index:'product_code',width:100,align:'left'},
                {name:'product_name', index:'product_name',width:150,align:'left'},
                {name:'out_quantity', index:'out_quantity',width:50,align:'center'},
                {name:'uom', index:'uom',width:50,align:'left'},
                {name:'batch_no', index:'batch_no',width:50,align:'left'},
                {name:'unit_price', index:'unit_price',width:50,align:'right',formatter:'currency',
                    formatoptions:{defaulValue:0,thousandsSeparator:' ',decimalPlaces:2,suffix:''}},

                {name:'reference_no', index:'reference_no',width:80,align:'left'},
                {name:'sub_inventory', index:'sub_inventory',width:100,align:'left'},
                {name:'inventory', index:'inventory',width:100,align:'left'}
            ],
            rowNum:50,
            rowList:[10,20,30,40,50,60,70,80,90,100,-1],
            pager: '#jqgrid-pager-product',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption:"Product Issued (Invoiced)",
            autowidth: true,
            height: 240,

            scrollOffset: 0,
            loadComplete: function() {
            },
            onSelectRow: function(rowid, status) {
//                executeEditProduct();
            }
        });
        $("#jqgrid-grid-product").jqGrid('navGrid', '#jqgrid-pager-product', {edit:false,add:false,del:false,search:false});

//        $(".ui-pg-selbox").children().each(function() {
//            if ($(this).val() == -1) {
//                $(this).html('All')
//            }
//
//        });

        $("#jqgrid-grid-batch").jqGrid({
            url:'${request.contextPath}/${params.controller}/onHandQtyBatchList',
            datatype: "json",
            colNames:[
                'ID',
                'Product Code',
                'Product Name',
                'Batch Number',
                'Batch Total Qty'
            ],
            colModel:[
                {name:'id',index:'id', width:100, hidden: true},
                {name:'product_code',index:'product_code', width:100},
                {name:'product_name',index:'product_name', width:150},
                {name:'batch_no', index:'batch_no',width:100,align:'left'},
                {name:'quantity', index:'quantity',width:80,align:'center'}
            ],
            rowNum:50,
            rowList:[10,20,30,40,50,60,70,80,90,100,-1],
            pager: '#jqgrid-pager-batch',
            sortname: 'name',
            viewrecords: true,
            sortorder: "desc",
            caption:"On Hand Qty Batchwise",
            width: 500,
            height: 230,

            scrollOffset: 0,
            loadComplete: function() {

            },
            onSelectRow: function(rowid, status) {
            }
        });
        $("#jqgrid-grid-batch").jqGrid('navGrid', '#jqgrid-pager-batch', {edit:false,add:false,del:false,search:false});
//     $("#jqgrid-grid").gridResize({minWidth:350,maxWidth:800,minHeight:200});
        $(".ui-pg-selbox").children().each(function() {
            if ($(this).val() == -1) {
                $(this).html('All')
            }

        });

        $("#jqgrid-grid-total").jqGrid({
            url:'${request.contextPath}/${params.controller}/onHandQtyItemList',
            datatype: "json",
            colNames:[
                'ID',
                'Product Code',
                'Product Name',
                'Total Qty'
            ],
            colModel:[
                {name:'id', index:'id',width:100,hidden: true},
                {name:'product_code', index:'product_code',width:100,align:'left'},
                {name:'product_name', index:'product_name',width:200,align:'left'},
                {name:'quantity', index:'quantity',width:100,align:'center'}
            ],
            rowNum:50,
            rowList:[10,20,30,40,50,60,70,80,90,100,-1],
            pager: '#jqgrid-pager-total',
            sortname: 'name',
            viewrecords: true,
            sortorder: "desc",
            caption:"On Hand Qty Itemwise",
            width: 500,
            height: 230,

            scrollOffset: 0,
            loadComplete: function() {

            },
            onSelectRow: function(rowid, status) {
//                executeEditProduct();
            }
        });
        $("#jqgrid-grid-total").jqGrid('navGrid', '#jqgrid-pager-total', {edit:false,add:false,del:false,search:false});

        $(".ui-pg-selbox").children().each(function() {
            if ($(this).val() == -1) {
                $(this).html('All')
            }

        });
        setDateRangeNoLimit('deliveryDateFrom','deliveryDateTo');

    });

    function loadDataByOrder(){
        $("#jqgrid-grid").setGridParam({url: '${resource(dir:'finishGoodWarehouse', file:'finishGoodInquiryList')}?subWarehouse=' + $('#subWarehouse').val()
        +'&deliveryDateFrom='+$('#deliveryDateFrom').val()+'&deliveryDateTo='+$('#deliveryDateTo').val()});
        $("#jqgrid-grid").trigger("reloadGrid");

        $("#jqgrid-grid-product").setGridParam({url: '${resource(dir:'finishGoodWarehouse', file:'issuedQuantityItemList')}?subWarehouse=' + $('#subWarehouse').val()
        +'&deliveryDateFrom='+$('#deliveryDateFrom').val()+'&deliveryDateTo='+$('#deliveryDateTo').val()});
        $("#jqgrid-grid-product").trigger("reloadGrid");

        $("#jqgrid-grid-batch").setGridParam({url: '${resource(dir:'finishGoodWarehouse', file:'onHandQtyBatchList')}?subWarehouse=' + $('#subWarehouse').val()
        +'&deliveryDateFrom='+$('#deliveryDateFrom').val()+'&deliveryDateTo='+$('#deliveryDateTo').val()});
        $("#jqgrid-grid-batch").trigger("reloadGrid");

        $("#jqgrid-grid-total").setGridParam({url: '${resource(dir:'finishGoodWarehouse', file:'onHandQtyItemList')}?subWarehouse=' + $('#subWarehouse').val()
        +'&deliveryDateFrom='+$('#deliveryDateFrom').val()+'&deliveryDateTo='+$('#deliveryDateTo').val()});
        $("#jqgrid-grid-total").trigger("reloadGrid");
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
                MessageRenderer.renderErrorText(XMLHttpRequest.responseText)
            },
            complete: function () {

            }
        });

    }
    function loadSubInventory(id) {
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: "POST",
            url: '${resource(dir:'subWarehouse', file:'listSubWarehouse')}?id=' + id,
            dataType: 'json',
            success: function (data, textStatus) {

                $("#subInventoryList").empty();
                $("#subInventoryList").flexbox(data ,{
                    watermark: "Select Sub Inventory",
                    width: 260,
                    onSelect: function() {

                        $("#subWarehouse").val($('#subInventoryList_hidden').val());
                    }
                });
                $('#subInventoryList_input').blur(function() {
                    if($('#subInventoryList_input').val() == ''){
                        $("#subInventoryList").val("");
                    }
                });

            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                MessageRenderer.renderErrorText(XMLHttpRequest.responseText)
            },
            complete: function () {
                SubmissionLoader.hideFrom();
            }
        });

    }
</script>
<div class="main_container">
    <div class="content_container">
        <h1>Finish Good Inventory Inquiry</h1>
        <h3>Finish Good Inventory Information</h3>
        <form name='gFormSecondaryDemandOrderSearch' id='gFormSecondaryDemandOrderSearch'>
            <g:hiddenField name="id" value="${secondaryDemandOrder?.id}" />
            <g:hiddenField name="version" value="${secondaryDemandOrder?.version}" />
            <g:hiddenField name="idEnterprise" id="idEnterprise" value=""/>
            <g:hiddenField name="idBusiness" id="idBusiness" value=""/>
            <div id="remote-content-territoryConfiguration"></div>
            <br>

            <div class="element_row_content_container lightColorbg pad_bot0">
                <table>
                    <script type="text/javascript">
                        jQuery(document).ready(function () {
                            $("#deliveryDateFrom,#deliveryDateTo").datepicker(
                                    { dateFormat: 'dd-mm-yy',
                                        changeMonth:true,
                                        changeYear:true
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
                                <g:if test="${list.size()==1}">
                                    <div  class='element-input inputContainer'>

                                        <g:textField name="enterPriseName" readonly="readonly" value="${list[0].name}" />
                                        <g:hiddenField name="enterpriseConfiguration.id"  id="enterpriseConfiguration" value="${list[0].id}" />

                                    </div>
                                    <script type="text/javascript">
                                   jQuery(document).ready(function () {
                                    loadWarehouse(${list[0].id});
                                            });
                                    </script>
                                </g:if>
                                <g:else>
                                    <div  class='element-input inputContainer'>
                                        <div id="enterpriseWarList" style="width: 350px;"></div>
                                        <script type="text/javascript">

                                            jQuery(document).ready(function () {
                                                var data=${result}

                                                        $("#enterpriseWarList").empty();
                                                $("#enterpriseWarList").flexbox(data ,{
                                                    watermark: "Select Enterprise",
                                                    width: 260,
                                                    onSelect: function() {

                                                        $("#enterpriseConfiguration").val($('#enterpriseWarList_hidden').val());
                                                        loadWarehouse($('#enterpriseWarList_hidden').val());

                                                    }
                                                });
                                                $('#enterpriseWarList_input').blur(function() {
                                                    if($('#enterpriseWarList_input').val() == ''){
                                                        $("#enterpriseWarList").val("");
                                                    }
                                                });

                                            });
                                        </script>

                                        <g:hiddenField name="enterpriseConfiguration.id"  id="enterpriseConfiguration" value="" />


                                    </div>
                                </g:else>

                            </g:if>
                            <g:else>
                                <div  class='element-input inputContainer'>
                                    <g:textField name="enterPriseName" readonly="readonly" value="" />
                                    <script type="text/javascript">
                                        jQuery(document).ready(function () {
                                            MessageRenderer.showHeaderMessage("You have no assigned enterprise, please assign enterprise first.",0)
                                        });
                                    </script>
                                </div>
                            </g:else>
                        </td>

                    </tr>

                    %{--<tr>--}%
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
                    <tr >
                        <td>
                            <label class="txtright bold hight1x width1x">
                                Sub Inventory

                            </label>

                        </td>

                        <td>
                                    <div  class='element-input inputContainer'>
                                        %{--<div id="subInventoryList" style="width: 350px;"></div>--}%

                                        %{--<g:hiddenField name="subWarehouse.id"  id="subWarehouse" value="" />--}%

                                        <g:select from="${subWarehouseList}" name="subWarehouse.id"
                                                  id="subWarehouse" class="width300"
                                                  optionKey="id"
                                                  optionValue="name"
                                                  onchange=""
                                                  noSelection="['':'All Sub Inventory']"
                                        />

                                </div>

                        </td>

                    </tr>
                    <tr hidden="hidden">
                        <td>
                            <label class="txtright bold hight1x width1x" style="width: 150px;">
                                Date Range From

                            </label>
                        </td>

                        <td>
                            <g:textField name="deliveryDateFrom" id="deliveryDateFrom" value="" class="width120" />
                        </td>
                        <td>
                            <label class="txtright bold hight1x width1x" style="width: 60px">
                                To
                            </label>

                        </td>
                        <td>
                            <g:textField name="deliveryDateTo" id="deliveryDateTo"  value="" class="width120"/>
                        </td>
                    </tr>

                    <tr>
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
                <div class="jqgrid-container width1000">
                    <table id="jqgrid-grid"></table>

                    <div id="jqgrid-pager"></div>
                </div>
                <br/>
                <div class="jqgrid-container">
                    <table id="jqgrid-grid-product"></table>

                    <div id="jqgrid-pager-product"></div>
                </div>
%{--<table>--}%
    %{--<tr>--}%
        %{--<td>--}%
            %{--<div class="jqgrid-container">--}%
                %{--<table id="jqgrid-grid-product"></table>--}%

                %{--<div id="jqgrid-pager-product"></div>--}%
            %{--</div>--}%

        %{--</td>--}%
    %{--</tr>--}%
%{--</table>--}%
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