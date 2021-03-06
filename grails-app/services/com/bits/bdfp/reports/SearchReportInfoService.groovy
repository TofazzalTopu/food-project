package com.bits.bdfp.reports

import com.bits.bdfp.customer.CustomerCategory
import com.bits.bdfp.geolocation.TerritoryConfiguration
import com.docu.commons.CommonConstants
import com.docu.security.ApplicationUser
import grails.plugins.springsecurity.SpringSecurityService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import groovy.sql.Sql
import javax.sql.DataSource

class SearchReportInfoService {
    static transactional = false
    DataSource dataSource
    Sql sql

    @Autowired
    SpringSecurityService springSecurityService

    @Transactional(readOnly = true)
    public List fetchGeolocationList() {
        List<TerritoryConfiguration> territorylist=[]
        Sql sql = new Sql(dataSource)
        String query = """SELECT id,name FROM territory_configuration"""
        return sql.rows(query.toString())
        return territorylist
    }

    @Transactional(readOnly = true)
    public List fetchCustomerCategoryList() {
        Sql sql = new Sql(dataSource)
        String query = """ SELECT id, name FROM customer_category """
        return sql.rows(query)
    }

    @Transactional(readOnly =true)
    public  List customerList(){
         Sql sql = new Sql(dataSource)
        String query = """SELECT  -1 AS ID , 'All' AS NAME
UNION ALL SELECT CODE AS ID , NAME  FROM customer_master"""
        return  sql.rows(query.toString())

    }
    @Transactional (readOnly = true)
    public Object dpPointList(Integer userId){
        Sql sql = new Sql(dataSource);
        String query = """SELECT  distribution_point.id AS ID , distribution_point.`name` FROM    application_user_distribution_point
INNER JOIN distribution_point ON application_user_distribution_point.`distribution_point_id` =  distribution_point.`id`
WHERE application_user_id =""" +userId
    return  sql.rows(query.toString())

    }

    @Transactional(readOnly = true)
    public List fetchCustomerCatagoryListwithoutSalesman() {
        List<CustomerCategory> customerCategorylist=[]
        Sql sql = new Sql(dataSource)
        String query = """SELECT id,NAME FROM customer_category
            WHERE NAME !='Sales Man'"""
        return sql.rows(query.toString())
       // return customerCategorylist
    }

    @Transactional(readOnly = true)
    public List fetchCustomerSalesChannelList() {
        List<CustomerCategory> customerSalesChannellist=[]
        Sql sql = new Sql(dataSource)
        String query = """SELECT id,name FROM customer_sales_channel"""
        return sql.rows(query.toString())
        return customerSalesChannellist
    }

    @Transactional(readOnly = true)
    public List fetchSalesmanList() {
        String query = """ SELECT DISTINCT	cstmrM.name Customer,cstmrM.id ID
    FROM `customer_master` cstmrM
	    INNER JOIN `customer_category` cstmrCTG ON  cstmrCTG.id = cstmrM.category_id
	    INNER JOIN `customer_territory_sub_area` ctsa ON ctsa.customer_master_id = cstmrM.id
	    INNER JOIN `territory_sub_area` ttsa ON  ttsa.id = ctsa.territory_sub_area_id
    WHERE cstmrCTG.name = 'Sales Man' """

        Sql sql = new Sql(dataSource)
        return sql.rows(query.toString())
    }

    @Transactional(readOnly = true)
    public Object fetchSalesmanListAll() {
        String query = """SELECT 'All' AS Customer, -1 AS ID
UNION ALL SELECT DISTINCT	cstmrM.name Customer,cstmrM.id ID
    FROM `customer_master` cstmrM
	    INNER JOIN `customer_category` cstmrCTG ON  cstmrCTG.id = cstmrM.category_id
	    INNER JOIN `customer_territory_sub_area` ctsa ON ctsa.customer_master_id = cstmrM.id
	    INNER JOIN `territory_sub_area` ttsa ON  ttsa.id = ctsa.territory_sub_area_id
    WHERE cstmrCTG.name = 'Sales Man' """

        Sql sql = new Sql(dataSource)
        return sql.rows(query.toString())
    }

