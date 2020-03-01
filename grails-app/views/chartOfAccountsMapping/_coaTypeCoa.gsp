<style>
html .ui-multiselect {
    width: 1000px !important;
}
.ui-multiselect div.available {
    width: 500px !important;
}

.ui-multiselect div.selected {
    width: 500px !important;
    border-left: 1px dashed #cccddd;
}

.ui-multiselect .count {
    font-size: 15px !important;
}
</style>
<script type="text/javascript">
    $(document).ready(function() {
        $("#coaMapping-list").multiselect();
    })
</script>
<h3 style="margin-bottom:0">Chart of Accounts Type to Chart of Accounts Mapping</h3>
<div class="clear" style="height: 2px;"></div>
<div id="coaMapping-list-block">
    <g:select id="coaMapping-list" class="multiselect" multiple="multiple" name="coaMapping-list"
              style="width:1000px; height:350px;" from="${chartOfAccountsList}" optionKey="id" optionValue="name" value="${selectedChartOfAccounts}">
    </g:select>
</div>
