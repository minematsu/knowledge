package org.support.project.knowledge.entity;

import org.support.project.knowledge.entity.gen.GenStockKnowledgesEntity;

import java.util.List;
import java.util.Map;

import org.support.project.common.bean.ValidateError;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

import java.sql.Timestamp;


/**
 * ストックしたナレッジ
 */
@DI(instance=Instance.Prototype)
public class StockKnowledgesEntity extends GenStockKnowledgesEntity {

	/** SerialVersion */
	private static final long serialVersionUID = 1L;

	/**
	 * インスタンス取得
	 * AOPに対応
	 * @return インスタンス
	 */
	public static StockKnowledgesEntity get() {
		return Container.getComp(StockKnowledgesEntity.class);
	}

	/**
	 * コンストラクタ
	 */
	public StockKnowledgesEntity() {
		super();
	}

	/**
	 * コンストラクタ
	 * @param knowledgeId ナレッジID
	 * @param stockId STOCK ID
	 */

	public StockKnowledgesEntity(Long knowledgeId, Long stockId) {
		super( knowledgeId,  stockId);
	}

}
