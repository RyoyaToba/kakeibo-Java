package com.demo.payment.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class BankAccount {
    /** ユーザID */
    private String userId;
    /** 口座ID */
    private Integer accountId;
    /** 口座タイプ */
    private Integer type;
    /** 口座名称 */
    private String name;
    /** 残高 */
    private Integer balance;
    /** 作成者 */
    private String createdBy;
    /** 作成日 */
    private Date createdDate;
    /** 更新者 */
    private String updatedBy;
    /** 更新日 */
    private Date updatedDate;

}
