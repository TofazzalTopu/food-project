package com.bits.bdfp.finance.customerpayment

import com.bits.bdfp.accounts.SubLedger
import com.bits.bdfp.common.BankAccount
import com.bits.bdfp.common.CashPool
import com.bits.bdfp.currency.CurrencyDemonstration
import com.bits.bdfp.customer.CustomerType
import com.bits.bdfp.finance.CustomerAccount
import com.bits.bdfp.finance.CustomerPayment
import com.bits.bdfp.finance.CustomerPaymentCurrencyDenomination
import com.bits.bdfp.finance.CustomerPaymentService
import com.bits.bdfp.inventory.demandorder.CustomerDemandOrderPayment
import com.bits.bdfp.inventory.warehouse.FinishedProductBooked
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.common.CodeGenerationUtil
import com.docu.common.Action
import com.docu.common.Message
import com.docu.commons.DateUtil
import com.docu.security.ApplicationUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.text.SimpleDateFormat

/**
 * Created by prianka.adhikary on 9/30/2015.
 */
@Component("createCustomerPaymentDepositPoolAction")
class CreateCustomerPaymentDepositPoolAction extends Action {

    private static final Log log = LogFactory.getLog(this)

    @Autowired
    CustomerPaymentService customerPaymentService
    Message message

    public Object preCondition(Object params, Object object) {
        try {
            Map map = (Map) object
//            CustomerPayment customerPayment = map.customerPayment
//            List<CustomerPaymentCurrencyDenomination> customerPaymentCurrencyDenominationList = map.customerPaymentCurrencyDenominationList
            List<SubLedger> subLedgers = map.subLedgers
            boolean isValidate = true
            message = this.getMessage("Customer Payment", Message.SUCCESS, SUCCESS_SAVE)
//            if (!customerPayment.validate()) {
//                isValidate = false
//                message = this.getValidationErrorMessage(customerPayment)
//            }
//            if (customerPaymentCurrencyDenominationList && customerPaymentCurrencyDenominationList.size() > 0) {
//                customerPaymentCurrencyDenominationList.each {
//                    if (!it.validate()) {
//                        isValidate = false
//                        message = this.getValidationErrorMessage(it)
//                    }
//                }
//            }

            if (subLedgers && subLedgers.size() > 0) {
                subLedgers.each {
                    if (!it.validate()) {
                        isValidate = false
                        message = this.getValidationErrorMessage(it)
                    }
                }
            }

            return message
        } catch (Exception ex) {
            log.error(ex.message)
            message = this.getMessage("Customer Payment", Message.ERROR, "Exception-${ex.message}")
            return message
        }
    }

