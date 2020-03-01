<%@ page import="com.bits.bdfp.customer.CustomerStatus; com.bits.bdfp.geolocation.TerritorySubArea; com.bits.bdfp.customer.CustomerTitle; com.docu.commons.Gender; com.bits.bdfp.geolocation.TerritoryConfiguration; com.bits.bdfp.common.Designation; com.bits.bdfp.settings.Department; com.bits.bdfp.settings.EnterpriseConfiguration; com.bits.bdfp.customer.CustomerMaster" %>
<script type="text/javascript" language="JavaScript">
    $(document).ready(function(){
        $("#create-button").val("Update");
        $("#code").attr('disabled', 'disabled');
        $("#cancel-button").hide();
        getTerritoryByEnterprise($("#enterpriseConfigurationId").val());
    });
    function getTerritoryByEnterprise(enterpriseId){
        var options = "<option value=''>Select Territory</option>";
        if(enterpriseId){
            $.ajax({
                type:'POST',
                data:'enterpriseId=' + enterpriseId,
                url:'${request.contextPath}/territoryConfiguration/searchTerritoryByEnterprise',
                success:function (data) {
                    var territoryCount = data.length;
                    for(var i=0; i < territoryCount; i++){
                        options += "<option value='" + data[i].id + "'>" + data[i].name + "</option>"
                    }
                    $("#territory").html(options);
                    var territoryIdTemp = '${territoryIds}';
                    if(territoryIdTemp){
                        var territoryIds = territoryIdTemp.toString().split(',');
                        $('#territory').val(territoryIds).attr('selected', 'selected');
                        loadGeoLocationByTerritory();
                    }
                },
                error:function (XMLHttpRequest, textStatus, errorThrown) {
                    if(XMLHttpRequest.status = 0){
                        $("#territory").html("");
                        MessageRenderer.renderErrorText("Network Problem: Time out");
                    } else{
                        MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                    }
                },
                complete:function (data) {
                },
                dataType:'json'
            });
        }else{
            $("#territory").html(options);
        }
    }
    function loadGeoLocationByTerritory(){
        var territoryIds = $("#territory").val();
        if(territoryIds != ''){
            $.ajax({
                type:'POST',
                data:'territoryIds=' + territoryIds + '&employeeId=' + $('#gFormCustomerMaster input[name = id]').val(),
                url:'${request.contextPath}/territorySubArea/searchTerritorySubAreaByTerritory',
                success:function (data) {
                    $("#td-geoLocation").html(data);
                },
                error:function (XMLHttpRequest, textStatus, errorThrown) {
                    if(XMLHttpRequest.status = 0){
                        $("#territorySubAreaList").html("");
                        $("#territorySubAreaList").multiselect();
                        MessageRenderer.renderErrorText("Network Problem: Time out");
                    } else{
                        MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                    }
                },
                complete:function (data) {
                },
                dataType:'html'
            });
        }else {
            $("#territorySubAreaList").html("");
            $(".remove-all").click();
        }
    }
</script>
<form name='gFormCustomerMaster' id='gFormCustomerMaster'>
<g:hiddenField name="id" id="employeeId" value="${customerMaster?.id}"/>
<g:hiddenField name="version" value="${customerMaster?.version}"/>
<g:hiddenField name="customerMaster.id" id="customerMasterId" value="${customerMaster?.customerMaster?.id}"/>
<g:hiddenField name="enterpriseConfiguration.id" id="enterpriseConfigurationId" value="${customerMaster?.enterpriseConfiguration?.id}"/>
<div id="remote-content-customerMaster"></div>

<div class="element_row_content_container lightColorbg pad_bot0">
<table>
<tr>
    <td>
        <label class="txtright bold hight1x width130">
            Employee PIN:
            <span class="mendatory_field">*</span>
        </label>
    </td>
    <td class='element-input inputContainer' style="padding-right: 10px;">
        <g:textField name="code" value="${customerMaster?.code}" class="validate[required]"
                     maxlength="20"/>
    </td>
    <td>
        <label class="txtright bold hight1x width130">
            Title:
            <span class="mendatory_field">*</span>
        </label>
    </td>
    <td class="element-input inputContainer" style="padding-right: 10px;">
        <g:select class="validate[required]" name="customerTitle.id" id="customerTitle"
                  from="${CustomerTitle.list()}" optionKey="id" noSelection="['': 'Select Title']"
                  value="${customerMaster?.customerTitle?.id}"/>
    </td>
    <td class="customerStatus">
        <label class="txtright bold hight1x width130">
            Status:
            <span class="mendatory_field">*</span>
        </label>
    </td>
    <td class="customerStatus element-input inputContainer" style="padding-right: 10px;">
        <g:select class="validate[required]" name="customerStatus" id="customerStatus"
                  from="${CustomerStatus.values()}" value="${customerMaster?.customerStatus}"
                  tabindex="-1"/>
    </td>
</tr>
<tr>
    <td>
        <label class="txtright bold hight1x width130">
            First Name:
            <span class="mendatory_field">*</span>
        </label>
    </td>
    <td class="element-input inputContainer" style="padding-right: 10px;">
        <g:textField class="validate[required]" name="firstName" value="${customerMaster?.firstName}"
                     maxlength="30"/>
    </td>
    <td>
        <label class="txtright bold hight1x width130">
            Middle Name:
        </label>
    </td>
    <td class="element-input inputContainer">
        <g:textField name="middleName" value="${customerMaster?.middleName}" maxlength="30"/>
    </td>
    <td>
        <label class="txtright bold hight1x width130">
            Last Name:
        </label>
    </td>
    <td class="element-input inputContainer">
        <g:textField name="lastName" value="${customerMaster?.lastName}" maxlength="30"/>
    </td>
