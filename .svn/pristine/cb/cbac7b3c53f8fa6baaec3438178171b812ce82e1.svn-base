<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }
        getBusinessMonth();
        $("#gFormLocalHoliday").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormLocalHoliday").validationEngine('attach');
        reset_form("#gFormLocalHoliday");
        $("#jqgrid-grid-division").jqGrid({
            url: '${request.contextPath}/${params.controller}/list',
            datatype: "json",
            colNames: [
                'SL',
                'ID',
                'Financial Year',
                'Territory',
                'Holiday Date',
                'Description'

            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30, sortable: true, align: 'left'},
                {name: 'id', index: 'id', width: 0, hidden: true},


                {name: 'name', index: 'name', width: 100, align: 'left'}

                ,
                {name: 'territory', index: 'territory', width: 100, align: 'left'}

                ,

                {name: 'holiday_date', index: 'holiday_date', width: 100, align: 'left'}

                ,
                {name: 'note', index: 'note', width: 100, align: 'left'}
            ],
            rowNum: 10,
            rowList: [10, 20, 30],
            pager: '#jqgrid-pager-division',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "All Local Holiday Information",
            autowidth: true,
            autoheight: true,
            scrollOffset: 0,
            altRows: true,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                executeEditHoliday();
            }
        });
        $("#jqgrid-grid-division").jqGrid('navGrid', '#jqgrid-pager-division', {
            edit: false,
            add: false,
            del: false,
            search: false
        })
                .navButtonAdd('#jqgrid-pager-division', {
                    caption: "Edit",
                    buttonicon: "ui-icon-edit",
                    onClickButton: function () {
                        executeEditDivision();
                    },
                    position: "last"
                })
                .navButtonAdd('#jqgrid-pager-division', {
                    caption: "Delete",
                    buttonicon: "ui-icon-del",
                    onClickButton: function () {
                        deleteAjaxDivision();
                    },
                    position: "last"
                });
    });
    function getSelectedDivisionId() {
        var divisionId = null;
        var rowid = $("#jqgrid-grid-division").jqGrid('getGridParam', 'selrow');
        if (rowid) {
            divisionId = $("#jqgrid-grid-division").jqGrid('getCell', rowid, 'id');
        }
        if (divisionId == null) {
            divisionId = $('#gFormLocalHoliday input[name = id]').val();
        }
        return divisionId;
    }
    function executePreConditionDivision() {
        // trim field vales before process.
        trim_form();
        if ($("#gFormLocalHoliday").validate().form({onfocusout: false}) == false) {
            return false;
        }
        return true;
    }
    function executeAjaxHoliday() {
        if ($('#month').val() && $('#day').val()) {
            MessageRenderer.hideHeaderMessage();
            var actionUrl = null;
            if ($('#gFormLocalHoliday input[name = id]').val()) {
                actionUrl = "${request.contextPath}/${params.controller}/update";
                $('#day').attr("disabled", false);
                $('#month').attr("disabled", false);
            } else {
                actionUrl = "${request.contextPath}/${params.controller}/create";
            }
            jQuery.ajax({
                type: 'post',
                data: jQuery("#gFormLocalHoliday").serialize(),
                url: actionUrl,
                success: function (data, textStatus) {
                    executePostConditionDivision(data);
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    if(XMLHttpRequest.status = 0){
                        MessageRenderer.renderErrorText("Network Problem: Time out");
                    }  else{
                        MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                    }
                },
                complete: function () {
                },
                dataType: 'json'
            });
        }
        else {
            MessageRenderer.showHeaderMessage("Please select Territory,Month And Day", 0);
        }

        return false;
    }
    function executePostConditionDivision(result) {
        if (result.type == 1) {
            $("#jqgrid-grid-division").trigger("reloadGrid");
            reset_form('#gFormLocalHoliday');
        }
        $("#financialYearId option:text=" + "Select One" + "").attr("selected", "selected");
        MessageRenderer.render(result);
    }


    function executePreConditionForEditDivision(divisionId) {
        if (divisionId == null) {
            alert("Please select a division to edit");
            return false;
        }
        return true;
    }
    function executeEditHoliday() {
        var divisionId = getSelectedDivisionId();
        if (executePreConditionForEditDivision(divisionId)) {
            $.ajax({
                type: "POST",
                url: "${request.contextPath}/${params.controller}/edit?id=" + divisionId,
                success: function (entity) {
                    executePostConditionForEditHoliday(entity);
                },
                dataType: 'json'
            });
        }
    }
    function executePostConditionForEditHoliday(data) {
        if (data == null) {
            alert('Selected Holiday might have been deleted by someone');  //Message renderer
        } else {
            showHoliday(data);
        }


    }
    function executePreConditionForDeleteDivision(divisionId) {
        if (divisionId == null) {
            alert("Please select a division to delete");
            return false;
        }
        return true;
    }
    function executePostConditionForDeleteDivision(data) {
        if (data.type == 1) {
            $("#jqgrid-grid-division").trigger("reloadGrid");
            reset_form('#gFormLocalHoliday');
        }
        MessageRenderer.render(data)
    }
    function showHoliday(entity) {
        $('#gFormLocalHoliday input[name = id]').val(entity.id);
        $('#gFormLocalHoliday input[name = version]').val(entity.version);

        if (entity.month && entity.year) {
            $('#monthSelect').val(entity.month + '-' + entity.year).attr("selected", "selected");
            if (entity.day) {
                getBusinessDayForEdit(entity.day);
            }
            if (entity.territoryConfiguration) {
                $('#territoryConfiguration').val(entity.territoryConfiguration.id)
            }
            if (entity.note) {
                $('#note').val(entity.note)
            }
            $('#create-button').attr('value', 'Update');
            $('#delete-button').show();

        }


    }
    function executeSearchDivision(fieldName, fieldValue) {
        if (executePreConditionForSearchDivision(fieldName, fieldValue)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'division', file:'search')}?fieldName=" + fieldName + "&fieldValue=" + fieldValue,
                success: function (entity) {
                    executePostConditionForSearchDivision(entity, fieldName, fieldValue);
                },
                dataType: 'json'
            });
        }
    }
    function executePreConditionForSearchDivision(fieldName, fieldValue) {
        // trim field vales before process.
        $('#' + fieldName).val($.trim($('#' + fieldName).val()));
        if (fieldValue == '' || fieldValue == null) {
            reset_form("#gFormLocalHoliday");
            return false;
        }
        return true;
    }
    function executePostConditionForSearchDivision(data, fieldName, fieldValue) {
        if (data == null) {
            reset_form("#gFormLocalHoliday"); // Clear Form
            $('#' + fieldName).val(fieldValue); // Set search field with fieldValue
        } else {
            showHoliday(data);
        }
    }

    function reset_form(formName) {
        $(formName).find(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
            if (this.type == 'hidden') {
                if (this.name != "financialYearId" && this.name != "financialYear.id") {
                    this.value = "";
                }

            } else {
                $('#monthSelect').val('');
                $('#day').val('');
                $('#territoryConfiguration').val('');
                $('#note').val('');
            }
        });

        $('input[type=checkbox]').attr('checked', false);
        $(formName + ' input[name = create-button]').attr('value', 'Save');
        $(formName + ' input[name = delete-button]').hide();

    }
    function reset_holiday_form() {
        reset_form("#gFormLocalHoliday");
    }
    function trim_form() {
        $(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
            this.value = $.trim(this.value);
        });
    }

    function getBusinessMonth() {
        var yearId = $('#financialYearId').val();
        if (yearId) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'businessHoliday', file:'getBusinessMonthForHoliday')}?id=" + yearId,
                beforeSend: function (jqXHR, settings) {
                    $("#loader_icon").show();
                },
                success: function (data) {
                    var options = '<option value="">Select Month</option>';
                    $.each(data, function (key, val) {
                        options += '<option value="' + val.month_val + '">' + val.month_string + '</option>';
//                    console.log(val)
                    })
                    $("#monthSelect").html(options);
                    if (data.length > 0) {
//                      $("#monthName").text(data[0].month_string);
//                      $("#month").val(data[0].month_val);
                        $("#year").val(data[0].year);
                        //getBusinessDay(0)
                    }
                    else {
                        MessageRenderer.showHeaderMessage("Please open a business month first", 1);
                    }

                },
                complete: function (data) {
                    $("#loader_icon").hide();
                },
                dataType: 'json'
            });
        }
        else {
            MessageRenderer.showHeaderMessage("Please Select Financial Year", 1)
        }

    }

    function getBusinessDay(day) {
        var yearId = $('#financialYearId').val();
        if (yearId) {
            var month_val = $("#monthSelect").val().split("-");
            var monthId = month_val[0];
            $('#year').val(month_val[1]);
            $('#month').val(monthId);

            $.ajax({
                type: "POST",
                url: "${resource(dir:'financialYear', file:'getBusinessDay')}?monthId=" + monthId + "&yearId=" + yearId + "&yearVal=" + $('#year').val(),
                beforeSend: function (jqXHR, settings) {
                    $("#loader_icon").show();
                },
                success: function (data) {
                    var options = '<option value="">Select Day</option>';
                    $.each(data, function (key, val) {
                        options += '<option value="' + val.date + '">' + val.date + '</option>';
//                    console.log(val)
                    })
                    $("#day").html(options);
//                  if(day!=0){
//                      $('#day').val(day).attr("selected","selected");
//                      $('#day').attr("disabled", true);
//                  }
                },
                complete: function (data) {
                    $("#loader_icon").hide();
                },
                dataType: 'json'
            });
        }
        else {
            MessageRenderer.showHeaderMessage("Financial year is not open", 1)
        }

    }
    function getBusinessDayForEdit(day) {
        var yearId = $('#financialYearId').val();
        if (yearId) {
            var month_val = $("#monthSelect").val().split("-");
            var monthId = month_val[0];
            $('#year').val(month_val[1]);
            $('#month').val(monthId);
            $.ajax({
                type: "POST",
                url: "${resource(dir:'financialYear', file:'getBusinessDay')}?monthId=" + monthId + "&yearId=" + yearId + "&yearVal=" + $('#year').val(),
                beforeSend: function (jqXHR, settings) {
                    $("#loader_icon").show();
                },
                success: function (data) {
                    var options = '<option value="">Select Day</option>';
                    $.each(data, function (key, val) {
                        options += '<option value="' + val.date + '">' + val.date + '</option>';
//                    console.log(val)
                    })
                    $("#day").html(options);
                    if (day != 0) {
                        $('#day').val(day).attr("selected", "selected");

                    }
                },
                complete: function (data) {
                    $("#loader_icon").hide();
                },
                dataType: 'json'
            });
        }
        else {
            MessageRenderer.showHeaderMessage("Financial year is not open", 1)
        }

    }
    function deleteAjaxHoliday() {    // Delete record
        var holidayId = getSelectedDivisionId();
        if (executePreConditionForDeleteHoliday(holidayId)) {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-holiday").dialog({
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
                            data: jQuery("#gFormLocalHoliday").serialize(),
                            url: "${resource(dir:'localHoliday', file:'delete')}",
                            success: function (message) {
                                executePostConditionForDeleteHoliday(message);
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
    function executePreConditionForDeleteHoliday(holidayId) {
        if (holidayId == null) {
            alert("Please select a holiday to delete");
            return false;
        }
        return true;
    }
    function executePostConditionForDeleteHoliday(data) {
        if (data.type == 1) {
            $("#jqgrid-grid-division").trigger("reloadGrid");
            reset_form('#gFormLocalHoliday');
        }
        MessageRenderer.render(data)
    }
</script>