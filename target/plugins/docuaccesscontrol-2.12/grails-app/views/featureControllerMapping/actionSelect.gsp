<%--
  Created by IntelliJ IDEA.
  User: Rashed
  Date: 5/5/11
  Time: 5:05 PM
  To change this template use File | Settings | File Templates.
--%>
%{--<select id="controller-list[${j}]" class="multiselect controllerList" multiple="multiple" name="items.featureControllerMapping[${j}].controllerAction" style="width:603px; height:200px;">--}%
  %{--<%--}%
    %{--map.each {key, val ->--}%
      %{--if (selected.contains(val)) {--}%
        %{--out << "<option value=\"${val}\" selected=\"selected\">${val}</option>"--}%
%{--//        out << "<option value=\"${key}\" selected=\"selected\">${val}</option>"--}%
      %{--} else {--}%
        %{--out << "<option value=\"${val}\">${val}</option>"--}%
%{--//        out << "<option value=\"${key}\">${val}</option>"--}%
      %{--}--}%
    %{--}--}%
  %{--%>--}%
%{--</select>--}%

<select id="controller-list[${j}]" class="multiselect controllerList" multiple="multiple" name="items.featureControllerMapping[${j}].controllerAction" style="width:603px; height:200px;">
  <%
    map.each {key, val ->
      if (selected.contains(key)) {
        out << "<option value=\"${key}\" selected=\"selected\">${key}</option>"
//        out << "<option value=\"${key}\" selected=\"selected\">${val}</option>"
      } else {
        out << "<option value=\"${key}\">${key}</option>"
//        out << "<option value=\"${key}\">${val}</option>"
      }
    }
  %>
</select>