<%@ page import="com.docu.commons.CommonConstants" %>
<script type="text/javascript" language="Javascript">
    var productSeq = 1;
    var promotionalProductSeq = 1;
    var deletedIds = [];
    var deleteSize = 0;

    var format = 'pdf';
    var productIds  = 0;
    var packSizeIds = 0;
    var channelIds  = 0;
    var categoryIds = 0;


    function generateReport() {
        var isAll = '';
        if ($('#isAll').is(':checked')) {
            $("#isAll").val('all')
            isAll = $("#isAll").val();
        } else {
            $("#isAll").val('1')
            isAll = $("#isAll").val();
        }

        var fromDate = $("#fromDate").val();
        if (!fromDate) {
            MessageRenderer.renderErrorText("Please Select From Date");
            return
        }
        var toDate = $("#toDate").val();
        if (!toDate) {
            MessageRenderer.renderErrorText("Please Select To Date");
            return
        }
        if (isAll == 1) {
            if (!productIds) {
                MessageRenderer.renderErrorText("Please Select Product");
                return false;
            }
            if (!packSizeIds) {
                MessageRenderer.renderErrorText("Please Select Pack Size");
                return false;
            }
            if (!channelIds) {
                MessageRenderer.renderErrorText("Please Select Channel");
                return false;
            }

            if (!categoryIds) {
                MessageRenderer.renderErrorText("Please Select Category");
                return false;
            }
        } else {
            if (!channelIds) {
                MessageRenderer.renderErrorText("Please Select Channel");
                return false;
            }

            if (!categoryIds) {
                MessageRenderer.renderErrorText("Please Select Category");
                return false;
            }
        }
        if (!productIds) {
            productIds = '0'
            packSizeIds = '0'
        }

        SubmissionLoader.showTo();
        window.open("${resource(dir:'reportList', file:'salesChannelAndUserWiseDemandReport')}?format=" + format + "&productIds=" + productIds + "&packSizeIds=" + packSizeIds + "&channelIds=" + channelIds + "&categoryIds=" + categoryIds + "&fromDate=" + fromDate + "&toDate=" + toDate + "&isAll=" + isAll);
        SubmissionLoader.hideFrom();
    }
    function initDatePicker() {
        $("#toDate,#fromDate").datepicker(
                {
                    dateFormat: 'dd-mm-yy',
                    changeMonth: true,
                    changeYear: true
                });
        $('#fromDate').mask('${CommonConstants.DATE_MASK_FORMAT}', {});
        $('#toDate').mask('${CommonConstants.DATE_MASK_FORMAT}', {});
    }

    function changeFormat(val) {
        if (val == 2) {
            format = 'xlsx';
        } else {
            format = 'pdf';
        }
    }
    $(document).ready(function () {
        initDatePicker();
        $("#pdf").attr('checked', 'checked');

        $('#ui-widget-header-text').html('TerritoryConfiguration');
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormSetupBonusPromotion").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormSetupBonusPromotion").validationEngine('attach');

        $(".date-picker").datepicker(
                {
                    dateFormat: 'dd-mm-yy',
                    changeMonth: true,
                    changeYear: true
                });

        $('.date-picker').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});

        //        Eligible Customer Category
        $("#tb-jqgrid-eligible-cc-grid").jqGrid({
            datatype: "local",
            data: '',
            %{--data:${customerCategoryList},--}%

            colNames: [
                'All Category',
                'Category ID'
            ],
            colModel: [
                {name: 'name', index: 'categoryName', width: 150, align: 'left'},
                {name: 'id', index: 'categoryId', width: 0, hidden: true}

            ],

            %{--rowNum: ${customerCategoryListSize},--}%
            sortname: 'name',
            viewrecords: true,
            sortorder: "asc",
            caption: "Customer Category",
            autowidth: false,
//            autoheight: false,
            height: 350,
            multiselect: true,
            scrollOffset: 28,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                $('#cb_tb-jqgrid-eligible-cc-grid').attr('checked', false);
                var selectedCategoryRows = $('#tb-jqgrid-eligible-cc-grid').jqGrid('getGridParam', 'selarrrow');
                var territoryIds = getCategoryRowIds(selectedCategoryRows);
            },
            onSelectAll: function (aRowids, status) {
                var selectedTerritoryRows = $('#tb-jqgrid-eligible-cc-grid').jqGrid('getGridParam', 'selarrrow');
                var territoryIds = getCategoryRowIds(selectedTerritoryRows);

            }
        });

        //Eligible Territory
        $("#tb-jqgrid-eligible-territory-grid").jqGrid({
            datatype: "local",
            data:${productList},

            colNames: [
                'All Product',
                'Product ID'
            ],
            colModel: [
                {name: 'name', index: 'name', width: 150, align: 'left'},
                {name: 'id', index: 'id', width: 0, hidden: true}

            ],

            rowNum: ${productListSize},
            sortname: 'name',
            viewrecords: true,
            sortorder: "asc",
            caption: "Product",
            autowidth: false,
            height: 350,
            multiselect: true,
            scrollOffset: 28,
            loadComplete: function () {

            },
            onSelectRow: function (rowid, status) {
                jQuery("#tb-jqgrid-eligible-geo-grid").clearGridData();
                $('#cb_tb-jqgrid-eligible-territory-grid').attr('checked', false);
                var selectedTerritoryRows = $('#tb-jqgrid-eligible-territory-grid').jqGrid('getGridParam', 'selarrrow');
                var pIds = getRowIdsAll(selectedTerritoryRows);
                if (pIds) {
                    SubmissionLoader.showTo();
                    jQuery.ajax({
                        type: 'post',
                        data: {productIds: productIds},
                        url: "${request.contextPath}/${params.controller}/getProductPackSizeByProduct",
                        success: function (data, textStatus) {
                            if (data.packSizeList[0]) {
                                jQuery("#tb-jqgrid-eligible-geo-grid")
                                        .jqGrid('setGridParam',
                                        {
                                            rowNum: data.packSizeList.length,
                                            datatype: 'local',
                                            data: data.packSizeList
                                        })
                                        .trigger("reloadGrid");
                            } else {
                                jQuery("#tb-jqgrid-eligible-geo-grid").clearGridData();
                            }
                        },
                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                            if (XMLHttpRequest.status = 0) {
                                jQuery("#tb-jqgrid-eligible-geo-grid").clearGridData();
//                            jQuery("#tb-jqgrid-eligible-customers-grid").clearGridData();
                                MessageRenderer.renderErrorText("Network Problem: Time out");
                            } else {
                                MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                            }
                        },
                        complete: function () {
                            SubmissionLoader.hideFrom();
                        },
                        dataType: 'json'
                    });
                }
            },
            onSelectAll: function (aRowids, status) {
                jQuery("#tb-jqgrid-eligible-geo-grid").clearGridData();
                var selectedProIds = $('#tb-jqgrid-eligible-territory-grid').jqGrid('getGridParam', 'selarrrow');
                var pIds = getRowIdsAll(selectedProIds);
                if (pIds) {
                    SubmissionLoader.showTo();
                    jQuery.ajax({
                        type: 'post',
                        data: {productIds: productIds},
                        url: "${request.contextPath}/${params.controller}/getProductPackSizeByProduct",
                        success: function (data, textStatus) {
                            if (data.packSizeList[0]) {
                                jQuery("#tb-jqgrid-eligible-geo-grid")
                                        .jqGrid('setGridParam',
                                        {
                                            rowNum: data.packSizeList.length,
                                            datatype: 'local',
                                            data: data.packSizeList
                                        })
                                        .trigger("reloadGrid");
                            } else {
                                jQuery("#tb-jqgrid-eligible-geo-grid").clearGridData();
                            }
                        },
                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                            if (XMLHttpRequest.status = 0) {
                                jQuery("#tb-jqgrid-eligible-geo-grid").clearGridData();
                                MessageRenderer.renderErrorText("Network Problem: Time out");
                            } else {
                                MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                            }
                        },
                        complete: function () {
                            SubmissionLoader.hideFrom();
                        },
                        dataType: 'json'
                    });
                }
            }
        });
        $("#tb-jqgrid-eligible-customers-grid").jqGrid({
            datatype: "local",
            data:${customerSalesChannelList},

            colNames: [
                'All Sales Channel',
                'Sales Channel ID'
            ],
            colModel: [
                {name: 'name', index: 'name', width: 150, align: 'left'},
                {name: 'id', index: 'id', width: 0, hidden: true}

            ],

            rowNum: ${customerSalesChannelListSize},
            sortname: 'name',
            viewrecords: true,
            sortorder: "asc",
            caption: "Customer Sales Channel",
            autowidth: false,
            height: 350,
            multiselect: true,
            scrollOffset: 28,
            loadComplete: function () {

            },
            onSelectRow: function (rowid, status) {
                jQuery("#tb-jqgrid-eligible-cc-grid").clearGridData();
                $('#cb_tb-jqgrid-eligible-territory-grid').attr('checked', false);
                var selectedGeoRows = $('#tb-jqgrid-eligible-customers-grid').jqGrid('getGridParam', 'selarrrow');
                var channelIds = getChannelRowIdsAll(selectedGeoRows);
                if (channelIds) {
                    SubmissionLoader.showTo();
                    jQuery.ajax({
                        type: 'post',
                        data: {channelIds: channelIds},
                        url: "${request.contextPath}/${params.controller}/getCategoryByChannelId",
                        success: function (data, textStatus) {
                            if (data.categoryList[0]) {
                                jQuery("#tb-jqgrid-eligible-cc-grid")
                                        .jqGrid('setGridParam',
                                        {
                                            rowNum: data.categoryList.length,
                                            datatype: 'local',
                                            data: data.categoryList
                                        })
                                        .trigger("reloadGrid");
                            } else {
                                jQuery("#tb-jqgrid-eligible-cc-grid").clearGridData();
                            }
                        },
                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                            if (XMLHttpRequest.status = 0) {
                                jQuery("#tb-jqgrid-eligible-cc-grid").clearGridData();
                                MessageRenderer.renderErrorText("Network Problem: Time out");
                            } else {
                                MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                            }
                        },
                        complete: function () {
                            SubmissionLoader.hideFrom();
                        },
                        dataType: 'json'
                    });
                }
            },
            onSelectAll: function (aRowids, status) {
                jQuery("#tb-jqgrid-eligible-cc-grid").clearGridData();
                var selectedChannelIds = $('#tb-jqgrid-eligible-customers-grid').jqGrid('getGridParam', 'selarrrow');
                var channelIds = getChannelRowIdsAll(selectedChannelIds);
                if (channelIds) {
                    SubmissionLoader.showTo();
                    jQuery.ajax({
                        type: 'post',
                        data: {channelIds: channelIds},
                        url: "${request.contextPath}/${params.controller}/getCategoryByChannelId",
                        success: function (data, textStatus) {
                            if (data.categoryList[0]) {
                                jQuery("#tb-jqgrid-eligible-cc-grid")
                                        .jqGrid('setGridParam',
                                        {
                                            rowNum: data.categoryList.length,
                                            datatype: 'local',
                                            data: data.categoryList
                                        })
                                        .trigger("reloadGrid");
                            } else {
                                jQuery("#tb-jqgrid-eligible-cc-grid").clearGridData();
                            }
                        },
                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                            if (XMLHttpRequest.status = 0) {
                                jQuery("#tb-jqgrid-eligible-cc-grid").clearGridData();
//                            jQuery("#tb-jqgrid-eligible-customers-grid").clearGridData();
                                MessageRenderer.renderErrorText("Network Problem: Time out");
                            } else {
                                MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                            }
                        },
                        complete: function () {
                            SubmissionLoader.hideFrom();
                        },
                        dataType: 'json'
                    });
                }
            }
        });
        $("#tb-jqgrid-eligible-geo-grid").jqGrid({
            datatype: "local",
            data: '',
            colNames: [
                'Pack Size',
                'Finish Product ID'
            ],
            colModel: [
                {name: 'name', index: 'name', width: 150, align: 'left'},
                {name: 'id', index: 'id', width: 0, hidden: true}

            ],

//            sortname: 'id',
            viewrecords: true,
            sortorder: "asc",
            caption: "Measurement Unit",
//            autowidth: false,
            width: 220,
            height: 350,
            multiselect: true,
            scrollOffset: 28,
            loadComplete: function () {

            },
            onSelectRow: function (rowid, status) {
                $('#cb_tb-jqgrid-eligible-territory-grid').attr('checked', false);
                var selectedTerritoryRows = $('#tb-jqgrid-eligible-geo-grid').jqGrid('getGridParam', 'selarrrow');
                var territoryIds = getPackSizeRowIds(selectedTerritoryRows);

            },
            onSelectAll: function (aRowids, status) {
                var selectedTerritoryRows = $('#tb-jqgrid-eligible-geo-grid').jqGrid('getGridParam', 'selarrrow');
                var territoryIds = getPackSizeRowIds(selectedTerritoryRows);
            }
        });
        $(".ui-pg-selbox").children().each(function () {
            if ($(this).val() == -1) {
                $(this).html('All')
            }
        });
    });
    function getChannelRowIdsAll(selectedRows) {
        var result = [];
        $.each(selectedRows, function (i, e) {
            if ($.inArray(e, result) == -1) result.push(e);
        });
        var selectedRowsFinal = result.filter(function (n) {
            return n != undefined
        });
        var ids = ""
        for (key in selectedRowsFinal) {
            if (ids) {
                ids += ',' + selectedRowsFinal[key]
            } else {
                ids = selectedRowsFinal[key]
            }
        }
        channelIds = ids
//        alert("ids " + channelIds)
        return channelIds;
    }
    function getCategoryRowIds(selectedRows) {
        var result = [];
        $.each(selectedRows, function (i, e) {
            if ($.inArray(e, result) == -1) result.push(e);
        });
        var selectedRowsFinal = result.filter(function (n) {
            return n != undefined
        });
        var ids = ""
        for (key in selectedRowsFinal) {
            if (ids) {
                ids += ',' + selectedRowsFinal[key]
            } else {
                ids = selectedRowsFinal[key]
            }
        }
        categoryIds = ids
//        alert("ids " + categoryIds)
        return categoryIds;
    }
    function getRowIdsAll(selectedRows) {
        var result = [];
        $.each(selectedRows, function (i, e) {
            if ($.inArray(e, result) == -1) result.push(e);
        });
        var selectedRowsFinal = result.filter(function (n) {
            return n != undefined
        });
        var ids = ''
        for (key in selectedRowsFinal) {
            if (ids) {
                ids += ',' + selectedRowsFinal[key]
            } else {
                ids = selectedRowsFinal[key]
            }
        }
        /*if(ids){
         ids += ",'"+selectedRowsFinal[key]+"'"
         }else{
         ids = "'"+selectedRowsFinal[key]+"'"
         }*/
        productIds = ids
//        alert("ids " + productIds)
        return productIds;
    }
    function getPackSizeRowIds(selectedRows) {
        var result = [];
        $.each(selectedRows, function (i, e) {
            if ($.inArray(e, result) == -1) result.push(e);
        });
        var selectedRowsFinal = result.filter(function (n) {
            return n != undefined
        });
        var ids = ""
        for (key in selectedRowsFinal) {
            if (ids) {
                ids += ',' + selectedRowsFinal[key]
            } else {
                ids = selectedRowsFinal[key]
            }
        }
        packSizeIds = ids
//        alert("packSizeIds " + packSizeIds)
        return packSizeIds;
    }
    function executePsPrecondition() {
        if (!$("#gFormSetupBonusPromotion").validationEngine('validate')) {
            return false;
        }
        return true;
    }

</script>