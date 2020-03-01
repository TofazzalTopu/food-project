<%@ page import="com.docu.commons.CommonConstants" %>
<script type="text/javascript" language="Javascript">

    var branch = 0;

    $(document).ready(function () {
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormOnePercentBonus").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormOnePercentBonus").validationEngine('attach');
//        reset_form("#gFormOnePercentBonus");

        $("#jqgrid-grid-customer").jqGrid({
            %{--url: '${resource(dir:'onePercentBonus', file:'list')}',--}%
            datatype: "json",
            colNames: [
                'ID',
                'Select',
                'Customer Name',
                'Customer Code',
                'Net Sales',
                'Bonus Amount',
                'Receivable Amount',
                'added'
            ],
            colModel: [
                {name: 'id', index: 'id', width: 0, hidden: true},
                {
                    name: 'select', width: 50, align: 'center',
                    formatter: cboxFormatterCustomer, formatoptions: {disabled: false},
                    edittype: "checkbox", editoptions: {value: "true:false", defaultValue: "false"},
                    stype: "select", searchoptions: {
                    sopt: ["eq", "ne"],
                    value: ":Any;true:true;false:false"
                }
                },
                {name: 'customerName', index: 'customerName', width: 150, align: 'left'},
                {name: 'customerCode', index: 'customerCode', width: 100, align: 'center'},
                {name: 'totalSales', index: 'totalSales', width: 100, align: 'right', formatter:"number",  formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2}, sortable: false},
                {name: 'bonusAmount', index: 'bonusAmount', width: 100, align: 'right', formatter:"number",  formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2}, sortable: false},
                {name: 'receivableAmount', index: 'receivableAmount', width: 100, align: 'right', formatter:"number",  formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2}, sortable: false},
                {name: 'selected', index: 'selected', width: 0, hidden: true}
            ],
            rowNum: -1,
//            rowList: [10, 20, 30],
//            pager: '#jqgrid-pager-customer',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Customer List",
            autowidth: true,
            autoHeight: true,
            scrollOffset: 0,
            altRows: true,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
