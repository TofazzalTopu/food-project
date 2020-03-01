<%@ page import="com.bits.bdfp.util.ApplicationConstants; com.docu.commons.CommonConstants" %>
<script type="text/javascript" language="Javascript">
    var jqGridCustomerList = null;
    $(document).ready(function() {
        $('#ui-widget-header-text').html('ProductPrice');
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress (checkForEnter);
        } else {
            $(textboxes).keydown (checkForEnter);
        }

        $("#gFormProductPrice").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormProductPrice").validationEngine('attach');
        setDateRangeNoLimit('dateEffectiveFrom','dateEffectiveTo');
        $("#dateEffectiveFrom").mask("${CommonConstants.DATE_MASK_FORMAT}");
        $("#dateEffectiveTo").mask("${CommonConstants.DATE_MASK_FORMAT}");
        $("#div_product_territory").hide();
        $("#div_customer_selection").hide();
//        reset_form("#gFormProductPrice");
        $("#jqgrid-grid-geoLocation").jqGrid({
            url:'',
            datatype: "local",
//            mtype: "POST",
            colNames:[
                'ID',
                'Geographic Location',
                'Country',
                'Division',
                'District',
                'Thana',
                'Union',
                'Para/ Locality',
                'Road'
            ],
            colModel:[
                {name:'id', index:'id', width:0, hidden:true},
                {name:'geographicLocation', index:'saleItemId', width:100, align:'left'},
                {name:'country', index:'country', width:100, align:'left'},
                {name:'division', index:'division', width:120, align:'left'},
                {name:'district', index:'district', width:120, align:'left'},
                {name:'thana', index:'thana', width:150, align:'left'},
                {name:'union', index:'union', width:100, align:'left'},
                {name:'paraOrLocality', index:'paraOrLocality',width:100, align:'left'},
                {name:'road', index:'road', width:100, align:'left'}
            ],
            rowNum: -1,
//            pager: '#jqgrid-pager-geoLocation',
            viewrecords:true,
            caption:"Assign Geographic Location",
            autowidth:true,
            height:true,
            scrollOffset:0,
            gridview:true,
            multiselect: true,
            rownumbers: true,
//            footerrow : true,
            loadComplete: function() {
            },
            onSelectRow: function(rowid, status) {
//                executeEditSaleItem();
            },
            loadError: function (jqXHR, textStatus, errorThrown) {
                MessageRenderer.renderErrorText(jqXHR.responseText,'');
            }
        });

        jqGridCustomerList = $("#jqgrid-grid-customer").jqGrid({
            url:'',
            datatype: "local",
            colNames:[
                'ID',
                'Customer Number',
                'Customer Name',
                'Customer Address'
            ],
            colModel:[
                {name:'id',index:'id', width:0, hidden:true},
                {name:'customerNumber', index:'saleItemId',width:200, align:'left'},
                {name:'customerName', index:'customerName',width:250, align:'left'},
                {name:'customerAddress', index:'customerAddress',width:300, align:'left'}
            ],
            rowNum: -1,
//            pager: '#jqgrid-pager-customer',
            viewrecords:true,
            caption:"Select Customer List",
            autowidth:true,
            height:true,
            scrollOffset:0,
            gridview:true,
            rownumbers: true,
            multiselect: true,
            gridComplete: function() {
            },
            loadComplete: function() {
            },
            onSelectRow: function(rowid, status) {
            },
            loadError: function (jqXHR, textStatus, errorThrown) {
                MessageRenderer.renderErrorText(jqXHR.responseText,'');
            }
        });
//        $("#cb_jqgrid-grid-geoLocation").hide();   // Hide Select All
//        $("#cb_jqgrid-grid-customer").hide();
    });
    function getSelectedProductPriceId() {
        return $('#gFormProductPrice input[name = id]').val();
    }
    function executePreConditionProductPrice() {
        // trim field vales before process.
        trim_form();
        if (!$("#gFormProductPrice").validationEngine('validate')) {
            return false;
        }
        return true;
    }
    function executeAjaxProductPrice() {
        if(!executePreConditionProductPrice()) {
            return false;
        }
        var actionUrl = null;
        if ($('#gFormProductPrice input[name = id]').val()) {
            actionUrl = "${request.contextPath}/${params.controller}/update";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/create";
        }
        var data =  $("#gFormProductPrice").serializeArray();

        var tempId = "";
        var isProductPriceSelected = false;
        $(".baseData").each(function () {
            if(this.value){
                data.push({'name': this.id, 'value': this.value});
                var idValues = this.id.split("_");
                if(idValues.length > 2){
                    // Set Vat Percent
                    tempId = 'vatPercent_' + idValues[1] + "_" + idValues[2];
                    data.push({'name':tempId, 'value': Number($("#" + tempId).val())});
                    // Set Vat Amount
                    tempId = 'vatAmount_' + idValues[1] + "_" + idValues[2];
                    data.push({'name':tempId, 'value': Number($("#" + tempId).val())});
                    isProductPriceSelected = true
                }
            }
        });
        if(!isProductPriceSelected){
            MessageRenderer.renderErrorText("Product Price value is not given");
            return false
        }

        var productPricingTypeId = $("#productPricingType").val();
        if(productPricingTypeId == ${ApplicationConstants.PRODUCT_PRICING_TYPE_STANDARD_ID}) {
            var selRowIds = $('#jqgrid-grid-geoLocation').jqGrid('getGridParam', 'selarrrow');
            if(selRowIds.length <=  0){
                MessageRenderer.renderErrorText("Please Select Geo Location");
                return false
            }
            data.push({'name': "geoLocationIds", 'value': selRowIds.toString()});
        } else if(productPricingTypeId == ${ApplicationConstants.PRODUCT_PRICING_TYPE_NEGOTIATED_ID}) {
            var customerIds = $('#jqgrid-grid-customer').jqGrid('getGridParam', 'selarrrow');
            if(customerIds.length <=  0){
                MessageRenderer.renderErrorText("Please Select Customer");
                return false
            }
            data.push({'name': "customerIds", 'value': customerIds.toString()});
        }

        SubmissionLoader.showTo();

        jQuery.ajax({
            type:'post',
            data: data,
            url: actionUrl,
            success:function(data, textStatus) {
                executePostConditionProductPrice(data);
            },
            error:function(XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    clean_form('#gFormProductPrice');
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
    function executePostConditionProductPrice(result) {
        if (result.type == 1) {
//            $("#jqgrid-grid").trigger("reloadGrid");
            clean_form('#gFormProductPrice');
        }
        MessageRenderer.render(result);
    }

    function getTerritoryListByEnterprise(enterpriseId) {
        var options = '<option value="">Select Territory</option>';
        if(enterpriseId){
            $.ajax({
                type:'POST',
                data:'enterpriseId=' + enterpriseId,
                url:'${request.contextPath}/territoryConfiguration/searchTerritoryByEnterprise',
                success:function (data) {
                    if (data.length > 0) {
                        for (var i = 0; i < data.length; i++) {
                            options += '<option value="' + data[i].id + '">' + data[i].name + '</option>';
                        }
                    }
                    $("#territoryConfiguration").html(options);
                },
                error:function (jqXHR, textStatus, errorThrown) {
                    MessageRenderer.renderErrorText(jqXHR.responseText, '');
                },
                complete:function (data) {
                },
                dataType:'json'
            });
        }else{
            $("#territoryConfiguration").html(options);
        }
    }

    function changeProductPricingType(productPricingTypeId){
        $("#div_customer_selection").hide();
        $("#div_product_territory").hide();
        if(productPricingTypeId == ${ApplicationConstants.PRODUCT_PRICING_TYPE_STANDARD_ID}) {
            $(".negotiated").hide();
            $(".negotiated input:text").each(function () {
                this.value = "";
            });

            $("#div_product_territory").show();
        } else if(productPricingTypeId == ${ApplicationConstants.PRODUCT_PRICING_TYPE_NEGOTIATED_ID}) {
            $(".negotiated").show();
            $("#div_customer_selection").show();
        }
    }

    function loadBusinessUnitByEnterprise(enterpriseId){
        $("#businessUnitList").empty();
        $("#businessUnitConfiguration").val("");
        $("#div_price_list").html("");
        $("#jqgrid-grid-geoLocation").jqGrid('clearGridData');
        $.ajax({
            type:'POST',
            data:'id=' + enterpriseId,
            url:'${request.contextPath}/businessUnitConfiguration/listBusinessUnit',
            success:function (result) {
                $("#businessUnitList").flexbox(result, {
                    watermark: "Select Business Unit",
                    width: 120,
                    onSelect: function () {
                        $("#businessUnitConfiguration").val($('#businessUnitList_hidden').val());
                        getProductPriceListByBusinessUnit($('#businessUnitList_hidden').val())
                    }
                });
                $('#businessUnitList_input').blur(function () {
                    if ($('#businessUnitList_input').val() == '') {
                        $("#businessUnitConfiguration").val("");
                    }
                });
                $('#businessUnitList_input').addClass("validate[required]");
            },
            error:function (jqXHR, textStatus, errorThrown) {
                MessageRenderer.renderErrorText(jqXHR.responseText, '');
            },
            complete:function (data) {
                //alert('in complete');
            },
            dataType:'json'
        });
    }

    function getProductPriceListByBusinessUnit(businessUnitId) {
        $("#div_price_list").html("");
        if(businessUnitId){
            var enterpriseId = $("#enterpriseConfiguration").val();
            SubmissionLoader.showTo();
            $.ajax({
                type:'POST',
                data:'businessUnitId=' + businessUnitId + '&enterpriseId=' + enterpriseId,
                url:'${request.contextPath}/${params.controller}/finishProductListForPricing',
                success:function (data) {
                    $("#div_price_list").html(data);
                },
                error:function (jqXHR, textStatus, errorThrown) {
                    MessageRenderer.renderErrorText(jqXHR.responseText, '');
                },
                complete:function (data) {
                    SubmissionLoader.hideFrom();
                },
                dataType:'html'
            });
        }
    }
    function loadGeographicLocationList(territoryId){
        if(territoryId) {
            $("#jqgrid-grid-geoLocation").jqGrid().setGridParam({url:"${resource(dir:'territorySubArea', file:'listTerritorySubAreaByTerritory')}?"
                    + "territoryId=" + territoryId,
                mtype: "POST",
                page:1,
                datatype:"json"
            }).trigger("reloadGrid")
        }else{
            $("#jqgrid-grid-geoLocation").jqGrid('clearGridData');
        }
    }

    var CustomerInfo = {
        customerCoreInfoId: null,
        popupCustomerListPanel: function(enterpriseId){
            var url = '${resource(dir:'productPrice', file:'popupCustomerListPanel')}' ;
            var params = {enterpriseId:enterpriseId};
            DocuAjax.html(url, params, function(html){
                $.fancybox(html);
            });
        },
        setCustomerInformation: function(customerCoreInfoId, customerCoreInfo){
            $("#searchKey").val(customerCoreInfo);
            $("#customerId").val(customerCoreInfoId);
            CustomerInfo.customerCoreInfoId = customerCoreInfoId;
        }
    };

    function loadCustomer(enterpriseId) {
        jQuery('#searchKey').autocomplete({
            minLength:'1',
            source:function (request, response) {
                //EmployeeRegister.employeeCoreInfoId = null;
                var data = {enterpriseId: enterpriseId, query: request.term};
                var url = '${resource(dir:'productPrice', file:'customerAutoCompleteByEnterPrise')}';
                DocuAutoComplete.setSpinnerSelector('searchKey').execute(response, url, data, function (item) {
                    item['label'] = "[" + item['code'] + "] " + item['name'];
                    item['value'] = item['label'];
                    return item;
                });
            },
            select:function (event, ui) {
                CustomerInfo.setCustomerInformation(ui.item.id, ui.item.value);
                $("#customerId").val(ui.item.id);
                $("#customerNumber").val(ui.item.code);
                $("#customerName").val(ui.item.name);
                $("#customerAddress").val(ui.item.present_address);
//                $('#name').val(ui.item.name);
            }
        }).data("autocomplete")._renderItem = function (ul, item) {
            var accountstype = "";
            if (item) {
                accountstype = '<div style="font-size: 9px; color:#326E93;">' + " Code: " + item.code + "; Legacy ID: " + item.legacy_id + "; Name: " + item.name + "; Location: " + item.present_address + '</div>';
            }
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + accountstype).appendTo(ul);
        };

        $('#search-btn-customer-register-id').click(function(){
            CustomerInfo.popupCustomerListPanel(enterpriseId);
        });
    }

    function addCustomerToGrid(){
        var customerId = $("#customerId").val();
        if(!customerId){
            MessageRenderer.renderErrorText("Customer is not selected");
            return
        }

        var customerName = $("#customerName").val();
        var customerNumber = $("#customerNumber").val();
        var customerAddress = $("#customerAddress").val();

        var rowTo = jqGridCustomerList.getRowData(customerId.toString());
        if (!rowTo.id) {
            var newRowData = [
                { 'id':customerId.toString(), 'customerNumber':customerNumber.toString(), 'customerName':customerName.toString(), 'customerAddress':customerAddress.toString()}
            ];
            jqGridCustomerList.addRowData(customerId.toString(), newRowData);
            $("#jqgrid-grid-customer [id*='undefined']").attr('id', customerId.toString());
            $("#searchKey").val("");
            $("#customerId").val("");
            $("#customerName").val("");
            $("#customerNumber").val("");
            $("#customerAddress").val("");
        }else{
            MessageRenderer.renderErrorText("Customer Already Selected");
        }
    }

    function clean_form(formName) {
        $(formName).find(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
            if (this.type == 'hidden') {
                this.value = "";
            } else {
                this.value = "";
            }
        });
        $(formName + ' input[name = create-button]').attr('value', 'Create');
        $(formName + ' input[name = delete-button]').hide();
        $("#div_product_territory").hide();
        $("#div_customer_selection").hide();
        $("#jqgrid-grid-geoLocation").jqGrid('clearGridData');
        $("#jqgrid-grid-customer").jqGrid('clearGridData');
        $("#div_price_list").html("");
    }
</script>