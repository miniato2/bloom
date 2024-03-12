package com.cov.bloom.order.model.dao;

import com.cov.bloom.order.model.dto.OptionDTO;
import com.cov.bloom.order.model.dto.OrderDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface OrderDAO {
    int registOrder(OrderDTO order);

    OptionDTO getOption(Map<String, String> searchOption);
}