import { ApplicationStatus } from './application-status';

export class Clinic {


clinicId : number;
clinicName: String ;
description : String;
address : String ;
phoneNum : String;
unitPrice : number;
applicationStatus: ApplicationStatus;
    
    constructor(clinicId?: number,clinicName? : String , description?: String , address?: String , phoneNum?: String ,   unitPrice?: number, applicationStatus?: ApplicationStatus) {
     this.clinicId=clinicId;
     this.clinicName= clinicName;
     this.description = description;
     this.address = address;
     this.phoneNum = phoneNum;
     this.unitPrice = unitPrice;
     this.applicationStatus = applicationStatus;
    }
}
