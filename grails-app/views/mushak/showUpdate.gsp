<script type="text/javascript" language="Javascript">

    var url = '';

    $(document).ready(function () {
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        if('${dpSize}' == '1'){
            url = '${resource(dir:'mushak', file:'list')}?id=' + ${dpList}[0].id;
        }else if('${dpSize}' != '0'){
            url = '${resource(dir:'mushak', file:'list')}';
        }

        $("#gFormMushak").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormMushak").validationEngine('attach');
        reset_form("#gFormMushak");
        $("#jqgrid-grid-mushak-update").jqGrid({
            url: url,
            datatype: "json",
            colNames: [
                'ID',

                'Mushak No',
                'Customer Name',
                'Challan No',
                'Challan Handover Date'
            ],
            colModel: [
                {name: 'id', index: 'id', width: 0, hidden: true},
                {name: 'mushakNo', index: 'mushakNo', width: 100, align: 'left'},
                {name: 'name', index: 'name', width: 100, align: 'left'},
                {name: 'challanNo', index: 'challanNo', width: 100, align: 'left'},
                {name: 'handoverDate', index: 'handoverDate', width: 100, align: 'center'}
            ],
            rowNum: 10,
            rowList: [10, 20, 30],
            pager: '#jqgrid-pager-mushak-update',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Mushak List",
            autowidth: true,
            autoheight: true,
            scrollOffset: 0,
            altRows: true,
            afterSaveCell: function (id, name, val, iRow, iCol) {
            },
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                executeEditMushak(rowid);
            }
        });

        loadDp();
    });

    function loadDp() {
        if ('${dpSize}' == '0') {
            $("#dpName").val('No Distribution Point Found.');
            $("#distributionPointUpdateHid").val(0);
        } else {
            var size = ${dpSize};
            if (size == 1) {
                $("#dpName").val(${dpList}[0].name);
                $("#distributionPointUpdateHid").val(${dpList}[0].id);
            } else {
                $("#single").attr('hidden', true);
                $("#multi").removeAttrs('hidden');
                var id = '';
                var options = '<option value="">Please Select</option>';
                for (var i = 0; i < size; i++) {
                    if('' + ${dpList}[i].is_factory == 'true'){
                        id = ${dpList}[i].id;
                    }
                    options += '<option value="' + ${dpList}[i].id + '">' + ${dpList}[i].name + '</option>';
                }
                $("#distributionPointUpdate").html(options);
                $("#distributionPointUpdate").val(id);
                $("#distributionPointUpdate").attr('disabled',true);
            }
        }

    }

    function searchMushak() {
        if($("#distributionPointUpdate").val() == ''){
            $("#jqgrid-grid-mushak-update").jqGrid('clearGridData');
            return false;
        }
        jQuery.ajax({
            type: 'post',
            url: '${resource(dir:'mushak', file:'search')}?mushak=' + $("#mushakNoUpdate").val() + "&challan=" + $("#challanNoUpdate").val(),
            success: function (data, textStatus) {
                if(data) {
                    if (data.length == 1) {
                        $("#nameUpdate").val(data[0].name);
                        $("#challanNoUpdate").val(data[0].challan_no);
                        $("#mushakNoUpdate").val(data[0].mushak_no);
                        $("#challanHandoverDateUpdate").val(data[0].challan_handover_date);
                        $("#jqgrid-grid-mushak-update").setGridParam({
                            url: '${resource(dir:'mushak', file:'list')}?mushakId=' + data[0].id
                        });
                        $("#jqgrid-grid-mushak-update").trigger("reloadGrid");
                    } else {
                        $("#nameUpdate").val('');
                        $("#challanHandoverDateUpdate").val('');
                        $("#jqgrid-grid-mushak-update").setGridParam({
                            url: '${resource(dir:'mushak', file:'list')}'
                        });
                        $("#jqgrid-grid-mushak-update").trigger("reloadGrid");
                    }
                }else{
                    MessageRenderer.render({
                        messageTitle: 'Data Error',
                        type: 2,
                        messageBody: 'Given Mushak no not found.'
                    });
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                }else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete: function () {
            },
            dataType: 'json'
        });
    }

    function executeEditMushak(id) {
        jQuery.ajax({
            type: 'post',
            url: '${resource(dir:'mushak', file:'edit')}?id=' + id,
            success: function (data, textStatus) {
                $('#popEmpDetails').html(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                }else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete: function (data) {
            },
            dataType: 'html'
        });
    }
</script>

<%@ page import="com.bits.bdfp.accounts.Mushak" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title><g:message code="mushak.create.label" default="View And Update Mushak"/></title>

<div class="main_container">
    <div class="content_container">
        <h1><g:message code="mushak.create.label" default="View And Update Mushak"/></h1>

        <h3><g:message code="mushak.Info.label" default="View And Update Mushak Details"/></h3>

        <div id="spinnerMushak" class="spinner" style="display:none;" align="left">
            <table class="spinnerTable">
                <tbody>
                <tr>
                    <td><img src="${resource(dir: 'images', file: 'spinner.gif')}" alt="Spinner"/></td>
                    <td>Communicating with the server... Please wait!</td>
                </tr>
                </tbody>
            </table>
        </div>

        <form name='gFormMushakView' id='gFormMushakView'>
            <div id="remote-content-mushak"></div>

            <div class="element_row_content_container lightColorbg pad_bot0">
                <table>
                    <tr>
                        <td>
                            <label for="distributionPointUpdate" class="txtright bold hight1x width1x" style="width: 140px;">
                                <g:message code='mushak.invoice.label' default='Distribution Point'/>
                            </label>

                        </td>
                        <td id="single">
                            <g:textField name="dpName"
                                         id="dpName"
                                         style="width: 170px;"
                                         value="" readonly="readonly"/>
                            <g:hiddenField name="distributionPointUpdateHid" id="distributionPointUpdateHid"/>
                        </td>
                        <td id="multi" hidden="hidden">
                            <g:select name="distributionPointUpdateID" id="distributionPointUpdate"
                                      from=""
                                      style="width: 176px;"
                                      optionKey="id"
                                      value=""/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label for="mushakNoUpdate" class="txtright bold hight1x width1x"
                                   style="width: 140px;"><g:message code='mushak.invoice.label' default='Mushak No'/>
                            </label>

                        </td>
                        <td>
                            <g:textField name="mushakNoUpdate"
                                         id="mushakNoUpdate"
                                         style="width: 170px;"
                                         value=""/>
                        </td>

                        <td>
                            <label for="nameUpdate" class="txtright bold hight1x width1x"
                                   style="width: 140px;">
                                <g:message code='mushak.name.label' default='Customer Name'/>
                            </label>

                        </td>
                        <td>
                            <g:textField name="nameUpdate" class="validate[required]"
                                         id="nameUpdate"
                                         style="width: 170px;"
                                         value="" readonly="readonly"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label for="challanNoUpdate" class="txtright bold hight1x width1x"
                                   style="width: 140px;">
                                <g:message code='mushak.challanNo.label' default='Challan No'/>
                            </label>
                        </td>
                        <td>
                            <g:textField name="challanNoUpdate"
                                         id="challanNoUpdate"
                                         style="width: 170px;"
                                         value=""/>
                        </td>
                        <td>
                            <label for="challanHandoverDateUpdate" class="txtright bold hight1x width1x"
                                   style="width: 140px;">
                                <g:message code='mushak.challanHandoverDate.label' default='Challan Handover Date'/>
                            </label>
                        </td>
                        <td>
                            <g:textField name="challanHandoverDateUpdate"
                                         id="challanHandoverDateUpdate"
                                         style="width: 170px; text-align: center;"
                                         value="" readonly="readonly"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <span class="button"><input type="button" name="create-button" id="search-button-mushak"
                                                        class="ui-button ui-widget ui-state-default ui-corner-all"
                                                        value="Search"
                                                        onclick="searchMushak();"/></span>
                        </td>
                    </tr>
                </table>

            </div>

            <div class="clear"></div>

            <div class="jqgrid-container">
                <table id="jqgrid-grid-mushak-update"></table>

                <div id="jqgrid-pager-mushak-update"></div>
            </div>

            <div class="clear"></div>

            <div id="popEmpDetails">
            </div>
        </form>

        <div class="clear height5"></div>

    </div>
</div>
