<script type="text/javascript" language="Javascript">
    var vbRowId = 1;
    var vbDelSlabIds = [];
    var vbDelCustomerIds = [];
    $(document).ready(function () {
        $("#vbEffectiveDateFrom").datepicker(
                {
                    dateFormat: 'dd-mm-yy',
                    changeMonth: true,
                    changeYear: true
                });
        $("#vbEffectiveDateTo").datepicker(
                {
                    dateFormat: 'dd-mm-yy',
                    changeMonth: true,
                    changeYear: true
                });
        $('#vbEffectiveDateFrom').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});
        $('#vbEffectiveDateTo').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});

//        Incentive Slab
        $("#vb-jqgrid-incentive-slab-grid").jqGrid({
            datatype: "local",
            data: ${volumeBasedIncentiveSlabList?volumeBasedIncentiveSlabList:''},
            colNames: [
                'SI',
                'ID',
                'Version',
                'Ids',
                'Product Set Name',
                'Eligible Volume From (In MasterUOM)',
                'Eligible Volume To (In Master UOM)',
                'Total Incentive Amount',
                'Incentive Volume in %',
                'Per Unit Master UOM Cost in BDT',
                'Master UOM',
                'Remove'
            ],
            colModel: [
                {name: 'si', index: 'si', width: 30, align: 'left'},
                {name: 'vbisId', index: 'vbisId', width: 30, align: 'left', hidden:true},
                {name: 'version', index: 'version', width: 30, align: 'left', hidden:true},
                {name: 'productIds', index: 'productIds', width: 50, align: 'left', hidden:true},
                {name: 'productSetName', index: 'productSetName', width: 130, align: 'left'},
                {name: 'volumeFrom', index: 'volumeFrom', width: 130, align: 'left'},
                {name: 'volumeTo', index: 'volumeTo', width: 115, align: 'left'},
                {name: 'incentiveAmountValue', index: 'incentiveAmountValue', width: 100, align: 'left'},
                {name: 'incentiveAmountPct', index: 'incentiveAmountPct', width: 100, align: 'left'},
                {name: 'perUnitMasterUomCost', index: 'perUnitMasterUomCost', width: 110, align: 'left'},
                {name: 'masterUomId', index: 'masterUomId', width: 80, align: 'left', hidden:true},
                {name: 'remove', index: 'remove', width: 60, align: 'center'}
            ],
            rowNum: 10,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#vb-jqgrid-incentive-slab-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "asc",
            caption: "Incentive Slab List",
            autowidth: false,
            height: true,
            scrollOffset: 0,
            loadComplete: function () {
                var grid = $("#vb-jqgrid-incentive-slab-grid");
                var rowIds = grid.jqGrid('getDataIDs');
                for(key in rowIds){
                    grid.jqGrid('setRowData', rowIds[key],{
                        si:vbRowId,
                        remove: '<a  href="javascript:removeVbIncentiveRow(' + vbRowId + ')" class="ui-icon ui-icon-trash" title="Delete"></a>'
                    });
                    vbRowId++
                }
            },
            onSelectRow: function (rowid, status) {

            }
        });
        $("#vb-jqgrid-incentive-slab-grid").jqGrid('navGrid', '#vb-jqgrid-incentive-slab-pager', {edit: false, add: false, del: false, search: false});
        $(".ui-pg-selbox").children().each(function () {
            if ($(this).val() == -1) {
                $(this).html('All')
            }
        });

