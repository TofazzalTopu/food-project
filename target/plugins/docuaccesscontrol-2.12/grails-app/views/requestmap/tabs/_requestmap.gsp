<%--
  Created by IntelliJ IDEA.
  User: bipul.kumar
  Date: 5/10/11
  Time: 1:46 PM
  To change this template use File | Settings | File Templates.
--%>

<g:textField name="editMode" id="editMode" style="display: none" value='${editMode}'/>
<% featureAction.eachWithIndex {key, itemList, i -> %>
<tr>
    <td id="featureName[${i}]">
        %{--<g:textField name="items.requestMap,${i},${key}.featureName" id="requestMap,${i},${key}.featureName" value='${key}' />--}%
        <g:textField name="items.item[${i}].featureName" id="featureName.${key}" value='${key}'/>
        %{--${key}--}%
    </td>
    <td id="featureAction[${i}]">
        <g:each in="${itemList}" var="item" status="j">
            <input type="checkbox" class="request-map-checkbox" id="checkAction_${item.id}" name="items.item[${i}].actions.[${j}].featureAction"
                   value="${item.actionName}"/> ${item.actionName} &nbsp;
        </g:each>
    </td>
</tr>

<% } %>

%{--test slot--}%
<script type="text/javascript">
    $(document).ready(function() {
        <% checkedAction.eachWithIndex{key, itemList, i -> %>
        <g:each in="${itemList}" var="item">
        $("#checkAction_${item.id}").attr("checked", true)
        </g:each>
        <% } %>
    });
</script>