    public Object execute(Object params, Object object) {
        try {
//            List<CustomerPaymentCurrencyDenomination> customerPaymentCurrencyDenominationList = []
//            CustomerPaymentCurrencyDenomination customerPaymentCurrencyDenomination = null
//            CustomerPaymentCurrencyDenomination customerPaymentCurrencyDenominationOld = null

            ApplicationUser applicationUser = (ApplicationUser) object
//            CustomerPayment customerPaymentOld = CustomerPayment.read(params?.id)
//            CustomerPayment customerPayment = new CustomerPayment()
//            customerPayment.customerMaster = customerPaymentOld.customerMaster
//            customerPayment.version = customerPaymentOld.version
//            customerPayment.amount = customerPaymentOld.amount
//            customerPayment.bankPaymentMethod = customerPaymentOld.bankPaymentMethod
//            customerPayment.refNo = customerPaymentOld.refNo
//            customerPayment.remark = customerPaymentOld.remark
//            customerPayment.isSecurityDeposit = customerPaymentOld.isSecurityDeposit
//            customerPayment.confirmAmount = customerPaymentOld.confirmAmount
//            customerPayment.invoices = customerPaymentOld.invoices
//            customerPayment.invoiceIssueDates = customerPaymentOld.invoiceIssueDates
//            customerPayment.transNo = CodeGenerationUtil.generate(8).toString()
//            customerPayment.mrNo = CodeGenerationUtil.generate(8).toString()
//            customerPayment.dateTransaction = new Date()
//            customerPayment.dateCreated = new Date()
//            customerPayment.userCreated = applicationUser
            CashPool cashPoolDepositor = CashPool.read(Long.parseLong(params?.dipositorCashPool))

//            String invoices = ''
//            String invoiceIssueDates = ''

//            if (params.paymentMode == 'Bank') {
//                customerPayment.paymentMode = 'Bank'
//                customerPayment.bankAccount = BankAccount.read(params?.bankAcId)
//            } else {
//                customerPayment.paymentMode = 'Cash'
//                customerPayment.cashPool = CashPool.read(Long.parseLong(params.cashPoolId))
//                customerPaymentCurrencyDenominationOld = CustomerPaymentCurrencyDenomination.findAllByCustomerPayment(customerPaymentOld)
//                customerPaymentCurrencyDenominationOld.each { key, val ->
//                    if (val instanceof Map && Float.parseFloat(val.qty) > 0) {
//                        CurrencyDemonstration currencyDemonstration = CurrencyDemonstration.read(Long.parseLong(val.id))
//                        customerPaymentCurrencyDenomination = new CustomerPaymentCurrencyDenomination()
//                        customerPaymentCurrencyDenomination.currencyDemonstration = currencyDemonstration
//                        customerPaymentCurrencyDenomination.customerPayment = customerPayment
//                        customerPaymentCurrencyDenomination.dateCreated = new Date()
//                        customerPaymentCurrencyDenomination.quantity = Long.parseLong(val.qty)
//                        customerPaymentCurrencyDenomination.userCreated = applicationUser
//                        customerPaymentCurrencyDenominationList.add(customerPaymentCurrencyDenomination)
//                    }
//                }
//            }

            EnterpriseConfiguration enterpriseConfiguration = EnterpriseConfiguration.read(cashPoolDepositor.enterpriseConfiguration.id)

            Date dateNow = new Date()
            SimpleDateFormat formatMonth = new SimpleDateFormat("MM")
            String currentMonth = formatMonth.format(dateNow)
            SimpleDateFormat formatYear = new SimpleDateFormat("YY")
            String currentYear = formatYear.format(dateNow)

            List<SubLedger> subLedgers = []
            SubLedger subLedger

            subLedger = new SubLedger()
            if (params.paymentMode == 'Bank') {
                subLedger.accCode = BankAccount.read(params?.bankAcId).ledgerAccountCode
//                subLedger.accCode = customerPayment.bankAccount.ledgerAccountCode
                subLedger.description = "Bank Account"
            } else {
                subLedger.accCode = CashPool.read(Long.parseLong(params.cashPoolId)).accountNo
//                subLedger.accCode = customerPayment.cashPool.accountNo
                subLedger.description = "Deposit Pool Account"
            }
            subLedger.debit = Double.parseDouble(params.amountToDeposit)
//            subLedger.debit = customerPayment.amount
            subLedger.credit = 0
            subLedger.transactionNo = CodeGenerationUtil.generate(8).toString()
//            subLedger.transactionNo = customerPayment.transNo
            subLedger.transactionType = 2
            subLedger.vin = CodeGenerationUtil.instance.getCode(enterpriseConfiguration, "VOUCHER", enterpriseConfiguration.code, "", "", "", "", "", currentYear, currentMonth, "", "")
            subLedger.dateCreated = dateNow
            subLedger.userCreated = applicationUser
            subLedger.isActive = true
            subLedgers.add(subLedger)

            subLedger = new SubLedger()
            subLedger.accCode = cashPoolDepositor.accountNo
            subLedger.description = "Cash deposited to bank"
            subLedger.credit = Double.parseDouble(params.amountToDeposit)
//            subLedger.credit = customerPayment.amount
            subLedger.debit = 0
            subLedger.transactionNo = CodeGenerationUtil.generate(8).toString()
//            subLedger.transactionNo = customerPayment.transNo
            subLedger.transactionType = 2
            subLedger.vin = CodeGenerationUtil.instance.getCode(enterpriseConfiguration, "VOUCHER", enterpriseConfiguration.code, "", "", "", "", "", currentYear, currentMonth, "", "")
            subLedger.dateCreated = dateNow
            subLedger.userCreated = applicationUser
            subLedger.isActive = true
            subLedgers.add(subLedger)


            Map map = [:]
//            map.put('customerPayment', customerPayment)
//            map.put('customerPaymentCurrencyDenominationList', customerPaymentCurrencyDenominationList)
            map.put('subLedgers', subLedgers)
//            map.put('customerPaymentOld', customerPaymentOld)

            message = this.preCondition(params, map)
            if (message.type == 1) {
                int noOfRows = customerPaymentService.createPaymentDepositPool(map)
                if (noOfRows > 0) {
                    message = this.getMessage("Customer Payment", Message.SUCCESS, SUCCESS_SAVE)
                } else {
                    message = this.getMessage("Customer Payment", Message.ERROR, FAIL_SAVE)
                }
            }
            return message
        } catch (Exception ex) {
            log.error(ex.message)
            message = this.getMessage("CustomerPayment", Message.ERROR, "Exception-${ex.message}")
            return message
        }
    }

    public Object postCondition(Object params, Object object) {
        return null
    }
}
