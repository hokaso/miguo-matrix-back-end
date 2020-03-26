package com.miguo.matrix.service.staff;

import com.miguo.matrix.vo.CountVo;
import com.miguo.matrix.repository.staff.QualityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 功能描述：
 *
 * @author Hocassian
 * @date 2020-03-25 14:57
 */
@Service
public class CountService {

    @Autowired
    private QualityRepository qualityRepository;

    public Map<String, Integer> findAllCount(){
        return qualityRepository.countAll();
//        return null;
    }
}
