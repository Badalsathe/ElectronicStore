package com.lcwd.electronic.store.helper;

import com.lcwd.electronic.store.dtos.PageableResponce;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class Helper {

    public static <U, V> PageableResponce<V> getPageableResponce(Page<U> page, Class<V> type) {

        List<U> entityList = page.getContent();

        List<V> dtoList = entityList.stream()
                .map(entityItem -> new ModelMapper().map(entityItem, type))
                .collect(Collectors.toList());

        PageableResponce<V> response = new PageableResponce<>();
        response.setContent(dtoList);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLastPage(page.isLast());

        return response;
    }
}
