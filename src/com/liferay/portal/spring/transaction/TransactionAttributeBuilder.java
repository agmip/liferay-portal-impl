/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.spring.transaction;

import com.liferay.portal.kernel.transaction.TransactionDefinition;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.util.PropsValues;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.interceptor.NoRollbackRuleAttribute;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;

/**
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 */
public class TransactionAttributeBuilder {

	public static TransactionAttribute build(Transactional transactional) {
		if (transactional == null) {
			return null;
		}

		return _build(
			transactional.enabled(), transactional.isolation().value(),
			transactional.propagation().value(), transactional.readOnly(),
			transactional.timeout(), transactional.rollbackFor(),
			transactional.rollbackForClassName(), transactional.noRollbackFor(),
			transactional.noRollbackForClassName());
	}

	private static TransactionAttribute _build(
		boolean enabled, int isolationLevel, int propagationBehavior,
		boolean readOnly, int timeout, Class<?>[] rollbackFor,
		String[] rollbackForClassName, Class<?>[] noRollbackFor,
		String[] noRollbackForClassName) {

		if (!enabled) {
			return null;
		}

		RuleBasedTransactionAttribute ruleBasedTransactionAttribute =
			new RuleBasedTransactionAttribute();

		if (isolationLevel == TransactionDefinition.ISOLATION_COUNTER) {
			ruleBasedTransactionAttribute.setIsolationLevel(
				PropsValues.TRANSACTION_ISOLATION_COUNTER);
		}
		else if (isolationLevel == TransactionDefinition.ISOLATION_PORTAL) {
			ruleBasedTransactionAttribute.setIsolationLevel(
				PropsValues.TRANSACTION_ISOLATION_PORTAL);
		}
		else {
			ruleBasedTransactionAttribute.setIsolationLevel(isolationLevel);
		}

		ruleBasedTransactionAttribute.setPropagationBehavior(
			propagationBehavior);
		ruleBasedTransactionAttribute.setReadOnly(readOnly);
		ruleBasedTransactionAttribute.setTimeout(timeout);

		List<RollbackRuleAttribute> rollbackRuleAttributes =
			new ArrayList<RollbackRuleAttribute>();

		for (int i = 0; i < rollbackFor.length; i++) {
			RollbackRuleAttribute rollbackRuleAttribute =
				new RollbackRuleAttribute(rollbackFor[i]);

			rollbackRuleAttributes.add(rollbackRuleAttribute);
		}

		for (int i = 0; i < rollbackForClassName.length; i++) {
			RollbackRuleAttribute rollbackRuleAttribute =
				new RollbackRuleAttribute(rollbackForClassName[i]);

			rollbackRuleAttributes.add(rollbackRuleAttribute);
		}

		for (int i = 0; i < noRollbackFor.length; ++i) {
			NoRollbackRuleAttribute noRollbackRuleAttribute =
				new NoRollbackRuleAttribute(noRollbackFor[i]);

			rollbackRuleAttributes.add(noRollbackRuleAttribute);
		}

		for (int i = 0; i < noRollbackForClassName.length; ++i) {
			NoRollbackRuleAttribute noRollbackRuleAttribute =
				new NoRollbackRuleAttribute(noRollbackForClassName[i]);

			rollbackRuleAttributes.add(noRollbackRuleAttribute);
		}

		List<RollbackRuleAttribute> ruleBasedRollbackRuleAttributes =
			ruleBasedTransactionAttribute.getRollbackRules();

		ruleBasedRollbackRuleAttributes.addAll(rollbackRuleAttributes);

		return ruleBasedTransactionAttribute;
	}

}