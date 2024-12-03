package com.gov.Authmis.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MIS_NONLIVE_TRN_LOGS")
public class MisNonLiveTrnLogsEntity {

        @Id
        @Column(name = "id")
        private long id;

        @Column(name = "AUTH_RESPONSE_CODE")
        private String authResponseCode;

        @Column(name = "AUA")
        private String aua;

        @Column(name = "ASA")
        private String asa;

        @Column(name = "SA")
        private String sa;

        @Column(name = "APPNAME")
        private String appname;

        @Column(name = "ENR_REF_ID")
        private String enrRefId;

        @Column(name = "DEVICE_PROVIDER_ID")
        private String deviceProviderId;

        @Column(name = "DEVICE_CODE")
        private String deviceCode;

        @Column(name = "MODEL_ID")
        private String modelId;

        @Column(name = "FINGER_MATCH_SCORE")
        private String fingerMatchScore;

        @Column(name = "PID_TS")
        private String pidTs;

        @Column(name = "REQ_DATETIME")
        private String reqDateTime;

        @Column(name = "DT")
        private String dt;

        @Column(name = "TXN")
        private String txn;

        @Column(name = "AUA_NAME")
        private String auaName;

        @Column(name = "RO_MAPPED")
        private String roMapped;

        private String uuid;
        private String aadhaarId;
        private String isBlocked;
        private String batchId;
        private String ssoId;
        private String pdfPath;
        private String emailDate;
        private String fromDate;
        private String toDate;
        
        public String getIsBlocked() {
                return isBlocked;
        }

        public void setIsBlocked(String isBlocked) {
                this.isBlocked = isBlocked;
        }

        public MisNonLiveTrnLogsEntity() {

        }

        public MisNonLiveTrnLogsEntity(long id,String authResponseCode,String aua,String asa,String sa,String appname,String enrRefId,String deviceProviderId, String deviceCode,
                        String modelId,String fingerMatchScore,Date pidTs,Date reqDateTime,Date dt,String txn,String auaName,String roMapped,String ssoId,String batchId,String pdfPath,
                        Date emailDate) {

        }
        
        


        public MisNonLiveTrnLogsEntity(String authResponseCode, String sa, String enrRefId, String deviceProviderId,
                        String deviceCode, String modelId, String fingerMatchScore, String pidTs, String reqDateTime, String dt,
                        String txn, String emailDate, String fromDate, String toDate) {
                super();
                this.authResponseCode = authResponseCode;
                this.sa = sa;
                this.enrRefId = enrRefId;
                this.deviceProviderId = deviceProviderId;
                this.deviceCode = deviceCode;
                this.modelId = modelId;
                this.fingerMatchScore = fingerMatchScore;
                this.pidTs = pidTs;
                this.reqDateTime = reqDateTime;
                this.dt = dt;
                this.txn = txn;
                this.emailDate = emailDate;
                this.fromDate = fromDate;
                this.toDate = toDate;
        }

        public long getId() {
                return id;
        }

        public void setId(long id) {
                this.id = id;
        }

        public String getAuthResponseCode() {
                return authResponseCode;
        }

        public void setAuthResponseCode(String authResponseCode) {
                this.authResponseCode = authResponseCode;
        }

        public String getAua() {
                return aua;
        }

        public void setAua(String aua) {
                this.aua = aua;
        }

        public String getAsa() {
                return asa;
        }

        public void setAsa(String asa) {
                this.asa = asa;
        }

        public String getSa() {
                return sa;
        }

        public void setSa(String sa) {
                this.sa = sa;
        }

        public String getAppname() {
                return appname;
        }

        public void setAppname(String appname) {
                this.appname = appname;
        }

        public String getEnrRefId() {
                return enrRefId;
        }

        public void setEnrRefId(String enrRefId) {
                this.enrRefId = enrRefId;
        }

        public String getDeviceProviderId() {
                return deviceProviderId;
        }

        public void setDeviceProviderId(String deviceProviderId) {
                this.deviceProviderId = deviceProviderId;
        }

        public String getDeviceCode() {
                return deviceCode;
        }

        public void setDeviceCode(String deviceCode) {
                this.deviceCode = deviceCode;
        }

        public String getModelId() {
                return modelId;
        }

        public void setModelId(String modelId) {
                this.modelId = modelId;
        }

        public String getFingerMatchScore() {
                return fingerMatchScore;
        }

        public void setFingerMatchScore(String fingerMatchScore) {
                this.fingerMatchScore = fingerMatchScore;
        }

        public String getPidTs() {
                return pidTs;
        }

        public void setPidTs(String pidTs) {
                this.pidTs = pidTs;
        }

        public String getReqDateTime() {
                return reqDateTime;
        }

        public void setReqDateTime(String reqDateTime) {
                this.reqDateTime = reqDateTime;
        }

        public String getDt() {
                return dt;
        }

        public void setDt(String dt) {
                this.dt = dt;
        }

        public String getTxn() {
                return txn;
        }

        public void setTxn(String txn) {
                this.txn = txn;
        }

        public String getAuaName() {
                return auaName;
        }

        public void setAuaName(String auaName) {
                this.auaName = auaName;
        }

        public String getRoMapped() {
                return roMapped;
        }

        public void setRoMapped(String roMapped) {
                this.roMapped = roMapped;
        }

        public String getUuid() {
                return uuid;
        }

        public String getBatchId() {
                return batchId;
        }

        public void setBatchId(String batchId) {
                this.batchId = batchId;
        }

        public String getPdfPath() {
                return pdfPath;
        }

        public void setPdfPath(String pdfPath) {
                this.pdfPath = pdfPath;
        }

        public String getEmailDate() {
                return emailDate;
        }

        public void setEmailDate(String emailDate) {
                this.emailDate = emailDate;
        }

        public void setUuid(String uuid) {
                this.uuid = uuid;
        }

        public String getAadhaarId() {
                return aadhaarId;
        }

        public void setAadhaarId(String aadhaarId) {
                this.aadhaarId = aadhaarId;
        }
        

        public String getFromDate() {
                return fromDate;
        }

        public void setFromDate(String fromDate) {
                this.fromDate = fromDate;
        }

        public String getToDate() {
                return toDate;
        }

        public void setToDate(String toDate) {
                this.toDate = toDate;
        }

        public String getSsoId() {
                return ssoId;
        }

        public void setSsoId(String ssoId) {
                this.ssoId = ssoId;
        }

        @Override
        public String toString() {
                return "MisNonLiveTrnLogsEntity [id=" + id + ", authResponseCode=" + authResponseCode + ", aua=" + aua
                                + ", asa=" + asa + ", sa=" + sa + ", appname=" + appname + ", enrRefId=" + enrRefId
                                + ", deviceProviderId=" + deviceProviderId + ", deviceCode=" + deviceCode + ", modelId=" + modelId
                                + ", fingerMatchScore=" + fingerMatchScore + ", pidTs=" + pidTs + ", reqDateTime=" + reqDateTime
                                + ", dt=" + dt + ", txn=" + txn + ", auaName=" + auaName + ", roMapped=" + roMapped + ", uuid=" + uuid
                                + ", aadhaarId=" + aadhaarId + ", isBlocked=" + isBlocked + ", batchId=" + batchId + ", ssoId=" + ssoId
                                + ", pdfPath=" + pdfPath + ", emailDate=" + emailDate + ", fromDate=" + fromDate + ", toDate=" + toDate
                                + "]";
        }
        
}