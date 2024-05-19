package com.soleel.paymentaccount.model

import com.soleel.finanzas.core.database.entities.PaymentAccountEntity
import com.soleel.finanzas.core.database.extras.PaymentAccountEntityWithTotalAmount


class PaymentAccountDbModel {
    companion object {
        fun asExternalModel(paymentAccountEntity: PaymentAccountEntity): PaymentAccount {
            return PaymentAccount(
                id = paymentAccountEntity.id,
                name = paymentAccountEntity.name,
                createAt = paymentAccountEntity.createAt,
                updatedAt = paymentAccountEntity.updatedAt,
                accountType = paymentAccountEntity.accountType,
            )
        }

        fun asExternalModelList(paymentAccountEntities: List<PaymentAccountEntity>): List<PaymentAccount> {
            return paymentAccountEntities.map(transform = ::asExternalModel)
        }

        fun asExternalModelWithTotalAmount(paymentAccountEntityWithTotalAmount: PaymentAccountEntityWithTotalAmount): PaymentAccount {
            return PaymentAccount(
                id = paymentAccountEntityWithTotalAmount.paymentAccountEntity.id,
                name = paymentAccountEntityWithTotalAmount.paymentAccountEntity.name,
                amount = paymentAccountEntityWithTotalAmount.totalIncome - paymentAccountEntityWithTotalAmount.totalExpense,
                createAt = paymentAccountEntityWithTotalAmount.paymentAccountEntity.createAt,
                updatedAt = paymentAccountEntityWithTotalAmount.paymentAccountEntity.updatedAt,
                accountType = paymentAccountEntityWithTotalAmount.paymentAccountEntity.accountType,
            )
        }

        fun asExternalModelWithTotalAmountList(paymentAccountEntitiesWithTotalAmount: List<PaymentAccountEntityWithTotalAmount>): List<PaymentAccount> {
            return paymentAccountEntitiesWithTotalAmount.map(transform = ::asExternalModelWithTotalAmount)
        }

        fun asInternalModel(paymentAccount: PaymentAccount): PaymentAccountEntity {
            return PaymentAccountEntity(
                id = paymentAccount.id,
                name = paymentAccount.name,
                createAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis(),
                accountType = paymentAccount.accountType
            )
        }
    }
}

