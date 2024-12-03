package com.gov.Authmis.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Skey">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>base64Binary">
 *                 &lt;attribute name="ci" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Hmac" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;element name="Data">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>base64Binary">
 *                 &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Pv">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>base64Binary">
 *                 &lt;attribute name="otp" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="otp">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>base64Binary">
 *                 &lt;attribute name="channel" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="uid" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="tid" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="subaua" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ip" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="fdc" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="idc" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="bt" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="udc" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="macadd" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="lot" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="lov" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="pfr" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="lr" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="lk" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "skey",
    "hmac",
    "data",
    "pv",
    "otp"
})
@XmlRootElement(name = "authrequest")
public class AuthrequestEkyc {

    @XmlElement(name = "Skey", required = true)
    protected AuthrequestEkyc.Skey skey;
    @XmlElement(name = "Hmac", required = true)
    protected byte[] hmac;
    @XmlElement(name = "Data", required = true)
    protected AuthrequestEkyc.Data data;
    @XmlElement(name = "Pv", required = true)
    protected AuthrequestEkyc.Pv pv;
    @XmlElement(required = true)
    protected AuthrequestEkyc.Otp otp;
    @XmlAttribute(name = "uid")
    protected String uid;
    @XmlAttribute(name = "tid")
    protected String tid;
    @XmlAttribute(name = "subaua")
    protected String subaua;
    @XmlAttribute(name = "ip")
    protected String ip;
    @XmlAttribute(name = "fdc")
    protected String fdc;
    @XmlAttribute(name = "idc")
    protected String idc;
    @XmlAttribute(name = "bt")
    protected String bt;
    @XmlAttribute(name = "udc")
    protected String udc;
    @XmlAttribute(name = "macadd")
    protected String macadd;
    @XmlAttribute(name = "lot")
    protected String lot;
    @XmlAttribute(name = "lov")
    protected String lov;
    @XmlAttribute(name = "pfr")
    protected String pfr;
    @XmlAttribute(name = "lr")
    protected String lr;
    @XmlAttribute(name = "lk")
    protected String lk;
    @XmlAttribute(name = "rc")
    protected String rc;
    @XmlAttribute(name = "type")
    protected String type;
    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getVer() {
		return ver;
	}

	public void setVer(String ver) {
		this.ver = ver;
	}


	@XmlAttribute(name = "ver")
    protected String ver;

    /**
     * Gets the value of the skey property.
     * 
     * @return
     *     possible object is
     *     {@link AuthrequestEkyc.Skey }
     *     
     */
    public AuthrequestEkyc.Skey getSkey() {
        return skey;
    }

    /**
     * Sets the value of the skey property.
     * 
     * @param value
     *     allowed object is
     *     {@link AuthrequestEkyc.Skey }
     *     
     */
    public void setSkey(AuthrequestEkyc.Skey value) {
        this.skey = value;
    }

    /**
     * Gets the value of the hmac property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getHmac() {
        return hmac;
    }

    /**
     * Sets the value of the hmac property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setHmac(byte[] value) {
        this.hmac = value;
    }

    /**
     * Gets the value of the data property.
     * 
     * @return
     *     possible object is
     *     {@link AuthrequestEkyc.Data }
     *     
     */
    public AuthrequestEkyc.Data getData() {
        return data;
    }

    /**
     * Sets the value of the data property.
     * 
     * @param value
     *     allowed object is
     *     {@link AuthrequestEkyc.Data }
     *     
     */
    public void setData(AuthrequestEkyc.Data value) {
        this.data = value;
    }

    /**
     * Gets the value of the pv property.
     * 
     * @return
     *     possible object is
     *     {@link AuthrequestEkyc.Pv }
     *     
     */
    public AuthrequestEkyc.Pv getPv() {
        return pv;
    }

    /**
     * Sets the value of the pv property.
     * 
     * @param value
     *     allowed object is
     *     {@link AuthrequestEkyc.Pv }
     *     
     */
    public void setPv(AuthrequestEkyc.Pv value) {
        this.pv = value;
    }

    /**
     * Gets the value of the otp property.
     * 
     * @return
     *     possible object is
     *     {@link AuthrequestEkyc.Otp }
     *     
     */
    public AuthrequestEkyc.Otp getOtp() {
        return otp;
    }

    /**
     * Sets the value of the otp property.
     * 
     * @param value
     *     allowed object is
     *     {@link AuthrequestEkyc.Otp }
     *     
     */
    public void setOtp(AuthrequestEkyc.Otp value) {
        this.otp = value;
    }

