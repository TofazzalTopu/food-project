package com.docu.accesscontrol

import com.docu.app.MenuGroupUtil
import groovy.sql.Sql
import groovy.sql.GroovyRowResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy

class MenuGroupService {

    static transactional = false

    @Autowired
    TransactionAwareDataSourceProxy dataSource

    public boolean save(Map mapInstance){
        MenuGroup menuGroup = (MenuGroup)mapInstance.get("menugroup")
        List<MenuItem> menuItemList = (List<MenuItem>) mapInstance.get("menuitems")
        try{
            menuGroup.save()
            String query = "delete from menu_item where menu_group_id = ${menuGroup.id}"
            Sql sql = new Sql(dataSource)
            sql.execute(query)

            for (MenuItem menuItem: menuItemList){
                menuItem.menuGroupId = menuGroup.id
                menuItem.save()
            }
            // Re Initialize Menu Group
            MenuGroupUtil.instance.resolve()
        }
        catch(Exception ex){
            println ex
        }

        return true
    }

    public boolean saveMenuGroupSorting(Map map){
         List<MenuGroup> menuGroupList  = (List<MenuGroup>)map.get("menuGroupList")
        try{
            for (MenuGroup menuGroup: menuGroupList){
                menuGroup.save()
            }
            // Re Initialize Menu Group
            MenuGroupUtil.instance.resolve()
        }
        catch(Exception ex){
            println ex
        }

        return true
    }

    public boolean saveMenuItemSorting(Map map){
        List<MenuItem> menuItemList = (List<MenuItem>)map.get('menuItemList')
        try{
            for (MenuItem menuItem: menuItemList){
                menuItem.save()
            }
            // Re Initialize Menu Group
            MenuGroupUtil.instance.resolve()
        }
        catch(Exception ex){
            println ex
        }

        return true
    }

    public List<GroovyRowResult> getMenuItemListByMenuGroupId(Long menuGroupId){
        String query = """
            SELECT
                mi.id,
                mi.position,
                fa.feature_name as "featureName"
            FROM
                menu_item AS mi
                LEFT JOIN feature_info AS fa ON (mi.feature_info_id = fa.id)
            WHERE
                menu_group_id = ${menuGroupId}
            ORDER BY
                mi.position
        """
        Sql sql = new Sql(dataSource)
        return sql.rows(query)
    }

    public Object getModuleInfo(Map params){
        ModuleInfo moduleInfo = ModuleInfo.read(params.moduleInfoId)
        return moduleInfo
    }
}
