package org.scorpio.microscope.enginex.core;

import java.sql.*;

public class MDatabaseMetaData extends MBase implements DatabaseMetaData {


    protected DatabaseMetaData passthru;
    protected MConnection connection;

    public MDatabaseMetaData(MFactory factory, DatabaseMetaData metadata, MConnection connection) {
        setMFactory(factory);
        this.passthru = metadata;
        this.connection = connection;
    }

    public boolean allProceduresAreCallable() throws SQLException {
        return passthru.allProceduresAreCallable();
    }

    public boolean allTablesAreSelectable() throws SQLException {
        return passthru.allTablesAreSelectable();
    }

    public boolean dataDefinitionCausesTransactionCommit() throws SQLException {
        return passthru.dataDefinitionCausesTransactionCommit();
    }

    public boolean dataDefinitionIgnoredInTransactions() throws SQLException {
        return passthru.dataDefinitionIgnoredInTransactions();
    }

    public boolean deletesAreDetected(int p0) throws SQLException {
        return passthru.deletesAreDetected(p0);
    }

    public boolean doesMaxRowSizeIncludeBlobs() throws SQLException {
        return passthru.doesMaxRowSizeIncludeBlobs();
    }

    public ResultSet getBestRowIdentifier(String p0, String p1, String p2, int p3, boolean p4) throws SQLException {
        return passthru.getBestRowIdentifier(p0, p1, p2, p3, p4);
    }

    public String getCatalogSeparator() throws SQLException {
        return passthru.getCatalogSeparator();
    }

    public String getCatalogTerm() throws SQLException {
        return passthru.getCatalogTerm();
    }

    public ResultSet getCatalogs() throws SQLException {
        return passthru.getCatalogs();
    }

    public ResultSet getColumnPrivileges(String p0, String p1, String p2, String p3) throws SQLException {
        return passthru.getColumnPrivileges(p0, p1, p2, p3);
    }

    public ResultSet getColumns(String p0, String p1, String p2, String p3) throws SQLException {
        return passthru.getColumns(p0, p1, p2, p3);
    }

    public Connection getConnection() throws SQLException {
        // return our p6connection
        return connection;
    }

    public ResultSet getCrossReference(String p0, String p1, String p2, String p3, String p4, String p5) throws SQLException {
        return passthru.getCrossReference(p0, p1, p2, p3, p4, p5);
    }

    public String getDatabaseProductName() throws SQLException {
        return passthru.getDatabaseProductName();
    }

    public String getDatabaseProductVersion() throws SQLException {
        return passthru.getDatabaseProductVersion();
    }

    public int getDefaultTransactionIsolation() throws SQLException {
        return passthru.getDefaultTransactionIsolation();
    }

    public int getDriverMajorVersion() {
        return passthru.getDriverMajorVersion();
    }

    public int getDriverMinorVersion() {
        return passthru.getDriverMinorVersion();
    }

    public String getDriverName() throws SQLException {
        return passthru.getDriverName();
    }

    public String getDriverVersion() throws SQLException {
        return passthru.getDriverVersion();
    }

    public ResultSet getExportedKeys(String p0, String p1, String p2) throws SQLException {
        return passthru.getExportedKeys(p0, p1, p2);
    }

    public String getExtraNameCharacters() throws SQLException {
        return passthru.getExtraNameCharacters();
    }

    public String getIdentifierQuoteString() throws SQLException {
        return passthru.getIdentifierQuoteString();
    }

    public ResultSet getImportedKeys(String p0, String p1, String p2) throws SQLException {
        return passthru.getImportedKeys(p0, p1, p2);
    }

    public ResultSet getIndexInfo(String p0, String p1, String p2, boolean p3, boolean p4) throws SQLException {
        return passthru.getIndexInfo(p0, p1, p2, p3, p4);
    }

    public int getMaxBinaryLiteralLength() throws SQLException {
        return passthru.getMaxBinaryLiteralLength();
    }

