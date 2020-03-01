<%@ page import="com.bits.bdfp.customer.CustomerLevel; com.bits.bdfp.inventory.product.PricingCategory; com.bits.bdfp.customer.CustomerContactType; com.bits.bdfp.customer.CustomerSalesChannel; com.bits.bdfp.customer.CustomerCategory" %>
<table style="float: left">
    <tr>
        <td>
            <label class="txtright bold hight1x width1x" style="width: 160px;">
                <g:message code="customerMaster.name.label" default="Name"/>
                <span class="mendatory_field">*</span>
            </label>
        </td>
        <td class="element-input inputContainer">
            <g:textField class="validate[required]" name="name" value="${customerMaster?.name}"
                         size="47"
                         onchange="setName();"/>
        </td>
    </tr>

    <tr>
        <td>
            <label class="txtright bold hight1x width1x" style="width: 160px;">
                <g:message code="customerMaster.alternativeName.label" default="Alternative Name"/>
            </label>
        </td>
        <td class="element-input inputContainer">
            <g:textField name="alternativeName" value="${customerMaster?.alternativeName}"
                         size="47"/>
        </td>
    </tr>

    <tr>
        <td>
            <label class="txtright bold hight1x width1x" style="width: 160px;">
                <g:message code="customerMaster.legacyId.label" default="Legacy ID"/>
                <span class="mendatory_field">*</span>
            </label>
        </td>
        <td class="element-input inputContainer">
            <g:textField class="validate[required]" name="legacyId" value="${customerMaster?.legacyId}"
                         size="47"/>
        </td>
    </tr>

    <tr>
        <td>
            <label class="txtright bold hight1x width1x" style="width: 160px;">
                <g:message code="customerMaster.customerType.label" default="Customer Type"/>
                <span class="mendatory_field">*</span>
            </label>
        </td>
        <td class="element-input inputContainer">
            <g:select class="validate[required]" name="customerType.id" id="customerType"
                      from="${com.bits.bdfp.customer.CustomerType.list()}" optionKey="id"
                      value="${customerMaster?.customerType?.id}"
                      noSelection="['': 'Please Select']"
                      style="width: 300px"/>
        </td>
    </tr>

    <tr>
        <td>
            <label class="txtright bold hight1x width1x" style="width: 160px;">
                <g:message code="customerMaster.category.label" default="Customer Category"/>
                <span class="mendatory_field">*</span>
            </label>
        </td>
        <td class="element-input inputContainer">
            <g:select class="validate[required]" name="category.id" id="category"
                      from="${CustomerCategory.list()}" optionKey="id"
                      value="${customerMaster?.category?.id}"
                      noSelection="['': 'Please Select']"
                      style="width: 300px"/>
        </td>
    </tr>

    <tr>
        <td>
            <label class="txtright bold hight1x width1x" style="width: 160px;">
                <g:message code="customerMaster.pricingCategory.label" default="Partner Type"/>
                <span class="mendatory_field">*</span>
            </label>
        </td>
        <td class="element-input inputContainer">
            <g:select class="validate[required]" name="pricingCategory.id" id="pricingCategory"
                      from="${PricingCategory.list()}"
                      optionKey="id" value="${customerMaster?.pricingCategory?.id}"
                      noSelection="['': 'Please Select']"
                      style="width: 300px"/>
        </td>
    </tr>


</table>

    <table style="float:left;padding-left: 50px ">

        <tr>
            <td>
                <label class="txtright bold hight1x width1x" style="width: 160px;">
                    <g:message code="customerMaster.customerPriority.label" default="Customer Priority"/>
                    <span class="mendatory_field">*</span>
                </label>
            </td>
            <td class="element-input inputContainer">
                <g:select class="validate[required]" name="customerPriority.id" id="customerPriority"
                          from="${com.bits.bdfp.customer.CustomerPriority.list()}"
                          optionKey="id" value="${customerMaster?.customerPriority?.id}"
                          noSelection="['': 'Please Select']"
                          style="width: 300px"/>
            </td>
        </tr>

        <tr>
            <td>
                <label class="txtright bold hight1x width1x" style="width: 160px;">
                    <g:message code="customerMaster.customerSalesChannel.label" default="Customer Sales Channel"/>
                    <span class="mendatory_field">*</span>
                </label>
            </td>
            <td class="element-input inputContainer">
                <g:select class="validate[required]" name="customerSalesChannel.id" id="customerSalesChannel"
                          from="${CustomerSalesChannel.list()}" optionKey="id"
                          value="${customerMaster?.customerSalesChannel?.id}"
                          noSelection="['': 'Please Select']"
                          style="width: 300px"/>
            </td>
        </tr>

        <tr>
            <td>
                <label class="txtright bold hight1x width1x" style="width: 160px;">
                    <g:message code="customerMaster.contactPerson.label" default="Contact Person"/>
                    <span class="mendatory_field">*</span>
                </label>
            </td>
            <td class="element-input inputContainer">
                <g:textField class="validate[required]" name="contactPerson" value="${customerMaster?.contactPerson}"
                             size="47"/>
            </td>
        </tr>

        <tr>
            <td>
                <label class="txtright bold hight1x width1x" style="width: 160px;">
                    <g:message code="customerMaster.contactNo.label" default="Customer Contact No"/>
                    <span class="mendatory_field">*</span>
                </label>
            </td>
            <td class="element-input inputContainer">
                <g:textField class="validate[required]" name="contactNo" value="${customerMaster?.contactNo}"
                             size="47"/>
            </td>
        </tr>

        <tr>
            <td>
                <label class="txtright bold hight1x width1x" style="width: 160px;">
                    <g:message code="customerMaster.customerContactType.label" default="Customer Contact Type"/>
                    <span class="mendatory_field">*</span>
                </label>
            </td>
            <td class="element-input inputContainer">
                <g:select class="validate[required]" name="customerContactType.id" id="customerContactType"
                          from="${CustomerContactType.list()}"
                          optionKey="id" value="${customerMaster?.customerContactType?.id}"
                          noSelection="['': 'Please Select']"
                          style="width: 300px"/>
            </td>
        </tr>

        <tr>
            <td>
                <label class="txtright bold hight1x width1x" style="width: 160px;">
                    Customer Level
                    <span class="mendatory_field">*</span>
                </label>
            </td>
            <td class="element-input inputContainer">
                <g:select class="validate[required]" name="customerLevel" id="customerLevel"
                          from="${CustomerLevel.values()}"
                          value="${customerMaster?.customerLevel}"
                          noSelection="['': 'Please Select']"
                          style="width: 300px"/>
            </td>
        </tr>
    </table>
