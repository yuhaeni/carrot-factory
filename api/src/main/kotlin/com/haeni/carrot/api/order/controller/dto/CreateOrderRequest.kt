package com.haeni.carrot.api.order.controller.dto

import com.haeni.carrot.api.order.service.dto.CreateOrderRequestDto
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive
import java.math.BigDecimal

data class CreateOrderRequest(
    @field:Positive
    val productId: Long,

    @field:NotBlank
    val productName: String,

    @field:Min(1)
    val quantity: Int,

    @field:Positive
    val totalAmount: BigDecimal,

    @field:Positive
    val customerId: Long
)

fun CreateOrderRequest.toDto() = CreateOrderRequestDto(
    productId = productId,
    productName = productName,
    quantity = quantity,
    totalAmount = totalAmount,
    customerId = customerId,
)