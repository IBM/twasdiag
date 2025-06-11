/*
 * Copyright 2025 IBM Corp. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy
 * of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.example;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

//import javax.enterprise.concurrent.ManagedExecutorService;
import javax.naming.InitialContext;
import javax.naming.NamingException;
//import javax.rmi.PortableRemoteObject;
import javax.sql.DataSource;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

public class DatabaseTest {

	//ManagedExecutorService managedExecutorService;

	private DataSource ds = null;
	boolean accumulateResults = true;
	boolean transactional = true;
	long sleepTime;

	public DatabaseTest(String jndiName, boolean accumulateResults, long sleepTime, boolean useGlobalTran) throws NamingException {
		InitialContext ic = new InitialContext();
		//managedExecutorService = 
		//		(ManagedExecutorService) ic.lookup("java:comp/DefaultManagedExecutorService");

		// Uncomment and also update web.xml resource-ref
		//ds = (DataSource) ic.lookup("java:comp/env/jdbc/db");

		this.accumulateResults = accumulateResults;
		this.sleepTime = sleepTime;
		this.transactional = useGlobalTran;
	}

	public Connection openConnection() throws SQLException {
		return ds.getConnection();
	}

	public int executeUpdate(String updateSql) throws SQLException {
		Integer rowCount = null;
		if (transactional) {
			UserTransaction userTransaction = null;
			try {
				userTransaction = lookupTran();
				userTransaction.begin();
				rowCount = executeSingleUpdate(updateSql);
				userTransaction.commit();
			} catch (Exception e) {
				try {
					if(userTransaction != null) {
						userTransaction.rollback();
					}
				} catch (SystemException e1) {
					System.out.println("Failed to rollback transaction: "+e1.getMessage());
				}
				throw new RuntimeException(e);
			}
		} else {
			try {
				rowCount = executeSingleUpdate(updateSql);
			} catch (SQLException sqle) {
				throw new RuntimeException(sqle);
			}
		}
		return rowCount;
	}

	private int executeSingleUpdate(String updateSql) throws SQLException {
		int result = 0;
		Connection conn = null;
		try {
			conn = openConnection();

			Statement stmt = null;

			try {
				stmt = conn.createStatement();

				result = stmt.executeUpdate(updateSql);

				// Sleep a bit to cause connection pooling issues
				if (sleepTime > 0) {
					try {
						Thread.sleep(sleepTime);
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
				}
			} finally {
				if (stmt != null) {
					try {
						stmt.close();
					} catch (Throwable t) {
						t.printStackTrace();
					}
				}
			}

		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
		}

		return result;
	}

	public int executeQueryRowCount(String query, int threads) throws SQLException, InterruptedException, ExecutionException {

		List<Future<Integer>> queryResults = null;

		int result = 0;

		List<Callable<Integer>> taskList = new ArrayList<Callable<Integer>>();
		for (int i = 0; i < threads; i++) {
			taskList.add(new QueryTask(query, i));
		}

		//queryResults = managedExecutorService.invokeAll((Collection<? extends Callable<Integer>>) taskList);

		//for (Future<Integer> future : queryResults) {
		//	result += future.get();
		//}

		throw new RuntimeException("Not implemented");

		//return result;
	}

	private Integer executeSingleQuery(String query, List<Object> accumulation) throws SQLException {

		int result = 0;

		Connection conn = null;

		try {
			conn = openConnection();

			Statement stmt = null;

			try {
				stmt = conn.createStatement();

				ResultSet rs = null;
				try {
					rs = stmt.executeQuery(query);

					if (query.toUpperCase().startsWith("SELECT COUNT")) {
						rs.next();
						result = rs.getInt(1); 
					} else {
						while (rs.next()) {
							result++;
							if (accumulation != null) {
								int colcount = rs.getMetaData().getColumnCount();
								for (int i = 1; i <= colcount; i++) {
									Object col = rs.getObject(i);
									accumulation.add(col);
								}
							}
						}
					}
					
					// Sleep a bit to cause connection pooling issues
					if (sleepTime > 0) {
						try {
							Thread.sleep(sleepTime);
						} catch (InterruptedException e) {
							throw new RuntimeException(e);
						}
					}

				} finally {
					if (rs != null) {
						try {
							rs.close();
						} catch (Throwable t) {
							t.printStackTrace();
						}
					}
				}

			} finally {
				if (stmt != null) {
					try {
						stmt.close();
					} catch (Throwable t) {
						t.printStackTrace();
					}
				}
			}

		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
		}

		return result;
	}
	private class QueryTask implements Callable<Integer> {

		private String query;
		@SuppressWarnings("unused")
        private String taskName;
		private List<Object> accumulation;

		public QueryTask(String query, int i) {
			this.query = query;
			this.taskName = "QueryTask eyecatcher, task #" + i;  // Something I might want to see in a dump
			this.accumulation = accumulateResults ? new ArrayList<Object>() : null;
		}

		public Integer call() {
			Integer rowCount = null;
			if (transactional) {
				UserTransaction userTransaction = null;
				try {
					userTransaction = lookupTran();
					userTransaction.begin();
					rowCount = (Integer) executeSingleQuery(query, accumulation);
					userTransaction.commit();
				} catch (Exception e) {
					try {
						if(userTransaction != null) {
							userTransaction.rollback();
						}
					} catch (SystemException e1) {
						System.out.println("Failed to rollback transaction: "+e1.getMessage());
					}
					throw new RuntimeException(e);
				}
			} else {
				try {
					rowCount = executeSingleQuery(query, accumulation);
				} catch (SQLException sqle) {
					throw new RuntimeException(sqle);
				}
			}
			return rowCount;
		}
	}


	protected UserTransaction lookupTran() throws NamingException {
		InitialContext ic = new InitialContext();
		return (UserTransaction)ic.lookup("java:comp/UserTransaction");
	}
}
