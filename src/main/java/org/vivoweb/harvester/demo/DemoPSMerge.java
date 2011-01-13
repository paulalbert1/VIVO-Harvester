package org.vivoweb.harvester.demo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.vfs.VFS;
import org.vivoweb.harvester.fetch.JDBCFetch;
import org.vivoweb.harvester.score.Score;
import org.vivoweb.harvester.score.algorithm.Algorithm;
import org.vivoweb.harvester.score.algorithm.EqualityTest;
import org.vivoweb.harvester.translate.XSLTranslator;
import org.vivoweb.harvester.util.Merge;
import org.vivoweb.harvester.util.repo.JDBCRecordHandler;
import org.vivoweb.harvester.util.repo.JenaConnect;
import org.vivoweb.harvester.util.repo.MemJenaConnect;
import org.vivoweb.harvester.util.repo.Record;
import org.vivoweb.harvester.util.repo.RecordHandler;
import org.vivoweb.harvester.util.repo.SDBJenaConnect;

/**
 * 	
 */
public class DemoPSMerge {
	
	/**
	 * @param args cmdline args
	 * @throws SQLException error
	 * @throws ClassNotFoundException error
	 * @throws IOException error
	 */
	public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
		Set<String> tableNames = new HashSet<String>();
		Map<String,String> fromClauses = new HashMap<String,String>();
		Map<String,List<String>> dataFields = new HashMap<String, List<String>>();
		Map<String,List<String>> idFields = new HashMap<String, List<String>>();
		Map<String,List<String>> whereClauses = new HashMap<String, List<String>>();
		Map<String,Map<String,String>> relations = new HashMap<String, Map<String,String>>();
		Map<String,String> queryStrings = new HashMap<String,String>();
		
		tableNames.add("t_UF_DIR_EMP_STU_1");
		idFields.put("t_UF_DIR_EMP_STU_1", Arrays.asList("UF_IDENTIFIER"));
		whereClauses.put("t_UF_DIR_EMP_STU_1", Arrays.asList(
				"t_UF_DIR_EMP_STU_1.UF_PROTECT_FLG='N'",
				"t_UF_DIR_EMP_STU_1.UF_SECURITY_FLG='N'"
		));
		
		tableNames.add("t_UF_DIR_EMP_STU_2");
		idFields.put("t_UF_DIR_EMP_STU_2", Arrays.asList("UF_UUID", "UF_TYPE_CD"));
		whereClauses.put("t_UF_DIR_EMP_STU_2", Arrays.asList(
				"t_UF_DIR_EMP_STU_2.UF_UUID=t_UF_DIR_EMP_STU_1.UF_IDENTIFIER",
				"t_UF_DIR_EMP_STU_1.UF_PROTECT_FLG='N'",
				"t_UF_DIR_EMP_STU_1.UF_SECURITY_FLG='N'"
		));
		fromClauses.put("t_UF_DIR_EMP_STU_2", "t_UF_DIR_EMP_STU_1");
		
		tableNames.add("t_UF_DIR_EMP_STU_3");
		idFields.put("t_UF_DIR_EMP_STU_3", Arrays.asList("UF_UUID", "UF_TYPE_CD"));
		whereClauses.put("t_UF_DIR_EMP_STU_3", Arrays.asList(
				"t_UF_DIR_EMP_STU_3.UF_UUID=t_UF_DIR_EMP_STU_1.UF_IDENTIFIER",
				"t_UF_DIR_EMP_STU_1.UF_PROTECT_FLG='N'",
				"t_UF_DIR_EMP_STU_1.UF_SECURITY_FLG='N'"
		));
		fromClauses.put("t_UF_DIR_EMP_STU_3", "t_UF_DIR_EMP_STU_1");
		
		tableNames.add("t_UF_DIR_EMP_STU_4");
		idFields.put("t_UF_DIR_EMP_STU_4", Arrays.asList("UF_UUID", "UF_TYPE_CD"));
		whereClauses.put("t_UF_DIR_EMP_STU_4", Arrays.asList(
				"t_UF_DIR_EMP_STU_4.UF_UUID=t_UF_DIR_EMP_STU_1.UF_IDENTIFIER",
				"t_UF_DIR_EMP_STU_1.UF_PROTECT_FLG='N'",
				"t_UF_DIR_EMP_STU_1.UF_SECURITY_FLG='N'"
		));
		fromClauses.put("t_UF_DIR_EMP_STU_4", "t_UF_DIR_EMP_STU_1");
		
