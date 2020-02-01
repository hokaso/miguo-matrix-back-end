package com.miguo.matrix.service.miniprogram;

import com.miguo.matrix.entity.miniprogram.Merchant;
import com.miguo.matrix.repository.miniprogram.MpMerchantRepository;
import com.miguo.matrix.utils.SnowflakeIdWorker;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * 功能描述：
 *
 * @author Hocassian
 * @date 2019-12-17 10:02
 */
@Service
public class MpMerchantService {

    @Autowired
    private MpMerchantRepository merchantRepository;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Autowired
    private HttpSession session;

    public void add(Merchant merchant){
        merchant.setCreateAt(new Date());
        merchant.setUpdateAt(new Date());
        merchant.setId(snowflakeIdWorker.nextId());
        merchant.setIsDel(false);
        merchant.setCreateBy((String) session.getAttribute("user"));
        merchant.setUpdateBy((String) session.getAttribute("user"));
        merchantRepository.save(merchant);
    }

    public void delete(List<Merchant> list){
        merchantRepository.deleteInBatch(list);
    }

    public Page<Merchant> findMerchantByKeywords(String keywords, int page, int size, Sort.Direction direction){
        page--;
        Pageable pageable = PageRequest.of(page, size, direction, "update_at");
        return merchantRepository.findMerchantByKeywords(keywords,pageable);
    }

    public Merchant findOneById(String id)
    {
        return merchantRepository.findMerchantById(id);
    }

    public void update(Merchant merchant){
        Merchant merchantTemp=this.findOneById(merchant.getId());
        BeanUtils.copyProperties(merchant, merchantTemp);
        merchantTemp.setUpdateBy((String) session.getAttribute("user"));
        merchantTemp.setUpdateAt(new Date());
        merchantRepository.saveAndFlush(merchantTemp);
    }
    
}
