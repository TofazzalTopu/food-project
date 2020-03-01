<%@ page import="com.docu.commons.CommonConstants" %>
<style>
#jqgh_cb .cbox {
    margin-left: 0 !important;
    margin-right: 3px;
    display: inherit;
}

#jqgrid-grid-priceList tr td .cbox {
    margin-left: 0 !important;
    margin-top: 3px;
}
</style>
<script type="text/javascript" language="Javascript">
    var jqGridPriceList = null;
    $(document).ready(function () {
        $('#ui-widget-header-text').html('ProductPrice');
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }
        $("#gFormSearchProductPrice").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormSearchProductPrice").validationEngine('attach');
        setDateRangeNoLimit('searchDateEffectiveFrom','searchDateEffectiveTo');
        $("#searchDateEffectiveFrom").mask("${CommonConstants.DATE_MASK_FORMAT}");
        $("#searchDateEffectiveTo").mask("${CommonConstants.DATE_MASK_FORMAT}");
        var options = '<option value="">Select Status</option>';
        options += '<option value="true">Active</option>';
        options += '<option value="false">Inactive</option>';
        $("#status").html(options);
        jqGridPriceList = $("#jqgrid-grid-priceList").jqGrid({
            url:'',
            datatype: "local",
//            mtype: "POST",
            colNames:[
                'ID',
                'pricingTypeId',
                'PL Name',
                'Effective From',
                'Effective To',
                'Status',
                'Ent.',
                'Business Unit',
                'Territory',
                'Geo Location',
                'Road',
                'Para',
                'Customer Name',
                'Customer Number',
                'Edit'
            ],
            colModel:[
                {name:'id', index:'id', width:0, hidden:true},
                {name:'pricingTypeId', index:'pricingTypeId', width:0, hidden:true},
                {name:'name', index:'name', width:100, align:'left'},
                {name:'dateEffectiveFrom', index:'dateEffectiveFrom', width:80, align:'left'},
                {name:'dateEffectiveTo', index:'dateEffectiveTo', width:80, align:'left'},
                {name:'status', index:'status', width:70, align:'left'},
                {name:'enterprise', index:'enterprise', width:100, align:'left'},
                {name:'businessUnit', index:'businessUnit', width:100, align:'left'},
                {name:'territory', index:'territory', width:100, align:'left'},
                {name:'geoLocation', index:'geoLocation', width:120, align:'left'},
                {name:'road', index:'read', width:100, align:'left', hidden:true},
                {name:'paraOrLocality', index:'paraOrLocality', width:100, align:'left',hidden:true},
                {name:'customerName', index:'customerName', width:120, align:'left'},
                {name:'customerNumber', index:'customerNumber', width:100, align:'left'},
                {name:'edit', index:'edit', width:50, align: 'center', editable: false, sortable: false}
            ],
            rowNum: -1,
//            pager: '#jqgrid-pager-geoLocation',
            viewrecords:true,
            caption:"Price List",
            autowidth:true,
            height:true,
            scrollOffset:0,
            gridview:true,
            multiselect: true,
            rownumbers: true,
            loadComplete: function(data) {
            },
            onSelectRow: function(rowid, status) {
//                executeEditSaleItem();
            },
            loadError: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                }else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            }
        });