    public List getSalesmanListByDp(Long dpId){
        String query = """
            SELECT 'All' AS Customer, -1 AS ID
            UNION ALL
            SELECT DISTINCT cstmrM.name Customer,cstmrM.id ID
            FROM `customer_master` cstmrM
                INNER JOIN `customer_category` cstmrCTG ON  cstmrCTG.id = cstmrM.category_id
                INNER JOIN `customer_territory_sub_area` ctsa ON ctsa.customer_master_id = cstmrM.id
                INNER JOIN `territory_sub_area` ttsa ON  ttsa.id = ctsa.territory_sub_area_id
                INNER JOIN `distribution_point_territory_sub_area` dptsa
                        ON  dptsa.territory_sub_area_id = ttsa.id
            WHERE cstmrCTG.name = 'Sales Man'
            AND dptsa.distribution_point_id = ${dpId}
        """

        Sql sql = new Sql(dataSource)
        return sql.rows(query.toString())
    }

    @Transactional(readOnly = true)
    public List fetchNonSalesmanList() {
        String query = """SELECT DISTINCT cstmrM.name Customer,cstmrM.id ID
    FROM `customer_master` cstmrM
        INNER JOIN `customer_category` cstmrCTG ON  cstmrCTG.id = cstmrM.category_id\t
        INNER JOIN `customer_territory_sub_area` ctsa ON ctsa.customer_master_id = cstmrM.id
        INNER JOIN `territory_sub_area` ttsa ON  ttsa.id = ctsa.territory_sub_area_id\t
        WHERE cstmrCTG.name NOT IN ('Sales Man')"""

        Sql sql = new Sql(dataSource)
        return sql.rows(query.toString())
    }

    @Transactional(readOnly = true)
    public List getStockOutReport(Object params) {
        sql = new Sql(dataSource)
        String qry = CommonConstants.EMPTY_STRING;

        try {
            List objList = null;

            if (params.fromDate) {
                Date oldfromDate = Date.parse('dd-MM-yyyy', params.fromDate)
                String newfromDate = oldfromDate.format('yyyy-MM-dd')

                Date oldtoDate = Date.parse('dd-MM-yyyy', params.toDate)
                String newtoDate = oldtoDate.format('yyyy-MM-dd')

                qry = " AND  (DATE(i.date_created) BETWEEN '${newfromDate}' AND '${newtoDate}') ";
            }
            if (params.monthYr) {
                String mnthParam = params.monthYr
                Date oldMnth = Date.parse('MM-yyyy', mnthParam)
                String newMonth = oldMnth.format('yyyy-MM')
                qry = " AND i.date_created LIKE '" + newMonth + "-%'"
            }

            String strSql = """
                SELECT DATE(i.date_created) AS date_created, fp.name AS productName , SUM(ids.quantity) AS quantity, ids.`unit_price` AS rate, SUM(ROUND(ids.quantity * ids.`unit_price`,2)) AS amount , SUM(ROUND((ids.quantity * ids.`unit_price`),2)) AS total,
                    'TP' AS short_name, ids.`unit_price` AS price , mp.name AS masterProductName, entp.name AS EnterpriseName, dp.`name` AS DPName, DATE_FORMAT(i.date_created, '%M') AS month_of_yr
                FROM invoice AS i
                    INNER JOIN invoice_details AS ids ON (i.id = ids.invoice_id)
                    INNER JOIN finish_product AS fp ON (ids.finish_product_id = fp.id)
                    INNER JOIN enterprise_configuration AS entp ON (entp.id = fp.enterprise_configuration_id)
                    INNER JOIN distribution_point AS dp ON (dp.id = i.`distribution_point_id`)
                    INNER JOIN master_product AS mp ON (mp.id = fp.master_product_id)
                WHERE i.`secondary_demand_order_id` IS NOT NULL
                    AND mp.id != 1 /* Exclude Fresh Milk */
                    AND i.`distribution_point_id` =  (
                        SELECT distribution_point_id FROM
                        `application_user_distribution_point`
                        WHERE application_user_distribution_point.`application_user_id`= ${params.userId}
                    )
                    ${qry}
                GROUP BY DATE(i.date_created), fp.`id`
            """
            objList = sql.rows(strSql)

            return objList;
        }catch (Exception e){
            log.error(e.message());
        }
    }

