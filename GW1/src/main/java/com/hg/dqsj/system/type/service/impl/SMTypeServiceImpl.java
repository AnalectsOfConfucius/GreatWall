package com.hg.dqsj.system.type.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hg.core.util.NumberUtil;
import com.hg.core.util.Pager;
import com.hg.core.util.RequestUtils;
import com.hg.core.util.StringUtil;
import com.hg.dqsj.system.type.dao.SMTypeDao;
import com.hg.dqsj.system.type.entity.SMType;
import com.hg.dqsj.system.type.service.SMTypeService;
import com.hg.dqsj.system.type.view.VSMType;

/**
 * 功能：系统类别SERVICE实现类
 * 
 * @author 吴晓敏
 *
 */
@Service
public class SMTypeServiceImpl implements SMTypeService {
	@Autowired
	private SMTypeDao smTypeDao; // 系统类别DAO接口

	/**
	 * 根据查询条件查询所有类别详细信息
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Pager selectByCriterias(HttpServletRequest request) {
		Map<String, Object> map = RequestUtils.getQueryParams(request);
		int currentPage = NumberUtil.strToInt(StringUtil.trimBlank(RequestUtils.getQueryParam(request, "currentPage")));
		List<VSMType> details = smTypeDao.selectByCriterias(map);
		// 将服务点详细信息分页并返回
		if (details != null && details.size() > 0) {

			for (VSMType vsmType : details) {
				if ("1".equals(vsmType.getTypeFlag())) {
					vsmType.setTypeFlagStr("资讯");
				} else if ("2".equals(vsmType.getTypeFlag())) {
					vsmType.setTypeFlagStr("美食");
				} else if ("3".equals(vsmType.getTypeFlag())) {
					vsmType.setTypeFlagStr("特色商品");
				} else if ("4".equals(vsmType.getTypeFlag())) {
					vsmType.setTypeFlagStr("酒店客房");
				} else if ("5".equals(vsmType.getTypeFlag())) {
					vsmType.setTypeFlagStr("年卡");
				}
			}

			Pager pager = new Pager();
			pager.setCurrentPage(currentPage);
			pager.setPageSize(10);
			pager.setTotalCount(details.size());
			pager.setItems(details);

			if (pager.getCurrentPage() > pager.getTotalPage()) {
				return null;
			}
			pager.setItems((List<SMType>) pager.doPagination());
			return pager;
		}
		return null;
	}

	/**
	 * 根据ID查询详细信息
	 * 
	 * @param id
	 * @return
	 */
	public SMType selectById(String id) {
		return smTypeDao.selectById(id);
	}

	/**
	 * 保存
	 * 
	 * @param smType
	 * @return
	 */
	public Integer save(SMType smType) {
		return smTypeDao.save(smType);
	}

	/**
	 * 更新
	 * 
	 * @param smType
	 * @return
	 */
	public Integer update(SMType smType) {
		return smTypeDao.update(smType);
	}

	/**
	 * 类别代码是否存在
	 * 
	 * @param id
	 * @param typeCode
	 * @return
	 */
	public boolean isTypeCodeExsit(String id, String typeCode) {
		SMType smType = smTypeDao.selectByTypeCode(typeCode);
		if (smType == null) {
			return false;
		}
		if (smType.getId().equals(id)) {
			return false;
		}
		return true;
	}

	/**
	 * 更新删除标识
	 * 
	 * @param smType
	 * @return
	 */
	public Integer updateDeleteFlagById(SMType smType) {
		return smTypeDao.update(smType);
	}

	/**
	 * 查询所有类别标识不为空的信息
	 * 
	 * @return
	 */
	public List<SMType> selectByTypeFlagNotNull() {
		return smTypeDao.selectByTypeFlagNotNull();
	}

	/**
	 * 根据类别代码获取类别信息
	 * 
	 * @param id
	 * @return
	 */
	public SMType selectByTypeCode(String typeCode) {
		return smTypeDao.selectByTypeCode(typeCode);
	}
}