		tableNames.add("t_UF_DIR_EMP_STU_5");
		idFields.put("t_UF_DIR_EMP_STU_5", Arrays.asList("UF_UUID1", "UF_TYPE_CD", "UF_UUID2", "PS_DEPTID", "UF_BEGIN_TS"));
		whereClauses.put("t_UF_DIR_EMP_STU_5", Arrays.asList(
				"t_UF_DIR_EMP_STU_5.UF_UUID1=t_UF_DIR_EMP_STU_1.UF_IDENTIFIER",
				"t_UF_DIR_EMP_STU_1.UF_PROTECT_FLG='N'",
				"t_UF_DIR_EMP_STU_1.UF_SECURITY_FLG='N'"
		));
		fromClauses.put("t_UF_DIR_EMP_STU_5", "t_UF_DIR_EMP_STU_1");
		
		tableNames.add("t_UF_DIR_EMP_STU_6");
		idFields.put("t_UF_DIR_EMP_STU_6", Arrays.asList("UF_UUID1", "UF_TYPE_CD", "UF_UUID2", "PS_DEPTID", "UF_BEGIN_TS"));
		whereClauses.put("t_UF_DIR_EMP_STU_6", Arrays.asList(
				"t_UF_DIR_EMP_STU_6.UF_UUID1=t_UF_DIR_EMP_STU_1.UF_IDENTIFIER",
				"t_UF_DIR_EMP_STU_1.UF_PROTECT_FLG='N'",
				"t_UF_DIR_EMP_STU_1.UF_SECURITY_FLG='N'"
		));
		fromClauses.put("t_UF_DIR_EMP_STU_6", "t_UF_DIR_EMP_STU_1");
		
		tableNames.add("t_UF_PER_UFAU");
		idFields.put("t_UF_PER_UFAU", Arrays.asList("UF_UUID", "UF_JOB_TITLE", "UF_PS_DEPTID"));
		whereClauses.put("t_UF_PER_UFAU", Arrays.asList(
				"t_UF_PER_UFAU.UF_UUID=t_UF_DIR_EMP_STU_1.UF_IDENTIFIER",
				"t_UF_DIR_EMP_STU_1.UF_PROTECT_FLG='N'",
				"t_UF_DIR_EMP_STU_1.UF_SECURITY_FLG='N'"
		));
		fromClauses.put("t_UF_PER_UFAU", "t_UF_DIR_EMP_STU_1");
		
		tableNames.add("t_UF_PA_GL_ACCT");
		idFields.put("t_UF_PA_GL_ACCT", Arrays.asList("OPRID", "USERIDALIAS"));
		whereClauses.put("t_UF_PA_GL_ACCT", Arrays.asList(
				"t_UF_PA_GL_ACCT.OPRID=t_UF_DIR_EMP_STU_1.UF_IDENTIFIER",
				"t_UF_DIR_EMP_STU_1.UF_PROTECT_FLG='N'",
				"t_UF_DIR_EMP_STU_1.UF_SECURITY_FLG='N'"
		));
		fromClauses.put("t_UF_PA_GL_ACCT", "t_UF_DIR_EMP_STU_1");
		
//		tableNames.add("t_PS_H_UF_ACAD_ORG");
//		idFields.put("t_PS_H_UF_ACAD_ORG", Arrays.asList("DTL_ID"));
//		dataFields.put("t_PS_H_UF_ACAD_ORG", Arrays.asList(
//			"DTL_DESC"//,
//			//"TREE_NAME",
//			//"L2_ID",
//			//"L2_DESC",
//			//"L3_ID",
//			//"L3_DESC",
//			//"L4_ID",
//			//"L4_DESC"
//		));
		
