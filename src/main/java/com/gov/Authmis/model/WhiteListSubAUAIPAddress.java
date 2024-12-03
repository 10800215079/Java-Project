package com.gov.Authmis.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


public class WhiteListSubAUAIPAddress {
        
        private Long id;
        
        private String subAuaCode;
        
        private String subAuaName;

        private String servicetype;
        
        private String iPAddress;
        
        private Date insertDate =new Date();
        
        private String status;
        
        private String appName;
        
        private String schemeName;
        
        private String ipBelongsTo;
        
        private Long isDocPublished;

        public WhiteListSubAUAIPAddress() {
                super();
        }

  

		public WhiteListSubAUAIPAddress(Long id, String subAuaCode, String subAuaName, String servicetype,
				String iPAddress, Date insertDate, String status, String appName, String schemeName, String ipBelongsTo,
				Long isDocPublished) {
			super();
			this.id = id;
			this.subAuaCode = subAuaCode;
			this.subAuaName = subAuaName;
			this.servicetype = servicetype;
			this.iPAddress = iPAddress;
			this.insertDate = insertDate;
			this.status = status;
			this.appName = appName;
			this.schemeName = schemeName;
			this.ipBelongsTo = ipBelongsTo;
			this.isDocPublished = isDocPublished;
		}



		public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getSubAuaCode() {
                return subAuaCode;
        }

        public void setSubAuaCode(String subAuaCode) {
                this.subAuaCode = subAuaCode;
        }

        public String getSubAuaName() {
                return subAuaName;
        }

        public void setSubAuaName(String subAuaName) {
                this.subAuaName = subAuaName;
        }

        public String getServicetype() {
                return servicetype;
        }

        public void setServicetype(String servicetype) {
                this.servicetype = servicetype;
        }

        public String getiPAddress() {
                return iPAddress;
        }

        public void setiPAddress(String iPAddress) {
                this.iPAddress = iPAddress;
        }

        public Date getInsertDate() {
                return insertDate;
        }

        public void setInsertDate(Date insertDate) {
                this.insertDate = insertDate;
        }

        public String getStatus() {
                return status;
        }

        public void setStatus(String status) {
                this.status = status;
        }

        public String getAppName() {
                return appName;
        }

        public void setAppName(String appName) {
                this.appName = appName;
        }

		public String getSchemeName() {
			return schemeName;
		}

		public void setSchemeName(String schemeName) {
			this.schemeName = schemeName;
		}

		public String getIpBelongsTo() {
			return ipBelongsTo;
		}

		public void setIpBelongsTo(String ipBelongsTo) {
			this.ipBelongsTo = ipBelongsTo;
		}

		public Long getIsDocPublished() {
			return isDocPublished;
		}

		public void setIsDocPublished(Long isDocPublished) {
			this.isDocPublished = isDocPublished;
		}
        
          
}
