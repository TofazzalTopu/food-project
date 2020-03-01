<%--
  Created by IntelliJ IDEA.
  User: sarahafreen.orny
  Date: 4/20/2016
  Time: 10:13 AM
--%>

<%@ page import="com.bits.bdfp.setup.salestarget.SalesReportMTController;com.docu.commons.CommonConstants;com.bits.bdfp.common.Division" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title>DCR for Sales Officer</title>

<script>
    var fromDate,toDate;
    $(document).ready(function () {
        dateValidation();
        $('#year').datepicker({
            dateFormat: 'yy',
            changeMonth: true,
            changeYear: true,
            onSelect: function (dateText) {
                yearDateRange(this.value);
            }
        });
        $('#year').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});

        $('#month').datepicker({
            dateFormat: 'mm-yy',
            changeMonth: true,
            changeYear: true,
            onSelect: function (dateText) {
                monthDateRange(this.value);
            }
        });
        $('#month').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});

    });

    function dateValidation(){
            var message = {
                'messageTitle': "DCR For Sales",
                'type': 2,
                'messageBody': "Select either Year or Month"
            }
            MessageRenderer.render(message);
    }
    function leapYear(year)
    {
        return ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0);
    }
    function yearDateRange(year) {
        var y = $("#year").val();
        fromDate = "01-01-" +y;
        toDate = "31-12-" +y;

    }
    function monthDateRange(month) {
        var m = $("#month").val();
        var month = m
        var year = parseInt(month.substring(3,7));
        var checkLeapyear= leapYear(year);
        month = month.substring(0, month.indexOf('-'));
        if(month == '01')
        {
            fromDate = "01-" + m;
            toDate = "31-" + m;
        }
        if(month == '02')
        {
            if(checkLeapyear == true )
            {
            fromDate = "01-" + m;
            toDate = "29-" + m;
            }
            else{
                fromDate = "01-" + m;
                toDate = "28-" + m;
            }

        }
        if(month == '03')
        {
            fromDate = "01-" + m;
            toDate = "31-" + m;
        }
        if(month == '04')
        {
            fromDate = "01-" + m;
            toDate = "30-" + m;
        }
        if(month == '05')
        {
            fromDate = "01-" + m;
            toDate = "31-" + m;
        }
        if(month == '06')
        {
            fromDate = "01-" + m;
            toDate = "30-" + m;
        }
        if(month == '07')
        {
            fromDate = "01-" + m;
            toDate = "31-" + m;
        }
        if(month == '08')
        {
            fromDate = "01-" + m;
            toDate = "31-" + m;
        }
        if(month == '09')
        {
            fromDate = "01-" + m;
            toDate = "30-" + m;
        }
        if(month == '10')
        {
            fromDate = "01-" + m;
            toDate = "31-" + m;
        }
        if(month == '11')
        {
            fromDate = "01-" + m;
            toDate = "30-" + m;
        }
        if(month == '12')
        {
            fromDate = "01-" + m;
            toDate = "31-" + m;
        }
    }
    function executePreCondition() {
             trim_form();
             return true;

    }
    function dcrForSalesReport() {
//        if (!executePreCondition()) {
//            return false;
//        }

//        var year = $('#year').val();
//        var month = $('#month').val();

        var division = $('#division').val();
        var divisiontext = $('#division option:selected').text();
        var district = $('#district').val();
        var districttext = $('#district option:selected').text();
        var salesofficer = $('#salesOfficer').val();
        var salesofficertext = $("#salesOfficer option:selected").text();

//        var territoryArea=$("#territory option:selected").text();

        SubmissionLoader.showTo();
        window.open("${resource(dir:'salesReportMT', file:'rptDcrForSales')}?format=PDF&fromDate="+fromDate+"&toDate="+toDate+"&division="+division+"&district="+district+"&salesofficer="+salesofficer+"&salesofficertext="+salesofficertext+"&districttext="+districttext+"&divisiontext="+divisiontext);
        SubmissionLoader.hideFrom();
    }

</script>

<div class="main_container">
    <div class="content_container">
        <h1><g:message code="salesReportMT.create.label" default="DCR for Sales Officer"/></h1>
        <h3><g:message code="salesReportMT.Info.label" default="DCR for Sales Officer"/></h3>

        <div class="clear height5"></div>

        <div class="element_container_big">
            <div class="block-title">

                <div class='element-title'>
                    <g:message code='salesReportMT.fromDate.label' default='Year'/>
                    <span class="mendatory_field">*</span>
                </div>

                <div class='element-title'>
                    <g:message code='salesReportMT.toDate.label' default='Month'/>
                    <span class="mendatory_field">*</span>
                </div>

                <div class='element-title'>
                    <g:message code='salesReportMT.toDate.label' default='Sales Officer'/>
                    <span class="mendatory_field">*</span>
                </div>


                <div class="clear"></div>
            </div>

            <div class="block-input">

                <div class='element-input inputContainer'><g:textField class="validate[required] text-input datepicker"
                                                                       name="year" id="year" />
                </div>

                <div class='element-input inputContainer'><g:textField class="validate[required] text-input datepicker"
                                                                       name="month" id="month"/>
                </div>
                <div class='element-input inputContainer'>
                    <g:select name="salesOfficer" id="salesOfficer" class="validate[required]" from="${list}"
                              optionKey="id"
                              optionValue="name"
                              noSelection="['': '- Select SPO -']"/>
                </div>

                <div class="clear"></div>

                <table>
                    <tr>
                        <td>
                            <label class="txtright bold hight1x width1x">
                                <g:message code="salesReportMT.territory.label"
                                           default="Division :"/>
                            </label>
                        </td>
                        <td>
                            <g:select name="division" id="division" class="validate[required]" from="${com.bits.bdfp.common.Division.list()}" optionKey="id"
                                      optionValue="name"   style="width: 150px;"
                                      noSelection="['':'- Select Division -']"
                            />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label class="txtright bold hight1x width1x" >
                                <g:message code="salesReportMT.territory.label"
                                           default="District :"/>
                            </label>
                        </td>
                        <td>
                            <g:select name="district" id="district" class="validate[required]" from="${com.bits.bdfp.common.District.list()}" optionKey="id"
                                      optionValue="name"  style="width: 150px;"
                                      noSelection="['':'- Select District -']"
                            />
                        </td>
                    </tr>
                </table>
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
                                    onclick="dcrForSalesReport();"/></span>

    </div>

</div>



