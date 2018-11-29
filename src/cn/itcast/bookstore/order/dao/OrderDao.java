package cn.itcast.bookstore.order.dao;


import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.bookstore.book.domain.Book;
import cn.itcast.bookstore.order.domain.Order;
import cn.itcast.bookstore.order.domain.OrderItem;
import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;

public class OrderDao {

	private QueryRunner qr = new TxQueryRunner();
	
	public void addOrder(Order order){
		
		
		try {
			String sql = "insert into orders values(?,?,?,?,?,?)";
			
			Timestamp timestamp= new Timestamp(order.getOrdertime().getTime());
			
			Object[]params = {order.getOid(),timestamp,order.getTotal(),order.getState()
					,order.getOwner().getUid(),order.getAdderss()};
			
			qr.update(sql,params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void addOrderItemList(List<OrderItem> orderItemList){
		
		try {
			String sql = "insert into orderitem values(?,?,?,?,?)";
			
			Object[][]params =new Object[orderItemList.size()][];
			
			for(int i = 0;i<orderItemList.size();i++ ){
				
				OrderItem item = orderItemList.get(i);
				
				params[i] = new Object[]{item.getIid(),item.getCount(),
						item.getSubtotal(),item.getOrder().getOid(),item.getBook().getBid()};
						
			}
			
			qr.batch(sql, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Order> findByUid(String uid) {


		
		try {
			String sql = "select * from orders where uid=?";
			List<Order>orderList = qr.query(sql, new BeanListHandler<Order>(Order.class),uid);
			
			for(Order order : orderList){
				
				loadOrderItems(order);			
				
			}
			return orderList;
			
			
		} catch (SQLException e) {


			e.printStackTrace();
			return null ;
		}
		
		
	}

	private void loadOrderItems(Order order) {


		
		
		try {
			String sql = "select * from orderitem i,book b where i.bid=b.bid and oid=?";
			List<Map<String,Object>>mapList = qr.query(sql, new MapListHandler(),order.getOid());
		
			List<OrderItem> orderItemList = toOrderItemList(mapList);
			
			order.setOrderItemList(orderItemList);
		
		
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

	private List<OrderItem> toOrderItemList(List<Map<String, Object>> mapList) {

        List<OrderItem> orderItemList =  new ArrayList<OrderItem>();
        
		for(Map<String,Object>map:mapList){
			
			OrderItem item  = toOrderItem(map);
			orderItemList.add(item);
			
		}
		return orderItemList;
	}

	private OrderItem toOrderItem(Map<String, Object> map) {


		OrderItem orderItem = CommonUtils.toBean(map, OrderItem.class);
		Book book = CommonUtils.toBean(map, Book.class);
		orderItem.setBook(book);
		
		return orderItem;
		
	}

	public Object load(String oid) {


	
		
		try {
			String sql  ="select * from orders where oid=?";
			Order order = qr.query(sql, new BeanHandler<Order>(Order.class),oid);
			loadOrderItems(order);
			return order;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public int getStateByOid(String oid){
		
		
		try {
			String sql ="select state from orders where oid=?";
			return (Integer) qr.query(sql, new ScalarHandler(),oid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return (Integer) null;
		}
	}
	
    public void updateState(String oid,int state){
		
		
		try {
			String sql ="update orders set state=? where oid=?";
			qr.update(sql,state,oid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	}
	
	
	
	
	
	
}
