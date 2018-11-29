package cn.itcast.bookstore.order.service;

import java.sql.SQLException;
import java.util.List;

import cn.itcast.bookstore.order.dao.OrderDao;
import cn.itcast.bookstore.order.domain.Order;
import cn.itcast.jdbc.JdbcUtils;

public class OrderService {

	private OrderDao orderDao = new OrderDao();
	
	
	
	
	public void add(Order order){
		
	
		
		try {
			JdbcUtils.beginTransaction();
			
			orderDao.addOrder(order);
		    orderDao.addOrderItemList(order.getOrderItemList());
			
			
			JdbcUtils.commitTransaction();
		} catch (SQLException e) {
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}




	public List<Order> myOrders(String uid) {

       return orderDao.findByUid(uid);
	}




	public Object load(String oid) {
		// TODO Auto-generated method stub
		return orderDao.load(oid);
	}
	
	public void confirm(String oid)throws OrderException{
		
		int state = orderDao.getStateByOid(oid);
		
		if(state!=3)throw new OrderException("订单确认失败！！");
		
		
		orderDao.updateState(oid, 4);
	}
}
