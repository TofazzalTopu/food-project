<%@ page import="com.bits.bdfp.inventory.setup.incentive.TargetBasedIncentive" %>
<script type="text/javascript" language="Javascript">
    var msg = '';
    var tbRowId = 1;
    var tbDelSlabIds = [];
    var tbDelCustomerIds = [];
    $(document).ready(function () {
        $("#tBeffectiveDateFrom").datepicker(
                {
                    dateFormat: 'dd-mm-yy',
                    changeMonth: true,
                    changeYear: true
                });
        $("#tBeffectiveDateTo").datepicker(
                {
                    dateFormat: 'dd-mm-yy',
                    changeMonth: true,
                    changeYear: true
                });
        $('#tBeffectiveDateFrom').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});
        $('#tBeffectiveDateTo').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});

//        Incentive Slab
        $("#tb-jqgrid-incentive-slab-grid").jqGrid({
            datatype: "local",
            data: ${targetBasedIncentiveSlabList?targetBasedIncentiveSlabList:''},
            colNames: [
                'SI',
                'ID',
                'Achievement % From',
                'Achievement % To',
                'Incentive Amount Each(BDT)',
                'Remove'
            ],
            colModel: [
                {name: 'si', index: 'id', width: 30, align: 'left'},
                {name: 'tbisId', index: 'tbisId', width: 30, align: 'left', hidden:true},
                {name: 'achievementFrom', index: 'achievementFrom', width: 150, align: 'left'},
                {name: 'achievementTo', index: 'achievementTo', width: 130, align: 'left'},
                {name: 'incentiveAmount', index: 'incentiveAmount', width: 180, align: 'left'},
                {name: 'remove', index: 'remove', width: 60, align: 'center'}
            ],
            rowNum: 10,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#tb-jqgrid-incentive-slab-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "asc",
            caption: "Incentive Slab List",
            autowidth: false,
            height: true,
            scrollOffset: 0,
            loadComplete: function () {
                var grid = $("#tb-jqgrid-incentive-slab-grid");
                var rowIds = grid.jqGrid('getDataIDs');
                for(key in rowIds){
                    grid.jqGrid('setRowData', rowIds[key],{
                        si:tbRowId,
                        remove: '<a  href="javascript:removeTbIncentiveRow(' + tbRowId + ')" class="ui-icon ui-icon-trash" title="Delete"></a>'
                    })
                    tbRowId++
                }
            },
            onSelectRow: function (rowid, status) {

            }
        });
        $("#tb-jqgrid-incentive-slab-grid").jqGrid('navGrid', '#tb-jqgrid-incentive-slab-pager', {edit: false, add: false, del: false, search: false});
        $(".ui-pg-selbox").children().each(function () {
            if ($(this).val() == -1) {
                $(this).html('All')
            }
        });

