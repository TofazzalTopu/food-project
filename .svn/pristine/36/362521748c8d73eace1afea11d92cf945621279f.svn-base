<%@ page import="com.bits.bdfp.util.ApplicationConstants" %>
<script type="text/javascript">
    var jqGridAssetList = null;
    var rowId = 1;
    $(document).ready(function(){
        $("#recoveryDate").datepicker({
            dateFormat: '${com.bits.bdfp.util.ApplicationConstants.JQUERY_UI_DATE_FORMAT}',
            maxDate: new Date()
        });
        $("#amount").format({precision: 2, allow_negative: false, autofix: true});
        loadGridRecovery();
    })

    function loadGridRecovery(){
        jqGridAssetList = $("#jqgrid-grid-recovery").jqGrid({
            datatype: "json",
            colNames: [
                'ID',
                'Date',
                'Amount',
                    ''
            ],
            colModel: [
                {name: 'id', index: 'id', width: 100, align: 'left',hidden:true},
                {name: 'recoveryDate', index: 'recoveryDate', width: 100, align: 'left'},
                {name: 'amount', index: 'amount', width: 100, align: 'right'},
                {name:'delete', index:'delete', width:30, align:'center', sortable:false},
            ],
            rowNum: 50,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#ship-address-grid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Asset Information",
            autowidth: false,
            height: 140,
            rownumbers: true,
            width: 785,
            scrollOffset: 0,
            footerrow : true,
            loadComplete: function () {
            },
            gridComplete: function() {
                calculateSummaryData();
            },

            afterSaveCell : function(rowid,name,val,iRow,iCol) {
                calculateSummaryData();
            }
        });


        function calculateSummaryData() {
            var $grid = $('#jqgrid-grid-recovery');
            var sumamount = $grid.jqGrid('getCol', 'amount', false, 'sum');
            sumamount=(sumamount).toFixed(2)
            $grid.jqGrid('footerData', 'set', { 'amount': sumamount });
            $grid.jqGrid('footerData', 'set', { 'recoveryDate': 'Total'});
        }

        $("#ship-address-grid").jqGrid('navGrid', '#ship-address-grid-pager', {
            edit: false,
            add: false,
            del: false,
            search: false
        });
    }
    function addToGridRecovery(){
        var rID=jqGridAssetList.getRowData().length+1
        var recoveryDate = $("#recoveryDate").val();
        var amount = $("#amount").val();


        if(!recoveryDate){
            MessageRenderer.renderErrorText("Date is not provided");
            return false
        }
        if(!amount){
            MessageRenderer.renderErrorText("Amount is not provided");
            return false
        }
        var newRowData = [
            { 'id':rID.toString(), 'recoveryDate': recoveryDate.toString(), 'amount':amount.toString(),
                'delete': '<a  href="javascript:deleteAssetRecovery(' + rID + ')" class="ui-icon ui-icon-trash" title="Delete"></a>'}
        ];
        jqGridAssetList.addRowData(rID.toString(), newRowData[0]);
        $("#jqgrid-grid-recovery [id*='undefined']").attr('id', null);
        rowId ++;
        cleanTextBoxRecovery();
    }

    function deleteAssetRecovery(rID)
    {
        $('#jqgrid-grid-recovery').jqGrid('delRowData', rID);
    }
    function saveRecoveryAjax(){
        if(!$('#hCustomerId').val()){
            MessageRenderer.renderErrorText("Please Select Customer to proceed");
            return false
        }
        var data =  new Array();
        var gd = $("#jqgrid-grid-recovery").jqGrid('getRowData');
        var length = gd.length;
        data.push({'name':'customerMasterId', 'value': $('#hCustomerId').val()});
        data.push({'name':'enterpriseConfiguration', 'value': $('#enterpriseConfiguration').val()});
        for (var i=0; i < length; ++i) {
            if(gd[i].id!='')
            {
                data.push({'name': 'items.customerRecovery[' + i + '].recoveryDate', 'value': gd[i].recoveryDate});
                data.push({'name': 'items.customerRecovery[' + i + '].amount', 'value': gd[i].amount});
            }
        }
        SubmissionLoader.showTo();
        jQuery.ajax({
            type:'post',
            data: data,
            url: "${request.contextPath}/${params.controller}/createNewRecovery",
            success:function(data, textStatus) {

                jQuery.ajax({
                    type:'post',
                    data: {'customerId':$('#hCustomerId').val()},
                    url: "${request.contextPath}/${params.controller}/getNetReceivable",
                    success:function(data, textStatus) {
                        $('#netReceiveAble').val(data.netReceivable)
                    },
                    error:function(XMLHttpRequest, textStatus, errorThrown) {
                        MessageRenderer.renderErrorText(XMLHttpRequest.responseText)
                    },
                    complete:function(){
                        SubmissionLoader.hideFrom();
                    },
                    dataType:'json'
                });

                $("#jqgrid-grid-recovery").setGridParam({
                    url: '${resource(dir:'customerAssetLending', file:'listRecovery')}?&customerId=' + $('#hCustomerId').val()
                });
                $("#jqgrid-grid-recovery").trigger("reloadGrid")
                executePostCondition(data);
            },
            error:function(XMLHttpRequest, textStatus, errorThrown) {
                MessageRenderer.renderErrorText(XMLHttpRequest.responseText)
            },
            complete:function(){
                SubmissionLoader.hideFrom();
            },
            dataType:'json'
        });
        return false;
    }
    function cleanTextBoxRecovery(){
        $("#recoveryDate").val('');
        $("#amount").val('');
    }
    function executePostCondition(result) {
        if (result.type == 1) {
            $("#recoveryDate").val('');
            $("#amount").val('');
        }
        MessageRenderer.render(result);
    }

</script>