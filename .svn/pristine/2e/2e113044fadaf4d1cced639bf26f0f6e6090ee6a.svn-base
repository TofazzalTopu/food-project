<%--
  Created by IntelliJ IDEA.
  User: mdalinaser.khan
  Date: 6/30/16
  Time: 11:39 AM
--%>

<%@ page import="com.bits.bdfp.util.ApplicationConstants; com.docu.security.ApplicationUser; com.docu.commons.CommonConstants; com.docu.commons.DateUtil; com.bits.bdfp.reports.SaleReportController" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title>Primary Sales Report (Zonal)</title>

<script type="text/javascript">
    $(document).ready(function () {
        $("#gFormPrimarySalesReport").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormPrimarySalesReport").validationEngine('attach');
        setDateRange('fromDate','toDate');
        $('#fromDate').mask('${CommonConstants.DATE_MASK_FORMAT}', {});
        $('#toDate').mask('${CommonConstants.DATE_MASK_FORMAT}', {});
    });
    function generateZonalSalesReport() {
        if (!$("#gFormPrimarySalesReport").validationEngine('validate')) {
            return false;
        }
        var zoneId = $("#zone").val();
        var zone = $("#zone option:selected").text();
        var searchCriteria = "Zone:" + zone;
        var customerCategoryId = $("#customerCategory").val();
        if(customerCategoryId){
            var customerCategory = $("#customerCategory option:selected").text();
            searchCriteria = searchCriteria + "  </br>" + "Customer Category:" + customerCategory;
        }

        var fromDate = $("#fromDate").val();
        var toDate = $("#toDate").val();

        window.open("${request.contextPath}/saleReport/rptZonalSaleReport?"
                + 'zoneId=' + zoneId + "&customerCategoryId=" + customerCategoryId
                + '&fromDate=' + fromDate + "&toDate=" + toDate + "&searchCriteria=" + searchCriteria
        );
    }
</script>

<div class="main_container">
    <div class="content_container">
        <h1>Primary Sales Report (Zonal)</h1>

        <h3>Primary Sales Report (Zonal)</h3>
        <form name='gFormPrimarySalesReport' id='gFormPrimarySalesReport'>
            <div class="element_container_big">
                <div class="block-title">
                    <div class='element-title'>
                        Zone
                        <span class="mendatory_field">*</span>
                    </div>

                    <div class='element-title'>
                        Customer Category
                    </div>

                    <div class="clear"></div>
                </div>

                <div class="block-input">
                    <div class='element-input inputContainer'>
                        <g:select class="validate[required]" name="zone" id="zone" from="${zoneList}"
                                  optionKey="id" optionValue="name" value="" />
                    </div>
                    <div class='element-input inputContainer '>
                        <g:select name="customerCategory" id="customerCategory" from="${customerCategoryList}"
                                  optionKey="id" optionValue="name" value="" noSelection="['':'All Primary Customer']"/>
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
                                    onclick="generateZonalSalesReport();"/></span>

    </div>

</div>