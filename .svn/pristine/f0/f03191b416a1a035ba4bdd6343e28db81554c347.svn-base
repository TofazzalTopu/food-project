


<%@ page import="com.bits.bdfp.settings.bussinessday.FinancialYear" %>


<div id="spinnerFinancialYear" class="spinner" style="display:none;" align="left">
    <table class="spinnerTable">
        <tbody>
        <tr>
            <td><img src="${resource(dir: 'images', file: 'spinner.gif')}" alt="Spinner"/></td>
            <td>Communicating with the server... Please wait!</td>
        </tr>
        </tbody>
    </table>
</div>
<form name='gFormFinancialYear' id='gFormFinancialYear'>
  <g:hiddenField name="id" value="${financialYear?.id}" />
  <g:hiddenField name="version" value="${financialYear?.version}" />
    <div id="remote-content-financialYear"></div>
    <div>
      
          <div class="element_container_big">
            <div class="block-title">
                <div class='element-title'>
                    <g:message code="financialYear.name.label" default="Enterprise"></g:message>
                </div>
                <div class='element-title'>
                    <g:message code='financialYear.name.label' default='Name' /></div>
                <div class='element-title'>
                    <g:message code='financialYear.dateStart.label' default='Date Start' /></div>
                <div class='element-title'>
                    <g:message code='financialYear.dateEnd.label' default='Date End' /></div>
                %{--<div class='element-title'>--}%
                    %{--<g:message code='financialYear.dateLastUpdated.label' default='Date Last Updated' /></div>--}%

                %{--<div class='element-title'><g:message code='financialYear.isOpened.label' default='Is Opened' /></div>--}%
                <div class="clear"></div>
            </div>
            <div class="block-input">
                <div class="element-input"><g:select  name="enterpriseConfiguration.id" id="enterpriseConfiguration"
                                                     from="${com.bits.bdfp.settings.EnterpriseConfiguration.list()}" optionValue="name" optionKey="id"
                                                     noSelection="['null':'Select a Enterprise']" /></div>
                <div class='element-input inputContainer'>
                    <g:textField name="name" id="name" value="${fieldValue(bean: financialYear, field: 'name')}" /></div>
                <script type='text/javascript'>$(document).ready(function(){$('#dateStart').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}',{});});</script>
                <div class='element-input inputContainer'>
                    <g:textField name="dateStart" id="dateStart" value="${fieldValue(bean: financialYear, field: 'dateStart')}" /></div>
                <script type='text/javascript'>$(document).ready(function(){$('#dateEnd').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}',{});});</script>
                <div class='element-input inputContainer'>
                    <g:textField name="dateEnd" id="dateEnd" value="${fieldValue(bean: financialYear, field: 'dateEnd')}" /></div>
                %{--<script type='text/javascript'>$(document).ready(function(){$('#dateLastUpdated').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}',{});});</script>--}%
                %{--<div class='element-input inputContainer'>--}%
                    %{--<g:textField name="dateLastUpdated" id="dateLastUpdated" value="${fieldValue(bean: financialYear, field: 'dateLastUpdated')}" /></div>--}%

                %{--<div class='element-input inputContainer '><g:checkBox name="isOpened" value="${financialYear?.isOpened}" /></div>--}%
                <div class="clear"></div>
            </div>
          </div>

          
        

        
          %{--<div class="element_container_big">--}%
            %{--<div class="block-title">--}%
                %{--<div class='element-title'><g:message code='financialYear.userLastUpdated.label' default='User Last Updated' /></div>--}%
                %{--<div class="clear"></div>--}%
            %{--</div>--}%
            %{--<div class="block-input">--}%
                %{--<div class='element-input inputContainer '><g:select name="userLastUpdated.id" id="userLastUpdated" from="${com.docu.security.ApplicationUser.list()}" optionKey="id" value="${financialYear?.userLastUpdated?.id}"  /></div>--}%
                %{--<div class="clear"></div>--}%
            %{--</div>--}%
          %{--</div>--}%
        
  </div>
  <div class="clear"></div>
  <div class="buttons" style="margin-left:10px;">
    <span class="button"><input type="button" name="create-button" id="create-button-financialYear" class="ui-button ui-widget ui-state-default ui-corner-all" value="Open" onclick="executeAjaxFinancialYear();"/></span>
    %{--<span class="button"><input type='button' name="delete-button" id="delete-button-financialYear" class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete' onclick="deleteAjaxFinancialYear();"/></span>--}%
    %{--<span class="button"><input name="clearFormButtonFinancialYear" class="ui-button ui-widget ui-state-default ui-corner-all" type="button" onclick=" reset_form('#gFormFinancialYear');" value="Cancel"/></span>--}%
  </div>
</form>
