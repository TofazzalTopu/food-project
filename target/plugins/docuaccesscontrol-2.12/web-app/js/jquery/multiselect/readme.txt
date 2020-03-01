
/*
Add js and css files inside html <head> tag
-------------------------------------------
css/ui-lightness/jquery-ui-1.8.5.custom.css
js/jquery/jquery-1.4.2-min.js
js/jquery/jquery-ui-1.8.1.custom.min.js
js/jquery/multiselect/ui.multiselect.css
js/jquery/multiselect/jquery.scrollTo-min.js
js/jquery/multiselect/ui.multiselect.js
*/
<link rel="stylesheet" href="${resource(dir: 'css/ui-lightness/', file: 'jquery-ui-1.8.5.custom.css')}"/>
<g:javascript src="jquery/jquery-1.4.2-min.js"/>
<g:javascript src="jquery/jquery-ui-1.8.1.custom.min.js"/>
<link rel="stylesheet" href="${resource(dir: 'js/jquery/multiselect/', file: 'ui.multiselect.css')}"/>
<g:javascript src="jquery/multiselect/jquery.scrollTo-min.js"/>
<g:javascript src="jquery/multiselect/ui.multiselect.js"/>





/*
initiate multiselect plugin after document is loaded
---------------------------------------------------
*/
<script type="text/javascript">
    $(document).ready(function() {
        $(".multiselect").multiselect();
    });
</script>





/*
html for multiselect plugin
---------------------------------------------------
*/
<form action="saveData" method="post">
    <select id="countries" class="multiselect" multiple="multiple" name="countries[]" style="width:460px; height:200px;">
      <g:each in="${countryList}">
        <g:if test = "${it.code}">
          <option value="${it.code}">${it.name}</option>
        </g:if>
      </g:each>
      <g:each in="${countrySelected}">
        <g:if test = "${it.code}">
          <option value="${it.code}" selected="selected">${it.name}</option>
        </g:if>
      </g:each>
    </select>
    <br/>
    <input type="submit" value="Submit Form"/>
</form>





/*
send data from controller to gsp
---------------------------------------------------
*/
def db = new Sql(dataSource)

// get all country list for the dropdown
def countryListResult = db.rows("SELECT c.code,c.name FROM country AS c LEFT JOIN jquery_multi_select AS s ON ( c.code = s.code) WHERE ISNULL(s.code) ORDER BY c.name")

//get already selected country list
def selectedCountryResult = db.rows("SELECT s.code,c.name FROM jquery_multi_select as s LEFT JOIN country as c on (s.code = c.code) ORDER BY c.name")

[ countryList:countryListResult, countrySelected:selectedCountryResult ]





/*
receive data into controller from form submit
---------------------------------------------------
*/
def selectedCountries = params.get("countries[]")
selectedCountries.each(){
    println "${it}"
}