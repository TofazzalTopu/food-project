<%@ page import="com.docu.commons.CommonConstants" %>

<meta name='layout' content='loginLayout'/>

%{--<link rel="shortcut icon" href="${request.contextPath}/${applicationConfigCacheService.getFavIcon()}" type="image/x-icon"/>--}%
<link rel="stylesheet" href="${resource(dir: 'css', file: 'docuThemeRollerLayoutStyle.css')}"/>
%{--<!--[if IE 6]>--}%
<link rel="stylesheet" href="${resource(dir: 'css', file: 'docuThemeRollerLayoutStyleIE6.css')}">
%{--<![endif]-->--}%
<!--[if lt IE 7]>
      <link rel="stylesheet" href="${resource(dir: 'css', file: 'docuThemeRollerLayoutStyleIE6.css')}"/>
    <![endif]-->
<!--[if IE 7]>
      <link rel="stylesheet" href="${resource(dir: 'css', file: 'docuThemeRollerLayoutStyleIE7.css')}"/>
    <![endif]-->
<!--[if IE 8]>
      <link rel="stylesheet" href="${resource(dir: 'css', file: 'docuThemeRollerLayoutStyleIE8.css')}"/>
    <![endif]-->
<!--[if IE 9]>
      <link rel="stylesheet" href="${resource(dir: 'css', file: 'docuThemeRollerLayoutStyleIE9.css')}"/>

    <![endif]-->
<link rel="stylesheet" href="${resource(dir: 'css', file: 'layout.css')}"/>
%{--color theme begin--}%
<link rel="stylesheet" href="${resource(dir: 'css', file: 'newtemp.css')}"/>

<link id="currentThemeCss" rel="stylesheet"
      href="${request.contextPath}/themes/${CommonConstants.DEFAULT_CURRENT_THEME}/docuThemeRollerColor.css"/>
<link id="currentThemeJs" rel="stylesheet"
      href="${request.contextPath}/themes/${CommonConstants.DEFAULT_CURRENT_THEME}/jquery-ui-custom.css"/>

<title>Release Notes</title>

<style>
li {
    font-weight: bold;
    margin: 0px;
    height: 100% !important;
    min-width: 100% !important;
}

#rel_footer {
    font-weight: bold;
    margin: 0px;
    height: 100% !important;
    min-width: 100% !important;
}
</style>