    @Transactional(readOnly = true)
    public List getSummaryDailyDeliveryReport(Object params) {
        sql = new Sql(dataSource)
        String strSql = CommonConstants.EMPTY_STRING;
        String qry = CommonConstants.EMPTY_STRING;
        ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()

        List objList = null;

        if (params.ledgerDateFrom) {
            String date1 = params.ledgerDateFrom
            Date oldfromDate = Date.parse('dd-MM-yyyy', date1)
            String newfromDate = oldfromDate.format('yyyy-MM-dd')


            String date2 = params.ledgerDateTo
            Date oldtoDate = Date.parse('dd-MM-yyyy', date2)
            String newtoDate = oldtoDate.format('yyyy-MM-dd')

            qry =" AND  (DATE(invoice.`transaction_date`) BETWEEN '${newfromDate}' AND '${newtoDate}') ";
        }
       /* if (params.monthYr) {
            String mnthParam = params.monthYr
            Date oldMnth = Date.parse('MM-yyyy', mnthParam)
            String newMonth = oldMnth.format('yyyy-MM')
            qry=" AND sdod.date_created LIKE '"+newMonth+"-%'"
        }*/

        strSql = """
            SELECT customer_master.`name` ,customer_master.`code`,invoice.`code` AS  challanNo , invoice.`transaction_date`, SUM(invoice.`invoice_amount`) AS amount
            FROM invoice
            INNER JOIN customer_master ON invoice.`default_customer_id` = customer_master.`id`
            INNER JOIN distribution_point ON invoice.`distribution_point_id`= distribution_point.id
            WHERE  customer_master.`category_id`=${params.categoryId}
            ${qry}
            GROUP BY  customer_master.name, DATE(invoice.`transaction_date`)
            ORDER BY customer_master.name
        """
        objList = sql.rows(strSql)

        return objList;

        /*
        SELECT distribution_point.`name` ,distribution_point.`code`,invoice.`code` AS  challanNo , invoice.`transaction_date`, SUM(invoice.`invoice_amount`) AS amount FROM invoice
            INNER JOIN customer_master ON invoice.`default_customer_id` = customer_master.`id`

            INNER JOIN application_user_distribution_point ON  application_user_distribution_point.`application_user_id`
            INNER JOIN distribution_point ON application_user_distribution_point.`distribution_point_id`= distribution_point.id
            WHERE  customer_master.`category_id`=${params.categoryId}
            ${qry}
            GROUP BY  distribution_point.id
         */
    }


    @Transactional(readOnly = true)
    public List getSummaryDailyDemandOrder(Object params) {
        sql = new Sql(dataSource)
        String strSql = CommonConstants.EMPTY_STRING;
        String condition = CommonConstants.EMPTY_STRING;
        ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()

        List objList = null;

        if (params.dateFrom) {
            String date1 = params.dateFrom
            Date oldfromDate = Date.parse('dd-MM-yyyy', date1)
            String newfromDate = oldfromDate.format('yyyy-MM-dd')


            String date2 = params.dateTo
            Date oldtoDate = Date.parse('dd-MM-yyyy', date2)
            String newtoDate = oldtoDate.format('yyyy-MM-dd')

            condition =" WHERE (DATE(primary_demand_order.order_date) BETWEEN '${newfromDate}' AND '${newtoDate}') "
            condition += " AND application_user.id =  ${applicationUser.id} "
        }

        strSql = """
            SELECT master_product.name, finish_product.pack_size, primary_order_history.new_quantity, primary_demand_order.order_date
            FROM primary_demand_order
            INNER JOIN primary_order_history
                    ON primary_demand_order.id = primary_order_history.primary_demand_order_id
            INNER JOIN application_user_distribution_point
                    ON application_user_distribution_point.distribution_point_id = primary_demand_order.distribution_point_id
            INNER JOIN application_user
                    ON application_user.id = application_user_distribution_point.application_user_id
            INNER JOIN finish_product
                    ON primary_order_history.finish_product_id = finish_product.id
            INNER JOIN master_product
                    ON finish_product.master_product_id = master_product.id
            ${condition}
        """
        objList = sql.rows(strSql)

        return objList;
    }

