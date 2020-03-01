<%--
  Created by IntelliJ IDEA.
  User: sarahafreen.orny
  Date: 8/7/2016
  Time: 4:49 PM
--%>

<%@ page import="com.bits.bdfp.inventory.demandorder.PrimaryDemandOrder" contentType="text/html;charset=UTF-8" %>

<style>
.ffb-office-container {
    top: 47px !important;
    left: 10px !important;
    width: 352px !important;
}

.dataTables_scrollHeadInner {
    width: 100% !important;
}

.display {
    width: 100% !important;
}
</style>

<script type="text/javascript">
    $(document).ready(function () {
        $("#forceProcess-button").click(function(){
            $("#button").text("Y");
            $.fancybox.close();
        });

        %{--alert(${params.text});--}%
    });

</script>

<div id="tab_history_list">
    <h3>Primary Demand Order Eligibility</h3>
    <div class="element_row_content_container lightColorbg pad_bot0">
<div class="main_container" style="overflow: hidden;">
    <table cellpadding="0" cellspacing="0" border="0" class="display" id="customer_${customerId}">
        <thead>
        <tr class="element_row_content_container inputContainer lightColorbg pad_bot0">
            <td>
                <label class="txtright bold hight1x width120" style="padding-left: 0px">
                   Order Amount:
                </label>
            </td>
            <td>
                <div class='element-input inputContainer'>
                    <g:textField class="amount alin_right width100" name="orderAmount" id="orderAmount" value="${params.orderValue}"
                                 readonly="readonly" disabled="disabled"/>
                </div>
            </td>
            <td>
                <label class="txtright bold hight1x width1x">
                    Total Receivable:
                </label>
            </td>
            <td>
                <div class='element-input inputContainer'>
                    <g:textField class="amount alin_right width100" name="receivableAmount" id="receivableAmount"
                                 value="${params.customerRecivable}" readonly="readonly" disabled="disabled"/>
                </div>
            </td>
        </tr>

        <tr class="element_row_content_container inputContainer lightColorbg pad_bot0">

            <td>
                <label class="txtright bold hight1x width120">
                    Security Deposit:
                </label>
            </td>
            <td>
                <div class='element-input inputContainer'>
                    <g:textField class="amount alin_right width100" name="securityAmount" id="securityAmount" value="${params.customerSecurityDeposit}"
                                 readonly="readonly" disabled="disabled"/>
                </div>
            </td>

            <td>
                <label class="txtright bold hight1x width1x">
                    Customer Priority:
                </label>
            </td>
            <td>
                <div class='element-input inputContainer'>
                    <g:textField class="width100" name="priority" id="priority" value="${params.priority}" readonly="readonly"
                                 disabled="disabled"/>
                </div>
            </td>


        </tr>
        <tr class="element_row_content_container inputContainer lightColorbg pad_bot0">
            <td>
                <label class="txtright bold hight1x width120">
                    Credit Limit:
                </label>
            </td>
            <td>
                <div class='element-input inputContainer'>
                    <g:textField class="amount alin_right width100" name="limit" id="limit" value="${params.limit}" readonly="readonly"
                                 disabled="disabled"/>
                </div>
            </td>
        </tr>

        </thead>
        <tbody></tbody>
    </table>

    <g:if test = "${params.text == 'Y'}">
    <script type="text/javascript">
        $(document).ready(function () {
            $("#forceProcess-button").hide();
        });

    </script>
    </g:if>
    <g:else >
        <script type="text/javascript">
            $(document).ready(function () {
                $("#forceProcess-button").show();
            });

        </script>
    </g:else>

        <div class="buttons" id="forceProcess" style="padding-top: 10px;">
            <span class="button"><input type="button" name="forceProcess-button" id="forceProcess-button"
                                        class="ui-button ui-widget ui-state-default ui-corner-all"
                                        value="Force Process"/></span>
        </div>

</div>
</div>
</div>