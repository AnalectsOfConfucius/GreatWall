package com.hg.dqsj.system.menu.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hg.core.filters.SessionValidFilter;
import com.hg.core.session.SessionProvider;
import com.hg.core.util.DateUtil;
import com.hg.core.util.StringUtil;
import com.hg.dqsj.system.auth.entity.Auth;
import com.hg.dqsj.system.auth.entity.Operate;
import com.hg.dqsj.system.menu.dao.MenuDao;
import com.hg.dqsj.system.menu.entity.Menu;
import com.hg.dqsj.system.menu.entity.MenuInfo;
import com.hg.dqsj.system.menu.entity.MenuOperate;
import com.hg.dqsj.system.menu.service.MenuService;

/**
 * 功能：系统菜单管理Service实现类
 * 
 * @author Administrator
 *
 */

@Service
public class MenuServiceImpl implements MenuService {
	@Autowired
	private SessionProvider session; // Session提供者
	@Autowired
	MenuDao menuDao;// 权限菜单DAO接口

	// 查询顶级菜单和所有子菜单
	@Override
	public Map<String, Object> selectAllLevelMenu(String userId, String adminFlag) {
		List<Menu> allMenu = this.menuDao.selectAllMenu();
		List<Menu> topMenuList = new ArrayList<>();
		Map<String, String> mTopMenu = new HashMap<>();
		Map<String, List<Menu>> mChildMenu = new HashMap<>();
		Map<String, String> mParentMenu = new HashMap<>();
		Map<String, Menu> mAllMenu = new HashMap<>();
		List<Menu> tempMenuList = null;
		String menuId = "";
		String parentId = "";
		for (Menu menu : allMenu) {
			menuId = menu.getId();
			parentId = StringUtil.trimBlank(menu.getParentId());
			mParentMenu.put(menuId, parentId);
			mAllMenu.put(menuId, menu);
		}

		Map<String, Operate> menuOperations = new HashMap<>();
		Operate op = null;
		Menu tempMenu = null;
		Map<String, String> mRepeatMenu = new HashMap<>();
		String opCode = "";
		if ("1".equals(adminFlag)) {
			op = new Operate();
			op.setAdd(true);
			op.setUpdate(true);
			op.setDelete(true);
			op.setSearch(true);
			op.setView(true);
			op.setImp(true);
			op.setExp(true);
			op.setPrint(true);
			op.setAudit(true);
			for (Menu menu : allMenu) {
				menuId = menu.getId();
				menuOperations.put(menuId, op);

				// 查找父菜单
				while (mParentMenu.containsKey(menuId)) {
					if (mRepeatMenu.containsKey(menuId)) {
						break;
					} else {
						mRepeatMenu.put(menuId, "");
					}

					tempMenu = mAllMenu.get(menuId);
					parentId = mParentMenu.get(menuId);
					if (StringUtil.isNullorEmpty(parentId)) {
						if (!mTopMenu.containsKey(menuId)) {
							topMenuList.add(tempMenu);
							mTopMenu.put(menuId, "");
						}
						break;
					} else {
						if (mChildMenu.containsKey(parentId)) {
							tempMenuList = mChildMenu.get(parentId);
						} else {
							tempMenuList = new ArrayList<>();
						}
						tempMenuList.add(tempMenu);
						mChildMenu.put(parentId, tempMenuList);
					}
					menuId = parentId;
				}
			}
		} else if ("0".equals(adminFlag)) {
			List<MenuOperate> lsMenuOp = this.menuDao.selectUserMenuOpration(userId);
			for (MenuOperate menuOp : lsMenuOp) {
				menuId = menuOp.getMenuId();
				opCode = menuOp.getOpCode();

				// 设置用户权限
				if (menuOperations.containsKey(menuId)) {
					op = menuOperations.get(menuId);
				} else {
					op = new Operate();
				}
				switch (opCode) {
				case "SEARCH": // 查询
					op.setSearch(true);
					break;
				case "VIEW": // 查看
					op.setView(true);
					break;
				case "ADD": // 新增
					op.setAdd(true);
					break;
				case "MODIFY": // 修改
					op.setUpdate(true);
					break;
				case "DELETE": // 删除
					op.setDelete(true);
					break;
				case "IMPORT": // 导入
					op.setImp(true);
					break;
				case "EXPORT": // 导出
					op.setExp(true);
					break;
				case "PRINT": // 打印
					op.setPrint(true);
					break;
				case "AUDIT": // 审核
					op.setAudit(true);
					break;
				default:
					break;
				}
				menuOperations.put(menuId, op);

				// 查找父菜单
				while (mParentMenu.containsKey(menuId)) {

					if (mRepeatMenu.containsKey(menuId)) {
						break;
					} else {
						mRepeatMenu.put(menuId, "");
					}

					tempMenu = mAllMenu.get(menuId);
					parentId = mParentMenu.get(menuId);
					if (StringUtil.isNullorEmpty(parentId)) {
						if (!mTopMenu.containsKey(menuId)) {
							topMenuList.add(tempMenu);
							mTopMenu.put(menuId, "");
						}
					} else {
						if (mChildMenu.containsKey(parentId)) {
							tempMenuList = mChildMenu.get(parentId);
						} else {
							tempMenuList = new ArrayList<>();
						}
						tempMenuList.add(tempMenu);
						mChildMenu.put(parentId, tempMenuList);
					}
					menuId = parentId;
				}
			}
		}

		if (topMenuList.size() > 1) {
			Collections.sort(topMenuList, new Comparator<Menu>() {
				public int compare(Menu menu1, Menu menu2) {
					return menu1.getMenuOrder().compareTo(menu2.getMenuOrder());
				}
			});
		}

		for (List<Menu> childMenuList : mChildMenu.values()) {
			if (childMenuList.size() > 1) {
				Collections.sort(childMenuList, new Comparator<Menu>() {
					public int compare(Menu menu1, Menu menu2) {
						return menu1.getMenuOrder().compareTo(menu2.getMenuOrder());
					}
				});
			}
		}

		Map<String, Object> mMenu = new HashMap<>();
		mMenu.put("topMenuList", topMenuList);
		mMenu.put("mChildMenu", mChildMenu);
		mMenu.put("menuOp", menuOperations);
		return mMenu;
	}

