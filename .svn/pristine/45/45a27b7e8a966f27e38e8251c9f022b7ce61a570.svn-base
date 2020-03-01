<%--
  Created by IntelliJ IDEA.
  User: liton.miah
  Date: 8/2/2016
  Time: 4:02 PM
--%>

<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        $('#ui-widget-header-text').html('Division')
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }
        loadTerritory();
        loadDP();
        var idValue = document.getElementById("territory").value
        if(idValue){
         //   selectTerritory(idValue);
            //fetchAvailableCash(idValue);
        }
        var dpId = document.getElementById("dp").value
        if(dpId){
            selectDP(dpId);
        }

        $("#asOfDate").datepicker(
            {
                dateFormat: 'dd-mm-yy',
                changeMonth: true,
                changeYear: true,
                maxDate:0
            });
        $('#asOfDate').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});
        $('#asOfDate').val($.datepicker.formatDate('dd-mm-yy', new Date()));


        $("#jqgrid-grid").jqGrid({
            datatype: "local",
            colNames: [
                'Customer Name',
                '',
                'Customer ID',
                'Legacy ID',
                'Commission Amount'
            ],
            colModel: [
                {name: 'customer_name', index: 'customer_name', width: 150, align: 'left'},
                {name: 'customer_id', index: 'customer_id', width: 100, align: 'left', hidden:true},
                {name: 'code', index: 'code', width: 100, align: 'left'},
                {name: 'legacy_id', index: 'legacy_id', width: 100, align: 'left'},
                {name: 'commissionAmount', index: 'commissionAmount', width: 100, align: 'right'}
            ],
            rowNum: 10,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
//            pager: '#jqgrid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Customer List",
            autowidth: false,
            height: true,
            multiselect: false,
            scrollOffset: 0,
            footerrow:true,
            gridComplete: function(){
                var $grid = $('#jqgrid-grid');
                var colSum = $grid.jqGrid('getCol', 'commissionAmount', false, 'sum');
                $grid.jqGrid('footerData', 'set', { commissionAmount: colSum, legacy_id:'Total Commission' });
            },
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
            }
        });

        var $footRow = $('#jqgrid-grid').closest(".ui-jqgrid-bdiv").next(".ui-jqgrid-sdiv").find(".footrow");

        var $name = $footRow.find('>td[aria-describedby="jqgrid-grid_customer_name"]'),
//                        $customer_id = $footRow.find('>td[aria-describedby="jqgrid-grid_customer_id"]'),
                $code = $footRow.find('>td[aria-describedby="jqgrid-grid_code"]'),
                $legacy_id = $footRow.find('>td[aria-describedby="jqgrid-grid_legacy_id"]'),
                width3 = $name.width() + $legacy_id.outerWidth() + $code.outerWidth();
        $name.css("display", "none");
        $code.css("display", "none");
        $legacy_id.attr("colspan", "3").width(width3);
        $legacy_id.css("text-align","center");

