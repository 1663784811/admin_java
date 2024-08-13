package com.cyyaw.tx.phone;

import java.util.List;

public interface PhoneService {


    List<PhoneEntity> phoneList();


    String phoneImage(String phone);


    String phoneStructure(String phone);


}
