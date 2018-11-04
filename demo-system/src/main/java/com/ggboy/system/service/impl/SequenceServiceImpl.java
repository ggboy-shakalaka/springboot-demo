package com.ggboy.system.service.impl;

import com.ggboy.common.utils.StringUtil;
import com.ggboy.system.mapper.SequenceMapper;
import com.ggboy.system.service.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class SequenceServiceImpl implements SequenceService {

    @Autowired
    private SequenceMapper sequenceMapper;

    @Override
    @Transactional
    public Long next(String sequenceName) {
        if (StringUtil.isEmpty(sequenceName))
            return null;
        Map<String, Object> result = sequenceMapper.lockSequenceName(sequenceName);
        if (result == null)
            return null;
        sequenceMapper.update(sequenceName);
        return (Long) result.get("value") + (Integer) result.get("inc");
    }
}
