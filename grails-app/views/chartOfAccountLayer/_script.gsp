<script type="text/javascript" language="Javascript">

    var editNode = null;
    var addedId = -1;
    var editId = -1;
    var parentId = -1;
    var data = [];
    var dataList = null;
    var update = -1;

    $(document).ready(function () {
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormChartOfAccount").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormChartOfAccount").validationEngine('attach');
        if ('${editable}' == '0') {
            $("#code").attr('disabled', true);
            $("#name").attr('disabled', true);
            $(".button").attr('hidden', 'hidden');
            MessageRenderer.showHeaderMessage("Chart Of Accounts can not be altered or created due to it already being in use.", 0)
        }
        update = ${update};
        tree();
    });

    function loadLayer() {
        data = [];
        var grid = $("#tree-grid");
        dataList = ${layerList};
        var list = ${layerList};
        var grpName = "Asset";
        var gridId = 0;
        if (list != '') {
            var lastId = 0;
            for (var j = 0; j < 4; j++) {
                if (j == 1) {
                    grpName = "Liability";
                } else if (j == 2) {
                    grpName = "Income";
                } else if (j == 3) {
                    grpName = "Expense";
                }
                for (var i = 0; i < list.length; i++) {
                    if (i == 0) {
                        data[gridId] = {
                            id: gridId,
                            coaId: '',
                            layerId: list[i].id,
                            layerNo: list[i].layer_number,
                            supId: '',
                            layerName: grpName,
                            codeLength: list[i].layer_code_length,
                            layerCode: '',
                            completeCode: '',
                            level: i,
                            parent: '',
                            isLeaf: false,
                            expanded: true,
                            loaded: true
                        };
                    } else if (i != list.length - 1) {
                        data[gridId] = {
                            id: gridId,
                            coaId: '',
                            layerId: list[i].id,
                            layerNo: list[i].layer_number,
                            supId: '',
                            layerName: list[i].layer_name,
                            codeLength: list[i].layer_code_length,
                            completeCode: '',
                            layerCode: '',
                            level: i,
                            parent: gridId - 1,
                            isLeaf: false,
                            expanded: true,
                            loaded: true
                        };
                    } else {
                        data[gridId] = {
                            id: gridId,
                            coaId: '',
                            layerId: list[i].id,
                            layerNo: list[i].layer_number,
                            supId: '',
                            layerName: list[i].layer_name,
                            codeLength: list[i].layer_code_length,
                            layerCode: '',
                            completeCode: '',
                            level: i,
                            parent: gridId - 1,
                            isLeaf: true,
                            expanded: true,
                            loaded: true
                        };
                    }
                    lastId = list[i].id;
                    gridId++;
                }
            }
        }

        grid[0].addJSONData({
            total: 1,
            page: 1,
            records: data.length,
            rows: data
        });
    }

    function loadUpdate(updateList) {
        data = [];
        var grid = $("#tree-grid");
        var list = '';
        if (updateList == '') {
            dataList = ${layerList};
            list = ${layerList};
        } else {
            dataList = updateList;
            list = updateList;
        }
        var count = Number($("#entLayers").val());
        var parId = [];
        if (list != '') {
            for (var i = 0, j = 0; j < list.length; j++) {
//                alert(count + ' ' + i);
                i = Number(list[j].layer_number) - 1;
                if (i == 0) {
                    data[j] = {
                        id: j,
                        coaId: list[j].id,
                        layerId: list[j].layer_id,
                        layerNo: list[j].layer_number,
                        supId: '',
                        layerName: list[j].chart_of_account_name,
                        codeLength: list[j].layer_code_length,
                        layerCode: list[j].chart_of_account_code_user,
                        completeCode: list[j].chart_of_account_code_auto,
                        level: i,
                        parent: '',
                        isLeaf: false,
                        expanded: true,
                        loaded: true
                    };
                    parId[i] = j;
                } else if (i != count - 1) {
                    data[j] = {
                        id: j,
                        coaId: list[j].id,
                        layerId: list[j].layer_id,
                        layerNo: list[j].layer_number,
                        supId: list[j].parent_id,
                        layerName: list[j].chart_of_account_name,
                        codeLength: list[j].layer_code_length,
                        layerCode: list[j].chart_of_account_code_user,
                        completeCode: list[j].chart_of_account_code_auto,
                        level: i,
                        parent: parId[i - 1],
                        isLeaf: false,
                        expanded: true,
                        loaded: true
                    };
                    parId[i] = j;
                } else {
                    data[j] = {
                        id: j,
                        coaId: list[j].id,
                        layerId: list[j].layer_id,
                        layerNo: list[j].layer_number,
                        supId: list[j].parent_id,
                        layerName: list[j].chart_of_account_name,
                        codeLength: list[j].layer_code_length,
                        layerCode: list[j].chart_of_account_code_user,
                        completeCode: list[j].chart_of_account_code_auto,
                        level: i,
                        parent: parId[i - 1],
                        isLeaf: true,
                        expanded: true,
                        loaded: true
                    };
                }
            }
        }
//        grid.jqGrid('clearGridData');
        grid[0].addJSONData({
            total: 1,
            page: 1,
            records: data.length,
            rows: data
        });

        $("#create-button").val('Submit');
    }

    function showLayerInfo(id) {
        var myGrid = $("#tree-grid");
        var temp = myGrid.jqGrid("getCell", Number(id) + 1, 'isLeaf');
        var leaf = myGrid.jqGrid("getCell", id, 'isLeaf');
//        alert(temp + ',' + leaf);
        if (temp == 'true' || leaf == 'true') {
            $("#layerAdder").attr('hidden', true);
        } else {
            $("#layerAdder").attr('hidden', false);
        }
        temp = myGrid.jqGrid("getCell", id, 'parent');
        if (temp != '') {
            if (myGrid.jqGrid("getCell", temp, 'layerCode') == '') {
//                    alert(myGrid.jqGrid("getCell", temp, 'layerCode'));
                MessageRenderer.render({
                    messageTitle: 'Missing Info',
                    type: 2,
                    messageBody: 'Setup parent layer first.'
                });
                return false;
            }
        }
        temp = id;
        while (true) {
            var temp2 = temp;
            temp = myGrid.jqGrid("getCell", temp, 'parent');
            if (temp == '') {
                parentId = temp2;
                break;
            }
        }
        editId = id;
        var rowData = myGrid.jqGrid("getRowData", id);
        var rowDataParent = myGrid.jqGrid("getRowData", rowData.parent);
        if (rowData.parent != '') {
            $("#parentCode").val(rowDataParent.layerCode);
            $("#parentName").val(rowDataParent.layerName);
        } else {
            $("#parentCode").val($("#entCode").val());
            $("#parentName").val($("#enterPriseName").val());
        }
        $("#completeCode").val(rowData.completeCode);
//        $("#code").attr('maxlength', rowData.codeLength);
        $("#code").val(rowData.layerCode);
        $("#name").val(rowData.layerName);
    }

    function addCode() {
        var myGrid = $("#tree-grid");
        if ($("#code").val().length != myGrid.jqGrid("getCell", editId, 'codeLength')) {
            MessageRenderer.render({
                messageTitle: 'Info Error',
                type: 2,
                messageBody: 'Input code length does not match layer code length.'
            });
            return false;
        }
//        for(var)
        myGrid.jqGrid("setCell", editId, 'layerName', $("#name").val());
        if (myGrid.jqGrid("getCell", editId, 'layerCode') != $("#code").val()) {
            myGrid.jqGrid("setCell", editId, 'layerCode', $("#code").val());
//            calcCode();
            codeCalc();
            changeCode();
        }
    }

    function addLayer() {
        var myGrid = $("#tree-grid");
        var tempData = [];
        var list = dataList;
        for (var i = 0; i <= editId; i++) {
            tempData[i] = data[i];
        }
        var added = 0;
        var temp = Number(editId) + 1;
        var level = Number(myGrid.jqGrid("getCell", editId, 'level')) + 1;
        while (true) {
            tempData[temp] = {
                id: temp,
                coaId: '',
                layerId: data[temp].layerId,
                layerNo: data[temp].layerNo,
                supId: temp == Number(editId) + 1 ? data[temp].parent_id : '',
                layerName: '',
                codeLength: data[temp].codeLength,
                layerCode: '',
                completeCode: '',
                level: level,
                parent: temp - 1,
                isLeaf: false,
                expanded: true,
                loaded: true
            };
            added++;
            level++;
            temp++;
            if (myGrid.jqGrid("getCell", temp, 'isLeaf') == 'true') {
                tempData[temp] = {
                    id: temp,
                    coaId: '',
                    layerId: data[temp].layerId,
                    layerNo: data[temp].layerNo,
                    supId: '',
                    layerName: '',
                    codeLength: data[temp].codeLength,
                    layerCode: '',
                    completeCode: '',
                    level: level,
                    parent: temp - 1,
                    isLeaf: true,
                    expanded: true,
                    loaded: true
                };
                added++;
                temp++;
                break;
            }
        }
        for (var j = Number(editId) + 1; j < data.length; j++, temp++) {
            tempData[temp] = data[j];
            tempData[temp].id = temp;
            if (tempData[temp].parent != editId && tempData[temp].parent != '') {
                tempData[temp].parent = Number(tempData[temp].parent) + added;
            }
        }
        data = [];
        data = tempData;
        myGrid.jqGrid('clearGridData');
        myGrid[0].addJSONData({
            total: 1,
            page: 1,
            records: data.length,
            rows: data
        });
    }

    //    function calcCode() {
    //        var myGrid = $("#tree-grid");
    //        var colData = myGrid.jqGrid("getCol", "layerCode");
    //        var colData2 = myGrid.jqGrid("getCol", "codeLength");
    //        var colData3 = myGrid.jqGrid("getCol", "isLeaf");
    //        var code = $("#entCode").val();
    //        var temp = Number(parentId) + 1;
    //        var temp2 = Number(editId) + 1;
    //        for (var i = temp; i < colData.length; i++) {
    //            if (i > temp2 || colData[i] == '') {
    //                for (var x = 0; x < colData2[i]; x++) {
    //                    code = code + '0';
    //                }
    //            } else if (colData[i] && colData[i] != '') {
    //                code = code + colData[i];
    //            }
    //            if (colData3[i] == 'true') {
    //                break;
    //            }
    //        }
    //        if (colData3[Number(editId) + 1] == 'true') {
    //            var temp3 = myGrid.jqGrid("getCol", "completeCode");
    //            var y = Number(editId) + 1;
    //            for (var z = 0; z < temp3.length; z++) {
    //                if (z != y && temp3[z] == code) {
    //                    MessageRenderer.render({
    //                        messageTitle: 'Info Error',
    //                        type: 2,
    //                        messageBody: 'Ledger code can not be duplicate. Enter different code.'
    //                    });
    //                    myGrid.jqGrid("setCell", editId, 'completeCode', null);
    //                    myGrid.jqGrid("setCell", editId, 'layerCode', null);
    //                    $("#completeCode").val('');
    //                    return false;
    //                }
    //            }
    //        }
    //        $("#completeCode").val(code);
    //        temp = Number(editId);
    //        myGrid.jqGrid("setCell", editId, 'completeCode', $("#completeCode").val());
    //        while (true) {
    //            temp++;
    //            if (colData3[temp] == 'true') {
    //                break;
    //            }
    //            myGrid.jqGrid("setCell", temp, 'completeCode', null);
    //            myGrid.jqGrid("setCell", temp, 'layerCode', null);
    //        }
    //    }

    function codeCalc() {
        var a = Number(editId);
        var myGrid = $("#tree-grid");
        var colData = myGrid.jqGrid("getCol", "layerCode");
        var colData3 = myGrid.jqGrid("getCol", "isLeaf");
        var code = '';
        var length = 0;
        while (true) {
            code = myGrid.jqGrid("getCell", a, "layerCode") + code;
            a = myGrid.jqGrid("getCell", a, "parent");
            if (a == '') {
                length = code.length;
                code = $("#entCode").val() + code;
                break;
            }
        }
        var temp = Number($("#entCodeLength").val());
        while (length < temp) {
            code = code + '0';
            length++;
        }
        if (colData3[Number(editId) + 1] == 'true') {
            var temp3 = myGrid.jqGrid("getCol", "completeCode");
            var y = Number(editId) + 1;
            for (var z = 0; z < temp3.length; z++) {
                if (z != y && temp3[z] == code) {
                    MessageRenderer.render({
                        messageTitle: 'Info Error',
                        type: 2,
                        messageBody: 'Ledger code can not be duplicate. Enter different code.'
                    });
                    myGrid.jqGrid("setCell", editId, 'completeCode', null);
                    myGrid.jqGrid("setCell", editId, 'layerCode', null);
                    $("#completeCode").val('');
                    return false;
                }
            }
        }
        $("#completeCode").val(code);
    }

    function changeCode() {
        var myGrid = $("#tree-grid");
        var temp = Number(editId);
        var temp2 = 0;
        var colData3 = myGrid.jqGrid("getCol", "parent");
        myGrid.jqGrid("setCell", editId, 'completeCode', $("#completeCode").val());
        if (myGrid.jqGrid("getCell", editId, "isLeaf") == 'true') {
            return false;
        }
        while (true) {
            temp++;
            temp2 = temp + 1;
            if (Number(colData3[temp2]) < Number(editId) || colData3[temp2] == '' || temp2 > data.length) {
                break;
            }
            myGrid.jqGrid("setCell", temp, 'completeCode', null);
            myGrid.jqGrid("setCell", temp, 'layerCode', null);
        }
    }

    function createChartOfAccount() {
        var data = $("#gFormChartOfAccount").serializeArray();
        var myGrid = $("#tree-grid");
        var gd = myGrid.jqGrid('getRowData');
        var length = gd.length;
        var ids = [];
        for (var i = 0; i < length; i++) {
            if ($("#create-button").val() == 'Submit' && gd[i].coaId != '') {
                data.push({'name': 'items.chartOfAccounts[' + i + '].id', 'value': gd[i].coaId});
            }
            data.push({'name': 'items.chartOfAccounts[' + i + '].gridId', 'value': gd[i].id});
            data.push({'name': 'items.chartOfAccounts[' + i + '].chartOfAccountLayer.id', 'value': gd[i].layerId});
            data.push({'name': 'items.chartOfAccounts[' + i + '].chartOfAccountCodeAuto', 'value': gd[i].completeCode});
            data.push({'name': 'items.chartOfAccounts[' + i + '].chartOfAccountCodeUser', 'value': gd[i].layerCode});
            data.push({'name': 'items.chartOfAccounts[' + i + '].chartOfAccountName', 'value': gd[i].layerName});
            data.push({'name': 'items.chartOfAccounts[' + i + '].parentId', 'value': gd[i].parent});
            data.push({
                'name': 'items.chartOfAccounts[' + i + '].enterpriseConfiguration.id',
                'value': $("#enterpriseConfiguration").val()
            });
            data.push({
                'name': 'items.chartOfAccounts[' + i + '].parentCode',
                'value': gd[i].parent ? myGrid.jqGrid("getCell", gd[i].parent, 'layerCode') : $("#entCode").val()
            });
            data.push({'name': 'items.chartOfAccounts[' + i + '].isActive', 'value': true});
        }
        data.push({'name': 'length', 'value': length});
        data.push({'name': 'lengthOld', 'value': dataList.length});

        if ($("#create-button").val() == 'Submit') {
            var actionUrl = "${request.contextPath}/${params.controller}/updateChartOfAccount";
        } else {
            var actionUrl = "${request.contextPath}/${params.controller}/saveChartOfAccount";
        }
        SubmissionLoader.showTo();
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

    function executePostCondition(result) {
        MessageRenderer.render(result);
        if (result.type == 1) {
            if ('${update}' == '0') {
                location.reload(true);
            } else {
                $("#tree").dynatree("destroy");
                jQuery.ajax({
                    type: 'post',
                    url: "${request.contextPath}/${params.controller}/listChartOfAccount?entId=" + $("#enterpriseConfiguration").val(),
                    success: function (data, textStatus) {
                        tree(data);
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        if(XMLHttpRequest.status = 0){
                            MessageRenderer.renderErrorText("Network Problem: Time out");
                        } else{
                            MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                        }
                    },
                    complete: function () {
                    },
                    dataType: 'json'
                });
                var x = $("#enterpriseConfiguration").val();
                var y = $("#entCodeLength").val();
                var z = $("#entLayers").val();
                var a = $("#entCode").val();
                clear_form('#gFormChartOfAccount');
                $("#enterpriseConfiguration").val(x);
                $("#entCodeLength").val(y);
                $("#entLayers").val(z);
                $("#entCode").val(a);
            }
        }
    }

    function tree(data) {
        var final = [];
        var finalind = 3;
        var list = null;
        list = ${layerList};
        dataList = ${layerList};
        if ('${update}' == '0') {
            $("#create-button").val('Create');
            var grpName = 'Asset';
            for (var i = 0; i < 4; i++) {
                if (i == 1) {
                    grpName = "Liability";
                } else if (i == 2) {
                    grpName = "Income";
                } else if (i == 3) {
                    grpName = "Expense";
                }
                final[i] = {
                    id: addedId,
                    title: grpName,
                    isFolder: true,
                    children: [],
                    parent: 0,
                    codeLength: list[0].layer_code_length,
                    layerCode: '',
                    completeCode: '',
                    layerId: list[0].layer_id,
                    layerNo: list[0].layer_number,
                    accountType: ''
                };
                addedId--;
            }
        } else {
            if (data != null) {
                list = data.coa;
                dataList = data.coa;
            }
            $("#create-button").val('Submit');
            var x = [];
            var ind = 0;
            var par = -1;
            for (var z = list.length - 1; z >= 0; z--) {
                if (list[z].layer_number == $("#entLayers").val()) {
                    if (Number(list[z].id) != par && list[z].parent_id != '0') {
                        x[ind] = {
                            id: list[z].id,
                            title: list[z].chart_of_account_name,
                            parent: list[z].parent_id,
                            codeLength: list[z].layer_code_length,
                            layerCode: list[z].chart_of_account_code_user,
                            completeCode: list[z].chart_of_account_code_auto,
                            layerId: list[z].layer_id,
                            layerNo: list[z].layer_number,
                            accountType: list[z].account_type
                        };
                        par = Number(list[z].parent_id);
                        ind++;
                    }
                } else {
                    ind--;
                    var temp = [];
                    var w = 0;
                    for (ind; ind >= 0; ind--) {
                        if (x[ind].parent == list[z].id) {
                            temp[w] = x[ind];
                            x[ind] = null;
                            w++;
                        } else {
                            break;
                        }
                    }
                    if (ind > 0) {
                        for (var h = ind; h >= 0; h--) {
                            if (x[h] && x[h].parent == list[z].id) {
                                temp[w] = x[h];
                                x[h] = null;
                                w++;
                            }
                        }
                    }
                    ind++;
                    x[ind] = {
                        id: list[z].id,
                        title: list[z].chart_of_account_name,
                        isFolder: true,
                        children: temp,
                        parent: list[z].parent_id,
                        codeLength: list[z].layer_code_length,
                        layerCode: list[z].chart_of_account_code_user,
                        completeCode: list[z].chart_of_account_code_auto,
                        layerId: list[z].layer_id,
                        layerNo: list[z].layer_number,
                        accountType: list[z].account_type
                    };
                    if (list[z].parent_id == '0') {
                        final[finalind] = x[ind];
                        finalind--;
                    }
                    ind++;
                }
            }
        }

        $("#tree").dynatree({
            onActivate: function (node) {
                if (node.data.enterprise) {
                    editNode = null;
                    return false;
                }
                editNode = node;
                setCompleteCode();
                var par = node.getParent();
                $("#parentCode").val(par.data.layerCode);
                $("#parentName").val(par.data.title);
                $("#completeCode").val(node.data.completeCode);
//                $("#code").attr('maxlength', node.data.codeLength);
                $("#code").val(node.data.layerCode);
                $("#name").val(node.data.title);
            },
            activeVisible: true,
            clickFolderMode: 1,
            persist: true,
            children: {
                id: $("#enterpriseConfiguration").val(),
                title: $("#enterPriseName").val(),
                layerCode: $("#entCode").val(),
                isFolder: true,
                children: final,
                enterprise: true
            }
        });
    }

    function setData() {
        var x = Number(editNode.data.codeLength);
        var y = $("#code").val();
        if (x > y.length) {
            MessageRenderer.render({
                messageTitle: 'Info Error',
                type: 2,
                messageBody: 'Input code length does not match layer code length.'
            });
            return false;
        }
        editNode.setTitle($("#name").val());
        editNode.data.layerCode = $("#code").val();
        setCompleteCode();
    }

    function setCompleteCode() {
        var code = editNode.data.layerCode;
        var par = editNode.getParent();
        while (!par.data.enterprise) {
            code = par.data.layerCode + code;
            par = par.getParent();
        }
        var codeLength = Number($("#entCodeLength").val());
        for (var i = code.length; i < codeLength; i++) {
            code = code + 0;
        }
        code = par.data.layerCode + code;
        editNode.data.completeCode = code;
        $("#completeCode").val(code);
    }

    function addChild() {
        if (editNode == null || !editNode.data.isFolder) {
            return false;
        }
        var accountType = '';
        var tempNode = editNode;
        while(true){
            if(tempNode.data.parent == 0){
                accountType = tempNode.data.title;
                break;
            }else{
                tempNode = tempNode.getParent();
            }
        }
        var list = ${layerList};
        var layers = ${layers};
        var level = editNode.getLevel();
        level = Number(level) - 1;
        var leaf = Number($("#entLayers").val()) - 1;
        var node = null;
        if (level == leaf) {
            node = {
                id: addedId,
                title: 'New Branch',
                parent: editNode.data.id,
                codeLength: layers[level].layer_code_length,
                layerCode: '',
                completeCode: '',
                layerId: layers[level].layer_id,
                layerNo: layers[level].layer_number,
                accountType: accountType
            };
        } else {
            node = {
                id: addedId,
                title: 'New Branch',
                isFolder: true,
                children: '',
                expand: true,
                parent: editNode.data.id,
                codeLength: layers[level].layer_code_length,
                layerCode: '',
                completeCode: '',
                layerId: layers[level].layer_id,
                layerNo: layers[level].layer_number,
                accountType: accountType
            };
        }
        editNode.addChild(node);
        addedId--;
    }

    function createOrUpdate() {
        var data = $("#gFormChartOfAccount").serializeArray();
        var tree = $("#tree").dynatree("getTree");
        var node = tree.getNodeByKey('_3');
        var length = 0;
        for (var i = 4; node != null; i++) {
            if ($("#create-button").val() == 'Submit' && Number(node.data.id) > -1) {
                data.push({'name': 'items.chartOfAccounts[' + length + '].id', 'value': node.data.id});
            }
            data.push({'name': 'items.chartOfAccounts[' + length + '].gridId', 'value': node.data.id});
            data.push({
                'name': 'items.chartOfAccounts[' + length + '].chartOfAccountLayer.id',
                'value': node.data.layerId
            });
            data.push({
                'name': 'items.chartOfAccounts[' + length + '].chartOfAccountCodeAuto',
                'value': node.data.completeCode
            });
            data.push({
                'name': 'items.chartOfAccounts[' + length + '].chartOfAccountCodeUser',
                'value': node.data.layerCode
            });
            data.push({'name': 'items.chartOfAccounts[' + length + '].chartOfAccountName', 'value': node.data.title});
            data.push({
                'name': 'items.chartOfAccounts[' + length + '].parentId',
                'value': node.data.parent == '0' ? '' : node.data.parent
            });
            data.push({
                'name': 'items.chartOfAccounts[' + length + '].enterpriseConfiguration.id',
                'value': $("#enterpriseConfiguration").val()
            });
            data.push({
                'name': 'items.chartOfAccounts[' + length + '].parentCode',
                'value': node.getParent().data.layerCode
            });
            data.push({
                'name': 'items.chartOfAccounts[' + length + '].accountType',
                'value': node.data.accountType
            });
            data.push({'name': 'items.chartOfAccounts[' + length + '].isActive', 'value': true});
            node = tree.getNodeByKey('_' + i);
            length++;
        }
        data.push({'name': 'length', 'value': length});
        data.push({'name': 'lengthOld', 'value': dataList.length});

        if ($("#create-button").val() == 'Submit') {
            var actionUrl = "${request.contextPath}/${params.controller}/updateChartOfAccount";
        } else {
            var actionUrl = "${request.contextPath}/${params.controller}/saveChartOfAccount";
        }
        SubmissionLoader.showTo();
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

</script>