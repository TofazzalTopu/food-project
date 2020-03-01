<%@ page import="com.docu.commons.CommonConstants; com.bits.bdfp.util.ApplicationConstants; com.bits.bdfp.inventory.demandorder.SecondaryDemandOrder" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title>Update Order Quantity</title>

<g:javascript src="jquery/watermark/jquery.watermark.js"/>
<script type="text/javascript" language="Javascript">

    var allElements = new Array();
    var elementIndex = 0;
    var lastSel = -1;
    var qtyText = '';
    function checkForValue(elem) {
        if (!elem) {
            return [false, "Please enter marks."];
        }
        else {
            return [true, ""];
        }
    }
    var updateOrderEditor = {
        onEnterKeyPressToGridCell: function () {
            var callback = {
                type: 'keydown',
                fn: function (e) {
                    var key = e.charCode || e.keyCode;

                    if (key == 9) {
                        if (this.name == allElements[allElements.length - 1].name) {
                            e.preventDefault();
                            jQuery('#jqgrid-grid').restoreRow(lastSel);
                            updateOrderEditor.editGridCell(lastSel)

                        }
                    }
                    else if (key == 13) {
                        jQuery('#jqgrid-grid').restoreRow(lastSel);
                        updateOrderEditor.editGridCell(lastSel)

                    }
                }
            };

            return callback;
        },

        editGridCell: function (rowid) {

            var qty = qtyText.value
            if(qty && rowid){
                if(isNaN(qty)){
                    alert("Please enter number as quantity");
                }
                else{
                    SubmissionLoader.showTo();
                    $.ajax({
                        type: "POST",
                        url: "${resource(dir:'secondaryDemandOrderDetails', file:'updateDetails')}?id=" + rowid + "&qty=" + qty,

                        datatype: 'json',
                        beforeSend: function (jqXHR, settings) {
                            $("#loader_icon").show();
                        },
                        success: function (data) {
                            $("#jqgrid-grid").jqGrid('setCell', (rowid), 'qty', qty);
                            $("#qty").val(qty);
                            $("#jqgrid-grid").trigger("reloadGrid");
                            MessageRenderer.render(data);
                        },
                        complete: function () {
                            SubmissionLoader.hideFrom();
//                            $("#loader_icon").hide();
                        }
                    });

                }

            }

            else{
                alert("Please enter quantity")
            }





        }
    }
    $(document).ready(function() {
        loadOrderNo()
        $("#jqgrid-grid").jqGrid({
            url:'' ,
            datatype: "json",
            colNames:[

                'Id',
                'PId',
                'Product Code',
                'Product',
                'Rate',
                'Quantity',
                'Amount'

            ],
            colModel:[

                {name:'id',index:'id', width:0, hidden:true},
                {name:'pid',index:'pid', width:0, hidden:true},
                {name:'productCode', index:'productCode',width:100,align:'left'},
                {name:'product', index:'product',width:100,align:'left'},
                {name:'rate', index:'rate',width:100,align:'left'},
                {name:'qty', index:'qty',width:100,align:'left', editable: true, edittype: 'text', resizable: true,
                    editoptions: {dataInit: function (elem) {
                        qtyText = elem;
                        $(elem).focus(function () {
                            this.select();
                        })
                    }, dataEvents: [updateOrderEditor.onEnterKeyPressToGridCell()]}, editrules: {custom: true, custom_func: checkForValue}},
                {name:'amount', index:'amount',width:100,align:'left'}

            ],
            rowNum:50,
            rowList:[10,20,30,40,50,60,70,80,90,100,-1],
            pager: '#jqgrid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption:"All Product Information",
            autowidth: true,
            height: 250,
            footerrow:true,
            scrollOffset: 0,
            loadComplete: function() {
                var myGrid = $("#jqgrid-grid");
                var sum = myGrid.jqGrid('getCol', 'amount', false, 'sum');
                myGrid.jqGrid('footerData','set', {productCode: 'Total:', amount: sum});

            },
            onSelectRow: function(rowid, status) {
                executeEditProduct();

                elementIndex = 0;
                allElements = new Array();
                if (rowid && rowid !== lastSel) {

                    jQuery('#jqgrid-grid').restoreRow(lastSel);
                    $("#jqgrid-grid").editRow(rowid, true, false, false, false, false);
                    lastSel = rowid;
                }
                if (rowid == lastSel) {
                    jQuery("#jqgrid-grid").jqGrid('editRow', rowid, true);
                    lastSel = rowid;

                }
            }
        });
        $("#jqgrid-grid").jqGrid('navGrid', '#jqgrid-pager', {edit:false,add:false,del:false,search:false});

        $(".ui-pg-selbox").children().each(function() {
            if ($(this).val() == -1) {
                $(this).html('All')
            }

        });

        $('#search-btn-customer-id').click(function(){

            CustomerInfo.popupCustomerListPanel($('#enterpriseConfiguration').val());

        });
        $('#search-btn-product-id').click(function() {

            CustomerInfo.popupProductListPanel();
        });

    });
    function loadOrderNo(){
        jQuery('#searchOrderKey').autocomplete({
            minLength:'1',
            source:function (request, response) {
                //EmployeeRegister.employeeCoreInfoId = null;
                $('#popEmpDetails').html("");
                var data = {searchKey:request.term};
                var url = '${resource(dir:'secondaryDemandOrder', file:'listOrderNoAutoCompleteForUpdate')}' ;
                DocuAutoComplete.setSpinnerSelector('searchOrderKey').execute(response, url, data, function (item) {
                    item['label'] = item['order_no'];
                    item['value'] = item['label'];
                    return item;
                });
            },
            select:function (event, ui) {

                $("#searchOrderKey").val(ui.item.order_no);

                $('#enterpriselist').setValue(ui.item.ename);

                $("#enterpriseConfiguration").val(ui.item.eid);

                $("#searchKey").val(ui.item.cus_name);
                $("#customerMaster").val(ui.item.cid);
                $("#id").val(ui.item.id);

                loadDataByOrder()
            }
        }).data("autocomplete")._renderItem = function (ul, item) {
            var accountstype = "";
            if (item) {
                accountstype = '<div style="font-size: 9px; color:#326E93;">' +" Order No: " +item.order_no+ '</div>';
            }
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + accountstype).appendTo(ul);

        }
    }
    function loadDataByOrder(){
        $("#jqgrid-grid").setGridParam({url: '${resource(dir:'secondaryDemandOrder', file:'listProductByOrder')}?orderNo=' + $('#searchOrderKey').val()
        });
        $("#jqgrid-grid").trigger("reloadGrid");

    }

    function editDemandOrder(id){
        window.open('${createLink(uri: '/')}secondaryDemandOrder/editDemandOrder?id=' + id);
    }




    function loadProduct() {
        $('#searchProductKey').blur(function () {
            if ($('#searchProductKey').val() == '') {
                $("#finishProduct").val('');
            }
        });
        jQuery('#searchProductKey').autocomplete({
            minLength:'1',
            source:function (request, response) {
                if ( $("#customerMaster").val()){
                    var data = {searchKey:request.term};
                    var url = '${resource(dir:'secondaryDemandOrder', file:'listProduct')}?customerId=' +  $("#customerMaster").val()+ "&query=" + $('#searchProductKey').val()+ + "&orderNo=" + $('#searchOrderKey').val();
                    DocuAutoComplete.setSpinnerSelector('searchProductKey').execute(response, url, data, function (item) {
                        item['label'] = item['code']+"-"+item['name'];
                        item['value'] = item['label'];
                        return item;
                    });
                }

            },
            select:function (event, ui) {

                CustomerInfo.setProductInformation(ui.item.id, ui.item.value);

                $('#rate').val(ui.item.price)
                $('#productCode').val(ui.item.code)
                $('#product').val(ui.item.name)

            }
        }).data("autocomplete")._renderItem = function (ul, item) {
            var accountstype = "";
            if (item) {
                accountstype = '<div style="font-size: 9px; color:#326E93;">' +" Code: " +item.code+", Name: "+item.name + '</div>';
            }
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + accountstype).appendTo(ul);

        }


    }

    var CustomerInfo = {
        customerCoreInfoId: null,
        popupCustomerListPanel: function(customerCoreInfoId){

            var url = '${resource(dir:'secondaryDemandOrder', file:'popupCustomerListPanel')}' ;
            var params = {id:customerCoreInfoId};
            DocuAjax.html(url, params, function(html){

                $.fancybox(html);
            });
        },
        popupCustomerDeliveryListPanel: function(customerCoreInfoId){
            var url = '${resource(dir:'secondaryDemandOrder', file:'popupCustomerDeliveryListPanel')}' ;
            var params = {id:customerCoreInfoId};
            DocuAjax.html(url, params, function(html){

                $.fancybox(html);
            });
        },
        popupProductListPanel: function(customerCoreInfoId){
            var url = '${resource(dir:'secondaryDemandOrder', file:'popupProductListPanel')}' ;
            var params = {customerId:customerCoreInfoId};
            DocuAjax.html(url, params, function(html){

                $.fancybox(html);
            });
        },
        setCustomerInformation: function(customerCoreInfoId, customerCoreInfo){
            $("#searchKey").val(customerCoreInfo);
            $("#customerMaster").val(customerCoreInfoId);
            loadProduct();
            CustomerInfo.customerCoreInfoId = customerCoreInfoId;
        },
        setCustomerDeliveryInformation: function(customerCoreInfoId, customerCoreInfo){

            $("#searchDeliveryKey").val(customerCoreInfo);
            $("#userTentativeDelivery").val(customerCoreInfoId);


            CustomerInfo.customerCoreInfoId = customerCoreInfoId;
        },
        setProductInformation: function(customerCoreInfoId, customerCoreInfo){

            $("#searchProductKey").val(customerCoreInfo);
            $("#finishProduct").val(customerCoreInfoId);
            CustomerInfo.customerCoreInfoId = customerCoreInfoId;
        }
    }
    function addNewItemToCollectionGrid() {
        var myGrid = $("#jqgrid-grid");
        var actionUrl = null;
        var  amount=($("#rate").val()) * ($("#qty").val())
        if ($('#add-button').val() == 'Update') {


            myGrid.jqGrid('footerData','set', {productCode: 'Total:', amount: sum});

            actionUrl = "${resource(dir:'secondaryDemandOrderDetails', file:'update')}";
            $('#add-button').val('Add');
            $('#remove-button').hide();

        } else {

            actionUrl = "${resource(dir:'secondaryDemandOrderDetails', file:'create')}";



        }
        SubmissionLoader.showTo();
        jQuery.ajax({
            type:'post',
            data:jQuery("#gFormSecondaryDemandOrderQtyUpdate").serialize(),
            url: actionUrl,
            success:function(data, textStatus) {
                executePostConditionSecondaryDemandOrder(data);
            },
            error:function(XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-grid").trigger("reloadGrid");
                    $("#searchProductKey").val('');
                    $("#productCode").val('');
                    $("#product").val('');
                    $("#rate").val('');
                    $("#qty").val('');
                    $("#amount").val('');
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                } else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete:function(){
                SubmissionLoader.hideFrom();
            },
            dataType:'json'
        });
        return false;

        var sum = myGrid.jqGrid('getCol', 'amount', false, 'sum');
        myGrid.jqGrid('footerData','set', {productCode: 'Total:', amount: sum});
        $("#searchProductKey").val('');
        gridCollection = myGrid.jqGrid('getRowData');
        $("#productCode").val('');
        $("#product").val('');
        $("#rate").val('');
        $("#qty").val('');
        $("#amount").val('');

    }
    function executeEditProduct() {
        var myGrid = $("#jqgrid-grid");

        var  amount=($("#rate").val()) * ($("#qty").val())
        selRowId = myGrid.jqGrid('getGridParam', 'selrow');

        celValue = myGrid.jqGrid('getCell', selRowId, 'id', $("#detailsOrder").val());
        celValue2 = myGrid.jqGrid('getCell', selRowId, 'pid', $("#finishProduct").val());
        celValue3 = myGrid.jqGrid('getCell', selRowId, 'productCode', $("#productCode").val());
        celValue4 = myGrid.jqGrid('getCell', selRowId, 'product', $("#product").val());
        celValue5 = myGrid.jqGrid('getCell', selRowId, 'rate', $("#rate").val());
        celValue6 = myGrid.jqGrid('getCell', selRowId, 'qty', $("#qty").val());

        $("#searchProductKey").val(celValue3+'-'+celValue4);
        $("#detailsOrder").val(celValue);
        $("#finishProduct").val(celValue2);
        $("#productCode").val(celValue3);
        $("#product").val(celValue4);
        $("#rate").val(celValue5);
        $("#qty").val(celValue6);


        $('#add-button').attr('value', 'Update');
        $('#remove-button').show();
    }

    function deleteProduct() {
        var myGrid = $("#jqgrid-grid");
        selRowId = myGrid.jqGrid('getGridParam', 'selrow');
        $("#dialog").dialog("destroy");
        $("#dialog-confirm-secondaryDemandOrderDetails").dialog({
            resizable: false,
            height:150,
            modal: true,
            title: 'Delete alert',
            buttons: {
                'Delete item(s)': function() {
                    $(this).dialog('close');
                    SubmissionLoader.showTo();
                    $.ajax({
                        type: "POST",
                        dataType: "json",
                        data:jQuery("#gFormSecondaryDemandOrderQtyUpdate").serialize(),
                        url: "${resource(dir:'secondaryDemandOrderDetails', file:'delete')}",
                        success: function(message) {
                            executePostConditionForDeleteSecondaryDemandOrder(message);
                        }
                    });
                    SubmissionLoader.hideFrom();
                },
                Cancel: function() {
                    $(this).dialog('close');
                }
            }
        });

        $("#productCode").val('');
        $("#product").val('');
        $("#rate").val('');
        $("#qty").val('');
        $("#amount").val('');
        $("#searchProductKey").val('');
        myGrid.jqGrid('footerData','set', {productCode: '', amount: ''});
        $('#add-button').val('Add');
        $('#remove-button').hide();
    }
    function executePostConditionForDeleteSecondaryDemandOrder(data) {
        if (data.type ==1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            $("#searchProductKey").val('');
            $("#productCode").val('');
            $("#product").val('');
            $("#rate").val('');
            $("#qty").val('');
            $("#amount").val('');

        }
        MessageRenderer.render(data)
    }
    function executePostConditionSecondaryDemandOrder(result) {
        if (result.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            $("#searchProductKey").val('');
            $("#productCode").val('');
            $("#product").val('');
            $("#rate").val('');
            $("#qty").val('');
            $("#amount").val('');

        }
        MessageRenderer.render(result);
    }

