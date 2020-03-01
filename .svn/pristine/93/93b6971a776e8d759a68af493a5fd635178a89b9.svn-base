<script type="text/javascript" language="Javascript">
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
                    name: 'select', width: 50, align: 'center',
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
            rowNum: 1000,
            rowList: [10, 20, 30],
            pager: '',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Territory List",
            width: 250,
            autoheight: true,
            scrollOffset: 0,
            altRows: true,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
            }
        });

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
            rowNum: 1000,
            rowList: [10, 20, 30],
            pager: '',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Geo Location List",
            width: 250,
            autoheight: true,
            scrollOffset: 0,
            altRows: true,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
            }
        });

        $("#jqgrid-grid-category").jqGrid({
            url: '${resource(dir:'onePercentBonus', file:'listCategory')}?entId=' + $("#enterpriseConfiguration").val(),
            datatype: "json",
            colNames: [
                'ID',
                'Select',
                'Category',
                'added'
            ],
            colModel: [
                {name: 'id', index: 'id', width: 0, hidden: true},
                {
                    name: 'select', width: 50, align: 'center',
                    formatter: cboxFormatterCategory, formatoptions: {disabled: false},
                    edittype: "checkbox", editoptions: {value: "true:false", defaultValue: "false"},
                    stype: "select", searchoptions: {
                    sopt: ["eq", "ne"],
                    value: ":Any;true:true;false:false"
                }
                },
                {name: 'category', index: 'category', width: 150, align: 'left'},
                {name: 'selected', index: 'selected', width: 0, hidden: true}
            ],
            rowNum: 1000,
            rowList: [10, 20, 30],
            pager: '',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Category List",
            width: 250,
            autoheight: true,
            scrollOffset: 0,
            altRows: true,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
//                executeEditOnePercentBonus();
            }
        });

        $("#jqgrid-grid-customer").jqGrid({
            %{--url: '${resource(dir:'onePercentBonus', file:'list')}',--}%
            datatype: "json",
            colNames: [
                'ID',
                'Select',
                'Customer',
                'Category',
                'Geo Location',
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
                {name: 'customer', index: 'customer', width: 150, align: 'left'},
                {name: 'category', index: 'category', width: 100, align: 'left'},
                {name: 'geoLocation', index: 'geoLocation', width: 100, align: 'left'},
                {name: 'selected', index: 'selected', width: 0, hidden: true}
            ],
            rowNum: 1000,
            rowList: [10, 20, 30],
            pager: '',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Customer List",
            width: 380,
            autoheight: true,
            scrollOffset: 0,
            altRows: true,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
//                executeEditOnePercentBonus();
            }
        });

        $("#jqgrid-grid-product").jqGrid({
            %{--url: '${resource(dir:'onePercentBonus', file:'listProduct')}?entId=' + $("#enterpriseConfiguration").val(),--}%
            datatype: "json",
            colNames: [
                'ID',
                'Select',
                'Finish Product',
                'added'
            ],
            colModel: [
                {name: 'id', index: 'id', width: 0, hidden: true},
                {
                    name: 'select', width: 50, align: 'center',
                    formatter: cboxFormatterProduct, formatoptions: {disabled: false},
                    edittype: "checkbox", editoptions: {value: "true:false", defaultValue: "false"},
                    stype: "select", searchoptions: {
                    sopt: ["eq", "ne"],
                    value: ":Any;true:true;false:false"
                }
                },
                {name: 'finishProduct', index: 'finishProduct', width: 150, align: 'left'},
                {name: 'selected', index: 'selected', width: 0, hidden: true}
            ],
            rowNum: 1000,
            rowList: [10, 20, 30],
            pager: '',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Product List",
            width: 380,
            autoheight: true,
            scrollOffset: 0,
            altRows: true,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
//                executeEditOnePercentBonus();
            }
        });

    });

    function listProduct(id){
        if(!id){
            $("#jqgrid-grid-product").jqGrid('clearGridData');
            $("#jqgrid-grid-customer").jqGrid('clearGridData');
            return false;
        }
        $("#jqgrid-grid-product").setGridParam({
            url: '${resource(dir:'onePercentBonus', file:'listProduct')}?entId=' + $("#enterpriseConfiguration").val() + '&bonus=' + $("#bonusPercent").val()
        });
        $("#jqgrid-grid-product").trigger("reloadGrid");
        loadGeoAndCustomer(3);
    }

    function cboxFormatterCustomer(cellvalue, options, rowObject) {
        return '<input type="checkbox" ' + (cellvalue == 'true' ? ' checked="checked"' : '') +
                'value="false" onchange="if(this.checked){selected(' + options.rowId + ', true, 4)}else{selected(' + options.rowId + ', false, 4)};"/>';
    }

    function cboxFormatterProduct(cellvalue, options, rowObject) {
        return '<input type="checkbox" ' + (cellvalue == 'true' ? ' checked="checked"' : '') +
                'value="false" onchange="if(this.checked){selected(' + options.rowId + ', true, 0)}else{selected(' + options.rowId + ', false, 0)};"/>';
    }

    function cboxFormatterTerritory(cellvalue, options, rowObject) {

        return '<input type="checkbox" ' + (cellvalue == 'true' ? ' checked="checked"' : '') +
                'value="false" onchange="if(this.checked){selected(' + options.rowId + ', true, 1)}else{selected(' + options.rowId + ', false, 1)} loadGeoAndCustomer(1);"/>';
    }

    function cboxFormatterGeo(cellvalue, options, rowObject) {
        return '<input type="checkbox" ' + (cellvalue == 'true' ? ' checked="checked"' : '') +
                'value="false" onchange="if(this.checked){selected(' + options.rowId + ', true, 2)}else{selected(' + options.rowId + ', false, 2)} loadGeoAndCustomer(2);"/>';
    }

    function cboxFormatterCategory(cellvalue, options, rowObject) {
        return '<input type="checkbox" ' + (cellvalue == 'true' ? ' checked="checked"' : '') +
                'value="false" onchange="if(this.checked){selected(' + options.rowId + ', true, 3)}else{selected(' + options.rowId + ', false, 3)} loadGeoAndCustomer(3);"/>';
    }

    function selected(id, val, key) {
        if (key == '1') {
            $("#jqgrid-grid-territory").jqGrid('setCell', id, 'selected', val);
        } else if (key == '2') {
            $("#jqgrid-grid-geolocation").jqGrid('setCell', id, 'selected', val);
        } else if (key == '3') {
            $("#jqgrid-grid-category").jqGrid('setCell', id, 'selected', val);
        } else if (key == '4') {
            $("#jqgrid-grid-customer").jqGrid('setCell', id, 'selected', val);
        } else {
            $("#jqgrid-grid-product").jqGrid('setCell', id, 'selected', val);
        }
    }

    function loadGeoAndCustomer(key) {
        var colData = $("#jqgrid-grid-territory").jqGrid('getRowData');
        var colData2 = $("#jqgrid-grid-geolocation").jqGrid('getRowData');
        var colData3 = $("#jqgrid-grid-category").jqGrid('getRowData');
        var bonus = $("#bonusPercent").val();
        var ter = [];
        var geo = [];
        var cat = [];
        if (key == 1) {
            var ids = [];
            var x = 0;
            for (var i = 0; i < colData.length; i++) {
                if (colData[i].selected == 'true') {
                    ids[x] = colData[i].id;
                    x++;
                }
            }
            if (ids.length == 0) {
                $("#jqgrid-grid-geolocation").jqGrid('clearGridData');
            } else {
                ter = ids;
                $("#jqgrid-grid-geolocation").setGridParam({
                    url: '${resource(dir:'onePercentBonus', file:'listGeolocation')}?territory=' + ids
                });
                $("#jqgrid-grid-geolocation").trigger("reloadGrid");
            }
            $("#jqgrid-grid-customer").jqGrid('clearGridData');
        } else {
            if(bonus == ''){
                $("#jqgrid-grid-customer").jqGrid('clearGridData');
                return false;
            }
            var y = 0;
            for (var j = 0; j < colData2.length; j++) {
                if (colData2[j].selected == 'true') {
                    geo[y] = colData2[j].id;
                    y++;
                }
            }
            y = 0;
            for (var k = 0; k < colData3.length; k++) {
                if (colData3[k].selected == 'true') {
                    cat[y] = colData3[k].id;
                    y++;
                }
            }
            $("#jqgrid-grid-customer").setGridParam({
                url: '${resource(dir:'onePercentBonus', file:'listCustomer')}?geo=' + geo + '&cat=' + cat + '&bonus=' + bonus
            });
            $("#jqgrid-grid-customer").trigger("reloadGrid");
        }
    }

    function executePreConditionOnePercentBonus() {
        // trim field vales before process.
        trim_form();
        if (!$("#gFormOnePercentBonus").validationEngine('validate')) {
            return false;
        }
        return true;
    }

    function executeAjaxOnePercentBonus() {
        if (!executePreConditionOnePercentBonus()) {
            return false;
        }
        var actionUrl = "${request.contextPath}/${params.controller}/create";
        var customerCol = $("#jqgrid-grid-customer").jqGrid('getRowData');
        var productCol = $("#jqgrid-grid-product").jqGrid('getRowData');
        var data = jQuery("#gFormOnePercentBonus").serialize();
        var customer = [];
        var product = [];
        var customerDec = [];
        var productDec = [];
        var k = 0;
        var l = 0;
        for(var z = 0; z < customerCol.length; z++){
            if (customerCol[z].selected == 'true') {
                customer[k] = customerCol[z].id;
                k++;
            }else{
                customerDec[l] = customerCol[z].id;
                l++;
            }
        }
        k = 0;
        l = 0;
        for(z = 0; z < productCol.length; z++){
            if (productCol[z].selected == 'true') {
                product[k] = productCol[z].id;
                k++;
            }else{
                productDec[l] = productCol[z].id;
                l++;
            }
        }
        data = data + '&customer=' + customer;
        data = data + '&product=' + product;
        data = data + '&customerRejected=' + customerDec;
        data = data + '&productRejected=' + productDec;

        jQuery.ajax({
            type: 'post',
            data: data,
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionOnePercentBonus(data);
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

    function executePostConditionOnePercentBonus(result) {
        if (result.type == 1) {
        }
        $("#jqgrid-grid-customer").trigger("reloadGrid");
        $("#jqgrid-grid-product").trigger("reloadGrid");
        MessageRenderer.render(result);
    }

</script>