    public int getMaxCatalogNameLength() throws SQLException {
        return passthru.getMaxCatalogNameLength();
    }

    public int getMaxCharLiteralLength() throws SQLException {
        return passthru.getMaxCharLiteralLength();
    }

    public int getMaxColumnNameLength() throws SQLException {
        return passthru.getMaxColumnNameLength();
    }

    public int getMaxColumnsInGroupBy() throws SQLException {
        return passthru.getMaxColumnsInGroupBy();
    }

    public int getMaxColumnsInIndex() throws SQLException {
        return passthru.getMaxColumnsInIndex();
    }

    public int getMaxColumnsInOrderBy() throws SQLException {
        return passthru.getMaxColumnsInOrderBy();
    }

    public int getMaxColumnsInSelect() throws SQLException {
        return passthru.getMaxColumnsInSelect();
    }

    public int getMaxColumnsInTable() throws SQLException {
        return passthru.getMaxColumnsInTable();
    }

    public int getMaxConnections() throws SQLException {
        return passthru.getMaxConnections();
    }

    public int getMaxCursorNameLength() throws SQLException {
        return passthru.getMaxCursorNameLength();
    }

    public int getMaxIndexLength() throws SQLException {
        return passthru.getMaxIndexLength();
    }

    public int getMaxProcedureNameLength() throws SQLException {
        return passthru.getMaxProcedureNameLength();
    }

    public int getMaxRowSize() throws SQLException {
        return passthru.getMaxRowSize();
    }

    public int getMaxSchemaNameLength() throws SQLException {
        return passthru.getMaxSchemaNameLength();
    }

    public int getMaxStatementLength() throws SQLException {
        return passthru.getMaxStatementLength();
    }

    public int getMaxStatements() throws SQLException {
        return passthru.getMaxStatements();
    }

    public int getMaxTableNameLength() throws SQLException {
        return passthru.getMaxTableNameLength();
    }

    public int getMaxTablesInSelect() throws SQLException {
        return passthru.getMaxTablesInSelect();
    }

    public int getMaxUserNameLength() throws SQLException {
        return passthru.getMaxUserNameLength();
    }

    public String getNumericFunctions() throws SQLException {
        return passthru.getNumericFunctions();
    }

    public ResultSet getPrimaryKeys(String p0, String p1, String p2) throws SQLException {
        return passthru.getPrimaryKeys(p0, p1, p2);
    }

    public ResultSet getProcedureColumns(String p0, String p1, String p2, String p3) throws SQLException {
        return passthru.getProcedureColumns(p0, p1, p2, p3);
    }

    public String getProcedureTerm() throws SQLException {
        return passthru.getProcedureTerm();
    }

    public ResultSet getProcedures(String p0, String p1, String p2) throws SQLException {
        return passthru.getProcedures(p0, p1, p2);
    }

    public String getSQLKeywords() throws SQLException {
        return passthru.getSQLKeywords();
    }

    public String getSchemaTerm() throws SQLException {
        return passthru.getSchemaTerm();
    }

    public ResultSet getSchemas() throws SQLException {
        return passthru.getSchemas();
    }

    public String getSearchStringEscape() throws SQLException {
        return passthru.getSearchStringEscape();
    }

    public String getStringFunctions() throws SQLException {
        return passthru.getStringFunctions();
    }

    public String getSystemFunctions() throws SQLException {
        return passthru.getSystemFunctions();
    }

    public ResultSet getTablePrivileges(String p0, String p1, String p2) throws SQLException {
        return passthru.getTablePrivileges(p0, p1, p2);
    }

    public ResultSet getTableTypes() throws SQLException {
        return passthru.getTableTypes();
    }

    public ResultSet getTables(String p0, String p1, String p2, String[] p3) throws SQLException {
        return passthru.getTables(p0, p1, p2, p3);
    }

    public String getTimeDateFunctions() throws SQLException {
        return passthru.getTimeDateFunctions();
    }

