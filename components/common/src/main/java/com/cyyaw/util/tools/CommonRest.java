package com.cyyaw.util.tools;


import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CommonRest<T> implements Serializable {

    private Integer code;

    private List<T> data;

    private String msg;

    private Integer total;

    private Integer page;

    private Integer size;

}
