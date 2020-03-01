package com.bits.bdfp.inventory.product
import org.springframework.transaction.annotation.Transactional
import com.docu.common.Service
import javax.sql.DataSource
import groovy.sql.Sql
import com.docu.common.Action

class ProductTypeService extends Service {
   static transactional = false
   DataSource dataSource
   Sql sql
  @Transactional
  public ProductType create(Object object) {
    ProductType productType = (ProductType) object
    if (productType.save(false)) {
      return productType
    }
    return null
  }

@Transactional
  public Integer update(Object object) {
    try {
        ProductType productType = (ProductType) object
        if (productType.save()) {
            return new Integer(1)
        }
        else {
            return new Integer(0)
        }
    } catch (Exception ex){
        log.error(ex.message)
        throw new RuntimeException(ex.message)
    }

  }

  @Transactional
  public Integer delete(Object object) {
    ProductType productType = (ProductType) object
    productType.delete()
    return new Integer(1)
  }

  @Transactional(readOnly = true) 
  public Map getListForGrid(Action action) {
    List<ProductType> objList = ProductType.withCriteria {
     if(action.resultPerPage !=-1){
            maxResults(action.resultPerPage)
        }
      firstResult(action.start)
      order(action.sortCol, action.sortOrder)
    }
    long total = ProductType.count()
    return [objList: objList, count: total]
  }

  @Transactional(readOnly = true)
  public List<ProductType> list() {
    return ProductType.list()
  }

  @Transactional(readOnly = true)
  public ProductType read(Long id) {
    return ProductType.read(id)
  }

  @Transactional(readOnly = true)
  public ProductType search(String fieldName, String fieldValue) {
    String query = "from ProductType as docu where docu."+ fieldName +" = '" + fieldValue +"'"
    return ProductType.find(query)
  }
}