    public ResultSet getTypeInfo() throws SQLException {
        return passthru.getTypeInfo();
    }

    public ResultSet getUDTs(String p0, String p1, String p2, int[] p3) throws SQLException {
        return passthru.getUDTs(p0, p1, p2, p3);
    }

    public String getURL() throws SQLException {
        return passthru.getURL();
    }

    public String getUserName() throws SQLException {
        return passthru.getUserName();
    }

    public ResultSet getVersionColumns(String p0, String p1, String p2) throws SQLException {
        return passthru.getVersionColumns(p0, p1, p2);
    }

    public boolean insertsAreDetected(int p0) throws SQLException {
        return passthru.insertsAreDetected(p0);
    }

    public boolean isCatalogAtStart() throws SQLException {
        return passthru.isCatalogAtStart();
    }

    public boolean isReadOnly() throws SQLException {
        return passthru.isReadOnly();
    }

    public boolean nullPlusNonNullIsNull() throws SQLException {
        return passthru.nullPlusNonNullIsNull();
    }

    public boolean nullsAreSortedAtEnd() throws SQLException {
        return passthru.nullsAreSortedAtEnd();
    }

    public boolean nullsAreSortedAtStart() throws SQLException {
        return passthru.nullsAreSortedAtStart();
    }

    public boolean nullsAreSortedHigh() throws SQLException {
        return passthru.nullsAreSortedHigh();
    }

    public boolean nullsAreSortedLow() throws SQLException {
        return passthru.nullsAreSortedLow();
    }

    public boolean othersDeletesAreVisible(int p0) throws SQLException {
        return passthru.othersDeletesAreVisible(p0);
    }

    public boolean othersInsertsAreVisible(int p0) throws SQLException {
        return passthru.othersInsertsAreVisible(p0);
    }

    public boolean othersUpdatesAreVisible(int p0) throws SQLException {
        return passthru.othersUpdatesAreVisible(p0);
    }

    public boolean ownDeletesAreVisible(int p0) throws SQLException {
        return passthru.ownDeletesAreVisible(p0);
    }

    public boolean ownInsertsAreVisible(int p0) throws SQLException {
        return passthru.ownInsertsAreVisible(p0);
    }

    public boolean ownUpdatesAreVisible(int p0) throws SQLException {
        return passthru.ownUpdatesAreVisible(p0);
    }

    public boolean storesLowerCaseIdentifiers() throws SQLException {
        return passthru.storesLowerCaseIdentifiers();
    }

    public boolean storesLowerCaseQuotedIdentifiers() throws SQLException {
        return passthru.storesLowerCaseQuotedIdentifiers();
    }

    public boolean storesMixedCaseIdentifiers() throws SQLException {
        return passthru.storesMixedCaseIdentifiers();
    }

    public boolean storesMixedCaseQuotedIdentifiers() throws SQLException {
        return passthru.storesMixedCaseQuotedIdentifiers();
    }

    public boolean storesUpperCaseIdentifiers() throws SQLException {
        return passthru.storesUpperCaseIdentifiers();
    }

    public boolean storesUpperCaseQuotedIdentifiers() throws SQLException {
        return passthru.storesUpperCaseQuotedIdentifiers();
    }

    public boolean supportsANSI92EntryLevelSQL() throws SQLException {
        return passthru.supportsANSI92EntryLevelSQL();
    }

    public boolean supportsANSI92FullSQL() throws SQLException {
        return passthru.supportsANSI92FullSQL();
    }

    public boolean supportsANSI92IntermediateSQL() throws SQLException {
        return passthru.supportsANSI92IntermediateSQL();
    }

    public boolean supportsAlterTableWithAddColumn() throws SQLException {
        return passthru.supportsAlterTableWithAddColumn();
    }

    public boolean supportsAlterTableWithDropColumn() throws SQLException {
        return passthru.supportsAlterTableWithDropColumn();
    }

    public boolean supportsBatchUpdates() throws SQLException {
        return passthru.supportsBatchUpdates();
    }

