
<%@ page import="com.docu.commons.Message; com.docu.commons.CommonConstants; org.codehaus.groovy.grails.commons.ApplicationHolder as AH; com.docu.commons.DateUtil;com.docu.app.ApplicationConfigUtil;" %>
%{--<link rel="stylesheet" href="${resource(dir: 'css', file: 'headermenu.css')}"/>--}%
<script type="text/javascript">
    $(document).ready(function () {
        $(document).click(function () {
            if ($('#login_container').is(':visible')) {
                $('#btn_login').click();
            }
            if ($('#color_theme_container').is(':visible')) {
                $('#btn_color').click();
            }
            if ($('#sub_txt_container').is(':visible')) {
                $('#acc_info_detail_middle').click();
            }
        });

        $('#btn_login').toggle(function () {
            $('#login_container').slideDown('slow')
            if ($('#color_theme_container').is(':visible')) {
                $('#btn_color').click();
            }
            if ($('#sub_txt_container').is(':visible')) {
                $('#acc_info_detail_middle').click();
            }
        }, function () {
            $('#login_container').slideUp('slow')
        });

        $('#btn_color').toggle(function () {
            $('#color_theme_container').slideDown('slow')
            if ($('#login_container').is(':visible')) {
                $('#btn_login').click();
            }
            if ($('#sub_txt_container').is(':visible')) {
                $('#acc_info_detail_middle').click();
            }
        }, function () {
            $('#color_theme_container').slideUp('slow')
        });

        $("#acc_info_detail_middle").toggle(
                function () {
                    $("#txt_container #sub_txt_container").slideDown()
                    $("#down_img").hide()
                    if ($('#login_container').is(':visible')) {
                        $('#btn_login').click();
                    }
                    if ($('#color_theme_container').is(':visible')) {
                        $('#btn_color').click();
                    }
                },
                function () {
                    $("#txt_container #sub_txt_container").slideUp()
                    $("#down_img").show()
                }
        );

        $('#color_theme_container li a').click(function () {
            var path = "${request.contextPath}/themes/" + $.trim($(this).text()).toLowerCase();
            DocuAjax.json("${request.contextPath}/menu/setTheme", {currentTheme: $.trim($(this).text()).toLowerCase()}, function (response) {
                if (response.type == ${Message.SUCCESS}) {
                    $("#currentThemeCss").attr('href', path + "/docuThemeRollerColor.css");
                    $("#currentThemeJs").attr('href', path + "/jquery-ui-custom.css");
                    MessageRenderer.render(response)
                }
            });
        });
    })
</script>
<script type="text/javascript">
    $(document).ready(function () {
        $('#quick-keyword').blur(function (event) {
            if ($('#quick-keyword').val() == '') {
                $('#quick-keyword').val('Search');
            }
        });

        $('#quick-keyword').focus(function (event) {
            if ($('#quick-keyword').val() == 'Search') {
                $('#quick-keyword').val('');
            }
        });

        $('#quick-keyword').keypress(function (event) {
            if (event.which == 13) {
                $('#quick-search-button').click();
            }
        });

        $('#quick-search-button').click(function () {
            $('.sbi-layout-body-container').html('<div id="search-result" style="margin-top:30px; width:85%;"></div><br/><br/><div id="pagination"></div>');

            var data = {};
            data['searchConfigurationId'] = 1;
            data['keyword'] = $('#quick-keyword').val();
            data['pageIndex'] = 0;

            $('#pagination').html('').hide();
            AjaxLoader.showTo('search-result');

            var url = "${request.contextPath}/search/newSearch";
            DocuAjax.json(url, data, function (response) {
                var params = {};
                params['resultCount'] = response.resultCount;
                params['pageActionUrl'] = "${request.contextPath}/search/show";
                params['searchConfigurationId'] = 1;
                params['keyword'] = $('#quick-keyword').val();
                SearchConfigurationAjax.initPagination(params);
            });
        });
    });
    function goToDayOpenClosePage() {
        location.href = "${request.contextPath}/businessDay/index";
    }
</script>

