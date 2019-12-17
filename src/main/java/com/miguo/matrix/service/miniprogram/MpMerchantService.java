package com.miguo.matrix.service.miniprogram;

import com.miguo.matrix.entity.miniprogram.Merchant;
import com.miguo.matrix.repository.miniprogram.MpMerchantRepository;
import com.miguo.matrix.utils.SnowflakeIdWorker;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

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

    // 添加赞助商对象
    public void add(Merchant merchant){
        merchant.setCreateAt(new Date());
        merchant.setUpdateAt(new Date());
        merchant.setId(snowflakeIdWorker.nextId());
        merchant.setIsDel(false);
        merchant.setCreateBy("test"); // 写死，到时候用session代替
        merchant.setUpdateBy("test"); // 写死，到时候用session代替
        merchantRepository.save(merchant);
    }

    // 批量软删除赞助商对象
    public void delete(String ids){
        String deleteIds[] = ids.split(",");
        for(int i =0;i<deleteIds.length;i++){
            merchantRepository.deleteById(deleteIds[i],new Date(),"test");  // 写死，用session代替
        }
    }

    // 查找所有已被删除的赞助商对象
    public Page<Merchant> findAllDeleted(int page,int size){
        page--;
        Pageable pageable=PageRequest.of(page,size);
        Page<Merchant> pageTemp = merchantRepository.findAllDeletedMerchant(pageable);
        return pageTemp;
    }

    // 查找所有未被删除的赞助商对象
    public Page<Merchant> findAllExist(int page,int size){
        page--;
        Pageable pageable=PageRequest.of(page,size);
        Page<Merchant> pageTemp = merchantRepository.findAllExistMerchant(pageable);
        return pageTemp;
    }

    // 通过id查找该赞助商对象
    public Merchant findOneById(String id)
    {
        return merchantRepository.findMerchantById(id);
    }

    // 更新赞助商对象
    public void update(Merchant merchant){
        Merchant merchantTemp=this.findOneById(merchant.getId());
        BeanUtils.copyProperties(merchant, merchantTemp);
        merchantTemp.setUpdateBy("test"); // 写死，session代替
        merchantTemp.setUpdateAt(new Date());
        merchantRepository.saveAndFlush(merchantTemp);
    }
    
}
