<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        $('#ui-widget-header-text').html('District')
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormDistrict").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormDistrict").validationEngine('attach');
        resetAll();

        $("#jqgrid-grid").jqGrid({
            url: '${resource(dir:'district', file:'list')}',
            datatype: "json",
            colNames: [
                'SL',
                'ID',

                'Division',
                'Geo Code',
                'District Name'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30, sortable: false, align: 'left'},
                {name: 'id', index: 'id', width: 0, hidden: true},

                {name: 'division', index: 'division', width: 100, align: 'left'},
                {name: 'geoCode', index: 'geoCode', width: 100, align: 'left'},
                {name: 'name', index: 'name', width: 100, align: 'left'}
            ],
            rowNum: 50,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#jqgrid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "asc",
            caption: "District List",
            autowidth: true,
            height: true,
            scrollOffset: 0,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                executeEditDistrict();
            }
        });
        $("#jqgrid-grid").jqGrid('navGrid', '#jqgrid-pager', {edit: false, add: false, del: false, search: false});
//     $("#jqgrid-grid").gridResize({minWidth:350,maxWidth:800,minHeight:200});
        $(".ui-pg-selbox").children().each(function () {
            if ($(this).val() == -1) {
                $(this).html('All')
            }

        });
    });
    function getSelectedDistrictId() {
        var districtId = null;
        var rowid = $("#jqgrid-grid").jqGrid('getGridParam', 'selrow');
        if (rowid) {
            districtId = $("#jqgrid-grid").jqGrid('getCell', rowid, 'id');
        }
        if (districtId == null) {
            districtId = $('#gFormDistrict input[name = id]').val();
        }
        return districtId;
    }
    function executePreConditionDistrict() {
        // trim field vales before process.
        trim_form();
        if (!$("#gFormDistrict").validationEngine('validate')) {
//        if ($("#gFormDistrict").validate().form({onfocusout: false}) == false) {
            return false;
        }
        return true;
    }
    function executeAjaxDistrict() {
        if (!executePreConditionDistrict()) {
            return false;
        }
        var actionUrl = null;
        if ($('#gFormDistrict input[name = id]').val()) {
            actionUrl = "${request.contextPath}/${params.controller}/update";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/create";
        }
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: jQuery("#gFormDistrict").serialize(),
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionDistrict(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-grid").trigger("reloadGrid");
                    $("#geoCode").val("");
                    $("#name").val("");
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
    function executePostConditionDistrict(result) {
        if (result.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            $("#geoCode").val("");
            $("#name").val("");
//            resetAll();
        }
        MessageRenderer.render(result);
    }
    function deleteAjaxDistrict() {    // Delete record
        var districtId = getSelectedDistrictId();
        if (executePreConditionForDeleteDistrict(districtId)) {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-district").dialog({
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
                            data: jQuery("#gFormDistrict").serialize(),
                            url: "${resource(dir:'district', file:'delete')}",
                            success: function (message) {
                                executePostConditionForDeleteDistrict(message);
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

    function executePreConditionForEditDistrict(districtId) {
        if (districtId == null) {
            alert("Please select a district to edit");
            return false;
        }
        return true;
    }
    function executeEditDistrict() {
        var districtId = getSelectedDistrictId();
        if (executePreConditionForEditDistrict(districtId)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'district', file:'edit')}?id=" + districtId,
                success: function (entity) {
                    executePostConditionForEditDistrict(entity);
                },
                dataType: 'json'
            });
        }
    }
    function executePostConditionForEditDistrict(data) {
        if (data == null) {
            alert('Selected district might have been deleted by someone');  //Message renderer
        } else {
            showDistrict(data);
        }
    }
    function executePreConditionForDeleteDistrict(districtId) {
        if (districtId == null) {
            alert("Please select a district to delete");
            return false;
        }
        return true;
    }
    function executePostConditionForDeleteDistrict(data) {
        if (data.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            resetAll();
        }
        MessageRenderer.render(data)
    }
    function showDistrict(data) {
        var entity = data.district
        var country = data.country
        $('#gFormDistrict input[name = id]').val(entity.id);
        $('#gFormDistrict input[name = version]').val(entity.version);

        if (entity.division) {
            $('#countryInfo').val(country.id).attr("selected", "selected");
            $('#countryInfo').attr('disabled',true);
            selectDivision(country.id, entity);
        }
        else {
            $('#division').val(entity.division);
        }
        $('#geoCode').val(entity.geoCode);
        $('#name').val(entity.name);
        if (entity.userCreated) {
            $('#userCreated').val(entity.userCreated.id).attr("selected", "selected");
        }
        else {
            $('#userCreated').val(entity.userCreated);
        }
        if (entity.userUpdated) {
            $('#userUpdated').val(entity.userUpdated.id).attr("selected", "selected");
        }
        else {
            $('#userUpdated').val(entity.userUpdated);
        }
        $('#dateCreated').val(entity.dateCreated);
        $('#lastUpdated').val(entity.lastUpdated);
        $('#create-button').attr('value', 'Update');
        $('#cancel-button').attr('value', 'Cancel');
        $('#delete-button').show();
    }
    function executeSearchDistrict(fieldName, fieldValue) {
        if (executePreConditionForSearchDistrict(fieldName, fieldValue)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'district', file:'search')}?fieldName=" + fieldName + "&fieldValue=" + fieldValue,
                success: function (entity) {
                    executePostConditionForSearchDistrict(entity, fieldName, fieldValue);
                },
                dataType: 'json'
            });
        }
    }
    function executePreConditionForSearchDistrict(fieldName, fieldValue) {
        // trim field vales before process.
        $('#' + fieldName).val($.trim($('#' + fieldName).val()));
        if (fieldValue == '' || fieldValue == null) {
            resetAll();
            return false;
        }
        return true;
    }
    function executePostConditionForSearchDistrict(data, fieldName, fieldValue) {
        if (data == null) {
            resetAll();
            $('#' + fieldName).val(fieldValue); // Set search field with fieldValue
        } else {
            showDistrict(data);
        }
    }

    function selectDivision(id, territory) {
        var options = '<option value="">Please Select</option>';
        if (id == '') {
            $("#division").html(options);
            return false;
        }
        jQuery.ajax({
            type: 'post',
            url: "${resource(dir:'territoryConfiguration', file:'fetchDropdownList')}?id=" + id + '&sl=' + 1,
            success: function (data) {
                options = '<option value="">Select Division</option>';
                $.each(data, function (key, val) {
                    options += '<option value="' + val.id + '">' + val.name + '</option>';
                })
                $("#division").html(options);
            },
            complete: function () {
                if (territory != 0) {
                    $('#division').val(territory.division.id).attr("selected", "selected");
                }
            },
            dataType: 'json'
        });
    }

    function resetAll(){
        reset_form("#gFormDistrict");
        $('#countryInfo').attr('disabled',false);
        $('#countryInfo').val('');
        selectDivision('',0);
    }
</script>