<%--
  Created by IntelliJ IDEA.
  User: sarahafreen.orny
  Date: 5/8/2016
  Time: 2:45 PM
--%>
<%@ page import="com.bits.bdfp.setup.salestarget.ReportListController;com.bits.bdfp.inventory.product.ProductPrice;com.docu.commons.CommonConstants" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title><g:message code="ttumFileExport.create.label" default="Price List"/></title>

<script>

    $(document).ready(function () {
        $('#territory').focus();

        setDateRange('fromDate','toDate');
        $('#fromDate').mask('${CommonConstants.DATE_MASK_FORMAT}', {});
        $('#toDate').mask('${CommonConstants.DATE_MASK_FORMAT}', {});
        var options = '<option value="">Select Status</option>';
        options += '<option value="1">Active</option>';
        options += '<option value="0">Inactive</option>';
        $("#status").html(options);

    });

    function loadPriceNameByPriceType(priceTypeId){
        var optionData = '<option value="">Select Price Name</option>';
        $.ajax({
            type:'POST',
            data:'priceTypeId=' + priceTypeId,
            url:'${request.contextPath}/productPrice/flexListPriceNameByPriceType',
            success:function (result) {
                var index = 0;
                for(index = 0; index < result.length; index++){
                    optionData += '<option value="' + result[index].id + '">' + result[index].name + '</option>';
                }
                $("select#priceName").html(optionData);
            },
            error:function (jqXHR, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                }else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete:function (data) {
                //alert('in complete');
            },
            dataType:'json'
        });
    }
    function priceList() {

        var fromDate = $('#fromDate').val();
        var toDate = $('#toDate').val();
        var Pricetype=$("#productPricingType option:selected").text();
        var Pricename=$("#priceName option:selected").text();
        //var status=$("#status option:selected").text();
        var status=$("#status").val();
        //alert(Pricename)

        if(Pricetype==""){
            return MessageRenderer.renderErrorText("Please Select Price Type");
        }
        if(status==""){
            return MessageRenderer.renderErrorText("Please Select Status");
        }
        if(Pricename=="Select Price Name"){
            return MessageRenderer.renderErrorText("Please Select Price Name");
        }

       /* if(fromDate==""){
            return MessageRenderer.renderErrorText("Please Select From Date");
        }
        if(toDate==""){
            return MessageRenderer.renderErrorText("Please Select To Date");
        }*/

        SubmissionLoader.showTo();
        window.open("${resource(dir:'reportList', file:'rptPriceList')}?format=PDF&pricename="+Pricename+"&pricetype="+Pricetype+"&fromDate="+fromDate+"&toDate="+toDate+"&status="+status);
        SubmissionLoader.hideFrom();
    }

</script>

<div class="main_container">
    <div class="content_container">
        <h1><g:message code="salesReportMT.create.label" default="Price List"/></h1>

        <h3><g:message code="salesReportMT.Info.label" default="Price List"/></h3>

        <div class="clear height5"></div>

        <div class="element_container_big">
            <div class="element_row_content_container lightColorbg pad_bot0">
                <table>
                    <tr>
                        <td>
                            <label class="txtright bold hight1x width110">
                                Price List Type:
                                <span class="mendatory_field">*</span>
                            </label>
                        </td>

                        <td>
                            <div class='element-input inputContainer'>
                                <g:select name="productPricingType" id="productPricingType"
                                          class="validate[required]"
                                          optionKey="id" from="${productPricingTypeList}"
                                          value="" onchange="loadPriceNameByPriceType(this.value)"
                                          noSelection="['': 'Select One']"/>
                            </div>
                        </td>
                        <td>
                            <label class="txtright bold hight1x width150">
                                Select Status:
                                <span class="mendatory_field">*</span>
                            </label>
                        </td>
                        <td>
                            <div class='element-input inputContainer'>
                                <g:select name="status" id="status"
                                          optionKey="id" from=""
                                          value=""
                                          noSelection="['': 'Select Status']"/>
                            </div>
                        </td>

                    </tr>
                    <tr>
                        <td>
                            <label class="txtright bold hight1x width110">
                                Price Name List:
                            </label>
                        </td>
                        <td>
                            <div class='element-input inputContainer'>

                                <g:select name="priceName" id="priceName"
                                          optionKey="id" from=""
                                          value=""
                                          noSelection="['': 'Select Price Name']"/>
                            </div>
                        </td>
                        <td>
                            <label class="txtright bold hight1x width150">
                                Effective Date: From
                            </label>
                        </td>
                        <td colspan="2">
                            <div class='element-input inputContainer width300'>
                                <g:textField class="width80" name="fromDate" value=""/>
                                <label class="txtright bold hight1x width30">To:</label>
                                <g:textField class="width80" name="toDate" value=""/>
                            </div>
                        </td>

                    </tr>
                </table>
                %{--</fieldset>--}%
            </div>


            %{--<div class="block-input">--}%



                %{--<div class="clear"></div>--}%

            %{--</div>--}%
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
                                    onclick="priceList();"/></span>

    </div>

</div>