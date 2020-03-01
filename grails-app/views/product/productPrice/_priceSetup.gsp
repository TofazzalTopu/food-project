<%@ page import="com.bits.bdfp.util.ApplicationConstants" %>
<script type="text/javascript">
    $(document).ready(function () {
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }
        $(".hidden").hide();
        $(".amount").format({precision: 2, allow_negative: false, autofix: true});
        var productPricingTypeId = $("#productPricingType").val();
        if(productPricingTypeId == ${ApplicationConstants.PRODUCT_PRICING_TYPE_STANDARD_ID}) {
            $(".negotiated").hide();
            $(".negotiated input:text").each(function () {
                this.value = "";
            });
        } else if(productPricingTypeId == ${ApplicationConstants.PRODUCT_PRICING_TYPE_NEGOTIATED_ID}) {
            $(".negotiated").show();
        }
    });
    function addProductData(data) {
        var tempId = "";
        $(".baseData").each(function () {
            if(this.value){
                data.push({'name': this.id, 'value': this.value});
                var idValues = this.id.split("_");
                if(idValues.length > 2){
                    // Set Vat Percent
                    tempId = 'vatPercent_' + idValues[1] + "_" + idValues[2];
                    data.push({'name':tempId, 'value': Number($("#" + tempId).val())});
                    // Set Vat Amount
                    tempId = 'vatAmount_' + idValues[1] + "_" + idValues[2];
                    data.push({'name':tempId, 'value': Number($("#" + tempId).val())});

                }
            }
        });
        return data
    }
    function calculate(elementId, postfixId, elementType){
        var basePrice = Number($("#basePrice_" + postfixId).val());
        var vatPercent = Number($("#vatPercent_" + postfixId).val());
        var vatAmount = Number($("#vatAmount_" + postfixId).val());
        if(elementType == "vatPercent"){
            if(vatPercent > 0.00){
                vatAmount = 0.00;
                $("#vatAmount_" + postfixId).val("");
                $("#vatAmount_" + postfixId).attr("readonly", "readonly");
            }else{
                $("#vatAmount_" + postfixId).removeAttr("readonly");
            }
        }else if(elementType == "vatAmount"){
            if(vatAmount > 0.00){
                vatPercent = 0.00;
                $("#vatPercent_" + postfixId).val("");
                $("#vatPercent_" + postfixId).attr("readonly", "readonly");
            }else{
                $("#vatPercent_" + postfixId).removeAttr("readonly");
            }
        }
        if(basePrice > 0.00){
            var calculatedVat = (basePrice * vatPercent)/100;
            basePrice = basePrice + Number(calculatedVat.toFixed(2)) + vatAmount;
            $("#actualPrice_" + postfixId).val(basePrice.toFixed(2));
        }else{
            $("#actualPrice_" + postfixId).val('');
        }
    }
</script>

