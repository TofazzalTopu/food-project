<%@ page import="com.bits.bdfp.util.ApplicationConstants" %>
<script type="text/javascript">
    var monthListJScript = ['Jan','Feb','Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
    var jqGridSubordinateEmployeeList = null;
    var jqGridMonthlyAssignedTargetList = null;
    var daysInFeb = ${daysInFeb};
    $(document).ready(function () {
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }
        if(${editableMonthFrom}){
            for(var i = 0; i <= ${editableMonthFrom}; i++) {
                $(".month_" + i).attr("disabled", true);
            }
        }
//        resetMonthWiseTarget();
        $("#reDistributeMonthWise-button").hide();
        $(".quantity").format({precision: 0, allow_negative: false, autofix: true});
        jqGridSubordinateEmployeeList = $("#jqgrid-grid-subordinate-employee").jqGrid({
            url:"${request.contextPath}/${params.controller}/listSubordinateEmployees",
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
                {name:'name', index:'name', width:350, align:'left'},
                {name:'department', index:'department', width:350, align:'left', sortable: false},
                {name:'designation', index:'designation', width:350, align:'left', sortable: false},
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
            },
            onSelectRow: function(rowid, status) {
            },
            loadError: function (jqXHR, textStatus, errorThrown) {
                MessageRenderer.renderErrorText(jqXHR.responseText,'');
            }
        });

    });
    function resetMonthWiseTarget() {
        $(".yearlyQuantity").each(function(){
            var yearlyDataId = this.id;
            var yearlyDataIdParts = yearlyDataId.split('_');
            var productId = yearlyDataIdParts[1];
            var yearlyTarget = Number(this.value);
            var nonEditableMonthlyTarget = 0;
            for(var i = 1; i <= ${editableMonthFrom}; i++) {
                nonEditableMonthlyTarget += Number($("#month_" + productId + "_" + i).val());
            }
            var perMonthTarget = Math.floor((yearlyTarget - nonEditableMonthlyTarget)/(12-${editableMonthFrom}) + 0.5);
            var totalMonthlyTarget = 0;
            for(var j = ${editableMonthFrom} + 1; j < 12; j++) {
                totalMonthlyTarget += Number(perMonthTarget.toFixed(0));
                $("#month_" + productId + "_" + j).val(perMonthTarget.toFixed(0));
            }
            var lastMonthTarget = (yearlyTarget - nonEditableMonthlyTarget) - totalMonthlyTarget;
            $("#month_" + productId + "_12").val(lastMonthTarget.toFixed(0));
        });
    }
    function distributeMonthWiseTarget(){
        $("#jqgrid-grid-subordinate-monthly").jqGrid('clearGridData');
        var selectedSubordinates = jQuery("#jqgrid-grid-subordinate-employee").jqGrid('getGridParam','selarrrow');
        if(selectedSubordinates == ''){
            MessageRenderer.renderErrorText("No Employee/Sales Man selected");
            return false
        }
        jqGridMonthlyAssignedTargetList = $("#jqgrid-grid-subordinate-monthly").jqGrid({
            url:"",
            datatype: "local",
            colNames:[
                'ID',
                'Product ID',
                'Product Code',
                'Product Name',
                'Employee ID',
                'Employee PIN',
                'Employee Name',
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
                {name:'id', index:'id', width:0, hidden: true},
                {name:'productId', index:'productId', hidden: true},
                {name:'productCode', index:'productCode', width:150, align:'left'},
                {name:'productName', index:'productName', width:200, align:'left'},
                {name:'employeeId', index:'employeeId', hidden: true},
                {name:'employeeCode', index:'employeeCode', width:80, align:'left'},
                {name:'employeeName', index:'employeeName', width:170, align:'left'},
                {name:'jan', index:'jan', width:40, align:'right', sorttype:'number', formatter:"number", editable: true, editrules:{number:true}, formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 0}},
                {name:'feb', index:'feb', width:40, align:'right', sorttype:'number', formatter:"number", editable: true, editrules:{number:true}, formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 0}},
                {name:'mar', index:'mar', width:40, align:'right', sorttype:'number', formatter:"number", editable: true, editrules:{number:true}, formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 0}},
                {name:'apr', index:'apr', width:40, align:'right', sorttype:'number', formatter:"number", editable: true, editrules:{number:true}, formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 0}},
                {name:'may', index:'may', width:40, align:'right', sorttype:'number', formatter:"number", editable: true, editrules:{number:true}, formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 0}},
                {name:'jun', index:'jun', width:40, align:'right', sorttype:'number', formatter:"number", editable: true, editrules:{number:true}, formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 0}},
                {name:'jul', index:'jul', width:40, align:'right', sorttype:'number', formatter:"number", editable: true, editrules:{number:true}, formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 0}},
                {name:'aug', index:'aug', width:40, align:'right', sorttype:'number', formatter:"number", editable: true, editrules:{number:true}, formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 0}},
                {name:'sep', index:'sep', width:40, align:'right', sorttype:'number', formatter:"number", editable: true, editrules:{number:true}, formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 0}},
                {name:'oct', index:'oct', width:40, align:'right', sorttype:'number', formatter:"number", editable: true, editrules:{number:true}, formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 0}},
                {name:'nov', index:'nov', width:40, align:'right', sorttype:'number', formatter:"number", editable: true, editrules:{number:true}, formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 0}},
                {name:'dec', index:'dec', width:40, align:'right', sorttype:'number', formatter:"number", editable: true, editrules:{number:true}, formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 0}},
                {name:'yearTotal', index:'yearTotal', width:50, align:'right', sorttype:'number', formatter:"number", formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 0}}
            ],
            rowNum: -1,
            viewrecords:true,
            caption:"Product wise, Employee Wise, Month-wise Target Grid",
            autowidth:false,
            shrinkToFit: true,
            height:true,
            scrollOffset:0,
            gridview:true,
            cellEdit: true,
            cellsubmit: 'clientArray',
            cellurl: null,

            gridComplete: function() {
            },
            loadComplete: function (data) {
            },
            onSelectRow: function(rowid, status) {
            },
            afterSaveCell : function(rowid,name,val,iRow,iCol) {
                var rowData = $("#jqgrid-grid-subordinate-monthly").jqGrid('getRowData', rowid);
                rowData.yearTotal = Number(rowData.jan) + Number(rowData.feb) + Number(rowData.mar) + Number(rowData.apr) + Number(rowData.may) + Number(rowData.jun) + Number(rowData.jul) + Number(rowData.aug) + Number(rowData.sep) + Number(rowData.oct) + Number(rowData.nov) + Number(rowData.dec);
                $('#jqgrid-grid-subordinate-monthly').jqGrid('setRowData', rowid, rowData);
            },
            loadError: function (jqXHR, textStatus, errorThrown) {
                MessageRenderer.renderErrorText(jqXHR.responseText,'');
            }
        });
        $("#create-button-monthlySalesTargetByVolume").show();
        var selectedSubordinateList = selectedSubordinates.toString().split(',');
        var selectedEmployeeCount = 0;
        for(var j = 0; j < selectedSubordinateList.length; j++){
            if(selectedSubordinateList[j]){
                selectedEmployeeCount++;
            }
        }
        $(".yearlyQuantity").each(function(){
            var yearlyDataId = this.id;
            var yearlyDataIdParts = yearlyDataId.split('_');
            var productId = yearlyDataIdParts[1];
            var productCode = $("#productCode_" + productId).val();
            var productName = $("#productName_" + productId).val();
            var yearlyTarget = Number(this.value);
            var janTotal = Math.floor((Number($("#month_" + productId + "_1").val())/selectedEmployeeCount) + 0.5);
            var febTotal = Math.floor((Number($("#month_" + productId + "_2").val())/selectedEmployeeCount) + 0.5);
            var marTotal = Math.floor((Number($("#month_" + productId + "_3").val())/selectedEmployeeCount) + 0.5);
            var aprTotal = Math.floor((Number($("#month_" + productId + "_4").val())/selectedEmployeeCount) + 0.5);
            var mayTotal = Math.floor((Number($("#month_" + productId + "_5").val())/selectedEmployeeCount) + 0.5);
            var junTotal = Math.floor((Number($("#month_" + productId + "_6").val())/selectedEmployeeCount) + 0.5);
            var julTotal = Math.floor((Number($("#month_" + productId + "_7").val())/selectedEmployeeCount) + 0.5);
            var augTotal = Math.floor((Number($("#month_" + productId + "_8").val())/selectedEmployeeCount) + 0.5);
            var sepTotal = Math.floor((Number($("#month_" + productId + "_9").val())/selectedEmployeeCount) + 0.5);
            var octTotal = Math.floor((Number($("#month_" + productId + "_10").val())/selectedEmployeeCount) + 0.5);
            var novTotal = Math.floor((Number($("#month_" + productId + "_11").val())/selectedEmployeeCount) + 0.5);
            var decTotal = Math.floor((Number($("#month_" + productId + "_12").val())/selectedEmployeeCount) + 0.5);

            var yearTotal = Number(janTotal) + Number(febTotal) + Number(marTotal) + Number(aprTotal) + Number(mayTotal) + Number(junTotal) + Number(julTotal) + Number(augTotal) + Number(sepTotal) + Number(octTotal) + Number(novTotal) + Number(decTotal);
            var calculatedJanTotal = 0; var calculatedFebTotal = 0; var calculatedMarTotal = 0; var calculatedAprTotal = 0;
            var calculatedMayTotal = 0; var calculatedJunTotal = 0; var calculatedJulTotal = 0; var calculatedAugTotal = 0;
            var calculatedSepTotal = 0; var calculatedOctTotal = 0; var calculatedNovTotal = 0; var calculatedDecTotal = 0;

            var adjustedJanTotal = 0; var adjustedFebTotal = 0; var adjustedMarTotal = 0; var adjustedAprTotal = 0;
            var adjustedMayTotal = 0; var adjustedJunTotal = 0; var adjustedJulTotal = 0; var adjustedAugTotal = 0;
            var adjustedSepTotal = 0; var adjustedOctTotal = 0; var adjustedNovTotal = 0; var adjustedDecTotal = 0;

            for(var i = 0; i < selectedSubordinateList.length ; i++){
                if(selectedSubordinateList[i]){
                    var rowData = jqGridSubordinateEmployeeList.getRowData(selectedSubordinateList[i]);
                    var rowCount  = jqGridMonthlyAssignedTargetList.getGridParam("reccount");
                    if(i < (selectedSubordinateList.length -1)){
                        calculatedJanTotal += Number(janTotal);
                        calculatedFebTotal += Number(febTotal);
                        calculatedMarTotal += Number(marTotal);
                        calculatedAprTotal += Number(aprTotal);
                        calculatedMayTotal += Number(mayTotal);
                        calculatedJunTotal += Number(junTotal);
                        calculatedJulTotal += Number(julTotal);
                        calculatedAugTotal += Number(augTotal);
                        calculatedSepTotal += Number(sepTotal);
                        calculatedOctTotal += Number(octTotal);
                        calculatedNovTotal += Number(novTotal);
                        calculatedDecTotal += Number(decTotal);
                        if(i > 1){
                            productCode = "";
                            productName = "";
                        }

                        var newRowData = [
                            { 'id':rowCount + 1,'productId':productId, 'productCode': productCode, 'productName': productName, 'employeeId': selectedSubordinateList[i].toString(),'employeeCode': rowData.code, 'employeeName': rowData.name,
                                'jan': janTotal,'feb': febTotal, 'mar':marTotal, 'apr':aprTotal, 'may':mayTotal, 'jun':junTotal, 'jul':julTotal, 'aug':augTotal, 'sep':sepTotal, 'oct':octTotal, 'nov':novTotal,'dec':decTotal, 'yearTotal': yearTotal
                            }
                        ];
                        jqGridMonthlyAssignedTargetList.addRowData(rowCount + 1, newRowData[0]);
                    }else{
                        // Adjust fraction data for last employee
                        adjustedJanTotal = Number($("#month_" + productId + "_1").val()) - calculatedJanTotal;
                        adjustedFebTotal = Number($("#month_" + productId + "_2").val()) - calculatedFebTotal;
                        adjustedMarTotal = Number($("#month_" + productId + "_3").val()) - calculatedMarTotal;
                        adjustedAprTotal = Number($("#month_" + productId + "_4").val()) - calculatedAprTotal;
                        adjustedMayTotal = Number($("#month_" + productId + "_5").val()) - calculatedMayTotal;
                        adjustedJunTotal = Number($("#month_" + productId + "_6").val()) - calculatedJunTotal;
                        adjustedJulTotal = Number($("#month_" + productId + "_7").val()) - calculatedJulTotal;
                        adjustedAugTotal = Number($("#month_" + productId + "_8").val()) - calculatedAugTotal;
                        adjustedSepTotal = Number($("#month_" + productId + "_9").val()) - calculatedSepTotal;
                        adjustedOctTotal = Number($("#month_" + productId + "_10").val()) - calculatedOctTotal;
                        adjustedNovTotal = Number($("#month_" + productId + "_11").val()) - calculatedNovTotal;
                        adjustedDecTotal = Number($("#month_" + productId + "_12").val()) - calculatedDecTotal;
                        var adjustedYearTotal = adjustedJanTotal + adjustedFebTotal + adjustedMarTotal + adjustedAprTotal + adjustedMayTotal + adjustedJunTotal + adjustedJulTotal + adjustedAugTotal + adjustedSepTotal + adjustedOctTotal + adjustedNovTotal + adjustedDecTotal;

                        var newRowDataForAdjust = [
                            { 'id':rowCount + 1,'productId':productId, 'productCode': productCode, 'productName': productName,'employeeId': selectedSubordinateList[i].toString(),'employeeCode': rowData.code, 'employeeName': rowData.name, 'jan': adjustedJanTotal, 'feb': adjustedFebTotal,
                                'mar':adjustedMarTotal, 'apr':adjustedAprTotal, 'may':adjustedMayTotal, 'jun':adjustedJunTotal, 'jul':adjustedJulTotal, 'aug':adjustedAugTotal, 'sep':adjustedSepTotal, 'oct':adjustedOctTotal, 'nov':adjustedNovTotal,'dec':adjustedDecTotal, 'yearTotal': adjustedYearTotal
                            }
                        ];
                        jqGridMonthlyAssignedTargetList.addRowData(rowCount + 1, newRowDataForAdjust[0]);
                    }
                }
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
                distributeMonthWiseTarget();
            }
        }
    }
    function executePreConditionMonthlySalesTargetByVolume() {
        // trim field vales before process.
        trim_form();
//        if (!$("#gFormMonthlySalesTargetByAmount").validationEngine('validate')) {
//            return false;
//        }
        return true;
    }
    function executeAjaxMonthlySalesTargetByVolume() {
        if (!executePreConditionMonthlySalesTargetByVolume()) {
            return false;
        }
        var data = [];
        $('#jqgrid-grid-subordinate-monthly').jqGrid("editCell", 0, 0, false);
        var gd = $("#jqgrid-grid-subordinate-monthly").jqGrid('getRowData');
        var length = gd.length;
        if(length <= 0){
            MessageRenderer.renderErrorText("Please Distribute Month-wise Target");
            return false
        }
        data.push({'name':'targetYear', 'value': $("#targetYear").val()});

        if($("#salesManSelection").is(':checked')){
            data.push({'name':'isSalesManIncluded', 'value': '1'});  // checked
        } else{
            data.push({'name':'isSalesManIncluded', 'value': '0'});  // unchecked
        }

        var productIndex = 0;
        var isAllowForSubmit = true;
        $(".yearlyQuantity").each(function(){
            var yearlyDataId = this.id;
            var yearlyDataIdParts = yearlyDataId.split('_');
            var productId = yearlyDataIdParts[1];
            var productCode = $("#productCode_" + productId).val();
            var productName = $("#productName_" + productId).val();
            var selfMonthWiseTarget = 0.0;
            var selfYearlyTarget  = 0.0;
            var selfAnnualTarget = Number(this.value);
            data.push({'name':'productId_' + productIndex, 'value': productId});
            data.push({'name':'yearlyTarget_' + productId, 'value': selfAnnualTarget});

            for(var j = 1; j <= 12; j++){
                selfMonthWiseTarget = Number($("#month_" + productId + "_" + j).val());
                selfYearlyTarget += selfMonthWiseTarget;
                var keyIndex = j-1;
                data.push({'name':'selfMonthWiseTarget_' + productId + "_" + keyIndex.toString() + '_targetQuantity', 'value': selfMonthWiseTarget});
            }
            if(selfYearlyTarget != selfAnnualTarget){
                MessageRenderer.renderErrorText("[" + productCode + "] " + productName + ": Total Target=" + selfYearlyTarget + " which is not equal to 12 month target=" + selfAnnualTarget);
                isAllowForSubmit = false;
                return false
            }
            var employeeId = 0;
            var janTotal = 0; var febTotal = 0; var marTotal = 0; var aprTotal = 0;
            var mayTotal = 0; var junTotal = 0; var julTotal = 0; var augTotal = 0;
            var sepTotal = 0; var octTotal = 0; var novTotal = 0; var decTotal = 0;
            for (var i=0; i < length; i++) {
                if(gd[i].productId == productId){
                    employeeId = gd[i].employeeId;
                    janTotal += Number(gd[i].jan); febTotal += Number(gd[i].feb); marTotal += Number(gd[i].mar); aprTotal += Number(gd[i].apr);
                    mayTotal += Number(gd[i].may); junTotal += Number(gd[i].jun); julTotal += Number(gd[i].jul); augTotal += Number(gd[i].aug);
                    sepTotal += Number(gd[i].sep); octTotal += Number(gd[i].oct); novTotal += Number(gd[i].nov); decTotal += Number(gd[i].dec);
                    data.push({'name':'subordinates_' + productId + "_" + employeeId + '_month_0_targetQuantity', 'value': gd[i].jan});
                    data.push({'name':'subordinates_' + productId + "_" + employeeId + '_month_1_targetQuantity', 'value': gd[i].feb});
                    data.push({'name':'subordinates_' + productId + "_" + employeeId + '_month_2_targetQuantity', 'value': gd[i].mar});
                    data.push({'name':'subordinates_' + productId + "_" + employeeId + '_month_3_targetQuantity', 'value': gd[i].apr});
                    data.push({'name':'subordinates_' + productId + "_" + employeeId + '_month_4_targetQuantity', 'value': gd[i].may});
                    data.push({'name':'subordinates_' + productId + "_" + employeeId + '_month_5_targetQuantity', 'value': gd[i].jun});
                    data.push({'name':'subordinates_' + productId + "_" + employeeId + '_month_6_targetQuantity', 'value': gd[i].jul});
                    data.push({'name':'subordinates_' + productId + "_" + employeeId + '_month_7_targetQuantity', 'value': gd[i].aug});
                    data.push({'name':'subordinates_' + productId + "_" + employeeId + '_month_8_targetQuantity', 'value': gd[i].sep});
                    data.push({'name':'subordinates_' + productId + "_" + employeeId + '_month_9_targetQuantity', 'value': gd[i].oct});
                    data.push({'name':'subordinates_' + productId + "_" + employeeId + '_month_10_targetQuantity', 'value': gd[i].nov});
                    data.push({'name':'subordinates_' + productId + "_" + employeeId + '_month_11_targetQuantity', 'value': gd[i].dec});
                }
            }
            productIndex += 1;
            var productJanTarget = Number($("#month_" + productId + "_1").val());
            if(janTotal != productJanTarget){
                MessageRenderer.renderErrorText("[" + productCode + "] " + productName + ": January target=" + productJanTarget + " is not equal to subordinate January target=" + janTotal);
                isAllowForSubmit = false;
                return false;
            }
            var productFebTarget = Number($("#month_" + productId + "_2").val());
            if(febTotal != productFebTarget){
                MessageRenderer.renderErrorText("[" + productCode + "] " + productName + ": February target=" + productFebTarget + " is not equal to subordinate February target=" + febTotal);
                isAllowForSubmit = false;
                return false;
            }
            var productMarTarget = Number($("#month_" + productId + "_3").val());
            if(marTotal != productMarTarget){
                MessageRenderer.renderErrorText("[" + productCode + "] " + productName + ": March target=" + productMarTarget + " is not equal to subordinate March target=" + marTotal);
                isAllowForSubmit = false;
                return false;
            }
            var productAprTarget = Number($("#month_" + productId + "_4").val());
            if(aprTotal != productAprTarget){
                MessageRenderer.renderErrorText("[" + productCode + "] " + productName + ": April target=" + productAprTarget + " is not equal to subordinate April target=" + aprTotal);
                isAllowForSubmit = false;
                return false;
            }
            var productMayTarget = Number($("#month_" + productId + "_5").val());
            if(mayTotal != productMayTarget){
                MessageRenderer.renderErrorText("[" + productCode + "] " + productName + ": May target=" + productMayTarget + " is not equal to subordinate May target=" + mayTotal);
                isAllowForSubmit = false;
                return false;
            }
            var productJunTarget = Number($("#month_" + productId + "_6").val());
            if(junTotal != productJunTarget){
                MessageRenderer.renderErrorText("[" + productCode + "] " + productName + ": June target=" + productJunTarget + " is not equal to subordinate June target=" + junTotal);
                isAllowForSubmit = false;
                return false;
            }
            var productJulTarget = Number($("#month_" + productId + "_7").val());
            if(julTotal != productJulTarget){
                MessageRenderer.renderErrorText("[" + productCode + "] " + productName + ": July target=" + productJulTarget + " is not equal to subordinate July target=" + julTotal);
                isAllowForSubmit = false;
                return false;
            }
            var productAugTarget = Number($("#month_" + productId + "_8").val());
            if(augTotal != productAugTarget){
                MessageRenderer.renderErrorText("[" + productCode + "] " + productName + ": August target=" + productAugTarget + " is not equal to subordinate August target=" + augTotal);
                isAllowForSubmit = false;
                return false;
            }
            var productSepTarget = Number($("#month_" + productId + "_9").val());
            if(sepTotal != productSepTarget){
                MessageRenderer.renderErrorText("[" + productCode + "] " + productName + ": September target=" + productSepTarget + " is not equal to subordinate September target=" + sepTotal);
                isAllowForSubmit = false;
                return false;
            }
            var productOctTarget = Number($("#month_" + productId + "_10").val());
            if(octTotal != productOctTarget){
                MessageRenderer.renderErrorText("[" + productCode + "] " + productName + ": October target=" + productOctTarget + " is not equal to subordinate October target=" + octTotal);
                isAllowForSubmit = false;
                return false;
            }
            var productNovTarget = Number($("#month_" + productId + "_11").val());
            if(novTotal != productNovTarget){
                MessageRenderer.renderErrorText("[" + productCode + "] " + productName + ": November target=" + productNovTarget + " is not equal to subordinate November target=" + novTotal);
                isAllowForSubmit = false;
                return false;
            }
            var productDecTarget = Number($("#month_" + productId + "_12").val());
            if(decTotal != productDecTarget){
                MessageRenderer.renderErrorText("[" + productCode + "] " + productName + ": December target=" + productDecTarget + " is not equal to subordinate December target=" + decTotal);
                isAllowForSubmit = false;
                return false;
            }
        });
        if(!isAllowForSubmit){
            return false;
        }
        data.push({'name':'productCount', 'value': productIndex});
        var employeeCount = length/productIndex;
        data.push({'name':'employeeCount', 'value': employeeCount});
        for(var j = 1; j <= employeeCount; j++){
            var employeeIndex = j-1;
            var rowDataForEmployee =  $("#jqgrid-grid-subordinate-monthly").jqGrid('getRowData', j);
            data.push({'name':'employeeId_' + employeeIndex, 'value': rowDataForEmployee.employeeId});
        }
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: data,
            url: "${request.contextPath}/${params.controller}/create",
            success: function (data, textStatus) {
                executePostConditionMonthlySalesTargetByVolume(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $$("#monthWiseTarget").html("");
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
        return true
    }
    function executePostConditionMonthlySalesTargetByVolume(result) {
        if (result.type == 1) {
            $("#monthWiseTarget").html("");
        }
        MessageRenderer.render(result);
    }
    function changeQuantityDataProperties(){
        $(".quantity").format({precision: 0, allow_negative: false, autofix: true});
    }
</script>
<div>
    <label>Monthly Target:</label>
    <br/>
    <br/>
    <table class="simple-table-css" style="width: 800px;">
        <tr>
            <td class="width150">Product Code</td>
            <td class="width200">Product Name</td>
            <td class="width80">Yearly Target</td>
            <g:each var="i" in="${ (0..<12) }">
                <td class="width60">${monthList[i]}</td>
            </g:each>
        </tr>

        <g:each in="${productWiseMonthlyTargetList}" var="productWiseMonthlyTarget" status="j">
            <tr>
                <g:each in="${productWiseMonthlyTarget}" var="monthlySalesTargetFinishProduct" status="i">
                    <g:if test="${i == 0}">
                        <td class="width150">
                            <g:textField class="width150"
                                         name="productCode_${monthlySalesTargetFinishProduct.finishProduct.id}"
                                         id="productCode_${monthlySalesTargetFinishProduct.finishProduct.id}" value="${monthlySalesTargetFinishProduct.finishProduct.code}"
                                         maxlength="9" disabled="disabled"/>
                        </td>
                        <td class="width200">
                            <g:textField class="width200"
                                         name="productName_${monthlySalesTargetFinishProduct.finishProduct.id}"
                                         id="productName_${monthlySalesTargetFinishProduct.finishProduct.id}" value="${monthlySalesTargetFinishProduct.finishProduct.name}"
                                         maxlength="9" disabled="disabled"/>
                        </td>
                        <td class="width80">
                            <g:textField class="width80 yearlyQuantity"
                                         name="yearlyQty_${monthlySalesTargetFinishProduct.finishProduct.id}"
                                         id="yearlyQty_${monthlySalesTargetFinishProduct.finishProduct.id}" value="${yearlyTargetQuantityList[j].get(monthlySalesTargetFinishProduct.finishProduct.id.toString())}"
                                         maxlength="9" disabled="disabled"/>
                        </td>
                    </g:if>

                    <td class="width40">
                        <g:if test="${monthlySalesTargetFinishProduct.id}">
                            <g:textField class="width40 alin_right month_${monthlySalesTargetFinishProduct.monthlySalesTargetByVolume.targetMonth}"
                                         name="month_${monthlySalesTargetFinishProduct.finishProduct.id}_${monthlySalesTargetFinishProduct.monthlySalesTargetByVolume.targetMonth}"
                                         id="month_${monthlySalesTargetFinishProduct.finishProduct.id}_${monthlySalesTargetFinishProduct.monthlySalesTargetByVolume.targetMonth}" value="${monthlySalesTargetFinishProduct.quantity}"
                                         maxlength="9" disabled="disabled"/>
                        </g:if>
                        <g:else>
                            <g:textField class="width40 alin_right quantity month_${monthlySalesTargetFinishProduct.monthlySalesTargetByVolume.targetMonth}"
                                         name="month_${monthlySalesTargetFinishProduct.finishProduct.id}_${monthlySalesTargetFinishProduct.monthlySalesTargetByVolume.targetMonth}"
                                         id="month_${monthlySalesTargetFinishProduct.finishProduct.id}_${monthlySalesTargetFinishProduct.monthlySalesTargetByVolume.targetMonth}" value="${monthlySalesTargetFinishProduct.quantity}"
                                         maxlength="9"/>
                        </g:else>
                    </td>
                </g:each>
            </tr>
        </g:each>
    </table>
</div>
<div class="buttons" style="margin-left:10px;">
    <span class="button"><input type="button" name="create-button" id="reset-button-monthlySalesTargetByAmount"
                                class="ui-button ui-widget ui-state-default ui-corner-all"
                                value="Reset Default"
                                onclick="resetMonthWiseTarget();"/>
        <input type="checkbox" name="salesManSelection" id="salesManSelection" value="includeSM" onchange="loadUnloadSM(this)"> <b>Include SM</b>
    </span>

</div>
<div class="jqgrid-container">
    <table id="jqgrid-grid-subordinate-employee"></table>
</div>
<div class="buttons" style="margin-left:10px;">
    <span class="button"><input type="button" name="distributeMonthWise-button" id="distributeMonthWise-button"
                                class="ui-button ui-widget ui-state-default ui-corner-all"
                                value="Distribute Month-wise Target"
                                onclick="distributeMonthWiseTarget();"/>
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
                                onclick="distributeMonthWiseTarget();"/>
    </span>
</div>
<div id="datewise-target-div">

</div>