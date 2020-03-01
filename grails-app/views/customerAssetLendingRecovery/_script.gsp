<%@ page import="com.bits.bdfp.util.ApplicationConstants" %>
<script type="text/javascript">
    var jqGridAssetList = null;
    var rowId = 1;
    $(document).ready(function(){
        $("#lendingDate").datepicker({
            dateFormat: '${com.bits.bdfp.util.ApplicationConstants.JQUERY_UI_DATE_FORMAT}',
            minDate: new Date()
        });
        jQuery('#customerList').autocomplete({
            minLength:'2',
            source:function (request, response) {
                //EmployeeRegister.employeeCoreInfoId = null;
                $('#popEmpDetails').html("");
                var data = {searchKey:request.term};
                var url = '${resource(dir:'customerMaster', file:'listCustomer')}';
                DocuAutoComplete.setSpinnerSelector('searchKey').execute(response, url, data, function (item) {
                    item['label'] = item['legacy_id'] + " [" + item['code'] + "] " + item['name'];
                    item['value'] = item['label'];
                    return item;
                });
            },
            select:function (event, ui) {

                $('#hCustomerId').val(ui.item.id);
                $('#name').val(ui.item.name);
                getNetReceiveAble(ui.item.id);
            }
        }).data("autocomplete")._renderItem = function (ul, item) {
            var accountstype = "";
            if (item.id) {
                accountstype = '<div style="font-size: 9px; color:#326E93;">' +" Legacy ID: " + item.legacy_id + ", Code: " +item.code+", Name: "+item.name+",Address: "+item.present_address + '</div>';
            }
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + accountstype).appendTo(ul);

        }
        loadGrid();
    })
    function getNetReceiveAble(customerId){

    }
    function loadGrid(){
        jqGridAssetList = $("#jqgrid-grid").jqGrid({
            datatype: "json",
            colNames: [
                'ID',
                'Asset Name',
                'Date',
                'Model Number',
                'Asset Cost'

            ],
            colModel: [
                {name: 'id', index: 'id', width: 100, align: 'left',hidden:true},
                {name: 'assetName', index: 'assetName', width: 100, align: 'left'},
                {name: 'lendingDate', index: 'lendingDate', width: 100, align: 'left'},
                {name: 'modelNumber', index: 'modelNumber', width: 100, align: 'left'},
                {name: 'assetCost', index: 'assetCost', width: 100, align: 'left'}
            ],
            rowNum: 50,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#ship-address-grid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Asset Information",
            autowidth: false,
            height: 100,
            width: 785,
            scrollOffset: 0,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
//                executeEditTerritorySubAreaConfiguration();
            }
        });
        $("#ship-address-grid").jqGrid('navGrid', '#ship-address-grid-pager', {
            edit: false,
            add: false,
            del: false,
            search: false
        });
    }
    function addToGrid(){

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
            { 'id':rowId, 'assetName': assetName.toString(), 'lendingDate':lendingDate.toString(), 'modelNumber':modelNumber.toString(),
                'assetCost':assetCost.toString()}
        ];
        jqGridAssetList.addRowData(rowId, newRowData);
        $("#jqgrid-grid [id*='undefined']").attr('id', rowId);
        rowId ++;
        cleanTextBox();
    }
    function saveAjax(){
        if(!$('#hCustomerId').val()){
            MessageRenderer.renderErrorText("Please Select Customer to proceed");
            return false
        }
        var data =  new Array();
        var gd = $("#jqgrid-grid").jqGrid('getRowData');
        var length = gd.length;
        for (var i=0; i < length; ++i) {
            data.push({'name':'items.customerAssetLending['+i+'].customerMaster.id', 'value': $('#hCustomerId').val()});
            data.push({'name':'items.customerAssetLending['+i+'].assetName', 'value': gd[i].assetName});
            data.push({'name':'items.customerAssetLending['+i+'].lendingDate', 'value': gd[i].lendingDate});
            data.push({'name':'items.customerAssetLending['+i+'].modelNumber', 'value': gd[i].modelNumber});
            data.push({'name':'items.customerAssetLending['+i+'].assetCost', 'value': gd[i].assetCost});
        }
        SubmissionLoader.showTo();
        jQuery.ajax({
            type:'post',
            data: data,
            url: "${request.contextPath}/${params.controller}/createNew",
            success:function(data, textStatus) {
                executePostCondition(data);
            },
            error:function(XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#assetName").val('');
                    $("#lendingDate").val('');
                    $("#modelNumber").val('');
                    $("#assetCost").val('');
                    $("#jqgrid-grid").jqGrid('clearGridData');

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
    function cleanTextBox(){
        $("#assetName").val('');
        $("#lendingDate").val('');
        $("#modelNumber").val('');
        $("#assetCost").val('');
    }
    function executePostCondition(result) {
        if (result.type == 1) {
            $("#assetName").val('');
            $("#lendingDate").val('');
            $("#modelNumber").val('');
            $("#assetCost").val('');
            $("#jqgrid-grid").jqGrid('clearGridData');
        }
        MessageRenderer.render(result);
    }

</script>