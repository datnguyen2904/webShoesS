package com.nhom1.store.repository;

import java.util.List;
import java.util.Optional;

import com.nhom1.store.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.nhom1.store.domain.Order;

public interface OrderRepository extends CrudRepository<Order, Long>, JpaRepository<Order, Long> {

	List<Order> findByUser(User user);
	
	@EntityGraph(attributePaths = { "cartItems", "shipping" })
	Order findEagerById(Long id);
	@Query(value = "select a.title, sum(ci.qty), sum(uo.order_total) \n" +
			"from user_order uo join cart_item ci on uo.id = ci.order_id \n" +
			"join article a on ci.article_id = a.id \n" +
			" where uo.order_status  = 'Đã hoàn thành' and uo.order_date between :fromDate and :endDate " +
			"group by a.title ", nativeQuery = true)
	List<Object[]> getAllReportByDate(Optional<String> fromDate, Optional<String> endDate);
}
