package com.nhom1.store.controller;

import com.nhom1.store.dto.ReportDTO;
import com.nhom1.store.repository.OrderRepository;
import com.nhom1.store.service.OrderService;
import com.nhom1.store.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;
    @RequestMapping("/order-list")
    public String orderList(Model model){
        List<Order> orders = orderService.findAllOrders();
        model.addAttribute("orders", orders);
        return "orderList";
    }
    @RequestMapping("/report-list")
    public String reportList(Model model) {
        LocalDate currentDate = LocalDate.now();
        LocalDate oneMonthAgo = currentDate.minusMonths(1);
        List<Object[]> reportData = orderRepository.getAllReportByDate(Optional.of(oneMonthAgo.toString()), Optional.of(currentDate.toString()));
        List<ReportDTO> reportDTOs = new ArrayList<>();
        BigDecimal sum = new BigDecimal(BigInteger.ZERO);
        for (Object[] data : reportData) {
            String productName = (String) data[0];
            int qty = ((Number) data[1]).intValue();
            BigDecimal orderTotal = new BigDecimal(((Number) data[2]).doubleValue());
            ReportDTO reportDTO = new ReportDTO(productName, qty, orderTotal);
            sum = sum.add(orderTotal);
            reportDTOs.add(reportDTO);
        }
        model.addAttribute("reports", reportDTOs);
        model.addAttribute("sum", sum);
        model.addAttribute("fromDate", oneMonthAgo.toString());
        model.addAttribute("toDate",currentDate.toString() );
        return "reportList";
    }

    @RequestMapping(value = "/report-list", method = RequestMethod.POST)
    public String filterByDate(Model model, HttpServletRequest request) {

        String date1 = request.getParameter("fromDate");
        String date2 = request.getParameter("toDate");
        List<Object[]> reportData = orderRepository.getAllReportByDate(Optional.of(date1), Optional.of(date2));
        List<ReportDTO> reportDTOs = new ArrayList<>();
        BigDecimal sum = new BigDecimal(BigInteger.ZERO);
        for (Object[] data : reportData) {
            String productName = (String) data[0];
            int qty = ((Number) data[1]).intValue();
            BigDecimal orderTotal = new BigDecimal(((Number) data[2]).doubleValue());
            ReportDTO reportDTO = new ReportDTO(productName, qty, orderTotal);
            sum = sum.add(orderTotal);
            reportDTOs.add(reportDTO);
        }

        model.addAttribute("reports", reportDTOs);
        model.addAttribute("sum", sum);
        model.addAttribute("fromDate", date1);
        model.addAttribute("toDate", date2);

        return "reportList";
    }
    @RequestMapping(value="/complete", method=RequestMethod.POST)
    public String finishOrder(@RequestParam("orderId") Long orderId) {
        Order order = orderService.getById(orderId);
        if (order != null) {
            order.setOrderStatus("Đã hoàn thành");
            orderService.updateOrder(order);
        }
        return "redirect:order-list";
    }

    @RequestMapping("/cancel")
    public String cancelOrder(@RequestParam("orderId") Long orderId, Model model) {
        Order order = orderService.getById(orderId);
        model.addAttribute("order", order);
        return "note";
    }

    @RequestMapping(value="/cancel", method=RequestMethod.POST)
    public String cancelOrderPost(@RequestParam("orderId") Long orderId, @RequestParam("note") String note) {
        Order order = orderService.getById(orderId);
        order.setOrderStatus("Đã huỷ bỏ     ");
        order.setNote(note);
        orderService.updateOrder(order);
        return "redirect:order-list";
    }

}