</script>
<div class="main_container">
    <div class="content_container">
        <h1><g:message code="enterpriseConfiguration.create.label" default="Update Order Quantity"/></h1>
        <h3><g:message code="enterpriseConfiguration.info.label" default="Demand order Information"/></h3>
        <form name='gFormSecondaryDemandOrderQtyUpdate' id='gFormSecondaryDemandOrderQtyUpdate'>
            <g:hiddenField name="id" value="" />
            <g:hiddenField name="version" value="${secondaryDemandOrder?.version}" />
            <g:hiddenField name="idEnterprise" id="idEnterprise" value=""/>
            <g:hiddenField name="idBusiness" id="idBusiness" value=""/>
            <div id="remote-content-territoryConfiguration"></div>
            <br>

            <div class="element_row_content_container lightColorbg pad_bot0">
                <table>

                    <tr class="element_row_content_container lightColorbg pad_bot0">
                        <script type="text/javascript">
                            jQuery(document).ready(function () {
                                $("#dateFrom, #dateTo").datepicker(
                                        { dateFormat: 'dd-mm-yy',
                                            changeMonth:true,
                                            changeYear:true
                                        });
                                $('#dateFrom').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});
                                $('#dateTo').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});
                            });
                        </script>
                        <td>
                            <label class="txtright bold hight1x width1x" style="width: 105px;">
                                <g:message code="secondaryDemandOrder.product.label"
                                           default="Order No"/>
                            </label>

                        </td>
                        <td><input type="text" id="searchOrderKey" name="searchOrderKey" class="width200"/>
                            <input type="hidden" id="productId" name="productId"/>

                        </td>





                    </tr>
                    <tr class="element_row_content_container lightColorbg pad_bot0">
                        <td>
                            <label class="txtright bold hight1x width1x" style="width: 105px;">
                                <g:message code="secondaryDemandOrder.enterpriseConfiguration.label"
                                           default="Enterprise "/>

                            </label>

                        </td>

                        <g:if test="${result}">
                            <g:if test="${list.size()==1}">
                                <td>
                                    <g:textField name="enterPriseName" id="enterPriseName" readonly="readonly" value="${list[0].name}" style="width: 300px;"/>
                                    <g:hiddenField name="enterpriseConfiguration"  id="enterpriseConfiguration" value="${list[0].id}" />
                                    <script type="text/javascript">
                                        jQuery(document).ready(function () {
                                            loadProduct()

                                        });
                                    </script>
                                </td>
                            </g:if>
                            <g:else>


                                <td><div id="enterpriselist" style="width: 300px;"></div></td>
                                <script type="text/javascript">
                                    %{--alert("${result}");--}%
                                    jQuery(document).ready(function () {

                                        $("#enterpriselist").empty();

                                        $("#enterpriselist").flexbox(${result}, {
                                            watermark: "Select Enterprise",
                                            width: 260,
                                            onSelect: function () {
                                                $("#enterpriseConfiguration").val($('#enterpriselist_hidden').val());
                                                loadProduct()

//
                                            }
                                        });
                                        $('#enterpriselist_input').addClass("validate[required]");


                                        $('#enterpriselist_input').blur(function () {
                                            if ($('#enterpriselist_input').val() == '') {
                                                $("#enterpriseId").val("");
                                                $("#enterpriseConfiguration").val("");
                                            }
                                        });
                                    });
                                </script>
                            </g:else>
                            <g:hiddenField name="enterpriseConfiguration"  id="enterpriseConfiguration" value="" />
                        </g:if>
                        <g:else>
                            <td>
                                <g:textField name="enterPriseName" readonly="readonly" value=""/>
                                <script type="text/javascript">
                                    jQuery(document).ready(function () {
                                        MessageRenderer.showHeaderMessage("You have no assigned enterprise, please assign enterprise first.", 0)
                                    });
                                </script>
                            </td>
                        </g:else>

                    </tr>
                    <tr class="element_row_content_container lightColorbg pad_bot0">
                        <td>
                            <label class="txtright bold hight1x width1x" style="width: 105px;">
                                <g:message code="secondaryDemandOrder.product.label"
                                           default="Product"/>
                            </label>

                        </td>
                        <td><input type="text" id="searchProductKey" name="searchProductKey" class="width150"/>
                            <input type="hidden" id="detailsOrder" name="detailsOrder"/>
                            <input type="hidden" id="finishProduct" name="finishProduct.id"/>
                            <input type="hidden" id="productCode" />
                            <input type="hidden" id="product" />
                            <span id="search-btn-product-id" title="" role="button"
                                  class="fld-btn ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only floatR">
                                <span class="ui-button-icon-primary ui-icon ui-icon-search"></span>
                                <span class="ui-button-text"></span>

                            </span>
                        </td>
                        %{--</tr>--}%

                        %{--<tr class="element_row_content_container lightColorbg pad_bot0">--}%
                        <td>
                            <label class="txtright bold hight1x width1x" style="width: 25px;">
                                <g:message code="secondaryDemandOrder.rate.label" default="Rate"/>
                            </label>

                        </td>
                        <td><g:textField name="rate" id="rate" value="" style="width: 80px;"/></td>
                        <td>
                            <label class="txtright bold hight1x width1x" style="width: 50px;">
                                <g:message code="secondaryDemandOrder.qty.label" default="Quantity"/>
                            </label>
                        </td>
                        <td><g:textField name="qty" value="" style="width: 50px;"/></td>
                        <td>
                            <div class="buttons">
                                <span class="button"><input type="button" name="add-button" id="add-button"
                                                            class="ui-button ui-widget ui-state-default ui-corner-all"
                                                            value="Add"
                                                            onclick="addNewItemToCollectionGrid();"/></span>
                                <span class="button"><input type='button' name="delete-button" id="remove-button"
                                                            class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete'
                                                            onclick="deleteProduct();"/></span>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="buttons">
                                <span class="button"><input type="button" name="show-button" id="show-button"
                                                            class="ui-button ui-widget ui-state-default ui-corner-all"
                                                            value="Show"
                                                            onclick="loadDataByOrder();"/></span>

                            </div>
                        </td>
                    </tr>
                </table>

                <div class="jqgrid-container">
                    <table id="jqgrid-grid"></table>

                    <div id="jqgrid-pager"></div>
                </div>
            </div>
            <div id="dialog-confirm-secondaryDemandOrderDetails" title="Delete alert" style="display: none">
                <p><span class="ui-icon ui-icon-alert"
                         style="float:left; margin:0 7px 20px 0;"></span>These item(s) will be permanently deleted and cannot be recovered. Are you sure?
                </p>
            </div>
        </form>
    </div>
</div>