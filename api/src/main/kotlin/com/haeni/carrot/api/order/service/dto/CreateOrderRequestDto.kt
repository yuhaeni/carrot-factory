package com.haeni.carrot.api.order.service.dto

import java.math.BigDecimal

data class CreateOrderRequestDto(
    val productId: Long,
    val productName: String,
    val quantity: Int,
    val totalAmount: BigDecimal,
    val customerId: Long
)
