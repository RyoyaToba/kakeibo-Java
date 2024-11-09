package com.demo.setting.controller;

import com.demo.common.utils.CommonUtils;
import com.demo.payment.entity.BankAccount;
import com.demo.payment.model.BankAccountUI;
import com.demo.payment.service.PaymentService;
import com.demo.setting.form.AccountForm;
import com.demo.setting.form.BalanceMonthForm;
import com.demo.setting.model.BankAccountMonthlyModel;
import com.demo.setting.service.BankAccountMonthlyService;
import com.demo.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.fasterxml.jackson.databind.type.LogicalType.DateTime;

@Controller
@RequestMapping("/balance-monthly")
public class MonthlyBalanceController {

    @Autowired
    private HttpSession session;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private BankAccountMonthlyService bankAccountMonthlyService;

    @ModelAttribute
    private AccountForm setAccountForm() {
        return new AccountForm();
    }

    @ModelAttribute
    public BalanceMonthForm setBalanceForm() {
        return new BalanceMonthForm();
    }

    /**
     * 月次時点の口座残高を登録するメソッド
     * @param model
     * @return
     */
    @RequestMapping("/register-balance-monthly")
    public String registerBalanceMonthly(BalanceMonthForm form, Model model) {

        // TODO yyyy/mm → yyyymm
        String targetYm = form.getTargetYm().replace("/", "");
        Integer accountId = Integer.parseInt(form.getAccountId());
        Integer balance = Integer.parseInt(form.getBalanceMonthly());

        User user = (User) session.getAttribute("user");

        BankAccountMonthlyModel bankAccountMonthlyModel =
                bankAccountMonthlyService.selectByTargetYm(targetYm, user.getUserId(), accountId);

        // 既に登録されている情報であれば更新、新規であれば追加
        if (bankAccountMonthlyModel != null) {
            bankAccountMonthlyService.updateBankAccountMonthly(targetYm, user.getUserId(), accountId, balance);
        } else {
            BankAccount bkAccount = paymentService.loadByUserIdAndAccountId(user.getUserId(), accountId);
            BankAccountMonthlyModel bkModel = new BankAccountMonthlyModel();
            bkModel.setTargetYm(targetYm);
            bkModel.setUserId(bkAccount.getUserId());
            bkModel.setName(bkAccount.getName());
            bkModel.setAccountId(bkAccount.getAccountId());
            bkModel.setBalance(balance);
            bkModel.setType(bkAccount.getType());
            bkModel.setCreatedBy(user.getUserId());
            bkModel.setCreatedDate(new Date());
            bkModel.setUpdatedBy(user.getUserId());
            bkModel.setUpdatedDate(new Date());

            bankAccountMonthlyService.insert(bkModel);
        }

        // 登録済み口座を全件取得
        List<BankAccount> accounts = paymentService.loadByUserId(user.getUserId());
        // UIベースに変換
        List<BankAccountUI> accountUIs = paymentService.convertAccountToAccountUI(accounts);

        model.addAttribute("accounts", accountUIs);
        // 年月選択用プルダウン
        model.addAttribute("months", CommonUtils.retrieveMonths());

        BankAccountMonthlyModel monthlyModel = new BankAccountMonthlyModel();

        return "setting";
    }

    @ResponseBody
    @RequestMapping("/bankInfos")
    public Map<String, Integer> retrieveItemPrice(@RequestBody Map<String, String> request) {
        User user = (User) session.getAttribute("user");

        System.out.println(request.get("selectedText"));

        Integer selectedAccountId = Integer.parseInt(request.get("selectedText"));

        return bankAccountMonthlyService.selectByTargetBanks(user.getUserId(), selectedAccountId);
    }


}
