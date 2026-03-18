package com.haeni.carrot.api.order.service.dto

import com.haeni.carrot.api.order.controller.dto.CreateOrderResponse
import com.haeni.carrot.core.order.domain.Order
import com.haeni.carrot.core.order.domain.OrderStatus
import java.math.BigDecimal
import java.time.LocalDateTime

data class CreateOrderResponseDto(
    val orderId: Long,
    val productName: String,
    val quantity: Int,
    val totalAmount: BigDecimal,
    val status: OrderStatus,
    val createdAt: LocalDateTime
) {
    companion object {
        fun from(order: Order) = CreateOrderResponseDto(
            orderId = order.id,
            productName = order.productName,
            quantity = order.quantity,
            totalAmount = order.totalAmount,
            status = order.status,
            createdAt = order.createdAt
        )
    }
}

fun CreateOrderResponseDto.toResponse() = CreateOrderResponse(
    orderId = orderId,
    productName = productName,
    quantity = quantity,
    totalAmount = totalAmount,
    status = status,
    createdAt = createdAt
)