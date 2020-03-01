<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        $('#ui-widget-header-text').html('DeliveryTruck')
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }
        $("#distributionPointList").flexbox('Select Distribution Point', {
            watermark: "Select Distribution Point",
            width: 260,
            onSelect: function () {
                $("#distributionPoint").val($('#distributionPointList_hidden').val());
//                        loadBusinessUnit($('#enterpriselist_hidden').val());
            }
        });
        $('#distributionPointList_input').blur(function () {
            if ($('#distributionPointList_input').val() == '') {
                $("#distributionPointList").val("");
            }
        });

        var result = data.results;
        if(result.length == 1){
            $("#distributionPointList").setValue(result[0].name);
            $("#distributionPoint").val(result[0].id);
        }
        $("#gFormDeliveryTruck").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });

        $("#gFormDeliveryTruck").validationEngine('attach');
        reset_deliveryTruck_form("#gFormDeliveryTruck");
        $("#jqgrid-grid").jqGrid({
            url: '${resource(dir:'deliveryTruck', file:'list')}',
            datatype: "json",
            colNames: [
                'SL',
                'ID',

                'Enterprise Configuration',
                'Distribution Point',
                'Name',
                'Vehicle Number',
                'Loading Capacity',
                'truckHeight',
                'truckWidth',
                'truckLength',
                'Truck Size'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30, sortable: false, align: 'left'},
                {name: 'id', index: 'id', width: 0, hidden: true},

                {name: 'enterpriseConfiguration', index: 'enterpriseConfiguration', width: 100, align: 'left'},
                {name: 'distributionPoint', index: 'distributionPoint', width: 100, align: 'left'},
                {name: 'name', index: 'name', width: 100, align: 'left'},
                {name: 'vehicleNumber', index: 'vehicleNumber', width: 100, align: 'center'},
                {name: 'loadingCapacity', index: 'loadingCapacity', width: 100, align: 'right'},
                {name: 'truckHeight', index: 'truckHeight', width: 0, align: 'left', hidden: true},
                {name: 'truckWidth', index: 'truckWidth', width: 0, align: 'left', hidden: true},
                {name: 'truckLength', index: 'truckLength', width: 0, align: 'left', hidden: true},
                {name: 'truckSize', index: 'truckSize', width: 120, align: 'left'}
            ],
            rowNum: 50,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#jqgrid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "All DeliveryTruck Information",
            autowidth: true,
            height: true,
            scrollOffset: 0,
            loadComplete: function () {
                var ids = jQuery("#jqgrid-grid").jqGrid('getDataIDs');
                for (key in ids) {
                    jQuery("#jqgrid-grid").jqGrid('setRowData', ids[key], {truckSize: jQuery("#jqgrid-grid").jqGrid('getCell', ids[key], 'truckHeight') + ' <b>X</b> ' +
                            jQuery("#jqgrid-grid").jqGrid('getCell', ids[key], 'truckWidth') + ' <b>X</b> ' +
                            jQuery("#jqgrid-grid").jqGrid('getCell', ids[key], 'truckLength')
                    });
                }
            },
            onSelectRow: function (rowid, status) {
                executeEditDeliveryTruck();
            }
        });
        $("#jqgrid-grid").jqGrid('navGrid', '#jqgrid-pager', {edit: false, add: false, del: false, search: false});
