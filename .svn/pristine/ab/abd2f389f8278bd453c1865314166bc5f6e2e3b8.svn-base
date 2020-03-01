

<script type="text/javascript" language="Javascript">
    var subAreaId = 1
    var id =${id}

    $(document).ready(function() {
        $('#ui-widget-header-text').html('SecondaryDemandOrder')
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress (checkForEnter);
        } else {
            $(textboxes).keydown (checkForEnter);
        }


        $("#gFormSecondaryDemandOrderUpdate").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormSecondaryDemandOrderUpdate").validationEngine('attach');
        reset_form("#gFormSecondaryDemandOrderUpdate");
        loadUpdateData()
        $('#searchKey').blur(function () {
            if ($('#searchKey').val() == '') {
                $("#customerMaster").val('');
            }
        });
        $('#searchDeliveryKey').blur(function () {
            if ($('#searchDeliveryKey').val() == '') {
                $("#userTentativeDelivery").val('');
            }
        });
        $('#searchProductKey').blur(function () {
            if ($('#searchProductKey').val() == '') {
                $("#finishProduct").val('');
            }
        });
//      $('#remove-button').hide();
        $("#jqgrid-grid").jqGrid({
            url:'${resource(dir:'secondaryDemandOrderDetails', file:'list')}?id='+id ,
            datatype: "json",
            colNames:[

                'Id',
                'PId',
                'Product Code',
                'Product',
                'Rate',
                'Quantity',
                'Amount',
                'Delete'

            ],
            colModel:[

                {name:'id',index:'id', width:0, hidden:true},
                {name:'pid',index:'pid', width:0, hidden:true},
                {name:'productCode', index:'productCode',width:200,align:'center'},
                {name:'product', index:'product',width:180,align:'left'},
                {name:'rate', index:'rate',width:80,align:'right'},
                {
                    name:'qty', index:'qty',width:80,align:'center',
                    sorttype:'number',
                    formatter:"number",
                    formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 0},
                    editable: true,
                    editrules:{number:true}
                },
                {name:'amount', index:'amount',width:100,align:'right'},
                {name: 'delete', index: 'delete', width: 50, align: 'center',formatter: deleteLink}

            ],
            rowNum:50,
            rowList:[10,20,30,40,50,60,70,80,90,100,-1],
            pager: '#jqgrid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption:"All Product Information",
            autowidth: true,
            height: true,
            footerrow:true,
            scrollOffset: 0,
            cellEdit: true,
            cellsubmit: 'clientArray',
            cellurl: null,
            loadComplete: function() {
                var myGrid = $("#jqgrid-grid");
                var sum = myGrid.jqGrid('getCol', 'amount', false, 'sum');
                myGrid.jqGrid('footerData','set', {productCode: 'Total:', amount: sum});

            },
            afterSaveCell : function(rowid,name,val,iRow,iCol) {
                var myGrid = $("#jqgrid-grid");
                var rowData = myGrid.jqGrid('getRowData', rowid);
                rowData.amount = rowData.qty * rowData.rate;
                myGrid.jqGrid('setRowData', rowid, rowData);
                var sum = myGrid.jqGrid('getCol', 'amount', false, 'sum');
                myGrid.jqGrid('footerData','set', {productCode: 'Total:', amount: sum});
                $("#detailsOrder").val(rowData.id);
                $("#finishProduct").val(rowData.pid);
                $("#qty").val(rowData.qty);
                $("#rate").val(rowData.rate);
                SubmissionLoader.showTo();
                jQuery.ajax({
                    type:'post',
                    data:jQuery("#gFormSecondaryDemandOrderUpdate").serialize(),
                    url: "${resource(dir:'secondaryDemandOrderDetails', file:'update')}",
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
            },
            onSelectRow: function(rowid, status) {
//                executeEditProduct();
            }
        });
        $("#jqgrid-grid").jqGrid('navGrid', '#jqgrid-pager', {edit:false,add:false,del:false,search:false});

        $(".ui-pg-selbox").children().each(function() {
            if ($(this).val() == -1) {
                $(this).html('All')
            }

        });

    });

    function deleteLink(cellValue, options, rowdata, action)  {
        $("#detailsOrder").val(rowdata[0]);
        return "<a href='javascript:deleteProduct();' class='ui-icon ui-icon-closethick' title='Delete'></a>";
    }

