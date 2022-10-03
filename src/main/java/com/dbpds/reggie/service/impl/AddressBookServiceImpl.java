package com.dbpds.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dbpds.reggie.entity.AddressBook;
import com.dbpds.reggie.mapper.AddressBookMapper;
import com.dbpds.reggie.service.AddressBookService;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
}
