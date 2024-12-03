package com.gov.Authmis.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "PROJECT_DETAILS")
public class ProjectDetailsEntity {
	@Id
	@Column(name = "PROJECT_ID")
	private Long projectId;
	
	@Column(name = "PROJECT_NAME")
	private String projectName;
	
	@OneToMany(mappedBy = "projectDetails", cascade = CascadeType.ALL)
	private List<ProjectInfraDataDatabaseDetails> databaseDetails;

	@OneToMany(mappedBy = "projectDetails", cascade = CascadeType.ALL)
	private List<ProjectInfraDataHardwareDetails> hardwareDetails;

	@OneToMany(mappedBy = "projectDetails", cascade = CascadeType.ALL)
	private List<ProjectInfraDataLBServerDetails> lbServerDetails;

	@OneToMany(mappedBy = "projectDetails", cascade = CascadeType.ALL)
	private List<ProjectInfraDataLeasedLineDetails> leasedLineDetails;

	@OneToMany(mappedBy = "projectDetails", cascade = CascadeType.ALL)
	private List<ProjectInfraDataServerDetails> serverDetails;

	

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public List<ProjectInfraDataDatabaseDetails> getDatabaseDetails() {
		return databaseDetails;
	}

	public void setDatabaseDetails(List<ProjectInfraDataDatabaseDetails> databaseDetails) {
		this.databaseDetails = databaseDetails;
	}

	public List<ProjectInfraDataHardwareDetails> getHardwareDetails() {
		return hardwareDetails;
	}

	public void setHardwareDetails(List<ProjectInfraDataHardwareDetails> hardwareDetails) {
		this.hardwareDetails = hardwareDetails;
	}

	public List<ProjectInfraDataLBServerDetails> getLbServerDetails() {
		return lbServerDetails;
	}

	public void setLbServerDetails(List<ProjectInfraDataLBServerDetails> lbServerDetails) {
		this.lbServerDetails = lbServerDetails;
	}

	public List<ProjectInfraDataLeasedLineDetails> getLeasedLineDetails() {
		return leasedLineDetails;
	}

	public void setLeasedLineDetails(List<ProjectInfraDataLeasedLineDetails> leasedLineDetails) {
		this.leasedLineDetails = leasedLineDetails;
	}

	public List<ProjectInfraDataServerDetails> getServerDetails() {
		return serverDetails;
	}

	public void setServerDetails(List<ProjectInfraDataServerDetails> serverDetails) {
		this.serverDetails = serverDetails;
	}



	
}
