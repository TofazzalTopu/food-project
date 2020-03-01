<%@ page import="com.bits.bdfp.util.ApplicationConstants" %>
<script type="text/javascript" language="Javascript">

    var deleteId = -1;
    var deleteSize = 0;
    var deletedId = [];
    var territoryDeleteSize = 0;
    var territoryDeleteId = [];
    var territoryAddedId = [];
    var territoryAddedSize = 0;
    var url = '';
    var url2 = '';
    var present = [];
    var size = 0;
    var rowIndex = -1;

    $(document).ready(function () {


        var c =${com.bits.bdfp.util.ApplicationConstants.CUSTOMER_PAYMENT_TYPE_CASH_ID};
        var id = document.getElementById('customerPaymentType').value
        if (id == c) {
            $("#customerCreditLimit").attr("disabled", "disabled");
            $("#creditPeriodInDays").attr("disabled", "disabled");
            $("#customerCreditLimit").val(0);
            $("#creditPeriodInDays").val(0);
        } else {
            $("#customerCreditLimit").attr("disabled", false);
            $("#creditPeriodInDays").attr("disabled", false);
        }
        //Change function for customerPaymentType
        $("#customerPaymentType").change(function () {
            var id =${com.bits.bdfp.util.ApplicationConstants.CUSTOMER_PAYMENT_TYPE_CASH_ID};
            if ($("#customerPaymentType").val() == id) {
                $("#customerCreditLimit").attr("disabled", "disabled");
                $("#creditPeriodInDays").attr("disabled", "disabled");
                $("#customerCreditLimit").val(0);
                $("#creditPeriodInDays").val(0);
            } else {
                $("#customerCreditLimit").attr("disabled", false);
                $("#creditPeriodInDays").attr("disabled", false);
            }
        });


        $('#ui-widget-header-text').html('CustomerMaster');
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        populateEdit();

        $("#gFormCustomerMaster").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormCustomerMaster").validationEngine('attach');
        resetall();

        $(function () {
            $("#tabs").tabs();
        });

        $("#territory-detail-grid").jqGrid({
//            url: url,
            datatype: "json",
            colNames: [
                'ID',
                'Territory ID',
//                'Country',
//                'Division',
//                'District',
//                'Thana Upazila Pouroshova',
                'Territory Name',
                'Geo Location',
                'Para/Locality',
                'Road',
                ''
            ],
            colModel: [
                {name: 'id', index: 'id', width: 100, align: 'left', hidden: true},
                {name: 'territory', index: 'territory', width: 100, align: 'left', hidden: true},
                {name: 'territoryName', index: 'territoryName', width: 0, align: 'left', hidden: true},
//                {name: 'division', index: 'division', width: 100, align: 'left'},
//                {name: 'district', index: 'district', width: 100, align: 'left'},
//                {name: 'thanaUpazilaPouroshova', index: 'thanaUpazilaPouroshova', width: 100, align: 'left'},
//                {name: 'unionInfo', index: 'unionInfo', width: 100, align: 'left'},
                {name: 'geoLocation', index: 'geoLocation', width: 95, align: 'left'},
                {name: 'paraOrLocality', index: 'paraOrLocality', width: 95, align: 'left'},
                {name: 'road', index: 'road', width: 80, align: 'left'},
                {
                    name: 'select', index: 'select', width: 70, align: 'left'

                }
            ],
            rowNum: -1,
//            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
//            pager: '#territory-detail-grid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Geo Locations",
            autowidth: true,
            height: true,
//            autowidth: true,
            scrollOffset: 0,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
//                executeEditTerritorySubAreaConfiguration();
            }
        });
        $("#territory-detail-grid").jqGrid('navGrid', '#territory-detail-grid-pager', {
            edit: false,
            add: false,
            del: false,
            search: false
        });

        $("#added-territory-detail-grid").jqGrid({
            url: url,
            datatype: "json",
            colNames: [
                'ID',
                'Territory ID',
//                'Country',
//                'Division',
//                'District',
//                'Thana Upazila Pouroshova',
                'Territory Name',
                'Geo Location',
                'Para/Locality',
                'Road',
                'Delete'
            ],
            colModel: [
                {name: 'id', index: 'id', width: 100, align: 'left', hidden: true},
                {name: 'territory', index: 'territory', width: 100, align: 'left', hidden: true},
                {name: 'territoryName', index: 'territoryName', width: 100, align: 'left'},
//                {name: 'division', index: 'division', width: 100, align: 'left'},
//                {name: 'district', index: 'district', width: 100, align: 'left'},
//                {name: 'thanaUpazilaPouroshova', index: 'thanaUpazilaPouroshova', width: 100, align: 'left'},
//                {name: 'unionInfo', index: 'unionInfo', width: 100, align: 'left'},
                {name: 'geoLocation', index: 'geoLocation', width: 100, align: 'left'},
                {name: 'paraOrLocality', index: 'paraOrLocality', width: 100, align: 'left'},
                {name: 'road', index: 'road', width: 80, align: 'left'},
                {
                    name: 'delete', index: 'delete', width: 46, align: 'right'
                }
            ],
            rowNum: -1,
//            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
//            pager: '#territory-detail-grid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Added Geo Locations" + '<span class="mendatory_field"> * </span>',
            autowidth: true,
            height: true,
//            width: true,
            scrollOffset: 0,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
//                executeEditTerritorySubAreaConfiguration();
            }
        });

        $("#ship-address-grid").jqGrid({
            url: url2,
            datatype: "json",
            colNames: [
//                'SL',
                'ID',
                'Customer Name',
                'Address',
                'Delete'
            ],
            colModel: [
//                {name: 'sl', index: 'sl', width: 30, sortable: false, align: 'left'},
                {name: 'id', index: 'id', width: 100, align: 'left', hidden: true},
//                {name: 'sl', index: 'sl', width: 30, sortable: false, align: 'left'},
                {name: 'name', index: 'name', width: 100, align: 'left'},
                {name: 'address', index: 'address', width: 100, align: 'left'},
                {
                    name: 'delete',
                    search: false,
                    index: 'delete',
                    width: 25,
                    sortable: false,
                    formatter: function (rowId, cellval, colpos, rwdat, _act) {
                        var rowInterviewId
                        if ('${customerMaster.id}' != '') {
                            if (colpos[0])
                                rowInterviewId = colpos[0].toString();
                            else
                                rowInterviewId = colpos.id.toString();
                        } else {
                            rowInterviewId = colpos.id.toString();
                        }
//                        $.each(data.rows, function (i, item) {
//                            var rowData = $("#jqgrid-grid-finishProduct").jqGrid('getRowData', data.rows[i].id);
//                            rowData.delete = '<a  href="javascript:deleteProduct(' + data.rows[i].id + ')" class="ui-icon ui-icon-trash" title="Delete"></a>';
//                            $('#jqgrid-grid-finishProduct').jqGrid('setRowData', data.rows[i].id, rowData);
//                        });
                        return '<a  href="javascript:deleteShipRecords(' + rowInterviewId + ')" class="ui-icon ui-icon-trash" title="Delete"></a>';
//                        return "<input type='button' id='" + rowInterviewId + "' value='delete' class='btn' onClick='deleteShipRecords(this)' />";
                    },
                    align: 'center'
                }
            ],
            rowNum: 50,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#ship-address-grid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Shipping Address" + '<span class="mendatory_field"> * </span>',
            autowidth: false,
            height: true,
            width: 785,
            scrollOffset: 0,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
//                executeEditTerritorySubAreaConfiguration();
            }
        });
        $("#ship-address-grid").jqGrid('navGrid', '#ship-address-grid-pager', {
            edit: false,
            add: false,
            del: false,
            search: false
        });

        $("#customerCreditLimit").format({precision: 2, allow_negative: false, autofix: true});
        $("#creditPeriodInDays").format({precision: 0, allow_negative: false, autofix: true});
        document.getElementById("pricingCategory").remove(3);
    });

    function cboxFormatter(cellvalue, options, rowObject) {
        return '<input type="checkbox" ' + (cellvalue == 'true' ? ' checked="checked"' : '') +
                'value="false" onchange="if(this.checked){addTerritoryRecords(' + options.rowId + ')}else{deleteTerritoryRecords(' + options.rowId + ')};"/>';
    }

    function populateEdit() {
        if ('${customerMaster.id}' != '') {
            $('#id').val('${customerMaster.id}');
            url = '${resource(dir:'customerTerritorySubArea', file:'list')}?id=' + '${customerMaster.id}';
            url2 = '${resource(dir:'customerShippingAddress', file:'list')}?id=' + '${customerMaster.id}';
        }
    }

    function resetall() {
        if ('${customerMaster.id}' == '') {
            var id = '';
            if (size == 1) {
                id = $("#enterpriseConfiguration").val();
            }
            $("#territory-detail-grid").clearGridData();
            $("#ship-address-grid").clearGridData();
            $("#customerContactType").val(null);
            $("#customerLevel").val("");
            $("#customerSalesChannel").val(null);
            $("#customerPriority").val(null);
            $("#pricingCategory").val(null);
            $("#category").val(null);
            $("#customerType").val(null);
            $("#customerPaymentType").val(null);
            reset_form('#gFormCustomerMaster');
            $("#enterpriseConfiguration").val(id);
            listTerritory(id);
        } else {
            $("#enterpriseConfiguration").val('${customerMaster.enterpriseConfiguration?.id}').attr("selected", "selected");
            listTerritory($("#enterpriseConfiguration").val());
        }
    }

    function getSelectedCustomerMasterId() {
        var customerMasterId = null;
        var rowid = $("#jqgrid-grid").jqGrid('getGridParam', 'selrow');
        if (rowid) {
            customerMasterId = $("#jqgrid-grid").jqGrid('getCell', rowid, 'id');
        }
        if (customerMasterId == null) {
            customerMasterId = $('#gFormCustomerMaster input[name = id]').val();
        }
        return customerMasterId;
    }
    function executePreConditionCustomerMaster() {
        // trim field vales before process.
        trim_form();
        var isError = false;
        if (!$("#gFormCustomerMaster").validationEngine('validate')) {
            isError = true;
        }
        if (!validateCustomerBasicInfo()) return false;
        if (!validateCustomerAddress()) return false;
        if (!validateCustomerFinancialInfo()) return false;
        if (!validateCustomerAssignment()) return false;
        if (!validateShippingAddress()) return false;
        if (isError) return false;
        return true;
    }

    function validateCustomerBasicInfo() {
        var tabid = 0;      // Basic Info Tab
        var ctl = $('#name');
        if (!checkTextFieldAndFocus(tabid, ctl)) return false;
        ctl = $('#legacyId');
        if (!checkTextFieldAndFocus(tabid, ctl)) return false;
        ctl = $('#customerType');
        if (!checkDropDownAndFocus(tabid, ctl)) return false;
        ctl = $('#category');
        if (!checkDropDownAndFocus(tabid, ctl)) return false;
        ctl = $('#pricingCategory');
        if (!checkDropDownAndFocus(tabid, ctl)) return false;
        ctl = $('#customerPriority');
        if (!checkDropDownAndFocus(tabid, ctl)) return false;
        ctl = $('#customerSalesChannel');
        if (!checkDropDownAndFocus(tabid, ctl)) return false;

        ctl = $('#contactPerson');
        if (!checkTextFieldAndFocus(tabid, ctl)) return false;
        ctl = $('#contactNo');
        if (!checkTextFieldAndFocus(tabid, ctl)) return false;

        ctl = $('#customerContactType');
        if (!checkDropDownAndFocus(tabid, ctl)) return false;
        ctl = $('#customerLevel');
        if (!checkDropDownAndFocus(tabid, ctl)) return false;
        return true;
    }

    function validateCustomerAddress() {
        var tabid = 1;      // HouseHold Info Tab
        var ctl = $('#presentAddress');
        if (!checkTextFieldAndFocus(tabid, ctl)) return false;
        ctl = $('#permanentAddress');
        if (!checkTextFieldAndFocus(tabid, ctl)) return false;
        return true;
    }

    function validateCustomerFinancialInfo() {
        var tabid = 2;      // HouseHold Info Tab
        var ctl = $('#customerPaymentType');
        if (!checkDropDownAndFocus(tabid, ctl)) return false;
        ctl = $('#customerCreditLimit');
        if (!checkTextFieldAndFocus(tabid, ctl)) return false;
        ctl = $('#creditPeriodInDays');
        if (!checkTextFieldAndFocus(tabid, ctl)) return false;
        return true;
    }

    function validateCustomerAssignment() {
        var tabid = 3;      // HouseHold Info Tab
//        var ctl = $('#territoryConfiguration');
//        if (!checkDropDownAndFocus(tabid, ctl)) return false;
        var geoLocationCount = $("#territory-detail-grid").jqGrid('getGridParam', 'records');
        if (geoLocationCount <= 0) {
            MessageRenderer.renderErrorText("Geo location is not added");
            $('#tabs').tabs('select', tabid);
            return false;
        }
        return true;
    }

    function validateShippingAddress() {
        var tabid = 4;
        var shippingAddressCount = $("#ship-address-grid").jqGrid('getGridParam', 'records');
        if (shippingAddressCount <= 0) {
            MessageRenderer.renderErrorText("Shipping Address is not added");
            $('#tabs').tabs('select', tabid);
            return false;
        }
        return true;
    }

    function executeAjaxCustomerMaster() {
        if (!executePreConditionCustomerMaster()) {
            return false;
        }
        var actionUrl = null;
        var data = null;

        var gridCollection2 = jQuery("#ship-address-grid").jqGrid('getRowData');
        var shipLength = gridCollection2.length;
        data = jQuery("#gFormCustomerMaster").serialize();
        if ($('#create-button').val() == 'Update') {
            actionUrl = "${request.contextPath}/${params.controller}/update";
            data = data + '&deletedEntries=' + deletedId;
            data = data + '&deletedEntriesCount=' + deleteSize;
            var count = 0;
            for (var i = 0; i < shipLength; i++) {
                if (gridCollection2[i].id < 0) {
                    data = data + '&items.shipToAddress[' + count + '].address=' + gridCollection2[i].address;
                    count++;
                }
            }
            data = data + '&shipSize=' + count;
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/create";
            for (var i = 0; i < shipLength; i++) {
                data = data + '&items.shipToAddress[' + i + '].address=' + gridCollection2[i].address;
            }
            data = data + '&shipSize=' + shipLength;
        }
        var gridCollection = jQuery("#added-territory-detail-grid").jqGrid('getRowData');
        for (var i = 0; i < gridCollection.length; i++) {
            if(gridCollection[i].id == '') {
                data = data + '&items2.customerTerritorySubArea[' + i + '].territorySubArea.id=' + gridCollection[i].territory;
            }
        }
//        data = data + '&territoryList=' + territoryAddedId;
//        data = data + '&territoryListCount=' + territoryAddedSize;

        if(territoryDeleteId){
            for(var t=0; t < territoryDeleteId.length;t++){
                jQuery('#territory-detail-grid').delRowData(territoryDeleteId[t])
            }
        }

        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: data,
            url : actionUrl,
            success: function (data, textStatus) {
                executePostConditionCustomerMaster(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if (XMLHttpRequest.status = 0) {
                    if ($('#id').val() == '') {
                        var id = '';
                        if (size == 1) {
                            id = $("#enterpriseConfiguration").val();
                        }
                        $("#territory-detail-grid").clearGridData();
                        $("#added-territory-detail-grid").clearGridData();
                        $("#ship-address-grid").clearGridData();
                        $("#customerLevel").val('');
                        $("#customerContactType").val(null);
                        $("#customerSalesChannel").val(null);
                        $("#customerPriority").val(null);
                        $("#pricingCategory").val(null);
                        $("#category").val(null);
                        $("#customerType").val(null);
                        listTerritory('');
                        $("#customerPaymentType").val(null);
                        reset_form('#gFormCustomerMaster');
                        $("#enterpriseConfiguration").val(id);
                        listTerritory(id);
                        MessageRenderer.renderErrorText("Network Problem: Time out");
                    } else {
                    }
                    territoryDeleteId = [];
                    territoryDeleteSize = 0;
                    territoryAddedId = [];
                    territoryAddedSize = 0;
                    deletedId = [];
                    deleteSize = 0;
                    present = [];
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
        return false;
    }

    function executePostConditionCustomerMaster(result) {
        if (result.type == 1) {
            if ($('#id').val() == '') {
                var id = '';
                if (size == 1) {
                    id = $("#enterpriseConfiguration").val();
                }
                $("#territory-detail-grid").clearGridData();
                //$("#territory-detail-grid").trigger('reloadGrid');
                $("#added-territory-detail-grid").clearGridData();
                $("#ship-address-grid").clearGridData();
                $("#customerLevel").val('');
                $("#customerContactType").val(null);
                $("#customerSalesChannel").val(null);
                $("#customerPriority").val(null);
                $("#pricingCategory").val(null);
                $("#category").val(null);
                $("#customerType").val(null);
                listTerritory('');
                $("#customerPaymentType").val(null);
                reset_form('#gFormCustomerMaster');
                $("#enterpriseConfiguration").val(id);
                listTerritory(id);
            } else {

            }
            territoryDeleteId = [];
            territoryDeleteSize = 0;
            territoryAddedId = [];
            territoryAddedSize = 0;
            deletedId = [];
            deleteSize = 0;
            present = [];
        }
        MessageRenderer.render(result);
    }
    function deleteAjaxCustomerMaster() {    // Delete record
        var customerMasterId = $('#id').val();
        if (executePreConditionForDeleteCustomerMaster(customerMasterId)) {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-customerMaster").dialog({
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
                            url: "${resource(dir:'customerMaster', file:'delete')}?id=" + customerMasterId,
                            success: function (message) {
                                executePostConditionForDeleteCustomerMaster(message);
                            }
                        });
                    },
                    complete: function () {

                    },
                    Cancel: function () {
                        $(this).dialog('close');
                    }
                }
            }); //end of dialog
        }
    }

    function executePreConditionForEditCustomerMaster(customerMasterId) {
        if (customerMasterId == null) {
            alert("Please select a customerMaster to edit");
            return false;
        }
        return true;
    }
    function executeEditCustomerMaster() {
        var customerMasterId = getSelectedCustomerMasterId();
        if (executePreConditionForEditCustomerMaster(customerMasterId)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'customerMaster', file:'edit')}?id=" + customerMasterId,
                success: function (entity) {
                    executePostConditionForEditCustomerMaster(entity);
                },
                dataType: 'json'
            });
        }
    }
    function executePostConditionForEditCustomerMaster(data) {
        if (data == null) {
            alert('Selected customerMaster might have been deleted by someone');  //Message renderer
        } else {
            showCustomerMaster(data);
        }
    }
    function executePreConditionForDeleteCustomerMaster(customerMasterId) {
        if (customerMasterId == null) {
            alert("Please select a customerMaster to delete");
            return false;
        }
        return true;
    }
    function executePostConditionForDeleteCustomerMaster(data) {
        if (data.type == 1) {
            $("#customerList").val('');
            $('#popEmpDetails').html('');
            //$("#territory-detail-grid").trigger('GridUnload');
        }
        MessageRenderer.render(data)
    }
    function showCustomerMaster(entity) {
        $('#gFormCustomerMaster input[name = id]').val(entity.id);
        $('#gFormCustomerMaster input[name = version]').val(entity.version);

        if (entity.enterpriseConfiguration) {
            $('#enterpriseConfiguration').val(entity.enterpriseConfiguration.id).attr("selected", "selected");
        }else {
            $('#enterpriseConfiguration').val(entity.enterpriseConfiguration);
        }
        $('#name').val(entity.name);
        $('#alternativeName').val(entity.alternativeName);
        $('#legacyId').val(entity.legacyId);
        if (entity.customerType) {
            $('#customerType').val(entity.customerType.id).attr("selected", "selected");
        }
        else {
            $('#customerType').val(entity.customerType);
        }
        if (entity.category) {
            $('#category').val(entity.category.id).attr("selected", "selected");
        }
        else {
            $('#category').val(entity.category);
        }
        if (entity.pricingCategory) {
            $('#pricingCategory').val(entity.pricingCategory.id).attr("selected", "selected");
        }
        else {
            $('#pricingCategory').val(entity.pricingCategory);
        }
        if (entity.customerPriority) {
            $('#customerPriority').val(entity.customerPriority.id).attr("selected", "selected");
        }
        else {
            $('#customerPriority').val(entity.customerPriority);
        }
        if (entity.customerSalesChannel) {
            $('#customerSalesChannel').val(entity.customerSalesChannel.id).attr("selected", "selected");
        }
        else {
            $('#customerSalesChannel').val(entity.customerSalesChannel);
        }
        $('#contactPerson').val(entity.contactPerson);
        $('#contactNo').val(entity.contactNo);
        if (entity.customerContactType) {
            $('#customerContactType').val(entity.customerContactType.id).attr("selected", "selected");
        }
        else {
            $('#customerContactType').val(entity.customerContactType);
        }

        if (entity.customerLevel) {
            $('#customerLevel').val(entity.customerLevel).attr("selected", "selected");
        }
        else {
            $('#customerLevel').val("").attr("selected", "selected");
        }
        $('#presentAddress').val(entity.presentAddress);
        $('#permanentAddress').val(entity.permanentAddress);
        if (entity.customerPaymentType) {
            $('#customerPaymentType').val(entity.customerPaymentType.id).attr("selected", "selected");
        }
        else {
            $('#customerPaymentType').val(entity.customerPaymentType);
        }
        $('#customerCreditLimit').val(entity.customerCreditLimit);
        $('#creditPeriodInDays').val(entity.creditPeriodInDays);
        $('#isImmediate').attr('checked', entity.isImmediate);

        $('#create-button').attr('value', 'Update');
        $('#cancel-button').attr('value', 'Cancel');
        $('#delete-button').show();
    }
    function executeSearchCustomerMaster(fieldName, fieldValue) {
        if (executePreConditionForSearchCustomerMaster(fieldName, fieldValue)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'customerMaster', file:'search')}?fieldName=" + fieldName + "&fieldValue=" + fieldValue,
                success: function (entity) {
                    executePostConditionForSearchCustomerMaster(entity, fieldName, fieldValue);
                },
                dataType: 'json'
            });
        }
    }
    function executePreConditionForSearchCustomerMaster(fieldName, fieldValue) {
        // trim field vales before process.
        $('#' + fieldName).val($.trim($('#' + fieldName).val()));
        if (fieldValue == '' || fieldValue == null) {
            reset_form("#gFormCustomerMaster");
            return false;
        }
        return true;
    }
    function executePostConditionForSearchCustomerMaster(data, fieldName, fieldValue) {
        if (data == null) {
            reset_form("#gFormCustomerMaster"); // Clear Form
            $('#' + fieldName).val(fieldValue); // Set search field with fieldValue
        } else {
            showCustomerMaster(data);
        }
    }

    function populateGrid(id) {
//        if (!id) {
//            $("#territory-detail-grid").clearGridData();
//        }
        // alert(id)
        %{--var ids = [];--}%
        %{--if ('${customerMaster.id}' == '') {--}%
        %{--ids = territoryAddedId;--}%
        %{--} else {--}%
        %{--var gridCollection2 = jQuery("#territory-detail-grid").jqGrid('getRowData');--}%
        %{--if (present.length == 0) {--}%
        %{--for (var i = 0; i < gridCollection2.length; i++) {--}%
        %{--present[i] = gridCollection2[i].territory;--}%
        %{--}--}%
        %{--}--}%
        %{--if (present == 0) {--}%
        %{--ids = territoryAddedId;--}%
        %{--} else {--}%
        %{--ids = present + ',' + territoryAddedId;--}%
        %{--}--}%
        %{--//            alert(present + '___' + territoryAddedId + '___' + territoryDeleteId);--}%
        %{--}--}%
        jQuery("#territory-detail-grid").jqGrid().setGridParam({
            url: '${resource(dir:'customerMaster', file:'fetchSubArea')}?id=' + id
        }).trigger("reloadGrid")
    }

    function addNewItemToCollectionGrid() {
        if ($("#address").val() == '' || $("#shipName").val() == '') {
            alert("Please Enter A Customer Name and/or Valid Address.");
            return false;
        }
        var myGrid = $("#ship-address-grid");
        var dataItem = {
            id: deleteId,
            name: $("#shipName").val(),
            address: $("#address").val()
        }
        myGrid.addRowData(deleteId, dataItem, "last");
        gridCollection = myGrid.jqGrid('getRowData');
        deleteId--;
        $("#address").val('');
    }

    function deleteShipRecords(rowid) {
        if ($('#id').val() != '') {
            var cont = $('#ship-address-grid').jqGrid('getCell', rowid, 'id');
            deletedId[deleteSize] = cont;
            deleteSize++;
            $('#ship-address-grid').jqGrid('delRowData', rowid);
        } else {
            $('#ship-address-grid').jqGrid('delRowData', rowid);
        }
    }

    function setName() {
        $('#shipName').val($('#name').val());
    }

    function deleteTerritoryRecords(row) {
        // jQuery('#territory-detail-grid').delRowData(id)
        var myGrid = $("#added-territory-detail-grid");
        if (row < 0) {
            myGrid.jqGrid('delRowData', row);
        }else if ($('#id').val() != '') {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-customerMaster").dialog({
                resizable: false,
                height: 150,
                modal: true,
                title: 'Delete alert',
                buttons: {
                    'Delete Item': function () {
                        $(this).dialog('close');
                        var myGrid = $("#added-territory-detail-grid");
                        myGrid.jqGrid('delRowData', id);
                        SubmissionLoader.showTo();
                        jQuery.ajax({
                            type: 'post',
                            url: '${resource(dir:'customerTerritorySubArea', file:'delete')}?id=' + row,
                            success: function (data, textStatus) {
                                myGrid.jqGrid('delRowData', row);
                                MessageRenderer.render(data);
                            },
                            error: function (XMLHttpRequest, textStatus, errorThrown) {
                                if (XMLHttpRequest.status = 0) {
                                    MessageRenderer.renderErrorText("Network Problem: Time out");
                                } else {
                                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                                }
                            },
                            complete: function (data) {
                                SubmissionLoader.hideFrom();
                            },
                            dataType: 'json'
                        });
                    },
                    Cancel: function () {
                        $(this).dialog('close');
                    }
                }
            });
//            var exist = true;
//            if (exist) {
//                territoryDeleteId[territoryDeleteSize] = row;
//                territoryDeleteSize++;
//
//                present = 0;
//            }
        }
    }

    function addTerritoryRecords(rowid) {
        var myGrid = $("#territory-detail-grid");
        var myGrid2 = $("#added-territory-detail-grid");
        var areaId = myGrid.jqGrid('getCell', rowid, 'territory');
        var addedAreaIds = myGrid2.jqGrid("getCol", "territory");
        for (var t = 1; t < addedAreaIds.length; t++) {
            if (areaId == addedAreaIds[t]) {
                return false;
            }
        }
//        if ($('#id').val() != '') {
//            var exist = false;
//            for (var i = 0; i < territoryDeleteSize; i++) {
//               // alert(rowid)
//                if (territoryDeleteId[i] == rowid) {
//                    territoryDeleteId[i] = null;
//                    territoryDeleteSize--;
////                    present = $("#territory-detail-grid").jqGrid('getCell', rowid, 'id');
//                    exist = true;
//                    break;
//                }
//            }
//            if (!exist) {
        territoryAddedId[territoryAddedSize] = rowid;
        territoryAddedSize++;
//            }
//        } else {
//            territoryAddedId[territoryAddedSize] = rowid;
//            territoryAddedSize++;
//        }
        var dataItem = {
            id: '',
            territory: myGrid.jqGrid('getCell', rowid, 'territory'),
            territoryName: myGrid.jqGrid('getCell', rowid, 'territoryName'),
            geoLocation: myGrid.jqGrid('getCell', rowid, 'geoLocation'),
            paraOrLocality: myGrid.jqGrid('getCell', rowid, 'paraOrLocality'),
            road: myGrid.jqGrid('getCell', rowid, 'road'),
            delete: '<a  href="javascript:deleteTerritoryRecords(' + rowIndex + ')" class="ui-icon ui-icon-trash" title="Delete"></a>'
        };
        myGrid2.addRowData(rowIndex, dataItem, "last");
        rowIndex--;
    }

    function isEmpty(val) {
        var val2;
        val2 = $.trim(val);
        return (val2.length == 0);
    }

    function focusTab(tabid, ctl) {
        $('#tabs').tabs('select', tabid);
        ctl.focus();
    }

    function checkDropDownAndFocus(tabid, ctl) {
        if (ctl.is(':disabled')) {
            return true;
        }
        if ($(ctl).val() == '') {
            focusTab(tabid, ctl);
            return false;
        }
        return true;
    }
    function checkTextFieldAndFocus(tabid, ctl) {
        if (ctl.is(':disabled')) {
            return true;
        }
        if (isEmpty(ctl.val())) {
            focusTab(tabid, ctl);
            return false;
        }
        return true;
    }
    function listTerritory(id) {
        var options = '<option value="">Please Select</option>';
        if (id == '') {
            $("#territoryConfiguration").html(options);
            return false;
        }
        jQuery.ajax({
            type: 'post',
            url: "${resource(dir:'territoryConfiguration', file:'searchTerritoryByEnterprise')}?enterpriseId=" + id,
            success: function (data) {
                options = '<option value="">Select Territory</option>';
                $.each(data, function (key, val) {
                    options += '<option value="' + val.id + '">' + val.name + '</option>';
                })
                $("#territoryConfiguration").html(options);
            },
            complete: function () {
                $("#territory-detail-grid").clearGridData();
                %{--$("#territoryConfiguration").val(${territoryId});--}%

            },
            dataType: 'json'
        });
    }
    function setEnterpriseId(id) {
        $('#enterPriseName').attr('disabled', true);
        $("#enterpriseConfiguration").val(id);
        listTerritory(id);
        size = 1;
    }
    /* $('#customerPaymentType').click(function() {
     var c=${com.bits.bdfp.util.ApplicationConstants.CUSTOMER_PAYMENT_TYPE_CASH_ID};
     if($("#customerPaymentType").val()== c){
     $("#customerCreditLimit").attr("disabled", "disabled");
     $("#creditPeriodInDays").attr("disabled", "disabled");
     }else{
     $("#customerCreditLimit").attr("disabled", false);
     $("#creditPeriodInDays").attr("disabled", false);
     }
     });
     */

</script>