<%@ page import="com.bits.bdfp.util.ApplicationConstants; grails.util.Environment; com.docu.app.ApplicationConfigUtil" %>

<meta name='layout' content='loginLayout'/>


<div id="main-container" style="background: none!important;">
    <div id="header" class="clearfix">
        <a href="#"><img id="logo_img" alt="login-logo"
                         src="${resource(dir: 'images', file: 'company_logo.png')}"/>
        </a>
    </div>

    <div id="tabs-container" class="clearfix">
        <div class="clearfix" style="text-align: right;"><g:link
                url="[action: 'releaseNotes', controller: 'login']">Version <g:meta name="app.version"/></g:link></div>
    </div>

    <div id="stylized" class="myform" style="margin-top: 0;">
        <form action='${request.contextPath}/j_spring_security_check' method='post' name='loginForm' id='loginForm'
              class='cssform' autocomplete='off' onsubmit="fixUserName()">
            <h1>Sign-in form</h1>
            <g:if test='${flash.message}'>
                <p align="center" style="color: #ff0000;">${flash.message}<p>
            </g:if>
            <label>User Name<span style="color: red"> *</span></label>
            <input type='hidden' name='j_username' name='username' id='username' value=""/>
            <g:if test="${grails.util.Environment.current == grails.util.Environment.DEVELOPMENT}">
                <input type='text' name='j_username_txt' name='username' id='username_txt' value="sadmin"/>
            </g:if>
            <g:else>
                <input type='text' name='j_username_txt' name='username' id='username_txt' value=""/>
            </g:else>
            <label>Password<span style="color: red"> *</span></label>
            <g:if test="${grails.util.Environment.current == grails.util.Environment.DEVELOPMENT}">
                <input type='password' name='j_password' id='password' value="abc123$" style="margin-bottom: 10px;"/>
            </g:if>
            <g:else>
                <input type='password' name='j_password' id='password' value="" style="margin-bottom: 10px;"/>
            </g:else>
            <div class="clear"></div>


            <div style="padding: 2px 63px 0px 0px; float:right;">
                <button type="submit">Sign-in</button>
            </div>

            %{--<div style="padding: 2px 0px 10px 25px; float:left;" class="littlelink" align="right">--}%
                %{--<label><a style="text-decoration: none; color:#1F75CC;"--}%
                          %{--href="${request.contextPath}/login/forgotPassword"--}%
                          %{--tabindex="-1">Forgot password?</a>--}%
                %{--</label>--}%

                %{--<div class="clear"></div>--}%
            %{--</div>--}%
        </form>
    </div>

    <div id="footer" style="min-height: 300px;">
        <g:if test="${true}">
            <div id="tabs-container-bottom"></div>

            <div class="clear"></div>

            <div class="tabs-container-bottom-left" align="justify" style="width:240px; float:left; ">
                <p><strong>Introduction</strong></p>

                <p><b> BRAC Dairy</b> was launched in 1998 to assist members of our Village Organisations (VO) who borrow to invest in cows. A large number of our microfinance loans are being used to buy livestock, which can pose high risks for borrowers owing to poor breeding, limited veterinary services and shortages in cow feed. Our Dairy and Food Project provides a market to members of the VOs by buying milk from them at a fair price, and ensures a constant and steady demand and good return for the milk being produced by these rural entrepreneurs.
                </p>
                <p>Today, BRAC Dairy not only secures fair prices for its rural entrepreneurs, but has also expanded to offer cattle development and technical training, vaccinations and feed cultivation facilities. BRAC Dairy collects milk from 100 collection and chilling stations located across the country including 10 that are located in ultra-poor areas.  BRAC Dairy is currently the only dairy company in Bangladesh to have received ISO 22000 Certification, setting an example of vigilance at every stage of dairy production, processing, and distribution contributing to dairy products’ safety record.</p>
            </div>

            <div class="tabs-container-bottom-left" align="justify"
                 style="width:240px; float:left; margin:0px 0px 0px 15px">
                <p><strong>BERP Purpose</strong></p>

                <p>BDFP’s original mission was to grant farmers market access, ultimately helping them generate income. Over time, BDFP’s goals have expanded to serve high quality milk product to their customers. With inconsistent electricity and therefore refrigeration, dairy products generally are not widely available in Bangladesh. 85 percent of Bangladesh still relies on the ‘informal’ milk market which delivers bulk amounts of raw milk to consumers. BDFP caters to the 15 percent of Bangladeshis who rely on the formal milk market which sells processed and packaged milk. In essence, BDFP channels milk from rural areas into urban areas while channelling urban money into rural areas.
                </p>
            </div>

            <div align="justify" style="width:240px; float:right;">
                <p><strong>BERP Model</strong></p>

                <p>BRAC Dairy has 100 collection and chilling stations located in 25 districts, including 10 located in ultra-poor areas. The enterprise collects 102,559 litres milk daily and erves 40,000 farmers, 64% being women. BRAC Dairy has 23 Distributors and 37 Sales centres nationwide, covering 16,000 outlets out of 23,000 and enjoying an overall market share of 22% with a dedicated consumer base of around 500,000. More than 2 packets of Aarong Milk (a BRAC Dairy product) every second</p>
            </div>
        </g:if>
        <div class="clear"></div>
    </div>


    <div style="margin: 0 auto; width:800px; border-top: 1px solid #AACCEE;">
        <div style="width:35%; float:left;">&nbsp;</div>

        <div style="width:35%; float:left; text-align:center;"></div>

        <div style="width:30%; float:right; text-align:right; padding-top: 2px;">
            Developed By: <a href="http://www.bracits.com/"><img id="bits_img" alt="logo" align="absmiddle"
                                                                 src="${resource(dir: 'images', file: 'biTS_logo_small.png')}"/>
        </a>
        </div>

        <div class="clear"></div>
    </div>

</div>

<script type="text/javascript">
    function fixUserName() {
        var uName = $("#username_txt").val();
        %{--var zSeven = "0000000";--}%
        %{--if (!isNaN(uName - 0)) {--}%
            %{--var ln = uName.length;--}%
            %{--if (ln < ${ApplicationConstants.APPLICATION_USER_LENGTH} && ln > 0) {--}%
                %{--uName = zSeven.substr(0, ${ApplicationConstants.APPLICATION_USER_LENGTH} - ln) + uName;--}%
            %{--}--}%
        %{--}--}%
        $("#username").val(uName);
        return true;
    }
</script>