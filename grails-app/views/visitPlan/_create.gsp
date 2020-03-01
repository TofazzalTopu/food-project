<%@ page import="com.bits.bdfp.setup.salestarget.VisitPlan" %>
<form name='gFormVisitPlan' id='gFormVisitPlan'>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            Employee Name
            <span class="mendatory_field">*</span>
        </label>

        <div class='element-input inputContainer width240'>
            <div id="employeeId" style="width: 350px;">${applicationUser.fullName}</div>
        </div>

        <g:hiddenField name="customerMaster.id" id="customerMaster" value="${applicationUser.customerMasterId}"/>

        <label class="txtright bold hight1x width1x">
                 Area/Location to Visit
            <span class="mendatory_field">*</span>
        </label>

        <div class='element-input inputContainer' style="padding-top: 2px">
            <g:textArea class="validate[required]" style="width: 180px;" name="placeOfVisit"
                        value="" maxlength="50"/>
        </div>

    </div>

    <div class="element_row_content_container lightColorbg pad_bot0">

        <label class="txtright bold hight1x width1x">
            Date From
            <span class="mendatory_field">*</span>
        </label>

        <div class='element-input inputContainer width240'>
            <g:textField class="validate[required]" name="dateFrom" value="${recentDate}" maxlength="50"/>
        </div>

        <label class="txtright bold hight1x width1x">
            Date To
            <span class="mendatory_field">*</span>
        </label>

        <div class='element-input inputContainer'>
            <g:textField class="validate[required]" name="dateTo" value="${recentDate}" maxlength="50"/>
        </div>

    </div>

    <div class="element_row_content_container lightColorbg pad_bot0">

        <label class="txtright bold hight1x width1x">
            Time From
            <span class="mendatory_field">*</span>
        </label>

        <div class='element-input inputContainer width240'>
            <g:textField class="validate[required]" name="timeFrom" value="" maxlength="50"/>
        </div>

        <label class="txtright bold hight1x width1x">
            Time To
            <span class="mendatory_field">*</span>
        </label>

        <div class='element-input inputContainer'>
            <g:textField class="validate[required]" name="timeTo" value="" maxlength="50"/>
        </div>

    </div>


    <div class="element_row_content_container lightColorbg">
        <label class="txtright bold hight1x width1x">
            Purpose
        </label>

        <div class='element-input inputContainer width240'>
            <g:textArea class="validate[required] width180" name="purpose" value=""
                        maxlength="50"/>
        </div>

        <label class="txtright bold hight1x width1x">
            Remarks
        </label>

        <div class='element-input inputContainer'>
            <g:textArea class="validate[required] width180" name="remarks" value=""
                        maxlength="50"/>
        </div>

    </div>


    <div class="clear"></div>

    <div class="buttons">
        <span class="button" id="merge-span"><input type="button" name="add-button" id="add-dates-button"
                                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                                    value="Add To Grid"
                                                    onclick="addItemsToGrid();"/></span>

    </div>

</form>