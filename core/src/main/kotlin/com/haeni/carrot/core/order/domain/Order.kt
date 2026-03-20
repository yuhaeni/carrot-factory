package com.haeni.carrot.core.order.domain

import com.haeni.carrot.core.order.shared.entity.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "orders")
class Order(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    // TODO 외부 상품 서비스에서 전달받은 값이라고 가정, 추후에 엔티티 추가
    @Column(nullable = false)
    val productId: Long,

    @Column(nullable = false)
    val productName: String,

    @Column(nullable = false)
    val quantity: Int,

    @Column(nullable = false)
    val totalAmount: BigDecimal,

    @Column(nullable = false)
    val customerId: Long,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: OrderStatus = OrderStatus.CREATED,

    ) : BaseEntity()