package com.miguo.matrix.repository.client;

import com.miguo.matrix.entity.client.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientVideoRepository extends JpaRepository<Video,String> {
}
