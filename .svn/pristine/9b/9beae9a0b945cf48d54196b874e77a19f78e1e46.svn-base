<%--
  Created by IntelliJ IDEA.
  User: bipul.kumar
  Date: 6/8/11
  Time: 5:28 PM
  To change this template use File | Settings | File Templates.
--%>

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
        $("#userEnterprise-list").multiselect();
    })
</script>
<g:select id="userEnterprise-list" class="multiselect" multiple="multiple" name="nEnterprise"
          style="width:515px; height:200px;" from="${enterpriseList}" optionKey="id" value="${selectedEnterpriseList}">
</g:select>