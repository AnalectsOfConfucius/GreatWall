package com.hg.dqsj.ad.dao;

import java.util.List;
import java.util.Map;

import com.hg.dqsj.ad.entity.FEAd;
import com.hg.dqsj.ad.view.AdObjLinkDetail;
import com.hg.dqsj.ad.view.VFEAd;

/**
 * 功能：广告DAO接口
 * 
 * @author 吴晓敏
 *
 */
public interface FEAdDao {
	/**
	 * 根据查询条件查询所有详细信息
	 * 
	 * @param map
	 * @return
	 */
	public List<VFEAd> selectByCriterias(Map<String, Object> map);

	/**
	 * 根据查询条件查询所有详细信息数
	 * 
	 * @param map
	 * @return
	 */
	public Integer countByCriterias(Map<String, Object> map);

	/**
	 * 根据ID查询详细信息
	 * 
	 * @param map
	 * @return
	 */
	public FEAd selectById(Map<String, String> map);

	/**
	 * 保存
	 * 
	 * @param feAd
	 * @return
	 */
	public Integer save(FEAd feAd);

	/**
	 * 更新
	 * 
	 * @param feAd
	 * @return
	 */
	public Integer update(FEAd feAd);

	/**
	 * 根据ID查询详细信息
	 * 
	 * @param map
	 * @return
	 */
	public VFEAd selectVById(Map<String, String> map);

	/**
	 * 更新删除标识
	 * 
	 * @param feAd
	 * @return
	 */
	public Integer updateDeleteFlagById(FEAd feAd);

	/**
	 * 根据【广告类别】和【广告目标】更新删除标识
	 * 
	 * @param map
	 * @return
	 */
	public Integer updateDeleteFlagByLinkTypeIdAndLinkObjId(Map<String, String> map);

	/**
	 * 根据查询条件查询所有资讯详细信息
	 * 
	 * @param map
	 * @return
	 */
	public List<AdObjLinkDetail> selectInfosByCriterias(Map<String, Object> map);

	/**
	 * 根据查询条件查询所有资讯详细信息数
	 * 
	 * @param map
	 * @return
	 */
	public Integer countInfosByCriterias(Map<String, Object> map);

	/**
	 * 根据查询条件查询所有店铺详细信息
	 * 
	 * @param map
	 * @return
	 */
	public List<AdObjLinkDetail> selectStoreByCriterias(Map<String, Object> map);

	/**
	 * 根据查询条件查询所有店铺详细信息数
	 * 
	 * @param map
	 * @return
	 */
	public Integer countStoreByCriterias(Map<String, Object> map);

	/**
	 * 根据查询条件查询所有商品详细信息
	 * 
	 * @param map
	 * @return
	 */
	public List<AdObjLinkDetail> selectGoodsByCriterias(Map<String, Object> map);

	/**
	 * 根据查询条件查询所有商品详细信息数
	 * 
	 * @param map
	 * @return
	 */
	public Integer countGoodsByCriterias(Map<String, Object> map);

	/**
	 * 根据查询条件查询所有客房详细信息
	 * 
	 * @param map
	 * @return
	 */
	public List<AdObjLinkDetail> selectGuestRoomByCriterias(Map<String, Object> map);

	/**
	 * 根据查询条件查询所有客房详细信息数
	 * 
	 * @param map
	 * @return
	 */
	public Integer countGuestRoomByCriterias(Map<String, Object> map);
}
