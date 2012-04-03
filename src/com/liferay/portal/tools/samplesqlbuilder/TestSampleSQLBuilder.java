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

package com.liferay.portal.tools.samplesqlbuilder;

import com.liferay.portal.tools.ArgumentsUtil;
import com.liferay.portal.tools.DBLoader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import java.util.Map;

/**
 * @author Tina Tian
 * @author Shuyang Zhou
 */
public class TestSampleSQLBuilder {

	public static void main(String[] args) throws Exception {
		Map<String, String> arguments = ArgumentsUtil.parseArguments(args);

		String sqlDir = arguments.get("sql.dir");
		String outputDir = arguments.get("sample.sql.output.dir");

		SampleSQLBuilder.main(args);

		new TestSampleSQLBuilder(sqlDir, outputDir);
	}

	public TestSampleSQLBuilder(String sqlDir, String outputDir)
		throws Exception {

		_sqlDir = sqlDir;
		_outputDir = outputDir;

		_loadHypersonic();
	}

	private void _loadHypersonic() throws Exception {
		Class.forName("org.hsqldb.jdbcDriver");

		Connection con = DriverManager.getConnection(
			"jdbc:hsqldb:mem:testSampleSQLBuilderDB;shutdown=true", "sa", "");

		DBLoader.loadHypersonic(
			con, _sqlDir + "/portal-minimal/portal-minimal-hypersonic.sql");
		DBLoader.loadHypersonic(
			con, _sqlDir + "/indexes/indexes-hypersonic.sql");
		DBLoader.loadHypersonic(con, _outputDir + "/sample-hypersonic.sql");

		Statement statement = con.createStatement();

		statement.execute("SHUTDOWN COMPACT");

		statement.close();

		con.close();
	}

	private String _outputDir;
	private String _sqlDir;

}