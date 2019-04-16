import { Component, OnInit } from '@angular/core';
import { SessionService } from '../session.service';
import { NavController } from '@ionic/angular';
import { PatientService } from '../patient.service';

@Component({
  selector: 'app-view-booking',
  templateUrl: './view-booking.page.html',
  styleUrls: ['./view-booking.page.scss'],
})
export class ViewBookingPage implements OnInit {
  currentUserCurrentQueuePosition:number;
  bookingId:number;
  clinicId:number;
  queueNum:number;

  constructor(public sessionService: SessionService, private patientService: PatientService, private navigationCtrl: NavController) { }

  ngOnInit() {
    this.bookingId = this.sessionService.getBookingId();
    this.clinicId = Object.values(this.sessionService.getClinicObj())[0];
  }

  ionViewWillEnter() {
    this.patientService.retrieveCurrentBookingQueuePosition(String(this.bookingId), String(this.clinicId)).subscribe(
      response => {
        this.queueNum = response.queueNumber
      }
    )
    console.log("BookingId, ClinicId: " + this.bookingId + ", " + this.clinicId);
  }
}
