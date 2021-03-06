<%@ page import="com.bits.bdfp.inventory.product.FinishProduct" %>
<style>

/*.ui-jqgrid .ui-jqgrid-htable*/
/*{*/
    /*table-layout: auto;*/
/*}*/

#productTypeTable {
    border-collapse: collapse;
}

#productTypeTable tr {
    border-bottom: 2px solid darkgray;
    border-right: 2px solid darkgray;
}

#productTypeTable th {
    border: 2px solid darkgray;
}

#productTypeTable td {
    border: 2px solid darkgray;
}

.productTypeDiv {
    display: none;
}

.customLabel {
    background-color: #d7dfe7;
    color: #000000;
    display: block;
    float: left;
    font-size: 11px;
    font-weight: bold;
    height: 17px;
    margin: 0;
    padding: 5px 10px 0;
    width: 160px;
}
.customLabel1 {
    background-color: #d7dfe7;
    color: #000000;
    display: block;
    float: left;
    font-size: 11px;
    font-weight: bold;
    height: 17px;
    margin-left: 4px;
    padding: 5px 10px 0;
    width: 160px;
}
.customInput {
    height: 18px;
    width: 160px;
}
</style>
<script type="text/javascript">
    function populateOtherSelect(enId) {
        $.ajax({
            type: "POST",
            url: "${resource(dir:'finishProduct', file:'businessUnitForEnterprise')}?id=" + enId,
            beforeSend: function (jqXHR, settings) {
                $("#loader_icon").show();
            },
            success: function (data) {
                var businessUnitData = data.businessUnitList;
                var productCategoryData = data.categoryList;
                var masterProductData = data.masterProductList;
                var mainProductData = data.mainProductList;
                var productTypeData = data.productTypeList;
                var measuerUnitData = data.measureUnitList;

                var businessUnitMap = new Object(); // or var map = {};
                businessUnitMap["results"] = businessUnitData;
                businessUnitMap["total"] = businessUnitData.length;
                $("#businessUnitList").empty();
                $("#businessUnitList").flexbox(businessUnitMap, {
                    watermark: "Select Business Unit",
                    width: 140,
                    onSelect: function () {
                        $("#businessUnitConfiguration").val($('#businessUnitList_hidden').val());

                    }
                });


                var productCategoryMap = new Object(); // or var map = {};
                productCategoryMap["results"] = productCategoryData;
                productCategoryMap["total"] = productCategoryData.length;
                $("#productCategoryList").empty();
                $("#productCategoryList").flexbox(productCategoryMap, {
                    watermark: "Select Category",
                    width: 140,
                    onSelect: function () {
                        $("#productCategory").val($('#productCategoryList_hidden').val());

                    }
                });


                var masterProductMap = new Object(); // or var map = {};
                masterProductMap["results"] = masterProductData;
                masterProductMap["total"] = masterProductData.length;
                $("#masterProductList").empty();
                $("#masterProductList").flexbox(masterProductMap, {
                    watermark: "Select Category",
                    width: 140,
                    onSelect: function () {
                        $("#masterProduct").val($('#masterProductList_hidden').val());

                    }
                });


                var masterProductMap = new Object(); // or var map = {};
                masterProductMap["results"] = mainProductData;
                masterProductMap["total"] = mainProductData.length;
                $("#mainProductList").empty();
                $("#mainProductList").flexbox(masterProductMap, {
                    watermark: "Select Category",
                    width: 140,
                    onSelect: function () {
                        $("#mainProduct").val($('#mainProductList_hidden').val());

                    }
                });


                options = '<option value="">Select Unit</option>';
                $.each(productTypeData, function (key, val) {
                    options += '<option value="' + val.id + '">' + val.name + '</option>';
//                    console.log(val)
                });
                $("#productType").html(options);

                options = '<option value="">Select Unit</option>';
                $.each(measuerUnitData, function (key, val) {
                    options += '<option value="' + val.id + '">' + val.name + '</option>';
//                    console.log(val)
                });
                $("#measureUnitConfiguration").html(options);


            },
            complete: function (data) {
                setTimeout(function () {
                    setEnterpirseVal(enId);
                }, 1000);
                $("#loader_icon").hide();
            },
            dataType: 'json'
        });

    }

    function setEnterpirseVal(enId) {
        $("#enterpriseConfiguration").val(enId);
    }