    @Transactional(readOnly = true)
    public List getSaleStockStatement(Object params) {
        sql = new Sql(dataSource)
        String strSql = CommonConstants.EMPTY_STRING;
        String qry = CommonConstants.EMPTY_STRING;
        ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()

        List objList = null;

        if (params.monthYr) {
            String mnthParam = params.monthYr
            Date oldMnth = Date.parse('MM-yyyy', mnthParam)
            String newMonth = oldMnth.format('yyyy-MM')
            qry=" AND dpst.date_created LIKE '"+newMonth+"-%'"
        }


        strSql = """

            SELECT  dps.`finish_product_id`, fp.`name`, SUM(dpst.in_quantity) AS received, SUM(dpst.`out_quantity`) AS sale,
            ROUND(dpst.in_quantity*dpst.unit_price,2) AS valueR, ROUND(dpst.out_quantity*dpst.unit_price,2) AS value_S,
            dps.sub_warehouse_id AS swid, sw.name AS sw_name, dp.name AS dpName, DATE_FORMAT(dpst.date_created, '%M %Y') AS month_of_yr, ec.name AS ename,
            SUM(dpst.in_quantity) AS val, dpst.date_created AS date_created, dpst.unit_price,

            (SELECT SUM(dpst.in_quantity - dpst.out_quantity)
            FROM distribution_point_stock AS dpss
            INNER JOIN distribution_point_stock_transaction dpst
             ON dpss.`id` = dpst.`distribution_point_stock_id`
            WHERE DATE(dpst.date_created)<=(LAST_DAY(DATE_ADD(CURDATE(), INTERVAL -1 MONTH)))
             AND dpss.`finish_product_id` = dps.`finish_product_id`
            )
            AS openingBalance,

            ((SELECT SUM(dpst.in_quantity - dpst.out_quantity)
            FROM distribution_point_stock AS dpss
            INNER JOIN distribution_point_stock_transaction dpst
             ON dpss.`id` = dpst.`distribution_point_stock_id`
            WHERE DATE(dpst.date_created)<=(LAST_DAY(DATE_ADD(CURDATE(), INTERVAL -1 MONTH)))
             AND dpss.`finish_product_id` = dps.`finish_product_id`
            )+SUM(dpst.in_quantity)-SUM(dpst.OUT_quantity)) AS closingBalance

            FROM distribution_point_stock AS dps, `distribution_point_stock_transaction` AS dpst, `finish_product` AS fp,
            sub_warehouse AS sw, distribution_point_warehouse AS dpwh, distribution_point AS dp, enterprise_configuration AS ec

            WHERE dps.id=  dpst.distribution_point_stock_id

            AND fp.id = dps.finish_product_id

            AND sw.id = dps.sub_warehouse_id
            AND dpwh.warehouse_id = sw.warehouse_id
            AND dpwh.distribution_point_id = ${params.dp}
            AND dp.id= dpwh.distribution_point_id
            AND ec.id= dp.enterprise_configuration_id
             AND dpst.date_created LIKE '2016-08-%'
            ${qry}
            GROUP BY dps.`finish_product_id`

            ORDER BY fp.`name` ASC
        """
        objList = sql.rows(strSql)

        return objList;
    }

    @Transactional(readOnly = true)
    public List getUhtSaleStockStatementDetails(Object params) {
        sql = new Sql(dataSource)
        String strSql = CommonConstants.EMPTY_STRING;
        String qry = CommonConstants.EMPTY_STRING;
        ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()

        List objList = null;
        if (params.monthYr) {
            String mnthParam = params.monthYr
            Date oldMnth = Date.parse('MM-yyyy', mnthParam)
            String newMonth = oldMnth.format('yyyy-MM')
            qry=" AND dpst.date_created LIKE '"+newMonth+"-%'"
        }

        strSql = """
            SELECT dpst.date_created AS  date_created, dps.`finish_product_id`, fp.`name`, dpst.in_quantity AS received, dpst.`out_quantity` AS sale,
            dpst.unit_price, ROUND(dpst.in_quantity*dpst.unit_price,2) AS valueR,  ROUND(dpst.out_quantity*dpst.unit_price,2) AS value_S,
            dps.sub_warehouse_id AS swid, sw.name AS sw_name, dp.name AS dpName,
            DATE_FORMAT(dpst.date_created, '%M %Y') AS month_of_yr , ec.name AS ename


            FROM distribution_point_stock AS dps, `distribution_point_stock_transaction` AS dpst, `finish_product` AS fp,
            sub_warehouse AS sw, distribution_point_warehouse AS dpwh, distribution_point AS dp , enterprise_configuration AS ec

            WHERE dps.id=  dpst.distribution_point_stock_id
            AND fp.id = dps.finish_product_id
            AND sw.id = dps.sub_warehouse_id
            AND dpwh.warehouse_id = sw.warehouse_id
            AND dpwh.distribution_point_id =  ${params.dp}
            AND dp.id= dpwh.distribution_point_id
            AND ec.id= dp.enterprise_configuration_id
            ${qry}

            GROUP BY dpst.date_created, dps.finish_product_id

            ORDER BY dpst.date_created ASC
        """
        objList = sql.rows(strSql)

        return objList;
    }

