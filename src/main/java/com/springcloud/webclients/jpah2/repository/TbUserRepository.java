package com.springcloud.webclients.jpah2.repository;

import com.springcloud.webclients.jpah2.entity.TbUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TbUserRepository extends JpaRepository<TbUser, Long> {


}
