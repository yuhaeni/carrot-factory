package com.haeni.carrot.core.order.event

import java.math.BigDecimal
import java.time.LocalDateTime

data class OrderEvent(
    val orderId: Long,
    val productId: Long,
    val productName: String,
    val quantity: Int,
    val totalAmount: BigDecimal,
    val customerId: Long,
    val eventType: OrderEventType,
    val occurredAt: LocalDateTime = LocalDateTime.now()
)