//        Eligible Territory
        $("#tb-jqgrid-eligible-territory-grid").jqGrid({
            datatype: "local",
            data:${territoryConfigurationList},

            colNames: [
                'All Territory',
                'Territory ID'
            ],
            colModel: [
                {name: 'name', index: 'territoryName', width: 150, align: 'left'},
                {name: 'id', index: 'territoryId', width:0, hidden:true}

            ],

            rowNum: ${territoryConfigurationSize},
            sortname: 'name',
            viewrecords: true,
            sortorder: "asc",
            caption: "Eligible Territory",
            autowidth: false,
            height: true,
            multiselect: true,
            scrollOffset: 17,
            loadComplete: function () {

            },
            onSelectRow: function (rowid, status) {
                jQuery("#tb-jqgrid-eligible-geo-grid").clearGridData();
                jQuery("#tb-jqgrid-eligible-pt-grid").clearGridData();
                jQuery("#tb-jqgrid-eligible-sc-grid").clearGridData();
                jQuery("#tb-jqgrid-eligible-cc-grid").clearGridData();
                jQuery("#tb-jqgrid-eligible-customers-grid").clearGridData();
                $('#cb_tb-jqgrid-eligible-territory-grid').attr('checked',false);
                var selectedRows = $('#tb-jqgrid-eligible-territory-grid').jqGrid('getGridParam', 'selarrrow');
                var ids = getRowIdsAll(selectedRows);

                if(ids==''){
                    return false;
                }

                SubmissionLoader.showTo();
                jQuery.ajax({
                    type: 'post',
                    data: {territoryIds:ids},
                    url:  "${request.contextPath}/${params.controller}/getTerritoryWiseGeoAndCustomers",
                    success: function (data, textStatus) {
                        if(data.geoList[0]) {
                            jQuery("#tb-jqgrid-eligible-geo-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        rowNum: data.geoList.length,
                                        datatype: 'local',
                                        data: data.geoList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#tb-jqgrid-eligible-geo-grid").clearGridData();
                        }

                        if(data.customerList[0]){
                            jQuery("#tb-jqgrid-eligible-customers-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        datatype: 'local',
                                        data: data.customerList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#tb-jqgrid-eligible-customers-grid").clearGridData();
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        if(XMLHttpRequest.status = 0){
                            jQuery("#tb-jqgrid-eligible-geo-grid").clearGridData();
                            jQuery("#tb-jqgrid-eligible-customers-grid").clearGridData();
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
            },
            onSelectAll: function(aRowids, status) {
                jQuery("#tb-jqgrid-eligible-geo-grid").clearGridData();
                jQuery("#tb-jqgrid-eligible-pt-grid").clearGridData();
                jQuery("#tb-jqgrid-eligible-sc-grid").clearGridData();
                jQuery("#tb-jqgrid-eligible-cc-grid").clearGridData();
                jQuery("#tb-jqgrid-eligible-customers-grid").clearGridData();
                var selectedRows = $('#tb-jqgrid-eligible-territory-grid').jqGrid('getGridParam', 'selarrrow');
                var ids = getRowIdsAll(selectedRows);

                if(ids==''){
                    return false;
                }

                SubmissionLoader.showTo();
                jQuery.ajax({
                    type: 'post',
                    data: {territoryIds:ids},
                    url:  "${request.contextPath}/${params.controller}/getTerritoryWiseGeoAndCustomers",
                    success: function (data, textStatus) {
                        if(data.geoList[0]) {
                            jQuery("#tb-jqgrid-eligible-geo-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        rowNum: data.geoList.length,
                                        datatype: 'local',
                                        data: data.geoList
                                    })
                                    .trigger("reloadGrid");

                        }else{
                            jQuery("#tb-jqgrid-eligible-geo-grid").clearGridData();
                        }

                        if(data.customerList[0]){
                            jQuery("#tb-jqgrid-eligible-customers-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        datatype: 'local',
                                        data: data.customerList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#tb-jqgrid-eligible-customers-grid").clearGridData();
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        if(XMLHttpRequest.status = 0){
                            jQuery("#tb-jqgrid-eligible-geo-grid").clearGridData();
                            jQuery("#tb-jqgrid-eligible-customers-grid").clearGridData();
                            MessageRenderer.renderErrorText("Network Problem: Time out");
                        } else{
                            MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                        }                    },
                    complete: function () {
                        SubmissionLoader.hideFrom();
                    },
                    dataType: 'json'
                });
            }
        });

//        Eligible Geographic Location
        $("#tb-jqgrid-eligible-geo-grid").jqGrid({
            datatype: "local",
            data:'',

            colNames: [
                'All Geographic Location',
                'GEO ID'
            ],
            colModel: [
                {name: 'name', index: 'territoryName', width: 170, align: 'left'},
                {name: 'id', index: 'territoryId', width:0, hidden:true}

            ],

            sortname: 'name',
            viewrecords: true,
            sortorder: "asc",
            caption: "Eligible Geographic Location",
            autowidth: false,
            height: true,
            multiselect: true,
            scrollOffset: 17,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                jQuery("#tb-jqgrid-eligible-pt-grid").clearGridData();
                jQuery("#tb-jqgrid-eligible-sc-grid").clearGridData();
                jQuery("#tb-jqgrid-eligible-cc-grid").clearGridData();
                jQuery("#tb-jqgrid-eligible-customers-grid").clearGridData();
                $('#cb_tb-jqgrid-eligible-geo-grid').attr('checked',false);
                var selectedTerritoryRows = $('#tb-jqgrid-eligible-territory-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedGeoRows = $('#tb-jqgrid-eligible-geo-grid').jqGrid('getGridParam', 'selarrrow');
                var territoryIds = getRowIdsAll(selectedTerritoryRows);
                var geoIds = getRowIdsAll(selectedGeoRows);

                SubmissionLoader.showTo();
                jQuery.ajax({
                    type: 'post',
                    data: {territoryIds:territoryIds, geoIds:geoIds},
                    url:  "${request.contextPath}/${params.controller}/getGeoWiseTpAndCustomers",
                    success: function (data, textStatus) {
                        if(data.ptList[0]) {
                            jQuery("#tb-jqgrid-eligible-pt-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        rowNum: data.ptList.length,
                                        datatype: 'local',
                                        data: data.ptList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#tb-jqgrid-eligible-pt-grid").clearGridData();
                        }

                        if(data.customerList[0]){
                            jQuery("#tb-jqgrid-eligible-customers-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        datatype: 'local',
                                        data: data.customerList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#tb-jqgrid-eligible-customers-grid").clearGridData();
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        if(XMLHttpRequest.status = 0){
                            jQuery("#tb-jqgrid-eligible-pt-grid").clearGridData();
                            jQuery("#tb-jqgrid-eligible-customers-grid").clearGridData();
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
            },
            onSelectAll: function(aRowids, status) {
                jQuery("#tb-jqgrid-eligible-pt-grid").clearGridData();
                jQuery("#tb-jqgrid-eligible-sc-grid").clearGridData();
                jQuery("#tb-jqgrid-eligible-cc-grid").clearGridData();
                jQuery("#tb-jqgrid-eligible-customers-grid").clearGridData();
                var selectedTerritoryRows = $('#tb-jqgrid-eligible-territory-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedGeoRows = $('#tb-jqgrid-eligible-geo-grid').jqGrid('getGridParam', 'selarrrow');
                var territoryIds = getRowIdsAll(selectedTerritoryRows);
                var geoIds = getRowIdsAll(selectedGeoRows);

                SubmissionLoader.showTo();
                jQuery.ajax({
                    type: 'post',
                    data: {territoryIds:territoryIds, geoIds:geoIds},
                    url:  "${request.contextPath}/${params.controller}/getGeoWiseTpAndCustomers",
                    success: function (data, textStatus) {
                        if(data.ptList[0]) {
                            jQuery("#tb-jqgrid-eligible-pt-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        rowNum: data.ptList.length,
                                        datatype: 'local',
                                        data: data.ptList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#tb-jqgrid-eligible-pt-grid").clearGridData();
                        }

                        if(data.customerList[0]){
                            jQuery("#tb-jqgrid-eligible-customers-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        datatype: 'local',
                                        data: data.customerList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#tb-jqgrid-eligible-customers-grid").clearGridData();
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        if(XMLHttpRequest.status = 0){
                            jQuery("#tb-jqgrid-eligible-pt-grid").clearGridData();
                            jQuery("#tb-jqgrid-eligible-customers-grid").clearGridData();
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
        });

//        Eligible Partner Type
        $("#tb-jqgrid-eligible-pt-grid").jqGrid({
            datatype: "local",
            data:'',

            colNames: [
                'All Partner Type',
                'Partner Type ID'
            ],
            colModel: [
                {name: 'name', index: 'territoryName', width: 140, align: 'left'},
                {name: 'id', index: 'territoryId', width:0, hidden:true}

            ],

            sortname: 'name',
            viewrecords: true,
            sortorder: "asc",
            caption: "Eligible Partner Type",
            autowidth: false,
            height: true,
            multiselect: true,
            scrollOffset: 17,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                jQuery("#tb-jqgrid-eligible-sc-grid").clearGridData();
                jQuery("#tb-jqgrid-eligible-cc-grid").clearGridData();
                jQuery("#tb-jqgrid-eligible-customers-grid").clearGridData();
                $('#cb_tb-jqgrid-eligible-pt-grid').attr('checked',false);
                var selectedTerritoryRows = $('#tb-jqgrid-eligible-territory-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedGeoRows = $('#tb-jqgrid-eligible-geo-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedPtRows = $('#tb-jqgrid-eligible-pt-grid').jqGrid('getGridParam', 'selarrrow');
                var territoryIds = getRowIdsAll(selectedTerritoryRows);
                var geoIds = getRowIdsAll(selectedGeoRows);
                var ptIds = getRowIdsAll(selectedPtRows);

                SubmissionLoader.showTo();
                jQuery.ajax({
                    type: 'post',
                    data: {territoryIds:territoryIds, geoIds:geoIds, ptIds:ptIds},
                    url:  "${request.contextPath}/${params.controller}/getPtWiseScAndCustomers",
                    success: function (data, textStatus) {
                        if(data.scList[0]) {
                            jQuery("#tb-jqgrid-eligible-sc-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        rowNum: data.scList.length,
                                        datatype: 'local',
                                        data: data.scList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#tb-jqgrid-eligible-sc-grid").clearGridData();
                        }

                        if(data.customerList[0]){
                            jQuery("#tb-jqgrid-eligible-customers-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        datatype: 'local',
                                        data: data.customerList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#tb-jqgrid-eligible-customers-grid").clearGridData();
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        if(XMLHttpRequest.status = 0){
                            jQuery("#tb-jqgrid-eligible-sc-grid").clearGridData();
                            jQuery("#tb-jqgrid-eligible-customers-grid").clearGridData();
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
            },
            onSelectAll: function(aRowids, status) {
                jQuery("#tb-jqgrid-eligible-sc-grid").clearGridData();
                jQuery("#tb-jqgrid-eligible-cc-grid").clearGridData();
                jQuery("#tb-jqgrid-eligible-customers-grid").clearGridData();
                var selectedTerritoryRows = $('#tb-jqgrid-eligible-territory-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedGeoRows = $('#tb-jqgrid-eligible-geo-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedPtRows = $('#tb-jqgrid-eligible-pt-grid').jqGrid('getGridParam', 'selarrrow');
                var territoryIds = getRowIdsAll(selectedTerritoryRows);
                var geoIds = getRowIdsAll(selectedGeoRows);
                var ptIds = getRowIdsAll(selectedPtRows);

                SubmissionLoader.showTo();
                jQuery.ajax({
                    type: 'post',
                    data: {territoryIds:territoryIds, geoIds:geoIds, ptIds:ptIds},
                    url:  "${request.contextPath}/${params.controller}/getPtWiseScAndCustomers",
                    success: function (data, textStatus) {
                        if(data.scList[0]) {
                            jQuery("#tb-jqgrid-eligible-sc-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        rowNum: data.scList.length,
                                        datatype: 'local',
                                        data: data.scList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#tb-jqgrid-eligible-sc-grid").clearGridData();
                        }

                        if(data.customerList[0]){
                            jQuery("#tb-jqgrid-eligible-customers-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        datatype: 'local',
                                        data: data.customerList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#tb-jqgrid-eligible-customers-grid").clearGridData();
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        if(XMLHttpRequest.status = 0){
                            jQuery("#tb-jqgrid-eligible-sc-grid").clearGridData();
                            jQuery("#tb-jqgrid-eligible-customers-grid").clearGridData();
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
        });

//        Eligible Sales Channel
        $("#tb-jqgrid-eligible-sc-grid").jqGrid({
            datatype: "local",
            data:'',

            colNames: [
                'All Sales Channel',
                'Sales Channel ID'
            ],
            colModel: [
                {name: 'name', index: 'territoryName', width: 140, align: 'left'},
                {name: 'id', index: 'territoryId', width:0, hidden:true}

            ],

            sortname: 'name',
            viewrecords: true,
            sortorder: "asc",
            caption: "Eligible Sales Channel",
            autowidth: false,
            height: true,
            multiselect: true,
            scrollOffset: 17,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                jQuery("#tb-jqgrid-eligible-cc-grid").clearGridData();
                jQuery("#tb-jqgrid-eligible-customers-grid").clearGridData();
                $('#cb_tb-jqgrid-eligible-sc-grid').attr('checked',false);
                var selectedTerritoryRows = $('#tb-jqgrid-eligible-territory-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedGeoRows = $('#tb-jqgrid-eligible-geo-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedPtRows = $('#tb-jqgrid-eligible-pt-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedScRows = $('#tb-jqgrid-eligible-sc-grid').jqGrid('getGridParam', 'selarrrow');
                var territoryIds = getRowIdsAll(selectedTerritoryRows);
                var geoIds = getRowIdsAll(selectedGeoRows);
                var ptIds = getRowIdsAll(selectedPtRows);
                var scIds = getRowIdsAll(selectedScRows);

                SubmissionLoader.showTo();
                jQuery.ajax({
                    type: 'post',
                    data: {territoryIds:territoryIds, geoIds:geoIds, ptIds:ptIds, scIds:scIds},
                    url:  "${request.contextPath}/${params.controller}/getScWiseCcAndCustomers",
                    success: function (data, textStatus) {
                        if(data.ccList[0]) {
                            jQuery("#tb-jqgrid-eligible-cc-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        rowNum: data.ccList.length,
                                        datatype: 'local',
                                        data: data.ccList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#tb-jqgrid-eligible-cc-grid").clearGridData();
                        }

                        if(data.customerList[0]){
                            jQuery("#tb-jqgrid-eligible-customers-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        datatype: 'local',
                                        data: data.customerList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#tb-jqgrid-eligible-customers-grid").clearGridData();
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        if(XMLHttpRequest.status = 0){
                            jQuery("#tb-jqgrid-eligible-cc-grid").clearGridData();
                            jQuery("#tb-jqgrid-eligible-customers-grid").clearGridData();
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
            },
            onSelectAll: function(aRowids, status) {
                jQuery("#tb-jqgrid-eligible-cc-grid").clearGridData();
                jQuery("#tb-jqgrid-eligible-customers-grid").clearGridData();
                var selectedTerritoryRows = $('#tb-jqgrid-eligible-territory-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedGeoRows = $('#tb-jqgrid-eligible-geo-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedPtRows = $('#tb-jqgrid-eligible-pt-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedScRows = $('#tb-jqgrid-eligible-sc-grid').jqGrid('getGridParam', 'selarrrow');
                var territoryIds = getRowIdsAll(selectedTerritoryRows);
                var geoIds = getRowIdsAll(selectedGeoRows);
                var ptIds = getRowIdsAll(selectedPtRows);
                var scIds = getRowIdsAll(selectedScRows);

                SubmissionLoader.showTo();
                jQuery.ajax({
                    type: 'post',
                    data: {territoryIds:territoryIds, geoIds:geoIds, ptIds:ptIds, scIds:scIds},
                    url:  "${request.contextPath}/${params.controller}/getScWiseCcAndCustomers",
                    success: function (data, textStatus) {
                        if(data.ccList[0]) {
                            jQuery("#tb-jqgrid-eligible-cc-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        rowNum: data.ccList.length,
                                        datatype: 'local',
                                        data: data.ccList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#tb-jqgrid-eligible-cc-grid").clearGridData();
                        }

                        if(data.customerList[0]){
                            jQuery("#tb-jqgrid-eligible-customers-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        datatype: 'local',
                                        data: data.customerList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#tb-jqgrid-eligible-customers-grid").clearGridData();
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        if(XMLHttpRequest.status = 0){
                            jQuery("#tb-jqgrid-eligible-cc-grid").clearGridData();
                            jQuery("#tb-jqgrid-eligible-customers-grid").clearGridData();
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
        });

//        Eligible Customer Category
        $("#tb-jqgrid-eligible-cc-grid").jqGrid({
            datatype: "local",
            data:'',

            colNames: [
                'All Customer Category',
                'Category ID'
            ],
            colModel: [
                {name: 'name', index: 'categoryName', width: 165, align: 'left'},
                {name: 'id', index: 'categoryId', width:0, hidden:true}

            ],

            sortname: 'name',
            viewrecords: true,
            sortorder: "asc",
            caption: "Eligible Customer Category",
            autowidth: false,
            height: true,
            multiselect: true,
            scrollOffset: 17,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                jQuery("#tb-jqgrid-eligible-customers-grid").clearGridData();
                $('#cb_tb-jqgrid-eligible-cc-grid').attr('checked',false);
                var selectedTerritoryRows = $('#tb-jqgrid-eligible-territory-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedGeoRows = $('#tb-jqgrid-eligible-geo-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedPtRows = $('#tb-jqgrid-eligible-pt-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedScRows = $('#tb-jqgrid-eligible-sc-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedCcRows = $('#tb-jqgrid-eligible-cc-grid').jqGrid('getGridParam', 'selarrrow');
                var territoryIds = getRowIdsAll(selectedTerritoryRows);
                var geoIds = getRowIdsAll(selectedGeoRows);
                var ptIds = getRowIdsAll(selectedPtRows);
                var scIds = getRowIdsAll(selectedScRows);
                var ccIds = getRowIdsAll(selectedCcRows);

                SubmissionLoader.showTo();
                jQuery.ajax({
                    type: 'post',
                    data: {territoryIds:territoryIds, geoIds:geoIds, ptIds:ptIds, scIds:scIds, ccIds:ccIds},
                    url:  "${request.contextPath}/${params.controller}/getCcWiseCustomers",
                    success: function (data, textStatus) {
                        if(data.customerList[0]){
                            jQuery("#tb-jqgrid-eligible-customers-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        datatype: 'local',
                                        data: data.customerList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#tb-jqgrid-eligible-customers-grid").clearGridData();
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        if(XMLHttpRequest.status = 0){
                            jQuery("#tb-jqgrid-eligible-customers-grid").clearGridData();
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
            },
            onSelectAll: function(aRowids, status) {
                jQuery("#tb-jqgrid-eligible-customers-grid").clearGridData();
                var selectedTerritoryRows = $('#tb-jqgrid-eligible-territory-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedGeoRows = $('#tb-jqgrid-eligible-geo-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedPtRows = $('#tb-jqgrid-eligible-pt-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedScRows = $('#tb-jqgrid-eligible-sc-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedCcRows = $('#tb-jqgrid-eligible-cc-grid').jqGrid('getGridParam', 'selarrrow');
                var territoryIds = getRowIdsAll(selectedTerritoryRows);
                var geoIds = getRowIdsAll(selectedGeoRows);
                var ptIds = getRowIdsAll(selectedPtRows);
                var scIds = getRowIdsAll(selectedScRows);
                var ccIds = getRowIdsAll(selectedCcRows);

                SubmissionLoader.showTo();
                jQuery.ajax({
                    type: 'post',
                    data: {territoryIds:territoryIds, geoIds:geoIds, ptIds:ptIds, scIds:scIds, ccIds:ccIds},
                    url:  "${request.contextPath}/${params.controller}/getCcWiseCustomers",
                    success: function (data, textStatus) {
                        if(data.customerList[0]){
                            jQuery("#tb-jqgrid-eligible-customers-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        datatype: 'local',
                                        data: data.customerList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#tb-jqgrid-eligible-customers-grid").clearGridData();
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        if(XMLHttpRequest.status = 0){
                            jQuery("#tb-jqgrid-eligible-customers-grid").clearGridData();
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
        });

//        Eligible Customers
        $("#tb-jqgrid-eligible-customers-grid").jqGrid({
            datatype: "local",
            data:'',

            colNames: [
                'Customer Name',
                'ID',
                'Customer ID',
                'Remove'
            ],
            colModel: [
                {name: 'name', index: 'name', width: 200, align: 'left'},
                {name: 'id', index: 'id', width:100, hidden:true},
                {name: 'code', index: 'code', width:100},
                {name: 'remove', index: 'remove', width:60}

            ],
            rowNum: 10,
            rowList: [10,20,30,40,50,60,70,80,90,100,-1],
            pager: '#tb-jqgrid-eligible-customers-pager',
            sortname: 'name',
            viewrecords: true,
            sortorder: "asc",
            caption: "Eligible Customers",
            autowidth: false,
            height: true,
            multiselect: true,
            scrollOffset: 17,
            loadComplete: function () {
                var rowIds = $("#tb-jqgrid-eligible-customers-grid").jqGrid('getDataIDs')
                for(key in rowIds){
                    $("#tb-jqgrid-eligible-customers-grid").jqGrid('setRowData', rowIds[key],{
                        remove: '<a  href="javascript:removeTbCustomersRow(' + rowIds[key] + ')" class="ui-icon ui-icon-trash" title="Delete"></a>'
                    })
                }
            },
            onSelectRow: function (rowid, status) {
            },
            onSelectAll: function(aRowids, status) {
            }
        });
        $("#tb-jqgrid-eligible-customers-grid").jqGrid('navGrid', '#tb-jqgrid-eligible-customers-pager', {edit: false, add: false, del: false, search: false});

//        Eligible Customers for Update
        $("#tb-update-jqgrid-eligible-customers-grid").jqGrid({
            datatype: "local",
            data:${targetBasedIncentiveCustomersList?targetBasedIncentiveCustomersList:''},

            colNames: [
                'SI',
                'tbicId',
                'Customer Name',
                'Customer ID',
                '',
                'Territory',
                'Geo Location',
                'Partner Type',
                'Sales Channel',
                'Customer Category',
                'Remove'
            ],
            colModel: [
                {name: 'si', index: 'si', width: 30, align: 'left'},
                {name: 'tbicId', index: 'tbicId', width: 60, align: 'left', hidden:true},
                {name: 'name', index: 'name', width: 120, align: 'left'},
                {name: 'code', index: 'code', width:100},
                {name: 'id', index: 'id', width:100, hidden:true},
                {name: 'territory', index: 'territory', width:120},
                {name: 'geo', index: 'geo', width:150},
                {name: 'partnerType', index: 'partnerType', width:100},
                {name: 'salesChannel', index: 'salesChannel', width:100},
                {name: 'customerCategory', index: 'customerCategory', width:100},
                {name: 'remove', index: 'remove', width:60}

            ],
            rowNum: 10,
            rowList: [10,20,30,40,50,60,70,80,90,100,-1],
            pager: '#tb-update-jqgrid-eligible-customers-pager',
//            sortname: 'name',
            viewrecords: true,
            sortorder: "asc",
            caption: "Existing Customer List",
            autowidth: false,
            height: true,
            multiselect: false,
            scrollOffset: 17,
            loadComplete: function () {
                var i = 1;
                var rowIds = $("#tb-update-jqgrid-eligible-customers-grid").jqGrid('getDataIDs');
                for(key in rowIds){
                    $("#tb-update-jqgrid-eligible-customers-grid").jqGrid('setRowData', rowIds[key],{
                        si:i,
                        remove: '<a  href="javascript:removeTbUpdateCustomersRow(' + rowIds[key] + ')" class="ui-icon ui-icon-trash" title="Delete"></a>'
                    });
                    i++;
                }
            },
            onSelectRow: function (rowid, status) {
                $('#cb_tb-update-jqgrid-eligible-customers-grid').attr('checked',false);
            }
        });

        $("#tb-update-jqgrid-eligible-customers-grid").jqGrid('navGrid', '#tb-update-jqgrid-eligible-customers-pager', {edit: false, add: false, del: false, search: false});
        $(".ui-pg-selbox").children().each(function () {
            if ($(this).val() == -1) {
                $(this).html('All')
            }
        });

//        Grid Max Height
        $('div.ui-jqgrid-bdiv').css("max-height","250px");

//        Check 100% Achievement From
        $('#tBachievementFrom').keypress(function(event) {
            if ((event.which != 46 || $(this).val().indexOf('.') != -1) &&
                    ((event.which < 48 || event.which > 57) &&
                    (event.which != 0 && event.which != 8))) {
                event.preventDefault();
            }

            var text = $(this).val();

            if ((text.indexOf('.') != -1) &&
                    (text.substring(text.indexOf('.')).length > 2) &&
                    (event.which != 0 && event.which != 8) &&
                    ($(this)[0].selectionStart >= text.length - 2)) {
                event.preventDefault();
            }
        });

//        Check 100% Achievement To
        $('#tBachievementTo').keypress(function(event) {
            if ((event.which != 46 || $(this).val().indexOf('.') != -1) &&
                    ((event.which < 48 || event.which > 57) &&
                    (event.which != 0 && event.which != 8))) {
                event.preventDefault();
            }

            var text = $(this).val();

            if ((text.indexOf('.') != -1) &&
                    (text.substring(text.indexOf('.')).length > 2) &&
                    (event.which != 0 && event.which != 8) &&
                    ($(this)[0].selectionStart >= text.length - 2)) {
                event.preventDefault();
            }
        });

//        Check Incentive Amount
        $('#tBincentiveAmount').keypress(function(event) {
            if ((event.which != 46 || $(this).val().indexOf('.') != -1) &&
                    ((event.which < 48 || event.which > 57) &&
                    (event.which != 0 && event.which != 8))) {
                event.preventDefault();
            }

            var text = $(this).val();

            if ((text.indexOf('.') != -1) &&
                    (text.substring(text.indexOf('.')).length > 2) &&
                    (event.which != 0 && event.which != 8) &&
                    ($(this)[0].selectionStart >= text.length - 2)) {
                event.preventDefault();
            }
        });

    });


    function addTbIncentive() {
        var myGrid = $("#tb-jqgrid-incentive-slab-grid");
        var slabDataList = myGrid.jqGrid('getRowData');

        var achievementFrom = $("#tBachievementFrom").val();
        var achievementTo = $("#tBachievementTo").val();
        var incentiveAmount = $("#tBincentiveAmount").val();

        if(slabDataList.length > 0){
            for(var i=0; i<slabDataList.length; i++){
                if(((parseFloat(achievementFrom) >= parseFloat(slabDataList[i].achievementFrom)) && (parseFloat(achievementFrom) <= parseFloat(slabDataList[i].achievementTo))) || ((parseFloat(achievementTo) >= parseFloat(slabDataList[i].achievementFrom)) && (parseFloat(achievementTo) <= parseFloat(slabDataList[i].achievementTo)))){
                    MessageRenderer.render({
                        "messageBody": "Overlapping incentive slab between achievement % from and achievement % to is not allowed.",
                        "messageTitle": "Target Based Incentive",
                        "type": "0"
                    });
                    return false;
                }
            }
        }

        if (achievementFrom && achievementTo && incentiveAmount) {
            if(achievementFrom && parseFloat(achievementFrom)>100.00){
                MessageRenderer.render({
                    "messageBody": "Achievement % from can’t be more than 100.00",
                    "messageTitle": "Target Based Incentive",
                    "type": "0"
                });
                return false;
            }
            else if(achievementTo && parseFloat(achievementTo)>100.00){
                MessageRenderer.render({
                    "messageBody": "Achievement % to can’t be more than 100.00",
                    "messageTitle": "Target Based Incentive",
                    "type": "0"
                });
                return false;
            }

            var dataItem = {
                si: tbRowId,
                achievementFrom: achievementFrom,
                achievementTo: achievementTo,
                incentiveAmount: incentiveAmount,
                remove: '<a  href="javascript:removeTbIncentiveRow(' + tbRowId + ')" class="ui-icon ui-icon-trash" title="Delete"></a>'
            };
            myGrid.addRowData(tbRowId,dataItem, "last");
            $("#tBachievementFrom").val('');
            $("#tBachievementTo").val('');
            $("#tBincentiveAmount").val('');
            tbRowId++;
        }else{
            if(!achievementFrom){
                MessageRenderer.render({
                    "messageBody": "Please enter value of achievement from.",
                    "messageTitle": "Target Based Incentive",
                    "type": "0"
                });
                return false;
            }
            else if(!achievementTo){
                MessageRenderer.render({
                    "messageBody": "Please enter value of achievement to.",
                    "messageTitle": "Target Based Incentive",
                    "type": "0"
                });
                return false;
            }
            else if(!incentiveAmount){
                MessageRenderer.render({
                    "messageBody": "Please enter incentive amount.",
                    "messageTitle": "Target Based Incentive",
                    "type": "0"
                });
                return false;
            }
        }
    }

    function removeTbIncentiveRow(id){
        var grid = $("#tb-jqgrid-incentive-slab-grid");
        var slabId = grid.jqGrid ('getCell', id, 'tbisId');

        if(slabId){
            tbDelSlabIds.push(slabId)
        }

        grid.jqGrid("delRowData", id);
    }

    function removeTbUpdateCustomersRow(id){
        var grid = $("#tb-update-jqgrid-eligible-customers-grid");
        var tbicId = grid.jqGrid ('getCell', id, 'tbicId');
        if(tbicId){
            tbDelCustomerIds.push(tbicId)
        }
        grid.jqGrid("delRowData", id);
    }

    function removeTbCustomersRow(id){
        $("#tb-jqgrid-eligible-customers-grid").jqGrid("delRowData", id);
    }

    function executeTbPrecondition(){
        if(!$("#gFormSetupIncentive").validationEngine('validate')){
            return false;
        }
        return true;
    }

    function executeAjaxIncentiveTargetBased(){
        if(!executeTbPrecondition()){
            return false;
        }

        var allData = {};

        allData["programName"] = $("#tBprogramName").val();
        allData["effectiveDateFrom"] = $("#tBeffectiveDateFrom").val();
        allData["effectiveDateTo"] = $("#tBeffectiveDateTo").val();

        var allCustomers = [];
        var allIncentive = $("#tb-jqgrid-incentive-slab-grid").jqGrid('getRowData');
        var selectedCustomerRows = $('#tb-jqgrid-eligible-customers-grid').jqGrid('getGridParam', 'selarrrow');
        var customerRowIds = filterListOfIds(selectedCustomerRows);

        var tbIncentiveId = $("#tbIncentiveId").val();
        var tbIncentiveVersion = $("#tbIncentiveVersion").val();

        if(allIncentive.length>0){
            for(var i = 0; i < allIncentive.length; i++){
                allData["allIncentive.incentive["+ i +"].id"] = allIncentive[i].tbisId;
                allData["allIncentive.incentive["+ i +"].achievementFrom"] = allIncentive[i].achievementFrom;
                allData["allIncentive.incentive["+ i +"].achievementTo"] = allIncentive[i].achievementTo;
                allData["allIncentive.incentive["+ i +"].incentiveAmount"] = allIncentive[i].incentiveAmount;
            }
        }else{
            MessageRenderer.render({
                "messageBody": "Please insert at least one incentive slab into the list.",
                "messageTitle": "Target Based Incentive",
                "type": "0"
            });
            return false;
        }

        if(customerRowIds == '' && tbIncentiveId == ''){
            MessageRenderer.render({
                "messageBody": "Please select at least one customer from the customer list.",
                "messageTitle": "Target Based Incentive",
                "type": "0"
            });
            return false;
        }else{
            for(key in customerRowIds){
                allCustomers.push($("#tb-jqgrid-eligible-customers-grid").getRowData(customerRowIds[key]));
            }

            for(var i = 0; i < allCustomers.length; i++){
                allData["customerList.customer["+ i +"].customerId"] = allCustomers[i].id;
            }
        }

        var url = '';

        if(tbIncentiveId){
            for(var i=0; i<tbDelSlabIds.length; i++){
                allData['incentiveSlabDeleteList.delete['+ i +'].id'] = tbDelSlabIds[i];
            }

            for(var i=0; i<tbDelCustomerIds.length; i++){
                allData['incentiveCustomersDeleteList.delete['+ i +'].id'] = tbDelCustomerIds[i];
            }

            var tbExistingCustomers = $("#tb-update-jqgrid-eligible-customers-grid").jqGrid('getDataIDs');
            if(customerRowIds == '' && tbExistingCustomers == ''){
                MessageRenderer.render({
                    "messageBody": "Please select at least one customer from the customer list.",
                    "messageTitle": "Target Based Incentive",
                    "type": "0"
                });
                return false;
            }

            url = "${request.contextPath}/${params.controller}/updateTargetBasedIncentive?id="+tbIncentiveId+"&version="+tbIncentiveVersion;
        }else{
            url = "${request.contextPath}/${params.controller}/createTargetBasedIncentive";
        }

        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: allData,
            url: url,
            success: function (data, textStatus) {
                if(data.type == 1){
                    clearTbAll();
                }
                MessageRenderer.render(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    clearTbAll();
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

    function checkTbAchievementFrom(){
        var val = parseFloat($('#tBachievementFrom').val());
        if(val > 100.00){
            return " * Achievement % from can’t be more than 100.00";
        }
    }

    function checkTbAchievementTo(){
        var val = parseFloat($('#tBachievementTo').val());
        if(val > 100.00){
            return " * Achievement % to can’t be more than 100.00";
        }
    }

    function clearTbAll(){
        $('#tBprogramName').val('');
        $('#tBeffectiveDateFrom').val('');
        $('#tBeffectiveDateTo').val('');

        $("#tb-jqgrid-incentive-slab-grid").clearGridData();
        $("#tb-jqgrid-eligible-territory-grid").trigger("reloadGrid");
        $("#tb-jqgrid-eligible-geo-grid").clearGridData();
        $("#tb-jqgrid-eligible-pt-grid").clearGridData();
        $("#tb-jqgrid-eligible-sc-grid").clearGridData();
        $("#tb-jqgrid-eligible-cc-grid").clearGridData();

        $("#tb-jqgrid-eligible-customers-grid").clearGridData();
        $("#tb-update-jqgrid-eligible-customers-grid").clearGridData();
    }

</script>
