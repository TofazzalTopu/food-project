<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        $('#delete-button').hide();
        $('#ui-widget-header-text').html('DistributionPoint')
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }
        $("#gFormDistributionPoint").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
//        $("#gFormDistributionPoint").validationEngine('attach');
//        reset_form("#gFormDistributionPoint");
        $("#territoryConfigurationList").flexbox('Select Business Unit', {
            watermark: "Select Business Unit",
            width: 300,
            onSelect: function () {
                $("#territoryConfiguration").val($('#territoryConfigurationList_hidden').val());
//                        loadBusinessUnit($('#enterpriselist_hidden').val());
            }
        });
        $('#territoryConfigurationList_input').val("")
        $('#territoryConfigurationList_input').addClass("validate[required]");

        $('#territoryConfigurationList_input').blur(function () {
            if ($('#territoryConfigurationList_input').val() == '') {
                $("#territoryConfiguration").val("");
            }
        });

        $("#inventoryList").flexbox('Select Inventory', {
            watermark: "",
            width: 300,
            onSelect: function () {
                $("#warehouse").val($('#inventoryList_hidden').val());
//                        loadBusinessUnit($('#enterpriselist_hidden').val());
            }
        });

        $('#inventoryList_input').blur(function () {
            if ($('#inventoryList_input').val() == '') {
                $("#warehouse").val("");
            }
        });


        $("#assignInchargeList").flexbox('Assign In-Charge', {
            watermark: "",
            width: 300,
            onSelect: function () {
                $("#inCharge").val($('#assignInchargeList_hidden').val());
//                        loadBusinessUnit($('#enterpriselist_hidden').val());
            }
        });

        $('#assignInchargeListt_input').blur(function () {
            if ($('#assignInchargeList_input').val() == '') {
                $("#inCharge").val("");
            }
        });

        $("#jqgrid-grid").jqGrid({
            url: '${resource(dir:'distributionPoint', file:'list')}',
            datatype: "json",
            colNames: [
                'SL',
                'ID',
                'Enterprise',
                'Distribution Point Name',
                'Is Factory',
                'Legacy ID',
                'Address',
                'Email',
                'Mobile'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30, sortable: false, align: 'left'},
                {name: 'id', index: 'id', width: 0, hidden: true},
                {name: 'enterpriseConfiguration', index: 'enterpriseConfiguration', width: 90, align: 'left'},
                {name: 'name', index: 'name', width: 125, align: 'left'},
                {name: 'isFactory', index: 'isFactory', width: 60, align: 'center'},
                {name: 'code', index: 'code', width: 80, align: 'left'},
                {name: 'address', index: 'address', width: 100, align: 'left'},
                {name: 'email', index: 'email', width: 100, align: 'left'},
                {name: 'mobile', index: 'mobile', width: 80, align: 'left'}
            ],
            rowNum: 50,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#jqgrid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "All DistributionPoint Information",
            autowidth: true,
            height: true,
            scrollOffset: 0,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                executeEditDistributionPoint();
            }
        });
        $("#jqgrid-grid").jqGrid('navGrid', '#jqgrid-pager', {edit: false, add: false, del: false, search: false});
