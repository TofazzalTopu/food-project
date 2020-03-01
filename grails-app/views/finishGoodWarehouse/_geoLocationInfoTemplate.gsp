<%@ page import="com.bits.bdfp.inventory.sales.DistributionPoint" %>
<style>
#geoTable {
    border-collapse: collapse;
}

#geoTable tr td {
    border: 1px solid silver;
}

.customLabel2 {
    background-color: darkgray;
    color: #000000;
    display: block;
    float: left;
    font-size: 11px;
    font-weight: bold;
    height: 15px;
    margin: 0;
    width: 130px;
    text-align: left;
}

.customLabel3 {
    background-color: darkgray;
    color: #000000;
    display: block;
    float: left;
    font-size: 11px;
    font-weight: bold;
    height: 15px;
    margin: 0;
    width: 20px;
    text-align: center;
}
.customLabel4 {
    background-color: darkgray;
    color: #000000;
    display: block;
    float: left;
    font-size: 11px;
    font-weight: bold;
    height: 15px;
    margin: 0;
    width: 80px;
    text-align: center;
}
</style>
<table id="geoTable">
    <tr>
        <td>
          <label class="customLabel3">&#10004;</label>
        </td>
        <td>
            <label  class='customLabel2'>Geographic Location</label>

        </td>
        <td>
            <label  class='customLabel4'>Country</label>
        </td>
         <td>
            <label  class='customLabel4'>Division</label>
        </td>
         <td>
            <label  class='customLabel4'>District</label>
        </td>
        <td>
            <label  class='customLabel2'>Thana</label>
        </td>
        <td>
            <label  class='customLabel4'>Union</label>
        </td>
        <td>
            <label  class='customLabel4'>Para</label>
        </td>
        <td>
            <label  class='customLabel4'>Road</label>
        </td>
    </tr>
<g:each in="${geoLocationInfoList}" var="geoLocation" status="inc">
    <td>
        <g:if test="${geoLocation.addedd_geo_location_id != null}">
            <input type="checkbox" checked="checked" class="geoCheckBox" id="geoCheckBox_${inc}">
        </g:if>
        <g:else>
            <input type="checkbox" class="geoCheckBox" id="geoCheckBox_${inc}">
        </g:else>
    </td>
    <td>
        ${geoLocation.geo_location}

    </td>
    <td>

        ${geoLocation.country_name}

    </td>
    <td>

        ${geoLocation.division_name}

    </td>
    <td>

        ${geoLocation.district_name}

    </td>
    <td>

        ${geoLocation.thana_name}

    </td>
    <td>

        ${geoLocation.union_name}

    </td>
    <td>

        ${geoLocation.para_or_locality}

    </td>
    <td>

        ${geoLocation.road}

    </td>
    </tr>
</g:each>
</table>