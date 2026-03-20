package com.haeni.carrot.core.order.domain

enum class OrderStatus(
    val description: String,
) {
    CREATED("주문 생성"),
    PAYMENT_COMPLETED("결제 완료"),
    STOCK_DEDUCTED("재고 차감 완료"),
    PROCESSING("주문 처리 중"),
    COMPLETED("주문 처리 완료"),
    CANCELLED("주문 취소"),
    FAILED("주문 처리 실패")
}