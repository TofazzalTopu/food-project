<%@ page import="com.bits.bdfp.util.ApplicationConstants" %>
<script type="text/javascript">
    var jqGridAssetLendingList = null;
    var rowId = 1;
    $(document).ready(function(){
        $("#assetCost").format({precision: 2, allow_negative: false, autofix: true});
        $("#lendingDate").datepicker({
            dateFormat: '${com.bits.bdfp.util.ApplicationConstants.JQUERY_UI_DATE_FORMAT}',
            //minDate: new Date()
            maxDate:new Date()
        });

        $('#rdoAssetLending').attr('checked',1);
        displayAssetDiv('lending')
        loadGrid();
    })

    function loadGrid(){
        jqGridAssetLendingList = $("#jqgrid-grid-lending").jqGrid({
            datatype: "json",
            colNames: [

                'ID',
                'Asset Name',
                'Date',
                'Model Number',
                'Asset Cost',
                ''
            ],
            colModel: [

                {name: 'id', index: 'id', width: 100, align: 'left',hidden:true},
                {name: 'assetName', index: 'assetName', width: 100, align: 'left'},
                {name: 'lendingDate', index: 'lendingDate', width: 80, align: 'left'},
                {name: 'modelNumber', index: 'modelNumber', width: 100, align: 'left'},
                {name: 'assetCost', index: 'assetCost', width: 70, align: 'right'},
                {name:'delete', index:'delete', width:30, align:'center', sortable:false}
            ],
            rowNum: 50,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            autowidth:true,
            height:true,
            scrollOffset:0,
            gridview:true,
            rownumbers: true,
            cellEdit: true,
            cellsubmit: 'clientArray',
            cellurl: null,
            footerrow : true,
            sortorder: 'asc',
            pager: '#ship-address-grid-pager',
            caption: "Asset Information",
            height: 140,
            width: 785,
            scrollOffset: 0,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
//                executeEditTerritorySubAreaConfiguration();
            },

            gridComplete: function() {
                calculateSummaryData();
            },

            afterSaveCell : function(rowid,name,val,iRow,iCol) {
                calculateSummaryData();
            }
        });


        function deleteProduct(productId){
            $('#jqgrid-grid-lending').jqGrid('delRowData', productId);
        }
        function calculateSummaryData() {
            var $grid = $('#jqgrid-grid-lending');
            var sumAssetCost = $grid.jqGrid('getCol', 'assetCost', false, 'sum');
            sumAssetCost=(sumAssetCost).toFixed(2)
            $grid.jqGrid('footerData', 'set', { 'assetCost': sumAssetCost });
            $grid.jqGrid('footerData', 'set', { 'assetName': 'Total'});
        }
        $("#ship-address-grid").jqGrid('navGrid', '#ship-address-grid-pager', {
            edit: false,
            add: false,
            del: false,
            search: false
        });
    }
    function addToGrid(){
        var rID = jqGridAssetLendingList.getRowData().length + 1;

        var assetName = $("#assetName").val();
        var lendingDate = $("#lendingDate").val();
        var modelNumber = $("#modelNumber").val();
        var assetCost = $("#assetCost").val();
        if(!assetName){
            MessageRenderer.renderErrorText("Asset name is not provided");
            return false
        }
        if(!lendingDate){
            MessageRenderer.renderErrorText("Date is not provided");
            return false
        }
        if(!modelNumber){
            MessageRenderer.renderErrorText("Model number is not provided");
            return false
        }
        if(!assetCost){
            MessageRenderer.renderErrorText("Asset cost is not provided");
            return false
        }

        var newRowData = [
            { 'id':rID.toString(), 'assetName': assetName.toString(), 'lendingDate':lendingDate.toString(), 'modelNumber':modelNumber.toString(),
                'assetCost':assetCost.toString(), 'delete': '<a  href="javascript:deleteAsset(' + rID + ')" class="ui-icon ui-icon-trash" title="Delete"></a>'}
        ];
        jqGridAssetLendingList.addRowData(rID.toString(), newRowData[0]);
        $("#jqgrid-grid-lending [id*='undefined']").attr('id', null);
        cleanTextBoxAssetLending();
    }
    function deleteAsset(rID){
        $('#jqgrid-grid-lending').jqGrid('delRowData', rID);
    }
    function cleanTextBoxAssetLending(){
        $("#assetName").val('');
        $("#lendingDate").val('');
        $("#modelNumber").val('');
        $("#assetCost").val('');
    }
    function saveAjax(){
        if(!$('#hCustomerId').val()){
            MessageRenderer.renderErrorText("Please Select Customer to proceed");
            return false
        }
        var data =  new Array();
        var gd = $("#jqgrid-grid-lending").jqGrid('getRowData');
        var length = gd.length;
        data.push({'name':'customerMasterId', 'value': $('#hCustomerId').val()});
        data.push({'name':'enterpriseConfiguration', 'value': $('#enterpriseConfiguration').val()});

        for (var i=0; i < length; ++i) {
            if(gd[i].id!='') {
                data.push({'name': 'items.customerAssetLending[' + i + '].assetName', 'value': gd[i].assetName});
                data.push({'name': 'items.customerAssetLending[' + i + '].lendingDate', 'value': gd[i].lendingDate});
                data.push({'name': 'items.customerAssetLending[' + i + '].modelNumber', 'value': gd[i].modelNumber});
                data.push({'name': 'items.customerAssetLending[' + i + '].assetCost', 'value': gd[i].assetCost});
                data.push({'name': 'items.customerAssetLending[' + i + '].sl', 'value': gd[i].sl});
            }
        }
        SubmissionLoader.showTo();
        jQuery.ajax({
            type:'post',
            data: data,
            url: "${request.contextPath}/${params.controller}/createNew",
            success:function(data, textStatus) {
                executePostConditionAssetLending(data);
            },
            error:function(XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#assetName").val('');
                    $("#lendingDate").val('');
                    $("#modelNumber").val('');
                    $("#assetCost").val('');
                    $("#jqgrid-grid-lending").jqGrid('clearGridData');

                    LoadAssetLendingGrid($('#hCustomerId').val())
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                }
                else{
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

    function executePostConditionAssetLending(result) {

        if (result.type == 1) {
            $("#assetName").val('');
            $("#lendingDate").val('');
            $("#modelNumber").val('');
            $("#assetCost").val('');
            $("#jqgrid-grid-lending").jqGrid('clearGridData');

            LoadAssetLendingGrid($('#hCustomerId').val())
        }
        MessageRenderer.render(result);
    }

    $('#search-btn-customer-register-id').click(function(){
        EmployeeRegister.popupEmployeeListPanel();
    });

    var EmployeeRegister = {

    employeeCoreInfoId: null,
    popupEmployeeListPanel: function () {
            var territorySubAreaId = $("#territoryConfiguration").val();
            var customerCategory = $("#customerCategory").val();
            var url = "${request.contextPath}/customerAssetLending/popupCustomerListPanel";
            var params = {territorySubAreaId: territorySubAreaId, customerCategory: customerCategory};
            DocuAjax.html(url, params, function (html) {
            $.fancybox(html);
            });
        }
    }


    $('#customerName').blur(function () {
        if ($('#customerName').val() == '') {
            clearAsset();
        }
    });


    function clearAsset()
    {

        $("#hCustomerId").val('');
        $('#netReceiveAble').val('')
        $("#jqgrid-grid-lending").jqGrid('clearGridData');
        $("#jqgrid-grid-recovery").jqGrid('clearGridData');

    }


    jQuery('#customerName').autocomplete({
        minLength: '1',
        source: function (request, response) {
            var data = {searchKey: request.term};
            var url = '${resource(dir:'customerAssetLending', file:'listCustomerByCode')}?query=' + $('#customerName').val() + "&customerCategory=" + $('#customerCategory').val() + "&territorySubAreaId=" + $('#territoryConfiguration').val();
            DocuAutoComplete.setSpinnerSelector('customerName').execute(response, url, data, function (item) {

                item['label'] = item['name'];
                item['value'] = item['label'];
                return item;
            });
        },
        select: function (event, ui) {
            loadCustomerData(ui.item.id, ui.item.name, ui.item.code, ui.item.enterprise, ui.item.present_address);
        }
    }).data("autocomplete")._renderItem = function (ul, item) {
        var accountstype = "";
        if (item) {
            accountstype = '<div style="font-size: 9px; color:#326E93;">' + "ID: " + item.code + "; Name: " + item.name + "; "+ "; Category: " + item.category + "; " + "Enterprise: " + item.enterprise + "; " + "Address: " + item.present_address + '</div>';
        }
        return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + accountstype).appendTo(ul);
    };

        function loadCustomerData(customerId, cusName, code, enterprise, address) {
            var customer=cusName+" - "+code;
            $("#hCustomerId").val(customerId);
            $("#customerName").val(customer);
            LoadAssetLendingGrid(customerId)
        }



    function LoadAssetLendingGrid(custID)
    {
        getNetReceiveAble(custID)
        $("#jqgrid-grid-lending").setGridParam({
            url: '${resource(dir:'customerAssetLending', file:'list')}?&customerId=' + custID
        });
        $("#jqgrid-grid-lending").trigger("reloadGrid");

        $("#jqgrid-grid-recovery").setGridParam({
            url: '${resource(dir:'customerAssetLending', file:'listRecovery')}?&customerId=' + custID
        });
        $("#jqgrid-grid-recovery").trigger("reloadGrid");
    }

    function getNetReceiveAble(customerId){

        jQuery.ajax({
            type:'post',
            data: {'customerId':customerId},
            url: "${request.contextPath}/${params.controller}/getNetReceivable",
            success:function(data, textStatus) {

                $('#netReceiveAble').val(data.netReceivable)
            },
            error:function(XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
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

    }



    function displayAssetDiv(type)
    {
        if(type=='lending')
        {
            $('#divAssetLending').show();
            $('#divAssetRecovery').hide()

        }
        else if(type=='recovery')
        {
            $('#divAssetLending').hide();
            $('#divAssetRecovery').show()
        }
    }
    function loadCustomerCategory(id){
//        alert(id);
        var options = '<option value="">Please Select</option>';
//        $("#district").html(options);
//        $("#customerCategory").html(options);
        if (id == '') {
            $("#customerCategory").html(options);
            return false;
        }
        jQuery.ajax({
            type:'post',
            data: {'territoryId':id},
            url: "${request.contextPath}/${params.controller}/loadCustomerCategory",
            success:function(data, textStatus) {
//                alert(JSON.stringify(data))
                options = '<option value="">Select Category</option>';
                $.each(data, function (key, val) {
                    options += '<option value="' + val.id + '">' + val.name + '</option>';
                })
                $("#customerCategory").html(options);
            },

            complete:function(){
            },
            dataType:'json'
        });
    }

</script>