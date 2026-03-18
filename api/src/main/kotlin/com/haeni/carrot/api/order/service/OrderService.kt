package com.haeni.carrot.api.order.service

import com.haeni.carrot.api.order.service.dto.CreateOrderRequestDto
import com.haeni.carrot.api.order.service.dto.CreateOrderResponseDto
import com.haeni.carrot.core.order.domain.Order
import com.haeni.carrot.core.order.repository.OrderRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class OrderService(
    private val orderRepository: OrderRepository,
) {
    @Transactional
    fun createOrder(requestDto: CreateOrderRequestDto): CreateOrderResponseDto {
        val order = Order(
            productId = requestDto.productId,
            productName = requestDto.productName,
            quantity = requestDto.quantity,
            totalAmount = requestDto.totalAmount,
            customerId = requestDto.customerId
        )

        val savedOrder = orderRepository.save(order)
        // TODO  Kafka 이벤트 발행 추가
        return CreateOrderResponseDto.from(savedOrder)
    }
}