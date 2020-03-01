<%@ page import="com.bits.bdfp.util.ApplicationConstants" %>
<script type="text/javascript">
    var monthListJScript = ['Jan','Feb','Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
    var jqGridSubordinateEmployeeList = null;
    var jqGridMonthlyAssignedTargetList = null;
    var daysInFeb = ${daysInFeb};
    var isRedistributionNeeded = false;
    $(document).ready(function () {
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }
        $("#yearlyTarget").val(${yearlyTarget});
        if(${editableMonthFrom}){
            for(var i = 0; i <= ${editableMonthFrom}; i++) {
                $("#month_" + i).attr("disabled", true);
            }
        }
//        resetMonthWiseTarget();
        $("#reDistributeMonthWise-button").hide();
        var actionUrl = "";
        <g:if test="${yearlySalesTargetByAmount.isSalesManIncluded}">
            actionUrl = "${request.contextPath}/${params.controller}/listSubordinateAndSalesManForTargetSetup";
        </g:if>
        <g:else>
            actionUrl = "${request.contextPath}/${params.controller}/listSubordinateEmployees";
        </g:else>
        jqGridSubordinateEmployeeList = $("#jqgrid-grid-subordinate-employee").jqGrid({
            url:actionUrl,
            datatype: "json",
            colNames:[
                'ID',
                'PIN',
                'Name',
                'Department',
                'Designation',
                'isEmployee'
            ],
            colModel:[
                {name:'id', index:'id', width:0, hidden:true},
                {name:'code', index:'code', width:80, align:'center'},
                {name:'name', index:'name', width:250, align:'left'},
                {name:'department', index:'department', width:250, align:'left', sortable: false},
                {name:'designation', index:'designation', width:250, align:'left', sortable: false},
                {name:'isEmployee', index:'isEmployee', width:250, align:'left', hidden: true}
            ],
            rowNum: -1,
            viewrecords:true,
            caption:"Available Employee/Sales Man",
            autowidth:false,
            shrinkToFit: true,
            height:true,
            scrollOffset:0,
            gridview:true,
            multiselect: true,
//            rownumbers: true,

            gridComplete: function() {
            },
            loadComplete: function (data) {
//                $.each(data.rows, function (i, item) {
//                    var rowData = $("#jqgrid-grid-subordinate-employee").jqGrid('getRowData', data.rows[i].id);
//                    rowData.isEmployee = "1";
//                    $('#jqgrid-grid-subordinate-employee').jqGrid('setRowData', data.rows[i].id, rowData);
//                });
                distributeMonthWiseTarget();
            },
            onSelectRow: function(rowid, status) {
            },
            loadError: function (jqXHR, textStatus, errorThrown) {
                MessageRenderer.renderErrorText(jqXHR.responseText,'');
            }
        });

    });
    function resetMonthWiseTarget() {
        var yearlyTarget = Number($("#yearlyTarget").val());
        var nonEditableMonthlyTarget = 0.0;
        for(var i = 1; i <= ${editableMonthFrom}; i++) {
            nonEditableMonthlyTarget += Number($("#month_" + i).val());
        }
        var perMonthTarget = (yearlyTarget - nonEditableMonthlyTarget)/(12-${editableMonthFrom});
        var totalMonthlyTarget = 0.0;
        for(var j = ${editableMonthFrom} + 1; j < 12; j++) {
            totalMonthlyTarget += Number(perMonthTarget.toFixed(2));
            $("#month_" + j).val(perMonthTarget.toFixed(2));
        }
        var lastMonthTarget = (yearlyTarget - nonEditableMonthlyTarget) - totalMonthlyTarget;
        $("#month_12").val(lastMonthTarget.toFixed(2));
    }
    function distributeMonthWiseTarget(){
        jqGridMonthlyAssignedTargetList = $("#jqgrid-grid-subordinate-monthly").jqGrid({
            url:"${request.contextPath}/${params.controller}/listSubordinateMonthLySalesTarget",
            postData:{targetYear: $("#targetYear").val()},
            datatype: "json",
            mtype: 'POST',
            colNames:[
                'ID',
                'PIN',
                'Name',
                'Jan',
                'Feb',
                'Mar',
                'Apr',
                'May',
                'Jun',
                'Jul',
                'Aug',
                'Sep',
                'Oct',
                'Nov',
                'Dec',
                'Year Total'
            ],
            colModel:[
                {name:'id', index:'id', width:0, hidden:true},
                {name:'code', index:'code', width:80, align:'left'},
                {name:'name', index:'name', width:150, align:'left'},
                {name:'jan', index:'jan', width:60, align:'right', sorttype:'number', formatter:"number", editable: true, editrules:{number:true}, formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2}},
                {name:'feb', index:'feb', width:60, align:'right', sorttype:'number', formatter:"number", editable: true, editrules:{number:true}, formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2}},
                {name:'mar', index:'mar', width:60, align:'right', sorttype:'number', formatter:"number", editable: true, editrules:{number:true}, formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2}},
                {name:'apr', index:'apr', width:60, align:'right', sorttype:'number', formatter:"number", editable: true, editrules:{number:true}, formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2}},
                {name:'may', index:'may', width:60, align:'right', sorttype:'number', formatter:"number", editable: true, editrules:{number:true}, formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2}},
                {name:'jun', index:'jun', width:60, align:'right', sorttype:'number', formatter:"number", editable: true, editrules:{number:true}, formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2}},
                {name:'jul', index:'jul', width:60, align:'right', sorttype:'number', formatter:"number", editable: true, editrules:{number:true}, formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2}},
                {name:'aug', index:'aug', width:60, align:'right', sorttype:'number', formatter:"number", editable: true, editrules:{number:true}, formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2}},
                {name:'sep', index:'sep', width:60, align:'right', sorttype:'number', formatter:"number", editable: true, editrules:{number:true}, formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2}},
                {name:'oct', index:'oct', width:60, align:'right', sorttype:'number', formatter:"number", editable: true, editrules:{number:true}, formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2}},
                {name:'nov', index:'nov', width:60, align:'right', sorttype:'number', formatter:"number", editable: true, editrules:{number:true}, formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2}},
                {name:'dec', index:'dec', width:60, align:'right', sorttype:'number', formatter:"number", editable: true, editrules:{number:true}, formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2}},
                {name:'yearTotal', index:'yearTotal', width:80, align:'right', sorttype:'number', formatter:"number", formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2}}
            ],
            rowNum: -1,
            viewrecords:true,
            caption:"Monthly Employee/Sales Man wise Target",
            autowidth:false,
            shrinkToFit: true,
            height:true,
            scrollOffset:0,
            gridview:true,
            cellEdit: true,
            cellsubmit: 'clientArray',
            cellurl: null,
            footerrow : true,

            gridComplete: function() {
            },
            loadComplete: function (data) {
                calculateMonthlySummaryData();
                loadDayWiseTarget();
            },
            onSelectRow: function(rowid, status) {
            },
            afterSaveCell : function(rowid,name,val,iRow,iCol) {
                var rowData = $("#jqgrid-grid-subordinate-monthly").jqGrid('getRowData', rowid);
                rowData.yearTotal = Number(rowData.jan) + Number(rowData.feb) + Number(rowData.mar) + Number(rowData.apr) + Number(rowData.may) + Number(rowData.jun) + Number(rowData.jul) + Number(rowData.aug) + Number(rowData.sep) + Number(rowData.oct) + Number(rowData.nov) + Number(rowData.dec);
                $('#jqgrid-grid-subordinate-monthly').jqGrid('setRowData', rowid, rowData);
                calculateMonthlySummaryData();
                updateDayWiseTarget(rowid, name, val);
            },
            loadError: function (jqXHR, textStatus, errorThrown) {
                MessageRenderer.renderErrorText(jqXHR.responseText,'');
            }
        });

        var editableMonthIndex = ${editableMonthFrom};
        var $grid = $('#jqgrid-grid-subordinate-monthly');
        switch (editableMonthIndex){
            case 0:
                break;
            case 1:
                $grid.jqGrid('setColProp', 'jan', {editable:false});
                break;
            case 2:
                $grid.jqGrid('setColProp', 'jan', {editable:false});
                $grid.jqGrid('setColProp', 'feb', {editable:false});
                break;
            case 3:
                $grid.jqGrid('setColProp', 'jan', {editable:false});
                $grid.jqGrid('setColProp', 'feb', {editable:false});
                $grid.jqGrid('setColProp', 'mar', {editable:false});
                break;
            case 4:
                $grid.jqGrid('setColProp', 'jan', {editable:false});
                $grid.jqGrid('setColProp', 'feb', {editable:false});
                $grid.jqGrid('setColProp', 'mar', {editable:false});
                $grid.jqGrid('setColProp', 'apr', {editable:false});
                break;
            case 5:
                $grid.jqGrid('setColProp', 'jan', {editable:false});
                $grid.jqGrid('setColProp', 'feb', {editable:false});
                $grid.jqGrid('setColProp', 'mar', {editable:false});
                $grid.jqGrid('setColProp', 'apr', {editable:false});
                $grid.jqGrid('setColProp', 'may', {editable:false});
                break;
            case 6:
                $grid.jqGrid('setColProp', 'jan', {editable:false});
                $grid.jqGrid('setColProp', 'feb', {editable:false});
                $grid.jqGrid('setColProp', 'mar', {editable:false});
                $grid.jqGrid('setColProp', 'apr', {editable:false});
                $grid.jqGrid('setColProp', 'jun', {editable:false});
                break;
            case 7:
                $grid.jqGrid('setColProp', 'jan', {editable:false});
                $grid.jqGrid('setColProp', 'feb', {editable:false});
                $grid.jqGrid('setColProp', 'mar', {editable:false});
                $grid.jqGrid('setColProp', 'apr', {editable:false});
                $grid.jqGrid('setColProp', 'jun', {editable:false});
                $grid.jqGrid('setColProp', 'jul', {editable:false});
                break;
            case 8:
                $grid.jqGrid('setColProp', 'jan', {editable:false});
                $grid.jqGrid('setColProp', 'feb', {editable:false});
                $grid.jqGrid('setColProp', 'mar', {editable:false});
                $grid.jqGrid('setColProp', 'apr', {editable:false});
                $grid.jqGrid('setColProp', 'jun', {editable:false});
                $grid.jqGrid('setColProp', 'jul', {editable:false});
                $grid.jqGrid('setColProp', 'aug', {editable:false});
                break;
            case 9:
                $grid.jqGrid('setColProp', 'jan', {editable:false});
                $grid.jqGrid('setColProp', 'feb', {editable:false});
                $grid.jqGrid('setColProp', 'mar', {editable:false});
                $grid.jqGrid('setColProp', 'apr', {editable:false});
                $grid.jqGrid('setColProp', 'jun', {editable:false});
                $grid.jqGrid('setColProp', 'jul', {editable:false});
                $grid.jqGrid('setColProp', 'aug', {editable:false});
                $grid.jqGrid('setColProp', 'sep', {editable:false});
                break;
            case 10:
                $grid.jqGrid('setColProp', 'jan', {editable:false});
                $grid.jqGrid('setColProp', 'feb', {editable:false});
                $grid.jqGrid('setColProp', 'mar', {editable:false});
                $grid.jqGrid('setColProp', 'apr', {editable:false});
                $grid.jqGrid('setColProp', 'jun', {editable:false});
                $grid.jqGrid('setColProp', 'jul', {editable:false});
                $grid.jqGrid('setColProp', 'aug', {editable:false});
                $grid.jqGrid('setColProp', 'sep', {editable:false});
                $grid.jqGrid('setColProp', 'oct', {editable:false});
                break;
            case 11:
                $grid.jqGrid('setColProp', 'jan', {editable:false});
                $grid.jqGrid('setColProp', 'feb', {editable:false});
                $grid.jqGrid('setColProp', 'mar', {editable:false});
                $grid.jqGrid('setColProp', 'apr', {editable:false});
                $grid.jqGrid('setColProp', 'jun', {editable:false});
                $grid.jqGrid('setColProp', 'jul', {editable:false});
                $grid.jqGrid('setColProp', 'aug', {editable:false});
                $grid.jqGrid('setColProp', 'sep', {editable:false});
                $grid.jqGrid('setColProp', 'oct', {editable:false});
                $grid.jqGrid('setColProp', 'nov', {editable:false});
                break;
            case 12:
                $grid.jqGrid('setColProp', 'jan', {editable:false});
                $grid.jqGrid('setColProp', 'feb', {editable:false});
                $grid.jqGrid('setColProp', 'mar', {editable:false});
                $grid.jqGrid('setColProp', 'apr', {editable:false});
                $grid.jqGrid('setColProp', 'jun', {editable:false});
                $grid.jqGrid('setColProp', 'jul', {editable:false});
                $grid.jqGrid('setColProp', 'aug', {editable:false});
                $grid.jqGrid('setColProp', 'sep', {editable:false});
                $grid.jqGrid('setColProp', 'oct', {editable:false});
                $grid.jqGrid('setColProp', 'nov', {editable:false});
                $grid.jqGrid('setColProp', 'dec', {editable:false});
                break;
        }
        $("#reDistributeMonthWise-button").show();
        return true;
    }

    function redistributeMonthWiseTarget(){
        $("#jqgrid-grid-subordinate-monthly").jqGrid('clearGridData');
        var selectedSubordinates = jQuery("#jqgrid-grid-subordinate-employee").jqGrid('getGridParam','selarrrow');
        if(selectedSubordinates == ''){
            MessageRenderer.renderErrorText("No Employee/Sales Man selected");
            return false
        }

        var selectedSubordinateList = selectedSubordinates.toString().split(',');
        var selectedEmployeeCount = 0;
        for(var j = 0; j < selectedSubordinateList.length; j++){
            if(selectedSubordinateList[j]){
                selectedEmployeeCount++;
            }
        }
//        console.log(selectedEmployeeCount);
        var janTotal = Number($("#month_1").val())/selectedEmployeeCount;
        var febTotal = Number($("#month_2").val())/selectedEmployeeCount;
        var marTotal = Number($("#month_3").val())/selectedEmployeeCount;
        var aprTotal = Number($("#month_4").val())/selectedEmployeeCount;
        var mayTotal = Number($("#month_5").val())/selectedEmployeeCount;
        var junTotal = Number($("#month_6").val())/selectedEmployeeCount;
        var julTotal = Number($("#month_7").val())/selectedEmployeeCount;
        var augTotal = Number($("#month_8").val())/selectedEmployeeCount;
        var sepTotal = Number($("#month_9").val())/selectedEmployeeCount;
        var octTotal = Number($("#month_10").val())/selectedEmployeeCount;
        var novTotal = Number($("#month_11").val())/selectedEmployeeCount;
        var decTotal = Number($("#month_12").val())/selectedEmployeeCount;
        var yearTotal = Number(janTotal.toFixed(2)) + Number(febTotal.toFixed(2)) + Number(marTotal.toFixed(2)) + Number(aprTotal.toFixed(2)) + Number(mayTotal.toFixed(2)) + Number(junTotal.toFixed(2)) + Number(julTotal.toFixed(2)) + Number(augTotal.toFixed(2)) + Number(sepTotal.toFixed(2)) + Number(octTotal.toFixed(2)) + Number(novTotal.toFixed(2)) + Number(decTotal.toFixed(2));
        var calculatedJanTotal = 0;
        var calculatedFebTotal = 0;
        var calculatedMarTotal = 0;
        var calculatedAprTotal = 0;
        var calculatedMayTotal = 0;
        var calculatedJunTotal = 0;
        var calculatedJulTotal = 0;
        var calculatedAugTotal = 0;
        var calculatedSepTotal = 0;
        var calculatedOctTotal = 0;
        var calculatedNovTotal = 0;
        var calculatedDecTotal = 0;

        var adjustedJanTotal = 0;
        var adjustedFebTotal = 0;
        var adjustedMarTotal = 0;
        var adjustedAprTotal = 0;
        var adjustedMayTotal = 0;
        var adjustedJunTotal = 0;
        var adjustedJulTotal = 0;
        var adjustedAugTotal = 0;
        var adjustedSepTotal = 0;
        var adjustedOctTotal = 0;
        var adjustedNovTotal = 0;
        var adjustedDecTotal = 0;
        for(var i = 0; i < selectedSubordinateList.length ; i++){
            if(selectedSubordinateList[i]){
                var rowData = jqGridSubordinateEmployeeList.getRowData(selectedSubordinateList[i]);
                if(i < (selectedSubordinateList.length -1)){
                    calculatedJanTotal += Number(janTotal.toFixed(2));
                    calculatedFebTotal += Number(febTotal.toFixed(2));
                    calculatedMarTotal += Number(marTotal.toFixed(2));
                    calculatedAprTotal += Number(aprTotal.toFixed(2));
                    calculatedMayTotal += Number(mayTotal.toFixed(2));
                    calculatedJunTotal += Number(junTotal.toFixed(2));
                    calculatedJulTotal += Number(julTotal.toFixed(2));
                    calculatedAugTotal += Number(augTotal.toFixed(2));
                    calculatedSepTotal += Number(sepTotal.toFixed(2));
                    calculatedOctTotal += Number(octTotal.toFixed(2));
                    calculatedNovTotal += Number(novTotal.toFixed(2));
                    calculatedDecTotal += Number(decTotal.toFixed(2));

                    var newRowData = [
                        { 'id':selectedSubordinateList[i].toString(),'code': rowData.code, 'name': rowData.name, 'jan': janTotal.toFixed(2),'feb': febTotal.toFixed(2), 'mar':marTotal.toFixed(2), 'apr':aprTotal.toFixed(2), 'may':mayTotal.toFixed(2),
                            'jun':junTotal.toFixed(2), 'jul':julTotal.toFixed(2), 'aug':augTotal.toFixed(2), 'sep':sepTotal.toFixed(2), 'oct':octTotal.toFixed(2), 'nov':novTotal.toFixed(2),'dec':decTotal.toFixed(2), 'yearTotal': yearTotal
                        }
                    ];
                    jqGridMonthlyAssignedTargetList.addRowData(selectedSubordinateList[i].toString(), newRowData[0]);
                }else{
                    // Adjust fraction data for last employee
                    adjustedJanTotal = Number($("#month_1").val()) - calculatedJanTotal;
                    adjustedFebTotal = Number($("#month_2").val()) - calculatedFebTotal;
                    adjustedMarTotal = Number($("#month_3").val()) - calculatedMarTotal;
                    adjustedAprTotal = Number($("#month_4").val()) - calculatedAprTotal;
                    adjustedMayTotal = Number($("#month_5").val()) - calculatedMayTotal;
                    adjustedJunTotal = Number($("#month_6").val()) - calculatedJunTotal;
                    adjustedJulTotal = Number($("#month_7").val()) - calculatedJulTotal;
                    adjustedAugTotal = Number($("#month_8").val()) - calculatedAugTotal;
                    adjustedSepTotal = Number($("#month_9").val()) - calculatedSepTotal;
                    adjustedOctTotal = Number($("#month_10").val()) - calculatedOctTotal;
                    adjustedNovTotal = Number($("#month_11").val()) - calculatedNovTotal;
                    adjustedDecTotal = Number($("#month_12").val()) - calculatedDecTotal;
                    var adjustedYearTotal = adjustedJanTotal + adjustedFebTotal + adjustedMarTotal + adjustedAprTotal + adjustedMayTotal + adjustedJunTotal + adjustedJulTotal + adjustedAugTotal + adjustedSepTotal + adjustedOctTotal + adjustedNovTotal + adjustedDecTotal;

                    var newRowDataForAdjust = [
                        { 'id':selectedSubordinateList[i].toString(),'code': rowData.code, 'name': rowData.name, 'jan': adjustedJanTotal.toFixed(2),'feb': adjustedFebTotal.toFixed(2), 'mar':adjustedMarTotal.toFixed(2), 'apr':adjustedAprTotal.toFixed(2), 'may':adjustedMayTotal.toFixed(2),
                            'jun':adjustedJunTotal.toFixed(2), 'jul':adjustedJulTotal.toFixed(2), 'aug':adjustedAugTotal.toFixed(2), 'sep':adjustedSepTotal.toFixed(2), 'oct':adjustedOctTotal.toFixed(2), 'nov':adjustedNovTotal.toFixed(2),'dec':adjustedDecTotal.toFixed(2), 'yearTotal': adjustedYearTotal.toFixed(2)
                        }
                    ];
                    jqGridMonthlyAssignedTargetList.addRowData(selectedSubordinateList[i].toString(), newRowDataForAdjust[0]);

                }
            }
        }
        calculateMonthlySummaryData();
        distributeDayWiseTarget();
        isRedistributionNeeded = false;
        return true
    }


    function calculateMonthlySummaryData() {
        var $grid = $('#jqgrid-grid-subordinate-monthly');
        var janSum = $grid.jqGrid('getCol', 'jan', false, 'sum');
        $grid.jqGrid('footerData', 'set', { 'jan': janSum });
        var febSum = $grid.jqGrid('getCol', 'feb', false, 'sum');
        $grid.jqGrid('footerData', 'set', { 'feb': febSum });
        var marSum = $grid.jqGrid('getCol', 'mar', false, 'sum');
        $grid.jqGrid('footerData', 'set', { 'mar': marSum });
        var aprSum = $grid.jqGrid('getCol', 'apr', false, 'sum');
        $grid.jqGrid('footerData', 'set', { 'apr': aprSum });
        var maySum = $grid.jqGrid('getCol', 'may', false, 'sum');
        $grid.jqGrid('footerData', 'set', { 'may': maySum });
        var junSum = $grid.jqGrid('getCol', 'jun', false, 'sum');
        $grid.jqGrid('footerData', 'set', { 'jun': junSum });
        var julSum = $grid.jqGrid('getCol', 'jul', false, 'sum');
        $grid.jqGrid('footerData', 'set', { 'jul': julSum });
        var augSum = $grid.jqGrid('getCol', 'aug', false, 'sum');
        $grid.jqGrid('footerData', 'set', { 'aug': augSum });
        var sepSum = $grid.jqGrid('getCol', 'sep', false, 'sum');
        $grid.jqGrid('footerData', 'set', { 'sep': sepSum });
        var octSum = $grid.jqGrid('getCol', 'oct', false, 'sum');
        $grid.jqGrid('footerData', 'set', { 'oct': octSum });
        var novSum = $grid.jqGrid('getCol', 'nov', false, 'sum');
        $grid.jqGrid('footerData', 'set', { 'nov': novSum });
        var decSum = $grid.jqGrid('getCol', 'dec', false, 'sum');
        $grid.jqGrid('footerData', 'set', { 'dec': decSum });
        var yearSum = $grid.jqGrid('getCol', 'yearTotal', false, 'sum');
        $grid.jqGrid('footerData', 'set', { 'yearTotal': yearSum });
        $grid.jqGrid('footerData', 'set', { 'code': 'Month Total' });
    }
    function initiateDailyTargetTable(){
        var htmlData = "";
        htmlData += "<table class='simple-table-css' id='dateWise-target-table'>";
        htmlData += "<tr>";

        htmlData += "<td class='width100'>PIN</td>";
        htmlData += "<td class='width200'>Name</td>";
        htmlData += "<td class='width80'>Month</td>";
        for(var j=1; j<= 31; j++){
            htmlData += "<td class='width70'>Day " + j + "</td>";
        }
        htmlData += "<td class='width90'>Month Total</td>";
        htmlData += "</tr>";
        htmlData += "</table>";
        htmlData += "<div class='buttons' style='margin-left:10px;'>";
        htmlData += "<span class='button'><input type='button' name='reDistributeDayWise-button' id='reDistributeDayWise-button' class='ui-button ui-widget ui-state-default ui-corner-all' value='Reset Default' onclick='distributeDayWiseTarget();'/></span>"
        $("#datewise-target-div").html(htmlData);
    }
    function loadDayWiseTarget(){
        initiateDailyTargetTable();
        var rowDataList = $("#jqgrid-grid-subordinate-monthly").jqGrid('getRowData');
        for(var k= 0; k< rowDataList.length; k++){
            loadCustomerYearlyDailySaleTarget(rowDataList[k].id);
            var subOrdinateRow = jqGridSubordinateEmployeeList.getRowData(rowDataList[k].id);
            if(!subOrdinateRow.id){
                MessageRenderer.renderErrorText("You are no longer supervisor for Subordinate: [" + rowDataList[k].code + "] " + rowDataList[k].name)
                isRedistributionNeeded = true;
            }else{
                jqGridSubordinateEmployeeList.setSelection(rowDataList[k].id, true);
            }
        }
    }
    function loadCustomerYearlyDailySaleTarget(customerId){
        SubmissionLoader.showTo();
        $.ajax({
            mtype: "GET",
            url: "${request.contextPath}/${params.controller}/listCustomerDailySalesTarget?targetYear=" + $("#targetYear").val() + "&customerId=" + customerId,
            success: function (data) {
                for(var i = 0; i< data.length; i++){
                    var monthTotal = 0;
                    var disabled = "";
                    if(i < ${editableMonthFrom}){
                        disabled = " disabled='disabled' ";
                    }else{
                        disabled = "";
                    }
                    var monthWiseData = data[i];
                    var customerCode = monthWiseData[0].code;
                    var customerName = monthWiseData[0].name;
                    if(i > 0){
                        customerCode = "";
                        customerName = "";
                    }
                    var htmlData = "<tr>";
                    htmlData += "<td class='width100'>" + customerCode + "</td>";
                    htmlData += "<td class='width200'>" + customerName + "</td>";
                    htmlData += "<td class='width200'>" + monthListJScript[i] + "</td>";
                    for(var j=0; j < monthWiseData.length; j++){
                        var dayIndex = Number(j) + 1;
                        monthTotal += Number(monthWiseData[j].targetAmount.toFixed(2));
                        htmlData += "<td class='width70'><input type='text' class='width60 alin_right amount validate[required,custom[integer],min[0]]' name='" + monthListJScript[i].toLocaleLowerCase() + "_" + customerId + "_" + dayIndex + "' id='" + monthListJScript[i].toLocaleLowerCase() + "_" + customerId + "_" + dayIndex + "' value='" + monthWiseData[j].targetAmount.toFixed(2) + "'" + disabled + " onblur='changeDayWiseSummary(\"" + monthListJScript[i].toLocaleLowerCase() + "\"," + customerId + "," + monthWiseData.length + ")'/></td>";
                    }

                    for(var k = monthWiseData.length; k < 31; k++){
                        htmlData += "<td class='width70'><input type='text' class='width60 alin_right' name='" + monthListJScript[i].toLocaleLowerCase() + "_" + customerId + "_" + k+1 + "' id='" + monthListJScript[i].toLocaleLowerCase() + "_" + customerId + "_" + k+1 + "' value='' disabled='disabled'/></td>";
                    }
                    // Set Total Data
                    htmlData += "<td class='width70'><input type='text' class='width60 alin_right' name='" + monthListJScript[i].toLocaleLowerCase() + "_total_" + customerId + "' id='" + monthListJScript[i].toLocaleLowerCase() + "_total_" + customerId + "' value='" + monthTotal.toFixed(2) + "' disabled='disabled'/></td>";
                    htmlData += "</tr>";
                    $("#dateWise-target-table").append(htmlData);
                    htmlData = "";
                    changeAmountDataProperties();
                }
            },
            complete: function(){
                SubmissionLoader.hideFrom();
            },
            dataType: 'json'
        });
    }
    function distributeDayWiseTarget(){
        var rowData = jqGridMonthlyAssignedTargetList.getRowData();
        var length = rowData.length;
        var htmlData = "";
        if(length > 0){
            htmlData += "<table class='simple-table-css'>";
            htmlData += "<tr>";

            htmlData += "<td class='width100'>PIN</td>";
            htmlData += "<td class='width200'>Name</td>";
            htmlData += "<td class='width80'>Month</td>";
            for(var j=1; j<= 31; j++){
                htmlData += "<td class='width70'>Day " + j + "</td>";
            }
            htmlData += "<td class='width90'>Month Total</td>";
            htmlData += "</tr>";

            for (var i=0; i < length; ++i) {
                var employeePin = rowData[i].code;
                var employeeName = rowData[i].name;

                for(var l=0; l < 12; l++){
                    if(l > 0){
                        employeePin = "";
                        employeeName = "";
                    }
                    htmlData += "<tr>";
                    htmlData += "<td class='width100'>" + employeePin + "</td>";
                    htmlData += "<td class='width200'>" + employeeName + "</td>";
                    htmlData += "<td class='width80'>" + monthListJScript[l] + "</td>";
                    var monthData = 0.00;
                    var perDayValue = 0.00;
                    var adjustedLastDayValue = 0.00;
                    var disabled = "";
                    if(l < ${editableMonthFrom}){
                        disabled = " disabled='disabled' ";
                    }else{
                        disabled = "";
                    }
                    switch (l){
                        case 0:
                            monthData = rowData[i].jan;
                            perDayValue = monthData/31;

                            for(k=1; k<= 30; k++){
                                htmlData += "<td class='width70'><input type='text' class='width60 alin_right amount validate[required,custom[integer],min[0]]' name='jan_" + rowData[i].id + "_" + k + "' id='jan_" + rowData[i].id + "_" + k + "' value='" + perDayValue.toFixed(2) + "'" + disabled + " onblur='changeDayWiseSummary(\"jan\"," + rowData[i].id + ",31)'/></td>";
                            }
                            // Set Adjusted Data for last day
                            adjustedLastDayValue = Number(monthData) - Number(perDayValue.toFixed(2)*30);
                            htmlData += "<td class='width70'><input type='text' class='width60 alin_right amount validate[required,custom[integer],min[0]]' name='jan_" + rowData[i].id + "_31' id='jan_" + rowData[i].id + "_31' value='" + adjustedLastDayValue.toFixed(2) + "'" + disabled + " onblur='changeDayWiseSummary(\"jan\"," + rowData[i].id + ",31)'/></td>";
                            // Set Total Data
                            htmlData += "<td class='width70'><input type='text' class='width60 alin_right' name='jan_total_" + rowData[i].id + "' id='jan_total_" + rowData[i].id + "' value='" + monthData + "' disabled='disabled'/></td>";
                            break;
                        case 1:
                            monthData = rowData[i].feb;
                            perDayValue = monthData/daysInFeb;
                            for(k=1; k< daysInFeb; k++){
                                htmlData += "<td class='width70'><input type='text' class='width60 alin_right amount validate[required,custom[integer],min[0]]' name='feb_" + rowData[i].id + "_" + k + "' id='feb_" + rowData[i].id + "_" + k + "' value='" + perDayValue.toFixed(2) + "'" + disabled + " onblur='changeDayWiseSummary(\"feb\"," + rowData[i].id + "," + daysInFeb + ")'/></td>";
                            }
                            // Set Adjusted Data for last day
                            adjustedLastDayValue = Number(monthData) - Number(perDayValue.toFixed(2)*(daysInFeb -1));
                            htmlData += "<td class='width70'><input type='text' class='width60 alin_right amount validate[required,custom[integer],min[0]]' name='feb_" + rowData[i].id + "_" + daysInFeb + "' id='feb_" + rowData[i].id + "_" + daysInFeb + "' value='" + adjustedLastDayValue.toFixed(2) + "'" + disabled + " onblur='changeDayWiseSummary(\"feb\"," + rowData[i].id + "," + daysInFeb + ")'/></td>";
                            for(k=daysInFeb + 1; k<=31; k++){
                                htmlData += "<td class='width70'><input type='text' class='width60 alin_right' name='feb_" + rowData[i].id + "_" + k + "' id='feb_" + rowData[i].id + "_" + k + "' value='' disabled='disabled'/></td>";
                                k++;
                            }
                            // Set Total Data
                            htmlData += "<td class='width70'><input type='text' class='width60 alin_right' name='feb_total_" + rowData[i].id + "' id='feb_total_" + rowData[i].id + "' value='" + monthData + "' disabled='disabled'/></td>";
                            break;
                        case 2:
                            monthData = rowData[i].mar;
                            perDayValue = monthData/31;
                            for(k=1; k<= 30; k++){
                                htmlData += "<td class='width70'><input type='text' class='width60 alin_right amount validate[required,custom[integer],min[0]]' name='mar_" + rowData[i].id + "_" + k + "' id='mar_" + rowData[i].id + "_" + k + "' value='" + perDayValue.toFixed(2) + "'" + disabled + " onblur='changeDayWiseSummary(\"mar\"," + rowData[i].id + ",31)'/></td>";
                            }
                            // Set Adjusted Data for last day
                            adjustedLastDayValue = Number(monthData) - Number(perDayValue.toFixed(2)*30);
                            htmlData += "<td class='width70'><input type='text' class='width60 alin_right amount validate[required,custom[integer],min[0]]' name='mar_" + rowData[i].id + "_31' id='mar_" + rowData[i].id + "_31' value='" + adjustedLastDayValue.toFixed(2) + "'" + disabled + " onblur='changeDayWiseSummary(\"mar\"," + rowData[i].id + ",31)'/></td>";
                            // Set Total Data
                            htmlData += "<td class='width70'><input type='text' class='width60 alin_right' name='mar_total_" + rowData[i].id + "' id='mar_total_" + rowData[i].id + "' value='" + monthData + "' disabled='disabled'/></td>";
                            break;
                        case 3:
                            monthData = rowData[i].apr;
                            perDayValue = monthData/30;
                            for(k=1; k<= 29; k++){
                                htmlData += "<td class='width70'><input type='text' class='width60 alin_right amount validate[required,custom[integer],min[0]]' name='apr_" + rowData[i].id + "_" + k + "' id='apr_" + rowData[i].id + "_" + k + "' value='" + perDayValue.toFixed(2) + "'" + disabled + " onblur='changeDayWiseSummary(\"apr\"," + rowData[i].id + ",30)'/></td>";
                            }
                            // Set Adjusted Data on last day
                            adjustedLastDayValue = Number(monthData) - Number(perDayValue.toFixed(2)*29);
                            htmlData += "<td class='width70'><input type='text' class='width60 alin_right amount validate[required,custom[integer],min[0]]' name='apr_" + rowData[i].id + "_30' id='apr_" + rowData[i].id + "_30' value='" + adjustedLastDayValue.toFixed(2) + "'" + disabled + " onblur='changeDayWiseSummary(\"apr\"," + rowData[i].id + ",30)'/></td>";
                            // Set Total Data
                            htmlData += "<td class='width70'><input type='text' class='width60 alin_right' name='apr_" + rowData[i].id + "_31' id='apr_" + rowData[i].id + "_31' value='' disabled='disabled'/></td>";
                            htmlData += "<td class='width70'><input type='text' class='width60 alin_right' name='apr_total_" + rowData[i].id + "' id='apr_total_" + rowData[i].id + "' value='" + monthData + "' disabled='disabled'/></td>";
                            break;
                        case 4:
                            monthData = rowData[i].may;
                            perDayValue = monthData/31;
                            for(k=1; k<= 30; k++){
                                htmlData += "<td class='width70'><input type='text' class='width60 alin_right amount validate[required,custom[integer],min[0]]' name='may_" + rowData[i].id + "_" + k + "' id='may_" + rowData[i].id + "_" + k + "' value='" + perDayValue.toFixed(2) + "'" + disabled + " onblur='changeDayWiseSummary(\"may\"," + rowData[i].id + ",31)'/></td>";
                            }
                            // Set Adjusted Data for last day
                            adjustedLastDayValue = Number(monthData) - Number(perDayValue.toFixed(2)*30);
                            htmlData += "<td class='width70'><input type='text' class='width60 alin_right amount validate[required,custom[integer],min[0]]' name='may_" + rowData[i].id + "_31' id='may_" + rowData[i].id + "_31' value='" + adjustedLastDayValue.toFixed(2) + "'" + disabled + " onblur='changeDayWiseSummary(\"may\"," + rowData[i].id + ",31)'/></td>";
                            // Set Total Data
                            htmlData += "<td class='width70'><input type='text' class='width60 alin_right' name='may_total_" + rowData[i].id + "' id='may_total_" + rowData[i].id + "' value='" + monthData + "' disabled='disabled'/></td>";
                            break;
                        case 5:
                            monthData = rowData[i].jun;
                            perDayValue = monthData/30;
                            for(k=1; k<= 29; k++){
                                htmlData += "<td class='width70'><input type='text' class='width60 alin_right amount validate[required,custom[integer],min[0]]' name='jun_" + rowData[i].id + "_" + k + "' id='jun_" + rowData[i].id + "_" + k + "' value='" + perDayValue.toFixed(2) + "'" + disabled + " onblur='changeDayWiseSummary(\"jun\"," + rowData[i].id + ",30)'/></td>";
                            }
                            // Set Adjusted Data for last day
                            adjustedLastDayValue = Number(monthData) - Number(perDayValue.toFixed(2)*29);
                            htmlData += "<td class='width70'><input type='text' class='width60 alin_right amount validate[required,custom[integer],min[0]]' name='jun_" + rowData[i].id + "_30' id='jun_" + rowData[i].id + "_30' value='" + adjustedLastDayValue.toFixed(2) + "'" + disabled + " onblur='changeDayWiseSummary(\"jun\"," + rowData[i].id + ",30)'/></td>";
                            // Set Total Data
                            htmlData += "<td class='width70'><input type='text' class='width60 alin_right' name='jun_" + rowData[i].id + "_31' id='jun_" + rowData[i].id + "_31' value='' disabled='disabled'/></td>";
                            htmlData += "<td class='width70'><input type='text' class='width60 alin_right' name='jun_total_" + rowData[i].id + "' id='jun_total_" + rowData[i].id + "' value='" + monthData + "' disabled='disabled'/></td>";
                            break;
                        case 6:
                            monthData = rowData[i].jul;
                            perDayValue = monthData/31;
                            for(k=1; k<= 30; k++){
                                htmlData += "<td class='width70'><input type='text' class='width60 alin_right amount validate[required,custom[integer],min[0]]' name='jul_" + rowData[i].id + "_" + k + "' id='jul_" + rowData[i].id + "_" + k + "' value='" + perDayValue.toFixed(2) + "'" + disabled + " onblur='changeDayWiseSummary(\"jul\"," + rowData[i].id + ",31)'/></td>";
                            }
                            // Set Adjusted Data for last day
                            adjustedLastDayValue = Number(monthData) - Number(perDayValue.toFixed(2)*30);
                            htmlData += "<td class='width70'><input type='text' class='width60 alin_right amount validate[required,custom[integer],min[0]]' name='jul_" + rowData[i].id + "_31' id='jul_" + rowData[i].id + "_31' value='" + adjustedLastDayValue.toFixed(2) + "'" + disabled + " onblur='changeDayWiseSummary(\"jul\"," + rowData[i].id + ",31)'/></td>";
                            // Set Total Data
                            htmlData += "<td class='width70'><input type='text' class='width60 alin_right' name='jul_total_" + rowData[i].id + "' id='jul_total_" + rowData[i].id + "' value='" + monthData + "' disabled='disabled'/></td>";
                            break;
                        case 7:
                            monthData = rowData[i].aug;
                            perDayValue = monthData/31;
                            for(k=1; k<= 30; k++){
                                htmlData += "<td class='width70'><input type='text' class='width60 alin_right amount validate[required,custom[integer],min[0]]' name='aug_" + rowData[i].id + "_" + k + "' id='aug_" + rowData[i].id + "_" + k + "' value='" + perDayValue.toFixed(2) + "'" + disabled + " onblur='changeDayWiseSummary(\"aug\"," + rowData[i].id + ",31)'/></td>";
                            }
                            // Set Adjusted Data for last day
                            adjustedLastDayValue = Number(monthData) - Number(perDayValue.toFixed(2)*30);
                            htmlData += "<td class='width70'><input type='text' class='width60 alin_right amount validate[required,custom[integer],min[0]]' name='aug_" + rowData[i].id + "_31' id='aug_" + rowData[i].id + "_31' value='" + adjustedLastDayValue.toFixed(2) + "'" + disabled + " onblur='changeDayWiseSummary(\"aug\"," + rowData[i].id + ",31)'/></td>";
                            // Set Total Data
                            htmlData += "<td class='width70'><input type='text' class='width60 alin_right' name='aug_total_" + rowData[i].id + "' id='aug_total_" + rowData[i].id + "' value='" + monthData + "' disabled='disabled'/></td>";
                            break;
                        case 8:
                            monthData = rowData[i].sep;
                            perDayValue = monthData/30;
                            for(k=1; k<= 29; k++){
                                htmlData += "<td class='width70'><input type='text' class='width60 alin_right amount validate[required,custom[integer],min[0]]' name='sep_" + rowData[i].id + "_" + k + "' id='sep_" + rowData[i].id + "_" + k + "' value='" + perDayValue.toFixed(2) + "'" + disabled + " onblur='changeDayWiseSummary(\"sep\"," + rowData[i].id + ",30)'/></td>";
                            }
                            // Set Adjusted Data for last day
                            adjustedLastDayValue = Number(monthData) - Number(perDayValue.toFixed(2)*29);
                            htmlData += "<td class='width70'><input type='text' class='width60 alin_right amount validate[required,custom[integer],min[0]]' name='sep_" + rowData[i].id + "_30' id='sep_" + rowData[i].id + "_30' value='" + adjustedLastDayValue.toFixed(2) + "'" + disabled + " onblur='changeDayWiseSummary(\"sep\"," + rowData[i].id + ",30)'/></td>";
                            // Set Total Data
                            htmlData += "<td class='width70'><input type='text' class='width60 alin_right' name='sep_" + rowData[i].id + "_31' id='sep_" + rowData[i].id + "_31' value='' disabled='disabled'/></td>";
                            htmlData += "<td class='width70'><input type='text' class='width60 alin_right' name='sep_total_" + rowData[i].id + "' id='sep_total_" + rowData[i].id + "' value='" + monthData + "' disabled='disabled'/></td>";
                            break;
                        case 9:
                            monthData = rowData[i].oct;
                            perDayValue = monthData/31;
                            for(k=1; k<= 30; k++){
                                htmlData += "<td class='width70'><input type='text' class='width60 alin_right amount validate[required,custom[integer],min[0]]' name='oct_" + rowData[i].id + "_" + k + "' id='oct_" + rowData[i].id + "_" + k + "' value='" + perDayValue.toFixed(2) + "'" + disabled + " onblur='changeDayWiseSummary(\"oct\"," + rowData[i].id + ",31)'/></td>";
                            }
                            // Set Adjusted Data for last day
                            adjustedLastDayValue = Number(monthData) - Number(perDayValue.toFixed(2)*30);
                            htmlData += "<td class='width70'><input type='text' class='width60 alin_right amount validate[required,custom[integer],min[0]]' name='oct_" + rowData[i].id + "_31' id='oct_" + rowData[i].id + "_31' value='" + adjustedLastDayValue.toFixed(2) + "'" + disabled + " onblur='changeDayWiseSummary(\"oct\"," + rowData[i].id + ",31)'/></td>";
                            // Set Total Data
                            htmlData += "<td class='width70'><input type='text' class='width60 alin_right' name='oct_total_" + rowData[i].id + "' id='oct_total_" + rowData[i].id + "' value='" + monthData + "' disabled='disabled'/></td>";
                            break;
                        case 10:
                            monthData = rowData[i].nov;
                            perDayValue = monthData/30;
                            for(k=1; k<= 29; k++){
                                htmlData += "<td class='width100'><input type='text' class='width60 alin_right amount validate[required,custom[integer],min[0]]' name='nov_" + rowData[i].id + "_" + k + "' id='nov_" + rowData[i].id + "_" + k + "' value='" + perDayValue.toFixed(2) + "'" + disabled + " onblur='changeDayWiseSummary(\"nov\"," + rowData[i].id + ",30)'/></td>";
                            }
                            // Set Adjusted Data for last day
                            adjustedLastDayValue = Number(monthData) - Number(perDayValue.toFixed(2)*29);
                            htmlData += "<td class='width70'><input type='text' class='width60 alin_right amount validate[required,custom[integer],min[0]]' name='nov_" + rowData[i].id + "_30' id='nov_" + rowData[i].id + "_30' value='" + adjustedLastDayValue.toFixed(2) + "'" + disabled + " onblur='changeDayWiseSummary(\"nov\"," + rowData[i].id + ",30)'/></td>";
                            // Set Total Data
                            htmlData += "<td class='width70'><input type='text' class='width60 alin_right' name='nov_" + rowData[i].id + "_31' id='nov_" + rowData[i].id + "_31' value='' disabled='disabled'/></td>";
                            htmlData += "<td class='width70'><input type='text' class='width60 alin_right' name='nov_total_" + rowData[i].id + "' id='nov_total_" + rowData[i].id + "' value='" + monthData + "' disabled='disabled'/></td>";
                            break;
                        case 11:
                            monthData = rowData[i].dec;
                            perDayValue = monthData/31;
                            for(k=1; k<= 30; k++){
                                htmlData += "<td class='width100'><input type='text' class='width60 alin_right amount validate[required,custom[integer],min[0]]' name='dec_" + rowData[i].id + "_" + k + "' id='dec_" + rowData[i].id + "_" + k + "' value='" + perDayValue.toFixed(2) + "'" + disabled + " onblur='changeDayWiseSummary(\"dec\"," + rowData[i].id + ",31)'/></td>";
                            }
                            // Set Adjusted Data for last day
                            adjustedLastDayValue = Number(monthData) - Number(perDayValue.toFixed(2)*30);
                            htmlData += "<td class='width70'><input type='text' class='width60 alin_right amount validate[required,custom[integer],min[0]]' name='dec_" + rowData[i].id + "_31' id='dec_" + rowData[i].id + "_31' value='" + adjustedLastDayValue.toFixed(2) + "'" + disabled + " onblur='changeDayWiseSummary(\"dec\"," + rowData[i].id + ",31)'/></td>";
                            // Set Total Data
                            htmlData += "<td class='width70'><input type='text' class='width60 alin_right' name='dec_total_" + rowData[i].id + "' id='dec_total_" + rowData[i].id + "' value='" + monthData + "' disabled='disabled'/></td>";
                            break;
                    }

                    htmlData += "</tr>";
                }
            }

            htmlData += "</table>";
            htmlData += "<div class='buttons' style='margin-left:10px;'>";
            htmlData += "<span class='button'><input type='button' name='reDistributeDayWise-button' id='reDistributeDayWise-button' class='ui-button ui-widget ui-state-default ui-corner-all' value='Reset Default' onclick='distributeDayWiseTarget();'/></span>"
            $("#datewise-target-div").html(htmlData);
            changeAmountDataProperties();
        }
    }

    function changeAmountDataProperties(){
        $(".amount").format({precision: 2, allow_negative: false, autofix: true});
    }
    function updateDayWiseTarget(employeeId, monthName, updatedValue){
        var updatedMonthValue = 0;
        var m = 1;
        switch (monthName){
            case "jan":
                updatedMonthValue = updatedValue/31;
                for(m=1; m<= 31; m++){
                    $("#jan_" + employeeId + "_" + m).val(updatedMonthValue.toFixed(2));
                }
                $("#jan_total_" + employeeId).val(updatedValue);
                break;
            case "feb":
                updatedMonthValue = updatedValue/daysInFeb;
                for(m=1; m<= daysInFeb; m++){
                    $("#feb_" + employeeId + "_" + m).val(updatedMonthValue.toFixed(2));
                }
                $("#feb_total_" + employeeId).val(updatedValue);
                break;
            case "mar":
                updatedMonthValue = updatedValue/31;
                for(m=1; m<= 31; m++){
                    $("#mar_" + employeeId + "_" + m).val(updatedMonthValue.toFixed(2));
                }
                $("#mar_total_" + employeeId).val(updatedValue);
                break;
            case "apr":
                updatedMonthValue = updatedValue/30;
                for(m=1; m<= 30; m++){
                    $("#apr_" + employeeId + "_" + m).val(updatedMonthValue.toFixed(2));
                }
                $("#apr_total_" + employeeId).val(updatedValue);
                break;
            case "may":
                updatedMonthValue = updatedValue/31;
                for(m=1; m<= 31; m++){
                    $("#may_" + employeeId + "_" + m).val(updatedMonthValue.toFixed(2));
                }
                $("#may_total_" + employeeId).val(updatedValue);
                break;
            case "jun":
                updatedMonthValue = updatedValue/30;
                for(m=1; m<= 30; m++){
                    $("#jun_" + employeeId + "_" + m).val(updatedMonthValue.toFixed(2));
                }
                $("#jun_total_" + employeeId).val(updatedValue);
                break;
            case "jul":
                updatedMonthValue = updatedValue/31;
                for(m=1; m<= 31; m++){
                    $("#jul_" + employeeId + "_" + m).val(updatedMonthValue.toFixed(2));
                }
                $("#jul_total_" + employeeId).val(updatedValue);
                break;
            case "aug":
                updatedMonthValue = updatedValue/31;
                for(m=1; m<= 31; m++){
                    $("#aug_" + employeeId + "_" + m).val(updatedMonthValue.toFixed(2));
                }
                $("#aug_total_" + employeeId).val(updatedValue);
                break;
            case "sep":
                updatedMonthValue = updatedValue/30;
                for(m=1; m<= 30; m++){
                    $("#sep_" + employeeId + "_" + m).val(updatedMonthValue.toFixed(2));
                }
                $("#sep_total_" + employeeId).val(updatedValue);
                break;
            case "oct":
                updatedMonthValue = updatedValue/31;
                for(m=1; m<= 31; m++){
                    $("#oct_" + employeeId + "_" + m).val(updatedMonthValue.toFixed(2));
                }
                $("#oct_total_" + employeeId).val(updatedValue);
                break;
            case "nov":
                updatedMonthValue = updatedValue/30;
                for(m=1; m<= 30; m++){
                    $("#nov_" + employeeId + "_" + m).val(updatedMonthValue.toFixed(2));
                }
                $("#nov_total_" + employeeId).val(updatedValue);
                break;
            case "dec":
                updatedMonthValue = updatedValue/31;
                for(m=1; m<= 31; m++){
                    $("#dec_" + employeeId + "_" + m).val(updatedMonthValue.toFixed(2));
                }
                $("#dec_total_" + employeeId).val(updatedValue);
                break;
        }

    }
    function changeDayWiseSummary(month, employeeId, noOfDay){
        var newTotalMonthData = 0;
        for(var i=1; i <= noOfDay; i++){
            newTotalMonthData += Number($("#" + month + "_" + employeeId + "_" + i).val());
        }
        $("#" + month + "_total_" + employeeId).val(newTotalMonthData.toFixed(2));
    }
    function loadUnloadSM(element){
        if (element.checked) {
            SubmissionLoader.showTo();
            $.ajax({
                type: "POST",
                url: "${request.contextPath}/${params.controller}/listSalesManForTargetSetup",
                success: function (data) {
                    for (var i = 0; i < data.length; i++) {
                        var newRowDataForSM = [
                            { 'id':data[i].id,'code': data[i].code, 'name': data[i].name, 'department': data[i].salesChannel,'designation': data[i].category, 'isEmployee': '0'
                            }
                        ];
                        jqGridSubordinateEmployeeList.addRowData(data[i].id, newRowDataForSM[0]);
                    }
                },
                complete: function(){
                    SubmissionLoader.hideFrom();
                },
                dataType: 'json'
            });
        }else{
            var isSalesManRemoved = false;
            var gridData = $("#jqgrid-grid-subordinate-employee").jqGrid('getRowData');
            for(var i= gridData.length -1; i >= 0; i--){
                var rowData = gridData[i];
                if(rowData.isEmployee == "0"){
                    $('#jqgrid-grid-subordinate-employee').jqGrid('delRowData',rowData.id);
                    isSalesManRemoved = true;
                }
            }
            if(isSalesManRemoved){
                redistributeMonthWiseTarget();
            }
        }
    }

    function executePreConditionMonthlySalesTargetByAmount() {
        // trim field vales before process.
        trim_form();
        if (!$("#gFormMonthlySalesTargetByAmount").validationEngine('validate')) {
            return false;
        }
        return true;
    }
    function executeAjaxMonthlySalesTargetByAmount() {
        if (isRedistributionNeeded) {
            MessageRenderer.renderErrorText("Please Re Distribute Month-wise Target");
            return
        }
        var data = [];
        $('#jqgrid-grid-subordinate-monthly').jqGrid("editCell", 0, 0, false);
        var gd = $("#jqgrid-grid-subordinate-monthly").jqGrid('getRowData');
        var length = gd.length;
        if(length <= 0){
            MessageRenderer.renderErrorText("Please Distribute Month-wise Target");
            return
        }
        data.push({'name':'targetYear', 'value': $("#targetYear").val()});
        data.push({'name':'employeeCount', 'value': length});
        var selfMonthWiseTarget = 0.0;
        var selfYearlyTarget  = 0.0;
        var selfAnnualTarget = Number($("#yearlyTarget").val());
        data.push({'name':'yearlyTarget', 'value': selfAnnualTarget.toFixed(2)});
        for(var j = 1; j <= 12; j++){
            selfMonthWiseTarget = Number($("#month_" + j).val());
            selfYearlyTarget += selfMonthWiseTarget;
            var keyIndex = j-1;
            data.push({'name':'selfMonthWiseTarget_' + keyIndex.toString() + '_targetAmount', 'value': selfMonthWiseTarget.toFixed(2)});
        }
        if(selfYearlyTarget.toFixed(2) != selfAnnualTarget.toFixed(2)){
            MessageRenderer.renderErrorText("Your Total Target=" + selfYearlyTarget.toFixed(2) + " which is not equal to Subordinate Sum:" + selfAnnualTarget.toFixed(2));
            return
        }

        var $grid = $('#jqgrid-grid-subordinate-monthly');
        var janSum = $grid.jqGrid('getCol', 'jan', false, 'sum');
        var selfJanTarget = Number($("#month_1").val());
        if(janSum.toFixed(2) != selfJanTarget.toFixed(2)){
            MessageRenderer.renderErrorText("Your January Target=" + selfJanTarget + " which is not equal to Subordinate Sum:" + janSum);
            return
        }
        var febSum = $grid.jqGrid('getCol', 'feb', false, 'sum');
        var selfFebTarget = Number($("#month_2").val());
        if(febSum.toFixed(2) != selfFebTarget.toFixed(2)){
            MessageRenderer.renderErrorText("Your February Target=" + selfFebTarget + " which is not equal to Subordinate Sum:" + febSum);
            return
        }
        var marSum = $grid.jqGrid('getCol', 'mar', false, 'sum');
        var selfMarTarget = Number($("#month_3").val());
        if(marSum.toFixed(2) != selfMarTarget.toFixed(2)){
            MessageRenderer.renderErrorText("Your March Target=" + selfMarTarget + " which is not equal to Subordinate Sum:" + marSum);
            return
        }
        var aprSum = $grid.jqGrid('getCol', 'apr', false, 'sum');
        var selfAprTarget = Number($("#month_4").val());
        if(aprSum.toFixed(2) != selfAprTarget.toFixed(2)){
            MessageRenderer.renderErrorText("Your April Target=" + selfAprTarget + " which is not equal to Subordinate Sum:" + aprSum);
            return
        }
        var maySum = $grid.jqGrid('getCol', 'may', false, 'sum');
        var selfMayTarget = Number($("#month_5").val());
        if(maySum.toFixed(2) != selfMayTarget.toFixed(2)){
            MessageRenderer.renderErrorText("Your May Target=" + selfMayTarget + " which is not equal to Subordinate Sum:" + maySum);
            return
        }
        var junSum = $grid.jqGrid('getCol', 'jun', false, 'sum');
        var selfJunTarget = Number($("#month_6").val());
        if(junSum.toFixed(2) != selfJunTarget.toFixed(2)){
            MessageRenderer.renderErrorText("Your June Target=" + selfJunTarget + " which is not equal to Subordinate Sum:" + junSum);
            return
        }
        var julSum = $grid.jqGrid('getCol', 'jul', false, 'sum');
        var selfJulTarget = Number($("#month_7").val());
        if(julSum.toFixed(2) != selfJulTarget.toFixed(2)){
            MessageRenderer.renderErrorText("Your July Target=" + selfJulTarget + " which is not equal to Subordinate Sum:" + julSum);
            return
        }
        var augSum = $grid.jqGrid('getCol', 'aug', false, 'sum');
        var selfAugTarget = Number($("#month_8").val());
        if(augSum.toFixed(2) != selfAugTarget.toFixed(2)){
            MessageRenderer.renderErrorText("Your August Target=" + selfAugTarget + " which is not equal to Subordinate Sum:" + augSum);
            return
        }
        var sepSum = $grid.jqGrid('getCol', 'sep', false, 'sum');
        var selfSepTarget = Number($("#month_9").val());
        if(sepSum.toFixed(2) != selfSepTarget.toFixed(2)){
            MessageRenderer.renderErrorText("Your September Target=" + selfSepTarget + " which is not equal to Subordinate Sum:" + sepSum);
            return
        }
        var octSum = $grid.jqGrid('getCol', 'oct', false, 'sum');
        var selfOctTarget = Number($("#month_10").val());
        if(octSum.toFixed(2) != selfOctTarget.toFixed(2)){
            MessageRenderer.renderErrorText("Your October Target=" + selfOctTarget + " which is not equal to Subordinate Sum:" + octSum);
            return
        }
        var novSum = $grid.jqGrid('getCol', 'nov', false, 'sum');
        var selfNovTarget = Number($("#month_11").val());
        if(novSum.toFixed(2) != selfNovTarget.toFixed(2)){
            MessageRenderer.renderErrorText("Your November Target=" + selfNovTarget + " which is not equal to Subordinate Sum:" + novSum);
            return
        }
        var decSum = $grid.jqGrid('getCol', 'dec', false, 'sum');
        var selfDecTarget = Number($("#month_12").val());
        if(decSum.toFixed(2) != selfDecTarget.toFixed(2)){
            MessageRenderer.renderErrorText("Your December Target=" + selfDecTarget + " which is not equal to Subordinate Sum:" + decSum);
            return
        }

        for (var i=0; i < length; i++) {
            data.push({'name':'subordinates_' + i + '_employeeId', 'value': gd[i].id});
            data.push({'name':'subordinates_' + i + '_month_0_targetAmount', 'value': gd[i].jan});
            data.push({'name':'subordinates_' + i + '_month_1_targetAmount', 'value': gd[i].feb});
            data.push({'name':'subordinates_' + i + '_month_2_targetAmount', 'value': gd[i].mar});
            data.push({'name':'subordinates_' + i + '_month_3_targetAmount', 'value': gd[i].apr});
            data.push({'name':'subordinates_' + i + '_month_4_targetAmount', 'value': gd[i].may});
            data.push({'name':'subordinates_' + i + '_month_5_targetAmount', 'value': gd[i].jun});
            data.push({'name':'subordinates_' + i + '_month_6_targetAmount', 'value': gd[i].jul});
            data.push({'name':'subordinates_' + i + '_month_7_targetAmount', 'value': gd[i].aug});
            data.push({'name':'subordinates_' + i + '_month_8_targetAmount', 'value': gd[i].sep});
            data.push({'name':'subordinates_' + i + '_month_9_targetAmount', 'value': gd[i].oct});
            data.push({'name':'subordinates_' + i + '_month_10_targetAmount', 'value': gd[i].nov});
            data.push({'name':'subordinates_' + i + '_month_11_targetAmount', 'value': gd[i].dec});
            var  k = 1;
            var janTotal = Number($("#jan_total_" + gd[i].id).val());
            var janCalculatedTotal = 0.0;
            for(k = 1; k <= 31; k++){
                janCalculatedTotal += Number($("#jan_" + gd[i].id + "_" + k).val());
                data.push({'name':'subordinates_' + i + '_month_0_'+ k + '_targetAmount', 'value': Number($("#jan_" + gd[i].id + "_" + k).val())});
            }
            if(janTotal.toFixed(2) != janCalculatedTotal.toFixed(2)){
                MessageRenderer.renderErrorText("Day wise January Total mismatch for employee:" + gd[i].name);
                return
            }
            var febTotal = Number($("#feb_total_" + gd[i].id).val());
            var febCalculatedTotal = 0.0;
            for(k = 1; k <= ${daysInFeb}; k++){
                febCalculatedTotal += Number($("#feb_" + gd[i].id + "_" + k).val());
                data.push({'name':'subordinates_' + i + '_month_1_'+ k + '_targetAmount', 'value': Number($("#feb_" + gd[i].id + "_" + k).val())});
            }
            if(febTotal.toFixed(2) != febCalculatedTotal.toFixed(2)){
                MessageRenderer.renderErrorText("Day wise February Total mismatch for employee:" + gd[i].name);
                return
            }
            var marTotal = Number($("#mar_total_" + gd[i].id).val());
            var marCalculatedTotal = 0.0;
            for(k = 1; k <= 31; k++){
                marCalculatedTotal += Number($("#mar_" + gd[i].id + "_" + k).val());
                data.push({'name':'subordinates_' + i + '_month_2_'+ k + '_targetAmount', 'value': Number($("#mar_" + gd[i].id + "_" + k).val())});
            }
            if(marTotal.toFixed(2) != marCalculatedTotal.toFixed(2)){
                MessageRenderer.renderErrorText("Day wise March Total mismatch for employee:" + gd[i].name);
                return
            }
            var aprTotal = Number($("#apr_total_" + gd[i].id).val());
            var aprCalculatedTotal = 0.0;
            for(k = 1; k <= 30; k++){
                aprCalculatedTotal += Number($("#apr_" + gd[i].id + "_" + k).val());
                data.push({'name':'subordinates_' + i + '_month_3_'+ k + '_targetAmount', 'value': Number($("#apr_" + gd[i].id + "_" + k).val())});
            }
            if(aprTotal.toFixed(2) != aprCalculatedTotal.toFixed(2)){
                MessageRenderer.renderErrorText("Day wise April Total mismatch for employee:" + gd[i].name);
                return
            }
            var mayTotal = Number($("#may_total_" + gd[i].id).val());
            var mayCalculatedTotal = 0.0;
            for(k = 1; k <= 31; k++){
                mayCalculatedTotal += Number($("#may_" + gd[i].id + "_" + k).val());
                data.push({'name':'subordinates_' + i + '_month_4_'+ k + '_targetAmount', 'value': Number($("#may_" + gd[i].id + "_" + k).val())});
            }
            if(mayTotal.toFixed(2) != mayCalculatedTotal.toFixed(2)){
                MessageRenderer.renderErrorText("Day wise May Total mismatch for employee:" + gd[i].name);
                return
            }
            var junTotal = Number($("#jun_total_" + gd[i].id).val());
            var junCalculatedTotal = 0.0;
            for(k = 1; k <= 30; k++){
                junCalculatedTotal += Number($("#jun_" + gd[i].id + "_" + k).val());
                data.push({'name':'subordinates_' + i + '_month_5_'+ k + '_targetAmount', 'value': Number($("#jun_" + gd[i].id + "_" + k).val())});
            }
            if(junTotal.toFixed(2) != junCalculatedTotal.toFixed(2)){
                MessageRenderer.renderErrorText("Day wise June Total mismatch for employee: " + gd[i].name);
                return
            }
            var julTotal = Number($("#jul_total_" + gd[i].id).val());
            var julCalculatedTotal = 0.0;
            for(k = 1; k <= 31; k++){
                julCalculatedTotal += Number($("#jul_" + gd[i].id + "_" + k).val());
                data.push({'name':'subordinates_' + i + '_month_6_'+ k + '_targetAmount', 'value': Number($("#jul_" + gd[i].id + "_" + k).val())});
            }
            if(julTotal.toFixed(2) != julCalculatedTotal.toFixed(2)){
                MessageRenderer.renderErrorText("Day wise July Total mismatch for employee:" + gd[i].name);
                return
            }
            var augTotal = Number($("#aug_total_" + gd[i].id).val());
            var augCalculatedTotal = 0.0;
            for(k = 1; k <= 31; k++){
                augCalculatedTotal += Number($("#aug_" + gd[i].id + "_" + k).val());
                data.push({'name':'subordinates_' + i + '_month_7_'+ k + '_targetAmount', 'value': Number($("#aug_" + gd[i].id + "_" + k).val())});
            }
            if(augTotal.toFixed(2) != augCalculatedTotal.toFixed(2)){
                MessageRenderer.renderErrorText("Day wise August Total mismatch for employee:" + gd[i].name);
                return
            }
            var sepTotal = Number($("#sep_total_" + gd[i].id).val());
            var sepCalculatedTotal = 0.0;
            for(k = 1; k <= 30; k++){
                sepCalculatedTotal += Number($("#sep_" + gd[i].id + "_" + k).val());
                data.push({'name':'subordinates_' + i + '_month_8_'+ k + '_targetAmount', 'value': Number($("#sep_" + gd[i].id + "_" + k).val())});
            }
            if(sepTotal.toFixed(2) != sepCalculatedTotal.toFixed(2)){
                MessageRenderer.renderErrorText("Day wise September Total mismatch for employee:" + gd[i].name);
                return
            }
            var octTotal = Number($("#oct_total_" + gd[i].id).val());
            var octCalculatedTotal = 0.0;
            for(k = 1; k <= 31; k++){
                octCalculatedTotal += Number($("#oct_" + gd[i].id + "_" + k).val());
                data.push({'name':'subordinates_' + i + '_month_9_'+ k + '_targetAmount', 'value': Number($("#oct_" + gd[i].id + "_" + k).val())});
            }
            if(octTotal.toFixed(2) != octCalculatedTotal.toFixed(2)){
                MessageRenderer.renderErrorText("Day wise October Total mismatch for employee:" + gd[i].name);
                return
            }
            var novTotal = Number($("#nov_total_" + gd[i].id).val());
            var novCalculatedTotal = 0.0;
            for(k = 1; k <= 30; k++){
                novCalculatedTotal += Number($("#nov_" + gd[i].id + "_" + k).val());
                data.push({'name':'subordinates_' + i + '_month_10_'+ k + '_targetAmount', 'value': Number($("#nov_" + gd[i].id + "_" + k).val())});
            }
            if(novTotal.toFixed(2) != novCalculatedTotal.toFixed(2)){
                MessageRenderer.renderErrorText("Day wise November Total mismatch for employee:" + gd[i].name);
                return
            }
            var decTotal = Number($("#dec_total_" + gd[i].id).val());
            var decCalculatedTotal = 0.0;
            for(k = 1; k <= 31; k++){
                decCalculatedTotal += Number($("#dec_" + gd[i].id + "_" + k).val());
                data.push({'name':'subordinates_' + i + '_month_11_'+ k + '_targetAmount', 'value': Number($("#dec_" + gd[i].id + "_" + k).val())});
            }
            if(decTotal.toFixed(2) != decCalculatedTotal.toFixed(2)){
                MessageRenderer.renderErrorText("Day wise December Total mismatch for employee:" + gd[i].name);
                return
            }
        }
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: data,
            url: "${request.contextPath}/${params.controller}/update",
            success: function (data, textStatus) {
                executePostConditionMonthlySalesTargetByAmount(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#monthWiseTarget").html("");
                    $("#yearlyTarget").val("0.00");
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
    }
    function executePostConditionMonthlySalesTargetByAmount(result) {
        if (result.type == 1) {
            $("#monthWiseTarget").html("");
            $("#yearlyTarget").val("0.00");
        }
        MessageRenderer.render(result);
    }

</script>
<div>
    <label>Monthly Target:</label>
    <br/>
    <br/>
    <table class="simple-table-css" style="width: 800px;">
        <tr>
            <g:each var="i" in="${ (0..<12) }">
                <td class="width80">${monthList[i]}</td>
            </g:each>
        </tr>

        <tr>
            <g:each in="${monthWiseYearlyTargetList}" var="monthWiseYearlyTarget" status="i">
                <td class="width80">
                    <g:if test="${i < editableMonthFrom}">
                        <g:textField class="width80 alin_right amount"
                                     name="month_${monthWiseYearlyTarget.targetMonth}"
                                     id="month_${monthWiseYearlyTarget.targetMonth}" value="${monthWiseYearlyTarget.targetAmount}"
                                     maxlength="9" disabled="disabled"/>
                    </g:if>
                    <g:else>
                        <g:textField class="width80 alin_right amount validate[required,custom[integer],min[0]]"
                                     name="month_${monthWiseYearlyTarget.targetMonth}"
                                     id="month_${monthWiseYearlyTarget.targetMonth}" value="${monthWiseYearlyTarget.targetAmount}"
                                     maxlength="9"/>
                    </g:else>

                </td>
            </g:each>
        </tr>
    </table>
</div>
<div class="buttons" style="margin-left:10px;">
    <span class="button"><input type="button" name="create-button" id="reset-button-monthlySalesTargetByAmount"
                                class="ui-button ui-widget ui-state-default ui-corner-all"
                                value="Reset Default"
                                onclick="resetMonthWiseTarget();"/>
        <g:if test="${yearlySalesTargetByAmount.isSalesManIncluded}">
            <input type="checkbox" name="salesManSelection" value="includeSM" checked="checkbox" onchange="loadUnloadSM(this)"> <b>Include SM</b>
        </g:if>
        <g:else>
            <input type="checkbox" name="salesManSelection" value="includeSM" onchange="loadUnloadSM(this)"> <b>Include SM</b>
        </g:else>
    </span>

</div>
<div class="jqgrid-container">
    <table id="jqgrid-grid-subordinate-employee"></table>
</div>
<div class="buttons" style="margin-left:10px;">
    <span class="button"><input type="button" name="distributeMonthWise-button" id="distributeMonthWise-button"
                                class="ui-button ui-widget ui-state-default ui-corner-all"
                                value="Distribute Month-wise Target"
                                onclick="redistributeMonthWiseTarget();"/>
    </span>
</div>
<div id="monthly-distribution-div">
    <div class="jqgrid-container">
        <table id="jqgrid-grid-subordinate-monthly"></table>
    </div>
</div>
<div class="buttons" style="margin-left:10px;">
    <span class="button"><input type="button" name="reDistributeMonthWise-button" id="reDistributeMonthWise-button"
                                class="ui-button ui-widget ui-state-default ui-corner-all"
                                value="Reset Default"
                                onclick="redistributeMonthWiseTarget();"/>
    </span>
</div>
<div id="datewise-target-div">

</div>
<div class="buttons" style="margin-left:10px;">
    <span class="button"><input type="button" name="create-button-monthlySalesTargetByAmount" id="create-button-monthlySalesTargetByAmount"
                                class="ui-button ui-widget ui-state-default ui-corner-all"
                                value="Submit Target"
                                onclick="executeAjaxMonthlySalesTargetByAmount();"/></span>

</div>