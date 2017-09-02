package com.bookstore.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.domain.User;
import com.bookstore.domain.UserPayment;
import com.bookstore.repository.UserPaymentRepository;
import com.bookstore.service.UserPaymentService;
@Service
public class UserPaymentServiceImpl implements UserPaymentService {

	@Autowired
	UserPaymentRepository userPaymentRepository;
	
	@Override
	public UserPayment findById(Long id) {
		return userPaymentRepository.findOne(id);
	}
	
	@Override
	public void removeById(Long id) {
		userPaymentRepository.delete(id);
	}
}
 