<div id="main-container" style="background: none!important;">
    <div id="header" class="clearfix">
        <a href="#"><img id="logo_img" alt="login-logo"
                         src="${resource(dir: 'images', file: 'company_logo.png')}"/>
        </a>
    </div>

    <div id="footer" style="min-height: 300px;">
        <div class="body_content_bg floatL">
            <div class="main_container mar_auto0 pad_top10">
                <h1 class="pad_left10 pad_right10 pad_top5 pad_bot3">
                    <span class="pad_top5">Release Notes</span>
                    <span class="floatR">
                        <a href="${request.contextPath}/login/auth">
                            <img src="${request.contextPath}/images/icon_user.png" align="absmiddle" class="pad_all0"
                                 title="Login" alt="Login"/>
                        </a>
                    </span>
                </h1>

                <div class="content_container">
                    <div class="floatL width780 pad_all10">
                        <div class="rel_note overflow_y_auto">
                            <ul>
                                <li>
                                    <h6>BDFE Version 2.2.51 Release Date: 27 February 2019</h6>
                                    <ul>
                                        <li>
                                            <div class="title">1. Implement Ship to Address to the bellow features</div>

                                            <div class="detail">2. Create New Primary Demand Order.</div>

                                            <div class="detail">3. Update New Primary Demand Order.</div>

                                            <div class="detail">4. View Primary Demand Order Status.</div>

                                            <div class="detail">5. All Demand Approval Channel.</div>

                                            <div class="detail">6. Process Primary Order.</div>

                                            <div class="detail">7. Print Primary Invoice.</div>

                                            <div class="detail">8. Print Invoice.</div>

                                            <div class="detail">9. Cancel Invoice.</div>

                                            <div class="detail">10. Create Loaading Slip.</div>

                                            <div class="detail">11. Print Loading Slip.</div>
                                        </li>
                                    </ul>

                                </li>
                            </ul>

                            <ul>
                                <li>
                                    <h6>BDFE Version 2.2.50 Release Date: 10 February 2019</h6>
                                    <ul>
                                        <li>
                                            <div class="title">External Product</div>

                                            <div class="detail">Create External Product.</div>

                                            <div class="detail">Create External Product Stock.</div>
                                        </li>

                                        <li>
                                            <div class="title">New Primary Demand Order</div>

                                            <div class="detail">Maintain history during create new primary demand order.</div>
                                        </li>

                                        <li>
                                            <div class="title">Update New Primary Demand Order</div>

                                            <div class="detail">Maintain history during update new primary demand order.</div>
                                        </li>

                                        <li>
                                            <div class="title">Approve Primary Demand Order</div>

                                            <div class="detail">Maintain history during approve primary demand order.</div>

                                            <div class="detail">Maintain history during update primary demand order.</div>
                                        </li>

                                        <li>
                                            <div class="title">Consolidate Retail Order</div>

                                            <div class="detail">Maintain history during generate secondary order.</div>
                                        </li>

                                        <li>
                                            <div class="title">Update and Submit Secondary Order</div>

                                            <div class="detail">Maintain secondary order history.</div>
                                        </li>
                                        <li>
                                            <div class="title">Show Actual Primary Demand order</div>

                                            <div class="detail">Region Wise Demand Report.</div>

                                            <div class="detail">Channel Wise Demand Report.</div>
                                        </li>
                                        <li>
                                            <div class="title">New Reports</div>

                                            <div class="detail">Region And User Wise Demand Report.</div>

                                            <div class="detail">Sales Channel And User Wise Demand Report.</div>
                                        </li>

                                    </ul>

                                </li>
                            </ul>
                            <ul>
                                <li>
                                    <h6>BDFE Version 2.2.49 Release Date: 7 January 2019</h6>
                                    <ul>
                                        <li>
                                            <div class="title">Fix and enhancement</div>

                                            <div class="detail">1: Fix: Customer ID’ replaced as ‘Legacy ID.</div>

                                            <div class="detail">2: Fix: SKU rearranged sequentially.</div>

                                            <div class="detail">3: ix: Menu freeze resolved while click on 'New Primary Demand Order'.</div>
                                        </li>
                                    </ul>
                                </li>
                            </ul>
                            <ul>
                                <li>
                                    <h6>BDFE Version 2.2.48 Release Date: 13 December 2018</h6>
                                    <ul>
                                        <li>

                                            <div class="title">Product Sequence</div>

                                            <div class="detail">Display Product according to Master Product, Main product and Finish Product sequence.</div>
                                        </li>

                                        <li>
                                            <div class="title">Active/Inactive Product</div>

                                            <div class="detail">Activate and Inactivate Product for Product Setup</div>

                                            <div class="detail">Display active products with product sequence in Retail Order</div>

                                            <div class="detail">Display active products with product sequence in Secondary Demand Order</div>

                                            <div class="detail">Display active products with product sequence in Primary Demand Order</div>

                                            <div class="detail">Display active products with product sequence in New Primary Demand Order</div>

                                            <div class="detail">Display active products with product sequence in Product Price Setup</div>

                                            <div class="detail">Display active products with product sequence in Product Package Conversion</div>

                                            <div class="detail">Display active products with product sequence in Process Primary Demand Order</div>

                                            <div class="detail">Display active products with product sequence in Create Factory Stock</div>

                                            <div class="detail">Display available products with product sequence in Manual Retail Order Process</div>

                                            <div class="detail">Release Notes page included</div>
                                        </li>
                                        <li>
                                            <div class="title">Approve Primary Demand Order</div>

                                            <div class="detail">New filter with Legacy ID</div>

                                            <div class="detail">New filter with Customer Sales Channel</div>
                                        </li>
                                        <li>
                                            <div class="title">View Order Status</div>

                                            <div class="detail">New filter with Is New</div>

                                            <div class="detail">New filter with Status</div>
                                        </li>
                                        <li>
                                            <div class="title">Approval History</div>

                                            <div class="detail">Display Pending Approval Block</div>
                                        </li>
                                        <li>
                                            <div class="title">Bug Resolved</div>

                                            <div class="detail">Undefined value in product selection autocomplete for Retail Order</div>

                                            <div class="detail">Undefined value in product selection autocomplete for Secondary Demand Order</div>

                                            <div class="detail">Primary order no autocomplete in View Order Status</div>

                                            <div class="detail">Secondary order no generate problem resolved</div>

                                            <div class="detail">Search field of autocomplete product selection in Manual Retail Order Process resolved</div>

                                            <div class="detail">Master product sequence update enabled</div>

                                            <div class="detail">Main product sequence update enabled</div>

                                            <div class="detail">Screen jump problem when click on product quantity during Create Retail Order resolved</div>

                                            <div class="detail">Screen jump problem when click on product quantity during Create New Primary Demand Order</div>
                                        </li>

                                    </ul>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>

                <div class="clear"></div>
            </div>
            <!-- Footer -->
            <div id="rel_footer" class="floatL width100p height15" style="border-top: 1px dotted #AACCEE;">
                <div class="clear"></div>

                <div class="width35p floatL height15">&nbsp;</div>

                <div class="width35p floatL height15 txtC">Copyright &copy; ${com.docu.app.ApplicationConfigUtil.instance.companyName}<sup></sup> ${com.docu.commons.DateUtil.getCurrentSystemYear()}
                </div>

                <div class="width30p floatR height15 txtR">Developed By: BRAC IT Services Ltd.</div>

                <div class="clear"></div>
            </div>
        </div>
    </div>
</div>