<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        $('#ui-widget-header-text').html('ThanaUpazilaPouroshova')
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormThanaUpazilaPouroshova").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormThanaUpazilaPouroshova").validationEngine('attach');
        resetAll();
        $("#jqgrid-grid").jqGrid({
            url: '${resource(dir:'thanaUpazilaPouroshova', file:'list')}',
            datatype: "json",
            colNames: [
                'SL',
                'ID',

                'District',
                'Geo Code',
                'Thana Name'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30, sortable: false, align: 'left'},
                {name: 'id', index: 'id', width: 0, hidden: true},

                {name: 'district', index: 'district', width: 100, align: 'left'},
                {name: 'geoCode', index: 'geoCode', width: 100, align: 'left'},
                {name: 'name', index: 'name', width: 100, align: 'left'}
            ],
            rowNum: 50,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#jqgrid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "asc",
            caption: "All Thana/Upazila Information",
            autowidth: true,
            height: true,
            scrollOffset: 0,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                executeEditThanaUpazilaPouroshova();
            }
        });
        $("#jqgrid-grid").jqGrid('navGrid', '#jqgrid-pager', {edit: false, add: false, del: false, search: false});
//        $("#jqgrid-grid").gridResize({minWidth: 350, maxWidth: 800, minHeight: 200});
        $(".ui-pg-selbox").children().each(function () {
            if ($(this).val() == -1) {
                $(this).html('All')
            }

        });
    });
    function getSelectedThanaUpazilaPouroshovaId() {
        var thanaUpazilaPouroshovaId = null;
        var rowid = $("#jqgrid-grid").jqGrid('getGridParam', 'selrow');
        if (rowid) {
            thanaUpazilaPouroshovaId = $("#jqgrid-grid").jqGrid('getCell', rowid, 'id');
        }
        if (thanaUpazilaPouroshovaId == null) {
            thanaUpazilaPouroshovaId = $('#gFormThanaUpazilaPouroshova input[name = id]').val();
        }
        return thanaUpazilaPouroshovaId;
    }
    function executePreConditionThanaUpazilaPouroshova() {
        // trim field vales before process.
        trim_form();
        if (!$("#gFormThanaUpazilaPouroshova").validationEngine('validate')) {
//        if ($("#gFormThanaUpazilaPouroshova").validate().form({onfocusout: false}) == false) {
            return false;
        }
        return true;
    }
    function executeAjaxThanaUpazilaPouroshova() {
        if (!executePreConditionThanaUpazilaPouroshova()) {
            return false;
        }
        var actionUrl = null;
        if ($('#gFormThanaUpazilaPouroshova input[name = id]').val()) {
            actionUrl = "${request.contextPath}/${params.controller}/update";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/create";
        }
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: jQuery("#gFormThanaUpazilaPouroshova").serialize(),
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionThanaUpazilaPouroshova(data);
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
    function executePostConditionThanaUpazilaPouroshova(result) {
        if (result.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            $("#geoCode").val("");
            $("#name").val("");
//            resetAll();
        }
        MessageRenderer.render(result);
    }
    function deleteAjaxThanaUpazilaPouroshova() {    // Delete record
        var thanaUpazilaPouroshovaId = getSelectedThanaUpazilaPouroshovaId();
        if (executePreConditionForDeleteThanaUpazilaPouroshova(thanaUpazilaPouroshovaId)) {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-thanaUpazilaPouroshova").dialog({
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
                            data: jQuery("#gFormThanaUpazilaPouroshova").serialize(),
                            url: "${resource(dir:'thanaUpazilaPouroshova', file:'delete')}",
                            success: function (message) {
                                executePostConditionForDeleteThanaUpazilaPouroshova(message);
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

    function executePreConditionForEditThanaUpazilaPouroshova(thanaUpazilaPouroshovaId) {
        if (thanaUpazilaPouroshovaId == null) {
            alert("Please select a thanaUpazilaPouroshova to edit");
            return false;
        }
        return true;
    }
    function executeEditThanaUpazilaPouroshova() {
        var thanaUpazilaPouroshovaId = getSelectedThanaUpazilaPouroshovaId();
        if (executePreConditionForEditThanaUpazilaPouroshova(thanaUpazilaPouroshovaId)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'thanaUpazilaPouroshova', file:'edit')}?id=" + thanaUpazilaPouroshovaId,
                success: function (entity) {
                    executePostConditionForEditThanaUpazilaPouroshova(entity);
                },
                dataType: 'json'
            });
        }
    }
    function executePostConditionForEditThanaUpazilaPouroshova(data) {
        if (data == null) {
            alert('Selected thanaUpazilaPouroshova might have been deleted by someone');  //Message renderer
        } else {
            showThanaUpazilaPouroshova(data);
        }
    }
    function executePreConditionForDeleteThanaUpazilaPouroshova(thanaUpazilaPouroshovaId) {
        if (thanaUpazilaPouroshovaId == null) {
            alert("Please select a thanaUpazilaPouroshova to delete");
            return false;
        }
        return true;
    }
    function executePostConditionForDeleteThanaUpazilaPouroshova(data) {
        if (data.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            resetAll();
        }
        MessageRenderer.render(data)
    }
    function showThanaUpazilaPouroshova(data) {
        var entity = data.thana
        var country = data.country

        $('#gFormThanaUpazilaPouroshova input[name = id]').val(entity.id);
        $('#gFormThanaUpazilaPouroshova input[name = version]').val(entity.version);

        if (entity.district) {
            $('#countryInfo').val(country.id).attr("selected", "selected");
            $('#countryInfo').attr('disabled',true);
            selectDivision(country.id, data);
        }
        else {
            $('#district').val(entity.district);
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
    function executeSearchThanaUpazilaPouroshova(fieldName, fieldValue) {
        if (executePreConditionForSearchThanaUpazilaPouroshova(fieldName, fieldValue)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'thanaUpazilaPouroshova', file:'search')}?fieldName=" + fieldName + "&fieldValue=" + fieldValue,
                success: function (entity) {
                    executePostConditionForSearchThanaUpazilaPouroshova(entity, fieldName, fieldValue);
                },
                dataType: 'json'
            });
        }
    }
    function executePreConditionForSearchThanaUpazilaPouroshova(fieldName, fieldValue) {
        // trim field vales before process.
        $('#' + fieldName).val($.trim($('#' + fieldName).val()));
        if (fieldValue == '' || fieldValue == null) {
            resetAll();
            return false;
        }
        return true;
    }
    function executePostConditionForSearchThanaUpazilaPouroshova(data, fieldName, fieldValue) {
        if (data == null) {
            resetAll();
            $('#' + fieldName).val(fieldValue); // Set search field with fieldValue
        } else {
            showThanaUpazilaPouroshova(data);
        }
    }

    function selectDivision(id, territory) {
        var options = '<option value="">Please Select</option>';
        $("#district").html(options);
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
                    $('#division').attr('disabled',true);
                    selectDistrict(territory.division.id,territory.thana);
                }
            },
            dataType: 'json'
        });
    }

    function selectDistrict(id, territory) {
        var options = '<option value="">Please Select</option>';
        if(id == ''){
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
                if(territory != 0){
                    $('#district').val(territory.district.id).attr("selected", "selected");
                }
            },
            dataType: 'json'
        });
    }

    function resetAll(){
        reset_form("#gFormThanaUpazilaPouroshova");
        $('#countryInfo').attr('disabled',false);
        $('#countryInfo').val('');
        $('#division').attr('disabled',false);
        $('#division').val('');
        selectDivision('',0);
        selectDistrict('',0);
    }
</script>