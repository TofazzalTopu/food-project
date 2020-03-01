<% import grails.persistence.Event %>
<% import org.codehaus.groovy.grails.orm.hibernate.support.ClosureEventTriggeringInterceptor as Events %>
<% import org.codehaus.groovy.grails.plugins.PluginManagerHolder %>
<%=packageName%>


<div id="spinner${className}" class="spinner" style="display:none;" align="left">
    <table class="spinnerTable">
        <tbody>
        <tr>
            <td><img src="\${resource(dir: 'images', file: 'spinner.gif')}" alt="Spinner"/></td>
            <td>Communicating with the server... Please wait!</td>
        </tr>
        </tbody>
    </table>
</div>
<form name='gForm${className}' id='gForm${className}'>
  <g:hiddenField name="id" value="\${${propertyName}?.id}" />
  <g:hiddenField name="version" value="\${${propertyName}?.version}" />
    <div id="remote-content-${propertyName}"></div>
    <div>
      <%  excludedProps = Event.allEvents.toList() << 'version' << 'id' << 'dateCreated' << 'lastUpdated' << 'userCreated' << 'userUpdated'
          props = domainClass.properties.findAll { !excludedProps.contains(it.name) }
          Collections.sort(props, comparator.constructors[0].newInstance([domainClass] as Object[]))
          List labelList = []
          List inputList = []
          boolean required = false
          String cssAlign = ""
          String requiredIndicator = ""
          for(List p: props){
             if (!Collection.class.isAssignableFrom(p.type)) {
                 cp = domainClass.constrainedProperties[p.name]
                 display = (cp ? cp.display : true)
                 required = (cp ? !(cp.propertyType in [boolean, Boolean]) && !cp.nullable && (cp.propertyType != String || !cp.blank) : false)
                 if (display) {
                     if(required){
                         requiredIndicator = "<span class=\"mendatory_field\"> * </span>"
                     }else{
                         requiredIndicator = ""
                     }
                     labelList.add("<div class='element-title'>\n <g:message code='${domainClass.propertyName}.${p.name}.label' default='${p.naturalName}' /> \n" + requiredIndicator + " \n</div>")

                 cssAlign = ""
                 if(p.type == double || p.type == Double.class || p.type == float || p.type == Float.class || p.type == int || p.type == Integer.class || p.type == long || p.type == Long.class || p.type == byte || p.type == Byte.class){
                    cssAlign = "setup-css-numeric-currency"
                 }

                 if(p.type == Date.class){
                    inputList.add("<div class='element-input inputContainer'>${renderEditor(p)}</div>\n<script type='text/javascript'>\n\$(document).ready(function(){ \$('#${p.name}').datepicker({dateFormat:'dd-mm-yy',\n changeMonth:true, \nchangeYear:true \n});\n \$('#${p.name}').mask('\${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}',{});});\n </script>")
                 }
                 else{
                    inputList.add("<div class='element-input inputContainer ${cssAlign}'>${renderEditor(p)}</div>")
                 }

                 if(labelList.size() < 4){
                    continue
                 }
      %>
          <div class="element_container_big">
            <div class="block-title">
                <%= labelList.join("") %>
                <div class="clear"></div>
            </div>
            <div class="block-input">
                <%= inputList.join("") %>
                <div class="clear"></div>
            </div>
          </div>

          <%
              if(labelList.size() == 4){
                labelList = []
                inputList = []
              }
          %>
        <%  }   }   } %>

        <% if(labelList.size() > 0){ %>
          <div class="element_container_big">
            <div class="block-title">
                <%= labelList.join("") %>
                <div class="clear"></div>
            </div>
            <div class="block-input">
                <%= inputList.join("") %>
                <div class="clear"></div>
            </div>
          </div>
        <% } %>
  </div>
  <div class="clear"></div>
  <div class="buttons" style="margin-left:10px;">
    <span class="button"><input type="button" name="create-button" id="create-button-${propertyName}" class="ui-button ui-widget ui-state-default ui-corner-all" value="\${message(code: 'default.button.create.label', default: 'Create')}" onclick="executeAjax${className}();"/></span>
    <span class="button"><input type='button' name="delete-button" id="delete-button-${propertyName}" class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete' onclick="deleteAjax${className}();"/></span>
    <span class="button"><input name="clearFormButton${className}" class="ui-button ui-widget ui-state-default ui-corner-all" type="button" onclick=" reset_form('#gForm${className}');" value="Cancel"/></span>
  </div>
</form>