//     $("#jqgrid-grid").gridResize({minWidth:350,maxWidth:800,minHeight:200});
        $(".ui-pg-selbox").children().each(function () {
            if ($(this).val() == -1) {
                $(this).html('All')
            }
        });


        //geo grid


        $("#jqgrid-grid-geolocation").jqGrid({
            url: '',
            datatype: "json",
            colNames: [
                'SL',
                'ID',
                '&#10004;',
                'Geographic Location',
                'Country',
                'Division',
                'District',
                'Thana',
                'Union',
                'Para',
                'Road',
                'Select'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30, sortable: false, align: 'left'},
                {name: 'id', index: 'id', width: 0, hidden: true},
                {name: 'checkGeo', index: 'checkGeo', width: 30},
                {name: 'geo_location', index: 'geo_location', width: 100, align: 'left'},
                {name: 'country_name', index: 'country_name', width: 100, align: 'left'},
                {name: 'division_name', index: 'division_name', width: 100, align: 'left'},
                {name: 'district_name', index: 'district_name', width: 100, align: 'left'},
                {name: 'thana_name', index: 'thana_name', width: 100, align: 'left'},
                {name: 'union_name', index: 'union_name', width: 100, align: 'left'},
                {name: 'para_or_locality', index: 'para_or_locality', width: 100, align: 'left'},
                {name: 'road', index: 'road', width: 100, align: 'left'},
                {name: 'addedd_geo_location_id', index: 'addedd_geo_location_id', width: 0, hidden: true}
            ],
            rowNum: -1,
//            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "All Geographic Location Information",
            autowidth: true,
            height: 200,
            scrollOffset: 0,
            loadComplete: function () {
                var ids = $("#jqgrid-grid-geolocation").jqGrid('getDataIDs');
                for (key in ids) {
                    var rowData = $("#jqgrid-grid-geolocation").getRowData(ids[key]);
                    if (rowData.addedd_geo_location_id) {
                        $("#jqgrid-grid-geolocation").jqGrid('setRowData', ids[key], {checkGeo: '<input type="checkbox" checked="checked" onchange="buttonStatusChange();"  class="disChck" value="' + ids[key] + '" />'});
                    }
                    else {
                        $("#jqgrid-grid-geolocation").jqGrid('setRowData', ids[key], {checkGeo: '<input type="checkbox"  onchange="buttonStatusChange();"  class="disChck" value="' + ids[key] + '" />'});
                    }
                }
            },
            onSelectRow: function (rowid, status) {
                //executeEditDistributionPoint();
            }
        });
        // end geo grid

        $('#defaultCustomer').blur(function () {
            if ($('#defaultCustomer').val() == '') {
                $("#defaultCustomerId").val('');
            }
        });

        jQuery('#defaultCustomer').autocomplete({
            minLength: '1',
            source: function (request, response) {
                //EmployeeRegister.employeeCoreInfoId = null;
                if($('#enterpriseConfiguration').val() && $('#territoryConfiguration').val()){
                    $('#popEmpDetails').html("");
                    var data = {searchKey: request.term};
                    %{--var url = "${request.contextPath}/${params.controller}/autoCompleteCustomerListForDistribution?id=" + $('#enterpriseConfiguration').val() + "&query=" + $('#defaultCustomer').val();--}%
                   if( $("#No").is(":checked")){
                       var url = "${request.contextPath}/${params.controller}/autoCompleteCustomerListForDistribution?id=" + $('#territoryConfiguration').val() + "&query=" + $('#defaultCustomer').val();
                       DocuAutoComplete.setSpinnerSelector('defaultCustomer').execute(response, url, data, function (item) {
                           item['label'] = item['name'] + " [" + item['code'] + "]";
                           item['value'] = item['label'];
                           return item;
                       });
                   }
                    else{
                       MessageRenderer.renderErrorText('Default Customer is not assign to Factory');
                   }

                }
                else{
                    MessageRenderer.renderErrorText('Select Enterprise and Territory');
                }

            },
            select: function (event, ui) {
                CustomerInfo.setCustomerInformation(ui.item.id, ui.item.value);
            }
        }).data("autocomplete")._renderItem = function (ul, item) {
            var customerDetails = "";
            if (item.type) {
                customerDetails = '<div style="font-size: 9px; color:#326E93;">' + " Name: " + item.name + "[" + item.code + "]" + '</div>';
            }
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + customerDetails).appendTo(ul);

        };
    });
    function getSelectedDistributionPointId() {
        var distributionPointId = null;
        var rowid = $("#jqgrid-grid").jqGrid('getGridParam', 'selrow');
        if (rowid) {
            distributionPointId = $("#jqgrid-grid").jqGrid('getCell', rowid, 'id');
        }
        if (distributionPointId == null) {
            distributionPointId = $('#gFormDistributionPoint input[name = id]').val();
        }
        return distributionPointId;
    }
    function executePreConditionDistributionPoint() {
        // trim field vales before process.
        trim_form();

        if (!$("#gFormDistributionPoint").validationEngine('validate')) {
            return false;
        }
        if( $("#warehouse").val() == ""){
            MessageRenderer.renderErrorText("Inventory is Mandatory for Distribution Point");
            return false;
        }

        if($('input:radio[name=isFactory]:checked').val() == false){
            if($("#defaultCustomerId").val() == ""){
                return false;
            }
        }
        if($('#No').is(':checked')){
            if($("#inCharge").val() == ""){
//                alert("In charge is mandatory for non factory distribution point !");
                MessageRenderer.renderErrorText('In charge is mandatory for non factory distribution point !');
                return false;

            }
            if(!$("#defaultCustomerId").val()){
//                alert("Default customer is mandatory for non factory distribution point !");
                MessageRenderer.renderErrorText('Default customer is mandatory for non factory distribution point !');
                return false;

            }
        }

        return true;
    }
    function executeAjaxDistributionPoint() {
        if (!executePreConditionDistributionPoint()) {
            return false;
        }
//

        var allIds = {}
        var i = 0;
        $('.disChck').each(function () {
            if (this.checked) {
//            var section=$('#'+$(this).val()+'_section').val();
//            var course=$('#'+$(this).val()+'_course').val();
                allIds['items.territory[' + i + '].territorySubAreaId'] = $(this).val();

                i++

            }
        });
        if (i < 1) {
            $("#dialog").dialog("destroy");
            $("#dialog-geoLocation-selection").dialog({
                resizable: false,
                height: 150,
                modal: true,
                title: 'Geo Location Selection Missing',
                buttons: {
                    Ok: function () {
                        $(this).dialog('close');
                    }
                }
            }); //end of dialog

            return false
        }

        allIds['name'] = $("#name").val();
        allIds['code'] = $("#code").val()
        $("input[name='isFactory']").each( function () {
           if(this.checked){
               allIds['isFactory']=$(this).val();
           }
        });

        allIds['address'] = $('#address').val()
        allIds['email'] = $('#email').val()
        allIds['mobile'] = $('#mobile').val()
        allIds['mobile'] = $('#mobile').val()
        allIds['enterpriseConfiguration.id'] = $('#enterpriseConfiguration').val()
        allIds['territoryConfiguration.id'] = $('#territoryConfiguration').val()
        allIds['warehouse.id'] = $('#warehouse').val()
        allIds['inCharge.id'] = $('#inCharge').val()
        allIds['bankAccount.id'] = $('#bankAccount').val()
        allIds['defaultCustomer.id'] = $('#defaultCustomerId').val()


        var actionUrl = null;
        if ($('#gFormDistributionPoint input[name = id]').val()) {
            allIds['id'] = $('#gFormDistributionPoint input[name = id]').val()
            actionUrl = "${request.contextPath}/${params.controller}/update";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/create";
        }
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: allIds,
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionDistributionPoint(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-grid").trigger("reloadGrid");
                    reset_form('#gFormDistributionPoint');
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                }
                else{
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
    function executePostConditionDistributionPoint(result) {
        if (result.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_form('#gFormDistributionPoint');
        }
        MessageRenderer.render(result);
    }
    function deleteAjaxDistributionPoint() {    // Delete record
        var distributionPointId = getSelectedDistributionPointId();
        if (executePreConditionForDeleteDistributionPoint(distributionPointId)) {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-distributionPoint").dialog({
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
                            data: jQuery("#gFormDistributionPoint").serialize(),
                            url: "${resource(dir:'distributionPoint', file:'delete')}",
                            success: function (message) {
                                executePostConditionForDeleteDistributionPoint(message);
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

    function executePreConditionForEditDistributionPoint(distributionPointId) {
        if (distributionPointId == null) {
            alert("Please select a distributionPoint to edit");
            return false;
        }
        return true;
    }
    function executeEditDistributionPoint() {
        var distributionPointId = getSelectedDistributionPointId();
        if (executePreConditionForEditDistributionPoint(distributionPointId)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'distributionPoint', file:'edit')}?id=" + distributionPointId,
                success: function (entity) {
//                    console.log(entity);
                    executePostConditionForEditDistributionPoint(entity);
                },
                dataType: 'json'
            });
        }
    }
    function executePostConditionForEditDistributionPoint(data) {
        if (data == null) {
            MessageRenderer.renderErrorText('Selected distributionPoint might have been deleted by someone');
//            alert('Selected distributionPoint might have been deleted by someone');  //Message renderer
        } else {
            showDistributionPoint(data);
        }
    }
    function executePreConditionForDeleteDistributionPoint(distributionPointId) {
        if (distributionPointId == null) {
            alert("Please select a distributionPoint to delete");
            return false;
        }
        return true;
    }
    function executePostConditionForDeleteDistributionPoint(data) {
        if (data.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_form('#gFormDistributionPoint');
        }
        MessageRenderer.render(data)
    }
    function showDistributionPoint(entity) {
//        debugger
        var distributionPoint = entity.distributionPoint
        var warehouse = entity.warehouse
        var inCharge = entity.inCharge
        var defaultCustomer = entity.defaultCustomer
        var territoryConfiguration = entity.territoryConfiguration
        var enterpriseConfiguration = entity.enterpriseConfiguration

        if (enterpriseConfiguration) {
            $('#enterpriselist').setValue(enterpriseConfiguration.name + '[' + enterpriseConfiguration.code + ']');
            $("#enterpriseConfiguration").val(enterpriseConfiguration.id);
            selectTerritoryAndInventoryForEdit(enterpriseConfiguration.id);
            setTimeout(function () {
                $('#territoryConfigurationList').setValue(territoryConfiguration.name);
                $('#territoryConfiguration').val(territoryConfiguration.id);
                getGeoLocation(territoryConfiguration.id,"true",distributionPoint.id);
                if (warehouse) {
                    $('#inventoryList').setValue(warehouse.name + '[' + warehouse.code + ']');
                    $('#warehouse').val(warehouse.id);
                }
                else{
                    $('#inventoryList').setValue('');
                    $('#warehouse').val(null);
                }
                if (inCharge) {
                    $('#assignInchargeList').setValue(inCharge.name + '[' + inCharge.code + ']');
                    $('#inCharge').val(inCharge.id);
                }
                else{
                    $('#assignInchargeList').setValue('');
                    $('#inCharge').val(null);
                }
                if (defaultCustomer) {
                    $('#defaultCustomer').val(defaultCustomer.name + '[' + defaultCustomer.code + ']');
                    $('#defaultCustomerId').val(defaultCustomer.id);
                }
                else{
                    $('#defaultCustomer').val('');
                    $('#defaultCustomerId').val(null);
                }

            }, 1000);
//            selectTerritoryAndInventoryForEdit(enterpriseConfiguration.id);

        }

        $('#gFormDistributionPoint input[name = id]').val(distributionPoint.id);
        $('#gFormDistributionPoint input[name = version]').val(distributionPoint.version);

        $('#name').val(distributionPoint.name);
        $('#code').val(distributionPoint.code);
        $('#address').val(distributionPoint.address);
        $('#email').val(distributionPoint.email);
        $('#mobile').val(distributionPoint.mobile);
        if(distributionPoint.isFactory){
           $("#Yes").attr('checked', 'checked');
        }
        else{
            $("#No").attr('checked', 'checked');
        }
        if(entity.distributionPoint.bankAccount){
            $("#bankAccount").val(entity.distributionPoint.bankAccount.id);
        } else{
            $("#bankAccount").val('');
        }

        $('#create-button').attr('value', 'Update');
        $('#cancel-button').attr('value', 'Cancel');
        $('#delete-button').show();
    }
    function executeSearchDistributionPoint(fieldName, fieldValue) {
        if (executePreConditionForSearchDistributionPoint(fieldName, fieldValue)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'distributionPoint', file:'search')}?fieldName=" + fieldName + "&fieldValue=" + fieldValue,
                success: function (entity) {
                    executePostConditionForSearchDistributionPoint(entity, fieldName, fieldValue);
                },
                dataType: 'json'
            });
        }
    }
    function executePreConditionForSearchDistributionPoint(fieldName, fieldValue) {
        // trim field vales before process.
        $('#' + fieldName).val($.trim($('#' + fieldName).val()));
        if (fieldValue == '' || fieldValue == null) {
            reset_form("#gFormDistributionPoint");
            return false;
        }
        return true;
    }
    function executePostConditionForSearchDistributionPoint(data, fieldName, fieldValue) {
        if (data == null) {
            reset_form("#gFormDistributionPoint"); // Clear Form
            $('#' + fieldName).val(fieldValue); // Set search field with fieldValue
        } else {
            showDistributionPoint(data);
        }
    }

    function selectTerritoryAndInventory(enId) {
        $.ajax({
            type: "POST",
            url: "${resource(dir:'distributionPoint', file:'selectTerritoryAndInventory')}?id=" + enId,
            beforeSend: function (jqXHR, settings) {
                $("#loader_icon").show();
            },
            success: function (data) {
//                debugger
                var territoryMap = new Object(); // or var map = {};
                territoryMap["results"] = data.territoryList;
                territoryMap["total"] = data.territoryList.length;
                $("#territoryConfigurationList").empty();
                $("#territoryConfigurationList").flexbox(territoryMap, {
                    watermark: "Select Business Unit",
                    width: 300,
                    onSelect: function () {
                        $("#territoryConfiguration").val($('#territoryConfigurationList_hidden').val());
                        if($("#Yes").is(":checked")){
//                            alert( $("#territoryConfiguration").val());
                            getGeoLocation($('#territoryConfiguration').val(),"false",'');
                        }

                    }

                });
                $('#territoryConfigurationList_input').val('')
                $('#territoryConfigurationList_input').addClass("validate[required]")
                $('#territoryConfigurationList_input').blur(function () {
                    if ($('#territoryConfigurationList_input').val() == '') {
                        $("#territoryConfiguration").val("");
                    }
                });


                var inventoryMap = new Object();
                inventoryMap["results"] = data.inventoryList;
                inventoryMap["total"] = data.inventoryList.length;
                $("#inventoryList").empty();
                $("#inventoryList").flexbox(inventoryMap, {
                    watermark: "Select Inventory",
                    width: 300,
                    onSelect: function () {
                        $("#warehouse").val($('#inventoryList_hidden').val());

                    }

                });

                $('#inventoryList_input').blur(function () {
                    if ($('#inventoryList_input').val() == '') {
                        $("#warehouse").val("");
                    }
                });
                var inchargeMap = new Object();

                inchargeMap["results"] = data.inventoryInchargeList;
                inchargeMap["total"] = data.inventoryInchargeList.length;
                $("#assignInchargeList").empty();
                $("#assignInchargeList").flexbox(inchargeMap, {
                    watermark: "Assign in-charge",
                    width: 300,
                    onSelect: function () {
                        $("#inCharge").val($('#assignInchargeList_hidden').val());

                    }

                });

                $('#assignInchargeList_input').blur(function () {
                    if ($('#assignInchargeList_input').val() == '') {
                        $("#inCharge").val("");
                    }
                });


            },
            complete: function (data) {
                $("#loader_icon").hide();
            },
            dataType: 'json'
        });

    }

    function getGeoLocation(territoryId,edit,dpId) {
//        alert(territoryId)
        $("#jqgrid-grid-geolocation").jqGrid("clearGridData");
        $('#geoLocationInfo').show();
        $("#jqgrid-grid-geolocation").setGridParam({
            url: '${resource(dir:'distributionPoint', file:'selectGeoLocationGridData')}?id='
            + territoryId+'&edit='+edit+'&dpId='+dpId
        });
        $("#jqgrid-grid-geolocation").trigger("reloadGrid");
        %{--$.ajax({--}%
        %{--type: "POST",--}%
        %{--url: "${resource(dir:'distributionPoint', file:'selectGeoLocationView')}?id=" + territoryId,--}%
        %{--beforeSend: function (jqXHR, settings) {--}%
        %{--$("#loader_icon").show();--}%
        %{--},--}%
        %{--success: function (data) {--}%
        %{--$('#geoLocationInfo').show();--}%

        %{--},--}%
        %{--complete: function (data) {--}%
        %{--$("#loader_icon").hide();--}%
        %{--},--}%
        %{--dataType: 'html'--}%
        %{--});--}%

    }

    function getGeoLocationbyCustomer(territoryId,edit,dpId) {
//        alert(territoryId)
        $("#jqgrid-grid-geolocation").jqGrid("clearGridData");
        $('#geoLocationInfo').show();
        $("#jqgrid-grid-geolocation").setGridParam({
            url: '${resource(dir:'distributionPoint', file:'selectGeoLocationGridDatabyCustomer')}?id='
            + territoryId+'&edit='+edit+'&dpId='+dpId
        });
        $("#jqgrid-grid-geolocation").trigger("reloadGrid");
    }
    $('#search-btn-customer-register-id').click(function () {
        if ($('#enterpriseConfiguration').val() && $('#territoryConfiguration').val() ) {
            if($("#No").is(":checked"))
            {
                CustomerInfo.popupCustomerListPanel($('#territoryConfiguration').val());
            }
            else
            {
                MessageRenderer.renderErrorText('Default Customer is not assign to Factory');
            }
        }
        else {
            MessageRenderer.renderErrorText('Select Enterprise and Territory');

        }
    });

    var CustomerInfo = {
        customerCoreInfoId: null,
        popupCustomerListPanel: function (customerCoreInfoId) {
            var url = '${resource(dir:'distributionPoint', file:'distributionPointCustomerList')}';
            var params = {id: customerCoreInfoId};
            DocuAjax.html(url, params, function (html) {

                $.fancybox(html);
            });
        },

        setCustomerInformation: function (customerCoreInfoId, customerCoreInfo) {
            $("#defaultCustomer").val(customerCoreInfo);
            $("#defaultCustomerId").val(customerCoreInfoId);
            CustomerInfo.customerCoreInfoId = customerCoreInfoId;
            loadGeoLocation($("#defaultCustomerId").val());
        }
    }


    function selectTerritoryAndInventoryForEdit(enId) {
        $.ajax({
            type: "POST",
            url: "${resource(dir:'distributionPoint', file:'selectTerritoryAndInventory')}?id=" + enId,
            beforeSend: function (jqXHR, settings) {
                $("#loader_icon").show();
            },
            success: function (data) {
//               alert( JSON.stringify(data));
                var territoryMap = new Object(); // or var map = {};
                territoryMap["results"] = data.territoryList;
                territoryMap["total"] = data.territoryList.length;
                $("#territoryConfigurationList").empty();
                $("#territoryConfigurationList").flexbox(territoryMap, {
                    watermark: "Select Business Unit",
                    width: 300,
                    onSelect: function () {
                        $("#territoryConfiguration").val($('#territoryConfigurationList_hidden').val());
                        setTimeout(function () {
                            getGeoLocation($('#territoryConfigurationList_hidden').val(),"true");
                        }, 1000);
                    }

                });
                $('#territoryConfigurationList_input').val('')
                $('#territoryConfigurationList_input').addClass("validate[required]")
                $('#territoryConfigurationList_input').blur(function () {
                    if ($('#territoryConfigurationList_input').val() == '') {
                        $("#territoryConfiguration").val("");
                    }
                });


                var inventoryMap = new Object();
                inventoryMap["results"] = data.inventoryList;
                inventoryMap["total"] = data.inventoryList.length;
                $("#inventoryList").empty();
                $("#inventoryList").flexbox(inventoryMap, {
                    watermark: "Select Inventory",
                    width: 300,
                    onSelect: function () {
                        $("#warehouse").val($('#inventoryList_hidden').val());

                    }

                });

                $('#inventoryList_input').blur(function () {
                    if ($('#inventoryList_input').val() == '') {
                        $("#warehouse").val("");
                    }
                });
                var inchargeMap = new Object();

                inchargeMap["results"] = data.inventoryInchargeList;
                inchargeMap["total"] = data.inventoryInchargeList.length;
                $("#assignInchargeList").empty();
                $("#assignInchargeList").flexbox(inchargeMap, {
                    watermark: "Assign in-charge",
                    width: 300,
                    onSelect: function () {
                        $("#inCharge").val($('#assignInchargeList_hidden').val());

                    }

                });

                $('#assignInchargeList_input').blur(function () {
                    if ($('#assignInchargeList_input').val() == '') {
                        $("#inCharge").val("");
                    }
                });


            },
            complete: function (data) {
                $("#loader_icon").hide();
            },
            dataType: 'json'
        });

    }

    function reset_form(formName) {
        $(formName).find(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
            if (this.type == 'hidden') {
                this.value = "";
            } else {
                this.value = this.defaultValue;
            }
        });
        $(formName).find('select').each(function () {
            this.value = "";
        });
        //$('.productTypeDiv').hide();
        $("#jqgrid-grid-geolocation").setGridParam({
            url: '${resource(dir:'distributionPoint', file:'selectGeoLocationGridData')}?id=0'
        });
        $("#jqgrid-grid-geolocation").trigger("reloadGrid");

        $(formName + ' input[name = create-button]').attr('value', 'Create');
        $(formName + ' input[name = delete-button]').hide();
        $(formName + ' input[name = select-button').attr('value', 'Select All')
    }

    function checkUnCheck() {
        if ($('#select-button').val() == 'Select All') {
            $('.disChck').each(function () {
                this.checked=true;
                $('#select-button').attr('value', 'Deselect All')
            })
        }
        else {
            $('.disChck').each(function () {
                this.checked=false;
                $('#select-button').attr('value', 'Select All')
            })
        }
    }

    function buttonStatusChange() {
        var isSelected = true;
        $('.disChck').each(function() {
            if (this.checked) {
                $('#select-button').attr('value', 'Deselect All')
                isSelected = false
            }
        })

        if (isSelected) {
            $('#select-button').attr('value', 'Select All')
        }
    }

    function loadGeoLocation(id){
//        alert(id);
        getGeoLocationbyCustomer(id,"false",'');

    }

</script>