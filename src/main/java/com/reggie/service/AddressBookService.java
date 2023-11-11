package com.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.reggie.entity.AddressBook;
import com.reggie.mapper.AddressBookMapper;
import com.reggie.service.impl.AddressBookServiceImpl;
import org.springframework.stereotype.Service;

public interface AddressBookService extends IService<AddressBook> {
}