    public boolean supportsCatalogsInDataManipulation() throws SQLException {
        return passthru.supportsCatalogsInDataManipulation();
    }

    public boolean supportsCatalogsInIndexDefinitions() throws SQLException {
        return passthru.supportsCatalogsInIndexDefinitions();
    }

    public boolean supportsCatalogsInPrivilegeDefinitions() throws SQLException {
        return passthru.supportsCatalogsInPrivilegeDefinitions();
    }

    public boolean supportsCatalogsInProcedureCalls() throws SQLException {
        return passthru.supportsCatalogsInProcedureCalls();
    }

    public boolean supportsCatalogsInTableDefinitions() throws SQLException {
        return passthru.supportsCatalogsInTableDefinitions();
    }

    public boolean supportsColumnAliasing() throws SQLException {
        return passthru.supportsColumnAliasing();
    }

    public boolean supportsConvert() throws SQLException {
        return passthru.supportsConvert();
    }

    public boolean supportsConvert(int p0, int p1) throws SQLException {
        return passthru.supportsConvert(p0, p1);
    }

    public boolean supportsCoreSQLGrammar() throws SQLException {
        return passthru.supportsCoreSQLGrammar();
    }

    public boolean supportsCorrelatedSubqueries() throws SQLException {
        return passthru.supportsCorrelatedSubqueries();
    }

    public boolean supportsDataDefinitionAndDataManipulationTransactions() throws SQLException {
        return passthru.supportsDataDefinitionAndDataManipulationTransactions();
    }

    public boolean supportsDataManipulationTransactionsOnly() throws SQLException {
        return passthru.supportsDataManipulationTransactionsOnly();
    }

    public boolean supportsDifferentTableCorrelationNames() throws SQLException {
        return passthru.supportsDifferentTableCorrelationNames();
    }

    public boolean supportsExpressionsInOrderBy() throws SQLException {
        return passthru.supportsExpressionsInOrderBy();
    }

    public boolean supportsExtendedSQLGrammar() throws SQLException {
        return passthru.supportsExtendedSQLGrammar();
    }

    public boolean supportsFullOuterJoins() throws SQLException {
        return passthru.supportsFullOuterJoins();
    }

    public boolean supportsGroupBy() throws SQLException {
        return passthru.supportsGroupBy();
    }

    public boolean supportsGroupByBeyondSelect() throws SQLException {
        return passthru.supportsGroupByBeyondSelect();
    }

    public boolean supportsGroupByUnrelated() throws SQLException {
        return passthru.supportsGroupByUnrelated();
    }

    public boolean supportsIntegrityEnhancementFacility() throws SQLException {
        return passthru.supportsIntegrityEnhancementFacility();
    }

    public boolean supportsLikeEscapeClause() throws SQLException {
        return passthru.supportsLikeEscapeClause();
    }

    public boolean supportsLimitedOuterJoins() throws SQLException {
        return passthru.supportsLimitedOuterJoins();
    }

    public boolean supportsMinimumSQLGrammar() throws SQLException {
        return passthru.supportsMinimumSQLGrammar();
    }

    public boolean supportsMixedCaseIdentifiers() throws SQLException {
        return passthru.supportsMixedCaseIdentifiers();
    }

    public boolean supportsMixedCaseQuotedIdentifiers() throws SQLException {
        return passthru.supportsMixedCaseQuotedIdentifiers();
    }

    public boolean supportsMultipleResultSets() throws SQLException {
        return passthru.supportsMultipleResultSets();
    }

    public boolean supportsMultipleTransactions() throws SQLException {
        return passthru.supportsMultipleTransactions();
    }

    public boolean supportsNonNullableColumns() throws SQLException {
        return passthru.supportsNonNullableColumns();
    }

    public boolean supportsOpenCursorsAcrossCommit() throws SQLException {
        return passthru.supportsOpenCursorsAcrossCommit();
    }

