<%--
  Created by IntelliJ IDEA.
  User: NZ
  Date: 6/13/2016
  Time: 10:42 AM
--%>

<%@ page import="com.docu.commons.CommonConstants; com.bits.bdfp.setup.salestarget.ReportListController" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title>Daily Product Demand Register</title>

<script>

    var format = 'pdf';
    $(document).ready(function () {
        initDatePicker();
        $("#pdf").attr('checked','checked');
    });


    function generateReport() {
        var date = $("#reportDate").val();
        if(!date){
            alert("Please Select Date");
            return
        }
        var branchId = $("#branch").val();
        if(!branchId){
            alert("Please Select Branch");
            return
        }
        SubmissionLoader.showTo();
        window.open("${resource(dir:'reportList', file:'rptDailyDemandRegister')}?format=" + format
            + "&date=" + date + "&branchId=" + branchId + "&branchName=" + $( "#branch option:selected" ).text()
            + "&month="+ $.datepicker.formatDate('MM', $("#reportDate").datepicker('getDate'))
            + "&masterProductId=" + $("#masterProduct").val() + "&masterProductName=" + $( "#masterProduct option:selected" ).text()
        );
        SubmissionLoader.hideFrom();
    }

    function initDatePicker() {
        %{--var branchList=  ${size};--}%
        %{--var options ='';--}%
        %{--if(branchList > 1){--}%
             %{--options = '<option value="">Please Select</option>';--}%
            %{--for (var i = 0; i < ${size}; i++) {--}%
                %{--options += '<option value="' + ${branchList}[i].id + '">' + ${branchList}[i].name + '</option>';--}%
            %{--}--}%
        %{--}else{--}%
            %{--options += '<option value="' + ${branchList}[0].id + '">' + ${branchList}[0].name + '</option>';--}%
        %{--}--}%
        %{--$("#branch").html(options);--}%

        $('#reportDate').datepicker(
                {
                    dateFormat: 'dd-mm-yy',
                    changeMonth: true,
                    changeYear: true,
                    maxDate: -1
                });
//        $("#calculatedTo").datepicker("setDate", "-1d");
        $('#reportDate').mask('${CommonConstants.DATE_MASK_FORMAT}', {});
    }

    function changeFormat(val){
        if(val == 2){
            format = 'xlsx';
        }else{
            format = 'pdf';
        }
    }

</script>

<div class="main_container">
    <div class="content_container">
        <h1>Daily Product Demand Register</h1>

        <h3>Daily Product Demand Register</h3>

        <div class="clear height5"></div>

        <div class="element_row_content_container lightColorbg pad_bot0">

            <table>
                <tr>
                    <td>
                        <label for="reportDate" class="txtright bold hight1x width105">
                            Date:
                            <span class="mendatory_field">*</span>
                        </label>
                    </td>
                    <td>
                        <g:textField name="reportDate" id="reportDate" class="validate[required] width160"
                                     value=""/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="branch" class="txtright bold hight1x width105">
                            Branch:
                            <span class="mendatory_field">*</span>
                        </label>
                    </td>
                    <td>
                        <g:select name="branch.id" from="${branchList}" class="width160"
                                  id="branch" optionValue="name"
                                  optionKey="id" value="" noSelection="['': 'Please Select']"
                                  onchange=""/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="masterProduct" class="txtright bold hight1x width105">
                            Master Product:
                            <span class="mendatory_field">*</span>
                        </label>
                    </td>
                    <td>
                        <g:select name="masterProduct.id" from="${com.bits.bdfp.inventory.product.MasterProduct.list()}"
                                  class="width160" id="masterProduct"
                                  optionKey="id" value="" noSelection="['': 'All Master Product']"
                                  onchange=""/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="pdf" class="txtright bold hight1x width105">
                            Report Format:
                            <span class="mendatory_field">*</span>
                        </label>
                    </td>
                    <td>
                        <g:radio name="pm" id="pdf" value="pdf" checked="checked" onclick="changeFormat(1);"/> PDF Format <br/>
                        <g:radio name="pm" id="xls" value="xls" onclick="changeFormat(2);"/> Excel Format <br/>
                    </td>
                </tr>
            </table>
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
                                    onclick="generateReport();"/></span>

    </div>

</div>