    /**
     * Gets the value of the uid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUid() {
        return uid;
    }

    /**
     * Sets the value of the uid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUid(String value) {
        this.uid = value;
    }

    /**
     * Gets the value of the tid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTid() {
        return tid;
    }

    /**
     * Sets the value of the tid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTid(String value) {
        this.tid = value;
    }

    /**
     * Gets the value of the subaua property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubaua() {
        return subaua;
    }

    /**
     * Sets the value of the subaua property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubaua(String value) {
        this.subaua = value;
    }

    /**
     * Gets the value of the ip property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIp() {
        return ip;
    }

    /**
     * Sets the value of the ip property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIp(String value) {
        this.ip = value;
    }

    /**
     * Gets the value of the fdc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFdc() {
        return fdc;
    }

    /**
     * Sets the value of the fdc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFdc(String value) {
        this.fdc = value;
    }

    /**
     * Gets the value of the idc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdc() {
        return idc;
    }

    /**
     * Sets the value of the idc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdc(String value) {
        this.idc = value;
    }

    /**
     * Gets the value of the bt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBt() {
        return bt;
    }

    /**
     * Sets the value of the bt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBt(String value) {
        this.bt = value;
    }

    /**
     * Gets the value of the udc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUdc() {
        return udc;
    }

    /**
     * Sets the value of the udc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUdc(String value) {
        this.udc = value;
    }

    /**
     * Gets the value of the macadd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMacadd() {
        return macadd;
    }

    /**
     * Sets the value of the macadd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMacadd(String value) {
        this.macadd = value;
    }

    /**
     * Gets the value of the lot property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLot() {
        return lot;
    }

    /**
     * Sets the value of the lot property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLot(String value) {
        this.lot = value;
    }

    /**
     * Gets the value of the lov property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLov() {
        return lov;
    }

    /**
     * Sets the value of the lov property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLov(String value) {
        this.lov = value;
    }

    /**
     * Gets the value of the pfr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPfr() {
        return pfr;
    }

    /**
     * Sets the value of the pfr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPfr(String value) {
        this.pfr = value;
    }

    /**
     * Gets the value of the lr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLr() {
        return lr;
    }

    /**
     * Sets the value of the lr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLr(String value) {
        this.lr = value;
    }

    /**
     * Gets the value of the lk property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLk() {
        return lk;
    }

    /**
     * Sets the value of the lk property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLk(String value) {
        this.lk = value;
    }


    public String getRc() {
		return rc;
	}

	public void setRc(String rc) {
		this.rc = rc;
	}


	/**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>base64Binary">
     *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class Data {

        @XmlValue
        protected byte[] value;
        @XmlAttribute(name = "type")
        protected String type;

        /**
         * Gets the value of the value property.
         * 
         * @return
         *     possible object is
         *     byte[]
         */
        public byte[] getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         * 
         * @param value
         *     allowed object is
         *     byte[]
         */
        public void setValue(byte[] value) {
            this.value = value;
        }

        /**
         * Gets the value of the type property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getType() {
            return type;
        }

        /**
         * Sets the value of the type property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setType(String value) {
            this.type = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>base64Binary">
     *       &lt;attribute name="channel" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class Otp {

        @XmlValue
        protected byte[] value;
        @XmlAttribute(name = "channel")
        protected String channel;

        /**
         * Gets the value of the value property.
         * 
         * @return
         *     possible object is
         *     byte[]
         */
        public byte[] getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         * 
         * @param value
         *     allowed object is
         *     byte[]
         */
        public void setValue(byte[] value) {
            this.value = value;
        }

        /**
         * Gets the value of the channel property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getChannel() {
            return channel;
        }

        /**
         * Sets the value of the channel property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setChannel(String value) {
            this.channel = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>base64Binary">
     *       &lt;attribute name="otp" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class Pv {

        @XmlValue
        protected byte[] value;
        @XmlAttribute(name = "otp")
        protected String otp;

        /**
         * Gets the value of the value property.
         * 
         * @return
         *     possible object is
         *     byte[]
         */
        public byte[] getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         * 
         * @param value
         *     allowed object is
         *     byte[]
         */
        public void setValue(byte[] value) {
            this.value = value;
        }

        /**
         * Gets the value of the otp property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getOtp() {
            return otp;
        }

        /**
         * Sets the value of the otp property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setOtp(String value) {
            this.otp = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>base64Binary">
     *       &lt;attribute name="ci" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class Skey {

        @XmlValue
        protected byte[] value;
        @XmlAttribute(name = "ci")
        protected String ci;

        /**
         * Gets the value of the value property.
         * 
         * @return
         *     possible object is
         *     byte[]
         */
        public byte[] getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         * 
         * @param value
         *     allowed object is
         *     byte[]
         */
        public void setValue(byte[] value) {
            this.value = value;
        }

        /**
         * Gets the value of the ci property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCi() {
            return ci;
        }

        /**
         * Sets the value of the ci property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCi(String value) {
            this.ci = value;
        }

    }

}
