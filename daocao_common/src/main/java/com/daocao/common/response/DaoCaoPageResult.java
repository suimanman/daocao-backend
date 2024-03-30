package com.daocao.common.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DaoCaoPageResult<T> implements Serializable {
    private List<T> list;
    private long total;
}
