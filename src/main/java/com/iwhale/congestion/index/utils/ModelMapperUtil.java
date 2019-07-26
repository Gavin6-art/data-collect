package com.iwhale.congestion.index.utils;


import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author chen.jinshu
 *         2018/06/27
 */
public class ModelMapperUtil {
    private static ModelMapper modelMapper = new ModelMapper();

    public ModelMapperUtil() {
    }

    public static <D> D strictMap(Object source, Class<D> destinationType) {
        return modelMapper.map(source, destinationType);
    }

    public static <D> List<D> strictMapList(Object source, Class<D> componentType) {
        List<D> list = new ArrayList();
        List<Object> objectList = (List) source;
        Iterator var4 = objectList.iterator();

        while (var4.hasNext()) {
            Object obj = var4.next();
            list.add(modelMapper.map(obj, componentType));
        }

        return list;
    }

    public static <D> PageResult<D> strictPageResult(Object source, Class<D> componentType) {
        PageResult<Object> objPage = (PageResult) source;
        PageResult<D> pageResult = new PageResult(objPage.getPageNumber(), objPage.getPageSize());
        pageResult.setCount(objPage.getCount());
        pageResult.setOrderBy(objPage.getOrderBy());
        List<D> list = strictMapList(objPage.getList(), componentType);
        pageResult.setList(list);
        return pageResult;
    }

    static {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }
}
