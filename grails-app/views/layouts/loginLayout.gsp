<%@ page import="com.docu.app.ApplicationConfigUtil" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

  <title><g:layoutTitle default="..::LOGIN::.."/></title>

  <link rel="shortcut icon" href="${resource(dir: 'images/layout', file: 'bdfe_favicon.gif')}" type="image/x-icon"/>

  <link rel="stylesheet" href="${resource(dir: 'css', file: 'login.css')}"/>

  <g:layoutHead/>
   <g:javascript library="jquery" plugin="jquery" src="jquery/jquery-1.4.4.min.js"/>                                    %{--${PathReference.JQUERY_JQUERY_MIN}--}%

  <script type="text/javascript">
            $(document).ready(function() {
            $("input::visible:first").focus();
          });
  </script>
</head>
<body>

<div id='contentHolder' style='overflow-y:auto;overflow-x:auto;padding:10px'>
  <g:layoutBody/>
</div>

</body>
</html>
