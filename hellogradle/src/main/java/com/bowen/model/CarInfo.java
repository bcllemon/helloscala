package com.bowen.model;

import org.springframework.data.annotation.Id;

/**
 * Created by bcl on 16/5/9.
 */
public class CarInfo {
    @Id
    public String id;

    public String CredentislasNum;
    public String LicenseNo;
    public String CityCode;
    public String CarVin;
    public String ModleName;
    public String IdType;
    public String InsuredName;
    public String BusinessExpireDate;
    public String ForceExpireDate;
    public String CarUsedType;
    public Integer Source;
    public String PurchasePrice;
    public String EngineNo;
    public String NextBusinessStartDate;
    public String PostedName;
    public String RegisterDate;
    public String NextForceStartDate;
    public String LicenseOwner;
    public String SeatCount;
}
