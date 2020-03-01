<%@ page import="com.docu.accesscontrol.MenuGroup" %>
<%--
  Created by IntelliJ IDEA.
  User: bipul.kumar
  Date: 2/29/12
  Time: 6:18 PM
  To change this template use File | Settings | File Templates.
--%>
<script type="text/javascript">
$(document).ready(function(){
    $( "#menu-group-sort").sortable({
        stop: function(event, ui) {
            MenuGroupSort.reIndexPosition('sortable-input');
        }
    });
    MenuGroupSort.reIndexPosition('sortable-input');
});
</script>
<ul id="menu-group-sort" style="padding: 0px;">
    <input type="hidden" name="cmd" id="cmd" value="saveMenuGroupSorting" />
    <g:each in="${menuGroupList}" var="menuGroup">
        <li class="sort-item-css">
            <input type="hidden" name="items.menuGroup[${menuGroup.id}].id" value="${menuGroup.id}" />
            <div style="float: left;">
                <input class="sortable-input" type="hidden" name="items.menuGroup[${menuGroup.id}].position" value="${menuGroup.position}">${menuGroup.title}
            </div>
            <div style="float:right; margin-left: 50px;">
                <a href="javascript:void(0);" onclick="MenuGroupSort.listMenuItem(${menuGroup.id})"> Sort </a>
            </div>
            <div style="float:right; margin-left: 50px;">
                <a href="javascript:void(0);" onclick="MenuGroupSort.editMenuGroup(${menuGroup.id},'${menuGroup.title}')">Edit</a>
            </div>
            <div style="clear:both;"></div>
        </li>
    </g:each>
</ul>