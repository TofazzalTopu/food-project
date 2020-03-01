<%@ page import="com.bits.bdfp.inventory.product.ProductPrice" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title>Product Price Setup</title>
<div class="main_container">
    <div class="content_container">
        <h1>Product Price Setup</h1>
        <h3>Product Price Details</h3>
        <g:render template='/product/productPrice/create' model="[productPricingTypeList:productPricingTypeList, list: list, enterpriseList: enterpriseList]"/>
        <br/>
        <g:render template="/product/productPrice/script" model="[list: list, enterpriseList: enterpriseList]"/>
    </div>
</div>