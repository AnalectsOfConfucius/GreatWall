package com.hg.dqsj.love.center.cart.dao;

import java.util.List;
import java.util.Map;

import com.hg.dqsj.love.center.cart.entity.ShoppingCart;
import com.hg.dqsj.love.center.cart.entity.ShoppingCartGoods;

public interface ShoppingCartDao {
	//查询购物车
	public List<ShoppingCart> selectShoppingCart(Map<String,String> param);
	//添加购物车
	public void insertCart(ShoppingCartGoods shoppingCartGoods);
	//逻辑删除购物车
	public void delete(Map<String,Object> param);
}
