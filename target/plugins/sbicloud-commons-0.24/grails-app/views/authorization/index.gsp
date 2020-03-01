<head>
  <meta name="layout" content="main" />
  <title>Dashboard</title>
  <link rel="stylesheet" href="%{--
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

${resource(dir: 'js', file: 'jquery/jqueryportlet/css/style.css')}"/>
  <g:javascript src="jquery/jqueryportlet/interface.js"/>
  <g:javascript src="jquery/jqueryportlet/dashboard.js"/>

  <script type="text/javascript">
    $(document).ready(function(){
      MyDashboard.init('closeEl', 'groupWrapper');
    });
  </script>  
</head>

             


<div>
<%
  def labels = ['MGF-1001', 'MCF-2102', 'MCF-1280']
  def loanlabels = ['Current', 'Late1', 'Late2', 'NIBL']
  def loanstatus = [8876, 1345, 353, 87]
  def colors = ['009999', '006B6B', 'BFFFFE']
  def colors1 = ['006B6B', 'C6D9FD', 'C6D9FD']
  def values = [350, 450, 100]
  def legends = ['MGF-1001:350K', 'MCF-2102:450K', 'MCF-1280:100K']
  def values2 = [[35, 45, 10], [3, 987, 2]]
  def values5 = [[0, 16.7, 23.3, 33.3, 60, 76.7, 83.3, 86.7, 93.3, 96.7, 100], [30, 45, 20, 50, 15, 80, 60, 70, 40, 55, 80], [0, 10, 16.7, 26.7, 33.3], [50, 10, 30, 55, 60]]
  def values3 = [97, 12, 454, 12, 5, 32, 78, 4, 99, 89, 98, 77, 7, 77]
  def values4 = [[97, 12, 454, 12, 5, 32, 78, 4, 99, 89, 98, 77, 7, 77], [1, 6, 42, 15, 78, 94, 26, 45, 12, 10, 21, 22, 33, 33]]
  def values6 = [[-500, 30, 700, 253], [2, -5, 3, 6]]
  def values7 = [97, 62, 78, 89, 98, 77, 64]
  def values8 = [7, 2, 8, 9, 8, 7, 4]
%>

<%
  portletrowid = "sort1"
  portletitemid = "currentproject"
  portletitemheader = "Currently active projects IN THIS BRANCH"
%>

