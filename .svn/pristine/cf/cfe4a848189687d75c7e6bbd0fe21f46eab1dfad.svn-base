<link rel="stylesheet" href="${resource(dir: 'js/jquery/multiselect/', file: 'ui.multiselect.css')}"/>
<g:javascript src="jquery/ui/jquery.ui.draggable.min.js"/>
<g:javascript src="jquery/multiselect/jquery.scrollTo-min.js"/>
<g:javascript src="jquery/multiselect/ui.multiselect.js"/>
<style>
.ui-multiselect div.available {
    width: 255px !important;
}

.ui-multiselect div.selected {
    width: 255px !important;
    border-left: 1px dashed #cccddd;
}

.ui-multiselect .count {
    font-size: 10px !important;
}
</style>
<script type="text/javascript">
    $(document).ready(function() {
        $("#userDistributionPoint-list").multiselect();
    })
</script>
<g:select id="userDistributionPoint-list" class="multiselect" multiple="multiple" name="nDistributionPoint"
          style="width:515px; height:200px;" from="${distributionPointList}" optionKey="id" value="${selectedDistributionPointList}">
</g:select>