//                executeEditOnePercentBonus();
            }
        });

        initDatePicker();
        if ('${isFactory}' == 'true') {
            $("#jqgrid-grid-territory").jqGrid({
                url: '${resource(dir:'onePercentBonus', file:'listTerritory')}?entId=' + $("#enterpriseConfiguration").val(),
                datatype: "json",
                colNames: [
                    'ID',
                    'Select',
                    'Territory',
                    'added'
                ],
                colModel: [
                    {name: 'id', index: 'id', width: 0, hidden: true},
                    {
                        name: 'select', width: 30, align: 'center',
                        formatter: cboxFormatterTerritory, formatoptions: {disabled: false},
                        edittype: "checkbox", editoptions: {value: "true:false", defaultValue: "false"},
                        stype: "select", searchoptions: {
                        sopt: ["eq", "ne"],
                        value: ":Any;true:true;false:false"
                    }
                    },
                    {name: 'territory', index: 'territory', width: 150, align: 'left'},
                    {name: 'selected', index: 'selected', width: 0, hidden: true}
                ],
                rowNum: 10,
                rowList: [10, 20, 30],
                pager: '#jqgrid-pager-territory',
                sortname: 'id',
                viewrecords: true,
                sortorder: "desc",
                caption: "Territory List",
                width: 390,
                autoheight: true,
                scrollOffset: 0,
                altRows: true,
                loadComplete: function () {
                },
                onSelectRow: function (rowid, status) {
                }
            });
//            $("#radio").attr('hidden', false);
            branch = 1;
        } else {
            $("#jqgrid-grid-geolocation").jqGrid({
                url: '${resource(dir:'onePercentBonus', file:'listGeolocationByDp')}',
                datatype: "json",
                colNames: [
                    'ID',
                    'Select',
                    'Geo Location',
                    'added'
                ],
                colModel: [
                    {name: 'id', index: 'id', width: 0, hidden: true},
                    {
                        name: 'select', width: 30, align: 'center',
                        formatter: cboxFormatterGeo, formatoptions: {disabled: false},
                        edittype: "checkbox", editoptions: {value: "true:false", defaultValue: "false"},
                        stype: "select", searchoptions: {
                        sopt: ["eq", "ne"],
                        value: ":Any;true:true;false:false"
                    }
                    },
                    {name: 'geoLocation', index: 'geoLocation', width: 150, align: 'left'},
                    {name: 'selected', index: 'selected', width: 0, hidden: true}
                ],
                rowNum: 10,
                rowList: [10, 20, 30],
                pager: '#jqgrid-pager-geolocation',
                sortname: 'id',
                viewrecords: true,
                sortorder: "desc",
                caption: "Geo Location List",
                width: 390,
                autoheight: true,
                scrollOffset: 0,
                altRows: true,
                loadComplete: function () {
                },
                onSelectRow: function (rowid, status) {
                }
            });
            branch = 0;
        }
    });

    function initDatePicker() {
        $('#calculatedTo').datepicker(
                {
                    dateFormat: 'dd-mm-yy',
                    changeMonth: true,
                    changeYear: true,
                    maxDate: -1
                });
//        $("#calculatedTo").datepicker("setDate", "-1d");
        $('#calculatedTo').mask('${CommonConstants.DATE_MASK_FORMAT}', {});
    }

    function cboxFormatterTerritory(cellvalue, options, rowObject) {
        return '<input type="checkbox" ' + (cellvalue == 'true' ? ' checked="checked"' : '') +
                'value="false" onchange="if(this.checked){selected(' + options.rowId + ', true, 1)}else{selected(' + options.rowId + ', false, 1)} loadGeoAndCustomer(1);"/>';
    }

    function cboxFormatterGeo(cellvalue, options, rowObject) {
        return '<input type="checkbox" ' + (cellvalue == 'true' ? ' checked="checked"' : '') +
                'value="false" onchange="if(this.checked){selected(' + options.rowId + ', true, 2)}else{selected(' + options.rowId + ', false, 2)} loadGeoAndCustomer(2);"/>';
    }

    function cboxFormatterCustomer(cellvalue, options, rowObject) {
        return '<input type="checkbox" ' + (cellvalue == 'true' ? ' checked="checked"' : '') +
                'value="false" onchange="if(this.checked){selected(' + options.rowId + ', true, 4)}else{selected(' + options.rowId + ', false, 4)};"/>';
    }

    function selected(id, val, key) {
        if (key == '1') {
            $("#jqgrid-grid-territory").jqGrid('setCell', id, 'selected', val);
        } else if (key == '2') {
            $("#jqgrid-grid-geolocation").jqGrid('setCell', id, 'selected', val);
        } else if (key == '4') {
            $("#jqgrid-grid-customer").jqGrid('setCell', id, 'selected', val);
        }
    }

    function loadGeoAndCustomer(key) {
        var colData = $("#jqgrid-grid-territory").jqGrid('getRowData');
        var colData2 = $("#jqgrid-grid-geolocation").jqGrid('getRowData');
        var ter = [];
        var geo = [];
        if (key == 1) {
            var ids = [];
            var x = 0;
            for (var i = 0; i < colData.length; i++) {
                if (colData[i].selected == 'true') {
                    ids[x] = colData[i].id;
                    x++;
                }
            }
            if (branch == 1) {
                if ($("#calculatedTo").val() == '') {
                    MessageRenderer.render({
                        messageTitle: 'Warning',
                        type: 2,
                        messageBody: 'Please select date first.'
                    });
                    $("#jqgrid-grid-territory").trigger("reloadGrid");
                    return false;
                }
                if (ids.length == 0) {
                    $("#jqgrid-grid-customer").jqGrid('clearGridData');
                } else {
                    ter = ids;
                    $("#jqgrid-grid-customer").setGridParam({
                        url: '${resource(dir:'onePercentBonus', file:'listBranch')}?territory=' + ids + '&date=' + $("#calculatedTo").val()
                    });
                    $("#jqgrid-grid-customer").trigger("reloadGrid");

                }
            } else {
                if (ids.length == 0) {
                    $("#jqgrid-grid-geolocation").jqGrid('clearGridData');
                } else {
                    ter = ids;
                    $("#jqgrid-grid-geolocation").setGridParam({
                        url: '${resource(dir:'onePercentBonus', file:'listGeolocation')}?territory=' + ids
                    });
                    $("#jqgrid-grid-geolocation").trigger("reloadGrid");

                }
            }
        } else {
            if ($("#calculatedTo").val() == '') {
                MessageRenderer.render({
                    messageTitle: 'Warning',
                    type: 2,
                    messageBody: 'Please select date first.'
                });
                $("#jqgrid-grid-geolocation").trigger("reloadGrid");
                return false;
            }
            var y = 0;
            for (var j = 0; j < colData2.length; j++) {
                if (colData2[j].selected == 'true') {
                    geo[y] = colData2[j].id;
                    y++;
                }
            }
            if (geo.length == 0) {
                $("#jqgrid-grid-customer").jqGrid('clearGridData');
            } else {
                $("#jqgrid-grid-customer").setGridParam({
                    url: '${resource(dir:'onePercentBonus', file:'listSalesForSalesman')}?geo=' + geo + '&date=' + $("#calculatedTo").val() + '&cat=' + 3
                });
                $("#jqgrid-grid-customer").trigger("reloadGrid");
            }
        }
    }

    function showGeo(key) {
        branch = key;
        if (key == 0) {
            $("#geo").attr('hidden', false);
            $("#jqgrid-grid-geolocation").jqGrid({
                %{--url: '${resource(dir:'onePercentBonus', file:'list')}',--}%
                datatype: "json",
                colNames: [
                    'ID',
                    'Select',
                    'Geo Location',
                    'added'
                ],
                colModel: [
                    {name: 'id', index: 'id', width: 0, hidden: true},
                    {
                        name: 'select', width: 50, align: 'center',
                        formatter: cboxFormatterGeo, formatoptions: {disabled: false},
                        edittype: "checkbox", editoptions: {value: "true:false", defaultValue: "false"},
                        stype: "select", searchoptions: {
                        sopt: ["eq", "ne"],
                        value: ":Any;true:true;false:false"
                    }
                    },
                    {name: 'geoLocation', index: 'geoLocation', width: 150, align: 'left'},
                    {name: 'selected', index: 'selected', width: 0, hidden: true}
                ],
                rowNum: 10,
                rowList: [10, 20, 30],
                pager: '#jqgrid-pager-geolocation',
                sortname: 'id',
                viewrecords: true,
                sortorder: "desc",
                caption: "Geo Location List",
                width: 390,
                autoheight: true,
                scrollOffset: 0,
                altRows: true,
                loadComplete: function () {
                },
                onSelectRow: function (rowid, status) {
                }
            });
        } else {
            $("#geo").attr('hidden', true);
        }
    }

    function adjustBonus() {
        var actionUrl = "${request.contextPath}/${params.controller}/adjustBonus";
        var data = $("#gFormAdjustOnePercentBonus").serializeArray();
        var gd = $("#jqgrid-grid-customer").jqGrid('getRowData');
        var length = gd.length;
        for (var i = 0; i < length; i++) {
            if (gd[i].selected == 'true') {
                data.push({
                    'name': 'items.adjustOnePercentBonus[' + i + '].enterpriseConfiguration.id',
                    'value': $("#enterpriseConfiguration").val()
                });
                data.push({'name': 'items.adjustOnePercentBonus[' + i + '].customerMaster.id', 'value': gd[i].id});
                data.push({'name': 'items.adjustOnePercentBonus[' + i + '].bonus', 'value': gd[i].bonusAmount});
            }
        }
        data.push({'name': 'branch', 'value': branch});
        data.push({'name': 'isFactory', 'value': '${isFactory}'});

        jQuery.ajax({
            type: 'post',
            data: data,
            url: actionUrl,
            success: function (data, textStatus) {
                executePostCondition(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                }else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete: function () {
            },
            dataType: 'json'
        });
        return false;
    }

    function executePostCondition(result) {
        if (result.type == 1) {
//            $("#jqgrid-grid-customer").trigger("reloadGrid");
            $("#jqgrid-grid-territory").trigger("reloadGrid");
            $("#jqgrid-grid-geolocation").trigger("reloadGrid");
            $("#jqgrid-grid-customer").jqGrid("clearGridData");
        }
        MessageRenderer.render(result);
    }

</script>