<%--
  Created by IntelliJ IDEA.
  User: bipul.kumar
  Date: 5/4/11
  Time: 11:06 AM
  To change this template use File | Settings | File Templates.
--%>

<style>
  .ui-multiselect div.available{width:250px !important;}
  .ui-multiselect div.selected{width:250px !important; border-left:1px dashed #cccddd;}
  .ui-multiselect .count {  font-size:10px !important;}
</style>
<script type="text/javascript">
  $(document).ready(function() {
    $("#controller-list").multiselect();
  });
</script>

<div>
%{--<g:select id="controller-list" class="multiselect" multiple="multiple" name="controller-list" style="width:503px; height:200px;" from="${schemeList}" optionKey="id" value="${selectedSchemeIdList}">--}%
%{--</g:select>--}%
</div>