	@Override
	public Map<String, Operate> selectMenuOp(String userId, String adminFlag) {

		Map<String, Operate> menuOperations = null;
		Operate op = null;

		if ("1".equals(adminFlag)) {
			menuOperations = new HashMap<>();
			List<Menu> allMenu = this.menuDao.selectAllMenu();
			for (Menu menu : allMenu) {
				op = new Operate();
				op.setAdd(true);
				op.setUpdate(true);
				op.setDelete(true);
				op.setSearch(true);
				op.setView(true);
				op.setImp(true);
				op.setExp(true);
				op.setPrint(true);
				op.setAudit(true);
				menuOperations.put(menu.getId(), op);
			}

		} else if ("0".equals(adminFlag)) {

			String menuId = "";
			String opCode = "";

			menuOperations = new HashMap<>();
			List<MenuOperate> lsMenuOp = this.menuDao.selectUserMenuOpration(userId);
			for (MenuOperate menuOp : lsMenuOp) {
				menuId = menuOp.getMenuId();
				opCode = menuOp.getOpCode();

				// 设置用户权限
				if (menuOperations.containsKey(menuId)) {
					op = menuOperations.get(menuId);
				} else {
					op = new Operate();
				}
				switch (opCode) {
				case "SEARCH": // 查询
					op.setSearch(true);
					break;
				case "VIEW": // 查看
					op.setView(true);
					break;
				case "ADD": // 新增
					op.setAdd(true);
					break;
				case "UPDATE": // 修改
					op.setUpdate(true);
					break;
				case "DELETE": // 删除
					op.setDelete(true);
					break;
				case "IMPORT": // 导入
					op.setImp(true);
					break;
				case "EXPORT": // 导出
					op.setExp(true);
					break;
				case "PRINT": // 打印
					op.setPrint(true);
					break;
				case "AUDIT": // 审核
					op.setAudit(true);
					break;
				default:
					break;
				}
				menuOperations.put(menuId, op);
			}
		}
		return menuOperations;
	}

	@Override
	public List<Menu> selectAllMenu() {
		return this.menuDao.selectAllMenu();
	}

