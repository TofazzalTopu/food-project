<%@ page import="com.docu.accesscontrol.commons.PluginGrailsClassUtil" %>
<%--
  Created by IntelliJ IDEA.
  User: bipul.kumar
  Date: 5/4/11
  Time: 11:24 AM
  To change this template use File | Settings | File Templates.
--%>
<script type="text/javascript">
  var feature_action_template_index = ${featureControllerClassListDb ? featureControllerClassListDb.size():0}
</script>
<script type="text/html" id="feature_action_template">
  <g:render plugin="docuaccesscontrol" template="tabs/actionMapping" model="['i':'#{i}']"/>
</script>

<div>
  <fieldset>
    <div id="feature-action-table">

      <g:each var="featureControllerClassDb" in="${featureControllerClassListDb}" status="i">
        <g:render plugin="docuaccesscontrol" template="tabs/actionMapping" model="[controllerClassList: controllerClassList, featureControllerClassDb:featureControllerClassDb, 'i':i]"/>
        <script type="text/javascript">
          $(document).ready(function() {
            $('#controllerList-${i}').change();
          });
        </script>
      </g:each>
    </div>
    <br>

    <div>
      <span class="button"><input type="button"
              name="submit" class="ui-button ui-widget ui-state-default ui-corner-all"
              value="Add Controller"
              onclick="addActionTemplate($('#feature-action-table'), $('#feature_action_template'), feature_action_template_index);
              feature_action_template_index++;"/></span>
    </div>

  </fieldset>
</div>


