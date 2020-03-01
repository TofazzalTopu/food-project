<%@ page import="com.docu.commons.CommonConstants;com.docu.commons.ModuleInfoUtil" %>


<div class="top_nav">
<!-- top navigation begin -->
  <ul>

        <li><a href="#">Agent Banking</a></li>

    <g:each in="${session.allowedModuleList}" var="moduleId">
        <li <g:if test="${session.moduleInfoId == moduleId }">class="select"</g:if>><a href="javascript:void(0);" onclick="MenuManager.setLeftMenu(${moduleId}, '${request.contextPath}/myDashboard')"><img src="${request.contextPath}/images/icon_${ModuleInfoUtil.instance.get(moduleId)?.fileName}.png" alt="${ModuleInfoUtil.instance.get(moduleId)?.fileName}" align="absmiddle"/>${ModuleInfoUtil.instance.get(moduleId)?.moduleShortName}</a></li>
    </g:each>
    %{--<li <g:if test="${session.moduleInfoId == CommonConstants.MODULE_INFO_ACCOUNTS}">class="select"</g:if>><a href="javascript:void(0);" onclick="MenuManager.setLeftMenu(${CommonConstants.MODULE_INFO_ACCOUNTS}, '${request.contextPath}/myDashboard')"><img src="${request.contextPath}/images/icon_accounts.png" alt="accounts" align="absmiddle"/> Accounts</a></li>--}%
    %{--<li <g:if test="${session.moduleInfoId == CommonConstants.MODULE_INFO_MICROFINANCE}">class="select"</g:if>><a href="javascript:void(0);" onclick="MenuManager.setLeftMenu(${CommonConstants.MODULE_INFO_MICROFINANCE}, '${request.contextPath}/myDashboard')"><img src="${request.contextPath}/images/icon_microfinance.png" alt="microfinance" align="absmiddle"/> <g:if test="${session.Office?.programName}">${session.Office?.programName}</g:if><g:else>Microfinance</g:else></a></li>--}%
    %{--<li <g:if test="${session.moduleInfoId == CommonConstants.MODULE_INFO_HR}">class="select"</g:if>><a href="javascript:void(0);" onclick="MenuManager.setLeftMenu(${CommonConstants.MODULE_INFO_HR}, '${request.contextPath}/myDashboard')"><img src="${request.contextPath}/images/icon_human_resource.png" alt="human resource" align="absmiddle"/> HR</a></li>--}%
    %{--<li <g:if test="${session.moduleInfoId == CommonConstants.MODULE_INFO_PROCUREMENT}">class="select"</g:if>><a href="javascript:void(0);" onclick="MenuManager.setLeftMenu(${CommonConstants.MODULE_INFO_PROCUREMENT}, '${request.contextPath}/myDashboard')"><img src="${request.contextPath}/images/icon_proc.png" alt="procurement" align="absmiddle"/> PROC</a></li>--}%
    %{--<li <g:if test="${session.moduleInfoId == CommonConstants.MODULE_INFO_ADMIN}">class="select"</g:if>><a href="javascript:void(0);" onclick="MenuManager.setLeftMenu(${CommonConstants.MODULE_INFO_ADMIN},'${request.contextPath}/myDashboard')"><img src="${request.contextPath}/images/icon_office.png" alt="admin" align="absmiddle"/> Administration</a></li>--}%
    %{--<li <g:if test="${session.moduleInfoId == CommonConstants.MODULE_INFO_FIXED_ASSET}">class="select"</g:if>><a href="javascript:void(0);" onclick="MenuManager.setLeftMenu(${CommonConstants.MODULE_INFO_FIXED_ASSET}, '${request.contextPath}/myDashboard')"><img src="${request.contextPath}/images/icon_fixedAsset.png" alt="fixed asset" align="absmiddle"/> Fixed Asset</a></li>--}%
    %{--<li <g:if test="${session.moduleInfoId == CommonConstants.MODULE_INFO_STORE}">class="select"</g:if>><a href="javascript:void(0);" onclick="MenuManager.setLeftMenu(${CommonConstants.MODULE_INFO_STORE},'${request.contextPath}/myDashboard')"><img src="${request.contextPath}/images/icon_store.png" alt="Store" align="absmiddle"/> Store</a></li>--}%
    %{--<li <g:if test="${session.moduleInfoId == CommonConstants.MODULE_INFO_PROPOSAL_TRACKING}">class="select"</g:if>><a href="javascript:void(0);" onclick="MenuManager.setLeftMenu(${CommonConstants.MODULE_INFO_PT},'${request.contextPath}/myDashboard')"><img src="${request.contextPath}/images/icon_donor.png" alt="ProposalTracking" align="absmiddle"/> PT</a></li>--}%
    %{--<li <g:if test="${session.moduleInfoId == CommonConstants.MODULE_INFO_DONOR}">class="select"</g:if>><a href="javascript:void(0);" onclick="MenuManager.setLeftMenu(${CommonConstants.MODULE_INFO_DONOR},'${request.contextPath}/myDashboard')"><img src="${request.contextPath}/images/icon_donor.png" alt="Donor" align="absmiddle"/> Donor</a></li>--}%
    %{--<li <g:if test="${session.moduleInfoId == CommonConstants.MODULE_INFO_BUDGET}">class="select"</g:if>><a href="javascript:void(0);" onclick="MenuManager.setLeftMenu(${CommonConstants.MODULE_INFO_BUDGET},'${request.contextPath}/myDashboard')"><img src="${request.contextPath}/images/icon_donor.png" alt="Budget" align="absmiddle"/> Budget</a></li>--}%
  </ul>
