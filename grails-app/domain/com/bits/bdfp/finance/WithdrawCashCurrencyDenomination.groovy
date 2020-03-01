package com.bits.bdfp.finance

import com.bits.bdfp.currency.CurrencyDemonstration

class WithdrawCashCurrencyDenomination {

    WithdrawCashFromDepositPool withdrawCashFromDepositPool
    CurrencyDemonstration       currencyDemonstration
    int                         quantity

    static constraints = {
        withdrawCashFromDepositPool(nullable: false)
        currencyDemonstration(nullable: false)
        quantity(nullable: false)
    }
}
