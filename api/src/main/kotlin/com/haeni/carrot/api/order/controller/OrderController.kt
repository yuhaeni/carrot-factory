package com.haeni.carrot.api.order.controller

import com.haeni.carrot.api.order.controller.dto.CreateOrderRequest
import com.haeni.carrot.api.order.controller.dto.CreateOrderResponse
import com.haeni.carrot.api.order.controller.dto.toDto
import com.haeni.carrot.api.order.controller.dto.toResponse
import com.haeni.carrot.api.order.service.OrderService
import com.haeni.carrot.core.order.shared.dto.ApiResponse
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/orders")
class OrderController(
    private val orderService: OrderService
) {
    @PostMapping
    fun createOrder(
        @Valid @RequestBody createOrderRequest: CreateOrderRequest
    ): ApiResponse<CreateOrderResponse> =
        ApiResponse.success(orderService.createOrder(createOrderRequest.toDto()).toResponse())
}