<!-- top navigation end -->
</div>

<script type="text/javascript">
    $(document).ready(function() {
//        alert($(window).width())
//        if (($(window).width()) <= 974) {
        if (($(window).width()) <= 1024) {
            var count = 0;
//            halfTotalWidth = Number($(window).width())/2
//            console.log('halfTotalWidth: '+ halfTotalWidth)
            totalLength = $('.top_nav li').size();
//            console.log('totalLength: '+totalLength);
            halfLength = totalLength/2;
            totalWidth = 0
            for(j=1;(j<=totalLength);j++){
//                console.log('j: '+ j)
//                console.log('j width: '+ $('.top_nav li:nth-child('+j+')').width())
//                totalWidth = Number(totalWidth) + Number($('.top_nav li:nth-child('+j+')').width())+6;
                totalWidth = Number(totalWidth) + Number($('.top_nav li:nth-child('+j+')').outerWidth(true));
            }
//            console.log('total width: '+ totalWidth)

            $(".header_top_bg").prepend("<div class='scrollL'></div>");
            $(".header_top_bg").append("<div class='scrollR'></div>");
            $(".top_nav").css({'height': '25', 'overflow': 'hidden', 'margin-left': '25px', 'position': 'relative', 'width': '410'});
            $(".top_nav ul").css({'width': totalWidth, 'margin-left': '0'});


            i = 1
            $(".header_top_bg .scrollL").click(function() {
                if(i>1){i=i-1;}
                var pulse = $('.top_nav li:nth-child('+i+')').width();
//                console.log('scrollL.pulse: '+pulse)
                pulse = Number(pulse) + 5;
                count = Number(count) + Number(pulse);
                if(count>0){count=0}
                $(".top_nav ul").animate({left: count }, 1000);
                $(".header_top_bg .scrollR").fadeIn('slow');
//                console.log('count '+count)
                if(count >= 0){
                    $(".header_top_bg .scrollL").fadeOut('slow');
                }
            });
            $(".header_top_bg .scrollR").click(function() {
//                console.log('i '+ i)
                var pulse = $('.top_nav li:nth-child('+i+')').width();
//                console.log('pulse: '+pulse)
                pulse = Number(pulse) + 5;
                count = Number(count) - Number(pulse);
                i=i+1;
                $(".top_nav ul").animate({left: count }, 1000);
                $(".header_top_bg .scrollL").fadeIn('slow');
//                console.log('scrollR.click '+i)
                if(i >= 6){
                    $(".header_top_bg .scrollR").fadeOut('slow');
                }
            });
            var menuPos = "${session.getAttribute('leftMenu')}"
//            alert(menuPos)
            var moduleMap = {'myDashboard' :1,
                             'MF':2,
                             'accounts':3,
                             'HR':4,
                             'PROC':5,
                             'administration':6,
                             'fa':7,
                             'store':8,
                             'donor':9,
                             'budget':10,
                             'pt':11
                             };
            var currentModuleIndex = moduleMap[menuPos]
//            alert(currentModuleIndex)
            function currentMenuPos(index){
                var countableWidth = 0;
                index = Number(index)
                var j=1
                while(j<=index){
                    countableWidth = Number(countableWidth) + Number($('.top_nav li:nth-child('+j+')').width())
                    j++;
                }
//                console.log('countableWidth: '+ countableWidth)
//                console.log('index: '+index)
//                console.log(Number(totalLength) - 3)
                if(index<=halfLength){
                    countableWidth = 0
                }
                else if(index >halfLength){
                    countableWidth = 341
                    i = index
                    $(".header_top_bg .scrollR").fadeOut('slow');
                }
//                console.log('before count: '+count)
                if(countableWidth == 0){
                    $('.header_top_bg .scrollL').fadeOut('fast')
                }
                count = Number(count) - Number(countableWidth)
                $(".top_nav ul").css('left', count);
//                console.log('after count: '+count)
            }
            currentMenuPos(currentModuleIndex);
        }
    });
</script>