</tr>
<tr>
    <td>
        <label class="txtright bold hight1x width130">
            Department:
            <span class="mendatory_field">*</span>
        </label>
    </td>
    <td class="element-input inputContainer">
        <g:select class="validate[required]" name="department.id" id="department"
                  from="${Department.list()}" optionKey="id" value="${customerMaster?.department?.id}"
                  noSelection="['': 'Select Department']"/>
    </td>
    <td>
        <label class="txtright bold hight1x width130">
            Mobile:
            <span class="mendatory_field">*</span>
        </label>
    </td>
    <td class="element-input inputContainer">
        <g:textField class="validate[required]" name="mobile" id="mobile"
                     value="${customerMaster?.mobile}"
                     maxlength="20"/>
    </td>
    <td>
        <label class="txtright bold hight1x width130">
            Email:
        </label>
    </td>
    <td class="element-input inputContainer">
        <g:textField class="validate[custom[email]]" name="email" value="${customerMaster?.email}"
                     maxlength="50"/>
    </td>
</tr>
<tr>
    <td>
        <label class="txtright bold hight1x width130">
            Designation:
            <span class="mendatory_field">*</span>
        </label>
    </td>
    <td class="element-input inputContainer">
        <g:select class="validate[required]" name="designation.id" id="designation"
                  from="${Designation.list()}" optionKey="id" value="${customerMaster?.designation?.id}"
                  noSelection="['': 'Select Designation']"/>
    </td>
    <td>
        <label class="txtright bold hight1x width130">
            Gender:
            <span class="mendatory_field">*</span>
        </label>
    </td>
    <td class="element-input inputContainer">
        <g:select class="validate[required]" name="gender.id" id="gender" from="${Gender.list()}"
                  optionKey="id" optionValue="genderType" value="${customerMaster?.gender?.id}"
                  noSelection="['': 'Select Gender']"/>
    </td>
    <td>
        <label class="txtright bold hight1x width130">
            Vehicle No.:
        </label>
    </td>
    <td class="element-input inputContainer">
        <g:textField name="vehicleNo" value="${customerMaster?.vehicleNo}" maxlength="30"/>
    </td>
</tr>
<tr>
    <td>
        <label class="txtright bold hight1x width130">
            Enterprise:
            <span class="mendatory_field">*</span>
        </label>
    </td>
    <td class="element-input inputContainer">
        <g:textField name="enterPriseName" id="enterPriseName" disabled="disabled"
                     value="${enterPriseName}"/>
    </td>
</tr>
<tr>
    <td>
        <label class="txtright bold hight1x width130">
            Territory:
        </label>
    </td>
    <td class="element-input inputContainer">
        <g:select name="territory" id="territory" value="" optionKey="id" optionValue="name"
                  multiple="multiple" size="3" onchange="loadGeoLocationByTerritory()"/>
    </td>
</tr>
<tr>
    <td>
        <label class="txtright bold hight1x width130">
            Geo Location:
        </label>
    </td>
    <td colspan="5" id="td-geoLocation">
        <g:select name="territorySubAreaList" id="territorySubAreaList"
                  class="multiselect" multiple="multiple"
                  from=""
                  optionKey="id"
                  optionValue="name" size="12"
                  value=""/>
    </td>
</tr>
<tr>
    <td>
        <label class="txtright bold hight1x width130">
            Present Address:
            <span class="mendatory_field">*</span>
        </label>
    </td>
    <td colspan="3" class="element-input inputContainer">
        <g:textArea class="validate[required] width380" name="presentAddress"
                    value="${customerMaster?.presentAddress}" maxlength="250"/>
    </td>
    <td colspan="2">
    </td>
</tr>
<tr>
    <td class="width200">
        <label class="txtright bold hight1x width130">
            Permanent Address:
            <span class="mendatory_field">*</span>
        </label>
    </td>
    <td colspan="3" class="element-input inputContainer">
        <g:textArea class="validate[required] width380" name="permanentAddress"
                    value="${customerMaster?.permanentAddress}" maxlength="250"/>
    </td>
    <td colspan="2">
    </td>
</tr>
<tr>
    <td>
        <label class="txtright bold hight1x width130">
            Remarks:
        </label>
    </td>
    <td colspan="3" class="element-input inputContainer">
        <g:textField class="width380" name="remarks" value="${customerMaster?.remarks}"
                     maxlength="250"/>
    </td>
    <td colspan="2">
    </td>
</tr>
<tr>
    <td>
        <label class="txtright bold hight1x width130">
            Supervisor:
        </label>
    </td>
    <td colspan="4">
        <div id="supervisor"></div>
    </td>
    <td>
    </td>
</tr>
</table>
</div>
<br/>

<div class="buttons">
    <span class="button"><input type="button" name="create-button" id="create-button"
                                class="ui-button ui-widget ui-state-default ui-corner-all"
                                value="${message(code: 'default.button.create.label', default: 'Create')}"
                                onclick="executeAjaxCustomerMaster();"/></span>
    <span class="button"><input type="button" name="cancel-button" id="cancel-button"
                                class="ui-button ui-widget ui-state-default ui-corner-all"
                                onclick=" clean_form('#gFormCustomerMaster');" value="Cancel"/></span>
</div>
</form>
