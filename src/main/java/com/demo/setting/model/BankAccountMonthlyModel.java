package com.demo.setting.model;

import lombok.Data;

import java.util.Date;

@Data
public class BankAccountMonthlyModel {

    /** 基準年月 */
    private String targetYm;
    /** ユーザID */
    private String userId;
    /** 銀行識別番号 */
    private Integer accountId;
    /** タイプ */
    private Integer type;
    /** 口座名 */
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
