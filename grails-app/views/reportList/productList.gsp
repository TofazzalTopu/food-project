<%--
  Created by IntelliJ IDEA.
  User: sarahafreen.orny
  Date: 5/8/2016
  Time: 2:45 PM
--%>


<%@ page import="com.bits.bdfp.setup.salestarget.ReportListController" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title><g:message code="ttumFileExport.create.label" default="Product List"/></title>

<script>

    $(document).ready(function () {
        $('#territory').focus();
    });

    function productList() {
                    SubmissionLoader.showTo();
                    window.open("${resource(dir:'reportList', file:'rptProductList')}?format=PDF&re="+3);
                    SubmissionLoader.hideFrom();
    }

</script>

<div class="main_container">
    <div class="content_container">
        <h1><g:message code="salesReportMT.create.label" default="Product List"/></h1>

        <h3><g:message code="salesReportMT.Info.label" default="Product List"/></h3>

        <div class="clear height5"></div>

        <div class="element_container_big">
            <div class="block-input">
                <div class="clear"></div>
            </div>
        </div>

        <div class="clear height5"></div>

        <div id="dialog-confirm-salessummary" title="Delete alert" style="display: none;">
            <p><span class="ui-icon ui-icon-alert"
                     style="float:left; margin:0 7px 20px 0;"></span> Report will be printed. Want to continue?</p>
        </div>

    </div>

    <div class="buttons" style="margin-left:10px;">
        <span class="button"><input type="button" name="print-button" id="dialog-confirm-workOrder"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="Generate Report"
                                    onclick="productList();"/></span>
    </div>

</div>