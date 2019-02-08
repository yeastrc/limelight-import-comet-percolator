package org.yeastrc.limelight.xml.comet_percolator.objects;

import java.util.Map;

public class CometResults {

	private String cometVersion;
	private Map<CometReportedPeptide, Map<String, Map<Integer, CometPSM>>> peptidePSMMap;
	private String scanFileExtension;

	/**
	 * @return the cometVersion
	 */
	public String getCometVersion() {
		return cometVersion;
	}
	/**
	 * @param cometVersion the cometVersion to set
	 */
	public void setCometVersion(String cometVersion) {
		this.cometVersion = cometVersion;
	}

	public Map<CometReportedPeptide, Map<String, Map<Integer, CometPSM>>> getPeptidePSMMap() {
		return peptidePSMMap;
	}

	public void setPeptidePSMMap(Map<CometReportedPeptide, Map<String, Map<Integer, CometPSM>>> peptidePSMMap) {
		this.peptidePSMMap = peptidePSMMap;
	}

	public String getScanFileExtension() {
		return scanFileExtension;
	}

	public void setScanFileExtension(String scanFileExtension) {
		this.scanFileExtension = scanFileExtension;
	}
}