<div>
    <g:set var="sl" value="${1}"/>
    <g:if test="${productList}">
        <label>Enter Price:</label>
        <br/>
        <table class="simple-table-css">
            <tr>
                <td style="font-size:12px;width:5px;">SL</td>
                <td class="hidden">ID</td>
                <td class="width100">Item Code</td>
                <td class="width100">Item Name</td>
                <g:if test="${categoryList}">
                    <g:each in="${categoryList}" var="category">
                        <td class="hidden">CategoryID</td>
                        <g:if test="${category.id == ApplicationConstants.PRICING_CATEGORY_NEGOTIATED_ID}">
                            <td class="width40 negotiated">${category.short_name} Base</td>
                            <td class="width30 negotiated">VAT%</td>
                            <td class="width30 negotiated">VAT in Value</td>
                            <td class="width40 negotiated">Actual ${category.short_name}</td>
                        </g:if>
                        <g:else>
                            <td class="width40">${category.short_name} Base</td>
                            <td class="width30">VAT%</td>
                            <td class="width30">VAT in Value</td>
                            <td class="width40">Actual ${category.short_name}</td>
                        </g:else>
                    </g:each>
                </g:if>
            </tr>
            <g:each in="${productList}" status="i" var="product">
                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                    <td><g:set var="sl" value="${sl + 1}"/>${sl}</td>
                    <td class="hidden"><g:hiddenField class="product-id" name="id_${product?.id}" id="id_${product?.id}"
                                                      value="${product?.id}"/>${product?.id}</td>
                    <td class="width100"><g:hiddenField name="code_${product?.id}" id="code_${product?.id}"
                                                        value="${product?.code}"/>${product?.code}</td>
                    <td class="width100"><g:hiddenField name="name_${product?.id}" id="name_${product?.id}"
                                                        value="${product?.name}"/>${product?.name}</td>
                    <g:if test="${categoryList}">
                        <g:each in="${categoryList}" var="category">
                            <td nowrap="nowrap" class="hidden">${category.id}</td>
                            <g:if test="${category.id == ApplicationConstants.PRICING_CATEGORY_NEGOTIATED_ID}">
                                <td nowrap="nowrap" class="negotiated"><g:textField class="width40 alin_right amount baseData negotiated"
                                                                                    name="basePrice_${category.id}_${product.id}"
                                                                                    id="basePrice_${category.id}_${product.id}" value=""
                                                                                    maxlength="9" onblur="calculate(this.id, '${category.id}_${product.id}', 'basePrice')"/></td>
                                <td nowrap="nowrap" class="negotiated"><g:textField class="width30 amount negotiated"
                                                                                    name="vatPercent_${category.id}_${product.id}"
                                                                                    id="vatPercent_${category.id}_${product.id}" value=""
                                                                                    maxlength="5" onblur="calculate(this.id, '${category.id}_${product.id}', 'vatPercent')"/></td>
                                <td nowrap="nowrap" class="negotiated"><g:textField class="width30 alin_right amount negotiated"
                                                                                    name="vatAmount_${category.id}_${product.id}"
                                                                                    id="vatAmount_${category.id}_${product.id}" value=""
                                                                                    maxlength="8" onblur="calculate(this.id, '${category.id}_${product.id}', 'vatAmount')"/></td>
                                <td nowrap="nowrap" class="negotiated"><g:textField class="width40 alin_right amount negotiated"
                                                                                    name="actualPrice_${category.id}_${product.id}"
                                                                                    id="actualPrice_${category.id}_${product.id}" value=""
                                                                                    readonly="readonly"/></td>
                            </g:if>
                            <g:else>
                                <td nowrap="nowrap"><g:textField class="width40 alin_right amount baseData"
                                                                 name="basePrice_${category.id}_${product.id}"
                                                                 id="basePrice_${category.id}_${product.id}" value=""
                                                                 maxlength="9" onblur="calculate(this.id, '${category.id}_${product.id}', 'basePrice')"/></td>
                                <td nowrap="nowrap"><g:textField class="width30 amount"
                                                                 name="vatPercent_${category.id}_${product.id}"
                                                                 id="vatPercent_${category.id}_${product.id}" value=""
                                                                 maxlength="5" onblur="calculate(this.id, '${category.id}_${product.id}', 'vatPercent')"/></td>
                                <td nowrap="nowrap"><g:textField class="width30 alin_right amount"
                                                                 name="vatAmount_${category.id}_${product.id}"
                                                                 id="vatAmount_${category.id}_${product.id}" value=""
                                                                 maxlength="8" onblur="calculate(this.id, '${category.id}_${product.id}', 'vatAmount')"/></td>
                                <td nowrap="nowrap"><g:textField class="width40 alin_right amount"
                                                                 name="actualPrice_${category.id}_${product.id}"
                                                                 id="actualPrice_${category.id}_${product.id}" value=""
                                                                 readonly="readonly"/></td>
                            </g:else>
                        </g:each>
                    </g:if>
                </tr>
            </g:each>
        </table>
    </g:if>
</div>

