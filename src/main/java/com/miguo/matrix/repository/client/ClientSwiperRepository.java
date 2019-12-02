package com.miguo.matrix.repository.client;

import com.miguo.matrix.entity.client.Swiper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientSwiperRepository extends JpaRepository<Swiper,String> {
}