    public boolean supportsOpenCursorsAcrossRollback() throws SQLException {
        return passthru.supportsOpenCursorsAcrossRollback();
    }

    public boolean supportsOpenStatementsAcrossCommit() throws SQLException {
        return passthru.supportsOpenStatementsAcrossCommit();
    }

    public boolean supportsOpenStatementsAcrossRollback() throws SQLException {
        return passthru.supportsOpenStatementsAcrossRollback();
    }

    public boolean supportsOrderByUnrelated() throws SQLException {
        return passthru.supportsOrderByUnrelated();
    }

    public boolean supportsOuterJoins() throws SQLException {
        return passthru.supportsOuterJoins();
    }

    public boolean supportsPositionedDelete() throws SQLException {
        return passthru.supportsPositionedDelete();
    }

    public boolean supportsPositionedUpdate() throws SQLException {
        return passthru.supportsPositionedUpdate();
    }

    public boolean supportsResultSetConcurrency(int p0, int p1) throws SQLException {
        return passthru.supportsResultSetConcurrency(p0, p1);
    }

    public boolean supportsResultSetType(int p0) throws SQLException {
        return passthru.supportsResultSetType(p0);
    }

    public boolean supportsSchemasInDataManipulation() throws SQLException {
        return passthru.supportsSchemasInDataManipulation();
    }

    public boolean supportsSchemasInIndexDefinitions() throws SQLException {
        return passthru.supportsSchemasInIndexDefinitions();
    }

    public boolean supportsSchemasInPrivilegeDefinitions() throws SQLException {
        return passthru.supportsSchemasInPrivilegeDefinitions();
    }

    public boolean supportsSchemasInProcedureCalls() throws SQLException {
        return passthru.supportsSchemasInProcedureCalls();
    }

    public boolean supportsSchemasInTableDefinitions() throws SQLException {
        return passthru.supportsSchemasInTableDefinitions();
    }

    public boolean supportsSelectForUpdate() throws SQLException {
        return passthru.supportsSelectForUpdate();
    }

    public boolean supportsStoredProcedures() throws SQLException {
        return passthru.supportsStoredProcedures();
    }

    public boolean supportsSubqueriesInComparisons() throws SQLException {
        return passthru.supportsSubqueriesInComparisons();
    }

    public boolean supportsSubqueriesInExists() throws SQLException {
        return passthru.supportsSubqueriesInExists();
    }

    public boolean supportsSubqueriesInIns() throws SQLException {
        return passthru.supportsSubqueriesInIns();
    }

    public boolean supportsSubqueriesInQuantifieds() throws SQLException {
        return passthru.supportsSubqueriesInQuantifieds();
    }

    public boolean supportsTableCorrelationNames() throws SQLException {
        return passthru.supportsTableCorrelationNames();
    }

    public boolean supportsTransactionIsolationLevel(int p0) throws SQLException {
        return passthru.supportsTransactionIsolationLevel(p0);
    }

    public boolean supportsTransactions() throws SQLException {
        return passthru.supportsTransactions();
    }

    public boolean supportsUnion() throws SQLException {
        return passthru.supportsUnion();
    }

    public boolean supportsUnionAll() throws SQLException {
        return passthru.supportsUnionAll();
    }

    public boolean updatesAreDetected(int p0) throws SQLException {
        return passthru.updatesAreDetected(p0);
    }

    public boolean usesLocalFilePerTable() throws SQLException {
        return passthru.usesLocalFilePerTable();
    }

    public boolean usesLocalFiles() throws SQLException {
        return passthru.usesLocalFiles();
    }

    // Since JDK 1.4
    public boolean supportsSavepoints() throws SQLException {
        return passthru.supportsSavepoints();
    }

    // Since JDK 1.4
    public boolean supportsNamedParameters() throws SQLException {
        return passthru.supportsNamedParameters();
    }

    // Since JDK 1.4
    public boolean supportsMultipleOpenResults() throws SQLException {
        return passthru.supportsMultipleOpenResults();
    }

