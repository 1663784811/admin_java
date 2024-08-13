package com.cyyaw.tx.phone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PhoneServiceImpl implements PhoneService {


    @Autowired
    private PhoneDao phoneDao;


    @Override
    public List<PhoneEntity> phoneList() {
        return phoneDao.phoneList();
    }


    @Override
    public String phoneImage(String phone) {
        return phoneDao.phoneImage(phone);
    }

    @Override
    public String phoneStructure(String phone) {
        return phoneDao.phoneStructure(phone);
    }
}
