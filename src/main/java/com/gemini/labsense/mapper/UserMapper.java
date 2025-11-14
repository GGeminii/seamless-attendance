package com.gemini.labsense.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gemini.labsense.pojo.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    List<String> findUserPermsList(Long id);
}
