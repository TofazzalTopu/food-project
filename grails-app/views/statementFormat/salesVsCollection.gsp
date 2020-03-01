<%--
  Created by IntelliJ IDEA.
  User: sarahafreen.orny
  Date: 4/20/2016
  Time: 10:13 AM
--%>

<%@ page import="com.bits.bdfp.setup.salestarget.StatementFormatController;com.bits.bdfp.inventory.product.ProductPrice;com.docu.commons.CommonConstants" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title><g:message code="ttumFileExport.create.label" default="Sales Vs Collection"/></title>

<script>

    $(document).ready(function () {
        $('#te').hide();
        $('#ch').hide();
        $('#territoryCombination').hide();
        setDateRange('fromDate','toDate');
        $('#fromDate').mask('${CommonConstants.DATE_MASK_FORMAT}', {});
        $('#toDate').mask('${CommonConstants.DATE_MASK_FORMAT}', {});

    });


    function findSalesChannel(value) {
        $('#territoryCombination').hide();
        $('#te').hide();
        $('#ch').show();
        if (value != '') {
            var options = '<option value="">All Sales Channel</option>';
        }
        jQuery.ajax({
            type: 'post',
            url: "${resource(dir:'statementFormat', file:'fetchDropDownList')}?id=" + value,
            success: function (data) {
                $.each(data, function (key, val) {
                    options += '<option value="' + val.ID + '">' + val.name + '</option>';
                })
                $("#Channel").html(options);
            },

            dataType: 'json'
        });
    }

    function findTerritory(value) {
        $('#ch').hide();
        $('#territoryCombination').show();

    }

    function findDivision(value) {
        $('#ch').hide();
        $('#te').show();
        if (value != '') {
            var options = '<option value="">Select Division</option>';
        }
        jQuery.ajax({
            type: 'post',
            url: "${resource(dir:'statementFormat', file:'fetchDropDownList')}?id=" + value,
            success: function (data) {
//                console.log(data);
                $.each(data, function (key, val) {
                    options += '<option value="' + val.ID + '">' + val.name + '</option>';
                })
                $("#territory").html(options);
            },
            dataType: 'json'
        });
    }

    function findDistrict(value) {
        $('#ch').hide();
        $('#te').show();
        if (value != '') {
            var options = '<option value="">Select District</option>';
        }
        jQuery.ajax({
            type: 'post',
            url: "${resource(dir:'statementFormat', file:'fetchDropDownList')}?id=" + value,
            success: function (data) {
                console.log(data);
                $.each(data, function (key, val) {
                    options += '<option value="' + val.ID + '">' + val.name + '</option>';
                })
                $("#territory").html(options);
            },
            dataType: 'json'
        });
    }

    function findThana(value) {
        $('#ch').hide();
        $('#te').show();
        if (value != '') {
            var options = '<option value="">Select Thana</option>';
        }
        jQuery.ajax({
            type: 'post',
            url: "${resource(dir:'statementFormat', file:'fetchDropDownList')}?id=" + value,
            success: function (data) {
                $.each(data, function (key, val) {
                    options += '<option value="' + val.ID + '">' + val.name + '</option>';
                })
                $("#territory").html(options);
            },
            dataType: 'json'
        });
    }
//
//    function executePreCondition() {
//
//        var fromDate = $('#fromDate').val();
//        var toDate = $('#toDate').val();
//
//
//        if (!isPastDate(toDate, fromDate)) {
//            var message = {
//                'messageTitle': "Supplier Report",
//                'type': 2,
//                'messageBody': "From Date can't be greater than To Date"
//            }
//            MessageRenderer.render(message);
//        }
//        else {
//            trim_form();
//            return true;
//        }
//    }


    function salesVsCollection() {
//        if (!executePreCondition()) {
//            return false;
//        }

        var fromDate = $('#fromDate').val();
        var toDate = $('#toDate').val();
        var t = $('input[name=territorycombination]:checked').val();
        var c = $('input[name=territorychannel]:checked').val();
        var territoryArea = $("#territory option:selected").text();
        var channelval=$("#Channel option:selected").text();

        if (c=="saleschannel" ) {
                        SubmissionLoader.showTo();
                        window.open("${resource(dir:'statementFormat', file:'rptSalesVsCollectionChannel')}?format=PDF&fromDate=" + fromDate + "&toDate=" + toDate+ "&channel=" + channelval);
                        SubmissionLoader.hideFrom();
        }
       else if (t=="division" ) {
                        SubmissionLoader.showTo();
                        window.open("${resource(dir:'statementFormat', file:'rptSalesVsCollectionDivision')}?format=PDF&fromDate=" + fromDate + "&toDate=" + toDate+ "&division=" + territoryArea);
                        SubmissionLoader.hideFrom();
        }
       else  if (t=="district" ) {
                        SubmissionLoader.showTo();
                        window.open("${resource(dir:'statementFormat', file:'rptSalesVsCollectionDistrict')}?format=PDF&fromDate=" + fromDate + "&toDate=" + toDate+ "&district=" + territoryArea);
                        SubmissionLoader.hideFrom();
        }
        else  if (t=="thana" ) {
                        SubmissionLoader.showTo();
                        window.open("${resource(dir:'statementFormat', file:'rptSalesVsCollectionThana')}?format=PDF&fromDate=" + fromDate + "&toDate=" + toDate+ "&thana=" + territoryArea);
                        SubmissionLoader.hideFrom();
        }
    }
