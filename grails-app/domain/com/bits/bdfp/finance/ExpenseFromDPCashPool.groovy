package com.bits.bdfp.finance

import com.bits.bdfp.accounts.ChartOfAccounts
import com.bits.bdfp.common.CashPool
import com.bits.bdfp.inventory.sales.DistributionPoint
import com.docu.security.ApplicationUser

class ExpenseFromDPCashPool {

    DistributionPoint   distributionPoint
    CashPool            cashPool
    ChartOfAccounts     expenditureHeads

    Double              expenseAmount
    String              transactionNo
    String              remarks
    Boolean             isActive

    ApplicationUser     userCreated
    ApplicationUser     userUpdated
    Date                dateCreated
    Date                lastUpdated

    static constraints = {
        distributionPoint(blank: true,nullable: true)
        cashPool(nullable: true)
        expenditureHeads(nullable: true)
        expenseAmount(blank: true,nullable: true)
        transactionNo(blank: true, nullable: true)
        remarks(blank: true,nullable: true)

        isActive(nullable: true)
        userCreated(blank: false, nullable: false)
        userUpdated(blank: true, nullable: true)
    }

    /*static mapping = {
        id(generator: 'org.hibernate.id.enhanced.SequenceStyleGenerator',
           params: [sequence_name: 'Expense_FromDP_Cash_Pool_seq', initial_value: 1])
    }*/
    @Override
    public String toString() {
        return id;
    }
}