		List<String> ufidLimiters = Arrays.asList("t_UF_DIR_EMP_STU_1.UF_IDENTIFIER LIKE '%8973%'");
		whereClauses.put("t_UF_DIR_EMP_STU_1", new ArrayList<String>(whereClauses.get("t_UF_DIR_EMP_STU_1")));
		whereClauses.get("t_UF_DIR_EMP_STU_1").addAll(ufidLimiters);
		whereClauses.put("t_UF_DIR_EMP_STU_2", new ArrayList<String>(whereClauses.get("t_UF_DIR_EMP_STU_2")));
		whereClauses.get("t_UF_DIR_EMP_STU_2").addAll(ufidLimiters);
		whereClauses.put("t_UF_DIR_EMP_STU_3", new ArrayList<String>(whereClauses.get("t_UF_DIR_EMP_STU_3")));
		whereClauses.get("t_UF_DIR_EMP_STU_3").addAll(ufidLimiters);
		whereClauses.put("t_UF_DIR_EMP_STU_4", new ArrayList<String>(whereClauses.get("t_UF_DIR_EMP_STU_4")));
		whereClauses.get("t_UF_DIR_EMP_STU_4").addAll(ufidLimiters);
		whereClauses.put("t_UF_DIR_EMP_STU_5", new ArrayList<String>(whereClauses.get("t_UF_DIR_EMP_STU_5")));
		whereClauses.get("t_UF_DIR_EMP_STU_5").addAll(ufidLimiters);
		whereClauses.put("t_UF_DIR_EMP_STU_6", new ArrayList<String>(whereClauses.get("t_UF_DIR_EMP_STU_6")));
		whereClauses.get("t_UF_DIR_EMP_STU_6").addAll(ufidLimiters);
		whereClauses.put("t_UF_PER_UFAU", new ArrayList<String>(whereClauses.get("t_UF_PER_UFAU")));
		whereClauses.get("t_UF_PER_UFAU").addAll(ufidLimiters);
		whereClauses.put("t_UF_PA_GL_ACCT", new ArrayList<String>(whereClauses.get("t_UF_PA_GL_ACCT")));
		whereClauses.get("t_UF_PA_GL_ACCT").addAll(ufidLimiters);
		
		Class.forName("net.sourceforge.jtds.jdbc.Driver");
		String connLine = "jdbc:jtds:sqlserver://erp-prod-odbc.ad.ufl.edu:1433/ODBCWH;domain=UFAD";
		Connection dbConn = DriverManager.getConnection(connLine, "USERNAME", "PASSWORD");
		RecordHandler rawRH = new JDBCRecordHandler("org.h2.Driver", "jdbc:h2:XMLVault/demoMergePS/XML/store", "sa", "", "rawData", "rawID");
		
		JDBCFetch psFetch = new JDBCFetch(dbConn, rawRH, connLine+"/", "", "", tableNames, fromClauses, dataFields, idFields, whereClauses, relations, queryStrings);
//		psFetch.execute();
		psFetch.toString();
		
//		System.out.println("Raw Records:");
		for(Record r : rawRH) {
			r.toString();
//			System.out.println(r.getID());
		}
		
		RecordHandler mergedRH = new JDBCRecordHandler("org.h2.Driver", "jdbc:h2:XMLVault/demoMergedPS/XML/store", "sa", "", "mergedData", "mergedID");
		Merge psMerge = new Merge(rawRH, mergedRH, "t_UF_DIR_EMP_STU_1_(id_-_.*?)");
//		psMerge.execute();
		psMerge.toString();
		
//		System.out.println();
//		System.out.println("Merged Records:");
		for(Record r : mergedRH) {
			r.toString();
//			System.out.println("=== "+r.getID()+" ===");
//			System.out.println(r.getData());
//			System.out.println();
		}
		
//		System.out.println("Merged Data:");
//		System.out.println(mergedRH.getRecord("id_-_89734048").getData());
		
		InputStream xsl = VFS.getManager().resolveFile(new File("."), "config/datamaps/PeopleSoftToVivo.xsl").getContent().getInputStream();
		RecordHandler transRH = new JDBCRecordHandler("org.h2.Driver", "jdbc:h2:XMLVault/demoTransPS/XML/store", "sa", "", "transData", "transID");
		XSLTranslator psTranslate = new XSLTranslator(mergedRH, transRH, xsl, true);
//		psTranslate.execute();
		psTranslate.toString();
		
//		System.out.println("Translated Data:");
//		System.out.println(transRH.getRecordData("id_-_89734048"));
		
		MemJenaConnect jc = new MemJenaConnect();
		jc.loadRdfFromString(transRH.getRecordData("id_-_89734048"), null, null);
		String data = jc.exportRdfToString();
		System.out.println("Reformatted Data:");
		System.out.println(data);
		
