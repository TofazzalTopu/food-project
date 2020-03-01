<%--
  Created by IntelliJ IDEA.
  User: sarahafreen.orny
  Date: 5/8/2016
  Time: 2:45 PM
--%>


<%@ page import="com.bits.bdfp.setup.salestarget.ReportListController" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title><g:message code="ttumFileExport.create.label" default="Employee List"/></title>

<script>

    $(document).ready(function () {
        $('#territory').focus();

    });


    function employeeList() {

        SubmissionLoader.showTo();
        window.open("${resource(dir:'reportList', file:'rptEmployeeList')}?format=CSV&re="+1);
        %{--window.open("${resource(dir:'ttumExport', file:'rptTTUMGenerate')}?format=CSV&fromDate="+$('#fromDate').val()+ '&toDate=' + $("#toDate").val() );--}%

        SubmissionLoader.hideFrom();

        %{--$("#dialog-confirm-salesMT").dialog({--}%
        %{--resizable: false,--}%
        %{--height: 150,--}%
        %{--modal: true,--}%
        %{--title: 'Confirm Dialog',--}%
        %{--buttons: {--}%
        %{--Ok: function () {--}%
        %{--$(this).dialog('close');--}%
        %{--SubmissionLoader.showTo();--}%
        %{--window.open("${resource(dir:'reportList', file:'rptEmployeeList')}?format=PDF&re="+1);--}%
        %{--SubmissionLoader.hideFrom();--}%
        %{--},--}%
        %{--Cancel: function () {--}%
        %{--$(this).dialog('close');--}%
        %{--}--}%
        %{--}--}%
        %{--});--}%
    }

</script>

<div class="main_container">
    <div class="content_container">
        <h1><g:message code="salesReportMT.create.label" default="Employee List"/></h1>

        <h3><g:message code="salesReportMT.Info.label" default="Employee List"/></h3>

        <div class="clear height5"></div>

        <div class="element_container_big">

            <div class="block-input">

                <div class="clear"></div>

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
                                    onclick="employeeList();"/></span>

    </div>

</div>