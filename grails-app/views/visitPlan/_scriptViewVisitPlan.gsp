<%@ page import="com.docu.commons.CommonConstants" %>
<script type="text/javascript">
    var jqGridProductList = null;
    var rowId = 1;
    var ediTedRowId = "";
    $(document).ready(function () {
        loadGrid();
        initDatePicker();
        $('#search-btn-employee-id').click(function () {
            var url = '${resource(dir:'visitPlan', file:'popupEmployeeListPanel')}';
            var params = {};
            DocuAjax.html(url, params, function (html) {
                $.fancybox(html);
            });
        });

    });
    function initDatePicker() {

        $('#dateFrom').datepicker(
                {
                    dateFormat: 'dd-mm-yy',
                    changeMonth: true,
                    changeYear: true,
                    maxDate: 0
                });

        $('#dateTo').datepicker(
                {
                    dateFormat: 'dd-mm-yy',
                    changeMonth: true,
                    changeYear: true,
                    maxDate: 0
                });

        $('#dateFrom').mask('${CommonConstants.DATE_MASK_FORMAT}', {});
        $('#dateTo').mask('${CommonConstants.DATE_MASK_FORMAT}', {});

        $('#timeFrom').timepicker({showPeriod: true, showLeadingZero: true});
        $('#timeTo').timepicker({showPeriod: true, showLeadingZero: true});

    }
    jQuery('#employeeName').autocomplete({
        minLength: '1',
        source: function (request, response) {
            var data = {searchKey: request.term};
            var url = "${request.contextPath}/${params.controller}/popUpEmployeeNameList?emp=" + $('#employeeName').val();
            DocuAutoComplete.setSpinnerSelector('product').execute(response, url, data, function (item) {
                item['label'] = "[" + item['code'] + "] " + item['employee'];
                item['value'] = item['label'];
                return item;
            });
        },
        select: function (event, ui) {
            $("#employeeName").val(ui.item.value);
            $("#ename").val(ui.item.employee);
            $("#employeeId").val(ui.item.code);
            $("#employeeNamehidden").val(ui.item.id);
            $("#enterprise").val(ui.item.enterprise);
        }
    }).data("autocomplete")._renderItem = function (ul, item) {
        var employeeDetails = "";
        employeeDetails = '<div style="font-size: 9px; color:#326E93;">' + " Name: " + item.employee + '</div>';
        return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + employeeDetails).appendTo(ul);

    };


    function clearPage() {
        $("#employeeNamehidden").val('');
        $("#employeeName").val('');
        $("#employeeId").val('');
        $("#enterprise").val('');
        $("#ename").val('');

    }


    function searchVisitPlan() {
//        debugger
        var employeeId = $("#employeeNamehidden").val();
        var dateFrom = $("#dateFrom").val();
        var dateTo = $("#dateTo").val();

        $("#jqgrid-grid").setGridParam({
            url: '${resource(dir:'visitPlan', file:'searchVisitPlan')}?employeeId=' + employeeId + '&dateFrom=' + dateFrom + '&dateTo=' + dateTo,
            datatype: 'json'
        });
        $("#jqgrid-grid").trigger("reloadGrid");
    }


    function loadGrid()
    {
        $("#jqgrid-grid").jqGrid({
            %{--url: '${resource(dir:'visitPlan', file:'searchVisitPlan')}?employeeId=' + employeeId + '&dateFrom=' + dateFrom + '&dateTo=' + dateTo,--}%
            datatype: "local",
            colNames: [
                'ID',
                'Address To Visit',
                'Date From',
                'Date To',
                'Time From',
                'Time To',
                'Purpose',
                'Remarks'
            ],
            colModel: [
                {name: 'id', index: 'id', width: 100, align: 'left', hidden: true},
                {name: 'placeOfVisit', index: 'placeOfVisit', width: 200, align: 'left', hidden: false},
                {name: 'dateFrom', index: 'dateFrom', width: 100, align: 'left',formatter:'date',formatoptions: { newformat:'d-m-y'}},
                {name: 'dateTo', index: 'dateTo', width: 100, align: 'left',formatter:'date',formatoptions: { newformat:'d-m-y'}},
                {name: 'timeFrom', index: 'timeFrom', width: 100, align: 'center'},
                {name: 'timeTo', index: 'timeTo', width: 100, align: 'center'},
                {name: 'purpose', index: 'purpose', width: 100, align: 'right'},
                {name: 'remarks', index: 'remarks', width: 100, align: 'right'}

            ],
            rowList: [10, 20, 30],
            pager: '#jqgrid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "visit Plan List",
//            autowidth: false,
//            width: 950,
//            height: 100,
            scrollOffset: 0,
            rowNum: 10,
            autowidth: true,
            autoheight: true,
            scrollOffset: 0,
            altRows: true,
            loadComplete: function () {
                clearPage();
            },
            onSelectRow: function (rowid, status) {
//                loadDataTable(rowid);
            }

        });
        $("#jqgrid-grid").jqGrid('navGrid', '#jqgrid-pager', {
            edit: false,
            add: false,
            del: false,
            search: false
        });
    }
    function printVisitPlan(){
        var fromDate = $('#dateFrom').val();
        var toDate = $('#dateTo').val();
        var pin = $('#employeeId').val();
        var ename=$("#ename option:selected").text();
//        alert(territoryArea);
//        alert(fromDate);


        SubmissionLoader.showTo();
        window.open("${resource(dir:'visitPlan', file:'rptVisitPlan')}?format=PDF&fromDate="+fromDate+"&toDate="+toDate+"&pin="+pin+"&ename="+ename);
        SubmissionLoader.hideFrom();
    }
</script>