<%@ page import="grails.util.Environment; com.docu.app.ApplicationConfigUtil" %>
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
%{--<head>--}%
<meta name='layout' content='loginLayout'/>
%{--</head>--}%

<div id="main-container" style="background: none!important;">
    <div id="header" class="clearfix">
        <a href="#"><img id="logo_img" alt="logo" src="${resource(dir: 'images/banner_img', file: 'login_logo.png')}"/>
        </a>
    </div>

    <div id="tabs-container" class="clearfix">
        <div class="clearfix" style="text-align: right;"><g:link
                url="[action: 'releaseNotes', controller: 'login']">Version <g:meta name="app.version"/></g:link></div>
    </div>

    <div id="stylized" class="myform" style="margin-top: 0;">
        <form action='${request.contextPath}/j_spring_security_check' method='post' name='loginForm' id='loginForm'
              class='cssform' autocomplete='off'>
            <h1>Sign-in form</h1>
            <g:if test='${flash.message}'>
                <p align="center" style="color: #ff0000;">${flash.message}<p>
            </g:if>
            <label>User Name</label>
            <g:if test="${grails.util.Environment.current == Environment.DEVELOPMENT}">
                <input type='text' name='j_username' %{--name='username'--}% id='username' value="sadmin"/>
            </g:if>
            <g:else>
                <input type='text' name='j_username' %{--name='username'--}% id='username' value=""/>
            </g:else>
            <label>Password</label>
            <g:if test="${grails.util.Environment.current == Environment.DEVELOPMENT}">
                <input type='password' name='j_password' id='password' value="abc123$" style="margin-bottom: 10px;"/>
            </g:if>
            <g:else>
                <input type='password' name='j_password' id='password' value="" style="margin-bottom: 10px;"/>
            </g:else>
            <div class="clear"></div>

            <div style="padding: 2px 43px 0px 0px; float:right;">
                <button type="submit">Sign-in</button>
            </div>

            <div style="padding: 2px 0px 10px 25px; float:left;" class="littlelink" align="right">
                <label><a style="text-decoration: none; color:#1F75CC;"
                          href="${request.contextPath}/login/forgotPassword" tabindex="-1">Forgot password?</a>
                </label>

                <div class="clear"></div>
            </div>
        </form>
    </div>

    %{--<div id="tabs-container-bottom"></div>--}%
    <!-- Footer -->
    <div id="footer" style="min-height: 300px;">
        <g:if test="${ApplicationConfigUtil.instance.showLoginText()}">
            <div id="tabs-container-bottom"></div>

            <div class="clear"></div>

            <div class="tabs-container-bottom-left" align="justify" style="width:240px; float:left; ">
                <p><strong>Where we work</strong></p>

                <p>The majority of BRAC&rsquo;s work is in Bangladesh. We have been delivering successful programmes since 1972 and grown to become the largest development organisation in the world. Our low cost, innovative solutions to the daily problems facing poor families have been scaled up to reach every village in Bangladesh.</p>

                <p>BRAC provides more than just microfinance. We use the microfinance groups as a social platform to deliver scaled-up services in health, education, business development and livelihood support. These are all critical components needed to ensure that poor people can break the cycle of poverty.</p>

                <p>We have won numerous international awards for turning problems into successful solutions.</p>

                <p>The majority of our 125,000 staff&nbsp;is in Bangladesh, with our headquarters&nbsp;in Dhaka, the national capital.</p>
            </div>

            <div class="tabs-container-bottom-left" align="justify"
                 style="width:240px; float:left; margin:0px 0px 0px 15px">
                <p><strong>Scaling up in Asia and Africa</strong></p>

                <p>BRAC has substantial operations in a growing number of Asian and African countries. We work in countries where we can achieve a major impact on reducing poverty and improving livelihoods.</p>

                <p>Since 2002, we have been using our experience to energise and accelerate poverty alleviation efforts in other countries. We deliver microfinance, health, education, agriculture and livestock services based on our integrated approach in Bangladesh.</p>

                <p>Our programmes can be scaled up quickly to a national level and are low cost, effective and adaptable.</p>
            </div>

            <div align="justify" style="width:240px; float:right;">
                <p><strong>Asian and African staff</strong></p>

                <p>We directly recruit and train&nbsp;local staff to deliver and manage our programmes.</p>

                <p>However, specialist managers from BRAC in Bangladesh are posted internationally for quality control and south-south knowledge exchange.</p>
                <ul class="bullet1">
                    <li>
                        <strong style="font-size: 12px;">
                            Asia
                        </strong>
                        <ul class="bullet1">
                            <li>
                                <span>Bangladesh</span>
                            </li>
                            <li>
                                <span>Afghanistan</span>
                            </li>
                            <li>
                                <span>Pakistan</span>
                            </li>
                            <li>
                                <span>Sri Lanka</span>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <strong style="font-size: 12px;">
                            Africa
                        </strong>

                        <ul class="bullet1">
                            <li>
                                Liberia
                            </li>
                            <li>
                                Sierra Leone
                            </li>
                            <li>
                                Southern Sudan
                            </li>
                            <li>
                                Tanzania
                            </li>
                            <li>
                                Uganda
                            </li>
                        </ul>
                    </li>
                </ul>

                <p>We also provide support to other development organisations in Haiti, Honduras, India, Indonesia, Pakistan, Peru, and Northern Sudan.</p>
            </div>
        </g:if>
        <div class="clear"></div>
    </div>


    <div style="margin: 0 auto; width:800px; border-top: 1px solid #AACCEE;">
        <div style="width:35%; float:left;">&nbsp;</div>

        %{--<div style="width:35%; float:left; text-align:center;">Copyright<sup>&copy;</sup> BRAC International 2013</div>--}%
        <div style="width:35%; float:left; text-align:center;"></div>

        <div style="width:30%; float:right; text-align:right; padding-top: 2px;">
            Developed By: <a href="http://www.bracits.com/"><img id="bits_img" alt="logo" align="absmiddle"
                                                                 src="${resource(dir: 'images', file: 'biTS_logo_small.png')}"/>
        </a>
            %{--BRAC IT Services Ltd.--}%
        </div>

        <div class="clear"></div>
    </div>
    %{--<div>--}%
    %{--<p id="copyright">--}%
    %{--Copyright © Documenta<sup>TM</sup> Ltd.<br>--}%
    %{--</p>--}%
    %{--<div class="clear"></div>--}%
    %{--</div>--}%
</div>