//    function cboxFormatter(cellvalue, options, rowObject) {
//        return '<a  href="javascript:removeProduct(' + options.rowId + ')" class="ui-icon ui-icon-trash" title="Delete"></a>';
//    }

    function executePreConditionSecondaryDemandOrder() {
        var fromDate = DocuDateUtil.createDateFromString($('#orderDate').val());
        var toDate = DocuDateUtil.createDateFromString($('#deliveryDate').val());
        // trim field vales before process.
      trim_form();
        if ($("#gFormSecondaryDemandOrderUpdate").validationEngine('validate')) {
            if(!((fromDate > toDate) && (toDate < fromDate))){
                return true;
            }else{
                MessageRenderer.render({
                    "messageBody": "Order date cannot greater than Delivery date.",
                    "messageTitle": "Create Demand Order",
                    "type": "0"
                });
                return false;
            }
        }
        else{
            return false;
        }

    }
    function executeUpdateAjaxSecondaryDemandOrder() {
        if(!executePreConditionSecondaryDemandOrder()) {
            return false;
        }
        var data = jQuery("#gFormSecondaryDemandOrderUpdate").serialize();
        var actionUrl = null;
        actionUrl = "${request.contextPath}/${params.controller}/update";
        SubmissionLoader.showTo();
        jQuery.ajax({
            type:'post',
            data:data,
            url: actionUrl,
            success:function(data, textStatus) {
                if (data.type == 1) {
                    $("#jqgrid-grid").trigger("reloadGrid");
                }
                MessageRenderer.render(data);
            },
            error:function(XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-grid").trigger("reloadGrid");
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

    function executePreConditionForDeleteSecondaryDemandOrder(secondaryDemandOrderId) {
        if(secondaryDemandOrderId == null)
        {
            alert("Please select a secondaryDemandOrder to delete") ;
            return false;
        }
        return true;
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

    function loadCustomer() {
        $('#searchKey').blur(function () {
            if ($('#searchKey').val() == '') {
                $("#customerMaster").val('');
            }
        });
        jQuery('#searchKey').autocomplete({
            minLength:'1',
            source:function (request, response) {
                //EmployeeRegister.employeeCoreInfoId = null;
                if ($("#enterpriseConfiguration").val()){
                    var data = {searchKey:request.term};
                    var url = '${resource(dir:'secondaryDemandOrder', file:'listCustomer')}?id=' + $("#enterpriseConfiguration").val()+ "&query=" + $('#searchKey').val();
                    DocuAutoComplete.setSpinnerSelector('searchKey').execute(response, url, data, function (item) {
                        item['label'] = item['code']+"-"+item['name']+"-"+"-"+item['status']+"-"+item['geo_location'];
                        item['value'] = item['label'];
                        return item;
                    });
                }
            },
            select:function (event, ui) {
                CustomerInfo.setCustomerInformation(ui.item.id, ui.item.value);
                $('#name').val(ui.item.name)
            }
        }).data("autocomplete")._renderItem = function (ul, item) {
            var accountstype = "";
            if (item) {
                accountstype = '<div style="font-size: 9px; color:#326E93;">' +" Code: " +item.code+", Name: "+item.name +"-"+", Status: "+item.status +"-"+", Location: "+item.geo_location + '</div>';
            }
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + accountstype).appendTo(ul);
        }
        $('#searchDeliveryKey').blur(function () {
            if ($('#searchDeliveryKey').val() == '') {
                $("#userTentativeDelivery").val('');
            }
        });

        jQuery('#searchDeliveryKey').autocomplete({
            minLength:'1',
            source:function (request, response) {
                if ($("#enterpriseConfiguration").val()){
                    var data = {searchKey:request.term};
                    var url = '${resource(dir:'secondaryDemandOrder', file:'listCustomer')}?id=' + $("#enterpriseConfiguration").val()+ "&query=" + $('#searchKey').val();
                    DocuAutoComplete.setSpinnerSelector('searchDeliveryKey').execute(response, url, data, function (item) {
                        item['label'] = item['code']+"-"+item['name']+"-"+"-"+item['status']+"-"+item['geo_location'];
                        item['value'] = item['label'];
                        return item;
                    });
                }
            },
            select:function (event, ui) {
                CustomerInfo.setCustomerDeliveryInformation(ui.item.id, ui.item.value);
            }
        }).data("autocomplete")._renderItem = function (ul, item) {
            var accountstype = "";
            if (item) {
                accountstype = '<div style="font-size: 9px; color:#326E93;">' +" Code: " +item.code+", Name: "+item.name +"-"+", Status: "+item.status +"-"+", Location: "+item.geo_location + '</div>';
            }
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + accountstype).appendTo(ul);
        }
    }
    $('#search-btn-customer-register-id').click(function(){
        CustomerInfo.popupCustomerListPanel($('#enterpriseConfiguration').val());
    });
    $('#search-btn-customer-delivery-id').click(function(){
        CustomerInfo.popupCustomerDeliveryListPanel($('#enterpriseConfiguration').val());
    });
    function loadProduct(customerId) {
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
                    var url = '${resource(dir:'secondaryDemandOrder', file:'listProduct')}?id=' + customerId + "&query=" + $('#searchProductKey').val();
                    DocuAutoComplete.setSpinnerSelector('searchProductKey').execute(response, url, data, function (item) {
                        item['label'] = item['code']+"-"+item['name'];
                        item['value'] = item['label'];
                        return item;
                    });
                }
            },
            select:function (event, ui) {
                CustomerInfo.setProductInformation(ui.item.id, ui.item.value);
                $('#rate').val(ui.item.price);
                $('#productCode').val(ui.item.code);
                $('#product').val(ui.item.name);
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
            var params = {id:customerCoreInfoId};
            DocuAjax.html(url, params, function(html){
                $.fancybox(html);
            });
        },
        setCustomerInformation: function(customerCoreInfoId, customerCoreInfo){
            $("#searchKey").val(customerCoreInfo);
            $("#customerMaster").val(customerCoreInfoId);
            loadProduct(customerCoreInfoId);
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
    $('#search-btn-customer-product-id').click(function(){
        CustomerInfo.popupProductListPanel($("#customerMaster").val());
    });
    function addNewItemToCollectionGrid() {
        var fromDate = DocuDateUtil.createDateFromString($('#orderDate').val());
        var toDate = DocuDateUtil.createDateFromString($('#deliveryDate').val());

        if(((fromDate > toDate) && (toDate < fromDate))){
            MessageRenderer.render({
                "messageBody": "Order date cannot greater than Delivery date.",
                "messageTitle": "Create Demand Order",
                "type": "0"
            });
            return;
        }

        var myGrid = $("#jqgrid-grid");
        var actionUrl = null;
        var  amount=($("#rate").val()) * ($("#qty").val());
        if ($('#add-button').val() == 'Update') {
            myGrid.jqGrid('footerData','set', {productCode: 'Total:', amount: sum});
            actionUrl = "${resource(dir:'secondaryDemandOrderDetails', file:'update')}";
            $('#add-button').val('Add');
            $('#remove-button').hide();
        } else {
            var colData = myGrid.jqGrid('getDataIDs');
            for(var i = 0; i < colData.length; i++){
                var productTemp = myGrid.jqGrid("getCell", colData[i], 'pid');
                if(productTemp == $("#finishProduct").val()){
                    var quantityTemp = myGrid.jqGrid("getCell", colData[i], 'qty');
                    var rateTemp = myGrid.jqGrid("getCell", colData[i], 'rate');
                    var qty = $("#qty").val();
                    quantityTemp = Number(quantityTemp) + Number(qty);
                    myGrid.jqGrid("setCell", colData[i], 'qty', quantityTemp);
                    myGrid.jqGrid("setCell", colData[i], 'amount', Number(quantityTemp) * Number(rateTemp));
                    actionUrl = "${resource(dir:'secondaryDemandOrderDetails', file:'update')}";
                    $("#detailsOrder").val(myGrid.jqGrid('getCell', colData[i], 'id'));
                    $("#qty").val(quantityTemp);
                    $("#rate").val(rateTemp);
                    break;
                }
            }
            if(actionUrl == null) {
                actionUrl = "${resource(dir:'secondaryDemandOrderDetails', file:'create')}";
            }
        }
        SubmissionLoader.showTo();
        jQuery.ajax({
            type:'post',
            data:jQuery("#gFormSecondaryDemandOrderUpdate").serialize(),
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

        var  amount=($("#rate").val()) * ($("#qty").val());
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
                        data:jQuery("#gFormSecondaryDemandOrderUpdate").serialize(),
                        url: "${resource(dir:'secondaryDemandOrderDetails', file:'delete')}",
                        success: function(message) {
                            executePostConditionForDeleteSecondaryDemandOrder(message);
                        }, error:function(XMLHttpRequest, textStatus, errorThrown) {
                            if(XMLHttpRequest.status = 0){
                                MessageRenderer.renderErrorText("Network Problem: Time out");
                            } else{
                                MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                            }
                        },
                        complete:function(){
                            SubmissionLoader.hideFrom();
                        }
                    });
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
        var sum = myGrid.jqGrid('getCol', 'amount', false, 'sum');
        myGrid.jqGrid('footerData','set', {productCode: 'Total:', amount: sum});
        $('#add-button').val('Add');
        $('#remove-button').hide();
    }
    function reset_form(formName) {
        $(formName).find(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
            this.value = this.defaultValue;
        });
        $(formName).find('select').each(function () {
            this.value = "";
        });

        $(formName + ' input[name = create-button]').attr('value', 'Create');
        $(formName + ' input[name = delete-button]').hide();
    }
    function loadUpdateData(){
        if ( ${secondaryDemandOrderList.size()>0}) {
            $('#enterpriselist').setValue('${secondaryDemandOrderList[0].ename}');
            $("#enterpriseConfiguration").val(${secondaryDemandOrderList[0].eid});
            $("#name").val('${secondaryDemandOrderList[0].name}');
            $("#searchKey").val('${secondaryDemandOrderList[0].cus_name}');
            $("#customerMaster").val(${secondaryDemandOrderList[0].cid});
            $("#searchDeliveryKey").val('${secondaryDemandOrderList[0].dname}');
            $("#userTentativeDelivery").val(${secondaryDemandOrderList[0].tid});
            $("#orderDate").val('${secondaryDemandOrderList[0].date_order}');
            $("#deliveryDate").val('${secondaryDemandOrderList[0].date_deliver}');
            loadCustomer(${secondaryDemandOrderList[0].eid});
            loadProduct(${secondaryDemandOrderList[0].cid});
        }
    }
</script>