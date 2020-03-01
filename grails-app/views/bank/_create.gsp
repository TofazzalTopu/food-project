<%@ page import="com.bits.bdfp.common.Bank" %>
<form name='gFormBank' id='gFormBank'>
    <g:hiddenField name="id" value="${bank?.id}"/>
    <g:hiddenField name="version" value="${bank?.version}"/>
    <g:hiddenField name="enterpriseConfiguration.id" id="enterpriseConfiguration" value=""/>
    <div id="remote-content-bank"></div>
    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code='depositPool.enterprise.label' default='Enterprise' />
            <span class="mendatory_field"> * </span>
        </label>
        <g:if test="${list}">
            <g:if test="${list.size()==1}">
                <div  class='element-input inputContainer'>
                    <g:textField name="enterPriseName" readonly="readonly" value="${list[0].name}" />
                    <script type="text/javascript">
                        $(document).ready(function(){
                            $("#enterpriseConfiguration").val("${list[0].id}");
                        })
                    </script>
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
                                    $("#enterpriseConfiguration").val("");
                                }
                            });

                        });
                    </script>
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
            <g:message code="bank.code.label" default="Bank Code" />
            <span class="mendatory_field"> * </span>
        </label>

        <div class='element-input inputContainer'>
            <g:textField class="validate[required]" name="code" value="${bank?.code}" maxlength="20"/>

        </div>

    </div>
    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code="bank.name.label" default="Bank Name" />
            <span class="mendatory_field"> * </span>
        </label>

        <div class='element-input inputContainer'>
            <g:textField class="validate[required]" name="name" value="${bank?.name}" maxlength="50"/>
        </div>

    </div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code="bank.note.label" default="Note" />
        </label>

        <div class='element-input inputContainer'>
            <g:textArea name="note" value="${bank?.note}" maxlength="512"/>
        </div>

    </div>
    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code="bank.isActive.label" default="Is Active" />
        </label>

        <div class='element-input inputContainer'>
            <g:checkBox name="isActive" value="${bank?.isActive}" checked="checked"/>
        </div>

    </div>

    <div class="buttons">
        <span class="button"><input type="button" name="create-button" id="create-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="${message(code: 'default.button.create.label', default: 'Create')}"
                                    onclick="executeAjaxBank();"/></span>
        <span class="button"><input type='button' name="delete-button" id="delete-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete'
                                    onclick="deleteAjaxBank();"/></span>
        <span class="button"><input type="button" name="cancel-button" id="cancel-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    onclick=" reset_form('#gFormBank');" value="Cancel"/></span>
    </div>
</form>
