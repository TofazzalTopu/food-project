<%--
  Created by IntelliJ IDEA.
  User: sarahafreen.orny
  Date: 5/3/2016
  Time: 5:52 PM
--%>

<%@ page import="com.bits.bdfp.setup.salestarget.StatementFormatController" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title><g:message code="statementFormat.create.label" default="Statement Format"/></title>

<script>

    $(document).ready(function () {

    });
    function findSalesMan(value) {
        alert(value);
        if (value != '') {
            var options = '<option value="">Select Customer</option>';

        }
        else {
            var options = '<option value="">- Select -</option>';
            return false;
        }
        $("#customer").html(options);
        jQuery.ajax({
            type: 'post',
            url: "${resource(dir:'statementFormat', file:'fetchDropDownList')}?id=" + value ,
            success: function (data) {
//              console.log(data);
                $.each(data, function (key, val) {
//                    alert(JSON.stringify(data));
                    options += '<option value="' + val.ID + '">' + val.Customer + '</option>';
                })
                $("#customer").html(options);
            },
//          complete: function () {
//              if (territory != 0) {
//                  $('#division').val(territory.division.id).attr("selected", "selected");
//              }
//          },
            dataType: 'json'
        });
    }
    function findOtherCustomer(value) {
//        alert(value);
        if (value != '') {
            var options = '<option value="">Select Customer</option>';

        }
        else {
            var options = '<option value="">- Select -</option>';
            return false;
        }
//        $("#customer").html(options);
        jQuery.ajax({
            type: 'post',
            url: "${resource(dir:'statementFormat', file:'fetchDropDownList')}?id=" + value ,
            success: function (data) {
              console.log(data);
                $.each(data, function (key, val) {
                    alert(JSON.stringify(val));
                    options += '<option value="' + val.ID + '">' + val.Customer + '</option>';
                })
                $("#customer").html(options);
            },

            dataType: 'json'
        });
    }

    function statementFormatgenerate() {
        alert("hello");
//        debugger
        var fromDate = $('#fromDate').val();
        var toDate = $('#toDate').val();
        var territory = $('#customer').val();
//        var territoryArea = $("#territory option:selected").text();
        alert(territory);
//        var territoryName=$('#territory').selected();
        %{--SubmissionLoader.showTo();--}%
        %{--window.open("${resource(dir:'statementFormatgenerate', file:'rptStatementFormat')}?format=PDF&fromDate=" + fromDate + "&toDate=" + toDate + "&territory=" + territory + "&territoryArea=" + territoryArea);--}%
        %{--SubmissionLoader.hideFrom();--}%
    }
</script>

<div class="main_container">
    <div class="content_container">
        <h1><g:message code="statementFormat.create.label" default="Sales Report MT"/></h1>

        <h3><g:message code="statementFormat.Info.label" default="Sales Report MT"/></h3>

        <div class="clear height5"></div>

        <div class="element_container_big">
            <div class="block-title">

                <div class='element-title'>
                    <g:message code='statementFormat.fromDate.label' default='From Date'/>
                    <span class="mendatory_field">*</span>
                </div>

                <div class='element-title'>
                    <g:message code='statementFormat.toDate.label' default='To Date'/>
                    <span class="mendatory_field">*</span>
                </div>


                <div class="clear"></div>
            </div>

            <div class="block-input">

                <div class='element-input inputContainer'><g:textField class="validate[required] text-input datepicker"
                                                                       name="fromDate" id="fromDate"/>
                </div>
                <script type='text/javascript'>
                    $(document).ready(function () {
                        $('#fromDate').datepicker({
                            dateFormat: 'dd-mm-yy',
                            changeMonth: true,
                            changeYear: true
                        });
                        $('#fromDate').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});
                    });
                </script>

                <div class='element-input inputContainer'><g:textField class="validate[required] text-input datepicker"
                                                                       name="toDate" id="toDate"/>
                </div>
                <script type='text/javascript'>
                    $(document).ready(function () {
                        $('#toDate').datepicker({
                            dateFormat: 'dd-mm-yy',
                            changeMonth: true,
                            changeYear: true
                        });
                        $('#toDate').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});
                    });
                </script>


                <div class="clear"></div>

                <table>
                    <tr>
                        <td>
                            <label class="txtright bold hight1x width1x" style="width: 120px;">
                                <g:message code="statementFormat.factory.label"
                                           default="Factory InCharge"/>
                            </label>
                        </td>
                        <td>
                            <g:radio name="factoryType" id="Factory" value="factory" onchange="findOtherCustomer(this.value)" />
                            %{--<input type="radio" name="factoryType" id="Factory" value="factory"--}%
                                   %{--onchange="findOtherCustomer(this.value)">--}%

                        </td>
                        <td>
                            <label class="txtright bold hight1x width1x" style="width: 80px;">
                                <g:message code="statementFormat.factory.label"
                                           default="Non Factory"/>
                            </label>
                        </td>
                        <td>
                            <g:radio name="factoryType" id="Nonfactory" value="nonfactory" onchange="findSalesMan(this.value)" />
                            %{--<input type="radio" name="Nonfactory" id="Nonfactory" value="nonfactory"--}%
                                   %{--onchange="findSalesMan(this.value)">--}%
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <label class="txtright bold hight1x width1x" style="width: 80px;">
                                <g:message code="salesReportMT.territory.label"
                                           default="Customer"/>
                            </label>
                        </td>
                        <td>
                            <g:select name="customer" id="customer" class="validate[required]"  optionKey="id"
                                      noSelection="['': '- Select -']"/>
                        </td>
                    </tr>
                </table>
            </div>
        </div>

        <div class="clear height5"></div>

    </div>

    <div class="buttons" style="margin-left:10px;">
        <span class="button"><input type="button" name="print-button" id="dialog-confirm-workOrder"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="Generate Report"
                                    onclick="statementFormatgenerate();"/></span>

    </div>

</div>