//        $("#jqgrid-grid").jqGrid('navGrid', '#jqgrid-pager', {edit: false, add: false, del: false, search: false});
//        $(".ui-pg-selbox").children().each(function () {
//            if ($(this).val() == -1) {
//                $(this).html('All')
//            }
//
//        });

    });

    function loadTerritory() {
        var size;
        var options = '';
        if ('${territoryList}' != '') {
            size = ${territoryListSize};
            if (size == 1) {
                options = '<option value="' + ${territoryList}[0].id + '">' + ${territoryList}[0].name + '</option>';
            } else {
                options = '<option value="">Please Select</option>';
                for (var i = 0; i < size; i++) {
                    options += '<option value="' + ${territoryList}[i].id + '">' + ${territoryList}[i].name     + '</option>';
                }
            }
            $("#territory").html(options);
        }
    }

    function loadDP() {
        var size;
        var options = '';
        if ('${dpList}' != '') {
            size = ${dpSize};
            if (size == 1) {
                options = '<option value="' + ${dpList}[0].id + '">' + ${dpList}[0].name + '</option>';
            } else {
                options = '<option value="">Please Select</option>';
                for (var i = 0; i < size; i++) {
                    options += '<option value="' + ${dpList}[i].id + '">' + ${dpList}[i].name     + '</option>';
                }
            }
            $("#dp").html(options);
        }
    }

    function selectTerritory(id) {
        if (!id) {
            MessageRenderer.render({
                "messageBody": "Select Territory!",
                "messageTitle": "View Sales Commission",
                "type": "2"
            });
            return false;
        }
        var options = '<option value="">Select DP...</option>';

        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            url: "${resource(dir:'salesCommission', file:'fetchDPDropdownList')}?id=" + id,
            success: function (data) {
                if(data[0]){
                    options = '<option value="">Select DP...</option>';
                    $.each(data, function (key, val) {
                        options += '<option value="' + val.id + '">' + val.name + '</option>';
                    })

                    $("#customerName").val('');
                    $("#customerId").val('');
                    $("#branchCommission").val('');
                    jQuery("#jqgrid-grid").clearGridData();
                    $("#dp").html(options);
                }else{
                    jQuery("#jqgrid-grid").clearGridData();
                    $("#dp").html(options);
                    $("#customerName").val('');
                    $("#customerId").val('');
                    $("#branchCommission").val('');
//                    reset_form("#gFormSalesCommission");
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
            },
            complete: function () {
                SubmissionLoader.hideFrom();
            },
            dataType: 'json'
        });
    }

    var msg = '';
    function selectDP(id) {
        if(!id){
            return false;
        }
        if(!$('#asOfDate').val()){
            MessageRenderer.render({
                "messageBody": "Please select as of date.",
                "messageTitle": "View Sales Commission",
                "type": "0"
            });
            return false
        }

        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            url: "${resource(dir:'salesCommission', file:'listDefaultCustomerByDP')}?id=" + id,
            success: function (data) {
                if(data[0]){
                    $("#customerName").val(data[0].name);
                    $("#customerId").val(data[0].code);
                }else{
                    $("#customerName").val('');
                    $("#customerId").val('');
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
            },
            complete: function () {
                msg = '';
                loadBranchCommission(id);
                loadGrid(id);
                SubmissionLoader.hideFrom();
            },
            dataType: 'json'
        });
    }

    function loadBranchCommission(id){
        if(id) {
            jQuery.ajax({
                type: 'post',
                url : "${resource(dir:'salesCommission', file:'listBranchCommissionByDP')}?" + "dpId=" + id +"&asOfDate="+$('#asOfDate').val(),

                success: function (data) {
                    if(data[0]) {
                        $('#branchCommission').val(data[0].commissionAmount);
                    }else{
                        $('#branchCommission').val('');
                        msg = 'Branch sales commission not found';
//                        MessageRenderer.render({
//                            "messageBody": "Branch sales commission not found.",
//                            "messageTitle": "View Sales Commission",
//                            "type": "0"
//                        });
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                },
                complete: function () {
                },
                dataType: 'json'
            });
        }
    }

    function loadGrid(id){
        if(id) {
            jQuery.ajax({
                type: 'post',
                url : "${resource(dir:'salesCommission', file:'listCustomersCommissionByDP')}?" + "dpId=" + id +"&asOfDate="+$('#asOfDate').val(),

                success: function (data) {
                    if(data[0]) {
                        jQuery("#jqgrid-grid")
                                .jqGrid('setGridParam',
                                {
                                    datatype: 'local',
                                    data: data
                                })
                                .trigger("reloadGrid");
                    }else{
                        if(msg){
                            msg += ' and Customer sales commission not found';
                        }else{
                            msg = 'Customer sales commission not found';
                        }
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                },
                complete: function () {
                    if(msg){
                        MessageRenderer.render({
                            "messageBody": msg+'.',
                            "messageTitle": "View Sales Commission",
                            "type": "0"
                        });
                        return false
                        jQuery("#jqgrid-grid").clearGridData();
                    }
                },
                dataType: 'json'
            });
        }else{
            $("#jqgrid-grid").jqGrid('clearGridData');
        }
    }
</script>