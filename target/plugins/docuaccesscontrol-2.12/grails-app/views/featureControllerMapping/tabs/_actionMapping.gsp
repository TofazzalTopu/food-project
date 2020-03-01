<%@ page import="com.docu.accesscontrol.ModuleInfo" %>
<%--
  Created by IntelliJ IDEA.
  User: bipul.kumar
  Date: 5/4/11
  Time: 12:09 PM
  To change this template use File | Settings | File Templates.
--%>

<style>
.ui-multiselect div.available {
  width: 300px !important;
}

.ui-multiselect div.selected {
  width: 300px !important;
  border-left: 1px dashed #cccddd;
}

.ui-multiselect .count {
  font-size: 10px !important;
}
</style>
<div>

  <div class="block-input">
  <g:hiddenField name='items.featureControllerMapping[${i}].isDeleted' id='featureControllerMapping${i}.isDeleted' value='false'/>
  <div class="block-title">
    <div class="element-title">Controller</div>
    <div class="clear"></div>
  </div>

     <div class="element-input" style="float:left; "><g:select name="items.featureControllerMapping[${i}].controllerName" id="controllerList-${i}"
            from="${controllerClassList}"
            optionKey="fullName"
            optionValue="shortName"
            value="${featureControllerClassDb?.fullName}"
            noSelection="['null':'-Select Controller-']"
            onChange="loadControllerAction(this.value, ${i})"/></div>

    <div style="float:right; width:20px; height:20px; margin-top:0px; padding-left:300px" class="element-input button-delete" onclick="document.getElementById('featureControllerMapping${i}.isDeleted').value = 'true';
       $(this).parent().hide()"></div>

  <div class="clear"></div>
    <br/>
    <div id="closer-list-${i}">

</div>

  </div>

</div>

