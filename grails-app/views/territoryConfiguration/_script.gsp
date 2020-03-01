<script type="text/javascript" language="Javascript">
    var subAreaId = -1;
    var deletedIds = [];
    var deleteSize = 0;

    $(document).ready(function () {
        $('#ui-widget-header-text').html('TerritoryConfiguration');
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }
        var option;
        $("#businessUnitList").flexbox('Select Business Unit', {
            watermark: "Select Business Unit",
            width: 317,
            onSelect: function () {
                $("#businessUnitConfiguration").val($('#businessUnitList_hidden').val());
            }
        });
        $('#businessUnitList_input').blur(function () {
            if ($('#businessUnitList_input').val() == '') {
                $("#businessUnitConfiguration").val("");
            }
        });

        $("#gFormTerritoryConfiguration").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormTerritoryConfiguration").validationEngine('attach');
        resetAll();
        $("#jqgrid-grid").jqGrid({
            url: '${resource(dir:'territoryConfiguration', file:'list')}',
            datatype: "json",
            colNames: [
                'SL',
                'ID',
                'Territory Name',
                'Enterprise',
                'Active'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30, sortable: false, align: 'left'},
                {name: 'id', index: 'id', width: 0, hidden: true},
                {name: 'name', index: 'name', width: 150, align: 'left'},
                {name: 'enterpriseConfiguration', index: 'enterpriseConfiguration', width: 100, align: 'left'},
                {name: 'isActive', index: 'isActive', width: 50, align: 'left'}
            ],
            rowNum: 50,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#jqgrid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "asc",
            caption: "Territory List",
            autowidth: true,
            height: true,
            scrollOffset: 0,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                executeEditTerritoryConfiguration();
            }
        });
        $("#jqgrid-grid").jqGrid('navGrid', '#jqgrid-pager', {edit: false, add: false, del: false, search: false});