%{--<div id="sort1" class="groupWrapper">--}%
%{--<g:render template="/shared/bargraph" model="[portletrowid:portletrowid,--}%
%{--portletitemid:portletitemid,--}%
%{--portletitemheader:portletitemheader,--}%
%{--labels:labels,--}%
%{--values:values,--}%
%{--colors:colors]"/>--}%
%{--<g:render template="/shared/bargraph" model="[portletrowid:portletrowid,--}%
%{--portletitemid:portletitemid,--}%
%{--portletitemheader:portletitemheader,--}%
%{--labels:labels,--}%
%{--values:values,--}%
%{--colors:colors]"/>--}%
%{--<p>&nbsp;</p>--}%
%{--</div>--}%
%{--<div id="sort1" class="groupWrapper ui-corner-all ui-widget-content">--}%
  %{--<div id="currentprojects" class="groupItem">--}%
    %{--<div class="itemHeader">Currently Running Projects IN THIS BRANCH<a href="#" class="closeEl">[-]</a></div>--}%
    %{--<div class="itemContent">--}%
      %{--<g:pieChart title=''--}%
              %{--size="${[400,240]}"--}%
              %{--colors="${colors}"--}%
              %{--labels="${labels}"--}%
              %{--fill="${'bg,s,efefef'}"--}%
              %{--legend="${legends}"--}%
              %{--dataType='simple'--}%
              %{--data='${values}'/>--}%
    %{--</div>--}%
  %{--</div>--}%
  %{--<div id="branchsummary" class="groupItem">--}%
    %{--<div class="itemHeader ui-widget-header ui-state-default">Current status<a href="#" class="closeEl">[-]</a></div>--}%
    %{--<div class="itemContent">--}%
      %{--<br/>--}%
      %{--<ul>--}%
        %{--<li>Total number of borrowers:     12,893</li>--}%
        %{--<li>Total disbursed:            80,76,593</li>--}%
        %{--<li>Total outstanding:          70,76,593</li>--}%
        %{--<li>Total current loan:             8,876</li>--}%
        %{--<li>Total number of late1:          1,345</li>--}%
        %{--<li>Total number of late2:            353</li>--}%
        %{--<li>Total number of NIBL:              87</li>--}%
      %{--</ul>--}%
      %{--<br/>--}%
      %{--<g:barChart title='Loan status'--}%
              %{--size="${[300,200]}"--}%
              %{--colors="FFCC00|B28F00|FEE680|FF0000"--}%
              %{--type="bvs"--}%
              %{--labels="${labels}"--}%
              %{--axes="x,y"--}%
              %{--axesLabels="${[0:['Current','Late1','Late2','NIBL']]}"--}%
              %{--fill="${'bg,s,ffffff'}"--}%
              %{--dataType='simple'--}%
              %{--data='${loanstatus}'/>--}%
    %{--</div>--}%
  %{--</div>--}%
  %{--<p>&nbsp;</p>--}%
%{--</div>--}%

<div id="sort2" class="groupWrapper ui-corner-all ui-widget-content">
  <div id="todaystarget" class="groupItem">
    <div class="itemHeader ui-widget-header ui-state-default">Today's Target<a href="#" class="closeEl">[-]</a></div>
    <div class="itemContent">
      <br/>
      <g:barChart title=''
              size="${[200,200]}"
              colors="${colors}"
              type="bhs"
              labels="${labels}"
              axes="x,y"
              axesLabels="${[1:['CO-1001','CO-1002','CO-1003','CO-1004','CO-1005','CO-1006','CO-1007']]}" fill="${'bg,s,ffffff'}" dataType='text' data='${values7}'/>
    </div>
  </div>
  </div>
<div id="sort3" class="groupWrapper  ui-corner-all ui-widget-content">
  <div id="todaysachievement" class="groupItem">
    <div class="itemHeader ui-widget-header ui-state-default">Today's Achievement<a href="#" class="closeEl">[-]</a></div>
    <div class="itemContent">
      <br/>
      <g:barChart title=''
              size="${[200,200]}"
              colors="${colors1}"
              type="bhs"
              labels="${labels}"
              axes="x,y"
              axesLabels="${[1:['CO-1001','CO-1002','CO-1003','CO-1004','CO-1005','CO-1006','CO-1007']]}" fill="${'bg,s,ffffff'}" dataType='text' data='${values8}'/>
    </div>
  </div>
  <p>&nbsp;</p>
</div>
%{--<div id="sort3" class="groupWrapper">--}%
  %{--<div id="mymailbox" class="groupItem">--}%
    %{--<div class="itemHeader">My mailbox<a href="#" class="closeEl">[-]</a></div>--}%
    %{--<div class="itemContent">--}%
      %{--You have two new message(s)--}%
    %{--</div>--}%
  %{--</div>--}%
  %{--<div id="noticeboard" class="groupItem">--}%
    %{--<div class="itemHeader">Today's Notice<a href="#" class="closeEl">[-]</a></div>--}%
    %{--<div class="itemContent">--}%
      %{--No new notices--}%
    %{--</div>--}%
  %{--</div>--}%
  %{--<div id="circular" class="groupItem">--}%
    %{--<div class="itemHeader">New circular<a href="#" class="closeEl">[-]</a></div>--}%
    %{--<div class="itemContent">--}%
      %{--<ul>--}%
        %{--<li>LoanID: 174873478</li>--}%
        %{--<li>LoanID: 174873478</li>--}%
        %{--<li>LoanID: 174873478</li>--}%
        %{--<li>LoanID: 174873478</li>--}%
        %{--<li>LoanID: 174873478</li>--}%
        %{--<li>LoanID: 174873478</li>--}%
        %{--<li>LoanID: 174873478</li>--}%
      %{--</ul>--}%
    %{--</div>--}%
  %{--</div>--}%
  <p>&nbsp;</p>
</div>