	@Override
	public void insertMenu(Menu menu) {
		this.menuDao.insertMenu(menu);
	}

	@Override
	public void updateMenu(Menu menu) {
		this.menuDao.updateMenu(menu);
	}

	@Override
	public void deleteMenu(String menuId, String updateUserId) {
		Map<String, String> m = new HashMap<>();
		m.put("id", menuId);
		m.put("updateDate", DateUtil.getFullTime());
		m.put("updateUserId", updateUserId);
		this.menuDao.deleteMenu(m);
	}

	@Override
	public List<Menu> selectParentMenu(Map<String, String> param) {
		return menuDao.selectParentMenu(param);
	}

	@Override
	public List<MenuInfo> selectChildMenu(Map<String, String> param) {
		return menuDao.selectChildMenu(param);
	}

	@Override
	public boolean getOper(String menuUrl, String oper, HttpServletRequest request, SessionProvider session) {

		String menuId = menuDao.selectIdByMenuUrl(menuUrl);
		if (null != menuId) {
			Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.AUTH_KEY);
			// Map<String, Operate> mop = auth.getMenuOperations();
			Map<String, Operate> mop = selectMenuOp(auth.getUserId(), auth.getUser().getAdminFlag());
			Operate op = mop.get(menuId);
			if ("SEARCH".equals(oper)) {// 查询
				return op.isSearch();
			} else if ("VIEW".equals(oper)) {// 查看
				return op.isView();
			} else if ("ADD".equals(oper)) {// 新增
				return op.isAdd();
			} else if ("UPDATE".equals(oper)) {// 修改
				return op.isUpdate();
			} else if ("DELETE".equals(oper)) {// 删除
				return op.isDelete();
			} else if ("IMPORT".equals(oper)) {// 导入
				return op.isImp();
			} else if ("EXPORT".equals(oper)) {// 导出
				return op.isExp();
			} else if ("PRINT".equals(oper)) {// 打印
				return op.isPrint();
			} else if ("AUDIT".equals(oper)) {// 审核
				return op.isAudit();
			}
		}

