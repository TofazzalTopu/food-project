<%--
  Created by IntelliJ IDEA.
  User: bipul.kumar
  Date: 4/28/11
  Time: 12:27 PM
  To change this template use File | Settings | File Templates.
--%>

<div class="block-input" style="padding-top:5px;">
    <g:hiddenField name='items.featureAction[${i}].isDeleted' id='featureAction${i}.isDeleted' value='false'/>
    <div class="ui-icon ui-icon-arrowthick-2-n-s" style="float: left; width:12px; display: block"></div>
    <div style="float: left; width:20px; display: none"><input class="items-position"
                                                               name="items.featureAction[${i}].position"
                                                               value='${featureActionInstance?.position}'
                                                               type="text"/></div>
    <g:if test="${featureActionInstance?.actionCode == null}">
        <div class="element-input" style="width:70px;"><g:textField style="width:60px; height: 16px;"
                                                                    name="items.featureAction[${i}].actionCode"
                                                                    readonly="readonly"
                                                                    value="AUTO"/></div>
    </g:if>
    <g:else>
        <div class="element-input" style="width:70px;"><g:textField style="width:60px; height: 16px;"
                                                                    name="items.featureAction[${i}].actionCode"
                                                                    readonly="readonly"
                                                                    value='${featureActionInstance?.actionCode}'/></div>
    </g:else>

    <g:if test="${featureActionInstance?.actionName != null}">
        <div class="element-input inputContainer value ${hasErrors(bean: featureActionInstance, field: 'actionName', 'errors')}" style="width:170px;">
            <g:textField style="width:170px; height: 16px;"
                         name="items.featureAction[${i}].actionName"
                         value='${featureActionInstance?.actionName}'
                         class="validate[required]"
            /></div>

        <div><g:hiddenField name="items.featureAction[${i}].id" value='${featureActionInstance?.id}'/></div>
    </g:if>
    <g:else>
        <div class="element-input inputContainer value ${hasErrors(bean: featureActionInstance, field: 'actionName', 'errors')}" style="width:170px;">
            <g:textField style="width:170px; height: 16px;"
                         name="items.featureAction[${i}].actionName"
                         value=""
                         class="validate[required]"
            /></div>

        <div><g:hiddenField name="items.featureAction[${i}].id" value=''/></div>
    </g:else>
    <g:if test="${featureActionInstance?.actionName != null}">
        <div class="element-input" style="width:140px;"><g:textField style="width:140px; height: 16px;"
                                                                     name="items.featureAction[${i}].Description"
                                                                     value='${featureActionInstance?.description}'/></div>
    </g:if>
    <g:else>
        <div class="element-input" style="width:140px;"><g:textField style="width:140px; height: 16px;"
                                                                     name="items.featureAction[${i}].Description"
                                                                     value=""/></div>
    </g:else>

    <div id="showMenuUrls_${i}_item" name="items.featureAction[${i}].showMenuUrls" style="width:300px;" class="element-input" ></div>
    <g:textField name="items.featureAction[${i}].menuUrl" id="items_featureAction${i}_menuUrl" value="${featureActionInstance?.menuUrl}" style="display: none"/>



    <div style="display:table-cell; padding-left:15px; float: right;" class="element-input button-delete"
         onclick="document.getElementById('featureAction${i}.isDeleted').value = 'true';
         $(this).parent().hide()"></div>

    <div class="clear"></div>
</div>