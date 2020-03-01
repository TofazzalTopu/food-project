<%@ page import="com.bits.bdfp.geolocation.TerritorySubArea" %>
<style type="text/css">
    html .ui-multiselect{
        width: 650px!important;
    }
</style>
<script type="text/javascript">
    $(document).ready(function(){
        $("#territorySubAreaList").multiselect(
                $.extend($.ui.multiselect, {
                    locale: {
                        addAll:'Add All',
                        removeAll:'Remove All',
                        itemsCount:'Geographic Location selected'
                    }
                })
        );
    });
</script>
<g:select name="territorySubAreaList" id="territorySubAreaList"
          class="multiselect" multiple="multiple"
          from="${availableTerritorySubArea}"
          optionKey="id"
          optionValue="name" size="12"
          value="${selectedTerritorySubArea}"/>