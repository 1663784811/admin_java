package com.cyyaw.user.service;


import com.cyyaw.user.table.entity.TPower;

import java.util.List;

public interface MenuService {


    List<TPower> getAdminMenu(String tid);
}
