package com.haeni.carrot.api.order.controller.dto

import com.haeni.carrot.api.order.service.dto.CreateOrderResponseDto
import com.haeni.carrot.core.order.domain.OrderStatus
import java.math.BigDecimal
import java.time.LocalDateTime

data class CreateOrderResponse(
    val orderId: Long,
    val productName: String,
    val quantity: Int,
    val totalAmount: BigDecimal,
    val status: OrderStatus,
    val createdAt: LocalDateTime
)

fun CreateOrderResponseDto.toResponse() = CreateOrderResponse(
    orderId = orderId,
    productName = productName,
    quantity = quantity,
    totalAmount = totalAmount,
    status = status,
    createdAt = createdAt
)