</script>

<div class="main_container">
    <div class="content_container">
        <h1><g:message code="salesReportMT.create.label" default="Sales Vs Collection"/></h1>

        <h3><g:message code="salesReportMT.Info.label" default="Sales Vs Collection"/></h3>

        <div class="clear height5"></div>

        <div class="element_container_big">
            <div class="block-title">

                <div class='element-title'>
                    <g:message code='salesReportMT.fromDate.label' default='From Date'/>
                    <span class="mendatory_field">*</span>
                </div>

                <div class='element-title'>
                    <g:message code='salesReportMT.toDate.label' default='To Date'/>
                    <span class="mendatory_field">*</span>
                </div>

                <div class="clear"></div>
            </div>

            <div class="block-input">

                <div class='element-input inputContainer'><g:textField class="validate[required] text-input datepicker"
                                                                       name="fromDate" id="fromDate"/>
                </div>
                <script type='text/javascript'>
                    $(document).ready(function () {
                        $('#fromDate').datepicker({
                            dateFormat: 'dd-mm-yy',
                            changeMonth: true,
                            changeYear: true
                        });
                        $('#fromDate').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});
                    });
                </script>

                <div class='element-input inputContainer'><g:textField class="validate[required] text-input datepicker"
                                                                       name="toDate" id="toDate"/>
                </div>
                <script type='text/javascript'>
                    $(document).ready(function () {
                        $('#toDate').datepicker({
                            dateFormat: 'dd-mm-yy',
                            changeMonth: true,
                            changeYear: true
                        });
                        $('#toDate').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});
                    });
                </script>

                <div class="clear"></div>

                <table>
                    <tr>
                        <td>
                            <label class="txtright bold hight1x width1x" style="width: 140px;">
                                <g:message code="statementFormat.factory.label"
                                           default="Territory Combination"/>
                            </label>
                        </td>
                        <td>
                            <g:radio name="territorychannel" id="Territory" value="territory"
                                     onchange="findTerritory(this.value)"/>
                        </td>
                        <td>
                            <label class="txtright bold hight1x width1x" style="width: 80px;padding-left: 40px">
                                <g:message code="statementFormat.factory.label"
                                           default="Sales Channel"/>
                            </label>
                        </td>
                        <td>
                            <g:radio name="territorychannel" id="SalesChannel" value="saleschannel"
                                     onchange="findSalesChannel(this.value)"/>
                        </td>
                    </tr>
                </table>
            </div>

            <div id="territoryCombination">
                <table>
                    <tr>
                        <td>
                            <label class="txtright bold hight1x width1x" style="width: 50px;">
                                <g:message code="statementFormat.factory.label"
                                           default="Division"/>
                            </label>
                        </td>
                        <td>
                            <g:radio name="territorycombination" id="Division" value="division"
                                     onchange="findDivision(this.value)"/>
                        </td>
                        <td>
                            <label class="txtright bold hight1x width1x" style="width: 50px;padding-left: 10px">
                                <g:message code="statementFormat.factory.label"
                                           default="District"/>
                            </label>
                        </td>
                        <td>
                            <g:radio name="territorycombination" id="District" value="district"
                                     onchange="findDistrict(this.value)"/>
                        </td>

                        <td>
                            <label class="txtright bold hight1x width1x" style="width: 50px;padding-left: 10px">
                                <g:message code="statementFormat.factory.label"
                                           default="Thana"/>
                            </label>
                        </td>
                        <td>
                            <g:radio name="territorycombination" id="Thana" value="thana"
                                     onchange="findThana(this.value)"/>
                        </td>
                    </tr>
                </table>
            </div>

            <div id="te">
                <label class="txtright bold hight1x width1x" style="width: 80px;">
                    <g:message code="salesReportMT.territory.label"
                               default="Territory"/>
                </label>

                <g:select name="territory" id="territory" class="validate[required]" optionKey="id"
                          optionValue="name"
                          noSelection="['': '- Select -']"/>
            </div>

            <div class="clear"></div>

            <div id="ch">
                <label class="txtright bold hight1x width1x" style="width: 80px;">
                    <g:message code="salesReportMT.territory.label"
                               default="Sales Channel"/>
                </label>

                <g:select name="channel" id="Channel" class="validate[required]" optionKey="id"  optionValue="name"
                          noSelection="['': '- Select -']"/>
            </div>
        </div>
    </div>
</div>

<div class="clear height5"></div>

<div id="dialog-confirm-salesMT" title="Delete alert" style="display: none;">
    <p><span class="ui-icon ui-icon-alert"
             style="float:left; margin:0 7px 20px 0;"></span> Report will be printed. Want to continue?</p>
</div>

</div>

<div class="buttons" style="margin-left:10px;">
    <span class="button"><input type="button" name="print-button" id="dialog-confirm-workOrder"
                                class="ui-button ui-widget ui-state-default ui-corner-all"
                                value="Generate Report"
                                onclick="salesVsCollection();"/></span>
</div>
</div>