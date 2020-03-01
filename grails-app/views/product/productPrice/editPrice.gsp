<%--
  Created by IntelliJ IDEA.
  User: mdalinaser.khan
  Date: 9/14/15
  Time: 1:05 PM
--%>

<%@ page import="com.bits.bdfp.inventory.product.ProductPrice" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title>Edit Product Price</title>
<style>
    .main_container {
        width:1000px!important;
    }
</style>
<div class="main_container width1000">
    <div class="content_container">
        <h1>Edit Product Price</h1>
        <h3>Edit /Inactive Price List</h3>
        <g:render template='/product/productPrice/searchPrice'/>
        <br/>
        <g:render template="/product/productPrice/editScript"/>
    </div>
</div>