//     $("#jqgrid-grid").gridResize({minWidth:350,maxWidth:800,minHeight:200});
        $(".ui-pg-selbox").children().each(function () {
            if ($(this).val() == -1) {
                $(this).html('All')
            }
        });
        initializeGrid();
    });

    function initializeGrid() {
        $("#territory-detail-grid").jqGrid({
            datatype: "json",
            colNames: [
                'ID',
                'Country',
                '',
                'Division',
                '',
                'District',
                '',
                'Thana',
                '',
                'Union',
                '',
                'Road',
                'Para/Locality',
                'Geo Location',
                'Enabled',
//                'Is Active',
                'Activate',
                'Delete'
            ],
            colModel: [
                {name: 'id', index: 'id', width: 100, align: 'left', hidden: true},
                {name: 'countryInfo', index: 'countryInfo', width: 100, align: 'left'},
                {name: 'countryInfoId', index: 'countryInfoId', width: 100, align: 'left', hidden: true},
                {name: 'division', index: 'division', width: 100, align: 'left'},
                {name: 'divisionId', index: 'divisionId', width: 100, align: 'left', hidden: true},
                {name: 'district', index: 'district', width: 100, align: 'left'},
                {name: 'districtId', index: 'districtId', width: 100, align: 'left', hidden: true},
                {name: 'thanaUpazilaPouroshova', index: 'thanaUpazilaPouroshova', width: 100, align: 'left'},
                {
                    name: 'thanaUpazilaPouroshovaId',
                    index: 'thanaUpazilaPouroshovaId',
                    width: 100,
                    align: 'left',
                    hidden: true
                },
                {name: 'unionInfo', index: 'unionInfo', width: 100, align: 'left'},
                {name: 'unionInfoId', index: 'unionInfoID', width: 100, align: 'left', hidden: true},
                {name: 'road', index: 'road', width: 100, align: 'left'},
                {name: 'paraOrLocality', index: 'paraOrLocality', width: 100, align: 'left'},
                {name: 'geoLocation', index: 'geoLocation', width: 100, align: 'left'},
                {name: 'isActive', index: 'isActive', width: 50, align: 'left', hidden: true},
                {
                    name: 'select', width: 65, align: 'center',
                    formatter: cboxFormatter, formatoptions: {disabled: false},
                    edittype: "checkbox", editoptions: {value: "true:false"},
                    stype: "select", searchoptions: {
                    sopt: ["eq", "ne"],
                    value: ":Any;true:true;false:false"
                }
                },
                {
                    name: 'remove', index: 'remove', width: 50, align: 'center'
                }

            ],
            rowNum: -1,
//            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100],
//            pager: '#territory-detail-grid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Geo Location(s)" + '<span class="mendatory_field"> *</span>',
            autowidth: true,
            height: true,
            scrollOffset: 0,
            rownumbers:true,
            loadComplete: function () {
                var ids = $("#territory-detail-grid").jqGrid('getDataIDs');
                var gridData = $("#territory-detail-grid").jqGrid('getRowData');
//                alert(JSON.stringify(gridData));
                for (key in ids) {
                    $("#territory-detail-grid").jqGrid('setRowData', ids[key], {remove: '<a href="javascript:territoryConfigurationIdwiseDelete('+ids[key]+')" " class="ui-icon ui-icon-trash" title="Delete"></a>'});

                }
            },
            onSelectRow: function (rowid, status) {
                executeEditTerritorySubArea(rowid);
            }
        });
        /*$("#territory-detail-grid").jqGrid('navGrid', '#territory-detail-grid-pager', {
            edit: false,
            add: false,
            del: false,
            search: false
        });*/
    }

    function addNewItemToCollectionGrid() {
        var myGrid = $("#territory-detail-grid");
        if (!$("#geoLocation").val()) {
            MessageRenderer.renderErrorText("Please input Geo Location");
            return false;
        }
        if (!$("#paraOrLocality").val()) {
            MessageRenderer.renderErrorText("Please input Para/Locality");
            return false;
        }
        if (!$("#road").val()) {
            MessageRenderer.renderErrorText("Please input Road");
            return false;
        }
        if (!$("#countryInfo").val()) {
            MessageRenderer.renderErrorText("Please Select Country");
            return false;
        }
        if (!$("#division").val()) {
            MessageRenderer.renderErrorText("Please Select Division");
            return false;
        }
        if (!$("#district").val()) {
            MessageRenderer.renderErrorText("Please Select District");
            return false;
        }
        if ($("#countryInfo").val()) {
            var countryInfo = $("#countryInfo option:selected").text();
            var countryInfoId = $("#countryInfo").val();
        }
        if ($("#division").val()) {
            var division = $("#division option:selected").text();
            var divisionId = $("#division").val();
        }
        if ($("#district").val()) {
            var district = $("#district option:selected").text();
            var districtId = $("#district").val();
        }
        if ($("#thanaUpazilaPouroshova").val()) {
            var thanaUpazilaPouroshova = $("#thanaUpazilaPouroshova option:selected").text();
            var thanaUpazilaPouroshovaId = $("#thanaUpazilaPouroshova").val();
        }
        if ($("#unionInfo").val()) {
            var unionInfo = $("#unionInfo option:selected").text();
            var unionInfoId = $("#unionInfo").val();
        }

        if ($('#add-button').val() == 'Update') {
            var selRowId = myGrid.jqGrid('getGridParam', 'selrow');
            myGrid.jqGrid('setCell', selRowId, 'geoLocation', $("#geoLocation").val());
            myGrid.jqGrid('setCell', selRowId, 'paraOrLocality', $("#paraOrLocality").val());
            myGrid.jqGrid('setCell', selRowId, 'road', $("#road").val());
            myGrid.jqGrid('setCell', selRowId, 'countryInfo', countryInfo);
            myGrid.jqGrid('setCell', selRowId, 'countryInfoId', countryInfoId);
            myGrid.jqGrid('setCell', selRowId, 'division', division);
            myGrid.jqGrid('setCell', selRowId, 'divisionId', divisionId);
            myGrid.jqGrid('setCell', selRowId, 'district', district);
            myGrid.jqGrid('setCell', selRowId, 'districtId', districtId);
            myGrid.jqGrid('setCell', selRowId, 'thanaUpazilaPouroshova', thanaUpazilaPouroshova);
            myGrid.jqGrid('setCell', selRowId, 'thanaUpazilaPouroshovaId', thanaUpazilaPouroshovaId);
            myGrid.jqGrid('setCell', selRowId, 'unionInfo', unionInfo);
            myGrid.jqGrid('setCell', selRowId, 'unionInfoId', unionInfoId);


//            if (document.getElementById('isSubActive').checked) {
//                myGrid.jqGrid('setCell', selRowId, 'isActiveM', "<font style='font-weight: bold;color: green;'>[&radic;]</font>");
//                myGrid.jqGrid('setCell', selRowId, 'isActive', true);
//                myGrid.jqGrid('setCell', selRowId, 'select', true);
//            } else {
//                myGrid.jqGrid('setCell', selRowId, 'isActiveM', "<font style='font-weight: bold;color: red;'>[X]</font>");
//                myGrid.jqGrid('setCell', selRowId, 'isActive', false);
//                myGrid.jqGrid('setCell', selRowId, 'select', false);
//            }
//            $("#geoLocation").val('');
//            $("#paraOrLocality").val('');
//            $("#road").val('');
        } else {
            var dataItem = {
                id: '',
                countryInfo: countryInfo,
                countryInfoId: countryInfoId,
                division: division,
                divisionId: divisionId,
                district: district,
                districtId: districtId,
                thanaUpazilaPouroshova: thanaUpazilaPouroshova,
                thanaUpazilaPouroshovaId: thanaUpazilaPouroshovaId,
                unionInfo: unionInfo,
                unionInfoId: unionInfoId,
                road: $("#road").val(),
                paraOrLocality: $("#paraOrLocality").val(),
                geoLocation: $("#geoLocation").val(),
                isActive: 'true',
                select: 'true',
                remove: '<a  href="javascript:removeSubAreaRow(' + subAreaId + ')" class="ui-icon ui-icon-trash" title="Delete"></a>'
            };
            myGrid.addRowData(subAreaId, dataItem, "last");
            subAreaId--;
        }
        gridCollection = myGrid.jqGrid('getRowData');
        clearSubArea();
        //        alert(gridCollection[0].road);
    }
    function removeSubAreaRow(rowId) {
        $("#territory-detail-grid").jqGrid("delRowData", rowId);
    }
    function territoryConfigurationIdwiseDelete(territorySubAreaId) {
//        alert(territorySubAreaId);
        if(id=null)
        {
            alert("Please select a Sub Area to edit");
            return false;
        }
        else
        $.ajax({

            type: "POST",
            url: "${resource(dir:' territorySubArea', file:'territorysubAreaDelete')}?id=" + territorySubAreaId,
            success: function (message) {
                MessageRenderer.render(message);
            },
            dataType: 'json'
        });
    }

    function executePreConditionForEditTerritorySubArea(territorySubAreaId) {
        if (territorySubAreaId == null) {
            alert("Please select a Sub Area to edit");
            return false;
        }
        return true;
    }
    function executeEditTerritorySubArea(territorySubAreaId) {
        var territorySubAreaId = territorySubAreaId;
        var territorySubArea;
        if (executePreConditionForEditTerritorySubArea(territorySubAreaId)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:' territorySubArea', file:'edit')}?id=" + territorySubAreaId,
                success: function (entity) {
                    executeEditTerritorySubAreaSetData(entity);
                },
                dataType: 'json'
            });
        }
        return territorySubArea;
    }
    function executeEditTerritorySubAreaSetData(territorySubArea) {
        $("#geoLocation").val(territorySubArea.geoLocation);
        $("#paraOrLocality").val(territorySubArea.paraOrLocality);
        $("#road").val(territorySubArea.road);
        if (territorySubArea.countryInfo) {
            $('#countryInfo').val(territorySubArea.countryInfo.id).attr("selected", "selected");
            selectDivision(territorySubArea.countryInfo.id, territorySubArea);
        }
        else {
            $('#countryInfo').val(territorySubArea.countryInfo);
        }
        if (territorySubArea.division) {
            $('#division').val(territorySubArea.division.id).attr("selected", "selected");
            selectDistrict(territorySubArea.division.id, territorySubArea);
        }
        else {
            $('#division').val(territorySubArea.division);
        }
        if (territorySubArea.district) {
            $('#district').val(territorySubArea.district.id).attr("selected", "selected");
            selectThana(territorySubArea.district.id, territorySubArea);
        }
        else {
            $('#district').val(territorySubArea.district);
        }
        if (territorySubArea.thanaUpazilaPouroshova) {
            $('#thanaUpazilaPouroshova').val(territorySubArea.thanaUpazilaPouroshova.id).attr("selected", "selected");
            selectUnion(territorySubArea.thanaUpazilaPouroshova.id, territorySubArea);
        }
        else {
            $('#thanaUpazilaPouroshova').val(territorySubArea.thanaUpazilaPouroshova);
        }
        if (territorySubArea.unionInfo) {
            $('#unionInfo').val(territorySubArea.unionInfo.id).attr("selected", "selected");
        }
        else {
            $('#unionInfo').val(territorySubArea.unionInfo);
        }
        if (territorySubArea.isActive) {
            $('#isActive').attr('checked', true);
            $('#isActiveVal').val(true);
        } else {
            $('#isActive').attr('checked', false);
            $('#isActiveVal').val(false);
        }


        $('#add-button').attr('value', 'Update');
//            $('#remove-button').show();


    }

    function deleteTerritorySubAreaConfiguration() {
        var myGrid = $("#territory-detail-grid");
        var selRowId = myGrid.jqGrid('getGridParam', 'selrow');
        myGrid.jqGrid('delRowData', selRowId);
        if (selRowId >= 0) {
            deletedIds[deleteSize] = selRowId;
            deleteSize++;
        }
        clearSubArea();
    }

    function getSelectedTerritoryConfigurationId() {
        var territoryConfigurationId = null;
        var rowid = $("#jqgrid-grid").jqGrid('getGridParam', 'selrow');
        if (rowid) {
            territoryConfigurationId = $("#jqgrid-grid").jqGrid('getCell', rowid, 'id');
        }
        if (territoryConfigurationId == null) {
            territoryConfigurationId = $('#gFormTerritoryConfiguration input[name = id]').val();
        }
        return territoryConfigurationId;
    }

    function loadBusinessUnit(id, key) {
        jQuery.ajax({
            type: "POST",
            url: '${resource(dir:'businessUnitConfiguration', file:'listBusinessUnit')}?id=' + id,
            dataType: 'json',
            success: function (data, textStatus) {
//                var options = '<option value="">Select Business Unit</option>';
//                $.each(data, function (key, val) {
//                    options += '<option value="' + val.id + '">' + val.name + '</option>';
////                    console.log(val)
//                });
                $("#businessUnitList").empty();
                $("#businessUnitList").flexbox(data, {
                    watermark: "Select Business Unit",
                    width: 317,
                    onSelect: function () {
                        $("#businessUnitConfiguration").val($('#businessUnitList_hidden').val());
                    }
                });
                $('#businessUnitList_input').blur(function () {
                    if ($('#businessUnitList_input').val() == '') {
                        $("#businessUnitConfiguration").val("");
                    }
                });
//                $("#businessUnitConfiguration").html(options);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                MessageRenderer.renderErrorText(XMLHttpRequest.responseText)
            },
            complete: function () {
                if (key) {
                    $("#businessUnitList").setValue(key.name);
                    $("#businessUnitConfiguration").val(key.id);
                }
            }
        });
    }

    function checkValue() {
        alert($("#enterpriselist").val());
    }

    function executePreConditionTerritoryConfiguration() {
        if (!$("#gFormTerritoryConfiguration").validationEngine('validate')) {
            return false;
        }
        return true;
    }
    function executeAjaxTerritoryConfiguration() {
        if (!executePreConditionTerritoryConfiguration()) {
            return false;
        }
        var businessUnitConfiguration = $("#businessUnitConfiguration").val();
        if(!businessUnitConfiguration){
            MessageRenderer.renderErrorText("Please Select Business Unit");
            return false
        }
        var actionUrl = null;
        if ($('#create-button').val() == 'Update') {
            actionUrl = "${request.contextPath}/${params.controller}/update";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/create";
            if (subAreaId == -1) {
                MessageRenderer.renderErrorText("At least one Geo Location is needed to create a Territory.");
                return false;
            }
        }
        var data = jQuery("#gFormTerritoryConfiguration").serialize();
        var gridCollection = jQuery("#territory-detail-grid").jqGrid('getRowData');
        var dataLength = gridCollection.length;
        for (var i = 0; i < dataLength; i++) {
            data = data + '&items.territorySubArea[' + i + '].id=' + gridCollection[i].id;
            data = data + '&items.territorySubArea[' + i + '].countryInfo.id=' + gridCollection[i].countryInfoId;
            data = data + '&items.territorySubArea[' + i + '].division.id=' + gridCollection[i].divisionId;
            data = data + '&items.territorySubArea[' + i + '].district.id=' + gridCollection[i].districtId;
            data = data + '&items.territorySubArea[' + i + '].thanaUpazilaPouroshova.id=' + gridCollection[i].thanaUpazilaPouroshovaId;
            data = data + '&items.territorySubArea[' + i + '].unionInfo.id=' + gridCollection[i].unionInfoId;
            data = data + '&items.territorySubArea[' + i + '].road=' + gridCollection[i].road;
            data = data + '&items.territorySubArea[' + i + '].paraOrLocality=' + gridCollection[i].paraOrLocality;
            data = data + '&items.territorySubArea[' + i + '].geoLocation=' + gridCollection[i].geoLocation;
            data = data + '&items.territorySubArea[' + i + '].isActive=' + gridCollection[i].isActive;
        }
        data = data + '&quantity=' + dataLength;
        data = data + '&deleted=' + deletedIds;
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: data,
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionTerritoryConfiguration(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-grid").trigger("reloadGrid");
                    resetAll();
                    $('#territory-detail-grid').clearGridData();

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
    function executePostConditionTerritoryConfiguration(result) {
        if (result.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            resetAll();
            $('#territory-detail-grid').clearGridData();
//            location.reload();
        }
        MessageRenderer.render(result);
    }
    function deleteAjaxTerritoryConfiguration() {    // Delete record
        var territoryConfigurationId = getSelectedTerritoryConfigurationId();
        if (executePreConditionForDeleteTerritoryConfiguration(territoryConfigurationId)) {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-territoryConfiguration").dialog({
                resizable: false,
                height: 150,
                modal: true,
                title: 'Delete alert',
                buttons: {
                    'Delete item(s)': function () {
                        $(this).dialog('close');
                        $.ajax({
                            type: "POST",
                            dataType: "json",
                            data: jQuery("#gFormTerritoryConfiguration").serialize(),
                            url: "${resource(dir:'territoryConfiguration', file:'delete')}",
                            success: function (message) {
                                executePostConditionForDeleteTerritoryConfiguration(message);
                            }
                        });
                    },
                    Cancel: function () {
                        $(this).dialog('close');
                    }
                }
            }); //end of dialog
        }
    }

    function executePreConditionForEditTerritoryConfiguration(territoryConfigurationId) {
        if (territoryConfigurationId == null) {
            alert("Please select a territoryConfiguration to edit");
            return false;
        }
        return true;
    }

    function executeEditTerritoryConfiguration() {
        var territoryConfigurationId = getSelectedTerritoryConfigurationId();
//        alert(territoryConfigurationId);
        if (executePreConditionForEditTerritoryConfiguration(territoryConfigurationId)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'territoryConfiguration', file:'edit')}?id=" + territoryConfigurationId,
                success: function (entity) {
//                    alert(JSON.stringify(entity));
                    executePostConditionForEditTerritoryConfiguration(entity);
                },
                dataType: 'json'
            });
        }
    }
    function executePostConditionForEditTerritoryConfiguration(data) {
//        alert(JSON.stringify(data));
        if (data == null) {
            alert('Selected territoryConfiguration might have been deleted by someone');  //Message renderer
        } else {
            showTerritoryConfiguration(data);
        }
    }
    function executePreConditionForDeleteTerritoryConfiguration(territoryConfigurationId) {
        if (territoryConfigurationId == null) {
            alert("Please select a territoryConfiguration to delete");
            return false;
        }
        return true;
    }
    function executePostConditionForDeleteTerritoryConfiguration(data) {
        if (data.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            resetAll();
            $('#territory-detail-grid').clearGridData();
        }
        MessageRenderer.render(data)
    }
    function showTerritoryConfiguration(entity) {
        clearSubArea();
        subAreaId = -1;
        deletedIds = [];
        deleteSize = 0;
        var territory = entity.territoryConfiguration;
        var enterprise = entity.enterpriseConfiguration;
        var business = entity.businessUnitConfiguration;
        $('#labelCol').attr('hidden', false);
        $('#fieldCol').attr('hidden', false);


        $('#gFormTerritoryConfiguration input[name = id]').val(territory.id);
        $('#gFormTerritoryConfiguration input[name = version]').val(territory.version);

        if (enterprise) {
            $('#enterpriselist').setValue(enterprise.name);
            $("#enterpriseConfiguration").val(enterprise.id);
            loadBusinessUnit(enterprise.id, business);
        }
        else {
            $('#enterpriselist').setValue(enterprise.id);
        }

        $('#name').val(territory.name);


        if (business) {
            $('#businessUnitConfiguration').setValue(business.id);
        }
        else {
            $('#businessUnitConfiguration').val(business.id);
        }

        $('#create-button').attr('value', 'Update');
        $('#cancel-button').attr('value', 'Cancel');
        $('#delete-button').show();

        $("#territory-detail-grid").jqGrid().setGridParam({url: '${resource(dir:'territorySubArea', file:'list')}?id=' + territory.id}).trigger("reloadGrid");
    }
    function executeSearchTerritoryConfiguration(fieldName, fieldValue) {
        if (executePreConditionForSearchTerritoryConfiguration(fieldName, fieldValue)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'territoryConfiguration', file:'search')}?fieldName=" + fieldName + "&fieldValue=" + fieldValue,
                success: function (entity) {
                    executePostConditionForSearchTerritoryConfiguration(entity, fieldName, fieldValue);
                },
                dataType: 'json'
            });
        }
    }
    function executePreConditionForSearchTerritoryConfiguration(fieldName, fieldValue) {
        // trim field vales before process.
        $('#' + fieldName).val($.trim($('#' + fieldName).val()));
        if (fieldValue == '' || fieldValue == null) {
            resetAll();
            return false;
        }
        return true;
    }
    function executePostConditionForSearchTerritoryConfiguration(data, fieldName, fieldValue) {
        if (data == null) {
            resetAll();
            $('#' + fieldName).val(fieldValue); // Set search field with fieldValue
        } else {
            showTerritoryConfiguration(data);
        }
    }

    function resetAll() {
        reset_territory_form('#gFormTerritoryConfiguration');
        $('#labelCol').attr('hidden', true);
        $('#fieldCol').attr('hidden', true);
        $('#labelSubCol').attr('hidden', true);
        $('#fieldSubCol').attr('hidden', true);
        $("#territory-detail-grid").jqGrid("clearGridData", true);
        $('#countryInfo').val('');
        $('#division').val('');
        $('#district').val('');
        $('#thanaUpazilaPouroshova').val('');
        $('#unionInfo').val('');
        deletedIds = [];
        deleteSize = 0;
        subAreaId = -1;
    }

    function statusChange() {
        clearSubArea();
        var myGrid = $("#territory-detail-grid");
        var ids = myGrid.jqGrid('getDataIDs');
        if (!document.getElementById('isActive').checked) {
            $("#isActiveVal").val(false);
            for (var i = 0; i < ids.length; i++) {
//                $("#territory-detail-grid").jqGrid('setCell', ids[i], 'isActiveM', "<font style='font-weight: bold;color: red;'>[X]</font>");
                $("#territory-detail-grid").jqGrid('setCell', ids[i], 'isActive', false);
                $("#territory-detail-grid").jqGrid('setCell', ids[i], 'select', 'false');
            }
        } else {
            $("#isActiveVal").val(true);
            for (var i = 0; i < ids.length; i++) {
//                $("#territory-detail-grid").jqGrid('setCell', ids[i], 'isActiveM', "<font style='font-weight: bold;color: green;'>[&radic;]</font>");
                $("#territory-detail-grid").jqGrid('setCell', ids[i], 'isActive', true);
                $("#territory-detail-grid").jqGrid('setCell', ids[i], 'select', 'true');
            }
        }
    }

    function clearSubArea() {
        $('#add-button').val('Add');
//        $('#remove-button').hide();
        $("#geoLocation").val('');
        $("#paraOrLocality").val('');
        $("#road").val('');
//        $("#countryInfo").val('');
//        $("#division").val('');
//        $("#district").val('');
//        $("#thanaUpazilaPouroshova").val('');
//        $("#unionInfo").val('');
        $('#labelSubCol').attr('hidden', true);
        $('#fieldSubCol').attr('hidden', true);
    }

    function cboxFormatter(cellvalue, options, rowObject) {
        return '<input type="checkbox" ' + (cellvalue == 'true' ? ' checked="checked"' : '') +
                'value="false" onchange="if(this.checked){' +
                'enableGeo(' + options.rowId + ')' +
                '}else{' +
                'disableGeo(' + options.rowId + ')' +
                '};"/>';
    }

    function enableGeo(id) {
//        $("#territory-detail-grid").jqGrid('setCell', id, 'isActiveM', "<font style='font-weight: bold;color: green;'>[&radic;]</font>");
//        if ($("#isActiveVal").val() == 'true') {
//            $("#territory-detail-grid").jqGrid('setCell', id, 'isActive', true);
//        } else {
//            $("#territory-detail-grid").jqGrid('setCell', id, 'select', 'false');
//            alert("The Territory must be activated first.");
//        }
        $("#territory-detail-grid").jqGrid('setCell', id, 'isActive', true);
    }

    function disableGeo(id) {

//        $("#territory-detail-grid").jqGrid('setCell', id, 'isActiveM', "<font style='font-weight: bold;color: red;'>[X]</font>");
//        if ($("#isActiveVal").val() == 'true') {
//            $("#territory-detail-grid").jqGrid('setCell', id, 'isActive', false);
//        } else {
//            $("#territory-detail-grid").jqGrid('setCell', id, 'select', 'true');
//            alert("The Territory must be activated first.");
//        }
        $("#territory-detail-grid").jqGrid('setCell', id, 'isActive', false);
    }

    function selectDivision(id, territorySubArea) {
        var options = '<option value="">Please Select</option>';
        $("#district").html(options);
        $("#thanaUpazilaPouroshova").html(options);
        $("#unionInfo").html(options);
        if (id == '') {
            $("#division").html(options);
            return false;
        }
        jQuery.ajax({
            type: 'post',
            url: "${resource(dir:'territoryConfiguration', file:'fetchDropdownList')}?id=" + id + '&sl=' + 1,
            success: function (data) {
                options = '<option value="">Select division</option>';
                $.each(data, function (key, val) {
                    options += '<option value="' + val.id + '">' + val.name + '</option>';
                })
                $("#division").html(options);
            },
            complete: function () {
                if (territorySubArea != 0) {
                    $('#division').val(territorySubArea.division.id).attr("selected", "selected");
                    selectDistrict(territorySubArea.division.id, territorySubArea);
                }
            },
            dataType: 'json'
        });
    }

    function selectDistrict(id, territorySubArea) {
        var options = '<option value="">Please Select</option>';
        $("#thanaUpazilaPouroshova").html(options);
        $("#unionInfo").html(options);
        if (id == '') {
            $("#district").html(options);
            return false;
        }
        jQuery.ajax({
            type: 'post',
            url: "${resource(dir:'territoryConfiguration', file:'fetchDropdownList')}?id=" + id + '&sl=' + 2,
            success: function (data) {
                options = '<option value="">Select District</option>';
                $.each(data, function (key, val) {
                    options += '<option value="' + val.id + '">' + val.name + '</option>';
                })
                $("#district").html(options);
            },
            complete: function () {
                if (territorySubArea != 0) {
                    $('#district').val(territorySubArea.district.id).attr("selected", "selected");
                    selectThana(territorySubArea.district.id, territory);
                }
            },
            dataType: 'json'
        });
    }

    function selectThana(id, territorySubArea) {
        var options = '<option value="">Please Select</option>';
        $("#unionInfo").html(options);
        if (id == '') {
            $("#thanaUpazilaPouroshova").html(options);
            return false;
        }
        jQuery.ajax({
            type: 'post',
            url: "${resource(dir:'territoryConfiguration', file:'fetchDropdownList')}?id=" + id + '&sl=' + 3,
            success: function (data) {
                options = '<option value="">Select Thana</option>';
                $.each(data, function (key, val) {
                    options += '<option value="' + val.id + '">' + val.name + '</option>';
                })
                $("#thanaUpazilaPouroshova").html(options);
            },
            complete: function () {
                if (territorySubArea != 0) {
                    if (!territorySubArea.thanaUpazilaPouroshova) {
                        return false;
                    }
                    $('#thanaUpazilaPouroshova').val(territorySubArea.thanaUpazilaPouroshova.id).attr("selected", "selected");
                    selectUnion(territorySubArea.thanaUpazilaPouroshova.id, territorySubArea);
                }
            },
            dataType: 'json'
        });
    }

    function selectUnion(id, territorySubArea) {
        var options = '<option value="">Please Select</option>';
        if (id == '') {
            $("#unionInfo").html(options);
            return false;
        }
        jQuery.ajax({
            type: 'post',
            url: "${resource(dir:'territoryConfiguration', file:'fetchDropdownList')}?id=" + id + '&sl=' + 4,
            success: function (data) {
                options = '<option value="">Select Union</option>';
                $.each(data, function (key, val) {
                    options += '<option value="' + val.id + '">' + val.name + '</option>';
                })
                $("#unionInfo").html(options);
            },
            complete: function () {
                if (territorySubArea != 0) {
                    if (!territorySubArea.unionInfo) {
                        return false;
                    }
                    $('#unionInfo').val(territorySubArea.unionInfo.id).attr("selected", "selected");
                }
            },
            dataType: 'json'
        });
    }
    function reset_territory_form(formName) {
        var enterpriseId = $("#enterpriseConfiguration").val();
        $(formName).find(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
            if (this.type == 'hidden') {
                this.value = "";
            } else {
                this.value = this.defaultValue;
            }
        });
        $(formName+' select').val('');
        $(formName+' input').attr('readonly',false);
//    $('input[type=checkbox]').attr('checked', false);
        $(formName + ' input[name = create-button]').attr('value', 'Create');
        $(formName + ' input[name = delete-button]').hide();
        $("#enterpriseConfiguration").val(enterpriseId);
        $("#isActiveVal").val("true");
    }
</script>