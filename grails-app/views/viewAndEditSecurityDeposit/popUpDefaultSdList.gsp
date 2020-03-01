<%--
  Created by IntelliJ IDEA.
  User: liton.miah
  Date: 1/17/2016
  Time: 4:43 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>

<style>
.ui-jqgrid td .pool-radio {
    margin: 3px 0 0 8px !important;
}
</style>
<div class="main_container content_container" style="overflow: hidden;">
    <h1 class="width515">Deposited List</h1>

    <div class="height10"></div>
    <div class="jqgrid-container">
        <table id="jqgrid-sd-grid"></table>
        <div id="jqgrid-sd-pager"></div>
    </div>
    <div class="height10"></div>

    <div class="input_container">
        <div class="input-element">
            <label class="txtright bold hight1x txtright width160">
                <g:message code="label" default="Editable Deposited Amount"/>
            </label>
        </div>
        <div class="input-element">
            <g:textField name="updateAmount" style="text-align: right;"/>
            <g:hiddenField name="updateId" readonly="true"/>
            <g:hiddenField name="customerId" value="${customerId}" readonly="true"/>
        </div>
        <div class="clear"></div>
    </div>

    <div class="height10 clear"></div>
    <div class="input_container">
        <span class="button"><input type="button" name="amnt-edit-button" id="amnt-edit-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="${message(code: 'default.button.amnt-update.label', default: 'Update')}"
                                    onclick="executeUpdate();"/></span>
        <span class="button"><input type="button" name="amnt-cancel-button" id="amnt-cancel-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="${message(code: 'default.button.amnt-cancel.label', default: 'Cancel')}"
                                    onclick="clearExit();"/></span>
        <div class="clear"></div>
    </div>
    <div class="height10 clear"></div>
</div>

<script>
    $(document).ready(function(){
        $("#jqgrid-sd-grid").jqGrid({
            url:'${request.contextPath}/${params.controller}/listCustomersSd?customerId=' + $('#customerId').val() + '&date=' + $('#asOfDate').val(),
            datatype: "json",
            colNames: [
                'SI',
                '',
                'Id',
                'Customer Id',
                'Customer Name',
                'Deposited',
                'Withdrawn',
                'Transaction Date'
            ],
            colModel: [
                {name: 'si', index: 'si', width: 30},
                {name: 'select', index: 'select', width: 30},
                {name: 'id', index: 'id', width: 0, hidden:true},
                {name: 'customer_master_id', index: 'customer_master_id', width: 0,hidden:true},
                {name: 'name', index: 'name', width: 130},
                {name: 'deposited', index: 'deposited', width: 105, align:'right'},
                {name: 'withdrawn', index: 'withdrawn', width: 100, align:'right'},
                {name: 'date_transaction', index: 'date_transaction', width: 120,
                    formatter: 'date',
                    formatoptions: {newformat:'d-m-Y'},
                    align:'center'
                }
            ],
            rowNum: 10,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#jqgrid-sd-pager',
            sortname: 'date_transaction',
            viewrecords: true,
            sortorder: "asc",
            caption: "All Transaction Records",
            autowidth: false,
            height: 130,
//            footerrow:true,
            scrollOffset: 0,
//            rownumbers:true,
            loadComplete: function () {
                var ids = jQuery("#jqgrid-sd-grid").jqGrid('getDataIDs');
                for (key in ids) {
                    var id = $("#jqgrid-sd-grid").getCell(ids[key], 'id');
                    var amount = $("#jqgrid-sd-grid").getCell(ids[key], 'deposited');

                    $("#jqgrid-sd-grid").jqGrid('setRowData', ids[key], {
                        select: '<input name="d-select" type="radio" id="pool-radio-' + id + '" class="pool-radio" val="' +
                        amount + '" onclick="getDepositAmount('+id+','+amount+');" />'});
                }
            },
            onSelectRow: function (rowid, status) {
                var id = $("#jqgrid-sd-grid").getCell(rowid, 'id');
//                var customerId = $("#jqgrid-sd-grid").getCell(rowid, 'customer_master_id');
                var amount = $("#jqgrid-sd-grid").getCell(rowid, 'deposited');
                $('#pool-radio-'+id).attr('checked',true);
                getDepositAmount(id,amount);
            }
        });
        $("#jqgrid-tp-grid").jqGrid('navGrid', '#jqgrid-tp-pager', {
            edit: false,
            add: false,
            del: false,
            search: false
        });
        $(".ui-pg-selbox").children().each(function() {
            if ($(this).val() == -1) {
                $(this).html('All')
            }
        });

    });

    function getDepositAmount(id,amount){
        $('#updateId').val(id);
        $('#updateAmount').val(amount);
    }

    function clearExit(){
        $('#updateId').val('');
        $('#updateAmount').val('');
        $.fancybox.close();
        loadDpDefaultCustomer($("#distributionPoint").val());
    }

    function executeUpdate(){
        if(!$('#updateId').val()){
            MessageRenderer.render({
                messageTitle: 'Security Deposit',
                type: 0,
                messageBody: 'Please select a trade partner first.'
            });
            return false;
        }
        var url = '${resource(dir:'viewAndEditSecurityDeposit', file:'updateSecurityDeposit')}';
        var params = {depositId:$('#updateId').val(), updateAmount:$('#updateAmount').val()};
        SubmissionLoader.showTo();
        DocuAjax.json(url, params, function (result) {
            reloadData();
            MessageRenderer.render(result);
        });
        SubmissionLoader.hideFrom();
    }

    function reloadData(){
        $("#jqgrid-sd-grid").trigger("reloadGrid");
        $('#updateId').val('');
        $('#updateAmount').val('');
    }

    $(window).click(function(e) {
        if(e.target.id == 'fancybox-overlay'){
            $.fancybox.close();
            loadDpDefaultCustomer($("#distributionPoint").val());
        }
    });
    $("#fancybox-close").click(function(e) {
        if(e.target.id == 'fancybox-close'){
            $.fancybox.close();
            loadDpDefaultCustomer($("#distributionPoint").val());
        }
    });
</script>