//     $("#jqgrid-grid").gridResize({minWidth:350,maxWidth:800,minHeight:200});
        $(".ui-pg-selbox").children().each(function () {
            if ($(this).val() == -1) {
                $(this).html('All')
            }
        });

        $("#gFormDeliveryTruck input[name=vehicleNumber]").keyup(function () {
            vehicleNumberValidation($(this).val());
        });
    });

    function vehicleNumberValidation(vNum) {
        var retVal = true;
        jQuery.ajax({
            type: 'post',
            data: {vehicleNumber: vNum},
            url: "${request.contextPath}/${params.controller}/getAjaxDataVal",
            success: function (res, textStatus) {
                if (res != null) {
                    MessageRenderer.render({
                        "messageBody": "This Vehicle Number already exists!",
                        "messageTitle": "Delivery Truck",
                        "type": "0"
                    });
                    retVal = "* This Vehicle Number already exists!";
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
            },
            dataType: 'json'
        });
        return retVal;
    }
    function getSelectedDeliveryTruckId() {
        var deliveryTruckId = null;
        var rowid = $("#jqgrid-grid").jqGrid('getGridParam', 'selrow');
        if (rowid) {
            deliveryTruckId = $("#jqgrid-grid").jqGrid('getCell', rowid, 'id');
        }
        if (deliveryTruckId == null) {
            deliveryTruckId = $('#gFormDeliveryTruck input[name = id]').val();
        }
        return deliveryTruckId;
    }
    function executePreConditionDeliveryTruck() {
        // trim field vales before process.
        trim_form();
        if (!$("#gFormDeliveryTruck").validationEngine('validate')) {
            return false;
        }
        return true;
    }
    function executeAjaxDeliveryTruck() {
        if (!executePreConditionDeliveryTruck()) {
            return false;
        }
        var actionUrl = null;
        if ($('#gFormDeliveryTruck input[name = id]').val()) {
            actionUrl = "${request.contextPath}/${params.controller}/update";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/create";
        }
        jQuery.ajax({
            type: 'post',
            data: jQuery("#gFormDeliveryTruck").serialize(),
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionDeliveryTruck(data);
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
        return false;
    }
    function executePostConditionDeliveryTruck(result) {
        if (result.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_deliveryTruck_form('#gFormDeliveryTruck');
        }
        MessageRenderer.render(result);
    }
    function deleteAjaxDeliveryTruck() {    // Delete record
        var deliveryTruckId = getSelectedDeliveryTruckId();
        if (executePreConditionForDeleteDeliveryTruck(deliveryTruckId)) {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-deliveryTruck").dialog({
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
                            data: jQuery("#gFormDeliveryTruck").serialize(),
                            url: "${resource(dir:'deliveryTruck', file:'delete')}",
                            success: function (message) {
                                executePostConditionForDeleteDeliveryTruck(message);
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

    function executePreConditionForEditDeliveryTruck(deliveryTruckId) {
        if (deliveryTruckId == null) {
            alert("Please select a deliveryTruck to edit");
            return false;
        }
        return true;
    }
    function executeEditDeliveryTruck() {
        var deliveryTruckId = getSelectedDeliveryTruckId();
        if (executePreConditionForEditDeliveryTruck(deliveryTruckId)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'deliveryTruck', file:'edit')}?id=" + deliveryTruckId,
                success: function (entity) {
                    executePostConditionForEditDeliveryTruck(entity);
                },
                dataType: 'json'
            });
        }
    }
    function executePostConditionForEditDeliveryTruck(data) {
        if (data == null) {
            alert('Selected deliveryTruck might have been deleted by someone');  //Message renderer
        } else {
            showDeliveryTruck(data);
        }
    }
    function executePreConditionForDeleteDeliveryTruck(deliveryTruckId) {
        if (deliveryTruckId == null) {
            alert("Please select a deliveryTruck to delete");
            return false;
        }
        return true;
    }
    function executePostConditionForDeleteDeliveryTruck(data) {
        if (data.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_deliveryTruck_form('#gFormDeliveryTruck');
        }
        MessageRenderer.render(data)
    }
    function showDeliveryTruck(entity) {
        var deliveryTruck = entity.deliveryTruck
        var enterpriseConfiguration = entity.enterpriseConfiguration
        var distributionPoint = entity.distributionPoint

        $('#gFormDeliveryTruck input[name = id]').val(deliveryTruck.id);
        $('#gFormDeliveryTruck input[name = version]').val(deliveryTruck.version);

        if (enterpriseConfiguration) {
            $('#enterpriseList').setValue(enterpriseConfiguration.name);
            $('#enterpriseConfiguration').val(enterpriseConfiguration.id)
        }

        if (distributionPoint) {
            $('#distributionPointList').setValue(distributionPoint.name);
            $('#distributionPoint').val(distributionPoint.id)
        }
        $('#name').val(deliveryTruck.name);
        $('#vehicleNumber').val(deliveryTruck.vehicleNumber);
        $('#loadingCapacity').val(deliveryTruck.loadingCapacity);
        $('#truckHeight').val(deliveryTruck.truckHeight);
        $('#truckWidth').val(deliveryTruck.truckWidth);
        $('#truckLength').val(deliveryTruck.truckLength);
        if (deliveryTruck.userCreated) {
            $('#userCreated').val(deliveryTruck.userCreated.id).attr("selected", "selected");
        }
        else {
            $('#userCreated').val(deliveryTruck.userCreated);
        }
        if (deliveryTruck.userUpdated) {
            $('#userUpdated').val(deliveryTruck.userUpdated.id).attr("selected", "selected");
        }
        else {
            $('#userUpdated').val(deliveryTruck.userUpdated);
        }
        $('#dateCreated').val(deliveryTruck.dateCreated);
        $('#dateUpdated').val(deliveryTruck.dateUpdated);
        $('#create-button').attr('value', 'Update');
        $('#cancel-button').attr('value', 'Cancel');
        $('#delete-button').show();
    }
    function executeSearchDeliveryTruck(fieldName, fieldValue) {
        if (executePreConditionForSearchDeliveryTruck(fieldName, fieldValue)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'deliveryTruck', file:'search')}?fieldName=" + fieldName + "&fieldValue=" + fieldValue,
                success: function (entity) {
                    executePostConditionForSearchDeliveryTruck(entity, fieldName, fieldValue);
                },
                dataType: 'json'
            });
        }
    }
    function executePreConditionForSearchDeliveryTruck(fieldName, fieldValue) {
        // trim field vales before process.
        $('#' + fieldName).val($.trim($('#' + fieldName).val()));
        if (fieldValue == '' || fieldValue == null) {
            reset_deliveryTruck_form("#gFormDeliveryTruck");
            return false;
        }
        return true;
    }
    function executePostConditionForSearchDeliveryTruck(data, fieldName, fieldValue) {
        if (data == null) {
            reset_deliveryTruck_form("#gFormDeliveryTruck"); // Clear Form
            $('#' + fieldName).val(fieldValue); // Set search field with fieldValue
        } else {
            showDeliveryTruck(data);
        }
    }
    function loadDistributionPoint(enterpriseId) {
        jQuery.ajax({
            type: "POST",
            url: '${resource(dir:'distributionPoint', file:'loadDistributionPoint')}?enterpriseId=' + enterpriseId,
            dataType: 'json',
            success: function (data, textStatus) {

                $("#distributionPointList").empty();
                $("#distributionPointList").flexbox(data, {
                    watermark: "Select Distribution Point",
                    width: 260,
                    onSelect: function () {
                        $("#distributionPoint").val($('#distributionPointList_hidden').val());
                        if ($('#distributionPointList_hidden').val()) {
                            $(".distributionPointList_inputformError").hide('slow');
                        }
                    }
                });

                $('#distributionPointList_input').val('');
                $('#distributionPointList_input').addClass("validate[required]");
                $('#distributionPointList_input').blur(function () {
                    if ($('#distributionPointList_input').val() == '') {
                        $("#distributionPointList").val("");
                    }
                });

            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                } else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete: function () {

            }
        });
    }
    function reset_deliveryTruck_form(formName) {
        var enterpriseId = $('#enterpriseConfiguration').val();
        $(formName).find(":input").not(":button, :submit, :reset, :checkbox, :radio, :selected").each(function () {
            if (this.type == 'hidden') {
                this.value = "";
            } else {
                this.value = this.defaultValue;
            }
        });
//    $('input[type=checkbox]').attr('checked', false);
        $(formName + ' input[name = create-button]').attr('value', 'Create');
        $(formName + ' input[name = delete-button]').hide();
        $(formName+' select').val('');
//        $("#packageType").html('');
//        $("#measureUnitConfiguration").html('');
        $('#enterpriseConfiguration').val(enterpriseId);
    }
</script>