    // Since JDK 1.4
    public boolean supportsGetGeneratedKeys() throws SQLException {
        return passthru.supportsGetGeneratedKeys();
    }

    // Since JDK 1.4
    public ResultSet getSuperTypes(String p0, String p1, String p2) throws SQLException {
        return passthru.getSuperTypes(p0, p1, p2);
    }

    // Since JDK 1.4
    public ResultSet getSuperTables(String p0, String p1, String p2) throws SQLException {
        return passthru.getSuperTables(p0, p1, p2);
    }

    // Since JDK 1.4
    public ResultSet getAttributes(String p0, String p1, String p2, String p3) throws SQLException {
        return passthru.getAttributes(p0, p1, p2, p3);
    }

    // Since JDK 1.4
    public boolean supportsResultSetHoldability(int p0) throws SQLException {
        return passthru.supportsResultSetHoldability(p0);
    }

    // Since JDK 1.4
    public int getResultSetHoldability() throws SQLException {
        return passthru.getResultSetHoldability();
    }

    // Since JDK 1.4
    public int getDatabaseMajorVersion() throws SQLException {
        return passthru.getDatabaseMajorVersion();
    }

    // Since JDK 1.4
    public int getDatabaseMinorVersion() throws SQLException {
        return passthru.getDatabaseMinorVersion();
    }

    // Since JDK 1.4
    public int getJDBCMajorVersion() throws SQLException {
        return passthru.getJDBCMajorVersion();
    }

    // Since JDK 1.4
    public int getJDBCMinorVersion() throws SQLException {
        return passthru.getJDBCMinorVersion();
    }

    // Since JDK 1.4
    public int getSQLStateType() throws SQLException {
        return passthru.getSQLStateType();
    }

    // Since JDK 1.4
    public boolean locatorsUpdateCopy() throws SQLException {
        return passthru.locatorsUpdateCopy();
    }

    // Since JDK 1.4
    public boolean supportsStatementPooling() throws SQLException {
        return passthru.supportsStatementPooling();
    }

    public DatabaseMetaData getJDBC() {
        DatabaseMetaData wrapped = (passthru instanceof MDatabaseMetaData) ?
                ((MDatabaseMetaData) passthru).getJDBC() :
                passthru;

        return wrapped;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return passthru.unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return passthru.isWrapperFor(iface);
    }

    @Override
    public RowIdLifetime getRowIdLifetime() throws SQLException {
        return passthru.getRowIdLifetime();
    }

    @Override
    public ResultSet getSchemas(String catalog, String schemaPattern)
            throws SQLException {
        return passthru.getSchemas(catalog, schemaPattern);
    }

    @Override
    public boolean supportsStoredFunctionsUsingCallSyntax() throws SQLException {
        return passthru.supportsStoredFunctionsUsingCallSyntax();
    }

    @Override
    public boolean autoCommitFailureClosesAllResultSets() throws SQLException {
        return passthru.autoCommitFailureClosesAllResultSets();
    }

    @Override
    public ResultSet getClientInfoProperties() throws SQLException {
        return passthru.getClientInfoProperties();
    }

    @Override
    public ResultSet getFunctions(String catalog, String schemaPattern,
                                  String functionNamePattern) throws SQLException {
        return passthru.getFunctions(catalog, schemaPattern, functionNamePattern);
    }

    @Override
    public ResultSet getFunctionColumns(String catalog, String schemaPattern,
                                        String functionNamePattern, String columnNamePattern)
            throws SQLException {
        return passthru.getFunctionColumns(catalog, schemaPattern, functionNamePattern, columnNamePattern);
    }

    @Override
    public ResultSet getPseudoColumns(String catalog, String schemaPattern, String tableNamePattern, String columnNamePattern) throws SQLException {
        // TODO by kevin
        return null;
    }

    @Override
    public boolean generatedKeyAlwaysReturned() throws SQLException {
        // TODO by kevin
        return false;
    }
}
