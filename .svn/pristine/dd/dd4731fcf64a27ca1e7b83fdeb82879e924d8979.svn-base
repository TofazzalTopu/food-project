<%--
  Created by IntelliJ IDEA.
  User: Feroz
  Date: 2/11/13
  Time: 12:22 PM
  To change this template use File | Settings | File Templates.
--%>
<script type="text/javascript">
var ${flaxBoxVar} = null;
$(document).ready(function(){
    var ${flaxBoxVar}Configuration = ${configuration};
    ${flaxBoxVar}Configuration['width'] = 165;
    <g:if test="${optionKey}">
    ${flaxBoxVar}Configuration['hiddenValue'] = '${optionKey}';
    </g:if>
    <g:if test="${optionValue}">
    ${flaxBoxVar}Configuration['displayValue'] = '${optionValue}';
    </g:if>
    <g:if test="${resultTemplate}">
    ${flaxBoxVar}Configuration['resultTemplate'] = "${resultTemplate}";
    </g:if>
    <g:if test="${watermark}">
    ${flaxBoxVar}Configuration['watermark'] = "${watermark}";
    </g:if>
    ${flaxBoxVar} = new DocuFlaxBox('${div}', '${id}', ${flaxBoxVar}Configuration);
    <g:if test="${data}">${flaxBoxVar}.setData(${data});</g:if>
    <g:if test="${value}">${flaxBoxVar}.val('${value}');</g:if>
});
</script>
<div id="docu-flaxbox-${id}" class="docu-flaxbox">
    <div id="${div}"></div>
    <div class="clear"></div>
    <input type="hidden" id="${id}" name="${name}" value="null" />
</div>