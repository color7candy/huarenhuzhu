package com.huzhu.service.impl;

import com.huzhu.entity.Message;
import com.huzhu.mapper.MessageMapper;
import com.huzhu.service.IMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author user
 * @since 2023-04-09
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {

}