</script>

<form name='gFormFinishProduct' id='gFormFinishProduct'>
    <g:hiddenField name="id" value="${finishProduct?.id}"/>
    <g:hiddenField name="version" value="${finishProduct?.version}"/>
    <g:hiddenField name="enterpriseId" id="enterpriseId" value=""/>
    <g:hiddenField name="enterpriseConfiguration.id"  id="enterpriseConfiguration" value=""/>
    <div id="remote-content-finishProduct"></div>
    <table>
        <tr>
            <table id="productTypeTable">

                <tr>
                    <td>
                        <label style="padding-right: 5px;" for='enterpriseConfiguration' class='customLabel'>
                            Enterprise Name
                            <span class="mendatory_field"> * </span>
                        </label>

                        <div class='element-input inputContainer'>
                            <g:if test="${result}">

                                <div id="enterpriselist"></div>
                                <script type="text/javascript">
                                    jQuery(document).ready(function () {
                                        $("#enterpriselist").empty();
                                        $("#enterpriselist").flexbox(${result}, {
                                            watermark: "Select Enterprise",
                                            width: 140,
                                            onSelect: function () {
                                                $("#enterpriseConfiguration").val($('#enterpriselist_hidden').val());
                                                businessUnitList($('#enterpriselist_hidden').val());
                                                loadCoaGroups($('#enterpriselist_hidden').val());
                                            }

                                        });
                                        $('#enterpriselist_input').addClass("validate[required]");

                                        $('#enterpriselist_input').blur(function () {
                                            if ($('#enterpriselist_input').val() == '') {
                                                $("#enterpriseConfiguration").val("");
                                                $("#coaGroup").html("");
                                                $("#chartOfAccounts").html("");
                                            }
                                        });

                                    });
                                </script>

                            </g:if>
                        </div>

                    </td>

                    <td><label style="padding-right: 5px;" for='businessUnitConfiguration' class='customLabel'><g:message
                            code='finishProduct.businessUnitConfiguration.label' default='Business Unit'/></label>

                        <div class='element-input inputContainer'>
                            <div id="businessUnitList"></div>

                            <g:hiddenField name="businessUnitConfiguration.id" id="businessUnitConfiguration" value=""/>
                        </div>
                    </td>
                    <td>
                        <label style="padding-right: 5px;" for='productCategory' class='customLabel'>
                            <g:message code='finishProduct.productCategory.label' default='Product Category'/>
                            <span class="mendatory_field"> * </span>
                        </label>

                        <div class='element-input inputContainer'>
                            <div id="productCategoryList"></div>
                            <g:hiddenField name="productCategory.id" id="productCategory" value=""/>
                        </div>
                    </td>
                    <td>
                        <label style="padding-right: 5px;" for='masterProduct' class='customLabel'>
                            <g:message code='finishProduct.masterProduct.label' default='Master Product'/>
                            <span class="mendatory_field"> * </span>
                        </label>

                        <div class='element-input inputContainer'>
                            <div id="masterProductList"></div>
                            <g:hiddenField name="masterProduct.id" id="masterProduct" value=""/>
                        </div>
                    </td>
                    <td>
                        <label style="padding-right: 5px;" for='mainProduct' class='customLabel'>
                            <g:message code='finishProduct.mainProduct.label' default='Main Product'/>
                            <span class="mendatory_field"> * </span>
                        </label>

                        <div class='element-input inputContainer'>
                            <div id="mainProductList"></div>
                            <g:hiddenField name="mainProduct.id" id="mainProduct" value=""/>
                        </div>

                    </td>

                    <td>
                        <label style="padding-right: 5px;" for='productType' class='customLabel'>
                            <g:message code='finishProduct.productType.label' default='Product Type'/>
                            <span class="mendatory_field"> * </span>
                        </label>

                        <div class='element-input inputContainer'>
                            <g:select name="productType.id" id="productType"
                                      class="validate[required]"
                                      optionKey="id"
                                      value=""
                                      noSelection="['': 'Select One']"/>
                        </div>

                    </td>
                </tr>

            </table>

        </tr>


        %{--end product type selection table--}%
        %{--start product code info--}%

        <tr>
            <td>
                <div id="Salable" class="productTypeDiv">
                    <div>
                        <div class="element-content-form-elements-product">
                            <label style="padding-right: 5px;" for='outsideSalesCode' class='bold txtalgnrght'><g:message
                                    code='finishProduct.outsideSalesCode.label' default='Outside Sales Code'/></label>

                            <div class='element-input inputContainer'>
                                <g:textField name="outsideSalesCode" value=""/>

                            </div>
                        </div>


                        <div class="element-content-form-elements-product">
                            <label style="padding-right: 5px;" for='exportSalesCode' class='bold txtalgnrght width150'>
                                Export Sales Code
                            </label>

                            <div class='element-input inputContainer'>
                                <g:textField name="exportSalesCode" value=""/>

                            </div>
                        </div>

                    </div>

                    <div>
                        <div class="element-content-form-elements-product">
                            <label style="padding-right: 5px;" for='insideSalesCode' class='bold txtalgnrght'>
                                Inside Sales Code
                            </label>

                            <div class='element-input inputContainer'>
                                <g:textField name="insideSalesCode" value=""/>

                            </div>
                        </div>

                        <div class="element-content-form-elements-product">
                            <label style="padding-right: 5px;" for='institutionalSalesCode' class='bold txtalgnrght width150'>
                                Institutional Sales Code
                            </label>

                            <div class='element-input inputContainer'>
                                <g:textField name="institutionalSalesCode" value=""/>

                            </div>
                        </div>
                    </div>

                    <div style="clear: both;"></div>
                </div>


                <div id="Sample" class="productTypeDiv">

                    <div>

                            <label style="padding-right: 5px;" for='countryInfo' class='customLabel1'>
                                Sample Code
                            </label>

                            <div class='element-input inputContainer'>
                            </div>



                            <label style="padding-right: 5px;" for='sampleCrCode' class='customLabel1'>
                                Cr*
                            </label>

                            <div class='element-input inputContainer'>
                                <g:textField class="customInput" name="sampleCrCode" value=""/>

                            </div>



                            <label style="padding-right: 5px;" for='sampleDrCode' class='customLabel1'>
                                DR*
                            </label>

                            <div class='element-input inputContainer'>
                                <g:textField  class="customInput" name="sampleDrCode" value=""/>

                            </div>


                    </div>
                </div>

                <div id="Promotional" class="productTypeDiv">

                    <div>

                            <label style="padding-right: 5px;" for='promotionalCrCode' class='customLabel1'>
                                Promotional Code
                            </label>

                            <div class='element-input inputContainer'>
                            </div>



                            <label style="padding-right: 5px;" for='promotionalCrCode' class='customLabel1'>
                                Cr*
                            </label>

                            <div class='element-input inputContainer'>
                                <g:textField class="customInput" name="promotionalCrCode" value=""/>

                            </div>



                            <label style="padding-right: 5px;" for='promotionalDrCode' class='customLabel1'>
                                DR*
                            </label>

                            <div class='element-input inputContainer'>
                                <g:textField class="customInput" name="promotionalDrCode" value=""/>

                            </div>


                    </div>
                </div>

                <div id="Bonus" class="productTypeDiv">

                    <div>

                            <label style="padding-right: 5px;" for='bonusCrCode' class='customLabel1'>
                                Bonus Code
                            </label>

                            <div class='element-input inputContainer'>
                            </div>



                            <label style="padding-right: 5px;" for='bonusCrCode' class='customLabel1'>
                                Cr*
                            </label>

                            <div class='element-input inputContainer'>
                                <g:textField class="customInput" name="bonusCrCode" value=""/>

                            </div>



                            <label style="padding-right: 5px;" for='bonusDrCode' class='customLabel1'>
                                DR*
                            </label>

                            <div class='element-input inputContainer'>
                                <g:textField  class="customInput" name="bonusDrCode" value=""/>

                            </div>

                    </div>
                </div>

                <div id="By_Product" class="productTypeDiv">

                    <div>

                            <label style="padding-right: 5px;" for='byProdSalesCode' class='customLabel1'>
                                By Prod Sales Code
                            </label>

                            <div class='element-input inputContainer'>
                                <g:textField class="customInput" name="byProdSalesCode" value=""/>
                            </div>

                    </div>
                </div>

                <div id="Scrap" class="productTypeDiv">

                    <div>

                            <label style="padding-right: 5px;" for='scrapSalesCode' class='customLabel1'>
                                Scrap Sales Code
                            </label>

                            <div class='element-input inputContainer'>
                                <g:textField class="customInput" name="scrapSalesCode" value=""/>
                            </div>

                    </div>
                </div>

                <div id="Wastage" class="productTypeDiv">

                    <div>

                            <label style="padding-right: 5px;" for='wastageSalesCode' class='customLabel1'>
                                Wastage Sales Code
                            </label>

                            <div class='element-input inputContainer'>
                                <g:textField class="customInput" name="wastageSalesCode" value=""/>
                            </div>

                    </div>
                </div>

                <div style="clear:both;"></div>
            </td>
        </tr>


        %{--Item info here--}%
        <tr>
            <td>
                <table >

                    <tr>
                        <td>
                            <label style="padding-right: 5px;" for='name' class='customLabel'>
                                Item Name
                                <span class="mendatory_field"> * </span>
                            </label>

                            <div class='element-input inputContainer'>
                                <g:textField class="customInput validate[required]" maxlength="30" name="name" value=""/>

                            </div>

                        </td>

                        <td>
                            <label style="padding-right: 5px;" for='description' class='customLabel'>
                                Description
                            </label>

                            <div class='element-input inputContainer'>
                                <g:textField class="customInput" name="description" maxlength="30" value=""/>
                            </div>

                        </td>
                        <td>
                            <label style="padding-right: 5px;" for='packSize' class='customLabel'>
                                Pack Size
                                <span class="mendatory_field"> * </span>
                            </label>

                            <div class='element-input inputContainer'>
                                <g:textField class="customInput validate[required]" name="packSize"  maxlength="30" value=""/>
                            </div>

                        </td>
                    </tr>
                    <tr>
                        <td>

                            <label style="padding-right: 5px;" for='code' class='customLabel'>
                                ERP Item Code
                            </label>

                            <div class='element-input inputContainer'>
                                <g:textField disabled="disabled" placeholder="Auto Generate" class="customInput" name="code" value=""/>
                            </div>

                        </td>
                        <td>

                            <label style="padding-right: 5px;" for='oldCode' class='customLabel'>
                                Old Item Code
                            </label>

                            <div class='element-input inputContainer'>
                                <g:textField class="customInput" name="oldCode" maxlength="30" value=""/>
                            </div>

                        </td>
                        <td>

                            <label style="padding-right: 5px;" for='measureUnitConfiguration' class='customLabel'>
                                Unit Of Measure
                                <span class="mendatory_field"> * </span>
                            </label>

                            <div class='element-input inputContainer'>
                                <g:select name="measureUnitConfiguration.id" id="measureUnitConfiguration"
                                          class="customInput validate[required]"
                                          optionKey="id"
                                          value=""
                                          noSelection="['': 'Select One']"/>
                            </div>

                        </td>
                    </tr>

                </table>
            </td>
        </tr>

        %{--end item info here --}%


        <tr>
            <td>
                <table id="itemSpecification">

                    <tr>
                        <td>
                            <label style="padding-right: 5px;" for='length' class='customLabel'>
                                Length
                            </label>

                            <div class='element-input inputContainer'>
                                <g:textField class="customInput" name="length"  maxlength="30" value=""/>

                            </div>

                        </td>

                        <td>
                            <label style="padding-right: 5px;" for='width' class='customLabel'>
                                Width
                            </label>

                            <div class='element-input inputContainer'>
                                <g:textField class="customInput" name="width"  maxlength="30" value=""/>
                            </div>

                        </td>
                        <td>
                            <label style="padding-right: 5px;" for='color' class='customLabel'>
                                Color
                            </label>

                            <div class='element-input inputContainer'>
                                <g:textField class="customInput" name="color"  maxlength="30" value=""/>
                            </div>

                        </td>
                    </tr>
                    <tr>
                        <td>

                            <label style="padding-right: 5px;" for='density' class='customLabel'><g:message
                                    code='finishProduct.density.label' default='Density'/></label>

                            <div class='element-input inputContainer'>
                                <g:textField class="customInput" name="density"  maxlength="30" value=""/>
                            </div>

                        </td>
                        <td>

                            <label style="padding-right: 5px;" for='texture' class='customLabel'><g:message
                                    code='finishProduct.texture.label' default='Texture'/></label>

                            <div class='element-input inputContainer'>
                                <g:textField class="customInput validate[required]" name="texture"  maxlength="30" value=""/>
                            </div>

                        </td>
                        <td>

                            <label style="padding-right: 5px;" for='other' class='customLabel'><g:message
                                    code='finishProduct.other.label' default='Other'/></label>

                            <div class='element-input inputContainer'>
                                <g:textField class="customInput validate[required]" name="other"  maxlength="30" value=""/>
                            </div>

                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label style="padding-right: 5px;" for='coaGroup' class='customLabel'>Select Account Group<span class="mendatory_field"> * </span></label>

                            <div class='element-input inputContainer'>
                                <g:select class="customInput validate[required]" name="chartOfAccountGroup.id" id="coaGroup" value="" from="" optionKey="id"
                                          noSelection="['':'Select Accounts Group']" onchange="loadCoaHeadByGroup(this.value)" style="min-width:100px;"/>
                            </div>

                        </td>
                        <td>
                            <label style="padding-right: 5px;" for='chartOfAccounts' class='customLabel'>Select Chart of Accounts<span class="mendatory_field"> * </span></label>

                            <div class='element-input inputContainer'>
                                <g:select class="customInput validate[required]" name="chartOfAccountHead.id" id="chartOfAccounts" value="" from="" optionKey="id" noSelection="['':'Select COA']" style="min-width:400px;"/>
                            </div>

                        </td>


                    </tr>
                    <tr>
                        <td>
                            <label style="padding-right: 5px;" for='qtyInLtr' class='customLabel'>QTY In Liter For PMP</label>
                            <div class='element-input inputContainer'>
                                <g:textField class="customInput " name="qtyInLtr"  maxlength="30" value=""/>
                            </div>
                        </td>
                        <td>
                            <label style="padding-right: 5px; width: auto;" for='sequenceNumber' class='customLabel'>
                                    Product Sequence Number <span class="mendatory_field"> * </span> </label>
                            <div class='element-input inputContainer'>
                                <g:textField class="customInput validate[required]" name="sequenceNumber"  maxlength="30" value=""/>
                            </div>

                        </td>
                        <td>
                            <label class="customLabel">Is Active
                            </label>

                            <div class='element-input inputContainer'>
                                %{--<input type="checkbox" name="isActive" id="isActive" value="true" checked="true" onclick="addRemoveCheckBoxForActive(this)"/>--}%
                                <g:checkBox name="isActive" id="isActive" value="true"/>
                            </div>

                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
    <br/>

    <div class="buttons">
        <span class="button"><input type="button" name="create-button" id="create-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="${message(code: 'default.button.create.label', default: 'Create')}"
                                    onclick="executeAjaxFinishProduct();"/></span>
        <span class="button"><input type='button' name="delete-button" id="delete-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete'
                                    onclick="deleteAjaxFinishProduct();"/></span>
        <span class="button"><input type="button" name="cancel-button" id="cancel-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    onclick=" reset_form('#gFormFinishProduct');" value="Cancel"/></span>
    </div>

    <div>
        <fieldset class='ui-state-default ui-corner-all top-margin'>
            <span><g:textField name="query" id="query" value="" placeholder="Type text here"
                               style="width: 280px;height: 20px;"/></span>
            <span class="button"><input type="button" name="search-button" id="search-button"
                                        class="ui-button ui-widget ui-state-default ui-corner-all" value="Search"
                                        onclick="searchProduct();"/></span>
            <span class="button"><input type="button" name="refresh-button" id="refresh-button"
                                        class="ui-button ui-widget ui-state-default ui-corner-all" value="Refresh"
                                        onclick="resetSearch();"/></span>
        </fieldset>
    </div>

</form>
