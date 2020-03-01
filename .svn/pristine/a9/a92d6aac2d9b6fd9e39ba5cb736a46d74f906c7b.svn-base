<%--
  Created by IntelliJ IDEA.
  User: mdalinaser.khan
  Date: 6/30/16
  Time: 11:39 AM
--%>

<%@ page import="com.bits.bdfp.util.ApplicationConstants; com.docu.security.ApplicationUser; com.docu.commons.CommonConstants; com.docu.commons.DateUtil; com.bits.bdfp.reports.SaleReportController" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title>Branch Sales and Collection Report</title>

<script type="text/javascript">
    $(document).ready(function () {
        $("#gFormBranchSalesReport").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormBranchSalesReport").validationEngine('attach');
        setDateRange('fromDate','toDate');
        $('#fromDate').mask('${CommonConstants.DATE_MASK_FORMAT}', {});
        $('#toDate').mask('${CommonConstants.DATE_MASK_FORMAT}', {});
    });
    function generateBranchSalesReport() {
        if (!$("#gFormBranchSalesReport").validationEngine('validate')) {
            return false;
        }
        var distributionPointId = $("#distributionPoint").val();
        var distributionPoint = $("#distributionPoint option:selected").text();
        var customerCategoryId = $("#customerCategory").val();
        var customerCategory = $("#customerCategory option:selected").text();
        var fromDate = $("#fromDate").val();
        var toDate = $("#toDate").val();
        var searchCriteria = "Distribution Point:" + distributionPoint + "  </br>" + "Customer Category:" + customerCategory;

        window.open("${request.contextPath}/saleReport/rptBranchSaleReport?"
                + 'distributionPointId=' + distributionPointId + "&customerCategoryId=" + customerCategoryId
                + '&fromDate=' + fromDate + "&toDate=" + toDate
               // + '&fromDate=' + fromDate + "&toDate=" + toDate + "&searchCriteria=" + searchCriteria
        );
    }
</script>

<div class="main_container">
    <div class="content_container">
        <h1>Branch Sales and Collection Report</h1>

        <h3>Branch Sales & Collection Report</h3>
        <form name='gFormBranchSalesReport' id='gFormBranchSalesReport'>
            <div class="element_container_big">
                <div class="block-title">
                    <div class='element-title'>
                        DP
                        <span class="mendatory_field">*</span>
                    </div>

                    <div class='element-title'>
                        Customer Category
                        <span class="mendatory_field">*</span>
                    </div>

                    <div class="clear"></div>
                </div>

                <div class="block-input">
                    <div class='element-input inputContainer'>
                        <g:select class="validate[required]" name="distributionPoint" id="distributionPoint" from="${distributionPointList}"
                                                                           optionKey="id" optionValue="name" value="" />
                    </div>
                    <div class='element-input inputContainer '>
                        <g:select class="validate[required]" name="customerCategory" id="customerCategory" from="${customerCategoryList}"
                                  optionKey="id" optionValue="name" value=""/>
                    </div>

                    <div class="clear"></div>
                </div>
            </div>

            <div class="element_container_big">
                <div class="block-title">
                    <div class='element-title'>
                        From Date:
                        <span class="mendatory_field">*</span>
                    </div>

                    <div class='element-title'>
                        To:
                        <span class="mendatory_field">*</span>
                    </div>

                    <div class="clear"></div>
                </div>

                <div class="block-input">
                    <div class='element-input inputContainer'><g:textField class="validate[required] text-input datepicker"
                                                                           name="fromDate" id="fromDate"
                                                                           value="" /></div>
                    <div class='element-input inputContainer'><g:textField class="validate[required] text-input datepicker"
                                                                           name="toDate" id="toDate"
                                                                           value="" /></div>

                    <div class="clear"></div>
                </div>
            </div>
        </form>

    </div>

    <div class="buttons" style="margin-left:10px;">
        <span class="button"><input type="button" name="print-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="Generate Report"
                                    onclick="generateBranchSalesReport();"/></span>

    </div>

</div>