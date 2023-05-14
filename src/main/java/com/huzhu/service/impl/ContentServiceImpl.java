package com.huzhu.service.impl;

import com.huzhu.entity.Content;
import com.huzhu.mapper.ContentMapper;
import com.huzhu.service.IContentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author user
 * @since 2023-05-07
 */
@Service
public class ContentServiceImpl extends ServiceImpl<ContentMapper, Content> implements IContentService {

}