    @Transactional(readOnly = true)
    public List fetchDistributionPointList() {

        sql = new Sql(dataSource)
        String strSql = CommonConstants.EMPTY_STRING;
        String qry = CommonConstants.EMPTY_STRING;
        ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()

        List objList=null;

        String query = """
            SELECT dp.id, dp.name AS dpName
            FROM application_user_distribution_point AS audp , distribution_point AS dp
            WHERE audp.application_user_id=${applicationUser.id}
            AND dp.id=audp.distribution_point_id
            """
        objList = sql.rows(strSql)

        return objList;
       // Sql sql = new Sql(dataSource)
       // return sql.rows(query.toString())
    }

    @Transactional(readOnly = true)
    public List distributionPointForLoggedInUser() {

        sql = new Sql(dataSource)
        String strSql = CommonConstants.EMPTY_STRING;
        String qry = CommonConstants.EMPTY_STRING;
        ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()

        List objList=null;

        String query = """
            SELECT dp.id
            FROM application_user_distribution_point AS audp , distribution_point AS dp
            WHERE audp.application_user_id=15
            AND dp.id=audp.distribution_point_id
            """
        objList = sql.rows(query)

        return objList;
    }


    @Transactional(readOnly = true)
    public List getBranchStockReport(Object params) {
        sql = new Sql(dataSource)
        String strSql = CommonConstants.EMPTY_STRING;
        String qry = CommonConstants.EMPTY_STRING;
        ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()

       /* try{
            List distributionPointList= distributionPointForLoggedInUser();
        }catch (Exception e){
            e.printStackTrace();
        }
*/
        List objList = null;

        if (params.monthYr) {
            String mnthParam = params.monthYr
            Date oldMnth = Date.parse('MM-yyyy', mnthParam)
            String newMonth = oldMnth.format('yyyy-MM')
            qry=" AND sdod.date_created LIKE '"+newMonth+"-%'"
        }

        Calendar cal = Calendar.getInstance();
        String mnthParam = params.monthYr
        Date oldMnth = Date.parse('MM-yyyy', mnthParam)
        String newMonth = oldMnth.format('yyyy-MM')

        cal.setTime(oldMnth);
        cal.add(cal.MONTH, -1);
        int mnth=cal.get(cal.MONTH);
        int yr=cal.get(cal.YEAR);
        //cal.set(dt, cal.getActualMaximum(dt));

        int lastDate=cal.getActualMaximum(mnth);

        String dpst='';
        String fgst='';
        String pmr='';
        String fgs='';
        if(mnth<9){
            /*String m="0"+mnth;
            mnth=Integer.parseInt(m);*/
             dpst= "'"+yr+"-0"+(mnth+1)+"%"+"'";
             fgst= "'"+yr+"-0"+(mnth+1)+"%"+"'";
             pmr= "'"+yr+"-0"+(mnth-1)+"%"+"'";
             fgs="'"+yr+"-0"+(mnth+1)+"%"+"'";
        }else{
             dpst= "'"+yr+"-"+(mnth+1)+"%"+"'";
             fgst= "'"+yr+"-"+(mnth+1)+"%"+"'";
             pmr= "'"+yr+"-"+(mnth-1)+"%"+"'";
             fgs="'"+yr+"-0"+(mnth+1)+"%"+"'";
        }
       // String dpst= "'"+yr+"-"+mnth+"-"+lastDate+"%"+"'";
       /* String dpst= "'"+yr+"-"+(mnth+1)+"%"+"'";
        String fgst= "'"+yr+"-"+(mnth+1)+"%"+"'";
        String pmr= "'"+yr+"-"+(mnth-1)+"%"+"'";
        cal.getTime();*/

        if(params.dp=="null"){
            List distributionPointList= distributionPointForLoggedInUser()
            strSql = """

                SELECT fp.name AS productName, SUM(dpst.in_quantity) AS branchStock, (SUM(dpst.in_quantity)*ppp.price) AS bsprice ,
                SUM(fgst.in_quantity) AS factoryStock, (SUM(fgst.in_quantity)*ppp.price) AS fsprice, ( SUM(dpst.in_quantity)+SUM(fgst.in_quantity)) AS openingStock,
                fgst.in_quantity AS inq, ((fgst.in_quantity)*ppp.price) AS inPrice,  SUM(fgst.out_quantity) AS outq, (SUM(fgst.out_quantity)*ppp.price) AS OutPrice ,
                (dpst.in_quantity+fgst.in_quantity-SUM(fgst.out_quantity)-(SUM(mrd.quantity)+SUM(pmrd.qc_quantity))) AS stbranch,
                ((dpst.in_quantity+fgst.in_quantity-SUM(fgst.out_quantity)-(SUM(mrd.quantity)+SUM(pmrd.qc_quantity)))*ppp.price) AS stbranchPrice,
                ((fgst.in_quantity)-(SUM(mrd.quantity)+SUM(pmrd.qc_quantity))) AS stfactory,
                ((dpst.in_quantity+fgst.in_quantity-SUM(fgst.out_quantity)-(SUM(mrd.quantity)+SUM(pmrd.qc_quantity)))+((fgst.in_quantity)-(SUM(mrd.quantity)+SUM(pmrd.qc_quantity)))) AS closingStock,
                (((fgst.in_quantity)-(SUM(mrd.quantity)+SUM(pmrd.qc_quantity)))*ppp.price) AS stfactoryPrice,
                SUM(mrd.quantity) AS claim, (SUM(mrd.quantity)*ppp.price) AS claimPrice, SUM(pmrd.qc_quantity) AS adjust, (SUM(pmrd.qc_quantity)*ppp.price) AS adjustPrice,
                SUM(pmrd.qc_failed_quantity) AS actual, (SUM(pmrd.qc_failed_quantity)*ppp.price) AS actualPrice
                , ppp.price AS price

                FROM distribution_point_stock AS dps, distribution_point_stock_transaction AS dpst,
                finish_good_stock AS fgs, finish_good_stock_transaction AS fgst ,finish_product AS fp,
                market_return_details AS mrd,  process_market_return AS pmr, process_market_return_details AS pmrd,
                sub_warehouse AS sw , distribution_point_warehouse AS dpw, product_price_product AS ppp, pricing_category AS pc

                WHERE dpst.distribution_point_stock_id IN (SELECT dp.id
                                                            FROM application_user_distribution_point AS audp , distribution_point AS dp
                                                            WHERE audp.application_user_id=15
                                                            AND dp.id=audp.distribution_point_id
                                                            )
                AND sw.warehouse_id=dpw.warehouse_id
                AND dps.sub_warehouse_id=sw.id
                AND dpst.distribution_point_stock_id=dps.id
                AND dpst.transaction_date LIKE  ${dpst}
                AND fgst.finish_good_stock_id=fgs.id
                AND fgst.transaction_date LIKE ${fgst}
                AND fgs.date_created LIKE ${fgst}
                AND fgs.finish_product_id=fp.id
                AND mrd.finish_product_id=fp.id
                AND mrd.mr_type='Market Return'
                AND pmr.date_created LIKE ${pmr}
                AND pmrd.process_market_return_id=pmr.id
                AND pmrd.finish_product_id=fp.id
                AND ppp.finish_product_id=fgs.finish_product_id
                AND ppp.pricing_category_id=pc.id
                AND pc.short_name="DP"

                GROUP BY fgs.finish_product_id,fgs.finish_product_id,mrd.finish_product_id, pmrd.finish_product_id
                """
        }
        else{

            strSql = """

                SELECT fp.name AS productName, SUM(dpst.in_quantity) AS branchStock, (SUM(dpst.in_quantity)*ppp.price) AS bsprice ,
                SUM(fgst.in_quantity) AS factoryStock, (SUM(fgst.in_quantity)*ppp.price) AS fsprice, ( SUM(dpst.in_quantity)+SUM(fgst.in_quantity)) AS openingStock,
                fgst.in_quantity AS inq, ((fgst.in_quantity)*ppp.price) AS inPrice,  SUM(fgst.out_quantity) AS outq, (SUM(fgst.out_quantity)*ppp.price) AS OutPrice ,
                (dpst.in_quantity+fgst.in_quantity-SUM(fgst.out_quantity)-(SUM(mrd.quantity)+SUM(pmrd.qc_quantity))) AS stbranch,
                ((dpst.in_quantity+fgst.in_quantity-SUM(fgst.out_quantity)-(SUM(mrd.quantity)+SUM(pmrd.qc_quantity)))*ppp.price) AS stbranchPrice,
                ((fgst.in_quantity)-(SUM(mrd.quantity)+SUM(pmrd.qc_quantity))) AS stfactory,
                ((dpst.in_quantity+fgst.in_quantity-SUM(fgst.out_quantity)-(SUM(mrd.quantity)+SUM(pmrd.qc_quantity)))+((fgst.in_quantity)-(SUM(mrd.quantity)+SUM(pmrd.qc_quantity)))) AS closingStock,
                (((fgst.in_quantity)-(SUM(mrd.quantity)+SUM(pmrd.qc_quantity)))*ppp.price) AS stfactoryPrice,
                SUM(mrd.quantity) AS claim, (SUM(mrd.quantity)*ppp.price) AS claimPrice, SUM(pmrd.qc_quantity) AS adjust, (SUM(pmrd.qc_quantity)*ppp.price) AS adjustPrice,
                SUM(pmrd.qc_failed_quantity) AS actual, (SUM(pmrd.qc_failed_quantity)*ppp.price) AS actualPrice
                , ppp.price AS price

                FROM distribution_point_stock AS dps, distribution_point_stock_transaction AS dpst,
                finish_good_stock AS fgs, finish_good_stock_transaction AS fgst ,finish_product AS fp,
                market_return_details AS mrd,  process_market_return AS pmr, process_market_return_details AS pmrd,
                sub_warehouse AS sw , distribution_point_warehouse AS dpw, product_price_product AS ppp, pricing_category AS pc

                WHERE dpst.distribution_point_stock_id=${params.dp}
                AND sw.warehouse_id=dpw.warehouse_id
                AND dps.sub_warehouse_id=sw.id
                AND dpst.distribution_point_stock_id=dps.id
                AND dpst.transaction_date LIKE  ${dpst}
                AND fgst.finish_good_stock_id=fgs.id
                AND fgst.transaction_date LIKE ${fgst}
                AND fgs.date_created LIKE ${fgst}
                AND fgs.finish_product_id=fp.id
                AND mrd.finish_product_id=fp.id
                AND mrd.mr_type='Market Return'
                AND pmr.date_created LIKE ${pmr}
                AND pmrd.process_market_return_id=pmr.id
                AND pmrd.finish_product_id=fp.id
                AND ppp.finish_product_id=fgs.finish_product_id
                AND ppp.pricing_category_id=pc.id
                AND pc.short_name="DP"

                GROUP BY fgs.finish_product_id,fgs.finish_product_id,mrd.finish_product_id, pmrd.finish_product_id
                """

        }


        objList = sql.rows(strSql)

        return objList;
//distribution_point_id=${params.dp}

        /*
          SELECT fp.name AS productName, SUM(dpst.in_quantity) AS branchStock ,SUM(fgst.in_quantity) AS factoryStock,
                fgst.in_quantity AS inq,  SUM(fgst.out_quantity) AS outq, (dpst.in_quantity+fgst.in_quantity-SUM(fgst.out_quantity)-(SUM(mrd.quantity)+SUM(pmrd.qc_quantity))) AS stbranch,
                ((fgst.in_quantity)-(SUM(mrd.quantity)+SUM(pmrd.qc_quantity))) stfactory, SUM(mrd.quantity) AS claim, SUM(pmrd.qc_quantity) AS adjust, SUM(pmrd.qc_failed_quantity) AS actual
                , ppp.price AS price

                FROM distribution_point_stock AS dps, distribution_point_stock_transaction AS dpst,
                finish_good_stock AS fgs, finish_good_stock_transaction AS fgst ,finish_product AS fp,
                market_return_details AS mrd,  process_market_return AS pmr, process_market_return_details AS pmrd,
                sub_warehouse AS sw , distribution_point_warehouse AS dpw, product_price_product AS ppp, pricing_category AS pc

                WHERE dpst.distribution_point_stock_id=1
                AND sw.warehouse_id=dpw.warehouse_id
                AND dps.sub_warehouse_id=sw.id
                AND dpst.distribution_point_stock_id=dps.id
                AND dpst.transaction_date LIKE LIKE ${dpst}
                AND fgst.finish_good_stock_id=fgs.id
                AND fgst.transaction_date LIKE ${fgst}
                AND fgs.date_created LIKE ${fgst}
                AND fgs.finish_product_id=fp.id
                AND mrd.finish_product_id=fp.id
                AND mrd.mr_type='Market Return'
                AND pmr.date_created LIKE ${fgst}
                AND pmrd.process_market_return_id=pmr.id
                AND pmrd.finish_product_id=fp.id
                AND ppp.finish_product_id=fgs.finish_product_id
                AND ppp.pricing_category_id=pc.id
                AND pc.short_name="DP"

                GROUP BY fgs.finish_product_id,fgs.finish_product_id,mrd.finish_product_id, pmrd.finish_product_id
        * */

       /* SELECT fp.name AS productName, dpst.in_quantity AS branchStock ,fgst.in_quantity AS factoryStock,
        fgs.in_quantity AS inq,  SUM(fgst.out_quantity) AS outq, (dpst.in_quantity+fgst.in_quantity-SUM(fgst.out_quantity)) AS stbranch,
                (fgst.in_quantity) stfactory, SUM(mrd.quantity) AS claim, SUM(pmrd.qc_quantity) AS adjust, SUM(pmrd.qc_failed_quantity) AS actual

        FROM distribution_point_stock AS dps, distribution_point_stock_transaction AS dpst,
                                                                                      finish_good_stock AS fgs, finish_good_stock_transaction AS fgst ,finish_product AS fp,
                                                                                                                                                                         market_return_details AS mrd,  process_market_return AS pmr, process_market_return_details AS pmrd,

                                                                                                                                                                                                                                                                       sub_warehouse AS sw , distribution_point_warehouse AS dpw

        WHERE sw.warehouse_id=dpw.warehouse_id
        AND dps.sub_warehouse_id=sw.id
        AND dpst.distribution_point_stock_id=dps.id
        AND dpst.transaction_date LIKE ${dpst}
        AND fgst.finish_good_stock_id=fgs.id
        AND fgst.transaction_date LIKE ${fgst}
        AND fgs.finish_product_id=fp.id
        AND mrd.finish_product_id=fp.id
        AND mrd.mr_type='Market Return'
        AND pmr.date_created LIKE ${pmr}
        AND pmrd.process_market_return_id=pmr.id
        AND pmrd.finish_product_id=fp.id
        GROUP BY fgs.finish_product_id,mrd.finish_product_id, pmrd.finish_product_id*/
    }

