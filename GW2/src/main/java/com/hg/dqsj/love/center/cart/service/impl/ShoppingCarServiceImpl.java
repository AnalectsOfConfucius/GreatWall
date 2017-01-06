package com.hg.dqsj.love.center.cart.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hg.dqsj.love.center.cart.dao.ShoppingCartDao;
import com.hg.dqsj.love.center.cart.entity.ShoppingCart;
import com.hg.dqsj.love.center.cart.entity.ShoppingCartGoods;
import com.hg.dqsj.love.center.cart.service.ShoppingCartService;

@Service
public class ShoppingCarServiceImpl implements ShoppingCartService {
	@Autowired
	private ShoppingCartDao dao;
	
	@Override
	public List<ShoppingCart> selectShoppingCart(Map<String, String> param) {
		return dao.selectShoppingCart(param);
	}

	@Override
	public void delete(Map<String,Object> param) {
		dao.delete(param);
	}

	@Override
	public void insertCart(ShoppingCartGoods shoppingCartGoods) {
		dao.insertCart(shoppingCartGoods);
	}

}
