<script type="text/javascript">
    $(document).ready(function () {
        setDateRangeNoLimit('customerEffectiveDateFrom','customerEffectiveDateTo');
        var actionUrl=''
        if($('#cid').val()){
            actionUrl='${resource(dir:'salesCommission', file:'updateProductSalesCommissionDetails')}?cscId=' + $('#cid').val()
        }
        $("#jqgrid-grid-update-order").jqGrid({
            url: actionUrl,
            datatype: "json",
            colNames: [
                'ID',
                'SL',
                'Product Code',
                'Product Name',
                'Delete'
            ],
            colModel: [
                {name: 'id', index: 'id', width: 20},
                {name: 'productid', index: 'productid', width: 40},
                {name: 'pcode', index: 'pcode', width: 300},
                {name: 'pname', index: 'pname', width: 320},
                {name: 'delete', index: 'delete', width: 80}
            ],
            rowNum: 50,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Product Information",
            autowidth: true,
            height: 200,
            scrollOffset: 0,
            loadComplete: function (result) {
                debugger
                $("#branchCommission").val(parseFloat(result.bcommission));
                $("#salesManCommission").val(parseFloat(result.scommission));
                $("#customerEffectiveDateFrom").val(result.dateFrom);
                $("#customerEffectiveDateTo").val(result.dateTo);
                $.each(result.rows, function (i, item) {
                    var rowData = $("#jqgrid-grid-update-order").jqGrid('getRowData', result.rows[i].id);
                    rowData.delete = '<span onclick="deleteProductSalesCommission(' + result.rows[i].id + ')" class="ui-icon ui-icon-closethick" title="Delete"></span>';
                    $('#jqgrid-grid-update-order').jqGrid('setRowData', result.rows[i].id, rowData);
                });
            },
            onSelectRow: function (rowid, status) {
            }
        });

    });

    function checkBranchCommission() {
        var val = parseFloat($('#branchCommission').val());
        if (val > 100.00) {
            $('#salesManCommission').val('');
            return " * Branch Commission % can’t be more than 100.00";
        }
    }

    function insertSalesManCommission(val) {
        var val = parseFloat(val);
        if (val <= 100) {
            $('#salesManCommission').val((100 - val).toFixed(2));
        } else {
            $('#salesManCommission').val('');
        }
    }
    function addProductDetails(){
        var url = null;
        var productid = $("#pId").val();
        $("#productId").val(productid);
        var text = $("#pId option:selected").text();
        var reg1 = text.match(/\[(.*?)\]/);
        var reg2=text.substring(text.lastIndexOf("-")+1)
        var myGrid = $("#jqgrid-grid-update-order");
        var colData = myGrid.jqGrid('getDataIDs');
        for(var i = 0; i < colData.length; i++){
            var productTemp = myGrid.jqGrid("getCell", colData[i], 'pcode');
            if(productTemp == reg1[1]){
                MessageRenderer.render({
                    "messageBody": "Duplicate product can not be added.",
                    "messageTitle": "Update Sales commission",
                    "type": "2"
                });
                $("#productId").val('');
                return;
            }
        }
         var data = $("#gFormUpdateProductSalesCommission").serialize(),
         url = "${resource(dir:'salesCommission', file:'createProductSalesCommission')}";
//         alert(data);
        SubmissionLoader.showTo();
        jQuery.ajax({
            type:'post',
            data: data,
            url: url,
            success:function(data, textStatus) {
                if (data.type == 1) {
                    $("#jqgrid-grid-update-order").trigger("reloadGrid");
                    $("#pId").val('');
                    $("#productId").val('');

                }
                MessageRenderer.render(data);
            },
            error:function(XMLHttpRequest, textStatus, errorThrown) {

                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);

            },
            complete:function(){
                SubmissionLoader.hideFrom();
            },
            dataType:'json'
        });
        return false;

    }
    function deleteProductSalesCommission(pid){
        SubmissionLoader.showTo();
        jQuery.ajax({
            type:'post',
            data: {pid:pid},
            url: "${resource(dir:'salesCommission', file:'deleteProduct')}",
            success:function(data, textStatus) {
                $("#jqgrid-grid-update-order").trigger("reloadGrid");
                MessageRenderer.render(data);
            },
            error:function(XMLHttpRequest, textStatus, errorThrown) {

                MessageRenderer.renderErrorText(XMLHttpRequest.responseText);

            },
            complete:function(){
                SubmissionLoader.hideFrom();
            },
            dataType:'json'
        });
        return false;

    }

    function updateDateForCustomer(){
        var url = null;
        var customerSalesCommissionId = $("#cid").val();
            if(!customerSalesCommissionId){
                MessageRenderer.render({
                    "messageBody": "Customer Sales Commission Not Found.",
                    "messageTitle": "Update Sales commission",
                    "type": "2"
                });
                return;
            }

        var data = $("#gFormUpdateProductSalesCommission").serialize(),
        url = "${resource(dir:'salesCommission', file:'updateDateForParticularCustomer')}";
//         alert(data);
        SubmissionLoader.showTo();
        jQuery.ajax({
            type:'post',
            data: data,
            url: url,
            success:function(data, textStatus) {
                if (data.type == 1) {
                    $("#jqgrid-grid-update-order").trigger("reloadGrid");
                    $("#pId").val('');
                    $("#productId").val('');

                }
                MessageRenderer.render(data);
            },
            error:function(XMLHttpRequest, textStatus, errorThrown) {

                MessageRenderer.renderErrorText(XMLHttpRequest.responseText);

            },
            complete:function(){
                SubmissionLoader.hideFrom();
            },
            dataType:'json'
        });
        return false;

    }

</script>