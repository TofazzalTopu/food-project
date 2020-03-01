<%--
  Created by IntelliJ IDEA.
  User: bipul.kumar
  Date: 5/24/11
  Time: 12:25 PM
  To change this template use File | Settings | File Templates.
--%>
<g:if test="${flash.message}">
  <script type="text/javascript">
    $(document).ready(function(){
      MessageRenderer.render(${flash.message})
    });
  </script>
</g:if>
<g:if test="${message}">
  <script type="text/javascript">
    $(document).ready(function(){
      MessageRenderer.render(${message})
    });
  </script>
</g:if>