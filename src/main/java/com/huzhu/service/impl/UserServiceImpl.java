package com.huzhu.service.impl;

import com.huzhu.entity.User;
import com.huzhu.mapper.UserMapper;
import com.huzhu.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author user
 * @since 2023-04-05
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
