package com.iyysoft.msdp.dp.app.controller.demo;// package com.iyysoft.msdp.dp.app.controller;
// import com.baomidou.mybatisplus.core.metadata.IPage;
// import com.baomidou.mybatisplus.core.toolkit.Wrappers;
// import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//
// import com.iyysoft.msdp.common.core.util.R;
// import com.iyysoft.msdp.common.data.mybatis.RPage;
// import com.iyysoft.msdp.common.security.service.MsdpUser;
// import com.iyysoft.msdp.common.security.util.SecurityUtils;
// import AccountLogQueryDto;
// import ChangePasswordDto;
// import OutCashDto;
// import ResetPasswordDto;
// import com.iyysoft.msdp.dp.app.entity.Account;
// import com.iyysoft.msdp.dp.app.service.AccountLogService;
// import com.iyysoft.msdp.dp.app.service.AccountService;
// import AccountLogListVo;
// import AccountVo;
// import InCashAppVo;
// import PayInfoVo;
// import io.swagger.annotations.Api;
// import io.swagger.annotations.ApiOperation;
// import io.swagger.annotations.ApiParam;
// import lombok.AllArgsConstructor;
// import org.springframework.web.bind.annotation.*;
//
// import javax.validation.Valid;
// import java.math.BigDecimal;
// import java.text.ParseException;
// import java.text.SimpleDateFormat;
// import java.time.LocalDateTime;
// import java.util.Calendar;
//
// @RequestMapping ("/api/v2/accounts")
// @RestController
// @AllArgsConstructor
// @Api (value = "AccountController", tags = "账户管理Api")
// public class AccountController {
//
//    private AccountService accountService;
//
//    private AccountLogService accountLogService;
//
//    //private CashBlotterService cashBlotterService;
//
//    /**
//     * 获取账户余额
//     * @return
//     */
//    @ApiOperation(value = "获取账户余额")
//    @GetMapping(value = "/info")
//    public R<AccountVo> info() {
//        return new R<AccountVo>(accountService.info(SecurityUtils.getUser()));
//    }
//
//    /**
//     * 账户明细列表
//     * @param rPage 分页
//     * @return
//     */
//    @ApiOperation(value = "账户账单明细列表")
//    @GetMapping(value = "/accountLog/page")
//    public R<RPage<AccountLogListVo>> list(@Valid @ApiParam("查询对象") AccountLogQueryDto accountLogQueryDto, @ApiParam("分页对象") RPage rPage) {
//        MsdpUser user = SecurityUtils.getUser();
//        Account account = accountService.getOne(Wrappers.<Account>query()
//                .lambda()
//                .eq(Account::getUserId, user.getUserId())
//                .eq(Account::getAccType, 1));
//        if(account == null){
//            account = accountService.insertNew(user);
//            return new R<>(new RPage<>());
//        }
//        //构建分页参数
//        Page<AccountLogListVo> page = new Page<AccountLogListVo>(rPage.getPageNum(), rPage.getPageSize());// 当前页，总条数 构造 page 对象
//        //调用分页查询
//
// 		String accDate = accountLogQueryDto.getAccDate();
// 		//accDate=2019-05
// 		boolean accDateMatches = accDate.matches("\\d{4}-\\d{2}");
// 		if(accDateMatches){
// 			Calendar calendar = Calendar.getInstance();
// 			try {
// 				accountLogQueryDto.setAccDateBegin(LocalDateTime.parse(accDate + "-01T00:00:00"));
// 				calendar.setTime(new SimpleDateFormat("yyyy-MM").parse(accDate));
// 				calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
// 				String lastDay = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
// 				accountLogQueryDto.setAccDateEnd(LocalDateTime.parse(lastDay + "T23:59:59"));
// 			} catch (ParseException e) {
// 				return new R<>(new RPage<>(new Page<>(page.getCurrent(),page.getSize())));
// 			}
// 		}
//
//        IPage<AccountLogListVo> accountLogListVoList = accountLogService.infoListByPage(page, account.getAccId(),accountLogQueryDto);
//
//        return new R<>(new RPage<>(accountLogListVoList));
//    }
//
//    /**
//     * 充值获取可支付频道
//     * @param payAmnt
//     * @return
//     */
//    @ApiOperation(value = "充值获取可支付频道")
//    @GetMapping(value = "/incash")
//    public R<InCashAppVo> InCashChannel(@ApiParam(value="充值金额",name="payAmnt",required=true)BigDecimal payAmnt) {
//        MsdpUser user = SecurityUtils.getUser();
//        return new R<InCashAppVo>(accountService.InCashChannel(payAmnt));
//    }
//    /**
//     * 充值获取支付凭证
//     * @return
//     */
//    @GetMapping(value = "/incash/{payChannel}")
//    @ApiOperation(value = "充值获取支付凭证")
//    public R<PayInfoVo> InCashPayInfo(@PathVariable @ApiParam(value="充值频道", name="payChannel",required=true) String payChannel,
//                                      @ApiParam(value="充值金额",name="payAmnt",required=true) BigDecimal payAmnt,
//                                      @ApiParam(value="OpenId 小程序支付时必传", name="openId",required=false) String openId,
//                                      @ApiParam(value="code 小程序支付时必传", name="code",required=false) String code) {
//        return accountService.InCashPayInfo(payChannel,payAmnt,openId,code);
//    }
//
// //    /**
// //     * 充值列表
// //     * @param qpage
// //     * @return
// //     */
// //    @ApiOperation(value = "充值列表",notes="")
// //    @GetMapping(value = "/incashlist")
// //    public R<RPage<CashBlotterListVo>> InCashList(@ApiParam("分页对象")QPage qpage) {
// //        MsdpUser user = SecurityUtils.getUser();
// //        Account account = accountService.getOne(Wrappers.<Account>query()
// //                .lambda()
// //                .eq(Account::getUserId, user.getUserId())
// //                .eq(Account::getAccType, 1));
// //        if(account == null){
// //            account = accountService.insertNew(user);
// //            return new R<RPage<CashBlotterListVo>>(new RPage());
// //        }
// //        //构建分页参数
// //        Page<CashBlotterListVo> page = new Page<CashBlotterListVo>(qpage.getPageNum(), qpage.getPageSize());// 当前页，总条数 构造 page 对象
// //        //调用分页查询
// //        IPage<CashBlotterListVo> accountLogListVoList = cashBlotterService.InCashListByPage(page, account.getAccId());
// //        //设置结果
// //
// //        //结果转换为自己公司规范的分页字段
// //        return new R<RPage<CashBlotterListVo>>(new RPage(accountLogListVoList));
// //    }
//
//    /**
//     * 校验微信付款
//     * @param payId
//     * @return
//     */
//    @ApiOperation(value = "校验微信付款",notes="")
//    @PostMapping(value = "/CheckInCashWxMiniPay/{payId}")
//    public R CheckInCashWxMiniPay(@PathVariable @ApiParam(value="支付ID",name="payId",required=true)String payId) {
//        return accountService.CheckInCashWxMiniPay(payId);
//    }
//
//    /**
//     * 校验支付宝付款
//     * @param payId
//     * @return
//     */
//    @ApiOperation(value = "校验微信付款",notes="")
//    @PostMapping(value = "/CheckInCashAliPay/{payId}")
//    public R CheckInCashAliPay(@PathVariable @ApiParam(value="支付ID",name="payId",required=true)String payId) {
//        return accountService.CheckInCashAliPay(payId);
//    }
//
//    /**
//     * 账户提现
//     * @param dto
//     * @return
//     */
//    @ApiOperation(value = "账户提现",notes="")
//    @PostMapping(value = "/outcash")
//    public R OutCash(@RequestBody OutCashDto dto) {
//        return accountService.OutCash(dto);
//    }
//
// //    /**
// //     * 提现列表
// //     * @param qpage
// //     * @return
// //     */
// //    @ApiOperation(value = "提现列表",notes="")
// //    @GetMapping(value = "/outcashlist")
// //    public R<RPage<CashBlotterListVo>> OutCashList(@ApiParam("分页对象")QPage qpage) {
// //        MsdpUser user = SecurityUtils.getUser();
// //        Account account = accountService.getOne(Wrappers.<Account>query()
// //                .lambda()
// //                .eq(Account::getUserId, user.getUserId())
// //                .eq(Account::getAccType, 1));
// //        if(account == null){
// //            account = accountService.insertNew(user);
// //            return new R<RPage<CashBlotterListVo>>();
// //        }
// //        //构建分页参数
// //        Page<CashBlotterListVo> page = new Page<CashBlotterListVo>(qpage.getPageNum(), qpage.getPageSize());// 当前页，总条数 构造 page 对象
// //        //调用分页查询
// //        IPage<CashBlotterListVo> accountLogListVoList = cashBlotterService.OutCashListByPage(page, account.getAccId());
// //        //设置结果
// //
// //        return new R<RPage<CashBlotterListVo>>(new RPage(accountLogListVoList));
// //    }
//
//
//    /**
//     * 修改支付密码
//     * @param dto
//     * @return
//     */
//    @ApiOperation(value = "修改支付密码",notes="")
//    @PostMapping(value = "/changePassword")
//    public R ChangePassword(@RequestBody ChangePasswordDto dto) {
//        return accountService.ChangePassword(dto);
//    }
//
//    /**
//     * 重置支付密码
//     * @param dto
//     * @return
//     */
//    @ApiOperation(value = "重置支付密码",notes="")
//    @PostMapping(value = "/resetPassword")
//    public R ResetPassword(@RequestBody ResetPasswordDto dto) {
//        return accountService.ResetPassword(dto);
//    }
// }
//