    @Transactional(readOnly = true)
    public List fetchSalesChannelList() {
        String query = """SELECT csc.id as ID,csc.name as name FROM `customer_sales_channel` csc"""

        Sql sql = new Sql(dataSource)
        return sql.rows(query.toString())
    }

    @Transactional (readOnly =true)
    public List dpList(){
        ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()
      String query = """ SELECT distribution_point_id AS Id,
 distribution_point.`name` AS NAME
FROM application_user_distribution_point
JOIN distribution_point ON application_user_distribution_point.`distribution_point_id` = distribution_point.`id`
WHERE application_user_id =
"""+applicationUser.id;
        Sql sql = new Sql(dataSource)
        return  sql.rows(query.toString())
//SELECT  0 AS ID , 'All' AS NAME  UNION ALL
    }
    @Transactional (readOnly = true)
    public List chartOfAccounts(){
        String query ="""SELECT  0 AS ID , 'All' AS NAME
UNION ALL
SELECT id AS ID ,  CONCAT(chart_of_account_name, '(', chart_of_account_code_auto,')') AS NAME FROM chart_of_accounts  """
        Sql sql = new Sql(dataSource)
        return  sql.rows(query.toString())

    }
    @Transactional(readOnly = true)
    public List fetchDivisionList() {
        String query = """SELECT dv.id as ID,dv.name as name FROM `division` dv"""

        Sql sql = new Sql(dataSource)
        return sql.rows(query.toString())
    }

