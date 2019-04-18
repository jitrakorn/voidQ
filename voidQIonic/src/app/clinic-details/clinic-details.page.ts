import { Component, OnInit } from '@angular/core';
import { SessionService } from '../session.service';
import { NgForm } from '@angular/forms';
import { Booking } from '../booking';
import { BookingService } from '../booking.service';
import { NavController } from '@ionic/angular';

@Component({
	selector: 'app-clinic-details',
	templateUrl: './clinic-details.page.html',
	styleUrls: ['./clinic-details.page.scss'],
})
export class ClinicDetailsPage implements OnInit {
	clinic: Object;
	submitted: boolean;
	visitreason: String;

	constructor(public sessionService: SessionService, private bookingService: BookingService, private navigationCtrl: NavController) {
		this.submitted = false;
	}

	ngOnInit() {

	}

	ionViewWillEnter() {
		var clinicVals = Object.values(this.sessionService.getClinicObj());
		this.clinic = {
			clinicId: clinicVals[0],
			address: clinicVals[1],
			clinicName: clinicVals[2],
			applicationStatus: clinicVals[3],
			description: clinicVals[4],
			lat: clinicVals[5],
			lng: clinicVals[6],
			phoneNum: clinicVals[7],
			unitPrice: clinicVals[8],
			queueNum: clinicVals[9]
		}
	}

	makeBooking(makeBookingForm: NgForm) {
		this.submitted = true;

		console.log(Object.values(this.sessionService.getClinicObj())[0].toString());
		console.log(this.visitreason);
		console.log(Object.values(this.sessionService.getCurrentPatient())[0].toString());

		if (makeBookingForm.valid) {
			this.bookingService.createBooking(Object.values(this.sessionService.getCurrentPatient())[0].toString(), this.visitreason.toString(), Object.values(this.sessionService.getClinicObj())[0].toString()).subscribe(
				response => {
					let newBookingId: number = response.bookingId;
					this.sessionService.setBookingId(newBookingId);
					this.navigationCtrl.navigateForward("/view-booking");
				}
			)
		}
	}
}
