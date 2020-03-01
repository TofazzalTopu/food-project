<%--
  Created by IntelliJ IDEA.
  User: sarahafreen.orny
  Date: 5/8/2016
  Time: 2:45 PM
--%>


<%@ page import="com.bits.bdfp.setup.salestarget.ReportListController" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title><g:message code="ttumFileExport.create.label" default="Customer Information"/></title>

<script>

    function customerList() {

        var customerStatus = $('input[name=customerStatus]:checked').val();
        var categoryId = $('#category').val();
        var category = $("#category option:selected").text();

        SubmissionLoader.showTo();
        window.open("${resource(dir:'reportList', file:'rptCustomerList')}?format=PDF&category=" + category + "&customerStatus=" + customerStatus + "&categoryId=" + categoryId );
        SubmissionLoader.hideFrom();
    }

</script>

<div class="main_container">
    <div class="content_container">
        <h1><g:message code="salesReportMT.create.label" default="Customer Information"/></h1>

        <h3><g:message code="salesReportMT.Info.label" default="Customer Information"/></h3>

        <div class="clear height5"></div>

        <div class="element_container_big">
            <div class="block-title">
                <div class='element-title'>
                    <g:message code='salesReportMT.toDate.label' default='Category'/>
                </div>
                <div class="clear"></div>
            </div>

            <div class="block-input">

                <div class='element-input inputContainer'><g:select name="category" id="category"
                                                                    class="validate[required]" from="${categoryList}"
                                                                    optionKey="id"
                                                                    optionValue="name"
                                                                    noSelection="['': 'All Category']"/>
                </div>

                <div class="clear"></div>
                <table>
                    <tr>
                        <td>
                            <label for="date" class="txtright bold hight1x width1x" style="width: 80px;padding-left: 10px"><g:message
                                    code='salesReportMT.toDate.label'
                                    default="Status"/>
                                <span class="mendatory_field">*</span>

                            </label>
                        </td>
                        <td>
                            <g:radio name="customerStatus" id="active" value="ACTIVE" checked="checked"/> Active <br/>
                        </td>
                        <td>
                            <g:radio name="customerStatus" id="inactive" value="INACTIVE"/> Inactive <br/>
                        </td>
                    </tr>
                </table>

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
                                    onclick="customerList();"/></span>

    </div>

</div>