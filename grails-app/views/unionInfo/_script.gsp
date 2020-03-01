<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        $('#ui-widget-header-text').html('UnionInfo')
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormUnionInfo").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormUnionInfo").validationEngine('attach');
        resetAll();
        $("#jqgrid-grid").jqGrid({
            url: '${resource(dir:'unionInfo', file:'list')}',
            datatype: "json",
            colNames: [
                'SL',
                'ID',
                'Union Name',
                'Geo Code',
                'Thana'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30, sortable: false, align: 'left'},
                {name: 'id', index: 'id', width: 0, hidden: true},
                {name: 'name', index: 'name', width: 100, align: 'left'},
                {name: 'geoCode', index: 'geoCode', width: 100, align: 'left'},
                {name: 'thanaUpazilaPouroshova', index: 'thanaUpazilaPouroshova', width: 100, align: 'left'}
            ],
            rowNum: 50,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#jqgrid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "asc",
            caption: "Union List",
            autowidth: true,
            height: true,
            scrollOffset: 0,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                executeEditUnionInfo();
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
    function getSelectedUnionInfoId() {
        var unionInfoId = null;
        var rowid = $("#jqgrid-grid").jqGrid('getGridParam', 'selrow');
        if (rowid) {
            unionInfoId = $("#jqgrid-grid").jqGrid('getCell', rowid, 'id');
        }
        if (unionInfoId == null) {
            unionInfoId = $('#gFormUnionInfo input[name = id]').val();
        }
        return unionInfoId;
    }
    function executePreConditionUnionInfo() {
        // trim field vales before process.
        trim_form();
        if (!$("#gFormUnionInfo").validationEngine('validate')) {
//        if ($("#gFormUnionInfo").validate().form({onfocusout: false}) == false) {
            return false;
        }
        return true;
    }
    function executeAjaxUnionInfo() {
        if (!executePreConditionUnionInfo()) {
            return false;
        }
        var actionUrl = null;
        if ($('#gFormUnionInfo input[name = id]').val()) {
            actionUrl = "${request.contextPath}/${params.controller}/update";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/create";
        }
        SubmissionLoader.hideFrom();
        jQuery.ajax({
            type: 'post',
            data: jQuery("#gFormUnionInfo").serialize(),
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionUnionInfo(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-grid").trigger("reloadGrid");
                    $("#geoCode").val("");
                    $("#name").val("");

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
    function executePostConditionUnionInfo(result) {
        if (result.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            $("#geoCode").val("");
            $("#name").val("");
//            resetAll();
        }
        MessageRenderer.render(result);
    }
    function deleteAjaxUnionInfo() {    // Delete record
        var unionInfoId = getSelectedUnionInfoId();
        if (executePreConditionForDeleteUnionInfo(unionInfoId)) {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-unionInfo").dialog({
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
                            data: jQuery("#gFormUnionInfo").serialize(),
                            url: "${resource(dir:'unionInfo', file:'delete')}",
                            success: function (message) {
                                executePostConditionForDeleteUnionInfo(message);
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

    function executePreConditionForEditUnionInfo(unionInfoId) {
        if (unionInfoId == null) {
            alert("Please select a unionInfo to edit");
            return false;
        }
        return true;
    }
    function executeEditUnionInfo() {
        var unionInfoId = getSelectedUnionInfoId();
        if (executePreConditionForEditUnionInfo(unionInfoId)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'unionInfo', file:'edit')}?id=" + unionInfoId,
                success: function (entity) {
                    executePostConditionForEditUnionInfo(entity);
                },
                dataType: 'json'
            });
        }
    }
    function executePostConditionForEditUnionInfo(data) {
        if (data == null) {
            alert('Selected unionInfo might have been deleted by someone');  //Message renderer
        } else {
            showUnionInfo(data);
        }
    }
    function executePreConditionForDeleteUnionInfo(unionInfoId) {
        if (unionInfoId == null) {
            alert("Please select a unionInfo to delete");
            return false;
        }
        return true;
    }
    function executePostConditionForDeleteUnionInfo(data) {
        if (data.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            resetAll();
        }
        MessageRenderer.render(data)
    }
    function showUnionInfo(data) {
        var entity = data.union
        var country = data.country

        $('#gFormUnionInfo input[name = id]').val(entity.id);
        $('#gFormUnionInfo input[name = version]').val(entity.version);

        $('#name').val(entity.name);
        $('#geoCode').val(entity.geoCode);
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
        if (entity.thanaUpazilaPouroshova) {
            $('#countryInfo').val(country.id).attr("selected", "selected");
            $('#countryInfo').attr('disabled',true);
            selectDivision(country.id, data);
        }
        else {
            $('#thanaUpazilaPouroshova').val(entity.thanaUpazilaPouroshova);
        }
        $('#create-button').attr('value', 'Update');
        $('#cancel-button').attr('value', 'Cancel');
        $('#delete-button').show();
    }
    function executeSearchUnionInfo(fieldName, fieldValue) {
        if (executePreConditionForSearchUnionInfo(fieldName, fieldValue)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'unionInfo', file:'search')}?fieldName=" + fieldName + "&fieldValue=" + fieldValue,
                success: function (entity) {
                    executePostConditionForSearchUnionInfo(entity, fieldName, fieldValue);
                },
                dataType: 'json'
            });
        }
    }
    function executePreConditionForSearchUnionInfo(fieldName, fieldValue) {
        // trim field vales before process.
        $('#' + fieldName).val($.trim($('#' + fieldName).val()));
        if (fieldValue == '' || fieldValue == null) {
            resetAll();
            return false;
        }
        return true;
    }
    function executePostConditionForSearchUnionInfo(data, fieldName, fieldValue) {
        if (data == null) {
            resetAll();
            $('#' + fieldName).val(fieldValue); // Set search field with fieldValue
        } else {
            showUnionInfo(data);
        }
    }

    function selectDivision(id, territory) {
        var options = '<option value="">Please Select</option>';
        $("#district").html(options);
        $("#thanaUpazilaPouroshova").html(options);
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
                    $('#division').attr('disabled', true);
                    selectDistrict(territory.division.id, territory);
                }
            },
            dataType: 'json'
        });
    }

    function selectDistrict(id, territory) {
        var options = '<option value="">Please Select</option>';
        $("#thanaUpazilaPouroshova").html(options);
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
                if (territory != 0) {
                    $('#district').val(territory.district.id).attr("selected", "selected");
                    $('#district').attr('disabled', true);
                    selectThana(territory.district.id, territory.union);
                }
            },
            dataType: 'json'
        });
    }

    function selectThana(id, territory) {
        var options = '<option value="">Please Select</option>';
        if(id == ''){
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
                if(territory != 0){
                    $('#thanaUpazilaPouroshova').val(territory.thanaUpazilaPouroshova.id).attr("selected", "selected");
                }
            },
            dataType: 'json'
        });
    }

    function resetAll() {
        reset_form("#gFormUnionInfo");
        $('#countryInfo').attr('disabled', false);
        $('#countryInfo').val('');
        $('#division').attr('disabled', false);
        $('#division').val('');
        $('#district').attr('disabled', false);
        $('#district').val('');
        selectDivision('', 0);
        selectDistrict('', 0);
        selectThana('',0);
    }
</script>