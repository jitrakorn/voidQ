import { BookingStatus } from "./booking-status";

export class Booking {
    bookingId : number;
    bookingStatus : BookingStatus;
    visitReason: String;
    transactionDateTime : Date;

    constructor(bookingId?:number, bookingStatus?:BookingStatus, visitReason?:String, transactionDateTime?:Date) {
        this.bookingId = bookingId;
        this.bookingStatus = bookingStatus;
        this.visitReason = visitReason;
        this.transactionDateTime = transactionDateTime;
    }
}
