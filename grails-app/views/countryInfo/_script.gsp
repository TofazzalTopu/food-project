<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        $('#ui-widget-header-text').html('CountryInfo')
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormCountryInfo").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormCountryInfo").validationEngine('attach');

        reset_form("#gFormCountryInfo");
        $("#jqgrid-grid").jqGrid({
            url: '${resource(dir:'countryInfo', file:'list')}',
            datatype: "json",
            colNames: [
                'SL',
                'ID',

                'Country Code',
                'Country Name'

            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30, sortable: false, align: 'left'},
                {name: 'id', index: 'id', width: 0, hidden: true},

                {name: 'code', index: 'code', width: 100, align: 'left'},
                {name: 'name', index: 'name', width: 100, align: 'left'}

            ],
            rowNum: 50,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#jqgrid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "asc",
            caption: "Country List",
            autowidth: true,
            height: true,
            scrollOffset: 0,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                executeEditCountryInfo();
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
    function getSelectedCountryInfoId() {
        var countryInfoId = null;
        var rowid = $("#jqgrid-grid").jqGrid('getGridParam', 'selrow');
        if (rowid) {
            countryInfoId = $("#jqgrid-grid").jqGrid('getCell', rowid, 'id');
        }
        if (countryInfoId == null) {
            countryInfoId = $('#gFormCountryInfo input[name = id]').val();
        }
        return countryInfoId;
    }
    function executePreConditionCountryInfo() {
        // trim field vales before process.
        trim_form();

        if (!$("#gFormCountryInfo").validationEngine('validate')) {
//      if ($("#gFormCountryInfo").validate().form({onfocusout: false}) == false) {
            return false;
        }
        return true;
    }
    function executeAjaxCountryInfo() {
        if (!executePreConditionCountryInfo()) {
            return false;
        }
        var actionUrl = null;
        if ($('#gFormCountryInfo input[name = id]').val()) {
            actionUrl = "${request.contextPath}/${params.controller}/update";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/create";
        }
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: jQuery("#gFormCountryInfo").serialize(),
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionCountryInfo(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-grid").trigger("reloadGrid");
                    reset_form('#gFormCountryInfo');
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
    function executePostConditionCountryInfo(result) {
        if (result.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_form('#gFormCountryInfo');
        }
        MessageRenderer.render(result);
    }
    function deleteAjaxCountryInfo() {    // Delete record
        var countryInfoId = getSelectedCountryInfoId();
        if (executePreConditionForDeleteCountryInfo(countryInfoId)) {

            $("#dialog").dialog("destroy");
            $("#dialog-confirm-countryInfo").dialog({
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
                            data: jQuery("#gFormCountryInfo").serialize(),
                            url: "${resource(dir:'countryInfo', file:'delete')}",
                            success: function (message) {
                                executePostConditionForDeleteCountryInfo(message);
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

    function executePreConditionForEditCountryInfo(countryInfoId) {
        if (countryInfoId == null) {
            alert("Please select a countryInfo to edit");
            return false;
        }
        return true;
    }
    function executeEditCountryInfo() {
        var countryInfoId = getSelectedCountryInfoId();
        if (executePreConditionForEditCountryInfo(countryInfoId)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'countryInfo', file:'edit')}?id=" + countryInfoId,
                success: function (entity) {
                    executePostConditionForEditCountryInfo(entity);
                },
                dataType: 'json'
            });
        }
    }
    function executePostConditionForEditCountryInfo(data) {
        if (data == null) {
            alert('Selected countryInfo might have been deleted by someone');  //Message renderer
        } else {
            showCountryInfo(data);
        }
    }
    function executePreConditionForDeleteCountryInfo(countryInfoId) {
        if (countryInfoId == null) {
            alert("Please select a countryInfo to delete");
            return false;
        }
        return true;
    }
    function executePostConditionForDeleteCountryInfo(data) {
        if (data.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_form('#gFormCountryInfo');
        }
        MessageRenderer.render(data)
    }
    function showCountryInfo(entity) {
        $('#gFormCountryInfo input[name = id]').val(entity.id);
        $('#gFormCountryInfo input[name = version]').val(entity.version);

        $('#code').val(entity.code);
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
    function executeSearchCountryInfo(fieldName, fieldValue) {
        if (executePreConditionForSearchCountryInfo(fieldName, fieldValue)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'countryInfo', file:'search')}?fieldName=" + fieldName + "&fieldValue=" + fieldValue,
                success: function (entity) {
                    executePostConditionForSearchCountryInfo(entity, fieldName, fieldValue);
                },
                dataType: 'json'
            });
        }
    }
    function executePreConditionForSearchCountryInfo(fieldName, fieldValue) {
        // trim field vales before process.
        $('#' + fieldName).val($.trim($('#' + fieldName).val()));
        if (fieldValue == '' || fieldValue == null) {
            reset_form("#gFormCountryInfo");
            return false;
        }
        return true;
    }
    function executePostConditionForSearchCountryInfo(data, fieldName, fieldValue) {
        if (data == null) {
            reset_form("#gFormCountryInfo"); // Clear Form
            $('#' + fieldName).val(fieldValue); // Set search field with fieldValue
        } else {
            showCountryInfo(data);
        }
    }
</script>