//        Eligible Territory
        $("#vb-jqgrid-eligible-territory-grid").jqGrid({
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
                jQuery("#vb-jqgrid-eligible-geo-grid").clearGridData();
                jQuery("#vb-jqgrid-eligible-pt-grid").clearGridData();
                jQuery("#vb-jqgrid-eligible-sc-grid").clearGridData();
                jQuery("#vb-jqgrid-eligible-cc-grid").clearGridData();
                jQuery("#vb-jqgrid-eligible-customers-grid").clearGridData();
                $('#cb_vb-jqgrid-eligible-territory-grid').attr('checked',false);
                var selectedRows = $('#vb-jqgrid-eligible-territory-grid').jqGrid('getGridParam', 'selarrrow');
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
                            jQuery("#vb-jqgrid-eligible-geo-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        rowNum: data.geoList.length,
                                        datatype: 'local',
                                        data: data.geoList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#vb-jqgrid-eligible-geo-grid").clearGridData();
                        }

                        if(data.customerList[0]){
                            jQuery("#vb-jqgrid-eligible-customers-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        datatype: 'local',
                                        data: data.customerList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#vb-jqgrid-eligible-customers-grid").clearGridData();
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        if(XMLHttpRequest.status = 0){
                            jQuery("#vb-jqgrid-eligible-geo-grid").clearGridData();
                            jQuery("#vb-jqgrid-eligible-customers-grid").clearGridData();
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
                jQuery("#vb-jqgrid-eligible-geo-grid").clearGridData();
                jQuery("#vb-jqgrid-eligible-pt-grid").clearGridData();
                jQuery("#vb-jqgrid-eligible-sc-grid").clearGridData();
                jQuery("#vb-jqgrid-eligible-cc-grid").clearGridData();
                jQuery("#vb-jqgrid-eligible-customers-grid").clearGridData();
                var selectedRows = $('#vb-jqgrid-eligible-territory-grid').jqGrid('getGridParam', 'selarrrow');
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
                            jQuery("#vb-jqgrid-eligible-geo-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        rowNum: data.geoList.length,
                                        datatype: 'local',
                                        data: data.geoList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#vb-jqgrid-eligible-geo-grid").clearGridData();
                        }

                        if(data.customerList[0]){
                            jQuery("#vb-jqgrid-eligible-customers-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        datatype: 'local',
                                        data: data.customerList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#vb-jqgrid-eligible-customers-grid").clearGridData();
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        if(XMLHttpRequest.status = 0){
                            jQuery("#vb-jqgrid-eligible-geo-grid").clearGridData();
                            jQuery("#vb-jqgrid-eligible-customers-grid").clearGridData();
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

//        Eligible Geographic Location
        $("#vb-jqgrid-eligible-geo-grid").jqGrid({
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
                jQuery("#vb-jqgrid-eligible-pt-grid").clearGridData();
                jQuery("#vb-jqgrid-eligible-sc-grid").clearGridData();
                jQuery("#vb-jqgrid-eligible-cc-grid").clearGridData();
                jQuery("#vb-jqgrid-eligible-customers-grid").clearGridData();
                $('#cb_vb-jqgrid-eligible-geo-grid').attr('checked',false);
                var selectedTerritoryRows = $('#vb-jqgrid-eligible-territory-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedGeoRows = $('#vb-jqgrid-eligible-geo-grid').jqGrid('getGridParam', 'selarrrow');
                var territoryIds = getRowIdsAll(selectedTerritoryRows);
                var geoIds = getRowIdsAll(selectedGeoRows);

                SubmissionLoader.showTo();
                jQuery.ajax({
                    type: 'post',
                    data: {territoryIds:territoryIds, geoIds:geoIds},
                    url:  "${request.contextPath}/${params.controller}/getGeoWiseTpAndCustomers",
                    success: function (data, textStatus) {
                        if(data.ptList[0]) {
                            jQuery("#vb-jqgrid-eligible-pt-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        rowNum: data.ptList.length,
                                        datatype: 'local',
                                        data: data.ptList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#vb-jqgrid-eligible-pt-grid").clearGridData();
                        }

                        if(data.customerList[0]){
                            jQuery("#vb-jqgrid-eligible-customers-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        datatype: 'local',
                                        data: data.customerList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#vb-jqgrid-eligible-customers-grid").clearGridData();
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        if(XMLHttpRequest.status = 0){
                            jQuery("#vb-jqgrid-eligible-pt-grid").clearGridData();
                            jQuery("#vb-jqgrid-eligible-customers-grid").clearGridData();
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
                jQuery("#vb-jqgrid-eligible-pt-grid").clearGridData();
                jQuery("#vb-jqgrid-eligible-sc-grid").clearGridData();
                jQuery("#vb-jqgrid-eligible-cc-grid").clearGridData();
                jQuery("#vb-jqgrid-eligible-customers-grid").clearGridData();
                var selectedTerritoryRows = $('#vb-jqgrid-eligible-territory-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedGeoRows = $('#vb-jqgrid-eligible-geo-grid').jqGrid('getGridParam', 'selarrrow');
                var territoryIds = getRowIdsAll(selectedTerritoryRows);
                var geoIds = getRowIdsAll(selectedGeoRows);

                SubmissionLoader.showTo();
                jQuery.ajax({
                    type: 'post',
                    data: {territoryIds:territoryIds, geoIds:geoIds},
                    url:  "${request.contextPath}/${params.controller}/getGeoWiseTpAndCustomers",
                    success: function (data, textStatus) {
                        if(data.ptList[0]) {
                            jQuery("#vb-jqgrid-eligible-pt-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        rowNum: data.ptList.length,
                                        datatype: 'local',
                                        data: data.ptList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#vb-jqgrid-eligible-pt-grid").clearGridData();
                        }

                        if(data.customerList[0]){
                            jQuery("#vb-jqgrid-eligible-customers-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        datatype: 'local',
                                        data: data.customerList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#vb-jqgrid-eligible-customers-grid").clearGridData();
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        if(XMLHttpRequest.status = 0){
                            jQuery("#vb-jqgrid-eligible-pt-grid").clearGridData();
                            jQuery("#vb-jqgrid-eligible-customers-grid").clearGridData();
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
        $("#vb-jqgrid-eligible-pt-grid").jqGrid({
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
                jQuery("#vb-jqgrid-eligible-sc-grid").clearGridData();
                jQuery("#vb-jqgrid-eligible-cc-grid").clearGridData();
                jQuery("#vb-jqgrid-eligible-customers-grid").clearGridData();
                $('#cb_vb-jqgrid-eligible-pt-grid').attr('checked',false);
                var selectedTerritoryRows = $('#vb-jqgrid-eligible-territory-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedGeoRows = $('#vb-jqgrid-eligible-geo-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedPtRows = $('#vb-jqgrid-eligible-pt-grid').jqGrid('getGridParam', 'selarrrow');
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
                            jQuery("#vb-jqgrid-eligible-sc-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        rowNum: data.scList.length,
                                        datatype: 'local',
                                        data: data.scList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#vb-jqgrid-eligible-sc-grid").clearGridData();
                        }

                        if(data.customerList[0]){
                            jQuery("#vb-jqgrid-eligible-customers-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        datatype: 'local',
                                        data: data.customerList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#vb-jqgrid-eligible-customers-grid").clearGridData();
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        if(XMLHttpRequest.status = 0){
                            jQuery("#vb-jqgrid-eligible-sc-grid").clearGridData();
                            jQuery("#vb-jqgrid-eligible-customers-grid").clearGridData();
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
                jQuery("#vb-jqgrid-eligible-sc-grid").clearGridData();
                jQuery("#vb-jqgrid-eligible-cc-grid").clearGridData();
                jQuery("#vb-jqgrid-eligible-customers-grid").clearGridData();
                var selectedTerritoryRows = $('#vb-jqgrid-eligible-territory-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedGeoRows = $('#vb-jqgrid-eligible-geo-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedPtRows = $('#vb-jqgrid-eligible-pt-grid').jqGrid('getGridParam', 'selarrrow');
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
                            jQuery("#vb-jqgrid-eligible-sc-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        rowNum: data.scList.length,
                                        datatype: 'local',
                                        data: data.scList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#vb-jqgrid-eligible-sc-grid").clearGridData();
                        }

                        if(data.customerList[0]){
                            jQuery("#vb-jqgrid-eligible-customers-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        datatype: 'local',
                                        data: data.customerList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#vb-jqgrid-eligible-customers-grid").clearGridData();
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        if(XMLHttpRequest.status = 0){
                            jQuery("#vb-jqgrid-eligible-sc-grid").clearGridData();
                            jQuery("#vb-jqgrid-eligible-customers-grid").clearGridData();
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
        $("#vb-jqgrid-eligible-sc-grid").jqGrid({
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
                jQuery("#vb-jqgrid-eligible-cc-grid").clearGridData();
                jQuery("#vb-jqgrid-eligible-customers-grid").clearGridData();
                $('#cb_vb-jqgrid-eligible-sc-grid').attr('checked',false);
                var selectedTerritoryRows = $('#vb-jqgrid-eligible-territory-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedGeoRows = $('#vb-jqgrid-eligible-geo-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedPtRows = $('#vb-jqgrid-eligible-pt-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedScRows = $('#vb-jqgrid-eligible-sc-grid').jqGrid('getGridParam', 'selarrrow');
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
                            jQuery("#vb-jqgrid-eligible-cc-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        rowNum: data.ccList.length,
                                        datatype: 'local',
                                        data: data.ccList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#vb-jqgrid-eligible-cc-grid").clearGridData();
                        }

                        if(data.customerList[0]){
                            jQuery("#vb-jqgrid-eligible-customers-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        datatype: 'local',
                                        data: data.customerList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#vb-jqgrid-eligible-customers-grid").clearGridData();
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        if(XMLHttpRequest.status = 0){
                            jQuery("#vb-jqgrid-eligible-cc-grid").clearGridData();
                            jQuery("#vb-jqgrid-eligible-customers-grid").clearGridData();
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
                jQuery("#vb-jqgrid-eligible-cc-grid").clearGridData();
                jQuery("#vb-jqgrid-eligible-customers-grid").clearGridData();
                var selectedTerritoryRows = $('#vb-jqgrid-eligible-territory-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedGeoRows = $('#vb-jqgrid-eligible-geo-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedPtRows = $('#vb-jqgrid-eligible-pt-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedScRows = $('#vb-jqgrid-eligible-sc-grid').jqGrid('getGridParam', 'selarrrow');
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
                            jQuery("#vb-jqgrid-eligible-cc-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        rowNum: data.ccList.length,
                                        datatype: 'local',
                                        data: data.ccList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#vb-jqgrid-eligible-cc-grid").clearGridData();
                        }

                        if(data.customerList[0]){
                            jQuery("#vb-jqgrid-eligible-customers-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        datatype: 'local',
                                        data: data.customerList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#vb-jqgrid-eligible-customers-grid").clearGridData();
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        if(XMLHttpRequest.status = 0){
                            jQuery("#vb-jqgrid-eligible-cc-grid").clearGridData();
                            jQuery("#vb-jqgrid-eligible-customers-grid").clearGridData();
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
        $("#vb-jqgrid-eligible-cc-grid").jqGrid({
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
                jQuery("#vb-jqgrid-eligible-customers-grid").clearGridData();
                $('#cb_vb-jqgrid-eligible-cc-grid').attr('checked',false);
                var selectedTerritoryRows = $('#vb-jqgrid-eligible-territory-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedGeoRows = $('#vb-jqgrid-eligible-geo-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedPtRows = $('#vb-jqgrid-eligible-pt-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedScRows = $('#vb-jqgrid-eligible-sc-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedCcRows = $('#vb-jqgrid-eligible-cc-grid').jqGrid('getGridParam', 'selarrrow');
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
                            jQuery("#vb-jqgrid-eligible-customers-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        datatype: 'local',
                                        data: data.customerList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#vb-jqgrid-eligible-customers-grid").clearGridData();
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        if(XMLHttpRequest.status = 0){
                            jQuery("#vb-jqgrid-eligible-customers-grid").clearGridData();
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
                jQuery("#vb-jqgrid-eligible-customers-grid").clearGridData();
                var selectedTerritoryRows = $('#vb-jqgrid-eligible-territory-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedGeoRows = $('#vb-jqgrid-eligible-geo-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedPtRows = $('#vb-jqgrid-eligible-pt-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedScRows = $('#vb-jqgrid-eligible-sc-grid').jqGrid('getGridParam', 'selarrrow');
                var selectedCcRows = $('#vb-jqgrid-eligible-cc-grid').jqGrid('getGridParam', 'selarrrow');
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
                            jQuery("#vb-jqgrid-eligible-customers-grid")
                                    .jqGrid('setGridParam',
                                    {
                                        datatype: 'local',
                                        data: data.customerList
                                    })
                                    .trigger("reloadGrid");
                        }else{
                            jQuery("#vb-jqgrid-eligible-customers-grid").clearGridData();
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        if(XMLHttpRequest.status = 0){
                            jQuery("#vb-jqgrid-eligible-customers-grid").clearGridData();
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

//        Eligible Products List
        $("#vb-jqgrid-all-products-grid").jqGrid({
            datatype: "local",
            data:'',

            colNames: [
                'All Products',
                'Product ID'
            ],
            colModel: [
                {name: 'name', index: 'productName', width: 250, align: 'left'},
                {name: 'id', index: 'productId', width:0, hidden:true}

            ],

            sortname: 'name',
            viewrecords: true,
            sortorder: "asc",
            caption: "Eligible Products",
            autowidth: false,
            height: 80,
            multiselect: true,
            scrollOffset: 17,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                $('#vbPrimaryUom').val('');
                cleanMasterUomList('vbMasterUom');
                $('#cb_vb-jqgrid-all-products-grid').attr('checked',false);
                var selectedProductRows = $('#vb-jqgrid-all-products-grid').jqGrid('getGridParam', 'selarrrow');
                var productIds = getRowIdsAll(selectedProductRows);

                if(!productIds){
                    return false;
                }

                SubmissionLoader.showTo();
                jQuery.ajax({
                    type: 'post',
                    data: {productIds:productIds},
                    url:  "${request.contextPath}/${params.controller}/getVbPrimaryUomByProduct",
                    success: function (data, textStatus) {
                        if(data.length == 1){
                            $('#vbPrimaryUom').val(data[0].name);
                            checkAndDisableMasterUom('vbMasterUom',data[0].name);
                        }else{
                            $('#vbPrimaryUom').val('');
                            cleanMasterUomList('vbMasterUom');
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        if(XMLHttpRequest.status = 0){
                            $('#vbPrimaryUom').val('');
                            cleanMasterUomList('vbMasterUom');
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
                $('#vbPrimaryUom').val('');
                cleanMasterUomList('vbMasterUom');
                var selectedProductRows = $('#vb-jqgrid-all-products-grid').jqGrid('getGridParam', 'selarrrow');
                var productIds = getRowIdsAll(selectedProductRows);

                if(!productIds){
                    return false;
                }

                SubmissionLoader.showTo()
                jQuery.ajax({
                    type: 'post',
                    data: {productIds:productIds},
                    url:  "${request.contextPath}/${params.controller}/getVbPrimaryUomByProduct",
                    success: function (data, textStatus) {
                        if(data.length == 1){
                            $('#vbPrimaryUom').val(data[0].name);
                            checkAndDisableMasterUom('vbMasterUom',data[0].name);
                        }else{
                            $('#vbPrimaryUom').val('');
                            cleanMasterUomList('vbMasterUom');
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        if(XMLHttpRequest.status = 0){
                            $('#vbPrimaryUom').val('');
                            cleanMasterUomList('vbMasterUom');
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
        $("#vb-jqgrid-eligible-customers-grid").jqGrid({
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
            pager: '#vb-jqgrid-eligible-customers-pager',
            sortname: 'name',
            viewrecords: true,
            sortorder: "asc",
            caption: "Eligible Customers",
            autowidth: false,
            height: true,
            multiselect: true,
            scrollOffset: 17,
            loadComplete: function () {
                var rowIds = $("#vb-jqgrid-eligible-customers-grid").jqGrid('getDataIDs')
                for(key in rowIds){
                    $("#vb-jqgrid-eligible-customers-grid").jqGrid('setRowData', rowIds[key],{
                        remove: '<a  href="javascript:removeVbCustomersRow(' + rowIds[key] + ')" class="ui-icon ui-icon-trash" title="Delete"></a>'
                    })
                }
            },
            onSelectRow: function (rowid, status) {
                $('#cb_vb-jqgrid-eligible-customers-grid').attr('checked',false);
            }
        });

//        Eligible Customers for Update
        $("#vb-update-jqgrid-eligible-customers-grid").jqGrid({
            datatype: "local",
            data:${volumeBasedIncentiveCustomersList?volumeBasedIncentiveCustomersList:''},

            colNames: [
                'SI',
                'vbicId',
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
                {name: 'vbicId', index: 'vbicId', width: 60, align: 'left', hidden:true},
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
            pager: '#vb-update-jqgrid-eligible-customers-pager',
//            sortname: 'name',
            viewrecords: true,
            sortorder: "asc",
            caption: "Existing Customers List",
            autowidth: false,
            height: true,
            multiselect: false,
            scrollOffset: 17,
            loadComplete: function () {
                var i = 1;
                var rowIds = $("#vb-update-jqgrid-eligible-customers-grid").jqGrid('getDataIDs');
                for(key in rowIds){
                    $("#vb-update-jqgrid-eligible-customers-grid").jqGrid('setRowData', rowIds[key],{
                        si:i,
                        remove: '<a  href="javascript:removeVbUpdateCustomersRow(' + rowIds[key] + ')" class="ui-icon ui-icon-trash" title="Delete"></a>'
                    });
                    i++;
                }
            },
            onSelectRow: function (rowid, status) {
                $('#cb_vb-update-jqgrid-eligible-customers-grid').attr('checked',false);
            }
        });

        $("#vb-update-jqgrid-eligible-customers-grid").jqGrid('navGrid', '#vb-update-jqgrid-eligible-customers-pager', {edit: false, add: false, del: false, search: false});

        $(".ui-pg-selbox").children().each(function () {
            if ($(this).val() == -1) {
                $(this).html('All')
            }
        });

//        Grid Max Height
        $('div.ui-jqgrid-bdiv').css("max-height","250px");

//        Check 100% Achievement From
        $('#vbVolumeFrom').keypress(function(event) {
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
        $('#vbVolumeTo').keypress(function(event) {
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

//        Check Incentive Amount in Value
        $('#vbIncentiveAmount').keypress(function(event) {
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

//        Check Incentive Amount in Percentage
        $('#vbVolumeInPct').keypress(function(event) {
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

//        Check Incentive Master UOM Per Unit Cost
        $('#vbMasterUomUnitCost').keypress(function(event) {
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

    function addVbIncentive() {
        var myGrid = $("#vb-jqgrid-incentive-slab-grid");
        var slabDataList = myGrid.jqGrid('getRowData');

        var selectedProductRows = $('#vb-jqgrid-all-products-grid').jqGrid('getGridParam', 'selarrrow');
        var productIds = getRowIdsAll(selectedProductRows);

        var vbProductSetName  = $("#vbProductSetName").val();
        var volumeFrom = $("#vbVolumeFrom").val();
        var volumeTo = $("#vbVolumeTo").val();
        var incentiveAmountValue = $("#vbIncentiveAmount").val();
        var incentiveAmountPct = $("#vbVolumeInPct").val();
        var perUnitMasterUomCost = $("#vbMasterUomUnitCost").val();
        var masterUomId = $("#vbMasterUom").val();
        var primaryUom = $('#vbPrimaryUom').val();
        var incentiveType = '';
        var msg1 = '';
        var msg2 = '';

        var productGrid = $('#vb-jqgrid-all-products-grid');

        if($('input[name=incentiveType]').is(':checked')){
            incentiveType = $("input[name=incentiveType]:checked").val();

            if(incentiveType == 'amt'){
                incentiveAmountPct = '';
                $("#vbVolumeInPct").val('');
            }else if(incentiveType == 'pct'){
                incentiveAmountValue = '';
                $("#vbIncentiveAmount").val('');
            }
        }

        if(slabDataList.length > 0){
            msg1 = '';
            msg2 = '';
            for(var i=0; i<slabDataList.length; i++){
                if(vbProductSetName.toLowerCase() === slabDataList[i].productSetName.toLowerCase()){
                    if(((parseFloat(volumeFrom) >= parseFloat(slabDataList[i].volumeFrom)) && (parseFloat(volumeFrom) <= parseFloat(slabDataList[i].volumeTo))) || ((parseFloat(volumeTo) >= parseFloat(slabDataList[i].volumeFrom)) && (parseFloat(volumeTo) <= parseFloat(slabDataList[i].volumeTo)))){
                        MessageRenderer.render({
                            "messageBody": "Overlapping between lower eligibility limit to upper limit for the same product is not allowed.",
                            "messageTitle": "Volume Based Incentive",
                            "type": "0"
                        });
                        return false;
                    }

                    $.each( slabDataList[i].productIds, function( key, value ) {
                        var index = $.inArray( value, productIds );
                        if(index == -1 && value != ',') {
                            if(msg1){
                                msg1 += ', ['+productGrid.jqGrid('getCell',value,'name')+']';
                            }else{
                                msg1 = 'Missing products ['+productGrid.jqGrid('getCell',value,'name')+']';
                            }
                        }
                    });

                    $.each( productIds , function( key, value ) {
                        var index = $.inArray( value, slabDataList[i].productIds );
                        if(index == -1 && value != ','){
                            if(msg2){
                                msg2 += ', ['+productGrid.jqGrid('getCell',value,'name')+']';
                            }else{
                                msg2 = 'Trying to add products ['+productGrid.jqGrid('getCell',value,'name')+']';
                            }
                        }
                    });

                    if(msg1 && msg2){
                        MessageRenderer.render({
                            "messageBody": 'Product set not matched! <br>' + msg1+' and '+msg2+' for the same product set "'+vbProductSetName+'"',
                            "messageTitle": "Volume Based Incentive",
                            "type": "0"
                        });
                        return false;
                    }else if(msg1){
                        MessageRenderer.render({
                            "messageBody": 'Product set not matched! <br>' +  msg1+' for the same product set "'+vbProductSetName+'"',
                            "messageTitle": "Volume Based Incentive",
                            "type": "0"
                        });
                        return false;
                    }else if(msg2){
                        MessageRenderer.render({
                            "messageBody": 'Product set not matched! <br>' + msg2+' for the same product set "'+vbProductSetName+'"',
                            "messageTitle": "Volume Based Incentive",
                            "type": "0"
                        });
                        return false;
                    }
                }else{
                    $.each( productIds , function( key, value ) {
                        var index = $.inArray( value, slabDataList[i].productIds );
                        if(index != -1 && value != ','){
                            if(msg2){
                                msg2 += ', ['+productGrid.jqGrid('getCell',value,'name')+']';
                            }else{
                                msg2 = 'Overlapping products ['+productGrid.jqGrid('getCell',value,'name')+']';
                            }
                        }
                    });

                    if(msg2){
                        MessageRenderer.render({
                            "messageBody":'Product set overlapped! <br>' + msg2+' with other product set are not allowed.',
                            "messageTitle": "Volume Based Incentive",
                            "type": "0"
                        });
                        return false;
                    }
                }
            }
        }

        if (productIds && vbProductSetName && primaryUom && masterUomId && volumeFrom && volumeTo && incentiveType) {
            if((incentiveType == 'amt') && (incentiveAmountValue == '')){
                MessageRenderer.render({
                    "messageBody": "Please insert incentive amount in value.",
                    "messageTitle": "Volume Based Incentive",
                    "type": "0"
                });
                return false;
            }
            else if((incentiveType == 'pct') && (incentiveAmountPct == '')){
                MessageRenderer.render({
                    "messageBody": "Please insert incentive amount in %.",
                    "messageTitle": "Volume Based Incentive",
                    "type": "0"
                });
                return false;
            }
            else if((incentiveType == 'pct') && (perUnitMasterUomCost == '')){
                MessageRenderer.render({
                    "messageBody": "Please enter per unit master UOM cost.",
                    "messageTitle": "Volume Based Incentive",
                    "type": "0"
                });
                return false;
            }

            var dataItem = {
                si: vbRowId,
                productIds:productIds,
                productSetName: vbProductSetName,
                volumeFrom: volumeFrom,
                volumeTo: volumeTo,
                incentiveAmountValue: incentiveAmountValue,
                incentiveAmountPct: incentiveAmountPct,
                perUnitMasterUomCost: perUnitMasterUomCost,
                masterUomId: masterUomId,
                remove: '<a  href="javascript:removeVbIncentiveRow(' + vbRowId + ')" class="ui-icon ui-icon-trash" title="Delete"></a>'
            };
            myGrid.addRowData(vbRowId, dataItem, "last");

//            $('#vb-jqgrid-all-products-grid').clearGridData();
//            $('#cb_vb-jqgrid-all-products-grid').attr('checked',false);
//            $("#vbMasterProduct").val('');
//            $("#vbMainProduct").val('');
//            $("#vbProductSetName").val('');
//            $("#vbMasterUom").val('');
//            $('#vbPrimaryUom').val('');

            $("#vbVolumeFrom").val('');
            $("#vbVolumeTo").val('');
            $("#vbIncentiveAmount").val('');
            $("#vbVolumeInPct").val('');
            $("#vbMasterUomUnitCost").val('');
            $('input[name=incentiveType]').attr('checked',false);
            vbRowId++;
        }else{
            if(!productIds){
                MessageRenderer.render({
                    "messageBody": "Please select at least one product.",
                    "messageTitle": "Volume Based Incentive",
                    "type": "0"
                });
                return false;
            }
            else if(!vbProductSetName){
                MessageRenderer.render({
                    "messageBody": "Please enter product set name.",
                    "messageTitle": "Volume Based Incentive",
                    "type": "0"
                });
                return false;
            }
            else if(!primaryUom){
                MessageRenderer.render({
                    "messageBody": "Primary UOM mismatch, go to product setup and check the primary UOM.",
                    "messageTitle": "Volume Based Incentive",
                    "type": "0"
                });
                return false;
            }
            else if(!masterUomId){
                MessageRenderer.render({
                    "messageBody": "Please select the master UOM.",
                    "messageTitle": "Volume Based Incentive",
                    "type": "0"
                });
                return false;
            }
            else if(!volumeFrom){
                MessageRenderer.render({
                    "messageBody": "Please enter eligible volume from (in Master UOM).",
                    "messageTitle": "Volume Based Incentive",
                    "type": "0"
                });
                return false;
            }
            else if(!volumeTo){
                MessageRenderer.render({
                    "messageBody": "Please enter eligible volume to (in Master UOM).",
                    "messageTitle": "Volume Based Incentive",
                    "type": "0"
                });
                return false;
            }
            else if(!incentiveType){
                MessageRenderer.render({
                    "messageBody": "Please select incentive amount or percentage.",
                    "messageTitle": "Volume Based Incentive",
                    "type": "0"
                });
                return false;
            }
        }
    }

    function removeVbIncentiveRow(id){
        var grid = $("#vb-jqgrid-incentive-slab-grid");
        var slabId = grid.jqGrid('getCell',id,'vbisId');
        if(slabId){
            vbDelSlabIds.push(slabId);
        }
        grid.jqGrid("delRowData", id);
    }

    function removeVbCustomersRow(id){
        $("#vb-jqgrid-eligible-customers-grid").jqGrid("delRowData", id);
    }

    function removeVbUpdateCustomersRow(id){
        var grid = $("#vb-update-jqgrid-eligible-customers-grid");
        var slabId = grid.jqGrid('getCell',id,'vbicId');
        if(slabId){
            vbDelCustomerIds.push(slabId);
        }
        grid.jqGrid("delRowData", id);
    }

    function executeVbPrecondition(){
        if(!$("#gFormSetupIncentive").validationEngine('validate')){
            return false;
        }
        return true;
    }

    function executeAjaxIncentiveVolumeBased(){
        if(!executeVbPrecondition()){
            return false;
        }

        var allData = {};

        allData["programName"] = $("#vbProgramName").val();
        allData["effectiveDateFrom"] = $("#vbEffectiveDateFrom").val();
        allData["effectiveDateTo"] = $("#vbEffectiveDateTo").val();

        var allCustomers = [];
        var allIncentive = $("#vb-jqgrid-incentive-slab-grid").jqGrid('getRowData');
        var selectedCustomerRows = $('#vb-jqgrid-eligible-customers-grid').jqGrid('getGridParam', 'selarrrow');
        var customerRowIds = filterListOfIds(selectedCustomerRows);
        var vbProgramId = $('#vbProgramId').val();
        var vbVersion = $('#vbVersion').val();

        if(allIncentive.length>0){
            for(var i = 0; i < allIncentive.length; i++){
                allData["allIncentive.incentive["+ i +"].id"] = allIncentive[i].vbisId;
                allData["allIncentive.incentive["+ i +"].version"] = allIncentive[i].version;
                allData["allIncentive.incentive["+ i +"].productIds"] = allIncentive[i].productIds;
                allData["allIncentive.incentive["+ i +"].productSetName"] = allIncentive[i].productSetName;
                allData["allIncentive.incentive["+ i +"].volumeFrom"] = allIncentive[i].volumeFrom;
                allData["allIncentive.incentive["+ i +"].volumeTo"] = allIncentive[i].volumeTo;
                allData["allIncentive.incentive["+ i +"].incentiveAmountValue"] = allIncentive[i].incentiveAmountValue;
                allData["allIncentive.incentive["+ i +"].incentiveAmountPct"] = allIncentive[i].incentiveAmountPct;
                allData["allIncentive.incentive["+ i +"].perUnitMasterUomCost"] = allIncentive[i].perUnitMasterUomCost;
                allData["allIncentive.incentive["+ i +"].masterUomId"] = allIncentive[i].masterUomId;
            }
        }else{
            MessageRenderer.render({
                "messageBody": "Please insert at least one incentive slab into the list.",
                "messageTitle": "Volume Based Incentive",
                "type": "0"
            });
            return false;
        }

        if(customerRowIds == '' && vbProgramId == ''){
            MessageRenderer.render({
                "messageBody": "Please select at least one customer from the customer list.",
                "messageTitle": "Volume Based Incentive",
                "type": "0"
            });
            return false;
        }else{
            for(key in customerRowIds){
                allCustomers.push($("#vb-jqgrid-eligible-customers-grid").getRowData(customerRowIds[key]));
            }

            for(var i = 0; i < allCustomers.length; i++){
                allData["customerList.customer["+ i +"].customerId"] = allCustomers[i].id;
            }
        }

        var url = '';

        if(vbProgramId){
            for(var i=0; i<vbDelSlabIds.length; i++){
                allData['incentiveSlabDeleteList.delete['+ i +'].id'] = vbDelSlabIds[i];
            }

            for(var i=0; i<vbDelCustomerIds.length; i++){
                allData['incentiveCustomersDeleteList.delete['+ i +'].id'] = vbDelCustomerIds[i];
            }

            var vbExistingCustomers = $("#vb-update-jqgrid-eligible-customers-grid").jqGrid('getDataIDs');
            if(customerRowIds == '' && vbExistingCustomers == ''){
                MessageRenderer.render({
                    "messageBody": "Please select at least one customer from the customer list.",
                    "messageTitle": "Volume Based Incentive",
                    "type": "0"
                });
                return false;
            }

            url = "${request.contextPath}/${params.controller}/updateVolumeBasedIncentive?id="+vbProgramId+"&version="+vbVersion;
        }else{
            url = "${request.contextPath}/${params.controller}/createVolumeBasedIncentive";
        }

        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: allData,
            url:  url,
            success: function (data, textStatus) {
                if(data.type == 1){
                    clearVbAll();
                }
                MessageRenderer.render(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    clearVbAll();
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

    function getVbUom(id){
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: {id:id},
            url:  "${request.contextPath}/${params.controller}/getVbUomByProduct",
            success: function (data, textStatus) {
                if(data){
                    $('#vbUom').val(data.name);
                }
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

    function getProductList(){
        var vbMasterProduct = $('#vbMasterProduct').val();
        var vbMainProduct = $('#vbMainProduct').val();

        jQuery("#vb-jqgrid-all-products-grid").clearGridData();

        if(!vbMasterProduct && !vbMainProduct){
            return false;
        }

        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: {masterProductId:vbMasterProduct, mainProductId:vbMainProduct},
            url:  "${request.contextPath}/${params.controller}/getVbProductList",
            success: function (data, textStatus) {
                if(data.length > 0){
                    jQuery("#vb-jqgrid-all-products-grid")
                            .jqGrid('setGridParam',
                            {
                                datatype: 'local',
                                data: data
                            })
                            .trigger("reloadGrid");

                }else{
                    jQuery("#vb-jqgrid-all-products-grid").clearGridData();
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    jQuery("#vb-jqgrid-all-products-grid").clearGridData();
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

    function clearVbAll(){
        $('#vbProgramName').val('');
        $('#vbEffectiveDateFrom').val('');
        $('#vbEffectiveDateTo').val('');

        $("#vb-jqgrid-incentive-slab-grid").clearGridData();
        $("#vb-jqgrid-eligible-territory-grid").trigger("reloadGrid");
        $("#vb-jqgrid-eligible-geo-grid").clearGridData();
        $("#vb-jqgrid-eligible-pt-grid").clearGridData();
        $("#vb-jqgrid-eligible-sc-grid").clearGridData();
        $("#vb-jqgrid-eligible-cc-grid").clearGridData();

        $("#vb-jqgrid-eligible-customers-grid").clearGridData();
        $("#vb-update-jqgrid-eligible-customers-grid").clearGridData();
    }

    function checkAndDisableMasterUom(selector, val){
        $('#'+selector).children('option').each(function() {
            if ( $(this).text().toLowerCase() === 'liter' && val.toLowerCase() === 'ml') {
                $(this).removeAttr('disabled').siblings().attr('disabled', true);
            }
            if ( $(this).text().toLowerCase() === 'kg' && val.toLowerCase() === 'gm') {
                $(this).removeAttr('disabled').siblings().attr('disabled', true);
            }
            if ( $(this).text().toLowerCase() === 'mettric ton' && val.toLowerCase() === 'kg') {
                $(this).removeAttr('disabled').siblings().attr('disabled', true);
            }
        });

        $('#'+selector).children('option').each(function() {
            if($(this).val() == ''){
                $(this).removeAttr('disabled');
            }
        });
    }

    function cleanMasterUomList(selector){
        $('#'+selector).children('option').each(function() {
            $(this).removeAttr('disabled');
        });
    }
</script>