		return true;
	}

	/**
	 * 查询所有级别的权限菜单列表
	 * 
	 * @param menuUrl
	 * @return
	 */
	public String selectIdByMenuUrl(String menuUrl) {
		return menuDao.selectIdByMenuUrl(menuUrl);
	}

	/**
	 * 判断是否拥有操作权限
	 * 
	 * @param request
	 * @param operator
	 * @return
	 */
	public boolean isHasOperator(HttpServletRequest request, String operator) {
		Auth auth = (Auth) session.getAttribute(request, SessionValidFilter.AUTH_KEY);
		if (auth == null || auth.getMenuOperations() == null) {
			return false;
		}

		if ("2".equals(auth.getUser().getAdminFlag())) {
			return true;
		}

		String menuUrl = null; // 菜单URL
		String hostUrl = request.getHeader("Host"); // 请求地址URL
		String refererUrl = request.getHeader("Referer"); // 请求完整地址URL
		Map<String, Operate> mop = selectMenuOp(auth.getUserId(), auth.getUser().getAdminFlag());
		Operate operate = null; // 操作权限

		/**
		 * 资讯信息
		 */
		// 十大世界管理
		if (refererUrl.contains(hostUrl + "/tenWorlds?")) {
			menuUrl = "tenWorlds";
		}
		// 园区概括
		else if (refererUrl.contains(hostUrl + "/parkSummary?")) {
			menuUrl = "parkSummary";
		}
		// 优惠活动
		else if (refererUrl.contains(hostUrl + "/promotions?")) {
			menuUrl = "promotions";
		}
		// 设备时间
		else if (refererUrl.contains(hostUrl + "/bussinessHours/equipment?")) {
			menuUrl = "bussinessHours/equipment";
		}
		// 交通指引-公交方式
		else if (refererUrl.contains(hostUrl + "/trafficDirection/transportation?")) {
			menuUrl = "trafficDirection/transportation";
		}
		// 游玩攻略
		else if (refererUrl.contains(hostUrl + "/playStrategy?")) {
			menuUrl = "playStrategy";
		}
		// 游客须知
		else if (refererUrl.contains(hostUrl + "/visitor?")) {
			menuUrl = "visitor";
		}
		// 票务信息
		else if (refererUrl.contains(hostUrl + "/ticketInfo?")) {
			menuUrl = "ticketInfo";
		}
		// 关于我们
		else if (refererUrl.contains(hostUrl + "/aboutUs?")) {
			menuUrl = "aboutUs";
		}
		// 交通指引-联系方式
		else if (refererUrl.contains(hostUrl + "/trafficDirection/contactWay?")) {
			menuUrl = "trafficDirection/contactWay";
		}
		// 开园时间
		else if (refererUrl.contains(hostUrl + "/bussinessHours/openGarden?")) {
			menuUrl = "bussinessHours/openGarden";
		}
		// 表演时间
		else if (refererUrl.contains(hostUrl + "/bussinessHours/performance?")) {
			menuUrl = "bussinessHours/performance";
		}
		/**
		 * 广告信息
		 */
		// 广告管理
		else if (refererUrl.contains(hostUrl + "/ad?")) {
			menuUrl = "ad";
		}
		// 酒店广告
		else if (refererUrl.contains(hostUrl + "/store/hotel/ad?")) {
			menuUrl = "store/hotel/ad";
		}
		// 餐饮店广告
		else if (refererUrl.contains(hostUrl + "/store/groceryStore/ad?")) {
			menuUrl = "store/groceryStore/ad";
		}
		// 特色商品广告
		else if (refererUrl.contains(hostUrl + "/specialGoods/ad?")) {
			menuUrl = "specialGoods/ad";
		}
		// 十大世界广告
		else if (refererUrl.contains(hostUrl + "/tenWorlds/ad?")) {
			menuUrl = "tenWorlds/ad";
		}
		// 游玩攻略广告
		else if (refererUrl.contains(hostUrl + "/playStrategy/ad?")) {
			menuUrl = "playStrategy/ad";
		}
		// 年卡广告
		else if (refererUrl.contains(hostUrl + "/annualCard/ad?")) {
			menuUrl = "annualCard/ad";
		}
		/**
		 * 留言管理
		 */
		else if (refererUrl.contains(hostUrl + "/message/enter?")) {
			menuUrl = "/message/enter";
		}
		/**
		 * 订单管理
		 */
		else if (refererUrl.contains(hostUrl + "/order?")) {
			menuUrl = "/order";
		}
		/**
		 * 酒店预订
		 */
		else if (refererUrl.contains(hostUrl + "/hotelReservation?")) {
			menuUrl = "/hotelReservation";
		}
		/**
		 * 退款管理
		 */
		else if (refererUrl.contains(hostUrl + "/refund?")) {
			menuUrl = "/refund";
		}
		/**
		 * 评价管理
		 */
		else if (refererUrl.contains(hostUrl + "/eval?")) {
			menuUrl = "/eval";
		}
		/**
		 * 用户积分管理
		 */
		else if (refererUrl.contains(hostUrl + "/credit/userCredit?")) {
			menuUrl = "/credit/userCredit";
		}
		/**
		 * 消费送积分设置
		 */
		else if (refererUrl.contains(hostUrl + "/credit/?")) {
			menuUrl = "credit/";
		}
		/**
		 * 积分抵金额设置
		 */
		else if (refererUrl.contains(hostUrl + "/credit/creditMoney?")) {
			menuUrl = "/credit/creditMoney";
		} else {
			return false;
		}

		operate = mop.get(this.selectIdByMenuUrl(menuUrl));

		if (operate == null) {
			return false;
		}

		if ("search".equals(operator)) {
			return operate.isSearch();
		} else if ("search".equals(operator)) {
			return operate.isSearch();
		} else if ("view".equals(operator)) {
			return operate.isView();
		} else if ("add".equals(operator)) {
			return operate.isAdd();
		} else if ("update".equals(operator)) {
			return operate.isUpdate();
		} else if ("delete".equals(operator)) {
			return operate.isDelete();
		} else if ("print".equals(operator)) {
			return operate.isPrint();
		} else if ("imp".equals(operator)) {
			return operate.isImp();
		} else if ("exp".equals(operator)) {
			return operate.isExp();
		} else if ("audit".equals(operator)) {
			return operate.isAudit();
		} else {
			return false;
		}
	}
}