		JenaConnect psInput = new SDBJenaConnect("jdbc:h2:XMLVault/demoTransPS/Jena/store", "sa", "", "H2", "org.h2.Driver", "layout2", "psTempModel");
		psInput.loadRdfFromRH(transRH, "http://vivo.ufl.edu/individual/");
		
		JenaConnect vivoJena = new SDBJenaConnect("jdbc:h2:XMLVault/demoVivo/store", "sa", "", "H2", "org.h2.Driver", "layout2", "vivoModel");
		vivoJena.loadRdfFromFile("XMLVault/vivoData.rdf.n3", "http://vivo.ufl.edu/individual/", "N3");
		
		JenaConnect scoreJena = psInput.neighborConnectClone("scoreData");
		JenaConnect tempJena = psInput.neighborConnectClone("tempModel");
		
		HashMap<String, Class<? extends Algorithm>> algorithms = new HashMap<String, Class<? extends Algorithm>>();
		HashMap<String, String> inputPredicates = new HashMap<String, String>();
		HashMap<String, String> vivoPredicates = new HashMap<String, String>();
		HashMap<String, Float> weights = new HashMap<String, Float>();
		
		tempJena.truncate();
		algorithms.clear();
		weights.clear();
		inputPredicates.clear();
		vivoPredicates.clear();
		algorithms.put("ufid", EqualityTest.class);
		weights.put("ufid", Float.valueOf(1f));
		inputPredicates.put("ufid", "http://vivo.ufl.edu/ontology/vivo-ufl/ufid");
		vivoPredicates.put("ufid", "http://vivo.ufl.edu/ontology/vivo-ufl/ufid");
		Score psScorePeople = new Score(psInput, vivoJena, scoreJena, tempJena, algorithms, inputPredicates, vivoPredicates, "http://vivoweb.org/harvest/ufl/peoplesoft/person/", weights);
		psScorePeople.execute();
		psScorePeople.toString();
		
		tempJena.truncate();
		algorithms.clear();
		weights.clear();
		inputPredicates.clear();
		vivoPredicates.clear();
		algorithms.put("deptId", EqualityTest.class);
		weights.put("deptId", Float.valueOf(1f));
		inputPredicates.put("deptId", "http://vivo.ufl.edu/ontology/vivo-ufl/deptID");
		vivoPredicates.put("deptId", "http://vivo.ufl.edu/ontology/vivo-ufl/deptID");
		Score psScoreDepts = new Score(psInput, vivoJena, scoreJena, tempJena, algorithms, inputPredicates, vivoPredicates, "http://vivoweb.org/harvest/ufl/peoplesoft/org/", weights);
		psScoreDepts.execute();
		psScoreDepts.toString();
		
		tempJena.truncate();
		algorithms.clear();
		weights.clear();
		inputPredicates.clear();
		vivoPredicates.clear();
		algorithms.put("posOrg", EqualityTest.class);
		weights.put("posOrg", Float.valueOf(1f));
		inputPredicates.put("posOrg", "http://vivoweb.org/ontology/core#positionInOrganization");
		vivoPredicates.put("posOrg", "http://vivoweb.org/ontology/core#positionInOrganization");
		algorithms.put("posPer", EqualityTest.class);
		weights.put("posPer", Float.valueOf(1f));
		inputPredicates.put("posPer", "http://vivoweb.org/ontology/core#positionForPerson");
		vivoPredicates.put("posPer", "http://vivoweb.org/ontology/core#positionForPerson");
		algorithms.put("deptPos", EqualityTest.class);
		weights.put("deptPos", Float.valueOf(1f));
		inputPredicates.put("deptPos", "http://vivo.ufl.edu/ontology/vivo-ufl/deptIDofPosition");
		vivoPredicates.put("deptPos", "hhttp://vivo.ufl.edu/ontology/vivo-ufl/deptIDofPosition");
		Score psScorePos = new Score(psInput, vivoJena, scoreJena, tempJena, algorithms, inputPredicates, vivoPredicates, "http://vivoweb.org/harvest/ufl/peoplesoft/position/", weights);
		psScorePos.execute();
		psScorePos.toString();
		
//		# Find matches using scores and rename nodes to matching uri
//		$Match $SCOREMODELS -t 1.0 -r
	}
}
