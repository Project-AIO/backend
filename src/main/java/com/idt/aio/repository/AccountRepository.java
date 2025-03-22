package com.idt.aio.repository;

import com.idt.aio.entity.Account;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    List<Account> findAll();

    @Modifying
    @Query(value = "insert into tb_account (account_id, admin_id, name, role) values (:accountId, :adminId, :name, :role)", nativeQuery = true)
    void saveAccount(@Param("accountId") final String accountId, @Param("adminId") final String adminId,
                     @Param("name") final String name, @Param("role") final String role);

    @Modifying
    @Query(value = "select a.account_id, a.admin_id, a.role, a.name, u.license_key from tb_account a " +
            "left outer join tb_admin u on a.admin_id = u.admin_id", nativeQuery = true)
    List<Account> findByAccounts();

    @Modifying
    @Query(value = "delete from tb_account s where s.accountId = :accountId", nativeQuery = true)
    void deleteByAccountId(String accountId);

}
