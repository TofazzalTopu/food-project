<%@ page import="org.codehaus.groovy.grails.commons.ApplicationHolder as AH" %>
%{--
- ****************************************************************
- Copyright © 2010 Documentatm (TM) Limited. All rights reserved.
- DOCUMENTA PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
- This software is the confidential and proprietary information of
- Documentatm (TM) Limited ("Confidential Information").
- You shall not disclose such Confidential Information and shall use
- it only in accordance with the terms of the license agreement
- you entered into with Documentatm (TM) Limited.
- *****************************************************************
--}%

<html>
<head>
  <title><g:layoutTitle default="ACCESS CONTROL PLUGIN"/></title>
  <g:set var="pluginCss" value="${AH.application.config.pluginCss}" />
  %{--StyleNew--}%
  %{--<link rel="stylesheet" href="${resource(dir: 'css', file: 'pluginStyle.css')}"/>--}%
  <link rel="stylesheet" href="${resource(dir: 'css', file:pluginCss )}"/>
  <link rel="stylesheet" href="${resource(dir: 'js/jquery/theme/redmond/', file: 'jquery-ui-1.8.5.custom.css')}"/>
  <link rel="stylesheet" href="${resource(dir: 'js/jquery/jgrowl-1.2.5/', file: 'jquery.jgrowl.css')}"/>
  <link rel="stylesheet" href="${resource(dir: 'js/jquery/jqgrid/css', file: 'ui.jqgrid.css')}"/>
  <link rel="stylesheet" href="${resource(dir: 'js/jquery/jgrowl-1.2.5/', file: 'jquery.jgrowl.css')}"/>
  <link rel="stylesheet" href="${resource(dir: 'js/pluginmessage-renderer/', file: 'pluginmessage-renderer.css')}"/>
  <link rel="stylesheet" href="${resource(dir: 'js/jquery/multiselect/', file: 'ui.multiselect.css')}"/>

   <g:javascript library="jquery" plugin="jquery" src="jquery/jquery-1.6.1.min.js"/>
   <g:javascript library="common" src="jquery/jquery.templateRenderer.js"/>
    <g:javascript library="jquery" src="jquery/jqgrid/i18n/grid.locale-en.js"/>
    <g:javascript library="jquery" src="jquery/jqgrid/jquery.jqGrid.min.js"/>
    <g:javascript library="jquery" src="jquery/jqgrid/lib/grid.base.js"/>
    <g:javascript library="jquery" src="jquery/jqgrid/lib/grid.common.js"/>
    <g:javascript library="jquery" src="jquery/jqgrid/lib/grid.celledit.js"/>
    <g:javascript library="jquery" src="jquery/jqgrid/lib/grid.formedit.js"/>
    <g:javascript library="jquery" src="jquery/jqgrid/lib/grid.inlinedit.js"/>
    <g:javascript library="jquery" src="jquery/jqgrid/lib/jquery.fmatter.js"/>
    <g:javascript library="jquery" src="jquery/jqgrid/lib/grid.grouping.js"/>

    <g:javascript src="jquery/ui/jquery.ui.widget.min.js"/>
    <g:javascript src="jquery/ui/jquery.ui.core.min.js"/>
    <g:javascript src="jquery/ui/jquery.ui.mouse.min.js"/>
    <g:javascript src="jquery/ui/jquery.ui.position.min.js"/>
    <g:javascript src="jquery/ui/jquery.ui.sortable.min.js"/>
    <g:javascript src="jquery/ui/jquery.ui.draggable.min.js"/>
    <g:javascript src="jquery/multiselect/ui.multiselect.js"/>
    <g:javascript src="jquery/multiselect/jquery.scrollTo-min.js"/>
    <g:javascript src="jquery/jgrowl-1.2.5/jquery.jgrowl_compressed.js"/>
    <g:javascript src="pluginmessage-renderer/pluginmessage-renderer.js"/>
    <g:javascript src="jquery/jquery.blockUI.js"/>



  <g:layoutHead/>
  <style type="text/css">
  .ui-layout-resizer {
    display: none !important;
  }

  .layout-node {
    display: none !important;
  }

  .layout-loader {
    text-align: center;
    margin-top: 200px;
  }

  </style>
  <SCRIPT type="text/javascript">
    $(document).ready(function () {
      $('.layout-loader').html('');
      $('.ui-layout-center, .ui-layout-north, .ui-layout-south, .ui-layout-west').removeClass('layout-node');

    });
  </SCRIPT>

</head>
<body>
<DIV class="ui-layout-center layout-node"><div class="sbi-layout-body-container"><g:layoutBody/></div></DIV>
</body>
</html>