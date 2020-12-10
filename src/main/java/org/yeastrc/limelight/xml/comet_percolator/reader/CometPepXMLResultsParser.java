/*
 * Original author: Michael Riffle <mriffle .at. uw.edu>
 *                  
 * Copyright 2018 University of Washington - Seattle, WA
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.yeastrc.limelight.xml.comet_percolator.reader;

import java.io.File;
import java.math.BigDecimal;
import org.yeastrc.limelight.xml.comet_percolator.objects.CometParameters;
import org.yeastrc.limelight.xml.comet_percolator.objects.CometResults;
import org.yeastrc.limelight.xml.comet_percolator.utils.CometPepXMLParsingUtils;
import org.yeastrc.limelight.xml.comet_percolator.utils.ReportedPeptideUtils;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.yeastrc.limelight.xml.comet_percolator.objects.CometPSM;
import org.yeastrc.limelight.xml.comet_percolator.objects.CometReportedPeptide;

import net.systemsbiology.regis_web.pepxml.MsmsPipelineAnalysis;
import net.systemsbiology.regis_web.pepxml.MsmsPipelineAnalysis.MsmsRunSummary;
import net.systemsbiology.regis_web.pepxml.MsmsPipelineAnalysis.MsmsRunSummary.SpectrumQuery;
import net.systemsbiology.regis_web.pepxml.MsmsPipelineAnalysis.MsmsRunSummary.SpectrumQuery.SearchResult;
import net.systemsbiology.regis_web.pepxml.MsmsPipelineAnalysis.MsmsRunSummary.SpectrumQuery.SearchResult.SearchHit;

/**
 * @author Michael Riffle
 * @date Feb 21, 2018
 *
 */
public class CometPepXMLResultsParser {

	public static CometResults getCometResults(Collection<File> pepXMLFiles, CometParameters cometParams, Collection<String> validReportedPeptides ) throws Throwable {

		CometResults finalResults = new CometResults();

		Map<CometReportedPeptide, Map<String, Map<Integer, CometPSM>>> resultMap = new HashMap<>();
		finalResults.setPeptidePSMMap( resultMap );

		for( File pepXMLFile : pepXMLFiles) {

			MsmsPipelineAnalysis msAnalysis = null;
			try {
				msAnalysis = CometPepXMLParsingUtils.getMSmsPipelineAnalysis(pepXMLFile);
			} catch (Throwable t) {
				System.err.println("Got an error parsing the pep XML file. Error: " + t.getMessage());
				throw t;
			}

			if( finalResults.getCometVersion() == null ) {
				finalResults.setCometVersion(CometPepXMLParsingUtils.getCometVersionFromXML(msAnalysis));
			}

			for (MsmsRunSummary runSummary : msAnalysis.getMsmsRunSummary()) {

				if( finalResults.getScanFileExtension() == null ) {
					finalResults.setScanFileExtension(CometPepXMLParsingUtils.getScanFileExtensionFromXML(runSummary));
					if (finalResults.getScanFileExtension() == null) {
						throw new Exception("Could not get scan file extension from pepXML file.");
					}
				}

				for (SpectrumQuery spectrumQuery : runSummary.getSpectrumQuery()) {

					int charge = CometPepXMLParsingUtils.getChargeFromSpectrumQuery(spectrumQuery);
					int scanNumber = CometPepXMLParsingUtils.getScanNumberFromSpectrumQuery(spectrumQuery);
					BigDecimal neutralMass = CometPepXMLParsingUtils.getNeutralMassFromSpectrumQuery(spectrumQuery);
					BigDecimal retentionTime = CometPepXMLParsingUtils.getRetentionTimeFromSpectrumQuery(spectrumQuery);

					for (SearchResult searchResult : spectrumQuery.getSearchResult()) {
						for (SearchHit searchHit : searchResult.getSearchHit()) {

							// do not include decoy hits
							if (CometPepXMLParsingUtils.searchHitIsDecoy(searchHit, cometParams)) {
								continue;
							}

							CometPSM psm;

							try {

								psm = CometPepXMLParsingUtils.getPsmFromSearchHit(searchHit, charge, scanNumber, neutralMass, retentionTime, cometParams);

							} catch (Throwable t) {

								System.err.println("Error reading PSM from pepXML. Error: " + t.getMessage());
								throw t;

							}

							if (psm != null) {

								if(finalResults.isDeltaCNStarPresent() && psm.getDeltaCnStar() == null) {
									finalResults.setDeltaCNStarPresent( false );
								}

								CometReportedPeptide tppRp = ReportedPeptideUtils.getTPPReportedPeptideForTPPPSM(psm);

								if( validReportedPeptides != null && validReportedPeptides.contains( tppRp.getReportedPeptideString() ) ) {

									if (!resultMap.containsKey(tppRp)) {
										resultMap.put(tppRp, new HashMap<>());
									}

									if (!resultMap.get(tppRp).containsKey(pepXMLFile.getName())) {
										resultMap.get(tppRp).put(pepXMLFile.getName(), new HashMap<>());
									}

									resultMap.get(tppRp).get(pepXMLFile.getName()).put(psm.getScanNumber(), psm);
								}
							}
						}
					}
				}
			}
		}

		return finalResults;
	}
	
}
