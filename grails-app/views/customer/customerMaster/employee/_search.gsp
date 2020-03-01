<%@ page import="com.bits.bdfp.util.ApplicationConstants; com.bits.bdfp.customer.CustomerMaster" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title>Search Employee</title>
<g:render template="/customer/customerMaster/employee/employeeDetailsScript" />
%{--<g:javascript src="jquery/watermark/jquery.watermark.js"/>--}%
<script type="text/javascript" language="Javascript">
    $(document).ready(function() {
        getFlexBoxEmployeeList();
    });

    function getFlexBoxEmployeeList() {
        $("#employee").empty();
        $("#employee").flexbox(${supervisorList}, {
            width: 355,
            onSelect: function() {
                $("#employeeId").val($('#employee_hidden').val());
                EmployeeRegister.setEmployeeInformation($('#employee_hidden').val(), $('#employee_input').val());
                EmployeeRegister.showEmployeeDetail();
            }
        });
        $('#employee_input').blur(function() {
            if($('#employee_input').val() == ''){
                $("#employeeId").val("");
                $('#popEmpDetails').html("");
            }
        });
    }
</script>
<div id="search">
    <form name='gFormEmployee' id='gFormEmployee'>
        <div class="element_row_content_container lightColorbg pad_bot0">
            <label for='employee' class='txtright bold hight1x width1x'>
                Search Employee
            </label>
            <div class="element-input inputContainer width395">
                <div id="employee" style="float: left"></div>
                <span id="search-btn-employee-register-id" title="" role="button"
                      class="fld-btn ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only floatR">
                    <span class="ui-button-icon-primary ui-icon ui-icon-search"></span>
                    <span class="ui-button-text"></span>
                </span>
            </div>
        </div>
        <div class="clear"></div>

        <div class="clear"></div>

        <div id="popEmpDetails" class="width1021">
            %{--<g:render template="agentDetailsPanel" model="[agent: agent]"/>--}%
        </div>
    </form>
</div>