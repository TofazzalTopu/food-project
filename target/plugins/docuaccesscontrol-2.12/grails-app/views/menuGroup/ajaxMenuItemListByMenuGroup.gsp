<%--
  Created by IntelliJ IDEA.
  User: bipul.kumar
  Date: 2/29/12
  Time: 7:31 PM
  To change this template use File | Settings | File Templates.
--%>

<script type="text/javascript">
    $(document).ready(function(){
        $( "#menu-item-sort").sortable({
            stop: function(event, ui) {
                MenuGroupSort.reIndexPosition('sortable-input');
            }
        });
        MenuGroupSort.reIndexPosition('sortable-input');
    });
</script>
<p style="margin-bottom: 10px; padding-left: 10px;">
    <a href="javascript:void(0)" onclick="MenuGroupSort.onModuleInfoChange($('#moduleList').val())">Back</a>
    &nbsp;&nbsp;|&nbsp;&nbsp;
    ${menuGroup.title}
</p>
<ul id="menu-item-sort" style="padding: 0px;">
    <input type="hidden" name="cmd" id="cmd" value="saveMenuItemSorting" />
    <g:each in="${menuItemList}" var="menuItem">
        <li class="sort-item-css">
            <input type="hidden" name="items.menuItem[${menuItem.id}].id" value="${menuItem.id}" />
            <div style="float: left;">
                <input class="sortable-input" type="hidden" name="items.menuItem[${menuItem.id}].position" value="${menuItem.position}">${menuItem.featureName}
            </div>
            <div style="clear:both;"></div>
        </li>
    </g:each>
</ul>