    @Transactional(readOnly = true)
    public List fetchDistrictList() {
        String query = """SELECT dis.id as ID,dis.name as name FROM `district` dis"""

        Sql sql = new Sql(dataSource)
        return sql.rows(query.toString())
    }


    @Transactional(readOnly = true)
    public List fetchThanaList() {
        String query = """SELECT tup.id as ID,tup.name as name FROM `thana_upazila_pouroshova` tup"""

        Sql sql = new Sql(dataSource)
        return sql.rows(query.toString())
    }



    @Transactional(readOnly = true)
    public List salesOfficerList(Long userId) {
        Sql sql = new Sql(dataSource)
        String query = """ SELECT DISTINCT customer_master.name,customer_territory_sub_area.customer_master_id id, customer_territory_sub_area.`territory_sub_area_id`
                            FROM `customer_territory_sub_area`

             INNER JOIN customer_master ON customer_master.`id` = customer_territory_sub_area.`customer_master_id`
             INNER JOIN `application_user` ON `application_user`.`customer_master_id` = customer_master.`id`
             INNER JOIN `customer_category` ON customer_category.id = customer_master.category_id
        WHERE `territory_sub_area_id` IN
            ( SELECT `territory_sub_area_id` FROM `customer_territory_sub_area` WHERE `customer_master_id`=
                (SELECT customer_master_id FROM `application_user` WHERE id = 61)
            )
        AND customer_territory_sub_area.customer_master_id <> (SELECT customer_master_id FROM `application_user` WHERE id = 61) AND customer_category.name ='Sales Man'
           GROUP BY customer_master.`id` """
        return sql.rows(query)
    }


}
