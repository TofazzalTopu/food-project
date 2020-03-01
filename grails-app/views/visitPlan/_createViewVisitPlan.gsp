<%@ page import="com.bits.bdfp.setup.salestarget.VisitPlan" %>
<form name='gFormVisitPlan' id='gFormVisitPlan'>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x width80">
            Employee
            <span class="mendatory_field"></span>
        </label>

        <div class='element-input inputContainer width340'>
            %{--<g:textField class="validate[required]"  name="employeeName" value="" maxlength="50"/>--}%
            <input type="text" id="employeeName" name="employeeName" class="validate[required] width300"/>
            <span id="search-btn-employee-id" title="" role="button"
                  class="fld-btn ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only floatR">
                <span class="ui-button-icon-primary ui-icon ui-icon-search"></span>
                <span class="ui-button-text"></span>
            </span>
            <g:hiddenField name="employeeNamehidden" id="employeeNamehidden"/>
        </div>

    </div>

    <div class="element_row_content_container lightColorbg pad_bot0">

        <label class="txtright bold hight1x width1x width80">
            Employee PIN
        </label>

        <div class='element-input inputContainer width140'>
            <g:textField class="validate[required] width100"  name="employeeId" id="employeeId"  maxlength="50" readonly="readonly"/>
        </div>

        <label class="txtright bold hight1x width1x width60">
            Name
        </label>

        <div class='element-input inputContainer width200'>
            <g:textField class="validate[required]"  name="ename" id="ename"  readonly="readonly"/>
        </div>

        <label class="txtright bold hight1x width1x width60">
            Enterprise
        </label>

        <div class='element-input inputContainer'>
            <g:textField class="validate[required]"  name="enterprise" id="enterprise"  readonly="readonly"/>
        </div>

    </div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x width80">
            Date From
            <span class="mendatory_field">*</span>
        </label>

        <div class='element-input inputContainer width240'>
            <g:textField class="validate[required]" name="dateFrom"  id="dateFrom" value="${recentDate}" maxlength="50"/>
        </div>

        <label class="txtright bold hight1x width1x">
            Date To
            <span class="mendatory_field">*</span>
        </label>

        <div class='element-input inputContainer'>
            <g:textField class="validate[required]" name="dateTo" id="dateTo" value="${recentDate}" maxlength="50"/>
        </div>

    </div>

    <div class="clear"></div>
    <div class="buttons">
            <span class="button" id="merge-span"><input type="button" name="search-button" id="search-button"
                                                        class="ui-button ui-widget ui-state-default ui-corner-all"
                                                        value="Search" onclick="searchVisitPlan();"/></span>
            <span class="button" ><input type="button" name="search-button" id="print-button"
                                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                                    value="Print" onclick="printVisitPlan();"/></span>

    </div>

</form>