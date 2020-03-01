package com.bits.bdfp.customer
import org.springframework.transaction.annotation.Transactional
import com.docu.common.Service
import javax.sql.DataSource
import groovy.sql.Sql
import com.docu.common.Action

class CustomerTypeService extends Service {
   static transactional = false
   DataSource dataSource
   Sql sql
  @Transactional
  public CustomerType create(Object object) {
    CustomerType customerType = (CustomerType) object
    if (customerType.save(false)) {
      return customerType
    }
    return null
  }

@Transactional
  public Integer update(Object object) {
    CustomerType customerType = (CustomerType) object
    if (customerType.save()) {
      return new Integer(1)
    }
    else {
       return new Integer(0)
    }
  }

  @Transactional
  public Integer delete(Object object) {
    CustomerType customerType = (CustomerType) object
    customerType.delete()
    return new Integer(1)
  }

  @Transactional(readOnly = true) 
  public Map getListForGrid(Action action) {
    List<CustomerType> objList = CustomerType.withCriteria {
     if(action.resultPerPage !=-1){
            maxResults(action.resultPerPage)
        }
      firstResult(action.start)
      order(action.sortCol, action.sortOrder)
    }
    long total = CustomerType.count()
    return [objList: objList, count: total]
  }

  @Transactional(readOnly = true)
  public List<CustomerType> list() {
    return CustomerType.list()
  }

  @Transactional(readOnly = true)
  public CustomerType read(Long id) {
    return CustomerType.read(id)
  }

  @Transactional(readOnly = true)
  public CustomerType search(String fieldName, String fieldValue) {
    String query = "from CustomerType as docu where docu."+ fieldName +" = '" + fieldValue +"'"
    return CustomerType.find(query)
  }
}