//        $("#cb_jqgrid-grid-priceList").hide();
    });

    function loadPriceNameByPriceType(priceTypeId){
        var optionData = '<option value="">Select Price Name</option>';
        $("#jqgrid-grid-priceList").jqGrid('clearGridData');
        if(priceTypeId){
            $.ajax({
                type:'POST',
                data:'priceTypeId=' + priceTypeId,
                url:'${request.contextPath}/productPrice/flexListPriceNameByPriceType',
                success:function (result) {
                    var index = 0;
                    for(index = 0; index < result.length; index++){
                        optionData += '<option value="' + result[index].id + '">' + result[index].name + '</option>';
                    }
                    $("select#priceName").html(optionData);
                },
                error:function (XMLHttpRequest, textStatus, errorThrown) {
                    if(XMLHttpRequest.status = 0){
                        MessageRenderer.renderErrorText("Network Problem: Time out");
                    }else{
                        MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                    }
                },
                complete:function (data) {
                    //alert('in complete');
                },
                dataType:'json'
            });
        } else {
            $("select#priceName").html(optionData);
        }
    }

    function searchProductPrice(){
        if (!$("#gFormSearchProductPrice").validationEngine('validate')) {
            return false;
        }
        var priceTypeId = $("#productPricingType").val();
        var status = $("#status").val();
        var priceNameId = $("#priceName").val();
        var dateEffectiveFrom = $("#searchDateEffectiveFrom").val();
        var dateEffectiveTo = $("#searchDateEffectiveTo").val();

        $("#jqgrid-grid-priceList").jqGrid().setGridParam({url:"${resource(dir:'productPrice', file:'searchProductPriceName')}?"
                + "priceTypeId=" + priceTypeId + "&status=" + status + "&priceNameId=" + priceNameId
                + "&dateEffectiveFrom=" + dateEffectiveFrom + "&dateEffectiveTo=" + dateEffectiveTo,
            mtype: "POST",
            page:1,
            datatype:"json"
        }).trigger("reloadGrid");
        return true
    }

    function activateSelected(){
        var priceNameList = $('#jqgrid-grid-priceList').jqGrid('getGridParam', 'selarrrow');
        if(priceNameList.length <=  0){
            MessageRenderer.renderErrorText("Please Select Price List Item");
            return false
        }
        SubmissionLoader.showTo();
        $.ajax({
            type:'POST',
            data:'priceNameList=' + priceNameList.toString(),
            url:'${request.contextPath}/productPrice/activateProductNameList',
            success:function (result) {
                $("#jqgrid-grid-priceList").trigger("reloadGrid");
                MessageRenderer.render(result);
            },
            error:function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-grid-priceList").trigger("reloadGrid");
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                }
                else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete:function (data) {
                //alert('in complete');
                SubmissionLoader.hideFrom();
            },
            dataType:'json'
        });

        return true
    }

    function inactivateSelected(){
        var priceNameList = $('#jqgrid-grid-priceList').jqGrid('getGridParam', 'selarrrow');
        if(priceNameList.length <=  0){
            MessageRenderer.renderErrorText("Please Select Price List Item");
            return false
        }
        SubmissionLoader.showTo();
        $.ajax({
            type:'POST',
            data:'priceNameList=' + priceNameList.toString(),
            url:'${request.contextPath}/productPrice/inactivateProductNameList',
            success:function (result) {
                $("#jqgrid-grid-priceList").trigger("reloadGrid");
                MessageRenderer.render(result);
            },
            error:function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-grid-priceList").trigger("reloadGrid");
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                }else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete:function (data) {
                //alert('in complete');
                SubmissionLoader.hideFrom();
            },
            dataType:'json'
        });

        return true
    }

    %{--function editLink(cellValue, options, rowdata, action) {--}%
        %{--console.log(cellValue);--}%
        %{--console.log(options);--}%
        %{--return "<a href='<%=request.getContextPath()%>/productPrice/productPriceDetails/" + rowdata.name + "&" + rowdata.pricingTypeId  + "' class='ui-icon ui-icon-pencil' ></a>";--}%
        %{--return "<a onclick='displayProductNameDetails(" + options.rowId + "," + rowdata.pricingTypeId + ")' class='ui-icon ui-icon-pencil'></a>"--}%
    %{--}--}%

    function displayProductNameDetails(priceName, priceTypeId){
        SubmissionLoader.showTo();
        $.ajax({
            type:'POST',
            data:'priceName=' + priceName + '&productPricingTypeId=' + priceTypeId,
            url:'${request.contextPath}/productPrice/productPriceDetails',
            success:function (result) {
//                $.fancybox(result);
                $("#priceDetails").html(result);
                $("#priceDetailsFancy").fancybox({
                    'transitionIn':'elastic',
                    'transitionOut':'elastic',
                    'autoDimensions':false,
                    'speedIn':600,
                    'speedOut':200,
                    'width':800,
                    'height':450,
                    'overlayShow':false
                }).trigger('click');
//                $("#jqgrid-grid-priceList").trigger("reloadGrid");
//                MessageRenderer.render(result);
            },
            error:function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                }else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete:function (data) {
                SubmissionLoader.hideFrom();
            },
            dataType:'html'
        });
    }

</script>