<g:set var="businessDayUtil" value="${ session.getAttribute('accountingDay')}"/>
<div class="header_container">

    %{--<div class="left">--}%
    <!-- header container begin -->
    <div class="header_top_bg">
        %{--<g:render template='/layouts/headermenu'/>--}%
        <div class="welcome_container">
            <!-- wecome container begin -->
            <span class="text">
                <sec:ifLoggedIn>${CommonConstants.WELCOME}</sec:ifLoggedIn> <b><sec:ifLoggedIn> <sec:loggedInUserInfo field="username"/></sec:ifLoggedIn></b>
            </span>
            <ul>
                <li>
                    %{--<a href="javascript:void(0)" id="btn_login" class="btn_user"><img src="${request.contextPath}/images/icon_user.png" alt="btn_login" height="23" width="26" align="absmiddle"/></a>--}%
                    <a href="javascript:void(0)" id="btn_login" class="btn_user"></a>
                    <ul class="login_container" id="login_container" style="display:none;">
                        <li><a href="#"><img src="${request.contextPath}/images/icon_my_acc.png" align="absmiddle"
                                             alt="my account"/> My Account</a></li>
                        <li><a href="${request.contextPath}/userAccount/changePassword"><img
                                src="${request.contextPath}/images/icon_chng_pass.png"
                                align="absmiddle" alt="change password"/> Change Password</a></li>
                        <li><a href="javascript:void(0);"
                               onclick="location.href = '${request.contextPath}/myDashboard/userPortletPreference';">
                            <img src="${request.contextPath}/images/icon_user_preference.png" align="absmiddle"
                                 alt="user_preference"/>User Preference</a></li>
                        <li class="last"><a href="${request.contextPath}/logout"><img
                                src="${request.contextPath}/images/icon_logout.png" align="absmiddle"
                                alt="logout"/> Logout</a></li>
                    </ul>
                </li>
                <li>
                    %{--<a href="javascript:void(0)" id="btn_color" class="btn_color"><img src="${request.contextPath}/images/btn_color_theme.png" height="23" width="26" align="absmiddle"></a>--}%
                    <a href="javascript:void(0)" id="btn_color" class="btn_color"></a>
                    <ul id="color_theme_container" class="color_theme_container" style="display:none;">
                        %{--<li><a href="#" id="black"><img src="${request.contextPath}/images/icon_black.png" alt="black"--}%
                        %{--align="absmiddle"/> Black</a></li>--}%
                        <li><a href="#" id="gray"><img src="${request.contextPath}/images/icon_gray.png" alt="gray"
                                                       align="absmiddle"/> Gray</a></li>
                        <li><a href="#"><img src="${request.contextPath}/images/icon_blue.png" alt="blue"
                                             align="absmiddle"/> Blue</a></li>
                        <li><a href="#"><img src="${request.contextPath}/images/icon_pink.png" alt="pink"
                                             align="absmiddle"/> Pink</a></li>
                        %{--<li class="last"><a href="#"><img src="${request.contextPath}/images/icon_green.png" alt="green"--}%
                        %{--align="absmiddle"/> Green</a></li>--}%
                    </ul>
                </li>
            </ul>

            <!-- wecome container end -->
        </div>
    </div>

    <div style="width:50%; float:left; clear:left; height:1px;"></div>

    <h1>
        <!-- logo begin -->
        <a href="#"><img src="${request.contextPath}/images/bdfe_logo.png" alt="Company Logo" title="test" align="baseline"/>

    </a>
        <!-- logo end -->
    </h1>

    <div class="right">

        <!--toggle wrapper begin -->
        <div class="toggle_wrapper">
            <div class="acc_info_container">
                <!-- account info begin -->

                <div class="acc_info_detail_top"></div>

                <div id="acc_info_detail_middle" class="acc_info_detail_middle">
                    <!-- account info content begin -->

                    <div class="txt_container" id="txt_container">
                        <g:if test="${businessDayUtil?.businessDate}">
                            <span>Date :
                                ${DateUtil.getFlexibleDateFormatAsString(businessDayUtil?.businessDate, CommonConstants.DATE_FORMAT)}</span>
                            <a href="javascript:void(0);" onclick="goToDayOpenClosePage();"
                               class="green">[DAY&nbsp;OPEN]</a>
                        </g:if>
                        <g:elseif test="${businessDayUtil?.lastClosedBusinessDate}">
                            <span>Date :
                                ${DateUtil.getFlexibleDateFormatAsString(businessDayUtil.lastClosedBusinessDate, CommonConstants.DATE_FORMAT)}</span>
                            <a href="javascript:void(0);" onclick="goToDayOpenClosePage();"
                               class="red">[DAY&nbsp;CLOSE]</a>
                        </g:elseif>
                        <g:else>
                            Date :
                            ${DateUtil.getFlexibleDateFormatAsString(new Date(), CommonConstants.DATE_FORMAT)}
                        </g:else>
                        <p id="sub_txt_container" style="display:none;">
                            DHO : ${DateUtil.getCurrentTimeByTimeZone(TimeZone.getTimeZone("Asia/Dhaka"))}<br/>
                            <g:if test="${session.configuredCountry}">
                                ${session.configuredCountry?.shortName} : ${DateUtil.getCurrentTimeByTimeZone(session.configuredCountry?.timeZone)}<br/>
                            </g:if>
                        </p>
                    </div>

                    <div id="down_img">
                        <img src="${request.contextPath}/images/arrow_down.png" alt="down"/>
                    </div>

                    <!-- account info content end -->
                </div>

                <div class="acc_info_detail_bottom"></div>

                <!-- account info end -->
            </div>
        </div>
        <!--toggle wrapper end -->
    </div>

    <div style="width:50%; float:left; clear:left; height:1px;"></div>

    <div class="header_bottom_bg">
        %{--<g:render template='/layouts/headerTopNav'/>--}%
        <div class="right">
            <div class="search_container">
                <!-- top search container begin -->
                <input type="text" id="quick-keyword" class="txt" value="Search"/>
                <input type="button" id="quick-search-button" class="btn" value=""/>
                <!-- top search container end -->
            </div>
        </div>

        <div class="hr_line"></div>
    </div>
    <!-- header container end -->
</div>