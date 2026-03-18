package com.haeni.carrot.core.order.repository

import com.haeni.carrot.core.order.domain.Order
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository : JpaRepository<Order, Long>