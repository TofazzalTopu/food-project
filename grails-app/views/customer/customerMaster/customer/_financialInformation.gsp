<table>
    <tr>
        <td>
            <label class="txtright bold hight1x width1x" style="width: 160px;">
                <g:message code="customerMaster.customerPaymentType.label" default="Customer Payment Type"/>
                <span class="mendatory_field">*</span>
            </label>
        </td>
        <td class="element-input inputContainer">
            <g:select class="validate[required]" name="customerPaymentType.id"
                      from="${com.bits.bdfp.customer.CustomerPaymentType.list()}"
                      id="customerPaymentType" noSelection="['': 'Please Select']"
                      optionKey="id" value="${customerMaster?.customerPaymentType?.id}"
                      style="width: 300px"/>
        </td>
    </tr>
    <tr id="cusCreditLimit">
        <td>
            <label class="txtright bold hight1x width1x" style="width: 160px;">
                <g:message code="customerMaster.customerCreditLimit.label" default="Customer Credit Limit"/>
                <span class="mendatory_field">*</span>
            </label>
        </td>
        <td class="element-input inputContainer">
            <g:textField class="validate[required]" name="customerCreditLimit"
                         value="${fieldValue(bean: customerMaster, field: 'customerCreditLimit')}"
                         size="47"/>
        </td>
    </tr>
    <tr id="creditPeriodInD">
        <td>
            <label class="txtright bold hight1x width1x" style="width: 160px;">
                <g:message code="customerMaster.creditPeriodInDays.label" default="Credit Period In Days"/>
                <span class="mendatory_field">*</span>
            </label>
        </td>
        <td class="element-input inputContainer">
            <g:textField class="validate[required]" name="creditPeriodInDays"
                         value="${fieldValue(bean: customerMaster, field: 'creditPeriodInDays')}"
                         size="47"/>
        </td>
    </tr>

    <tr>
        <td>
            <label class="txtright bold hight1x width1x" style="width: 160px;">
                <g:message code="customerMaster.isImmediate.label" default="Is Immediate"/>
            </label>
        </td>
        <td class="element-input inputContainer">
            <g:checkBox name="isImmediate" value="${customerMaster?.isImmediate}"/>
        </td>
    </tr>
</table>