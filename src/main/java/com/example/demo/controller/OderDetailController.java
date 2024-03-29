package com.example.demo.controller;

import com.example.demo.model.OrderDetail;
import com.example.demo.model.Orders;
import com.example.demo.model.Product;
import com.example.demo.service.IOrderDetailService;
import com.example.demo.service.IOrdersService;
import com.example.demo.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@CrossOrigin("*")
@RequestMapping("/api/orderDetails")
public class OderDetailController {
   @Autowired
   IOrderDetailService iOrderDetailService;
   @Autowired
   IOrdersService iOrdersService;
   @Autowired
   IProductService iProductService;



   @PostMapping
   public ResponseEntity<OrderDetail> save(@PathVariable Long id, @RequestBody OrderDetail orderDetail) {
      Product product=iProductService.findById(orderDetail.getProduct().getId()).get();
      Orders orders =iOrdersService.findById(orderDetail.getOrders().getId()).get();
      product.setQuantity(product.getQuantity()-orderDetail.getQuantity());//số lượng cũ - số lượng đã bị mua ra số lượng còn lại
      iProductService.save(product);
      orders.setTotalPrice(orders.getTotalPrice()+product.getQuantity()*product.getPrice());//Tổng giá = tổng giá ban đầu(=0)+(số sản phẩm * giá sản phẩm)
      iOrdersService.save(orders);

      return new ResponseEntity<>(iOrderDetailService.save(orderDetail), HttpStatus.CREATED);
   }


   @GetMapping//Tìm
   public ResponseEntity<Iterable<OrderDetail>> findAll() {
      return new ResponseEntity<>(iOrderDetailService.findAll(), HttpStatus.OK);
   }
//
//   @PostMapping("")//Add-Thêm-RequestBody để lấy product trực tiếp từ web về để dùng
//   public ResponseEntity add(@RequestBody OrderDetail orderDetail) {
//      iOrderDetailService.save(orderDetail);
//      return new ResponseEntity<>(HttpStatus.CREATED);
//
//   }
//
//   @PutMapping("/{id}")//EDIT-Sửa
//   public ResponseEntity<OrderDetail> update(@PathVariable Long id, @RequestBody OrderDetail orderDetail) {
//      Optional<OrderDetail> orderDetailOptional = iOrderDetailService.findById(id);
//      if (!orderDetailOptional.isPresent()) {
//         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//      }
//      orderDetail.setId(orderDetailOptional.get().getId());
//      iOrderDetailService.save(orderDetail);
//      return new ResponseEntity<>(HttpStatus.OK);
//   }
//
//   @DeleteMapping("/{id}")//Delete-Xóa
//   public ResponseEntity<OrderDetail> delete(@PathVariable Long id) {
//      Optional<OrderDetail> orderDetail = iOrderDetailService.findById(id);
//      if (!orderDetail.isPresent()) {
//         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//      }
//      iOrderDetailService.remove(id);
//      return new ResponseEntity<>(orderDetail.get(), HttpStatus.OK);
//   }
}
