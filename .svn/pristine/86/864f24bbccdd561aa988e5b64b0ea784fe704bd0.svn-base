<script type="text/javascript">
    var jqGridProductList = null;
    var rowId = 1;
    var ediTedRowId = "";
    $(document).ready(function () {
        initDatePicker();
        initializeGrid();

    });
    function initDatePicker() {

//        $("#dateFrom, #dateTo").datepicker(
//                {
//                    dateFormat: 'dd-mm-yy',
//                    changeMonth: true,
//                    changeYear: true
////                    minDate: 0
//                });
        $("#dateTo").datepicker({ dateFormat: 'dd-mm-yy',changeMonth: true, changeYear: true });

        $("#dateFrom").datepicker({
            dateFormat: 'dd-mm-yy',
            changeMonth: true,
            changeYear: true,
//            minDate: 0,
            onSelect: function () {
                var autoDateto = $(this).datepicker('getDate');
                $("#dateTo").datepicker("setDate", new Date(autoDateto.getTime()));
//                $("#dateTo").datepicker("setDate", "+15d");
            }
        });

        $('#dateFrom').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});
        $('#dateTo').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});

        $('#timeFrom').timepicker({showPeriod: true, showLeadingZero: true});
        $('#timeTo').timepicker({showPeriod: true, showLeadingZero: true});

    }
    function initializeGrid() {
        jqGridProductList = $("#jqgrid-grid").jqGrid({
            datatype: "json",
            colNames: [
                'ID',
                'Address To Visit',
                'Date From',
                'Date To',
                'Time From',
                'Time To',
                'Purpose',
                'Remarks',
                'Delete'
            ],
            colModel: [
                {name: 'id', index: 'id', width: 100, align: 'left', hidden: true},
                {name: 'placeOfVisit', index: 'placeOfVisit', width: 200, align: 'left', hidden: false},
                {name: 'dateFrom', index: 'dateFrom', width: 100, align: 'left'},
                {name: 'dateTo', index: 'dateTo', width: 100, align: 'center'},
                {name: 'timeFrom', index: 'timeFrom', width: 100, align: 'center'},
                {name: 'timeTo', index: 'timeTo', width: 100, align: 'center'},
                {name: 'purpose', index: 'purpose', width: 100, align: 'right'},
                {name: 'remarks', index: 'remarks', width: 100, align: 'right'},
                {name: 'delete', index: 'delete', width: 100, align: 'right'}

            ],
            rowNum: 50,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#customer-order-grid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Create Visit Plan",
            autowidth: false,
            width: 950,
            height: 100,
            scrollOffset: 0,
            loadComplete: function () {

            },
            onSelectRow: function (rowid, status) {
                loadDataTable(rowid);
            }
        });
        $("#customer-order-grid").jqGrid('navGrid', '#customer-order-grid-pager', {
            edit: false,
            add: false,
            del: false,
            search: false
        });
    }
    function addItemsToGrid() {
        var customerId = $('#customerMaster').val();
        if (!customerId) {
            MessageRenderer.renderErrorText("Employee is not selected");
            return;
        }
        var placeOfVisit = $("#placeOfVisit").val();
        if (!placeOfVisit) {
            MessageRenderer.renderErrorText("place to visit is not set");
            return;
        }
        var dateFrom = $("#dateFrom").val();
        if (!dateFrom) {
            MessageRenderer.renderErrorText("Date from is not set");
            return;
        }
        var dateTo = $("#dateTo").val();
        if (!dateTo) {
            MessageRenderer.renderErrorText("Date to is not set");
            return;
        }
        var timeFrom = $("#timeFrom").val();
        if (!timeFrom) {
            MessageRenderer.renderErrorText("Time from is not set");
            return;
        }
        var timeTo = $("#timeTo").val();
        if (!timeTo) {
            MessageRenderer.renderErrorText("Time to is not set");
            return;
        }
        var purpose = $("#purpose").val();
        var remarks = $("#remarks").val();
        if ($("#add-dates-button").val() == "Update") {
            jqGridProductList.delRowData(ediTedRowId);
            $('#jqgrid-grid').jqGrid('delRowData', ediTedRowId);
            $("#add-dates-button").val("Add To Grid");
        }
        var newRowData = [
            {
                'id': rowId,
                'placeOfVisit': placeOfVisit,
                'dateFrom': dateFrom.toString(),
                'dateTo': dateTo.toString(),
                'timeFrom': timeFrom.toString(),
                'timeTo': timeTo.toString(),
                'purpose': purpose.toString(),
                'remarks': remarks.toString(),
                'delete': '<a  href="javascript:deleteItems(' + rowId + ')" class="ui-icon ui-icon-trash" title="Delete"></a>'
            }
        ];
        jqGridProductList.addRowData(rowId, newRowData[0]);
        rowId++;
        clearPage();
    }
    function deleteItems(rowId) {
        jqGridProductList.delRowData(rowId);
        $('#jqgrid-grid').jqGrid('delRowData', rowId);
    }
    function clearPage() {
        ediTedRowId = "";
        $("#placeOfVisit").val('');
//        $("#dateFrom").val('');
//        $("#dateTo").val('');
        $("#timeFrom").val('');
        $("#timeTo").val('');
        $("#purpose").val('');
        $("#remarks").val('');
    }
    function loadDataTable(rowId) {
        var rowTo = jqGridProductList.getRowData(rowId.toString());
        if (rowTo) {
            ediTedRowId = rowTo.id;
            $("#placeOfVisit").val(rowTo.placeOfVisit);
            $("#dateFrom").val(rowTo.dateFrom);
            $("#dateTo").val(rowTo.dateTo);
            $("#timeFrom").val(rowTo.timeFrom);
            $("#timeTo").val(rowTo.timeTo);
            $("#purpose").val(rowTo.purpose);
            $("#remarks").val(rowTo.remarks);
            $("#add-dates-button").val("Update");
        }
    }
    function saveGridItems() {
        var data = new Array();
        var gd = $("#jqgrid-grid").jqGrid('getRowData');
        var length = gd.length;
        for (var i = 0; i < length; ++i) {
            data.push({'name': 'items.visitPlan[' + i + '].customerMaster.id', 'value': $('#customerMaster').val()});
            data.push({'name': 'items.visitPlan[' + i + '].placeOfVisit', 'value': gd[i].placeOfVisit});
            data.push({'name': 'items.visitPlan[' + i + '].dateFrom', 'value': gd[i].dateFrom});
            data.push({'name': 'items.visitPlan[' + i + '].dateTo', 'value': gd[i].dateTo});
            data.push({'name': 'items.visitPlan[' + i + '].timeFrom', 'value': gd[i].timeFrom});
            data.push({'name': 'items.visitPlan[' + i + '].timeTo', 'value': gd[i].timeTo});
            data.push({'name': 'items.visitPlan[' + i + '].purpose', 'value': gd[i].purpose});
            data.push({'name': 'items.visitPlan[' + i + '].remarks', 'value': gd[i].remarks});
        }
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: data,
            url: "${request.contextPath}/${params.controller}/create",
            success: function (data, textStatus) {
                executePostConditionRetailOrder(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-grid").jqGrid('clearGridData');
                    clearPage();
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                } else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete: function () {
                SubmissionLoader.hideFrom();
            },
            dataType: 'json'
        });
        return false;
    }
    function executePostConditionRetailOrder(result) {
        if (result.type == 1) {
            $("#jqgrid-grid").jqGrid('clearGridData');
            clearPage();
        }
        MessageRenderer.render(result);
    }
</script>