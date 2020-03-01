

<%@ page import="com.bits.bdfp.inventory.setup.VatType" %>
<form name='gFormVatType' id='gFormVatType'>
  <g:hiddenField name="id" value="${vatType?.id}" />
  <g:hiddenField name="version" value="${vatType?.version}" />
  <div id="remote-content-vatType"></div>
<div class="element_row_content_container lightColorbg pad_bot0">
    <label class="txtright bold hight1x width1x">
        <g:message code='depositPool.enterprise.label' default='Enterprise' />
        <span class="mendatory_field"> * </span>
    </label>
    <g:if test="${list}">
        <g:if test="${list.size()==1}">
            <div  class='element-input inputContainer'>

                <g:textField name="enterPriseName" readonly="readonly" value="${list[0].name}" />
                <g:hiddenField name="enterpriseConfiguration.id"  id="enterpriseConfiguration" value="${list[0].id}" />
            </div>
        </g:if>
        <g:else>
            <div  class='element-input inputContainer'>
                <div id="enterpriseList" style="width: 350px;"></div>
                <script type="text/javascript">

                    jQuery(document).ready(function () {
                        var data=${result}
                                $("#enterpriseList").empty();
                        $("#enterpriseList").flexbox(data , {
                            watermark: "Select Enterprise",
                            width: 260,
                            onSelect: function() {

                                $("#enterpriseConfiguration").val($('#enterpriseList_hidden').val());

                            }
                        });
                        $('#enterpriseList_input').blur(function() {
                            if($('#enterpriseList_hidden').val() == ''){
                                $("#enterpriseList").val("");
                            }
                        });

                    });
                </script>

                <g:hiddenField name="enterpriseConfiguration.id"  id="enterpriseConfiguration" value="" />

            </div>
        </g:else>

    </g:if>
    <g:else>
        <div  class='element-input inputContainer'>
            <g:textField name="enterPriseName" readonly="readonly" value="" />
            <script type="text/javascript">
                jQuery(document).ready(function () {
                    MessageRenderer.showHeaderMessage("You have no assigned enterprise, please assign enterprise first.",0)
                });
            </script>
        </div>
    </g:else>
</div>

<div class="element_row_content_container lightColorbg pad_bot0">
    <label class="txtright bold hight1x width1x">
        <g:message code="vatType.name.label" default="Name" />
        <span class="mendatory_field"> * </span>
    </label>

    <div class='element-input inputContainer'>
        <g:textField class="validate[required]" name="name" value="${vatType?.name}" />

    </div>

</div>

<div class="element_row_content_container lightColorbg pad_bot0">
    <label class="txtright bold hight1x width1x">
        <g:message code="vatType.code.label" default="Account Code" />
        %{--<span class="mendatory_field"> * </span>--}%
    </label>

    <div class='element-input inputContainer'>
        <g:textField name="accountCode" value="${vatType?.accountCode}" />

    </div>

</div>


<div class="element_row_content_container lightColorbg pad_bot0">
    <label class="txtright bold hight1x width1x">
        <g:message code="vatType.note.label" default="Note" />

    </label>

    <div class='element-input inputContainer'>
        <g:textArea name="note" value="${vatType?.note}" />

    </div>

</div>


  <div class="buttons">
    <span class="button"><input type="button" name="create-button" id="create-button" class="ui-button ui-widget ui-state-default ui-corner-all" value="${message(code: 'default.button.create.label', default: 'Create')}" onclick="executeAjaxVatType();"/></span>
    <span class="button"><input type='button' name="delete-button" id="delete-button" class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete' onclick="deleteAjaxVatType();"/></span>
    <span class="button"><input type="button" name="cancel-button" id="cancel-button" class="ui-button ui-widget ui-state-default ui-corner-all" onclick=" reset_vatType_form('#gFormVatType');" value="Cancel"/></span>
  </div>
</form>
