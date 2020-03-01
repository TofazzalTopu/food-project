<%--
  Created by IntelliJ IDEA.
  User: bipul
  Date: 2/13/12
  Time: 3:37 PM
  To change this template use File | Settings | File Templates.
--%>


   <div id="feature_info-table">
    <g:each var="featureInstance" in="${featureInstance.sort{it.position}}" status="i">

     <div  class="block-input" style="padding-top:5px;">
        <g:hiddenField name="items.featureInfo[${i}].id" id="featureInfoId[${i}]" value='${featureInstance?.id}'/>

        <div style="float: left; width:20px;display: none "><input class="items-position"
        name="items.featureInfo[${i}].position"
        value='${featureInstance?.position}'
        type="text" /></div>

        <div class="element-input" style="width:250px;"><g:textField style="width:200px;"
                                                                     name="items.featureInfo[${i}].featureName"
                                                                     value='${featureInstance?.featureName}'
                                                                     readonly="readonly"/></div>


        <div class="element-input" style="width:250px;"><g:textField style="width:180px;"
                                                                     name="items.featureInfo[${i}].description"
                                                                     value='${featureInstance?.description}'
                                                                     readonly="readonly"/></div>

        <div class="clear"></div>
        <br>
    </div>
    </g:each>
   <div class="clear"></div>
   <div class="buttons">
      <input type="button"
              class="ui-button ui-widget ui-state-default ui-corner-all"
              value="<g:message code="accountsLedgerMapping.saveButton.label" default="Arrange"/>"
              onclick="arrangeFeatureInfo()"/>
    </div>
</div>